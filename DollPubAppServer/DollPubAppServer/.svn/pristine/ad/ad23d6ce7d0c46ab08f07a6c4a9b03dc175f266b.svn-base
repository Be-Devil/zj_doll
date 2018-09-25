package com.imlianai.dollpub.app.modules.core.coinfactory.dao;

import com.imlianai.dollpub.domain.coinfactory.MachinePushCoin;


/**
 * @author wurui
 * @create 2018-03-28 15:17
 **/
public interface PushCoinMachineDao {
    long insert(MachinePushCoin pushCoin);
    MachinePushCoin getOne(long optId);
    MachinePushCoin getOne(long optId,long uid);


    /**
     * 更新投币or出币
     * @param type 0：更新投币个数，1：更新出币个数
     * @param coin
     * @return
     */
    int updateCoin(long optId,int coin,int type);

    /**
     * 锁定操作记录
     * @param optId
     * @return
     */
    int updateState(long optId);



    // ******************** machine_device *********************

    /**
     * 锁定更新机器状态
     *
     * 操作 machine_device 表
     *
     * @param deviceId
     * @param status
     * @param userId
     * @return
     */
    public int updateMachineLocked(String deviceId, int status, long userId);




}
