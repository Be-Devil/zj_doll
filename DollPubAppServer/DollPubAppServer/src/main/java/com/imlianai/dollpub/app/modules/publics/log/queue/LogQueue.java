package com.imlianai.dollpub.app.modules.publics.log.queue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.constants.CacheConst;
import com.imlianai.dollpub.domain.log.LogPage;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.cache.CacheManager;
import com.imlianai.rpc.support.utils.ExecutorServiceUtil;

/**
 * 日志队列
 * 
 * @author Max
 * 
 */
@Service
public class LogQueue {

	private static Logger logger = Logger.getLogger(LogQueue.class);
//	@Resource
//	private MongoManager mongoManager;
	@Resource
	private CacheManager cacheManager;
	private static Queue<JSONObject> logs = new ConcurrentLinkedQueue<JSONObject>();
	private static ExecutorService executorServiceLog;
	private static int logThreadNums = 5;

	/**
	 * 初始化
	 */
//	@PostConstruct
	public void init() {
		reQueue();
		Runnable runLog = new Runnable() {
			@Override
			public void run() {
				JSONObject log;
				while (true) {
					log = logs.poll();
					if (log != null) {
						handleLog(log);
					}
					try {
						Thread.sleep(20L);
					} catch (InterruptedException e) {
						logger.info(e, e);
						logger.error(e, e);
					}
				}
			}
		};
		executorServiceLog = ExecutorServiceUtil.newNamedFixedThreadPool(
				"log_server", logThreadNums);
		executorServiceLog.execute(runLog);
	}
	/**
	 * 处理日志
	 * 
	 * @param log
	 */
	private void handleLog(JSONObject log) {
		String table = null;
		try {
			table = log.getString("table");
			log.remove("table");
			String ip = null;
			if (log.getBooleanValue("location")
					&& StringUtils.isNotBlank(ip = log.getString("ip"))) {
				JSONObject location = AppUtils.getCityInfoByIP(ip);
				if (location != null) {
					log.putAll(location);
				}
			}
//			mongoManager.save(table, log.toJSONString());
		} catch (Exception e) {
			if (LogPage.get(table) != LogPage.IMEI_STORE) {
				logger.info(e, e);
				logger.error(e, e);
			}
		}
	}
	/**
	 * 销毁
	 */
	@PreDestroy
	public void destroy() {
		int size = logs.size();
		logger.info("日志队列[destroy]当前size:" + logs.size());
		if (size > 0) {
			String cacheKey = CacheConst.LOGS_QUEUE_CACHE;
			cacheManager.lBeanPush(cacheKey, JSON.toJSONString(logs));
		}
		if (executorServiceLog != null) {
			executorServiceLog.shutdown();
		}
	}

	/**
	 * 日志重新入列
	 */
	private void reQueue() {
		try {
			String cacheKey = CacheConst.LOGS_QUEUE_CACHE;
			String cacheValue = null;
			while ((cacheValue = cacheManager.lBeanPop(cacheKey, String.class)) != null) {
				JSONArray array = JSON.parseArray(cacheValue);
				int index = 0;
				for (Object json : array) {
					index++;
					logger.info("日志重新入列index[" + index + "]" + json);
					logs.add((JSONObject) json);
				}
			}
		} catch (Exception e) {
			logger.info("日志重新入列error", e);
			logger.error(e, e);
			PrintException.printException(logger, e);
		}
	}

	/**
	 * 记录日志
	 * 
	 * @param log
	 */
	public void addLog(JSONObject json) {
		logs.add(json);
	}
}
