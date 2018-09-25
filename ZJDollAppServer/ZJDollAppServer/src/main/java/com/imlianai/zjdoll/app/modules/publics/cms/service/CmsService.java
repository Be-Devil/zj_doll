package com.imlianai.zjdoll.app.modules.publics.cms.service;

public interface CmsService {

	/**
	 * 获取后台文案
	 * @param key
	 * @return
	 */
	public String getTextConfig(String key);
	
	/**
	 * 是否是强爪uid
	 * @param uid
	 * @return
	 */
	public boolean isStrongUid(long uid); 
}
