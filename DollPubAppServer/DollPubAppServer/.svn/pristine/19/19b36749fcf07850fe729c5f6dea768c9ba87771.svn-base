package com.imlianai.dollpub.app.modules.core.doll.domain;

import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.dollpub.domain.doll.DollOptRecord;
import com.imlianai.dollpub.domain.optrecord.OptRecord;

public class UserDollHistoryRecord extends DollOptRecord {

	private DollInfo dollInfo;

	public UserDollHistoryRecord() {
	}

	public UserDollHistoryRecord(DollOptRecord record, DollInfo dollInfo) {
		super(record.getUid(), record.getBusId(), record.getLogId(), record
				.getDollId(), record.getCost());
		this.setStartTime(record.getStartTime());
		if (dollInfo!=null) {
			this.dollInfo=dollInfo;
		}
	}
	public UserDollHistoryRecord(OptRecord record, DollInfo dollInfo) {
		super(record.getUid(), record.getBusId(), record.getOptId(), record
				.getDollId(), 0);
		this.setStartTime(record.getStartTime());
		if (dollInfo!=null) {
			this.dollInfo=dollInfo;
		}
	}
	

	public DollInfo getDollInfo() {
		return dollInfo;
	}

	public void setDollInfo(DollInfo dollInfo) {
		this.dollInfo = dollInfo;
	}

}
