package com.imlianai.zjdoll.app.modules.core.egg.service;


import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.core.egg.vo.AccountInfo;
import com.imlianai.zjdoll.app.modules.core.egg.vo.EggInfo;
import com.imlianai.zjdoll.app.modules.core.egg.vo.EggMachineInfo;
import com.imlianai.zjdoll.app.modules.core.egg.vo.PlayReqVO;
import com.imlianai.zjdoll.domain.egg.EggMacClassifyInfo;
import com.imlianai.zjdoll.domain.egg.EggMachine;

import java.util.List;

public interface EggMachineService {

	/**
	 * 获取扭蛋机分类列表
	 * @return
	 */
	List<EggMacClassifyInfo> getEggMacClassifies();

	/**
	 * 获取扭蛋机列表
	 * @param type
	 * @return
	 */
	List<EggMachine> getEggMachines(int type);

	/**
	 * 获取扭蛋机信息
	 * @param machineId
	 * @param uid 
	 * @return
	 */
	EggMachineInfo getEggMachineInfo(long machineId, Long uid);

	/**
	 * 获取用户账户信息
	 * @param uid
	 * @return
	 */
	AccountInfo getAccountInfo(Long uid);

	/**
	 * 用户上机
	 * @param reqVO
	 * @return
	 */
	BaseRespVO play(PlayReqVO reqVO);

	/**
	 * 自动发放奖励
	 */
	void autoHandleReward();

	/**
	 * 获取扭蛋详情
	 * @param machineId
	 * @return
	 */
	List<EggInfo> getEggDetails(long machineId);

	/**
	 * 用户领取奖品
	 * @param optId
	 * @return
	 */
	BaseRespVO getRewards(long optId);

	/**
	 * 获取充值列表
	 * @param uid
	 * @return
	 */
	BaseRespVO getRechargeList(Long uid);

}
