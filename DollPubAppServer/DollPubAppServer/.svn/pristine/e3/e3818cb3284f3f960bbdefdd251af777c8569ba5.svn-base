package com.imlianai.dollpub.app.modules.core.user.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.user.service.UserLoginService;
import com.imlianai.dollpub.app.modules.core.user.vo.UserInfoReqVO;
import com.imlianai.dollpub.app.modules.core.user.vo.UserLoginCheckReqVO;
import com.imlianai.dollpub.app.modules.core.user.vo.UserLoginReqVO;
import com.imlianai.dollpub.app.modules.core.user.vo.UserLoginRespVO;
import com.imlianai.dollpub.app.modules.core.user.vo.UserRegisterReqVO;
import com.imlianai.dollpub.app.modules.core.user.vo.UserUpdateReqVO;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;

/**
 * 用户登陆
 * 
 * @author AX
 */
@Component("user")
@Api("用户登录相关")
public class CmdUserLogin extends RootCmd {

	@Resource
	UserLoginService userLoginService;
	@Resource
	CmdUserInfo cmdUserInfo;

	@ApiHandle
	@LogHead("手机登录注册-注册")
	@Path("api/user/register")
	@ApiOperation(value = "【1.0.0】机登录注册-注册接口", notes = "机登录注册-注册，相关特殊返回码 120-帐号被封 74-号码不正确 77-该手机号码已注册 74-该手机号码不正确 76-验证码错误啦", httpMethod = "POST", response = UserLoginRespVO.class)
	public BaseRespVO register(UserRegisterReqVO reqVO) {
		return userLoginService.handleUserRegister(reqVO);
	}

	@ApiHandle
	@LogHead("用户登录-登录")
	@Path("api/user/login")
	@ApiOperation(value = "【1.0.0】用户登录(含第三方登录)-登录接口", notes = "用户登录-登录，相关特殊返回码 715-帐号或密码不正确 716-帐号或密码不正确且验证次数过多 120-帐号被封 74-号码不正确", httpMethod = "POST", response = UserLoginRespVO.class)
	public BaseRespVO login(UserLoginReqVO reqVO) {
		return userLoginService.handleUserLogin(reqVO);
	}

	@ApiHandle
	@LoginCheck
	@LogHead("用户登录-校验")
	@Path("api/user/oauth")
	@ApiOperation(value = "【1.0.0】用户登录-校验接口", notes = "用户登录-校验登录，相关返回码 728-登录信息过期 120-帐号被封", httpMethod = "POST", response = UserLoginRespVO.class)
	public BaseRespVO oauth(UserLoginCheckReqVO reqVO) {
		return userLoginService.handleUserLoginCheck(reqVO);
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/user/loginOut")
	public BaseRespVO loginOut(BaseReqVO reqVO) {
		userLoginService.handleLoginOut(reqVO.getUid());
		return new BaseRespVO();
	}

	@ApiHandle
	@LoginCheck
	@LogHead("用户资料页查询")
	public BaseRespVO info(UserInfoReqVO vo) {
		return cmdUserInfo.info(vo);
	}

	@ApiHandle
	@LoginCheck
	@LogHead("查看自己资料页")
	public BaseRespVO self(UserInfoReqVO vo) {
		return cmdUserInfo.self(vo);
	}
	
	@ApiHandle
	@LoginCheck
	@LogHead("用户信息更新")
	public BaseRespVO update(UserUpdateReqVO vo) {
		return cmdUserInfo.update(vo);
	}
}
