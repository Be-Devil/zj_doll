package com.imlianai.zjdoll.app.modules.support.redpacket.certification.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawUserCertificationFaceRecord;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawUserCertificationInfo;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.support.redpacket.certification.dao.CertificationDAO;
import com.imlianai.zjdoll.app.modules.support.redpacket.certification.utils.AliCreditVerifyUtil;
import com.imlianai.zjdoll.app.modules.support.redpacket.certification.utils.VerificationResult;
import com.imlianai.zjdoll.app.modules.support.redpacket.dao.WithdrawDao;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.HandleFaceCerReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.HandleFaceCerRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.QueryFaceResultRespVO;

@Service
public class CertificationServiceImpl implements CertificationService {

	private static final BaseLogger LOG = BaseLogger.getLogger(CertificationServiceImpl.class);
	
	@Resource
	WithdrawDao withdrawDao;
	@Resource
	CertificationDAO certificationDAO;
	
	@Override
	public BaseRespVO handleFaceCertification(HandleFaceCerReqVO reqVO) {
		try {
			String name = reqVO.getName();
			String certificationId = reqVO.getCertificationId();
			Long uid = reqVO.getUid();
			WithdrawUserCertificationInfo userCerInfo = certificationDAO.getUserCertificationInfo(uid);
			if(userCerInfo != null && !StringUtil.isNullOrEmpty(userCerInfo.getCertificationId())) {
				return new HandleFaceCerRespVO(-1, false, "已绑定无需重复绑定");
			}
			name = name.replace("<", "《").replace(">", "》").replace("on", ""); // 防xss攻击
			certificationId = certificationId.replace("<", "《").replace(">", "》").replace("on", "");
			VerificationResult verificationesult= addFaceCertification(uid, name, certificationId);
			if (verificationesult.isState()) {
				HandleFaceCerRespVO respVO = new HandleFaceCerRespVO();
				respVO.setUrl(verificationesult.getMsg());
				respVO.setZmBillId(verificationesult.getZmBillid());
				return respVO;
			} else {
				return new HandleFaceCerRespVO(-1, false, verificationesult.getMsg());
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new HandleFaceCerRespVO(-1, false, "认证失败，请重试~");
		}
	}

	private VerificationResult addFaceCertification(Long uid, String name, String certificationId) {
		String billId = System.currentTimeMillis()
				+ AppUtils.randomStr("1234567890", 4);
		LOG.info("addFaceCertification uid:" + uid + " cert_no:"
				+ certificationId + " name:" + name + " billId:" + billId);
		VerificationResult verificationResult = AliCreditVerifyUtil.createFaceCreditVerifyOrder(billId, certificationId, name);
		if (verificationResult != null && verificationResult.isState()) {
			certificationDAO.addFaceCertificationRecord(uid, name,
					certificationId, verificationResult.getZmBillid(), billId);
		}
		return verificationResult;
	}

	@Override
	public BaseRespVO queryFaceResult(Long uid) {
		QueryFaceResultRespVO respVO = new QueryFaceResultRespVO();
		WithdrawUserCertificationInfo userCerInfo = certificationDAO.getUserCertificationInfo(uid);
		if (userCerInfo == null || StringUtil.isNullOrEmpty(userCerInfo.getCertificationId())) {
			WithdrawUserCertificationFaceRecord mobileWithdrawFaceRecord = certificationDAO.getMobileWithdrawFaceRecord(uid);
			if (mobileWithdrawFaceRecord != null) {
				VerificationResult verificationResult = AliCreditVerifyUtil.QueryCertificationResult(mobileWithdrawFaceRecord
								.getZmBillId());
				if (verificationResult.isState()) {
					certificationDAO.updateMobileWithdrawFaceRecord(uid, 1);
					certificationDAO.addCertificationInfo(uid, mobileWithdrawFaceRecord.getName(),
							mobileWithdrawFaceRecord.getCertificationId());
					respVO.setAuth(true);
					return respVO;
				} else {
					respVO.setAuth(false);
					respVO.setState(false);
					respVO.setReason(verificationResult.getMsg());
					respVO.setMsg(verificationResult.getMsg());
					return respVO;
				}
			} else {
				return new BaseRespVO(-1, false, "未找到相关订单");
			}
		} else {
			respVO.setAuth(true);
			return respVO;
		}
	}
}
