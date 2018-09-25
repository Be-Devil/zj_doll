package com.imlianai.zjdoll.app.modules.support.exchange.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.doll.DollGetRecord;
import com.imlianai.zjdoll.app.modules.support.exchange.dao.DollGetDao;

@Service
public class DollGetServiceImpl implements DollGetService {

	@Resource
	DollGetDao dollGetDao;
	
	@Override
	public int saveDollGetRecord(DollGetRecord record) {
		return dollGetDao.saveDollGetRecord(record);
	}

	@Override
	public DollGetRecord getDollGetRecord(Long uDollId) {
		return dollGetDao.getDollGetRecord(uDollId);
	}
}
