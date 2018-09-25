package com.imlianai.zjdoll.app.modules.support.event.invite20180320.service;


import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteEnergyRank;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteEnergyRecord;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteUsedTimes;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.dto.Event20180320InviteEnergyRankDTO;

import java.util.List;
import java.util.Map;

/**
 * 邀请奖励服务
 * @author wurui
 * @create 2018-03-20 19:12
 **/
public interface Event20180320InviteEnergyService {

    /**
     * 获取榜单列表
     * @return
     */
    List<Event20180320InviteEnergyRankDTO> getRankList();


    Event20180320InviteEnergyRank getOneByUid(long uid);

    /**
     * 处理邀请者能获取的能量值
     * @param inviteUid
     * @param code
     */
    void inviteUidEnergyHandle(long uid, long inviteUid,long code,String remark);


    /**
     * 处理好友抓中后的能量赠送
     */
    void successEnergyHandle(long uid);


    /**
     * 获取能量记录列表
     * @param uid
     * @return
     */
    List<Event20180320InviteEnergyRecord> getRecordByInviteUidTop(long inviteUidTop);

    /**
     * 获取单条榜单
     * @param uid
     * @return
     */
    Event20180320InviteEnergyRank getRankByUid(long uid);

    /**
     * 拿到前50的排名
     * @return
     */
    public int getRank(long uid);


    /**
     * 发送邀请成功消息
     * @param user
     * @param energy
     */
    public void sendInviteSuccessMsg(UserGeneral user,int energy);

    /**
     * 发送好友抓中第一个娃娃的消息
     */
    public void handleCatchDollSuccess(long uid);


    /**
     * 邀请的好友又邀请了人发消息
     * @param uid
     * @param inviteUid
     * @param energy
     */
    void sendMsgA_B_C(long uid,long inviteUid, int energy);


    /**
     * 每晚8点通知
     */
    void sendMsg20Min();

    /**
     * 活动结束通知
     */
    void eventEndMsg();


    int init(long uid);

    int updateUsedTimes(long uid);

    Event20180320InviteUsedTimes getUsedTimeByUid(long uid);
}
