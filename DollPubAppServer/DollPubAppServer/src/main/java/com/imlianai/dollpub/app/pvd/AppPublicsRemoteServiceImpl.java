package com.imlianai.dollpub.app.pvd;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.imlianai.dollpub.app.iface.IAppPublicsRemoteService;
import com.imlianai.dollpub.app.modules.publics.live.service.LiveService;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.domain.live.LiveUrlSet;
import com.imlianai.dollpub.domain.msg.Msg;
import com.imlianai.rpc.support.common.BaseLogger;

/**
 * Created by sam on 17-7-25.
 */
@Service(interfaceClass = IAppPublicsRemoteService.class)
public class AppPublicsRemoteServiceImpl implements IAppPublicsRemoteService {
    private final BaseLogger logger = BaseLogger.getLogger(this.getClass());

    @Resource
    MsgService msgService;
    @Resource
    LiveService liveService;
    @Override
    public List<Object> testAppCall(List<Object> objLst, String msg, byte byteVal, short shortVal, int intVal,
                                    long longVal, char charVal, boolean boolVal, float floatVal, double doubleVal) {
        String ret=msg+"->"+byteVal+"->"+shortVal+"->"+intVal+"->"+longVal+"->"+charVal+"->"+
                boolVal+"->"+floatVal+"->"+doubleVal;
        logger.error("=debug: " +ret);
        objLst.add(ret);

        return objLst;
    }
    
    /**
	 * 发送普通消息
	 * 
	 * @param msg
	 */
    @Override
	public void msgSendMsg(Msg msg){
		msgService.sendMsg(msg);
	}
	
	/**
	 * 发送全站消息
	 * @param msg
	 */
    @Override
	public void msgSendSysMsg(Msg msg){
		msgService.sendSysMsg(msg);
	}
	
	/**
	 * 发送全站在线消息
	 * @param msg
	 */
    @Override
	public void msgSendOnlineSysMsg(Msg msg){
		msgService.sendOnlineSysMsg(msg);
	}
    @Override
    public LiveUrlSet liveGetLiveUrlSet(int customerGroup, int busId){
    	return liveService.getLiveUrlSet(customerGroup, busId);
    }
}
