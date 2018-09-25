package com.imlianai.zjdoll.app.modules.support.dailytask.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.dailytask.DailytaskTasks;
import com.imlianai.zjdoll.domain.dailytask.DailytaskUserActiveness;
import com.imlianai.zjdoll.domain.dailytask.DailytaskUserBox;
import com.imlianai.zjdoll.domain.dailytask.DailytaskUserTask;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class DailytaskDaoImpl implements DailytaskDao {

	@Resource
	JdbcHandler jdbcHandler;

	private String getUserActiveness = "select * from dailytask_user_activeness where uid=? and dateCode=?";
	@Override
	public DailytaskUserActiveness getUserActiveness(Long uid, int dateCode) {
		return jdbcHandler.queryOneBySql(DailytaskUserActiveness.class, getUserActiveness, uid, dateCode);
	}
	
	private String getUserBox = "select * from dailytask_user_box where uid=? and dateCode=? and boxId=?";
	@Override
	public DailytaskUserBox getUserBox(Long uid, int dateCode, int boxId) {
		return jdbcHandler.queryOneBySql(DailytaskUserBox.class, getUserBox, uid, dateCode, boxId);
	}
	
	private String getDailytaskTasks = "select * from dailytask_tasks order by sortIndex asc";
	@Override
	public List<DailytaskTasks> getDailytaskTasks() {
		return jdbcHandler.queryBySql(DailytaskTasks.class, getDailytaskTasks);
	}
	
	private String getUserTask = "select * from dailytask_user_task where uid=? and dateCode=? and taskId=?";
	@Override
	public DailytaskUserTask getUserTask(Long uid, int dateCode, Long taskId) {
		return jdbcHandler.queryOneBySql(DailytaskUserTask.class, getUserTask, uid, dateCode, taskId);
	}
	
	private String updateUserTaskStatus = "update dailytask_user_task set status=?,updateTime=now() where uid=? and dateCode=? and taskId=? and status=1";
	@Override
	public int updateUserTaskStatus(int dateCode, Long uid, long taskId, int status) {
		return jdbcHandler.executeSql(updateUserTaskStatus, status, uid, dateCode, taskId);
	}
	
	private String saveOrUpdateUserActiveness = "insert into dailytask_user_activeness(uid,dateCode,value,createTime,updateTime) values(?,?,?,now(),now()) on duplicate key update "
			+ " value=value+?,updateTime=now()";
	@Override
	public int saveOrUpdateUserActiveness(Long uid, int dateCode, int value) {
		return jdbcHandler.executeSql(saveOrUpdateUserActiveness, uid, dateCode, value, value);
	}
	
	private String saveUserActivenessRecord = "insert into dailytask_user_activeness_record(uid,value,remark,createTime) values(?,?,?,now())";
	@Override
	public int saveUserActivenessRecord(Long uid, int value, String remark) {
		return jdbcHandler.executeSql(saveUserActivenessRecord, uid, value, remark);
	}
	
	private String updateUserBoxStatus = "update dailytask_user_box set status=2,updateTime=now(),openTime=now() where uid=? and dateCode=? and boxId=? and status=1";
	@Override
	public int updateUserBoxStatus(Long uid, int dateCode, int boxId) {
		return jdbcHandler.executeSql(updateUserBoxStatus, uid, dateCode, boxId);
	}
	
	private String saveOrUpdateUserTask = "insert into dailytask_user_task(uid,dateCode,taskId,status,currNum,createTime,updateTime) values(?,?,?,?,?,now(),now())  on duplicate key update "
			+ "currNum=?,updateTime=now(),status=?";
	@Override
	public int saveOrUpdateUserTask(Long uid, int dateCode, long taskId, int status, int currNum) {
		return jdbcHandler.executeSql(saveOrUpdateUserTask, uid, dateCode, taskId, status, currNum, currNum, status);
	}
	
	private String saveOrUpdateUserBox = "insert into dailytask_user_box(uid,dateCode,boxId,createTime,updateTime) values(?,?,?,now(),now())  on duplicate key update "
			+ " updateTime=now()";
	@Override
	public int saveOrUpdateUserBox(Long uid, int dateCode, int boxId) {
		return jdbcHandler.executeSql(saveOrUpdateUserBox, uid, dateCode, boxId);
	}
	
	private String getUserTaskCount = "select count(1) from dailytask_user_task where uid=? and dateCode=? and status=?";
	@Override
	public int getUserTaskCount(Long uid, int dateCode, int status) {
		return jdbcHandler.queryCount(getUserTaskCount, uid, dateCode, status);
	}
	
	private String getUserBoxCountByParams = "select count(1) from dailytask_user_box where uid=? and dateCode=? and status=?";
	@Override
	public int getUserBoxCountByParams(Long uid, int dateCode, int status) {
		return jdbcHandler.queryCount(getUserBoxCountByParams, uid, dateCode, status);
	}
	
	private String getUserActivenessListByParams = "select * from dailytask_user_activeness where dateCode=? and value<?";
	@Override
	public List<DailytaskUserActiveness> getUserActivenessListByParams(int dateCode, int value) {
		return jdbcHandler.queryBySql(DailytaskUserActiveness.class, getUserActivenessListByParams, dateCode, value);
	}
}
