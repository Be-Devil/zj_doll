package com.imlianai.zjdoll.app.modules.support.event.newyearRecharge20180207.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.zjdoll.app.controller.WebCmd;
import com.imlianai.zjdoll.app.modules.support.event.newyearRecharge20180207.service.EventNewyearRecharge20180207Service;
import com.imlianai.zjdoll.app.modules.support.event.newyearRecharge20180207.vo.AddValueReqVO;
import com.imlianai.zjdoll.app.modules.support.event.newyearRecharge20180207.vo.GetRankingRespVO;
import com.imlianai.zjdoll.app.modules.support.event.newyearRecharge20180207.vo.GetStatusRespVO;

@Api("新春充值活动")
@LogHead("新春充值活动")
@Component("newyearRechargeWeb")
public class CmdNewyearRechargeWeb extends WebCmd{
	
	@Resource
	EventNewyearRecharge20180207Service eventNewyearRecharge20180207Service;

	@ApiHandle
	@Path("api/newyearRecharge/getStatus")
	@ApiOperation(value = "获取新春充值活动状态信息", notes = "获取新春充值活动状态信息", httpMethod = "POST", response = GetStatusRespVO.class)
	@LogHead("获取新春充值活动状态信息")
	public BaseRespVO getStatus(BaseReqVO reqVO) {
		return eventNewyearRecharge20180207Service.getStatus();
	}
	
	@ApiHandle
	@Path("api/newyearRecharge/getRanking")
	@ApiOperation(value = "获取新春充值活动榜单", notes = "获取新春充值活动榜单", httpMethod = "POST", response = GetRankingRespVO.class)
	@LogHead("获取新春充值活动榜单")
	public BaseRespVO getRanking(BaseReqVO reqVO) {
		return eventNewyearRecharge20180207Service.getRanking(reqVO.getUid());
	}
	
	@ApiHandle
	@Path("api/newyearRecharge/addValue")
	@LogHead("给用户添加福气值")
	public BaseRespVO addValue(AddValueReqVO reqVO) {
		if(reqVO.getValue() <= 0) {
			return new BaseRespVO(-1, false, "请输入有效的福气值");
		}
		return eventNewyearRecharge20180207Service.addValue(reqVO.getUid(), reqVO.getValue());
	}
	
}
