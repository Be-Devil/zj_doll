package com.imlianai.zjdoll.app.modules.publics.oauth.wechat.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.UserWechat;
@Repository
public class WechatDaoImpl implements WechatDao{

	@Resource
	private JdbcHandler jdbcHandler;
	
	@Override
	public void saveWechatUserInfo(String openId, String unionId, String name,
			String head,String appid) {
		jdbcHandler.executeSql("insert into wechat_user (unionId,openId,name,head,appid,time) values (?,?,?,?,?,now()) on duplicate key update name=? , head=? ,time=now()",unionId,openId,name,head,appid,name,head);
	}

	@Override
	public UserWechat getUserWechat(String unionId) {
		return jdbcHandler.queryOneBySql(UserWechat.class, "select * from wechat_user where unionId=? ", unionId);
	}

}
