package com.imlianai.dollpub.app.modules.support.shipping.dao;

import java.util.List;

import com.imlianai.dollpub.domain.doll.DollOrderInfo;
import com.imlianai.dollpub.domain.doll.DollOrderRecord;
import com.imlianai.dollpub.domain.shipping.DollShippingNotice;

public interface ShippingDao {

	/**
	 * 娃娃订单信息
	 * @param uid
	 * @param uDollId
	 * @param orderNum
	 * @param dollId
	 * @param optId
	 * @param company 
	 * @return
	 */
	int saveDollOrderInfo(Long uid, long uDollId, long orderNum, int dollId, long optId, int company);

	/**
	 * 保存娃娃订单记录
	 * @param orderNum
	 * @param uid
	 * @param addressId 
	 * @param customerId 
	 * @return
	 */
	int saveDollOrderRecord(long orderNum, Long uid, Long addressId, int customerId,long agencId, String remark,int payStatus,int amt);

	/**
	 * 获取用户的娃娃发货次数
	 * @param uid
	 * @return
	 */
	int getShippingTimes(Long uid);

	/**
	 * 获取发货通知
	 * @return
	 */
	DollShippingNotice getShippingNotice();

	/**
	 * 保存娃娃发货记录
	 * @param uid
	 * @param orderNum
	 * @param customerId 
	 * @return
	 */
	int saveShippingRecord(Long uid, long orderNum, int customerId);

	/**
	 * 更新支付状态
	 * @param orderNum
	 * @param payStatus
	 * @return
	 */
	int updateOrderRecordPayStatus(long orderNum, int payStatus);

	/**
	 * 获取过期的记录
	 * @param minutes
	 * @return
	 */
	DollOrderRecord getInvalidRecord(int minutes);

	/**
	 * 根据订单号获取全部订单信息
	 * @param orderNum
	 * @return
	 */
	List<DollOrderInfo> getDollOrderInfos(long orderNum);

	/**
	 * 根据订单id获取订单
	 * @param orderNum
	 * @return
	 */
	DollOrderRecord getDollOrderRecord(long orderNum);

	/**
	 * 根据uid获取全部订单
	 * @param uid
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<DollOrderRecord> getDollOrderRecordsByUid(long uid, int page,
			int pageSize);

	/**
	 * 根据订单号获取全部发货订单
	 * @param orderNum
	 * @return
	 */
	List<DollOrderInfo> getDollOrderInfosAll(long orderNum);

}
