package com.imlianai.zjdoll.app.modules.core.doll.cmd;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.constants.JumpTarget;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgRoomJump;
import com.imlianai.zjdoll.domain.msg.MsgRoomType;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.robot.service.DollRobotService;
import com.imlianai.zjdoll.app.modules.core.doll.utils.DollUtil;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.schedule.DollShippingTask;

@Component("busRobot")
public class CmdBusRobot extends RootCmd {

	@Resource
	DollRobotService dollRobotService;
	@Resource
	UserService userService;
	@Resource
	MsgService msgService;
	@Resource
	DollShippingTask dollShippingTask;
	@Resource
	TradeService tradeService;

	@Override
	public String doCommand(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String code = request.getParameter("code");
		if (code.equals("initRobot")) {
			dollRobotService.initRobotUser();
		} else if (code.equals("msg")) {
			String uid = request.getParameter("uid");
			UserGeneral userGeneral = userService.getUserGeneral(Long
					.parseLong(uid));
			String msgString = DollUtil.getAppSuccessMsg(userGeneral.getName(),
					"特殊娃娃",1, 2);
			Msg msg = new Msg(userGeneral, 0, MsgType.GAIN_SUCCESS.type,
					msgString);
			msgService.sendOnlineSysMsgByRoom(msg);
		} else if (code.equals("shipping")) {
			dollShippingTask.handleDollShipping();
		}else if (code.equals("decode")) {
			String shipping_name = request.getParameter("shipping_name");
			logger.info("CmdBusRobot shipping_name:"+shipping_name);
			logger.info("CmdBusRobot shipping_name:"+URLEncoder.encode(shipping_name));
			String isoshipping_name = new String(shipping_name.getBytes("iso-8859-1"),"UTF-8");
			logger.info("CmdBusRobot iso shipping_name:"+isoshipping_name);
			logger.info("CmdBusRobot iso shipping_name:"+URLDecoder.decode(isoshipping_name,"UTF-8"));
			if (!StringUtil.isNullOrEmpty(shipping_name)) {
				shipping_name = URLDecoder.decode(shipping_name,"UTF-8");
			}
			logger.info("CmdBusRobot shipping_name:"+shipping_name);
		} else if (code.equals("tradeDealCharge")) {
			if (request.getParameter("sign").equals("nmasdjaoibb12319")) {
				String uid = request.getParameter("uid");
				String type = request.getParameter("type");
				String tradeCode = request.getParameter("tradeCode");
				String cost = request.getParameter("cost");
				String costType = request.getParameter("costType");
				String remark = request.getParameter("remark");
				if (remark!=null) {
					remark = new String(remark.getBytes("iso-8859-1"),"UTF-8");
				}
				TradeRecord record = new TradeRecord(Long.parseLong(uid),
						Long.parseLong(uid), Integer.parseInt(type),
						Integer.parseInt(tradeCode), Integer.parseInt(cost),
						Integer.parseInt(costType), remark);
				tradeService.charge(record);
			}
		} else if (code.equals("tradeDealConsume")) {
			if (request.getParameter("sign").equals("qez4893xnhsqesdjaapiui9")) {
				String uid = request.getParameter("uid");
				String type = request.getParameter("type");
				String tradeCode = request.getParameter("tradeCode");
				String cost = request.getParameter("cost");
				String costType = request.getParameter("costType");
				String remark = request.getParameter("remark");
				if (remark!=null) {
					remark = new String(remark.getBytes("iso-8859-1"),"UTF-8");
				}
				TradeRecord record = new TradeRecord(Long.parseLong(uid),
						Long.parseLong(uid), Integer.parseInt(type),
						Integer.parseInt(tradeCode), Integer.parseInt(cost),
						Integer.parseInt(costType), remark);
				tradeService.consume(record);
			}
		}else if (code.equals("testAllMsg")) {
			if (request.getParameter("sign").equals("qez4893xnhsqesdjaapiui9")) {
				MsgRoomJump msgRoomSys = new MsgRoomJump( MsgRoomType.BUS_SYS.type, "<font color='#FF7B7B'>这条跳充值</font>",null);
				msgRoomSys.setTarget(JumpTarget.CHARGE.target);
				msgService.sendMsgRoomAll(msgRoomSys);
				
				
				msgRoomSys = new MsgRoomJump( MsgRoomType.BUS_SYS.type, "<font color='#FF7B7B'>这条跳用户资料页</font>",null);
				msgRoomSys.setTarget(JumpTarget.USER.target);
				msgRoomSys.setTargetId(1889013);
				msgService.sendMsgRoomAll(msgRoomSys);
				msgRoomSys = new MsgRoomJump( MsgRoomType.BUS_SYS.type, "<font color='#FF7B7B'>这条跳娃娃资料页(背包里的)</font>",null);
				msgRoomSys.setTarget(JumpTarget.DOLL.target);
				msgService.sendMsgRoomAll(msgRoomSys);
				msgRoomSys = new MsgRoomJump( MsgRoomType.BUS_SYS.type, "<font color='#FF7B7B'>这条跳娃娃机房间</font>",null);
				msgRoomSys.setTarget(JumpTarget.BUS.target);
				msgRoomSys.setTargetId(32);
				msgService.sendMsgRoomAll(msgRoomSys);
				msgRoomSys = new MsgRoomJump( MsgRoomType.BUS_SYS.type, "<font color='#FF7B7B'>这条跳邀请奖励</font>",null);
				msgRoomSys.setTarget(JumpTarget.INVITE.target);
				msgService.sendMsgRoomAll(msgRoomSys);
				msgRoomSys = new MsgRoomJump( MsgRoomType.BUS_SYS.type, "<font color='#FF7B7B'>这条跳输入邀请码</font>",null);
				msgRoomSys.setTarget(JumpTarget.INVITE_CODE.target);
				msgService.sendMsgRoomAll(msgRoomSys);
				msgRoomSys = new MsgRoomJump( MsgRoomType.BUS_SYS.type, "<font color='#FF7B7B'>这条跳我的红包</font>",null);
				msgRoomSys.setTarget(JumpTarget.REDPACKET.target);
				msgService.sendMsgRoomAll(msgRoomSys);
				msgRoomSys = new MsgRoomJump( MsgRoomType.BUS_SYS.type, "<font color='#FF7B7B'>这条跳我的红包提现</font>",null);
				msgRoomSys.setTarget(JumpTarget.REDPACKET_WITHDRAW.target);
				msgService.sendMsgRoomAll(msgRoomSys);
				msgRoomSys = new MsgRoomJump( MsgRoomType.BUS_SYS.type, "<font color='#FF7B7B'>这条跳积分商城</font>",null);
				msgRoomSys.setJumpUrl("http://t.xiehou360.com/DollAppServer/event/shop/index.html");
				msgRoomSys.setTargetTitle("积分商城");
				msgService.sendMsgRoomAll(msgRoomSys);
			}
		}
		return responseJson(response, "ok", "响应请求");
	}

	public static void main(String[] args) {
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("live", 1);
		HttpUtil.Post(
				"http://122.11.48.251:6639/DollAppServer/api/qiyiguo/back",
				JSON.toJSONString(postMap));
	}
}
