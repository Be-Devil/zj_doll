package com.imlianai.zjdoll.app.modules.core.doll.robot.domain;

import com.imlianai.rpc.support.common.BaseModel;

public class BusRobotStat extends BaseModel {

	private int busId;

	private int realyNum;

	private int fakeNum;

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public int getRealyNum() {
		return realyNum;
	}

	public void setRealyNum(int realyNum) {
		this.realyNum = realyNum;
	}

	public int getFakeNum() {
		return fakeNum;
	}

	public void setFakeNum(int fakeNum) {
		this.fakeNum = fakeNum;
	}

}
