package com.imlianai.dollpub.app.modules.support.shopkeeper.dao;

import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperIncomeRatio;

import java.util.List;

/**
 * 店主收入比例dao
 * @author wurui
 * @create 2018-04-26 17:57
 **/
public interface ShopkeeperIncomeRatioDao {
    ShopkeeperIncomeRatio getRatioById(int id);
    List<ShopkeeperIncomeRatio> getRatioList();
}
