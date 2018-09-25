package com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.enm;

public enum AwardEnm {

	COIN_25(1, 0, 25, "25币"),
	COIN_29(2, 0, 29, "29币"),
	COIN_33(3, 0, 33, "33币"),
	COIN_49(4, 0, 49, "49币"),
	LUCKYBAG(5, 2, 1, "福袋"),
	BIGPRIZE(6, 1, 1, "大奖品");
	
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
