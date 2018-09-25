package com.imlianai.zjdoll.app.modules.core.push.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.imlianai.dollpub.domain.coinfactory.MachinePushCoin;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.rpc.support.utils.SysTimerHandle;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoDao;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.ZengjingUtils;
import com.imlianai.zjdoll.app.modules.core.push.consts.PushCoinConsts;
import com.imlianai.zjdoll.app.modules.core.push.dao.PushCoinBoxDao;
import com.imlianai.zjdoll.app.modules.core.push.dao.PushCoinOptRouterDao;
import com.imlianai.zjdoll.app.modules.core.push.dao.PushCoinPointGiveDao;
import com.imlianai.zjdoll.app.modules.core.push.dto.UserPointGiveRecordDTO;
import com.imlianai.zjdoll.app.modules.core.push.dto.UserPushCoinRecordDTO;
import com.imlianai.zjdoll.app.modules.core.push.virtual.util.ZengjingVirtualUtils;
import com.imlianai.zjdoll.app.modules.core.push.vo.*;
import com.imlianai.zjdoll.app.modules.core.trade.record.TradeRecordService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.dao.UserDAO;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.zjdoll.domain.msg.*;
import com.imlianai.zjdoll.domain.pushcoin.*;
import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;


/**
 * @author wurui
 * @create 2018-05-23 9:45
 **/
@Service
public class PushCoinServiceImpl implements PushCoinService {

    private BaseLogger logger = BaseLogger.getLogger(getClass());

    @Resource
    private PushCoinOptRouterDao pushCoinOptRouterDao;

    @Resource
    private TradeService tradeService;

    @Resource
    private UserService userService;

    @Resource
    private DollBusService dollBusService;


    @Resource
    private DollInfoDao dollInfoDao;

    @Resource
    private DollInfoService dollInfoService;

    @Resource
    private MsgService msgService;

    @Resource
    private PushCoinBoxDao pushCoinBoxDao;

    @Resource
    private UserDAO userDAO;

    @Resource
    private PushCoinPointGiveDao pushCoinPointGiveDao;

    @Resource
    private UserDollService userDollService;

    @Resource
    private TradeRecordService tradeRecordService;


    @Override
    public BaseRespVO apply(long uid, int busId, int customerId) {
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null) {
            if (dollBus.getValid() != 1) {
                return new BaseRespVO(0, false, "当前机器下架");
            }
            if (dollBus.getType() != 1) {
                return new BaseRespVO(0, false, "上机失败:类型错误");
            }
            if (dollBus.getPrice() <= 0) {
                return new BaseRespVO(0, false, "上机失败:机器不存在");
            }
            UserBase userBase = userService.getUserBase(uid);
            if (userBase != null) {
                try {

                    DollInfo dollInfo = dollInfoService.getDollInfo(dollBus.getDollId());
                    if (dollInfo != null) {

                        TradeAccount tradeAccount = tradeService.getAccount(userBase.getUid());
                        if (tradeAccount != null) {

                            boolean isPlay = false;
                            if (dollBus.getPrizeType() == 0) {
                                logger.info("推币机上机,当前机器投币出钻石");
                                if (tradeAccount.getCoin() >= dollBus.getPrice()) {
                                    isPlay = true;
                                } else {
                                    return new BaseRespVO(20, false, "游戏币不足");
                                }
                            } else {
                                if (dollBus.getPrizeType() == 1) {
                                    logger.info("推币机上机,当前机器投钻石出币");
                                    if (tradeAccount.getJewel() >= dollBus.getPrice()) {
                                        isPlay = true;
                                    } else {
                                        return new BaseRespVO(20, false, "钻石数量不足");
                                    }
                                }
                            }

                            if (isPlay) {
                                logger.info("开始申请上机 busId=>" + dollBus.getBusId());

                                ApplyRespVO vo = ZengjingUtils.apply(uid, Integer.parseInt(dollBus.getDeviceId()), customerId);
                                logger.info(JSON.toJSONString(vo));

                                if (!StringUtil.isNullOrEmpty(vo) && vo.isState() && vo.getOptId() != 0) {
                                    MachinePushCoin machinePushCoin = new MachinePushCoin();
                                    machinePushCoin.setOptId(vo.getOptId());
                                    machinePushCoin.setUid(userBase.getUid());
                                    machinePushCoin.setBusId(dollBus.getBusId());
                                    machinePushCoin.setDeviceId(dollBus.getDeviceId());
                                    machinePushCoin.setCustomerId(customerId);
                                    machinePushCoin.setPrice(dollBus.getPrice());
                                    machinePushCoin.setTime(vo.getPlayTime());

                                    if (dollBus.getPrizeType() == 0) {
                                        //币/钻
                                        machinePushCoin.setRate(dollInfo.getReturnJewel());
                                        machinePushCoin.setPrizeType(0);
                                    } else {
                                        //钻得币
                                        if (dollBus.getPrizeType() == 1) {
                                            machinePushCoin.setRate(dollInfo.getCoin());
                                            machinePushCoin.setPrizeType(1);
                                        } else {
                                            return new BaseRespVO(25, false, "被抢先一步上机了~~~");
                                        }
                                    }

                                    //计算宝箱峰值或初始化宝箱
                                    this.calcBox(dollBus);

                                    if (pushCoinOptRouterDao.insert(machinePushCoin) > 0) {
                                        logger.info("新增操作流水成功");
                                        UserGeneral userGeneral = userService.getUserGeneral(uid);
                                        if (userGeneral != null) {
                                            Map<String, Object> data = Maps.newHashMap();
                                            data.put("uid", userGeneral.getUid());
                                            sendRoomMsg(dollBus, MsgRoomType.START_PLAY.type, data, userGeneral.getName() + " 成功上机");
                                        }
                                        return vo;
                                    }


                                }
                                return new BaseRespVO(vo.getResult(), vo.isState(), vo.getMsg());

                            } else {

                                return new BaseRespVO(26, false, "上机失败,请重试~~~");

                            }
                        } else {
                            tradeService.initAccount(userBase.getUid());
                            return new BaseRespVO(21, false, "上机失败:账户异常");
                        }


                    } else {
                        return new BaseRespVO(30, false, "上机失败:当前机器不能出奖");
                    }

                } catch (Exception e) {
                    logger.info("apply=>" + e.getMessage());
                    return new BaseRespVO(22, false, "上机失败:异常操作");
                }
            } else {
                return new BaseRespVO(23, false, "上机失败:用户不存在");
            }
        }
        return new BaseRespVO(24, false, "上机失败:机器不存在");
    }

    @Override
    public BaseRespVO push(long uid, int busId, int customerId, long optId) {
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null) {
            if (dollBus.getValid() != 1) {
                return new BaseRespVO(0, false, "当前机器已下架~");
            }
            if (dollBus.getType() != 1) {
                return new BaseRespVO(0, false, "当前机器类型错误~");
            }

            //计算宝箱峰值或初始化宝箱
            this.calcBox(dollBus);

            UserBase userBase = userService.getUserBase(uid);
            if (userBase != null) {
                try {
                    MachinePushCoin machinePushCoin = pushCoinOptRouterDao.getOne(optId, uid);
                    if (machinePushCoin != null) {
                        if (machinePushCoin.getState() != 1 && machinePushCoin.getUid() == uid && machinePushCoin.getBusId() == busId) {

                            logger.info("push  machinePushCoin=>" + JSON.toJSONString(machinePushCoin));

                            TradeAccount tradeAccount = tradeService.getAccount(userBase.getUid());
                            if (tradeAccount != null) {
                                logger.info("push  tradeAccount=>" + JSON.toJSONString(tradeAccount));

                                TradeRecord record = new TradeRecord();
                                boolean isPush = false;
                                if (dollBus.getPrizeType() == 0) {
                                    logger.info("推币机上机,当前机器投币出钻石");
                                    if (tradeAccount.getCoin() >= dollBus.getPrice()) {
                                        isPush = true;

                                        record = new TradeRecord(userBase.getUid(), 0, TradeType.PUSH_COIN.type, dollBus.getBusId(), dollBus.getPrice(), TradeCostType.COST_COIN.type, "推币机消费" + dollBus.getPrice() + "游戏币");


                                    } else {
                                        return new BaseRespVO(20, false, "游戏币不足");
                                    }
                                } else {
                                    if (dollBus.getPrizeType() == 1) {
                                        logger.info("推币机上机,当前机器投钻石出币");
                                        if (tradeAccount.getJewel() >= dollBus.getPrice()) {
                                            isPush = true;

                                            record = new TradeRecord(userBase.getUid(), 0, TradeType.PUSH_JEWEL.type, dollBus.getBusId(), dollBus.getPrice(), TradeCostType.COST_JEWEL.type, "推币机消费" + dollBus.getPrice() + "钻石");

                                        } else {
                                            return new BaseRespVO(20, false, "钻石数量不足");
                                        }
                                    }
                                }

                                record.setOptId(machinePushCoin.getOptId());

                                if (isPush) {
                                    //return iAppPushCoinRemoteService.putCoin(vo.getUid(),vo.getBusId(),vo.getCustomerId());
                                    //扣费
                                    if (tradeService.consume(record)) {
                                        OperateRespVO vo = ZengjingUtils.push(uid, Integer.parseInt(dollBus.getDeviceId()), customerId);
                                        logger.info("push  OperateRespVO=>" + JSON.toJSONString(vo));
                                        TradeAccount newTradeAccount = tradeService.getAccount(userBase.getUid());
                                        if (newTradeAccount != null) {
                                            vo.setData(newTradeAccount);
                                        }
                                        if (!StringUtil.isNullOrEmpty(vo) && vo.isState()) {
                                            if (pushCoinOptRouterDao.updateCoin(vo.getOptId(), 1, dollBus.getPrice(), 0) > 0) {

                                                if (pushCoinBoxDao.addBoxCoin(busId, dollBus.getPrice(), uid) > 0) {
                                                    logger.info("推币机=>" + dollBus.getBusId() + ",uid=>" + uid + ",coin=>" + dollBus.getPrice() + ",money=>" + dollBus.getPrice() / 10);

                                                    if (dollBus.getPrizeType() == 0) {
                                                        this.calcBonus(dollBus.getPrice());
                                                    } else {
                                                        logger.info("投钻石出币不计入奖池");
                                                    }

                                                }

                                                return vo;
                                            }
                                        }
                                        return new BaseRespVO(vo.getResult(), vo.isState(), null != vo.getMsg() ? vo.getMsg() : "数据请求失败");
                                    }
                                }
                                return new BaseRespVO(20, false, "余额不足");
                            } else {
                                return new BaseRespVO(21, false, "账户异常");
                            }
                        }
                    }

                } catch (Exception e) {
                    logger.info("apply=>" + e.getMessage());
                    return new BaseRespVO(22, false, "扣费失败");
                }
            } else {
                return new BaseRespVO(23, false, "用户不存在");
            }
        }
        return new BaseRespVO(24, false, "推币失败:机器不存在");
    }

    @Override
    public BaseRespVO operate(long uid, int busId) {
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null) {
            if (dollBus.getValid() != 1) {
                return new BaseRespVO(0, false, "当前机器下架");
            }
            if (dollBus.getType() != 1) {
                return new BaseRespVO(0, false, "摆动失败:类型错误");
            }
            //return iAppPushCoinRemoteService.operate(vo.getUid(),vo.getBusId());
            return ZengjingUtils.operate(uid, Integer.parseInt(dollBus.getDeviceId()));
        }
        return new BaseRespVO(0, false, "摆动失败:机器不存在");
    }

    @Override
    public BaseRespVO query(long optId, int customerId) {
        return ZengjingUtils.query(optId, customerId);
    }

    @Override
    public BaseRespVO finish(long uid, int busId, int customerId) {
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null) {
//            if (dollBus.getValid() != 1) {
//                return new BaseRespVO(0, false, "当前机器下架");
//            }
            if (dollBus.getType() != 1) {
                return new BaseRespVO(0, false, "下机失败:机器类型错误");
            }
            BaseRespVO vo = ZengjingUtils.finish(uid, Integer.parseInt(dollBus.getDeviceId()), customerId);
            if (vo.isState()) {
                Map<String, Object> data = Maps.newHashMap();
                data.put("uid", uid);
                sendRoomMsg(dollBus, MsgRoomType.PUSH_COIN_RELEASE.type, data, "用户下机");
                return vo;
            }
            //return iAppPushCoinRemoteService.finish(vo.getUid(),vo.getBusId(),vo.getCustomerId());
            return new BaseRespVO(0, false, vo.getMsg());
        }
        return new BaseRespVO(0, false, "下机失败:机器不存在");
    }

    @Override
    public BaseRespVO getStatus(int busId, int customerId) {
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null) {
            return ZengjingUtils.getStatus(Integer.parseInt(dollBus.getDeviceId()), customerId);
        }
        return new BaseRespVO(0, false, "获取状态失败:机器不存在");
    }

    @Override
    public void handleTimeOutRecord() {
        List<MachinePushCoin> machinePushCoinList = pushCoinOptRouterDao.getTimeOutRecord();
        if (!StringUtil.isNullOrEmpty(machinePushCoinList)) {
            logger.info("处理超时记录=>" + JSON.toJSONString(machinePushCoinList));
            try {
                for (MachinePushCoin machinePushCoin : machinePushCoinList) {
                    if (machinePushCoin != null && machinePushCoin.getState() != 1) {
                        int flag = pushCoinOptRouterDao.updateTimeOutStatus(machinePushCoin.getId());
                        if (flag > 0) {
                            logger.info("成功处理记录=>" + JSON.toJSONString(machinePushCoin));
                        }
                    }
                }
            } catch (Exception e) {
                logger.info("处理超时记录异常=>" + e.getMessage());
            }
        }
    }

    /**
     * 系统充值(充钻石)
     *
     * @param uid
     * @param optId
     * @param jewel
     * @return
     * @throws TradeOperationException
     */
    private int charge_jewel(long uid, long optId, int busId, int jewel, String remark) {
        logger.info("charge 执行系统充值");
        try {
            TradeRecord tradeRecord = new TradeRecord(uid, uid,
                    TradeType.PUSH_PRIZE_JEWEL.type, busId, jewel,
                    TradeCostType.COST_JEWEL.type, remark);
            tradeRecord.setOptId(optId);

            if (tradeService.charge(tradeRecord)) {
                logger.info("charge 成功充值=>" + jewel + " 个钻石");
                return 1;
            }
        } catch (TradeOperationException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 系统充值(充游戏币)
     *
     * @param uid
     * @param optId
     * @param coin
     * @return
     * @throws TradeOperationException
     */
    private int charge_coin(long uid, long optId, int busId, int coin, String remark) {
        logger.info("charge 执行系统充值");
        try {
            TradeRecord tradeRecord = new TradeRecord(uid, uid,
                    TradeType.PUSH_PRIZE_COIN.type, busId, coin,
                    TradeCostType.COST_COIN.type, remark);
            tradeRecord.setOptId(optId);

            if (tradeService.charge(tradeRecord)) {
                logger.info("charge 成功充值=>" + coin + " 个游戏币");
                return 1;
            }
        } catch (TradeOperationException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 系统额外的充值
     *
     * @param uid
     * @param optId
     * @param busId
     * @param type
     * @param costType
     * @param remark
     * @return
     */
    private int sysExCharge(long uid, long optId, int busId, int num, int type, int costType, String remark) {
        logger.info("sysExCharge 执行系统额外充值");
        try {
            TradeRecord tradeRecord = new TradeRecord(uid, uid, type, busId, num, costType, remark);
            tradeRecord.setOptId(optId);
            if (tradeService.charge(tradeRecord)) {
                return 1;
            }
        } catch (TradeOperationException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void sendRoomMsg(DollBus dollBus, int msgRoomType, Map<String, Object> data, String body) {
        MsgRoom msgRoom = new MsgRoom(dollBus, msgRoomType, body);
        if (!StringUtil.isNullOrEmpty(data)) {
            msgRoom.setData(JSON.toJSONString(data));
        }
        msgService.sendMsgRoom(msgRoom);
    }

    //全站弹幕
    private void sendAllRoomMsg(DollBus dollBus, int msgRoomType, Map<String, Object> data, String body, int type) {
        List<DollBus> list = dollBusService.getAllDollBus();
        if (!StringUtil.isNullOrEmpty(list)) {
            MsgRoom msgRoom = new MsgRoom(dollBus, msgRoomType, body);
            msgRoom.setData(JSON.toJSONString(data));
            for (DollBus bus : list) {
                msgRoom.setConversationId(bus.getConversationId());
                msgRoom.setBusId(bus.getBusId());
                if (type == 0) {
                    if (bus.getBusId() != dollBus.getBusId()) {
                        msgService.sendMsgRoom(msgRoom);
                    }
                } else {
                    msgService.sendMsgRoom(msgRoom);
                }
            }
        }
    }

    //全站弹幕
    private void sendAllRoomMsgWebP(DollBus dollBus, int msgRoomType, Map<String, Object> data, String body, int type) {
        List<DollBus> list = dollBusService.getAllDollBus();
        if (!StringUtil.isNullOrEmpty(list)) {
            MsgRoom msgRoom = new MsgRoom(dollBus, msgRoomType, body);
            msgRoom.setData(JSON.toJSONString(data));
            //msgRoomWebp.initWebPMsg("http://lianai-image-sys.qiniudn.com/e20180125/toastn.webp");
            msgRoom.initWebPMsg("http://lianai-image-sys.qiniudn.com/e20180125/xinnianpiaopin.webp");
            for (DollBus bus : list) {
                msgRoom.setConversationId(bus.getConversationId());
                msgRoom.setBusId(bus.getBusId());
                if (type == 0) {
                    if (bus.getBusId() != dollBus.getBusId()) {
                        msgService.sendMsgRoom(msgRoom);
                    }
                } else {
                    msgService.sendMsgRoom(msgRoom);
                }
            }
        }
    }


    @Override
    public int CoinToJewel(long uid, int busId, long optId, int outCoin, int isFinal) {

        logger.info("CoinToJewel 收到" + outCoin + "币");
        UserBase userBase = userService.getUserBase(uid);
        if (userBase != null) {

            MachinePushCoin machinePushCoin = pushCoinOptRouterDao.getOne(optId, uid);
            if (machinePushCoin != null) {

                if (machinePushCoin.getIntoCoin() == 0) {
                    logger.info("用户未投币却收到币,此记录不算");
                    return 0;
                }

                try {

                    final DollBus dollBus = dollBusService.getDollBus(machinePushCoin.getBusId());

                    logger.info("CoinToJewel dollBus=>" + JSON.toJSONString(dollBus));

                    if (dollBus != null && dollBus.getDeviceId().equals(busId + "")) {

                        logger.info("CoinToJewel 同一台机器");
                        DollInfo dollInfo = dollInfoService.getDollInfo(dollBus.getDollId());
                        if (dollInfo != null) {

                            //消息体
                            final Map<String, Object> map = Maps.newHashMap();
                            final Map<String, Object> allMsgMap = Maps.newHashMap();
                            map.put("isFinal", isFinal);
                            map.put("uid", uid);

                            allMsgMap.put("uid", uid);

                            final UserGeneral userGeneral = userService.getUserGeneral(uid);
                            if (userGeneral != null) {
                                map.put("name", userGeneral.getName());
                                map.put("head", userGeneral.getHead());

                                allMsgMap.put("name", userGeneral.getName());
                                allMsgMap.put("head", userGeneral.getHead());


                                //实时更新
                                if (isFinal == 0) {
                                    logger.info("CoinToJewel 实时更新");
                                    if (outCoin == 0) return 0;

                                    if (outCoin > 20) {
                                        logger.info("CoinToJewel 实际出币>20");
                                        outCoin = 20;
                                    }


                                    int jewel = 0;
                                    int coin = 0;


                                    if (dollBus.getPrizeType() == 0) {

                                        logger.info("推币出钻石=>" + outCoin);

                                        jewel = dollInfo.getReturnJewel() * outCoin;

                                        PushCoinBoxPool outCoinPool = new PushCoinBoxPool();
                                        outCoinPool.setId(PushCoinConsts.PUSH_COIN_BOX_POOL_ID);
                                        outCoinPool.setCurrent(-outCoin * dollBus.getPrice());
                                        outCoinPool.seteOutCoin(outCoin);
                                        outCoinPool.seteTotalOutCoin(outCoin);
                                        outCoinPool.setvOutCoin(outCoin * dollBus.getPrice());
                                        outCoinPool.setvTotalOutCoin(outCoin * dollBus.getPrice());

                                        if (pushCoinBoxDao.updateBoxPoolOutValue(outCoinPool) > 0) {
                                            logger.info("推币机出币，奖池 -" + outCoin * dollBus.getPrice());
                                        }

                                        if (machinePushCoin.getState() != 1 && machinePushCoin.getJewelStatus() != 1) {
                                            if (pushCoinOptRouterDao.updateCoin(optId, outCoin, outCoin * dollBus.getPrice(), 1) > 0) {
                                                logger.info("CoinToJewel 实际出币=>" + outCoin + ",兑换成钻石比例 1:" + dollInfo.getReturnJewel() + ",实际兑换钻石数=>" + jewel);
                                                if (pushCoinOptRouterDao.updateJewel(jewel, machinePushCoin.getOptId()) > 0) {
                                                    if (this.charge_jewel(userBase.getUid(), machinePushCoin.getOptId(), dollBus.getBusId(), jewel, "推币得" + jewel + "钻") == 1) {
                                                        map.put("jewel", jewel);
                                                        map.put("time", 30);
                                                        TradeAccount tradeAccount = tradeService.getAccount(userBase.getUid());
                                                        if (tradeAccount != null) {
                                                            map.put("u_coin", tradeAccount.getCoin());
                                                            map.put("u_jewel", tradeAccount.getJewel());
                                                        }

                                                        allMsgMap.put("jewel", jewel);


                                                        final int finalJewel = jewel;
                                                        SysTimerHandle.execute(new TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    sendRoomMsg(dollBus, MsgRoomType.PUSH_COIN_JEWEL.type, map, "恭喜玩家 <span style='color: #eaec04'>" + userGeneral.getName() + " </span>获得 <span style='color: #4d16ec'>" + finalJewel + " </span>钻石");

                                                                    sendAllRoomMsg(dollBus, MsgRoomType.PUSH_COIN_ROOM_ALL_MSG.type, allMsgMap,
                                                                            "<font color=\"#FFF203\">恭喜玩家 " + userGeneral.getName() + " 在 " + dollBus.getBusId() + " 号机推币获得 <font color=\"#0b09ff\">" + finalJewel + "</font> 钻石~~~</font>", 0);

                                                                } catch (Exception e) {
                                                                    PrintException.printException(logger, e);
                                                                }
                                                            }
                                                        }, 0);

                                                        return 1;
                                                    }
                                                }
                                            }
                                        }

                                    } else {


                                        if (dollBus.getPrizeType() == 1) {

                                            logger.info("推钻石出币=>" + outCoin);

                                            coin = dollInfo.getCoin() * outCoin;

                                            if (machinePushCoin.getState() != 1 && machinePushCoin.getJewelStatus() != 1) {
                                                if (pushCoinOptRouterDao.updateCoin(optId, outCoin, outCoin * dollBus.getPrice(), 1) > 0) {
                                                    logger.info("CoinToJewel 实际出币=>" + outCoin + ",兑换成游戏币比例 1:" + dollInfo.getCoin() + ",实际兑换游戏币=>" + coin);
                                                    if (pushCoinOptRouterDao.updateCoin(coin, machinePushCoin.getOptId()) > 0) {
                                                        if (this.charge_coin(userBase.getUid(), machinePushCoin.getOptId(), dollBus.getBusId(), coin, "推钻石得" + coin + "游戏币") == 1) {
                                                            map.put("jewel", coin);
                                                            map.put("time", 30);
                                                            TradeAccount tradeAccount = tradeService.getAccount(userBase.getUid());
                                                            if (tradeAccount != null) {
                                                                map.put("u_coin", tradeAccount.getCoin());
                                                                map.put("u_jewel", tradeAccount.getJewel());
                                                            }

                                                            allMsgMap.put("jewel", coin);

                                                            final int finalCoin = coin;
                                                            SysTimerHandle.execute(new TimerTask() {
                                                                @Override
                                                                public void run() {
                                                                    try {
                                                                        sendRoomMsg(dollBus, MsgRoomType.PUSH_COIN_JEWEL.type, map, "恭喜玩家 <span style='color: #eaec04'>" + userGeneral.getName() + " </span>获得 <span style='color: #4d16ec'>" + finalCoin + " </span>游戏币");


                                                                        sendAllRoomMsg(dollBus, MsgRoomType.PUSH_COIN_ROOM_ALL_MSG.type, allMsgMap,
                                                                                "<font color=\"#FFF203\">恭喜玩家 " + userGeneral.getName() + " 在 " + dollBus.getBusId() + " 号机推币获得 <font color=\"#0b09ff\">" + finalCoin + "</font> 游戏币~~~</font>", 0);

                                                                    } catch (Exception e) {
                                                                        PrintException.printException(logger, e);
                                                                    }
                                                                }
                                                            }, 0);


                                                            return 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                //最终状态
                                if (isFinal == 1) {
                                    logger.info("CoinToJewel 最终状态");
                                    //更新状态
                                    if (pushCoinOptRouterDao.updateState(optId) > 0 && pushCoinOptRouterDao.updateJewelStatus(optId) > 0) {
                                        logger.info("CoinToJewel 更新操作状态成功");
                                        map.put("jewel", 0);
                                        map.put("time", 0);
                                        TradeAccount tradeAccount = tradeService.getAccount(userBase.getUid());
                                        if (tradeAccount != null) {
                                            map.put("u_coin", tradeAccount.getCoin());
                                            map.put("u_jewel", tradeAccount.getJewel());
                                        }
                                        sendRoomMsg(dollBus, MsgRoomType.PUSH_COIN_RELEASE.type, map, "操作到期，可以重新上机");
                                        return 1;
                                    }
                                }

                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    @Override
    public BaseRespVO getBoxValue(int busId) {
        BoxValueRespVO vo = new BoxValueRespVO();
        PushCoinBox pushCoinBox = pushCoinBoxDao.getBox(busId);
        if (pushCoinBox != null) {
            vo.setTotal(pushCoinBox.getTotal());
            vo.setValue(pushCoinBox.getCurrent());
            if (vo.getValue() >= vo.getTotal()) {
                vo.setIsOpen(1);
            }
        }
        vo.setMsg("数据请求成功");
        vo.setState(true);
        vo.setResult(100);
        return vo;
    }

    @Override
    public BaseRespVO openBox(int busId, long uid) {

        //推币机是否存在
        final DollBus dollBus = dollBusService.getDollBus(busId);
        logger.info("dollBus=>" + JSON.toJSONString(dollBus));
        if (dollBus != null) {// && dollBus.getBusType() == 1 && dollBus.getValid() == 1

            if (dollBus.getType() == 1) {


                long optId = 0;
                boolean isCanOpen = false;

                try {

                    //实体机器
                    if (dollBus.getVirtual() == 0) {
                        BaseRespVO respVO = ZengjingUtils.getStatus(Integer.parseInt(dollBus.getDeviceId()), 88);
                        if (!StringUtil.isNullOrEmpty(respVO)) {
                            if (respVO.isState() && respVO.getResult() == 100 && null != respVO.getData()) {
                                JSONObject object = JSONObject.parseObject(respVO.getData().toString());
                                long bus_uid = object.getLong("uid");

                                if (bus_uid != 0 && uid == bus_uid) {

                                    optId = object.getLong("optId");
                                    isCanOpen = true;
                                    logger.info("实体推币机,用户正在上机状态,optId=>" + optId);

                                } else {
                                    return new BaseRespVO(0, false, "上机之后才能开宝箱哦~");
                                }
                            }
                        }
                    } else {
                        //虚拟机器
                        BaseRespVO respVO = ZengjingVirtualUtils.getStatus(uid, Integer.parseInt(dollBus.getDeviceId()));
                        if (!StringUtil.isNullOrEmpty(respVO)) {
                            if (respVO.isState() && respVO.getResult() == 200) {
                                String s_optId = respVO.getData().toString();
                                optId = Long.parseLong(s_optId);
                                isCanOpen = true;
                                logger.info("虚拟推币机,用户正在上机状态,optId=>" + optId);
                            } else {
                                return new BaseRespVO(0, false, "上机之后才能开宝箱哦~");
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.info("开箱异常 e=>" + e.getMessage());
                }

                if (isCanOpen) {

                    final long final_optId = optId;

                    final UserBase userBase = userService.getUserBase(uid);
                    if (userBase != null) {

                        //箱子是否能开
                        final PushCoinBox pushCoinBox = pushCoinBoxDao.getBox(busId);
                        if (pushCoinBox != null) {

                            logger.info("机器=>" + busId + ",当前投币=>" + pushCoinBox.getCurrent() + ",达到=>" + pushCoinBox.getTotal() + "可开宝箱");

                            //可开
                            if (pushCoinBox.getCurrent() >= pushCoinBox.getTotal()) {

                                try {
                                    //判断是否开过
                                    if (pushCoinBoxDao.getRecordCount(dollBus.getBusId(), pushCoinBox.getBoxId(), userBase.getUid()) > 0) {
                                        logger.info("用户=>" + uid + " 已开过宝箱,结束。");
                                        return new BaseRespVO(0, false, "你已经开过一次箱子啦，不能太贪心哦~");
                                    }

                                    Random random = new Random();

                                    List<TradeRecord> tradeRecords = tradeRecordService.getRecords(userBase.getUid(), TradeType.CHARGE.type, 1);

                                    final Map<String, Object> allMsgMap = Maps.newHashMap();

                                    final Map<String, Object> map = Maps.newHashMap();

                                    map.put("uid", uid);

                                    allMsgMap.put("uid", uid);

                                    final UserGeneral userGeneral = userService.getUserGeneral(uid);


                                    if (userGeneral != null) {
                                        map.put("name", userGeneral.getName());
                                        map.put("head", userGeneral.getHead());

                                        allMsgMap.put("name", userGeneral.getName());
                                        allMsgMap.put("head", userGeneral.getHead());


                                        boolean isOpen = false;

                                        PushCoinBoxLog pushCoinBoxLog = new PushCoinBoxLog();
                                        pushCoinBoxLog.setBusId(pushCoinBox.getBusId());
                                        pushCoinBoxLog.setUid(userBase.getUid());
                                        pushCoinBoxLog.setLastId(pushCoinBox.getLastId());
                                        pushCoinBoxLog.setBoxId(pushCoinBox.getBoxId());

                                        int jewel = 0;

                                        if (!StringUtil.isNullOrEmpty(tradeRecords)) {
                                            if (tradeRecords.size() > 0) {
                                                logger.info("当前用户充值过 uid=>" + userBase.getUid());

                                                logger.info("开始执行清空奖池=>" + busId + ",uid=>" + uid);
                                                int flag = pushCoinBoxDao.openBox(busId);
                                                if (flag > 0) {

                                                    isOpen = true;

                                                    logger.info("清空奖池成功=>" + busId + ",uid=>" + uid + ",奖金=>" + pushCoinBox.getJackpot());
                                                    int rate = 3; // 奖池单位 ：游戏币-游戏币比例1:3

                                                    logger.info("比例=>" + rate);

                                                    pushCoinBoxLog.setRate(rate);

                                                    if (pushCoinBox.getJackpot() > 0) {

                                                        //  金额换算成钻石

                                                        jewel = pushCoinBox.getJackpot() * rate; //奖金(单位游戏币) * 钻石比例 1:3

                                                        logger.info("金额换算成钻石=>" + jewel);

                                                        //pushCoinBoxLog.setNum(jewel);
                                                        //pushCoinBoxLog.setType(4);//钻石

                                                        map.put("type", 4);
                                                        map.put("value", jewel);

                                                        pushCoinBoxLog.setRemark("奖池奖金有" + pushCoinBox.getJackpot() + "游戏币,一共送" + jewel + "钻石");

                                                    } else {
                                                        logger.info("奖池金额为0,执行赠送其他操作");

                                                        jewel = random.nextInt(21) + 100;
                                                        //pushCoinBoxLog.setNum(jewel);
                                                        //pushCoinBoxLog.setType(4);//钻石

                                                        logger.info("随机钻石数=>" + jewel);

                                                        map.put("type", 4);
                                                        map.put("value", jewel);

                                                        pushCoinBoxLog.setRemark("奖池没有奖金,随机送" + jewel + "钻石");
                                                        //isOpen  = false;
                                                    }

                                                } else {
                                                    logger.info("清空奖池失败");
                                                }
                                            } else {
                                                isOpen = false;
                                                logger.info("当前用户未充值过 uid=>" + userBase.getUid());
                                            }
                                        } else {
                                            isOpen = false;
                                            logger.info("当前用户未充值过 uid=>" + userBase.getUid());
                                        }

                                        if (!isOpen) {

                                            jewel = random.nextInt(46) + 5;
                                            map.put("type", 4);
                                            map.put("value", jewel);
                                            pushCoinBoxLog.setRemark("未充值用户开箱,随机送" + jewel + "钻石");

                                        }

                                        pushCoinBoxLog.setNum(jewel);
                                        pushCoinBoxLog.setType(4);//钻石

                                        pushCoinBoxLog.setVirtual(dollBus.getVirtual());

                                        final int openBoxLogId = pushCoinBoxDao.addOpenBoxRecord(pushCoinBoxLog);
                                        if (openBoxLogId > 0) {
                                            logger.info("新增开箱记录");
                                            if (this.charge_jewel(userBase.getUid(), final_optId, dollBus.getBusId(), jewel, "开宝箱得" + jewel + "钻") == 1) {
                                                logger.info("成功充值=>" + jewel + "钻");

                                                allMsgMap.put("jewel", jewel);

                                                final int finalJewel = jewel;
                                                SysTimerHandle.execute(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        try {

                                                            //处理额外赠送
                                                            String msg = handlePointGive(openBoxLogId, userBase, userGeneral, dollBus, final_optId, pushCoinBox.getBoxId());

                                                            Thread.sleep(200);

                                                            if (msg != null && !msg.equals("") && msg.length() > 5) {

                                                                map.put("type", 5);
                                                                map.put("body", "开启宝箱获得 " + finalJewel + " 个钻石\n" + msg);
                                                            }


                                                            sendRoomMsg(dollBus, MsgRoomType.PUSH_COIN_BOX.type, map, "恭喜玩家 <span style='color: #eaec04'>" + userGeneral.getName() + "</span> 开启宝箱获得 <span style='color: #4d16ec'>" + finalJewel + "</span> 个钻石");

                                                            MsgRoom msgRoomWebpOne = new MsgRoom(dollBus, userGeneral, MsgRoomType.BUS_WEBP.type, "恭喜玩家 <span style='color: #eaec04'>" + userGeneral.getName() + "</span> 开启宝箱获得 <span style='color: #4d16ec'>" + finalJewel + "</span> 个钻石" + msg);
                                                            msgRoomWebpOne.initWebPMsg("http://lianai-image-sys.qiniudn.com/e20180125/xinnianpiaopin.webp");
                                                            msgService.sendMsgRoom(msgRoomWebpOne);

                                                            //MsgRoom msgRoomWebp = new MsgRoom(dollBus, userGeneral, MsgRoomType.BUS_WEBP.type, "<font color=\"#FFF203\">恭喜玩家 " + userGeneral.getName() + " 在 " + dollBus.getBusId() + " 号机开启宝箱获得 <font color=\"#0b09ff\">" + finalJewel + "</font> 钻石~~~</font>");
                                                            //msgRoomWebp.initWebPMsg("http://lianai-image-sys.qiniudn.com/e20180125/toastn.webp");
                                                            //msgRoomWebp.initWebPMsg("http://lianai-image-sys.qiniudn.com/e20180125/xinnianpiaopin.webp");
                                                            //msgService.sendMsgRoomAll(msgRoomWebp);

                                                            sendAllRoomMsgWebP(dollBus, MsgRoomType.BUS_WEBP.type, allMsgMap,
                                                                    "<font color='#FFF203'>恭喜玩家 " + userGeneral.getName() + " 在 " + dollBus.getBusId() + " 号机开启宝箱获得 <font color='#0b09ff'>" + finalJewel + "</font> 钻石~~~</font>" + msg, 0);

                                                        } catch (Exception e) {
                                                            PrintException.printException(logger, e);
                                                        }
                                                    }
                                                }, 0);


                                                return new BaseRespVO(100, true, "开箱成功,共获得:" + jewel + "钻石");

                                            } else {

                                                logger.info("充值操作失败");
                                            }

                                        } else {
                                            logger.info("新增开箱记录失败");
                                        }
                                    } else {
                                        logger.info("用户详细信息不存在");
                                    }

                                } catch (Exception e) {
                                    logger.info("开箱异常=>" + e.getMessage());
                                }
                            }
                        } else {
                            logger.info("宝箱未初始化,执行初始化操作,当前开箱失败!");
                            this.calcBox(dollBus);
                        }

                        return new BaseRespVO(0, false, "当前宝箱还不能开哦~");

                    } else {
                        logger.info("用户信息不存在");
                    }
                } else {
                    logger.info("获取状态失败");
                }
            } else {
                logger.info("机器类型错误");
            }
        } else {
            logger.info("当前机器不存在");
        }
        return new BaseRespVO(0, false, "开箱失败~");
    }

    @Override
    public PushCoinBox getPushCoinBox(int busId) {
        return pushCoinBoxDao.getBox(busId);
    }

    @Override
    public PushCoinBoxPool getPushCoinBoxPool() {
        return pushCoinBoxDao.getBoxPool(PushCoinConsts.PUSH_COIN_BOX_POOL_ID);
    }

    @Override
    public BaseRespVO getOperatorRecord(int busId, long optId, long uid) {
        try {
            DollBus dollBus = dollBusService.getDollBus(busId);
            Map<String, Object> resultMap = Maps.newHashMap();
            if (dollBus != null && dollBus.getType() == 1 && dollBus.getValid() == 1) {
                MachinePushCoin machinePushCoin = pushCoinOptRouterDao.getOne(optId);
                if (machinePushCoin != null) {
                    if (machinePushCoin.getUid() == uid) {
                        resultMap.put("opt", UserPushCoinRecordDTO.adapter(machinePushCoin,
                                userDAO.getUserGeneral(machinePushCoin.getUid()), //userService 的 getUserGeneral有缓存
                                tradeService.getAccount(machinePushCoin.getUid()), dollBus));
                    }
                }
            }
            PushCoinBoxPool pushCoinBoxPool = pushCoinBoxDao.getBoxPool(PushCoinConsts.PUSH_COIN_BOX_POOL_ID);
            if (pushCoinBoxPool != null) {
                resultMap.put("pool", pushCoinBoxPool);
            }
            return new BaseRespVO(resultMap);
        } catch (Exception e) {
            return new BaseRespVO(0, false, "系统异常,请稍后重试~");
        }
    }

    @Override
    public BaseRespVO getOperatorRecordList(int busId, long optId, long uid) {
        List<UserPushCoinRecordDTO> userPushCoinRecordDTOList = Lists.newArrayList();
        Map<String, Object> resultMap = Maps.newHashMap();
        try {
            //当前上机用户
            if (optId > 0) {
                DollBus dollBus = dollBusService.getDollBus(busId);
                if (dollBus != null && dollBus.getType() == 1 && dollBus.getValid() == 1) {
                    MachinePushCoin machinePushCoin = pushCoinOptRouterDao.getOne(optId);
                    if (machinePushCoin != null) {
                        if (machinePushCoin.getUid() == uid) {
                            resultMap.put("opt", UserPushCoinRecordDTO.adapter(machinePushCoin,
                                    userDAO.getUserGeneral(machinePushCoin.getUid()), //userService 的 getUserGeneral有缓存
                                    tradeService.getAccount(machinePushCoin.getUid()), dollBus));

                        }
                    }
                }
            }

            //上机列表
            List<MachinePushCoin> machinePushCoinList = pushCoinOptRouterDao.getUserPlayRecordList();
            if (!StringUtil.isNullOrEmpty(machinePushCoinList)) {
                for (MachinePushCoin machinePushCoin : machinePushCoinList) {
                    DollBus dollBus = dollBusService.getDollBus(machinePushCoin.getBusId());
                    if (dollBus != null && dollBus.getType() == 1 && dollBus.getValid() == 1) {
                        userPushCoinRecordDTOList.add(UserPushCoinRecordDTO.adapter(machinePushCoin,
                                userDAO.getUserGeneral(machinePushCoin.getUid()), //userService 的 getUserGeneral有缓存
                                tradeService.getAccount(machinePushCoin.getUid()), dollBus));
                    }
                }
            }
            resultMap.put("optList", userPushCoinRecordDTOList);

            PushCoinBoxPool pushCoinBoxPool = pushCoinBoxDao.getBoxPool(PushCoinConsts.PUSH_COIN_BOX_POOL_ID);
            if (pushCoinBoxPool != null) {
                resultMap.put("pool", pushCoinBoxPool);
            }
            return new BaseRespVO(resultMap);
        } catch (Exception e) {
            return new BaseRespVO(0, false, "系统异常,请稍后重试~");
        }
    }

    @Override
    public BaseRespVO addPointGiveBoxRecord(int busId, long uid, int exJewel, int exCoin, int exScore, int exCoupon, List<Integer> exDoll) {
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null) {

            UserBase userBase = userService.getUserBase(uid);
            if (userBase != null) {

                PushCoinBoxPointGive pointGive = new PushCoinBoxPointGive();


                //必须字段
                pointGive.setBusId(busId);
                pointGive.setUid(uid);

                //待办字段
                pointGive.setOptId(0);
                pointGive.setStatus(0);
                pointGive.setBoxId(0);

                pointGive.setExCoin(0);
                pointGive.setExJewel(0);
                pointGive.setExScore(0);
                pointGive.setExCoupon(0);


                //额外的钻石
                if (exJewel != 0 && exJewel > 0) {
                    pointGive.setExJewel(exJewel);
                }

                //额外的游戏币
                if (exCoin != 0 && exCoin > 0) {
                    pointGive.setExCoin(exCoin);
                }

                //额外的积分
                if (exScore != 0 && exScore > 0) {
                    pointGive.setExScore(exScore);
                }

                //额外的时光卷
                if (exCoupon != 0 && exCoupon > 0) {
                    pointGive.setExCoupon(exCoupon);
                }


                //额外的娃娃
                if (!StringUtil.isNullOrEmpty(exDoll)) {
                    pointGive.setExDoll(JSON.toJSONString(exDoll));
                } else {
                    pointGive.setExDoll("[]");
                }

                int flag = pushCoinPointGiveDao.addPointGiveRecord(pointGive);
                if (flag > 0) {
                    return new BaseRespVO(200, true, "新增点送记录成功");
                } else {
                    return new BaseRespVO(0, false, "新增点送记录失败");
                }
            } else {
                return new BaseRespVO(0, false, "新增点送记录失败,用户不存在");
            }
        }
        return new BaseRespVO(0, false, "新增点送记录失败,机器不存在或已经下架");
    }

    @Override
    public BaseRespVO getPointGiveBoxRecordList(long uid) {
        BoxPointGiveRespVO vo = new BoxPointGiveRespVO();
        Map<String, String> dollInfoMap = Maps.newHashMap();
        List<DollInfo> dollInfoList = dollInfoDao.getAllDollInfos();
        if (!StringUtil.isNullOrEmpty(dollInfoList)) {
            for (DollInfo dollInfo : dollInfoList) {
                dollInfoMap.put(dollInfo.getDollId() + "", dollInfo.getName());
            }
        }
        vo.setDollInfoMaps(dollInfoMap);

        List<PushCoinBoxPointGive> pointGiveList;
        if (uid != 0) {
            pointGiveList = pushCoinPointGiveDao.getPointGiveListByUid(uid);
        } else {
            pointGiveList = pushCoinPointGiveDao.getPointGiveList();

        }
        List<UserPointGiveRecordDTO> giveRecordDTOList = Lists.newArrayList();
        if (!StringUtil.isNullOrEmpty(pointGiveList)) {
            for (PushCoinBoxPointGive pointGive : pointGiveList) {
                DollBus dollBus = dollBusService.getDollBus(pointGive.getBusId());
                if (dollBus != null) {
                    Map<String, String> dollMap = Maps.newHashMap();
                    UserGeneral userGeneral = userService.getUserGeneral(pointGive.getUid());
                    UserPointGiveRecordDTO recordDTO = UserPointGiveRecordDTO.adapter(userGeneral, dollBus, pointGive);
                    if (!StringUtil.isNullOrEmpty(pointGive.getExDoll())) {
                        List<Integer> dollIds = JSON.parseArray(pointGive.getExDoll(), Integer.class);
                        if (!StringUtil.isNullOrEmpty(dollIds)) {
                            for (int dollId : dollIds) {
                                DollInfo dollInfo = dollInfoService.getDollInfo(dollId);
                                if (dollInfo != null) {
                                    dollMap.put(dollInfo.getDollId() + "", dollInfo.getName());
                                }
                            }
                        }
                    }
                    recordDTO.setDollMap(dollMap);
                    giveRecordDTOList.add(recordDTO);
                }
            }
        }
        vo.setList(giveRecordDTOList);

        vo.setState(true);
        vo.setResult(200);
        vo.setMsg("数据请求成功");
        return vo;
    }

    @Override
    public BaseRespVO deletePointGiveBoxRecordById(int id) {

        PushCoinBoxPointGive pointGive = pushCoinPointGiveDao.getPointGiveRecordById(id);
        if (pointGive != null && pointGive.getStatus() != 1) {
            int flag = pushCoinPointGiveDao.deletePointRecordById(id);
            if (flag > 0) {
                return new BaseRespVO(200, true, "删除记录成功");
            }
        }
        return new BaseRespVO(0, false, "删除记录失败");
    }

    @Override
    public String handlePointGive(int openBoxLogId, UserBase userBase, UserGeneral userGeneral, DollBus dollBus, long optId, int boxId) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        logger.info("=====handlePointGive=====");
        if (dollBus != null) {

            if (userBase != null && userGeneral != null) {

                PushCoinBoxPointGive pointGive = pushCoinPointGiveDao.getPointGiveRecordByUid(userBase.getUid());
                if (pointGive != null) {

                    if (pointGive.getBusId() == dollBus.getBusId()) {

                        //处理宝箱点送
                        logger.info("点送宝箱匹配成功,开始执行赠送===");

                        try {

                            int giveId = pushCoinPointGiveDao.updatePointGiveRecord(pointGive.getId(), boxId, optId, 1);
                            logger.info("更新赠送id=>" + giveId);

                            if (giveId > 0) {

                                if (pushCoinBoxDao.updateOpenBoxRecord(openBoxLogId, giveId, JSON.toJSONString(pointGive), "赠送额外奖励：" + DateUtils.getCurrentDateString("yyyy-MM-dd HH:mm:ss")) > 0) {
                                    logger.info("更新开箱记录=>" + openBoxLogId);

                                    //数据
                                    Map<String, Object> dataMaps = Maps.newConcurrentMap();
                                    dataMaps.put("uid", userBase.getUid());
                                    dataMaps.put("name", userGeneral.getName());
                                    dataMaps.put("head", userGeneral.getHead());

                                    //赠送额外的游戏币 类型1
                                    if (pointGive.getExCoin() != 0) {
                                        int num = pointGive.getExCoin();
                                        logger.info("赠送：" + num + " 游戏币");

                                        if (sysExCharge(userBase.getUid(), optId, dollBus.getBusId(), pointGive.getExCoin(), TradeType.PUSH_BOX_POINT_GIVE.type, TradeCostType.COST_COIN.type, "开启宝箱额外赠送" + num + "币") > 0) {

                                            logger.info("赠送：" + num + " 游戏币执行成功,开始发送消息");


                                            dataMaps.put("type", 1);//游戏币类型
                                            dataMaps.put("value", num);

                                            String commonMsg = "开启宝箱额外赠送" + num + "游戏币";
                                            String roomMsg = "恭喜玩家 <span style='color: #eaec04'>" + userGeneral.getName() + " </span>开启宝箱额外获得 <span style='color: #4d16ec'>" + num + " </span>游戏币";
                                            String allRoomMsg = "<font color=\"#FFF203\">恭喜玩家 " + userGeneral.getName() + " 在 " + dollBus.getBusId() + "号机 开启宝箱额外获得 <font color=\"#0b09ff\">" + num + "</font> 游戏币~~~</font>";

                                            sb.append("额外赠送").append(num).append("游戏币\n");

                                            this.handlePointMsg(dataMaps, userBase, dollBus, commonMsg, roomMsg, allRoomMsg);
                                        }

                                    }

                                    //赠送额外的钻石 类型4
                                    if (pointGive.getExJewel() != 0) {
                                        int num = pointGive.getExJewel();
                                        logger.info("赠送：" + num + " 个钻石");

                                        if (sysExCharge(userBase.getUid(), optId, dollBus.getBusId(), pointGive.getExCoin(), TradeType.PUSH_BOX_POINT_GIVE.type, TradeCostType.COST_JEWEL.type, "开启宝箱额外赠送" + num + "钻石") > 0) {

                                            logger.info("赠送：" + num + " 钻石执行成功,开始发送消息");

                                            dataMaps.put("type", 4);//钻石类型
                                            dataMaps.put("value", num);

                                            sb.append("额外赠送").append(num).append("个钻石\n");

                                            String commonMsg = "开启宝箱额外赠送" + num + "个钻石";
                                            String roomMsg = "恭喜玩家 <span style='color: #eaec04'>" + userGeneral.getName() + " </span>开启宝箱额外获得 <span style='color: #4d16ec'>" + num + " </span>个钻石";
                                            String allRoomMsg = "<font color=\"#FFF203\">恭喜玩家 " + userGeneral.getName() + " 在 " + dollBus.getBusId() + "号机 开启宝箱额外获得 <font color=\"#0b09ff\">" + num + "</font> 个钻石~~~</font>";

                                            this.handlePointMsg(dataMaps, userBase, dollBus, commonMsg, roomMsg, allRoomMsg);
                                        }
                                    }

                                    //赠送额外的积分 类型3
                                    if (pointGive.getExScore() != 0) {

                                        int num = pointGive.getExScore();
                                        logger.info("赠送：" + num + " 积分");

                                        if (sysExCharge(userBase.getUid(), optId, dollBus.getBusId(), pointGive.getExCoin(), TradeType.PUSH_BOX_POINT_GIVE.type, TradeCostType.COST_SCORE.type, "开启宝箱额外赠送" + num + "积分") > 0) {

                                            logger.info("赠送：" + num + " 积分执行成功,开始发送消息");

                                            dataMaps.put("type", 3);//积分类型
                                            dataMaps.put("value", num);

                                            sb.append("额外赠送").append(num).append("积分\n");

                                            String commonMsg = "开启宝箱额外赠送" + num + "积分";
                                            String roomMsg = "恭喜玩家 <span style='color: #eaec04'>" + userGeneral.getName() + " </span>开启宝箱额外获得 <span style='color: #4d16ec'>" + num + " </span>积分";
                                            String allRoomMsg = "<font color=\"#FFF203\">恭喜玩家 " + userGeneral.getName() + " 在 " + dollBus.getBusId() + "号机 开启宝箱额外获得 <font color=\"#0b09ff\">" + num + "</font> 积分~~~</font>";

                                            this.handlePointMsg(dataMaps, userBase, dollBus, commonMsg, roomMsg, allRoomMsg);
                                        }

                                    }

                                    //赠送额外的时光卷 类型2
                                    if (pointGive.getExCoupon() != 0) {
                                        int num = pointGive.getExScore();
                                        logger.info("赠送：" + num + " 张时光卷");

                                        if (sysExCharge(userBase.getUid(), optId, dollBus.getBusId(), pointGive.getExCoin(), TradeType.PUSH_BOX_POINT_GIVE.type, TradeCostType.TIMECOUPON.type, "开启宝箱额外赠送" + num + "张时光卷") > 0) {

                                            logger.info("赠送：" + num + " 张时光卷执行成功,开始发送消息");

                                            dataMaps.put("type", 3);//积分类型
                                            dataMaps.put("value", num);

                                            sb.append("额外赠送").append(num).append("张时光卷\n");

                                            String commonMsg = "开启宝箱额外赠送" + num + "张时光卷";
                                            String roomMsg = "恭喜玩家 <span style='color: #eaec04'>" + userGeneral.getName() + " </span>开启宝箱额外获得 <span style='color: #4d16ec'>" + num + " </span>张时光卷";
                                            String allRoomMsg = "<font color=\"#FFF203\">恭喜玩家 " + userGeneral.getName() + " 在 " + dollBus.getBusId() + "号机 开启宝箱额外获得 <font color=\"#0b09ff\">" + num + "</font> 张时光卷~~~</font>";

                                            this.handlePointMsg(dataMaps, userBase, dollBus, commonMsg, roomMsg, allRoomMsg);
                                        }

                                    }

                                    //赠送额外的娃娃 类型5
                                    if (!StringUtil.isNullOrEmpty(pointGive.getExDoll())) {
                                        logger.info("赠送娃娃：" + pointGive.getExDoll());
                                        List<Integer> dollList = JSON.parseArray(pointGive.getExDoll(), Integer.class);
                                        if (!StringUtil.isNullOrEmpty(dollList)) {

                                            logger.info("赠送娃娃数=>" + dollList.size());
                                            Map<Integer, DollInfo> dollInfoMap = dollInfoService.getDollInfos(dollList);
                                            //if (!StringUtil.isNullOrEmpty(dollInfoMap)) {

                                            dataMaps.put("type", 5);//娃娃类型
                                            dataMaps.put("value", dollList.size());

                                            for (int dollId : dollList) {
                                                //DollInfo dollInfo = dollInfoMap.get(dollId);
                                                DollInfo dollInfo = dollInfoService.getDollInfo(dollId);
                                                logger.info("具体娃娃=>" + JSON.toJSONString(dollInfo));

                                                //取消上下架判断
                                                if (dollInfo != null) {

                                                    UserDoll userDoll = new UserDoll();
                                                    userDoll.setUid(userBase.getUid());
                                                    userDoll.setDollId(dollId);
                                                    userDoll.setStatus(0);
                                                    userDoll.setRemark("开启宝箱赠送");
                                                    userDoll.setGoodsType(dollInfo.getGoodsType());
                                                    long uDollId = userDollService.saveUserDoll(userDoll, "开启宝箱额外赠送娃娃：" + dollInfo.getName());

                                                    if (uDollId > 0) {

                                                        logger.info("成功赠送娃娃=>" + dollInfo.getName());

                                                        sb.append("额外赠送娃娃：").append(dollInfo.getName()).append("\n");

                                                        String commonMsg = "开启宝箱额外赠送娃娃：" + dollInfo.getName();
                                                        String roomMsg = "恭喜玩家 <span style='color: #eaec04'>" + userGeneral.getName() + " </span>开启宝箱额外获得娃娃 <span style='color: #4d16ec'>" + dollInfo.getName() + " </span>";
                                                        String allRoomMsg = "<font color=\"#FFF203\">恭喜玩家 " + userGeneral.getName() + " 在 " + dollBus.getBusId() + "号机 开启宝箱额外获得娃娃 <font color=\"#0b09ff\">" + dollInfo.getName() + "</font> ~~~</font>";

                                                        this.handlePointMsg(dataMaps, userBase, dollBus, commonMsg, roomMsg, allRoomMsg);
                                                    }
                                                }
                                            }
                                            //}
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            logger.info("赠送异常=>" + e.getMessage());
                        }


                    } else {
                        logger.info("当前用户被标记为点送玩家,机器不匹配,busId=>" + dollBus.getBusId());
                    }
                } else {
                    logger.info("当前用户未被标记过点送");
                }
            }
        }
        return sb.toString();
    }


    /**
     * @param dataMaps
     * @param userBase
     * @param dollBus
     * @param commonMsg
     * @param roomMsg
     * @param allRoomMsg
     */
    private void handlePointMsg(Map<String, Object> dataMaps, UserBase userBase, DollBus dollBus, String commonMsg, String roomMsg, String allRoomMsg) {

        if (!StringUtil.isNullOrEmpty(commonMsg)) {
            //发送普通消息
            Msg msg = new Msg(userBase.getUid(), MsgType.NOTICE_SYS.type, commonMsg);
            msgService.sendMsg(msg);
        }

        if (!StringUtil.isNullOrEmpty(roomMsg)) {
            //发送房间弹幕消息
            sendRoomMsg(dollBus, MsgRoomType.PUSH_COIN_BOX_EX.type, dataMaps,
                    roomMsg);
        }
        if (!StringUtil.isNullOrEmpty(allRoomMsg)) {
            //发送全站消息
            sendAllRoomMsg(dollBus, MsgRoomType.PUSH_COIN_ROOM_ALL_MSG.type, dataMaps,
                    allRoomMsg, 0);
        }

    }


    /**
     * 计算总奖池
     *
     * @param coin
     */
    @Override
    public void calcBonus(int coin) {
        logger.info("开始统计奖池,投入=>" + coin + "币");
        if (coin > 0) {
            try {

                PushCoinBoxPool intoPool = new PushCoinBoxPool();
                intoPool.setId(PushCoinConsts.PUSH_COIN_BOX_POOL_ID);
                intoPool.setCurrent(coin);//当轮币数(虚拟)
                intoPool.seteIntoCoin(1);//当轮投币(实体)
                intoPool.seteTotalIntoCoin(1);//实体总投币
                intoPool.setvIntoCoin(coin);//当轮投币(虚拟)
                intoPool.setvTotalIntoCoin(coin);//虚拟总投币


                if (pushCoinBoxDao.updateBoxPoolIntoValue(intoPool) > 0) {

                    PushCoinBoxPool pushCoinBoxPool = pushCoinBoxDao.getBoxPool(PushCoinConsts.PUSH_COIN_BOX_POOL_ID);
                    logger.info("奖池实体=>" + JSON.toJSONString(pushCoinBoxPool));

                    if (pushCoinBoxPool != null) {
                        int total = pushCoinBoxPool.getTotal(); //奖池峰值(虚拟)
                        int current = pushCoinBoxPool.getCurrent();//当前奖池(虚拟)
                        int eIntoCoin = pushCoinBoxPool.geteIntoCoin();//当轮投币(实体)
                        int vIntoCoin = pushCoinBoxPool.getvIntoCoin();//当轮投币(虚拟)
                        int eOutCoin = pushCoinBoxPool.geteOutCoin();//当轮出币(实体)
                        int vOutCoin = pushCoinBoxPool.getvOutCoin();//当轮出币(虚拟)
                        int rate = pushCoinBoxPool.getRate();//当前比例

                        if (current >= total) {
                            DecimalFormat df = new DecimalFormat("#");

                            logger.info("奖池已达到一个峰值,开始计算返现到宝箱金额");
                            if (pushCoinBoxDao.emptyBoxPool(PushCoinConsts.PUSH_COIN_BOX_POOL_ID, eIntoCoin, vIntoCoin, eOutCoin, vOutCoin) > 0) {
                                List<Integer> busIds = Lists.newArrayList();
                                List<DollBus> dollBusList = dollBusService.getAllDollBus();
                                if (!StringUtil.isNullOrEmpty(dollBusList)){
                                    for (DollBus dollBus : dollBusList){
                                        if (dollBus.getType() == 1 && dollBus.getValid() == 1){
                                            busIds.add(dollBus.getBusId());
                                        }
                                    }
                                }

                                if (!StringUtil.isNullOrEmpty(busIds)){

                                    List<PushCoinBox> boxList = pushCoinBoxDao.getBoxListByBusIds(busIds);

                                    if (!StringUtil.isNullOrEmpty(boxList) && boxList.size() != 0) {
                                        Map<Integer, Integer> disBusAlloc = Maps.newHashMap();
                                        String remark = "算法类型=>" + pushCoinBoxPool.getAllocType();

                                        if (rate != 0) {
                                            int result = Integer.valueOf(df.format(1.0 * total * (1.0 * rate / 100))); //返利金额

                                            // 下面通过 返利金额 按需分配
                                            int alloc = 0;
                                            switch (pushCoinBoxPool.getAllocType()) {
                                                case 0: //权重分配算法
                                                    try {
                                                        remark = "使用权重分配算法";
                                                        logger.info("使用权重分配算法 ======== 当前返利=>" + result + ",当前宝箱=>" + boxList.size() + "个");
                                                        disBusAlloc = weightAlloc(boxList, result);
                                                    } catch (Exception e) {
                                                        logger.info("权重分配算法计算异常 err=>" + e.getMessage());
                                                    }
                                                    break;
                                                case 1: //平均分配算法
                                                    try {
                                                        remark = "使用平均分配算法";
                                                        alloc = result / boxList.size();
                                                        logger.info("使用平均分配算法 ======== 奖池峰值=>" + total + ",当前=>" + current + ",计算返利=> " + total + " * ( " + rate + " / 100 ) = " + result + ",总共有 " + boxList.size() + " 个宝箱,每个宝箱平均分配=>" + result / boxList.size() + "金额");
                                                        disBusAlloc = equalAlloc(boxList, alloc);
                                                    } catch (Exception e) {
                                                        logger.info("平均分配算法计算异常 err=>" + e.getMessage());
                                                    }
                                                    break;
                                                default:
                                                    logger.info("算法分配类型=>" + pushCoinBoxPool.getAllocType());
                                                    break;
                                            }
                                            if (pushCoinBoxDao.addPoolRecord(new PushCoinBoxPoolLog(PushCoinConsts.PUSH_COIN_BOX_POOL_ID, current, eIntoCoin, eOutCoin, vIntoCoin, vOutCoin, rate, JSON.toJSONString(disBusAlloc), alloc, remark)) > 0) {
                                                logger.info("新增奖池分配记录");
                                            }
                                        } else {
                                            logger.info("奖池比率为0");
                                        }
                                    } else {
                                        logger.info("宝箱列表为空或数量小于0");
                                    }
                                }else {
                                    logger.info("推币机列表为空,分配失败");
                                }
                            } else {
                                logger.info("清空奖池异常");
                            }
                        } else {
                            logger.info("当前奖池=>" + current + ",未达到峰值=>" + total);
                        }
                    } else {
                        logger.info("奖池实体不存在...");
                    }
                } else {
                    logger.info("更新当前奖池失败...");
                }
            } catch (Exception e) {
                logger.info("计算奖池异常=>" + e.getMessage());
            }
        }
    }


    /**
     * 权重分配算法
     *
     * @param boxList     宝箱列表
     * @param alloc_total 需要分配的权重总额
     * @return 分配结果
     */
    private Map<Integer, Integer> weightAlloc(List<PushCoinBox> boxList, int alloc_total) {
        DecimalFormat df = new DecimalFormat("0.0");
        Map<Integer, Integer> disBusAlloc = Maps.newHashMap();//存放分配的机子和价格
        Map<Integer, Integer> busAndPrice = Maps.newConcurrentMap();//存放推币机和对应的价钱
        List<Integer> busIds = Lists.newArrayList();
        Map<Integer, DollBus> dollBusMap = Maps.newConcurrentMap();
        if (!StringUtil.isNullOrEmpty(boxList)) {
            for (PushCoinBox pushCoinBox : boxList) {
                busIds.add(pushCoinBox.getBusId());
            }
            logger.info("busId 数量=>" + busIds.size());
            if (!StringUtil.isNullOrEmpty(busIds)) {
                int total = 0; //当前所有推币机的价钱总和
                for (PushCoinBox pushCoinBox : boxList) {
                    busIds.add(pushCoinBox.getBusId());
                    DollBus dollBus = dollBusService.getDollBus(pushCoinBox.getBusId());
                    logger.info("busId=>" + pushCoinBox.getBoxId() + ",dollBus=>" + JSON.toJSONString(dollBus));
                    if (dollBus != null) {
                        int price = 0;
                        if (dollBus.getPrizeType() == 0) {
                            price = dollBus.getPrice();
                        } else {
                            if (dollBus.getPrizeType() == 1) {
                                price = dollBus.getPrice() / 3;
                                if (price < 1) {
                                    price = 1;
                                }
                            }
                        }
                        total += price;//总价
                        busAndPrice.put(dollBus.getBusId(), price); //统计每台机器的价钱
                        dollBusMap.put(pushCoinBox.getBusId(), dollBus);
                    }
                }
                logger.info("当前所有推币机的价钱总和=>" + total);
                logger.info("busAndPrice=>" + JSON.toJSONString(busAndPrice));
                logger.info("dollBusMap=>" + JSON.toJSONString(dollBusMap));
                try {
                    if (alloc_total != 0 && total != 0) {
                        double rate_result = Double.valueOf(df.format(1.0 * alloc_total / total));
                        logger.info("权重比例=>" + rate_result);
                        if (!StringUtil.isNullOrEmpty(busAndPrice)) {

                            Collections.shuffle(boxList); //洗牌

                            DecimalFormat dfi = new DecimalFormat("#");
                            int weight_total = 0;
                            int size = boxList.size();
                            for (int i = 0; i < size; i++) {
                                PushCoinBox pushCoinBox = boxList.get(i);
                                if (pushCoinBox != null) {
                                    int alloc = 0;
                                    int price = busAndPrice.get(pushCoinBox.getBusId());
                                    int weight_price = Integer.valueOf(dfi.format(rate_result * price));
                                    int b = weight_total + weight_price; //1980
                                    if (i == size - 1) {
                                        if (price > total) {
                                            alloc = weight_price;
                                            logger.info("-->" + alloc);
                                        } else {
                                            alloc = weight_price - (b - alloc_total);
                                            logger.info("-->" + alloc);
                                        }
                                    } else {
                                        if (b < alloc_total) {
                                            alloc = weight_price;
                                            logger.info("-->" + alloc);
                                        } else {
                                            if (b - alloc_total > 0) {
                                                alloc = weight_price - (b - alloc_total);
                                                logger.info("-->" + alloc);
                                            }
                                        }
                                    }
                                    if (alloc != 0) {
                                        if (pushCoinBoxDao.addJackpot(pushCoinBox.getBusId(), alloc) > 0) {
                                            disBusAlloc.put(pushCoinBox.getBusId(), alloc);
                                            logger.info("推币机宝箱=>" + pushCoinBox.getBusId() + ",增加=>" + alloc + "元");
                                        }
                                    }
                                    weight_total += weight_price; //1638
                                }
                            }


//                            int weight_total = 0;
//                            for (PushCoinBox pushCoinBox : boxList) {
//                                int price = busAndPrice.get(pushCoinBox.getBusId());
//                                if (price != 0) {
//                                    int weight_price = Integer.valueOf(dfi.format(rate_result * price));
//                                    weight_total += weight_price;
//                                    if (weight_total <= alloc_total){
//                                        if (pushCoinBoxDao.addJackpot(pushCoinBox.getBusId(), weight_price) > 0) {
//                                            disBusAlloc.put(pushCoinBox.getBusId(), weight_price);
//                                            logger.info("推币机宝箱=>" + pushCoinBox.getBusId() + ",增加=>" + weight_price + "元");
//                                        }
//                                    }else {
//                                        if (alloc_total - weight_price > 0){
//                                            logger.info("总额");
//                                            if (pushCoinBoxDao.addJackpot(pushCoinBox.getBusId(), weight_price - (alloc_total - weight_price) ) > 0) {
//                                                disBusAlloc.put(pushCoinBox.getBusId(), weight_price);
//                                                logger.info("推币机宝箱=>" + pushCoinBox.getBusId() + ",增加=>" + (weight_price- (alloc_total - weight_price))  + "元");
//                                            }
//                                        }
//                                    }
//                                    logger.info("分配后的额度=>" + weight_total);
//                                } else {
//                                    logger.info("推币机单价为0=>" + pushCoinBox.getBusId());
//                                }
//                            }

                        }
                    } else {
                        logger.info("推币机价钱总和为0,终止分配");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("weightAlloc err=>" + e.getMessage());
                }
            }
        }
        return disBusAlloc;
    }


    /**
     * 平均分配算法
     *
     * @param boxList
     * @return
     */
    private Map<Integer, Integer> equalAlloc(List<PushCoinBox> boxList, int alloc) {
        DecimalFormat df = new DecimalFormat("#");
        Map<Integer, Integer> disBusAlloc = Maps.newHashMap();
        logger.info("当前有=>" + boxList.size() + "个宝箱,平均每个宝箱获得=>" + alloc + "金额");
        if (alloc != 0) {
            logger.info("开始执行分配...");
            for (PushCoinBox pushCoinBox : boxList) {
                if (pushCoinBoxDao.addJackpot(pushCoinBox.getBusId(), alloc) > 0) {
                    disBusAlloc.put(pushCoinBox.getBusId(), alloc);
                    logger.info("推币机宝箱ID=>" + pushCoinBox.getBusId() + ",增加=>" + alloc + "元");
                }
            }
        } else {
            logger.info("分配失败,分配金额为0");
        }
        return disBusAlloc;
    }


    /**
     * 计算宝箱峰值或初始化宝箱
     *
     * @param dollBus
     */
    @Override
    public void calcBox(DollBus dollBus) {
        if (dollBus != null) {
            PushCoinBox pushCoinBox = pushCoinBoxDao.getBox(dollBus.getBusId());
            if (pushCoinBox != null) {
                logger.info("当前基数=>" + pushCoinBox.getBase());
                if (pushCoinBox.getTotal() != dollBus.getPrice() * pushCoinBox.getBase()) {

                    int price = dollBus.getPrice();
                    if (price <= 0) {
                        price = 1;
                    }

                    logger.info("当前机器单价被改变,重新计算峰值");
                    if (pushCoinBoxDao.updateTotal(dollBus.getBusId(), price) > 0) {
                        logger.info("将峰值从原来的=>" + pushCoinBox.getTotal() + " 修改为=>" + price * pushCoinBox.getBase());
                    }
                }
            } else {
                int price = dollBus.getPrice();
                if (price <= 0) {
                    price = 1;
                }
                if (pushCoinBoxDao.initBox(dollBus.getBusId(), price) > 0) {
                    logger.info("初始化宝箱 busId=>" + dollBus.getBusId());
                }
            }
        }
    }


    public static void main(String[] args) {

//        DecimalFormat df = new DecimalFormat("0.0");
//        int total = 2000;
//        int price = 55;
//        double result = Double.valueOf(df.format(1.0 * total / price));
//        System.out.println(result);

//        System.out.println(result * 5);
//        System.out.println(result * 10);
//        System.out.println(result * 10);
//        System.out.println(result * 10);
//        System.out.println(result * 10);
//        System.out.println(result * 10);


//        System.out.println("----------");
//        DecimalFormat dfi = new DecimalFormat("#");
//
//        List<Integer> list = Lists.newArrayList();
//        list.add(5);
//        list.add(10);
//        list.add(10);
//        list.add(10);
//        list.add(10);
//        list.add(10);
//
//        int weight_total = 0;
//        int size = list.size();
//
//        for (int i = 0; i < size; i++) {
//            int weight_price = Integer.valueOf(dfi.format(result * list.get(i)));
//            int b = weight_total + weight_price; //1980
//
//            if (i == size - 1) {
//                if (price > total) {
//                    System.out.println("-->" + weight_price);
//                } else {
//                    System.out.println("b-->" + b + ",weight_price-->" + weight_price + ",-->" + (weight_price - (b - 2000)));
//                }
//            } else {
//                if (b < 2000) {
//                    //System.out.println("weight_total=>" + weight_total + ",weight_price=>" + weight_price);
//                    System.out.println("-->" + weight_price);
//                } else {
//                    if (b - 2000 > 0) {
//                        //System.out.println("c=>" + (b - 2000));
//                        System.out.println("-->" + (weight_price - (b - 2000)));
//                    }
//                }
//            }
//            weight_total += weight_price; //1638
//        }


//        List<Integer> list = Lists.newArrayList();
//        list.add(360);
//        list.add(180);
//        list.add(360);
//        list.add(360);
//        list.add(360);
//        list.add(360);
//        System.out.println(JSON.toJSONString(list));
//        Collections.sort(list, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1 - o2;
//            }
//        });
//
//
//
//        int t = 20;
//        for (int i = 0 ;i < list.size() ; i++){
//            if (t != 0){
//                int a = list.get(i);
//                list.remove(i);
//                list.add(i,a + t / list.size());
//                t--;
//            }
//        }
//
//        System.out.println(JSON.toJSONString(list));


//        List<Integer> doll = Lists.newArrayList();
//        doll.add(1);
//        doll.add(2);
//
//        System.out.println(JSON.toJSONString(doll));
//        List<Integer> dollList = JSON.parseArray(JSON.toJSONString(doll), Integer.class);
//
//
//        for (int d : dollList) {
//            System.out.println(d);
//        }


//        String uuid = UUID.randomUUID().toString().replace("-", "");
//        System.out.println(uuid);
//        System.out.println(MD5_HexUtil.md5Hex("WWXQDZ123**" + uuid));

//        Random random = new Random();
//        int num = random.nextInt(51) + 50;
//        System.out.println(num);

        System.out.println(188 / 10 * 15);

    }
}
