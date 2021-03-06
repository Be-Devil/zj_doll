package com.imlianai.dollpub.app.modules.support.shopkeeper.dao.impl;


import com.imlianai.dollpub.app.modules.support.shopkeeper.dao.ShopkeeperIncomeRecordDao;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperIncomeRecord;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wurui
 * @create 2018-04-26 18:03
 **/
@Repository
public class ShopkeeperIncomeRecordDaoImpl implements ShopkeeperIncomeRecordDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int addRecord(ShopkeeperIncomeRecord record) {
        return jdbcHandler.executeSql("INSERT INTO shopkeeper_income_record(uid,chargeUid,chargeMoney,incomeRatioId,divideIncome,level,inviteNum,remark,createTime) " +
                "VALUES(?,?,?,?,?,?,?,?,NOW())", record.getUid(), record.getChargeUid(), record.getChargeMoney(), record.getIncomeRatioId(), record.getDivideIncome(), record.getLevel(), record.getInviteNum(), record.getRemark());
    }

    @Override
    public List<ShopkeeperIncomeRecord> getRecordList(long uid) {
        return jdbcHandler.queryBySql(ShopkeeperIncomeRecord.class, "SELECT * FROM shopkeeper_income_record WHERE uid=? ORDER BY createTime DESC", uid);
    }

    @Override
    public List<ShopkeeperIncomeRecord> getRecordSameMonth(long uid) {
        return jdbcHandler.queryBySql(ShopkeeperIncomeRecord.class,"select * from shopkeeper_income_record where date_format(createTime,'%Y%m')=date_format(curdate(),'%Y%m') and uid=?",uid);
    }

    @Override
    public List<ShopkeeperIncomeRecord> getRecordSameDayRank() {
        return jdbcHandler.queryBySql(ShopkeeperIncomeRecord.class,"select * from shopkeeper_income_record where to_days(createTime) = to_days(now())");
    }
}
