package com.imlianai.dollpub.app.modules.support.report.dao;

import java.util.List;

public interface ReportDAO {

	/**
	 * 更新关键字
	 * @param word
	 * @param type (1:新增 2:删除)
	 * @return
	 */
	public int addWord(String word, int type);
	/**
	 * 获取关键字列表
	 * @param uid
	 * @param imei
	 * @param type (0:全部 1:新增 2:删除)
	 * @return
	 */
	public List<String> getWords(long uid, String imei, int type);
	
}
