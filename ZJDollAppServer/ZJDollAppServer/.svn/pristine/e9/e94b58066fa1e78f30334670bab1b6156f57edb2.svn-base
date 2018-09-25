package com.imlianai.zjdoll.app.modules.core.doll.cmd;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.DollBus.DollBusCompany;
import com.imlianai.rpc.support.common.json.JsonUtils;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollListService;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.core.doll.service.DollService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.support.banner.service.BannerService;

@Component("zengjing")
public class CmdDollZengjing extends RootCmd {

	@Resource
	DollListService dollListService;
	@Resource
	DollRecordService dollRecordService;
	@Resource
	DollService dollService;
	@Resource
	TradeService tradeService;
	@Resource
	BannerService bannerService;

	@Override
	public String doCommand(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap = JsonUtils.getMapFromHttpPortNoEncrypt(request);
		logger.info("paraMap:"+JSON.toJSONString(paraMap));
		String optInString=paraMap.get("optId").toString();
		logger.info("log_id:"+optInString);
		long logId=Long.parseLong(optInString);
		String operateResultStr=paraMap.get("result").toString();
		int operateResult=Integer.parseInt(operateResultStr);
		logger.info("operate_result:"+paraMap.get("result"));
		logger.info("busId:"+paraMap.get("busId"));
		logger.info("uid:"+paraMap.get("uid"));
		logger.info("remark:"+paraMap.get("remark"));
		dollService.handleResult(logId, operateResult==1?1:0, DollBusCompany.ZENGJING.type);
		return responseJson(response, "ok", "响应请求");
	}
}
