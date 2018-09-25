package com.imlianai.dollpub.app.modules.support.exchange.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.support.exchange.dao.DollGetDao;
import com.imlianai.dollpub.domain.doll.DollGetRecord;

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
