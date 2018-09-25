package com.imlianai.zjdoll.app.modules.support.xxrecharge.cmd;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.json.JsonUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.HttpConnection;
import com.imlianai.zjdoll.app.modules.support.xxrecharge.service.XxingRechargeService;

@Component("xinxingRecharge")
public class CmdXxingRecharge extends RootCmd{
	
	@Resource
	XxingRechargeService xxingRechargeService;

	@Override
	public String doCommand(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap = JsonUtils.getMapFromHttpPortNoEncrypt(request);
		logger.info("xinxingRecharge.callback-paraMap:"+JSON.toJSONString(paraMap));
		if(!StringUtil.isNullOrEmpty(paraMap)) {
			String orderNo = (String) paraMap.get("outTradeNo");
			String handleFlag = (String) paraMap.get("handleFlag");
			if(!StringUtil.isNullOrEmpty(orderNo)) {
				if(orderNo.startsWith("test")) {
					if(!StringUtil.isNullOrEmpty(handleFlag)) {
						xxingRechargeService.saveCallBackInfo(orderNo, JSON.toJSONString(paraMap)); // 保存回调信息
						int status = (int) paraMap.get("status");
						String failReason = (String) paraMap.get("failReason");
						xxingRechargeService.updateTradeChargeStatus(orderNo, status, failReason);
					} else {
						paraMap.put("handleFlag", "1");
						String paraStr = JSON.toJSONString(paraMap);
						HttpConnection.doPost("http://t.xiehou360.com/DollAppServer/api/xinxingRecharge/callback", paraStr);
					}
				} else {
					xxingRechargeService.saveCallBackInfo(orderNo, JSON.toJSONString(paraMap)); // 保存回调信息
					int status = (int) paraMap.get("status");
					String failReason = (String) paraMap.get("failReason");
					xxingRechargeService.updateTradeChargeStatus(orderNo, status, failReason);
				}
			}
		}
		return responseJson(response, "OK", "响应请求");
	}
}
