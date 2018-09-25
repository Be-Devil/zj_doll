package com.imlianai.dollpub.app.modules.core.trade.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.trade.record.TradeRecordService;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.core.trade.vo.TradeRecordListReqVO;
import com.imlianai.dollpub.app.modules.core.trade.vo.TradeRecordListRespVO;
import com.imlianai.dollpub.app.modules.publics.log.service.LogService;
import com.imlianai.dollpub.domain.trade.TradeAccount;
import com.imlianai.dollpub.domain.trade.TradeRecord;
import com.imlianai.dollpub.domain.trade.TradeRecordRes;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.utils.StringUtil;

/**
 * 
 * @author tensloveq
 */
@Component("trade")
@Api("交易相关")
public class CmdHandleTrade extends RootCmd {

	@Resource
	private LogService logService;

	@Resource
	private TradeRecordService tradeRecordService;

	@Resource
	private TradeChargeService tradeChargeService;

	@Resource
	private TradeService tradeService;

	@ApiHandle
	@Path("api/trade/list")
	@ApiOperation(value = "【1.0.0】获取游戏币账单接口", notes = "", httpMethod = "POST", response = TradeRecordListRespVO.class)
	public BaseRespVO list(TradeRecordListReqVO vo) {
		TradeRecordListRespVO respVo = new TradeRecordListRespVO();
		if (vo.getPage() == 1) {
			TradeAccount account = tradeService.getAccount(vo.getUid());
			if (account != null) {
				respVo.setCoin(account.getCoin());
			}
		}
		List<TradeRecordRes> list = tradeRecordService.getRecords(vo.getUid(), vo.getPage());
		if (!StringUtil.isNullOrEmpty(list)) {
			respVo.setList(list);
		}
		return respVo;
	}
}
