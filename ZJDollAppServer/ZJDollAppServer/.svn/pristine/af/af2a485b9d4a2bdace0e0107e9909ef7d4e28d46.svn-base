package com.imlianai.zjdoll.app.modules.support.version.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.constants.DollCacheConst;
import com.imlianai.zjdoll.domain.banner.Banner;
import com.imlianai.zjdoll.domain.banner.Banner.BannerLocationType;
import com.imlianai.zjdoll.domain.version.Channel;
import com.imlianai.zjdoll.domain.version.Version;
import com.imlianai.zjdoll.domain.version.VersionBootimg;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.manager.cache.CacheManager;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.UserFirstChargeTarget;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.utils.CatalogUtils;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.zjdoll.app.modules.support.banner.service.BannerService;
import com.imlianai.zjdoll.app.modules.support.version.dao.VersionDAO;

@Service
public class VersionServiceImpl implements VersionService {

	@Resource
	private VersionDAO versionDAO;

	@Resource
	private BannerService bannerService;
	@Resource
	private CacheManager cacheManager;
	@Resource
	private TradeChargeService tradeChargeService;
	
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
		try {
			return isAuditChannel(channel, versionCode);
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return true;
		}
		
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
	public VersionBootimg getBootimg(long uid, int osType, String channel,String loginKey,int version) {
		List<Banner> list = bannerService.getBannerByLocation(uid,
				BannerLocationType.BOOTIMG_BANNER.type, osType, channel,loginKey,version);
		if (!StringUtil.isNullOrEmpty(list)) {
			return new VersionBootimg(list.get(0));
		}
		return null;
	}

	@Override
	/*@CacheWrite(key = DollCacheConst.VERSION_CHANNEL_CACHE, pkField = {
			"version", "channel" }, validTime = 60)*/
	public boolean isAuditChannel(String channel, int version) {
		boolean isIOSHide = false;// 是否ios隐藏
		List<Channel> list = getChannels();
		if (list != null) {
			for (int i = 0, len = list.size(); i < len; i++) {
				Channel cnf = list.get(i);
				if (StringUtils.equals(channel, cnf.getCode())
						&& cnf.isAActive() && cnf.isAAudit()
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
	public Banner getCenterAd(long uid, int osType, String channel,String loginKey,int version) {
		//首冲弹框
		UserFirstChargeTarget code=tradeChargeService.getUserFirstChargeTarget(uid);
		if (code!=null&&code.getPushCount()>0&&code.getCode()>0) {
			if (!hasShowChargeImg(uid)) {
				Banner banner=new Banner();
				if (code.getCode()==CatalogUtils.code20) {
					banner.setImgUrl("http://lianai-image-sys.qiniudn.com/e20180310/20180310173050.png");
				}else{
					banner.setImgUrl("http://lianai-image-sys.qiniudn.com/e20180310/20180310173107.png");
				}
				banner.setJumpCharge(code.getCode());
				return banner;
			}
		}
		List<Banner> list = bannerService.getBannerByLocation(uid,
				BannerLocationType.APP_BANNER.type, osType, channel,loginKey, version);
		if (!StringUtil.isNullOrEmpty(list)) {
			for (Banner banner : list) {
				if(bannerService.getBannerWatchRecord(uid, banner.getId())==0){
					bannerService.addBannerWatchRecord(uid, banner.getId());
					return (banner);
				}
			}
		}
		return null;
	}

	/**
	 * 是否显示过首冲推送引导
	 * @param uid
	 * @return
	 */
	private boolean hasShowChargeImg(long uid){
		String key=DollCacheConst.CHARGE_PUSH_AD+":"+uid;
		String value=cacheManager.getString(key, 0);
		logger.info("hasShowChargeImg uid:"+uid+" value:"+value);
		if (StringUtil.isNullOrEmpty(value)||!value.trim().equals("1")) {
			value="1";
			cacheManager.setString(key, value, 24*3600, 0);
			return false;
		}
		return true;
	}
	@Override
	@CacheWrite(validTime=10)
	public String getBGM() {
		return versionDAO.getBGM();
	}

	@Override
	@CacheWrite(validTime = 60)
	public boolean isIosAudit() {
		boolean isIOSHide = false;// 是否ios隐藏
		List<Channel> list = getChannels();
		if (list != null) {
			for (int i = 0, len = list.size(); i < len; i++) {
				Channel cnf = list.get(i);
				if (cnf.getOsType()>0
						&& cnf.isAActive() && cnf.isAAudit()&&!"appstore".equals(cnf.getCode())) {
					isIOSHide = true;
					break;
				}
			}
		}
		return isIOSHide;
	}

	@Override
	public void updateDeviceInfo(String imei,String channel,int osType) {
		if (!StringUtil.isNullOrEmpty(imei)) {
			versionDAO.updateDeviceInfo(imei,channel,osType);
		}
	}
}
