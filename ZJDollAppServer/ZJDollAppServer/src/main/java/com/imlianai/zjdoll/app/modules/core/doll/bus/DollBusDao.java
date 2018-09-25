package com.imlianai.zjdoll.app.modules.core.doll.bus;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.BusOperatingRecord;
import com.imlianai.zjdoll.domain.doll.DollBus;

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
	 * @param type
	 * @return
	 */
	public List<DollBus> getDollBus(Integer type);

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

	/**
	 * 更新娃娃机状态
	 * @param busId
	 * @param valid
	 * @param remark
	 * @return
	 */
	int updateBusValid(int busId, int valid, String remark);


	List<DollBus> getAllDollBus();

}