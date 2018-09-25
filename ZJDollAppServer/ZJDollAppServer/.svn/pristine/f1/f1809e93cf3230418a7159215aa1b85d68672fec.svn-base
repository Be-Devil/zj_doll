package com.imlianai.zjdoll.app.modules.support.userdoll.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.doll.BaseDollInfo;
import com.imlianai.zjdoll.domain.doll.LogisticsInfo;
import com.imlianai.zjdoll.domain.doll.user.RankingItem;
import com.imlianai.zjdoll.domain.invite.InviteRelation;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.user.vo.GetDollsReqVO;
import com.imlianai.zjdoll.app.modules.core.user.vo.GetDollsRespVO;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.invite.service.InviteService;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.zjdoll.app.modules.support.userdoll.vo.GetDollSizeInfo;
import com.imlianai.zjdoll.app.modules.support.userdoll.vo.GetDollSizeRespVO;
import com.imlianai.zjdoll.app.modules.support.userdoll.vo.GetLogisticsReqVO;
import com.imlianai.zjdoll.app.modules.support.userdoll.vo.GetLogisticsRespVO;
import com.imlianai.zjdoll.app.schedule.DollExchangeTask;
import com.imlianai.zjdoll.app.schedule.HandleWeekRewardTask;
import com.imlianai.zjdoll.app.schedule.WeekMonthCardTask;

@Api("用户娃娃")
@LogHead("用户娃娃")
@Component("userDoll")
public class CmdUserDoll extends RootCmd{
	
	private static BaseLogger LOG = BaseLogger.getLogger(CmdUserDoll.class);

	@Resource
	UserDollService userDollService;
	@Resource
	DollExchangeTask dollExchangeTask;
	@Resource
	HandleWeekRewardTask handleWeekRewardTask;
	@Resource
	MsgService msgService;
	@Resource
	WeekMonthCardTask weekMonthCardTask;
	@Resource
	private InviteService inviteService;
	@ApiHandle
	@Path("api/userDoll/getDolls")
	@ApiOperation(value = "【1.0.0】查看用户娃娃", notes = "查看用户娃娃", httpMethod = "POST", response = GetDollsRespVO.class)
	@LogHead("查看用户娃娃")
	public BaseRespVO getDolls(GetDollsReqVO reqVO) {
		GetDollsRespVO respVO = new GetDollsRespVO();
		RankingItem rankingItem = userDollService.getUserDollCount(reqVO.getUserId());
		List<BaseDollInfo> dollList = userDollService.getUserDollList(reqVO.getUserId(), reqVO.getLastId());
		respVO.setUserInfo(rankingItem);
		respVO.setDollList(dollList);
		InviteRelation inviteRelation=inviteService.getInviteRelationByUid(reqVO.getUserId());
		if (inviteRelation!=null) {
			respVO.setInviteCode(inviteRelation.getCode());
		}
		return respVO;
	}
	
	@ApiHandle
	@Path("api/userDoll/getSize")
	@ApiOperation(value = "【1.0.0】获取用户娃娃个数", notes = "获取用户娃娃个数", httpMethod = "POST", response = GetDollSizeRespVO.class)
	@LogHead("获取用户娃娃个数")
	public BaseRespVO getSize(BaseReqVO reqVO) {
		GetDollSizeRespVO respVO = new GetDollSizeRespVO();
		GetDollSizeInfo info = userDollService.getDollSize(reqVO.getUid());
		respVO.setDollSizeInfo(info);
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/userDoll/getLogistics")
	@ApiOperation(value = "【1.0.0】获取物流信息", notes = "获取物流信息", httpMethod = "POST", response = GetLogisticsRespVO.class)
	@LogHead("获取物流信息")
	public BaseRespVO getLogistics(GetLogisticsReqVO reqVO) {
		GetLogisticsRespVO respVO = new GetLogisticsRespVO();
		List<LogisticsInfo> logisticsInfos = userDollService.getLogistics(reqVO.getUdollId());
		respVO.setLogisticsInfos(logisticsInfos);
		return respVO;
	}
	
	// 娃娃自动兑换成游戏币任务
	@ApiHandle
	@Path("api/userDoll/handleExchange")
	public BaseRespVO handleExchange() {
		dollExchangeTask.handleExchange();
		return new BaseRespVO();
	}
	
	// 每周奖励任务
	@ApiHandle
	@Path("api/userDoll/handleReward")
	public BaseRespVO handleReward() {
		handleWeekRewardTask.handleReward();
		return new BaseRespVO();
	}
	
	@ApiHandle
	@Path("api/userDoll/noticeMsgTest")
	public BaseRespVO noticeMsgTest(BaseReqVO reqVO) {
		Long uid = reqVO.getUid();
		LOG.info("noticeMsgTest:uid" + uid);
		if(uid == null) {
			uid = 1519574l;
		}
		Msg msg = new Msg(uid, MsgType.NOTICE_SYS.type,
				"苏绮珊要睡觉了");
		msgService.sendMsg(msg);
		return new BaseRespVO();
	}
	
	@ApiHandle
	@Path("api/userDoll/pushRemindRewardMsg")
	public BaseRespVO pushRemindRewardMsg() {
		weekMonthCardTask.pushRemindRewardMsg();
		return new BaseRespVO();
	}
	
}
