package com.imlianai.dollpub.app.modules.publics.log.service;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.app.modules.publics.log.queue.LogQueue;
import com.imlianai.dollpub.domain.log.LogPage;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.utils.DateUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class LogServiceImpl implements LogService {

	private static Logger logger = Logger.getLogger(LogServiceImpl.class);

	@Resource
	private LogQueue logQueue;
//	@Resource
//	private MongoManager mongoManager;

	@Override
	public void add(LogPage page, Object data, Class<?> clazz) {
		try {
			String jsonStr = JSON.toJSONString(data);
			JSONObject json = JSON.parseObject(jsonStr);
			Date now = new Date();
			String date = DateUtils.dateToString(now);
			String createAt = DateUtils.dateToStringWithTime(now);
			json.put("date", date);
			json.put("clazz", clazz);
			json.put("table", page.page);
			json.put("createAt", createAt);
			json.put("serverName", AppUtils.getServerName());
			// logger.info("开始执行mongo日志:" + json.toJSONString());
			logQueue.addLog(json);
		} catch (Exception e) {
			logger.info(e, e);
			logger.error(e, e);
		}
	}

	@Override
	public void addMoreDate(LogPage page, Object data, Class<?> clazz) {
		try {
			String jsonStr = JSON.toJSONString(data);
			JSONObject json = JSON.parseObject(jsonStr);
			Date now = new Date();
			String date = DateUtils.dateToString(now);
			String createAt = DateUtils.dateToStringWithTime(now);
			Calendar calendar = Calendar.getInstance();
			json.put("year", calendar.get(Calendar.YEAR));
			json.put("month", calendar.get(Calendar.MONTH) + 1);
			json.put("date", date);
			json.put("clazz", clazz);
			json.put("table", page.page);
			json.put("createAt", createAt);
			json.put("serverName", AppUtils.getServerName());
			// logger.info("开始执行mongo日志:" + json.toJSONString());
			logQueue.addLog(json);
		} catch (Exception e) {
			logger.info(e, e);
			logger.error(e, e);
		}
	}

	@Override
	public void addMoreDate(LogPage page, BaseReqVO vo, Object data,
			Class<?> clazz) {
		String jsonStr = null;
		try {
			JSONObject json = null;
			if (data == null) {
				json = new JSONObject();
			} else {
				jsonStr = JSON.toJSONString(data);
				json = JSON.parseObject(jsonStr);
			}
			Date now = new Date();
			String date = DateUtils.dateToString(now);
			String createAt = DateUtils.dateToStringWithTime(now);
			Calendar calendar = Calendar.getInstance();
			json.put("year", calendar.get(Calendar.YEAR));
			json.put("month", calendar.get(Calendar.MONTH) + 1);
			json.put("date", date);
			json.put("clazz", clazz);
			json.put("table", page.page);
			json.put("createAt", createAt);
			json.put("serverName", AppUtils.getServerName());
			if (vo != null) {
				String jsonStrVo = JSON.toJSONString(vo);
				JSONObject jsonVo = JSON.parseObject(jsonStrVo);
				json.putAll(jsonVo);
			}
			// logger.info("开始执行mongo日志:" + json.toJSONString());
			logQueue.addLog(json);
		} catch (Exception e) {
			logger.info(jsonStr, e);
			logger.error(jsonStr, e);
		}
	}

	@Override
	public void addWithCity(LogPage page, Object data, Class<?> clazz) {
		try {
			String jsonStr = JSON.toJSONString(data);
			JSONObject json = JSON.parseObject(jsonStr);
			Date now = new Date();
			String date = DateUtils.dateToString(now);
			String createAt = DateUtils.dateToStringWithTime(now);
			json.put("date", date);
			json.put("clazz", clazz);
			json.put("table", page.page);
			json.put("location", true);
			json.put("createAt", createAt);
			json.put("serverName", AppUtils.getServerName());
			// logger.info("开始执行mongo日志:" + json.toJSONString());
			logQueue.addLog(json);
		} catch (Exception e) {
			logger.info(e, e);
			logger.error(e, e);
		}
	}

	@Override
	public void addUnique(LogPage page, Map<String, Object> key, Object data,
			Class<?> clazz) {
		if (key == null || key.isEmpty())
			return;
		try {
			String jsonStr = JSON.toJSONString(data);
			JSONObject json = JSON.parseObject(jsonStr);
			Date now = new Date();
			String date = DateUtils.dateToString(now);
			String createAt = DateUtils.dateToStringWithTime(now);
			json.put("date", date);
			json.put("clazz", clazz.toString());
			json.put("createAt", createAt);
			json.put("serverName", AppUtils.getServerName());
			DBObject setOnInsertData = new BasicDBObject(json);
			DBObject where = new BasicDBObject(key);
			DBObject setOnInsert = new BasicDBObject("$setOnInsert",
					setOnInsertData);
			int result = 0;//mongoManager.saveOne(page.page, where, setOnInsert);
			logger.info("setOnInsert=>" + result);
		} catch (Exception e) {
			logger.info(e, e);
			logger.error(e, e);
		}
	}
}
