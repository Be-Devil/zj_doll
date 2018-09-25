package com.imlianai.zjdoll.app.modules.publics.oauth.wechat.cmd;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.UserWechat;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.service.WechatService;
import com.imlianai.zjdoll.app.modules.publics.weixinAccount.service.WebWeixinOauthService;
import com.imlianai.zjdoll.app.modules.support.banner.service.BannerService;
import com.imlianai.zjdoll.app.modules.support.version.service.VersionService;

@Component("wechatOauth")
public class CmdWechatOauth extends RootCmd {

	@Resource
	private BannerService bannerService;

	@Resource
	private UserService userService;

	@Resource
	private DollRecordService dollRecordService;

	@Resource
	private VersionService versionService;

	@Resource
	private WebWeixinOauthService webWeixinOauthService;

	@Resource
	private WechatService wechatService;

	@Override
	public String doCommand(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		String appid = request.getParameter("appid");
		UserWechat userBean = webWeixinOauthService.getUserInfoWeb(appid, code);
		return "";
	}
}
