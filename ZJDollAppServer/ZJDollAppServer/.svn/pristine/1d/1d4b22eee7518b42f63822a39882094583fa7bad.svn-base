package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.enm;

public enum AwardEnm {

	COIN_11(1, 0, 11, "11币"),
	COIN_15(2, 0, 15, "15币"),
	COIN_19(3, 0, 19, "19币"),
	COIN_25(4, 0, 25, "25币"),
	COIN_33(5, 0, 33, "33币"),
	LUCKYBAG(6, 2, 1, "福袋");
	
	public int id;
	
	public int type;
	
	public int num;
	
	public String desc;

	private AwardEnm(int id, int type, int num, String desc) {
		this.id = id;
		this.type = type;
		this.num = num;
		this.desc = desc;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
	
	public static AwardEnm getAwardEnmById(int id) {
		for(AwardEnm awardEnm : AwardEnm.values()) {
			if(awardEnm.getId() == id) {
				return awardEnm;
			}
		}
		return null;
	}
}
