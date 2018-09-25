package com.imlianai.dollpub.app.modules.support.shopkeeper.dao;

import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperIncomeRecord;

import java.util.List;

/**
 * 店主收入记录dao
 * @author wurui
 * @create 2018-04-26 18:00
 **/
public interface ShopkeeperIncomeRecordDao {
    /**
     * 新增记录
     * @param record
     * @return
     */
    int addRecord(ShopkeeperIncomeRecord record);

    /**
     * 查询收入记录列表
     * @param uid
     * @return
     */
    List<ShopkeeperIncomeRecord> getRecordList(long uid);

    /**
     * 获取当月总收入
     * @param uid
     * @return
     */
    List<ShopkeeperIncomeRecord> getRecordSameMonth(long uid);

    /**
     * 获取当日收入榜单
     * @return
     */
    List<ShopkeeperIncomeRecord> getRecordSameDayRank();

}
