package com.imlianai.zjdoll.app.modules.support.redpacket.certification.dao;

import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawUserCertificationFaceRecord;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawUserCertificationInfo;

public interface CertificationDAO {

	/**
	 * 保存人脸认证记录
	 * @param uid
	 * @param name
	 * @param certificationId
	 * @param zmBillid
	 * @param billId
	 * @return
	 */
	int addFaceCertificationRecord(Long uid, String name, String certificationId, String zmBillid, String billId);

	/**
	 * 获取脸认证记录
	 * @param uid
	 * @return
	 */
	WithdrawUserCertificationFaceRecord getMobileWithdrawFaceRecord(Long uid);

	/**
	 * 修改认证记录
	 * @param uid
	 * @param state
	 * @return
	 */
	int updateMobileWithdrawFaceRecord(Long uid, int state);

	/**
	 * 添加用户实名信息
	 * @param uid
	 * @param name
	 * @param certificationId
	 * @param string
	 * @return
	 */
	int addCertificationInfo(Long uid, String name, String certificationId);

	/**
	 * 获取用户实名信息
	 * @param uid
	 * @return
	 */
	WithdrawUserCertificationInfo getUserCertificationInfo(Long uid);
	
	/**
	 * 根据手机号获取绑定信息
	 * @param numberStr
	 * @return
	 */
	WithdrawUserCertificationInfo getWithdrawUserCertificationInfoByPhone(String numberStr);

	/**
	 * 提现绑定手机
	 * @param uid
	 * @param number
	 * @return
	 */
	int updateBindNumber(Long uid, long number);
	
}
