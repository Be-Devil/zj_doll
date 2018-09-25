package com.imlianai.zjdoll.app.modules.core.user.phone.code;

import com.imlianai.zjdoll.domain.user.UserCode;


/**
 * 验证码相关
 * @author Max
 *
 */
public interface UserCodeService {
	
	/**
	 * 更新验证码
	 * @param number
	 * @param code
	 * @return
	 */
	public int updateCode(String number, String code);
	/**
	 * 更新验证码状态
	 * @param number
	 * @param code
	 * @return
	 */
	public int updateCodeState(String number, String code);
	/**
	 * 删除登录验证码
	 * @param number
	 * @return
	 */
	public int delCode(String number);
	/**
	 * 获取登录验证码
	 * @param number
	 * @return
	 */
	public UserCode getCode(String number);
}
