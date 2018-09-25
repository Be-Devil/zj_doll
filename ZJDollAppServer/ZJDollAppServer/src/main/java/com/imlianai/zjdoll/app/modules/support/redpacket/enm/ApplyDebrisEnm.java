package com.imlianai.zjdoll.app.modules.support.redpacket.enm;

/**
 * 成功上机获得的娃娃碎片奖励
 * @author cls
 *
 */
public enum ApplyDebrisEnm {

	COMMDEBRIS_1(1, 0, 1, "+1块普通娃娃碎片"),
	RAREDEBRIS_1(2, 1, 1, "+1块稀有娃娃碎片"),
	COMMDEBRIS_2(3, 0, 2, "+2块普通娃娃碎片"),
	RAREDEBRIS_2(4, 1, 2, "+2块稀有娃娃碎片");
	
	public int type;
	
	public int debrisType;
	
	public int num;
	
	public String desc;

	private ApplyDebrisEnm(int type, int debrisType, int num, String desc) {
		this.type = type;
		this.debrisType = debrisType;
		this.num = num;
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDebrisType() {
		return debrisType;
	}

	public void setDebrisType(int debrisType) {
		this.debrisType = debrisType;
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
	
	public static String getDesc(int type) {
		for(ApplyDebrisEnm applyDebrisEnm : ApplyDebrisEnm.values()) {
			if(applyDebrisEnm.getType() == type) {
				return applyDebrisEnm.getDesc();
			}
		}
		return "";
	}
}
