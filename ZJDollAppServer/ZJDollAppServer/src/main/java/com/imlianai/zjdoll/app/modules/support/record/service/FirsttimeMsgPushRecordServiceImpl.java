package com.imlianai.zjdoll.app.modules.support.record.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.app.modules.support.record.dao.FirsttimeMsgPushRecordDao;

@Service
public class FirsttimeMsgPushRecordServiceImpl implements FirsttimeMsgPushRecordService {
	
	@Resource
	FirsttimeMsgPushRecordDao firsttimeMsgPushRecordDao;

	@Override
	public boolean isPush(Long uid, int type, int num) {
		if(firsttimeMsgPushRecordDao.getFirsttimeMsgPushRecord(uid, type, num) != null) {
			return true;
		}
		return false;
	}

	@Override
	public int saveFirsttimeMsgPushRecord(Long uid, int type, int num) {
		return firsttimeMsgPushRecordDao.saveFirsttimeMsgPushRecord(uid, type , num);
	}
}
