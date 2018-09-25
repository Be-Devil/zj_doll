package com.imlianai.zjdoll.app.modules.support.redpacket.certification.service;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.HandleFaceCerReqVO;




/**
 * 实名认证
 * @author cls
 *
 */
public interface CertificationService {

	/**
	 * 人脸识别验证
	 * @param reqVO
	 * @return
	 */
	public BaseRespVO handleFaceCertification(HandleFaceCerReqVO reqVO);

	/**
	 * 查询是否已实名
	 * @param uid
	 * @return
	 */
	public BaseRespVO queryFaceResult(Long uid);
}
