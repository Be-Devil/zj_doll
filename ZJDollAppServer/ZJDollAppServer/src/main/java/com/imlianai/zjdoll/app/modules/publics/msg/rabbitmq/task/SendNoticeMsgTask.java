package com.imlianai.zjdoll.app.modules.publics.msg.rabbitmq.task;

import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.rpc.support.common.BaseModel;
import com.imlianai.supply.rabbitMq.iface.RabbitMqFroZJDollRemoteService;
import com.imlianai.zjdoll.app.modules.publics.umeng.service.IMPushService;

import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhicheng on 2018/4/4.
 */
public class SendNoticeMsgTask implements Runnable {

    private static Logger logger = Logger.getLogger(SendNoticeMsgTask.class);
    private static final String MSG_NOTICE_QUEUE_NAME="zjdollapp-notice-queue";

    private RabbitMqFroZJDollRemoteService rabbitMqRemoteService;
    private IMPushService iMPushService;

    public SendNoticeMsgTask(RabbitMqFroZJDollRemoteService rabbitMqRemoteService, IMPushService iMPushService) {
        this.rabbitMqRemoteService = rabbitMqRemoteService;
        this.iMPushService = iMPushService;
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
                    BaseModel baseModel = rabbitMqRemoteService.getMsgFromRabbit(MSG_NOTICE_QUEUE_NAME);
                    if (baseModel!=null&&(baseModel instanceof Msg)){
                        msg= (Msg) baseModel;
                        boolean sendStatus = iMPushService.sendPushNotice(msg);//发送通知消息
                        if (!sendStatus){
                            logger.error("rabbitmq日志:notice消息发送失败:messageID="+msg.getId()+" 发送者uid="+msg.getUid()+" 接收者="+msg.getTid());
                        }else{
                            logger.info("rabbitmq日志:发送notice消息messageID="+msg.getId()+" 发送者uid="+msg.getUid()+" 接收者="+msg.getTid());
                        }
                    }
                }catch (Exception e){
                    logger.error("rabbitmq日志:notice消息发送失败"+e);
                }
        }
    }
}