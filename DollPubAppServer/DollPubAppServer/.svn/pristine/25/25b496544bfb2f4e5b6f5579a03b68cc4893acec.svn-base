package com.imlianai.dollpub.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinShareOauthUtil;
import com.imlianai.dollpub.app.modules.publics.security.SecurityManager;
import com.imlianai.dollpub.domain.user.UserBase;

/**
 * 第三方授权
 * 
 * @author tensloveq
 * 
 */
@Controller
public class Cmd3thOauthController {

	@Resource
	private UserService userService;

	@Resource
	private SecurityManager securityManager;

	/* 微信授权 start */
	@RequestMapping("/api/oauth/merchant/{customerId}/{authCustomerId}/{userId}/{token}")
	private void merchantOauth(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("customerId") String customerId,
			@PathVariable("authCustomerId") String authCustomerId,
			@PathVariable("userId") String userId,
			@PathVariable("token") String token) throws IOException {
		int customerIdi = Integer.parseInt(customerId);
		int authCustomerIdi = Integer.parseInt(authCustomerId);
		long uid = userService.initUser(customerIdi, authCustomerIdi, userId,
				token);
		String url = "http://www.realgamecloud.com/webapp/?";
		if (uid > 0) {
			UserBase user = userService.getUserBase(uid);
			if (user!=null) {
				String loginKey = securityManager.getLoginSecurityCodeNew(user
						.getUid());
				
				String dir_url = url + "?uid="
						+ user.getUid() + "&loginKey=" + loginKey + "&customerId="
						+ customerId + "&agentId=" + authCustomerId + "&fin=1";
					Map<String, String> cookieMap = new HashMap<String, String>();
					cookieMap.put("uid", user.getUid() + "");
					cookieMap.put("loginKey", loginKey);
					cookieMap.put("customerId", customerId + "");
					cookieMap.put("agentId", authCustomerId + "");
					WebWeixinShareOauthUtil.setCookies(response, cookieMap,
							3600 * 24 * 3, "/");
					response.sendRedirect(dir_url);
					return ;
			}
		}
		String dir_url = url + "?uid=0&loginKey=123&customerId="
				+ customerId + "&agentId=" + authCustomerId + "&fin=1";
		response.sendRedirect(dir_url);
	}

	/* 微信授权 start */
	@RequestMapping("/api/oauth/merchantwechat/{customerId}/{authCustomerId}/{userId}/{token}")
	private void merchantOauth(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
	}
}
