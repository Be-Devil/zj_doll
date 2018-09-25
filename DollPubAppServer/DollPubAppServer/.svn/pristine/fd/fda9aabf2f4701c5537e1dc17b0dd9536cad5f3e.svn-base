package com.imlianai.dollpub.app.modules.support.machine.dao;

import com.imlianai.dollpub.domain.machine.MachineBasicData;
import com.imlianai.dollpub.domain.machine.MachineCallback;
import com.imlianai.dollpub.domain.machine.MachineClawVoltage;
import com.imlianai.dollpub.domain.machine.MachineMotorSpeed;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.rpc.support.utils.StringUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wurui
 * @create 2018-01-15 17:12
 **/
@Repository
public class MachineDaoImpl implements MachineDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int insert(MachineCallback machineCallback) {
        int result = 0;
        String insert_sql = "INSERT INTO machine_callback(deviceId, busId, optId, groupId, result, remark) VALUES (?,?,?,?,?,?)";
        if (machineCallback != null) {
            result = jdbcHandler.executeSql(insert_sql, machineCallback.getDeviceId(), machineCallback.getBusId(), machineCallback.getOptId(), machineCallback.getGroupId(), machineCallback.isResult(), machineCallback.getRemark());
        }
        return result;
    }

    @Override
    public int update(MachineCallback machineCallback) {
        int result = 0;
        String update_sql = "UPDATE machine_callback SET customerId=?,srcUrl=?,request=?,response=?,updateTime=NOW() WHERE optId=?";
        if (machineCallback != null) {
            result = jdbcHandler.executeSql(update_sql, machineCallback.getCustomerId(), machineCallback.getSrcUrl(), machineCallback.getRequest(), machineCallback.getResponse(), machineCallback.getOptId());
        }
        return result;
    }

    private String select_sql = "select * from machine_callback";


    @Override
    public MachineCallback getMachineCallbackById(int id) {
        return jdbcHandler.queryOneBySql(MachineCallback.class, select_sql + " where id=?", id);
    }

    @Override
    public MachineCallback getMachineCallbackByOptId(long optId) {
        return jdbcHandler.queryOneBySql(MachineCallback.class, select_sql + " where optId=?", optId);
    }

    @Override
    public List<MachineCallback> getMachineCallbackByResult(int result) {
        return jdbcHandler.queryBySql(MachineCallback.class, select_sql + " where result=?" + result);
    }

    @Override
    public List<MachineCallback> getMachineCallbackByGroupId(int groupId) {
        return jdbcHandler.queryBySql(MachineCallback.class, select_sql + " where groupId=?", groupId);
    }

    @Override
    public List<MachineCallback> getMachineCallbackByStartTime(Date startTime) {
        return jdbcHandler.queryBySql(MachineCallback.class, select_sql + " where startTime>=?", startTime);
    }

    @Override
    public List<Object> getMachineSet(List<String> deviceIds,String tableName, Class clazz) {
        if (StringUtil.isNullOrEmpty(deviceIds) || StringUtil.isNullOrEmpty(tableName)) {
            return new ArrayList<>();
        }
        StringBuilder deviceId = new StringBuilder();
        for (int i = 0; i < deviceIds.size(); i++) {
            if (i > 0) {
                deviceId.append(",");
            }
            deviceId.append("'").append(deviceIds.get(i)).append("'");
        }
        String sql = "select * from " + tableName + " where deviceId in (" + deviceId.toString() + ")";
        return jdbcHandler.queryBySql(clazz,sql);
    }

    @Override
    public int insertOrUpdateMachineBDSetting(MachineBasicData machineBasicData) {

        String insertOrUpdate = "INSERT INTO machine_basic_data (deviceId,busId,language,advertMusicOnOff,advertMusicIntervalTime,coin,inning,coinRetain,playTime,gameType,probability,exitPosition,airGrip,bgMusic,gameMusic,photoEye,shake,retain,cusum,remark,createTime) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW()) ON DUPLICATE KEY UPDATE " +
                "language=?,advertMusicOnOff=?,advertMusicIntervalTime=?,coin=?,inning=?,coinRetain=?,playTime=?,gameType=?,probability=?,exitPosition=?,airGrip=?,bgMusic=?,gameMusic=?,photoEye=?,shake=?,retain=?,cusum=?,remark=?,updateTime=NOW()";

        int result = 0;
        if (machineBasicData != null) {
            result = jdbcHandler.executeSql(insertOrUpdate,machineBasicData.getDeviceId(),machineBasicData.getBusId(),machineBasicData.getLanguage(),machineBasicData.getAdvertMusicOnOff(),
                    machineBasicData.getAdvertMusicIntervalTime(),machineBasicData.getCoin(),machineBasicData.getInning(),machineBasicData.getCoinRetain(),machineBasicData.getPlayTime(),
                    machineBasicData.getGameType(),machineBasicData.getProbability(),machineBasicData.getExitPosition(),machineBasicData.getAirGrip(),machineBasicData.getBgMusic(),machineBasicData.getGameMusic(),
                    machineBasicData.getPhotoEye(),machineBasicData.getShake(),machineBasicData.getRetain(),machineBasicData.getCusum(),machineBasicData.getRemark(),

                    machineBasicData.getLanguage(),machineBasicData.getAdvertMusicOnOff(),
                    machineBasicData.getAdvertMusicIntervalTime(),machineBasicData.getCoin(),machineBasicData.getInning(),machineBasicData.getCoinRetain(),machineBasicData.getPlayTime(),
                    machineBasicData.getGameType(),machineBasicData.getProbability(),machineBasicData.getExitPosition(),machineBasicData.getAirGrip(),machineBasicData.getBgMusic(),machineBasicData.getGameMusic(),
                    machineBasicData.getPhotoEye(),machineBasicData.getShake(),machineBasicData.getRetain(),machineBasicData.getCusum(),machineBasicData.getRemark());

        }

        return result;
    }

    @Override
    public MachineBasicData getMachineBasicDataByDeviceId(String deviceId) {
        return jdbcHandler.queryOneBySql(MachineBasicData.class, "SELECT * FROM machine_basic_data WHERE deviceId=?", deviceId);
    }

    @Override
    public MachineBasicData getMachineBasicDataByBusId(int busId) {
        return jdbcHandler.queryOneBySql(MachineBasicData.class, "SELECT * FROM machine_basic_data WHERE busId=?", busId);
    }

    @Override
    public int insertOrUpdateMachineClawVoltage(MachineClawVoltage machineClawVoltage) {

        String insertOrUpdate = "INSERT INTO machine_claw_voltage (deviceId,busId,strongClawHigh,strongClawLow,weakClawHigh,weakClawLow,weakClawBackHigh,weakClawBackLow,winHigh,winLow,strongForceTime,weakForceTime,strongToWeakType,payingOffLengthTime,closeClawSpeed,riseDelay,dropDelayRatio,dropDelayTime,cusum,strongClawVoltage,weakClawVoltage,weakClawBackVoltage,winVoltage,remark,createTime) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW()) ON DUPLICATE KEY UPDATE " +
                "strongClawHigh=?,strongClawLow=?,weakClawHigh=?,weakClawLow=?,weakClawBackHigh=?,weakClawBackLow=?,winHigh=?,winLow=?,strongForceTime=?,weakForceTime=?,strongToWeakType=?,payingOffLengthTime=?,closeClawSpeed=?,riseDelay=?,dropDelayRatio=?,dropDelayTime=?,cusum=?,strongClawVoltage=?,weakClawVoltage=?,weakClawBackVoltage=?,winVoltage=?,remark=?,updateTime=NOW()";
        int result = 0;
        if (machineClawVoltage != null){
            result = jdbcHandler.executeSql(insertOrUpdate,machineClawVoltage.getDeviceId(),machineClawVoltage.getBusId(),machineClawVoltage.getStrongClawHigh(),machineClawVoltage.getStrongClawLow(),machineClawVoltage.getWeakClawHigh(),machineClawVoltage.getWeakClawLow(),
                    machineClawVoltage.getWeakClawBackHigh(),machineClawVoltage.getWeakClawBackLow(),machineClawVoltage.getWinHigh(),machineClawVoltage.getWinLow(),machineClawVoltage.getStrongForceTime(),machineClawVoltage.getWeakForceTime(),machineClawVoltage.getStrongToWeakType(),machineClawVoltage.getPayingOffLengthTime(),
                    machineClawVoltage.getCloseClawSpeed(),machineClawVoltage.getRiseDelay(),machineClawVoltage.getDropDelayRatio(),machineClawVoltage.getDropDelayTime(),machineClawVoltage.getCusum(),machineClawVoltage.getStrongClawVoltage(),machineClawVoltage.getWeakClawVoltage(),machineClawVoltage.getWeakClawBackVoltage(),machineClawVoltage.getWinVoltage(),machineClawVoltage.getRemark(),

                    machineClawVoltage.getStrongClawHigh(),machineClawVoltage.getStrongClawLow(),machineClawVoltage.getWeakClawHigh(),machineClawVoltage.getWeakClawLow(),
                    machineClawVoltage.getWeakClawBackHigh(),machineClawVoltage.getWeakClawBackLow(),machineClawVoltage.getWinHigh(),machineClawVoltage.getWinLow(),machineClawVoltage.getStrongForceTime(),machineClawVoltage.getWeakForceTime(),machineClawVoltage.getStrongToWeakType(),machineClawVoltage.getPayingOffLengthTime(),
                    machineClawVoltage.getCloseClawSpeed(),machineClawVoltage.getRiseDelay(),machineClawVoltage.getDropDelayRatio(),machineClawVoltage.getDropDelayTime(),machineClawVoltage.getCusum(),machineClawVoltage.getStrongClawVoltage(),machineClawVoltage.getWeakClawVoltage(),machineClawVoltage.getWeakClawBackVoltage(),machineClawVoltage.getWinVoltage(),machineClawVoltage.getRemark());
        }

        return result;
    }

    @Override
    public MachineClawVoltage getMachineClawVoltageByDeviceId(String deviceId) {
        return jdbcHandler.queryOneBySql(MachineClawVoltage.class,"SELECT * FROM machine_claw_voltage WHERE deviceId=?",deviceId);
    }

    @Override
    public MachineClawVoltage getMachineClawVoltageByBusId(int busId) {
        return jdbcHandler.queryOneBySql(MachineClawVoltage.class,"SELECT * FROM machine_claw_voltage WHERE busId=?",busId);
    }

    @Override
    public MachineMotorSpeed getMachineMotorSpeedByDeviceId(String deviceId) {
        return jdbcHandler.queryOneBySql(MachineMotorSpeed.class,"SELECT * FROM machine_motor_speed WHERE deviceId=?",deviceId);
    }

    @Override
    public MachineMotorSpeed getMachineMotorSpeedByBusId(int busId) {
        return jdbcHandler.queryOneBySql(MachineMotorSpeed.class,"SELECT * FROM machine_motor_speed WHERE busId=?",busId);
    }

    @Override
    public int insertOrUpdateMachineMotorSpeed(MachineMotorSpeed machineMotorSpeed) {

        String insertOrUpdate = "INSERT INTO machine_motor_speed(deviceId,busId,frontAndBack,leftAndRight,upAndDown,cusum,remark,createTime) " +
                "VALUES (?,?,?,?,?,?,?,NOW()) ON DUPLICATE KEY UPDATE " +
                "frontAndBack=?,leftAndRight=?,upAndDown=?,cusum=?,remark=?,updateTime=NOW()";
        int result = 0;
        if (machineMotorSpeed != null){
            result = jdbcHandler.executeSql(insertOrUpdate,
                    machineMotorSpeed.getDeviceId(),machineMotorSpeed.getBusId(),machineMotorSpeed.getFrontAndBack(),machineMotorSpeed.getLeftAndRight(),machineMotorSpeed.getUpAndDown(),machineMotorSpeed.getCusum(),machineMotorSpeed.getRemark(),
                    machineMotorSpeed.getFrontAndBack(),machineMotorSpeed.getLeftAndRight(),machineMotorSpeed.getUpAndDown(),machineMotorSpeed.getCusum(),machineMotorSpeed.getRemark());
        }
        return result;
    }
}
