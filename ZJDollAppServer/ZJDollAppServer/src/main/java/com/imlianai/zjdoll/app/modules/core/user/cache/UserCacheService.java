package com.imlianai.zjdoll.app.modules.core.user.cache;

import java.util.List;
import java.util.Map;

import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;

/**
 * 用户缓存
 * @author Max
 *
 */
public interface UserCacheService {

	/**
	 * 批量获取用户常规信息
	 * @param uids
	 * @return
	 */
	public Map<Long, UserGeneral> getUserGeneralMap(List<Long> uids);
	/**
	 * 批量获取用户基础信息
	 * @param uids
	 * @return
	 */
	public Map<Long, UserBase> getUserBaseMap(List<Long> uids);
	/**
	 * 获取用户常规信息
	 * @param uid
	 * @return
	 */
	public UserGeneral getUserGeneral(long uid);
	/**
	 * 更新用户常规信息
	 * @param uid
	 */
	public UserGeneral updateUserGeneral(long uid);
	/**
	 * 获取用户基础信息
	 * @param uid
	 * @return
	 */
	public UserBase getUserBase(long uid);
	/**
	 * 更新用户基础信息
	 * @param uid
	 */
	public UserBase updateUserBase(long uid);
} 
