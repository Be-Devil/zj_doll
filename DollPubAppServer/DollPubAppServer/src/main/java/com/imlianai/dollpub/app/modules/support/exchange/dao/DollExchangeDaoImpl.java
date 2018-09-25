package com.imlianai.dollpub.app.modules.support.exchange.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.doll.DollExchangeRecord;
import com.imlianai.dollpub.domain.exchange.ShoppingExchangeRecord;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class DollExchangeDaoImpl implements DollExchangeDao {

	@Resource
	JdbcHandler jdbcHandler;

	String getExchangeRecordByUserDollId = "select * from doll_exchange_record where uDollId=?";
	@Override
	public DollExchangeRecord getExchangeRecordByUserDollId(long userDollId) {
		return jdbcHandler.queryOneBySql(DollExchangeRecord.class, getExchangeRecordByUserDollId, userDollId);
	}
	
	String saveExchangeRecord = "insert into doll_exchange_record(uDollId,coin,createTime) values(?,?,now())";
	@Override
	public int saveExchangeRecord(long id, int currency) {
		return jdbcHandler.executeSql(saveExchangeRecord, id, currency);
	}
	
	String saveShoppingExchangeRecord = "insert into shopping_exchange_record(uDollId,uid,cost,remark,createTime,type,commNum,rareNum) values(?,?,?,?,now(),?,?,?)";
	@Override
	public int saveShoppingExchangeRecord(Long uid, long udollId, int cost, String remark, int type, int commNum, int rareNum) {
		return jdbcHandler.executeSql(saveShoppingExchangeRecord, udollId, uid, cost, remark, type, commNum, rareNum);
	}
	
	String saveOrUpdateShoppingExchangeNum = "insert into shopping_exchange_num(dollId,num,createTime,updateTime) values(?,1,now(),now()) on duplicate key update num=num+1,updateTime=now()";
	@Override
	public int saveOrUpdateShoppingExchangeNum(int dollId) {
		return jdbcHandler.executeSql(saveOrUpdateShoppingExchangeNum, dollId);
	}
	
	String getShoppingExchangeNum = "select num from shopping_exchange_num where dollId=?";
	@Override
	public int getShoppingExchangeNum(int dollId) {
		return jdbcHandler.queryCount(getShoppingExchangeNum, dollId);
	}
	
	String getExcRecordsByUid = "select * from shopping_exchange_record where uid=? order by createTime desc";
	@Override
	public List<ShoppingExchangeRecord> getExcRecordsByUid(Long uid) {
		return jdbcHandler.queryBySql(ShoppingExchangeRecord.class, getExcRecordsByUid, uid);
	}
}
