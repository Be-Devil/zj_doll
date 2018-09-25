package com.imlianai.zjdoll.app.modules.publics.msg.rabbitmq.task;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.rpc.support.common.BaseModel;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.supply.rabbitMq.iface.RabbitMqFroZJDollRemoteService;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollListService;
import com.imlianai.zjdoll.app.modules.publics.msg.leancloud.IMMsgService;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhicheng on 2018/4/4.
 */
public class SendMsgByRoomTask implements Runnable {

    private static Logger logger = Logger.getLogger(SendMsgByRoomTask.class);
    private static final String MSG_SYSBYROOM_QUEUE_NAME="zjdollapp-msgbyroom-queue";
    private DollListService dollListService;
    private RabbitMqFroZJDollRemoteService rabbitMqRemoteService;
    private IMMsgService iMMsgService;

    public SendMsgByRoomTask(RabbitMqFroZJDollRemoteService rabbitMqRemoteService, IMMsgService iMMsgService,DollListService dollListService) {
        this.rabbitMqRemoteService = rabbitMqRemoteService;
        this.iMMsgService = iMMsgService;
        this.dollListService=dollListService;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    logger.error("InterruptedException");
                }
                Msg msg=null;
                try{
                    BaseModel baseModel = rabbitMqRemoteService.getMsgFromRabbit(MSG_SYSBYROOM_QUEUE_NAME);
                    if (baseModel!=null&&(baseModel instanceof Msg)){
                        msg= (Msg) baseModel;
                        List<DollBus> list = dollListService.getBusList(null);
                        if (!StringUtil.isNullOrEmpty(list)) {
                            for (DollBus dollBus : list) {
                                boolean sendStatus=iMMsgService.sendRoomSysMsg(msg, dollBus.getConversationId());
                                if (!sendStatus){
                                    logger.error("rabbitmq日志:sysbyRoom消息发送失败:messageID="+msg.getId()+" 发送者uid="+msg.getUid()+" 接收者uid"+msg.getTid());
                                }else{
                                    logger.info("rabbitmq日志:发送sysbyRoom消息messageID="+msg.getId()+" 发送者uid="+msg.getUid()+" 接收者uid"+msg.getTid());
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error("rabbitmq日志:sysbyRoom消息发送失败"+e);
                }
        }
    }
}