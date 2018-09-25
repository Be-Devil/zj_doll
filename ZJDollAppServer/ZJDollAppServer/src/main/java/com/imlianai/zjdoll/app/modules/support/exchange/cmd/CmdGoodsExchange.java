package com.imlianai.zjdoll.app.modules.support.exchange.cmd;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.support.exchange.service.DollExchangeSevice;
import com.imlianai.zjdoll.app.modules.support.exchange.vo.ExchangeGetListRespVO;
import com.imlianai.zjdoll.app.modules.support.exchange.vo.ExchangeRecordsRespVO;
import com.imlianai.zjdoll.app.modules.support.exchange.vo.GetRecentExcListRespVO;
import com.imlianai.zjdoll.app.modules.support.exchange.vo.GetRecycleListRespVO;
import com.imlianai.zjdoll.app.modules.support.exchange.vo.GoodsExchangeReqVO;
import com.imlianai.zjdoll.app.modules.support.exchange.vo.RecycleListReqVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("商品兑换")
@LogHead("商品兑换")
@Component("goodsExchange")
public class CmdGoodsExchange extends RootCmd{
	
	@Resource
	DollExchangeSevice dollExchangeSevice;

	@ApiHandle
	@Path("api/goodsExchange/getList")
	@ApiOperation(value = "【1.1.0】获取可钻石兑换的娃娃列表", notes = "获取可钻石兑换的娃娃列表", httpMethod = "POST", response = ExchangeGetListRespVO.class)
	@LogHead("获取可钻石兑换的娃娃列表")
	public BaseRespVO getList(BaseReqVO reqVO) {
		return dollExchangeSevice.getList(reqVO.getUid());
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/goodsExchange/exchange")
	@ApiOperation(value = "【1.1.0】钻石兑换成娃娃", notes = "钻石兑换成娃娃", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("钻石兑换成娃娃")
	public BaseRespVO exchange(GoodsExchangeReqVO reqVO) {
		return dollExchangeSevice.jewelExchangeToDoll(reqVO.getUid(), reqVO.getDollId());
	}
	
	@ApiHandle
	@Path("api/goodsExchange/getRecycleList")
	@ApiOperation(value = "【1.1.0】获取可回收成钻石的用户娃娃列表", notes = "获取可回收成钻石的用户娃娃列表", httpMethod = "POST", response = GetRecycleListRespVO.class)
	@LogHead("获取可回收成钻石的用户娃娃列表")
	public BaseRespVO getRecycleList(BaseReqVO reqVO) {
		return dollExchangeSevice.getRecycleList(reqVO.getUid());
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/goodsExchange/recycleList")
	@ApiOperation(value = "【1.1.0】回收娃娃列表", notes = "回收娃娃列表", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("回收娃娃列表")
	public BaseRespVO recycleList(RecycleListReqVO reqVO) {
		return dollExchangeSevice.recycleList(reqVO.getUid(), reqVO.getDollList());
	}
	
	@ApiHandle
	@Path("api/goodsExchange/getRecentExcList")
	@ApiOperation(value = "【1.3.0】获取最近兑换的娃娃列表", notes = "获取最近兑换的娃娃列表", httpMethod = "POST", response = GetRecentExcListRespVO.class)
	@LogHead("获取最近兑换的娃娃列表")
	public BaseRespVO getRecentExcList(BaseReqVO reqVO) {
		GetRecentExcListRespVO respVO = new GetRecentExcListRespVO();
		respVO.setDollInfos(dollExchangeSevice.getRecentExcList());
		return respVO;
	}
	
	@ApiHandle
	@Path("api/goodsExchange/getExcRecords")
	@ApiOperation(value = "【1.3.0】兑换记录", notes = "兑换记录", httpMethod = "POST", response = ExchangeRecordsRespVO.class)
	@LogHead("兑换记录")
	public BaseRespVO getExcRecords(BaseReqVO reqVO) {
		ExchangeRecordsRespVO respVO = new ExchangeRecordsRespVO();
		respVO.setRecords(dollExchangeSevice.getExcRecords(reqVO.getUid()));
		return respVO;
	}
}
