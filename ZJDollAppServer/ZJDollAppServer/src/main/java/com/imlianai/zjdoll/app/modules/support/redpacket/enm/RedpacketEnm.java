package com.imlianai.zjdoll.app.modules.support.redpacket.enm;

/**
 * 红包类型
 * @author cls
 *
 */
public enum RedpacketEnm {

	REWARD_REDPACKET(0, "注册奖励、活动上线奖励红包"),
	INVITE_REWARD_REDPACKET(1, "邀请奖励红包"),
	FRIEND_REDPACKET(2, "好友红包"),
	WITHDRAW(3, "红包提现"),
	BUS_REWARD(4, "房间红包"),
	SIGNIN(5, "签到红包"),
	DAILYTASK(6, "每日任务红包"),
	;
	
	public int type;
	
	public String desc;

	private RedpacketEnm(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
