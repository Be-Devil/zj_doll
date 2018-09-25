package com.imlianai.dollpub.app.modules.publics.simple;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.constants.CacheConst;
import com.imlianai.dollpub.domain.simple.SimpleUser;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWipe;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Service
public class SimpleUserService {

	@Resource
	private JdbcHandler jdbcHandler;
	
	@CacheWrite(key=CacheConst.SIMPLE_USER_INFO)
	public SimpleUser loadSimpleUser(String userName){
		String sql="select * from simple_user where user=?";
		return jdbcHandler.queryOneBySql(SimpleUser.class, sql,userName);
	}
	
	@CacheWipe(key=CacheConst.SIMPLE_USER_INFO)
	public void saveSimpleUser(SimpleUser user){
		jdbcHandler.saveOrUpdateAuto(user);
	}
}
