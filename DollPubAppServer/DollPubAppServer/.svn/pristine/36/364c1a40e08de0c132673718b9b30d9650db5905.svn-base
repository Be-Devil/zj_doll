package com.imlianai.dollpub.app.modules.core.user.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.app.modules.core.user.dao.UserSrcDao;
import com.imlianai.dollpub.app.modules.core.user.domain.CustomerAuthToken;
import com.imlianai.dollpub.domain.user.UserSrc;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

/**
 * @author wurui
 * @create 2018-01-24 11:27
 **/
@Repository
public class UserSrcDaoImpl implements UserSrcDao {

	@Resource
	private JdbcHandler jdbcHandler;

	private String insert_sql = "INSERT INTO user_src(uid,name,head,customerId,srcId,srcCode,regTime) VALUES(?,?,?,?,?,?,NOW())";

	@Override
	public int saveSrcUser(UserSrc userSrc) {
		return jdbcHandler.executeSql(insert_sql, userSrc.getUid(),
				userSrc.getName(), userSrc.getHead(), userSrc.getCustomerId(),
				userSrc.getSrcId(), userSrc.getSrcCode());
	}

	private String update_sql = "UPDATE user_src SET name=?,head=? WHERE uid=?";

	@Override
	public int updateSrcUser(UserSrc userSrc) {
		return jdbcHandler.executeSql(update_sql, userSrc.getName(),
				userSrc.getHead(), userSrc.getUid());
	}

	private String select_sql = "select * from user_src";

	@Override
	public UserSrc getUserSrcBySrcUidAndCusId(String srcId, int customerId) {
		return jdbcHandler.queryOneBySql(UserSrc.class, select_sql
				+ " where srcId=? and customerId=?", srcId, customerId);
	}

	@Override
	public CustomerAuthToken getCustomerAuthToken(int customerId, String uid) {
		return jdbcHandler
				.queryOneBySql(
						CustomerAuthToken.class,
						"select * from oauth_customer_auth_token where authCustomerId=? and srcUid=? limit 1",customerId,uid);
	}

	@Override
	public int addCustomerAuthToken(int customerId, String uid, String token) {
		return jdbcHandler
				.executeSql(
						"insert into oauth_customer_auth_token(authCustomerId,srcUid,token,time) value (?,?,?,now()) on duplicate key update token=? ,time=now()",
						customerId, uid, token, token);
	}

	@Override
	public int updateInnerUid(int authCustomerId, String srcUid, long uid) {
		return jdbcHandler.executeSql("update oauth_customer_auth_token set innerUid=? ,time=now() where authCustomerId=? and srcUid=?", uid,authCustomerId,srcUid);
	}

	@Override
	public CustomerAuthToken getCustomerAuthToken(long innerUid) {
		return jdbcHandler
				.queryOneBySql(
						CustomerAuthToken.class,
						"select * from oauth_customer_auth_token where innerUid=? limit 1",innerUid);
	}

}
