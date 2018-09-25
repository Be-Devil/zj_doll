package com.imlianai.zjdoll.app.modules.support.userdoll.enm;

import java.util.ArrayList;
import java.util.List;

public enum FirsttimePushType {
	
	COMMDEBRIS_250(0,250,"普通碎片达到250次"),
	COMMDEBRIS_50(0,50,"普通碎片达到50次"),
	COMMDEBRIS_3(0,3,"普通碎片达到3次"),
	STOREDDOLL_3(1,4,"寄存娃娃超过3个以上");

	public int type;
	
	public int num;
	
	public String desc;
	
	private FirsttimePushType(int type, int num, String desc) {
		this.type = type;
		this.num = num;
		this.desc = desc;
	}
	
	public static List<FirsttimePushType> getFirsttimePushTypeArry(int type) {
		List<FirsttimePushType> list = new ArrayList<FirsttimePushType>();
		for(FirsttimePushType pushType : FirsttimePushType.values()) {
			if(pushType.getType() == type) {
				list.add(pushType);
			}
		}
		return list;
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
}

	
