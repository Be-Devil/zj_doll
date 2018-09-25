package com.imlianai.zjdoll.app.modules.core.push.dao;

import com.google.common.collect.Maps;
import com.imlianai.dollpub.domain.coinfactory.MachinePushCoin;

import java.util.List;

/**
 * 带路由的dao操作
 * 规则：通过商户组区分表名
 * @author wurui
 * @create 2018-04-03 16:36
 **/
public interface PushCoinOptRouterDao {

    long insert(MachinePushCoin pushCoin);
    MachinePushCoin getOne(long optId);
    MachinePushCoin getOne(long optId, long uid);
    List<MachinePushCoin> getTimeOutRecord();
    int updateTimeOutStatus(int id);

    /**
     * 获取用户上机列表
     * @return
     */
    List<MachinePushCoin> getUserPlayRecordList();

    /**
     * 更新投币or出币
     * @param optId
     * @param eCoin
     * @param vCoin
     * @param type 0：更新投币个数，1：更新出币个数
     * @return
     */
    int updateCoin(long optId, int eCoin,int vCoin, int type);

    /**
     * 锁定操作记录
     * @param optId
     * @return
     */
    int updateState(long optId);


    /**
     * 更新钻石
     * @param jewel
     * @param optId
     * @return
     */
    int updateJewel(int jewel,long optId);

    /**
     * 更新游戏币
     * @param coin
     * @param optId
     * @return
     */
    int updateCoin(int coin,long optId);


    /**
     * 更新钻石状态
     * @param optId
     * @return
     */
    int updateJewelStatus(long optId);


}
