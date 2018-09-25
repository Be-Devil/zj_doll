package com.imlianai.zjdoll.app.modules.support.oauth.dao;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.support.oauth.domain.WeiXinUser;
@Repository
public class OauthDaoImpl implements OauthDao {

	@Resource
	private JdbcHandler jdbcHandler;

	private static String addWeiXinUserSql = "insert into weixin_user(openid,nickname,sex,province,city,country,headimgurl,privilege,unionid,time) values(?,?,?,?,?,?,?,?,?,now())";

	@Override
	public int addWeiXinUser(WeiXinUser user) {
		return jdbcHandler.executeSql(addWeiXinUserSql, user.getOpenid(),
				user.getNickname(), user.getSex(), user.getProvince(),
				user.getCity(), user.getCountry(), user.getHeadimgurl(),
				user.getPrivilege(), user.getUnionid());
	}
}
