package com.imlianai.zjdoll.app.modules.support.report.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.app.modules.support.report.dao.ReportDAO;

@Service
public class ReportServiceImpl implements ReportService {

	@Resource
	ReportDAO reportDAO;

	@Override
	public List<String> getWords(long uid, String imei, int type) {
		return reportDAO.getWords(uid, imei, type);
	}

	@Override
	public int addForbidImei(String imei, long uid) {
		return reportDAO.addForbidImei(imei, uid);
	}

	@Override
	public boolean isForbidImei(String imei) {
		return reportDAO.isForbidImei(imei);
	}

	@Override
	public void removeForbidImei(String imei) {
		reportDAO.removeForbidImei(imei);
	}
}
