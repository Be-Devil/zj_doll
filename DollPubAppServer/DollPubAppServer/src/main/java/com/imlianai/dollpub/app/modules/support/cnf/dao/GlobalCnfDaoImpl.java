package com.imlianai.dollpub.app.modules.support.cnf.dao;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.cnf.GlobalCnf;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
@Repository
public class GlobalCnfDaoImpl implements GlobalCnfDao {

	@Resource
	private JdbcHandler jdbcHandler;

	private static String getAllSql = "select * from global_cnf where status>0";

	@Override
	public List<GlobalCnf> getAll() {
		return jdbcHandler.queryBySql(GlobalCnf.class, getAllSql);
	}

}
