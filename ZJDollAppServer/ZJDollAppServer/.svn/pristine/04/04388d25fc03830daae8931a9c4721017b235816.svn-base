package com.imlianai.zjdoll.app.modules.core.doll.pattern.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import com.imlianai.rpc.support.common.BaseModel;

@ApiModel("用户捉取策略")
public class UserStageRecord extends BaseModel{

	@ApiModelProperty("uid")
	private long uid;
	@ApiModelProperty("累计捉取次数")
	private int total;
	@ApiModelProperty("循环轮次")
	private int round;
	@ApiModelProperty("轮次阶段")
	private int stage;
	@ApiModelProperty("本轮次数")
	private int num;
	@ApiModelProperty("最后操作id")
	private long lastOptId;
	
	private Date time;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public long getLastOptId() {
		return lastOptId;
	}

	public void setLastOptId(long lastOptId) {
		this.lastOptId = lastOptId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public static enum UserRoundStage {

		BeWeak("补充成功后弱爪概率", 0),
		BeRandom("补充弱爪后按机器概率", 1),
		BeStrong("累计机器失败过多强爪带补", 2),
		;
		public int type;
		public String desc;

		private UserRoundStage(String desc, int type) {
			this.type = type;
			this.desc=desc;
		}
	}
	
}
