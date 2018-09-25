package com.imlianai.dollpub.app.modules.core.api.service;

import java.util.List;

import com.imlianai.dollpub.app.modules.core.api.vo.DollBusApplyMachineRespVO;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.optrecord.OptRecord;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public interface CustomerDollBusService {

	/**
	 * 获取客户娃娃机列表
	 * 
	 * @param customer
	 * @param start
	 * @param size
	 * @return
	 */
	public List<DollBus> getCustomerDollBusList(Customer customer, int start, int size);

	/**
	 * 申请上机
	 * 
	 * @param uid
	 * @param busId
	 * @param customer
	 * @param remark
	 * @return
	 */
	public DollBusApplyMachineRespVO applyMachine(long uid, int busId, Customer customer,String remark,int type);

	/**
	 * 娃娃机操作【条件：先分配设备且在游戏内才可以操作】
	 * 
	 * @param busId
	 * @param uid
	 * @param optId
	 * @param type
	 *            【1】向前移动，【2】向后移动，【3】向左移动，【4】向右移动，【5】开始游戏，【6】下抓，【7】停止移动
	 * @return
	 */
	public BaseRespVO controlMachine(int busId, long uid, long optId, int type);

	/**
	 * 娃娃机下爪操作
	 * @param busId
	 * @param uid
	 * @param optId
	 * @param type 【0】默认，【1】弱爪，【2】强爪
	 * @return
	 */
	public BaseRespVO downClaw(int busId, long uid, long optId, int type);



	/**
	 * 查询操作结果
	 * 
	 * @param optId
	 * @param appId
	 * @return
	 */
	public OptRecord queryOptResult(long optId, String appId);
	
	/**
	 * 操作结果回调
	 * @param deviceId
	 * @param busId
	 * @param optId
	 * @param catchResult
	 * @param groupId
	 * @return
	 */
	public void returnMachineResult(final String deviceId, final int busId, final long optId,
			final boolean catchResult, final int groupId,String remark);
	/**
	 * 商户通知结果
	 * @param customer
	 * @param optId
	 */
	public void customerResultNotice(Customer customer, long optId);


	/**
	 * 第三方应用登陆
	 * @param srcId 第三方应用uid
	 * @param srcCode 第三方应用授权码
	 * @param customerId 商户id
	 * @return UserBase
	 */
	public BaseRespVO srcUserLogin(String srcId, String srcCode, int customerId);


	/**
	 * 判断当前机器是否属于当前商户
	 * @param busId
	 * @param appId
	 * @return
	 */
	boolean isBelongCurrentCustomer(int busId,String appId);

}
