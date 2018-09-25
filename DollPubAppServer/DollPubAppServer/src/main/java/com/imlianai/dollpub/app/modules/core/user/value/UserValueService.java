package com.imlianai.dollpub.app.modules.core.user.value;

import java.util.List;
import java.util.Map;

import com.imlianai.dollpub.domain.user.UserValue;

/**
 * 用户数值相关
 * @author Max
 *
 */
public interface UserValueService {
	
	/**
	 * 初始化用户数值
	 * @param uid
	 * @return
	 */
	public int initUserValue(long uid);
	
	/**
	 * 增加娃娃数
	 * @param uid
	 * @param dollNum
	 * @return
	 */
	public int addDollNum(long uid, int dollNum);
	/**
	 * 更新邀请数
	 * @param uid
	 * @param inviteNum
	 * @return
	 */
	public int updateInviteNum(long uid, int inviteNum);
	/**
	 * 获取用户数值
	 * @param uid
	 * @return
	 */
	public UserValue getUserValue(long uid);
	/**
	 * 批量获取用户数值
	 * @param uids
	 * @return
	 */
	public Map<Long, UserValue> getUserValues(List<Long> uids);
	
	/**
	 * 增加邀请消息数
	 * @param uid
	 * @param dollNum
	 * @return
	 */
	public int addInviteMsgNum(long uid, int inviteNum);
	
	/**
	 * 重置邀请消息数
	 * @param uid
	 */
	public void resetInviteMsgNum(long uid);
}
