package com.imlianai.dollpub.app.modules.core.user.phone;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.user.UserEmail;
import com.imlianai.dollpub.domain.user.UserPhone;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class UserPhoneDAOImpl implements UserPhoneDAO{
	
	@Resource
	private JdbcHandler jdbcHandler;
	
	private String initPhone = "insert into user_phone(number,imei,checkCode,inviteCode,sendTime,regTime) values(?,?,?,?,now(),now()) " +
			"on duplicate key update imei=?,checkCode=?,inviteCode=?,count=?,regState=0,sendTime=now() ";
	@Override
	public int initPhone(UserPhone phone) {
		return jdbcHandler.executeSql(initPhone, phone.getNumber(), phone.getImei(), phone.getCheckCode(), phone.getInviteCode(),
				phone.getImei(), phone.getCheckCode(), phone.getInviteCode(), phone.getCount());
	}
	
	private String updateCheckCode = "update user_phone set regState=?,checkCode=?,regTime=now() where number=? ";
	@Override
	public int updateCheckCode(long number, int checkCode) {
		return jdbcHandler.executeSql(updateCheckCode, 0, checkCode, number);
	}
	
	private String updateInviteCode = "update user_phone set inviteCode=?,regTime=now() where number=? ";
	@Override
	public int updateInviteCode(long number, String inviteCode) {
		return jdbcHandler.executeSql(updateInviteCode, inviteCode, number);
	}
	
	private String updateState = "update user_phone set regState=?,regTime=now() where number=? and regState=? and checkCode=? ";
	@Override
	public int updateState(long number, int checkCode) {
		return jdbcHandler.executeSql(updateState, 1, number, 0, checkCode);
	}
	
	private String getPhoneByNum = "select * from user_phone where number=? ";
	@Override
	public UserPhone getPhoneByNum(long number) {
		return jdbcHandler.queryOneBySql(UserPhone.class, getPhoneByNum, number);
	}

	private String initEmail = "insert into user_email(email,pwd,regTime) values(?,?,now()) "
			+ " on duplicate key update pwd=? ";
	@Override
	public int initEmail(String email, String pwd) {
		return jdbcHandler.executeSql(initEmail, email, pwd, pwd);
	}
	
	private String updateEmailUid = "update user_email set uid=? where email=? ";
	public int updateEmailUid(String email, long uid){
		return jdbcHandler.executeSql(updateEmailUid, uid, email);
	}	
	
	private String getEmail = "select * from user_email where email=? and uid!=0 ";
	@Override
	public UserEmail getEmail(String email) {
		return jdbcHandler.queryOneBySql(UserEmail.class, getEmail, email);
	}
}
