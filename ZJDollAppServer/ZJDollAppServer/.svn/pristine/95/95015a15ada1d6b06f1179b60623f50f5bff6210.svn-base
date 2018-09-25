package com.imlianai.zjdoll.app.modules.support.version.service;

import java.util.List;

import com.imlianai.zjdoll.domain.banner.Banner;
import com.imlianai.zjdoll.domain.version.Channel;
import com.imlianai.zjdoll.domain.version.Version;
import com.imlianai.zjdoll.domain.version.VersionBootimg;

/**
 * 版本更新
 * @author tensloveq
 *
 */
public interface VersionService {
	
	/**
	 * 新增版本
	 * @param version
	 * @return
	 */
	public int addVersion(Version version);
	/**
	 * 更新版本状态
	 * @param osType
	 * @param channel
	 * @param valid
	 * @return
	 */
	public int updateVersion(int osType, String channel, int valid);
	/**
	 * 获取最新版本信息
	 * @param osType
	 * @param channel
	 * @return
	 */
	public Version getVersion(int osType, String channel);
	
	/**
	 * 判断是否在审核中
	 * @param channel
	 * @return
	 */
	public boolean isAudit(int osType, String channel, int versionCode);
	
	/**
	 * 插入忽略更新记录
	 * @param uid
	 * @param versionCode 此次忽略版本
	 * @return
	 */
	public int addIgnore(long uid, int versionCode);
	/**
	 * 是否已忽略版本更新
	 * @param uid
	 * @return
	 */
	public boolean isIgnore(long uid);
	
	/**
	 * 获取最新闪屏
	 * @return
	 */
	public VersionBootimg getBootimg(long uid,int osType, String channel,String loginKey,int version);
	
	
	/**
	 * 获取中部广告
	 * @param uid
	 * @param osType
	 * @param channel
	 * @return
	 */
	public Banner getCenterAd(long uid,int osType, String channel,String loginKey,int version);
	
	/**
	 * 判断是否在审核中
	 * @param channel
	 * @return
	 */
	public boolean isAuditChannel(String channel,int version);
	
	/**
	 * 是否有ios审核中
	 * @return
	 */
	public boolean isIosAudit();
	
	/**
	 * 更新channel
	 * @param channel
	 */
	public int updateChannel(Channel channel);
	
	/**
	 * 获取渠道列表
	 * @return
	 */
	public List<Channel> getChannels();
	
	public String getBGM();
	
	
	/**
	 * 更新设备信息
	 * @param imei
	 */
	public void updateDeviceInfo(String imei,String channel,int osType);
}
