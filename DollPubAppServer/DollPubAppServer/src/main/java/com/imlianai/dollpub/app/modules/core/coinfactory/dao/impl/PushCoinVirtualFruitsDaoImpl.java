package com.imlianai.dollpub.app.modules.core.coinfactory.dao.impl;

import com.imlianai.dollpub.app.modules.core.coinfactory.dao.PushCoinVirtualFruitsDao;
import com.imlianai.dollpub.domain.coinfactory.virtual.fruits.PushCoinFruitsAllot;
import com.imlianai.dollpub.domain.coinfactory.virtual.fruits.PushCoinFruitsResult;
import com.imlianai.dollpub.domain.coinfactory.virtual.fruits.PushCoinFruitsValue;
import com.imlianai.dollpub.domain.coinfactory.virtual.fruits.PushCoinFruitsWin;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 水果机相关表操作
 * @author wurui
 * @create 2018-07-19 21:37
 **/
@Repository
public class PushCoinVirtualFruitsDaoImpl implements PushCoinVirtualFruitsDao {

    @Resource
    private JdbcHandler jdbcHandler;


    @Override
    public List<PushCoinFruitsValue> getAllFruitsValue() {
        return jdbcHandler.queryBySql(PushCoinFruitsValue.class,"select * from push_coin_fruits_value");
    }

    @Override
    public int addResult(PushCoinFruitsResult result) {
        return jdbcHandler.executeSql("insert into push_coin_fruits_result(a,b,c,type,assembly,remark,time) values(?,?,?,?,?,?,now())",
                result.getA(),result.getB(),result.getC(),result.getType(),result.getAssembly(),result.getRemark());
    }

    @Override
    public List<PushCoinFruitsResult> getResultByType(int type) {
        return jdbcHandler.queryBySql(PushCoinFruitsResult.class,"select * from push_coin_fruits_result where type=?",type);
    }

    @Override
    public int clear() {
        return jdbcHandler.executeSql("delete from push_coin_fruits_result");
    }

    @Override
    public int addWinRecord(PushCoinFruitsWin fruitsWin) {
        String insert = "insert into push_coin_fruits_win(customerId,uid,busId,optId,fvId,node,type,magnetism,result,total,current,remark,nodeJson,createTime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
        return jdbcHandler.executeSql(insert,fruitsWin.getCustomerId(),fruitsWin.getUid(),fruitsWin.getBusId(),fruitsWin.getOptId(),fruitsWin.getFvId(),fruitsWin.getNode(),fruitsWin.getType(),
                fruitsWin.getMagnetism(),fruitsWin.getResult(),fruitsWin.getTotal(),fruitsWin.getCurrent(),fruitsWin.getRemark(),fruitsWin.getNodeJson());
    }

    @Override
    public int completeAssigned(int id,String remark) {
        return jdbcHandler.executeSql("update push_coin_fruits_win set result=1,remark=?,updateTime=now() where id=? and result<>1",remark,id);
    }

    @Override
    public int updateCurrent(int id, int coin) {
        return jdbcHandler.executeSql("update push_coin_fruits_win set current=current+?,updateTime=now() where id=?",coin,id);
    }

    @Override
    public PushCoinFruitsWin getRecordByOptId(long optId) {
        return jdbcHandler.queryOneBySql(PushCoinFruitsWin.class,"select * from push_coin_fruits_win where optId=? and result<>1 order by createTime asc limit 1",optId);
    }

    @Override
    public PushCoinFruitsWin getRecordById(int id) {
        return jdbcHandler.queryOneBySql(PushCoinFruitsWin.class,"select * from push_coin_fruits_win where id=?",id);
    }

    @Override
    public PushCoinFruitsWin getRecordByCondition(int busId,int customerId,long uid) {
        return jdbcHandler.queryOneBySql(PushCoinFruitsWin.class,"select * from push_coin_fruits_win where (busId=? and customerId=? and uid=?) and result<>1 order by createTime asc limit 1",busId,customerId,uid);
    }

    @Override
    public PushCoinFruitsWin getRecordByOne() {
        return jdbcHandler.queryOneBySql(PushCoinFruitsWin.class,"select * from push_coin_fruits_win where result<>1 order by createTime asc limit 1");
    }

    @Override
    public int addWinAllot(PushCoinFruitsAllot allot) {
        String insert = "insert into push_coin_fruits_allot(winId,customerId,busId,uid,optId,fvId,type,coin,magnetism,assembly,rate,result,remark,createTime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
        return jdbcHandler.executeSql(insert,allot.getWinId(),allot.getCustomerId(),allot.getBusId(),allot.getUid(),allot.getOptId(),allot.getFvId(),allot.getType(),allot.getCoin(),allot.getMagnetism(),allot.getAssembly(),allot.getRate(),allot.getResult(),allot.getRemark());
    }

    @Override
    public int updateAllotResult(long id, int result,String remark) {
        return jdbcHandler.executeSql("update push_coin_fruits_allot set result=?,remark=?,updateTime=now() where id=? and result<>?",result,remark,id,result);
    }

    @Override
    public List<PushCoinFruitsAllot> getWinAllotByOptId(long optId) {
        return jdbcHandler.queryBySql(PushCoinFruitsAllot.class,"select * from push_coin_fruits_allot where optId=?",optId);
    }

    @Override
    public PushCoinFruitsAllot getOneWinAllotByOptId(long optId) {
        return jdbcHandler.queryOneBySql(PushCoinFruitsAllot.class,"select * from push_coin_fruits_allot where optId=? and result=0 order by createTime asc limit 1",optId);
    }

    @Override
    public PushCoinFruitsAllot getOneWinAllotByWinId(int winId) {
        return jdbcHandler.queryOneBySql(PushCoinFruitsAllot.class,"select * from push_coin_fruits_allot where winId=? and result<>2 order by createTime asc limit 1",winId);
    }

    @Override
    public PushCoinFruitsAllot getOneWinAllotById(long id) {
        return jdbcHandler.queryOneBySql(PushCoinFruitsAllot.class,"select * from push_coin_fruits_allot where id=?",id);
    }

    @Override
    public PushCoinFruitsAllot getOneWinAllotByCondition(int busId, int customerId, long uid) {
        return jdbcHandler.queryOneBySql(PushCoinFruitsAllot.class,"select * from push_coin_fruits_allot where (busId=? and uid=? and customerId=?) and result<>2 order by createTime asc limit 1",busId,uid,customerId);
    }

    @Override
    public int updateAllotAffirmResult(long id,int actual,int dialRate,long optId, String remark) {
        return jdbcHandler.executeSql("update push_coin_fruits_allot set actual=?,dialRate=?,affirm=1,result=2,remark=?,optId=?,updateTime=now() where id=? and affirm<>1 and result<>2",actual,dialRate,remark,optId,id);
    }

    @Override
    public List<PushCoinFruitsAllot> getNoAllotByWinId(int winId) {
        return jdbcHandler.queryBySql(PushCoinFruitsAllot.class,"select * from push_coin_fruits_allot where winId=? and result=0",winId);
    }


}
