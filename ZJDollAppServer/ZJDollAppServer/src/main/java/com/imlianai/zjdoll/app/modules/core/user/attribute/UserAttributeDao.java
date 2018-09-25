package com.imlianai.zjdoll.app.modules.core.user.attribute;

import java.util.List;

import com.imlianai.zjdoll.domain.user.UserAttribute;

public interface UserAttributeDao {

	/**
	 * 获取用户属性
	 * @param uid
	 * @return
	 */
	UserAttribute getUserAttribute(Long uid);
	
	/**
	 * 更新用户封禁
	 * @param uids
	 * @param reason
	 * @param time
	 */
	public void updateUserBan(List<Long> uids,String reason,String time);
	
	/**
	 * 解除用户封禁
	 * @param uids
	 */
	public void removeUserBan(List<Long> uids);
	/**
	 * 初始化
	 * @param uid
	 */
	public void initUserAttribute(Long uid);
	
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
