package com.imlianai.zjdoll.app.modules.core.user.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserBase.UserSrcType;
import com.imlianai.zjdoll.domain.user.UserPhone;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.user.phone.UserPhoneService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.core.user.util.UserUtil;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserLoginRespVO;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserPhoneBindReqVO;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserPhoneReqVO;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserRegisterReqVO;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserPhoneReqVO.CheckCodeType;
import com.imlianai.zjdoll.app.modules.publics.log.service.LogService;
import com.imlianai.zjdoll.app.modules.publics.security.SecurityManager;

/**
 * 用户手机号码注册
 * 
 * @author AX
 */
@Component("phone")
@Api("手机验证码相关")
public class CmdUserPhone extends RootCmd {

	@Resource
	private UserPhoneService userPhoneService;
	@Resource
	private UserService userService;
	@Resource
	private SecurityManager securityManager;
	@Resource
	private LogService logService;

	@ApiHandle
	@LogHead("手机登录注册-发送验证码")
	@Path("api/phone/checkcode")
	@ApiOperation(value = "【1.0.0】手机登录注册-发送验证码接口", notes = "手机登录注册-发送验证码，相关特殊返回码  77-该手机号码已注册 74-该手机号码不正确", httpMethod = "POST", response = UserLoginRespVO.class)
	public BaseRespVO checkcode(UserPhoneReqVO reqVO) {
		Pattern p = Pattern.compile("^[1][0-9]{10}$");
		Matcher m = p.matcher(reqVO.getNumber());
		if (!StringUtil.isNumber(reqVO.getNumber())||!m.matches())
			return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_ERROR);
		long number = Long.valueOf(reqVO.getNumber());
		if (reqVO.getCheckCodeType()==CheckCodeType.REG.type) {//手机注册
			UserBase userBase = userService.getUserBaseByNumber(number);
			if (userBase!=null) {
				return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_EXISTS);
			}
			UserPhone phoneInit = new UserPhone(number, reqVO.getImei());
			UserPhone phone = userPhoneService.getPhoneByNum(number);
			if (phone != null && DateUtils.isSameDay(phone.getSendTime())) {
				int value =3;
				if (phone.getCount() >= value && !AppUtils.isTestEnv()) {
					return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_OVER_DOLL);
				} else {
					phoneInit.setCount(phone.getCount() + 1);
				}
			}
			int checkCode = Integer.valueOf(UserUtil.buildDynNmbN(6));
			phoneInit.setCheckCode(checkCode);
			int flag = userPhoneService.initPhone(phoneInit);
			if (flag > 0) {
				userPhoneService.sendSMS(CheckCodeType.REG.type, number, checkCode,phoneInit);
				Map<String, Object> map = new HashMap<String, Object>();
				if (AppUtils.isTestEnv())
					map.put("checkCode", checkCode);
				return new BaseRespVO(map);
			} else {
				return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
			}
		}else if (reqVO.getCheckCodeType()==CheckCodeType.BIND.type) {//绑定手机--一个手机号可以绑定多个账号，但同类账号只允许一个
			UserBase userBaseCurrent = userService.getUserBase(reqVO.getUid().longValue());
			if (userBaseCurrent==null) {
				return new BaseRespVO(ResCodeEnum.LOGIN_TIMEOUT);
			}
			if(!securityManager.isLoginSecurityCodeValid(reqVO.getUid().longValue(), reqVO.getLoginKey())){
				return new BaseRespVO(ResCodeEnum.LOGIN_TIMEOUT);
			}
			UserBase userBase = userService.getUserBaseByNumber(number,userBaseCurrent.getSrcType());
			if (userBase!=null) {
				return new BaseRespVO(77,false,"该手机号码已绑定其他账号");
			}
			UserPhone phoneInit = new UserPhone(number, reqVO.getImei());
			UserPhone phone = userPhoneService.getPhoneByNum(number);
			if (phone != null && DateUtils.isSameDay(phone.getSendTime())) {
				int value =3;
				if (phone.getCount() >= value && !AppUtils.isTestEnv()) {
					return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_OVER_DOLL);
				} else {
					phoneInit.setCount(phone.getCount() + 1);
				}
			}
			int checkCode = Integer.valueOf(UserUtil.buildDynNmbN(6));
			phoneInit.setCheckCode(checkCode);
			int flag = userPhoneService.initPhone(phoneInit);
			if (flag > 0) {
				userPhoneService.sendSMS(CheckCodeType.RESET_PWD.type, number, checkCode,phoneInit);
				Map<String, Object> map = new HashMap<String, Object>();
				if (AppUtils.isTestEnv())
					map.put("checkCode", checkCode);
				return new BaseRespVO(map);
			} else {
				return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
			}
		}else if (reqVO.getCheckCodeType()==CheckCodeType.RESET_PWD.type) {//修改密码
			UserBase userBase = userService.getUserBaseByNumber(number);
			if (userBase==null) {
				return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_NO);
			}
			UserPhone phoneInit = new UserPhone(number, reqVO.getImei());
			UserPhone phone = userPhoneService.getPhoneByNum(number);
			if (phone != null && DateUtils.isSameDay(phone.getSendTime())) {
				int value =3;
				if (phone.getCount() >= value && !AppUtils.isTestEnv()) {
					return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_OVER_DOLL);
				} else {
					phoneInit.setCount(phone.getCount() + 1);
				}
			}
			int checkCode = Integer.valueOf(UserUtil.buildDynNmbN(6));
			phoneInit.setCheckCode(checkCode);
			int flag = userPhoneService.initPhone(phoneInit);
			if (flag > 0) {
				userPhoneService.sendSMS(CheckCodeType.RESET_PWD.type, number, checkCode,phoneInit);
				Map<String, Object> map = new HashMap<String, Object>();
				if (AppUtils.isTestEnv())
					map.put("checkCode", checkCode);
				return new BaseRespVO(map);
			} else {
				return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
			}
		}else{
			return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		}
	}

	@ApiHandle
	@LogHead("手机重置密码")
	@Path("api/phone/resetPwd")
	@ApiOperation(value = "【1.0.0】手机重置密码接口", notes = "手机重置密码，相关特殊返回码  79-该手机号码未注册 74-该手机号码不正确 76-验证码错误啦", httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO resetPwd(UserRegisterReqVO reqVO) {
		Pattern p = Pattern.compile("^[1][0-9]{10}$");
		Matcher m = p.matcher(reqVO.getNumber());
		if (!m.matches())
			return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_ERROR);
		long number = Long.valueOf(reqVO.getNumber());
		UserBase userBase = userService.getUserBaseByNumber(number);
		if (userBase==null) {
			return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_NO);
		}
		UserPhone userPhone = userPhoneService.getPhoneByNum(number);
		if (reqVO.getCheckCode()==userPhone.getCheckCode()) {
			String pwdOld=userBase.getPwd();
			userBase.updatePwd(reqVO.getPwd());
			if (pwdOld.trim().equals(userBase.getPwd().trim())) {
				return new BaseRespVO(ResCodeEnum.PWD_ISSAME);
			}
			logger.info("resetPwd:"+JSON.toJSONString(userBase));
			int falg=userService.updatePwd(userBase);
			if (falg>0) {
				securityManager.getLoginSecurityCodeNew(userBase.getUid());
				return new BaseRespVO();
			}else{
				return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
			}
		}else{
			return new BaseRespVO().getRespVO(ResCodeEnum.CHECKCODE_IS_ERROR);
		}
	}
	
	@ApiHandle
	@LoginCheck
	@LogHead("手机绑定")
	@Path("api/phone/bind")
	@ApiOperation(value = "【1.0.0】手机绑定接口", notes = "手机绑定，相关特殊返回码 145-您已绑定手机，不能重复绑定  132-该手机号已绑定其他账号，请先解绑后再尝试 74-该手机号码不正确 76-验证码错误啦", httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO bind(UserPhoneBindReqVO reqVO) {
		Pattern p = Pattern.compile("^[1][0-9]{10}$");
		Matcher m = p.matcher(reqVO.getNumber());
		if (!m.matches())
			return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_ERROR);
		long number = Long.valueOf(reqVO.getNumber());
		UserBase userBaseCurrent = userService.getUserBase(reqVO.getUid());
		if (userBaseCurrent.getSrcType()==UserSrcType.PHONE.type&&userBaseCurrent.getNumber()==number) {
			return new BaseRespVO().getRespVO(ResCodeEnum.USER_IS_BIND_PHONE);
		}
		UserBase userBase = userService.getUserBaseByNumber(number,userBaseCurrent.getSrcType());
		if (userBase!=null) {
			return new BaseRespVO().getRespVO(ResCodeEnum.PHONE_USED);
		}
		UserPhone userPhone = userPhoneService.getPhoneByNum(number);
		if (reqVO.getCheckCode()==userPhone.getCheckCode()) {
			int falg=userService.updateNumber(reqVO.getUid(), number);
			if (falg>0) {
				return new BaseRespVO();
			}else{
				return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
			}
		}else{
			return new BaseRespVO().getRespVO(ResCodeEnum.CHECKCODE_IS_ERROR);
		}
	}
}
