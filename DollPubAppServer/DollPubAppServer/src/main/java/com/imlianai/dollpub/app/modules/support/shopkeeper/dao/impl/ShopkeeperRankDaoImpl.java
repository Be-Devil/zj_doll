package com.imlianai.dollpub.app.modules.support.shopkeeper.dao.impl;

import com.imlianai.dollpub.app.modules.support.shopkeeper.dao.ShopkeeperRankDao;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperIncomeDay;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperIncomeMonth;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wurui
 * @create 2018-04-27 11:26
 **/
@Repository
public class ShopkeeperRankDaoImpl implements ShopkeeperRankDao {


    @Resource
    private JdbcHandler jdbcHandler;


    @Override
    public int insertOrUpdateIncomeDay(ShopkeeperIncomeDay incomeDay) {
        return jdbcHandler.executeSql("INSERT INTO shopkeeper_income_day(uid,date,head,name,dayIncome,createTime) VALUES(?,?,?,?,?,NOW()) ON DUPLICATE KEY UPDATE  dayIncome=dayIncome+?,updateTime=NOW()",
                incomeDay.getUid(),incomeDay.getDate(),incomeDay.getHead(),incomeDay.getName(),incomeDay.getDayIncome(),incomeDay.getDayIncome());
    }

    @Override
    public int insertOrUpdateIncomeMonth(ShopkeeperIncomeMonth incomeMonth) {
        return jdbcHandler.executeSql("INSERT INTO shopkeeper_income_month(uid,date,head,name,monthIncome,createTime) VALUES(?,?,?,?,?,NOW()) ON DUPLICATE KEY UPDATE  monthIncome=monthIncome+?,updateTime=NOW()",
                incomeMonth.getUid(),incomeMonth.getDate(),incomeMonth.getHead(),incomeMonth.getName(),incomeMonth.getMonthIncome(),incomeMonth.getMonthIncome());
    }

    @Override
    public List<ShopkeeperIncomeDay> getIncomeDayRank(String day) {
        return jdbcHandler.queryBySql(ShopkeeperIncomeDay.class,"SELECT * FROM shopkeeper_income_day WHERE date=? ORDER BY dayIncome DESC,createTime ASC LIMIT 20 ",day);
    }

    @Override
    public List<ShopkeeperIncomeMonth> getIncomeMonthRank(String month) {
        return jdbcHandler.queryBySql(ShopkeeperIncomeMonth.class,"SELECT * FROM shopkeeper_income_month WHERE date=? ORDER BY monthIncome DESC,createTime ASC LIMIT 20",month);
    }
}
