package com.imlianai.dollpub.app.modules.core.user.phone;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.imlianai.dollpub.domain.user.UserEmail;
import com.imlianai.dollpub.domain.user.UserPhone;

/**
 * 用户手机号码相关
 * @author Max
 *
 */
public interface UserPhoneService {

	/**
	 * 初始化手机注册信息
	 * @param phone
	 * @return
	 */
	public int initPhone(UserPhone phone);
	/**
	 * 更新手机校验码
	 * @param number
	 * @param checkCode
	 * @return
	 */
	public int updateCheckCode(long number, int checkCode);
	/**
	 * 更新手机邀请码
	 * @param number
	 * @param inviteCode
	 * @return
	 */
	public int updateInviteCode(long number, String inviteCode);
	/**
	 * 更新手机验证状态
	 * @param number
	 * @param value
	 * @return
	 */
	public int updateState(long number, int checkCode);
	/**
	 * 获取手机号码注册信息
	 * @param number
	 * @return
	 */
	public UserPhone getPhoneByNum(long number);
	
	/**
	 * 初始化邮箱注册信息
	 * @param email
	 * @param pwd
	 * @return
	 */
	public int initEmail(String email, String pwd);
	/**
	 * 更新邮箱注册状态
	 * @param email
	 * @param uid
	 * @return
	 */
	public int updateEmailUid(String email, long uid);
	/**
	 * 获取已注册邮箱账号密码
	 * @param email
	 * @return
	 */
	public UserEmail getEmail(String email);
	
	/**
	 * 是否可以发送注册短信
	 * @param request
	 * @param headMap
	 * @return
	 */
	public boolean canSMS(HttpServletRequest request, Map<String,Object> headMap);
	/**
	 * 发送短信
	 * @param type (1:注册 2:重置密码 3:绑定手机 4:账号验证)
	 * @param number
	 * @param checkCode
	 */
	public boolean sendSMS(int type, long number, int checkCode,int customerId);
	/**
	 * 发送语音
	 * @param number
	 * @param checkCode
	 * @return
	 */
	public boolean sendVoice(long number, int checkCode);
}
