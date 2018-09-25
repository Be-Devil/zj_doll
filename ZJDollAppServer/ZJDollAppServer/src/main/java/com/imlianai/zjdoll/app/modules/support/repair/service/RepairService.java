package com.imlianai.zjdoll.app.modules.support.repair.service;

public interface RepairService {

	/**
	 * 报修
	 * @param busId
	 * @param uid
	 * @param type
	 * @return
	 */
	public int addReocrd(int busId,long uid, String reason);
}
