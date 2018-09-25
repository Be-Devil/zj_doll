package com.imlianai.zjdoll.app.modules.publics.kws.dao;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.kws.KWType;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
@Repository
public class KeyWordDaoImpl implements KeyWordDao {

	@Resource
	private JdbcHandler jdbcHandler;

	private static String getAllSql = "select word from sys_keyword where `type`=?";

	@Override
	public List<String> getAll(KWType type) {
		return jdbcHandler.queryBySql(String.class, getAllSql, type.bit);
	}

}
