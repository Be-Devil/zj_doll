package com.imlianai.dollpub.app.schedule;

import com.imlianai.dollpub.app.modules.core.dice.service.DiceService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 骰子机定时任务
 * @author wurui
 * @create 2018-06-05 9:59
 **/
@Component
public class DiceTask {

    @Resource
    private DiceService diceService;

    /**
     * 定期处理骰子机超时结果
     */
    public void handleInvalidDiceBusOpt(){
        diceService.handleTimeOutRecord();
    }

}
