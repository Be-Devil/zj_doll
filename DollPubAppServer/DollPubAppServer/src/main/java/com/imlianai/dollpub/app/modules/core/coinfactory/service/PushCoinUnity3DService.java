package com.imlianai.dollpub.app.modules.core.coinfactory.service;

import com.imlianai.dollpub.app.modules.core.coinfactory.vo.EntryVirtualRoomReqVO;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.PushCoinVirtualConfig;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import java.util.Map;

/**
 * 处理3D推币机逻辑
 * @author wurui
 * @create 2018-06-30 11:05
 **/
public interface PushCoinUnity3DService {

    /**
     * 上机
     * @param uid
     * @param busId
     * @param customer
     * @return
     */
    BaseRespVO apply(long uid, int busId, Customer customer,int weight);


    /**
     * 投币
     * @param uid
     * @param busId
     * @param customer
     * @return
     */
    BaseRespVO putCoin(long uid, int busId,Customer customer,int weight);


    /**
     * 操作(摆动)
     * @param uid
     * @param busId
     * @return
     */
    BaseRespVO operate(long uid,int busId,Customer customer);


    /**
     * 主动下机
     * @param uid
     * @param busId
     * @return
     */
    BaseRespVO finish(long uid,int busId,Customer customer);

    /**
     * 获取机器配置
     * @param busId
     * @return
     */
    PushCoinVirtualConfig getVirtualPushCoinConfig(int busId);

    BaseRespVO entryVirtualCoinPushRoom(EntryVirtualRoomReqVO reqVO);
    BaseRespVO laveVirtualCoinPushRoom(EntryVirtualRoomReqVO reqVO);
    BaseRespVO handleCallback(String p1,String p2,String md5_value, int p4,int p5,int p6,int p7,int p8,int p9,int p10,int p11,DollBus dollBus, long uid, Customer customer);


    int updateEndTime(long uid,long optId,int busId, Customer customer);

    int getGameTime(int busId);

    void handleTimeOutRecord();

    /**
     * 获取虚拟推币机用户是否在上机状态
     * @param uid
     * @param busId
     * @param customer
     * @return
     */
    BaseRespVO getVirtualByUser(long uid, int busId, Customer customer);

}
