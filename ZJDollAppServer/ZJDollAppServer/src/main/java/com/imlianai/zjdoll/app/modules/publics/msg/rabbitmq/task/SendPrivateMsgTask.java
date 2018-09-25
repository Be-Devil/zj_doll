package com.imlianai.zjdoll.app.modules.publics.msg.rabbitmq.task;

import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.rpc.support.common.BaseModel;
import com.imlianai.supply.rabbitMq.iface.RabbitMqFroZJDollRemoteService;
import com.imlianai.zjdoll.app.modules.publics.msg.leancloud.IMMsgService;

import org.apache.log4j.Logger;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhicheng on 2018/4/4.
 */
public class SendPrivateMsgTask implements Runnable {

    private static Logger logger = Logger.getLogger(SendPrivateMsgTask.class);
    private static final String MSG_PRIVATE_QUEUE_NAME="zjdollapp-privatemsg-queue";

    private RabbitMqFroZJDollRemoteService rabbitMqRemoteService;
    private IMMsgService iMMsgService;

    public SendPrivateMsgTask(RabbitMqFroZJDollRemoteService rabbitMqRemoteService, IMMsgService iMMsgService) {
        this.rabbitMqRemoteService = rabbitMqRemoteService;
        this.iMMsgService = iMMsgService;
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
                    BaseModel baseModel = rabbitMqRemoteService.getMsgFromRabbit(MSG_PRIVATE_QUEUE_NAME);
                    if (baseModel!=null&&(baseModel instanceof Msg)){
                        msg= (Msg) baseModel;
                        boolean sendStatus = iMMsgService.sendMsg(msg);//发送私人消息
                        if (!sendStatus){
                            logger.error("rabbitmq日志:private消息发送失败:messageID="+msg.getId()+" 发送者uid="+msg.getUid()+" 接收者uid"+msg.getTid());
                        }else{
                            logger.info("rabbitmq日志:发送private消息messageID="+msg.getId()+" 发送者uid="+msg.getUid()+" 接收者uid"+msg.getTid());
                        }
                    }
                }catch (Exception e){
                    logger.error("rabbitmq日志:private消息发送失败"+e);
                }
        }
    }
}
