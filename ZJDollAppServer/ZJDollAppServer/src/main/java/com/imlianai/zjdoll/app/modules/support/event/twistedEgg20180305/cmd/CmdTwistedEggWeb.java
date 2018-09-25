package com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.cmd;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.service.EventTwistedEgg20180305Service;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.vo.GetInfosRespVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.vo.ReceiveReqVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.vo.TwistedEggInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("任务扭蛋活动")
@LogHead("任务扭蛋活动")
@Component("twistedEggWeb")
public class CmdTwistedEggWeb extends RootCmd {

	@Resource
	EventTwistedEgg20180305Service eventTwistedEgg20180305Service;

	@ApiHandle
	@Path("api/twistedEggWeb/getList")
	@ApiOperation(value = "获取扭蛋列表信息", notes = "获取扭蛋列表信息", httpMethod = "POST", response = GetInfosRespVO.class)
	@LogHead("获取扭蛋列表信息")
	public BaseRespVO getList(BaseReqVO reqVO) {
		GetInfosRespVO respVO = new GetInfosRespVO();
		List<TwistedEggInfo> twistedEggList = eventTwistedEgg20180305Service.getList(reqVO.getUid());
		respVO.setTwistedEggList(twistedEggList);
		respVO.setCurrAmt(eventTwistedEgg20180305Service.getDayRechargeAmt(reqVO.getUid()));
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/twistedEggWeb/receive")
	@ApiOperation(value = "领取任务扭蛋", notes = "领取任务扭蛋", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("领取任务扭蛋")
	public BaseRespVO receive(ReceiveReqVO reqVO) {
		return eventTwistedEgg20180305Service.receive(reqVO.getUid(), reqVO.getTeggId());
	}
	
	// 活动结束后7天改为已过期
	@ApiHandle
	@Path("api/twistedEggWeb/twistedEggUpdateStatusTask")
	public BaseRespVO receive() {
		eventTwistedEgg20180305Service.twistedEggUpdateStatusTask();
		return new BaseRespVO();
	}
	
}
