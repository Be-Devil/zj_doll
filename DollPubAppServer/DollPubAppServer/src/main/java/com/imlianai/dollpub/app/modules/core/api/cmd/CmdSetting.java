package com.imlianai.dollpub.app.modules.core.api.cmd;

import com.imlianai.dollpub.domain.machine.MachineMotorSpeed;
import com.imlianai.dollpub.domain.machine.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.api.service.CustomerDollBusService;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;

import com.imlianai.dollpub.app.modules.support.machine.service.MachineService;
import com.imlianai.dollpub.app.modules.support.machine.vo.MachineBasicDataSetReqVO;
import com.imlianai.dollpub.app.modules.support.machine.vo.MachineClawVoltageSetReqVO;
import com.imlianai.dollpub.app.modules.support.machine.vo.MachineSettingQueryReqVO;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.machine.MachineBasicData;
import com.imlianai.dollpub.domain.machine.MachineClawVoltage;
import com.imlianai.dollpub.machine.iface.IMachineRemoteService;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.SignCheck;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.StringUtil;

@Api("机器配置相关接口")
@Component("setting")
public class CmdSetting extends RootCmd {

    @Resource
    private CustomerService customerService;

    @Resource
    private CustomerDollBusService customerDollBusService;

    @Reference
    private IMachineRemoteService iMachineRemoteService;

    @Resource
    private MachineService machineService;

    @Resource
    private DollBusService dollBusService;



//    ------------------------------------------------- 以下是机器设置相关接口------------------------------------------------

    @SignCheck
    @ApiHandle
    @Path("api/setting/queryMachineSetting")
    @ApiOperation(value = "查询机器设置", notes = "查询机器设置", httpMethod = "POST", response = BaseRespVO.class)
    public BaseRespVO queryMachineSetting(MachineSettingQueryReqVO vo) {
        if (vo.getType() != 0 && vo.getBusId() != 0) {
            DollBus dollBus = dollBusService.getDollBus(vo.getBusId());
            if (dollBus != null) {
                if (vo.getType() == 1 || vo.getType() == 2 || vo.getType() == 3) {
                    BaseRespVO baseRespVO = new BaseRespVO(100, true, "数据请求成功");
                    if (vo.getType() == 1) {
                        Object machineBasicData = machineService.machineSettingQuery(dollBus.getDeviceId(), vo.getType());
                        if (machineBasicData != null){
                            baseRespVO.setData(new BasicDataSetDTO((MachineBasicData)machineBasicData ));
                        }
                    }
                    if (vo.getType() == 2) {
                        Object machineClawVoltage = machineService.machineSettingQuery(dollBus.getDeviceId(), vo.getType());
                        if (machineClawVoltage != null){
                            baseRespVO.setData(new ClawVoltageSetDTO((MachineClawVoltage) machineClawVoltage));
                        }
                    }
                    if (vo.getType() == 3){
                        Object machineMotorSpeed = machineService.machineSettingQuery(dollBus.getDeviceId(),vo.getType());
                        if (machineMotorSpeed != null){
                            baseRespVO.setData(new MotorSpeedSetDTO((MachineMotorSpeed)machineMotorSpeed));
                        }
                    }
                    return baseRespVO;
                } else {
                    return new BaseRespVO(0, false, "数据请求失败,类型无效！");
                }
            }
        }
        return new BaseRespVO(0, false, "数据请求失败=>" + JSON.toJSONString(vo));
    }

    @SignCheck
    @ApiHandle
    @Path("api/setting/setMachineBasicData")
    @ApiOperation(value = "发送机器基础数据设置指令", notes = "发送机器基础数据设置指令", httpMethod = "POST", response = BaseRespVO.class)
    public BaseRespVO setMachineBasicData(MachineBasicDataSetReqVO vo) {
        logger.info("setMachineBasicData===>" + JSON.toJSONString(vo));
        if (customerDollBusService.isBelongCurrentCustomer(vo.getBusId(),vo.getAppId())){
            if (!StringUtil.isNullOrEmpty(vo)) {
                if (vo.getBusId() != 0) {
                    int result = machineService.sendMachineBasicDataSet(new MachineBasicDataSetDTO_RPC(vo));
                    if (result > 0) {
                        if (result == 200){ return new BaseRespVO(100, true, "发送机器基础数据指令成功..."); }
                        if (result == 1){ return new BaseRespVO(0, false, "发送机器基础数据指令失败,机器目前处于游戏中...."); }
                        if (result == 2){ return new BaseRespVO(0, false, "发送机器基础数据指令失败,机器目前处于离线状态...."); }
                    }
                }
            }
        }
        return new BaseRespVO(0, false, "发送机器基础数据指令失败!");
    }

    @SignCheck
    @ApiHandle
    @Path("api/setting/setMachineClawVoltage")
    @ApiOperation(value = "发送机器爪力电压设置指令", notes = "发送机器爪力电压设置指令", httpMethod = "POST", response = BaseRespVO.class)
    public BaseRespVO setMachineClawVoltage(MachineClawVoltageSetReqVO vo) {
        logger.info("setMachineClawVoltage===>" + JSON.toJSONString(vo));
        if (customerDollBusService.isBelongCurrentCustomer(vo.getBusId(),vo.getAppId())){
            if (!StringUtil.isNullOrEmpty(vo)) {
                if (vo.getBusId() != 0) {
                    int result = machineService.sendMachineClawVoltageSet(new MachineClawVoltageSetDTO_RPC(vo));
                    if (result > 0) {
                        if (result == 200){ return new BaseRespVO(100, true, "发送机器爪力电压设置指令成功..."); }
                        if (result == 1){ return new BaseRespVO(100, true, "发送机器爪力电压设置指令失败,机器目前处于游戏中...."); }
                        if (result == 2){ return new BaseRespVO(100, true, "发送机器爪力电压设置指令失败,机器目前处于离线状态...."); }
                    }
                }
            }
        }
        return new BaseRespVO(0, false, "发送机器爪力电压设置指令失败!");
    }




    public static void main(String[] args) {
        Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("start", 1517280272);
        postData.put("end", 1517280291);
        postData.put("streamUrl", "rtmp://pili-live-rtmp.xiehou360.com/suanguolive/mqww20171215_09_01");
        HttpEntity httpEntity = HttpUtil.Post("http://t.xiehou360.com/DollPubAppServer/api/wawa/video", JSON.toJSONString(postData));
        System.out.println(httpEntity.getHtml());
    }
}
