package com.imlianai.dollpub.app.modules.support.version.cmd;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.support.version.service.VersionService;
import com.imlianai.dollpub.domain.version.Version;

/**
 * 下载地址修改
 * 
 * @author tensloveq
 * 
 */
@Component("download_bean")
public class CmdGetDownloadUrl extends RootCmd {

	private static final Logger logger = Logger
			.getLogger(CmdGetDownloadUrl.class);
	@Resource
	private VersionService versionService;

	@Override
	public String doCommand(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ua = request.getHeader("User-Agent");
		String channel =request.getAttribute("channel").toString();
		int versionCode = 0;
		int osType=0;
		if (ua!=null) {
			ua=ua.toLowerCase();
		}
		if (ua.contains("micromessenger")||ua.contains("qq")) {
			if (channel!=null&&channel.trim().equals("shuangzhu")) {
				/*response.setCharacterEncoding("UTF-8");  
		        response.setHeader("Content-type", "text/html;charset=UTF-8");  
		        response.setContentType("text/html;charset=UTF-8");  
		        String date = "<h1>微信浏览器暂时不能下载，请点击右上角按钮在系统浏览器打开</h1>";  
		        PrintWriter print = response.getWriter();  
		        print.write(date);  
		        return null;*/
			}else{
				channel="wechat";
				osType=0;
			}
		}else if (ua.contains("iphone")||ua.contains("ipad")||ua.contains("ipod")){
			if (channel==null||channel.trim().equals("")) {
				channel="appstore";
			}
			osType=1;
		}else{
			if (channel==null||channel.trim().equals("")) {
				channel="gw";
			}
			osType=0;
		}
		Version versionInfo = null;
		// 取出当前渠道最新版本
		versionInfo = versionService.getVersion(osType, channel);
		logger.info("versionCode:" + versionCode + " versionInfo:"
				+ versionInfo);
		if (versionInfo != null && versionInfo.getVersionCode() > versionCode) {
			response.sendRedirect(versionInfo.getDownloadUrl());
		} else{
			response.setCharacterEncoding("UTF-8");  
	        response.setHeader("Content-type", "text/html;charset=UTF-8");  
	        response.setContentType("text/html;charset=UTF-8");  
	        String date = "<h1>最新版本即将推出，敬请期待！3秒后将前往官网</h1>";  
	        PrintWriter print = response.getWriter();  
	        print.write(date);  
			response.setHeader("Refresh","3;URL=http://www.mengquww.com/"); 
		}
		return null;
	}

}
