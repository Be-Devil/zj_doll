package com.imlianai.dollpub.app.modules.publics.oauth.wechat.cmd;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.event.service.WechatEventService;
import com.imlianai.dollpub.domain.trade.TradeAccount;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserBase.UserSrcType;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;

/**
 * 微信基础支持接口
 * 
 * @author tensloveq
 * 
 */
@Component("wechatUserReward")
public class CmdWechatUserReward extends RootCmd {

	@Resource
	private WechatEventService wechatEventService;
	
	@Resource
	UserService userService;
	@Resource
	TradeService tradeService;

	@ApiHandle
	public BaseRespVO hasReward(BaseReqVO vo) {
		UserBase userBase=userService.getUserBase(vo.getUid());
		if (userBase!=null) {
			int reward=wechatEventService.hasReward(userBase);
			BaseRespVO respVO=new BaseRespVO();
			respVO.setResult(reward);
			return respVO;
		}
		return new BaseRespVO(0, false, "");
	}

	@ApiHandle
	public BaseRespVO hasRewardSrc(BaseReqVO vo) {
		UserBase userBase=userService.getUserBase(vo.getUid());
		if (userBase!=null&&userBase.getSrcType()==UserSrcType.SRC_APP.type&&userBase.getAgentId()==91) {
			int reward=wechatEventService.hasReward(userBase);
			BaseRespVO respVO=new BaseRespVO();
			respVO.setResult(reward);
			return respVO;
		}
		return new BaseRespVO(0, false, "");
	}
	
	@ApiHandle
	public BaseRespVO getReward(BaseReqVO vo) {
		UserBase userBase=userService.getUserBase(vo.getUid());
		if (userBase!=null) {
			int reward=wechatEventService.getReward(userBase);
			if (reward>0) {
				TradeAccount account=tradeService.getAccount(vo.getUid());
				BaseRespVO respVO=new BaseRespVO();
				respVO.setResult(account.getCoin());
				return respVO;
			}
		}
		return new BaseRespVO(0, false, "");
	}

}
