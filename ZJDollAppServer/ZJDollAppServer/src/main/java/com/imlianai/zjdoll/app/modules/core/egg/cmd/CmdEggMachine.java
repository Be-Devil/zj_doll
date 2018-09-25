package com.imlianai.zjdoll.app.modules.core.egg.cmd;


import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.egg.service.EggMachineService;
import com.imlianai.zjdoll.app.modules.core.egg.vo.*;
import com.imlianai.zjdoll.domain.egg.EggMacClassifyInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import java.util.List;

@Component("eggMachine")
@Api("扭蛋机相关")
public class CmdEggMachine extends RootCmd {
	
	@Resource
	EggMachineService eggMachineService;

	@ApiHandle
	@LogHead("扭蛋机分类清单")
	@Path("api/eggMachine/classify")
	@ApiOperation(value = "【1.4.0】扭蛋机分类清单", notes = "扭蛋机分类清单", httpMethod = "POST", response = EggMacClassifyRespVO.class)
	public BaseRespVO classify(BaseReqVO reqVO) {
		EggMacClassifyRespVO respVO = new EggMacClassifyRespVO();
		List<EggMacClassifyInfo> classifyList = eggMachineService.getEggMacClassifies();
		
		if (!StringUtil.isNullOrEmpty(classifyList)) {
			respVO.setClassifyList(classifyList);
		}
		return respVO;
	}
	
	@ApiHandle
	@LogHead("扭蛋机列表")
	@Path("api/eggMachine/list")
	@ApiOperation( value = "【1.4.0】获取扭蛋机列表", notes = "获取扭蛋机列表", httpMethod = "POST", response = EggMacListRespVO.class)
	public BaseRespVO list(EggMacListReqVO reqVO) {
		EggMacListRespVO respVO = new EggMacListRespVO();
		respVO.setEggMachineList(eggMachineService.getEggMachines(reqVO.getType()));
		return respVO;
	}
	
	@ApiHandle
	@LogHead("获取扭蛋机信息")
	@Path("api/eggMachine/getEggMachineInfo")
	@ApiOperation(value = "【1.4.0】获取扭蛋机信息", notes = "获取扭蛋机信息", httpMethod = "POST", response = GetInfoRespVO.class)
	public BaseRespVO getEggMachineInfo(GetInfoReqVO reqVO) {
		GetInfoRespVO respVO = new GetInfoRespVO();
		respVO.setEggMachineInfo(eggMachineService.getEggMachineInfo(reqVO.getMachineId(), reqVO.getUid()));
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@LogHead("获取用户账户信息")
	@Path("api/eggMachine/getAccountInfo")
	@ApiOperation(value = "【1.4.0】获取用户账户信息", notes = "获取用户账户信息", httpMethod = "POST", response = GetAccountInfoRespVO.class)
	public BaseRespVO getAccountInfo(BaseReqVO reqVO) {
		GetAccountInfoRespVO respVO = new GetAccountInfoRespVO();
		respVO.setAccountInfo(eggMachineService.getAccountInfo(reqVO.getUid()));
		return respVO;
	}
	
	@ApiHandle
//	@LoginCheck
	@LogHead("用户上机")
	@Path("api/eggMachine/play")
	@ApiOperation(value = "【1.4.0】用户上机", notes = "用户上机", httpMethod = "POST", response = PlayRespVO.class)
	public BaseRespVO play(PlayReqVO reqVO) {
		return eggMachineService.play(reqVO);
	}
	
	@ApiHandle
	@LogHead("获取扭蛋详情")
	@Path("api/eggMachine/getEggDetails")
	@ApiOperation(value = "【1.4.0】获取扭蛋详情", notes = "获取扭蛋详情", httpMethod = "POST", response = GetEggDetailsRespVO.class)
	public BaseRespVO getEggDetails(GetInfoReqVO reqVO) {
		GetEggDetailsRespVO respVO = new GetEggDetailsRespVO();
		respVO.setEggInfos(eggMachineService.getEggDetails(reqVO.getMachineId()));
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@LogHead("用户领取奖品")
	@Path("api/eggMachine/getRewards")
	@ApiOperation(value = "【1.4.0】用户领取奖品", notes = "用户领取奖品", httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO getRewards(GetRewardsReqVO reqVO) {
		return eggMachineService.getRewards(reqVO.getOptId());
	}
	
	@ApiHandle
	@LoginCheck
	@LogHead("获取充值列表")
	@Path("api/eggMachine/getRechargeList")
	@ApiOperation(value = "【1.4.0】获取充值列表", notes = "获取充值列表", httpMethod = "POST", response = GetRechargeListRespVO.class)
	public BaseRespVO getRechargeList(BaseReqVO reqVO) {
		return eggMachineService.getRechargeList(reqVO.getUid());
	}
}
