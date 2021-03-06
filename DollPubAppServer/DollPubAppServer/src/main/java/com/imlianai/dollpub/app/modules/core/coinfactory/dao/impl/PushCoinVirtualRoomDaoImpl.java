package com.imlianai.dollpub.app.modules.core.coinfactory.dao.impl;

import com.imlianai.dollpub.app.modules.core.coinfactory.dao.PushCoinVirtualRoomDao;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.VirtualPushCoinRoom;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author wurui
 * @create 2018-07-02 15:00
 **/
@Repository
public class PushCoinVirtualRoomDaoImpl implements PushCoinVirtualRoomDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int addWatchRecord(VirtualPushCoinRoom virtualPushCoinRoom) {
        return jdbcHandler.executeSql("insert into push_coin_virtual_room(uid,busId,authType,customerId,time) " +
                        "values(?,?,?,?,now()) on duplicate key update busId=?,authType=?,time=now()",
                virtualPushCoinRoom.getUid(),virtualPushCoinRoom.getBusId(),virtualPushCoinRoom.getAuthType(),virtualPushCoinRoom.getCustomerId(),
                virtualPushCoinRoom.getBusId(),virtualPushCoinRoom.getAuthType());
    }

    @Override
    public VirtualPushCoinRoom getWatchBus(int customerId,long uid) {
        return jdbcHandler.queryOneBySql(VirtualPushCoinRoom.class,"select * from push_coin_virtual_room where customerId=? and uid=?",customerId,uid);
    }

    @Override
    public int laveVirtualRoom(int customerId, long uid) {
        return jdbcHandler.executeSql("delete from push_coin_virtual_room where customerId=? and uid=?",customerId,uid);
    }
}
