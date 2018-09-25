package com.imlianai.dollpub.app.modules.publics.live.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.live.LiveCdn;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
@Repository
public class LiveDaoImpl implements LiveDao {

	@Resource
	JdbcHandler jdbcHandler;
	private String getLiveCdn = "select * from live_cdn where status=1 order by weight desc limit 1";

	@Override
	public LiveCdn getLiveCdn() {
		return jdbcHandler.queryOneBySql(LiveCdn.class, getLiveCdn);
	}

	private String getCdn = "select * from live_cdn where status=1 order by weight desc";

	@Override
	public List<LiveCdn> getCdn() {
		return jdbcHandler.queryBySql(LiveCdn.class, getCdn);
	}
}
