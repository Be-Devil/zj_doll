package com.imlianai.dollpub.app.modules.core.user.phone.code;

import com.imlianai.dollpub.domain.user.UserCode;

/**
 * 验证码相关
 * @author Max
 *
 */
public interface UserCodeDAO {
	
	/**
	 * 更新验证码
	 * @param type
	 * @param number
	 * @param code
	 * @return
	 */
	public int updateCode(int type, String number, String code);
	/**
	 * 更新验证码状态
	 * @param type
	 * @param number
	 * @param code
	 * @return
	 */
	public int updateCodeState(int type, String number, String code);
	/**
	 * 删除登录验证码
	 * @param type
	 * @param number
	 * @return
	 */
	public int delCode(int type, String number);
	/**
	 * 获取登录验证码
	 * @param number
	 * @return
	 */
	public UserCode getCode(int type, String number);
}
