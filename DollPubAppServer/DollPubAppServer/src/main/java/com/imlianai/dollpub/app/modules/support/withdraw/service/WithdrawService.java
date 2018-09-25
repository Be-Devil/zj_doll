package com.imlianai.dollpub.app.modules.support.withdraw.service;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public interface WithdrawService {

	void init();

	void doPayWechatMoneySchedule();

	void doPayWechatMoneyResultCheckSchedule();

	/**
	 * 是否以实名
	 * @param uid
	 * @return
	 */
	int hasCertificationInfo(long uid);
	
	/**
	 * 保存实名信息
	 * @param uid
	 * @param name
	 * @param number
	 * @return
	 */
	public int addCertificationInfo(long uid,String name,String number);
}
