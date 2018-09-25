package com.imlianai.zjdoll.app.modules.support.signin.enm;

public enum AwardTypeEnm {

	REDPACKET(1, "红包"),
	COIN(2, "币"),
	COMMDEBRIS(3, "普通娃娃碎片"),
	RAREDEBRIS(4, "稀有娃娃碎片");
	
	public int type;
	
	public String desc;

	private AwardTypeEnm(int type, String desc) {
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
	
	public static String getDesc(int type) {
		for(AwardTypeEnm awardTypeEnm : AwardTypeEnm.values()) {
			if(awardTypeEnm.getType() == type) {
				return awardTypeEnm.getDesc();
			}
		}
		return "";
	}
}
