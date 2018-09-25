package com.imlianai.zjdoll.app.modules.publics.msg.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.publics.msg.vo.MsgPushStatReqVO;

@Api("消息相关")
@Component("msgHandle")
public class CmdMsgHandle extends RootCmd {

	@Resource
	MsgService msgService;

	@Path("/api/msgHandle/addPushNum")
	@ApiOperation(value = "【1.0.0】推送数量统计", httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO addPushNum(MsgPushStatReqVO msgPushStatReqVO) {
		msgService.addPushNum(msgPushStatReqVO.getUid(),
				msgPushStatReqVO.getPushId());
		return new BaseRespVO();
	}

}
