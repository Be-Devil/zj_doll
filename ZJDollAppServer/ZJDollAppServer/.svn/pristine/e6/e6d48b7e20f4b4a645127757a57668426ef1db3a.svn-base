package com.imlianai.zjdoll.app.modules.core.trade.record;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.trade.TradeIncomeRecord;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class TradeRecordDAOImpl implements TradeRecordDAO{
	
	private final BaseLogger logger = BaseLogger.getLogger(TradeRecordDAOImpl.class);
	
	@Resource
	private JdbcHandler jdbcHandler;
	
	private String addRecord = "insert into trade_record(uid,tid,type,recordType,code,cost,costType,remark,time,optId) values(?,?,?,?,?,?,?,?,now(),?) ";
	@Override
	public long addRecord(TradeRecord record) throws TradeOperationException {
		try {
			return jdbcHandler.executeSql(addRecord, record.getUid(), record.getTid(), record.getType(), record.getRecordType(),
					record.getCode(), record.getCost(), record.getCostType(),record.getRemark(),record.getOptId());
		} catch (Exception e) {
            logger.error("addRecord error:"+e);
            throw new TradeOperationException(e);
		}
	}

	private String getRecords = "select * from trade_record where uid=? order by id desc limit ?,? ";
	@Override
	public List<TradeRecord> getRecords(long uid, int page, int limit) {
		try {
			return jdbcHandler.queryBySql(TradeRecord.class, getRecords, uid, (page-1)*limit, limit);
		} catch (Exception e) {
            logger.error("getRecords error:"+e);
            return null;
		}
	}

	private String getRecordsType = "select * from trade_record where uid=? and type=? order by id desc limit ?,? ";
	@Override
	public List<TradeRecord> getRecords(long uid, int type, int page, int limit) {
		try {
			return jdbcHandler.queryBySql(TradeRecord.class, getRecordsType, uid, type, (page-1)*limit, limit);
		} catch (Exception e) {
            logger.error("getRecordsType error:"+e);
            return null;
		}
	}
	
	private String addIncomeRecord = "insert into trade_income_record(uid,cost,time) values(?,?,DATE_FORMAT(NOW(),'%Y%m')) " +
			" ON DUPLICATE KEY UPDATE cost=cost+? ";
	@Override
	public int addIncomeRecord(long uid, int cost) {
		try {
			return jdbcHandler.executeSql(addIncomeRecord, uid, cost, cost);
		} catch (Exception e) {
            logger.error("addIncomeRecord error:"+e);
            return 0;
		}
	}
	
	private String getIncomeRecords = "select * from trade_income_record where uid=? order by time desc limit ?,? ";
	@Override
	public List<TradeIncomeRecord> getIncomeRecords(long uid, int page, int limit) {
		try {
			List<TradeIncomeRecord> tradeIncomes = jdbcHandler.queryBySql(TradeIncomeRecord.class, getIncomeRecords, uid, (page-1)*limit, limit);
			if(tradeIncomes!=null &&tradeIncomes.size()>0){
				for(TradeIncomeRecord ti :tradeIncomes){
					if(ti.getTime() > 0){
						String timeStr = ti.getTime() + "";
						ti.setTimeMonth(timeStr.substring(0, 4)+"-"+timeStr.substring(4, 6));
					}
				}
				return tradeIncomes;
			}else{
				return null;
			}
		} catch (Exception e) {
            logger.error("getIncomeRecords error:"+e);
            return null;
		}
	}
	
	private String getRecordsAmt = "select sum(cost) from trade_record where uid=? and type=? and costType=?";
	@Override
	public int getRecordsAmt(long uid, int type,int costType) {
		try {
			return jdbcHandler.queryCount(getRecordsAmt, uid, type,costType);
		} catch (Exception e) {
            logger.error("getRecordsAmt error:"+e);
            return 0;
		}
	}
}
