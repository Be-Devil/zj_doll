package com.imlianai.zjdoll.app.modules.support.dailytask.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.dailytask.DailytaskTasks;
import com.imlianai.zjdoll.domain.dailytask.DailytaskUserActiveness;
import com.imlianai.zjdoll.domain.dailytask.DailytaskUserBox;
import com.imlianai.zjdoll.domain.dailytask.DailytaskUserTask;

public interface DailytaskDao {

	/**
	 * 获取用户当日活跃度
	 * @param uid
	 * @param dateCode
	 * @return
	 */
	DailytaskUserActiveness getUserActiveness(Long uid, int dateCode);

	/**
	 * 获取用户宝箱
	 * @param uid
	 * @param dateCode
	 * @param boxId
	 * @return
	 */
	DailytaskUserBox getUserBox(Long uid, int dateCode, int boxId);

	/**
	 * 每日任务列表
	 * @return
	 */
	List<DailytaskTasks> getDailytaskTasks();

	/**
	 * 获取用户任务
	 * @param uid
	 * @param dateCode
	 * @param taskId
	 * @return
	 */
	DailytaskUserTask getUserTask(Long uid, int dateCode, Long taskId);

	/**
	 * 修改用户任务状态
	 * @param dateCode
	 * @param uid
	 * @param taskId
	 * @param status
	 * @return
	 */
	int updateUserTaskStatus(int dateCode, Long uid, long taskId, int status);

	/**
	 * 保存or更新用户活跃度值
	 * @param uid
	 * @param dateCode
	 * @param value
	 * @return
	 */
	int saveOrUpdateUserActiveness(Long uid, int dateCode, int value);

	/**
	 * 保存用户活跃度记录
	 * @param uid
	 * @param value
	 * @param remark
	 * @return
	 */
	int saveUserActivenessRecord(Long uid, int value, String remark);

	/**
	 * 修改用户宝箱状态
	 * @param uid
	 * @param dateCode
	 * @param boxId
	 * @return
	 */
	int updateUserBoxStatus(Long uid, int dateCode, int boxId);

	/**
	 * 保存or修改用户任务信息
	 * @param uid
	 * @param dateCode
	 * @param taskId
	 * @param status
	 * @param currNum
	 * @return
	 */
	int saveOrUpdateUserTask(Long uid, int dateCode, long taskId, int status, int currNum);

	/**
	 * 保存or更新用户宝箱信息
	 * @param uid
	 * @param dateCode
	 * @param boxId
	 * @return
	 */
	int saveOrUpdateUserBox(Long uid, int dateCode, int boxId);

	/**
	 * 获取用户任务数量
	 * @param uid
	 * @param dateCode
	 * @param status
	 * @return
	 */
	int getUserTaskCount(Long uid, int dateCode, int status);

	/**
	 * 获取用户宝箱数量
	 * @param uid
	 * @param dateCode
	 * @param status
	 * @return
	 */
	int getUserBoxCountByParams(Long uid, int dateCode, int status);

	/**
	 * 获取用户活跃度列表
	 * @param dateCode
	 * @param value
	 * @return
	 */
	List<DailytaskUserActiveness> getUserActivenessListByParams(int dateCode, int value);

}
