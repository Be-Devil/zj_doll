package com.imlianai.dollpub.app.modules.core.trade.record;

import java.util.List;

import com.imlianai.dollpub.domain.trade.TradeIncomeRecord;
import com.imlianai.dollpub.domain.trade.TradeRecord;
import com.imlianai.dollpub.domain.trade.TradeRecordRes;
import com.imlianai.rpc.support.common.exception.TradeOperationException;

/**
 * 交易记录处理类
 * @author Max
 *
 */
public interface TradeRecordService {

	/**
	 * 增加交易记录
	 * @param record
	 * @return
	 * @throws TradeOperationException
	 */
	public long addRecord(TradeRecord record) throws TradeOperationException;;
	/**
	 * 获取交易记录列表
	 * @param uid
	 * @param page
	 * @return
	 */
	public List<TradeRecordRes> getRecords(long uid, int page);
	/**
	 * 获取指定类型交易记录列表
	 * @param uid
	 * @param type
	 * @param page
	 * @return
	 */
	public List<TradeRecord> getRecords(long uid, int type, int page);
	/**
	 * 获取收入记录列表
	 * @param uid
	 * @param page
	 * @return
	 */
	public List<TradeIncomeRecord> getIncomeRecords(long uid, int page);
	
	/**
	 * 获取指定类型交易总金额
	 * @param uid
	 * @param type
	 * @param costType
	 * @return
	 */
	public int getRecordsAmt(long uid, int type,int costType);
}
