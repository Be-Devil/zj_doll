package com.imlianai.dollpub.app.modules.core.doll.bus;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.constants.DollCacheConst;
import com.imlianai.dollpub.domain.doll.BusOperatingRecord;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWipe;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.rpc.support.utils.StringUtil;

@Repository
public class DollBusDaoImpl implements DollBusDao {
	@Resource
	private JdbcHandler jdbcHandler;

	@Override
	public int updateConversationId(int busId, String conversationId) {
		return jdbcHandler.executeSql("update doll_bus set conversationId=? where busId=?", conversationId, busId);
	}

	@Override
	public List<DollBus> getDollBus() {
		return jdbcHandler.queryBySql(DollBus.class, "select * from doll_bus where valid=1 order by `index` asc");
	}

	@Override
	public List<DollBus> getDollBusList(int start, int size) {
		return jdbcHandler.queryBySql(DollBus.class,"SELECT * FROM doll_bus ORDER BY busId ASC LIMIT ?,?",start,size);
	}

	@Override
	public DollBus getDollBus(int busId) {
		return jdbcHandler.queryOneBySql(DollBus.class, "select * from doll_bus where busId=? ", busId);
	}

	@Override
	public BusOperatingRecord getBusOperatingRecord(int busId) {
		return jdbcHandler.queryOneBySql(BusOperatingRecord.class, "select * from bus_operating_record where busId=?",
				busId);
	}

	@Override
	@CacheWipe(key = DollCacheConst.BUS_OPERATING_RECORD, pkField = "busId")
	public int addBusOperatingRecord(int busId, long uid, long optId, long logId) {
		return jdbcHandler.executeSql(
				"insert into bus_operating_record (busId,uid,optId,logId,startTime,endTime,releaseTime) value(?,?,?,?,now(),null,DATE_ADD(now(),INTERVAL 40 SECOND))  on duplicate key update uid=?,optId=?,logId=?,startTime=now(),endTime=null,releaseTime=DATE_ADD(now(),INTERVAL 38 SECOND)",
				busId, uid, optId, logId, uid, optId, logId);
	}

	@Override
	@CacheWipe(key = DollCacheConst.BUS_OPERATING_RECORD, pkField = "busId")
	public int closeBusOperatingRecord(int busId, long optId) {
		return jdbcHandler.executeSql(
				"update bus_operating_record set endTime=now() ,releaseTime=DATE_ADD(now(),INTERVAL 14 SECOND) where busId=? and optId=?",
				busId, optId);
	}

	@Override
	@CacheWipe(key = DollCacheConst.BUS_OPERATING_RECORD, pkField = "busId")
	public int removeBusOperatingRecord(int busId, long optId) {
		return jdbcHandler.executeSql("delete from bus_operating_record where busId=? and optId=?", busId, optId);
	}

	@Override
	public List<BusOperatingRecord> getInvaildRecord() {
		return jdbcHandler.queryBySql(BusOperatingRecord.class,
				"select * from bus_operating_record where releaseTime<=now()");
	}

	@Override
	public void updateBusWatchCount(int busId, int num) {
		jdbcHandler.executeSql("update doll_bus set watchNum=watchNum+? where busId=?", num, busId);
	}

	private String selectByDeviceId = "select * from doll_bus where deviceId=?";

	@Override
	public DollBus getDollBus(String deviceId) {
		return jdbcHandler.queryOneBySql(DollBus.class, selectByDeviceId, deviceId);
	}

	@Override
	public List<DollBus> getDollBusByDeviceIds(List<String> deviceIds) {
		if (StringUtil.isNullOrEmpty(deviceIds)){
			return new ArrayList<>();
		}
		StringBuilder deviceId = new StringBuilder();
		for (int i = 0; i < deviceIds.size(); i++) {
			if (i > 0) {
				deviceId.append(",");
			}
			deviceId.append("'").append(deviceIds.get(i)).append("'");
		}
		String sql = "select * from doll_bus where deviceId in (" + deviceId.toString() + ")";
		return jdbcHandler.queryBySql(DollBus.class,sql);
	}

	private String selectByBusId = "SELECT valid from doll_bus where busId=?";

	@Override
	public int getDollBusValidByBusId(int busId) {
		return jdbcHandler.queryOneBySql(Integer.class, selectByBusId, busId);
	}

	private String selectByGroupId = "SELECT * FROM doll_bus WHERE groupId=? and valid=1 ORDER BY `index` asc, busId ASC LIMIT ?,?";

	@Override
	public List<DollBus> getDollBusByGroupId(int groupId, int start, int size) {
		return jdbcHandler.queryBySql(DollBus.class, selectByGroupId, groupId, start, size);
	}

	@Override
	public int getDollBusCountByGroupId(int groupId) {
		return jdbcHandler.queryCount("select count(*) from doll_bus where groupId=?and valid=1 ", groupId);
	}

	private String updateApplyStatus = "UPDATE doll_bus SET STATUS=? WHERE busId=?";

	@Override
	public int updateApplyStatus(int busId, int status) {
		return jdbcHandler.executeSql(updateApplyStatus, status, busId);
	}

	@Override
	public int updateInventory(int busId, int currentInventory) {
		return jdbcHandler.executeSql("UPDATE doll_bus SET currentInventory = currentInventory+? WHERE busId=? AND currentInventory<>0",currentInventory,busId);
	}

	@Override
	public int resetInventory(int busId) {
		return jdbcHandler.executeSql("UPDATE  doll_bus SET currentInventory=inventory WHERE busId=? AND inventory<>0",busId);
	}

	@Override
	public int updateValid(int busId, int valid) {
		return jdbcHandler.executeSql("UPDATE  doll_bus SET valid=? WHERE busId=? AND valid<>?",valid,busId,valid);
	}

	@Override
	public List<DollBus> getDollBus(List<Integer> busIds) {
		if (StringUtil.isNullOrEmpty(busIds)) {
			return new ArrayList<>();
		}
		String busids = "";
		for (int i = 0; i < busIds.size(); i++) {
			if (i > 0) {
				busids += ",";
			}
			busids += busIds.get(i);
		}
		String sql = "select * from doll_bus where busId in (?)";
		return jdbcHandler.queryBySql(DollBus.class, sql, busids);
	}

}
