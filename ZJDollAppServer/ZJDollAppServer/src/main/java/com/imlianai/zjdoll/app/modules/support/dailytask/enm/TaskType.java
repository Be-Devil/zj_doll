package com.imlianai.zjdoll.app.modules.support.dailytask.enm;

public enum TaskType {

	SIGNIN(1, "每日签到"),
	WECHATGROUP(2, "分享邀请码到微信群"),
	MOMENTS(3, "分享邀请码到朋友圈"),
	RECHARGE_RANDOM_AMT(4, "每日首充任意金额"),
	OPEN_FRIEND_REDPACK(5, "用积分开启好友红包1次"),
	CATCH_DOLL_1(6, "抓娃娃1次"),
	CATCH_DOLL_6(7, "抓娃娃6次"),
	CATCH_DOLL_30(8, "抓娃娃30次"),
	RECHARGE_AMT_10(9, "充值满10元"),
	RECHARGE_AMT_30(10, "充值满30元"),
	RECHARGE_AMT_50(11, "充值满50元"),
	RECHARGE_AMT_100(12, "充值满100元"),
	CATCH_DOLL_SUCC_1(13, "抓中1个娃娃"),
	CATCH_DOLL_SUCC_3(14, "抓中3个娃娃"),
	CATCH_DOLL_SUCC_10(15, "抓中10个娃娃");
	
	public int taskId;
	
	public String desc;
	
	private TaskType(int taskId, String desc) {
		this.taskId = taskId;
		this.desc = desc;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
