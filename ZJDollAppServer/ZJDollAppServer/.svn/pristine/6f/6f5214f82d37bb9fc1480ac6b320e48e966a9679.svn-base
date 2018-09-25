package com.imlianai.zjdoll.app.modules.core.doll.cmd;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.DollBus.DollBusCompany;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollListService;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.core.doll.service.DollService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.support.banner.service.BannerService;

@Component("qiyiguo")
public class CmdDollQiyiguo extends RootCmd {

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
		logger.info(request.getQueryString());
		logger.info("log_id:"+request.getParameter("log_id"));
		String logIdStr=request.getParameter("log_id");
		long logId=Long.parseLong(logIdStr);
		String operateResultStr=request.getParameter("operate_result");
		int operateResult=Integer.parseInt(operateResultStr);
		logger.info("operate_result:"+request.getParameter("operate_result"));
		logger.info("ts:"+request.getParameter("ts"));
		logger.info("sign:"+request.getParameter("sign"));
		dollService.handleResult(logId, operateResult==1?1:0, DollBusCompany.QIYIGUO.type);
		if (!AppUtils.isTestEnv()) {
			HttpUtil.Get("http://t.xiehou360.com/DollAppServer/api/qiyiguo/back?"+request.getQueryString());
		}
		return responseJson(response, "ok", "响应请求");
	}

	public static void main(String[] args) {
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("live", 1);
		HttpUtil.Post(
				"http://122.11.48.251:6639/DollAppServer/api/qiyiguo/back",
				JSON.toJSONString(postMap));
	}
}
