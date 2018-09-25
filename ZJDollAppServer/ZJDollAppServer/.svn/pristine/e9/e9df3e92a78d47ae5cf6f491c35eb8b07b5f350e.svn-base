package com.imlianai.zjdoll.app.modules.support.repair.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.constants.DollCacheConst;
import com.imlianai.rpc.support.manager.cache.CacheManager;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.support.repair.dao.RepairDao;

@Service
public class RepairServiceImpl implements RepairService {

	@Resource
	RepairDao repairDao;
	@Resource
	CacheManager cacheManager;

	@Override
	public int addReocrd(int busId, long uid, String reason) {
		String value=cacheManager.getString(DollCacheConst.REPAIR_RECORD+"_"+uid+"_"+busId, 0);
		if (StringUtil.isNullOrEmpty(value)) {
			repairDao.addReocrd(busId, uid, reason);
			cacheManager.setString(DollCacheConst.REPAIR_RECORD+"_"+uid+"_"+busId, "ok", 0);
			return 1;
		}
		return 0;
	}
}
