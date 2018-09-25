package com.imlianai.dollpub.app.modules.support.machine.cmd;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;

import com.imlianai.dollpub.app.modules.support.machine.service.MachineService;
import com.imlianai.dollpub.app.modules.support.machine.vo.MachineClawVoltageSetReqVO;
import com.imlianai.dollpub.app.modules.support.machine.vo.MachineMotorSpeedSetReqVO;
import com.imlianai.dollpub.app.modules.support.machine.vo.MachineSettingQueryReqVO;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.machine.MachineBasicData;
import com.imlianai.dollpub.domain.machine.MachineClawVoltage;
import com.imlianai.dollpub.domain.machine.MachineMotorSpeed;
import com.imlianai.dollpub.domain.machine.dto.*;
import com.imlianai.dollpub.machine.iface.IMachineRemoteService;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import java.util.List;

/**
 * 机器相关接口
 *
 * @author wurui
 * @create 2018-02-27 14:16
 **/
//@Api("机器相关接口")
@Component("machine")
public class CmdMachine extends RootCmd {

    @Resource
    private MachineService machineService;

    @Resource
    private DollBusService dollBusService;

    @Resource
    private CustomerService customerService;

    @Reference
    private IMachineRemoteService iMachineRemoteService;


    @ApiHandle
    @Path("api/machine/queryMachineSetting")
    @ApiOperation(value = "查询机器设置", notes = "查询机器设置", httpMethod = "POST", response = BaseRespVO.class)
    public BaseRespVO queryMachineSetting(MachineSettingQueryReqVO vo){
        if (vo.getType() != 0 && vo.getBusId() != 0){
            DollBus dollBus = dollBusService.getDollBus(vo.getBusId());
            if (dollBus != null){
                if (vo.getType() == 1 || vo.getType() == 2 || vo.getType() == 3){
                    BaseRespVO baseRespVO = new BaseRespVO(100,true,"数据请求成功");
                    if (vo.getType() == 1){
                        baseRespVO.setData(new BasicDataSetDTO((MachineBasicData) machineService.machineSettingQuery(dollBus.getDeviceId(),vo.getType())));
                    }
                    if (vo.getType() == 2){
                        baseRespVO.setData(new ClawVoltageSetDTO((MachineClawVoltage) machineService.machineSettingQuery(dollBus.getDeviceId(),vo.getType())));
                    }
                    if (vo.getType() == 3){
                        baseRespVO.setData(new MotorSpeedSetDTO((MachineMotorSpeed) machineService.machineSettingQuery(dollBus.getDeviceId(),vo.getType())));
                    }
                    return baseRespVO;
                }else {
                    return new BaseRespVO(0,false,"数据请求失败,类型无效！" );
                }
            }
        }
        return new BaseRespVO(0,false,"数据请求失败=>" + JSON.toJSONString(vo));
    }
//
//    @ApiHandle
//    @Path("api/machine/setMachineBasicData")
//    @ApiOperation(value = "发送机器基础数据设置指令", notes = "发送机器基础数据设置指令", httpMethod = "POST", response = BaseRespVO.class)
//    public BaseRespVO setMachineBasicData(MachineBasicDataSetReqVO vo){
//        logger.info("setMachineBasicData===>" + JSON.toJSONString(vo));
//        if (!StringUtil.isNullOrEmpty(vo)){
//            if (vo.getBusId() != 0){
//                int result = machineService.sendMachineBasicDataSet(new MachineBasicDataSetDTO_RPC(vo));
//                if (result > 0){
//                    return new BaseRespVO(0,false,"发送机器基础数据指令成功...");
//                }
//            }
//        }
//        return new BaseRespVO(0,false,"发送机器基础数据指令失败!");
//    }
//
    @ApiHandle
    @Path("api/machine/setMachineClawVoltage")
    @ApiOperation(value = "发送机器爪力电压设置指令", notes = "发送机器爪力电压设置指令", httpMethod = "POST", response = BaseRespVO.class)
    public BaseRespVO setMachineClawVoltage(MachineClawVoltageSetReqVO vo){
        logger.info("setMachineClawVoltage===>" + JSON.toJSONString(vo));
        if (!StringUtil.isNullOrEmpty(vo)){
            if (vo.getBusId() != 0){
                int result = machineService.sendMachineClawVoltageSet(new MachineClawVoltageSetDTO_RPC(vo));
                if (result > 0){
                    return new BaseRespVO(0,false,"发送机器爪力电压设置指令成功...");
                }
            }
        }
        return new BaseRespVO(0,false,"发送机器爪力电压设置指令失败!");
    }

    @ApiHandle
    @Path("api/machine/setMachineMotorSpeed")
    @ApiOperation(value = "发送机器马达速度设置指令", notes = "发送机器马达速度设置指令", httpMethod = "POST", response = BaseRespVO.class)
    public BaseRespVO setMachineMotorSpeed(MachineMotorSpeedSetReqVO vo){
        logger.info("setMachineMotorSpeed===>" + JSON.toJSONString(vo));
        if (!StringUtil.isNullOrEmpty(vo)){
            if (vo.getBusId() != 0){
                int result = machineService.sendMachineMotorSpeedSet(new MachineMotorSpeedSetDTO_RPC(vo));
                if (result > 0){
                    return new BaseRespVO(0,false,"发送机器马达速度设置指令成功...");
                }
            }
        }
        return new BaseRespVO(0,false,"发送机器马达速度设置指令失败!");
    }
//
//
//    @ApiHandle
//    @Path("api/machine/initMachineSetting")
//    @ApiOperation(value = "初始化机器相关设置", notes = "初始化", httpMethod = "POST", response = BaseRespVO.class)
//    public BaseRespVO initMachineSetting(MachineInitReqVO vo){
//        if (vo.getType() == 0){
//            machineService.initAll();
//            return new BaseRespVO(0,false,"初始化所有机器设置成功");
//        }else if (vo.getType() == 1){
//            if (vo.getBusId() != 0){
//                machineService.initMachineBasicData(vo.getBusId());
//                machineService.initMachineClawVoltage(vo.getBusId());
//            }else {
//                return new BaseRespVO(0,false,"初始化单台机器设置失败,请填写busId !");
//            }
//        }else {
//            return new BaseRespVO(0,false,"类型错误,不是有效的类型=>" + vo.getType());
//        }
//        return new BaseRespVO(0,false,"初始化单台机器设置成功,busId=>" + vo.getBusId());
//    }

    @ApiHandle
    @Path("api/machine/moreMachineSettingQuery")
    public BaseRespVO moreMachineSettingQuery(MachineSettingQueryReqVO vo) {
            List<DollBus> dollBusList = null;
            if (vo.getCustomerId() == 0){
                dollBusList = dollBusService.getAllDollBus(0,1000);
            }else {
                Customer customer = customerService.getCustomerById(vo.getCustomerId());
                if (customer != null) {
                    dollBusList = dollBusService.getDollBusByGroupId(customer.getGroupId(), 0, 1000);
                }
            }
            logger.info("dollBusList=====>" + JSON.toJSONString(dollBusList));
            if (!StringUtil.isNullOrEmpty(dollBusList)) {
                List<String> deviceIds = Lists.newArrayList();
                for (DollBus dollBus : dollBusList) {
                    if (dollBus.getStatus() != 2){
                        deviceIds.add(dollBus.getDeviceId());
                    }
                }
                logger.info("deviceIds=====>" + JSON.toJSONString(deviceIds));
                BaseRespVO baseRespVO = new BaseRespVO(100, true, "请求数据成功");
                baseRespVO.setData(machineService.moreMachineSettingQuery(deviceIds, vo.getType()));
                return baseRespVO;
            }
        return new BaseRespVO(0, false, "请求数据失败");
    }

    @ApiHandle
    @Path("api/machine/initMachineSet")
    public BaseRespVO initMachineSet(Integer type) {
        return new BaseRespVO(100,true,"初始化结果=>" + machineService.initAll(type));
    }

}
