package com.imlianai.dollpub.app.modules.support.idfa.service;

import com.imlianai.dollpub.domain.idfa.IdfaRecord;

public interface IdfaService {

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

}
