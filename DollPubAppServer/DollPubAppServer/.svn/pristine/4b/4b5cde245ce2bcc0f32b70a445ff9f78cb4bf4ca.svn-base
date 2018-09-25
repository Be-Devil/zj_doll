package com.imlianai.dollpub.app.modules.core.coinfactory.dao.impl;

import com.imlianai.dollpub.app.modules.core.coinfactory.dao.PushBusStatusDao;
import com.imlianai.dollpub.domain.coinfactory.PushBusStatus;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wurui
 * @create 2018-03-28 17:37
 **/
@Repository
public class PushBusStatusDaoImpl implements PushBusStatusDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int insert(PushBusStatus pushBusStatus,int playTime) {
        return jdbcHandler.executeSql("insert into push_bus_status(busId,deviceId,optId,uid,startTime,endTime) value (?,?,?,?,now(),date_add(now(),interval ? second))",
                pushBusStatus.getBusId(),pushBusStatus.getDeviceId(),pushBusStatus.getOptId(),pushBusStatus.getUid(),playTime);
    }

    @Override
    public int updateEndTime(int busId,int playTime) {
        return jdbcHandler.executeSql("update push_bus_status set endTime=date_add(now(),interval ? second) where busId=?",playTime,busId);
    }

    @Override
    public int updateLockState(int busId) {
        return jdbcHandler.executeSql("UPDATE push_bus_status SET lockState=1,lockTime=NOW() WHERE busId=? AND lockState<>1",busId);
    }

    @Override
    public int delete(int busId) {
        return jdbcHandler.executeSql("DELETE FROM push_bus_status WHERE busId=?",busId);
    }

    @Override
    public PushBusStatus get(int busId) {
        return jdbcHandler.queryOneBySql(PushBusStatus.class,"select * from push_bus_status WHERE busId=?",busId);
    }

    @Override
    public List<PushBusStatus> getEndTimeTimeOutRecord(int second) {
        return jdbcHandler.queryBySql(PushBusStatus.class,"select * from push_bus_status where endTime < DATE_SUB(NOW(),INTERVAL ? SECOND)",second);
    }

    @Override
    public List<PushBusStatus> getEndTimeTimeOutRecord() {
        return jdbcHandler.queryBySql(PushBusStatus.class,"select * from push_bus_status where endTime < now()");
    }

    @Override
    public List<PushBusStatus> getAllPushBusStatus() {
        return jdbcHandler.queryBySql(PushBusStatus.class,"SELECT * FROM push_bus_status");
    }
}
