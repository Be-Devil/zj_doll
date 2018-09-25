package com.imlianai.dollpub.app.modules.core.trade.catalog.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.trade.ChargeCatalog;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class ChargeCatalogDaoImpl implements ChargeCatalogDao {

	private static final BaseLogger logger = BaseLogger
			.getLogger(ChargeCatalogDao.class);

	@Resource
	private JdbcHandler jdbcHandler;

	private static String getCatalogsSql = "select * from charge_catalog where type=? and isFirst=? and customerId=? and `valid`>0 order by `index`";

	@Override
	public List<ChargeCatalog> getCatalogs(int type,int isFirst,int customerId) {
		try {
			return jdbcHandler.queryBySql(ChargeCatalog.class, getCatalogsSql,
					type,isFirst,customerId);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	private static String getCatalogsSql1 = "select * from charge_catalog where type=? and isFirst=? and tag=? and customerId=? and `valid`>0 order by `index`";

	@Override
	public List<ChargeCatalog> getCatalogs(int type, int tag, int isFirst,int customerId) {
		try {
			return jdbcHandler.queryBySql(ChargeCatalog.class, getCatalogsSql1,
					type,isFirst,tag,customerId);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	private static String getCatalogSql = "select * from charge_catalog where code=? and `valid`>0";

	@Override
	public ChargeCatalog getCatalog(int code) {
		try {
			return jdbcHandler.queryOneBySql(ChargeCatalog.class,
					getCatalogSql, code);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	private static String getChargeCodeByChannelSql = "select code from mobile_charge_ios_catalog where channel=? limit 1";

	@Override
	public int getChargeCodeByChannel(String channel) {
		try {
			Integer id = jdbcHandler.queryOneBySql(Integer.class,
					getChargeCodeByChannelSql, channel);
			if (id == null)
				return 0;
			return id;
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}


}
