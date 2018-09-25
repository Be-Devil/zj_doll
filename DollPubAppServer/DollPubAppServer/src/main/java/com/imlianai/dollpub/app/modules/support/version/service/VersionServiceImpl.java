package com.imlianai.dollpub.app.modules.support.version.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.support.banner.service.BannerService;
import com.imlianai.dollpub.app.modules.support.version.dao.VersionDAO;
import com.imlianai.dollpub.constants.DollCacheConst;
import com.imlianai.dollpub.domain.banner.Banner;
import com.imlianai.dollpub.domain.banner.Banner.BannerLocationType;
import com.imlianai.dollpub.domain.version.Channel;
import com.imlianai.dollpub.domain.version.Version;
import com.imlianai.dollpub.domain.version.VersionBootimg;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class VersionServiceImpl implements VersionService {

	@Resource
	private VersionDAO versionDAO;

	@Resource
	private BannerService bannerService;
	
	protected final BaseLogger logger = BaseLogger.getLoggerSwitch(this.getClass());

	@Override
	public int addVersion(Version version) {
		return versionDAO.addVersion(version);
	}

	@Override
	public int updateVersion(int osType, String channel, int valid) {
		return versionDAO.updateVersion(osType, channel, valid);
	}

	@Override
	public Version getVersion(int osType, String channel) {
		return versionDAO.getVersion(osType, channel);
	}

	@Override
	public boolean isAudit(int osType, String channel, int versionCode) {
		return isAuditChannel(channel, versionCode);
	}

	@Override
	public int addIgnore(long uid, int versionCode) {
		return versionDAO.addIgnore(uid, versionCode);
	}

	@Override
	public boolean isIgnore(long uid) {
		return versionDAO.getIgnore(uid) > 0;
	}

	@Override
	public VersionBootimg getBootimg(long uid, int osType, String channel,int customerId) {
		List<Banner> list = bannerService.getBannerByLocation(uid,
				BannerLocationType.BOOTIMG_BANNER.type, osType, channel,customerId);
		if (!StringUtil.isNullOrEmpty(list)) {
			return new VersionBootimg(list.get(0));
		}
		return null;
	}

	@Override
	@CacheWrite(key = DollCacheConst.VERSION_CHANNEL_CACHE, pkField = {
			"version", "channel" }, validTime = 60)
	public boolean isAuditChannel(String channel, int version) {
		boolean isIOSHide = false;// 是否ios隐藏
		List<Channel> list = getChannels();
		if (list != null) {
			for (int i = 0, len = list.size(); i < len; i++) {
				Channel cnf = list.get(i);
				if (StringUtils.equals(channel, cnf.getCode())
						&& cnf.isActive() && cnf.isAudit()
						&& version == cnf.getAuditVersion()) {
					isIOSHide = true;
					break;
				}
			}
		}
		return isIOSHide;
	}

	@Override
	public int updateChannel(Channel channel) {
		return versionDAO.updateChannel(channel);
	}

	@Override
	public List<Channel> getChannels() {
		return versionDAO.getChannels();
	}

	@Override
	public VersionBootimg getCenterAd(long uid, int osType, String channel,int customerId) {
		List<Banner> list = bannerService.getBannerByLocation(uid,
				BannerLocationType.APP_BANNER.type, osType, channel,customerId);
		if (!StringUtil.isNullOrEmpty(list)) {
			for (Banner banner : list) {
				return new VersionBootimg(banner);
				/*if(bannerService.getBannerWatchRecord(uid, banner.getId())==0){
					bannerService.addBannerWatchRecord(uid, banner.getId());
					return new VersionBootimg(banner);
				}*/
			}
		}
		return null;
	}
}
