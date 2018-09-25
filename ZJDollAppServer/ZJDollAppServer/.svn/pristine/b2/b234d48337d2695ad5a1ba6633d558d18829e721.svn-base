package com.imlianai.zjdoll.app.modules.support.event.invite20180320.dao.impl;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.dao.Event20180320InviteEnergyRecordDao;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteEnergyRecord;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wurui
 * @create 2018-03-20 19:00
 **/
@Repository
public class Event20180320InviteEnergyRecordDaoImpl implements Event20180320InviteEnergyRecordDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int addRecord(Event20180320InviteEnergyRecord energyRecord) {
        String insert = "INSERT INTO event_20180320_invite_energy_record(uid,inviteUid,inviteUidTop,code,remark,energy,type,createTime) VALUES(?,?,?,?,?,?,?,NOW())";
        return jdbcHandler.executeSql(insert,energyRecord.getUid(),energyRecord.getInviteUid(),energyRecord.getInviteUidTop(),energyRecord.getCode(),energyRecord.getRemark(),energyRecord.getEnergy(),energyRecord.getType());
    }

    @Override
    public List<Event20180320InviteEnergyRecord> getEnergyRecordByUid(long uid) {
        return jdbcHandler.queryBySql(Event20180320InviteEnergyRecord.class,"SELECT * FROM event_20180320_invite_energy_record WHERE uid=?",uid);
    }

    @Override
    public List<Event20180320InviteEnergyRecord> getRecordByInviteUidTop(long inviteUidTop) {
        String select = "SELECT * FROM event_20180320_invite_energy_record WHERE inviteUidTop=? ORDER BY createTime DESC";
        return jdbcHandler.queryBySql(Event20180320InviteEnergyRecord.class,select,inviteUidTop);
    }
}
