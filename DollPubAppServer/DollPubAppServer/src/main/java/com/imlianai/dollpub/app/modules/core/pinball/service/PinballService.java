package com.imlianai.dollpub.app.modules.core.pinball.service;

import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import java.util.List;

/**
 * 弹珠机相关服务
 * @author wurui
 * @create 2018-07-13 22:04
 **/
public interface PinballService {

    /**
     * 处理指令回调结果
     * @param dollBus
     * @param deviceId
     * @param hexs
     */
    void handleResult(DollBus dollBus, final String deviceId, final List<String> hexs);


    /**
     * 开局指令
     * 指令ID  0xa2(非兼容模式，返回a2,参数不同)
     * @param busId 机器ID
     * @param uid 玩家ID
     * @param gameId 游戏局数
     * @param time_out 超时时间，单位秒。超过此时间自动结束游戏
     * @param bet 押分  取值范围为0-255
     * @return
     */
    public BaseRespVO start(int busId,long uid,int gameId,int time_out,int bet);


    /**
     * 押分
     * @param busId
     * @param uid
     * @param bet 取值范围为0-255
     * @return
     */
    BaseRespVO bet(int busId, long uid, int bet);

    /**
     * 下球
     * @param busId
     * @param uid
     * @return
     */
    BaseRespVO ball(int busId, long uid);

    /**
     * 力度
     * @param busId
     * @param uid
     * @param power 取值范围为0-255
     * @return
     */
    BaseRespVO force(int busId, long uid, int power);

    /**
     * 发射
     * @param busId
     * @param uid
     * @param power 取值范围为0-255
     * @param bet 取值范围为0-255
     * @return
     */
    BaseRespVO launchCmd(int busId, long uid, int power, int bet);


    BaseRespVO set(int busId,long uid,int item,int low,int high);

}
