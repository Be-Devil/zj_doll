package com.imlianai.dollpub.app.pvd;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.imlianai.dollpub.app.iface.IAppBusSetRemoteService;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.app.modules.support.machine.service.MachineService;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.machine.dto.MachineBasicDataSetDTO_RPC;
import com.imlianai.dollpub.domain.machine.dto.MachineClawVoltageSetDTO_RPC;
import com.imlianai.dollpub.domain.machine.dto.MachineMotorSpeedSetDTO_RPC;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.StringUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wurui
 * @create 2018-03-05 11:36
 **/
@Service(interfaceClass = IAppBusSetRemoteService.class)
public class IAppBusSetRemoteServiceImpl implements IAppBusSetRemoteService {

    private BaseLogger logger = BaseLogger.getLogger(getClass());

    @Resource
    private MachineService machineService;

    @Resource
    private CustomerService customerService;

    @Resource
    private DollBusService dollBusService;


    @Override
    public List moreMachineSettingQuery(int customerId,int type) {

        //超管
        if (customerId == 0){
            List<DollBus> dollBusList = dollBusService.getAllDollBus(0,1000);
            if (!StringUtil.isNullOrEmpty(dollBusList)){
                List<String> deviceIds = Lists.newArrayList();
                for (DollBus dollBus : dollBusList){
                    if (dollBus.getStatus() != 2){
                        deviceIds.add(dollBus.getDeviceId());
                    }
                }
                logger.info("moreMachineSettingQuery : deviceIds=====>" + JSON.toJSONString(deviceIds));
                return machineService.moreMachineSettingQuery(deviceIds,type);
            }
        }

        Customer customer = customerService.getCustomerById(customerId);
        if (customer != null){
            List<DollBus> dollBusList = dollBusService.getDollBusByGroupId(customer.getGroupId(),0,1000);
            logger.info("moreMachineSettingQuery : dollBusList=====>" + JSON.toJSONString(dollBusList));
            if (!StringUtil.isNullOrEmpty(dollBusList)){
                List<String> deviceIds = Lists.newArrayList();
                for (DollBus dollBus : dollBusList){
                    if (dollBus.getStatus() != 2){
                        deviceIds.add(dollBus.getDeviceId());
                    }
                }
                logger.info("moreMachineSettingQuery : deviceIds=====>" + JSON.toJSONString(deviceIds));
                return machineService.moreMachineSettingQuery(deviceIds,type);
            }
        }
        return Lists.newArrayList();
    }

    @Override
    public int setMachineBasicData(MachineBasicDataSetDTO_RPC basicDataSetDTO_rpc) {
        return machineService.sendMachineBasicDataSet(basicDataSetDTO_rpc);
    }

    @Override
    public int setMachineClawVoltage(MachineClawVoltageSetDTO_RPC clawVoltageSetDTO_rpc) {
        return machineService.sendMachineClawVoltageSet(clawVoltageSetDTO_rpc);
    }

    @Override
    public int setMachineMotorSpeed(MachineMotorSpeedSetDTO_RPC motorSpeedSetDTO_rpc) {
        return machineService.sendMachineMotorSpeedSet(motorSpeedSetDTO_rpc);
    }
}
