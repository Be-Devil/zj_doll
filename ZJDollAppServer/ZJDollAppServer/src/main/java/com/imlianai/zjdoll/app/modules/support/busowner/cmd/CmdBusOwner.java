package com.imlianai.zjdoll.app.modules.support.busowner.cmd;


import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.msg.MsgRoomJump;
import com.imlianai.zjdoll.domain.msg.MsgRoomType;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.busowner.service.BusOwnerService;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.AddShareValueReqVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetBusOwnerInfoReqVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetBusOwnerInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetIncomeInfoReqVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetIncomeInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetPlayerRankingsReqVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetPlayerRankingsRespVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetShareRecordsReqVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetShareRecordsRespVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetShopListRespVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetTimeInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.IsMengDianReqVO;
import com.imlianai.zjdoll.app.schedule.BusOwnerTask;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("萌主")
@LogHead("萌主")
@Component("busOwner")
public class CmdBusOwner extends RootCmd {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(CmdBusOwner.class);
	
	@Resource
	BusOwnerService busOwnerService;
	@Resource
	UserService userService;
	@Resource
	MsgService msgService;
	@Resource
	DollBusService dollBusService;
	@Resource
	BusOwnerTask busOwnerTask;
	
	@ApiHandle
	@Path("api/busOwner/addShareValue")
	@ApiOperation(value = "增加分享值(h5)", notes = "增加分享值", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("增加分享值")
	public BaseRespVO addShareValue(AddShareValueReqVO reqVO) {
		return busOwnerService.addShareValue(reqVO);
	}
	
	@ApiHandle
	@Path("api/busOwner/getShareRecords")
	@ApiOperation(value = "查看分享值记录(h5)", notes = "查看分享值记录", httpMethod = "POST", response = GetShareRecordsRespVO.class)
	@LogHead("查看分享值记录")
	public BaseRespVO getShareRecords(GetShareRecordsReqVO reqVO) {
		GetShareRecordsRespVO respVO = new GetShareRecordsRespVO();
		respVO.setShareRecords(busOwnerService.getShareRecords(reqVO.getUid(), reqVO.getUnionId(), reqVO.getBusId()));
		return respVO;
	}
	
	@ApiHandle
	@Path("api/busOwner/getPlayerRankings")
	@ApiOperation(value = "玩家排行榜单列表(h5)", notes = "玩家排行榜单列表", httpMethod = "POST", response = GetPlayerRankingsRespVO.class)
	@LogHead("玩家排行榜单列表")
	public BaseRespVO getPlayerRankings(GetPlayerRankingsReqVO reqVO) {
		GetPlayerRankingsRespVO respVO = new GetPlayerRankingsRespVO();
		respVO.setPlayerRankingItems(busOwnerService.getPlayerRankings(reqVO.getUid(), reqVO.getBusId()));
		return respVO;
	}
	
	@ApiHandle
	@Path("api/busOwner/getTimeInfo")
	@ApiOperation(value = "当前轮时间信息(h5)", notes = "当前轮时间信息", httpMethod = "POST", response = GetTimeInfoRespVO.class)
	@LogHead("当前轮时间信息")
	public BaseRespVO getTimeInfo(BaseReqVO reqVO) {
		return busOwnerService.getTimeInfo(reqVO);
	}
	
	@ApiHandle
	@Path("api/busOwner/getShopList")
	@ApiOperation(value = "萌店列表(h5)", notes = "萌店列表", httpMethod = "POST", response = GetShopListRespVO.class)
	@LogHead("萌店列表")
	public BaseRespVO getShopList(BaseReqVO reqVO) {
		GetShopListRespVO respVO = new GetShopListRespVO();
		respVO.setShopItems(busOwnerService.getShopList(reqVO));
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/busOwner/getIncomeInfo")
	@ApiOperation(value = "经营收益信息(h5)", notes = "经营收益信息", httpMethod = "POST", response = GetIncomeInfoRespVO.class)
	@LogHead("经营收益信息")
	public BaseRespVO getIncomeInfo(GetIncomeInfoReqVO reqVO) {
		BaseRespVO respVO = busOwnerService.getIncomeInfo(reqVO.getUid(), reqVO.getBusId());
		LOG.info("getIncomeInfo:uid-" + reqVO.getUid() + ",busId-" + reqVO.getBusId() + ",respVO-" + JSON.toJSON(respVO));
		return respVO;
	}
	
	@ApiHandle
	@Path("api/busOwner/getBusOwnerInfo")
	@ApiOperation(value = "萌主基本信息(h5)", notes = "萌主基本信息", httpMethod = "POST", response = GetBusOwnerInfoRespVO.class)
	@LogHead("萌主基本信息")
	public BaseRespVO getBusOwnerInfo(GetBusOwnerInfoReqVO reqVO) {
		GetBusOwnerInfoRespVO respVO = new GetBusOwnerInfoRespVO();
		respVO.setBusOwnerInfos(busOwnerService.getBusOwnerInfos(reqVO.getBusId()));
		return respVO;
	}
	
	@ApiHandle
	@Path("api/busOwner/isMengDian")
	@ApiOperation(value = "判断某台机器是否为萌店(h5)", notes = "判断某台机器是否为萌店", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("判断某台机器是否为萌店")
	public BaseRespVO isMengDian(IsMengDianReqVO reqVO) {
		if(!busOwnerService.isMengDian(reqVO.getBusId())) {
			return new BaseRespVO(-1, false, reqVO.getBusId() + "号机不是萌店");
		}
		return new BaseRespVO();
	}
	
	// 产生萌主、奖励发放的定时任务
	/*@ApiHandle
	@Path("api/busOwner/busOwnerTask")
	public BaseRespVO busOwnerTask() {
		busOwnerTask.busOwnerTask();
		return new BaseRespVO();
	}*/
	
	// 榜单排名消息推送
	/*@ApiHandle
	@Path("api/busOwner/pushRankingMsg")
	public BaseRespVO pushRankingMsg() {
		busOwnerService.pushRankingMsg();
		return new BaseRespVO();
	}*/
	
	@ApiHandle
	@Path("api/busOwner/noticeMsgTest")
	public BaseRespVO noticeMsgTest(BaseReqVO reqVO) {
		Long uid = 1895586l;
		UserGeneral userGeneral = userService.getUserGeneral(uid);
		MsgRoomJump msgRoom = new MsgRoomJump(MsgRoomType.BUS_OWNER_NOTICE.type, 
				"<font color=\"#FFF203\">恭喜 " + userGeneral.getName() +" 成为 " + "57" + "号机 的萌主</font>", userGeneral); // 全站弹幕
		msgService.sendMsgRoomAll(msgRoom);
		
		uid = 1327263l;
		userGeneral = userService.getUserGeneral(uid);
		msgRoom = new MsgRoomJump(MsgRoomType.BUS_OWNER_NOTICE.type, 
				"<font color=\"#FFF203\">恭喜 " + userGeneral.getName() +" 成为 " + "63" + "号机 的萌主</font>", userGeneral); // 全站弹幕
		msgService.sendMsgRoomAll(msgRoom);
			
		return new BaseRespVO();
	}
}
