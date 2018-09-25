package com.imlianai.dollpub.app.modules.core.doll.bus;

import java.util.List;

import com.imlianai.dollpub.domain.doll.BusOperatingRecord;
import com.imlianai.dollpub.domain.doll.DollBus;

public interface DollBusDao {

	/**
	 * 更新会话id
	 * @param busId
	 * @param conversationId
	 * @return
	 */
	public int updateConversationId(int busId,String conversationId);
	
	/**
	 * 获取娃娃机列表
	 * 
	 * @return
	 */
	public List<DollBus> getDollBus();


	/**
	 * 获取所有娃娃机列表
	 *
	 * @return
	 */
	public List<DollBus> getDollBusList(int start,int size);
	
	/**
	 * 获取多个娃娃机
	 * @param busIds
	 * @return
	 */
	public List<DollBus> getDollBus(List<Integer> busIds);
	
	/**
	 * 获取娃娃机
	 * @param busId
	 * @return
	 */
	public DollBus getDollBus(int busId);
	
	/**
	 * 获取当前bus操作者
	 * 
	 * @param busId
	 * @return
	 */
	public BusOperatingRecord getBusOperatingRecord(int busId);
	
	/**
	 * 记录上机
	 * @param busId
	 * @param uid
	 * @param optId
	 * @param logId
	 * @return
	 */
	public int addBusOperatingRecord(int busId,long uid,long optId,long logId);
	
	/**
	 * 完成上机捉取
	 * @param busId
	 * @return
	 */
	public int closeBusOperatingRecord(int busId,long optId);
	
	/**
	 * 清理上机操作
	 * @param busId
	 * @return
	 */
	public int removeBusOperatingRecord(int busId,long optId);
	
	/**
	 * 获取已过期的上机记录
	 * @return
	 */
	public List<BusOperatingRecord> getInvaildRecord();
	
	/**
	 * 更新娃娃机当前在线人数
	 * 
	 * @param num
	 *            增加数值（非最终数量）
	 */
	public void updateBusWatchCount(int busId, int num);
	
	public DollBus getDollBus(String deviceId);

	public List<DollBus> getDollBusByDeviceIds(List<String> deviceIds);

	public int getDollBusValidByBusId(int busId);
	
	/**
	 * 通过商户组获取娃娃机列表
	 * 
	 * @return
	 */
	public List<DollBus> getDollBusByGroupId(int groupId,int start,int size);
	
	/**
	 * 获取商户娃娃机总数
	 * @param groupId
	 * @return
	 */
	public int getDollBusCountByGroupId(int groupId);
	
	/**
	 * 更新机器状态
	 * @param busId
	 * @param status
	 * @return
	 */
	public int updateApplyStatus(int busId,int status);

	/**
	 * 更新库存
	 * @param busId
	 * @param currentInventory
	 * @return
	 */
	public int updateInventory(int busId,int currentInventory);

	int resetInventory(int busId);

	/**
	 * 更新valid状态
	 * @param busId
	 * @return
	 */
	int updateValid(int busId,int valid);

}
