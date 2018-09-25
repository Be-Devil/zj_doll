package com.imlianai.dollpub.app.modules.publics.rpctest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserGeneral;

@Component("webTest")
public class CmdWebTest extends RootCmd {

	@Resource
	UserService userService;

	public String doCommand(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uidsString="";
		for (int i = 0; i < 100; i++) {
			UserBase userBase = new UserBase(13800138000l, "szjiajia");
			userBase.setCustomerId(79);
			userBase.setAgentId(0l);
			UserGeneral user = new UserGeneral();
			user.setCustomerId(79);
			user.setAgentId(0l);
			long uid=userService.initUser(userBase, user);
			uidsString=uid+",";
		}
		
		return responseJson(response, uidsString, null);
	}
}
