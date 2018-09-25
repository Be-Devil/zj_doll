package com.imlianai.zjdoll.app.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.exchange.service.DollComposeService;
import com.imlianai.zjdoll.app.modules.support.exchange.service.DollExchangeSevice;
import com.imlianai.zjdoll.app.modules.support.pack.constants.PackConstants;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;

/**
 * 娃娃自动兑换成游戏币、自动回收成钻石任务
 * @author cls
 *
 */
@Component
public class DollExchangeTask {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(DollExchangeTask.class);
	
	@Resource
	UserDollService userDollService;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	DollExchangeSevice dollExchangeSevice;
	@Resource
	TradeService tradeService;
	@Resource
	MsgService msgService;
	@Resource
	DollComposeService dollComposeService;

	public void handleExchange() {
		try {
			List<UserDoll> userDolls = userDollService.getExchangeUserDollList(-(PackConstants.SAVE_MAX_DAYS-1)); // 获取可自动兑换的娃娃
			LOG.info("handleExchange:userDolls-" + JSON.toJSONString(userDolls));
			if(userDolls != null && userDolls.size() > 0) {
				for(UserDoll userDoll : userDolls) {
					DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
					userDollService.handleAutoExchange(userDoll, dollInfo);
				}
			}
			pushWillExchangeMsg(); // 推送即将自动兑换or即将自动回收的消息
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}

	private void pushWillExchangeMsg() {
		List<UserDoll> userDolls = userDollService.getExchangeUserDollList(-(PackConstants.SAVE_MAX_DAYS-2));
		if(!StringUtil.isNullOrEmpty(userDolls)) {
			for(UserDoll userDoll : userDolls) {
				DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
				String textString = "";
				if(userDoll.getType() == 0) {
					textString = "小主抓中的" + dollInfo.getName() + "已寄存14天啦，24小时内还不申请发货就会自动兑换成" + dollInfo.getCoin() +"币哟。";
				} else if(userDoll.getType() == 1 || userDoll.getType() == 2) {
					textString  = "小主背包中的" + dollInfo.getName() + "已寄存14天啦，24小时内还不申请发货就会自动回收为" + dollInfo.getReturnJewel() +"钻哟。";
				}
				if(!StringUtil.isNullOrEmpty(textString)) {
					// 系统通知
					MsgNotice msg = new MsgNotice(userDoll.getUid(), MsgType.NOTICE_SYS.type, textString);
					msg.setJumpDoll(userDoll.getId());
					msgService.sendMsg(msg);
				}
			}
		}
	}
}
