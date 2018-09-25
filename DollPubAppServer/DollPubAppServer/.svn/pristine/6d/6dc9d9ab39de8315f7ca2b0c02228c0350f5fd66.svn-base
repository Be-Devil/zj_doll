package com.imlianai.dollpub.app.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imlianai.dollpub.app.modules.publics.oauth.wechat.callback.WechatOauthController;

@Controller
public class CmdOauthController {
	
	
	@Resource
	private WechatOauthController wechatOauthController;

	
	/*微信授权 start*/
	@RequestMapping("/api/oauth/wxAuth/{customerId}/{target}")
	private void handleFirstStep(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("customerId") String customerId,@PathVariable("target") String target) throws IOException {
		wechatOauthController.handleFirstStep(request, response,customerId,target);
	}

	@RequestMapping("/api/oauth/getOpenId/{customerId}/{target}")
	private void getOpenId(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("customerId") String customerId,@PathVariable("target") String target) throws IOException {
		wechatOauthController.getOpenId(request, response,customerId,target);
	}

	@RequestMapping("/api/oauth/getUserInfo/{customerId}/{target}")
	private void getUserInfo(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("customerId") String customerId,@PathVariable("target") String target) throws IOException {
		wechatOauthController.getUserInfo(request, response,customerId,target);
	}
	
	@RequestMapping("/api/oauth/saveUserInfo/{customerId}/{target}")
	private void saveUserInfo(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("customerId") String customerId,@PathVariable("target") String target) throws IOException {
		wechatOauthController.saveUserInfo(request, response,customerId,target);
	}
	
	@RequestMapping("/api/oauth/codeOnly/{customerId}/{target}")
	private void codeOnly(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("customerId") String customerId,@PathVariable("target") String target) throws IOException {
		wechatOauthController.codeOnly(request, response,customerId,target);
	}
	/*微信授权 end*/
	
}
