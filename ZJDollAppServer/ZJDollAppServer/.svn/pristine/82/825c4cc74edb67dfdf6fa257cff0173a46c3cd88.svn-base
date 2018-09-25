package com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.domain;

public enum ZengjingClawType {

	 Default(0, "默认爪"), Weak(1, "弱爪"),Strong(2, "强爪");

	public int type;
	public String des;

	private ZengjingClawType(int type, String des) {
		this.type = type;
		this.des = des;
	}

	public static ZengjingClawType getType(int type) {
		ZengjingClawType[] values = ZengjingClawType.values();
		for (ZengjingClawType t : values) {
			if (t.type == type) {
				return t;
			}
		}
		return null;
	}
}
