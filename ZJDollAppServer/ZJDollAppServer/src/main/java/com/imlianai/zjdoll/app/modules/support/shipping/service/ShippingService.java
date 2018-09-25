package com.imlianai.zjdoll.app.modules.support.shipping.service;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.BaseDollInfo;
import com.imlianai.zjdoll.domain.doll.DollOrderExpress;
import com.imlianai.zjdoll.domain.doll.DollOrderInfo;
import com.imlianai.zjdoll.domain.doll.DollOrderRecord;
import com.imlianai.zjdoll.domain.doll.DollOrderRecordBill;
import com.imlianai.zjdoll.domain.trade.TradeCharge;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.shipping.vo.ApplyDollInfo;

public interface ShippingService {

	/**
	 * 获取用户可发货的娃娃列表
	 * @param uid
	 * @param uDollId 
	 * @return
	 */
	public List<BaseDollInfo> getShippingList(Long uid, Long uDollId);

	/**
	 * 申请发货
	 * @param dollList
	 * @param uid
	 * @param addressId 
	 * @param remark 
	 * @return
	 */
	public BaseRespVO applyShipping(List<ApplyDollInfo> dollList, Long uid, Long addressId, String remark);
	
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
	 * 获取人民币油费
	 * @param dollList
	 * @param uid
	 * @return
	 */
	public int getPostageRmb(List<ApplyDollInfo> dollList, Long uid);

	/**
	 * 申请发货 并生成预订单
	 * @param dollList
	 * @param uid
	 * @param addressId
	 * @param remark
	 * @return
	 */
	public BaseRespVO applyShippingBill(List<ApplyDollInfo> dollList, Long uid,
			Long addressId, String remark);
	
	/**
	 * 根据订单号获取订单
	 * @param orderNum
	 * @return
	 */
	public DollOrderRecord getDollOrderRecord(long orderNum) ;
	
	/**
	 * 不论状态获取全部订单
	 * @param orderNum
	 * @return
	 */
	public List<DollOrderInfo> getDollOrderInfosAll(long orderNum);
	
	/**
	 * 取消订单
	 * @param uid
	 * @param orderNum
	 * @return
	 */
	public BaseRespVO cancelShippingBill(long uid,long orderNum) ;
	
	/**
	 * 根据用户id获取订单
	 * @param uid
	 * @return
	 */
	public List<DollOrderRecord> getDollOrderRecordsByUid(long uid,int page,int pageSize) ;

	/**
	 * 获取历史订单列表
	 * @param uid
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<DollOrderRecordBill> getDollOrderRecordBill(long uid, int page,
			int pageSize);
	
	/**
	 * 更新订单支付状态
	 * @param orderNum
	 * @param payStatus
	 * @return
	 */
	public int updateOrderRecordPayStatus(long orderNum,int payStatus);
	
	/**
	 * 处理支付发放
	 * @param charge
	 */
	public boolean handleShippingPayed(TradeCharge charge);

	/**
	 * 处理过期
	 */
	public void handleShippingInvalid();
}
