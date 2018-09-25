package com.imlianai.zjdoll.app.modules.support.share.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.share.ShareInfo;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class ShareDaoImpl implements ShareDao {

	@Resource
	private JdbcHandler jdbcHandler;

	@Override
	public ShareInfo getShareInfo(String shareCode) {
		return jdbcHandler.queryOneBySql(ShareInfo.class, "select * from share_info where code=? limit 1",shareCode);
	}

}
