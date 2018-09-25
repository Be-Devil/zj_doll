package com.imlianai.dollpub.app.modules.support.banner.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.support.banner.dao.BannerDAO;
import com.imlianai.dollpub.constants.DollCacheConst;
import com.imlianai.dollpub.domain.banner.Banner;
import com.imlianai.dollpub.domain.banner.BannerRes;
import com.imlianai.dollpub.domain.banner.Banner.BannerLocationType;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class BannerServiceImpl implements BannerService {

	@Resource
	private BannerDAO bannerDAO;

	@Override
	public int updateBanner(Banner banner) {
		return bannerDAO.updateBanner(banner);
	}

/*	@CacheWrite(key = DollCacheConst.BANNERS_CACHE, pkField = { "osType",
			"version", "channel", "location" }, validTime = 300)*/
	@Override
	public List<BannerRes> getBanners(int osType, int version, String channel, int location,int customerId) {
		List<BannerRes> list = new ArrayList<BannerRes>();
		List<Banner> banners = bannerDAO.getBanners(location,customerId);
		if (banners == null || banners.isEmpty())
			return list;
		for (Banner banner : banners) {
			List<Integer> locationList=banner.getLocationList();
			if (locationList.contains(location)) {
				list.add(new BannerRes(banner));
			}
			/*if (isBannerTarget(banner, channel, osType)) {
				list.add(new BannerRes(banner));
			}*/
		}
		return list;
	}

	@Override
	public boolean isBannerTarget(Banner banner, String channel, int osType) {
		boolean isTarget = true;
		String bannerStartTime = banner.getStartTime();
		String bannerEndTime = banner.getEndTime();
		if (bannerStartTime.trim().contains("false")) {
			bannerStartTime = "1900-01-01";
		}
		if (bannerEndTime.trim().contains("false")) {
			bannerEndTime = "2999-12-31";
		}
		/*List<String> channelList = banner.getChannelList();
		List<String> osTypeList = banner.getOsTypeList();
		if (DateUtils.isBetween(bannerStartTime, bannerEndTime)) {
			if (channelList == null || channelList.isEmpty()) {// 针对全站
				if (osTypeList.contains(osType + "")) {
					isTarget = true;
				}
			} else {// 针对渠道
				if (channelList.contains(channel)) {
					if (osTypeList.contains(osType + "")) {
						isTarget = true;
					}
				}
			}
		}*/
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
			String channel,int customerId) {
		List<Banner> orglist = bannerDAO.getBanners(location,customerId);
		List<Banner> res = new ArrayList<Banner>();
		List<Banner> list = getLoactionList(uid, location, osType, channel,
				orglist);
		if (!StringUtil.isNullOrEmpty(list)) {
			res = list;
		}
		return res;
	}

	private List<Banner> getLoactionList(long uid, int location, int osType,
			String channel, List<Banner> list) {
		List<Banner> res = new ArrayList<Banner>();
		if (!StringUtil.isNullOrEmpty(list)) {
			for (Banner banner : list) {
				List<Integer> locationList=banner.getLocationList();
				if (locationList.contains(location)) {
					res.add(banner);
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
