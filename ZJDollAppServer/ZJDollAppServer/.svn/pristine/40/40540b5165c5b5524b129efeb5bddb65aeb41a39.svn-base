package com.imlianai.zjdoll.app.modules.support.redpacket.cmd;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.support.redpacket.certification.service.CertificationService;
import com.imlianai.zjdoll.app.modules.support.redpacket.service.RedpacketService;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BindPhoneCheckCodeReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BindPhoneReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BoundWechatReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BusRedpacket;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BusRedpacketGetReqVo;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BusRedpacketReqVo;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BusRedpacketRespVo;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.GetInviteInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.GetInviteSituationRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.GetPageInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.GetRecordsReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.GetRecordsRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.GetRedpacketReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.GetWithdrawInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.HandleFaceCerReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.HandleFaceCerRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.HandleInviteReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.HandleWithdrawReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.InviteRedpacketRes;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.MyRedpacketInfo;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.MyRedpacketRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.QueryFaceResultRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.RedpacketListRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.RedpacketLogRes;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.RedpacketOpenRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.SaveInvitePreRecordReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.VerifyAmtReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.WithdrawInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("红包")
@LogHead("红包模块")
@Component("redpacket")
public class CmdRedpacket extends RootCmd {

	@Resource
	RedpacketService redpacketService;
	@Resource
	CertificationService certificationService;
	@Resource
	TradeService tradeService;

	@ApiHandle
	@Path("api/redpacket/myRedpacket")
	@ApiOperation(value = "【1.1.0】我的红包", notes = "我的红包", httpMethod = "POST", response = MyRedpacketRespVO.class)
	@LogHead("我的红包")
	public BaseRespVO myRedpacket(BaseReqVO reqVO) {
		MyRedpacketRespVO respVO = new MyRedpacketRespVO();
		MyRedpacketInfo myRedpacketInfo = redpacketService
				.getMyRedpacketInfo(reqVO);
		TradeAccount tradeAccount = tradeService.getAccount(reqVO.getUid());
		respVO.setMyRedpacketInfo(myRedpacketInfo);
		respVO.setCoin(tradeAccount == null ? 0 : tradeAccount.getCoin());
		return respVO;
	}

	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/getRedpacket")
	@ApiOperation(value = "【1.1.0】领取红包", notes = "领取红包", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("领取红包")
	public BaseRespVO getRedpacket(GetRedpacketReqVO reqVO) {
		return redpacketService.getRedpacket(reqVO);
	}

	@ApiHandle
	@Path("api/redpacket/getList")
	@ApiOperation(value = "【1.1.0】红包列表", notes = "红包列表", httpMethod = "POST", response = RedpacketListRespVO.class)
	@LogHead("红包列表")
	public BaseRespVO getList(BaseReqVO reqVO) {
		RedpacketListRespVO respVO = new RedpacketListRespVO();
		List<InviteRedpacketRes> inviteRedpacks = redpacketService
				.getList(reqVO.getUid());
		respVO.setInviteRedpacks(inviteRedpacks);
		respVO.setIsOverNum(redpacketService.isOverNum(reqVO.getUid()));
		return respVO;
	}

	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/open")
	@ApiOperation(value = "【1.1.0】开启红包", notes = "开启红包", httpMethod = "POST", response = RedpacketOpenRespVO.class)
	@LogHead("开启红包")
	public BaseRespVO open(GetRedpacketReqVO reqVO) {
		return redpacketService.open(reqVO);
	}

	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/getWithdrawInfo")
	@ApiOperation(value = "【1.1.0】获取提现信息", notes = "获取提现信息", httpMethod = "POST", response = GetWithdrawInfoRespVO.class)
	@LogHead("获取提现信息")
	public BaseRespVO getWithdrawInfo(BaseReqVO reqVO) {
		GetWithdrawInfoRespVO respVO = new GetWithdrawInfoRespVO();
		WithdrawInfo withdrawInfo = redpacketService.getWithdrawInfo(reqVO
				.getUid());
		respVO.setWithdrawInfo(withdrawInfo);
		return respVO;
	}

	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/verifyAmt")
	@ApiOperation(value = "【1.1.0】提现校验金额", notes = "提现校验金额", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("提现校验金额")
	public BaseRespVO verifyAmt(VerifyAmtReqVO reqVO) {
		return redpacketService.verifyAmt(reqVO.getUid(), reqVO.getAmount());
	}

	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/boundWechat")
	@ApiOperation(value = "【1.1.0】微信授权", notes = "微信授权", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("微信授权")
	public BaseRespVO boundWechat(BoundWechatReqVO reqVO) {
		return redpacketService.boundWechat(reqVO);
	}

	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/handleFaceCertification")
	@ApiOperation(value = "【1.1.0】处理人脸实名", notes = "处理人脸实名", httpMethod = "POST", response = HandleFaceCerRespVO.class)
	@LogHead("处理人脸实名")
	public BaseRespVO handleFaceCertification(HandleFaceCerReqVO reqVO) {
		return certificationService.handleFaceCertification(reqVO);
	}

	@ApiHandle
	@Path("api/redpacket/queryFaceResult")
	@ApiOperation(value = "【1.1.0】查询是否已实名", notes = "查询是否已实名", httpMethod = "POST", response = QueryFaceResultRespVO.class)
	@LogHead("查询是否已实名")
	public BaseRespVO queryFaceResult(BaseReqVO reqVO) {
		BaseRespVO baseRespVO=certificationService.queryFaceResult(reqVO.getUid());
		logger.info("queryFaceResult "+JSON.toJSONString(baseRespVO));
		return baseRespVO;
	}

	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/handleWithdraw")
	@ApiOperation(value = "【1.1.0】提现", notes = "提现", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("提现")
	public BaseRespVO handleWithdraw(HandleWithdrawReqVO reqVO) {
		return redpacketService.handleWithdraw(reqVO);
	}

	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/queryBusRedpacket")
	@ApiOperation(value = "【1.1.0】查询成功捉取分享红包", notes = "查询成功捉取分享红包", httpMethod = "POST", response = BusRedpacketRespVo.class)
	@LogHead("查询成功捉取分享红包")
	public BaseRespVO queryBusRedpacket(BusRedpacketReqVo reqVO) {
		BusRedpacket busRedpacket = redpacketService.getBusRedpacket(
				reqVO.getUid(), reqVO.getBusId(), reqVO.getOptId());
		BusRedpacketRespVo resp = new BusRedpacketRespVo();
		if (busRedpacket != null) {
			resp.setRedpacket(busRedpacket);
		}
		return resp;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/getBusRedpacket")
	@ApiOperation(value = "【1.1.0】获取房间红包", notes = "获取房间红包", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("获取房间红包")
	public BaseRespVO getBusRedpacket(BusRedpacketGetReqVo reqVO) {
		return new BaseRespVO();
	}
	
	
	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/getSuperBusRedpacket")
	@ApiOperation(value = "【1.2.1】领取房间暴击红包", notes = "领取房间暴击红包", httpMethod = "POST", response = BusRedpacketRespVo.class)
	@LogHead("获取房间红包")
	public BaseRespVO getSuperBusRedpacket(BusRedpacketGetReqVo reqVO) {
		BusRedpacketRespVo respVo=new BusRedpacketRespVo();
		BusRedpacket redpacket =redpacketService.getSuperBusRedpacket(reqVO.getRedpacketId(), reqVO.getUid());
		if (redpacket!=null) {
			respVo.setRedpacket(redpacket);
		}
		return respVo;
	}
	
	@ApiHandle
	@Path("api/redpacket/getInviteInfo")
	@ApiOperation(value = "【1.1.0】获取邀请信息", notes = "获取邀请信息", httpMethod = "POST", response = GetInviteInfoRespVO.class)
	@LogHead("获取邀请信息")
	public BaseRespVO getInviteInfo() {
		return new GetInviteInfoRespVO();
	}
	
	@ApiHandle
	@Path("api/redpacket/saveInvitePreRecord")
	@ApiOperation(value = "【1.1.0】保存预邀请记录", notes = "保存预邀请记录", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("保存预邀请记录")
	public BaseRespVO saveInvitePreRecord(SaveInvitePreRecordReqVO reqVO) {
		return redpacketService.saveInvitePreRecord(reqVO.getUid(), reqVO.getUnionId());
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/checkcode")
	@ApiOperation(value = "【1.1.0】提现手机绑定-发送验证码", notes = "提现手机绑定-发送验证码", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("提现手机绑定-发送验证码")
	public BaseRespVO checkcode(BindPhoneCheckCodeReqVO reqVO) {
		return redpacketService.checkcode(reqVO.getUid(), reqVO.getNumber());
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/bindPhone")
	@ApiOperation(value = "【1.1.0】提现手机绑定-绑定手机", notes = "提现手机绑定-绑定手机", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("提现手机绑定-绑定手机")
	public BaseRespVO bindPhone(BindPhoneReqVO reqVO) {
		return redpacketService.bindPhone(reqVO.getUid(), reqVO.getNumber(), reqVO.getCheckCode());
	}
	
	@ApiHandle
	@Path("api/redpacket/getRecords")
	@ApiOperation(value = "【1.2.1】红包明细", notes = "红包明细", httpMethod = "POST", response = GetRecordsRespVO.class)
	@LogHead("红包明细")
	public BaseRespVO getRecords(GetRecordsReqVO reqVO) {
		GetRecordsRespVO respVO = new GetRecordsRespVO();
		List<RedpacketLogRes> records = redpacketService.getRecords(reqVO.getUid(), reqVO.getPage());
		respVO.setRecords(records);
		return respVO;
	}
	
	@ApiHandle
	@Path("api/redpacket/getInviteSituation")
	@ApiOperation(value = "【1.2.1】邀请好友情况", notes = "邀请好友情况", httpMethod = "POST", response = GetInviteSituationRespVO.class)
	@LogHead("邀请好友情况")
	public BaseRespVO getInviteSituation(BaseReqVO reqVO) {
		return redpacketService.getInviteSituation(reqVO.getUid());
	}
	
	
	@ApiHandle
	@LoginCheck
	@Path("api/redpacket/handleInvite")
	@ApiOperation(value = "【1.2.1】(好友红包)暴击红包分享成功处理", notes = "(好友红包)暴击红包分享成功处理", httpMethod = "POST", response = RedpacketOpenRespVO.class)
	@LogHead("(好友红包)暴击红包分享成功处理")
	public BaseRespVO handleInvite(HandleInviteReqVO reqVO) {
		return redpacketService.handleInvite(reqVO.getUid(), reqVO.getId());
	}
	
	@ApiHandle
	@LogHead("暴击红包分享页面信息")
	@Path("api/redpacket/getPageInfo")
	@ApiOperation(value = "【1.2.1】暴击红包分享页面信息(h5调用)", notes = "暴击红包分享页面信息", httpMethod = "POST", response = GetPageInfoRespVO.class) 
	public BaseRespVO getPageInfo(BaseReqVO vo) {
		return redpacketService.getPageInfo(vo.getUid());
	}
	
}
