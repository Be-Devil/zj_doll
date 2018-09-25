package com.imlianai.dollpub.app.modules.core.api.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.api.service.CustomerDollBusService;
import com.imlianai.dollpub.app.modules.core.api.vo.DollBusListReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;

@Api("商户发货相关接口")
@Component("shipping1")
public class CmdShipping extends RootCmd {

	@Resource
	private CustomerDollBusService customerDollBusService;

	@ApiHandle
	@LogHead("获取商户娃娃机列表接口")
	@Path("api/shipping/order")
	@ApiOperation(value = "申请发货接口", notes = "申请发货", httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO order(DollBusListReqVO vo) {
		return null;
//		return customerDollBusService.getCustomerDollBusList(vo.getAppId(),
//				vo.getPage(), vo.getSize());
	}

}
