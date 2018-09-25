package com.imlianai.zjdoll.app.modules.publics.qiniu.dao;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.qiniu.QiniuCnf;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class QiNiuDaoImpl implements QiNiuDao {

	@Resource
	private JdbcHandler jdbcHandler;

	private static String loadqnCnfSql = "select * from qiniu_cnf";

	@Override
	public Map<Integer, QiniuCnf> loadqnCnf() throws SQLException {
		Map<Integer, QiniuCnf> results = null;
		List<QiniuCnf> qinius = jdbcHandler.queryBySql(QiniuCnf.class,
				loadqnCnfSql);
		if (qinius != null && qinius.size() > 0) {
			results = new HashMap<Integer, QiniuCnf>();
			for (QiniuCnf qn : qinius) {
				results.put(qn.getType(), qn);
			}
			return results;
		} else {
			return null;
		}
	}

	@Override
	public int addTokenRecord(long uid, String token) {
		// TODO Auto-generated method stub
		return 0;
	}

}
