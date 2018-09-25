package com.imlianai.zjdoll.app.modules.support.banner.service;

import java.util.List;

import com.imlianai.zjdoll.domain.banner.Banner;
import com.imlianai.zjdoll.domain.banner.BannerRes;

/**
 * 轮播图相关
 * @author Max
 *
 */
public interface BannerService {
	
	/**
	 * 更新banner
	 * @param banner
	 * @return
	 */
	public int updateBanner(Banner banner);
	/**
	 * 获取banner列表
	 * @param osTyep
	 * @param version
	 * @param channel
	 * @param location 
	 * @return
	 */
	public List<BannerRes> getBanners(int osTyep, int version, String channel, int location,long uid,String loginKey);

	/**
	 * 判断是否是banner目标用户
	 * @param banner
	 * @param channel
	 * @param osType
	 * @return
	 */
	public boolean isBannerTarget(Banner banner, String channel, int osType,int version);
	
	/**
	 * 删除banner
	 * @param bannerId
	 * @return
	 */
	public int delBanner(int bannerId);
	
	/**
	 * 上下架
	 * @param bannerId
	 * @param valid
	 * @return
	 */
	public int updateVaild(int bannerId,int valid);
	
	/**
	 * 获取banner列表
	 * @param valid
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<Banner> getBanners(int valid,int page,int pageSize);
	
	/**
	 * 获取特定位置banner
	 * @param uid
	 * @param location
	 * @return
	 */
	public List<Banner> getBannerByLocation(long uid,int location,int osType,String channel,String loginKey,int version);
	
	/**
	 * 增加观看记录
	 * @param uid
	 * @param bannerId
	 */
	public void addBannerWatchRecord(long uid,int bannerId);
	
	/**
	 * 获取观看记录
	 * @param uid
	 * @param bannerId
	 * @return
	 */
	public int getBannerWatchRecord(long uid,int bannerId);
}
