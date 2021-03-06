package com.imlianai.zjdoll.app.modules.core.trade.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.imlianai.rpc.support.utils.StringUtil;
import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.trade.ChargeState;
import com.imlianai.zjdoll.domain.trade.ChargeWay;
import com.imlianai.zjdoll.domain.trade.TradeCharge;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.UserFirstChargeTarget;
import com.imlianai.zjdoll.app.modules.core.trade.dao.TradeChargeDAO;
import com.imlianai.zjdoll.app.modules.core.trade.util.ali.domain.AlipayH5Body;

@Repository
public class TradeChargeDAOImpl implements TradeChargeDAO {

    @Resource
    private JdbcHandler jdbcHandler;

    private static String addSql = "insert into trade_charge(otherId,uid,way,cost,itemCode,channel,osType,remark,time,imei,state,chargeType,orderNum,deductionCost,realyCost,targetCost) "
            + "values(?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?) ";

    @Override
    public long add(TradeCharge c) {
        return jdbcHandler.executeSql(addSql, c.getOtherId(), c.getUid(),
                c.getWay(), c.getCost(), c.getItemCode(), c.getChannel(),
                c.getOsType(), c.getRemark(), c.getImei(), c.getState(), c.getChargeType(), c.getOrderNum(), c.getDeductionCost(), c.getRealyCost(), c.getTargetCost());
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

    private static String updateStateSql = "update trade_charge set `state`=? ,time=now() where id=? and `state`!=?";

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
    public long hasChargeSpecialAmt(long uid, int amt) {
        return jdbcHandler.queryCount(hasChargeSpecialAmt, uid, amt);
    }

    @Override
    public int addAlipayH5Body(long id, String body, long uid, int price) {
        return jdbcHandler.executeSql("insert into trade_charge_alipay_bill_body (chargeId,body,time,state,uid,price) values (?,?,now(),0,?,?)", id, body, uid, price);
    }

    @Override
    public AlipayH5Body getAlipayH5Body(long id) {
        return jdbcHandler.queryOneBySql(AlipayH5Body.class, "select * from trade_charge_alipay_bill_body where id=?", id);
    }

    @Override
    public double getUserAllCost(Long uid, String startDate) {
        Double allCost = jdbcHandler.queryOneBySql(Double.class, "select sum(cost) from trade_charge where uid=? and state=1 and time>=str_to_date(?, '%Y-%m-%d')", uid, startDate);
        return allCost == null ? 0 : allCost.doubleValue();
    }

    @Override
    public int hasCharge(long uid) {
        return jdbcHandler.queryCount("select 1 from trade_charge where uid=? and state=1 and time>='2018-01-31 00:00:00' limit 1", uid);
    }

    @Override
    public int addUserFirstChargeMsg(long uid) {
        return jdbcHandler.executeSql("insert into user_first_charge_msg (uid,time,dayDate) value (?,now(),DATE_FORMAT(now(),'%Y-%m-%d'))", uid);
    }

    @Override
    public int delUserFirstChargeMsg(long uid) {
        return jdbcHandler.executeSql("delete from user_first_charge_msg where uid=?", uid);
    }

    @Override
    public List<Long> getUserFirstChargeMsg() {
        return jdbcHandler.queryBySql(Long.class, "select uid from user_first_charge_msg where pushCount=0 order by time asc");
    }

    @Override
    public UserFirstChargeTarget getFirstChargeMsg(long uid) {
        return jdbcHandler.queryOneBySql(UserFirstChargeTarget.class, "select * from user_first_charge_msg where uid=? limit 1", uid);
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

    @Override
    public int addFirstChargeTarget(long uid, int code, int nextPushHour) {
        return jdbcHandler.executeSql("insert into user_first_charge_target (uid,code,pushCount,time,nextPushTime) value (?,?,0,now(),DATE_ADD(now(),INTERVAL ? HOUR )) on duplicate key update code=?,pushCount=pushCount+1,nextPushTime=DATE_ADD(now(),INTERVAL ? HOUR )", uid, code, nextPushHour, code, nextPushHour);
    }

    @Override
    public int removeFirstChargeTarget(long uid) {
        return jdbcHandler.executeSql("delete from user_first_charge_target where uid=? limit 1", uid);
    }

    @Override
    public int isFirstChargePushTarget(long uid) {
        return jdbcHandler.queryCount("select code from user_first_charge_target where uid=? limit 1", uid);
    }

    @Override
    public List<UserFirstChargeTarget> getUserFirstChargeTarget() {
        return jdbcHandler.queryBySql(UserFirstChargeTarget.class, "select * from user_first_charge_target where nextPushTime<now()");
    }

    @Override
    public UserFirstChargeTarget getUserFirstChargeTarget(long uid) {
        return jdbcHandler.queryOneBySql(UserFirstChargeTarget.class, "select * from user_first_charge_target where uid=? limit 1", uid);
    }

    @Override
    public void incUserFirstChargeMsg(long uid, int nextPushHour) {
        jdbcHandler.executeSql("update user_first_charge_msg set pushCount=pushCount+1 ,nextPushTime=DATE_ADD(now(),INTERVAL ? HOUR ) where uid=?", nextPushHour, uid);
    }
}
