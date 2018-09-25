package com.imlianai.zjdoll.app.modules.support.redpacket.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawDetailRecord;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawUserAccount;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawUserPhone;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawWechatBoundMiddleware;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawWechatCommunicationLog;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawWechatInnerMiddleware;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class WithdrawDaoImpl implements WithdrawDao {

	@Resource
	JdbcHandler jdbcHandler;

	String getWithdrawUserAccountByUid = "select * from withdraw_user_account where uid=?";
	@Override
	public WithdrawUserAccount getWithdrawUserAccountByUid(Long uid) {
		return jdbcHandler.queryOneBySql(WithdrawUserAccount.class, getWithdrawUserAccountByUid, uid);
	}
	
	String getWithdrawTimes = "select count(1) from user_red_packet_log where uid=? and type=?";
	@Override
	public int getWithdrawTimes(Long uid, int type) {
		return jdbcHandler.queryCount(getWithdrawTimes, uid, type);
	}
	
	String getCurrDateWithdrawTimes = "select count(1) from user_red_packet_log where uid=? and type=? and dateCode=?";
	@Override
	public int getCurrDateWithdrawTimes(Long uid, int type, int dateCode) {
		return jdbcHandler.queryCount(getCurrDateWithdrawTimes, uid, type, dateCode);
	}
	
	String getWithdrawWechatBoundMiddlewareByUid = "select * from withdraw_wechat_bound_middleware where uid=?";
	@Override
	public WithdrawWechatBoundMiddleware getWithdrawWechatBoundMiddlewareByUid(Long uid) {
		return jdbcHandler.queryOneBySql(WithdrawWechatBoundMiddleware.class, getWithdrawWechatBoundMiddlewareByUid, uid);
	}

	String getWithdrawWechatBoundMiddlewareByUnionId = "select * from withdraw_wechat_bound_middleware where unionId=? limit 1";
	@Override
	public WithdrawWechatBoundMiddleware getWithdrawWechatBoundMiddlewareByUnionId(String unionId) {
		return jdbcHandler.queryOneBySql(WithdrawWechatBoundMiddleware.class, getWithdrawWechatBoundMiddlewareByUnionId, unionId);
	}
	
	String saveWithdrawWechatBoundMiddleware = "insert into withdraw_wechat_bound_middleware(uid,openId,unionId,time) values(?,?,?,now())";
	@Override
	public int saveWithdrawWechatBoundMiddleware(Long uid, String openId, String unionId) {
		return jdbcHandler.executeSql(saveWithdrawWechatBoundMiddleware, uid, openId, unionId);
	}
	
	String getWithdrawWechatInnerMiddlewareByUnionid = "select * from withdraw_wechat_inner_middleware where unionId=? limit 1";
	@Override
	public WithdrawWechatInnerMiddleware getWithdrawWechatInnerMiddlewareByUnionid(String unionId) {
		return jdbcHandler.queryOneBySql(WithdrawWechatInnerMiddleware.class, getWithdrawWechatInnerMiddlewareByUnionid, getWithdrawWechatInnerMiddlewareByUnionid);
	}
	
	String addWithdrawUserAccount = "insert into withdraw_user_account(uid,openId) values(?,?)  on duplicate key update openId=?";
	@Override
	public int addWithdrawUserAccount(Long uid, String openId) {
		return jdbcHandler.executeSql(addWithdrawUserAccount, uid, openId,openId);
	}
	
	String updateWithdrawAccountSql = "update withdraw_user_account set "
			+ "followFlag=1  where uid=? ";
	@Override
	public int updateWithdrawAccountFollow(Long uid) {
		return jdbcHandler.executeSql(updateWithdrawAccountSql, uid);
	}
	
	String getWechatInnerBoundOpenId = "select openId from withdraw_wechat_inner_middleware where unionId=?";
	@Override
	public String getWechatInnerBoundOpenId(String unionId) {
		return jdbcHandler.queryOneBySql(String.class, getWechatInnerBoundOpenId, unionId);
	}
	
	String addWechatInnerMiddleware = "insert into withdraw_wechat_inner_middleware(openId,unionId,time) values(?,?,now()) ";
	@Override
	public int addWechatInnerMiddleware(String openId, String unionId) {
		return jdbcHandler.executeSql(addWechatInnerMiddleware, openId, unionId);
	}
	
	String getBoundUid = "select uid from withdraw_user_account where openId=? and valid=1 limit 1";
	@Override
	public Long getBoundUid(String openId) {
		return jdbcHandler.queryOneBySql(Long.class, getBoundUid, openId);
	}
	
	String getAppWechatBoundUidByUnionId = "select uid from withdraw_wechat_bound_middleware where unionId=? limit 1";
	@Override
	public Long getAppWechatBoundUidByUnionId(String unionId) {
		return jdbcHandler.queryOneBySql(Long.class, getAppWechatBoundUidByUnionId, unionId);
	}
	
	String saveWithdrawRecord = "insert into withdraw_record(uid,openId,amt,remark,createTime,createDate) values(?,?,?,?,now(),?)";
	@Override
	public int saveWithdrawRecord(Long uid, String openId, int amt, int dateCode, String remark) {
		return jdbcHandler.executeSql(saveWithdrawRecord, uid, openId, amt, remark, dateCode);
	}
	
	String updateWithdrawAccountTotalAmt = "update withdraw_user_account set amount=amount+? where uid=?";
	@Override
	public int updateWithdrawAccountTotalAmt(Long uid, int withdrawAmt) {
		return jdbcHandler.executeSql(updateWithdrawAccountTotalAmt, withdrawAmt, uid);
	}
	
	String withdrawDetailRecord = "select * from withdraw_record where auditFlag=? order by createTime asc limit 1";
	@Override
	public WithdrawDetailRecord withdrawDetailRecord(int auditFlag) {
		return jdbcHandler.queryOneBySql(WithdrawDetailRecord.class, withdrawDetailRecord, auditFlag);
	}
	
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
	
	private static String updateWithdrawRecordsAsPaySql = "update withdraw_record set "
			+ " auditFlag=2  where id=? and auditFlag=1";
	@Override
	public int updateWithdrawRecordsAsPay(Long id) {
		return jdbcHandler.executeSql(updateWithdrawRecordsAsPaySql, id);
	}
	
	private static String addWechatCallbackLogSql = "insert into withdraw_wechat_communication_log (recordId,billId,uid,openId,amt,`request`,time,dateSort) values (?,?,?,?,?,?,now(),DATE_FORMAT(now(),'%Y-%m-%d'))";
	@Override
	public int addWechatCallbackLog(long recordId,String billId, long uid, String openId,
			double amt, String request) {
		return jdbcHandler.executeSql(addWechatCallbackLogSql,recordId,billId,uid,openId,amt,request);
	}
	
	@Override
	public void updateReason(long id, String msg) {
		jdbcHandler.executeSql("update withdraw_record set reason=? where id=?", msg,id);
	}

	private static String resetWithdrawRecordsSql = "update withdraw_record set "
			+ " auditFlag=0  where id=? ";
	@Override
	public int resetWithdrawRecords(long recordId) {
		return jdbcHandler.executeSql(resetWithdrawRecordsSql, recordId);
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
	
	private static String updateWithdrawRecordsWithWechatInfoPayStateSql = "update withdraw_record set "
			+ " payFlag=?  where id=? ";
	@Override
	public int updateWithdrawRecordsWithWechatInfoPayState(Long id, int payFlag) {
		return jdbcHandler.executeSql(updateWithdrawRecordsWithWechatInfoPayStateSql, payFlag,id);
	}

	String getWithdrawUserPhone = "select * from withdraw_user_phone where number=?";
	@Override
	public WithdrawUserPhone getWithdrawUserPhoneByNumber(long number) {
		return jdbcHandler.queryOneBySql(WithdrawUserPhone.class, getWithdrawUserPhone, number);
	}

	String initPhone = "insert into withdraw_user_phone(number,uid,checkCode,count,sendTime) values(?,?,?,1,now()) on duplicate key update checkCode=?,count=?,sendTime=now()";
	@Override
	public int initPhone(WithdrawUserPhone phoneInit) {
		return jdbcHandler.executeSql(initPhone, phoneInit.getNumber(), phoneInit.getUid(), phoneInit.getCheckCode(), phoneInit.getCheckCode(), phoneInit.getCount());
	}
}
