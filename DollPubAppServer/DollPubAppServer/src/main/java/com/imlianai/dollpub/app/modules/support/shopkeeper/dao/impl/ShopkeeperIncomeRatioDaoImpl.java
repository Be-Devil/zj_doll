package com.imlianai.dollpub.app.modules.support.shopkeeper.dao.impl;

import com.imlianai.dollpub.app.modules.support.shopkeeper.dao.ShopkeeperIncomeRatioDao;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperIncomeRatio;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wurui
 * @create 2018-04-26 17:58
 **/
@Repository
public class ShopkeeperIncomeRatioDaoImpl implements ShopkeeperIncomeRatioDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public ShopkeeperIncomeRatio getRatioById(int id) {
        return jdbcHandler.queryOneBySql(ShopkeeperIncomeRatio.class,"SELECT * FROM shopkeeper_income_ratio WHERE id=?",id);
    }

    @Override
    public List<ShopkeeperIncomeRatio> getRatioList() {
        return jdbcHandler.queryBySql(ShopkeeperIncomeRatio.class,"SELECT * FROM shopkeeper_income_ratio");
    }
}
