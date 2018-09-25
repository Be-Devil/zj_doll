package com.imlianai.dollpub.app.modules.support.invite.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.support.invite.h5.service.InviteH5Service;
import com.imlianai.dollpub.app.modules.support.invite.vo.InviteListH5RespVO;
import com.imlianai.dollpub.app.modules.support.invite.vo.InviteRewardH5ReqVO;
import com.imlianai.dollpub.domain.invite.InviteH5Relation;
import com.imlianai.dollpub.domain.invite.InviteH5RewardCatalog;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;

/**
 * 邀请相关
 * 
 * @author Max
 * 
 */
@Component("inviteH5")
@Api("邀请相关-h5")
public class CmdInviteH5 extends RootCmd {

	@Resource
	private UserService userService;
	@Resource
	private InviteH5Service inviteH5Service;

	@ApiHandle
	@LogHead("查询邀请进度接口")
	@LoginCheck
	@Path("api/inviteH5/process")
	@ApiOperation(value = "【1.0.0】查询邀请进度接口", httpMethod = "POST", response = InviteListH5RespVO.class)
	public BaseRespVO process(BaseReqVO vo) {
		List<InviteH5RewardCatalog> list = inviteH5Service
				.getInviteH5RewardCatalog(vo.getUid());
		InviteListH5RespVO respVO = new InviteListH5RespVO();
		respVO.setList(list);
		InviteH5Relation relation=inviteH5Service.getInviteRelationByUid(vo.getUid());
		if (relation!=null) {
			respVO.setCount(relation.getInviteNum());
		}
		return respVO;
	}

	@ApiHandle
	@LoginCheck
	@LogHead("领取邀请奖励接口")
	@Path("api/inviteH5/reward")
	@ApiOperation(value = "【1.0.0】领取邀请奖励接口", httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO reward(InviteRewardH5ReqVO vo) {
		return inviteH5Service.gainInviteH5Reward(vo.getUid(), vo.getId());
	}

}
