package com.imlianai.dollpub.app.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.domain.doll.BusOperatingRecord;
import com.imlianai.rpc.support.utils.StringUtil;

@Component
public class DollBusTask {

	@Resource
	DollBusService dollBusService;


	/**
	 * 定时清理已到期的上机记录
	 */
	public void handleBusInvalidOpt() {
		List<BusOperatingRecord> list = dollBusService.getInvaildRecord();
		if (!StringUtil.isNullOrEmpty(list)) {
			for (BusOperatingRecord record : list) {
				if (record.getEndTime() == null) {// 尚未推送结束消息
				}
				dollBusService.removeBusOperatingRecord(record);
				dollBusService.sendReleaseMsg(record);
			}
		}
	}
}
