package com.imlianai.dollpub.app.modules.core.coinfactory.dao.impl;

import com.imlianai.dollpub.app.modules.core.coinfactory.dao.PushBusStatusVirtualDao;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.PushBusStatusVirtual;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wurui
 * @create 2018-07-02 17:37
 **/
@Repository
public class PushBusStatusVirtualDaoImpl implements PushBusStatusVirtualDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int insert(PushBusStatusVirtual pushBusStatusVirtual, int playTime) {
        return jdbcHandler.executeSql("insert into push_bus_status_virtual(busId,deviceId,optId,uid,customerId,startTime,endTime) value (?,?,?,?,?,now(),date_add(now(),interval ? second))",
                pushBusStatusVirtual.getBusId(),pushBusStatusVirtual.getDeviceId(),pushBusStatusVirtual.getOptId(),pushBusStatusVirtual.getUid(),pushBusStatusVirtual.getCustomerId(),playTime);
    }

    @Override
    public int delete(int busId, long optId) {
        return jdbcHandler.executeSql("delete from push_bus_status_virtual where busId=? and optId=?",busId,optId);
    }

    @Override
    public PushBusStatusVirtual get(int busId, long optId) {
        return jdbcHandler.queryOneBySql(PushBusStatusVirtual.class,"select * from push_bus_status_virtual where busId=? and optId=?",busId,optId);
    }

    @Override
    public PushBusStatusVirtual get(int busId,long uid,int customerId) {
        return jdbcHandler.queryOneBySql(PushBusStatusVirtual.class,"select * from push_bus_status_virtual where busId=? and uid=? and customerId=?",busId,uid,customerId);
    }

    @Override
    public int updateEndTime(int busId,long optId, int playTime) {
        return jdbcHandler.executeSql("update push_bus_status_virtual set endTime=date_add(now(),interval ? second) where busId=? and optId=?",playTime,busId,optId);
    }

    @Override
    public List<PushBusStatusVirtual> getEndTimeTimeOutRecord() {
        return jdbcHandler.queryBySql(PushBusStatusVirtual.class,"select * from push_bus_status_virtual where endTime < now()");
    }

    @Override
    public List<PushBusStatusVirtual> getListByBusId(int busId) {
        return jdbcHandler.queryBySql(PushBusStatusVirtual.class,"select * from push_bus_status_virtual where busId=?",busId);
    }

    @Override
    public int updateWeight(int busId, long optId, int weight) {
        return jdbcHandler.executeSql("update push_bus_status_virtual set weight=? where busId=? and optId=?",weight,busId,optId);
    }
}