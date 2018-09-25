package com.imlianai.zjdoll.app.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.SpringContextHolder;
import com.imlianai.rpc.support.utils.AspectUtil;

/**
 * 指令执行入口
 * 
 * @author RUIZ
 */
@Controller
public class CmdAppController {

	protected BaseLogger logger = BaseLogger.getLogger(this.getClass());

	@RequestMapping("/api/{target}/{method}")
	public void api(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("target") String target,
			@PathVariable("method") String method) throws Exception {
		try {
			// 测试环境允许跨域
			/* if(AppUtils.isTestEnv()) */
			response.setHeader("Access-Control-Allow-Origin", "*");
			logger.info("target=>" + target + ",method=>" + method);
			response.setContentType("text/html;charset=UTF-8");
			if (StringUtils.isBlank(target)) {
				logger.error("收到请求:请求目标为空:" + target);
				printRespJson(response, ResCodeEnum.CMD_IS_NULL);
				return;
			}
			if (StringUtils.isBlank(method)) {
				logger.error("收到请求:请求目标为空:" + target);
				printRespJson(response, ResCodeEnum.METHOD_IS_NULL);
				return;
			}
			RootCmd cmd = (RootCmd) SpringContextHolder.getBean(target);
			request.setAttribute("target", target);
			request.setAttribute("method", method);
			cmd.doCommand(request, response);
		} catch (Exception e) {
			PrintException.printException(logger, e);
			printRespJson(response, ResCodeEnum.SYSTEM_ERROR);
		} finally {
			AspectUtil.removeChannel();
			AspectUtil.removeVersion();
		}
	}

	@RequestMapping("/api/back/{target}/{method}")
	public String back(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("target") String target,
			@PathVariable("method") String method) throws Exception {
		try {
			logger.info("target=>" + target + ",method=>" + method);
			response.setContentType("text/html;charset=UTF-8");
			if (StringUtils.isBlank(target)) {
				logger.error("收到请求:请求目标为空:" + target);
				printRespJson(response, ResCodeEnum.CMD_IS_NULL);
				return null;
			}
			if (StringUtils.isBlank(method)) {
				logger.error("收到请求:请求目标为空:" + target);
				printRespJson(response, ResCodeEnum.METHOD_IS_NULL);
				return null;
			}
			RootCmd cmd = (RootCmd) SpringContextHolder.getBean(target);
			request.setAttribute("method", method);
			return cmd.doBack(request, response);
		} catch (Exception e) {
			PrintException.printException(logger, e);
			printRespJson(response, ResCodeEnum.SYSTEM_ERROR);
			return null;
		}
	}

	@RequestMapping("/api/web/{target}/{method}")
	public String web(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("target") String target,
			@PathVariable("method") String method) throws Exception {
		try {
			logger.info("target=>" + target + ",method=>" + method);
			response.setContentType("text/html;charset=UTF-8");
			if (StringUtils.isBlank(target)) {
				logger.error("收到请求:请求目标为空:" + target);
				printRespJson(response, ResCodeEnum.CMD_IS_NULL);
				return null;
			}
			if (StringUtils.isBlank(method)) {
				logger.error("收到请求:请求目标为空:" + target);
				printRespJson(response, ResCodeEnum.METHOD_IS_NULL);
				return null;
			}
			WebCmd cmd = (WebCmd) SpringContextHolder.getBean(target);
			request.setAttribute("method", method);
			return cmd.doCommand(request, response);
		} catch (Exception e) {
			PrintException.printException(logger, e);
			printRespJson(response, ResCodeEnum.SYSTEM_ERROR);
			return null;
		}
	}

	private static final String DOWNLOAD_BEAN_NAME = "download_bean";

	@RequestMapping("/download/{channel}")
	public String download(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("channel") String channel) throws Exception {
		try {
			logger.info("download=> channel" + channel);
			response.setContentType("text/html;charset=UTF-8");
			RootCmd cmd = (RootCmd) SpringContextHolder
					.getBean(DOWNLOAD_BEAN_NAME);
			request.setAttribute("channel", channel);
			return cmd.doCommand(request, response);
		} catch (Exception e) {
			PrintException.printException(logger, e);
			printRespJson(response, ResCodeEnum.SYSTEM_ERROR);
			return null;
		}
	}

	protected String printRespJson(HttpServletResponse response, ResCodeEnum enm) {
		String json = JSON.toJSONString(new BaseRespVO(enm));
		print(response, json);
		return json;
	}

	protected String print(HttpServletResponse resp, String json) {
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

}
