package com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("奇异果机器对象")
public class QiyiguoMachine {

	@ApiModelProperty("设备id")
	private String device_id;
	@ApiModelProperty("缩略图")
	private String img;
	@ApiModelProperty("机器名称")
	private String name;
	@ApiModelProperty("使用状态")
	private int status;
	@ApiModelProperty("商品id")
	private String goods_id;

	private String stream_address_1;
	
	private String stream_address_2;
	
	private String stream_address_raw_1;
	
	private String stream_address_raw_2;

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStream_address_1() {
		return stream_address_1;
	}

	public void setStream_address_1(String stream_address_1) {
		this.stream_address_1 = stream_address_1;
	}

	public String getStream_address_2() {
		return stream_address_2;
	}

	public void setStream_address_2(String stream_address_2) {
		this.stream_address_2 = stream_address_2;
	}

	public String getStream_address_raw_1() {
		return stream_address_raw_1;
	}

	public void setStream_address_raw_1(String stream_address_raw_1) {
		this.stream_address_raw_1 = stream_address_raw_1;
	}

	public String getStream_address_raw_2() {
		return stream_address_raw_2;
	}

	public void setStream_address_raw_2(String stream_address_raw_2) {
		this.stream_address_raw_2 = stream_address_raw_2;
	}
	
	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public enum MachineMode {

		ALL_WEAK(1,"固定弱抓力"),
		
		ALL_STRONG(2,"固定强抓力"),

		RAND_STRONG(3,"随机强抓力"),

		STRONG_ACCOMPANY(4,"固定强爪带补"),
		
		RAND_STRONG_ACCOMPANY(5,"随机强抓带补"),
		
		;

		public int type;

		public String des;
		
		private MachineMode(int type,String des) {
			this.type = type;
			this.des=des;
		}
		
		public static MachineMode getMachineMode(int type){
			MachineMode[] values = MachineMode.values();
			for (MachineMode t : values) {
				if (t.type == type) {
					return t;
				}
			}
			return null;
		}

	}
}
