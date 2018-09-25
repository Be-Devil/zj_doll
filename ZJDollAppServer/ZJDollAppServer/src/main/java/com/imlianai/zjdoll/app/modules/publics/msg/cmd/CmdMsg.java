package com.imlianai.zjdoll.app.modules.publics.msg.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollSuccessRes;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.publics.msg.vo.MsgJumpDemo;
import com.imlianai.zjdoll.app.modules.publics.msg.vo.MsgRoomDemo;
import com.imlianai.zjdoll.app.modules.publics.msg.vo.MsgSysDemo;

@Api("消息结构")
public class CmdMsg extends RootCmd {

	@Resource
	private MsgService msgService;

	@Path("Msg/普通系统消息")
	@ApiOperation(value = "【1.0.0】系统消息结构", httpMethod = "POST", response = MsgSysDemo.class)
	public MsgSysDemo getMsg() {
		return null;
	}

	@Path("MsgRoom/普通房间消息")
	@ApiOperation(value = "【1.0.0】房间消息结构", httpMethod = "POST", response = MsgRoomDemo.class)
	public MsgRoomDemo getMsgRoom() {
		return null;
	}

	@Path("MsgRoom success /普通房间消息--捉取娃娃成功")
	@ApiOperation(value = "【1.0.0】房间消息结构--捉取娃娃成功",notes="lctext.body.data对象", httpMethod = "POST", response = DollSuccessRes.class)
	public MsgRoomDemo getMsgRoomSuccess() {
		return null;
	}
	

	@Path("MsgJump/带跳转的系统消息")
	@ApiOperation(value = "【1.0.0】带跳转的系统消息结构", httpMethod = "POST", response = MsgJumpDemo.class)
	public MsgRoomDemo getMsgJump() {
		return null;
	}

}
