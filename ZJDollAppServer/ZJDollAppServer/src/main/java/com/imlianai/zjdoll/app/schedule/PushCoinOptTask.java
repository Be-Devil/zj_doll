package com.imlianai.zjdoll.app.schedule;


import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.zjdoll.app.modules.core.push.service.PushCoinService;
import com.imlianai.zjdoll.app.modules.core.push.virtual.service.PushCoinVirtualService;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;


/**
 * 推币机相关定时器
 * @author cls
 *
 */
@Component
public class PushCoinOptTask {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(PushCoinOptTask.class);
	
	@Resource
	private PushCoinService pushCoinService;

	@Resource
	private PushCoinVirtualService pushCoinVirtualService;

	public void handleTimeOutRecord() {
		pushCoinService.handleTimeOutRecord();
		pushCoinVirtualService.handleTimeOutRecord();
	}
}
