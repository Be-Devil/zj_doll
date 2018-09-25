package com.imlianai.zjdoll.app.modules.support.banner.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.banner.Banner;
import com.imlianai.zjdoll.domain.banner.BannerRes;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.support.banner.dao.BannerDAO;
import com.imlianai.zjdoll.app.modules.support.version.service.VersionService;

@Service
public class BannerServiceImpl implements BannerService {

	@Resource
	private BannerDAO bannerDAO;

	@Resource
	private VersionService versionService;
	@Override
	public int updateBanner(Banner banner) {
		return bannerDAO.updateBanner(banner);
	}
	@Override
	public List<BannerRes> getBanners(int osType, int version, String channel, int location,long uid,String loginKey) {
		List<BannerRes> list = new ArrayList<BannerRes>();
		List<Banner> banners = bannerDAO.getBanners(location);
		if (banners == null || banners.isEmpty())
			return list;
		for (Banner banner : banners) {
			List<Integer> locationList=banner.getLocationList();
			if (locationList.contains(location)) {
				if (isBannerTarget(banner, channel, osType,version)) {
							if (banner.getTarget()==1) {
								String url=banner.getUrl().indexOf("?")>-1?(banner.getUrl()+"&uid="+uid+"&loginKey="+loginKey):(banner.getUrl()+"?uid="+uid+"&loginKey="+loginKey);
								banner.setUrl(url);
							}
					list.add(new BannerRes(banner));
				}
			}
		}
		return list;
	}

	@Override
	public boolean isBannerTarget(Banner banner, String channel, int osType,int version) {
		boolean isTarget = false;
		String bannerStartTime = banner.getStartTime();
		String bannerEndTime = banner.getEndTime();
		if (bannerStartTime.trim().contains("false")) {
			bannerStartTime = "1900-01-01";
		}
		if (bannerEndTime.trim().contains("false")) {
			bannerEndTime = "2999-12-31";
		}
		if(banner.getReview()==0){
			if (versionService.isAuditChannel(channel, version)) {
				return false;
			}
		}
		if (DateUtils.isBetween(bannerStartTime, bannerEndTime)) {
			if (!StringUtil.isNullOrEmpty(banner.getChannel())) {
				List<String> channels=null;
				try {
					channels=JSON.parseArray(banner.getChannel(), String.class);
				} catch (Exception e) {
				}
				if (!StringUtil.isNullOrEmpty(channels)) {
					for (String channelBanner : channels) {
						if (channelBanner.trim().equals("gw")) {
							isTarget = true;
							break;
						}else if (channelBanner.trim().equals(channel)) {
							isTarget = true;
							break;
						}else if (channel.startsWith(channelBanner.trim())) {
							isTarget = true;
							break;
						}
					}
				}else{
					isTarget = true;
				}
			}else{
				isTarget = true;
			}
		}
		return isTarget;
	}

	@Override
	public int delBanner(int bannerId) {
		return bannerDAO.delBanner(bannerId);
	}

	@Override
	public int updateVaild(int bannerId, int valid) {
		return bannerDAO.updateVaild(bannerId, valid);
	}

	@Override
	public List<Banner> getBanners(int valid, int page, int pageSize) {
		return bannerDAO.getBanners(valid, page, pageSize);
	}

	@Override
	public List<Banner> getBannerByLocation(long uid, int location, int osType,
			String channel,String loginKey,int version) {
		List<Banner> orglist = bannerDAO.getBanners(location);
		List<Banner> res = new ArrayList<Banner>();
		List<Banner> list = getLoactionList(uid, location, osType, channel,
				orglist,version);
		if (!StringUtil.isNullOrEmpty(list)) {
			for (Banner banner : list) {
				if (banner.getTarget()==1) {
					String url=banner.getUrl().indexOf("?")>-1?(banner.getUrl()+"&uid="+uid+"&loginKey="+loginKey):(banner.getUrl()+"?uid="+uid+"&loginKey="+loginKey);
					banner.setUrl(url);
				}
			}
			res = list;
		}
		return res;
	}

	private List<Banner> getLoactionList(long uid, int location, int osType,
			String channel, List<Banner> list,int version) {
		List<Banner> res = new ArrayList<Banner>();
		if (!StringUtil.isNullOrEmpty(list)) {
			for (Banner banner : list) {
				List<Integer> locationList=banner.getLocationList();
				if (locationList.contains(location)) {
					if (isBannerTarget(banner, channel, osType,version)){
						res.add(banner);
					}
				}
				/*if (isBannerTarget(banner, channel, osType)) {
					list.add(new BannerRes(banner));
				}*/
			}
		}
		return res;
	}

	@Override
	public void addBannerWatchRecord(long uid, int bannerId) {
		bannerDAO.addBannerWatchRecord(uid, bannerId);
	}

	@Override
	public int getBannerWatchRecord(long uid, int bannerId) {
		return 0;
		//return bannerDAO.getBannerWatchRecord(uid, bannerId);
	}

}
