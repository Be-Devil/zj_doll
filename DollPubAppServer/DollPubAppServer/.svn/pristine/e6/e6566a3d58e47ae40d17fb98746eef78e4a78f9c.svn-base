package com.imlianai.dollpub.app.modules.support.shipping.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.doll.DollOrderInfo;
import com.imlianai.dollpub.domain.doll.DollOrderRecord;
import com.imlianai.dollpub.domain.doll.DollOrderRecord.OrderPayStatus;
import com.imlianai.dollpub.domain.shipping.DollShippingNotice;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class ShippingDaoImpl implements ShippingDao {

	@Resource
	JdbcHandler jdbcHandler;

	String saveApplyShippingRecord = "insert into doll_order_info(uid,uDollId,orderNum,createTime,dollId,optId,company) values(?,?,?,now(),?,?,?)";
	@Override
	public int saveDollOrderInfo(Long uid, long uDollId, long orderNum, int dollId, long optId, int company) {
		return jdbcHandler.executeSql(saveApplyShippingRecord, uid, uDollId, orderNum, dollId, optId, company);
	}
	
	String saveDollOrderRecord = "insert into doll_order_record(orderNum,uid,createTime,addressId,customerId,agencyId,remark,payStatus,amt) values(?,?,now(),?,?,?,?,?,?)";
	@Override
	public int saveDollOrderRecord(long orderNum, Long uid, Long addressId, int customerId,long agencId, String remark,int payStatus,int amt) {
		return jdbcHandler.executeSql(saveDollOrderRecord, orderNum, uid, addressId, customerId,agencId,remark,payStatus,amt);
	}
	
	String getShippingTimes = "select count(1) from doll_shipping_record where uid=?";
	@Override
	public int getShippingTimes(Long uid) {
		return jdbcHandler.queryCount(getShippingTimes, uid);
	}
	
	String getShippingNotice = "select * from doll_shipping_notice order by createTime desc limit 1";
	@Override
	public DollShippingNotice getShippingNotice() {
		return jdbcHandler.queryOneBySql(DollShippingNotice.class, getShippingNotice);
	}
	
	String saveShippingRecord = "insert into doll_shipping_record(uid,orderNum,createTime,customerId) values(?,?,now(),?)";
	@Override
	public int saveShippingRecord(Long uid, long orderNum, int customerId) {
		return jdbcHandler.executeSql(saveShippingRecord, uid, orderNum, customerId);
	}
	
	@Override
	public int updateOrderRecordPayStatus(long orderNum, int payStatus) {
		return jdbcHandler.executeSql("update doll_order_record set payStatus=? where orderNum=? limit 1", payStatus,orderNum);
	}
	@Override
	public DollOrderRecord getInvalidRecord(int minutes) {
		return jdbcHandler.queryOneBySql(DollOrderRecord.class, "select * from doll_order_record where payStatus=? and createTime<DATE_SUB(now(),INTERVAL ? MINUTE) order by createTime asc limit 1",OrderPayStatus.INIT.status,minutes);
	}
	
	@Override
	public List<DollOrderInfo> getDollOrderInfos(long orderNum) {
		return jdbcHandler.queryBySql(DollOrderInfo.class,"select * from doll_order_info where orderNum=? and status=1",orderNum);
	}
	
	@Override
	public DollOrderRecord getDollOrderRecord(long orderNum) {
		return jdbcHandler.queryOneBySql(DollOrderRecord.class, "select * from doll_order_record where orderNum=? limit 1",orderNum);
	}
	
	@Override
	public List<DollOrderRecord> getDollOrderRecordsByUid(long uid,int page,int pageSize) {
		return jdbcHandler.queryBySql(DollOrderRecord.class, "select * from doll_order_record where uid=? order by createTime desc limit ?,?",uid,(page-1)*pageSize,pageSize);
	}
	
	@Override
	public List<DollOrderInfo> getDollOrderInfosAll(long orderNum){
		return jdbcHandler.queryBySql(DollOrderInfo.class,"select * from doll_order_info where orderNum=? ",orderNum);
	}
}
