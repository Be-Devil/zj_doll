package com.imlianai.dollpub.app.modules.core.dice.service;


import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import java.util.List;

/**
 * @author wurui
 * @create 2018-06-01 11:54
 **/
public interface DiceService {

    /**
     * 开始游戏
     * @param busId
     * @return
     */
    long start(int busId,long uid,int type);


    BaseRespVO stop(long optId,int busId,long uid);

    /**
     * 判断是否可上机
     * @param busId
     * @return
     */
    boolean isPlay(int busId);


    void resultHandle(DollBus dollBus, final String deviceId, final List<String> hexs);


    void handleTimeOutRecord();


}