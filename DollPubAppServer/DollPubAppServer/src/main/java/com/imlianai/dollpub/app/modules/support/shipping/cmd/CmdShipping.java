package com.imlianai.dollpub.app.modules.support.shipping.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.support.shipping.service.ShippingService;
import com.imlianai.dollpub.app.modules.support.shipping.vo.ApplyReqVO;
import com.imlianai.dollpub.app.modules.support.shipping.vo.ApplyShippingRespVO;
import com.imlianai.dollpub.app.modules.support.shipping.vo.CancelBillReqVO;
import com.imlianai.dollpub.app.modules.support.shipping.vo.GetBillsReqVO;
import com.imlianai.dollpub.app.modules.support.shipping.vo.GetBillsRespVO;
import com.imlianai.dollpub.app.modules.support.shipping.vo.GetPostageRespVO;
import com.imlianai.dollpub.app.modules.support.shipping.vo.GetShippingListRespVO;
import com.imlianai.dollpub.app.modules.support.shipping.vo.QueryBillReqVO;
import com.imlianai.dollpub.app.modules.support.shipping.vo.QueryBillRespVO;
import com.imlianai.dollpub.domain.doll.BaseDollInfo;
import com.imlianai.dollpub.domain.doll.DollOrderRecord;
import com.imlianai.dollpub.domain.shipping.DollOrderRecordBill;
import com.imlianai.dollpub.domain.trade.TradeAccount;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.utils.StringUtil;

@Api("发货")
@LogHead("发货模块")
@Component("shipping")
public class CmdShipping extends RootCmd{
	
	@Resource
    ShippingService shippingService;
	@Resource
	TradeService tradeService;
	
	@ApiHandle
	@Path("api/shipping/getDollList")
	@ApiOperation(value = "发货的娃娃列表", notes = "发货的娃娃列表", httpMethod = "POST", response = GetShippingListRespVO.class)
	@LogHead("发货的娃娃列表")
	public BaseRespVO getDollList(BaseReqVO reqVO) {
		GetShippingListRespVO respVO = new GetShippingListRespVO();
		List<BaseDollInfo> dollList = shippingService.getShippingList(reqVO.getUid());
		respVO.setDollList(dollList);
		return respVO;
	}
	
	@ApiHandle
	@Path("api/shipping/phonebind")
	@ApiOperation(value = "申请发货验证手机是否绑定", notes = "申请发货验证手机是否绑定", httpMethod = "POST", response = BaseReqVO.class)
	@LogHead("申请发货验证手机是否绑定")
	public BaseRespVO phonebind(BaseReqVO reqVO) {
		return shippingService.phonebind(reqVO.getUid());
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/shipping/applyShipping")
	@ApiOperation(value = "申请发货", notes = "申请发货", httpMethod = "POST", response = ApplyShippingRespVO.class)
	@LogHead("申请发货")
	public BaseRespVO applyShipping(ApplyReqVO reqVO) {
		return shippingService.applyShipping(reqVO.getDollList(), reqVO.getUid(), reqVO.getAddressId());
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/shipping/applyShippingBill")
	@ApiOperation(value = "申请发货", notes = "申请发货", httpMethod = "POST", response = ApplyShippingRespVO.class)
	@LogHead("申请发货")
	public BaseRespVO applyShippingBill(ApplyReqVO reqVO) {
		return shippingService.applyShippingBill(reqVO.getDollList(), reqVO.getUid(), reqVO.getAddressId(),"");
	}
	@ApiHandle
	@LoginCheck
	@Path("api/shipping/getPostage")
	@ApiOperation(value = "获取邮费", notes = "获取邮费", httpMethod = "POST", response = GetPostageRespVO.class)
	@LogHead("获取邮费")
	public BaseRespVO getPostage(ApplyReqVO reqVO) {
		GetPostageRespVO respVO = new GetPostageRespVO();
		int postage = shippingService.getPostage(reqVO.getDollList(), reqVO.getUid());
		int postageRMB = shippingService.getPostageRmb(reqVO.getDollList(), reqVO.getUid());
		respVO.setPostage(postage); // 邮费
		respVO.setPostageRMB(postageRMB);
		TradeAccount tradeAccount = tradeService.getAccount(reqVO.getUid());
		respVO.setCoin(tradeAccount.getCoin()); // 余额
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/shipping/getBills")
	@ApiOperation(value = "【1.3.0】获取历史订单", notes = "获取历史订单", httpMethod = "POST", response = GetBillsRespVO.class)
	@LogHead("获取历史订单")
	public BaseRespVO getBills(GetBillsReqVO reqVO) {
		GetBillsRespVO respVO = new GetBillsRespVO();
		List<DollOrderRecordBill> list=shippingService.getDollOrderRecordBill(reqVO.getUid(), reqVO.getPage(), 30);
		if (!StringUtil.isNullOrEmpty(list)) {
			respVO.setList(list);
		}
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/shipping/queryBill")
	@ApiOperation(value = "【1.3.0】获取某一订单信息", notes = "获取某一订单信息", httpMethod = "POST", response = QueryBillRespVO.class)
	@LogHead("获取某一订单信息")
	public BaseRespVO queryBill(QueryBillReqVO reqVO) {
		QueryBillRespVO respVO = new QueryBillRespVO();
		DollOrderRecord order=shippingService.getDollOrderRecord(reqVO.getOrderNum());
		if (order!=null) {
			respVO.setOrder(order);
		}else{
			return new BaseRespVO(0, false, "不存在该订单");
		}
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/shipping/cancelBill")
	@ApiOperation(value = "【1.3.0】取消订单", notes = "取消订单", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("取消订单")
	public BaseRespVO cancelBill(CancelBillReqVO reqVO) {
		BaseRespVO respVO=shippingService.cancelShippingBill(reqVO.getUid(), reqVO.getOrderNum());
		return respVO;
	}
}
