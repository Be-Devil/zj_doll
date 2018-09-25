package com.imlianai.dollpub.app.modules.core.coinfactory.dao;

import com.imlianai.dollpub.domain.coinfactory.MachinePushCoin;

/**
 * 带路由的dao操作
 * 规则：通过商户组区分表名
 * @author wurui
 * @create 2018-04-03 16:36
 **/
public interface PushCoinOptRouterDao {

    long insert(int groupId,MachinePushCoin pushCoin);
    MachinePushCoin getOne(int groupId,long optId);
    MachinePushCoin getOne(int groupId,long optId,long uid);


    /**
     * 更新投币or出币
     * @param type 0：更新投币个数，1：更新出币个数
     * @param coin
     * @return
     */
    int updateCoin(int groupId,long optId,int coin,int type);

    /**
     * 锁定操作记录
     * @param optId
     * @return
     */
    int updateState(int groupId,long optId);



}
