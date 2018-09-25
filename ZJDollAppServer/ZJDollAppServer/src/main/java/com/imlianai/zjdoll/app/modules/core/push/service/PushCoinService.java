package com.imlianai.zjdoll.app.modules.core.push.service;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.pushcoin.PushCoinBox;
import com.imlianai.zjdoll.domain.pushcoin.PushCoinBoxPool;
import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;

import java.util.List;
import java.util.Map;

/**
 * @author wurui
 * @create 2018-05-22 21:04
 **/
public interface PushCoinService {

    BaseRespVO apply(long uid, int busId, int customerId);
    BaseRespVO push(long uid, int busId, int customerId,long optId);
    BaseRespVO operate(long uid, int busId);
    BaseRespVO query(long optId, int customerId);
    BaseRespVO finish(long uid, int busId, int customerId);
    BaseRespVO getStatus(int busId,int customerId);

    /**
     * 处理超时记录
     */
    void handleTimeOutRecord();

    /**
     * 币转钻石(用于doll_pub_app 出币回调)
     * @param uid
     * @param busId
     * @param optId
     * @param outCoin
     * @param isFinal
     * @return
     */
    int CoinToJewel(long uid,int busId,long optId,int outCoin,int isFinal);

    /**
     * 获得宝箱进度
     * @param busId
     * @return
     */
    BaseRespVO getBoxValue(int busId);

    /**
     * 用户点击宝箱开奖
     * @param busId
     * @param uid
     * @return
     */
    BaseRespVO openBox(int busId,long uid);


    /**
     * 计算宝箱峰值或初始化宝箱
     * @param dollBus
     */
    void calcBox(DollBus dollBus);
    PushCoinBox getPushCoinBox(int busId);
    PushCoinBoxPool getPushCoinBoxPool();

    /**
     * 计算奖池
     * @param coin
     */
    void calcBonus(int coin);

    /**
     * 获取操作记录
     * @param busId
     * @param optId
     * @param uid
     * @return
     */
    BaseRespVO getOperatorRecord(int busId,long optId,long uid);

    BaseRespVO getOperatorRecordList(int busId,long optId,long uid);

    /**
     * 宝箱点送
     * @param busId
     * @param uid
     * @param exJewel
     * @param exCoin
     * @param exScore
     * @param exCoupon
     * @param exDoll
     * @return
     */
    BaseRespVO addPointGiveBoxRecord(int busId,long uid,int exJewel,int exCoin,int exScore,int exCoupon,List<Integer> exDoll);

    /**
     * 获取点送记录列表
     * @param uid
     * @return
     */
    BaseRespVO getPointGiveBoxRecordList(long uid);

    /**
     * 删除点送记录
     * @param id
     * @return
     */
    BaseRespVO deletePointGiveBoxRecordById(int id);


    /**
     * 处理点送
     * @param openBoxLogId
     * @param userBase
     * @param userGeneral
     * @param dollBus
     * @param optId
     * @param boxId
     */
    String handlePointGive(int openBoxLogId,UserBase userBase, UserGeneral userGeneral,DollBus dollBus, long optId, int boxId);





}
