package com.imlianai.dollpub.app.modules.core.coinfactory.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.imlianai.dollpub.app.modules.core.coinfactory.dao.*;
import com.imlianai.dollpub.app.modules.core.coinfactory.service.PushCoinFruitService;
import com.imlianai.dollpub.app.modules.core.coinfactory.service.PushCoinUnity3DService;
import com.imlianai.dollpub.app.modules.core.coinfactory.util.PushCoinVirtualUtil;
import com.imlianai.dollpub.app.modules.core.coinfactory.vo.*;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.MachinePushCoinVirtual;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.PushBusStatusVirtual;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.PushCoinVirtualConfig;
import com.imlianai.dollpub.domain.coinfactory.virtual.fruits.PushCoinFruitsAllot;
import com.imlianai.dollpub.domain.coinfactory.virtual.fruits.PushCoinFruitsResult;
import com.imlianai.dollpub.domain.coinfactory.virtual.fruits.PushCoinFruitsValue;
import com.imlianai.dollpub.domain.coinfactory.virtual.fruits.PushCoinFruitsWin;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.MD5_HexUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.rpc.support.utils.SysTimerHandle;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 水果机核心数值
 *
 * @author wurui
 * @create 2018-06-19 10:18
 **/
@Service
public class PushCoinFruitServiceImpl implements PushCoinFruitService, ApplicationListener<ContextRefreshedEvent> {
    private BaseLogger logger = BaseLogger.getLogger(getClass());

    @Resource
    private PushCoinVirtualFruitsDao pushCoinVirtualFruitsDao;

    @Resource
    private PushCoinUnity3DService pushCoinUnity3DService;

    @Resource
    private DollBusService dollBusService;

    @Resource
    private CustomerService customerService;


    //全表
    @Resource
    private PushCoinVirtualMachineDao pushCoinVirtualMachineDao;

    //带分表
    @Resource
    private PushCoinVirtualOptRouterDao pushCoinVirtualOptRouterDao;

    @Resource
    private PushCoinVirtualConfigDao pushCoinVirtualConfigDao;

    @Resource
    private PushBusStatusVirtualDao pushBusStatusVirtualDao;

    /**
     * 存放水果机能出现的所有结果 7*7*7=343
     */
    private Map<Integer, List<PushCoinFruitsResult>> fruitResultMap = Maps.newConcurrentMap();

    /**
     * 存放水果机核心数值
     */
    private Map<Integer, PushCoinFruitsValue> fruitsCoreValueMap = Maps.newConcurrentMap();

    /**
     * 存放中奖类型与结果类型的映射
     */
    private Map<Integer, Integer> winTypeToResultTypeMap = Maps.newConcurrentMap();


    @Override
    public VirtualFruitsQueryRespVO query(VirtualFruitsQueryReqVO reqVO) {
        VirtualFruitsQueryRespVO respVO = new VirtualFruitsQueryRespVO();

        Customer customer = customerService.getCustomer(reqVO.getAppId());
        if (customer == null) {

            respVO.setState(false);
            respVO.setMsg("商户不存在");
            respVO.setResult(-1);

            return respVO;
        }


        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null) {
            if (dollBus.getVirtual() == 1 && dollBus.getType() == 1) {

                respVO.setBusId(dollBus.getBusId());

                MachinePushCoinVirtual machinePushCoinVirtual = pushCoinVirtualMachineDao.getOne(reqVO.getOptId());
                if (machinePushCoinVirtual != null && machinePushCoinVirtual.getState() != 1) {

                    try {

                        PushCoinFruitsAllot pushCoinFruitsAllot = pushCoinVirtualFruitsDao.getOneWinAllotByCondition(
                                machinePushCoinVirtual.getBusId(),
                                machinePushCoinVirtual.getCustomerId(),
                                machinePushCoinVirtual.getUid());

                        if (pushCoinFruitsAllot != null) {

                            final int winId = pushCoinFruitsAllot.getWinId();

                            if ((pushCoinFruitsAllot.getResult() == 0 || pushCoinFruitsAllot.getResult() == 1) && pushCoinFruitsAllot.getAffirm() == 0) {
                                logger.info("中奖记录未被使用过");

                                if (pushCoinFruitsAllot.getBusId() == dollBus.getBusId() && pushCoinFruitsAllot.getUid() == reqVO.getUid()) {

                                    logger.info("中奖分配匹配成功,当前用户中奖=>" + pushCoinFruitsAllot.getCoin() + "币");

                                    boolean isNext = false;

                                    if (pushCoinFruitsAllot.getResult() == 0) {

                                        if (pushCoinVirtualFruitsDao.updateAllotResult(pushCoinFruitsAllot.getId(), 1, pushCoinFruitsAllot.getRemark() + "(待领取)") > 0) {
                                            isNext = true;
                                        } else {
                                            logger.info("更新分配结果失败");
                                        }

                                    } else if (pushCoinFruitsAllot.getResult() == 1) {

                                        logger.info("该奖处于待确认领奖状态,继续往下执行.");
                                        isNext = true;

                                    } else {
                                        logger.info("中奖记录状态错误");
                                    }


                                    if (isNext) {

                                        logger.info("optId=>" + reqVO.getOptId() + ",uid=>" + reqVO.getUid() + ",busId=>" + dollBus.getBusId() + ",中奖类型：" + PushCoinFruitsValue.FruitsValueWinType.getDescByType(pushCoinFruitsAllot.getType()));

                                        int coin = pushCoinFruitsAllot.getRate() == 0 ? pushCoinFruitsAllot.getCoin() : pushCoinFruitsAllot.getCoin() * pushCoinFruitsAllot.getRate();
                                        logger.info("等待确认：" + coin + "币");

                                        //点泡泡 和 转盘 需要传金币
//                                        if (pushCoinFruitsAllot.getType() == 1 || pushCoinFruitsAllot.getType() == 2) {
//                                            respVO.setCoin(pushCoinFruitsAllot.getCoin());
//                                        }

                                        //respVO.setCoin(coin);

                                        respVO.setCoin(pushCoinFruitsAllot.getCoin());
                                        respVO.setRate(pushCoinFruitsAllot.getRate());

                                        respVO.setAllotId(pushCoinFruitsAllot.getId());
                                        respVO.setFvId(pushCoinFruitsAllot.getFvId());

                                        respVO.setType(pushCoinFruitsAllot.getType());
                                        respVO.setMagnetism(pushCoinFruitsAllot.getMagnetism());
                                        respVO.setState(true);

                                        try {
                                            List<Integer> assembly = JSON.parseArray(pushCoinFruitsAllot.getAssembly(), Integer.TYPE);
                                            if (!StringUtil.isNullOrEmpty(assembly)) {

                                                respVO.setA(assembly.get(0));
                                                respVO.setB(assembly.get(1));
                                                respVO.setC(assembly.get(2));
                                                respVO.setMsg("待确认领奖");
                                                respVO.setResult(200);

                                            } else {

                                                int resultType = winTypeToResultTypeMap.get(pushCoinFruitsAllot.getType());

                                                PushCoinFruitsResult winResult = getWinAssemblyByType(resultType);

                                                if (winResult != null) {
                                                    logger.info("随机中奖结果:" + winResult.getAssembly() + ",中奖描述=>" + winResult.getRemark());
                                                    respVO.setA(winResult.getA());
                                                    respVO.setB(winResult.getB());
                                                    respVO.setC(winResult.getC());
                                                    respVO.setMsg("待确认领奖");
                                                    respVO.setResult(210);

                                                } else {

                                                    logger.info("结果处理错误....");
                                                    PushCoinFruitsResult noWinResult = getNoWinAssembly();
                                                    if (noWinResult != null) {

                                                        logger.info("自动生成不中奖结果....");

                                                        respVO.setA(noWinResult.getA());
                                                        respVO.setB(noWinResult.getB());
                                                        respVO.setC(noWinResult.getC());

                                                    } else {

                                                        logger.info("手动生成不中奖结果....");

                                                        respVO.setA(1);
                                                        respVO.setB(2);
                                                        respVO.setC(3);

                                                    }
                                                    respVO.setMsg("结果处理错误");
                                                    respVO.setResult(100);
                                                }

                                            }
                                        } catch (Exception e) {
                                            logger.info("手动生成不中奖结果....err=>" + e.getMessage());

                                            respVO.setA(1);
                                            respVO.setB(2);
                                            respVO.setC(3);
                                            respVO.setMsg("结果处理错误");
                                            respVO.setResult(101);

                                        }
                                        return respVO;

                                    } else {
                                        logger.info("状态错误或更新时错误");
                                    }
                                } else {
                                    logger.info("具体分配中的busId或uid不符合,无法送出奖励");
                                }
                            } else {
                                logger.info("当前中奖记录已被使用过,不可重复使用");
                            }

                            //查出中奖记录但是查不出分配记录时，可能是分配记录以及被领过了... 所以在这里需要将该中奖结果更改为已使用，防止后面卡死不中奖
                            handleAfterRecord(winId);

                        } else {
                            logger.info("获取奖励分配失败");

                        }
                    } catch (Exception e) {
                        logger.info("err=>" + e.getMessage());
                    }
                }
            }
        }

        respVO.setState(true);
        respVO.setResult(-1);

        PushCoinFruitsResult noWinResult = getNoWinAssembly();
        if (noWinResult != null) {

            logger.info("自动生成不中奖结果....");

            respVO.setA(noWinResult.getA());
            respVO.setB(noWinResult.getB());
            respVO.setC(noWinResult.getC());
            respVO.setMsg(noWinResult.getRemark());

        } else {

            logger.info("手动生成不中奖结果....");

            respVO.setA(1);
            respVO.setB(2);
            respVO.setC(3);

            respVO.setMsg("结果处理错误");
        }
        return respVO;
    }

    @Override
    public BaseRespVO affirm(VirtualFruitsAffirmReqVO reqVO) {
        logger.info("进入确认中奖处理");
        Customer customer = customerService.getCustomer(reqVO.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "商户不存在");
        }

        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null) {

            MachinePushCoinVirtual machinePushCoinVirtual = pushCoinVirtualMachineDao.getOne(reqVO.getOptId());
            if (machinePushCoinVirtual != null && machinePushCoinVirtual.getState() != 1) {

                final PushCoinFruitsAllot allot = pushCoinVirtualFruitsDao.getOneWinAllotById(reqVO.getAllotId());
                if (allot != null) {

                    if (allot.getAffirm() == 1) {
                        return new BaseRespVO(0, false, "该记录已领取过");
                    }

                    int type = allot.getType();
                    logger.info("确认中奖类型:" + PushCoinFruitsValue.FruitsValueWinType.getDescByType(type));

                    VirtualFruitsAffirmRespVO respVO = new VirtualFruitsAffirmRespVO();

                    int autoType = machinePushCoinVirtual.getAuthType();//加密类型

                    switch (type) {
                        case 0://水果机
                        case 3://保留
                        case 4://一个小丑
                        case 5://二个小丑
                        case 6://三个小丑
                            respVO = handleBaseWin(allot, dollBus.getBusId(), reqVO.getUid(), customer.getId(), reqVO.getOptId(), autoType);
                            break;
                        case 1: //点泡泡
                            respVO = handleBubble(allot, dollBus.getBusId(), reqVO.getUid(), reqVO.getOptId(), customer.getId(), reqVO.getResult(), autoType);
                            break;
                        case 2://转盘
                            respVO = handleDial(allot, dollBus.getBusId(), reqVO.getUid(), reqVO.getOptId(), customer.getId(), reqVO.getResult(), autoType);
                            break;
                        default:
                            break;
                    }

                    if (respVO.isState()) {

                        final int coin = respVO.getTemp1();

                        SysTimerHandle.execute(new TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    PushCoinFruitsWin fruitsWin = pushCoinVirtualFruitsDao.getRecordById(allot.getWinId());
                                    if (fruitsWin != null) {
                                        if (pushCoinVirtualFruitsDao.updateCurrent(fruitsWin.getId(), coin) > 0) {
                                            logger.info("总需返奖:" + fruitsWin.getTotal() + ",当前已返:" + (fruitsWin.getCurrent() + coin));
                                            handleAfterRecord(fruitsWin.getId());
                                        }
                                    }
                                } catch (Exception e) {
                                    PrintException.printException(logger, e);
                                }
                            }
                        }, 0);
                    }

                    return respVO;
                }


            }


            return new BaseRespVO(0, false, "不存在的奖励记录");
        }
        return new BaseRespVO(0, false, "机器不存在或已下架");
    }

    @Override
    public BaseRespVO verify(VirtualFruitsVerifyReqVO reqVO) {

        Customer customer = customerService.getCustomer(reqVO.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "商户不存在");
        }

        if (reqVO.getResult() == 0) {
            return new BaseRespVO(-2, false, "中奖金币为0");
        }

        BaseRespVO respVO = new BaseRespVO();
        PushCoinFruitsAllot allot = pushCoinVirtualFruitsDao.getOneWinAllotById(reqVO.getAllotId());
        if (allot != null) {
            respVO.setData(allot.getOptId());

            int coin = allot.getActual();

            if (reqVO.getResult() == 0 || coin == 0) {
                respVO.setResult(201);
                respVO.setMsg("中奖结果有效~");
                respVO.setState(true);
                return respVO;
            }

            //防止数值表0倍的情况
            if (allot.getType() == 2 && allot.getRate() == 0){
                allot.setRate(1);
            }

            //验证结果有效性
            if (((allot.getActual() <= 2000 && allot.getActual() <= (allot.getType() == 2 ? allot.getCoin() * allot.getRate() : allot.getCoin())) && reqVO.getResult() <= coin)
                    && (reqVO.getBusId() == allot.getBusId() && reqVO.getOptId() == allot.getOptId())) {

                respVO.setResult(200);
                respVO.setMsg("中奖结果有效-");
                respVO.setState(true);
                return respVO;

            } else {

                respVO.setResult(500);
                respVO.setMsg("中奖结果无效,正常中奖:" + coin + "币,非法出奖:" + reqVO.getResult() + "币");
                respVO.setState(false);
                return respVO;

            }

        }

        respVO.setResult(-3);
        respVO.setMsg("中奖记录不存在");
        respVO.setState(false);
        return respVO;
    }

    @Override
    public BaseRespVO slots(VirtualFruitsSlotsReqVO reqVO) {

        final Customer customer = customerService.getCustomer(reqVO.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "商户不存在");
        }
        if (reqVO.getWeight() == 0) {
            reqVO.setWeight(1);
        }

        if (reqVO.getCoin() <= 0) {
            return new BaseRespVO(-2, false, "拉霸币数不能小于0");
        }

        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null && dollBus.getVirtual() == 1) {

            if (dollBus.getType() == 1) {

                if (dollBus.getGroupId() != dollBus.getGroupId()) {
                    return new BaseRespVO(6, false, "当前商户没有操作改机器的权限");
                }


                PushCoinVirtualConfig pushCoinVirtualConfig = pushCoinUnity3DService.getVirtualPushCoinConfig(dollBus.getBusId());
                if (pushCoinVirtualConfig == null) {
                    return new BaseRespVO(5, false, "机器配置错误");
                }

                if (pushCoinVirtualConfig.getIsSlots() != 1) {
                    return new BaseRespVO(7, false, "当前摇杆暂不开放");
                }


                PushBusStatusVirtual pushBusStatusVirtual = pushBusStatusVirtualDao.get(reqVO.getBusId(), reqVO.getUid(), customer.getId());
                if (pushBusStatusVirtual != null) {

                    final MachinePushCoinVirtual machinePushCoinVirtual = pushCoinVirtualMachineDao.getOne(pushBusStatusVirtual.getOptId());

                    if (machinePushCoinVirtual != null) {

                        final int flag = pushCoinVirtualMachineDao.updateSlots(machinePushCoinVirtual.getOptId(), reqVO.getCoin(), reqVO.getWeight());

                        if (flag > 0) {
                            logger.info("全表操作入币成功");

                            if (pushCoinVirtualOptRouterDao.updateSlots(customer.getGroupId(), pushBusStatusVirtual.getOptId(), reqVO.getCoin()) > 0) {
                                logger.info("分表操作入币成功");

                                if (pushCoinVirtualConfigDao.updateNodeValue(reqVO.getBusId(), reqVO.getCoin()) > 0) {
                                    logger.info("总数值入币成功");

                                    final long optId = machinePushCoinVirtual.getOptId();
                                    final long uid = reqVO.getUid();
                                    final int busId = dollBus.getBusId();

                                    SysTimerHandle.execute(new TimerTask() {
                                        @Override
                                        public void run() {
                                            try {
                                                handleBeforeAllot(optId);
                                                pushCoinUnity3DService.updateEndTime(uid, optId, busId, customer);
                                            } catch (Exception e) {
                                                PrintException.printException(logger, e);
                                            }
                                        }
                                    }, 0);

                                    VirtualFruitsSlotsRespVO vo = new VirtualFruitsSlotsRespVO();
                                    vo.setOptId(optId);
                                    vo.setPlayTime(pushCoinUnity3DService.getGameTime(busId));
                                    vo.setSlots(reqVO.getCoin());

                                    return vo;

                                }

                            }
                        } else {
                            return new BaseRespVO(4, false, "投币失败,当前操作已结束!");
                        }
                    } else {
                        return new BaseRespVO(3, false, "相关记录不存在!");
                    }
                } else {
                    return new BaseRespVO(2, false, "请先上机!");
                }
            } else {
                return new BaseRespVO(0, false, "投币失败,不是推币机类型!");
            }
        }
        return new BaseRespVO(1, false, "机器不存在或类型错误!");
    }

    /**
     * 处理基本的中奖结果
     *
     * @param allot
     * @param busId
     * @param uid
     * @param optId
     * @return
     */
    private VirtualFruitsAffirmRespVO handleBaseWin(PushCoinFruitsAllot allot, int busId, long uid, int customerId, long optId, int autoType) {
        VirtualFruitsAffirmRespVO respVO = new VirtualFruitsAffirmRespVO();

        respVO.setState(false);
        respVO.setResult(0);

        respVO.setOptId(optId);


        if (allot != null) {

            //验证记录有效性
            if (allot.getBusId() == busId && (allot.getUid() == uid && allot.getCustomerId() == customerId)) {

                respVO.setUid(uid);

                logger.info("handleBaseWin 用户=>" + uid + ",busId=>" + busId + ",optId=>" + optId);

                if (pushCoinVirtualFruitsDao.updateAllotAffirmResult(allot.getId(), allot.getCoin(), 0, optId, allot.getRemark() + "(确认中奖结果)") > 0) {

                    respVO = PushCoinVirtualUtil.getAffirmResp(allot, autoType);

                    respVO.setUid(uid);
                    respVO.setOptId(optId);

                    respVO.setResult(200);
                    respVO.setState(true);

                    return respVO;

                } else {
                    respVO.setMsg("确认结果失败");
                }
            } else {
                respVO.setMsg("该中奖记录无效");
            }

            if (pushCoinVirtualFruitsDao.updateAllotAffirmResult(allot.getId(), 0, 0, optId, allot.getRemark() + "(程序处理错误结果)") > 0) {

                allot.setCoin(0);
                allot.setRate(0);

                respVO = PushCoinVirtualUtil.getAffirmResp(allot, autoType);

                respVO.setResult(100);
                respVO.setState(false);


            } else {
                respVO.setMsg("确认结果失败");
            }

        } else {
            respVO.setMsg("不存在的奖励记录");
        }

        return respVO;
    }


    /**
     * 处理点泡泡结果
     *
     * @param allot
     * @param busId
     * @param uid
     * @param optId
     * @return
     */
    private VirtualFruitsAffirmRespVO handleBubble(PushCoinFruitsAllot allot, int busId, long uid, long optId, int customerId, int result, int autoType) {
        VirtualFruitsAffirmRespVO respVO = new VirtualFruitsAffirmRespVO();

        respVO.setState(false);
        respVO.setResult(0);

        respVO.setOptId(optId);

        if (allot != null) {

            if (allot.getType() != 1) {
                respVO.setMsg("错误的中奖类型");
                return respVO;
            }


            //验证记录有效性
            if (allot.getBusId() == busId && result <= allot.getCoin() && (allot.getUid() == uid && allot.getCustomerId() == customerId)) {

                respVO.setUid(uid);

                logger.info("handleBubble 用户=>" + uid + ",busId=>" + busId + ",optId=>" + optId + ",点中金币=>" + result);

                if (pushCoinVirtualFruitsDao.updateAllotAffirmResult(allot.getId(), result, 0, optId, allot.getRemark() + "(确认中奖结果,玩家点泡泡得:" + result + "币)") > 0) {

                    allot.setCoin(result);
                    respVO = PushCoinVirtualUtil.getAffirmResp(allot, autoType);

                    respVO.setUid(uid);
                    respVO.setOptId(optId);

                    respVO.setResult(200);
                    respVO.setState(true);

                } else {
                    respVO.setMsg("确认结果失败");
                }
            } else {
                respVO.setMsg("该中奖记录无效");
            }

            if (pushCoinVirtualFruitsDao.updateAllotAffirmResult(allot.getId(), 0, 0, optId, allot.getRemark() + "(程序处理错误结果)") > 0) {

                //allot.setCoin(0);
                //allot.setRate(0);

                //respVO = PushCoinVirtualUtil.getAffirmResp(allot, autoType);

                respVO.setResult(100);
                respVO.setState(false);

            } else {
                respVO.setMsg("确认结果失败");
            }

        } else {
            respVO.setMsg("不存在的奖励记录");
        }

        return respVO;
    }


    /**
     * 处理摇奖结果
     *
     * @param allot
     * @param busId
     * @param uid
     * @param optId
     * @param result
     * @return
     */
    private VirtualFruitsAffirmRespVO handleDial(PushCoinFruitsAllot allot, int busId, long uid, long optId, int customerId, int result, int autoType) {
        VirtualFruitsAffirmRespVO respVO = new VirtualFruitsAffirmRespVO();
        if (allot != null) {
            // type=2 转盘类型
            if (allot.getType() != 2) {
                respVO.setState(false);
                respVO.setResult(0);
                respVO.setMsg("游戏类型错误");
                return respVO;
            }

            //验证记录有效性
            if (allot.getBusId() == busId && (allot.getUid() == uid && allot.getCustomerId() == customerId)) {

                logger.info("handleDial 用户=>" + uid + ",busId=>" + busId + ",optId=>" + optId);

                respVO.setUid(allot.getUid());
                respVO.setOptId(allot.getOptId());
                try {

                    if (allot.getCoin() == 0) {

                        allot.setRate(0);
                        allot.setCoin(0);

                        respVO = PushCoinVirtualUtil.getAffirmResp(allot, autoType);
                        respVO.setUid(allot.getUid());
                        respVO.setOptId(allot.getOptId());

                        respVO.setMsg("好可惜,差一点点就成功了!");

                        if (pushCoinVirtualFruitsDao.updateAllotAffirmResult(allot.getId(), allot.getCoin(), 0, optId, allot.getRemark() + "(数值错误)") > 0) {
                            logger.info("数值错误");
                            return respVO;
                        }

                    }

                    respVO.setResult(200);
                    respVO.setState(true);

                    if (result == 0) {

                        //点怂默认1倍
                        allot.setRate(1);

                        respVO = PushCoinVirtualUtil.getAffirmResp(allot, autoType);
                        respVO.setUid(allot.getUid());
                        respVO.setOptId(allot.getOptId());

                        respVO.setMsg("玩家认怂!");

                        if (pushCoinVirtualFruitsDao.updateAllotAffirmResult(allot.getId(), allot.getCoin(), 1, optId, allot.getRemark() + "(玩家选择怂,默认获得:" + allot.getCoin() + "币)") > 0) {
                            logger.info("更新摇奖结果成功,玩家选择怂,默认获得:" + allot.getCoin() + "币");
                            return respVO;
                        }

                    } else if (result == 1) {

                        int random_result = PushCoinVirtualUtil.getRandomDialValue();

                        logger.info("摇奖结果:" + random_result);

                        if (random_result == 1) {

                            allot.setRate(random_result);

                            respVO = PushCoinVirtualUtil.getAffirmResp(allot, autoType);
                            respVO.setUid(allot.getUid());
                            respVO.setOptId(allot.getOptId());

                            respVO.setMsg("我想吃章鱼!");

                            if (pushCoinVirtualFruitsDao.updateAllotAffirmResult(allot.getId(), allot.getCoin(), random_result, optId, allot.getRemark() + "(玩家选择转,倍率:" + random_result + "倍,获得:" + allot.getCoin() + "币)") > 0) {
                                logger.info("更新摇奖结果成功,玩家选择转,倍率:" + random_result + "倍,获得:" + allot.getCoin() + "币");
                            }

                        } else if (random_result == 3 || random_result == 5) {

                            //中奖设定倍率
                            int winRate;

                            if (allot.getRate() == 0) {
                                winRate = 1;
                            } else {
                                winRate = allot.getRate();
                            }

                            //allot.setRate(random_result >= winRate ? winRate : random_result);

                            allot.setRate(winRate);

                            int dialCoin = allot.getCoin() * allot.getRate();

                            allot.setCoin(dialCoin);

                            respVO = PushCoinVirtualUtil.getAffirmResp(allot, autoType);
                            respVO.setUid(allot.getUid());
                            respVO.setOptId(allot.getOptId());


                            //logger.info("实际随机倍率:" + random_result + ",数值表倍率:" + winRate + ",应获得:" + dialCoin + "币");

                            logger.info("数值表倍率:" + winRate + ",应获得:" + dialCoin + "币");

                            if (allot.getRate() == 1) {
                                respVO.setMsg("我想吃章鱼!");

                            } else if (allot.getRate() == 3) {
                                respVO.setMsg("吃串烧丸子也不错哦!");

                            } else {
                                respVO.setMsg("大吉大利今晚吃章鱼!");
                            }

                            if (pushCoinVirtualFruitsDao.updateAllotAffirmResult(allot.getId(), dialCoin, allot.getRate(), optId, allot.getRemark() + "(随机倍率:" + random_result + ",数值表倍率:" + allot.getRate() + ",按数值表倍率玩家获得:" + dialCoin + "币)") > 0) {
                                logger.info("更新摇奖结果成功,玩家选择转,倍率:" + winRate + "倍,获得:" + dialCoin + "币");
                            }

                        } else {

                            allot.setRate(random_result);
                            allot.setCoin(0);

                            respVO = PushCoinVirtualUtil.getAffirmResp(allot, autoType);

                            respVO.setUid(allot.getUid());
                            respVO.setOptId(allot.getOptId());

                            respVO.setMsg("好可惜,差一点点就成功了!");

                            if (pushCoinVirtualFruitsDao.updateAllotAffirmResult(allot.getId(), 0, random_result, optId, allot.getRemark() + "(随机转到" + random_result + "倍)") > 0) {
                                logger.info("更新摇奖结果成功,玩家选择转,倍率:" + random_result + "倍,获得:" + random_result + "币");
                            }

                        }

                        return respVO;

                    } else {
                        respVO.setMsg("请选择怂或转!");
                    }

                } catch (Exception e) {
                    respVO.setMsg("摇奖错误!");
                }

            } else {
                respVO.setMsg("该记录无效!");
            }

            if (pushCoinVirtualFruitsDao.updateAllotAffirmResult(allot.getId(), 0, 0, optId, allot.getRemark() + "(程序处理错误结果)") > 0) {
                logger.info("程序处理错误结果");
            }


        } else {
            respVO.setMsg("不存在的奖励记录");
        }

        respVO.setState(false);
        respVO.setResult(0);
        return respVO;
    }


    @Override
    public void initFruitCore() {
        if (fruitResultMap == null || fruitResultMap.isEmpty()) {
            initFruitResultToMap();
        }

        if (fruitsCoreValueMap == null || fruitsCoreValueMap.isEmpty()) {
            initFruitsCoreValueToMap();
        }

        if (winTypeToResultTypeMap == null || winTypeToResultTypeMap.isEmpty()) {
            winTypeMapperToResultType();
        }
    }

    @Override
    public void handleBeforeAllot(long optId) {

        this.initFruitCore();

        MachinePushCoinVirtual pushCoinVirtual = pushCoinVirtualMachineDao.getOne(optId);

        if (pushCoinVirtual != null) {

            PushCoinVirtualConfig config = pushCoinUnity3DService.getVirtualPushCoinConfig(pushCoinVirtual.getBusId());

            if (config != null) {

                if (pushCoinVirtual.getState() != 1) {

                    int nodeCoin = config.getNodeCoin();
                    int fvId = config.getFvId();

                    try {

                        PushCoinFruitsValue nodeValue;

                        boolean isClear = false;

                        if (fvId >= fruitsCoreValueMap.size()) {
                            if (pushCoinVirtualConfigDao.resetNode(config.getBusId()) > 0) {
                                fvId = 0;
                                logger.info("操作ID节点：" + fvId + ",已到达最终数值:" + fruitsCoreValueMap.size() + ",执行重置节点操作");
                            }
                        }


                        if (fvId == 0) {
                            //判断是否达到第一个节点
                            nodeValue = fruitsCoreValueMap.get(1);
                            if (nodeValue != null) {
                                if (nodeCoin >= nodeValue.getNode()) {
                                    //清空当前节点值,节点ID+1
                                    int flag = pushCoinVirtualConfigDao.updateNextNode(nodeValue.getNode(), config.getBusId());
                                    if (flag > 0) {
                                        isClear = true;
                                    }
                                }
                            }

                        } else {
                            nodeValue = fruitsCoreValueMap.get(fvId + 1);
                            if (nodeValue != null) {
                                if (nodeCoin >= nodeValue.getNode()) {
                                    int flag = pushCoinVirtualConfigDao.updateNextNode(nodeValue.getNode(), config.getBusId());
                                    if (flag > 0) {
                                        isClear = true;
                                    }
                                }
                            }
                        }

                        if (nodeValue != null) {
                            logger.info("当前节点数值ID：" + fvId + ",下一节点应投币数:" + nodeValue.getNode() + ",当前已投:" + nodeCoin);
                        }

                        if (isClear) {

                            fvId++;
                            logger.info("变更之后数值ID：" + fvId);

                            int type = 0;

                            if (nodeValue.getClown1() > 0) {
                                type = 1;
                            }
                            if (nodeValue.getClown2() > 0) {
                                type = 2;
                            }
                            if (nodeValue.getClown3() > 0) {
                                type = 3;
                            }

                            PushCoinFruitsWin fruitsWin = new PushCoinFruitsWin();

                            //数值属性
                            fruitsWin.setFvId(fvId);//数值id
                            fruitsWin.setNode(nodeValue.getNode());//节点值
                            fruitsWin.setTotal(nodeValue.getCoin());//中奖金币
                            fruitsWin.setMagnetism(nodeValue.getMagnetism());//中奖磁力

                            //玩家属性
                            fruitsWin.setCustomerId(pushCoinVirtual.getCustomerId());
                            fruitsWin.setUid(pushCoinVirtual.getUid());
                            fruitsWin.setBusId(pushCoinVirtual.getBusId());


                            //待更新属性
                            fruitsWin.setType(type);
                            fruitsWin.setResult(0);
                            fruitsWin.setCurrent(0);
                            fruitsWin.setOptId(0);
                            fruitsWin.setNodeJson(JSON.toJSONString(nodeValue));
                            fruitsWin.setRemark("节点ID:" + fvId + ",奖励:" + nodeValue.getCoin() + "币");


                            int winId = pushCoinVirtualFruitsDao.addWinRecord(fruitsWin);

                            if (winId > 0) {

                                logger.info("新增节点中奖记录,开始执行具体分配奖励");

                                PushCoinFruitsAllot allot = new PushCoinFruitsAllot();
                                allot.setWinId(winId);//中奖ID

                                allot.setCustomerId(pushCoinVirtual.getCustomerId());
                                allot.setBusId(pushCoinVirtual.getBusId());
                                allot.setUid(pushCoinVirtual.getUid());
                                allot.setOptId(pushCoinVirtual.getOptId());

                                allot.setFvId(fvId);//每次都加1
                                allot.setMagnetism(nodeValue.getMagnetism());//磁力根据节点数值
                                allot.setResult(0);
                                allot.setAffirm(0);

                                //拿到权重列表,后续只需从中随机抽取即可
                                List<MachinePushCoinVirtual> weightMaxList = this.getWeightMax(pushCoinVirtual.getBusId());

                                //水果机 两个水果
                                if (!StringUtil.isNullOrEmpty(nodeValue.getFruits())) {
                                    List<Integer> fruitsList = JSON.parseArray(nodeValue.getFruits(), Integer.TYPE);

                                    logger.info("分配水果奖=>" + JSON.toJSONString(fruitsList));

                                    for (int fruit : fruitsList) {

                                        if (weightMaxList != null && !weightMaxList.isEmpty()) {
                                            Collections.shuffle(weightMaxList);
                                            pushCoinVirtual = weightMaxList.get(0);
                                            logger.info("权重随机后该奖分配给玩家:" + pushCoinVirtual.getUid() + ",操作ID:" + pushCoinVirtual.getOptId());
                                        }

                                        allot.setCustomerId(pushCoinVirtual.getCustomerId());
                                        allot.setBusId(pushCoinVirtual.getBusId());
                                        allot.setUid(pushCoinVirtual.getUid());
                                        allot.setOptId(pushCoinVirtual.getOptId());


                                        allot.setType(PushCoinFruitsValue.FruitsValueWinType.ERNIE_TYPE.type);//水果机类型
                                        allot.setCoin(fruit);


                                        int fruitType = PushCoinFruitsResult.FruitsResultType.WIN_EQ_TWO_FRUIT.type; //水果机 排列结果
                                        List<PushCoinFruitsResult> resultList = fruitResultMap.get(fruitType);
                                        if (!StringUtil.isNullOrEmpty(resultList)) {
                                            Collections.shuffle(resultList);
                                            allot.setAssembly(resultList.get(0).getAssembly());
                                        }

                                        allot.setRemark("水果机出奖:" + fruit + "币");

                                        if (pushCoinVirtualFruitsDao.addWinAllot(allot) > 0) {
                                            logger.info("新增具体水果机奖励");
                                        }
                                    }

                                }

                                //泡泡奖 二水果+一丑
                                if (nodeValue.getPop() > 0) {

                                    if (weightMaxList != null && !weightMaxList.isEmpty()) {
                                        Collections.shuffle(weightMaxList);
                                        pushCoinVirtual = weightMaxList.get(0);
                                        logger.info("权重随机后该奖分配给玩家:" + pushCoinVirtual.getUid() + ",操作ID:" + pushCoinVirtual.getOptId());
                                    }

                                    allot.setCustomerId(pushCoinVirtual.getCustomerId());
                                    allot.setBusId(pushCoinVirtual.getBusId());
                                    allot.setUid(pushCoinVirtual.getUid());
                                    allot.setOptId(pushCoinVirtual.getOptId());

                                    allot.setType(PushCoinFruitsValue.FruitsValueWinType.BUBBLE_TYPE.type);//点泡泡游戏类型
                                    allot.setCoin(nodeValue.getPop());

                                    int popType = PushCoinFruitsResult.FruitsResultType.WIN_HAT.type;  //帽子戏法(点泡泡) 排列结果
                                    List<PushCoinFruitsResult> resultList = fruitResultMap.get(popType);
                                    if (!StringUtil.isNullOrEmpty(resultList)) {
                                        Collections.shuffle(resultList);
                                        allot.setAssembly(resultList.get(0).getAssembly());
                                    }

                                    allot.setRemark("点泡泡出奖:" + nodeValue.getPop() + "币");

                                    if (pushCoinVirtualFruitsDao.addWinAllot(allot) > 0) {
                                        logger.info("新增具体点泡泡奖励");
                                    }

                                }

                                //转盘游戏 三水果（小游戏）
                                if (nodeValue.getDial() > 0) {

                                    if (weightMaxList != null && !weightMaxList.isEmpty()) {
                                        Collections.shuffle(weightMaxList);
                                        pushCoinVirtual = weightMaxList.get(0);
                                        logger.info("权重随机后该奖分配给玩家:" + pushCoinVirtual.getUid() + ",操作ID:" + pushCoinVirtual.getOptId());
                                    }

                                    allot.setCustomerId(pushCoinVirtual.getCustomerId());
                                    allot.setBusId(pushCoinVirtual.getBusId());
                                    allot.setUid(pushCoinVirtual.getUid());
                                    allot.setOptId(pushCoinVirtual.getOptId());

                                    allot.setType(PushCoinFruitsValue.FruitsValueWinType.DIAL_TYPE.type);//转盘游戏类型
                                    allot.setCoin(nodeValue.getDial());

                                    int dialType = PushCoinFruitsResult.FruitsResultType.WIN_EQ_THREE_FRUIT.type;  //相同3个水果(转盘游戏) 排列结果
                                    List<PushCoinFruitsResult> resultList = fruitResultMap.get(dialType);
                                    if (!StringUtil.isNullOrEmpty(resultList)) {
                                        Collections.shuffle(resultList);
                                        allot.setAssembly(resultList.get(0).getAssembly());
                                    }

                                    //转盘倍率
                                    allot.setRate(nodeValue.getRate() == 0 ? 1 : nodeValue.getRate());

                                    allot.setRemark("转盘游戏出奖:" + nodeValue.getDial() + "币");

                                    if (pushCoinVirtualFruitsDao.addWinAllot(allot) > 0) {
                                        logger.info("新增具体转盘游戏奖励");
                                    }

                                }


                                //一丑
                                if (nodeValue.getClown1() > 0) {

                                    if (weightMaxList != null && !weightMaxList.isEmpty()) {
                                        Collections.shuffle(weightMaxList);
                                        pushCoinVirtual = weightMaxList.get(0);
                                        logger.info("权重随机后该奖分配给玩家:" + pushCoinVirtual.getUid() + ",操作ID:" + pushCoinVirtual.getOptId());
                                    }

                                    allot.setCustomerId(pushCoinVirtual.getCustomerId());
                                    allot.setBusId(pushCoinVirtual.getBusId());
                                    allot.setUid(pushCoinVirtual.getUid());
                                    allot.setOptId(pushCoinVirtual.getOptId());

                                    allot.setType(PushCoinFruitsValue.FruitsValueWinType.ONE_CLOWN_TYPE.type);//一丑类型
                                    allot.setCoin(nodeValue.getClown1());

                                    int clown1Type = PushCoinFruitsResult.FruitsResultType.WIN_ONE_CLOWN.type;  //一丑排列结果
                                    List<PushCoinFruitsResult> resultList = fruitResultMap.get(clown1Type);
                                    if (!StringUtil.isNullOrEmpty(resultList)) {
                                        Collections.shuffle(resultList);
                                        allot.setAssembly(resultList.get(0).getAssembly());
                                    }
                                    allot.setRemark("一丑出奖:" + nodeValue.getClown1() + "币");

                                    if (pushCoinVirtualFruitsDao.addWinAllot(allot) > 0) {
                                        logger.info("新增具体一丑出奖奖励");
                                    }
                                }

                                //二丑
                                if (nodeValue.getClown2() > 0) {

                                    if (weightMaxList != null && !weightMaxList.isEmpty()) {
                                        Collections.shuffle(weightMaxList);
                                        pushCoinVirtual = weightMaxList.get(0);
                                        logger.info("权重随机后该奖分配给玩家:" + pushCoinVirtual.getUid() + ",操作ID:" + pushCoinVirtual.getOptId());
                                    }

                                    allot.setCustomerId(pushCoinVirtual.getCustomerId());
                                    allot.setBusId(pushCoinVirtual.getBusId());
                                    allot.setUid(pushCoinVirtual.getUid());
                                    allot.setOptId(pushCoinVirtual.getOptId());

                                    allot.setType(PushCoinFruitsValue.FruitsValueWinType.TWO_CLOWN_TYPE.type);//二丑类型
                                    allot.setCoin(nodeValue.getClown2());

                                    int clown2Type = PushCoinFruitsResult.FruitsResultType.WIN_TWO_CLOWN.type;  //二丑排列结果
                                    List<PushCoinFruitsResult> resultList = fruitResultMap.get(clown2Type);
                                    if (!StringUtil.isNullOrEmpty(resultList)) {
                                        Collections.shuffle(resultList);
                                        allot.setAssembly(resultList.get(0).getAssembly());
                                    }
                                    allot.setRemark("二丑出奖:" + nodeValue.getClown2() + "币");

                                    if (pushCoinVirtualFruitsDao.addWinAllot(allot) > 0) {
                                        logger.info("新增具体二丑出奖奖励");
                                    }
                                }


                                //三丑 彩金

                                if (nodeValue.getClown3() > 0) {

                                    if (weightMaxList != null && !weightMaxList.isEmpty()) {
                                        Collections.shuffle(weightMaxList);
                                        pushCoinVirtual = weightMaxList.get(0);
                                        logger.info("权重随机后该奖分配给玩家:" + pushCoinVirtual.getUid() + ",操作ID:" + pushCoinVirtual.getOptId());
                                    }

                                    allot.setCustomerId(pushCoinVirtual.getCustomerId());
                                    allot.setBusId(pushCoinVirtual.getBusId());
                                    allot.setUid(pushCoinVirtual.getUid());
                                    allot.setOptId(pushCoinVirtual.getOptId());

                                    allot.setType(PushCoinFruitsValue.FruitsValueWinType.THREE_CLOWN_TYPE.type);//三丑类型
                                    allot.setCoin(nodeValue.getClown3());

                                    int clown3Type = PushCoinFruitsResult.FruitsResultType.WIN_THREE_CLOWN.type;  //三丑排列结果
                                    List<PushCoinFruitsResult> resultList = fruitResultMap.get(clown3Type);
                                    if (!StringUtil.isNullOrEmpty(resultList)) {
                                        Collections.shuffle(resultList);
                                        allot.setAssembly(resultList.get(0).getAssembly());
                                    }
                                    allot.setRemark("三丑出奖:" + nodeValue.getClown3() + "币");

                                    if (pushCoinVirtualFruitsDao.addWinAllot(allot) > 0) {
                                        logger.info("新增具体三丑出奖奖励");
                                    }
                                }

                            }

                        }

                    } catch (Exception e) {
                        logger.info("handleBeforeAllot err=>" + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * 处理后续中奖记录
     */
    private void handleAfterRecord(int winId) {
        PushCoinFruitsWin pushCoinFruitsWin = pushCoinVirtualFruitsDao.getRecordById(winId);
        if (pushCoinFruitsWin != null && pushCoinFruitsWin.getResult() != 1) {
            List<PushCoinFruitsAllot> winList = pushCoinVirtualFruitsDao.getNoAllotByWinId(winId);
            if (winList == null) {
                logger.info("奖项分配完毕,处理完成该奖的所有结果");
                if (pushCoinVirtualFruitsDao.completeAssigned(winId, pushCoinFruitsWin.getRemark() + "(完成所有分配奖励)") > 0) {
                    logger.info("中奖记录完成所有的分配");
                }
            } else {
                logger.info("未分配的奖项还剩:" + winList.size() + "条");
            }
        }

        PushCoinFruitsWin fruitsWin = pushCoinVirtualFruitsDao.getRecordById(winId);
        if (fruitsWin != null && fruitsWin.getResult() != 1) {
            if (fruitsWin.getCurrent() >= fruitsWin.getTotal()) {
                if (pushCoinVirtualFruitsDao.completeAssigned(winId, fruitsWin.getRemark() + "(完成所有分配奖励)") > 0) {
                    logger.info("中奖记录完成所有的分配");
                } else {
                    if (fruitsWin.getResult() == 1) {
                        logger.info("中奖记录完成所有的分配");
                    }
                }
            }
        }
    }


    /**
     * 初始化水果机结果到内存(内存使用并发map,避免出现并发)
     */
    private void initFruitResultToMap() {
        for (PushCoinFruitsResult.FruitsResultType resultType : PushCoinFruitsResult.FruitsResultType.values()) {
            fruitResultMap.put(resultType.type, pushCoinVirtualFruitsDao.getResultByType(resultType.type));
        }
        if (!StringUtil.isNullOrEmpty(fruitResultMap)) {
            logger.info("初始化水果机结果");
        }
    }

    /**
     * 初始化水果机核心数值到内存(内存使用并发map,避免出现并发)
     */
    private void initFruitsCoreValueToMap() {
        if (fruitsCoreValueMap != null) {
            if (!fruitsCoreValueMap.isEmpty()) {
                fruitsCoreValueMap.clear();
            }
        }
        List<PushCoinFruitsValue> valueList = pushCoinVirtualFruitsDao.getAllFruitsValue();
        if (!StringUtil.isNullOrEmpty(valueList)) {
            for (PushCoinFruitsValue value : valueList) {
                fruitsCoreValueMap.put(value.getId(), value);
            }
            logger.info("初始化水果机数值");
        }
    }

    /**
     * 随机获得一个不中奖的组合
     *
     * @return
     */
    private PushCoinFruitsResult getNoWinAssembly() {
        if (!StringUtil.isNullOrEmpty(fruitResultMap)) {
            List<PushCoinFruitsResult> resultList = fruitResultMap.get(PushCoinFruitsResult.FruitsResultType.NOT_WIN_FRUIT.type);
            if (!StringUtil.isNullOrEmpty(resultList)) {
                Collections.shuffle(resultList);
                return resultList.get(0);
            }
        }
        return null;
    }

    /**
     * 通过类型随机获得一个中奖的组合
     *
     * @param type
     * @return
     */
    private PushCoinFruitsResult getWinAssemblyByType(int type) {
        if (!StringUtil.isNullOrEmpty(fruitResultMap)) {
            List<PushCoinFruitsResult> resultList = fruitResultMap.get(type);
            if (!StringUtil.isNullOrEmpty(resultList)) {
                Collections.shuffle(resultList);
                return resultList.get(0);
            }
        }
        return null;
    }


    /**
     * 中奖类型与结果类型映射
     */
    private void winTypeMapperToResultType() {
        for (PushCoinFruitsValue.FruitsValueWinType winType : PushCoinFruitsValue.FruitsValueWinType.values()) {

            //水果机 两个水果
            if (winType.type == PushCoinFruitsValue.FruitsValueWinType.ERNIE_TYPE.type) {
                winTypeToResultTypeMap.put(winType.type, PushCoinFruitsResult.FruitsResultType.WIN_EQ_TWO_FRUIT.type);
            }

            //泡泡奖 二水果+一丑
            if (winType.type == PushCoinFruitsValue.FruitsValueWinType.BUBBLE_TYPE.type) {
                winTypeToResultTypeMap.put(winType.type, PushCoinFruitsResult.FruitsResultType.WIN_HAT.type);
            }

            //转盘游戏 三水果（小游戏）
            if (winType.type == PushCoinFruitsValue.FruitsValueWinType.DIAL_TYPE.type) {
                winTypeToResultTypeMap.put(winType.type, PushCoinFruitsResult.FruitsResultType.WIN_EQ_THREE_FRUIT.type);
            }

            //1丑
            if (winType.type == PushCoinFruitsValue.FruitsValueWinType.ONE_CLOWN_TYPE.type) {
                winTypeToResultTypeMap.put(winType.type, PushCoinFruitsResult.FruitsResultType.WIN_ONE_CLOWN.type);
            }

            //2丑
            if (winType.type == PushCoinFruitsValue.FruitsValueWinType.TWO_CLOWN_TYPE.type) {
                winTypeToResultTypeMap.put(winType.type, PushCoinFruitsResult.FruitsResultType.WIN_TWO_CLOWN.type);
            }

            //3丑
            if (winType.type == PushCoinFruitsValue.FruitsValueWinType.THREE_CLOWN_TYPE.type) {
                winTypeToResultTypeMap.put(winType.type, PushCoinFruitsResult.FruitsResultType.WIN_THREE_CLOWN.type);
            }
        }
        logger.info("初始化中奖类型映射到结果类型");
    }


    /**
     * 初始化所有水果出现的结果
     */
    private void initResult() {
        int total = 0;
        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 7; j++) {
                for (int k = 1; k <= 7; k++) {
                    total++;
                    PushCoinFruitsResult result = new PushCoinFruitsResult();

                    result.setA(i);
                    result.setB(j);
                    result.setC(k);
                    result.setAssembly("[" + i + "," + j + "," + k + "]");

                    //1:不中奖水果,2:相同2个水果,3:相同3个水果,4:帽子戏法,5:中1丑,6:中2丑,7:中3丑

                    //不中奖的结果
                    if (i != 7 && i != j) {
                        result.setType(1);
                        result.setRemark("不中奖结果");
                        pushCoinVirtualFruitsDao.addResult(result);
                    }
                    //二个相同的
                    if (i == j && k != 7 && i != k && i != 7) {
                        result.setType(2);
                        result.setRemark("相同2个水果");
                        pushCoinVirtualFruitsDao.addResult(result);
                    }
                    //三个相同的
                    if (i == j && i == k && i != 7) {
                        result.setType(3);
                        result.setRemark("相同3个水果");
                        pushCoinVirtualFruitsDao.addResult(result);
                    }
                    if (i == j && i != k && k == 7) {
                        result.setType(4);
                        result.setRemark("帽子戏法");
                        pushCoinVirtualFruitsDao.addResult(result);
                    }
                    //一丑
                    if ((i == 7 && j != 7)) {
                        result.setType(5);
                        result.setRemark("中1丑");
                        pushCoinVirtualFruitsDao.addResult(result);
                    }
                    //二丑
                    if (i == 7 && j == 7 && k != 7) {
                        result.setType(6);
                        result.setRemark("中2丑");
                        pushCoinVirtualFruitsDao.addResult(result);
                    }
                    //三丑
                    if (i == 7 && j == 7 && k == 7) {
                        result.setType(7);
                        result.setRemark("中3丑");
                        pushCoinVirtualFruitsDao.addResult(result);
                    }
                }
            }
        }
        logger.info("initFruitResult 生成=>" + total + " 种结果");
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            logger.info("==================onApplicationEvent initFruitCore==================");
            this.initFruitCore();
        }
    }


    /**
     * 通过权重获取一条操作记录
     *
     * @return
     */
    private List<MachinePushCoinVirtual> getWeightMax(int busId) {
        List<MachinePushCoinVirtual> pushCoinVirtualList = pushCoinVirtualMachineDao.getOptRecordByBusId(busId);

        if (!StringUtil.isNullOrEmpty(pushCoinVirtualList)) {

            if (pushCoinVirtualList.size() == 1) {
                return pushCoinVirtualList;
            }

            List<MachinePushCoinVirtual> tempList = new LinkedList<>();
            for (MachinePushCoinVirtual pushBusStatusVirtual : pushCoinVirtualList) {

                if (pushBusStatusVirtual.getState() != 1) {

                    //30秒无任何活动的默认不分奖
                    if (DateUtils.secondBetween(pushBusStatusVirtual.getEndTime()) < 30) {
                        if (pushBusStatusVirtual.getWeight() != 0) {
                            for (int i = 0; i < pushBusStatusVirtual.getWeight(); i++) {
                                tempList.add(pushBusStatusVirtual);
                            }
                        }
                    }

                }

            }

            if (!StringUtil.isNullOrEmpty(tempList)) {
                Collections.shuffle(tempList);
                return tempList;
            }
        }
        return null;
    }

}