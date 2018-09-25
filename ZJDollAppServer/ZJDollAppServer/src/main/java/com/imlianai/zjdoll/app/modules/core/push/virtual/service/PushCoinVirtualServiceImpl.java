package com.imlianai.zjdoll.app.modules.core.push.virtual.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.MachinePushCoinVirtual;
import com.imlianai.dollpub.domain.coinfactory.virtual.fruits.PushCoinFruitsValue;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.NotEnoughBeanException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.rpc.support.utils.SysTimerHandle;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoDao;
import com.imlianai.zjdoll.app.modules.core.doll.service.DollService;
import com.imlianai.zjdoll.app.modules.core.push.dao.PushCoinBoxDao;
import com.imlianai.zjdoll.app.modules.core.push.service.PushCoinService;
import com.imlianai.zjdoll.app.modules.core.push.virtual.dao.PushCoinOptVirtualDao;
import com.imlianai.zjdoll.app.modules.core.push.virtual.dto.VirtualPushCoinConfigDTO;
import com.imlianai.zjdoll.app.modules.core.push.virtual.util.ZengjingVirtualUtils;
import com.imlianai.zjdoll.app.modules.core.push.virtual.vo.*;
import com.imlianai.zjdoll.app.modules.core.push.virtual.vo.ApplyVirtualRespVO;
import com.imlianai.zjdoll.app.modules.core.push.virtual.vo.VirtualFruitsVerifyReqVO;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.msg.MsgRoom;
import com.imlianai.zjdoll.domain.msg.MsgRoomType;
import com.imlianai.zjdoll.domain.pushcoin.PushCoinOptVirtualWeight;
import com.imlianai.zjdoll.domain.pushcoin.PushCoinVirtualWinRecord;
import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * @author wurui
 * @create 2018-07-04 11:53
 **/
@Service
public class PushCoinVirtualServiceImpl implements PushCoinVirtualService {

    private BaseLogger logger = BaseLogger.getLogger(getClass());

    @Resource
    private DollBusService dollBusService;

    @Resource
    private UserService userService;

    @Resource
    private DollService dollService;

    @Resource
    private TradeService tradeService;

    @Resource
    private PushCoinOptVirtualDao pushCoinOptVirtualDao;

    @Resource
    private DollInfoDao dollInfoDao;

    @Resource
    private MsgService msgService;

    @Resource
    private PushCoinService pushCoinService;

    @Resource
    private PushCoinBoxDao pushCoinBoxDao;

    @Override
    public BaseRespVO entryVirtualCoinPushRoom(EntryVirtualRoomReqVO reqVO) {
        logger.info("entryVirtualCoinPushRoom 进入虚拟推币机=>" + reqVO.getUid());
        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null && dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {
            pushCoinService.calcBox(dollBus);
            UserBase userBase = userService.getUserBase(reqVO.getUid());
            if (userBase != null) {
                Map<String, Object> respMap = Maps.newHashMap();

                //设置用户账户
                TradeAccount tradeAccount = tradeService.getAccount(reqVO.getUid());
                if (tradeAccount != null) {
                    respMap.put("coin", tradeAccount.getCoin());
                    respMap.put("jewel", tradeAccount.getJewel());
                }

                //设置机器单价
                respMap.put("price", dollBus.getPrice());

                //请求进入房间
                BaseRespVO respVO = ZengjingVirtualUtils.entryVirtualCoinPushRoom(userBase.getUid(), Integer.parseInt(dollBus.getDeviceId()));
                logger.info("entryVirtualCoinPushRoom 请求结果=>" + JSON.toJSONString(respVO));
                if (!StringUtil.isNullOrEmpty(respVO)) {
                    if (respVO.isState() && null != respVO.getData()) {
                        VirtualPushCoinConfigDTO configDTO = VirtualPushCoinConfigDTO.jsonDataToConfig(respVO.toJson());
                        //  在此处切入机器配置
                        respMap.put("config", configDTO);

                        try {
                            //加密类型
                            Integer type = JSON.parseObject(respVO.toJson()).getJSONObject("data").getInteger("type");
                            if (type != null) {
                                respMap.put("type", type);
                                dollService.enterBus(userBase.getUid(), dollBus.getBusId());
                            }
                        } catch (Exception e) {
                            logger.info("entryVirtualCoinPushRoom err=>" + e.getMessage());
                            return new BaseRespVO(-1, false, "进入房间失败");
                        }
                    }
                }
                return new BaseRespVO(respMap);
            }
        }
        return new BaseRespVO(0, false, "房间类型错误或机器已下架");
    }

    @Override
    public BaseRespVO laveVirtualCoinPushRoom(EntryVirtualRoomReqVO reqVO) {
        logger.info("laveVirtualCoinPushRoom 离开虚拟推币机=>" + reqVO.getUid());
        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null && dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {
            UserBase userBase = userService.getUserBase(reqVO.getUid());
            if (userBase != null) {
                BaseRespVO respVO = ZengjingVirtualUtils.laveVirtualCoinPushRoom(userBase.getUid(), Integer.parseInt(dollBus.getDeviceId()));
                logger.info("laveVirtualCoinPushRoom 请求结果=>" + JSON.toJSONString(respVO));
                if (!StringUtil.isNullOrEmpty(respVO)) {
                    if (respVO.isState()) {
                        dollService.leaveBus(userBase.getUid(), dollBus.getBusId(), false);
                        logger.info("laveVirtualCoinPushRoom 离开房间成功");
                    }
                    return respVO;
                }
            }
        }
        return new BaseRespVO(0, false, "房间类型错误或机器已下架");
    }

    @Override
    public BaseRespVO apply(OperateVirtualReqVO reqVO) {
        logger.info("virtual apply 用户上机");
        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null) {
            logger.info("virtual apply 机器类型  type=>" + dollBus.getType() + "virtual=>" + dollBus.getVirtual());
            if (dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {

                pushCoinService.calcBox(dollBus);

                UserBase userBase = userService.getUserBase(reqVO.getUid());
                if (userBase != null) {
                    TradeAccount tradeAccount = tradeService.getAccount(reqVO.getUid());
                    if (tradeAccount != null && tradeAccount.getCoin() >= dollBus.getPrice()) {
                        logger.info("virtual apply 余额足够");

                        //默认用户权重
                        int weight;

                        PushCoinOptVirtualWeight pushCoinOptVirtualWeight = pushCoinOptVirtualDao.getWeightByUid(userBase.getUid());
                        if (pushCoinOptVirtualWeight != null){
                            weight = pushCoinOptVirtualWeight.getWeight();
                            logger.info("强制标记权重为:" + weight);
                        }else {
                            //默认权重为5
                            weight = 5;
                            logger.info("默认权重为:" + weight);
                        }

                        ApplyVirtualRespVO respVO = ZengjingVirtualUtils.apply(userBase.getUid(), Integer.parseInt(dollBus.getDeviceId()),weight);
                        logger.info("virtual apply 请求结果=>" + JSON.toJSONString(respVO));
                        if (!StringUtil.isNullOrEmpty(respVO)) {

                            if (respVO.isState() && respVO.getResult() == 200) {

                                DollInfo dollInfo = dollInfoDao.getDollInfo(dollBus.getDollId());
                                if (dollInfo != null) {

                                    MachinePushCoinVirtual machinePushCoinVirtual = new MachinePushCoinVirtual();
                                    machinePushCoinVirtual.setOptId(respVO.getOptId());
                                    machinePushCoinVirtual.setUid(userBase.getUid());
                                    machinePushCoinVirtual.setBusId(dollBus.getBusId());
                                    machinePushCoinVirtual.setDeviceId(dollBus.getDeviceId());
                                    machinePushCoinVirtual.setCustomerId(88); //商户ID
                                    machinePushCoinVirtual.setPrice(dollBus.getPrice());
                                    machinePushCoinVirtual.setRate(dollInfo.getReturnJewel());
                                    machinePushCoinVirtual.setTime(respVO.getPlayTime());

                                    machinePushCoinVirtual.setWeight(weight);
                                    machinePushCoinVirtual.setLean(respVO.getLean());
                                    machinePushCoinVirtual.setMagnetism(respVO.getMagnetism());

                                    if (pushCoinOptVirtualDao.insert(machinePushCoinVirtual) > 0) {
                                        logger.info("新增操作流水成功");
                                        return respVO;
                                    } else {
                                        return new BaseRespVO(5, false, "操作记录生成失败,请重新上机");
                                    }
                                } else {
                                    return new BaseRespVO(4, false, "娃娃信息不存在");
                                }
                            } else {

                                //签名不合法
                                if (respVO.getResult() == 110) {
                                    return new BaseRespVO(-100, false, "系统需要休息一会~");
                                }
                                //重复上机
                                if (respVO.getResult() == 6){
                                    return respVO;
                                }

                                logger.info("virtual 请求异常 result=>" + respVO.getResult() + ",msg=>" + respVO.getMsg());

                                return new BaseRespVO(6, false, respVO.getMsg());
                            }
                        }
                    } else {
                        return new BaseRespVO(2, false, "账户余额不足");
                    }
                } else {
                    return new BaseRespVO(1, false, "用户信息不存在");
                }
            } else {
                return new BaseRespVO(3, false, "房间类型错误或机器已下架");
            }
        }
        return new BaseRespVO(0, false, "机器不存在");
    }

    @Override
    public BaseRespVO push(OperateVirtualReqVO reqVO) {
        final DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null) {

            if (dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {

                pushCoinService.calcBox(dollBus);

                UserBase userBase = userService.getUserBase(reqVO.getUid());
                if (userBase != null) {

                    MachinePushCoinVirtual machinePushCoinVirtual = pushCoinOptVirtualDao.getOne(reqVO.getOptId(), reqVO.getUid());
                    if (machinePushCoinVirtual != null && machinePushCoinVirtual.getState() != 1) {

                        logger.info("optId=>" + machinePushCoinVirtual.getOptId() + ",状态=>" + machinePushCoinVirtual.getState());

                        TradeAccount tradeAccount = tradeService.getAccount(reqVO.getUid());
                        if (tradeAccount != null && tradeAccount.getCoin() >= dollBus.getPrice()) {
                            try {

                                TradeRecord record = new TradeRecord(userBase.getUid(), 0, TradeType.PUSH_COIN_VIRTUAL.type, dollBus.getBusId(), dollBus.getPrice(), TradeCostType.COST_COIN.type, "虚拟推币机消费" + dollBus.getPrice() + "游戏币");
                                record.setOptId(machinePushCoinVirtual.getOptId());
                                if (tradeService.consume(record)) {

                                    logger.info("virtual push 扣费成功");

                                    int weight = machinePushCoinVirtual.getWeight();
                                    if (machinePushCoinVirtual.getTemp() != 0){
                                        weight = machinePushCoinVirtual.getTemp();
                                        logger.info("默认权重：" + machinePushCoinVirtual.getWeight() + ",使用临时权重:" + weight);
                                    }

                                    OperateVirtualRespVO respVO = ZengjingVirtualUtils.push(userBase.getUid(), Integer.parseInt(dollBus.getDeviceId()),weight);
                                    if (!StringUtil.isNullOrEmpty(respVO)) {
                                        logger.info("virtual push 请求结果=>" + JSON.toJSONString(respVO));
                                        if (respVO.isState()) {
                                            try {
                                                if (machinePushCoinVirtual.getOptId() == respVO.getOptId()) {
                                                    //入币
                                                    if (pushCoinOptVirtualDao.updateCoin(machinePushCoinVirtual.getOptId(), 1, dollBus.getPrice(), 0) > 0) {

                                                        if (pushCoinBoxDao.addBoxCoin(dollBus.getBusId(), dollBus.getPrice(), userBase.getUid()) > 0) {
                                                            logger.info("推币机=>" + dollBus.getBusId() + ",uid=>" + userBase.getUid() + ",coin=>" + dollBus.getPrice() + ",money=>" + dollBus.getPrice() / 10);

                                                            if (dollBus.getPrizeType() == 0) {
                                                                pushCoinService.calcBonus(dollBus.getPrice());
                                                            } else {
                                                                logger.info("投钻石出币不计入奖池");
                                                            }
                                                        }

                                                        logger.info("更新入币成功");
                                                        final Map<String, Object> respMap = Maps.newHashMap();
                                                        respMap.put("optId", respVO.getData());
                                                        respMap.put("uid", tradeAccount.getUid());
                                                        TradeAccount account = tradeService.getAccount(reqVO.getUid());
                                                        if (account != null) {
                                                            respMap.put("coin", account.getCoin());
                                                            respMap.put("jewel", account.getJewel());
                                                        }

                                                        respMap.put("playTime", respVO.getPlayTime());

                                                        //额外的控制出奖参数
                                                        respMap.put("magnetism",machinePushCoinVirtual.getMagnetism());
                                                        respMap.put("lean",machinePushCoinVirtual.getLean());


                                                        SysTimerHandle.execute(new TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                sendRoomMsg(dollBus, MsgRoomType.USER_INFO.type, respMap, "");
                                                            }
                                                        }, 0);


                                                        return new BaseRespVO(respMap);
                                                    }
                                                } else {
                                                    return new BaseRespVO(6, false, "非法的结果~~");
                                                }
                                            } catch (Exception e) {
                                                return new BaseRespVO(5, false, "系统异常~~");
                                            }
                                        } else {
                                            return respVO;
                                        }
                                    } else {
                                        return new BaseRespVO(7, false, "请求失败,请稍后重试~~");
                                    }
                                }
                            } catch (TradeOperationException | NotEnoughBeanException e) {
                                e.printStackTrace();
                            }
                        }
                        return new BaseRespVO(2, false, "账户余额不足");
                    } else {
                        return new BaseRespVO(4, false, "操作记录不存在或已结束,请重新上机~~");
                    }
                } else {
                    return new BaseRespVO(1, false, "用户信息不存在");
                }
            } else {
                return new BaseRespVO(3, false, "房间类型错误或机器已下架");
            }
        }
        return new BaseRespVO(0, false, "机器不存在");
    }

    @Override
    public BaseRespVO operate(OperateVirtualReqVO reqVO) {
        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null) {
            if (dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {
                UserBase userBase = userService.getUserBase(reqVO.getUid());
                if (userBase != null) {
                    BaseRespVO respVO = ZengjingVirtualUtils.operate(userBase.getUid(), Integer.parseInt(dollBus.getDeviceId()));
                    if (!StringUtil.isNullOrEmpty(respVO)) {
                        if (respVO.isState()) {
                            return respVO;
                        }
                    }
                } else {
                    return new BaseRespVO(1, false, "用户信息不存在");
                }
            } else {
                return new BaseRespVO(3, false, "房间类型错误或机器已下架");
            }
        }
        return new BaseRespVO(0, false, "机器不存在");
    }

    @Override
    public BaseRespVO finish(OperateVirtualReqVO reqVO) {
        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null) {
            if (dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {
                UserBase userBase = userService.getUserBase(reqVO.getUid());
                if (userBase != null) {
                    MachinePushCoinVirtual machinePushCoinVirtual = pushCoinOptVirtualDao.getOne(reqVO.getOptId(), reqVO.getUid());
                    if (machinePushCoinVirtual != null && machinePushCoinVirtual.getState() != 1) {
                        BaseRespVO respVO = ZengjingVirtualUtils.finish(userBase.getUid(), Integer.parseInt(dollBus.getDeviceId()));
                        if (!StringUtil.isNullOrEmpty(respVO)) {
                            if (respVO.isState()) {
                                if (respVO.getResult() == 200) {
                                    try {
                                        long optId = Long.parseLong(respVO.getData().toString());
                                        if (optId > 0 && optId == reqVO.getOptId()) {
                                            logger.info("请求成功,执行主动下机");
                                            if (pushCoinOptVirtualDao.updateState(optId) > 0) {
                                                logger.info("状态更新成功");
                                                Map<String, Object> respMap = Maps.newHashMap();
                                                TradeAccount account = tradeService.getAccount(reqVO.getUid());
                                                if (account != null) {
                                                    respMap.put("coin", account.getCoin());
                                                    respMap.put("jewel", account.getJewel());
                                                }
                                                return new BaseRespVO(respMap);
                                            } else {
                                                return new BaseRespVO(4, false, "主动下机失败");
                                            }
                                        } else {
                                            return new BaseRespVO(6, false, "服务器操作ID异常");
                                        }
                                    } catch (Exception e) {
                                        return new BaseRespVO(8, false, "服务器操作ID异常");
                                    }
                                } else {
                                    return new BaseRespVO(9, false, respVO.getMsg());
                                }
                            } else {
                                return new BaseRespVO(7, false, respVO.getMsg());
                            }
                        }
                    } else {
                        return new BaseRespVO(5, false, "当前状态已下机");
                    }
                } else {
                    return new BaseRespVO(1, false, "用户信息不存在");
                }
            } else {
                return new BaseRespVO(3, false, "房间类型错误或机器已下架");
            }
        }
        return new BaseRespVO(0, false, "机器不存在");
    }

    @Override
    public BaseRespVO query(VirtualFruitsQueryReqVO reqVO) {
        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null) {
            if (dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {
                UserBase userBase = userService.getUserBase(reqVO.getUid());
                if (userBase != null) {

                    MachinePushCoinVirtual machinePushCoinVirtual = pushCoinOptVirtualDao.getOne(reqVO.getOptId(), reqVO.getUid());
                    if (machinePushCoinVirtual != null && machinePushCoinVirtual.getState() != 1) {

                        logger.info("virtual push 操作记录  optId=>" + machinePushCoinVirtual.getOptId() + ",状态=>" + machinePushCoinVirtual.getState());

                        //水果机查奖请求
                        VirtualFruitsQueryRespVO respVO = this.query(reqVO.getUid(), reqVO.getOptId(),dollBus);
                        if (!StringUtil.isNullOrEmpty(respVO)){
                            return respVO;
                        }

                        return new BaseRespVO(-200, false, "请求超时,请稍后重试");

                    } else {
                        return new BaseRespVO(4, false, "操作记录不存在或已结束,请重新上机~~");
                    }

                } else {
                    return new BaseRespVO(1, false, "用户信息不存在");
                }
            } else {
                return new BaseRespVO(3, false, "房间类型错误或机器已下架");
            }
        }
        return new BaseRespVO(0, false, "机器不存在");
    }

    /**
     * 水果机查奖
     * @param uid
     * @param optId
     * @param dollBus
     * @return
     */
    private VirtualFruitsQueryRespVO query(long uid,long optId,DollBus dollBus){
        VirtualFruitsQueryRespVO respVO = ZengjingVirtualUtils.query(uid, optId, Integer.parseInt(dollBus.getDeviceId()));
        if (!StringUtil.isNullOrEmpty(respVO)) {
            //在此处切入修改
            //中奖类型(0:摇奖机,1:泡泡,2:转盘,3:保留,4:1丑,5:2丑,6:3丑)

            //处理中奖记录
            handleWinRecord(respVO, dollBus, uid, optId);

            //只有点泡泡跟转盘需要预先传个金币数量
            if (respVO.getType() == 1 || respVO.getType() == 2){

            }else {
                respVO.setCoin(0);
                respVO.setRate(0);
            }

            return respVO;

        }
        return null;
    }


    /**
     * 处理中奖记录
     *
     * @param respVO
     */
    private void handleWinRecord(VirtualFruitsQueryRespVO respVO, DollBus dollBus, final long uid, final long optId) {
        if (!StringUtil.isNullOrEmpty(respVO)) {
            int result = respVO.getResult();
            if (result == -1) {
                logger.info("无中奖结果。");
            }

            if (result == 200 || result == 210) {
                logger.info("记录中奖结果");

                logger.info("中奖类型: " + PushCoinFruitsValue.FruitsValueWinType.getDescByType(respVO.getType()));

                if (dollBus != null) {

                    if (dollBus.getDeviceId().equals(respVO.getBusId() + "")) {
                        PushCoinVirtualWinRecord record = new PushCoinVirtualWinRecord();

                        BeanUtils.copyProperties(respVO, record);

                        record.setBusId(dollBus.getBusId());
                        record.setDeviceId(dollBus.getDeviceId());

                        record.setUid(uid);
                        record.setOptId(optId);

                        record.setAffirm(0);
                        record.setResult(0);
                        record.setRemark("待确认领奖");

                        if (pushCoinOptVirtualDao.insertWinRecord(record) > 0) {
                            logger.info("新增虚拟推币机中奖记录");

                            //中奖则处理权重
                            SysTimerHandle.execute(new TimerTask() {
                                @Override
                                public void run() {
                                    MachinePushCoinVirtual machinePushCoinVirtual = pushCoinOptVirtualDao.getOne(optId,uid);
                                    if (machinePushCoinVirtual != null){
                                        if (machinePushCoinVirtual.getTemp() == 0){
                                            logger.info("临时权重为0时无需处理");
                                        }else {
                                            if(pushCoinOptVirtualDao.updateTempWeight(machinePushCoinVirtual.getId(),0) > 0){
                                                logger.info("成功处理临时权重,现在将按正常权重计算");
                                            }
                                        }
                                    }
                                }
                            },0);

                        }
                    }
                }
            } else {
                //服务端处理错误
                if (result == 100 || result == 101) {
                    logger.info("服务端返回错误结果");
                }
            }
        }
    }

    //全站弹幕
    private void sendAllRoomMsgWebP(DollBus dollBus, int msgRoomType, Map<String, Object> data, String body, int type, String webpUrl) {
        List<DollBus> list = dollBusService.getAllDollBus();
        if (!StringUtil.isNullOrEmpty(list)) {
            MsgRoom msgRoom = new MsgRoom(dollBus, msgRoomType, body);
            msgRoom.setData(JSON.toJSONString(data));
            //msgRoomWebp.initWebPMsg("http://lianai-image-sys.qiniudn.com/e20180125/toastn.webp");
            msgRoom.initWebPMsg(webpUrl);
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
    public BaseRespVO affirm(VirtualFruitsAffirmReqVO reqVO) {
        final DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null) {
            if (dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {
                UserBase userBase = userService.getUserBase(reqVO.getUid());
                if (userBase != null) {

                    MachinePushCoinVirtual machinePushCoinVirtual = pushCoinOptVirtualDao.getOne(reqVO.getOptId(), reqVO.getUid());
                    if (machinePushCoinVirtual != null && machinePushCoinVirtual.getState() != 1) {

                        logger.info("virtual push 操作记录  optId=>" + machinePushCoinVirtual.getOptId() + ",当前上机状态=>" + (machinePushCoinVirtual.getState() == 0 ? "上机中" : "已下机"));

                        final VirtualFruitsAffirmRespVO respVO = ZengjingVirtualUtils.affirm(reqVO.getUid(), reqVO.getOptId(), Integer.parseInt(dollBus.getDeviceId()), reqVO.getAllotId(), reqVO.getResult());
                        if (!StringUtil.isNullOrEmpty(respVO)) {
                            //在此处切入修改

                            final long uid = userBase.getUid();
                            final long allotId = reqVO.getAllotId();
                            final int resultCoin = respVO.getTemp1();
                            final int resultRate = respVO.getTemp2();

                            final PushCoinVirtualWinRecord winRecord = pushCoinOptVirtualDao.getWinRecordByAllotIdAndUid(allotId,uid);
                            if (winRecord != null && winRecord.getAffirm() == 0 && winRecord.getUid() == respVO.getUid()) {

                                if (pushCoinOptVirtualDao.updateWinRecordResult(allotId, resultCoin, resultRate, winRecord.getRemark() + "(确认中奖结果,获得:" + resultCoin + "币)") > 0) {

                                    logger.info("确认中奖结果");

                                    final UserGeneral userGeneral = userService.getUserGeneral(uid);
                                    if (userGeneral != null) {

                                        String msg = winRecord.getType() == 0 ? "水果奖"
                                                : winRecord.getType() == 1 ? "泡泡奖"
                                                : winRecord.getType() == 2 ? "转盘奖"
                                                : winRecord.getType() == 4 ? "一丑奖"
                                                : winRecord.getType() == 5 ? "二丑奖"
                                                : winRecord.getType() == 6 ? "三丑奖"
                                                : "未知奖";

                                        final String body = "恭喜玩家 <span style='color: #0088cc'>" + userGeneral.getName() + "</span> 摇中 <span style='color: #4d16ec'>" + msg + "</span> ,奖励 <span style='color: #03a9f4'>" + resultCoin + "</span> 币";
                                        final String allBody = "<font color='#0088cc'>恭喜玩家 " + userGeneral.getName() + " 在 " + dollBus.getBusId() + " 号机摇中 <span style='color: #4d16ec'>" + msg + "</span> ,奖励 <span style='color: #03a9f4'>" + resultCoin + "</span> 币";
                                        boolean isSend = false;

                                        String webpUrl = "";
                                        if (winRecord.getType() == 0 || winRecord.getType() == 1 || winRecord.getType() == 2) {

                                            webpUrl = "http://lianai-image-sys.qiniudn.com/e20180125/toastn.webp";
                                            isSend = true;

                                        } else if (winRecord.getType() == 4 || winRecord.getType() == 5 || winRecord.getType() == 6) {

                                            webpUrl = "http://lianai-image-sys.qiniudn.com/e20180125/xinnianpiaopin.webp";
                                            isSend = true;

                                        } else {
                                            logger.info("无效的类型");
                                        }

                                        if (isSend) {

                                            final String finalWebpUrl = webpUrl;

                                            SysTimerHandle.execute(new TimerTask() {
                                                @Override
                                                public void run() {

                                                    //消息
                                                    MsgRoom msgRoomWebpOne = new MsgRoom(dollBus, userGeneral, MsgRoomType.BUS_WEBP.type, body);
                                                    msgRoomWebpOne.initWebPMsg(finalWebpUrl);
                                                    msgService.sendMsgRoom(msgRoomWebpOne);

                                                    sendAllRoomMsgWebP(dollBus, MsgRoomType.BUS_WEBP.type, Maps.<String, Object>newHashMap(),
                                                            allBody, 0, finalWebpUrl);

                                                }
                                            }, 0);

                                        }
                                    }

                                }
                            }

                            return respVO;
                        }

                    } else {
                        return new BaseRespVO(4, false, "操作记录不存在或已结束,请重新上机~~");
                    }

                } else {
                    return new BaseRespVO(1, false, "用户信息不存在");
                }
            } else {
                return new BaseRespVO(3, false, "房间类型错误或机器已下架");
            }
        }
        return new BaseRespVO(0, false, "机器不存在");
    }

    @Override
    public BaseRespVO verify(VirtualFruitsVerifyReqVO reqVO) {
        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null) {
            if (dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {
                UserBase userBase = userService.getUserBase(reqVO.getUid());
                if (userBase != null) {
                    BaseRespVO respVO = ZengjingVirtualUtils.verifyResult(reqVO.getAllotId(), Integer.parseInt(dollBus.getDeviceId()),reqVO.getOptId(),reqVO.getResult());
                    if (!StringUtil.isNullOrEmpty(respVO)) {
                        if (respVO.isState()) {
                            return new BaseRespVO(200,true,"SUCCESS");
                        }else {
                            //异常操作,封号处理
                            if (respVO.getResult() == 500){

                                //  处理异常中奖结果
                                this.handleErrorRecord(reqVO.getAllotId(),reqVO.getOptId(),reqVO.getUid());

                                return new BaseRespVO(100,false,"ERROR");

                            }
                        }
                        return new BaseRespVO(100,false,respVO.getMsg());
                    }
                } else {
                    return new BaseRespVO(1, false, "用户信息不存在");
                }
            } else {
                return new BaseRespVO(3, false, "房间类型错误或机器已下架");
            }
        }
        return new BaseRespVO(0, false, "机器不存在");
    }

    @Override
    public BaseRespVO refresh(OperateVirtualReqVO reqVO) {
        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null) {
            if (dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {
                UserBase userBase = userService.getUserBase(reqVO.getUid());
                if (userBase != null) {

                    MachinePushCoinVirtual machinePushCoinVirtual = pushCoinOptVirtualDao.getOne(reqVO.getOptId(), reqVO.getUid());
                    if (machinePushCoinVirtual != null && machinePushCoinVirtual.getState() != 1) {

                        logger.info("virtual push 操作记录  optId=>" + machinePushCoinVirtual.getOptId() + ",状态=>" + machinePushCoinVirtual.getState());

                        BaseRespVO respVO = ZengjingVirtualUtils.refresh(reqVO.getUid(), reqVO.getOptId(), Integer.parseInt(dollBus.getDeviceId()));
                        if (!StringUtil.isNullOrEmpty(respVO)) {

                            if (pushCoinOptVirtualDao.refreshEndTime(reqVO.getOptId()) > 0) {
                                logger.info("刷新游戏时间");
                            }

                            return respVO;
                        }

                    } else {
                        return new BaseRespVO(4, false, "操作记录不存在或已结束,请重新上机~~");
                    }

                } else {
                    return new BaseRespVO(1, false, "用户信息不存在");
                }
            } else {
                return new BaseRespVO(3, false, "房间类型错误或机器已下架");
            }
        }
        return new BaseRespVO(0, false, "机器不存在");
    }

    @Override
    public BaseRespVO slots(VirtualFruitsSlotsReqVO reqVO) {
        final DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null) {

            if (dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {

                pushCoinService.calcBox(dollBus);

                UserBase userBase = userService.getUserBase(reqVO.getUid());
                if (userBase != null) {

                    final MachinePushCoinVirtual machinePushCoinVirtual = pushCoinOptVirtualDao.getOne(reqVO.getOptId(), reqVO.getUid());
                    if (machinePushCoinVirtual != null && machinePushCoinVirtual.getState() != 1) {

                        logger.info("virtual slots 操作记录  optId=>" + machinePushCoinVirtual.getOptId() + ",状态=>" + machinePushCoinVirtual.getState());

                        if (dollBus.getSlots() <= 0){
                            dollBus.setSlots(5);
                        }

                        //需消耗金币
                        int consumeCoin = dollBus.getPrice() * dollBus.getSlots();

                        TradeAccount tradeAccount = tradeService.getAccount(reqVO.getUid());
                        if (tradeAccount != null && tradeAccount.getCoin() >= consumeCoin) {
                            try {

                                TradeRecord record = new TradeRecord(userBase.getUid(), 0, TradeType.PUSH_SLOTS_VIRTUAL_COIN.type, dollBus.getBusId(), consumeCoin, TradeCostType.COST_COIN.type, "水果摇奖消费" + consumeCoin + "游戏币");
                                record.setOptId(machinePushCoinVirtual.getOptId());
                                if (tradeService.consume(record)) {

                                    logger.info("virtual push 扣费成功");

                                    final int tempWeight = machinePushCoinVirtual.getWeight() + 1;

                                    VirtualFruitsSlotsRespVO respVO = ZengjingVirtualUtils.slots(userBase.getUid(), machinePushCoinVirtual.getOptId(),Integer.parseInt(dollBus.getDeviceId()),tempWeight,dollBus.getSlots());
                                    if (!StringUtil.isNullOrEmpty(respVO)) {

                                        logger.info("virtual push 请求结果=>" + JSON.toJSONString(respVO));
                                        if (respVO.isState()) {
                                            try {
                                                if (machinePushCoinVirtual.getOptId() == respVO.getOptId() && respVO.getSlots() == dollBus.getSlots()) {

                                                    //更新入币
                                                    if (pushCoinOptVirtualDao.updateSlots(machinePushCoinVirtual.getOptId(), respVO.getSlots(), consumeCoin) > 0) {
                                                        logger.info("拉霸机更新入币成功");

                                                        final Map<String, Object> respMap = Maps.newHashMap();
                                                        respMap.put("optId", respVO.getData());
                                                        respMap.put("uid", tradeAccount.getUid());
                                                        TradeAccount account = tradeService.getAccount(reqVO.getUid());
                                                        if (account != null) {
                                                            respMap.put("coin", account.getCoin());
                                                            respMap.put("jewel", account.getJewel());
                                                        }

                                                        respMap.put("playTime", respVO.getPlayTime());

                                                        //额外的控制出奖参数
                                                        respMap.put("magnetism",machinePushCoinVirtual.getMagnetism());
                                                        respMap.put("lean",machinePushCoinVirtual.getLean());


                                                        SysTimerHandle.execute(new TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                if (pushCoinOptVirtualDao.updateTempWeight(machinePushCoinVirtual.getId(),tempWeight) > 0){
                                                                    logger.info("更新临时权重为:" + tempWeight);
                                                                }
                                                                sendRoomMsg(dollBus, MsgRoomType.USER_INFO.type, respMap, "");
                                                            }
                                                        }, 0);


                                                        //水果机查奖请求
                                                        VirtualFruitsQueryRespVO fruitsQueryRespVO = this.query(reqVO.getUid(), reqVO.getOptId(),dollBus);
                                                        if (fruitsQueryRespVO != null){
                                                            fruitsQueryRespVO.setData(respMap);
                                                            return fruitsQueryRespVO;
                                                        }

                                                        return new BaseRespVO(-200, false, "请求超时,请稍后重试");

                                                    }
                                                } else {
                                                    return new BaseRespVO(6, false, "非法的结果~~");
                                                }
                                            } catch (Exception e) {
                                                return new BaseRespVO(5, false, "系统异常~~");
                                            }
                                        } else {
                                            return respVO;
                                        }
                                    } else {
                                        return new BaseRespVO(7, false, "请求失败,请稍后重试~~");
                                    }
                                }
                            } catch (TradeOperationException | NotEnoughBeanException e) {
                                e.printStackTrace();
                            }
                        }
                        return new BaseRespVO(2, false, "账户余额不足");
                    } else {
                        return new BaseRespVO(4, false, "操作记录不存在或已结束,请重新上机~~");
                    }
                } else {
                    return new BaseRespVO(1, false, "用户信息不存在");
                }
            } else {
                return new BaseRespVO(3, false, "房间类型错误或机器已下架");
            }
        }
        return new BaseRespVO(0, false, "机器不存在");
    }

    @Override
    public int CoinToJewel(long uid, int busId, long optId, int outCoin, int win, int isFinal) {
        logger.info("virtual CoinToJewel 虚拟推币机入币回调=>" + outCoin + "中奖得币=>" + win + ",是否最终结果=>" + isFinal);

        MachinePushCoinVirtual machinePushCoinVirtual = pushCoinOptVirtualDao.getOne(optId, uid);

        logger.info("virtual CoinToJewel record=>" + JSON.toJSONString(machinePushCoinVirtual));

        if (machinePushCoinVirtual != null) {

            logger.info("virtual CoinToJewel 回调的busId=>" + busId + ",真实的busId=>" + machinePushCoinVirtual.getBusId());

            DollBus dollBus = dollBusService.getDollBus(machinePushCoinVirtual.getBusId());
            if (dollBus.getVirtual() == 1 && dollBus.getValid() == 1 && dollBus.getType() == 1) {

                logger.info("virtual CoinToJewel 机器认证通过");

                UserBase userBase = userService.getUserBase(uid);
                if (userBase != null) {

                    logger.info("virtual CoinToJewel 用户真实存在=>" + userBase.getUid());

                    try {
                        if (machinePushCoinVirtual.getState() != 1) {

                            logger.info("virtual CoinToJewel 正在游戏中...");


                            //消息体
                            Map<String, Object> map = Maps.newHashMap();
                            Map<String, Object> allMsgMap = Maps.newHashMap();
                            map.put("isFinal", isFinal);
                            map.put("uid", uid);

                            allMsgMap.put("uid", uid);


                            UserGeneral userGeneral = userService.getUserGeneral(uid);
                            if (userGeneral != null) {
                                map.put("name", userGeneral.getName());
                                map.put("head", userGeneral.getHead());

                                allMsgMap.put("name", userGeneral.getName());
                                allMsgMap.put("head", userGeneral.getHead());


                                if (isFinal == 0) {

                                    logger.info("virtual CoinToJewel 正常得币状态,outCoin=>" + outCoin);

                                    if (outCoin > 0) {

                                        DollInfo dollInfo = dollInfoDao.getDollInfo(dollBus.getDollId());
                                        if (dollInfo != null) {

                                            logger.info("virtual CoinToJewel 娃娃可兑换钻石=>" + dollInfo.getReturnJewel());

                                            if (pushCoinOptVirtualDao.updateCoin(machinePushCoinVirtual.getOptId(), outCoin, outCoin * dollBus.getPrice(), 1) > 0) {

                                                logger.info("virtual CoinToJewel 更新出币成功=>" + outCoin + ",uid=>" + uid);

                                                int jewel = outCoin * dollInfo.getReturnJewel();
                                                if (jewel != 0) {

                                                    if (pushCoinOptVirtualDao.updateJewel(jewel, optId) > 0) {

                                                        if (charge(userBase.getUid(), machinePushCoinVirtual.getOptId(), dollBus.getBusId(), jewel, "虚拟推币机出奖得" + jewel + "钻") > 0) {

                                                            logger.info("virtual CoinToJewel 充值钻石成功,开始发送获奖消息");

                                                            map.put("jewel", jewel);
                                                            map.put("time", 30);
                                                            TradeAccount tradeAccount = tradeService.getAccount(userBase.getUid());
                                                            if (tradeAccount != null) {
                                                                map.put("u_coin", tradeAccount.getCoin());
                                                                map.put("u_jewel", tradeAccount.getJewel());
                                                            }
                                                            sendRoomMsg(dollBus, MsgRoomType.PUSH_COIN_JEWEL.type, map, "恭喜玩家 <span style='color: #eaec04'>" + userGeneral.getName() + " </span>获得 <span style='color: #4d16ec'>" + jewel + " </span>钻石");

                                                            allMsgMap.put("jewel", jewel);

                                                            //发送全站消息

                                                            sendAllRoomMsg(dollBus, MsgRoomType.PUSH_COIN_ROOM_ALL_MSG.type, allMsgMap,
                                                                    "<font color=\"#FFF203\">恭喜玩家 " + userGeneral.getName() + " 在 " + dollBus.getBusId() + " 号机推币获得 <font color=\"#0b09ff\">" + jewel + "</font> 钻石~~~</font>", 0);

                                                        } else {
                                                            logger.info("virtual CoinToJewel 系统充值失败");
                                                        }
                                                    } else {
                                                        logger.info("virtual CoinToJewel 更新钻石失败");
                                                    }
                                                }
                                            } else {

                                                logger.info("virtual CoinToJewel 更新出币失败");
                                            }
                                        }
                                    } else {

                                        logger.info("virtual CoinToJewel 出币为0不处理");
                                    }

                                } else {

                                    if (isFinal == 1) {

                                        logger.info("virtual CoinToJewel 最终结果状态");

                                        if (pushCoinOptVirtualDao.updateState(optId) > 0 && pushCoinOptVirtualDao.updateJewelStatus(optId) > 0) {
                                            logger.info("virtual CoinToJewel 更新操作状态成功");
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
                            } else {
                                logger.info("virtual CoinToJewel 已被锁定不可操作 optId=>" + machinePushCoinVirtual.getOptId());
                            }
                        } else {
                            logger.info("virtual CoinToJewel 已结束上机,不处理结果...");
                        }
                    } catch (Exception e) {
                        logger.info("virtual CoinToJewel err=>" + e.getMessage());
                    }
                }

            }
        }

        return 0;
    }

    @Override
    public void handleVirtualConfig() {

    }

    @Override
    public void handleTimeOutRecord() {
        List<MachinePushCoinVirtual> virtualList = pushCoinOptVirtualDao.getTimeOutRecord();
        if (!StringUtil.isNullOrEmpty(virtualList)) {
            logger.info("处理超时记录=>" + JSON.toJSONString(virtualList));
            try {
                for (MachinePushCoinVirtual machinePushCoinVirtual : virtualList) {
                    if (machinePushCoinVirtual != null && machinePushCoinVirtual.getState() != 1) {
                        int flag = pushCoinOptVirtualDao.updateTimeOutStatus(machinePushCoinVirtual.getId());
                        if (flag > 0) {
                            logger.info("成功处理记录=>" + JSON.toJSONString(machinePushCoinVirtual));
                        }
                    }
                }
            } catch (Exception e) {
                logger.info("处理超时记录异常=>" + e.getMessage());
            }
        }
    }


    /**
     * 系统充值
     *
     * @param uid
     * @param optId
     * @param jewel
     * @return
     * @throws TradeOperationException
     */
    private int charge(long uid, long optId, int busId, int jewel, String remark) {
        logger.info("virtual charge 执行系统充值");
        try {
            TradeRecord tradeRecord = new TradeRecord(uid, uid,
                    TradeType.PUSH_PRIZE_VIRTUAL_JEWEL.type, busId, jewel,
                    TradeCostType.COST_JEWEL.type, remark);
            tradeRecord.setOptId(optId);

            if (tradeService.charge(tradeRecord)) {
                logger.info("virtual charge 成功充值=>" + jewel + " 个钻石");
                return 1;
            }
        } catch (TradeOperationException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 发送房间消息
     *
     * @param dollBus
     * @param msgRoomType
     * @param data
     * @param body
     */
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

    /**
     * 处理异常记录
     * @param allotId
     * @param optId
     */
    private void handleErrorRecord(long allotId,long optId,long uid){

        MachinePushCoinVirtual pushCoinVirtual = pushCoinOptVirtualDao.getOne(optId,uid);
        if (pushCoinVirtual != null){

            String remark;

            if (pushCoinVirtual.getError() == 0){

                remark = "异常中奖ID:" + allotId;

            }else if (pushCoinVirtual.getError() > 0){

                remark = pushCoinVirtual.getRemark() + "," + allotId;

            }else {

                remark = pushCoinVirtual.getRemark() + "," + allotId;

            }

            if(pushCoinOptVirtualDao.updateErrorRecord(optId,uid,remark) > 0){
                logger.info("处理一条异常记录,optId=>" + optId + ",uid=>" + uid);
            }

        }

    }

}