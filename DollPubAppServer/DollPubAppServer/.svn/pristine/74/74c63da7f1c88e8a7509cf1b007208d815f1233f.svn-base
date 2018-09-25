package com.imlianai.dollpub.app.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imlianai.dollpub.app.modules.publics.oauth.wechat.callback.WechatInviteOauthController;

@Controller
public class CmdInviteOauthController {

	@Resource
	private WechatInviteOauthController wechatInviteOauthController;

	/* 微信授权 start */
	@RequestMapping("/api/inviteOauth/wxAuth/{customerId}/{agentId}/{inviteUid}")
	private void handleFirstStep(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("customerId") String customerId,
			@PathVariable("agentId") String agentId,@PathVariable("inviteUid") String inviteUid) throws IOException {
		wechatInviteOauthController.handleFirstStep(request, response,
				customerId, agentId,inviteUid);
	}

	@RequestMapping("/api/inviteOauth/saveUserInfo/{customerId}/{agentId}/{inviteUid}")
	private void saveUserInfo(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("customerId") String customerId,
			@PathVariable("agentId") String agentId,@PathVariable("inviteUid") String inviteUid) throws IOException {
		wechatInviteOauthController.saveUserInfo(request, response, customerId,
				agentId,inviteUid);
	}

	@RequestMapping("/api/inviteOauth/codeOnly/{customerId}/{agentId}/{inviteUid}")
	private void codeOnly(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("customerId") String customerId,
			@PathVariable("agentId") String agentId,@PathVariable("inviteUid") String inviteUid) throws IOException {
		wechatInviteOauthController.codeOnly(request, response, customerId,
				agentId,inviteUid);
	}
	/* 微信授权 end */

}
