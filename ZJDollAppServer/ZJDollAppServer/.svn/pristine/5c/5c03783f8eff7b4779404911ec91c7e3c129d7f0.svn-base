package com.imlianai.zjdoll.app.modules.support.event.invite20180320.dao;

import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteEnergyRank;

import java.util.List;

/**
 * 能量值榜单
 * @author wurui
 * @create 2018-03-20 18:38
 **/
public interface Event20180320InviteEnergyRankDao {


    int addEnergyRank(Event20180320InviteEnergyRank energyRank);

    Event20180320InviteEnergyRank getEnergyRankByUid(long uid);

    List<Event20180320InviteEnergyRank> getEnergyRankLimit50();
    List<Event20180320InviteEnergyRank> getEnergyRankLimit30();
    /**
     * 加能量
     * @param uid
     * @param energy
     * @return
     */
    int addEnergy(long uid,int energy);


    /**
     * 初始化榜单
     * @param uid
     * @return
     */
    int init(long uid);

    /**
     * 更新邀请码使用次数
     * @param uid
     * @return
     */
    int updateUsedTimes(long uid);



}
