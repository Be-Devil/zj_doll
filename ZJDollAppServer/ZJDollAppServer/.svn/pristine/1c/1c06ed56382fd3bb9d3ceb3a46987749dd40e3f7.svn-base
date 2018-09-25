package com.imlianai.zjdoll.app.modules.publics.cms.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class CmsDao {

	@Resource
	JdbcHandler jdbcHandler;
	
	public String getTextConf(String code){
		return jdbcHandler.queryOneBySql(String.class, "select content from text_cnf where code=? limit 1",code);
	}
}
