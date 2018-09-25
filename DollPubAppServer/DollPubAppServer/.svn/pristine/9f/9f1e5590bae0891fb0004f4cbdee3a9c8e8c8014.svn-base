package com.imlianai.dollpub.app.modules.support.machine.dao;

import com.imlianai.dollpub.domain.machine.MachineBasicData;
import com.imlianai.dollpub.domain.machine.MachineCallback;
import com.imlianai.dollpub.domain.machine.MachineClawVoltage;
import com.imlianai.dollpub.domain.machine.MachineMotorSpeed;

import java.util.Date;
import java.util.List;

/**
 * 机器相关
 * @author wurui
 * @create 2018-01-15 17:11
 **/
public interface MachineDao {

    /**
     * 新增机器回调
     * @param machineCallback
     * @return
     */
    public int insert(MachineCallback machineCallback);

    /**
     * 更新机器回调
     * @param machineCallback
     * @return
     */
    public int update(MachineCallback machineCallback);

    public MachineCallback getMachineCallbackById(int id);

    public MachineCallback getMachineCallbackByOptId(long optId);

    public List<MachineCallback> getMachineCallbackByResult(int result);

    public List<MachineCallback> getMachineCallbackByGroupId(int groupId);

    public List<MachineCallback> getMachineCallbackByStartTime(Date startTime);


//    ---------------------------------------机器相关设置-------------------------------------

    /**
     * 查询机器设置
     * @param deviceIds
     * @param clazz
     * @param <T>
     * @return
     */
    List<Object> getMachineSet(List<String> deviceIds,String tableName,Class clazz);




//############################## 机器基础数据相关 ############################

    /**
     *  插入或更新机器设置
     * @param machineBasicData
     * @return
     */
    int insertOrUpdateMachineBDSetting(MachineBasicData machineBasicData);


    /**
     *  通过设备id查询机器基础设置
     * @param deviceId
     * @return
     */
    MachineBasicData getMachineBasicDataByDeviceId(String deviceId);

    /**
     *  通过机器id查询机器基础设置
     * @param busId
     * @return
     */
    MachineBasicData getMachineBasicDataByBusId(int busId);




//############################## 机器爪力电压相关 ############################



    /**
     *  插入或更新机器爪力电压
     * @param machineClawVoltage
     * @return
     */
    int insertOrUpdateMachineClawVoltage(MachineClawVoltage machineClawVoltage);


    /**
     *  通过设备id查询机器爪力电压设置
     * @param deviceId
     * @return
     */
    MachineClawVoltage getMachineClawVoltageByDeviceId(String deviceId);

    /**
     *  通过机器id查询机器爪力电压设置
     * @param busId
     * @return
     */
    MachineClawVoltage getMachineClawVoltageByBusId(int busId);






//############################## 机器马达速度相关 ############################


    /**
     * 通过设备id查询
     * @param deviceId
     * @return
     */
    MachineMotorSpeed getMachineMotorSpeedByDeviceId(String deviceId);

    /**
     * 通过机器id查询
     * @param busId
     * @return
     */
    MachineMotorSpeed getMachineMotorSpeedByBusId(int busId);

    /**
     * 插入或更新机器马达速度
     * @param machineMotorSpeed
     * @return
     */
    int insertOrUpdateMachineMotorSpeed(MachineMotorSpeed machineMotorSpeed);

}
