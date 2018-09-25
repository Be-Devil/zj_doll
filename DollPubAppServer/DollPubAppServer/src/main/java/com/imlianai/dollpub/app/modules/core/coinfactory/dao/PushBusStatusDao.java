package com.imlianai.dollpub.app.modules.core.coinfactory.dao;

import com.imlianai.dollpub.domain.coinfactory.PushBusStatus;

import java.util.List;

/**
 * @author wurui
 * @create 2018-03-28 17:34
 **/
public interface PushBusStatusDao {

    int insert(PushBusStatus pushBusStatus,int playTime);
    int updateEndTime(int busId,int playTime);
    int updateLockState(int busId);
    int delete(int busId);
    PushBusStatus get(int busId);

    /**
     * 获取结束时间超过当前多少秒的记录
     * @param second
     * @return
     */
    List<PushBusStatus> getEndTimeTimeOutRecord(int second);

    /**
     * 结束时间超过当前时间的记录
     * @return
     */
    List<PushBusStatus> getEndTimeTimeOutRecord();
    /**
     * 获取所有推币机状态
     * @return
     */
    List<PushBusStatus> getAllPushBusStatus();

}
