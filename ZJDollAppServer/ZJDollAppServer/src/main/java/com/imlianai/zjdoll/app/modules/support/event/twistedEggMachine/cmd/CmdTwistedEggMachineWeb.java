package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.cmd;

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
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.service.EventTwistedEggMachineService;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.GetInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.GetRecordRespVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.PlayReqVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.PlayRespVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.UserAwardInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("扭蛋机活动")
@LogHead("扭蛋机活动")
@Component("twistedEggMachineWeb")
public class CmdTwistedEggMachineWeb extends RootCmd {

	@Resource
	EventTwistedEggMachineService eventTwistedEggMachineService;

	@ApiHandle
	@Path("api/twistedEggMachineWeb/getInfo")
	@ApiOperation(value = "获取扭蛋机信息", notes = "获取扭蛋机信息", httpMethod = "POST", response = GetInfoRespVO.class)
	@LogHead("获取扭蛋机信息")
	public BaseRespVO getInfo(BaseReqVO reqVO) {
		return eventTwistedEggMachineService.getInfo(reqVO.getUid());
	}
	
	@ApiHandle
	@Path("api/twistedEggMachineWeb/getRecords")
	@ApiOperation(value = "获取扭蛋记录", notes = "获取扭蛋记录", httpMethod = "POST", response = GetRecordRespVO.class)
	@LogHead("获取扭蛋记录")
	public BaseRespVO getRecords(BaseReqVO reqVO) {
		GetRecordRespVO respVO = new GetRecordRespVO();
		List<UserAwardInfo> awardRecords = eventTwistedEggMachineService.getTwistedEggRecords(reqVO.getUid(), 0);
		respVO.setAwardRecords(awardRecords);
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/twistedEggMachineWeb/play")
	@ApiOperation(value = "抽奖", notes = "抽奖", httpMethod = "POST", response = PlayRespVO.class)
	@LogHead("抽奖")
	public BaseRespVO play(PlayReqVO reqVO) {
		return eventTwistedEggMachineService.play(reqVO);
	}
	
	// 每15分钟消息推送
	@ApiHandle
	@Path("api/twistedEggMachineWeb/twistedEggMachineEvery15MinsMsg")
	public BaseRespVO twistedEggMachineEvery15MinsMsg() {
		eventTwistedEggMachineService.twistedEggMachineEvery15MinsMsg();
		return new BaseRespVO();
	}
	
	// 手气王榜单消息推送
	@ApiHandle
	@Path("api/twistedEggMachineWeb/twistedEggMachineRankingMsg")
	public BaseRespVO twistedEggMachineRankingMsg() {
		eventTwistedEggMachineService.twistedEggMachineRankingMsg();
		return new BaseRespVO();
	}
	
}
