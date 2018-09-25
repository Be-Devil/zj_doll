package com.imlianai.dollpub.app.schedule;

import com.imlianai.dollpub.app.modules.core.coinfactory.service.PushCoinMachineService;
import com.imlianai.dollpub.app.modules.core.coinfactory.service.PushCoinUnity3DService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 推币机任务
 * @author wurui
 * @create 2018-03-29 11:59
 **/
@Component
public class PushCoinBusTask {

    @Resource
    private PushCoinMachineService pushCoinMachineService;

    @Resource
    private PushCoinUnity3DService pushCoinUnity3DService;

    /**
     * 定期处理推币机超时结果
     */
    public void handleInvalidPushCoinBusOpt(){
        //实体
        pushCoinMachineService.resultFinal();
        //虚拟
        pushCoinUnity3DService.handleTimeOutRecord();
    }

    /**
     * 定期查询推币机光眼计数
     */
    public void handleQueryPushCoinResult(){
        pushCoinMachineService.perSecondUpdateOutCoin();
    }


}
