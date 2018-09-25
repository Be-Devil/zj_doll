package com.imlianai.dollpub.app.modules.core.coinfactory.dao.impl;

import com.imlianai.dollpub.app.modules.core.coinfactory.dao.PushCoinOptRouterDao;
import com.imlianai.dollpub.app.modules.core.coinfactory.router.PushCoinOptRouter;
import com.imlianai.dollpub.domain.coinfactory.MachinePushCoin;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author wurui
 * @create 2018-04-03 16:56
 **/
@Repository
public class PushCoinOptRouterDaoImpl implements PushCoinOptRouterDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Resource
    private PushCoinOptRouter pushCoinOptRouter;

    @Override
    public long insert(int groupId, MachinePushCoin pushCoin) {
        String insert = "INSERT INTO %s (optId,deviceId,busId,uid,customerId,startTime,createTime) VALUES(?,?,?,?,?,NOW(),NOW())";
        return jdbcHandler.executeSql(pushCoinOptRouter.getFormatSql(insert,groupId),pushCoin.getOptId(),pushCoin.getDeviceId(),pushCoin.getBusId(),pushCoin.getUid(),pushCoin.getCustomerId());
    }

    private String select = "SELECT * FROM %s";

    @Override
    public MachinePushCoin getOne(int groupId, long optId) {
        return jdbcHandler.queryOneBySql(MachinePushCoin.class,pushCoinOptRouter.getFormatSql(select,groupId) + " WHERE optId=?",optId);
    }

    @Override
    public MachinePushCoin getOne(int groupId, long optId, long uid) {
        return jdbcHandler.queryOneBySql(MachinePushCoin.class,pushCoinOptRouter.getFormatSql(select,groupId) + " WHERE optId=? AND uid=?",optId,uid);
    }


    @Override
    public int updateCoin(int groupId, long optId, int coin, int type) {
        String tableName = pushCoinOptRouter.getFormatSql("UPDATE %s",groupId);
        String intoCoin = tableName + " SET intoCoin = intoCoin+?,endTime=NOW() WHERE state<>1 AND optId=?";
        String outCoin = tableName + " SET outCoin = outCoin+? WHERE state<>1 AND optId=?";
        //投币
        if (type == 0){
            return jdbcHandler.executeSql(intoCoin,coin,optId);
        }
        //出币
        if (type == 1){
            return jdbcHandler.executeSql(outCoin,coin,optId);
        }
        return 0;
    }

    @Override
    public int updateState(int groupId, long optId) {
        return jdbcHandler.executeSql(pushCoinOptRouter.getFormatSql("UPDATE %s",groupId) + " SET state=1 WHERE state<>1 AND optId=?",optId);
    }
}
