package com.imlianai.dollpub.app.modules.support.userdoll.cmd;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import com.imlianai.dollpub.app.modules.support.userdoll.vo.*;
import com.imlianai.dollpub.domain.doll.DollDetails;
import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.doll.info.DollInfoService;
import com.imlianai.dollpub.app.modules.core.user.vo.GetDollsReqVO;
import com.imlianai.dollpub.app.modules.core.user.vo.GetDollsRespVO;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.dollpub.app.schedule.DollExchangeTask;
import com.imlianai.dollpub.app.schedule.HandleWeekRewardTask;
import com.imlianai.dollpub.domain.doll.BaseDollInfo;
import com.imlianai.dollpub.domain.doll.LogisticsInfo;
import com.imlianai.dollpub.domain.doll.user.RankingItem;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("用户娃娃")
@LogHead("用户娃娃")
@Component("userDoll")
public class CmdUserDoll extends RootCmd {

	@Resource
	private UserDollService userDollService;

	@ApiHandle
	@Path("api/userDoll/getAllDolls")
	@ApiOperation(value = "查看用户全部娃娃", notes = "查看用户全部娃娃", httpMethod = "POST", response = UserDollRespVO.class)
	@LogHead("查看用户全部娃娃")
	public BaseRespVO getAllDolls(GetDollsReqVO reqVO) {
		UserDollRespVO respVO = new UserDollRespVO();
		RankingItem rankingItem = userDollService.getUserDollCount(reqVO.getUid());
		List<BaseDollInfo> dollList = userDollService.getUserDollList(reqVO.getUid(), reqVO.getLastId());
		respVO.setUserInfo(rankingItem);
		respVO.setDollList(dollList);
		respVO.setAllNum(userDollService.getUserDollSizeByParams(reqVO.getUid(), -1));	//全部娃娃
		respVO.setExNum(userDollService.getUserDollSizeByParams(reqVO.getUid(), 3));	//已兑换
		respVO.setCheckNum(userDollService.getUserDollSizeByParams(reqVO.getUid(), 0));	//寄存中
		respVO.setShipkNum(userDollService.getUserDollSizeByParams(reqVO.getUid(), 8)); //已发货
		return respVO;
	}

	@ApiHandle
	@Path("api/userDoll/getDollsByStatus")
	@ApiOperation(value = "查看对应状态的用户娃娃", notes = "查看对应状态的用户娃娃", httpMethod = "POST", response = UserDollStateRespVO.class)
	@LogHead("查看对应状态的用户娃娃")
	public BaseRespVO getDollsByStatus(UserDollStateReqVO reqVO) {
		return userDollService.getUserDollByState(reqVO.getUid(), reqVO.getStatus());
	}

	@ApiHandle
	@Path("api/userDoll/getDollInfo")
	@ApiOperation(value = "获取单个娃娃详情", notes = "获取单个娃娃详情", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("获取单个娃娃详情")
	public BaseRespVO getDollInfo(UserDollInfoReqVO reqVO) {
		BaseRespVO baseRespVO = new BaseRespVO();
		DollDetails dollDetails = userDollService.getDollInfo(reqVO.getUid(), reqVO.getId());
		if(dollDetails != null){
			baseRespVO.setData(dollDetails);
			baseRespVO.setResult(100);
			baseRespVO.setState(true);

			// 0:寄存中
			if (dollDetails.getStatus() == 0) {
				baseRespVO.setMsg("寄存中娃娃");
			}

			// 1:成功申请发货 2:已发货 4:已拒绝
			if(dollDetails.getStatus() == 1 || dollDetails.getStatus() == 2 || dollDetails.getStatus() == 4){

				if (dollDetails.getStatus() == 1) {
					baseRespVO.setMsg("发货准备中");
				}

				if (dollDetails.getStatus() == 2) {
					baseRespVO.setMsg("已发货");
				}
				if (dollDetails.getStatus() == 4) {
					baseRespVO.setMsg("已拒绝");
				}
			}

			// 3:已兑换
			if (dollDetails.getStatus() == 3) {
				baseRespVO.setMsg("已兑换");
			}

			return baseRespVO;
		}
		return new BaseRespVO(-1,false,"找不到 id=" + reqVO.getId() + " 的娃娃信息~");
	}

	@ApiHandle
	@Path("api/userDoll/exchange")
	@ApiOperation(value = "兑换娃娃", notes = "兑换娃娃", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("兑换娃娃")
	public BaseRespVO exchange(UserDollInfoReqVO reqVO) {
		return userDollService.exchange(reqVO.getUid(), reqVO.getId());
	}
	
	@ApiHandle
	@Path("api/userDoll/getSize")
	@ApiOperation(value = "获取用户娃娃个数", notes = "获取用户娃娃个数", httpMethod = "POST", response = GetDollSizeRespVO.class)
	@LogHead("获取用户娃娃个数")
	public BaseRespVO getSize(BaseReqVO reqVO) {
		GetDollSizeRespVO respVO = new GetDollSizeRespVO();
		GetDollSizeInfo info = userDollService.getDollSize(reqVO.getUid());
		respVO.setDollSizeInfo(info);
		return respVO;
	}
	
	@ApiHandle
	@Path("api/userDoll/getDolls")
	@ApiOperation(value = "查看用户娃娃", notes = "查看用户娃娃", httpMethod = "POST", response = GetDollsRespVO.class)
	@LogHead("查看用户娃娃")
	public BaseRespVO getDolls(GetDollsReqVO reqVO) {
		GetDollsRespVO respVO = new GetDollsRespVO();
		RankingItem rankingItem = userDollService.getUserDollCount(reqVO.getUserId());
		List<BaseDollInfo> dollList = userDollService.getUserDollList(reqVO.getUserId(), reqVO.getLastId());
		respVO.setUserInfo(rankingItem);
		respVO.setDollList(dollList);
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/userDoll/getLogistics")
	@ApiOperation(value = "获取物流信息", notes = "获取物流信息", httpMethod = "POST", response = GetLogisticsRespVO.class)
	@LogHead("获取物流信息")
	public BaseRespVO getLogistics(GetLogisticsReqVO reqVO) {
		GetLogisticsRespVO respVO = new GetLogisticsRespVO();
		List<LogisticsInfo> logisticsInfos = userDollService.getLogistics(reqVO.getUdollId());
		respVO.setLogisticsInfos(logisticsInfos);
		return respVO;
	}
	
	@ApiHandle
	@Path("api/userDoll/getEverySize")
	@ApiOperation(value = "查看用户娃娃各状态数量", notes = "查看用户娃娃各状态数量", httpMethod = "POST", response = GetEverySizeRespVO.class)
	@LogHead("查看用户娃娃各状态数量")
	public BaseRespVO getEverySize(GetEverySizeReqVO reqVO) {
		GetEverySizeRespVO respVO = new GetEverySizeRespVO();
		respVO.setStoreSize(userDollService.getUserDollSizeByParams(reqVO.getUid(), 0)); //寄存中
		respVO.setShippedSize(userDollService.getUserDollSizeByParams(reqVO.getUid(), 8)); //已发货
		respVO.setExchangeSize(userDollService.getUserDollSizeByParams(reqVO.getUid(), 3));	//已兑换
		return respVO;
	}

}
