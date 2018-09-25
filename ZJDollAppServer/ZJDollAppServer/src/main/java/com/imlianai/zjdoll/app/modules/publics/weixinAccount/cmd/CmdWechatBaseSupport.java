package com.imlianai.zjdoll.app.modules.publics.weixinAccount.cmd;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.info.PackageMsg;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinJSSDKHandler;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinTokenHandler;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinUserInfoHandler;
import com.imlianai.zjdoll.app.modules.publics.oauth.weixin.config.OauthWeiXinConfig;
import com.imlianai.zjdoll.app.modules.publics.weixinAccount.service.WebWeixinService;

/**
 * 微信基础支持接口
 * @author tensloveq
 *
 */
@Component("wechatSupport")
public class CmdWechatBaseSupport extends RootCmd {
	
	@Resource
	private WebWeixinService webWeixinService;

	@Override
	public String doCommand(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int type = Integer.parseInt(request.getParameter("type"));
		Map<String,Object> resInfo = null;
		//允许跨域
		response.setHeader("Access-Control-Allow-Origin", "*");
		switch (type) {
		case 1: {
			String openid = request.getParameter("openid");
			String appid = request.getParameter("appid");
			if (appid==null||appid.trim().equals("")) {
				appid = OauthWeiXinConfig.ZS_ACCOUNT_APP_ID;
			}
			//TODO 根据openId获取用户信息
			Map<Integer,WebWeixinToken> tokenMap = webWeixinService.getWebTokenByAppid(appid);
			String accessToken =tokenMap.get(WebWeixinTokenHandler.ACCESS_TOKEN_TYPE).getToken_ticket();
			Map<String, Object> user=WebWeixinUserInfoHandler.getUserInfoMapByOpenID(accessToken, openid);
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
			if (appid==null||appid.trim().equals("")) {
				appid =OauthWeiXinConfig.ZS_ACCOUNT_APP_ID;
			}
			Map<Integer,WebWeixinToken> tokenMap = webWeixinService.getWebTokenByAppid(appid);
			logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+tokenMap);
			String accessTicket =tokenMap.get(WebWeixinTokenHandler.ACCESS_TICKET_TYPE).getToken_ticket();
			Map<String, String> jsTokenMap = WebWeixinJSSDKHandler.sign(appid,accessTicket, url);
			resInfo = PackageMsg.getRightOperCode("1");
			resInfo.put("js", jsTokenMap);
		}
			break;
		}
		return responseJson(response, resInfo, "获取Oauth用户信息-");
	}

}
