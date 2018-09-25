package com.imlianai.zjdoll.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel("申请上机返回对象")
public class DollApplyRespVO extends BaseRespVO {

	@ApiModelProperty("用户id")
	private Long uid;

	@ApiModelProperty("娃娃机id")
	private Integer busId;

	@ApiModelProperty("上机操作id")
	private long optId;
	@ApiModelProperty("上机操作密钥")
	private long logKey;
	@ApiModelProperty("剩余操作时长")
	private int remainSecond;

	@ApiModelProperty("正面流地址-操作者视野")
	private String stream1Realy;
	@ApiModelProperty("侧面流地址-操作者视野")
	private String stream2Realy;

	@ApiModelProperty("上机成功奖励")
	private String reward;
	
	public void setBusInfo(DollBus dollBus) {
		if (dollBus != null) {
			this.busId = dollBus.getBusId();
			this.stream1Realy = dollBus.getStream1Realy();
			this.stream2Realy = dollBus.getStream2Realy();
		}
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getBusId() {
		return busId;
	}

	public void setBusId(Integer busId) {
		this.busId = busId;
	}

	public long getOptId() {
		return optId;
	}

	public void setOptId(long optId) {
		this.optId = optId;
	}

	public int getRemainSecond() {
		return remainSecond;
	}

	public void setRemainSecond(int remainSecond) {
		this.remainSecond = remainSecond;
	}

	public String getStream1Realy() {
		return stream1Realy;
	}

	public void setStream1Realy(String stream1Realy) {
		this.stream1Realy = stream1Realy;
	}

	public String getStream2Realy() {
		return stream2Realy;
	}

	public void setStream2Realy(String stream2Realy) {
		this.stream2Realy = stream2Realy;
	}

	public long getLogKey() {
		return logKey;
	}

	public void setLogKey(long logKey) {
		this.logKey = logKey;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}
}
