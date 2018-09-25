package com.imlianai.zjdoll.app.modules.publics.cms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.manager.cache.CacheManager;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.publics.cms.dao.CmsDao;
import com.imlianai.zjdoll.app.modules.publics.cms.utils.CmsCacheUtil;
@Service
public class CmsServiceImpl implements CmsService{
	protected final BaseLogger logger = BaseLogger.getLogger(this.getClass());
	@Resource
	CacheManager cacheManager;
	@Resource
	CmsDao cmsDao;
	
	private static HashMap<String, String> configMap=new HashMap<String, String>();
	
	private static Date lastRefreshTimeDate=null;
	@Override
	@CacheWrite(validTime=5)
	public String getTextConfig(String key) {
		String value=cmsDao.getTextConf(key);
		//initTextConfig();
		//String value=configMap.get(key);
		return value;
	}
	
	private void initTextConfig(){
		if (lastRefreshTimeDate==null||DateUtils.minuteBetween(lastRefreshTimeDate)>3) {
			String jsonString=cacheManager.getString("text_cnf", 0);
			logger.info("initTextConfig text_cnf :"+jsonString);
			if (!StringUtil.isNullOrEmpty(jsonString)) {
				Map<String, String> uidMap = JSON.parseObject(jsonString,
						new TypeReference<Map<String, String>>() {
						});
				if (!StringUtil.isNullOrEmpty(uidMap)) {
					for (String mapKey : uidMap.keySet()) {
						String valueString = uidMap.get(mapKey);
						logger.info("initTextConfig mapKey:"+mapKey+" valueString:"+valueString);
						if (!StringUtil.isNullOrEmpty(valueString)) {
							configMap.put(mapKey, valueString);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isStrongUid(long uid) {
		String value=getTextConfig(CmsCacheUtil.STRONGUID);
		logger.info("isStrongUid value:"+value+" uid:"+uid);
		if (!StringUtil.isNullOrEmpty(value)) {
			if (value.contains(uid+"")) {
				return true;
			}
		}
		return false;
	}

}
