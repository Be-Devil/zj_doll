package com.imlianai.dollpub.app.modules.core.coinfactory.dao;

import com.imlianai.dollpub.domain.coinfactory.virtual.base.PushBusStatusVirtual;

import java.util.List;


/**
 * @author wurui
 * @create 2018-03-28 17:34
 **/
public interface PushBusStatusVirtualDao {

    int insert(PushBusStatusVirtual pushBusStatusVirtual, int playTime);
    int delete(int busId,long optId);
    PushBusStatusVirtual get(int busId,long optId);
    PushBusStatusVirtual get(int busId,long uid,int customerId);
    int updateEndTime(int busId,long optId,int playTime);

    /**
     * 结束时间超过当前时间的记录
     * @return
     */
    List<PushBusStatusVirtual> getEndTimeTimeOutRecord();

    /**
     * 获取当前机器的所有上机状态
     * @param busId
     * @return
     */
    List<PushBusStatusVirtual> getListByBusId(int busId);

    /**
     * 更新权重
     * @param busId
     * @param optId
     * @param weight
     * @return
     */
    int updateWeight(int busId,long optId,int weight);
}
