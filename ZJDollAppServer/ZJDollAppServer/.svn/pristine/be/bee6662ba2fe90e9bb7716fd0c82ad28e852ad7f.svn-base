package com.imlianai.zjdoll.app.modules.core.doll.list;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollClassifyRes;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo.QiyiguoMachine;

/**
 * 娃娃机列表
 * @author tensloveq
 *
 */
public interface DollListService {

	
	/**
	 * 获取娃娃机列表
	 * @param type
	 * @return
	 */
	public List<DollBus> getBusList(Integer type);
	
	/**
	 * 获取娃娃机信息
	 * @param busId
	 * @return
	 */
	public DollBus getDollBus(int busId,boolean refresh);
	
	/**
	 * 更新娃娃机占用情况
	 */
	public void refreshBusStatus();
	
	/**
	 * 获取用户娃娃列表
	 * @param uid
	 * @param lastId
	 * @param status 
	 * @return
	 */
	List<UserDoll> getDollListByPage(Long uid, long lastId, int pageSize, int status);

	/**
	 * 获取奇艺果娃娃机状态
	 * @param deviceId
	 * @param refresh-强制刷新
	 * @return
	 */
	public QiyiguoMachine getQiyiguoMachineByDeviceId(String deviceId,boolean refresh);
	
	/**
	 * 按分类获取娃娃机列表
	 * @param classify
	 * @return
	 */
	public List<DollBus> getDollBusByClassify(int classify);
	
	/**
	 * 获取娃娃分类
	 * @return
	 */
	public List<DollClassifyRes> getDollClassifies();
	
	/**
	 * 搜索相关机器
	 * @param keyword
	 * @return
	 */
	public List<Integer> searchBus(String keyword);

	/**
	 * 获取娃娃机信息列表
	 * @param busIds
	 * @return
	 */
	public List<DollBus> getDollBus(List<Integer> busIds);

	/**
	 * 获取用户娃娃列表(130版本后)
	 * @param uid
	 * @param lastId
	 * @param dollPageSize
	 * @param status
	 * @return
	 */
	public List<UserDoll> getDollListByPageAfter130(Long uid, long lastId, int dollPageSize, int status);
	

}
