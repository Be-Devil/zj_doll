package com.imlianai.zjdoll.app.modules.support.xxrecharge.service;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public interface XxingRechargeService {

	/**
	 * 心行充值处理
	 * @param uid
	 * @param type
	 * @param productCode
	 * @param phone
	 * @param uDollId
	 */
	BaseRespVO handleRecharge(Long uid, int type, int productCode, long phone, long uDollId);

	/**
	 * 保存回调信息
	 * @param orderNO
	 * @param callBackValue
	 * @return
	 */
	int saveCallBackInfo(String orderNO, String callBackValue);

	/**
	 * 修改订单状态
	 * @param orderNo
	 * @param status
	 * @param failReason
	 * @return
	 */
	int updateTradeChargeStatus(String orderNo, int status, String failReason);

	/**
	 * 心行充值订单查询
	 */
	void xinxingRechargeQuery();
}
