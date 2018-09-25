package com.imlianai.zjdoll.app.modules.support.heartbeat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.supply.heartbeat.domain.Message;
import com.imlianai.supply.heartbeat.iface.HeartBeatCheckService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhicheng on 2018/1/26.
 */
//@Component
public class HeartBeatCheckTask implements InitializingBean {
    private static final BaseLogger logger = BaseLogger.getLogger(HeartBeatCheckTask.class);
    @Reference
    HeartBeatCheckService heartBeatCheckService;

    private static final String BUSINESS_LINE="doll";
    private static final String BUSINESS_MODEL="doll-app";

    @Override
    public void afterPropertiesSet() throws Exception {

        final Message message=new Message();
        message.setBusiness_line(BUSINESS_LINE);
        message.setBusiness_model(BUSINESS_MODEL);
        Runnable checkRunner=new Runnable() {
            @Override
            public void run() {
                while (true){
                    logger.info("heartbeatcheck start....");
                    try{
                        if (heartBeatCheckService!=null){
                            logger.info("heartbeatcheck heartBeatCheckService!=null");
                            message.setSendTimes(new Date().getTime());
                            InetAddress inetAddress=InetAddress.getLocalHost();
                            String ip=inetAddress.getHostAddress();
                            message.setIp(ip);
                            String re=heartBeatCheckService.ping(message);
                            logger.info("heartbeatcheck result="+re);
                        }else{
                            logger.warn("heartbeatcheck heartBeatCheckService=null,heartbeat-rpc注入失败");
                        }
                    }catch (Exception e){
                    }finally {
                        try {
                            TimeUnit.SECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Thread thread=new Thread(checkRunner);
        thread.start();
    }
}
