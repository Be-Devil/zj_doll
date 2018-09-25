package com.imlianai.dollpub.app.modules.support.exchange.cmd;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.support.exchange.service.DollComposeService;
import com.imlianai.dollpub.app.modules.support.exchange.vo.ComposeGetListRespVO;
import com.imlianai.dollpub.app.modules.support.exchange.vo.ComposeReqVO;
import com.imlianai.dollpub.app.modules.support.exchange.vo.GetDollDetailReqVO;
import com.imlianai.dollpub.app.modules.support.exchange.vo.GetRecentComListRespVO;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("娃娃合成")
@LogHead("娃娃合成")
@Component("dollCompose")
public class CmdDollCompose extends RootCmd{
	
	@Resource
	DollComposeService dollComposeService;

	@ApiHandle
	@Path("api/dollCompose/getList")
	@ApiOperation(value = "获取可合成的娃娃列表", notes = "获取可合成的娃娃列表", httpMethod = "POST", response = ComposeGetListRespVO.class)
	@LogHead("获取可合成的娃娃列表")
	public BaseRespVO getList(BaseReqVO reqVO) {
		return dollComposeService.getList(reqVO.getUid());
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/dollCompose/compose")
	@ApiOperation(value = "娃娃合成", notes = "娃娃合成", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("娃娃合成")
	public BaseRespVO compose(ComposeReqVO reqVO) {
		return dollComposeService.compose(reqVO.getUid(), reqVO.getDollId());
	}
	
	@ApiHandle
	@Path("api/dollCompose/getDollDetail")
	@ApiOperation(value = "娃娃详情信息", notes = "娃娃详情信息", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("娃娃详情信息")
	public BaseRespVO getDollDetail(GetDollDetailReqVO reqVO) {
		return dollComposeService.getDollDetail(reqVO.getType(), reqVO.getId());
	}
	
	@ApiHandle
	@Path("api/dollCompose/getRecentComList")
	@ApiOperation(value = "获取最近合成的娃娃列表", notes = "获取最近合成的娃娃列表", httpMethod = "POST", response = GetRecentComListRespVO.class)
	@LogHead("获取最近合成的娃娃列表")
	public BaseRespVO getRecentComList(BaseReqVO reqVO) {
		GetRecentComListRespVO respVO = new GetRecentComListRespVO();
		respVO.setDollInfos(dollComposeService.getRecentComList());
		return respVO;
	}
}
