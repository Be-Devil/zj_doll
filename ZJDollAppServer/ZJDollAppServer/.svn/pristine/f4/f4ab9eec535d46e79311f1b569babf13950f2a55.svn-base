package com.imlianai.zjdoll.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.CmdKit;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.SpringContextHolder;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiLog;
import com.imlianai.rpc.support.utils.AspectUtil;
import com.imlianai.rpc.support.utils.PropertUtil;
import com.imlianai.rpc.support.utils.RequestUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;

public abstract class RootCmd implements InitializingBean {

	// 日志
	protected final BaseLogger logger = BaseLogger.getLogger(this
			.getClass());
	// 初始化接口
	protected Map<String, Method> apiMap = new ConcurrentHashMap<String, Method>();
	protected Map<String, ApiLog> logMap = new HashMap<String, ApiLog>();

	/***************************************** 数据接收处理 ************************************************/

	/**
	 * 默认执行方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String doCommand(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 以路由形式分发接口业务
		Object method = request.getAttribute("method");
		if (!StringUtil.isNullOrEmpty(method)) {
			long start = System.currentTimeMillis();
			Map<String, Object> paraMap = CmdKit
					.getParaMapWithHeadForAPPNoEncrypt(request);
			paraMap.putAll(getRequestHeader(request));
			Method api = apiMap.get(method.toString());
			if (api != null) {
				BaseRespVO respVO = CmdKit.invokAPI(api, paraMap);
				long offset = System.currentTimeMillis() - start;
				StringBuffer logMsg = new StringBuffer("请求=>");
				if (AppUtils.isTestEnv()&&!method.toString().equals("list")) {
					logMsg.append(method);
					logMsg.append(",参数=>").append(paraMap);
					logMsg.append(",结果").append(JSON.toJSONString(respVO));
					logMsg.append(",耗时=>").append(offset);
				} else {
					logMsg.append(method);
					logMsg.append(",参数=>").append(paraMap);
					logMsg.append(",耗时=>").append(offset);
				}
				logger.info(logMsg.toString()+" "+JSON.toJSONString(respVO));
				return print(response, JSON.toJSONString(respVO,
						SerializerFeature.DisableCircularReferenceDetect));
			} else {
				logger.info("请求方法不存在：" + method);
				return printRespJson(response, ResCodeEnum.API_NOT_EXISTS);
			}
		} else {
			logger.info("请求方法不存在：" + method);
			return printRespJson(response, ResCodeEnum.API_NOT_EXISTS);
		}
	}

	private Map<String, Object> getRequestHeader(HttpServletRequest request){
		Map<String, Object> headerMap=new HashMap<String, Object>();
		String ip = RequestUtil.getIpAddr(request);
		logger.info("getRequestHeader ip:"+ip);
		headerMap.put("ip", ip);
		return headerMap;
	}
	/**
	 * 第三方回调
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String doBack(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Object method = request.getAttribute("method");
		if (!StringUtil.isNullOrEmpty(method)) {
			Method api = apiMap.get(method.toString());
			if (api != null) {
				Class<?> targeCmdClass = PropertUtil.getMethodClass(api);
				Object cmd = SpringContextHolder.getBean(targeCmdClass);
				Object[] obj = new Object[]{request, response};
				Object result = api.invoke(cmd, obj);
				if (result != null) {
					return result.toString();
				}
				return null;
			} else {
				logger.info("请求方法不存在：" + method);
				return printRespJson(response, ResCodeEnum.API_NOT_EXISTS);
			}
		} else {
			logger.info("请求方法不存在：" + method);
			return printRespJson(response, ResCodeEnum.API_NOT_EXISTS);
		}
	}

	/**
	 * 组装打印信息
	 * 
	 * @param response
	 * @param enm
	 * @return
	 */
	private String printRespJson(HttpServletResponse response, ResCodeEnum enm) {
		String json = JSON.toJSONString(new BaseRespVO(enm));
		print(response, json);
		return json;
	}

	/**
	 * 日志打印
	 * 
	 * @param resp
	 * @param json
	 * @return
	 */
	public String print(HttpServletResponse resp, String json) {
		try {
			PrintWriter pw = resp.getWriter();
			if (json != null) {
				resp.setCharacterEncoding("utf-8");
				resp.setHeader("Content-Length", json.getBytes("UTF-8").length
						+ "");
			}
			pw.print(json);
			pw.flush();
			pw.close();
			return json;
		} catch (Exception e) {
		}
		return null;
	}

	/***************************************** 数据返回处理 ************************************************/

	/**
	 * 返回Web端JSON数据
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	public String responseAjax(HttpServletRequest request, String json) {
		request.setAttribute("result", json);
		return "ajax/ajax_result";
	}

	/**
	 * 返回客户端JSON数据
	 * 
	 * @param response
	 * @param resultMap
	 *            数据体
	 * @param description
	 *            描述
	 * @return
	 */
	protected String responseJson(HttpServletResponse response,
			Map<String, Object> resultMap, String description) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			String strJson = JSON.toJSONString(resultMap);
			if (strJson != null) {
				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-Length",
						strJson.getBytes("UTF-8").length + "");
			}
			pw.print(strJson);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			logger.error("RootCmd.error:" + PrintException.getErrorStack(e, 0));
		} finally {
			pw.flush();
			pw.close();
		}
		return null;
	}

	/**
	 * 返回客户端JSON数据
	 * 
	 * @param response
	 * @param resultMap
	 *            数据体
	 * @param description
	 *            描述
	 * @return
	 */
	protected String responseJson(HttpServletResponse response, boolean state,
			Object... obj) {
		PrintWriter pw = null;
		try {
			Map<String, Object> mapCode = new HashMap<String, Object>();
			mapCode.put("state", state);
			if (obj != null) {
				int index = obj.length;
				for (int i = 0; i < obj.length; i += 2) {
					if (i + 1 < index)
						mapCode.put(obj[i].toString(), obj[i + 1]);
				}
			}
			pw = response.getWriter();
			String strJson = JSON.toJSONString(mapCode);
			if (strJson != null) {
				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-Length",
						strJson.getBytes("UTF-8").length + "");
			}
			pw.print(strJson);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			logger.error("RootCmd.error:" + PrintException.getErrorStack(e, 0));
		} finally {
			pw.flush();
			pw.close();
		}
		return null;
	}

	/**
	 * 返回客户端JSON数据
	 * 
	 * @param response
	 * @param json
	 *            数据体
	 * @param description
	 *            描述
	 * @return
	 */
	protected String responseJson(HttpServletResponse response, String json,
			String description) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			if (json != null) {
				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-Length",
						json.getBytes("UTF-8").length + "");
			}
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			logger.error("RootCmd.error:" + PrintException.getErrorStack(e, 0));
		} finally {
			pw.flush();
			pw.close();
		}
		return null;
	}

	/**
	 * 数据组装
	 * 
	 * @param params
	 * @param name
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getParam(Map<String, Object> params, String name,
			Class<T> clazz) {
		Object v = null;
		if (params == null || (v = params.get(name)) == null)
			return null;
		String value = v.toString();
		if (clazz == String.class)
			return (T) value;
		else if (clazz == Integer.class)
			return (T) Integer.valueOf(value);
		else if (clazz == Long.class)
			return (T) Long.valueOf(value);
		throw new RuntimeException("not supported class:" + clazz.getName());
	}

	/**
	 * 初始化接口
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (!StringUtil.isNullOrEmpty(apiMap)) {
			return;
		}
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method method : methods) {
			ApiHandle api = method.getAnnotation(ApiHandle.class);
			if (StringUtil.isNullOrEmpty(api)) {
				continue;
			}
			ApiLog apiLog = method.getAnnotation(ApiLog.class);
			String apiName = api.value();
			if (StringUtil.isNullOrEmpty(apiName)) {
				apiName = method.getName();
			}
			method.setAccessible(true);
			logger.info("rootCmd初始化接口:" + apiName + "="
					+ PropertUtil.getMethodClass(method) + "."
					+ method.getName());
			logger.info("serverName=>" + AppUtils.getServerName());
			apiMap.put(apiName, method);
			logMap.put(apiName, apiLog);
		}
	}

}
