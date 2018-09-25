package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.enm;

public enum BigPrizeEnm {

	JDCARD(1, "http://lianai-image-sys.qiniudn.com/twistedEgg/award_jdcard.png", "100元京东卡"),
	LIPSTICK(2, "http://lianai-image-sys.qiniudn.com/twistedEgg/award_kh.png", "MAC口红"),
	JUICER(3, "http://lianai-image-sys.qiniudn.com/twistedEgg/award_zzj.png", "迷你榨汁机");
	
	public int type;
	
	public String icon;
	
	public String desc;

	private BigPrizeEnm(int type, String icon, String desc) {
		this.type = type;
		this.icon = icon;
		this.desc = desc;
	}
	
	public static BigPrizeEnm getBigPrizeEnmByType(int type) {
		for(BigPrizeEnm bigPrizeEnm : BigPrizeEnm.values()) {
			if(bigPrizeEnm.getType() == type) {
				return bigPrizeEnm;
			}
		}
		return null;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
