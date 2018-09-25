package com.imlianai.dollpub.app.modules.support.probability.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.dollpub.app.modules.core.user.value.UserValueDAO;
import com.imlianai.dollpub.app.modules.publics.mail.MailSenderFactory;
import com.imlianai.dollpub.app.modules.publics.mail.SimpleMailSender;
import com.imlianai.dollpub.app.modules.support.probability.dao.DownClawDao;
import com.imlianai.dollpub.app.modules.support.probability.util.DownClawUtil;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.user.UserDownClaw;
import com.imlianai.dollpub.domain.user.UserDownClawRecord;
import com.imlianai.dollpub.domain.user.UserValue;
import com.imlianai.dollpub.machine.iface.IMachineRemoteService;
import com.imlianai.dollpub.machine.iface.domain.MachineCmdResult;
import com.imlianai.dollpub.machine.iface.domain.MachineDevice;
import com.imlianai.dollpub.machine.iface.domain.MachineOptRecord;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * 下抓概率计算服务
 *
 * @author wurui
 * @create 2018-04-10 15:36
 **/
@Service
public class DownClawService {

    private BaseLogger logger = BaseLogger.getLogger(getClass());

    @Resource
    private UserValueDAO userValueDAO;

    @Resource
    private DownClawDao downClawDao;

    @Resource
    private TradeChargeService tradeChargeService;

    @Reference
    private IMachineRemoteService iMachineRemoteService;

    @Resource
    private DollBusService dollBusService;

    /**
     * 处理下抓
     *
     * @param busId
     * @param uid
     * @param optId
     */
    public int downClawHandle(int busId, long uid, long optId) {

        MachineOptRecord optRec = downClawDao.getMachineOptRecord(optId);
        if (optRec != null && optRec.getFinish() == 0 && optRec.getDeviceId() != null) {

            if (optRec.getUid() == uid && busId == optRec.getBusId()) {

                int downClawNum = 0;
                int userDollNumber = 0;
                int time = 0;



                //int time = downClawDao.getDownClawTime(optRec.getDeviceId()); //当前概率
                //下抓类型(0:机器弱抓,1:机器默认,2:机器强爪,3:系统弱抓,4:系统默认,5:系统强爪)
                int type = 0;
                String remark = "";

                UserDownClaw userDownClaw = downClawDao.get(uid);
                if (userDownClaw != null) {
                    downClawNum = userDownClaw.getNum();
                    time = userDownClaw.getTime();
                } else {
                    if (downClawDao.init(uid) > 0) {
                        logger.info("初始化用户=>" + uid);
                    }
                }

                UserValue userValue = userValueDAO.getUserValueNoCache(uid);
                if (userValue != null) {
                    userDollNumber = userValue.getDollNum();
                } else {
                    userValueDAO.initUserValue(new UserValue(uid));
                }



                String current = "当前:下爪数=>" + downClawNum + ",背包数=>" + userDollNumber + ",当前概率=>" + time;
                logger.info(current);

                try {
                    if (userDollNumber < DownClawUtil.USER_DOLL) {
                        logger.info("执行背包数 [<1] 逻辑....");

                        //1次弱爪(系统)
                        if (downClawNum < DownClawUtil.NEW_USER_NO_DOLL) {
                            type = this.machineRequired(busId, uid, optRec.getOptId(), downClawNum, DownClawUtil.SYS_CATCH_WEAK);

                            type = 3;
                            remark = "背包数小于1，下爪数=>[" + (downClawNum + 1) + "],新用户固定3次弱抓,执行类型=>" + type;

                            logger.info(remark);

                        } else {
                            //跳出弱爪，固定强爪(系统)
                            type = this.machineRequired(busId, uid, optRec.getOptId(), downClawNum, DownClawUtil.SYS_CATCH_STRONG);

                            type = 5;
                            remark = "背包数小于1，下爪数=>[" + (downClawNum + 1) + "],新用户失败1次以上，固定强爪,执行类型=>" + type;

                            logger.info(remark);

                        }

                    } else if (userDollNumber == DownClawUtil.USER_DOLL) {
                        logger.info("执行背包数 [=1] 逻辑....");

                        //判断是否充值过
                        if (tradeChargeService.hasCharge(uid) > 0) {

                            //固定7次弱抓(系统)
                            if (downClawNum < DownClawUtil.WEAK_NUM_7) {
                                type = this.machineRequired(busId, uid, optRec.getOptId(), downClawNum, DownClawUtil.SYS_CATCH_WEAK);

                                type = 3;
                                remark = "背包数等于1，用户充值过，下爪数=>[" + (downClawNum + 1) + "],新用户抓取成功1次，默认7次弱抓,执行类型=>" + type;

                                logger.info(remark);

                            } else {

                                //机器弱抓
                                //机器概率
//                                type = this.machineDefault(busId, uid, optRec);
//                                remark = "背包数等于1，用户充值过，下爪数=>[" + (downClawNum + 1) + "],新用户抓取成功1次，默认走机器概率,执行类型=>" + type;
//                                logger.info(remark);


                                //8次机器概率
                                if (time < DownClawUtil.TIME_NUM_8) {
                                    //机器概率
                                    type = this.machineDefault(busId, uid, optRec);
                                    remark = "背包数等于1，用户充值过，下爪数=>[" + (downClawNum + 1) + "],新用户抓取成功1次，默认7次弱抓后,走8次机器概率,执行类型=>" + type;
                                    logger.info(remark);

                                } else {
                                    //跳出机器概率，固定强爪(系统)
                                    type = this.machineRequired(busId, uid, optRec.getOptId(), downClawNum, DownClawUtil.SYS_CATCH_STRONG);

                                    type = 5;
                                    remark = "背包数等于1，用户充值过，下爪数=>[" + (downClawNum + 1) + "],新用户抓取成功1次，默认7次弱抓后,失败次数=>[" + time  + "+],固定强爪,执行类型=>" + type;

                                    logger.info(remark);

                                }

                            }

                        } else {
                            //用户未充值，固定弱抓(系统)
                            type = this.machineRequired(busId, uid, optRec.getOptId(), downClawNum, DownClawUtil.SYS_CATCH_WEAK);

                            type = 3;
                            remark = "背包数小于1，用户未充值过，固定弱抓,执行类型=>" + type;

                            logger.info(remark);

                        }

                    } else if (userDollNumber > DownClawUtil.USER_DOLL) {
                        logger.info("执行背包数 [>1] 逻辑....");

                        //5次弱抓(系统)
                        if (downClawNum < DownClawUtil.WEAK_NUM_5) {

                            type = this.machineRequired(busId, uid, optRec.getOptId(), downClawNum, DownClawUtil.SYS_CATCH_WEAK);

                            type = 3;
                            remark = "背包数大于1，下爪数=>[" + (downClawNum + 1) + "],固定5次弱抓,执行类型=>" + type;

                            logger.info(remark);

                        } else {

                            //12次机器概率
                            if (time < DownClawUtil.TIME_NUM_12) {

                                //机器概率
                                type = this.machineDefault(busId, uid, optRec);
                                remark = "背包数大于1，下爪数=>[" + (downClawNum + 1) + "]，默认5次弱抓后,走12次机器概率,执行类型=>" + type;
                                logger.info(remark);

                            } else {
                                //跳出机器概率，固定强爪(系统)
                                type = this.machineRequired(busId, uid, optRec.getOptId(), downClawNum, DownClawUtil.SYS_CATCH_STRONG);

                                type = 5;
                                remark = "背包数大于1，下爪数=>[" + (downClawNum + 1) + "]，默认5次弱抓后,失败次数=>[" + time + "+],固定强爪,执行类型=>" + type;

                                logger.info(remark);

                            }
                        }
                    }
                } catch (Exception e) {
                    logger.info("downClawHandle error=>" + e.getMessage());
                    logger.error("downClawHandle error=>" + e.getMessage());
                }

                logger.info("最终确定下爪类型====>" + type);
                UserDownClawRecord userDownClawRecord = new UserDownClawRecord(busId, uid, optId, type, remark);
                int flag = this.addUserDownClawRecord(userDownClawRecord);
                if (flag > 0) {
                    logger.info("新增用户下抓记录成功=>" + JSON.toJSONString(userDownClawRecord));
                }
                SimpleMailSender sms = MailSenderFactory.getSender(1);
                try {
                    if (sms != null){
                        if (uid ==386511 || uid== 386523 ){
                            sms.send("619815816@qq.com", "最终下爪类型=>" + type, remark);
                        }

                        if (uid == 359144 || uid == 384637){
                            sms.send("luxiaoting@imlianai.com","最终下爪类型=>" + type,remark);
                        }
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                return type;
            }
        }
        return -1;
    }


    /**
     * 按机器概率下抓
     *
     * @return
     */
    private int machineDefault(int busId, long uid, MachineOptRecord optRec) {
        int type = DownClawUtil.SYS_CATCH_DEFAULT;
        String typeDesc = "默认";

        MachineDevice machineDevice = iMachineRemoteService.getMachineState(busId);
        if (machineDevice != null) {

            int time = downClawDao.getDownClawTime(machineDevice.getDeviceId()); //当前概率

            if (time >= machineDevice.getStrong()) {
                time = 0;
            }

            if (time == machineDevice.getStrong() - 1) { // 下一次为强抓
                type = DownClawUtil.SYS_CATCH_STRONG;
                typeDesc = "强抓";
            }

            logger.info("执行抓取动作-" + typeDesc);

            //下抓操作
            MachineCmdResult machineCmdResult = iMachineRemoteService.sendClawCmd(busId, uid, optRec.getOptId(), type);
            if (machineCmdResult != null && machineCmdResult.isSuccess()) {
                if (downClawDao.saveOrUpdateDownClawCount(machineDevice.getDeviceId(), time + 1) > 0) {
                    int dateCode = Integer.parseInt(DateUtils.getCurrentDateString("yyyyMMdd"));
                    downClawDao.saveDownClawRecord(machineDevice.getDeviceId(), time + 1, typeDesc, dateCode);
                    downClawDao.updateTimeAddOne(uid);//time+1
                }
            }
        }
        if (type == DownClawUtil.SYS_CATCH_DEFAULT) type = DownClawUtil.MAC_CATCH_DEFAULT;
        if (type == DownClawUtil.SYS_CATCH_STRONG) type = DownClawUtil.MAC_CATCH_STRONG;
        return type;
    }

    /**
     * 按要求下爪
     *
     * @param busId
     * @param uid
     * @param optId
     * @param type
     */
    private int machineRequired(int busId, long uid, long optId, int downClawNum, int type) {
        MachineCmdResult machineCmdResult = iMachineRemoteService.sendClawCmd(busId, uid, optId, type);
        if (machineCmdResult != null && machineCmdResult.isSuccess()) {
            downClawDao.updateNum(uid, downClawNum + 1);
        } else {
            logger.info("downClawHandle=>" + JSON.toJSONString(machineCmdResult));
        }
        return type;
    }


    /**
     * 重置下抓次数
     *
     * @param uid
     * @return
     */
    private int resetDownClawNum(long uid, long optId) {

        logger.info("重置下抓次数 reset=> uid=>" + uid + ",optId=>" + optId);

        UserValue userValue = userValueDAO.getUserValueNoCache(uid);
        logger.info("userValue=>" + JSON.toJSONString(userValue));
        //用户娃娃 >=1
        if (userValue != null && userValue.getDollNum() >= DownClawUtil.USER_DOLL) {
            //判断是否是机器概率抓中,如果是则继续走机器概率
            UserDownClawRecord userDownClawRecord = this.getUserDownClawRecord(uid, optId);
            logger.info("userDownClawRecord=>" + JSON.toJSONString(userDownClawRecord));
            if (userDownClawRecord != null) {
                int type = userDownClawRecord.getType();
                //说明该记录是机器概率抓中
                if (type == 0 || type == 1 || type == 2) {
                    //downClawDao.updateUserDownClaw(uid, 5,0); //之前是机器概率抓中补5次下爪
                    downClawDao.updateUserDownClaw(uid, 0,0); //现在是无论什么情况，抓中就重置
                    return 1;
                }
            }
        }
        return downClawDao.updateUserDownClaw(uid, 0,0);
    }

    /**
     * 重置机器概率
     *
     * @param deviceId
     * @return
     */
    private int resetMachineTime(String deviceId) {
        return downClawDao.saveOrUpdateDownClawCount(deviceId, 0);
    }

    /**
     * 重置下抓次数&重置抓取次数
     *
     * @param uid
     * @param optId
     * @param deviceId
     */
    public void resetDownClawNumAndMachineTime(Customer customer, long uid, long optId, String deviceId) {
        if (customer != null && customer.getChanceOptimize() == 1) {
            try {
                if (this.resetDownClawNum(uid, optId) > 0) {
                    logger.info("重置下抓次数=>" + uid);
                }
                if (this.resetMachineTime(deviceId) > 0) {
                    logger.info("重置机器概率=>" + deviceId);
                }
            } catch (Exception e) {
                logger.info("重置失败=>" + e.getMessage());
                logger.error("重置失败=>" + e.getMessage());
            }
        }
    }


    /**
     * 抓中保护
     */
    public void catchSafe(Customer customer, int busId) {
        logger.info("catchSafe=>" + busId + ",customer=>" + JSON.toJSONString(customer));
        if (customer != null) {
            try {
                if (customer.getCatchSafe() == 1){
                    int count = downClawDao.selectDownClawCatchSuccessCount(busId,customer.getCatchSafeTime());
                    logger.info("开启保护=>" + busId + ",[" + customer.getCatchSafeTime() + "]分钟内抓中=>[" + count + "]次");
                    if (count >= customer.getCatchSafeNum()){
                        logger.info("机器 busId=>" + busId + ",在 [" + customer.getCatchSafeTime() + "] 分钟内，连续抓中=>[" + count + "] 次，大于等于预定=>[" +  customer.getCatchSafeNum() +"]次");
                        if (dollBusService.updateValid(busId,0) > 0){
                            logger.info("机器下架成功=>" + busId);
                        }
                    }
                }
            } catch (Exception e) {
                logger.info("catchSafe=>" + e.getMessage());
                logger.error("catchSafe=>" + e.getMessage());
            }
        }
    }

    /**
     * 更新下爪抓中的结果
     * @param uid
     * @param optId
     * @param result
     * @return
     */
    public int updateResult(long uid,long optId,int result){
        return downClawDao.updateResult(uid,optId,result);
    }


    public int addUserDownClawRecord(UserDownClawRecord record){
        return downClawDao.addUserDownClawRecord(record);
    }

    public UserDownClawRecord getUserDownClawRecord(long uid,long optId){
        return downClawDao.getRecord(uid,optId);
    }


}
