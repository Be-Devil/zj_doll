package com.imlianai.dollpub.app.modules.support.pack.cmd;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.doll.info.DollInfoService;
import com.imlianai.dollpub.app.modules.core.doll.share.DollShareService;
import com.imlianai.dollpub.app.modules.support.pack.constants.PackConstants;
import com.imlianai.dollpub.app.modules.support.pack.domain.GrabDollRecord;
import com.imlianai.dollpub.app.modules.support.pack.service.PackService;
import com.imlianai.dollpub.app.modules.support.pack.vo.AppealInfo;
import com.imlianai.dollpub.app.modules.support.pack.vo.AppealReqVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.AppealStatusReqVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.AppealStatusRespVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.GetDescReqVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.GetDescRespVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.GetDollDetailReqVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.GetDollDetailRespVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.GetDollGrabRecordRespVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.GetDollListReqVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.GetDollListRespVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.GetOptRecordReqVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.GetOptRecordRespVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.OptRecordInfo;
import com.imlianai.dollpub.app.modules.support.pack.vo.ReceiveReqVO;
import com.imlianai.dollpub.app.modules.support.shipping.service.ShippingService;
import com.imlianai.dollpub.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.dollpub.domain.doll.BaseDollInfo;
import com.imlianai.dollpub.domain.doll.DollDetails;
import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.dollpub.domain.doll.user.UserDoll;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("背包")
@LogHead("背包模块")
@Component("pack")
public class CmdPack extends RootCmd{
	
	private static final BaseLogger LOG = BaseLogger.getLogger(CmdPack.class);
	
	@Resource
	private PackService packService;

	@Resource
	private ShippingService shippingService;

	@Resource
	private DollShareService dollShareService;

	@Resource
	private DollInfoService dollInfoService;

	@Resource
	private UserDollService userDollService;

	@ApiHandle
	@Path("api/pack/getUserGrabRecord")
	@ApiOperation(value = "获取用户抓取记录", notes = "获取用户抓取记录", httpMethod = "POST", response = GetDollGrabRecordRespVO.class)
	@LogHead("获取用户抓取记录")
	public GetDollGrabRecordRespVO getUserGrabRecord(BaseReqVO reqVO) {
		LOG.info("uid=>" + reqVO.getUid());
		GetDollGrabRecordRespVO dollGrabRecordRespVO = new GetDollGrabRecordRespVO();
		List<GrabDollRecord> list = packService.getGrabRecord(reqVO.getUid());
		if(!StringUtil.isNullOrEmpty(list)) {
			dollGrabRecordRespVO.setData(list);
		}else {
			dollGrabRecordRespVO.setMsg("暂无抓取记录，快去抓一只吧~");
		}
		return dollGrabRecordRespVO;
	}
	
	@SuppressWarnings("unchecked")
	@ApiHandle
	@Path("api/pack/getDollList")
	@ApiOperation(value = "获取抓到的娃娃列表", notes = "获取抓到的娃娃列表", httpMethod = "POST", response = GetDollListRespVO.class)
	@LogHead("获取抓到的娃娃列表")
	public BaseRespVO getDollList(GetDollListReqVO reqVO) {
		GetDollListRespVO respVO = new GetDollListRespVO();
		Map<String, Object> dataMap = packService.getDollList(reqVO.getUid(), reqVO.getLastId(), reqVO.getStatus());
		respVO.setDollList((List<BaseDollInfo>) dataMap.get("dollInfoList"));
		respVO.setSize((int) dataMap.get("size"));
		return respVO;
	}
	
	@ApiHandle
	@Path("api/pack/getDollDetail")
	@ApiOperation(value = "获取娃娃详情信息", notes = "获取娃娃详情信息", httpMethod = "POST", response = GetDollDetailRespVO.class)
	@LogHead("获取娃娃详情信息")
	public BaseRespVO getDollDetail(GetDollDetailReqVO reqVO) {
		GetDollDetailRespVO respVO = new GetDollDetailRespVO();
		DollDetails dollDetail = packService.getDollDetail(reqVO.getId());
		respVO.setDollDetail(dollDetail);
		UserDoll userDoll = userDollService.getUserDollById(reqVO.getId());
		DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
		respVO.setDollSucceRes(dollShareService.getDollSuccessShare(reqVO.getUid(), dollInfo, 0, reqVO.getCustomerId()));
		if(dollDetail.getStatus() == 0) {
			respVO.setNotice(shippingService.getShippingNotice());
		}
		respVO.setPageTitle(PackConstants.PAGE_TITLE);
		respVO.setPageUrl(PackConstants.PAGE_URL);
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/pack/exchange")
	@ApiOperation(value = "娃娃兑换", notes = "娃娃兑换", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("娃娃兑换")
	public BaseRespVO exchange(GetDollDetailReqVO reqVO) {
		return packService.exchange(reqVO.getId(), reqVO.getUid());
	}
	
	@ApiHandle
	@Path("api/pack/getOptRecords")
	@ApiOperation(value = "游戏记录", notes = "游戏记录", httpMethod = "POST", response = GetOptRecordRespVO.class)
	@LogHead("游戏记录")
	public BaseRespVO getOptRecords(GetOptRecordReqVO reqVO) {
		GetOptRecordRespVO respVO = new GetOptRecordRespVO();
		List<OptRecordInfo> records = packService.getOptRecords(reqVO.getLastId(),reqVO.getUid(),reqVO.getCustomerId());
		respVO.setRecords(records);
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/pack/appeal")
	@ApiOperation(value = "申诉", notes = "申诉", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("申诉")
	public BaseRespVO appeal(AppealReqVO reqVO) {
		return packService.appeal(reqVO.getOptId(),reqVO.getCustomerId(), reqVO.getUid(), reqVO.getReason());
	}
	
	@ApiHandle
	@Path("api/pack/appealStatus")
	@ApiOperation(value = "查看申诉状态", notes = "查看申诉状态", httpMethod = "POST", response = AppealStatusRespVO.class)
	@LogHead("查看申诉状态")
	public BaseRespVO appealStatus(AppealStatusReqVO reqVO) {
		AppealStatusRespVO respVO = new AppealStatusRespVO();
		AppealInfo appealInfo = packService.appealStatus(reqVO.getOptId());
		respVO.setAppealInfo(appealInfo);
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/pack/recycle")
	@ApiOperation(value = "娃娃回收", notes = "娃娃回收", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("娃娃回收")
	public BaseRespVO recycle(GetDollDetailReqVO reqVO) {
		return packService.recycle(reqVO.getId(), reqVO.getUid());
	}
	
	@ApiHandle
	@Path("api/pack/getNotice")
	@ApiOperation(value = "获取娃娃的温馨提示", notes = "获取娃娃的温馨提示", httpMethod = "POST", response = GetDescRespVO.class)
	@LogHead("获取娃娃的温馨提示")
	public BaseRespVO getNotice(GetDescReqVO reqVO) {
		return packService.getNotice(reqVO.getDollId());
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/pack/receive")
	@ApiOperation(value = "娃娃领取", notes = "娃娃领取", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("娃娃领取")
	public BaseRespVO receive(ReceiveReqVO reqVO) {
		return packService.receive(reqVO);
	}
}
