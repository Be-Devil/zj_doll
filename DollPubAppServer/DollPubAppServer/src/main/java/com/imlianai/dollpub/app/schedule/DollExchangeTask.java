package com.imlianai.dollpub.app.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.core.doll.info.DollInfoService;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.app.modules.support.exchange.service.DollExchangeSevice;
import com.imlianai.dollpub.app.modules.support.pack.constants.PackConstants;
import com.imlianai.dollpub.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.dollpub.domain.doll.user.UserDoll;
import com.imlianai.dollpub.domain.msg.Msg;
import com.imlianai.dollpub.domain.msg.MsgType;
import com.imlianai.dollpub.domain.trade.TradeCostType;
import com.imlianai.dollpub.domain.trade.TradeRecord;
import com.imlianai.dollpub.domain.trade.TradeType;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;

/**
 * 娃娃自动兑换成游戏币任务
 * 
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

	public void handleExchange() {
		try {
			List<UserDoll> userDolls = userDollService.getExchangeUserDollList(-(PackConstants.SAVE_MAX_DAYS - 1)); // 获取可自动兑换的娃娃
			LOG.info("handleExchange:userDolls" + JSON.toJSONString(userDolls));
			if (userDolls != null && userDolls.size() > 0) {
				for (UserDoll userDoll : userDolls) {
					DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
					if (userDollService.updateUserDollStatus(userDoll.getId(), 3) > 0) {
						dollExchangeSevice.saveExchangeRecord(userDoll.getId(), dollInfo.getCoin());
						TradeRecord tradeRecord = new TradeRecord(userDoll.getUid(), userDoll.getUid(),
								TradeType.EXCHANGE_RETURN.type, 0, dollInfo.getCoin(), TradeCostType.COST_COIN.type,
								"自动兑换" + dollInfo.getCoin() + "个游戏币");
						tradeService.charge(tradeRecord);
						// 系统通知
						Msg msg = new Msg(userDoll.getUid() , MsgType.NOTICE_SYS.type,
						"逾期未申请发货，" + dollInfo.getName() +"自动兑换成" + dollInfo.getCoin() + "币。");
						msgService.sendMsg(msg);
					}
				}
			}
		} catch (Exception e) {
			PrintException.printException(LOG, e);
		}
	}
}
