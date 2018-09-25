package com.imlianai.zjdoll.app.modules.support.event.lanternFestival20180227.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.support.exchange.service.DollComposeService;
import com.imlianai.zjdoll.app.modules.support.exchange.service.DollExchangeSevice;

@Service
public class EventLanternFestivalServiceImpl implements EventLanternFestivalService{
	
	private static final BaseLogger LOG = BaseLogger.getLogger(EventLanternFestivalServiceImpl.class);

	@Resource
	TradeService tradeService;
	@Resource
	DollComposeService dollComposeService;
	@Resource
	DollExchangeSevice dollExchangeSevice;
	
	@Override
	public void handleCatchDoll(int status, long uDollId, long uid, DollInfo doll) {
		try {
			if(status == 3) {
				dollExchangeSevice.saveExchangeRecord(uDollId, doll.getCoin());
				TradeRecord tradeRecord = new TradeRecord(uid, uid,
						TradeType.EXCHANGE_RETURN.type, 0, doll.getCoin(),
						TradeCostType.COST_COIN.type, "兑换" + doll.getCoin() + "个游戏币");
				tradeService.charge(tradeRecord);
			} else if(status == 5) {
				dollComposeService.saveRecycleRecord(uDollId, doll.getReturnJewel());
				TradeRecord tradeRecord = new TradeRecord(uid, uid,
						TradeType.RECYCLE_RETURN.type, 0, doll.getReturnJewel(),
						TradeCostType.COST_JEWEL.type, "回收" + doll.getReturnJewel() + "钻");
				tradeService.charge(tradeRecord);
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}
}
