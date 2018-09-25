package com.imlianai.dollpub.app.modules.publics.oauth.wechat.cmd;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.conf.WechatAppIdUtils;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.service.WechatService;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinJSSDKHandler;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinTokenHandler;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinUserInfoHandler;
import com.imlianai.dollpub.domain.user.UserOauth;
import com.imlianai.rpc.support.common.info.PackageMsg;

/**
 * 微信基础支持接口
 * 
 * @author tensloveq
 * 
 */
@Component("wechatSupport")
public class CmdWechatBaseSupport extends RootCmd {

	@Resource
	private WechatService wechatService;

	@Override
	public String doCommand(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int type = Integer.parseInt(request.getParameter("type"));
		Map<String, Object> resInfo = null;
		// 允许跨域
		response.setHeader("Access-Control-Allow-Origin", "*");
		switch (type) {
		case 1: {
			String openid = request.getParameter("openid");
			String appid = request.getParameter("appid");
			if (appid == null || appid.trim().equals("")) {
				appid =WechatAppIdUtils.ZENGJING_APPID;
			}
			// TODO 根据openId获取用户信息
			Map<Integer, WebWeixinToken> tokenMap = wechatService
					.getWebTokenByAppid(appid);
			String accessToken = tokenMap.get(
					WebWeixinTokenHandler.ACCESS_TOKEN_TYPE).getToken_ticket();
			UserOauth user = WebWeixinUserInfoHandler
					.getUserInfoMapByOpenID(accessToken, openid);
			if (user != null) {
				resInfo = PackageMsg.getRightOperCode(user);
			} else {
				resInfo = PackageMsg.getErrorOperCode(-1);
			}
		}
			break;
		case 2: {
			String url = request.getParameter("url");
			String appid = request.getParameter("appid");
			if (appid == null || appid.trim().equals("")) {
				appid = WechatAppIdUtils.ZENGJING_APPID;
			}
			Map<Integer, WebWeixinToken> tokenMap = wechatService
					.getWebTokenByAppid(appid);
			logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"
					+ tokenMap);
			String accessTicket = tokenMap.get(
					WebWeixinTokenHandler.ACCESS_TICKET_TYPE).getToken_ticket();
			Map<String, String> jsTokenMap = WebWeixinJSSDKHandler.sign(appid,
					accessTicket, url);
			resInfo = PackageMsg.getRightOperCode("1");
			resInfo.put("js", jsTokenMap);
		}
			break;
		}
		return responseJson(response, resInfo, "获取Oauth用户信息-");
	}

}
