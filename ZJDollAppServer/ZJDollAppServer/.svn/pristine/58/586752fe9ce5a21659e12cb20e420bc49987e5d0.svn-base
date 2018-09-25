package com.imlianai.zjdoll.app.modules.support.dailytask.enm;

public enum BoxType {
	
	FIRST_BOX(1, 15, "15点活跃度开启"),
	SECOND_BOX(2, 35, "35点活跃度开启"),
	THIRTD_BOX(3, 55, "55点活跃度开启"),
	FOURTH_BOX(4, 85, "85点活跃度开启"),
	FIFTH_BOX(5, 120, "120点活跃度开启");
	
	public int boxId;
	
	public int activeness;
	
	public String activenessDesc;
	
	BoxType(int boxId, int activeness, String activenessDesc) {
		this.boxId = boxId;
		this.activeness = activeness;
		this.activenessDesc = activenessDesc;
	}

	public int getBoxId() {
		return boxId;
	}

	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}

	public int getActiveness() {
		return activeness;
	}

	public void setActiveness(int activeness) {
		this.activeness = activeness;
	}

	public String getActivenessDesc() {
		return activenessDesc;
	}

	public void setActivenessDesc(String activenessDesc) {
		this.activenessDesc = activenessDesc;
	}
	
	public static BoxType getBoxTypeByActiveness(int activeness) {
		for(BoxType boxType : BoxType.values()) {
			if(boxType.getActiveness() == activeness)
				return boxType;
		}
		return null;
	}
}
