package com.imlianai.zjdoll.app.modules.publics.qiniu.dao;

import java.sql.SQLException;
import java.util.Map;

import com.imlianai.zjdoll.domain.qiniu.QiniuCnf;

public interface QiNiuDao {

	/**
	 * 加载配置
	 * 
	 * @return
	 */
	Map<Integer, QiniuCnf> loadqnCnf() throws SQLException;

	/**
	 * 加入token记录
	 * 
	 * @param uid
	 * @param token
	 * @return
	 */
	int addTokenRecord(long uid, String token);

}
