package com.imlianai.zjdoll.app.modules.support.signin.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.signin.SigninAward;
import com.imlianai.zjdoll.domain.signin.UserSignin;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class UserSigninDaoImpl implements UserSigninDao {
	
	@Resource
	JdbcHandler jdbcHandler;

	String getSigninAwards = "select * from signin_award order by id asc";
	@Override
	public List<SigninAward> getSigninAwards() {
		return jdbcHandler.queryBySql(SigninAward.class, getSigninAwards);
	}
	
	String getUserSigninByDateCode = "select * from user_signin where uid=? and dateCode=?";
	@Override
	public UserSignin getUserSigninByDateCode(Long uid, int dateCode) {
		return jdbcHandler.queryOneBySql(UserSignin.class, getUserSigninByDateCode, uid, dateCode);
	}
	
	String getSigninTimes = "select times from user_signin where uid=? order by times desc limit 1";
	@Override
	public int getSigninTimes(Long uid) {
		return jdbcHandler.queryCount(getSigninTimes, uid);
	}
	
	String saveUserSiginin = "insert into user_signin(uid,dateCode,remark,createTime,times,awardId) values(?,?,?,now(),?,?)";
	@Override
	public int saveUserSiginin(Long uid, int dateCode, String remark, int times, long awardId) {
		return jdbcHandler.executeSql(saveUserSiginin, uid, dateCode, remark, times, awardId);
	}
}
