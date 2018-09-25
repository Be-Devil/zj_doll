package com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.enm;

public enum TwistedEggEnm {

	FIRST_TWISTEDEGG(1, 30, "累计充值满30元"),
	SECOND_TWISTEDEGG(2, 50, "累计充值满50元"),
	THIRD_TWISTEDEGG(3, 100, "累计充值满100元"),
	FOURTH_TWISTEDEGG(4, 150, "累计充值满150元"),
	FIFTH_TWISTEDEGG(5, 200, "累计充值满200元"),
	SIXTH_TWISTEDEGG(6, 300, "累计充值满300元");
	
	public int id;
	
	public int amt;
	
	public String desc;
	
	private TwistedEggEnm(int id, int amt, String desc) {
		this.id = id;
		this.amt = amt;
		this.desc = desc;
	}
	
	public static TwistedEggEnm getTwistedEggEnmByAmt(int amt) {
		for(TwistedEggEnm twistedEggEnm : TwistedEggEnm.values()) {
			if(amt == twistedEggEnm.getAmt()) {
				return twistedEggEnm;
			}
		}
		return null;
	}
	
	public static TwistedEggEnm getTwistedEggEnmById(int id) {
		for(TwistedEggEnm twistedEggEnm : TwistedEggEnm.values()) {
			if(id == twistedEggEnm.getId()) {
				return twistedEggEnm;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmt() {
		return amt;
	}

	public void setAmt(int amt) {
		this.amt = amt;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
