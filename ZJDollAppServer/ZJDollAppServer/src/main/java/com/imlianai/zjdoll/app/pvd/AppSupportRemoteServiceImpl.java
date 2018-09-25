package com.imlianai.zjdoll.app.pvd;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.iface.IAppSupportRemoteService;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.ZengjingUtils;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.live.qiniu.pili.SaveRet;
import com.imlianai.zjdoll.app.modules.support.invite.service.InviteService;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.zjdoll.app.modules.support.xxrecharge.service.XxingRechargeService;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;

@Service(interfaceClass = IAppSupportRemoteService.class)
public class AppSupportRemoteServiceImpl implements IAppSupportRemoteService {

	BaseLogger logger = BaseLogger.getLogger(this.getClass());
	
	@Resource
	UserDollService userDollService;
	@Resource
	InviteService inviteService;
	@Resource
	XxingRechargeService xxingRechargeService;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	UserService userService;

	@Override
	public BaseRespVO returnDoll(Long optId, long uid) {
		return userDollService.returnDoll(optId, uid);
	}

	@Override
	public BaseRespVO refundCoin(Long optId, long uid) {
		return userDollService.refundCoin(optId, uid);
	}

	@Override
	public int exchangeUpdateShopNotice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BaseRespVO handleRecharge(Long uid, int type, int productCode, long phone, long uDollId) {
		return xxingRechargeService.handleRecharge(uid, type, productCode, phone, uDollId);
	}

	@Override
	public BaseRespVO giveDoll(Long uid, int dollId, String remark) {
		DollInfo dollInfo = dollInfoService.getDollInfo(dollId);
		if(userService.getUserGeneral(uid) == null) {
			return new BaseRespVO(-1, false, "用户不存在~");
		}
		if(dollInfo == null) {
			return new BaseRespVO(-1, false, "娃娃【" + dollId + "】不存在，请刷新重试~");
		}
		UserDoll userDoll = new UserDoll();
		userDoll.setUid(uid);
		userDoll.setDollId(dollId);
		userDoll.setStatus(0);
		userDoll.setRemark(remark);
		userDoll.setGoodsType(dollInfo.getGoodsType());
		long uDollId = userDollService.saveUserDoll(userDoll);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("uDollId", uDollId);
		BaseRespVO respVO = new BaseRespVO();
		respVO.setData(resultMap);
		return respVO;
	}

	@Override
	public String getVideo(long start, long end, String streamUrl) {
		SaveRet saveRet= ZengjingUtils.getVideo(start,end,streamUrl);
		if (!StringUtil.isNullOrEmpty(saveRet)){
			return saveRet.getFname();
		}
		return "";
	}
}
