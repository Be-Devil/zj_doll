package com.imlianai.zjdoll.app.modules.core.doll.enm;

public enum VirtualGoodType {

	JEWEL(0, "钻石"),
	COIN(1, "游戏币"),
	FLOW(2, "流量"),
	PHONE_BILL(3, "话费"),
	CARD(4, "京东卡/VIP卡"),
	MANUAL_HANDING(5, "人工处理");
	
	private VirtualGoodType(int specType, String desc) {
		this.specType = specType;
		this.desc = desc;
	}

	public int specType;
	
	public String desc;

	public int getSpecType() {
		return specType;
	}

	public void setSpecType(int specType) {
		this.specType = specType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static VirtualGoodType getVirGoodType(int specType) {
		for(VirtualGoodType virGoodType : VirtualGoodType.values()) {
			if(virGoodType.getSpecType() == specType) {
				return virGoodType;
			}
		}
		return null;
	}
}
