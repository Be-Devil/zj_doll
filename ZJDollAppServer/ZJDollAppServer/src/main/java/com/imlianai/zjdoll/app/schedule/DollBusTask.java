package com.imlianai.zjdoll.app.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.BusOperatingRecord;
import com.imlianai.zjdoll.domain.doll.DollBus.DollBusCompany;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.core.doll.service.DollService;
import com.imlianai.zjdoll.app.modules.core.doll.utils.DollUtil;
import com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo.QiyiguoDollResult;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.ZengjingUtils;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.domain.ZengjingDollResult;

@Component
public class DollBusTask {

	@Resource
	DollBusService dollBusService;
	@Resource
	DollService dollService;
	@Resource
	DollRecordService dollRecordService;
	protected final BaseLogger logger = BaseLogger.getLogger(this
			.getClass());
	/**
	 * 定时清理已到期的上机记录
	 */
	public void handleBusInvalidOpt() {
		List<BusOperatingRecord> list = dollBusService.getInvaildRecord();
		if (!StringUtil.isNullOrEmpty(list)) {
			for (BusOperatingRecord record : list) {
				if (record.getEndTime() == null) {// 尚未推送结束消息
					DollOptRecord dollOptRecord = dollRecordService.getOptRecord(record.getOptId());
					logger.info("handleBusInvalidOpt dollOptRecord:"+JSON.toJSONString(dollOptRecord));
					boolean isSuccess = false;
					int logId = record.getLogId();
					int type = DollBusCompany.QIYIGUO.type;
					if(dollOptRecord.getDeviceCompany() == DollBusCompany.QIYIGUO.type) { // 奇艺果
						QiyiguoDollResult result = DollUtil.getDollResult(record
								.getLogId());
						if (result!=null&&result.isSuccessCatch()) {
							isSuccess = true;
						}
					} else if(dollOptRecord.getDeviceCompany() == DollBusCompany.ZENGJING.type){ // 增景
						ZengjingDollResult result = ZengjingUtils.getDollResult(record.getUid(), record.getLogId());
						type = DollBusCompany.ZENGJING.type;
						if(result != null && result.getOptRecord() != null 
								&& result.getOptRecord().getResult() == 1) {
							isSuccess = true;
						}
					}
					if (isSuccess) {
						dollService.handleResult(logId, 1, type);
					}else{
						dollService.handleResult(logId, 0, type);
					}
				}
				logger.info("handleBusInvalidOpt removeBusOperatingRecord record:"+JSON.toJSONString(record));
				dollBusService.removeBusOperatingRecord(record);
				dollBusService.sendReleaseMsg(record);
			}
		}
	}
}
