package com.imlianai.dollpub.app.modules.core.api.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Path;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.api.dto.OptRecordCallbackDTO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@Api("捉取结果回调")
public class CmdApiBack extends RootCmd {

	@Path("商户的回调地址")
	@ApiOperation(value = "捉取结果回调", notes = "捉取结果回调", httpMethod = "POST", response = OptRecordCallbackDTO.class)
	public BaseRespVO list() {
		return new BaseRespVO();
	}

}
