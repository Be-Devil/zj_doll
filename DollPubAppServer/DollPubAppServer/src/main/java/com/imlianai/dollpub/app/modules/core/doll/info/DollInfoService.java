package com.imlianai.dollpub.app.modules.core.doll.info;

import java.util.List;
import java.util.Map;

import com.imlianai.dollpub.domain.doll.DollInfo;

public interface DollInfoService {

	/**
	 * 获取娃娃信息
	 * @param dollId
	 * @return
	 */
	public DollInfo getDollInfo(int dollId);
	
	/**
	 * 初始化娃娃信息
	 * @return
	 */
	public void initDollInfos();
	
	/**
	 * 获取娃娃信息
	 * @param dollIds
	 * @return
	 */
	public Map<Integer, DollInfo> getDollInfos(List<Integer> dollIds);
	
	/**
	 * 获取娃娃信息(推荐使用)
	 * @param list 集合对象中必须要带有 dollId 属性
	 * @param fieldNames 属性名 ，传入 "dollId"
	 * @return
	 */
	public Map<Integer, DollInfo> getDollInfos(List<?> list,String fieldNames);
	
	
	/**
	 * 更新商品id
	 * @param dollId
	 * @param goodsId
	 */
	public void updateDollGoodsId(int dollId,String goodsId);
	
	/**
	 * 获取可碎片合成的娃娃列表
	 * @return
	 */
	public List<DollInfo> getComposeDolls();
	
	/**
	 * 获取可钻石兑换的娃娃列表
	 * @return
	 */
	public List<DollInfo> getExchangeDolls(int customerId);
	
	/**
	 * 修改娃娃上下架
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
	public List<DollInfo> getRecentExchangeDollInfos(int customerId,int size);
	
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
}
