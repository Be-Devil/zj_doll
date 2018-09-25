package com.imlianai.dollpub.app.modules.core.coinfactory.dao.impl;

import com.imlianai.dollpub.app.modules.core.coinfactory.dao.PushCoinCallbackDao;
import com.imlianai.dollpub.domain.coinfactory.PushCoinCallbackLog;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author wurui
 * @create 2018-07-06 11:07
 **/
@Repository
public class PushCoinCallbackDaoImpl implements PushCoinCallbackDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int insert(PushCoinCallbackLog pushCoinCallbackLog) {
        int result = 0;
        String insert_sql = "insert into push_coin_callback_log(deviceId,busId,optId,customerId,groupId,record,srcUrl,request,virtual,remark) values (?,?,?,?,?,?,?,?,?,?)";
        if (pushCoinCallbackLog != null) {
            result = jdbcHandler.executeSql(insert_sql, pushCoinCallbackLog.getDeviceId(), pushCoinCallbackLog.getBusId(), pushCoinCallbackLog.getOptId(), pushCoinCallbackLog.getCustomerId(),
                    pushCoinCallbackLog.getGroupId(), pushCoinCallbackLog.getRecord(), pushCoinCallbackLog.getSrcUrl(),pushCoinCallbackLog.getRequest(),pushCoinCallbackLog.getVirtual(),
                    pushCoinCallbackLog.getRemark());
        }
        return result;
    }

    @Override
    public int update(PushCoinCallbackLog pushCoinCallbackLog) {
        int result = 0;
        String update_sql = "update push_coin_callback_log set response=?,updateTime=now() where optId=? and busId=? and groupId=? and customerId=?";
        if (pushCoinCallbackLog != null) {
            result = jdbcHandler.executeSql(update_sql, pushCoinCallbackLog.getResponse(),pushCoinCallbackLog.getOptId(),pushCoinCallbackLog.getBusId(),pushCoinCallbackLog.getGroupId(),pushCoinCallbackLog.getCustomerId());
        }
        return result;
    }
}
