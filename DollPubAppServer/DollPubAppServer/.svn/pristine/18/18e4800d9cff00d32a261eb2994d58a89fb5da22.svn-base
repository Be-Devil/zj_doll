package com.imlianai.dollpub.app.modules.support.machine.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.support.machine.consts.MachineCmdConst;
import com.imlianai.dollpub.app.modules.support.machine.consts.MachineTableName;
import com.imlianai.dollpub.app.modules.support.machine.dao.MachineDao;
import com.imlianai.dollpub.app.modules.support.machine.vo.MachineClawVoltageSetReqVO;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.machine.MachineBasicData;
import com.imlianai.dollpub.domain.machine.MachineCallback;
import com.imlianai.dollpub.domain.machine.MachineClawVoltage;

import com.imlianai.dollpub.domain.machine.MachineMotorSpeed;
import com.imlianai.dollpub.domain.machine.dto.*;
import com.imlianai.dollpub.machine.iface.IMachineConnectRemoteService;
import com.imlianai.dollpub.machine.iface.IMachineRemoteService;
import com.imlianai.dollpub.machine.iface.domain.MachineDevice;
import com.imlianai.rpc.support.common.BaseLogger;

import com.imlianai.rpc.support.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 机器相关服务
 *
 * @author wurui
 * @create 2018-01-15 17:18
 **/
@Service
public class MachineServiceImpl implements MachineService {

    private BaseLogger logger = BaseLogger.getLogger(this.getClass());

    @Resource
    private MachineDao machineDao;

    @Resource
    private DollBusService dollBusService;

    @Reference
    private IMachineConnectRemoteService iMachineConnectRemoteService;

    @Reference
    private IMachineRemoteService iMachineRemoteService;

    @Override
    public int add(MachineCallback machineCallback) {
        return machineDao.insert(machineCallback);
    }

    @Override
    public int update(MachineCallback machineCallback) {
        return machineDao.update(machineCallback);
    }

    @Override
    public List moreMachineSettingQuery(List<String> deviceIds, int type) {
        if (StringUtil.isNullOrEmpty(deviceIds)) {
            logger.warn("deviceId is Null or Empty !");
        }
        Map<Integer, DollBus> dollBusMap = dollBusService.getDollBusByDeviceIds(deviceIds);
        //1:普通数据查询，2:爪力电压查询,3:机器马达速度
        switch (type) {
            case 1:
                List<Object> machineBasicDataList = machineDao.getMachineSet(deviceIds, MachineTableName.MACHINE_BASIC_DATA_TABLE_NAME, MachineBasicData.class);
                List<BasicDataSetDTO> basicDataSetDTOS = Lists.newArrayList();
                if (!StringUtil.isNullOrEmpty(machineBasicDataList)) {
                    for (Object object : machineBasicDataList) {
                        MachineBasicData machineBasicData = (MachineBasicData) object;
                        if (machineBasicData != null) {
                            basicDataSetDTOS.add(BasicDataSetDTO.adapter(machineBasicData, dollBusMap.get(machineBasicData.getBusId())));
                        }
                    }
                }
                return basicDataSetDTOS;

            case 2:
                List<Object> machineClawVoltageList = machineDao.getMachineSet(deviceIds, MachineTableName.MACHINE_CLAW_VOLTAGE_TABLE_NAME, MachineClawVoltage.class);
                List<ClawVoltageSetDTO> clawVoltageSetDTOS = Lists.newArrayList();
                if (!StringUtil.isNullOrEmpty(machineClawVoltageList)) {
                    for (Object object : machineClawVoltageList) {
                        MachineClawVoltage machineClawVoltage = (MachineClawVoltage) object;
                        if (machineClawVoltage != null) {
                            clawVoltageSetDTOS.add(ClawVoltageSetDTO.adapter(machineClawVoltage, dollBusMap.get(machineClawVoltage.getBusId())));
                        }
                    }
                }
                return clawVoltageSetDTOS;

            case 3:
                List<Object> machineMotorSpeedList = machineDao.getMachineSet(deviceIds,MachineTableName.MACHINE_MOTOR_SPEED_TABLE_NAME,MachineMotorSpeed.class);
                List<MotorSpeedSetDTO> motorSpeedSetDTOS = Lists.newArrayList();
                if (!StringUtil.isNullOrEmpty(machineMotorSpeedList)){
                    for (Object object : machineMotorSpeedList){
                        MachineMotorSpeed machineMotorSpeed = (MachineMotorSpeed)object;
                        logger.info("machineMotorSpeed=>" + JSON.toJSONString(machineMotorSpeed));
                        if (machineMotorSpeed != null){
                            motorSpeedSetDTOS.add(MotorSpeedSetDTO.adapter(machineMotorSpeed,dollBusMap.get(machineMotorSpeed.getBusId())));
                        }
                    }
                }
                return motorSpeedSetDTOS;
            default:
                break;
        }
        return Lists.newArrayList();
    }

    @Override
    public Object machineSettingQuery(String deviceId, int type) {
        logger.info("machineSettingQuery=================>" + deviceId);
        if (deviceId == null || deviceId.equals("")) {
            logger.warn("deviceId is Null or Empty !");
        }
        try {
            //1:普通数据查询，2:爪力电压查询，3:机器马达速度
            switch (type) {
                case 1:
                    DollBus dollBus_mbd = dollBusService.getDollBusByDeviceId(deviceId);
                    logger.info("dollBus_mbd=================>" + JSON.toJSONString(dollBus_mbd));
                    if (dollBus_mbd != null) {
                        this.initMachineBasicData(dollBus_mbd.getBusId());
                        Thread.sleep(1500);//休眠3秒。。。
                    }
                    return machineDao.getMachineBasicDataByDeviceId(deviceId);
                case 2:
                    DollBus dollBus_mcv = dollBusService.getDollBusByDeviceId(deviceId);
                    logger.info("dollBus_mcv=================>" + JSON.toJSONString(dollBus_mcv));
                    if (dollBus_mcv != null) {
                        this.initMachineClawVoltage(dollBus_mcv.getBusId());
                        Thread.sleep(1500);//休眠3秒。。。
                    }
                    return machineDao.getMachineClawVoltageByDeviceId(deviceId);
                case 3:
                    DollBus dollBus_mms = dollBusService.getDollBusByDeviceId(deviceId);
                    logger.info("dollBus_mms=================>" + JSON.toJSONString(dollBus_mms));
                    if (dollBus_mms != null) {
                        this.initMachineMotorSpeed(dollBus_mms.getBusId());
                        Thread.sleep(1500);//休眠3秒。。。
                    }
                    return machineDao.getMachineMotorSpeedByDeviceId(deviceId);
                default:
                    break;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    @Override
    public int machineBasicDataSettingHandle(String deviceId, List<String> hexs) {
        int result = 0;
        if (!StringUtil.isNullOrEmpty(hexs) && hexs.size() == 22) {
            try {
                DollBus dollBus = dollBusService.getDollBusByDeviceId(deviceId);
                MachineBasicData machineBasicData = new MachineBasicData();
                machineBasicData.setDeviceId(deviceId);
                if (dollBus != null) {
                    machineBasicData.setBusId(dollBus.getBusId());
                }
                machineBasicData.setLanguage(Integer.parseInt(hexs.get(4), 16) + "");
                machineBasicData.setAdvertMusicOnOff(Integer.parseInt(hexs.get(5), 16) + "");
                machineBasicData.setAdvertMusicIntervalTime(Integer.parseInt(hexs.get(6), 16) + "");
                machineBasicData.setCoin(Integer.parseInt(hexs.get(7), 16) + "");
                machineBasicData.setInning(Integer.parseInt(hexs.get(8), 16) + "");
                machineBasicData.setCoinRetain(Integer.parseInt(hexs.get(9), 16) + "");
                machineBasicData.setPlayTime(Integer.parseInt(hexs.get(10), 16) + "");
                machineBasicData.setGameType(Integer.parseInt(hexs.get(11), 16) + "");
                machineBasicData.setProbability(Integer.parseInt(hexs.get(12), 16) + "");
                machineBasicData.setExitPosition(Integer.parseInt(hexs.get(13), 16) + "");
                machineBasicData.setAirGrip(Integer.parseInt(hexs.get(14), 16) + "");
                machineBasicData.setBgMusic(Integer.parseInt(hexs.get(15), 16) + "");
                machineBasicData.setGameMusic(Integer.parseInt(hexs.get(16), 16) + "");
                machineBasicData.setPhotoEye(Integer.parseInt(hexs.get(17), 16) + "");
                machineBasicData.setShake(Integer.parseInt(hexs.get(18), 16) + "");
                machineBasicData.setRetain(Integer.parseInt(hexs.get(19), 16) + "");
                machineBasicData.setCusum(Integer.parseInt(hexs.get(20), 16) + "");
                machineBasicData.setRemark(JSON.toJSONString(hexs));
                result = machineDao.insertOrUpdateMachineBDSetting(machineBasicData);
                logger.info(JSON.toJSONString(machineBasicData));
            } catch (Exception e) {
                logger.error(JSON.toJSONString(e));
            }
        }
        return result;
    }

    @Override
    public int machineClawVoltageHandle(String deviceId, List<String> hexs) {
        int result = 0;
        if (!StringUtil.isNullOrEmpty(hexs) && hexs.size() == 22) {
            try {
                if ((hexs.get(4) != null && !"".equals(hexs.get(4)))
                        && (hexs.get(5) != null && !"".equals(hexs.get(5)))
                        && (hexs.get(6) != null && !"".equals(hexs.get(6)))
                        && (hexs.get(7) != null && !"".equals(hexs.get(7)))
                        && (hexs.get(8) != null && !"".equals(hexs.get(8)))
                        && (hexs.get(9) != null && !"".equals(hexs.get(9)))
                        && (hexs.get(10) != null && !"".equals(hexs.get(10)))
                        && (hexs.get(11) != null && !"".equals(hexs.get(11)))) {
                    DollBus dollBus = dollBusService.getDollBusByDeviceId(deviceId);
                    MachineClawVoltage machineClawVoltage = new MachineClawVoltage();
                    machineClawVoltage.setDeviceId(deviceId);
                    if (dollBus != null) {
                        machineClawVoltage.setBusId(dollBus.getBusId());
                    }

                    //强爪电压
                    machineClawVoltage.setStrongClawHigh(Integer.parseInt(hexs.get(4), 16) + "");
                    machineClawVoltage.setStrongClawLow(Integer.parseInt(hexs.get(5), 16) + "");
                    machineClawVoltage.setStrongClawVoltage(Integer.parseInt(hexs.get(4) + hexs.get(5), 16)); //010E => 270 => 27V


                    //弱爪电压
                    machineClawVoltage.setWeakClawHigh(Integer.parseInt(hexs.get(6), 16) + "");
                    machineClawVoltage.setWeakClawLow(Integer.parseInt(hexs.get(7), 16) + "");
                    machineClawVoltage.setWeakClawVoltage(Integer.parseInt(hexs.get(6) + hexs.get(7), 16)); // 0041 => 65 => 6.5V

                    //弱爪后电压
                    machineClawVoltage.setWeakClawBackHigh(Integer.parseInt(hexs.get(8), 16) + "");
                    machineClawVoltage.setWeakClawBackLow(Integer.parseInt(hexs.get(9), 16) + "");
                    machineClawVoltage.setWeakClawBackVoltage(Integer.parseInt(hexs.get(8) + hexs.get(9), 16));// 0041 => 65 => 6.5V

                    //中奖电压
                    machineClawVoltage.setWinHigh(Integer.parseInt(hexs.get(10), 16) + "");
                    machineClawVoltage.setWinLow(Integer.parseInt(hexs.get(11), 16) + "");
                    machineClawVoltage.setWinVoltage(Integer.parseInt(hexs.get(10) + hexs.get(11), 16));

                    //强力维持时间
                    machineClawVoltage.setStrongForceTime(Integer.parseInt(hexs.get(12), 16) + "");

                    //弱力维持时间
                    machineClawVoltage.setWeakForceTime(Integer.parseInt(hexs.get(13), 16) + "");

                    //强变弱方式
                    machineClawVoltage.setStrongToWeakType(Integer.parseInt(hexs.get(14), 16) + "");

                    //放线长度时间
                    machineClawVoltage.setPayingOffLengthTime(Integer.parseInt(hexs.get(15), 16) + "");

                    //收爪速度
                    machineClawVoltage.setCloseClawSpeed(Integer.parseInt(hexs.get(16), 16) + "");

                    //上升延时
                    machineClawVoltage.setRiseDelay(Integer.parseInt(hexs.get(17), 16) + "");

                    //掉落延时比例
                    machineClawVoltage.setDropDelayRatio(Integer.parseInt(hexs.get(18), 16) + "");

                    //掉落延时时间
                    machineClawVoltage.setDropDelayTime(Integer.parseInt(hexs.get(19), 16) + "");

                    //累加和
                    machineClawVoltage.setCusum(Integer.parseInt(hexs.get(20), 16) + "");

                    //remark为机器指令
                    machineClawVoltage.setRemark(JSON.toJSONString(hexs));

                    result = machineDao.insertOrUpdateMachineClawVoltage(machineClawVoltage);
                    logger.info(JSON.toJSONString(machineClawVoltage));
                }
            } catch (Exception e) {
                logger.error(JSON.toJSONString(e));
            }
        }
        return result;
    }

    @Override
    public int machineMotorSpeedHandle(String deviceId, List<String> hexs) {
        int result = 0;
        if (!StringUtil.isNullOrEmpty(hexs) && hexs.size() == 9) {
            DollBus dollBus = dollBusService.getDollBusByDeviceId(deviceId);
            MachineMotorSpeed machineMotorSpeed = new MachineMotorSpeed();
            machineMotorSpeed.setDeviceId(deviceId);
            if (dollBus != null) {
                machineMotorSpeed.setBusId(dollBus.getBusId());
            }
            machineMotorSpeed.setFrontAndBack(Integer.parseInt(hexs.get(4), 16) + "");
            machineMotorSpeed.setLeftAndRight(Integer.parseInt(hexs.get(5), 16) + "");
            machineMotorSpeed.setUpAndDown(Integer.parseInt(hexs.get(6), 16) + "");
            machineMotorSpeed.setCusum(Integer.parseInt(hexs.get(7), 16) + "");
            machineMotorSpeed.setRemark(JSON.toJSONString(hexs));

            result = machineDao.insertOrUpdateMachineMotorSpeed(machineMotorSpeed);
            logger.info(JSON.toJSONString(machineMotorSpeed));
        }
        return result;
    }

    @Override
    public void initMachineBasicData(int busId) {
        try {
            iMachineConnectRemoteService.handleDirective(busId, MachineCmdConst.MACHINE_BASIC_DATA_QUERY, "查询机器基础设置");
            logger.info("初始化单个机器基础设置到数据库表成功=>" + busId);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(e));
        }
    }

    @Override
    public void initMachineClawVoltage(int busId) {
        try {
            iMachineConnectRemoteService.handleDirective(busId, MachineCmdConst.MACHINE_CLAW_VOLTAGE_QUERY, "查询爪力电压设置");
            logger.info("初始化单个机器爪力电压设置到数据库表成功=>" + busId);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(e));
        }
    }

    @Override
    public void initMachineMotorSpeed(int busId) {
        try {
            iMachineConnectRemoteService.handleDirective(busId, MachineCmdConst.MACHINE_MOTOR_SPEED_QUERY, "查询机器马达速度设置");
            logger.info("初始化单个机器马达速度设置到数据库表成功=>" + busId);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(e));
        }
    }


    @Override
    public void initAll() {
        logger.info("===========initAllMachineSetting=========");
        List<MachineDevice> machineDeviceList = iMachineRemoteService.getMachineDeviceList();
        if (!StringUtil.isNullOrEmpty(machineDeviceList)) {
            logger.info("machineDeviceList==>" + JSON.toJSONString(machineDeviceList));
            for (MachineDevice machineDevice : machineDeviceList) {
                try {
                    if (machineDevice.getStatus() != 2) {
                        this.initMachineBasicData(machineDevice.getBusId());
                        this.initMachineClawVoltage(machineDevice.getBusId());
                        this.initMachineMotorSpeed(machineDevice.getBusId());
                    }
                } catch (Exception e) {
                    logger.error(JSON.toJSONString(e));
                }
            }
        }
    }

    @Override
    public int initAll(int type) {
        int result = 0;
        List<MachineDevice> machineDeviceList = iMachineRemoteService.getMachineDeviceList();
        if (!StringUtil.isNullOrEmpty(machineDeviceList)) {
            logger.info("machineDeviceList==>" + JSON.toJSONString(machineDeviceList));
            for (MachineDevice machineDevice : machineDeviceList) {
                try {
                    if (type == 1) {
                        if (machineDevice.getStatus() != 2) {
                            this.initMachineBasicData(machineDevice.getBusId());
                        }
                    }
                    if (type == 2) {
                        if (machineDevice.getStatus() != 2) {
                            this.initMachineClawVoltage(machineDevice.getBusId());
                        }
                    }
                    if (type == 3) {
                        if (machineDevice.getStatus() != 2) {
                            this.initMachineMotorSpeed(machineDevice.getBusId());
                        }
                    }
                    Thread.sleep(500);
                } catch (Exception e) {
                    logger.error(JSON.toJSONString(e));
                }
            }
            result = 1;
        }
        return result;
    }

    @Override
    public void machineRESET(int busId) {
        try {
            iMachineConnectRemoteService.handleDirective(busId, MachineCmdConst.MACHINE_RESET, "机器复位");
            logger.info("机器复位成功=>" + busId);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(e));
        }
    }

    @Override
    public int sendMachineBasicDataSet(MachineBasicDataSetDTO_RPC vo) {
        logger.info("sendMachineBasicDataSet ====>" + JSON.toJSONString(vo));
        int result = 0;
        try {

            MachineDevice machineDevice = iMachineRemoteService.getMachineState(vo.getBusId());
            if (machineDevice != null) { //游戏中跟离线状态的机子不能设置
                //机子游戏中
                if (machineDevice.getStatus() == 1) return 1;
                //机子离线
                if (machineDevice.getStatus() == 2) return 2;

                // 机子空闲
                if (machineDevice.getStatus() == 0) {

                    MachineBasicData machineBasicData = machineDao.getMachineBasicDataByBusId(vo.getBusId());
                    if (machineBasicData != null) {
                        int check16 = 19 + 16 + 1 + vo.getLanguage() + vo.getAdvertMusicOnOff() + vo.getAdvertMusicIntervalTime() +
                                vo.getCoin() + vo.getInning() + vo.getCoinRetain() + vo.getPlayTime() + vo.getGameType() +
                                vo.getProbability() + vo.getExitPosition() + vo.getAirGrip() + vo.getBgMusic() + vo.getGameMusic() +
                                vo.getPhotoEye() + vo.getShake() + vo.getRetain();
                        byte[] data22 = {(byte) 0x8a, (byte) 0x13, (byte) 0x10, (byte) 0x01,
                                (byte) vo.getLanguage(), (byte) vo.getAdvertMusicOnOff(), (byte) vo.getAdvertMusicIntervalTime(),
                                (byte) vo.getCoin(), (byte) vo.getInning(), (byte) vo.getCoinRetain(),
                                (byte) vo.getPlayTime(), (byte) vo.getGameType(), (byte) vo.getProbability(),
                                (byte) vo.getExitPosition(), (byte) vo.getAirGrip(), (byte) vo.getBgMusic(),
                                (byte) vo.getGameMusic(), (byte) vo.getPhotoEye(), (byte) vo.getShake(),
                                (byte) vo.getRetain(), (byte) check16, (byte) 0x55};
                        //兼容旧设置方式
                        iMachineRemoteService.updateDeviceSetting(machineBasicData.getBusId(), 3, vo.getExitPosition());
                        iMachineRemoteService.updateDeviceSetting(machineBasicData.getBusId(), 1, vo.getPlayTime());
                        iMachineRemoteService.updateDeviceSetting(machineBasicData.getBusId(), 2, vo.getProbability());
                        //兼容旧设置方式 end
                        iMachineConnectRemoteService.handleDirective(machineBasicData.getBusId(), data22, "发送机器基础设置指令");

                        logger.info("sendMachineBasicDataSet: check16=>" + check16 + ",data22=>" + JSON.toJSONString(data22));
                        result = 200;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(JSON.toJSONString(e));
        }
        return result;
    }

    @Override
    public int sendMachineClawVoltageSet(MachineClawVoltageSetDTO_RPC vo) {
        int result = 0;
        try {

            MachineDevice machineDevice = iMachineRemoteService.getMachineState(vo.getBusId());
            if (machineDevice != null) { //游戏中跟离线状态的机子不能设置
                //机子游戏中
                if (machineDevice.getStatus() == 1) return 1;
                //机子离线
                if (machineDevice.getStatus() == 2) return 2;

                // 机子空闲
                if (machineDevice.getStatus() == 0) {

                    MachineClawVoltage machineClawVoltage = machineDao.getMachineClawVoltageByBusId(vo.getBusId());
                    if (machineClawVoltage != null) {

                        //用于控制是否执行后续重要操作
                        boolean isExecute = false;

                        //初始化默认最低值...
                        int strongClawHigh = 1,
                                strongClawLow = 14,
                                weakClawHigh = 0,
                                weakClawLow = 65,
                                weakClawBackHigh = 0,
                                weakClawBackLow = 65,
                                winHigh = 1,
                                winLow = 14;

                        //强爪电压
                        int strong_claw_v_10 = vo.getStrongClawVoltage();
                        if (strong_claw_v_10 != 0) {
                            //十进制范围 130-475
                            if (strong_claw_v_10 < 130) {
                                strong_claw_v_10 = 130;
                            }
                            if (strong_claw_v_10 > 475) {
                                strong_claw_v_10 = 475;
                            }
                            String[] _v = this.toHex(strong_claw_v_10);
                            if (_v != null && _v.length == 2) {
                                strongClawHigh = Integer.parseInt(_v[0], 16);
                                strongClawLow = Integer.parseInt(_v[1], 16);
                                isExecute = true;
                                logger.info("strong_claw_v_10--->" + strongClawHigh + "," + strongClawLow);
                            }
                        } else {
                            return result;
                        }

                        //弱爪电压
                        int weak_claw_v_10 = vo.getWeakClawVoltage();
                        if (weak_claw_v_10 != 0) {
                            //十进制范围 20-200
                            if (weak_claw_v_10 < 20) {
                                weak_claw_v_10 = 20;
                            }
                            if (weak_claw_v_10 > 200) {
                                weak_claw_v_10 = 200;
                            }
                            String[] _v = this.toHex(weak_claw_v_10);
                            if (_v != null && _v.length == 2) {
                                weakClawHigh = Integer.parseInt(_v[0], 16);
                                weakClawLow = Integer.parseInt(_v[1], 16);
                                isExecute = true;
                                logger.info("weak_claw_v_10--->" + weakClawHigh + "," + weakClawLow);
                            }
                        } else {
                            return result;
                        }

                        //弱爪后电压
                        int weak_back_claw_v_10 = vo.getWeakClawBackVoltage();
                        if (weak_back_claw_v_10 != 0) {
                            //十进制范围 20-400
                            if (weak_back_claw_v_10 < 20) {
                                weak_back_claw_v_10 = 20;
                            }
                            if (weak_back_claw_v_10 > 400) {
                                weak_back_claw_v_10 = 400;
                            }
                            String[] _v = this.toHex(weak_back_claw_v_10);
                            if (_v != null && _v.length == 2) {
                                weakClawBackHigh = Integer.parseInt(_v[0], 16);
                                weakClawBackLow = Integer.parseInt(_v[1], 16);
                                isExecute = true;
                                logger.info("weak_back_claw_v_10--->" + weakClawBackHigh + "," + weakClawBackLow);
                            }
                        } else {
                            return result;
                        }

                        //中奖电压
                        int win_v_10 = vo.getWinVoltage();
                        if (win_v_10 != 0) {
                            //十进制范围 45-480
                            if (win_v_10 < 45) {
                                win_v_10 = 45;
                            }
                            if (win_v_10 > 480) {
                                win_v_10 = 480;
                            }
                            String[] _v = this.toHex(win_v_10);
                            if (_v != null && _v.length == 2) {
                                winHigh = Integer.parseInt(_v[0], 16);
                                winLow = Integer.parseInt(_v[1], 16);
                                isExecute = true;
                                logger.info("win_v_10--->" + winHigh + "," + winLow);
                            }
                        } else {
                            return result;
                        }

                        //累计和
                        int check16 = 19 + 17 + 1 + strongClawHigh + strongClawLow + weakClawHigh + weakClawLow +
                                weakClawBackHigh + weakClawBackLow + winHigh + winLow +
                                vo.getStrongForceTime() + vo.getWeakForceTime() + vo.getStrongToWeakType() +
                                vo.getPayingOffLengthTime() + vo.getCloseClawSpeed() + vo.getRiseDelay() +
                                vo.getDropDelayRatio() + vo.getDropDelayTime();

                        //组装指令
                        byte[] data22 = {(byte) 0x8a, (byte) 0x13, (byte) 0x11, (byte) 0x01,
                                (byte) strongClawHigh, (byte) strongClawLow, (byte) weakClawHigh, (byte) weakClawLow,
                                (byte) weakClawBackHigh, (byte) weakClawBackLow, (byte) winHigh, (byte) winLow,
                                (byte) vo.getStrongForceTime(), (byte) vo.getWeakForceTime(), (byte) vo.getStrongToWeakType(), (byte) vo.getPayingOffLengthTime(),
                                (byte) vo.getCloseClawSpeed(), (byte) vo.getRiseDelay(), (byte) vo.getDropDelayRatio(),
                                (byte) vo.getDropDelayTime(), (byte) check16, (byte) 0x55};
                        if (isExecute) {
                            iMachineConnectRemoteService.handleDirective(machineClawVoltage.getBusId(), data22, "发送机器爪力电压设置指令");
                            logger.info("sendMachineClawVoltageSet: check16=>" + check16 + ",data22=>" + JSON.toJSONString(data22));
                            result = 200;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(JSON.toJSONString(e));
        }
        return result;
    }

    @Override
    public int sendMachineMotorSpeedSet(MachineMotorSpeedSetDTO_RPC vo) {
        logger.info("sendMachineBasicDataSet ====>" + JSON.toJSONString(vo));
        int result = 0;
        try {

            MachineDevice machineDevice = iMachineRemoteService.getMachineState(vo.getBusId());
            if (machineDevice != null) { //游戏中跟离线状态的机子不能设置
                //机子游戏中
                if (machineDevice.getStatus() == 1) return 1;
                //机子离线
                if (machineDevice.getStatus() == 2) return 2;

                // 机子空闲
                if (machineDevice.getStatus() == 0) {

                    MachineMotorSpeed machineMotorSpeed = machineDao.getMachineMotorSpeedByBusId(vo.getBusId());
                    if (machineMotorSpeed != null) {
                        int check6 = 6 + 18 + 1 + vo.getFrontAndBack() + vo.getLeftAndRight() + vo.getUpAndDown();
                        byte[] data9 = {
                                (byte) 0x8a, (byte) 0x06, (byte) 0x12, (byte) 0x01,
                                (byte)vo.getFrontAndBack(),(byte)vo.getLeftAndRight (),(byte)vo.getUpAndDown(),
                                (byte) check6, (byte) 0x55};
                        iMachineConnectRemoteService.handleDirective(machineMotorSpeed.getBusId(), data9, "发送机器马达速度设置指令");
                        logger.info("sendMachineMotorSpeedSet: check6=>" + check6 + ",data9=>" + JSON.toJSONString(data9));
                        result = 200;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(JSON.toJSONString(e));
        }
        return result;
    }

    // 将十进制电压转换成十六进制再切割成两段十六进制....
    public static String[] toHex(int v) {
        String[] hex = null;
        String _v = Integer.toHexString(v);
        if (!"".equals(_v) && _v.length() > 0) {
            hex = new String[2];
            int size = _v.length();
            if (size > 4) { //超出范围 0~4
                return null;
            }
            if (size == 1) {  // 可能是 1-15 的值 前面补3个0,再切割
                //String _1L = "000" + _v;
                hex[0] = "00";
                hex[1] = "0" + _v;
            }
            if (size == 2) { // 大于15的十六进制长度 >= 2 前面补2个0，再切割
                //String _2L = "00" + _v;
                hex[0] = "00";
                hex[1] = _v;
            }
            if (size == 3) { // 如十六进制是 01f 前面补1个0，再切割
                String _3L = "0" + _v;
                hex[0] = _3L.substring(0, 1) + 1;
                hex[1] = _3L.substring(2, 4);
            }
            if (size == 4) { // 如十六进制是 000e 则不需要补0，直接切割
                hex[0] = _v.substring(0, 1) + 1;
                hex[1] = _v.substring(2, 4);
            }
        }
        return hex;
    }


    public static void main(String[] args) {

        //int check16 = 19 + 22 + 2 + 1 + 1 + 1 + 1 + 1 + 30 + 4 + 10 + 9 + 10 + 1 + 1;
        //基础设置
//        byte[] data22_a ={(byte)0x8a, (byte)0x13, (byte)0x10,(byte)0x01,
//                (byte)0x01, (byte)0x01,(byte)0x00,
//                (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x14,
//                (byte)0x02, (byte)0x0a, (byte)0x00, (byte)0x01,
//                (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01,
//                (byte)0x01, (byte)0x4F, (byte)0x55};

        //电压设置
        byte[] data22_a = {(byte) 0x8a, (byte) 0x13, (byte) 0x11, (byte) 0x01,
                (byte) 0x01, (byte) 0x69, (byte) 0x00, (byte) 0x64,
                (byte) 0x00, (byte) 0x64, (byte) 0x01, (byte) 0x68,
                (byte) 0x08, (byte) 0x02, (byte) 0x01, (byte) 0x0c,
                (byte) 0x02, (byte) 0x01, (byte) 0x00,
                (byte) 0x01, (byte) 475, (byte) 0x55};

//        byte[] data22_a = {(byte) 0x8a, (byte) 0x13, (byte) 0x11, (byte) 0x01,
//                (byte) 0x01, (byte) 0x2c, (byte) 0x00, (byte) 0x50,
//                (byte) 0x00, (byte) 0x50, (byte) 0x01, (byte) 0x2c,
//                (byte) 0x08, (byte) 0x08, (byte) 0x00, (byte) 0x0c,
//                (byte) 0x02, (byte) 0x01, (byte) 0x00,
//                (byte) 0x01, (byte) 0x3f, (byte) 0x55};

        System.out.println("data22_a=>" + JSON.toJSONString(data22_a));

//        MachineBasicDataSetReqVO vo = new MachineBasicDataSetReqVO();
//
//
//        vo.setLanguage(1);
//        vo.setAdvertMusicOnOff(1);
//        vo.setAdvertMusicIntervalTime(0);
//        vo.setCoin(1);
//        vo.setInning(1);
//        vo.setCoinRetain(1);
//        vo.setPlayTime(20);vo.setGameType(2);vo.setProbability(10);vo.setExitPosition(0);vo.setAirGrip(1);
//        vo.setBgMusic(1);vo.setGameMusic(1);vo.setPhotoEye(1);vo.setShake(1);vo.setRetain(1);
//
//        int check16 = 19 + 16 + 1 +vo.getLanguage() + vo.getAdvertMusicOnOff() + vo.getAdvertMusicIntervalTime() +
//                vo.getCoin() + vo.getInning() + vo.getCoinRetain() + vo.getPlayTime() + vo.getGameType() +
//                vo.getProbability() + vo.getExitPosition() + vo.getAirGrip() + vo.getBgMusic() + vo.getGameMusic() +
//                vo.getPhotoEye() + vo.getShake() + vo.getRetain();
//        byte[] data22_b = {(byte) 0x8a, (byte) 0x13, (byte) 0x10, (byte) 0x01,
//            (byte) vo.getLanguage(), (byte) vo.getAdvertMusicOnOff(), (byte) vo.getAdvertMusicIntervalTime(),
//            (byte) vo.getCoin(), (byte) vo.getInning(), (byte) vo.getCoinRetain(),
//            (byte) vo.getPlayTime(), (byte) vo.getGameType(), (byte) vo.getProbability(),
//            (byte) vo.getExitPosition(), (byte) vo.getAirGrip(), (byte) vo.getBgMusic(),
//            (byte) vo.getGameMusic(), (byte) vo.getPhotoEye(), (byte) vo.getShake(),
//            (byte) vo.getRetain(), (byte) check16, (byte) 0x55};


        MachineClawVoltageSetReqVO vo = new MachineClawVoltageSetReqVO();


//        vo.setStrongClawHigh(1);vo.setStrongClawLow(14);vo.setWeakClawHigh(0);vo.setWeakClawLow(65);
//        vo.setWeakClawBackHigh(0);vo.setWeakClawBackLow(65);vo.setWinHigh(1);vo.setWinLow(14);
//        vo.setStrongForceTime(8);vo.setWeakForceTime(8);vo.setStrongToWeakType(0);
//        vo.setPayingOffLengthTime(12);vo.setCloseClawSpeed(2);vo.setRiseDelay(1);vo.setDropDelayRatio(0);vo.setDropDelayTime(1);
//

        vo.setStrongClawVoltage(361);
        vo.setWeakClawVoltage(100);
        vo.setWeakClawBackVoltage(100);
        vo.setWinVoltage(360);


        vo.setStrongForceTime(8);
        vo.setWeakForceTime(2);
        vo.setStrongToWeakType(1);
        vo.setPayingOffLengthTime(12);
        vo.setCloseClawSpeed(2);
        vo.setRiseDelay(1);
        vo.setDropDelayRatio(0);
        vo.setDropDelayTime(1);

        //用于控制是否执行后续重要操作
        boolean isExecute = false;

        //初始化默认最低值...
        int strongClawHigh = 1,
                strongClawLow = 14,
                weakClawHigh = 0,
                weakClawLow = 65,
                weakClawBackHigh = 0,
                weakClawBackLow = 65,
                winHigh = 1,
                winLow = 14;

        //int strong_claw_v_10 = vo.getStrongClawVoltage();//强爪电压
        //int weak_claw_v_10 = vo.getWeakClawVoltage(); //弱爪电压
        //int weak_back_claw_v_10 = vo.getWeakClawBackVoltage();//弱爪后电压
        //int win_v_10 = vo.getWinVoltage();//中奖电压

        //强爪电压
        int strong_claw_v_10 = vo.getStrongClawVoltage();
        if (strong_claw_v_10 != 0) {
            //十进制范围 130-475
            if (strong_claw_v_10 < 130) {
                strong_claw_v_10 = 130;
            }
            if (strong_claw_v_10 > 475) {
                strong_claw_v_10 = 475;
            }
            String[] _v = toHex(strong_claw_v_10);
            if (_v != null && _v.length == 2) {
                strongClawHigh = Integer.parseInt(_v[0], 16);
                strongClawLow = Integer.parseInt(_v[1], 16);
                isExecute = true;
                System.out.println("strong_claw_v_10--->" + strongClawHigh + "," + strongClawLow);
            }
        } else {
            isExecute = false;
        }

        //弱爪电压
        int weak_claw_v_10 = vo.getWeakClawVoltage();
        if (weak_claw_v_10 != 0) {
            //十进制范围 20-200
            if (weak_claw_v_10 < 20) {
                weak_claw_v_10 = 20;
            }
            if (weak_claw_v_10 > 200) {
                weak_claw_v_10 = 200;
            }
            String[] _v = toHex(weak_claw_v_10);
            if (_v != null && _v.length == 2) {
                weakClawHigh = Integer.parseInt(_v[0], 16);
                weakClawLow = Integer.parseInt(_v[1], 16);
                isExecute = true;
                System.out.println("weak_claw_v_10--->" + weakClawHigh + "," + weakClawLow);
            }
        } else {
            isExecute = false;
        }

        //弱爪后电压
        int weak_back_claw_v_10 = vo.getWeakClawBackVoltage();
        if (weak_back_claw_v_10 != 0) {
            //十进制范围 20-400
            if (weak_back_claw_v_10 < 20) {
                weak_back_claw_v_10 = 20;
            }
            if (weak_back_claw_v_10 > 400) {
                weak_back_claw_v_10 = 400;
            }
            String[] _v = toHex(weak_back_claw_v_10);
            if (_v != null && _v.length == 2) {
                weakClawBackHigh = Integer.parseInt(_v[0], 16);
                weakClawBackLow = Integer.parseInt(_v[1], 16);
                isExecute = true;
                System.out.println("weak_back_claw_v_10--->" + weakClawBackHigh + "," + weakClawBackLow);
            }
        } else {
            isExecute = false;
        }

        //中奖电压
        int win_v_10 = vo.getWinVoltage();
        if (win_v_10 != 0) {
            //十进制范围 45-480
            if (win_v_10 < 45) {
                win_v_10 = 45;
            }
            if (win_v_10 > 480) {
                win_v_10 = 480;
            }
            String[] _v = toHex(win_v_10);
            if (_v != null && _v.length == 2) {
                winHigh = Integer.parseInt(_v[0], 16);
                winLow = Integer.parseInt(_v[1], 16);
                isExecute = true;
                System.out.println("win_v_10--->" + winHigh + "," + winLow);
            }
        } else {
            isExecute = false;
        }

        int check16 = 19 + 17 + 1 + strongClawHigh + strongClawLow + weakClawHigh + weakClawLow +
                weakClawBackHigh + weakClawBackLow + winHigh + winLow +
                vo.getStrongForceTime() + vo.getWeakForceTime() + vo.getStrongToWeakType() +
                vo.getPayingOffLengthTime() + vo.getCloseClawSpeed() + vo.getRiseDelay() +
                vo.getDropDelayRatio() + vo.getDropDelayTime();
        byte[] data22_b = {(byte) 0x8a, (byte) 0x13, (byte) 0x11, (byte) 0x01,
                (byte) strongClawHigh, (byte) strongClawLow, (byte) weakClawHigh, (byte) weakClawLow,
                (byte) weakClawBackHigh, (byte) weakClawBackLow, (byte) winHigh, (byte) winLow,
                (byte) vo.getStrongForceTime(), (byte) vo.getWeakForceTime(), (byte) vo.getStrongToWeakType(), (byte) vo.getPayingOffLengthTime(),
                (byte) vo.getCloseClawSpeed(), (byte) vo.getRiseDelay(), (byte) vo.getDropDelayRatio(),
                (byte) vo.getDropDelayTime(), (byte) check16, (byte) 0x55};

        System.out.println("\n累加和=>" + check16);
        System.out.println("data22_b=>" + JSON.toJSONString(data22_b));


        for (int i = 0; i < data22_a.length; i++) {
            System.out.println(i + "=" + data22_a[i] + "," + data22_b[i]);
        }

        System.out.println("\n是否执行=>" + isExecute);
    }
}
