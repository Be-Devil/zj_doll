package com.imlianai.zjdoll.app.modules.core.trade.catalog.domain;

import java.util.Date;

import com.imlianai.rpc.support.common.BaseModel;

public class ChargeCardExpirePushLog extends BaseModel{
	
	private static final long serialVersionUID = 1L;

	private Long ccardLogId;
	
	private Long uid;
	
	private Date pushTime;

	public Long getCcardLogId() {
		return ccardLogId;
	}

	public void setCcardLogId(Long ccardLogId) {
		this.ccardLogId = ccardLogId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
}
