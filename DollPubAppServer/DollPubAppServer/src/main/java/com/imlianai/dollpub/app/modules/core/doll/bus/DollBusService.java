package com.imlianai.dollpub.app.modules.core.doll.bus;

import java.util.List;
import java.util.Map;

import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.BusOperatingRecord;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.optrecord.OptRecord;
import com.imlianai.dollpub.machine.iface.domain.MachineDevice;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

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
	 * 获取所有娃娃机(最新)
	 * @param start
	 * @param size
	 * @return
	 */
	public List<DollBus> getAllDollBus(int start,int size);

	/**
	 * 通过商户组获取娃娃机列表
	 *
	 * @return
	 */
	public List<DollBus> getDollBusByGroupId(int groupId,int start,int size);

	/**
	 * 获取多个娃娃机
	 * @param busIds
	 * @return
	 */
	public List<DollBus> getDollBus(List<Integer> busIds);

	/**
	 * 获取多个娃娃机
	 * @param deviceIds
	 * @return
	 */
	public Map<Integer,DollBus> getDollBusByDeviceIds(List<String> deviceIds);


	/**
	 * 获取娃娃机列表
	 * 
	 * @return
	 */
	public List<DollBus> getDollBus();

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
	 * @param record
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
	
	public DollBus getDollBusByDeviceId(String deviceId);
	public int getDollBusValidByBusId(int busId);

	/**
	 * 获取娃娃机状态
	 * @param busIds
	 * @return
	 */
	public Map<Integer, MachineDevice> getMachineDevice(List<Integer> busIds);

	/**
	 * 获取娃娃机状态
	 * @param busId
	 * @return
	 */
	public MachineDevice getMachineDevice(int busId);

	/**
	 * 发送娃娃机下机消息
	 * @param record
	 */
	public void sendReleaseMsg(OptRecord record);
	
	
	public int abandon(long uid,int busId);

	/**
	 * 更新库存
	 * @param busId
	 * @param inventory
	 * @return
	 */
	int updateInventory(int busId,int inventory);

	/**
	 * 库存警报
	 * @param busId
	 */
	void alertInventory(int busId);

    int resetInventory(int busId);

	/**
	 * 更新valid状态
	 * @param busId
	 * @return
	 */
	int updateValid(int busId,int valid);
}
