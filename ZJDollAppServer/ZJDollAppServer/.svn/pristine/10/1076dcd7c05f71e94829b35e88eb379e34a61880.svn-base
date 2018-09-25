package com.imlianai.zjdoll.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.XssRequestWrapper;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.CmdKit;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.common.json.Jackson;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.utils.PropertUtil;
import com.imlianai.rpc.support.utils.StringUtil;

public abstract class WebCmd implements InitializingBean{
	// 日志
		protected final BaseLogger logger = BaseLogger.getLoggerSwitch(this
				.getClass());
		// 初始化接口
		protected Map<String, Method> apiMap = new HashMap<String, Method>();

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
			//允许跨域
			response.setHeader("Access-Control-Allow-Origin", "*");
			/**
			 * 过滤Xss跨站脚本
			 */
			request=new XssRequestWrapper(request);
			// 以路由形式分发接口业务
			Map<String, Object> paraMap = CmdKit.getParaMapForWEB(request);
			String action = (String) request.getAttribute("method");
			Method api = apiMap.get(action);
			if (StringUtil.isNullOrEmpty(api)) {
				logger.info(this.getClass() + "请求方法不存在：" + action);
				return printRespJson(response, ResCodeEnum.API_NOT_EXISTS);
			}
			BaseRespVO respVO=CmdKit.invokAPI(api, paraMap);
			return print(response, JSON.toJSONString(respVO,SerializerFeature.DisableCircularReferenceDetect));
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
		private String print(HttpServletResponse resp, String json) {
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
				Map<String,Object> resultMap, String description) {
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				String strJson = Jackson.objToJson(resultMap);
				if (strJson != null) {
					response.setCharacterEncoding("utf-8");
					response.setHeader("Content-Length",
							strJson.getBytes("UTF-8").length + "");
				}
				pw.print(strJson);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				logger.error("TruboCmd.error:" + PrintException.getErrorStack(e, 0));
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
				logger.error("TruboCmd.error:" + PrintException.getErrorStack(e, 0));
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
				Map<String,Object> mapCode = new HashMap<String,Object>();
				mapCode.put("state", state);
				if (obj != null) {
					int index = obj.length;
					for (int i = 0; i < obj.length; i += 2) {
						if (i + 1 < index)
							mapCode.put(obj[i].toString(), obj[i + 1]);
					}
				}
				pw = response.getWriter();
				String strJson = Jackson.objToJson(mapCode);
				if (strJson != null) {
					response.setCharacterEncoding("utf-8");
					response.setHeader("Content-Length",
							strJson.getBytes("UTF-8").length + "");
				}
				pw.print(strJson);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				logger.error("TruboCmd.error:" + PrintException.getErrorStack(e, 0));
			} finally {
				pw.flush();
				pw.close();
			}
			return null;
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
				String apiName = api.value();
				if (StringUtil.isNullOrEmpty(apiName)) {
					apiName = method.getName();
				}
				method.setAccessible(true);
				logger.info("webCmd初始化接口:"+apiName+"="+PropertUtil.getMethodClass(method)+"."+method.getName());
				apiMap.put(apiName, method);
			}
		}
}
