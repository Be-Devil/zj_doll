package com.imlianai.zjdoll.app.modules.support.report.service;

import java.util.List;

/**
 * 举报相关
 * 
 * @author Max
 * 
 */
public interface ReportService {

	/**
	 * 获取关键字列表
	 * 
	 * @param uid
	 * @param imei
	 * @param type
	 *            (1:新增 2:删除)
	 * @return
	 */
	public List<String> getWords(long uid, String imei, int type);

	/**
	 * 增加封禁设备
	 * @param imei
	 * @param uid
	 * @return
	 */
	public int addForbidImei(String imei, long uid);
	
	/**
	 * 是否是封禁的设备
	 * @param imei
	 * @return
	 */
	public boolean isForbidImei(String imei);
	
	/**
	 * 清除设备封禁
	 * @param imei
	 */
	public void removeForbidImei(String imei);

}
