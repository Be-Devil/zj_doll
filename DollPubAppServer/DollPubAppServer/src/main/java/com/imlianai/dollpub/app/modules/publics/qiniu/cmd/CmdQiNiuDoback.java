package com.imlianai.dollpub.app.modules.publics.qiniu.cmd;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.trade.util.common.CommonUtil;
import com.imlianai.dollpub.app.modules.publics.log.service.LogService;
import com.imlianai.dollpub.app.modules.publics.qiniu.service.QiNiuService;
import com.imlianai.dollpub.app.modules.publics.qiniu.utils.QINiuUtil;
import com.imlianai.dollpub.domain.log.LogPage;
import com.imlianai.dollpub.domain.qiniu.QiniuCnf;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.SpringContextHolder;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;

@Component("qiniucbk")
public class CmdQiNiuDoback extends RootCmd {

	@Resource
	private QiNiuService qiNiuService;
	@Resource
	private LogService logService;

	@ApiHandle
	public String doback(HttpServletRequest request,
			HttpServletResponse response) {
		int type = Integer.valueOf(request.getParameter("type"));
		String fileName = request.getParameter("fileName");
		long uid = Long.valueOf(request.getParameter("uid"));
		String aviInfo = request.getParameter("avinfo");
		// String loginKey = request.getParameter("loginKey");
		Map<String, String> getParam = AppUtils.getParam(request);
		logger.info("获取七牛参数uid=>" + uid + "getParam=>" + getParam);
		QiniuCnf cnf = qiNiuService.getcnfByType(type);
		String url = qiNiuService.getImageUrlByType(uid, type, fileName,
				cnf.getDefaultSzie(), request);
		Map<String, Object> params = AppUtils.getParamStrObject(request);
		String prefix = QINiuUtil.getPrefix(url);
		params.put("url", StringUtils.substringBefore(url, prefix));// 原图
		Map<String, Object> retMap = doInvoke(cnf, params);
		retMap.put("fileName", fileName);
		qiNiuService.putDefault(cnf, retMap, request);
		if (cnf.getInfo() > 0) {
			QINiuUtil.putInfo(request, retMap);
		}
		String format = null;
		if (cnf.getType() == 15
				&& !StringUtils.equalsIgnoreCase(
						(format = QINiuUtil.getAvinfoFormatName(aviInfo)),
						"mp3")) {
			int seconds = QINiuUtil.getAvinfoDuration(aviInfo);
			String key = QINiuUtil.getKey(uid, cnf, fileName);
			logger.info("key=>" + key + ",format=>" + format + ",seconds=>"
					+ seconds);
			retMap.put("seconds", seconds);
			qiNiuService.fops2Mp3(key, "mp3");
			retMap.remove("avinfo");
		}
		BaseRespVO vo = new BaseRespVO(retMap);
		String json = JSON.toJSONString(vo);
		logger.info("json===>" + json);
		return print(response, json);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> doInvoke(QiniuCnf cnf,
			Map<String, Object> params) {
		if (StringUtils.isNotBlank(cnf.getBeanName())
				&& StringUtils.isNotBlank(cnf.getBeanMethod())) {
			Object bean = SpringContextHolder.getBean(cnf.getBeanName());
			Class<?> clazz = bean.getClass();
			try {
				Method method = clazz.getMethod(cnf.getBeanMethod(), Map.class);
				Map<String, Object> result = (Map<String, Object>) method
						.invoke(bean, params);
				if (result != null)
					params.putAll(result);
			} catch (NoSuchMethodException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| SecurityException e) {
				logger.info(e);
				logger.error(e, e);
			}
		}
		return params;
	}

	@ApiHandle
	public String fops2Mp3back(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String jsonStr = CommonUtil.getInputStreamParamsStr(request);
			logger.info("fops2Mp3back 回调:" + jsonStr);
			LogPage page = LogPage.QINIU_FOPS2MP3BACK;
			Object data = JSON.parse(jsonStr);
			logService.add(page, data, this.getClass());
		} catch (Exception e) {
			PrintException.printException(logger, e);
			logger.info(e);
			logger.error(e, e);
		}
		return null;
	}
}
