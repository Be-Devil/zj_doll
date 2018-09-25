package com.imlianai.dollpub.app.modules.core.user.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.core.user.phone.UserPhoneService;
import com.imlianai.dollpub.app.modules.core.user.phone.code.UserCodeService;
import com.imlianai.dollpub.app.modules.core.user.service.UserLoginService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.core.user.util.RandomImageGenerator;
import com.imlianai.dollpub.app.modules.core.user.vo.UserLoginCheckReqVO;
import com.imlianai.dollpub.app.modules.core.user.vo.UserLoginReqVO;
import com.imlianai.dollpub.app.modules.core.user.vo.UserLoginRespVO;
import com.imlianai.dollpub.app.modules.core.user.vo.UserLoginSyncData;
import com.imlianai.dollpub.app.modules.core.user.vo.UserRegisterReqVO;
import com.imlianai.dollpub.app.modules.publics.log.service.LogService;
import com.imlianai.dollpub.app.modules.publics.msg.leancloud.IMCommonService;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.app.modules.publics.security.SecurityManager;
import com.imlianai.dollpub.app.modules.support.invite.service.InviteService;
import com.imlianai.dollpub.domain.trade.TradeAccount;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserBase.UserSrcType;
import com.imlianai.dollpub.domain.user.UserCode;
import com.imlianai.dollpub.domain.user.UserCommon;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.dollpub.domain.user.UserPhone;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.utils.MD5_HexUtil;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class UserLoginServiceImpl implements UserLoginService {
	

	private final BaseLogger logger = BaseLogger.getLogger(UserLoginServiceImpl.class);
	@Resource
	private LogService logService;
	@Resource
	private UserService userService;
	@Resource
	private SecurityManager securityManager;
	@Resource
	private UserPhoneService userPhoneService;
	@Resource
	private UserCodeService userCodeService;
	@Resource
	private IMCommonService iMCommonService;
	@Resource
	private TradeService tradeService;
	@Resource
	private InviteService inviteService;
	@Resource
	private MsgService msgService;
	
	@Override
	public BaseRespVO handleUserRegister(UserRegisterReqVO reqVO) {
		if (reqVO!=null&&!StringUtil.isNullOrEmpty(reqVO.getNumber())) {
			Pattern p = Pattern.compile("^[1][0-9]{10}$");
			Matcher m = p.matcher(reqVO.getNumber());
			long number = Long.parseLong(reqVO.getNumber());
			if (m.matches()) {
				if (userService.getUserBaseByNumberCustomerId(number,reqVO.getCustomerId()) != null) {
					return new BaseRespVO()
							.getRespVO(ResCodeEnum.NUMBER_IS_EXISTS);
				}
			} else {
				return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_ERROR);
			}
		} else {
			return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_ERROR);
		}
		long number = Long.parseLong(reqVO.getNumber());
		UserPhone userPhone = userPhoneService.getPhoneByNum(number);
		/*
		 * if (userPhone.getRegState() != 1) { return new
		 * BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_EXISTS); }
		 */
		if (userPhone==null||userPhone.getCheckCode() != reqVO.getCheckCode()) {
			return new BaseRespVO().getRespVO(ResCodeEnum.CHECKCODE_IS_ERROR);
		}
		UserBase userBase = new UserBase(number, reqVO.getPwd());
		userBase.setCustomerId(reqVO.getCustomerId());
		userBase.setAgentId(reqVO.getAgentId());
		UserGeneral user = new UserGeneral();
		user.setCustomerId(reqVO.getCustomerId());
		user.setAgentId(reqVO.getAgentId());
		userService.initUser(userBase, user);
		if (userBase.getUid() > 0) {// 注册成功
			//记录代理授权记录
			userService.handleUserAgentRelate(userBase.getUid(), reqVO.getAgentId());
			user=userService.getUserGeneral(userBase.getUid());
			long uid = userBase.getUid();
			String loginKey = securityManager.getLoginSecurityCodeNew(uid);
			UserCommon userCommon = new UserCommon(user, userBase);
			UserLoginRespVO respVo = new UserLoginRespVO();
			respVo.setLoginKey(loginKey);
			respVo.setUser(userCommon);
			// 登陆同步数据操作
			UserLoginSyncData resMap = commonLoginProcessSync(user, userBase,
					reqVO, true);
			// 登录异步数据操作
			commonLoginProcessAsyn(user, userBase, reqVO, true);
			respVo.setAdditionData(resMap);
			return respVo;
		} else {
			return new BaseRespVO().getRespVO(ResCodeEnum.REGISTER_IS_ERROR);
		}
	}

	@Override
	public BaseRespVO handleUserLogin(UserLoginReqVO reqVO) {
		UserBase userBase = null;
		boolean isRgister = false;
		if (reqVO.getCustomerId()==null) {
			reqVO.setCustomerId(76);
		}
		if (reqVO.getSrcType() == UserSrcType.PHONE.type) {// 手机验证码登录
			UserCode userCode=userCodeService.getCode(reqVO.getNumber());
			if (!StringUtil.isNullOrEmpty(reqVO.getNumber())) {
				Pattern p = Pattern.compile("^[1][0-9]{10}$");
				Matcher m = p.matcher(reqVO.getNumber());
				long number = Long.parseLong(reqVO.getNumber());
				if (m.matches()) {
					userBase = userService.getUserBaseByNumberCustomerId(number,reqVO.getCustomerId());
				}
				if (!m.matches() || userBase == null) {
					userBase = userService.getUserBase(number);
				}
				if (userBase == null) {
					return new BaseRespVO().getRespVO(ResCodeEnum.PWD_IS_WRONG);
				}
			} else {
				return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_ERROR);
			}
			if ((StringUtils.isBlank(userBase.getPwd()) || !userBase.getPwd()
					.equals(MD5_HexUtil.xor_MD5((String) reqVO.getPwd())))) {
				if (userCode.getCount()>=3) {
					BaseRespVO respVO=new BaseRespVO(ResCodeEnum.PWD_IS_WRONG_FORBIND);
					respVO.setData(RandomImageGenerator.getCheckCodeUrl(reqVO.getNumber()));
					return respVO;
				}
				return new BaseRespVO().getRespVO(ResCodeEnum.PWD_IS_WRONG);
			}
			userCodeService.delCode(reqVO.getNumber());
		} else {// 第三方登录
			userBase = userService.getUserBaseBySrc(reqVO.getSrcType(),
					reqVO.getSrcId(), reqVO.getSrcUnionId(),reqVO.getCustomerId());
			if (userBase == null) {// 第三方自动注册
				BaseRespVO vo = userService.initUser(reqVO.getSrcType(),
						reqVO.getSrcId(), reqVO.getSrcUnionId(),
						reqVO.getSrcToken(),reqVO.getCustomerId());
				if (vo.isState()) {
					userBase = userService.getUserBaseBySrc(reqVO.getSrcType(),
							reqVO.getSrcId(), reqVO.getSrcUnionId(),reqVO.getCustomerId());
					userBase.setVersion(reqVO.getVersion());
					isRgister = true;
				} else {
					return vo;
				}
			}
		}
		UserGeneral user = userService.getUserGeneral(userBase.getUid());
		if (user.getValid() != 0) {
			return new BaseRespVO().getRespVO(ResCodeEnum.LOGIN_BLOCK);
		}
		UserCommon userCommon = new UserCommon(user, userBase);
		String loginKey = securityManager.getLoginSecurityCodeNew(userBase
				.getUid());
		UserLoginRespVO respVo = new UserLoginRespVO();
		respVo.setLoginKey(loginKey);
		respVo.setUser(userCommon);
		// 登陆同步数据操作
		UserLoginSyncData resMap = commonLoginProcessSync(user, userBase,
				reqVO, isRgister);
		// 登录异步数据操作
		commonLoginProcessAsyn(user, userBase, reqVO, isRgister);
		respVo.setAdditionData(resMap);
		return respVo;
	}

	@Override
	public BaseRespVO handleUserLoginCheck(UserLoginCheckReqVO reqVO) {
		String loginKey = securityManager.getLoginSecurityCode(reqVO.getUid());
		UserGeneral user = userService.getUserGeneral(reqVO.getUid());
		if (user.getValid() != 0) {
			return new BaseRespVO().getRespVO(ResCodeEnum.LOGIN_BLOCK);
		}
		UserBase userBase = userService.getUserBase(reqVO.getUid());
		UserCommon userCommon = new UserCommon(user, userBase);
		UserLoginRespVO respVo = new UserLoginRespVO();
		respVo.setLoginKey(loginKey);
		respVo.setUser(userCommon);
		// 登陆同步数据操作
		UserLoginSyncData resMap = commonLoginProcessSync(user, userBase,
				reqVO, false);
		// 登录异步数据操作
		commonLoginProcessAsyn(user, userBase, reqVO, false);
		respVo.setAdditionData(resMap);
		return respVo;
	}

	// 登录异步数据操作
	@Override
	public void commonLoginProcessAsyn(UserGeneral user, UserBase userBase,
			BaseReqVO reqVo, boolean isRegister) {
		// 登陆数据更新
		userService.updateUserDevice(user.getUid(), reqVo);
		// 日志记录
		if (isRegister) {
		} else {
		}
	}

	// 登陆同步数据操作
	@Override
	public UserLoginSyncData commonLoginProcessSync(UserGeneral user,
			UserBase userBase, BaseReqVO reqVo, boolean isRegister) {
		userBase.setImei(reqVo.getImei());
		UserLoginSyncData reSyncData=new UserLoginSyncData();
		if (isRegister) {
			int reward=inviteService.handleRegReward(user, userBase);
			if (reward>0) {
				reSyncData.setRegReward(reward);
				reSyncData.setInviteReward(reward);
			}
		}
		TradeAccount account = tradeService.getAccount(user.getUid());
		if (account != null) {
			reSyncData.setCoin(account.getCoin());
		}
		return reSyncData;
	}

	@Override
	public void handleLoginOut(long uid) {
		securityManager.getLoginSecurityCodeNew(uid);
	}
}
