package com.imlianai.dollpub.app.modules.core.api.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.core.api.service.CustomerDollBusService;
import com.imlianai.dollpub.app.modules.core.api.service.DollBusStatusService;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.DollBusStatus;
import com.imlianai.dollpub.machine.iface.IMachineRemoteService;
import com.imlianai.dollpub.machine.iface.domain.MachineOptRecord;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.StringUtil;

/**
 * 机器状态相关定时任务
 */
@Component
public class DollBusStatusTask {

    private BaseLogger logger = BaseLogger.getLogger(getClass());

    @Resource
    private DollBusStatusService dollBusStatusService;

    @Resource
    private CustomerService customerService;

    @Resource
    private DollBusService dollBusService;

    @Resource
    private CustomerDollBusService customerDollBusService;

    @Reference
    private IMachineRemoteService iMachineRemoteService;

    /**
     * 扫描maxPlayTime<=now()的操作记录并清除
     */
    public void invalidBusMaxPlayTimeOpt() {
        List<DollBusStatus> list = dollBusStatusService.getInvaildRecord(0);// 0:扫描 maxPlayTime<=now()
        logger.info("invalidBusMaxPlayTimeOpt==>" + JSON.toJSONString(list));
        if (!StringUtil.isNullOrEmpty(list)) {
            for (DollBusStatus busStatus : list) {
                MachineOptRecord machineOptRecord = iMachineRemoteService.queryMachineOptResult(busStatus.getOptId());
                if (machineOptRecord != null) {
                    Customer customer = customerService.getCustomerById(machineOptRecord.getCustomerId());
                    if (customer != null) {
                        customerDollBusService.returnMachineResult(machineOptRecord.getDeviceId(),
                                machineOptRecord.getBusId(), machineOptRecord.getOptId(),
                                machineOptRecord.getResult() == 1,
                                customer.getGroupId(), "定时器回调");
                    }
                }
            }
        }
    }


    /**
     * 扫描keepTime<=now()的操作记录并清除
     */
    public void invalidBusKeepTimeOpt() {
        List<DollBusStatus> list = dollBusStatusService.getInvaildRecord(1);// 1:扫描 keepTime<=now()
        if (!StringUtil.isNullOrEmpty(list)) {
            for (DollBusStatus busStatus : list) {
                logger.info("keepTime================>" + JSON.toJSONString(busStatus));
                if (dollBusService.abandon(busStatus.getUserId(), busStatus.getBusId()) > 0) {
                    logger.info("invalidBusKeepTimeOpt===========>" + JSON.toJSONString(busStatus));
                }
            }
        }
    }
}
