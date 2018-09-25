package com.imlianai.dollpub.app.modules.support.shipping.service;

import java.util.List;

import com.imlianai.dollpub.app.modules.support.shipping.vo.ApplyDollInfo;
import com.imlianai.dollpub.domain.doll.BaseDollInfo;
import com.imlianai.dollpub.domain.doll.DollOrderInfo;
import com.imlianai.dollpub.domain.doll.DollOrderRecord;
import com.imlianai.dollpub.domain.shipping.DollOrderRecordBill;
import com.imlianai.dollpub.domain.trade.TradeCharge;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public interface ShippingService {

	/**
	 * 获取用户可发货的娃娃列表
	 * @param uid
	 * @return
	 */
	public List<BaseDollInfo> getShippingList(Long uid);

	/**
	 * 申请发货
	 * @param dollList
	 * @param uid
	 * @param addressId 
	 * @return
	 */
	public BaseRespVO applyShipping(List<ApplyDollInfo> dollList, Long uid, Long addressId);
	
	/**
	 * 获取邮费
	 * @param dollList
	 * @param uid
	 * @return
	 */
	public int getPostage(List<ApplyDollInfo> dollList, Long uid);

	/**
	 * 申请发货验证手机是否绑定
	 * @param uid
	 * @return
	 */
	public BaseRespVO phonebind(Long uid);

	/**
	 * 获取发货须知
	 * @return
	 */
	public String getShippingNotice();

	/**
	 * 申请发货-订单
	 * @param dollList
	 * @param uid
	 * @param addressId
	 * @param remark
	 * @return
	 */
	public BaseRespVO applyShippingBill(List<ApplyDollInfo> dollList, Long uid,
			Long addressId, String remark);

	/**
	 * 获取油费
	 * @param dollList
	 * @param uid
	 * @return
	 */
	public int getPostageRmb(List<ApplyDollInfo> dollList, Long uid);

	/**
	 * 更新订单支付状态
	 * @param orderNum
	 * @param payStatus
	 * @return
	 */
	public int updateOrderRecordPayStatus(long orderNum, int payStatus);

	/**
	 * 更新支付状态
	 * @param charge
	 * @return
	 */
	public boolean handleShippingPayed(TradeCharge charge);

	/**
	 * 定时处理过期订单
	 */
	public void handleShippingInvalid();

	/**
	 * 根据订单号获取订单娃娃状态
	 * @param orderNum
	 * @return
	 */
	public List<DollOrderInfo> getDollOrderInfos(long orderNum);

	/**
	 * 根据订单id获取订单
	 * @param orderNum
	 * @return
	 */
	DollOrderRecord getDollOrderRecord(long orderNum);

	/**
	 * 取消订单
	 * @param uid
	 * @param orderNum
	 * @return
	 */
	BaseRespVO cancelShippingBill(long uid, long orderNum);

	/**
	 * 根据uid获取全部发货订单
	 * @param uid
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<DollOrderRecord> getDollOrderRecordsByUid(long uid, int page,
			int pageSize);

	/**
	 * 根据订单id获取全部发货
	 * @param orderNum
	 * @return
	 */
	List<DollOrderInfo> getDollOrderInfosAll(long orderNum);

	/**
	 * 获取用户全部发货订单
	 * @param uid
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<DollOrderRecordBill> getDollOrderRecordBill(long uid, int page,
			int pageSize);
}
