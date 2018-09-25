package com.imlianai.dollpub.app.pvd;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.imlianai.dollpub.app.iface.IAppSupportRemoteService;
import com.imlianai.dollpub.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.dollpub.app.modules.support.xxrecharge.service.XxingRechargeService;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@Service(interfaceClass = IAppSupportRemoteService.class)
public class AppSupportRemoteServiceImpl implements IAppSupportRemoteService {

	BaseLogger logger = BaseLogger.getLogger(this.getClass());
	
	@Resource
	private UserDollService userDollService;
	@Resource
	private XxingRechargeService xxingRechargeService;

	@Override
	public BaseRespVO returnDoll(Long optId, long uid) {
		return userDollService.returnDoll(optId, uid);
	}

	@Override
	public BaseRespVO refundCoin(Long optId, long uid) {
		return userDollService.refundCoin(optId, uid);
	}

	@Override
	public BaseRespVO handleRecharge(Long uid, int type, int productCode, long phone, long uDollId) {
		return xxingRechargeService.handleRecharge(uid, type, productCode, phone, uDollId);
	}
	
}
