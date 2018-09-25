package com.imlianai.dollpub.app.schedule;

import com.imlianai.dollpub.app.modules.support.machine.service.MachineService;
import com.imlianai.rpc.support.common.BaseLogger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 机器相关定时任务
 * @author wurui
 * @create 2018-02-28 17:35
 **/
@Component
public class MachineTask {

    @Resource
    private MachineService machineService;

    private BaseLogger logger = BaseLogger.getLogger(getClass());

    /**
     * 定时更新所有机器的最新设置
     */
    public void handleUpdataMachineSetting(){
        logger.info("MachineTask ====> handleUpdataMachineSetting()");
        machineService.initAll();
    }

}
