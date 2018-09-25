package com.imlianai.zjdoll.app.modules.core.doll.share;

import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.invite.InviteRelation;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollSuccessRes;
import com.imlianai.zjdoll.app.modules.support.invite.service.InviteService;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;

@Service
public class DollShareServiceImpl implements DollShareService {
	@Resource
	DollInfoService dollInfoService;
	@Resource
	InviteService inviteService;
	@Resource
	UserDollService userDollService;

	protected final BaseLogger logger = BaseLogger.getLogger(this
			.getClass());
	
	@Override
	public DollSuccessRes getDollSuccessShare(long uid, DollInfo doll, int busId) {
		long inviteNum = 0;
		InviteRelation inviteRelation = inviteService
				.getInviteRelationByUid(uid);
		if (inviteRelation != null && inviteRelation.getCode() > 0) {
			inviteNum = inviteRelation.getCode();
		}
		// 获取周排名
		int rank = userDollService.getRankingNum(0, uid);
		DollSuccessRes res = new DollSuccessRes(uid, inviteNum,
				doll.getImgSuccess(), doll.getImgShare(), doll.getDollId(),
				doll.getName(), busId, rank);
		logger.info("getDollSuccessShare res:"+JSON.toJSONString(res));
		return res;
	}

}
