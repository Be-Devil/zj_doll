package com.imlianai.dollpub.app.modules.core.trade.dao.impl;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.imlianai.rpc.support.utils.StringUtil;
import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.app.modules.core.trade.dao.TradeChargeDAO;
import com.imlianai.dollpub.app.modules.core.trade.domain.AlipayH5Body;
import com.imlianai.dollpub.domain.trade.ChargeMchInfo;
import com.imlianai.dollpub.domain.trade.ChargeState;
import com.imlianai.dollpub.domain.trade.ChargeWay;
import com.imlianai.dollpub.domain.trade.TradeCharge;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.rpc.support.utils.DateUtils;

import java.util.List;

@Repository
public class TradeChargeDAOImpl implements TradeChargeDAO {

	@Resource
	private JdbcHandler jdbcHandler;

	private static String addSql = "insert into trade_charge(otherId,uid,way,cost,itemCode,channel,osType,remark,time,imei,state,customerId,agentId,chargeType,orderNum) "
			+ "values(?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?) ";

	@Override
	public long add(TradeCharge c) {
		return jdbcHandler.executeSql(addSql, c.getOtherId(), c.getUid(),
				c.getWay(), c.getCost(), c.getItemCode(), c.getChannel(),
				c.getOsType(), c.getRemark(), c.getImei(), c.getState(),
				c.getCustomerId(), c.getAgentId(), c.getChargeType(),
				c.getOrderNum());
	}

	private static String addTempSql = "insert into trade_charge_temp(otherId,uid,way,cost,itemCode,channel,osType,remark,time,imei) "
			+ "values(?,?,?,?,?,?,?,?,now(),?) ";

	@Override
	public long addTemp(TradeCharge c) {
		return jdbcHandler.executeSql(addTempSql, c.getOtherId(), c.getUid(),
				c.getWay(), c.getCost(), c.getItemCode(), c.getChannel(),
				c.getOsType(), c.getRemark(), c.getImei());
	}

	private static String getTempByOtherIdSql = "select * from trade_charge_temp where otherId=? and way=?";

	@Override
	public TradeCharge getTempByOtherId(String otherId, ChargeWay way) {
		return jdbcHandler.queryOneBySql(TradeCharge.class,
				getTempByOtherIdSql, otherId, way.type);
	}

	private static String updateStateSql = "update trade_charge set `state`=? where id=? and `state`!=?";

	@Override
	public int updateState(long id, ChargeState state) {
		return jdbcHandler.executeSql(updateStateSql, state.type, id,
				state.type);
	}

	private static String getByIdSql = "select * from trade_charge where id=?";

	@Override
	public TradeCharge getById(long id) {
		return jdbcHandler.queryOneBySql(TradeCharge.class, getByIdSql, id);
	}

	private static String addLogSql = "insert into trade_charge_log(chargeId,uid,createParams,createDate,httpParams) values(?,?,?,now(),?)";

	@Override
	public void addLog(long chargeId, long uid, String createParams,
			String httpParams) {
		jdbcHandler.executeSql(addLogSql, chargeId, uid, createParams,
				httpParams);
	}

	private static String updatelogSql = "update trade_charge_log set callBackValue=?,callBackDate=now() where chargeId=?";

	@Override
	public void updatelog(long chargeId, String callBackValue) {
		jdbcHandler.executeSql(updatelogSql, callBackValue, chargeId);
	}

	private static String getByOtherIdSql = "select * from trade_charge where otherId=? and way=?";

	@Override
	public TradeCharge getByOtherId(String otherId, ChargeWay way) {
		return jdbcHandler.queryOneBySql(TradeCharge.class, getByOtherIdSql,
				otherId, way.type);
	}

	private static String hasChargeSpecialAmt = "select id from trade_charge where uid=?  and itemCode=? and state=1 order by id asc limit 1";

	@Override
	public long hasChargeSpecialAmt(long uid, int code) {
		return jdbcHandler.queryCount(hasChargeSpecialAmt, uid, code);
	}

	@Override
	public int addAlipayH5Body(long id, String body, long uid, int price) {
		return jdbcHandler
				.executeSql(
						"insert into trade_charge_alipay_bill_body (chargeId,body,time,state,uid,price) values (?,?,now(),0,?,?)",
						id, body, uid, price);
	}

	@Override
	public AlipayH5Body getAlipayH5Body(long id) {
		return jdbcHandler.queryOneBySql(AlipayH5Body.class,
				"select * from trade_charge_alipay_bill_body where id=?", id);
	}

	@Override
	public ChargeMchInfo getChargeMchInfo(int customerId) {
		return jdbcHandler.queryOneBySql(ChargeMchInfo.class,
				"select * from charge_mch_info where customerId=? limit 1",
				customerId);
	}

	@Override
	public ChargeMchInfo getChargeMchInfo(String mchId) {
		return jdbcHandler.queryOneBySql(ChargeMchInfo.class,
				"select * from charge_mch_info where mchId=? limit 1", mchId);
	}

	@Override
	public int hasCharge(long uid) {
		return jdbcHandler.queryCount(
				"select 1 from trade_charge where uid=? and state=1 limit 1",
				uid);
	}

	private static String hasChargeToday = "select id from trade_charge where uid=?  and itemCode=? and state=1 and time>=? and time<=? order by id asc limit 1";

	@Override
	public long hasChargeToday(long uid, int code) {
		return jdbcHandler.queryCount(hasChargeToday, uid, code,
				DateUtils.getTodatFirstTime(), DateUtils.getTodatLastTime());
	}

	@Override
	public void addTradeChargeSrc(long uid, int code, int srcPrice, int target,
			int customerId, String srcId) {
		jdbcHandler
				.executeSql(
						"insert into trade_charge_src (uid,code,srcPrice,target,customerId,srcId,time,dateCol) value (?,?,?,?,?,?,now(),now()) ",
						uid, code, srcPrice, target, customerId, srcId);
	}

	@Override
	public int hasChargeSrc(long uid, int code) {
		return jdbcHandler
				.queryCount(
						"select 1 from trade_charge_src where uid=? and code=? limit 1",
						uid, code);
	}
	
	@Override
	public int hasChargeSrcToday(long uid, int code) {
		return jdbcHandler
				.queryCount(
						"select 1 from trade_charge_src where uid=? and code=? and dateCol=DATE_FORMAT(now(),'%Y-%m-%d') limit 1",
						uid, code);
	}

	@Override
	public List<TradeCharge> getListByUidsAndTime(List<Long> uIds, String start, String end) {
		if (!StringUtil.isNullOrEmpty(uIds)) {
			String uids = "";
			for (int i = 0; i < uIds.size(); i++) {
				if (i > 0) {
					uids += ",";
				}
				uids += uIds.get(i);
			}
			String sql = "select * from trade_charge where uid in (" + uids + ") and (time>='" + start + "' and time<='" + end + "') and state=1 order by time desc";
			return jdbcHandler.queryBySql(TradeCharge.class, sql);

		}

		return Lists.newArrayList();
	}
}
