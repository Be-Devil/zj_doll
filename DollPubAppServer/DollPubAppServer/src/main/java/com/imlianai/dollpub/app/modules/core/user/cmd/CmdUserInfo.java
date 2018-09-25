package com.imlianai.dollpub.app.modules.core.user.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.core.user.util.UserUtil;
import com.imlianai.dollpub.app.modules.core.user.value.UserValueService;
import com.imlianai.dollpub.app.modules.core.user.vo.UserInfoReqVO;
import com.imlianai.dollpub.app.modules.core.user.vo.UserInfoRespVO;
import com.imlianai.dollpub.app.modules.core.user.vo.UserUpdateReqVO;
import com.imlianai.dollpub.app.modules.publics.kws.mr.Result;
import com.imlianai.dollpub.app.modules.publics.kws.service.KeyWordService;
import com.imlianai.dollpub.app.modules.publics.log.service.LogService;
import com.imlianai.dollpub.app.modules.support.invite.service.InviteService;
import com.imlianai.dollpub.app.modules.support.relation.service.RelationService;
import com.imlianai.dollpub.domain.log.LogPage;
import com.imlianai.dollpub.domain.trade.TradeAccount;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserCommon;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.dollpub.domain.user.UserName;
import com.imlianai.dollpub.domain.user.UserValue;
import com.imlianai.rpc.support.common.KeyWordsTools;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.utils.DateUtils;
//import com.imlianai.doll.app.modules.core.article.service.ArticleService;
//import com.imlianai.doll.app.modules.core.edit.service.ArticleEditService;

/**
 * 用户信息
 * 
 * @author Max
 */
@Service
@Api("用户信息相关")
public class CmdUserInfo {

	@Resource
	private LogService logService;
	@Resource
	private UserService userService;
	@Resource
	private UserValueService userValueService;
	@Resource
	private RelationService relationService;
	@Resource
	private InviteService inviteService;
	@Resource
	private KeyWordService keyWordService;
	@Resource
	private TradeService tradeService;

	@ApiHandle
	@LoginCheck
	@LogHead("用户资料页查询")
	@Path("api/user/info")
	@ApiOperation(value = "【1.0.0】用户资料页查询接口", notes = "用户资料页查询", httpMethod = "POST", response = UserInfoRespVO.class)
	public BaseRespVO info(UserInfoReqVO vo) {
		UserGeneral user = userService.getUserGeneral(vo.getTid());
		if (user != null && user.getValid() == 0) {
			UserBase base = userService.getUserBase(vo.getTid());
			UserCommon userCommon = new UserCommon(user, base, vo.getTid());
			UserInfoRespVO respVo = new UserInfoRespVO();
			respVo.setUser(userCommon);
			logService.addMoreDate(LogPage.USER_INFO, vo, this.getClass());
			return respVo;
		} else {
			logService.addMoreDate(LogPage.USER_INFO, vo, this.getClass());
			return new BaseRespVO(ResCodeEnum.USER_NOT_FOUND);
		}
	}

	@ApiHandle
	@LoginCheck
	@LogHead("查看自己资料页")
	@Path("api/user/self")
	@ApiOperation(value = "【1.0.0】查看自己资料页接口", notes = "查看自己资料页", httpMethod = "POST", response = UserInfoRespVO.class)
	public BaseRespVO self(UserInfoReqVO vo) {
		UserInfoRespVO respVo = new UserInfoRespVO();
		UserGeneral user = userService.getUserGeneral(vo.getUid());
		if (user != null) {
			UserBase base = userService.getUserBase(vo.getUid());
			UserCommon userCommon = new UserCommon(user, base);
			respVo.setUser(userCommon);
			UserValue userValue = userValueService.getUserValue(vo.getUid());
			if (userValue != null) {
				respVo.setInviteMsgNum(userValue.getInviteMsgNum());
			}
			TradeAccount account = tradeService.getAccount(vo.getUid());
			if (account != null) {
				respVo.setCoin(account.getCoin());
			}
		}else {
			return new BaseRespVO(ResCodeEnum.ACCOUNT_IS_BAN);
		}
		logService.addMoreDate(LogPage.USER_INFO, vo, this.getClass());
		return respVo;
	}

	@ApiHandle
	@LoginCheck
	@LogHead("用户信息更新")
	@Path("api/user/update")
	@ApiOperation(value = "【1.0.0】用户信息更新接口", notes = "用户信息更新", httpMethod = "POST", response = UserInfoRespVO.class)
	public BaseRespVO update(UserUpdateReqVO vo) {
		if (vo.getName() != null) {
			vo.setName(vo.getName().replaceAll(" ", ""));
			if (!UserUtil.checkUsername(vo.getName())) {
				return new BaseRespVO()
						.getRespVO(ResCodeEnum.NAME_LENGTH_ERROR);
			}
		}
		if (StringUtils.isNotBlank(vo.getName())) {// 同名检测
			if (KeyWordsTools.isMaskStr(vo.getName()))
				return new BaseRespVO().getRespVO(ResCodeEnum.WORD_ERROR);
			vo.setName(AppUtils.getNoXSS(vo.getName()));
			if (userService.isSameName(vo.getName()))
				return new BaseRespVO().getRespVO(ResCodeEnum.NAME_SAME);
		}
		UserGeneral user = userService.getUserGeneral(vo.getUid());
		if (StringUtils.isNotBlank(vo.getAbout())) {
			vo.setAbout(StringUtils.substring(vo.getAbout(), 0, 50));
			String verifyAbout = AppUtils.removeSymbol(vo.getAbout());
			Result match = keyWordService.matchA(verifyAbout);
			if (match != null) {
				return new BaseRespVO().getRespVO(ResCodeEnum.WORD_ERROR);
			}
			match = keyWordService.matchB(verifyAbout);
			vo.setAbout(AppUtils.getNoXSS(vo.getAbout()));
		}
		userService.updateUserInfo(vo);
		user = userService.getUserGeneral(vo.getUid());
		UserBase base = userService.getUserBase(vo.getUid());
		UserCommon userCommon = new UserCommon(user, base);
		UserInfoRespVO respVo = new UserInfoRespVO();
		respVo.setUser(userCommon);
		logService.addMoreDate(LogPage.USER_UPDATE, vo, this.getClass());
		return respVo;
	}

}
