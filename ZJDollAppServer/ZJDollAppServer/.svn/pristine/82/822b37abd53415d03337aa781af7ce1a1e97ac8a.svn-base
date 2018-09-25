package com.imlianai.zjdoll.app.schedule;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.dailytask.DailytaskUserActiveness;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.dailytask.service.DailytaskService;

/**
 * 活跃度还没达到10的消息推送
 * @author cls
 *
 */
@Component
public class DailytaskTask {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(DailytaskTask.class);

	@Resource
	DailytaskService dailytaskService;
	@Resource
	MsgService msgService;

	public void pushDailytaskMsg() {
		try {
			int dateCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd")); 
			List<DailytaskUserActiveness> userActivenessList = dailytaskService.getUserActivenessListByParams(dateCode, 10);
			if(!StringUtil.isNullOrEmpty(userActivenessList)) {
				for(DailytaskUserActiveness userActiveness : userActivenessList) {
					String textString = "送你5个大宝箱，快来打开领取游戏币和现金红包吧！";
					MsgNotice msg = new MsgNotice(userActiveness.getUid(), MsgType.NOTICE_SYS.type, textString);
					msg.setJumpDailytask();
					msg.setPushMsg(textString);
					msg.setNeedSave(0);
					msgService.sendMsg(msg);
				}
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}
}
