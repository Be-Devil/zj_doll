package com.imlianai.dollpub.app.modules.publics.msg.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.imlianai.dollpub.app.modules.core.doll.list.DollListService;
import com.imlianai.dollpub.app.modules.publics.msg.leancloud.IMMsgService;
import com.imlianai.dollpub.domain.msg.Msg;
import com.imlianai.dollpub.domain.msg.MsgRoom;
import com.imlianai.dollpub.message.iface.IMessageRemoteService;

@Service
public class MsgServiceImpl implements MsgService {

	private static final Logger logger = Logger.getLogger(MsgServiceImpl.class);

	@Resource
	IMMsgService iMMsgService;
	@Resource
	DollListService dollListService;

	@Reference
	IMessageRemoteService iMessageRemoteService;

	@Override
	public void sendMsg(Msg msg) {
		iMMsgService.sendMsg(msg);
	}

	@Override
	public void pushUMengMsg(Msg msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMsgRoom(MsgRoom msgRoom) {
		iMessageRemoteService.sendRoomMsg(msgRoom);
		iMMsgService.sendRoomMsg(msgRoom);
	}

	@Override
	public void sendMsgRoomAll(MsgRoom msgRoom) {
		iMessageRemoteService.sendAllRoomMsg(msgRoom);

	}

	@Override
	public void pushMediaMsgAll(Msg msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public int updateToken(long uid, String token, String channel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void sendOnlineSysMsg(Msg msg) {
		//iMMsgService.sendSysMsg(msg, true);
	}

	@Override
	public void sendSysMsg(Msg msg) {
		//iMMsgService.sendSysMsg(msg, false);
	}

}
