package com.imlianai.zjdoll.app.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.callback.WechatOauthController;

@Controller
public class CmdOauthController {
	
	
	@Resource
	private WechatOauthController wechatOauthController;

	
	/*微信授权 start*/
	@RequestMapping("/api/oauth/wxAuth/{target}")
	private void handleFirstStep(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("target") String target) throws IOException {
		wechatOauthController.handleFirstStep(request, response,target);
	}

	@RequestMapping("/api/oauth/getOpenId/{target}")
	private void getOpenId(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("target") String target) throws IOException {
		wechatOauthController.getOpenId(request, response,target);
	}

	@RequestMapping("/api/oauth/getUserInfo/{target}")
	private void getUserInfo(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("target") String target) throws IOException {
		wechatOauthController.getUserInfo(request, response,target);
	}
	
	@RequestMapping("/api/oauth/saveUserInfo/{target}")
	private void saveUserInfo(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("target") String target) throws IOException {
		wechatOauthController.saveUserInfo(request, response,target);
	}
	
	@RequestMapping("/api/oauth/codeOnly/{target}")
	private void codeOnly(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("target") String target) throws IOException {
		wechatOauthController.codeOnly(request, response,target);
	}
	/*微信授权 end*/
	
}
