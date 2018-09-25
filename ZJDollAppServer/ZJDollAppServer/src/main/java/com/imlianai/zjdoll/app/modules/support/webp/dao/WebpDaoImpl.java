package com.imlianai.zjdoll.app.modules.support.webp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.webp.Webp;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class WebpDaoImpl implements WebpDao {

	@Resource
	JdbcHandler jdbcHandler;

	@Override
	public List<Webp> getWebpList() {
		return jdbcHandler.queryBySql(Webp.class, "select * from webp where endTime>now()");
	}
	
}
