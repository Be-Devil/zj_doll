package com.imlianai.zjdoll.app.modules.support.repair.dao;

public interface RepairDao {

	/**
	 * 报修
	 * 
	 * @param busId
	 * @param uid
	 * @param reason
	 * @return
	 */
	public int addReocrd(int busId, long uid, String reason);
}
