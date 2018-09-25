package com.imlianai.zjdoll.app.modules.core.user.service;

import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserLoginCheckReqVO;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserLoginReqVO;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserLoginSyncData;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserRegisterReqVO;

public interface UserLoginService {

	/**
	 * 处理用户登录
	 * 
	 * @param reqVO
	 * @return
	 */
	public BaseRespVO handleUserLogin(UserLoginReqVO reqVO);

	/**
	 * 处理用户登录校验
	 * 
	 * @param reqVO
	 * @return
	 */
	public BaseRespVO handleUserLoginCheck(UserLoginCheckReqVO reqVO);

	/**
	 * 处理用户注册
	 * 
	 * @param reqVO
	 * @return
	 */
	public BaseRespVO handleUserRegister(UserRegisterReqVO reqVO);

	/**
	 * 用户登录后续处理流程-主要针对同步性登录后续操作
	 * 
	 * @param user
	 * @param reqVo
	 * @param isRegister
	 */
	public UserLoginSyncData commonLoginProcessSync(UserGeneral user,
			UserBase userBase, BaseReqVO reqVo, boolean isRegister);

	/**
	 * 用户登录后续处理流程-主要针对非同步性登录后续操作
	 * 
	 * @param user
	 * @param reqVo
	 * @param isRegister
	 */
	public void commonLoginProcessAsyn(UserGeneral user, UserBase userBase,
			BaseReqVO reqVo, boolean isRegister);

}
