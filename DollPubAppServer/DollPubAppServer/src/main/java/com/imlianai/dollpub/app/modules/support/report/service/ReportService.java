package com.imlianai.dollpub.app.modules.support.report.service;

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

}
