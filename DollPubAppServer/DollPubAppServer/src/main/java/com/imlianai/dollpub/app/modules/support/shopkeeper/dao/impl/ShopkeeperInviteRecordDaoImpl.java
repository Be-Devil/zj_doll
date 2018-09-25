package com.imlianai.dollpub.app.modules.support.shopkeeper.dao.impl;

import com.google.common.collect.Lists;
import com.imlianai.dollpub.app.modules.support.shopkeeper.dao.ShopkeeperInviteRecordDao;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperInviteRecord;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author wurui
 * @create 2018-04-26 20:25
 **/
@Repository
public class ShopkeeperInviteRecordDaoImpl implements ShopkeeperInviteRecordDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int addRecord(ShopkeeperInviteRecord record) {
        return jdbcHandler.executeSql("INSERT INTO shopkeeper_invite_record(uid,inviteUid,inviteUidTop,remark,inviteTime) VALUES(?,?,?,?,NOW())",
                record.getUid(), record.getInviteUid(), record.getInviteUidTop(), record.getRemark());
    }

    @Override
    public List<ShopkeeperInviteRecord> getRecord(long inviteUidTop, int first_or_second) {
        if (first_or_second != 0) {
            String sql = "SELECT * FROM shopkeeper_invite_record";
            if (first_or_second == 1) {
                return jdbcHandler.queryBySql(ShopkeeperInviteRecord.class, sql + " WHERE inviteUidTop=? AND inviteUid=0", inviteUidTop);
            }
            if (first_or_second == 2) {
                return jdbcHandler.queryBySql(ShopkeeperInviteRecord.class, sql + " WHERE inviteUidTop=? AND inviteUid<>0", inviteUidTop);
            }
        }
        return Lists.newArrayList();
    }

}
