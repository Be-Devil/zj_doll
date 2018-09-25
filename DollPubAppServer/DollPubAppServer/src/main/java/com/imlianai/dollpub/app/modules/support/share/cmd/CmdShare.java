package com.imlianai.dollpub.app.modules.support.share.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.doll.info.DollInfoService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.support.invite.service.InviteService;
import com.imlianai.dollpub.app.modules.support.share.service.ShareService;
import com.imlianai.dollpub.app.modules.support.share.vo.ShareInfoReqVo;
import com.imlianai.dollpub.app.modules.support.share.vo.ShareInfoRespVo;
import com.imlianai.dollpub.app.modules.support.share.vo.ShareWinReqVo;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.dollpub.domain.invite.InviteRelation;
import com.imlianai.dollpub.domain.share.ShareInfo;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;

@Api("分享相关")
@Component("share")
public class CmdShare extends RootCmd {

	@Resource
	ShareService shareService;
	@Resource
	DollBusService dollBusService;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	InviteService inviteService;
	@Resource
	UserService userService;

	@ApiHandle
	@LoginCheck
	@Path("/api/share/info")
	@ApiOperation(value = "【1.0.0】获取分享详情接口", notes = "根据分享code获取分享信息", httpMethod = "POST", response = ShareInfoRespVo.class)
	public BaseRespVO info(ShareInfoReqVo vo) {
		ShareInfo shareInfo = shareService.getShareInfo(vo.getShareCode());
		if (shareInfo != null) {
			if (vo.getShareCode().trim().equals("share_game_record")) {
				shareInfo.setLink(shareInfo.getLink() + "?uid=" + vo.getUid()
						+ "&d1=2");
			}
			if (vo.getShareCode().trim().equals("doll_win")) {
				shareInfo.setLink(shareInfo.getLink() + "?uid=" + vo.getUid()+"&did="+vo.getId()
						+ "&d1=2");
			}
			ShareInfoRespVo respVo = new ShareInfoRespVo(shareInfo);
			return respVo;
		}
		return new BaseRespVO(ResCodeEnum.NOT_DATA);
	}

	@ApiHandle
	@Path("/api/share/win")
	@ApiOperation(value = "【1.0.0】获取分享捉取成功详情接口", notes = "根据分享code获取分享信息", httpMethod = "POST", response = ShareInfoRespVo.class)
	public BaseRespVO win(ShareWinReqVo vo) {
		DollBus bus = dollBusService.getDollBus(vo.getBusId());
		if (bus != null) {
			BaseRespVO respVo = new BaseRespVO();
			Map<String, Object> res = new HashMap<String, Object>();
			DollInfo info = dollInfoService.getDollInfo(bus.getDollId());
			InviteRelation relation = inviteService.getInviteRelationByUid(vo
					.getUid());
			res.put("img", info.getImgSuccess());
			res.put("name", info.getName());
			if (relation==null) {
				res.put("code", 0);
			}else{
				res.put("code", relation.getCode());
			}
			respVo.setData(res);
			return respVo;
		}
		return new BaseRespVO(ResCodeEnum.NOT_DATA);
	}
	
	@ApiHandle
	@Path("/api/share/inviteCode")
	@ApiOperation(value = "【1.0.0】获取分享捉取成功详情接口", notes = "根据分享code获取分享信息", httpMethod = "POST", response = ShareInfoRespVo.class)
	public BaseRespVO inviteCode(ShareWinReqVo vo) {
		BaseRespVO respVo = new BaseRespVO();
		InviteRelation relation = inviteService.getInviteRelationByUid(vo
				.getUid());
		Map<String, Object> res = new HashMap<String, Object>();
		if (relation != null) {
			res.put("code", relation.getCode());
		}else{
			res.put("code", 0);
		}
		UserGeneral user=userService.getUserGeneral(vo.getUid());
		if(user!=null){
			res.put("name", user.getName());
			res.put("head", user.getHead());
		}
		respVo.setData(res);
		return respVo;
	}
}
