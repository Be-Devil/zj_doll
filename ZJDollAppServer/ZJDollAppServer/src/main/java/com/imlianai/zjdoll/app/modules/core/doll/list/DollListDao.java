package com.imlianai.zjdoll.app.modules.core.doll.list;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.DollClassify;
import com.imlianai.zjdoll.domain.doll.DollClassifyRes;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;

public interface DollListDao {


	/**
	 * 获取用户娃娃列表
	 * @param uid
	 * @param lastId
	 * @param status 
	 * @return
	 */
	public List<UserDoll> getDollListByPage(Long uid, long lastId, int pageSize, int status);
	

	/**
	 * 获取娃娃分类
	 * @return
	 */
	public List<DollClassifyRes> getDollClassifies();
	
	/**
	 * 根据分类id获取娃娃机id
	 * @param classify
	 * @return
	 */
	public List<Integer> getBusIdsByClassify(int classify);

	/**
	 * 获取全部标签
	 * @return
	 */
	public List<DollClassify> getAllClassify();

	/**
	 * 获取用户娃娃列表
	 * @param uid
	 * @param lastId
	 * @param status 
	 * @return
	 */
	public List<UserDoll> getDollListByPageAfter130(Long uid, long lastId, int pageSize, int status);
}
