package com.imlianai.zjdoll.app.modules.support.dailytask.cmd;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollListService;
import com.imlianai.zjdoll.app.modules.support.dailytask.service.DailytaskService;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.DailytaskInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.GetActivenessReqVO;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.GetCountRespVO;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.GetDollBusRespVO;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.HandleInviteReqVO;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.OpenBoxReqVO;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.OpenBoxRespVO;
import com.imlianai.zjdoll.app.schedule.DailytaskTask;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("每日任务")
@LogHead("每日任务模块")
@Component("dailytask")
public class CmdDailytask extends RootCmd{
	
	@Resource
	DailytaskService dailytaskService;
	@Resource
	DollListService dollListService;
	@Resource
	DailytaskTask dailytaskTask;
	
	@ApiHandle
	@Path("api/dailytask/info")
	@ApiOperation(value = "【1.2.0】每日任务信息", notes = "每日任务信息", httpMethod = "POST", response = DailytaskInfoRespVO.class)
	@LogHead("每日任务信息")
	public BaseRespVO info(BaseReqVO reqVO) {
		return dailytaskService.getDailytaskInfo(reqVO);
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/dailytask/getActiveness")
	@ApiOperation(value = "【1.2.0】领取活跃度", notes = "领取活跃度", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("领取活跃度")
	public BaseRespVO getActiveness(GetActivenessReqVO reqVO) {
		return dailytaskService.getActiveness(reqVO.getUid(), reqVO.getTaskId());
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/dailytask/openBox")
	@ApiOperation(value = "【1.2.0】开启宝箱", notes = "开启宝箱", httpMethod = "POST", response = OpenBoxRespVO.class)
	@LogHead("开启宝箱")
	public BaseRespVO openBox(OpenBoxReqVO reqVO) {
		return dailytaskService.openBox(reqVO.getUid(), reqVO.getBoxId());
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/dailytask/getDollBus")
	@ApiOperation(value = "【1.2.0】获取随机一台娃娃机信息", notes = "获取随机一台娃娃机信息", httpMethod = "POST", response = GetDollBusRespVO.class)
	@LogHead("获取随机一台娃娃机信息")
	public BaseRespVO getDollBus(BaseReqVO reqVO) {
		GetDollBusRespVO respVO = new GetDollBusRespVO();
		List<DollBus> dollBusList = dollListService.getBusList(null);
		if(!StringUtil.isNullOrEmpty(dollBusList)) {
			for(int i = 1; i <= 100; i++) {
				DollBus dollBus = dollBusList.get(new Random().nextInt(dollBusList.size()));
				if(dollBus.getStatus() != 2) { // 排除故障中的机器
					respVO.setDollBus(dollBus);
					break;
				}
			}
		}
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/dailytask/getCount")
	@ApiOperation(value = "【1.2.0】获取可领取活跃度的任务数量", notes = "获取可领取活跃度的任务数量", httpMethod = "POST", response = GetCountRespVO.class)
	@LogHead("获取可领取活跃度的任务数量")
	public BaseRespVO getCount(BaseReqVO reqVO) {
		GetCountRespVO respVO = new GetCountRespVO();
		respVO.setNum(dailytaskService.getCount(reqVO.getUid()));
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/dailytask/handleInvite")
	@ApiOperation(value = "【1.2.0】分享邀请处理", notes = "分享邀请处理", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("分享邀请处理")
	public BaseRespVO handleInvite(HandleInviteReqVO reqVO) {
		return dailytaskService.handleInvite(reqVO);
	}
	
	// 活跃度还没达到10的消息推送
	@ApiHandle
	@Path("api/dailytask/pushDailytaskMsg")
	public BaseRespVO pushDailytaskMsg() {
		dailytaskTask.pushDailytaskMsg();
		return new BaseRespVO();
	}
}
