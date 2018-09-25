package com.imlianai.dollpub.app.modules.core.coinfactory.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.imlianai.dollpub.app.modules.core.coinfactory.dao.*;
import com.imlianai.dollpub.app.modules.core.coinfactory.service.PushCoinFruitService;
import com.imlianai.dollpub.app.modules.core.coinfactory.util.PushCoinVirtualUtil;
import com.imlianai.dollpub.app.modules.core.coinfactory.service.PushCoinUnity3DService;
import com.imlianai.dollpub.app.modules.core.coinfactory.vo.ApplyVirtualRespVO;
import com.imlianai.dollpub.app.modules.core.coinfactory.vo.EntryVirtualRoomReqVO;
import com.imlianai.dollpub.app.modules.core.coinfactory.vo.OperateRespVO;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.domain.coinfactory.*;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.MachinePushCoinVirtual;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.PushBusStatusVirtual;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.PushCoinVirtualConfig;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.VirtualPushCoinRoom;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.rpc.support.utils.SysTimerHandle;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * @author wurui
 * @create 2018-06-30 11:07
 **/
@Service
public class PushCoinUnity3DServiceImpl implements PushCoinUnity3DService {

    private BaseLogger logger = BaseLogger.getLogger(getClass());


    @Resource
    private DollBusService dollBusService;

    //全表
    @Resource
    private PushCoinVirtualMachineDao pushCoinVirtualMachineDao;

    //带分表
    @Resource
    private PushCoinVirtualOptRouterDao pushCoinVirtualOptRouterDao;

    @Resource
    private PushCoinVirtualRoomDao pushCoinVirtualRoomDao;

    @Resource
    private CustomerService customerService;

    @Resource
    private PushBusStatusVirtualDao pushBusStatusVirtualDao;

    @Resource
    private PushCoinCallbackDao pushCoinCallbackDao;


    @Resource
    private PushCoinVirtualConfigDao pushCoinVirtualConfigDao;

    @Resource
    private PushCoinFruitService pushCoinFruitService;


    @Override
    public BaseRespVO apply(long uid, int busId, Customer customer,int weight) {

        logger.info("虚拟机器上机,机器ID=>" + busId + ",上机用户=>" + uid + ",商户=>" + JSON.toJSONString(customer));

        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null && dollBus.getVirtual() == 1) {
            if (dollBus.getType() == 1) {

                if (dollBus.getGroupId() != dollBus.getGroupId()) {
                    return new BaseRespVO(8, false, "当前商户没有操作该机器的权限");
                }

                PushBusStatusVirtual pushBusStatusVirtual = pushBusStatusVirtualDao.get(dollBus.getBusId(), uid, customer.getId());
                if (pushBusStatusVirtual != null) {
                    BaseRespVO respVO = new BaseRespVO(6, false, "请勿重复上机!");
                    respVO.setData(pushBusStatusVirtual.getOptId());
                    return respVO;
                }

                VirtualPushCoinRoom virtualPushCoinRoom = pushCoinVirtualRoomDao.getWatchBus(customer.getId(), uid);
                if (virtualPushCoinRoom != null) {
                    if (virtualPushCoinRoom.getBusId() == dollBus.getBusId()) {
                        logger.info("条件认证成功,开始上机操作 uid=>" + uid + ",busId=>" + busId);
                        PushCoinVirtualConfig config = this.getVirtualConfig(dollBus.getBusId());
                        if (config != null) {
                            MachinePushCoinVirtual machinePushCoinVirtual = new MachinePushCoinVirtual();
                            machinePushCoinVirtual.setBusId(dollBus.getBusId());
                            machinePushCoinVirtual.setCustomerId(customer.getId());
                            machinePushCoinVirtual.setDeviceId(dollBus.getDeviceId());
                            machinePushCoinVirtual.setUid(uid);
                            machinePushCoinVirtual.setAuthType(virtualPushCoinRoom.getAuthType());

                            machinePushCoinVirtual.setWeight(weight);

                            long optId = pushCoinVirtualMachineDao.insert(machinePushCoinVirtual);
                            if (optId > 0) {
                                logger.info("新增全表操作记录成功");
                                machinePushCoinVirtual.setOptId(optId);
                                if (pushCoinVirtualOptRouterDao.insert(customer.getGroupId(), machinePushCoinVirtual) > 0) {
                                    logger.info("新增推币机分表操作流水=>" + JSON.toJSONString(machinePushCoinVirtual));
                                    int playTime = config.getPlayTime();
                                    //状态
                                    if (pushBusStatusVirtualDao.insert(new PushBusStatusVirtual(dollBus.getBusId(), dollBus.getDeviceId(), optId, uid, customer.getId()), playTime) > 0) {
                                        logger.info("新增上机操作状态,游戏时间=>" + playTime + "秒");
                                        ApplyVirtualRespVO vo = new ApplyVirtualRespVO();
                                        vo.setMsg("上机成功!");
                                        vo.setResult(200);
                                        vo.setState(true);
                                        vo.setOptId(optId);

                                        //添加额外的属性
                                        vo.setPlayTime(playTime);
                                        vo.setLean(config.getLean());
                                        vo.setMagnetism(config.getSuction());

                                        return vo;
                                    }
                                }
                            }
                        } else {
                            return new BaseRespVO(7, false, "拉取机器配置失败!");
                        }
                    } else {
                        return new BaseRespVO(4, false, "房间号错误!");
                    }
                } else {
                    return new BaseRespVO(3, false, "请先进入房间!");
                }
            } else {
                return new BaseRespVO(2, false, "上机失败,当前机器不是推币机");
            }
        } else {
            return new BaseRespVO(1, false, "上机失败,当前机器不是虚拟机");
        }
        return new BaseRespVO(5, false, "上机操作失败!");
    }

    @Override
    public BaseRespVO putCoin(final long uid, final int busId, final Customer customer,int weight) {
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null && dollBus.getVirtual() == 1) {
            if (dollBus.getType() == 1) {

                if (dollBus.getGroupId() != dollBus.getGroupId()) {
                    return new BaseRespVO(6, false, "当前商户没有操作改机器的权限");
                }

                PushBusStatusVirtual pushBusStatusVirtual = pushBusStatusVirtualDao.get(busId, uid, customer.getId());
                if (pushBusStatusVirtual != null) {

                    final MachinePushCoinVirtual machinePushCoinVirtual = pushCoinVirtualMachineDao.getOne(pushBusStatusVirtual.getOptId());

                    if (machinePushCoinVirtual != null) {

                        final int flag = pushCoinVirtualMachineDao.updateCoin(pushBusStatusVirtual.getOptId(), 1, 0,weight);

                        if (flag > 0) {
                            logger.info("全表操作入币成功");

                            if (pushCoinVirtualOptRouterDao.updateCoin(customer.getGroupId(), pushBusStatusVirtual.getOptId(), 1, 0) > 0) {
                                logger.info("分表操作入币成功");

                                if (pushCoinVirtualConfigDao.updateNodeValue(busId, 1) > 0) {
                                    logger.info("总数值入币成功");

                                    final long optId = machinePushCoinVirtual.getOptId();

                                    SysTimerHandle.execute(new TimerTask() {
                                        @Override
                                        public void run() {
                                            try {
                                                pushCoinFruitService.handleBeforeAllot(optId);
                                                updateEndTime(uid, optId, busId, customer);
                                            } catch (Exception e) {
                                                PrintException.printException(logger, e);
                                            }
                                        }
                                    }, 0);

                                    OperateRespVO vo = new OperateRespVO();
                                    vo.setOptId(optId);
                                    vo.setPlayTime(this.getGameTime(busId));
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

    @Override
    public BaseRespVO operate(long uid, int busId, Customer customer) {
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null && dollBus.getVirtual() == 1) {
            if (dollBus.getType() == 1) {

                if (dollBus.getGroupId() != dollBus.getGroupId()) {
                    return new BaseRespVO(3, false, "当前商户没有操作改机器的权限");
                }

                PushBusStatusVirtual pushBusStatusVirtual = pushBusStatusVirtualDao.get(busId, uid, customer.getId());
                if (pushBusStatusVirtual != null) {

                    return new BaseRespVO(200, true, "执行摆动!");

                } else {
                    return new BaseRespVO(2, false, "请先上机!");
                }
            } else {
                return new BaseRespVO(1, false, "投币失败,不是推币机类型!");
            }
        }
        return new BaseRespVO(0, false, "机器不存在或类型错误!");
    }

    @Override
    public BaseRespVO finish(long uid, int busId, Customer customer) {
        PushBusStatusVirtual pushBusStatusVirtual = pushBusStatusVirtualDao.get(busId, uid, customer.getId());
        if (pushBusStatusVirtual != null) {
            int flag = pushBusStatusVirtualDao.delete(busId, pushBusStatusVirtual.getOptId());
            if (flag > 0) {
                if (pushCoinVirtualMachineDao.updateState(pushBusStatusVirtual.getOptId()) > 0) {
                    logger.info("更改总表状态成功.");
                }
                if (pushCoinVirtualOptRouterDao.updateState(customer.getGroupId(), pushBusStatusVirtual.getOptId()) > 0) {
                    logger.info("更改分表状态成功.");
                }

                BaseRespVO respVO = new BaseRespVO(200, true, "成功下机");
                respVO.setData(pushBusStatusVirtual.getOptId());

                DollBus dollBus = dollBusService.getDollBus(pushBusStatusVirtual.getBusId());
                if (dollBus != null) {
                    PushCoinCallbackLog pushCoinCallbackLog = new PushCoinCallbackLog(
                            dollBus.getDeviceId(), dollBus.getBusId(), pushBusStatusVirtual.getOptId(),
                            customer.getGroupId(), customer.getId(), "",
                            customer.getPushCallbackUrl(), "",
                            1, "虚拟机主动下机处理");

                    MachinePushCoinVirtual machinePushCoinVirtual = pushCoinVirtualOptRouterDao.getOne(customer.getGroupId(), pushBusStatusVirtual.getOptId());
                    if (machinePushCoinVirtual != null) {
                        pushCoinCallbackLog.setRecord(JSON.toJSONString(machinePushCoinVirtual));
                    }


                    pushCoinCallbackLog.setResponse(JSON.toJSONString(respVO));

                    if (pushCoinCallbackDao.insert(pushCoinCallbackLog) > 0) {
                        logger.info("新增推币机回调记录=>" + dollBus.getBusId());
                    }

                }

                //发送到萌趣
                //this.sendToDollApp(customer,pushBusStatusVirtual.getBusId(),pushBusStatusVirtual.getUid(),pushBusStatusVirtual.getOptId(),0,0,1);

                return respVO;
            }
        }
        return new BaseRespVO(201, true, "状态不存在,无需下机");
    }

    @Override
    public PushCoinVirtualConfig getVirtualPushCoinConfig(int busId) {
        return this.getVirtualConfig(busId);
    }

    @Override
    public BaseRespVO entryVirtualCoinPushRoom(EntryVirtualRoomReqVO reqVO) {
        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null && dollBus.getVirtual() == 1) {

            //初始化水果机核心组件
            //pushCoinFruitService.initFruitCore();

            Customer customer = customerService.getCustomer(reqVO.getAppId());
            if (customer != null) {
                if (dollBus.getGroupId() != dollBus.getGroupId()) {
                    return new BaseRespVO(4, false, "当前商户没有操作改机器的权限");
                }
                try {
                    int authType = PushCoinVirtualUtil.getRandomType();
                    if (authType > 0) {
                        Map<String, Object> dataMap = Maps.newHashMap();
                        PushCoinVirtualConfig config = pushCoinVirtualConfigDao.getVirtualConfigByBusId(dollBus.getBusId());
                        if (config == null) {
                            if (pushCoinVirtualConfigDao.initConfig(dollBus.getBusId()) > 0) {
                                logger.info("初始化虚拟推币机配置");
                            }
                            config = pushCoinVirtualConfigDao.getVirtualConfigByBusId(dollBus.getBusId());
                        }
                        dataMap.put("config", config);

                        VirtualPushCoinRoom virtualPushCoinRoom = new VirtualPushCoinRoom();
                        virtualPushCoinRoom.setAuthType(authType);
                        virtualPushCoinRoom.setBusId(reqVO.getBusId());
                        virtualPushCoinRoom.setCustomerId(customer.getId());
                        virtualPushCoinRoom.setUid(reqVO.getUid());
                        if (pushCoinVirtualRoomDao.addWatchRecord(virtualPushCoinRoom) > 0) {
                            dataMap.put("type", authType);
                            return new BaseRespVO(dataMap);
                        }
                    }
                } catch (Exception e) {
                    logger.info("entryVirtualCoinPushRoom err=>" + e.getMessage());
                }
                return new BaseRespVO(3, false, "生成认证类型错误");
            } else {
                return new BaseRespVO(2, false, "当前商户不存在");
            }
        }
        return new BaseRespVO(1, false, "机器不存在或类型不正确.");
    }

    @Override
    public BaseRespVO laveVirtualCoinPushRoom(EntryVirtualRoomReqVO reqVO) {
        Customer customer = customerService.getCustomer(reqVO.getAppId());
        if (customer != null) {
            int flag = pushCoinVirtualRoomDao.laveVirtualRoom(customer.getId(), reqVO.getUid());
            if (flag > 0) {
                //判断是否在游戏中
                PushBusStatusVirtual pushBusStatusVirtual = pushBusStatusVirtualDao.get(reqVO.getBusId(), reqVO.getUid(), customer.getId());
                if (pushBusStatusVirtual != null) {
                    if (pushBusStatusVirtualDao.delete(reqVO.getBusId(), pushBusStatusVirtual.getOptId()) > 0) {
                        logger.info("处理下机操作...");

                        if (pushCoinVirtualMachineDao.updateState(pushBusStatusVirtual.getOptId()) > 0) {
                            logger.info("更改总表状态成功.");
                        }
                        if (pushCoinVirtualOptRouterDao.updateState(customer.getGroupId(), pushBusStatusVirtual.getOptId()) > 0) {
                            logger.info("更改分表状态成功.");
                        }

                        DollBus dollBus = dollBusService.getDollBus(pushBusStatusVirtual.getBusId());
                        if (dollBus != null) {

                            //发送到萌趣
                            this.sendToDollApp(customer, dollBus, pushBusStatusVirtual.getUid(), pushBusStatusVirtual.getOptId(), 0, 0, 1, "虚拟推币机退出房间处理");

                        }

                    }
                }
                logger.info("用户=>" + reqVO.getUid() + ",离开房间=>" + reqVO.getBusId());
            }
            return new BaseRespVO(200, true, "离开房间");
        } else {
            return new BaseRespVO(0, false, "当前商户不存在");
        }
    }

    /**
     * 获取虚拟推币机配置
     *
     * @param busId
     * @return
     */
    private PushCoinVirtualConfig getVirtualConfig(int busId) {
        PushCoinVirtualConfig config = pushCoinVirtualConfigDao.getVirtualConfigByBusId(busId);
        if (config != null) {
            return config;
        } else {
            if (pushCoinVirtualConfigDao.initConfig(busId) > 0) {
                logger.info("初始化虚拟推币机配置 busId=>" + busId);
                config = pushCoinVirtualConfigDao.getVirtualConfigByBusId(busId);
            } else {
                logger.info("初始化虚拟推币机配置失败 busId=>" + busId);
            }
        }
        return config;
    }


    @Override
    public BaseRespVO handleCallback(String p1, String p2, String md5_value, int p4, int p5, int p6, int p7, int p8, int p9,int p10,int p11,final DollBus dollBus, final long uid, final Customer customer) {
        final PushBusStatusVirtual pushBusStatusVirtual = pushBusStatusVirtualDao.get(dollBus.getBusId(), uid, customer.getId());
        if (pushBusStatusVirtual != null) {
            MachinePushCoinVirtual machinePushCoinVirtual = pushCoinVirtualMachineDao.getOne(pushBusStatusVirtual.getOptId());
            if (machinePushCoinVirtual != null) {
                Map<String, Integer> valueMaps = PushCoinVirtualUtil.verifyOutCoinIsValid(machinePushCoinVirtual.getAuthType(), p1, p2, md5_value, p4, p5, p6, p7, p8, p9,p10,p11);
                if (!StringUtil.isNullOrEmpty(valueMaps)) {
                    final int T = valueMaps.get("T");//当前推板
                    final int Z = valueMaps.get("Z");//当前回收
                    final int Y = valueMaps.get("Y");//当前出币
                    final int X = machinePushCoinVirtual.getIntoCoin(); //当前投币
                    final int O = machinePushCoinVirtual.getOutCoin(); //当前入币
                    final int S = valueMaps.get("S");//水果机出奖
                    final int C = valueMaps.get("C");//推板差值

                    logger.info("参数验证成功  T=>" + T + ",Z=>" + Z + ",Y=>" + Y + ",X=>" + X + ",S=>" + S + ",C=>" + C + ",O=>" + O);

                    if (Y > 0) {

                        // 结果正确性验证
                        if (PushCoinVirtualUtil.VerifyValidity(machinePushCoinVirtual.getAuthType(), X, Y, Z, T, S, C, O)) {
                            logger.info("结果验证真实有效,出币=>" + Y + ",optId=>" + pushBusStatusVirtual.getOptId() + ",state=>" + machinePushCoinVirtual.getState());

                            if (pushCoinVirtualMachineDao.updateCoin(pushBusStatusVirtual.getOptId(), Y, 1,0) > 0) {
                                logger.info("全表操作 入币=>" + Y);

                                if (pushCoinVirtualOptRouterDao.updateCoin(customer.getGroupId(), pushBusStatusVirtual.getOptId(), Y, 1) > 0) {
                                    logger.info("分表操作 入币=>" + Y);

                                    SysTimerHandle.execute(new TimerTask() {
                                        @Override
                                        public void run() {
                                            try {
                                                //发送到萌趣
                                                sendToDollApp(customer, dollBus, pushBusStatusVirtual.getUid(), pushBusStatusVirtual.getOptId(), Y, S, 0, "");

                                                updateEndTime(pushBusStatusVirtual.getUid(), pushBusStatusVirtual.getOptId(), pushBusStatusVirtual.getBusId(), customer);
                                            } catch (Exception e) {
                                                PrintException.printException(logger, e);
                                            }
                                        }
                                    }, 0);

                                    BaseRespVO vo = new BaseRespVO(200, true, "出奖");
                                    vo.setData(Y);
                                    return vo;

                                } else {
                                    logger.info("更新分表出币失败 optId=>" + pushBusStatusVirtual.getOptId() + ",Y=>" + Y);
                                }
                            } else {
                                logger.info("更新全表出币失败 optId=>" + pushBusStatusVirtual.getOptId() + ",Y=>" + Y);
                            }

                            return new BaseRespVO(201, true, "本次操作已超時,不计入数据");

                        } else {
                            BaseRespVO vo = new BaseRespVO(1, false, "结果有效性错误");
                            Map<String, Object> map = Maps.newHashMap();
                            map.put("T", T);
                            map.put("Z", Z);
                            map.put("Y", Y);
                            map.put("X", X);
                            map.put("O", O);
                            map.put("S", S);
                            map.put("C", C);

                            vo.setData(map);
                            return vo;

                            //return new BaseRespVO(1, false, "结果有效性错误");
                        }
                    } else {
                        return new BaseRespVO(202, true, "出币为0,不计入数据");
                    }
                } else {
                    return new BaseRespVO(2, false, "参数验证失败");
                }
            } else {
                return new BaseRespVO(3, false, "找不到当前操作记录");
            }
        }
        return new BaseRespVO(4, false, "机器空闲状态");
    }

    @Override
    public int updateEndTime(long uid, long optId, int busId, Customer customer) {
        int status;
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null && dollBus.getType() == 1 && dollBus.getVirtual() == 1) {
            PushBusStatusVirtual pushBusStatusVirtual = pushBusStatusVirtualDao.get(busId, uid, customer.getId());
            if (pushBusStatusVirtual != null && pushBusStatusVirtual.getOptId() == optId) {
                MachinePushCoinVirtual machinePushCoinVirtual = pushCoinVirtualMachineDao.getOne(pushBusStatusVirtual.getOptId());
                if (machinePushCoinVirtual != null && machinePushCoinVirtual.getState() != 1) {
                    PushCoinVirtualConfig config = this.getVirtualConfig(dollBus.getBusId());
                    if (config != null) {
                        //更新结束时间
                        status = pushBusStatusVirtualDao.updateEndTime(dollBus.getBusId(), pushBusStatusVirtual.getOptId(), config.getPlayTime());
                        logger.info(status > 0 ? "更新结束时间为=>" + config.getPlayTime() : "更新结束时间失败");
                    } else {
                        status = -4;
                    }
                } else {
                    status = -3;
                }
            } else {
                status = -2;
            }
        } else {
            status = -1;
        }
        return status;
    }

    @Override
    public int getGameTime(int busId) {
        PushCoinVirtualConfig pushCoinVirtualConfig = this.getVirtualConfig(busId);
        if (pushCoinVirtualConfig != null) {
            return pushCoinVirtualConfig.getPlayTime();
        }
        return 0;
    }

    @Override
    public void handleTimeOutRecord() {
        List<PushBusStatusVirtual> virtualList = pushBusStatusVirtualDao.getEndTimeTimeOutRecord();
        logger.info("handleTimeOutRecord=>" + JSON.toJSONString(virtualList));
        if (!StringUtil.isNullOrEmpty(virtualList)) {
            for (PushBusStatusVirtual virtual : virtualList) {
                try {
                    if (pushCoinVirtualMachineDao.updateState(virtual.getOptId()) > 0) {
                        logger.info("handleTimeOutRecord 锁定最终流水=>" + virtual.getOptId());

                        Customer customer = customerService.getCustomerById(virtual.getCustomerId());
                        if (customer != null) {
                            if (pushCoinVirtualOptRouterDao.updateState(customer.getGroupId(), virtual.getOptId()) > 0) {
                                logger.info("handleTimeOutRecord 推币机分表操作-->锁定最终流水=>" + virtual.getOptId());
                            }
                            DollBus dollBus = dollBusService.getDollBus(virtual.getBusId());
                            if (dollBus != null) {

                                //  发送到萌趣(出币转钻石)
                                this.sendToDollApp(customer, dollBus, virtual.getUid(), virtual.getOptId(), 0, 0, 1, "虚拟推币机超时处理");
                            }


                            //this.customerResultNotice(customer,pushBusStatus.getOptId());
                            //  发送到萌趣(出币转钻石)
                            //this.sendToDollApp(customer,pushBusStatus.getBusId(),pushBusStatus.getUid(),pushBusStatus.getOptId(),0,1);
                        }

                        if (pushBusStatusVirtualDao.delete(virtual.getBusId(), virtual.getOptId()) > 0) {
                            logger.info("handleTimeOutRecord 清除机器状态=>" + virtual.getBusId() + ",操作ID=>" + virtual.getOptId());
                        }
                    }
                } catch (Exception e) {
                    logger.info("handleTimeOutRecord err=>" + e.getMessage());
                }
            }
        }
    }

    @Override
    public BaseRespVO getVirtualByUser(long uid, int busId, Customer customer) {
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null && dollBus.getType() == 1 && dollBus.getVirtual() == 1) {
            logger.info("机器验证成功,开始检验是否上机状态");
            PushBusStatusVirtual pushBusStatusVirtual = pushBusStatusVirtualDao.get(dollBus.getBusId(), uid, customer.getId());
            if (pushBusStatusVirtual != null) {
                MachinePushCoinVirtual machinePushCoinVirtual = pushCoinVirtualMachineDao.getOne(pushBusStatusVirtual.getOptId());
                if (machinePushCoinVirtual != null && machinePushCoinVirtual.getState() != 1) {
                    BaseRespVO respVO = new BaseRespVO(200, true, "当前用户正在上机状态");
                    respVO.setData(machinePushCoinVirtual.getOptId());
                    return respVO;
                }
            }
        }
        return new BaseRespVO(1, false, "当前用户未上机");
    }


    //发送到doll_app的请求
    private void sendToDollApp(Customer customer, DollBus dollBus, long uid, long optId, int outCoin, int win, int isFinal, String remark) {
        logger.info("virtual sendToDollApp 开始将结果发送到zjdoll_app=>" + JSON.toJSONString(customer));
        if (customer != null && customer.getInside() == 1) {
            Map<String, Object> postMap = Maps.newHashMap();
            postMap.put("busId", dollBus.getBusId());
            postMap.put("optId", optId);
            postMap.put("outCoin", outCoin);
            postMap.put("win", win);
            postMap.put("isFinal", isFinal);
            postMap.put("uid", uid);
            postMap.put("auto", "dollPub_service_app_virtual");
            try {
                if (!StringUtil.isNullOrEmpty(customer.getVirtualPushCallbackUrl())) {

                    String param = JSON.toJSONString(postMap);

                    logger.info("virtual sendToDollApp customer=>" + customer.getName() + ",url=>" + customer.getVirtualPushCallbackUrl() + ",参数=>" + param);

                    if (isFinal == 1) {

                        PushCoinCallbackLog pushCoinCallbackLog = new PushCoinCallbackLog(
                                dollBus.getDeviceId(), dollBus.getBusId(), optId,
                                customer.getGroupId(), customer.getId(), "",
                                customer.getPushCallbackUrl(), JSON.toJSONString(postMap),
                                1, remark);

                        MachinePushCoinVirtual machinePushCoinVirtual = pushCoinVirtualOptRouterDao.getOne(customer.getGroupId(), optId);
                        if (machinePushCoinVirtual != null) {
                            pushCoinCallbackLog.setRecord(JSON.toJSONString(machinePushCoinVirtual));
                        }


                        if (pushCoinCallbackDao.insert(pushCoinCallbackLog) > 0) {
                            logger.info("新增推币机回调记录=>" + dollBus.getBusId());
                        }

                        HttpEntity httpEntity = HttpUtil.Post(customer.getVirtualPushCallbackUrl(), param);
                        if (httpEntity != null) {
                            logger.info("virtual sendToDollApp 得币通知结果回调=>" + JSON.toJSONString(httpEntity));

                            pushCoinCallbackLog.setResponse(JSON.toJSONString(httpEntity));

                            if (pushCoinCallbackDao.update(pushCoinCallbackLog) > 0) {
                                logger.info("更新推币机回调记录");
                            }
                        }

                    } else {

                        HttpEntity httpEntity = HttpUtil.Post(customer.getVirtualPushCallbackUrl(), param);
                        if (httpEntity != null) {
                            logger.info("virtual sendToDollApp 得币通知结果回调=>" + JSON.toJSONString(httpEntity));
                        }

                    }

                }
            } catch (Exception e) {
                logger.info("virtual sendToDollApp push coin customerResultNotice error ==>" + e.getMessage());
            }
        }
    }

}
