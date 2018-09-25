package com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.BaseModel;

@ApiModel(value = "娃娃机数据传输对象")
public class ZengjingDollBusDTO extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("机器id")
	private int busId;

	@ApiModelProperty("娃娃id")
	private int dollId;

	@ApiModelProperty("设备名称")
	private String name;

	@ApiModelProperty("设备id")
	private String deviceId;

	@ApiModelProperty("封面")
	private String cover;

	@ApiModelProperty("设备状态 0-空闲，1-游戏中,2-故障状态")
	private int status;

	@ApiModelProperty("正面流地址")
	private String stream1;

	@ApiModelProperty("侧面流地址")
	private String stream2;

	@ApiModelProperty("正面流地址-操作者")
	private String stream1Realy;

	@ApiModelProperty("侧面流地址-操作者")
	private String stream2Realy;

	@ApiModelProperty(value = "强爪频率 1次/strong次", hidden = true)
	private int strong;

	@ApiModelProperty(value = "机器类型", hidden = true)
	private int type;

	@ApiModelProperty(value = "是否实体,0:实体，1:虚拟", hidden = true)
	private int virtual;

	public ZengjingDollBusDTO() {
	}

	public ZengjingDollBusDTO(int busId, int dollId, String name, String deviceId,
			String cover, int status, String stream1, String stream2,
			String stream1Realy, String stream2Realy, int strong) {
		super();
		this.busId = busId;
		this.dollId = dollId;
		this.name = name;
		this.deviceId = deviceId;
		this.cover = cover;
		this.status = status;
		this.stream1 = stream1;
		this.stream2 = stream2;
		this.stream1Realy = stream1Realy;
		this.stream2Realy = stream2Realy;
		this.strong = strong;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public int getDollId() {
		return dollId;
	}

	public void setDollId(int dollId) {
		this.dollId = dollId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStream1() {
		return stream1;
	}

	public void setStream1(String stream1) {
		this.stream1 = stream1;
	}

	public String getStream2() {
		return stream2;
	}

	public void setStream2(String stream2) {
		this.stream2 = stream2;
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

	public int getStrong() {
		return strong;
	}

	public void setStrong(int strong) {
		this.strong = strong;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getVirtual() {
		return virtual;
	}

	public void setVirtual(int virtual) {
		this.virtual = virtual;
	}
}
