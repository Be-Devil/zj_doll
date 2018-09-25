package com.imlianai.zjdoll.app.modules.core.trade.catalog.enm;

public enum RemindRewardMsg {

	FIRST_MSG(1, "嘤嘤嘤，X奖励的Y币再不领就要过期喽~"),
	SECOND_MSG(2, "X奖励的Y币白送都不来领-_-你的爱豆知道了会很伤心的"),
	THIRD_MSG(3, "班长说要送你Y币，快来萌趣领取吧~"),
	FOURTH_MSG(4, "X奖励的Y币已就位，等你来抓娃娃~");
	
	public int num;
	
	private String desc;
	
	private RemindRewardMsg(int num, String desc) {
		this.num = num;
		this.desc = desc;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static String getDesc(int num) {
		for(RemindRewardMsg remindRewardMsg : RemindRewardMsg.values()) {
			if(remindRewardMsg.getNum() == num) {
				return remindRewardMsg.getDesc();
			}
		}
		return "";
	}
}
