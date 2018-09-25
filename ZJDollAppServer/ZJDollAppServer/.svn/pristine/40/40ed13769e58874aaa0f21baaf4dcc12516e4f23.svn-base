package com.imlianai.zjdoll.app.modules.core.user.attribute;

import java.util.List;

import com.imlianai.zjdoll.domain.user.UserAttribute;

public interface UserAttributeService {

	/**
	 * 获取用户属性
	 * @param uid
	 * @return
	 */
	public UserAttribute getUserAttribute(Long uid);
	
	/**
	 * 更新用户封禁
	 * @param uids
	 * @param reason
	 * @param time
	 */
	public void updateUserBan(List<Long> uids,String reason,String time);
	
	/**
	 * 解除封号
	 * @param uids
	 */
	public void removeUserBan(List<Long> uids);
	
	/**
	 * 增加用户充值金额
	 * @param uid
	 * @param inc
	 */
	public void incTotalCharge(long uid,int inc);
	
	/**
	 * 限制提现(0:可提现 1:不可提现)
	 */
	public void updateWithdraw(long uid,int redpacket);
}
