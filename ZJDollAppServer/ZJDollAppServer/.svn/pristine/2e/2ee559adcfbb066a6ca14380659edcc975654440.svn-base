package com.imlianai.zjdoll.app.modules.core.push.dao.impl;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.core.push.dao.PushCoinPointGiveDao;
import com.imlianai.zjdoll.domain.pushcoin.PushCoinBoxPointGive;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wurui
 * @create 2018-06-19 11:49
 **/
@Repository
public class PushCoinPointGiveDaoImpl implements PushCoinPointGiveDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int addPointGiveRecord(PushCoinBoxPointGive pushCoinBoxPointGive) {
        return jdbcHandler.executeSql("insert into push_coin_box_point_give(busId,uid,optId,boxId,exJewel,exCoin,exScore,exCoupon,exDoll,status,createTime) " +
                "values(?,?,?,?,?,?,?,?,?,?,now())",pushCoinBoxPointGive.getBusId(),pushCoinBoxPointGive.getUid(),pushCoinBoxPointGive.getOptId(),pushCoinBoxPointGive.getBoxId(),
                pushCoinBoxPointGive.getExJewel(),pushCoinBoxPointGive.getExCoin(),pushCoinBoxPointGive.getExScore(),pushCoinBoxPointGive.getExCoupon(),pushCoinBoxPointGive.getExDoll(),
                pushCoinBoxPointGive.getStatus());
    }

    @Override
    public int updatePointGiveRecord(int id, int boxId, long optId, int status) {
        return jdbcHandler.executeSql("update push_coin_box_point_give set boxId=?,optId=?,status=?,endTime=now() where id=?",boxId,optId,status,id);
    }

    @Override
    public PushCoinBoxPointGive getPointGiveRecordByUid(long uid) {
        return jdbcHandler.queryOneBySql(PushCoinBoxPointGive.class,"select * from push_coin_box_point_give where uid=? and status<>1 order by createTime asc limit 1",uid);
    }

    @Override
    public PushCoinBoxPointGive getPointGiveRecordById(int id) {
        return jdbcHandler.queryOneBySql(PushCoinBoxPointGive.class,"select * from push_coin_box_point_give where id=?",id);
    }

    @Override
    public int deletePointRecordById(int id) {
        return jdbcHandler.executeSql("delete from push_coin_box_point_give where id=? and status<>1",id);
    }

    @Override
    public List<PushCoinBoxPointGive> getPointGiveListByUid(long uid) {
        return jdbcHandler.queryBySql(PushCoinBoxPointGive.class,"select * from push_coin_box_point_give where uid=?",uid);
    }

    @Override
    public List<PushCoinBoxPointGive> getPointGiveList() {
        return jdbcHandler.queryBySql(PushCoinBoxPointGive.class,"select * from push_coin_box_point_give order by createTime desc");
    }
}
