package com.imlianai.zjdoll.app.modules.support.shipping.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.DollOrderExpress;
import com.imlianai.zjdoll.domain.doll.DollOrderInfo;
import com.imlianai.zjdoll.domain.doll.DollOrderRecord;
import com.imlianai.zjdoll.domain.shipping.DollShippingNotice;

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
	 * @param remark 
	 * @param payStatus-支付状态
	 * amt-待支付金额
	 * @return
	 */
	int saveDollOrderRecord(long orderNum, Long uid, Long addressId, String remark,int payStatus,int amt);

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
	 * @return
	 */
	int saveShippingRecord(Long uid, long orderNum);
	
	/**
	 * 获取审核通过的订单
	 * @param intervalMinutes
	 * @return
	 */
	public List<DollOrderRecord> getDollOrderRecords(int intervalMinutes);
	
	/**
	 * 按订单号获取需要发货的娃娃
	 * @param orderNum
	 * @return
	 */
	public List<DollOrderInfo> getDollOrderInfos(long orderNum);
	
	/**
	 * 按订单号获取需要发货的娃娃--不论状态
	 * @param orderNum
	 * @return
	 */
	public List<DollOrderInfo> getDollOrderInfosAll(long orderNum);
	
	
	/**
	 * 更新发货申请状态
	 * @param orderNum
	 * @param status
	 * @return
	 */
	public int updateDollOrderRecordStatus(long orderNum,int status); 
	
	/**
	 * 创建物流订单
	 * @param orderNum
	 * @param orderId
	 * @return
	 */
	public int initDollOrderExpress(long orderNum,long uid,long orderId);

	/**
	 * 更新订单失败原因
	 * @param orderNum
	 * @param reason
	 * @return
	 */
	public int updateDollOrderRecordReason(long orderNum,String reason);
	
	/**
	 * 记录发货请求流水
	 * @param orderNum
	 * @param uid
	 * @param req
	 * @param resp
	 * @return
	 */
	public int addDollOrderRecordTaskLog(long orderNum,long uid,String req,String resp);
	
	/**
	 * 根据id获取物流编号
	 * @param orderId
	 * @return
	 */
	public DollOrderExpress getDollOrderExpress(long orderId);
	
	/**
	 * 更新娃娃物流
	 * @param orderNum
	 * @param expressNum
	 * @param expressCom
	 * @param expressName
	 * @return
	 */
	public int updateDollOrderExpress(long orderNum,String expressNum,String expressCom,String expressName,String expressRemark);
	
	/**
	 * 增加物流回调记录
	 * @param content
	 */
	public void addShippingCallbackLog(String content);

	/**
	 * 获取娃娃订单信息
	 * @param uDollId
	 * @return
	 */
	public DollOrderInfo getDollOrderInfoByUDollId(long uDollId);
	
	/**
	 * 根据用户id获取订单
	 * @param uid
	 * @return
	 */
	public List<DollOrderRecord> getDollOrderRecordsByUid(long uid,int page,int pageSize) ;
	
	/**
	 * 根据订单号获取订单
	 * @param orderNum
	 * @return
	 */
	public DollOrderRecord getDollOrderRecord(long orderNum) ;
	
	/**
	 * 更新订单支付状态
	 * @param orderNum
	 * @param payStatus
	 * @return
	 */
	public int updateOrderRecordPayStatus(long orderNum,int payStatus);
	
	/**
	 * 获取失效订单
	 * @param minutes
	 * @return
	 */
	public DollOrderRecord getInvalidRecord(int minutes);
}
