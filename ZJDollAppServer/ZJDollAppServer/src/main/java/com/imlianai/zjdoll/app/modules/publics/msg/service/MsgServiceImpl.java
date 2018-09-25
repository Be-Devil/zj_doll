package com.imlianai.zjdoll.app.modules.publics.msg.service;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.imlianai.supply.rabbitMq.domain.RabbitMqPackage;
import com.imlianai.supply.rabbitMq.iface.RabbitMqFroZJDollRemoteService;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollListService;
import com.imlianai.zjdoll.app.modules.publics.msg.dao.MsgDao;
import com.imlianai.zjdoll.app.modules.publics.msg.leancloud.IMMsgService;
import com.imlianai.zjdoll.app.modules.publics.umeng.service.IMPushService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgRoom;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class MsgServiceImpl implements MsgService {

	private static final Logger logger = Logger.getLogger(MsgServiceImpl.class);

	@Resource
	IMMsgService iMMsgService;
	@Resource
	DollListService dollListService;
	@Resource
	MsgDao msgDao;
	@Resource
	IMPushService iMPushService;
	@Reference
	RabbitMqFroZJDollRemoteService rabbitMqRemoteService;

	@Override
	public void sendMsg(Msg msg) {
		RabbitMqPackage rabbitMqPackage=new RabbitMqPackage();
		rabbitMqPackage.setExchangeName("zj-dollapp-exchange");
		rabbitMqPackage.setRoutingKey("privatemsg");
		rabbitMqPackage.setMessage(msg);
		rabbitMqRemoteService.sendMsgToRabbit(rabbitMqPackage);
//		iMMsgService.sendMsg(msg);
	}

	@Override
	public void pushUMengMsg(Msg msg) {
		RabbitMqPackage rabbitMqPackage=new RabbitMqPackage();
		rabbitMqPackage.setExchangeName("zj-dollapp-exchange");
		rabbitMqPackage.setRoutingKey("noticemessage");
		rabbitMqPackage.setMessage(msg);
		rabbitMqRemoteService.sendMsgToRabbit(rabbitMqPackage);
//		iMPushService.sendPushNotice(msg);
	}

	@Override
	public void pushUMengSysMsg(Msg msg,int osType) {
		iMPushService.sendSysMsg(msg, osType);
	}
	
	@Override
	public void sendMsgRoom(MsgRoom msgRoom) {
		RabbitMqPackage rabbitMqPackage=new RabbitMqPackage();
		rabbitMqPackage.setExchangeName("zj-dollapp-exchange");
		rabbitMqPackage.setRoutingKey("roommsg");
		rabbitMqPackage.setMessage(msgRoom);
		rabbitMqRemoteService.sendMsgToRabbit(rabbitMqPackage);
//		iMMsgService.sendRoomMsg(msgRoom);
	}

	@Override
	public void sendMsgRoomAll(MsgRoom msgRoom) {
		List<DollBus> list = dollListService.getBusList(null);
		if (!StringUtil.isNullOrEmpty(list)) {
			for (DollBus dollBus : list) {
				if (dollBus != null) {
					msgRoom.setConversationId(dollBus.getConversationId());
					msgRoom.setBusId(dollBus.getBusId());
					RabbitMqPackage rabbitMqPackage=new RabbitMqPackage();
					rabbitMqPackage.setExchangeName("zj-dollapp-exchange");
					rabbitMqPackage.setRoutingKey("roommsg");
					rabbitMqPackage.setMessage(msgRoom);
					rabbitMqRemoteService.sendMsgToRabbit(rabbitMqPackage);
//					iMMsgService.sendRoomMsg(msgRoom);
				}
			}
		}

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
	public void sendOnlineSysMsg(Msg msg) {//发送online系统消息
		sendSysMsg(msg);//暂时全是offline的系统消息
	}

	@Override
	public void sendSysMsg(Msg msg) {//发送offline系统消息
		RabbitMqPackage rabbitMqPackage=new RabbitMqPackage();
		rabbitMqPackage.setExchangeName("zj-dollapp-exchange");
		rabbitMqPackage.setRoutingKey("sysmsg");
		rabbitMqPackage.setMessage(msg);
		rabbitMqRemoteService.sendMsgToRabbit(rabbitMqPackage);
//		iMMsgService.sendSysMsg(msg);
	}

	@Override
	public void sendOnlineSysMsgByRoom(Msg msg) {
		RabbitMqPackage rabbitMqPackage=new RabbitMqPackage();
		rabbitMqPackage.setExchangeName("zj-dollapp-exchange");
		rabbitMqPackage.setRoutingKey("msgbyroom");
		rabbitMqPackage.setMessage(msg);
		rabbitMqRemoteService.sendMsgToRabbit(rabbitMqPackage);
//		List<DollBus> list = dollListService.getBusList();
//		if (!StringUtil.isNullOrEmpty(list)) {
//			for (DollBus dollBus : list) {
//				iMMsgService.sendRoomSysMsg(msg, dollBus.getConversationId());
//			}
//		}
	}

	@Override
	public void addPushNum(long uid, long pushId) {
		msgDao.addPushNum(uid, pushId);
	}

}
