package com.imlianai.dollpub.app.modules.support.withdraw.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.withdraw.WithdrawDetailRecord;
import com.imlianai.dollpub.domain.withdraw.WithdrawUserAccount;
import com.imlianai.dollpub.domain.withdraw.WithdrawUserPhone;
import com.imlianai.dollpub.domain.withdraw.WithdrawWechatBoundMiddleware;
import com.imlianai.dollpub.domain.withdraw.WithdrawWechatCommunicationLog;
import com.imlianai.dollpub.domain.withdraw.WithdrawWechatInnerMiddleware;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class WithdrawDaoImpl implements WithdrawDao {

	@Resource
	JdbcHandler jdbcHandler;
	
	
	private static String hasWithdrawInAMinuteSql = "select 1 from withdraw_wechat_communication_log where uid=? and time>DATE_SUB(now(), Interval 1 MINUTE) limit 1";
	@Override
	public int hasWithdrawInAMinute(Long uid) {
		return jdbcHandler.queryRowCount(hasWithdrawInAMinuteSql, uid);
	}
	
	private static String hasWithdrawInAMinuteSql1 = "select 1 from withdraw_wechat_communication_log where openId=? and time>DATE_SUB(now(), Interval 1 MINUTE) limit 1";
	@Override
	public int hasWithdrawInAMinute(String openId){
		return jdbcHandler.queryRowCount(hasWithdrawInAMinuteSql1, openId);
	}
	
	private static String addWechatCallbackLogSql = "insert into withdraw_wechat_communication_log (recordId,billId,uid,openId,amt,`request`,time,dateSort) values (?,?,?,?,?,?,now(),DATE_FORMAT(now(),'%Y-%m-%d'))";
	@Override
	public int addWechatCallbackLog(long recordId,String billId, long uid, String openId,
			double amt, String request) {
		return jdbcHandler.executeSql(addWechatCallbackLogSql,recordId,billId,uid,openId,amt,request);
	}

	private static String updateWechatCallbackLogSql ="update withdraw_wechat_communication_log set respone=? , successFlag=? where recordId=? and billId=?";
	@Override
	public int updateWechatCallbackLog(long recordId,String wechatPaymentId , String respone,
			int successflag) {
		return jdbcHandler.executeSql(updateWechatCallbackLogSql,respone,successflag,recordId,wechatPaymentId);
	}

	private static String updateWithdrawRecordsWithWechatInfoSql = "update withdraw_record set "
			+ " wechatPaymentId=?,payTime=now(),payFlag=?  where id=? ";
	@Override
	public int updateWithdrawRecordsWithWechatInfo(long id,
			String wechatPaymentId, int successFlag) {
		return jdbcHandler.executeSql(updateWithdrawRecordsWithWechatInfoSql,wechatPaymentId,successFlag,id);
	}
	
	private static String getMobileWithdrawWechatCommunicationRecordListSql = "select * from withdraw_wechat_communication_log where dateSort=DATE_FORMAT(DATE_SUB(now(), Interval 4 DAY),'%Y-%m-%d')  and successFlag=1";
	@Override
	public List<WithdrawWechatCommunicationLog> getMobileWithdrawWechatCommunicationRecordList() {
		return jdbcHandler.queryBySql(WithdrawWechatCommunicationLog.class, getMobileWithdrawWechatCommunicationRecordListSql);
	}
	
	private static String addWechatCallbackLogSql1 = "insert into withdraw_wechat_communication_log (recordId,billId,uid,openId,amt,`request`,`respone`,time,dateSort,successFlag) values (?,?,?,?,?,?,?,?,DATE_FORMAT(?,'%Y-%m-%d'),99)";
	@Override
	public int addWechatCallbackLog(Long recordId, String billId, Long uid, String openId, double amt, String request,
			String respone, String time) {
		return jdbcHandler.executeSql(addWechatCallbackLogSql1, recordId, billId, uid, openId, amt, request, respone, time, time);
	}
	@Override
	public int addCertificationInfo(long uid, String name, String number) {
		return jdbcHandler.executeSql("insert into withdraw_certification_info (uid,name,number,time) value (?,?,?,now())", uid,name,number);
	}
	@Override
	public int hasCertificationInfo(long uid){
		return jdbcHandler.queryCount("select 1 from withdraw_certification_info where uid=?", uid);
	}
	
}
