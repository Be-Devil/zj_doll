package com.imlianai.dollpub.app.modules.support.idfa.dao;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.idfa.IdfaRecord;
import com.imlianai.dollpub.domain.idfa.IdfaStatus;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class IdfaDaoImpl implements IdfaDao {

	@Resource
	private JdbcHandler jdbcHandler;

	private static String initSql = "insert into idfa_record(idfa,offer,appid,actip,status,clickAt,createAt) values(?,?,?,?,?,now(),now()) on DUPLICATE key update num=num+1";

	@Override
	public int init(IdfaRecord record) {
		return jdbcHandler.executeSql(initSql, record.getIdfa(),
				record.getOffer(), record.getAppid(), record.getActip(),
				record.getStatus());
	}

	private static String activeSql = "update idfa_record set status=?,imei=?,backAt=now() where idfa=? and status=?";

	@Override
	public int active(String idfa, String imei) {
		return jdbcHandler.executeSql(activeSql, IdfaStatus.ACTIVED.status,
				imei, idfa, IdfaStatus.INIT.status);
	}

	private static String registerSql = "update idfa_record set status=?,uid=?,regAt=now() where imei=?";

	@Override
	public int register(long uid, String imei) {
		return jdbcHandler.executeSql(registerSql,
				IdfaStatus.REGISTERED.status, uid, imei);
	}

	private static String getSql = "select * from idfa_record where idfa=?";

	@Override
	public IdfaRecord get(String idfa) {
		return jdbcHandler.queryOneBySql(IdfaRecord.class, getSql, idfa);
	}

}
