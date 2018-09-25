package com.imlianai.dollpub.app.modules.publics.msg.cmd;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.controller.WebCmd;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.doll.service.DollService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.app.modules.publics.msg.vo.MsgSysDemo;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.doll.DollOptRecord;
import com.imlianai.dollpub.domain.msg.MsgRoom;
import com.imlianai.dollpub.domain.msg.MsgRoomType;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;

@Component("msgTest")
public class CmdMsgTest extends WebCmd {

	@Resource
	private MsgService msgService;
	@Resource
	DollService dollService;
	@Resource
	DollBusService dollBusService;
	@Resource
	UserService userService;
	@ApiHandle
	public MsgSysDemo sendSuccessMsg(int busId,long uid) {
		DollBus bus=dollBusService.getDollBus(busId);
		DollOptRecord record=new DollOptRecord(uid, busId, 0, 0, 0);
		dollService.sendSuccessMsg(bus, record, 0);
		return null;
	}
	@ApiHandle
	public MsgSysDemo sendFailMsg(int busId,long uid) {
		DollBus bus=dollBusService.getDollBus(busId);
		DollOptRecord record=new DollOptRecord(uid, busId, 0, 0, 0);
		record.setStartTime(new Date());
		dollService.sendFailMsg(bus, record);
		return null;
	}

	@ApiHandle
	public MsgSysDemo sendApplyMsg(int busId,long uid) {
		DollBus dollBus=dollBusService.getDollBus(busId);
		UserGeneral user = userService.getUserGeneral(uid);
		MsgRoom msgRoom = new MsgRoom(dollBus, user,
				MsgRoomType.START_PLAY.type, user.getName() + " 成功上机了");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("remainSecond", 30);
		msgRoom.setData(JSON.toJSONString(dataMap));
		msgService.sendMsgRoom(msgRoom);
		return null;
	}

}
