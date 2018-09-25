package com.imlianai.zjdoll.app.pvd;

import java.util.List;
import java.util.TimerTask;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.SysTimerHandle;
import com.imlianai.zjdoll.app.iface.IAppPublicsRemoteService;
import com.imlianai.zjdoll.app.modules.publics.live.records.service.LiveRecordsService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.domain.msg.Msg;

/**
 * Created by sam on 17-7-25.
 */
@Service(interfaceClass = IAppPublicsRemoteService.class)
public class AppPublicsRemoteServiceImpl implements IAppPublicsRemoteService {
    private final BaseLogger logger = BaseLogger.getLogger(this.getClass());

    @Resource
    MsgService msgService;
    @Resource
    LiveRecordsService liveRecordsService;
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
    public void liveSaveRecordVideo(final long optId){
    	SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					logger.info("liveSaveRecordVideo now:" + DateUtils.getNowTime()+" optId："+optId);
					liveRecordsService.saveQiniuVideoRecords(optId);
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
    }
}
