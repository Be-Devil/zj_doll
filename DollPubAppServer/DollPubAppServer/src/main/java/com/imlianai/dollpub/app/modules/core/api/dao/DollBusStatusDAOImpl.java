package com.imlianai.dollpub.app.modules.core.api.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import com.imlianai.dollpub.domain.doll.DollBusStatus;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class DollBusStatusDAOImpl implements DollBusStatusDAO {

	@Resource
	private JdbcHandler jdbcHandler;

	//private String insert_sql = "INSERT INTO doll_bus_status(busId,optId,userId,status,startTime,maxPlayTime) VALUES(?,?,?,?,?,?)";

	private String insertOrUpdate = "insert into doll_bus_status (busid,optid,userid,status,starttime,maxplaytime,keeptime,isDownClaw) value (?,?,?,?,now(),date_add(now(),interval ? second),null,0) on duplicate key update optid=?,starttime=now(),maxplaytime=date_add(now(),interval ? second),keeptime=null,isDownClaw=0";
	
	// private String insert_sql ="INSERT INTO
	// doll_bus_status(busId,optId,userId,status,startTime,maxPlayTime)
	// VALUES(?,?,?,?,NOW(),DATE_ADD(NOW(),INTERVAL ? SECOND))";
	@Override
	public int addDollBusStatus(DollBusStatus dollBusStatus) {
		return jdbcHandler.executeSql(insertOrUpdate, dollBusStatus.getBusId(), dollBusStatus.getOptId(),
				dollBusStatus.getUserId(), dollBusStatus.getStatus(),10,dollBusStatus.getOptId(),10);
	}

	private String update_sql = "update doll_bus_status set status=? where busId=? and status<>?";

	@Override
	public int updateDollBusStatus(int busId, int status) {
		return jdbcHandler.executeSql(update_sql, status, busId, status);
	}

	private String selectByBusId = "SELECT * FROM doll_bus_status WHERE busId=?";

	@Override
	public DollBusStatus getDollBusStatusByBusId(int busId) {
		return jdbcHandler.queryOneBySql(DollBusStatus.class, selectByBusId, busId);
	}
	@Override
	public int updateDownClaw(int busId,long optId){
		return jdbcHandler.executeSql("update doll_bus_status set isDownClaw=1 where busId=? and optId=? and isDownClaw=0", busId,optId);
	}
	
	private String deleteByBusId = "DELETE FROM doll_bus_status WHERE busId=?";

	@Override
	public int deleteDollBusStatusByBusId(int busId) {
		return jdbcHandler.executeSql(deleteByBusId, busId);
	}

//	private String timestampdiff = "SELECT TIMESTAMPDIFF(SECOND,?,NOW())";

//	@Override
//	public int timestampdiff(Date startTime) {
//		return jdbcHandler.executeSql(timestampdiff, startTime);
//	}

	@Override
	public List<DollBusStatus> getInvaildRecord(int condition) {
		if (condition == 0) {
			return jdbcHandler.queryBySql(DollBusStatus.class,
					"SELECT * FROM doll_bus_status WHERE maxPlayTime<=NOW()");
		}
		return jdbcHandler.queryBySql(DollBusStatus.class, "SELECT * FROM doll_bus_status WHERE keepTime<=NOW()");
	}

	private String update_KeppTime = "update doll_bus_status set maxPlayTime=null,keepTime=ADDDATE(NOW(), INTERVAL ? SECOND) where busId=?";

	@Override
	public int updateDollBusStatusKeepTime(int busId, Date keepTime) {
		return jdbcHandler.executeSql(update_KeppTime, 10, busId);
	}

	@Override
	public int addDollBusStatus(int busId, long optId, long uid, int status, int time) {
		return jdbcHandler.executeSql(insertOrUpdate, busId, optId,
				uid, status,time,optId,time);
	}

}
