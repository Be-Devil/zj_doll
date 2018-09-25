package com.imlianai.dollpub.app.modules.support.report.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.support.report.dao.ReportDAO;

@Service
public class ReportServiceImpl implements ReportService {

	@Resource
	ReportDAO reportDAO;

	@Override
	public List<String> getWords(long uid, String imei, int type) {
		return reportDAO.getWords(uid, imei, type);
	}

}
