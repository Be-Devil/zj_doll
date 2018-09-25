package com.imlianai.dollpub.app.modules.support.withdraw.dao;

import java.util.List;

import com.imlianai.dollpub.domain.withdraw.WithdrawDetailRecord;
import com.imlianai.dollpub.domain.withdraw.WithdrawUserPhone;
import com.imlianai.dollpub.domain.withdraw.WithdrawWechatBoundMiddleware;
import com.imlianai.dollpub.domain.withdraw.WithdrawWechatCommunicationLog;
import com.imlianai.dollpub.domain.withdraw.WithdrawWechatInnerMiddleware;

public interface WithdrawDao {


	/**
	 * 是否在一分钟内有提现记录
	 * @param uid
	 * @return
	 */
	int hasWithdrawInAMinute(Long uid);

	/**
	 * 是否在一分钟内有提现记录
	 * @param openId
	 * @return
	 */
	int hasWithdrawInAMinute(String openId);


	/**
	 * 插入回调记录
	 * @param recordId 提现记录ID
	 * @param billId 微信订单ID
	 * @param uid
	 * @param openId
	 * @param amt
	 * @param request
	 * @return
	 */
	public int addWechatCallbackLog(long recordId, String billId, long uid, String openId, double amt, String request);


	/**
	 * 更新回调记录
	 * @param recordId 提现记录ID
	 * @param wechatPaymentId
	 * @param respone
	 * @param successflag
	 * @return
	 */
	public int updateWechatCallbackLog(long recordId,String wechatPaymentId ,String respone,int successflag);

	/**
	 * 更新微信支付订单信息
	 * @param id
	 * @param wechatPaymentId
	 * @param successFlag
	 * @return
	 */
	public int updateWithdrawRecordsWithWechatInfo(long id,String wechatPaymentId,int successFlag);

	/**
	 * 获取需要校验的订单
	 * @return
	 */
	List<WithdrawWechatCommunicationLog> getMobileWithdrawWechatCommunicationRecordList();

	/**
	 * 插入查询回调记录
	 * @param recordId
	 * @param billId
	 * @param uid
	 * @param openId
	 * @param amt
	 * @param request
	 * @param respone
	 * @param time
	 * @return
	 */
	int addWechatCallbackLog(Long recordId, String billId, Long uid, String openId, double amt, String request,
			String respone, String time);

	/**
	 * 保存实名信息
	 * @param uid
	 * @param name
	 * @param number
	 * @return
	 */
	public int addCertificationInfo(long uid,String name,String number);

	/**
	 * 是否以实名
	 * @param uid
	 * @return
	 */
	int hasCertificationInfo(long uid);
}
