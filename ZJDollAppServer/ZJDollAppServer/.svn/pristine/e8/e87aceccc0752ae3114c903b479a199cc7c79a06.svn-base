package com.imlianai.zjdoll.app.modules.core.trade.cmd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.zjdoll.app.modules.core.trade.util.ali.domain.AlipayH5Body;

/**
 * 支付宝h5订单内容
 * 
 * @author tensloveq
 * 
 */
@Component("alipayH5HtmlBack")
public class AlipayH5HtmlBack extends RootCmd{

	private static final BaseLogger logger = BaseLogger.getLogger(AlipayH5HtmlBack.class);

	@Resource
	private TradeChargeService tradeChargeService;

	@Override
	public String doBack(HttpServletRequest request,
			HttpServletResponse response)  {
		String billIdStr = request.getParameter("billId");// 游戏内部订单id
		if (billIdStr!=null) {
			long billId=Long.parseLong(billIdStr);
			AlipayH5Body alipayH5Body=tradeChargeService.getAlipayH5Body(billId);
			if (alipayH5Body!=null) {
				handleHtml(response, alipayH5Body.getBody());
			}else{
				handleHtml(response, "订单信息不存在");
			}
		}else {
			handleHtml(response, "订单号不存在");
		}
		return "";
	}
	
	public void handleHtml(HttpServletResponse response,String body){
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			pw.print(body);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			
		} finally {
			pw.flush();
			pw.close();
		}
	}
}
