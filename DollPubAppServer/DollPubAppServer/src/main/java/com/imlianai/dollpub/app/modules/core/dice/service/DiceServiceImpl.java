package com.imlianai.dollpub.app.modules.core.dice.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.core.dice.consts.DiceConsts;
import com.imlianai.dollpub.app.modules.core.dice.dao.DiceDao;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.domain.dice.Dice;
import com.imlianai.dollpub.domain.dice.DiceStatus;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.machine.iface.IMachineConnectRemoteService;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wurui
 * @create 2018-06-01 11:55
 **/
@Service
public class DiceServiceImpl implements DiceService {

    BaseLogger logger = BaseLogger.getLogger(getClass());

    @Reference
    private IMachineConnectRemoteService iMachineConnectRemoteService;

    @Resource
    private DollBusService dollBusService;

    @Resource
    private DiceDao diceDao;

    @Override
    public long start(int busId, long uid, int type) {
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null && dollBus.getType() == 2) {
            //上机
            logger.info("骰子机类型");
            long optId = diceDao.addDiceRecord(new Dice(dollBus.getDeviceId(), dollBus.getBusId()));
            if (optId > 0) {
                logger.info("新增骰子记录");
                if (diceDao.addDiceStatus(new DiceStatus(dollBus.getBusId(), dollBus.getDeviceId(), optId, uid)) > 0) {
                    logger.info("新增摇骰子上机状态");
                    logger.info("发送要骰子指令");
                    if (type == 1) {
                        iMachineConnectRemoteService.handleDirective(dollBus.getBusId(), DiceConsts.SHAKE_AND_RETURN, "");
                    } else if (type == 2) {
                        iMachineConnectRemoteService.handleDirective(dollBus.getBusId(), DiceConsts.START_SHAKE_DICE, "");
                    } else {
                        iMachineConnectRemoteService.handleDirective(dollBus.getBusId(), DiceConsts.SHAKE_AND_RETURN, "");
                    }
                    return optId;
                }
            }
        }
        return 0;
    }

    @Override
    public BaseRespVO stop(long optId, int busId, long uid) {
        DiceStatus diceStatus = diceDao.getDiceStatusByBusId(busId);
        if (diceStatus != null) {
            if (diceStatus.getOptId() == optId && diceStatus.getUid() == uid) {
                logger.info("发送停骰子指令");
                iMachineConnectRemoteService.handleDirective(busId, DiceConsts.STOP_SHAKE_DICE, "");
                return new BaseRespVO(200, true, "发送停止骰子指令成功,等待接收结果");
            } else {
                return new BaseRespVO(0, false, "非法操作");
            }
        }
        return new BaseRespVO(0, false, "停止骰子失败");
    }

    @Override
    public boolean isPlay(int busId) {
        boolean result = false;
        DiceStatus diceStatus = diceDao.getDiceStatusByBusId(busId);
        if (diceStatus == null) {
            result = true;
        }
        return result;
    }

    @Override
    public void resultHandle(DollBus dollBus, String deviceId, List<String> hexs) {
        if (!StringUtil.isNullOrEmpty(hexs)) {
            switch (hexs.get(2)) {
                case "03"://结果
                case "06":
                    logger.info("dollBus=>" + JSON.toJSONString(dollBus) + ",deviceId=>" + deviceId + ",hexs" + JSON.toJSONString(hexs));
                    if (hexs.get(3).equalsIgnoreCase("02")) {
                        logger.info("识别点数成功");
                        updateResult(dollBus, deviceId, Integer.parseInt(hexs.get(4), 16), JSON.toJSONString(hexs));
                        //删除状态
                        if (diceDao.deleteDiceStatus(dollBus.getBusId()) > 0) {
                            logger.info("删除骰子机状态");
                        }
                    }
                    break;
                case "04"://开始摇骰子
                    logger.info("正在摇骰子");
                    break;
                case "05":
                    if (hexs.get(3).equalsIgnoreCase("02")) {
                        logger.info("停止摇骰子成功,发送识别点数指令");
                        iMachineConnectRemoteService.handleDirective(dollBus.getBusId(), DiceConsts.QUERY_RETURN, "");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void handleTimeOutRecord() {
        List<DiceStatus> diceStatusList = diceDao.getTimeOutRecord(15);//超过10秒的超时记录
        if (!StringUtil.isNullOrEmpty(diceStatusList)) {
            for (DiceStatus diceStatus : diceStatusList) {
                try {

                    logger.info("正在处理超时记录,发送停止摇骰子指令");
                    iMachineConnectRemoteService.handleDirective(diceStatus.getBusId(), DiceConsts.STOP_SHAKE_DICE, "");

                    Thread.sleep(500);

                    logger.info("正在处理超时记录,发送识别点数指令");
                    iMachineConnectRemoteService.handleDirective(diceStatus.getBusId(), DiceConsts.QUERY_RETURN, "");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateResult(DollBus dollBus, String deviceId, int point, String remark) {
        DiceStatus diceStatus = diceDao.getDiceStatusByBusId(dollBus.getBusId());
        logger.info("diceStatus=>" + JSON.toJSONString(diceStatus));
        if (diceStatus != null && diceStatus.getDeviceId().equals(deviceId)) {
            Dice dice = diceDao.getDiceByOptId(diceStatus.getOptId());
            logger.info("dice=>" + JSON.toJSONString(dice));
            if (dice != null && dice.getStatus() == 0) {
                if (diceDao.updateDiceResult(diceStatus.getOptId(), point, remark) > 0) {
                    logger.info("骰子=>" + diceStatus.getOptId() + ",结果=>" + point);
                }
            }
        }
    }

}
