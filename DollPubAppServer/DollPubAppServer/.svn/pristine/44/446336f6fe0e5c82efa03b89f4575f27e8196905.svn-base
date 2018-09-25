package com.imlianai.dollpub.app.modules.support.idfa.service;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.support.idfa.dao.IdfaDao;
import com.imlianai.dollpub.app.modules.support.idfa.util.IdfaUtil;
import com.imlianai.dollpub.domain.idfa.IdfaRecord;
import com.imlianai.rpc.support.utils.HttpUtil;
@Service
public class IdfaServiceImpl implements IdfaService {

	@Resource
	private IdfaDao dao;

	@Override
	public int init(IdfaRecord record) {
		return dao.init(record);
	}

	@Override
	public int active(String idfa, String imei) {
		int result = dao.active(idfa, imei);
		IdfaRecord record = null;
		if (result != 1 || (record = dao.get(idfa)) == null)
			return result;
		String url = IdfaUtil.getZtUrl(idfa, record.getActip());
		HttpUtil.Get(url);
		return 1;
	}

	@Override
	public int register(long uid, String imei) {
		return dao.register(uid, imei);
	}

}
