package com.imlianai.dollpub.app.modules.core.doll.list;

import java.util.List;

import com.imlianai.dollpub.app.modules.core.doll.utils.qiyiguo.QiyiguoMachine;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.doll.user.UserDoll;

/**
 * 娃娃机列表
 * @author tensloveq
 *
 */
public interface DollListService {

	
	/**
	 * 获取娃娃机列表
	 * @return
	 */
	public List<DollBus> getBusList();
	
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
}
