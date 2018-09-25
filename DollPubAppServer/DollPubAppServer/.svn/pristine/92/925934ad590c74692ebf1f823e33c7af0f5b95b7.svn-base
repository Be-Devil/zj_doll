package com.imlianai.dollpub.app.modules.support.test.cmd;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.schedule.DollExchangeTask;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;

@LogHead("定时器测试")
@Component("scheduleTest")
public class CmdScheduleTest extends RootCmd{
	
	@Resource
	DollExchangeTask dollExchangeTask;
	
	@ApiHandle
	@Path("api/scheduleTest/dollExchangeTask")
	@LogHead("娃娃自动兑换成游戏币任务")
	public BaseRespVO dollExchangeTask() {
		dollExchangeTask.handleExchange();
		return new BaseRespVO();
	}
	
}
