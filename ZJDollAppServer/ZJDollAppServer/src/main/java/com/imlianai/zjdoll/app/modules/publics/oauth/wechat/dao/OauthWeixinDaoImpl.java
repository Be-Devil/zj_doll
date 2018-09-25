package com.imlianai.zjdoll.app.modules.publics.oauth.wechat.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class OauthWeixinDaoImpl implements OauthWeixinDao{
	
	private static final BaseLogger logger  = BaseLogger.getLogger(OauthWeixinDaoImpl.class);
	
	@Resource
	private JdbcHandler jdbcHandler;
	
	private static String addOauthBondInfoSql = "insert into wx_bond_info(uid,openId,unionId,time) values(?,?,?,now())";
	@SuppressWarnings("unchecked")
	@Override
	public int addOauthBondInfo(String openId, long uid, String unionId) {
		try {
			return jdbcHandler.executeSql(addOauthBondInfoSql,uid, openId,unionId);
		} catch (Exception e) {
			logger.error("addOauthBondInfo-e:" + e.getMessage(),e);
		}
		return 0;
	}
	
	private static String hasBondInfoSql = "select 1 from wx_bond_info where uid=? limit 1";
	@Override
	public int hasBondInfo(long uid) {
		try {
			return jdbcHandler.queryRowCount(hasBondInfoSql,uid);
		} catch (Exception e) {
			logger.error("isExitUserInfo-e:" + e.getMessage(),e);
		}
		return 0;
	}
	
	private static String getBondUidByUnionIdSql = "select uid from wx_bond_info where unionId=? limit 1";
	@Override
	public Long getBondUidByUnionId(String unionId) {
		try {
			return jdbcHandler.queryOneBySql(Long.class, getBondUidByUnionIdSql,unionId);
		} catch (Exception e) {
			logger.error("getBondUidByUnionId-e:" + e.getMessage(),e);
		}
		return null;
	}

}
