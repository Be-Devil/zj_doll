package com.imlianai.dollpub.app.modules.core.api.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.core.api.dao.DollBusStatusDAO;
import com.imlianai.dollpub.constants.MachineStateConst;
import com.imlianai.dollpub.domain.doll.DollBusStatus;
import com.imlianai.rpc.support.common.BaseLogger;
@Service
public class DollBusStatusServiceImpl implements DollBusStatusService {

	private BaseLogger logger = BaseLogger.getLogger(getClass());

	
	@Resource
	private DollBusStatusDAO busStatusDAO;
	
	@Override
	public DollBusStatus getDollBusStatusByBusId(int busId) {
		return busStatusDAO.getDollBusStatusByBusId(busId);
	}

	@Override
	public List<DollBusStatus> getInvaildRecord(int condition) {
		return busStatusDAO.getInvaildRecord(condition);
	}

	@Override
	public int deleteDollBusStatusByBusId(int busId) {
		return busStatusDAO.deleteDollBusStatusByBusId(busId);
	}

	@Override
	public int updateKeepTime(int busId, Date keepTime) {
		return busStatusDAO.updateDollBusStatusKeepTime(busId, keepTime);
	}

	@Override
	public boolean isApply(int busId, long uid) {
		DollBusStatus dollBusStatus = busStatusDAO.getDollBusStatusByBusId(busId);
//		logger.info("dollBusStatus=============================>" + JSON.toJSONString(dollBusStatus));
//		//空闲
		if (dollBusStatus == null || dollBusStatus.getStatus() == MachineStateConst.LEISURE) {
			return true;
		}
		
		if(dollBusStatus != null && dollBusStatus.getUserId() == uid) {
			return true;
		}
		
		return false;
	}

}
