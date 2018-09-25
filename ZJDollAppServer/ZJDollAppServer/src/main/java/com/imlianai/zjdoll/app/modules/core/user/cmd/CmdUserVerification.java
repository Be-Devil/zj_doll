package com.imlianai.zjdoll.app.modules.core.user.cmd;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.user.phone.code.UserCodeService;
import com.imlianai.zjdoll.app.modules.core.user.util.RandomImageGenerator;

@Component("userVerification")
public class CmdUserVerification extends RootCmd {

	@Resource
	private UserCodeService userCodeService;

	@Override
	public String doCommand(HttpServletRequest request,
			HttpServletResponse response) {
		// String key=getPara("key");
		String key = request.getParameter("key");
		if (StringUtil.isNullOrEmpty(key)){}
		String code = RandomImageGenerator.random(4);
		userCodeService.updateCode(key, code);
		try {
			RandomImageGenerator.render(code, response, 130, 53);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
