package com.imlianai.zjdoll.app.modules.support.dailytask.service;

import java.util.List;

import com.imlianai.zjdoll.domain.dailytask.DailytaskUserActiveness;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.HandleInviteReqVO;

public interface DailytaskService {

	/**
	 * 领取活跃度
	 * @param uid
	 * @param taskId
	 * @return
	 */
	BaseRespVO getActiveness(Long uid, long taskId);

	/**
	 * 每日任务信息
	 * @param reqVO
	 * @return
	 */
	BaseRespVO getDailytaskInfo(BaseReqVO reqVO);

	/**
	 * 开启宝箱
	 * @param uid
	 * @param boxId
	 * @return
	 */
	BaseRespVO openBox(Long uid, int boxId);
	
	/**
	 * 保存or修改用户任务信息
	 * @param uid
	 * @param taskId
	 * @param num
	 * @return
	 */
	void saveOrUpdateUserTask(Long uid, long taskId, int num);

	/**
	 * 抓娃娃处理
	 * @param record
	 */
	void handleCatchDoll(DollOptRecord record);

	/**
	 * 充值处理
	 * @param cost
	 * @param uid
	 */
	void handleRecharge(double cost, long uid);

	/**
	 * 获取可领取活跃度的任务数量
	 * @param uid
	 * @return
	 */
	int getCount(Long uid);

	/**
	 * 分享邀请处理
	 * @param reqVO
	 * @return
	 */
	BaseRespVO handleInvite(HandleInviteReqVO reqVO);

	/**
	 * 获取用户活跃度列表
	 * @param dateCode
	 * @param value
	 * @return
	 */
	List<DailytaskUserActiveness> getUserActivenessListByParams(int dateCode, int value);

	/**
	 * 抓中娃娃处理
	 * @param record
	 */
	void handleSuccCatchDoll(DollOptRecord record);
}
