package com.imlianai.dollpub.app.modules.core.doll.bus;

import java.util.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import com.ctrip.framework.apollo.util.PropertiesUtil;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.app.modules.publics.mail.MailSenderFactory;
import com.imlianai.dollpub.app.modules.publics.mail.SimpleMailSender;
import com.imlianai.dollpub.constants.MachineStateConst;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.machine.iface.IMachineRemoteService;
import com.imlianai.rpc.support.utils.LivePropsUtil;
import com.imlianai.rpc.support.utils.PropertUtil;
import com.imlianai.rpc.support.utils.SysTimerHandle;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.core.api.dao.DollBusStatusDAO;
import com.imlianai.dollpub.app.modules.core.user.customer.opt.dao.CustomerOptRecordDao;
import com.imlianai.dollpub.app.modules.core.doll.utils.DollUtil;
import com.imlianai.dollpub.app.modules.core.doll.utils.qiyiguo.QiyiguoMachine;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.constants.DollCacheConst;
import com.imlianai.dollpub.domain.doll.BusOperatingRecord;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.doll.DollBusStatus;
import com.imlianai.dollpub.domain.doll.DollBus.DollBusCompany;
import com.imlianai.dollpub.domain.msg.MsgRoom;
import com.imlianai.dollpub.domain.msg.MsgRoomType;
import com.imlianai.dollpub.domain.optrecord.OptRecord;
import com.imlianai.dollpub.machine.iface.domain.MachineDevice;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class DollBusServiceImpl implements DollBusService {
    protected final BaseLogger logger = BaseLogger.getLogger(this
            .getClass());
    @Resource
    private DollBusDao dollBusDao;
    @Resource
    private MsgService msgService;
    @Resource
    private DollBusStatusDAO busStatusDAO;

    @Resource
    private DollBusService dollBusService;

    @Resource
    private CustomerOptRecordDao customerOptRecordDao;

    @Reference
    private IMachineRemoteService iMachineRemoteService;

    @Resource
    private CustomerService customerService;

    @Override
    public int updateConversationId(int busId, String conversationId) {
        return dollBusDao.updateConversationId(busId, conversationId);
    }

    @Override
    public DollBus getDollBus(int busId) {
        return dollBusDao.getDollBus(busId);
    }

    @Override
    public List<DollBus> getAllDollBus(int start, int size) {
        return this.getNewestDollBusList(dollBusDao.getDollBusList(start, size));
    }

    @Override
    public List<DollBus> getDollBusByGroupId(int groupId, int start, int size) {
        return this.getNewestDollBusList(dollBusDao.getDollBusByGroupId(groupId, start, size));
    }

    @Override
    public List<DollBus> getDollBus() {
        return dollBusDao.getDollBus();
    }

    @Override
    public void updateBusWatchCount(int busId, int num) {
        dollBusDao.updateBusWatchCount(busId, num);
    }

    @Override
    @CacheWrite(key = DollCacheConst.BUS_OPERATING_RECORD, pkField = "busId", validTime = 10)
    public BusOperatingRecord getBusOperatingRecord(int busId) {
        return dollBusDao.getBusOperatingRecord(busId);
    }

    @Override
    public int addBusOperatingRecord(int busId, long uid, long optId, long logId) {
        return dollBusDao.addBusOperatingRecord(busId, uid, optId, logId);
    }

    @Override
    public int closeBusOperatingRecord(int busId, long optId) {
        return dollBusDao.closeBusOperatingRecord(busId, optId);
    }

    @Override
    public int removeBusOperatingRecord(BusOperatingRecord record) {
        if (record != null) {
            return dollBusDao.removeBusOperatingRecord(record.getBusId(),
                    record.getOptId());
        }
        return 0;
    }

    @Override
    public List<DollBus> getCurrentDevice() {
        List<DollBus> bus = new ArrayList<DollBus>();
        List<QiyiguoMachine> list = DollUtil.getDeviceList();
        if (!StringUtil.isNullOrEmpty(list)) {
            for (QiyiguoMachine qiyiguoMachine : list) {
                bus.add(new DollBus(qiyiguoMachine.getDevice_id(),
                        qiyiguoMachine.getStatus(), qiyiguoMachine.getName(),
                        qiyiguoMachine.getImg(), DollBusCompany.QIYIGUO.type,
                        qiyiguoMachine.getStream_address_1(), qiyiguoMachine
                        .getStream_address_2(), qiyiguoMachine
                        .getStream_address_raw_1(), qiyiguoMachine
                        .getStream_address_raw_2()));
            }
        }
        return bus;
    }

    @Override
    public List<BusOperatingRecord> getInvaildRecord() {
        List<BusOperatingRecord> list = dollBusDao.getInvaildRecord();
        return list;
    }

    @Override
    public void sendReleaseMsg(BusOperatingRecord record) {
        DollBus bus = getDollBus(record.getBusId());
        if (bus != null) {
            MsgRoom msgRoom = new MsgRoom(bus, MsgRoomType.BUS_RELEASE.type,
                    "操作到期，可以重新上机");
            msgService.sendMsgRoom(msgRoom);
        }
    }

    @Override
    public void sendReleaseMsg(OptRecord record) {
        DollBus bus = getDollBus(record.getBusId());
        if (bus != null) {
            MsgRoom msgRoom = new MsgRoom(bus, MsgRoomType.BUS_RELEASE.type,
                    "操作到期，可以重新上机");
            msgService.sendMsgRoom(msgRoom);
        }
    }

    @Override
    public DollBus getDollBusByDeviceId(String deviceId) {
        return dollBusDao.getDollBus(deviceId);
    }

    @Override
    public int getDollBusValidByBusId(int busId) {
        return dollBusDao.getDollBusValidByBusId(busId);
    }

    @Override
    public Map<Integer, MachineDevice> getMachineDevice(List<Integer> busIds) {
        Map<Integer, MachineDevice> resMap = new HashMap<Integer, MachineDevice>();
        if (!StringUtil.isNullOrEmpty(busIds)) {
            List<MachineDevice> list = iMachineRemoteService.getMachinesState(busIds);
            if (!StringUtil.isNullOrEmpty(list)) {
                for (MachineDevice machineDevice : list) {
                    resMap.put(machineDevice.getBusId(), machineDevice);
                }
            }
        }
        logger.info("getMachineDevice resMap:" + JSON.toJSONString(resMap));
        return resMap;
    }

    @Override
    public MachineDevice getMachineDevice(int busId) {
        return iMachineRemoteService.getMachineState(busId);
    }

    @Override
    public int abandon(long uid, int busId) {
        logger.info("abandon==========================================>" + uid + "," + busId);
        DollBusStatus dollBusStatus = busStatusDAO.getDollBusStatusByBusId(busId);
        if (dollBusStatus != null) {
            if (dollBusStatus.getUserId() == uid && dollBusStatus.getKeepTime() != null) {
                if (busStatusDAO.deleteDollBusStatusByBusId(dollBusStatus.getBusId()) > 0) {
                    DollBus dollBus = dollBusDao.getDollBus(dollBusStatus.getBusId());
                    if (dollBus != null) {
                        OptRecord optRecord = customerOptRecordDao.getEntityByOptId(dollBus.getGroupId(), dollBusStatus.getOptId());
                        if (optRecord != null) {
                            dollBusService.sendReleaseMsg(optRecord);
                        }
                    }
                    logger.info("清除上机成功=>" + JSON.toJSONString(dollBusStatus));
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public int updateInventory(int busId, int inventory) {
        return dollBusDao.updateInventory(busId, inventory);
    }

    @Override
    public void alertInventory(final int busId) {
        SysTimerHandle.execute(new TimerTask() {
            @Override
            public void run() {
                logger.info("alertInventory...");

                //抓中库存-1
                if (updateInventory(busId, -1) > 0) {
                    logger.info("busId=>" + busId + ",库存-1");
                }

                DollBus dollBus = dollBusDao.getDollBus(busId);
                if (dollBus != null) {
                    List<Customer> customerList = customerService.getCustomerByGroupId(dollBus.getGroupId());
                    if (!StringUtil.isNullOrEmpty(customerList)) {
                        SimpleMailSender sms = MailSenderFactory.getSender(1);
                        logger.info("sms=>" + sms);
                        for (Customer customer : customerList) {

                            //判断是否开启库存警报
                            if (customer != null && customer.getInvAlert() == 1) {

                                int currentInventory = dollBus.getCurrentInventory();
                                int alertValue = dollBus.getAlertValue();

                                String resetAddress = PropertiesUtil.getString("application","reset.dollBus.inventory.url") +
                                        "key=RESET_DOLL_BUSS_INVENTORY" +
                                        "&busId=" + dollBus.getBusId()  ;
                                if (currentInventory != 0 && alertValue != 0) {
                                    //当前库存<=报警库存 需要报警
                                    if (currentInventory <= alertValue) {
                                        String mailJson = customer.getMail();

                                        String subject = "娃娃机：" + dollBus.getNickName() + " 库存警报通知!";
                                        String content =
                                                "<br>机器ID：<span style='color:red'>" + dollBus.getBusId() + "</span>" +
                                                        "<br>设备名：<span style='color:red'>" + dollBus.getNickName() + "</span>" +
                                                        "<br>当前剩余娃娃库存：<span style='color:red'>" + currentInventory + "</span>" +
                                                        "<br><br>↓↓↓↓↓请及时补充娃娃库存,补充完成之后点击下面的链接即可重新开始统计↓↓↓↓↓" +
                                                        "<br><a href='" + resetAddress + "' style='color:blue'>补充完娃娃库存之后点击此链接重新统计</a>";

                                        if (null != mailJson && !"".equals(mailJson)) {
                                            List<String> mailList = JSON.parseArray(mailJson, String.class);
                                            logger.info("alertInventory : 邮件列表=>" + JSON.toJSONString(mailList));
                                            if (!StringUtil.isNullOrEmpty(mailList)) {
                                                if (sms != null) {
                                                    logger.info("alertInventory=>发送库存警报邮件...");
                                                    for (String mail : mailList){
                                                        try {
                                                            sms.send(mail, subject, content);
                                                            logger.info("发送邮件成功=>" + mail);
                                                        } catch (MessagingException e) {
                                                            logger.info("发送邮件失败=>" + e.getMessage() + ",失败邮箱=>" + mail);
                                                            logger.error(e.getMessage());
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 0);
    }

    @Override
    public int resetInventory(int busId) {
        return dollBusDao.resetInventory(busId);
    }

    @Override
    public int updateValid(int busId, int valid) {
        return dollBusDao.updateValid(busId,valid);
    }

    @Override
    public List<DollBus> getDollBus(List<Integer> busIds) {
        return dollBusDao.getDollBus(busIds);
    }

    @Override
    public Map<Integer, DollBus> getDollBusByDeviceIds(List<String> deviceIds) {
        List<DollBus> dollBusList = dollBusDao.getDollBusByDeviceIds(deviceIds);
        if (!StringUtil.isNullOrEmpty(dollBusList)) {
            Map<Integer, DollBus> dollBusMap = new HashMap<>();
            for (DollBus dollBus : dollBusList) {
                dollBusMap.put(dollBus.getBusId(), dollBus);
            }
            return dollBusMap;
        }
        return new HashMap<>();
    }


    /**
     * 获取最新的机器列表
     *
     * @param oldList 旧的娃娃机列表
     * @return
     */
    private List<DollBus> getNewestDollBusList(List<DollBus> oldList) {
        if (!StringUtil.isNullOrEmpty(oldList)) {
            List<Integer> busIds = new ArrayList<>();
            for (DollBus dollBus : oldList) {
                busIds.add(dollBus.getBusId());
            }
            List<MachineDevice> list = iMachineRemoteService.getMachinesState(busIds);
            Map<Integer, MachineDevice> deviceMap = new HashMap<>();
            if (!StringUtil.isNullOrEmpty(list)) {
                for (MachineDevice machineDevice : list) {
                    deviceMap.put(machineDevice.getBusId(), machineDevice);
                }
            }
            List<DollBus> newDollBusList = new ArrayList<>();
            for (DollBus dollBus : oldList) {
                MachineDevice machineDevice = deviceMap.get(dollBus.getBusId());
                if (machineDevice != null) {
                    dollBus.setStatus(machineDevice.getStatus());
                    dollBus.setDeviceId(machineDevice.getDeviceId());
                } else {
                    //虚拟机
                    if (dollBus.getVirtual() == 1){
                        //默认空闲
                        dollBus.setStatus(0);
                    }else {
                        dollBus.setStatus(MachineStateConst.BROKEN);
                    }
                }
                newDollBusList.add(dollBus);
            }
            return newDollBusList;
        }
        return null;
    }
}
