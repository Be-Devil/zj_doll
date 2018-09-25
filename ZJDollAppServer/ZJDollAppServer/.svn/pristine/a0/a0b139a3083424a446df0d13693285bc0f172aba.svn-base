package com.imlianai.zjdoll.app.modules.support.idfa.dao;
import com.imlianai.zjdoll.domain.idfa.IdfaRecord;
public interface IdfaDao {

	/**
	 * 初始化idfa记录
	 * 
	 * @param record
	 * @return
	 */
	int init(IdfaRecord record);

	/**
	 * 激活idfa
	 * 
	 * @param idfa
	 * @param imei
	 * @return
	 */
	int active(String idfa, String imei);

	/**
	 * 注册
	 * 
	 * @param uid
	 * @param imei
	 * @return
	 */
	int register(long uid, String imei);
	/**
	 * 获取idfa记录
	 * 
	 * @param idfa
	 * @return
	 */
	IdfaRecord get(String idfa);

}
