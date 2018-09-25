package com.imlianai.zjdoll.app.modules.publics.umeng.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserInfoRespVO;
import com.imlianai.zjdoll.app.modules.publics.umeng.vo.PushUpdateReqVO;

/**
 * 记录设备token
 * 
 * @author tensloveq
 * 
 */
@Api("推送相关")
@Component("pushToken")
public class CmdUpdateDeviceToken extends RootCmd {

	@Resource
	private UserService userService;

	@ApiHandle
	@Path("api/pushToken/update")
	@ApiOperation(value = "【1.0.0】推送token更新接口",  httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO update(PushUpdateReqVO reqVo) throws Exception {
		if (!StringUtil.isNullOrEmpty(reqVo.getPushToken())) {
			userService.updatePushToken(reqVo.getUid(), reqVo.getPushToken());
		}
		return new BaseRespVO();
	}
}
