package com.imlianai.dollpub.app.modules.core.user.value;

import java.util.List;
import java.util.Map;

import com.imlianai.dollpub.domain.user.UserValue;

/**
 * 用户数值相关
 * @author Max
 *
 */
public interface UserValueDAO {
	
	/**
	 * 初始化用户数值
	 * @param value
	 * @return
	 */
	public int initUserValue(UserValue value);
	/**
	 * 更新邀请书
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
	 * 获取用户数值
	 * @param uid
	 * @return
	 */
	public UserValue getUserValueNoCache(long uid);


	/**
	 * 批量获取用户数值
	 * @param uids
	 * @return
	 */
	public Map<Long, UserValue> getUserValues(List<Long> uids);
	
	/**
	 * 增加娃娃数
	 * @param uid
	 * @param dollNum
	 * @return
	 */
	public int addDollNum(long uid, int dollNum);
	
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
	
	/**
	 * 更新用户捉到的娃娃数量
	 * @param uid
	 * @param dollNum
	 * @return
	 */
	public int updateDollNum(long uid, int dollNum);
	
	/**
	 * 从数据库获取用户数值
	 * @param uid
	 * @return
	 */
	public UserValue getUserValueInDb(Long uid);
}
