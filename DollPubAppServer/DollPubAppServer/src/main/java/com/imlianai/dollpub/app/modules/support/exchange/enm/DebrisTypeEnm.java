package com.imlianai.dollpub.app.modules.support.exchange.enm;

public enum DebrisTypeEnm {

	COMMDEBRIS(0, "普通娃娃碎片"),
	RAREDEBRIS(1, "稀有娃娃碎片");
	
	public int type;
	
	public String desc;

	private DebrisTypeEnm(int type, String desc) {
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
		for(DebrisTypeEnm typeEnm : DebrisTypeEnm.values()) {
			if(typeEnm.getType() == type) {
				return typeEnm.getDesc();
			}
		}
		return "";
	}
}
