package com.imlianai.zjdoll.app.modules.core.doll.info;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.DollInfo;

public interface DollInfoDao {

	/**
	 * 获取娃娃信息
	 * @param dollId
	 * @return
	 */
	public DollInfo getDollInfo(int dollId);
	
	/**
	 * 获取全部有效的娃娃信息
	 * @return
	 */
	public List<DollInfo> getAllDollInfos();
	
	/**
	 * 更新商品id
	 * @param dollId
	 * @param goodsId
	 */
	public void updateDollGoodsId(int dollId,String goodsId);

	/**
	 * 修改娃娃上下架状态
	 * @param dollId
	 * @param valid
	 * @return
	 */
	public int updateDollValidById(int dollId, int valid);

	/**
	 * 修改娃娃最后兑换时间
	 * @param dollId
	 * @return
	 */
	public int updateUserDollLastExchangeTime(int dollId);

	/**
	 * 获取最近被兑换过的娃娃列表
	 * @param size
	 * @return
	 */
	public List<DollInfo> getRecentExchangeDollInfos(int size);

	/**
	 * 获取最近被合成过的娃娃列表
	 * @param size
	 * @return
	 */
	public List<DollInfo> getRecentComposeDollInfos(int size);

	/**
	 * 修改娃娃最后合成时间
	 * @param dollId
	 * @return
	 */
	public int updateUserDollLastComposeTime(int dollId);

	/**
	 * 修改娃娃的已兑换数量
	 * @param dollId
	 * @return
	 */
	public int updateDollExchangeNum(int dollId);
}
