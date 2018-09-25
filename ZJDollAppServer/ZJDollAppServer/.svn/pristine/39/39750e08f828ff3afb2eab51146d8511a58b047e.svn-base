package com.imlianai.zjdoll.app.modules.support.signin.cmd;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.support.signin.service.UserSigninService;
import com.imlianai.zjdoll.app.modules.support.signin.vo.GetBoxContentRespVO;
import com.imlianai.zjdoll.app.modules.support.signin.vo.SigninRespVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("用户签到")
@LogHead("用户签到")
@Component("userSignin")
public class CmdUserSignin extends RootCmd{
	
	@Resource
	UserSigninService userSigninService;
	
	@ApiHandle
	@Path("api/userSignin/getBoxContent")
	@ApiOperation(value = "【1.1.0】获取签到弹框内容", notes = "获取签到弹框内容", httpMethod = "POST", response = GetBoxContentRespVO.class)
	@LogHead("获取签到弹框内容")
	public BaseRespVO getBoxContent(BaseReqVO reqVO) {
		return userSigninService.getBoxContent(reqVO);
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/userSignin/signin")
	@ApiOperation(value = "【1.1.0】签到", notes = "签到", httpMethod = "POST", response = SigninRespVO.class)
	@LogHead("签到")
	public BaseRespVO signin(BaseReqVO reqVO) {
		return userSigninService.signin(reqVO.getUid(), reqVO.getVersion());
	}
}
