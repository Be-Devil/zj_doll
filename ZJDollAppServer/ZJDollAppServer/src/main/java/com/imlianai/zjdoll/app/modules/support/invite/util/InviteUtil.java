package com.imlianai.zjdoll.app.modules.support.invite.util;

import com.imlianai.zjdoll.app.modules.support.event.invite20180320.util.Event20180320InviteUtil;

public class InviteUtil {

	/**
	 * 邀请登录的奖励
	 */
	private static int rewardReg=20;
	/**
	 * 新用户登录送币
	 */
	private static int rewardFirstReg=50;
	
	/**
	 * 邀请助攻的奖励
	 */
	private static int rewardSuccess=20;
	
	/**
	 * 邀请次数限制
	 */
	private static int inviteTimeLimit=30;
	
	public static int getRegReward(){
		return rewardReg;
	}
	
	public static int getSuccessReward(){
		return rewardSuccess;
	}
	
	public static int getInviteTimeLimit(){
		return inviteTimeLimit;
	}

	public static int getRewardFirstReg(){
		return rewardFirstReg;
	}
}
