package com.imlianai.dollpub.app.schedule;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.dollpub.domain.doll.user.UserDollWeekCount;
import com.imlianai.dollpub.domain.msg.Msg;
import com.imlianai.dollpub.domain.msg.MsgType;
import com.imlianai.dollpub.domain.trade.TradeCostType;
import com.imlianai.dollpub.domain.trade.TradeRecord;
import com.imlianai.dollpub.domain.trade.TradeType;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;

/**
 * 每周奖励任务
 * @author cls
 *
 */
@Component
public class HandleWeekRewardTask {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(HandleWeekRewardTask.class);
	
	private static final int RANK_SIZE = 20; // 每周前20名有奖励
	
	@Resource
	UserDollService userDollService;
	@Resource
	MsgService msgService;
	@Resource
	TradeService tradeService;

	public void handleReward() {
		try {
			int code = Integer.parseInt(DateUtils.dateToString(DateUtils.addDate(new Date(), -7), "yyyyMMdd")); // 上个星期一的日期
			//code = 20171204; // test
			LOG.info("handleReward-code:" + code);
			List<UserDollWeekCount> userDollCounts = userDollService.getUserDollWeekCountList(code, RANK_SIZE, null);
			LOG.info("handleReward-userDollCounts:" + userDollCounts);
			if(!StringUtil.isNullOrEmpty(userDollCounts)) {
				int rank = 1; // 排名
				int coin = 0; // 奖励的游戏币
				for(UserDollWeekCount weekCount : userDollCounts) {
					if(rank == 1) {
						coin = 1000;
					} else if(rank >= 2 && rank <= 10) {
						coin = 500;
					} else {
						coin = 100;
					}
					TradeRecord tradeRecord = new TradeRecord(weekCount.getUid(), weekCount.getUid(), TradeType.WEEKLY_REWARD.type, 0, 
							coin, TradeCostType.COST_COIN.type, code + "周榜获得第" + rank + "名，奖励游戏币" + coin);
					boolean result = tradeService.charge(tradeRecord);
					if(result) {
						Msg msg = new Msg(weekCount.getUid(), MsgType.NOTICE_SYS.type,
								"恭喜小主获得周榜第" + rank + "名，" + coin + "游戏币奖励已发放~~");
						msgService.sendMsg(msg);
						rank++;
					}
				}
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}
}
