package com.imlianai.dollpub.app.modules.support.xxrecharge.dao;

import java.util.List;

import com.imlianai.dollpub.domain.xxtrade.XinxingTradeCharge;

public interface XxingRechargeDao {

	/**
	 * 订单生成
	 * @param tradeCharge
	 * @return
	 */
	int saveXinxingTradeCharge(XinxingTradeCharge tradeCharge);

	/**
	 * 保存订单充值记录
	 * @param orderNo
	 * @param uid
	 * @param reqParams
	 * @param respParams
	 * @param createDate
	 * @return
	 */
	int saveXinxingTradeChargeLog(String orderNo, Long uid, String reqParams, String respParams, int createDate);

	/**
	 * 更新订单提交状态
	 * @param orderNo
	 * @param status
	 * @return
	 */
	int updateTradeChargeSubmitStatus(String orderNo, int status);

	/**
	 * 保存回调信息
	 * @param orderNo
	 * @param callBackValue
	 * @return
	 */
	int saveCallBackInfo(String orderNo, String callBackValue);

	/**
	 * 修改订单最终状态
	 * @param orderNo
	 * @param status
	 * @param failReason
	 * @return
	 */
	int updateTradeChargeStatus(String orderNo, int status, String failReason);

	/**
	 * 前5分钟充值状态为充值中的订单
	 * @return
	 */
	List<XinxingTradeCharge> queryRechargingOrderNo();

}
