package com.imlianai.zjdoll.app.modules.support.report.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class ReportDAOImpl implements ReportDAO {
	@Resource
	private JdbcHandler jdbcHandler;

	@Override
	public int addWord(String word, int type) {
		return 0;
	}

	@Override
	public List<String> getWords(long uid, String imei, int type) {
		return jdbcHandler.queryBySql(String.class,"select word from report_word");
	}

	private String addForbidImei = "insert into report_forbid_imei(`imei`,`hid`) values(?,?) ";
	@Override
	public int addForbidImei(String imei,long uid) {
		return jdbcHandler.executeSql(addForbidImei, imei,uid);
	}

	@Override
	public boolean isForbidImei(String imei) {
		return jdbcHandler.queryCount("select 1 from report_forbid_imei where imei =? limit 1", imei)>0?true:false;
	}

	@Override
	public void removeForbidImei(String imei) {
		jdbcHandler.executeSql("delete from report_forbid_imei where imei =?", imei);
	}
	
}
