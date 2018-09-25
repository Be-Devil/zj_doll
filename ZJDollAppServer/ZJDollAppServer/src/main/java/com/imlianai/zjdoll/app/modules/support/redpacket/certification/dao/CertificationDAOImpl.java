package com.imlianai.zjdoll.app.modules.support.redpacket.certification.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawUserCertificationFaceRecord;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawUserCertificationInfo;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class CertificationDAOImpl implements CertificationDAO{

	@Resource
	JdbcHandler jdbcHandler;
	
	String addFaceCertificationRecord = "insert into withdraw_user_certification_face_record (uid,name,certificationId,zmBillId,innerBillId,time,state) values  "
			+ "(?,?,?,?,?,now(),?) ON DUPLICATE KEY UPDATE name=? ,certificationId=?,zmBillId=?,innerBillId=?,time=now()";
	@Override
	public int addFaceCertificationRecord(Long uid, String name, String certificationId, String zmBillId, String innerBillId){
		return jdbcHandler.executeSql(addFaceCertificationRecord, uid,name,certificationId,zmBillId,innerBillId,0,name,certificationId,zmBillId,innerBillId);
	}
	
	String getMobileWithdrawFaceRecord = "select * from withdraw_user_certification_face_record where uid=?";
	@Override
	public WithdrawUserCertificationFaceRecord getMobileWithdrawFaceRecord(Long uid) {
		return jdbcHandler.queryOneBySql(WithdrawUserCertificationFaceRecord.class, getMobileWithdrawFaceRecord, uid);
	}
	
	String updateMobileWithdrawFaceRecord = "update withdraw_user_certification_face_record set state=? where uid=?";
	@Override
	public int updateMobileWithdrawFaceRecord(Long uid, int state) {
		return jdbcHandler.executeSql(updateMobileWithdrawFaceRecord, state, uid);
	}
	
	String addCertificationInfo = "insert into withdraw_user_certification_info (uid,name,certificationId,time) values  "
			+ "(?,?,?,now()) ON DUPLICATE KEY UPDATE name=? , certificationId=?";
	@Override
	public int addCertificationInfo(Long uid, String name, String certificationId) {
		return jdbcHandler.executeSql(addCertificationInfo, uid, name, certificationId, name, certificationId);
	}
	
	String getUserCertificationInfo = "select * from withdraw_user_certification_info where uid=?";
	@Override
	public WithdrawUserCertificationInfo getUserCertificationInfo(Long uid) {
		return jdbcHandler.queryOneBySql(WithdrawUserCertificationInfo.class, getUserCertificationInfo, uid);
	}
	
	String getWithdrawUserCertificationInfoByPhone = "select * from withdraw_user_certification_info where phone=? limit 1";
	@Override
	public WithdrawUserCertificationInfo getWithdrawUserCertificationInfoByPhone(String numberStr) {
		return jdbcHandler.queryOneBySql(WithdrawUserCertificationInfo.class, getWithdrawUserCertificationInfoByPhone, numberStr);
	}
	
	String updateBindNumber = "insert into withdraw_user_certification_info(uid,phone,time) values "
			+ "(?,?,now()) ON DUPLICATE KEY UPDATE phone=?, time=now()";
	@Override
	public int updateBindNumber(Long uid, long number) {
		return jdbcHandler.executeSql(updateBindNumber, uid, number, number);
	}
}
