package com.imlianai.zjdoll.app.modules.core.trade.record;

import java.util.List;

import com.imlianai.zjdoll.domain.trade.TradeIncomeRecord;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.rpc.support.common.exception.TradeOperationException;

/**
 * 交易记录相关
 * @author Max
 *
 */
public interface TradeRecordDAO {

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
	 * @param limit
	 * @return
	 */
	public List<TradeRecord> getRecords(long uid, int page, int limit);
	/**
	 * 获取指定类型交易记录列表
	 * @param uid
	 * @param type
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<TradeRecord> getRecords(long uid, int type, int page, int limit);
	
	/**
	 * 增加收入记录
	 * @param uid
	 * @param cost
	 * @return
	 */
	public int addIncomeRecord(long uid, int cost);
	/**
	 * 获取收入记录列表
	 * @param uid
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<TradeIncomeRecord> getIncomeRecords(long uid, int page, int limit);
	
	/**
	 * 获取指定类型交易总金额
	 * @param uid
	 * @param type
	 * @param costType
	 * @return
	 */
	public int getRecordsAmt(long uid, int type,int costType);
}
