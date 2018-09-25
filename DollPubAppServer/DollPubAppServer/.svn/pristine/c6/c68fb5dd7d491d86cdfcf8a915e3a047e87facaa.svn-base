package com.imlianai.dollpub.app.modules.support.report.dao;

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

}
