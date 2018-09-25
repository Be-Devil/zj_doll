package com.imlianai.dollpub.app.modules.core.coinfactory.service;

import com.imlianai.dollpub.app.modules.core.coinfactory.vo.*;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

/**
 * 水果机相关服务
 * @author wurui
 * @create 2018-06-19 10:16
 **/
public interface PushCoinFruitService {


    /**
     * 查询水果机中奖
     * @param reqVO
     * @return
     */
    VirtualFruitsQueryRespVO query(VirtualFruitsQueryReqVO reqVO);


    /**
     * 确认中奖结果并领奖
     * @param reqVO
     * @return
     */
    BaseRespVO affirm(VirtualFruitsAffirmReqVO reqVO);


    /**
     * 验证中奖结果有效性
     * @param reqVO
     * @return
     */
    BaseRespVO verify(VirtualFruitsVerifyReqVO reqVO);


    /**
     * 拉霸机投币
     * @param reqVO
     * @return
     */
    BaseRespVO slots(VirtualFruitsSlotsReqVO reqVO);


    /**
     * 初始化水果机核心组件
     */
    void initFruitCore();


    /**
     * 处理前置中奖分配(投币时处理)
     * @param optId
     */
    void handleBeforeAllot(long optId);


}
