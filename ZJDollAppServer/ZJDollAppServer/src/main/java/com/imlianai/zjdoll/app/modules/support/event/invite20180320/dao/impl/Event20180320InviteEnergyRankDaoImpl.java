package com.imlianai.zjdoll.app.modules.support.event.invite20180320.dao.impl;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.dao.Event20180320InviteEnergyRankDao;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteEnergyRank;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wurui
 * @create 2018-03-20 18:51
 **/
@Repository
public class Event20180320InviteEnergyRankDaoImpl implements Event20180320InviteEnergyRankDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int addEnergyRank(Event20180320InviteEnergyRank energyRank) {
        String insertOrUpdate = "INSERT INTO event_20180220_invite_energy_rank(uid,totalEnergy,createTime) VALUES(?,?,NOW()) " +
                "ON DUPLICATE KEY UPDATE totalEnergy = totalEnergy + ?,updateTime=NOW()";
        return jdbcHandler.executeSql(insertOrUpdate,energyRank.getUid(),energyRank.getTotalEnergy(),energyRank.getTotalEnergy());
    }

    @Override
    public Event20180320InviteEnergyRank getEnergyRankByUid(long uid) {
        String select = "SELECT * FROM event_20180220_invite_energy_rank WHERE uid=?";
        return jdbcHandler.queryOneBySql(Event20180320InviteEnergyRank.class,select,uid);
    }

    @Override
    public List<Event20180320InviteEnergyRank> getEnergyRankLimit50() {
        return jdbcHandler.queryBySql(Event20180320InviteEnergyRank.class,"SELECT * FROM event_20180220_invite_energy_rank ORDER BY totalEnergy DESC,createTime ASC LIMIT 50");
    }

    @Override
    public List<Event20180320InviteEnergyRank> getEnergyRankLimit30() {
        return jdbcHandler.queryBySql(Event20180320InviteEnergyRank.class,"SELECT * FROM event_20180220_invite_energy_rank ORDER BY totalEnergy DESC,createTime ASC LIMIT 30");
    }

    @Override
    public int addEnergy(long uid, int energy) {
        String addEnergy = "UPDATE event_20180220_invite_energy_rank SET totalEnergy = totalEnergy + ? WHERE uid=?";
        return jdbcHandler.executeSql(addEnergy,energy,uid);
    }

    @Override
    public int init(long uid) {
        //return jdbcHandler.executeSql("INSERT INTO event_20180220_invite_energy_rank(uid,totalEnergy,usedTimes,createTime) VALUES(?,0,0,NOW())",uid);
        return jdbcHandler.executeSql("INSERT INTO event_20180220_invite_energy_rank(uid,totalEnergy,usedTimes,createTime) VALUES(?,0,0,NOW()) ON DUPLICATE KEY UPDATE usedTimes = usedTimes+1",uid);



    }

    @Override
    public int updateUsedTimes(long uid) {
        return jdbcHandler.executeSql("UPDATE  event_20180220_invite_energy_rank SET usedTimes=usedTimes+1 WHERE uid=?",uid);
    }


}
