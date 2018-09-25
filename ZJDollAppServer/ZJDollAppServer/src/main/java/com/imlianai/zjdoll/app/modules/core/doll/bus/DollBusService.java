package com.imlianai.zjdoll.app.modules.core.doll.bus;

import java.util.List;
import java.util.Map;

import com.imlianai.zjdoll.domain.doll.BusOperatingRecord;
import com.imlianai.zjdoll.domain.doll.DollBus;

public interface DollBusService {

	/**
	 * 更新会话id
	 * 
	 * @param busId
	 * @param conversationId
	 * @return
	 */
	public int updateConversationId(int busId, String conversationId);

	/**
	 * 获取娃娃机
	 * 
	 * @param busId
	 * @return
	 */
	public DollBus getDollBus(int busId);

	/**
	 * 获取娃娃机列表
	 * 
	 * @param type
	 * @return
	 */
	public List<DollBus> getDollBus(Integer type);

	/**
	 * 更新娃娃机当前在线人数
	 * 
	 * @param num
	 *            增加数值（非最终数量）
	 */
	public void updateBusWatchCount(int busId, int num);

	/**
	 * 获取当前bus操作者
	 * 
	 * @param busId
	 * @return
	 */
	public BusOperatingRecord getBusOperatingRecord(int busId);
	
	/**
	 * 从内存获取当前操作者
	 * @param busId
	 * @return
	 */
	public BusOperatingRecord getBusOperatingRecordLocal(int busId);
	
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
	public int removeBusOperatingRecord(BusOperatingRecord record);
	
	/**
	 * 获取已过期的上机记录
	 * @return
	 */
	public List<BusOperatingRecord> getInvaildRecord();
	
	/**
	 * 获取当前设备状态-主要用于后台，busId为空
	 * @return
	 */
	public List<DollBus> getCurrentDevice();
	
	/**
	 * 发送释放娃娃机消息
	 * @param record
	 */
	public void sendReleaseMsg(BusOperatingRecord record);


	Map<Integer,DollBus> getDollBusMap(List<Integer> busIds);


	List<DollBus> getAllDollBus();

}
