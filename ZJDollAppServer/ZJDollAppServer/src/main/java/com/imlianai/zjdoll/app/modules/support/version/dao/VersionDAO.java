package com.imlianai.zjdoll.app.modules.support.version.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.version.Channel;
import com.imlianai.zjdoll.domain.version.Version;

/**
 * 版本检查相关
 * 
 * @author tensloveq
 * 
 */
public interface VersionDAO {

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
	 * 获取版本信息
	 * 
	 * @return
	 */
	public List<Version> getVersions();
	/**
	 * 获取最新版本信息
	 * @param osType
	 * @param channel
	 * @return
	 */
	public Version getVersion(int osType, String channel);
	
	/**
	 * 插入本次忽略记录
	 * 
	 * @param uid
	 * @param versionCode
	 * @return
	 */
	public int addIgnore(long uid, int versionCode);
	/**
	 * 取出最近的忽略更新记录 -当前取3天内是否有记录
	 * 
	 * @param uid
	 * @return 0无忽略记录 1有忽略记录
	 */
	public int getIgnore(long uid);

	/**
	 * 获取所有渠道
	 * @return
	 */
	public List<Channel> getChannels();
	
	/**
	 * 更改channel
	 * @param channel
	 */
	public int updateChannel(Channel channel);
	
	public String getBGM();
	
	/**
	 * 更新设备信息
	 * @param imei
	 */
	public void updateDeviceInfo(String imei,String channel,int osType);
}
