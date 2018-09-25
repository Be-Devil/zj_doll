package com.imlianai.zjdoll.app.modules.support.redpacket.enm;

public enum GetFriendsRedpackMsg {
	
	FIRST_MSG(1, "成功领取了X元好友红包，邀请好友越多，可领红包越多哟~"),
	SECOND_MSG(2, "成功领取了X元好友红包，好友红包每天都可免费领取哟~");
	
	public int num;
	
	public String msg;

	private GetFriendsRedpackMsg(int num, String msg) {
		this.num = num;
		this.msg = msg;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
