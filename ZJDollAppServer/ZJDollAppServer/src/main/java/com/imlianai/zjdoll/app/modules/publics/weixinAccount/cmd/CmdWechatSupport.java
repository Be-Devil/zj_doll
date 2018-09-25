package com.imlianai.zjdoll.app.modules.publics.weixinAccount.cmd;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.info.PackageMsg;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinJSSDKHandler;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinTokenHandler;
import com.imlianai.zjdoll.app.modules.publics.weixinAccount.service.WebWeixinService;

/**
 * 微信基础支持接口
 * 
 * @author tensloveq
 * 
 */
@Component("wechatSupportP")
public class CmdWechatSupport extends RootCmd {

	@Resource
	private WebWeixinService webWeixinService;

	@ApiHandle
	public BaseRespVO js(String url, String appid) {
		Map<String, Object> resInfo = null;
		// 允许跨域
		if (appid == null || appid.trim().equals("")) {
			appid = "wxc68eccf842b1eeb0";
		}
		Map<Integer, WebWeixinToken> tokenMap = webWeixinService.getWebTokenByAppid(appid);
		logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"
				+ tokenMap);
		String accessTicket = tokenMap.get(
				WebWeixinTokenHandler.ACCESS_TICKET_TYPE).getToken_ticket();
		Map<String, String> jsTokenMap = WebWeixinJSSDKHandler.sign(appid,
				accessTicket, url);
		resInfo = PackageMsg.getRightOperCode("1");
		resInfo.put("js", jsTokenMap);
		return new BaseRespVO(resInfo);
	}

}
