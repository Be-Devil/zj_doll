package com.imlianai.zjdoll.app.schedule;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.doll.user.UserDollWeekCount;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;

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
			//int code = 20180129; // test
			LOG.info("handleReward-code:" + code);
			List<UserDollWeekCount> userDollCounts = userDollService.getUserDollWeekCountList(code, RANK_SIZE);
			LOG.info("handleReward-userDollCounts:" + userDollCounts);
			if(!StringUtil.isNullOrEmpty(userDollCounts)) {
				int rank = 1; // 排名
				int coin = 0; // 奖励的游戏币
				for(UserDollWeekCount weekCount : userDollCounts) {
					if(rank == 1) {
						coin = 100;
					} else if(rank >= 2 && rank <= 5) {
						coin = 100;
					} else if(rank >= 6 && rank <= 10) {
						coin = 50;
					} else {
						coin = 50;
					}
					TradeRecord tradeRecord = new TradeRecord(weekCount.getUid(), weekCount.getUid(), TradeType.WEEKLY_REWARD.type, 0, 
							coin, TradeCostType.COST_COIN.type, code + "周榜获得第" + rank + "名");
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
