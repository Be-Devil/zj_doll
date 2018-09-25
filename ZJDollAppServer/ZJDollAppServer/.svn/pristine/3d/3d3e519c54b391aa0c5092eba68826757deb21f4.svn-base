package com.imlianai.zjdoll.app.modules.support.shipping.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.doll.DollOrderExpress;
import com.imlianai.zjdoll.domain.doll.DollOrderInfo;
import com.imlianai.zjdoll.domain.doll.DollOrderRecord;
import com.imlianai.zjdoll.domain.doll.DollOrderRecord.OrderPayStatus;
import com.imlianai.zjdoll.domain.doll.DollOrderRecord.OrderStatus;
import com.imlianai.zjdoll.domain.shipping.DollShippingNotice;
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
	
	String saveDollOrderRecord = "insert into doll_order_record(orderNum,uid,createTime,addressId,userRemark,payStatus,amt) values(?,?,now(),?,?,?,?)";
	@Override
	public int saveDollOrderRecord(long orderNum, Long uid, Long addressId, String remark,int payStatus,int amt) {
		return jdbcHandler.executeSql(saveDollOrderRecord, orderNum, uid, addressId, remark,payStatus,amt);
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
	
	String saveShippingRecord = "insert into doll_shipping_record(uid,orderNum,createTime) values(?,?,now())";
	@Override
	public int saveShippingRecord(Long uid, long orderNum) {
		return jdbcHandler.executeSql(saveShippingRecord, uid, orderNum);
	}
	@Override
	public List<DollOrderRecord> getDollOrderRecords(int intervalMinutes) {
		return jdbcHandler.queryBySql(DollOrderRecord.class, "select * from doll_order_record where auditTime<DATE_SUB(now(),INTERVAL 1 HOUR) and status in (?,?)", OrderStatus.PASS_COMMITTED.status,OrderStatus.PART_COMMITTED.status);
	}
	@Override
	public List<DollOrderInfo> getDollOrderInfos(long orderNum) {
		return jdbcHandler.queryBySql(DollOrderInfo.class,"select * from doll_order_info where orderNum=? and status=1",orderNum);
	}
	@Override
	public List<DollOrderInfo> getDollOrderInfosAll(long orderNum){
		return jdbcHandler.queryBySql(DollOrderInfo.class,"select * from doll_order_info where orderNum=? ",orderNum);
	}
	@Override
	public int updateDollOrderRecordStatus(long orderNum, int status) {
		return jdbcHandler.executeSql("update doll_order_record set status=? where orderNum=? ", status,orderNum);
	}
	@Override
	public int initDollOrderExpress(long orderNum,long uid, long orderId) {
		return jdbcHandler.executeSql("insert into doll_order_express (orderNum,uid,orderId,state,createTime) value (?,?,?,1,now())", orderNum,uid,orderId);
	}
	@Override
	public int updateDollOrderRecordReason(long orderNum, String reason) {
		return jdbcHandler.executeSql("update doll_order_record set reason=? where orderNum=?", reason,orderNum);
	}
	@Override
	public int addDollOrderRecordTaskLog(long orderNum,long uid, String req, String resp) {
		return jdbcHandler.executeSql("insert into doll_shipping_req_log (orderNum,uid,time,req,resp) value (?,?,now(),?,?)", orderNum,uid,req,resp);
	}
	@Override
	public DollOrderExpress getDollOrderExpress(long orderId) {
		return jdbcHandler.queryOneBySql(DollOrderExpress.class, "select * from doll_order_express where orderId=? limit 1",orderId);
	}
	@Override
	public int updateDollOrderExpress(long orderNum, String expressNum,
			String expressCom, String expressName,String expressRemark) {
		return jdbcHandler.executeSql("update doll_order_express set updateTime=now(),"
				+ "expressTime=now(), expressNum=? , expressCom=?, expressName=? ,status=?,expressRemark=?"
				+ " where orderNum=?",expressNum,expressCom,expressName,30,expressRemark,orderNum);
	}
	@Override
	public void addShippingCallbackLog(String content) {
		jdbcHandler.executeSql("insert into doll_shipping_callback_log (content,time) value (?,now())",content);
	}
	
	@Override
	public DollOrderInfo getDollOrderInfoByUDollId(long uDollId) {
		return jdbcHandler.queryOneBySql(DollOrderInfo.class, "select * from doll_order_info where uDollId=? order by createTime desc limit 1", uDollId);
	}
	@Override
	public List<DollOrderRecord> getDollOrderRecordsByUid(long uid,int page,int pageSize) {
		return jdbcHandler.queryBySql(DollOrderRecord.class, "select * from doll_order_record where uid=? order by createTime desc limit ?,?",uid,(page-1)*pageSize,pageSize);
	}
	@Override
	public DollOrderRecord getDollOrderRecord(long orderNum) {
		return jdbcHandler.queryOneBySql(DollOrderRecord.class, "select * from doll_order_record where orderNum=? limit 1",orderNum);
	}
	@Override
	public int updateOrderRecordPayStatus(long orderNum, int payStatus) {
		return jdbcHandler.executeSql("update doll_order_record set payStatus=? where orderNum=? limit 1", payStatus,orderNum);
	}
	@Override
	public DollOrderRecord getInvalidRecord(int minutes) {
		return jdbcHandler.queryOneBySql(DollOrderRecord.class, "select * from doll_order_record where payStatus=? and createTime<DATE_SUB(now(),INTERVAL ? MINUTE) order by createTime asc limit 1",OrderPayStatus.INIT.status,minutes);
	}
}
