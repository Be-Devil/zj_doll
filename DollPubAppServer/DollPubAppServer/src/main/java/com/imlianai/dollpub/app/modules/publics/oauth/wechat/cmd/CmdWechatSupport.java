package com.imlianai.dollpub.app.modules.publics.oauth.wechat.cmd;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.conf.WechatAppIdUtils;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.service.WechatService;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinJSSDKHandler;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinTokenHandler;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.info.PackageMsg;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;

/**
 * 微信基础支持接口
 * 
 * @author tensloveq
 * 
 */
@Component("wechatSupportP")
public class CmdWechatSupport extends RootCmd {

	@Resource
	private WechatService wechatService;

	@ApiHandle
	public BaseRespVO js(String url, String appid) {
		Map<String, Object> resInfo = null;
		// 允许跨域
		if (appid == null || appid.trim().equals("")) {
			appid =WechatAppIdUtils.ZENGJING_APPID;// "wx8c5308361b165de9";
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
		return new BaseRespVO(resInfo);
	}

}
