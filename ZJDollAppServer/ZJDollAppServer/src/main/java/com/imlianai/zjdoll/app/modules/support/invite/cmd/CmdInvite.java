package com.imlianai.zjdoll.app.modules.support.invite.cmd;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.invite.InviteRelation;
import com.imlianai.zjdoll.domain.invite.InviteRewardRecord;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserBase.UserSrcType;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.cms.service.CmsService;
import com.imlianai.zjdoll.app.modules.publics.cms.utils.CmsCacheUtil;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.util.Event20180320InviteUtil;
import com.imlianai.zjdoll.app.modules.support.invite.service.InviteService;
import com.imlianai.zjdoll.app.modules.support.invite.util.InviteUtil;
import com.imlianai.zjdoll.app.modules.support.invite.vo.InviteAddReqVO;
import com.imlianai.zjdoll.app.modules.support.invite.vo.InviteInfoReqVO;
import com.imlianai.zjdoll.app.modules.support.invite.vo.InviteInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.invite.vo.InviteMineRespVO;
import com.imlianai.zjdoll.app.modules.support.invite.vo.InviteRewardReqVO;

/**
 * 邀请相关
 * 
 * @author Max
 * 
 */
@Component("invite")
@Api("邀请相关")
public class CmdInvite extends RootCmd {

	@Resource
	private UserService userService;
	@Resource
	private InviteService inviteService;

	@Resource
	private CmsService cmsService;

	@ApiHandle
	@LogHead("获取填写邀请码页面接口")
	@Path("api/invite/code")
	@ApiOperation(value = "【1.0.0】填写邀请码页面接口", notes = "填写邀请码页面", httpMethod = "POST", response = InviteInfoRespVO.class)
	public BaseRespVO code(InviteInfoReqVO vo) {
		InviteInfoRespVO resp = new InviteInfoRespVO();
		UserBase user = userService.getUserBase(vo.getUid());
		if (user.getSrcType() == UserSrcType.WECHAT.type) {
			// 是否已填邀请码
			long invitedId = inviteService.getInviteId(vo.getUid());
			if (invitedId > 0) {
				InviteRelation invitedIdRelation=inviteService.getInviteRelationByUid(invitedId);
				if (invitedIdRelation!=null) {
					resp.setInvitedCode(invitedIdRelation.getCode());
				}
			} else {
				resp.setShowInput(true);
			}
		} else {
			resp.setShowInput(false);
			String invitedHtmlR = String.format(invitedHtml,
					UserSrcType.getType(user.getSrcType()).des);
			resp.setInvitedHtml(invitedHtmlR);
		}
		String officalHtml=cmsService.getTextConfig(CmsCacheUtil.INVITE_OFFICIAL_CODE);
		resp.setOfficalHtml(officalHtml);
		List<InviteRewardRecord> rewardList=inviteService.getInviteRewardRecords(1, 1, 50);
		List<String> srcollList = new ArrayList<String>();
		if (!StringUtil.isNullOrEmpty(rewardList)) {
			for (InviteRewardRecord inviteRewardRecord : rewardList) {
				srcollList.add(inviteRewardRecord.getHead()+ "邀请了" + inviteRewardRecord.getName()+ "获得"+inviteRewardRecord.getReward()+"币");
			}
		}
		resp.setSrcollList(srcollList);
		return resp;
	}

	private static final String invitedHtml = "微信登录后，输入好友的福利码，双方都可获得"+InviteUtil.getRegReward()+"币！（您当前为%s登录）";
	private static final String officalHtml = "1、QQ福利群：467023853，官方长期派发福利哟！<br/>2、同一福利码，每人只能使用一次，同一设备、同一ID视为同一人。";

	@ApiHandle
	@LogHead("填写邀请码接口")
	@LoginCheck
	@Path("api/invite/add")
	@ApiOperation(value = "【1.0.0】填写邀请码接口", notes = "填写邀请码,特殊返回码 112-邀请码有误，请重新输入 149-邀请码使用次数已达上限", httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO add(InviteAddReqVO vo) {
		if (vo.getType() == 1) {// 用户邀请码
			if (vo.getInviteCode() == 66666) {
				return new BaseRespVO(ResCodeEnum.INVITE_ERROR_DOLL);
			} else if (vo.getInviteCode() == 9999999) {
				return new BaseRespVO(ResCodeEnum.INVITE_EXHAUST);
			} else {
				long invitedId = inviteService.getInviteId(vo.getUid());
				if (invitedId > 0) {
					return new BaseRespVO(0, false, "您已填写过福利码了");
				} else {
					int num=inviteService.getInviteCodeWrongTimes(vo.getUid());
					if (num>=3) {
						return new BaseRespVO(0,false,"福利码错误次数太多");
					}
					InviteRelation relation = inviteService
							.getInviteRelationByCode(vo.getInviteCode());
					if (relation==null) {
						inviteService.addInviteCodeWrongTimes(vo.getUid());
						return new BaseRespVO(ResCodeEnum.INVITE_ERROR_DOLL);
					}
					if (relation.getUid()==vo.getUid().longValue()) {
						return new BaseRespVO(ResCodeEnum.INVITE_SELF_DOLL);
					}
					// 邀请奖励活动时间 20180324-20180412
					int eventStatus = Event20180320InviteUtil.eventStatus();
					if (eventStatus == 0 || eventStatus == 2){
						//return new BaseRespVO(0,false,Event20180320InviteUtil.EVENT_NOT_BEGIN);
						if (relation.getUsedTimes()>=InviteUtil.getInviteTimeLimit()) {
							return new BaseRespVO(ResCodeEnum.INVITE_EXHAUST);
						}
					}
					logger.info("活动状态=>" + Event20180320InviteUtil.EVENT_MOTION);
					return inviteService.addInvite(vo.getUid(),relation.getUid(), vo.getInviteCode());
				}
			}
		} else if (vo.getType() == 2) {// 官方邀请码
			return inviteService.addOfficalCode(vo.getUid(), vo.getImei(), vo.getInviteCode());
		}
		return new BaseRespVO(ResCodeEnum.INVITE_ERROR_DOLL);
	}

	@ApiHandle
	@LogHead("获取我的邀请码页面接口")
	@Path("api/invite/mine")
	@ApiOperation(value = "【1.0.0】我的邀请码页面接口", notes = "我的邀请码页面", httpMethod = "POST", response = InviteMineRespVO.class)
	public BaseRespVO mine(InviteInfoReqVO vo) {
		InviteMineRespVO resp = new InviteMineRespVO();
		InviteRelation relation=inviteService.getInviteRelationByUid(vo.getUid());
		if (relation!=null) {
			resp.setInvitedCode(relation.getCode());
		}
		List<InviteRewardRecord> regList=inviteService.getInviteRewardRecords(vo.getUid(), 1, 1, InviteUtil.getInviteTimeLimit());
		if (regList.size()<InviteUtil.getInviteTimeLimit()) {
			int add=InviteUtil.getInviteTimeLimit()-regList.size();
			for (int i = 0; i < add; i++) {
				regList.add(new InviteRewardRecord(vo.getUid(), InviteUtil.getRegReward(), 1));
			}
		}
		resp.setInviteList(regList);
		List<InviteRewardRecord> successList=inviteService.getInviteRewardRecords(vo.getUid(), 2, 1, InviteUtil.getInviteTimeLimit());
//		if (!StringUtil.isNullOrEmpty(successList)) {
//			for (InviteRewardRecord inviteRewardRecord : successList) {
//				inviteRewardRecord.setRemark(DateUtils.dateToStringWithTime(inviteRewardRecord.getTime())+" "+inviteRewardRecord.getRemark());
//			}
//		}
		resp.setSuccessList(successList);
		List<InviteRewardRecord> rewardList=inviteService.getInviteRewardRecords(1, 1, 50);
		List<String> srcollList = new ArrayList<String>();
		if (!StringUtil.isNullOrEmpty(rewardList)) {
			for (InviteRewardRecord inviteRewardRecord : rewardList) {
				srcollList.add(inviteRewardRecord.getHead()+ "邀请了" + inviteRewardRecord.getName()+ "获得"+inviteRewardRecord.getReward()+"币");
			}
		}
		resp.setSrcollList(srcollList);
		UserBase base=userService.getUserBase(vo.getUid());
		if (base!=null) {
			resp.setInvitedHtml(String.format(resp.getInvitedHtml(), UserSrcType.getType(base.getSrcType()).des));
		}
		return resp;
	}

	@ApiHandle
	@LoginCheck
	@LogHead("领取邀请奖励接口")
	@Path("api/invite/reward")
	@ApiOperation(value = "【1.0.0】领取邀请奖励接口", notes = "领取邀请奖励", httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO reward(InviteRewardReqVO vo) {
		return inviteService.gainInviteReward(vo.getUid(), vo.getRewardId());
	}
}
