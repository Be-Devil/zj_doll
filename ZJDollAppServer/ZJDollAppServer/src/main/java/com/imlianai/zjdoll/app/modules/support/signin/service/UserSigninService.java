package com.imlianai.zjdoll.app.modules.support.signin.service;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.signin.vo.GetBoxContentRespVO;

public interface UserSigninService {

	/**
	 * 获取签到弹框内容
	 * @param reqVO 
	 * @return
	 */
	GetBoxContentRespVO getBoxContent(BaseReqVO reqVO);

	/**
	 * 签到
	 * @param uid
	 * @param version 
	 * @return
	 */
	BaseRespVO signin(Long uid, int version);
	
	/**
	 * 今日用户是否已签到(true:已签到)
	 * @param uid
	 * @return
	 */
	boolean isSignin(Long uid);

}
