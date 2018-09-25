package com.imlianai.dollpub.app.modules.support.machine.service;

import com.imlianai.dollpub.domain.machine.MachineCallback;
import com.imlianai.dollpub.domain.machine.dto.MachineBasicDataSetDTO_RPC;
import com.imlianai.dollpub.domain.machine.dto.MachineClawVoltageSetDTO_RPC;
import com.imlianai.dollpub.domain.machine.dto.MachineMotorSpeedSetDTO_RPC;

import java.util.List;

/**
 * @author wurui
 * @create 2018-01-15 17:17
 **/
public interface MachineService {

    /**
     * 新增机器回调
     * @param machineCallback
     * @return
     */
     int add(MachineCallback machineCallback);


    /**
     * 更新机器回调
     * @param machineCallback
     * @return
     */
    int update(MachineCallback machineCallback);


    /**
     * 机器设置相关查询（多个）
     * @param deviceIds
     * @param type
     * @return
     */
    List moreMachineSettingQuery(List<String> deviceIds,int type);


    /**
     * 机器设置相关查询（单个）
     * @param deviceId
     * @param type 1:普通数据查询，2:爪力电压查询，3：机器马达速度
     * @return
     */
    Object machineSettingQuery(String deviceId,int type);

    /**
     * 新增或更新机器基本设置
     * @param deviceId
     * @param hexs
     * @return
     */
    int machineBasicDataSettingHandle(String deviceId,List<String> hexs);

    /**
     * 新增或更新机器爪力电压
     * @param deviceId
     * @param hexs
     * @return
     */
    int machineClawVoltageHandle(String deviceId,List<String> hexs);

    /**
     * 新增或更新机器马达速度
     * @param deviceId
     * @param hexs
     * @return
     */
    int machineMotorSpeedHandle(String deviceId,List<String> hexs);


    /**
     * 初始化单个机器基础设置到数据库表
     * @param busId
     */
    void initMachineBasicData(int busId);

    /**
     * 初始化单个机器爪力电压设置到数据库表
     * @param busId
     */
    void initMachineClawVoltage(int busId);


    /**
     * 初始化单个机器马达速度设置到数据库表
     * @param busId
     */
    void initMachineMotorSpeed(int busId);

    /**
     * 初始化所有机器设置到数据库表
     */
    void initAll();

    /**
     * 初始化指定类型机器设置到数据库表
     * @param type 1：机器基础数据，2：机器爪力电压，3：机器马达速度
     */
    int initAll(int type);


    /**
     * 机器复位(每次设置成功之后都需要复位)
     * @param busId
     */
    void machineRESET(int busId);

    /**
     * 发送机器基础数据设置指令
     * @param vo
     * @return
     */
    int sendMachineBasicDataSet(MachineBasicDataSetDTO_RPC vo);


    /**
     * 发送机器爪力电压设置指令
     * @param vo
     * @return
     */
    int sendMachineClawVoltageSet(MachineClawVoltageSetDTO_RPC vo);


    int sendMachineMotorSpeedSet(MachineMotorSpeedSetDTO_RPC vo);

}
