package com.imlianai.zjdoll.app.modules.core.trade.record;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeIncomeRecord;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeRecordRes;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class TradeRecordServiceImpl implements TradeRecordService {

	@Resource
	private TradeRecordDAO tradeRecordDAO;
	
	@Override
	public long addRecord(TradeRecord record) throws TradeOperationException {
		long recordId = tradeRecordDAO.addRecord(record);
		if(recordId > 0){
			if(record.getRecordType()==1 && record.getCostType()==TradeCostType.COST_COIN.type)
				tradeRecordDAO.addIncomeRecord(record.getUid(), record.getCost());
		}
		return recordId;
	}

	@Override
	public List<TradeRecordRes> getRecords(long uid, int page) {
		List<TradeRecordRes> resList=new ArrayList<TradeRecordRes>();
		List<TradeRecord> list=tradeRecordDAO.getRecords(uid, page, 20);
		if (!StringUtil.isNullOrEmpty(list)) {
			for (TradeRecord tradeRecord : list) {
				resList.add(new TradeRecordRes(tradeRecord));
			}
		}
		return resList;
	}

	@Override
	public List<TradeRecord> getRecords(long uid, int type, int page) {
		return tradeRecordDAO.getRecords(uid, type, page, 20);
	}
	
	@Override
	public List<TradeIncomeRecord> getIncomeRecords(long uid, int page) {
		return tradeRecordDAO.getIncomeRecords(uid, page, 20);
	}

	@Override
	public int getRecordsAmt(long uid, int type,int costType) {
		return tradeRecordDAO.getRecordsAmt(uid, type, costType);
	}
	
}
