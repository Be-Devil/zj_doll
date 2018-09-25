package com.imlianai.zjdoll.app.modules.support.event.invite20180320.dao.impl;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.dao.Event20180320InviteUsedTimesDao;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteUsedTimes;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author wurui
 * @create 2018-03-26 11:09
 **/
@Repository
public class Event20180320InviteUsedTimesDaoImpl implements Event20180320InviteUsedTimesDao {


    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int init(long uid) {
        String sql = "INSERT INTO event_20180320_invite_usedTimes(uid,usedTimes,createTime) VALUES(?,1,NOW()) ON DUPLICATE KEY UPDATE usedTimes = usedTimes+1,updateTime=NOW()";
        return jdbcHandler.executeSql(sql,uid);
    }

    @Override
    public int update(long uid) {
        return jdbcHandler.executeSql("UPDATE  event_20180320_invite_usedTimes SET usedTimes=usedTimes+1 WHERE uid=?",uid);
    }

    @Override
    public Event20180320InviteUsedTimes getByUid(long uid) {
        return jdbcHandler.queryOneBySql(Event20180320InviteUsedTimes.class,"SELECT * FROM event_20180320_invite_usedTimes WHERE uid=?",uid);
    }
}
