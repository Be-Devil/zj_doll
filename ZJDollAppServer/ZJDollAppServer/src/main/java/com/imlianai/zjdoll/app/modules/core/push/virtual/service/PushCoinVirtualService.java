package com.imlianai.zjdoll.app.modules.core.push.virtual.service;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.core.push.virtual.vo.*;
import com.imlianai.zjdoll.app.modules.core.push.virtual.vo.VirtualFruitsVerifyReqVO;

/**
 * @author wurui
 * @create 2018-07-04 11:51
 **/
public interface PushCoinVirtualService {

    /**
     * 进入房间
     * @param reqVO
     * @return
     */
    BaseRespVO entryVirtualCoinPushRoom(EntryVirtualRoomReqVO reqVO);


    /**
     * 离开房间
     * @param reqVO
     * @return
     */
    BaseRespVO laveVirtualCoinPushRoom(EntryVirtualRoomReqVO reqVO);


    /**
     * 上机
     * @return
     */
    BaseRespVO apply(OperateVirtualReqVO reqVO);


    /**
     * 投币
     * @return
     */
    BaseRespVO push(OperateVirtualReqVO reqVO);

    /**
     * 操作(摆动)
     * @return
     */
    BaseRespVO operate(OperateVirtualReqVO reqVO);

    /**
     * 主动下机
     * @return
     */
    BaseRespVO finish(OperateVirtualReqVO reqVO);


    /**
     * 水果机查奖
     * @param reqVO
     * @return
     */
    BaseRespVO query(VirtualFruitsQueryReqVO reqVO);

    /**
     * 确认中奖
     * @param reqVO
     * @return
     */
    BaseRespVO affirm(VirtualFruitsAffirmReqVO reqVO);

    /**
     * 验证中奖结果
     * @param reqVO
     * @return
     */
    BaseRespVO verify(VirtualFruitsVerifyReqVO reqVO);


    /**
     * 更新游戏时间到最新
     * @param reqVO
     * @return
     */
    BaseRespVO refresh(OperateVirtualReqVO reqVO);

    /**
     * 拉霸机查奖
     * @param reqVO
     * @return
     */
    BaseRespVO slots(VirtualFruitsSlotsReqVO reqVO);

    /**
     * 币转钻石(用于doll_pub_app 出币回调)
     * @param uid
     * @param busId
     * @param optId
     * @param outCoin
     * @param win
     * @param isFinal
     * @return
     */
    int CoinToJewel(long uid,int busId,long optId,int outCoin,int win,int isFinal);

    /**
     * 处理虚拟推币机配置设置(发消息方式)
     */
    void handleVirtualConfig();


    /**
     * 处理超时记录
     */
    void handleTimeOutRecord();

}
