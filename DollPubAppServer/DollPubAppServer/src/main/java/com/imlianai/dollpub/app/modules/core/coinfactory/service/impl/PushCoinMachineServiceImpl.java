package com.imlianai.dollpub.app.modules.core.coinfactory.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.imlianai.dollpub.app.modules.core.coinfactory.dao.PushCoinCallbackDao;
import com.imlianai.dollpub.app.modules.core.coinfactory.dto.MachinePushCoinDTO;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.app.modules.core.coinfactory.consts.PushCoinCmdConst;
import com.imlianai.dollpub.app.modules.core.coinfactory.consts.PushCoinSupportConst;
import com.imlianai.dollpub.app.modules.core.coinfactory.dao.PushBusStatusDao;
import com.imlianai.dollpub.app.modules.core.coinfactory.dao.PushCoinMachineDao;
import com.imlianai.dollpub.app.modules.core.coinfactory.dao.PushCoinOptRouterDao;
import com.imlianai.dollpub.app.modules.core.coinfactory.service.PushCoinMachineService;
import com.imlianai.dollpub.app.modules.core.coinfactory.vo.ApplyRespVO;
import com.imlianai.dollpub.app.modules.core.coinfactory.vo.OperateRespVO;
import com.imlianai.dollpub.constants.MachineStateConst;
import com.imlianai.dollpub.domain.callback.DollMachSignUtil;
import com.imlianai.dollpub.domain.coinfactory.MachinePushCoin;
import com.imlianai.dollpub.domain.coinfactory.PushBusStatus;
import com.imlianai.dollpub.domain.coinfactory.PushCoinCallbackLog;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.trade.*;
import com.imlianai.dollpub.machine.iface.IMachineConnectRemoteService;
import com.imlianai.dollpub.machine.iface.IMachineRemoteService;
import com.imlianai.dollpub.machine.iface.domain.MachineDevice;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.NotEnoughBeanException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.MD5_HexUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wurui
 * @create 2018-03-28 10:42
 **/
@Service
public class PushCoinMachineServiceImpl implements PushCoinMachineService {

    private BaseLogger logger = BaseLogger.getLogger(getClass());

    @Reference
    private IMachineRemoteService iMachineRemoteService;

    @Reference
    private IMachineConnectRemoteService iMachineConnectRemoteService;

    @Resource
    private PushCoinMachineDao pushCoinMachineDao;

    @Resource
    private PushBusStatusDao pushBusStatusDao;

    @Resource
    private PushCoinOptRouterDao pushCoinOptRouterDao;

    @Resource
    private CustomerService customerService;

    @Resource
    private DollBusService dollBusService;

    @Resource
    private TradeService tradeService;

    @Resource
    private PushCoinCallbackDao pushCoinCallbackDao;

    @Override
    public BaseRespVO apply(long uid, int busId, Customer customer) {
        MachineDevice machineDevice = iMachineRemoteService.getMachineState(busId);
        if (machineDevice != null) {
            if (machineDevice.getStatus() == MachineStateConst.IS_PLAY) {
                return new BaseRespVO(12, false, "当前机器有人正在玩~~~!");
            }
            if (machineDevice.getStatus() == MachineStateConst.BROKEN) {
                return new BaseRespVO(11, false, "机器故障!");
            }

            DollBus dollBus = dollBusService.getDollBus(machineDevice.getBusId());
            if (dollBus != null){

                if (dollBus.getType()==1){

                    if (customer.getGroupId() != dollBus.getGroupId()){
                        return new BaseRespVO(30,false,"非法机器操作");
                    }


                    if (uid != 0) {
                        try {
                            //空闲
                            if (machineDevice.getStatus() == MachineStateConst.LEISURE) {
                                if (pushCoinMachineDao.updateMachineLocked(machineDevice.getDeviceId(), 1, uid) > 0) {
                                    PushBusStatus pushBusStatus = pushBusStatusDao.get(machineDevice.getBusId());

                                    //空闲
                                    if (pushBusStatus == null) {
                                        long optId = this.applyOperate(uid, customer, machineDevice,dollBus.getPlayTime());
                                        if (optId > 0) {
                                            ApplyRespVO vo = new ApplyRespVO();
                                            vo.setMsg("上机成功!");
                                            vo.setResult(100);
                                            vo.setState(true);
                                            vo.setOptId(optId);
                                            vo.setPlayTime(dollBus.getPlayTime());
                                            return vo;
                                        } else {
                                            logger.info("新增机器状态失败 busId=>" + busId);
                                        }
                                    } else {
                                        logger.info("当前机器有人正在玩~~~");
                                        return new BaseRespVO(10, false, "上机失败，当前机器有人正在玩~~~");
                                    }
                                } else {
                                    logger.info("machine device 状态更新失败 busId=>" + busId);
                                }
                            } else {
                                logger.info("machineDevice status=>" + machineDevice.getStatus());
                            }
                        } catch (Exception e) {
                            this.rollback(busId);
                            logger.error(e.getMessage());
                        }
                        return new BaseRespVO(0, false, "上机失败!");

                    } else {
                        return new BaseRespVO(8, false, "当前用户不存在!");
                    }
                }else {
                    return new BaseRespVO(20, false, "机器类型错误!");
                }
            }

        }
        return new BaseRespVO(9, false, "机器不存在=>" + busId);
    }

    @Override
    public BaseRespVO putCoin(long uid, int busId, Customer customer) {
        MachineDevice machineDevice = iMachineRemoteService.getMachineState(busId);
        logger.info("putCoin=>" + JSON.toJSONString(machineDevice));

        if (machineDevice != null) {
            if (machineDevice.getStatus() == MachineStateConst.BROKEN) {
                return new BaseRespVO(11, false, "机器故障!");
            }

            DollBus dollBus = dollBusService.getDollBus(machineDevice.getBusId());
            if (dollBus != null) {

                if (dollBus.getType() == 1) {

                    if (customer.getGroupId() != dollBus.getGroupId()){
                        return new BaseRespVO(30,false,"非法机器操作");
                    }

                    //本地机器状态
                    PushBusStatus pushBusStatus = pushBusStatusDao.get(busId);
                    logger.info("putCoin=======>" + JSON.toJSONString(pushBusStatus));

                    if (pushBusStatus != null) {

                        //正在上机的用户,可继续投币
                        if (pushBusStatus.getUid() == uid && pushBusStatus.getLockState() != 1) {
                            logger.info("玩家=>" + uid + ",optId=>" + pushBusStatus.getOptId());

                            try {

                                //更新投币次数
                                int result = pushCoinMachineDao.updateCoin(pushBusStatus.getOptId(), PushCoinSupportConst.COIN, 0);
                                if (result > 0) {
                                    logger.info("玩家连续投币成功....");

                                    if (pushCoinOptRouterDao.updateCoin(customer.getGroupId(), pushBusStatus.getOptId(), PushCoinSupportConst.COIN, 0) > 0) {
                                        logger.info("[正常投币] 推币机分表操作-->投币成功");
                                    }

                                    //发指令
                                    iMachineConnectRemoteService.handleDirective(pushBusStatus.getBusId(), PushCoinCmdConst.PUT_COIN_CMD, "投币");
                                    logger.info("[正常投币]=>发送投币指令......");

                                    iMachineConnectRemoteService.handleDirective(pushBusStatus.getBusId(), PushCoinCmdConst.OUTPUT_SIGNAL_COIN, "投币输出");
                                    logger.info("[正常投币]=>发送投币输出指令......");

                                    //更新结束时间
                                    int flag = pushBusStatusDao.updateEndTime(busId, dollBus.getPlayTime());
                                    logger.info(flag > 0 ? "更新结束时间=>" + flag : "更新结束时间失败=>" + flag);

                                    OperateRespVO vo = new OperateRespVO();
                                    vo.setMsg("成功投入=>" + PushCoinSupportConst.COIN + " 币");
                                    vo.setResult(100);
                                    vo.setState(true);
                                    vo.setOptId(pushBusStatus.getOptId());
                                    vo.setPlayTime(dollBus.getPlayTime());
                                    return vo;
                                } else {
                                    logger.info("更新投币失败=>" + result);
                                }
                            } catch (Exception e) {
                                this.rollback(busId);
                                logger.error(e.getMessage());
                            }
                            return new BaseRespVO(12, false, "投币失败，投币太快啦~~~");
                        } else {
                            return new BaseRespVO(13, false, "非法操作,当前占机用户=>" + pushBusStatus.getUid());
                        }
                    } else {

                        logger.info("跳过开始直接投币 uid=>" + uid + ",busId=>" + busId);

                        if (machineDevice.getStatus() == MachineStateConst.IS_PLAY) {
                            return new BaseRespVO(10, false, "上机失败，有人正在玩~~");
                        }

                        //上机
                        try {
                            if (machineDevice.getStatus() == MachineStateConst.LEISURE) {

                                //设置为正在玩
                                int flag = pushCoinMachineDao.updateMachineLocked(machineDevice.getDeviceId(), PushCoinSupportConst.IS_PLAY, uid);
                                if (flag > 0) {
                                    // 必须参数：deviceId,busId,uid,customerId
                                    long optId = this.applyOperate(uid, customer, machineDevice,dollBus.getPlayTime());
                                    if (optId > 0) {
                                        //投币
                                        if (pushCoinMachineDao.updateCoin(optId, 1, 0) > 0) {

                                            if (pushCoinOptRouterDao.updateCoin(customer.getGroupId(), optId, PushCoinSupportConst.COIN, 0) > 0) {
                                                logger.info("[跳过开始] 推币机分表操作-->投币成功");
                                            }

                                            iMachineConnectRemoteService.handleDirective(busId, PushCoinCmdConst.PUT_COIN_CMD, "投币");
                                            logger.info("[跳过开始]=>发送投币指令......");

                                            iMachineConnectRemoteService.handleDirective(busId, PushCoinCmdConst.OUTPUT_SIGNAL_COIN, "投币输出");
                                            logger.info("[跳过开始]=>发送投币输出指令......");

                                            ApplyRespVO vo = new ApplyRespVO();
                                            vo.setMsg("跳过上机,投入" + PushCoinSupportConst.COIN + "币");
                                            vo.setResult(101);
                                            vo.setState(true);
                                            vo.setOptId(optId);
                                            vo.setPlayTime(dollBus.getPlayTime());
                                            return vo;
                                        } else {
                                            return new BaseRespVO(12, false, "投币失败，投币太快啦~~~");
                                        }
                                    }
                                } else {
                                    return new BaseRespVO(0, false, "机器状态设置失败!");
                                }
                            }
                        } catch (Exception e) {
                            this.rollback(busId);
                            logger.error(e.getMessage());
                        }
                    }
                }else {
                    return new BaseRespVO(20, false, "机器类型错误!");
                }
            }
        }
        return new BaseRespVO(9, false, "机器不存在=>" + busId);
    }


    @Override
    public BaseRespVO operate(long uid, int busId) {
        PushBusStatus pushBusStatus = pushBusStatusDao.get(busId);
        if (pushBusStatus != null ) {
            logger.info("玩家=>" + uid + ",操作摆动=>" + busId);
            if (pushBusStatus.getUid() == uid){
                iMachineConnectRemoteService.handleDirective(pushBusStatus.getBusId(), PushCoinCmdConst.OUTPUT_SIGNAL, "操作摆动");
                logger.info("发送机器摆动操作...");
                return new BaseRespVO(100, true, "操作成功");
            }else {
                return new BaseRespVO(0, true, "非法操作");
            }
        }
        return new BaseRespVO(0, false, "操作失败,请先投币~");
    }

    @Override
    public BaseRespVO finish(long uid, int busId) {
        PushBusStatus pushBusStatus = pushBusStatusDao.get(busId);
        logger.info("finish=>" + JSON.toJSONString(pushBusStatus));
        if (pushBusStatus != null ){
            if (pushBusStatus.getUid() == uid && pushBusStatus.getLockState() != 1 ){
                logger.info("finish 玩家=>" + uid + ",主动下机=>" + busId);
                iMachineConnectRemoteService.handleDirective(pushBusStatus.getBusId(), PushCoinCmdConst.QUERY_REFUND_COIN_OPTICAL_COUNT_CMD, "查询光眼计数");
                if (pushBusStatusDao.updateLockState(pushBusStatus.getBusId()) > 0) {
                    logger.info("finish resultFinal=>lock....");
                    return new BaseRespVO(100,true,"下机操作成功,等待接收最终结果...");
                }
            }else {
                return new BaseRespVO(0,false,"非法操作!");
            }
        }
        return new BaseRespVO(0,false,"请先申请上机!");
    }

    @Override
    public void resultHandle(final DollBus dollBus, final String deviceId, final String coin) {
        logger.info("======resultHandle======");
        PushBusStatus pushBusStatus = pushBusStatusDao.get(dollBus.getBusId());
        if (pushBusStatus != null) {
            try {
                Customer customer = null;
                MachinePushCoin machinePushCoin = pushCoinMachineDao.getOne(pushBusStatus.getOptId());
                if (machinePushCoin != null) {
                    customer = customerService.getCustomerById(machinePushCoin.getCustomerId());
                }
                int coin_int = Integer.parseInt(coin, 16);
                logger.info("收到币数=================================>" + coin_int);
                if (coin_int > 0) {
                    int result = pushCoinMachineDao.updateCoin(pushBusStatus.getOptId(), coin_int, 1);
                    if (result > 0) {

                        if (customer != null) {
                            if (pushCoinOptRouterDao.updateCoin(customer.getGroupId(), pushBusStatus.getOptId(), coin_int, 1) > 0) {
                                logger.info("推币机分表操作-->入币成功");
                                //  发送到萌趣(出币转钻石)
                                this.sendToDollApp(customer,dollBus,pushBusStatus.getUid(),pushBusStatus.getOptId(),coin_int,0);
                            }
                        }

                        MachineDevice machineDevice = iMachineRemoteService.getMachineState(pushBusStatus.getBusId());
                        if (machineDevice != null && machineDevice.getStatus() != MachineStateConst.BROKEN){
                            //更新结束时间
                            int flag = pushBusStatusDao.updateEndTime(pushBusStatus.getBusId(), machineDevice.getPlayTime());
                            logger.info(flag > 0 ? "[回调] 更新结束时间=>" + flag : "[回调] 更新结束时间失败=>" + flag);
                        }

                        logger.info("optId=>" + pushBusStatus.getOptId() + ",增加=> " + coin_int + " 币");
                    }
                }
                int finalState = pushBusStatus.getLockState();
                if (finalState == 1) {
                    if (pushCoinMachineDao.updateState(pushBusStatus.getOptId()) > 0) {
                        logger.info("锁定最终流水=>" + pushBusStatus.getOptId());

                        if (customer != null) {
                            if (pushCoinOptRouterDao.updateState(customer.getGroupId(), pushBusStatus.getOptId()) > 0) {
                                logger.info("推币机分表操作-->锁定最终流水=>" + pushBusStatus.getOptId());
                            }
                            this.customerResultNotice(customer,dollBus,pushBusStatus.getOptId());
                            //  发送到萌趣(出币转钻石)
                            this.sendToDollApp(customer,dollBus,pushBusStatus.getUid(),pushBusStatus.getOptId(),0,1);
                        }

                        if (pushCoinMachineDao.updateMachineLocked(pushBusStatus.getDeviceId(), 0, 0) > 0) {
                            if (pushBusStatusDao.delete(dollBus.getBusId()) > 0) {
                                logger.info("清除机器状态=>" + dollBus.getBusId());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public void resultFinal() {
        try {
            //List<PushBusStatus> pushBusStatusList = pushBusStatusDao.getEndTimeTimeOutRecord(15);//超过10秒自动终止

            //拿到所有超时记录(超过xx秒未投币则为最终记录)
            List<PushBusStatus> pushBusStatusList = pushBusStatusDao.getEndTimeTimeOutRecord();
            logger.info("resultFinal=>" + JSON.toJSONString(pushBusStatusList));
            if (!StringUtil.isNullOrEmpty(pushBusStatusList)) {
                for (PushBusStatus pushBusStatus : pushBusStatusList) {
                    if (pushBusStatus.getLockState() != 1) {
                        try{
                            MachinePushCoin machinePushCoin = pushCoinMachineDao.getOne(pushBusStatus.getOptId());
                            if (machinePushCoin != null && machinePushCoin.getState() != 1) {//不为最终状态时

                                DollBus dollBus = dollBusService.getDollBus(pushBusStatus.getBusId());
                                if (dollBus != null){

                                    if (dollBus.getQueryType() == 0){
                                        logger.info("查询光眼 busId=>" + dollBus.getBusId());
                                        iMachineConnectRemoteService.handleDirective(pushBusStatus.getBusId(), PushCoinCmdConst.QUERY_REFUND_COIN_OPTICAL_COUNT_CMD, "查询光眼计数");
                                    }else {
                                        if (dollBus.getQueryType() == 1){
                                            logger.info("查询礼品 busId=>" + dollBus.getBusId());
                                            iMachineConnectRemoteService.handleDirective(pushBusStatus.getBusId(), PushCoinCmdConst.QUERY_GIFT_CMD, "查询礼品计数");
                                        }
                                    }
                                }

                                //iMachineConnectRemoteService.handleDirective(pushBusStatus.getBusId(), PushCoinCmdConst.QUERY_REFUND_COIN_OPTICAL_COUNT_CMD, "查询光眼计数");

                                logger.info("超过10秒未投币,查询最终结果=>" + JSON.toJSONString(pushBusStatus));
                                //锁定状态
                                if (pushBusStatusDao.updateLockState(pushBusStatus.getBusId()) > 0) {
                                    logger.info("resultFinal=>lock....");
                                }
                            }
                        }catch (Exception e){
                            this.rollback(pushBusStatus.getBusId());
                            logger.info("锁定状态异常=>" + e.getMessage());
                        }
                    }else {

                        try {
                            //判断时间
                            if(DateUtils.addSECONDToDate(pushBusStatus.getLockTime(),10).getTime() <= new Date().getTime()){
                                MachinePushCoin pushCoin = pushCoinMachineDao.getOne(pushBusStatus.getOptId());
                                if (pushCoin != null && pushCoin.getState() == 1){
                                    //  1、强制更改机器状态  2、删除机器状态
                                    this.rollback(pushBusStatus.getBusId());
                                }
                            }
                        }catch (Exception e){
                            logger.info("resultFinal 清理异常 err=>" + e.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void perSecondUpdateOutCoin() {
        try {
            List<PushBusStatus> pushBusStatusList = pushBusStatusDao.getAllPushBusStatus();
            logger.info("perSecondUpdateOutCoin=>" + JSON.toJSONString(pushBusStatusList));
            if (!StringUtil.isNullOrEmpty(pushBusStatusList)) {
                for (PushBusStatus pushBusStatus : pushBusStatusList) {

                    DollBus dollBus = dollBusService.getDollBus(pushBusStatus.getBusId());
                    if (dollBus != null){

                        if (dollBus.getQueryType() == 0){
                            logger.info("查询光眼 busId=>" + dollBus.getBusId());
                            iMachineConnectRemoteService.handleDirective(pushBusStatus.getBusId(), PushCoinCmdConst.QUERY_REFUND_COIN_OPTICAL_COUNT_CMD, "查询光眼计数");
                        }else {
                            if (dollBus.getQueryType() == 1){
                                logger.info("查询礼品 busId=>" + dollBus.getBusId());
                                iMachineConnectRemoteService.handleDirective(pushBusStatus.getBusId(), PushCoinCmdConst.QUERY_GIFT_CMD, "查询礼品计数");
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.info("定时光眼查询异常=>" + e.getMessage());
        }
    }

    @Override
    public MachinePushCoin getOptRecordRouter(int groupId,long optId) {
        return pushCoinOptRouterDao.getOne(groupId,optId);
    }

    @Override
    public MachinePushCoin getOptRecord(long optId) {
        return pushCoinMachineDao.getOne(optId);
    }

    @Override
    public BaseRespVO getPushCoinStatus(int busId) {
        PushBusStatus pushBusStatus = pushBusStatusDao.get(busId);
        Map<String,Object> data = Maps.newHashMap();
        if (pushBusStatus != null){
            BaseRespVO vo = new BaseRespVO(100,true,"当前机器处于占机状态");
            data.put("uid",pushBusStatus.getUid());
            data.put("optId",pushBusStatus.getOptId());
            data.put("busId",pushBusStatus.getBusId());
            vo.setData(data);
            return vo;
        }
        BaseRespVO vo = new BaseRespVO(0,true,"当前机器空闲");
        data.put("uid",0);
        data.put("optId",0);
        data.put("busId",busId);
        vo.setData(data);
        return vo;
    }

    @Override
    public void customerResultNotice(Customer customer, DollBus dollBus,long optId) {
        logger.info("推币机商户回调=>customerResultNotice");
        if (customer != null && customer.getLevel() != 1 && customer.getId() != 88 && customer.getId() != 81){
            MachinePushCoin machinePushCoin = this.getOptRecordRouter(customer.getGroupId(),optId);
            logger.info("推币机商户操作记录=>" + JSON.toJSONString(machinePushCoin));
            if (machinePushCoin != null){

                Map<String, Object> postMap = JSON.parseObject(JSON.toJSONString(MachinePushCoinDTO.adapter(machinePushCoin)),
                        new TypeReference<Map<String, Object>>() {});
                postMap.put("ts", System.currentTimeMillis());
                postMap.put("appId", customer.getAppId());

                String signText = DollMachSignUtil.createLinkString(DollMachSignUtil.paraFilter(postMap));
                logger.info("signText:" + signText);

                String firstSign = MD5_HexUtil.md5Hex(signText);
                logger.info("firstSign:" + firstSign);

                String signRes = MD5_HexUtil.md5Hex(firstSign + customer.getAppKey());
                logger.info("signRes:" + signRes);

                postMap.put("sign", signRes);

                logger.info("推币机商户操作记录 postMap=>" + JSON.toJSONString(postMap));

                try {
                    if (!StringUtil.isNullOrEmpty(customer.getPushCallbackUrl())) {

                        PushCoinCallbackLog pushCoinCallbackLog = new PushCoinCallbackLog(
                                dollBus.getDeviceId(),dollBus.getBusId(),optId,
                                customer.getGroupId(),customer.getId(),JSON.toJSONString(machinePushCoin),
                                customer.getPushCallbackUrl(),JSON.toJSONString(postMap),
                                0,"实体机");

                        if(pushCoinCallbackDao.insert(pushCoinCallbackLog) > 0){
                            logger.info("新增推币机回调记录=>" + dollBus.getBusId());
                        }


                        HttpEntity httpEntity = HttpUtil.Post(customer.getPushCallbackUrl(), JSON.toJSONString(postMap));
                        if (httpEntity != null) {
                            logger.info("通知结果回调=>" + JSON.toJSONString(httpEntity));

                            pushCoinCallbackLog.setResponse(JSON.toJSONString(httpEntity));
                            if(pushCoinCallbackDao.update(pushCoinCallbackLog) > 0){
                                logger.info("更新推币机回调记录=>" + dollBus.getBusId());
                            }


                        }
                    }
                } catch (Exception e) {
                    logger.info("push coin customerResultNotice error ==>" + e.getMessage());
                }
            }
        }
    }

    @Override
    public int getUserCoin(long uid) {
        TradeAccount tradeAccount = tradeService.getAccount(uid);
        if (tradeAccount != null){
            return tradeAccount.getCoin();
        }
        return 0;
    }

    @Override
    public boolean isCanPushCoin(long uid, int busId) {
        PushBusStatus pushBusStatus = pushBusStatusDao.get(busId);
        if (pushBusStatus != null){
            if (pushBusStatus.getUid() == uid && pushBusStatus.getLockState() != 1){
                return true;
            }
        }
        return false;
    }


    /**
     * 上机逻辑
     *
     * @param uid
     * @param customer
     * @param machineDevice
     * @return 操作ID
     */
    private long applyOperate(long uid, Customer customer, MachineDevice machineDevice,int playTime) {
        if (customer != null) {
            try {
                // 必须参数：deviceId,busId,uid,customerId
                MachinePushCoin machinePushCoin = new MachinePushCoin();
                machinePushCoin.setDeviceId(machineDevice.getDeviceId());
                machinePushCoin.setBusId(machineDevice.getBusId());
                machinePushCoin.setUid(uid);
                machinePushCoin.setCustomerId(customer.getId());
                long optId = pushCoinMachineDao.insert(machinePushCoin);
                if (optId > 0) {
                    logger.info("新增推币机操作流水=>" + optId);

                    machinePushCoin.setOptId(optId);
                    if (pushCoinOptRouterDao.insert(customer.getGroupId(), machinePushCoin) > 0) {
                        logger.info("新增推币机分表操作流水=>" + JSON.toJSONString(machinePushCoin));
                    }

                    //状态
                    if (pushBusStatusDao.insert(new PushBusStatus(machineDevice.getBusId(), machineDevice.getDeviceId(), optId, uid), playTime) > 0) {
                        return optId;
                    }
                } else {
                    logger.info("新增操作流水失败=>" + JSON.toJSONString(machinePushCoin));
                }
            } catch (Exception e) {
                this.rollback(machineDevice.getBusId());
                logger.error("applyOperate=>" + e.getMessage());
            }
        }
        return 0;
    }

    /**
     * 失败操作强制回滚
     *
     * @param busId
     */
    private void rollback(int busId) {
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null) {
            pushCoinMachineDao.updateMachineLocked(dollBus.getDeviceId(), 0, 0);
            pushBusStatusDao.delete(dollBus.getBusId());
            logger.info("rollback 失败强制回滚成功=>" + JSON.toJSONString(dollBus));
        }
    }

    /**
     * 处理投币消费
     * @param uid
     * @param busId
     * @return
     */
    @Override
    public int consumeHandle(long uid, int busId){
        int result = 0;
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null){
            TradeRecord record = new TradeRecord(uid, 0,
                    TradeType.PUSH_COIN.type, dollBus.getBusId(), dollBus.getPrice(),
                    TradeCostType.COST_COIN.type,dollBus.getNickName() + ",投币" + dollBus.getPrice() + "币");
            try {
                if(tradeService.consume(record)){
                    result = 1;
                }
            } catch (TradeOperationException | NotEnoughBeanException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    //发送到doll_app的请求
    private void sendToDollApp(Customer customer,DollBus dollBus,long uid,long optId,int outCoin,int isFinal){
        logger.info("sendToDollApp 开始将结果发送到zjdoll_app=>" + JSON.toJSONString(customer));
        if (customer != null && customer.getInside() == 1){
            Map<String,Object> postMap = Maps.newHashMap();
            postMap.put("busId",dollBus.getBusId());
            postMap.put("optId",optId);
            postMap.put("outCoin",outCoin);
            postMap.put("isFinal",isFinal);
            postMap.put("uid",uid);
            postMap.put("auto","dollPub_service_app_88");
            try {
                if (!StringUtil.isNullOrEmpty(customer.getPushCallbackUrl())) {

                    String param = JSON.toJSONString(postMap);

                    logger.info("sendToDollApp customer=>" +customer.getName()+",url=>" + customer.getPushCallbackUrl() + ",参数=>" + param);

                    try {

                        if (isFinal == 1){

                            PushCoinCallbackLog pushCoinCallbackLog = new PushCoinCallbackLog(
                                    dollBus.getDeviceId(),dollBus.getBusId(),optId,
                                    customer.getGroupId(),customer.getId(),"",
                                    customer.getPushCallbackUrl(),JSON.toJSONString(postMap),
                                    0,"实体机");

                            MachinePushCoin machinePushCoin = pushCoinMachineDao.getOne(optId);
                            if (machinePushCoin != null){
                                pushCoinCallbackLog.setRecord(JSON.toJSONString(machinePushCoin));
                            }


                            if(pushCoinCallbackDao.insert(pushCoinCallbackLog) > 0){
                                logger.info("新增推币机回调记录=>" + dollBus.getBusId());
                            }
                            HttpEntity httpEntity = HttpUtil.Post(customer.getPushCallbackUrl(), param);
                            if (httpEntity != null) {
                                logger.info("sendToDollApp 得币通知结果回调=>" + JSON.toJSONString(httpEntity));
                                pushCoinCallbackLog.setResponse(JSON.toJSONString(httpEntity));
                                if(pushCoinCallbackDao.update(pushCoinCallbackLog) > 0){
                                    logger.info("更新推币机回调记录=>" + dollBus.getBusId());
                                }
                            }

                        }else {

                            HttpEntity httpEntity = HttpUtil.Post(customer.getPushCallbackUrl(), param);
                            if (httpEntity != null) {
                                logger.info("sendToDollApp 得币通知结果回调=>" + JSON.toJSONString(httpEntity));
                            }
                        }

                    }catch (Exception e){
                        logger.info("回调异常=>"+ e.getMessage());
                    }
                }
            } catch (Exception e) {
                logger.info("sendToDollApp push coin customerResultNotice error ==>" + e.getMessage());
            }
        }
    }


    public static void main(String[] args) {

        //int time = DateUtils.secondBetween(DateUtils.addSECONDToDate(new Date().setTime(1),10),new Date());

        String a = "2018-07-16 10:09:00";
        String b = "2018-07-16 10:09:10";
        try {
            Date ad= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(a);
            Date bd= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(b);
            System.out.println(DateUtils.addSECONDToDate(ad,10).getTime() <= bd.getTime());


            System.out.println(DateUtils.secondBetween(DateUtils.addSECONDToDate(ad,9),new Date()));

            //int time = DateUtils.secondBetween(DateUtils.addSECONDToDate(date,10),new Date());
            //System.out.println(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}
