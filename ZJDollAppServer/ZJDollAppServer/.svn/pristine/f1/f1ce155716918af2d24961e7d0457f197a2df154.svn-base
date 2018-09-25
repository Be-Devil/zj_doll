package com.imlianai.zjdoll.app.modules.support.banner.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.banner.Banner;

/**
 * Banner相关
 * @author Max
 *
 */
public interface BannerDAO {

	/**
	 * 更新banner
	 * @param banner
	 * @return
	 */
	public int updateBanner(Banner banner);
	/**
	 * 获取banner
	 * @param bannerId
	 * @return
	 */
	public Banner getBanner(long bannerId);
	/**
	 * 获取banner列表
	 * @return
	 */
	public List<Banner> getBanners(int location);
	
	
	/**
	 * 更新banner有效状态
	 * @param bannerId
	 * @param valid
	 * @return
	 */
	public int updateVaild(int bannerId, int valid);
	
	/**
	 * 删除banner
	 * @param bannerId
	 * @return
	 */
	public int delBanner(int bannerId);
	
	/**
	 * 获取banner列表
	 * @param valid
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<Banner> getBanners(int valid, int page, int pageSize);
	
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
