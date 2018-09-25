package com.imlianai.dollpub.app.modules.support.shopkeeper.dao;

import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperIncomeDay;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperIncomeMonth;

import java.util.List;

/**
 * @author wurui
 * @create 2018-04-27 11:24
 **/
public interface ShopkeeperRankDao {
    /**
     * 新增或更新日榜
     * @param incomeDay
     * @return
     */
    int insertOrUpdateIncomeDay(ShopkeeperIncomeDay incomeDay);

    /**
     * 新增或更新月榜
     * @param incomeMonth
     * @return
     */
    int insertOrUpdateIncomeMonth(ShopkeeperIncomeMonth incomeMonth);

    /**
     * 获取日榜
     * @return
     */
    List<ShopkeeperIncomeDay> getIncomeDayRank(String day);

    /**
     * 获取月绑
     * @return
     */
    List<ShopkeeperIncomeMonth> getIncomeMonthRank(String month);
}
