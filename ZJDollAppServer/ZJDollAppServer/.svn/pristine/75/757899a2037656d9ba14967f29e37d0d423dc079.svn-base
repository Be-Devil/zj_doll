package com.imlianai.zjdoll.app.modules.core.trade.callback;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.imlianai.zjdoll.domain.log.LogPage;
import com.imlianai.zjdoll.domain.trade.ChargeState;
import com.imlianai.zjdoll.domain.trade.TradeCharge;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.trade.callback.service.PayBackExcuteService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.zjdoll.app.modules.core.trade.util.BillIdUtil;
import com.imlianai.zjdoll.app.modules.core.trade.util.ali.config.AliPaySaisiConfig;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.CommonUtil;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.PayStateEnum.AliPayState;
import com.imlianai.zjdoll.app.modules.publics.log.service.LogService;

@Component("payalih5")
public class AlipayH5Back extends RootCmd {

	private static final Logger logger = Logger.getLogger("payLog");

	@Resource
	private PayBackExcuteService payBackExcuteService;
	@Resource
	private TradeChargeService tradeChargeService;
	@Resource
	private LogService logService;

	@ApiHandle
	public String doBack(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String charset = CommonUtil.getCharacterEncoding(request, response);
		Map<String, String> paramsMap = AppUtils.getParam(request, charset);
		logger.info("【支付宝】paramsMap:"+JSON.toJSONString(paramsMap));
		logService.add(LogPage.PAY_ALI_BACK, paramsMap, this.getClass());
		boolean signVerified = true;
		try {
			signVerified = AlipaySignature.rsaCheckV1(paramsMap, AliPaySaisiConfig.rsa2PublicAliPay, AliPaySaisiConfig.charSet, "RSA2");
			
			//boolean isSign = RSA.verify(preSignStr, sign,AliPay.publicKeyAli, "utf-8");
			if (!signVerified) {
				logger.info("【支付宝】签名验证失败:sign=" + ",params=" + paramsMap);
				return createResultStr(response, AliPayState.FAIL);
			}
		} catch (AlipayApiException e) {
			logger.info(e);
			logger.error(e, e);
			logger.info("【支付宝】签名验证失败:sign=" + ",params=" + paramsMap);
			return createResultStr(response, AliPayState.FAIL);
		}
		String tradeNo = paramsMap.get("out_trade_no");
		String tradeStatus = request.getParameter("trade_status");
		tradeStatus = new String(tradeStatus.getBytes(), "UTF-8");
		if (!tradeStatus.equals("TRADE_SUCCESS")&&!tradeStatus.equals("TRADE_FINISHED")) {
			logger.info("交易失败,状态异常tradeNo:" + tradeNo+" tradeStatus:"+tradeStatus);
			return createResultStr(response, AliPayState.SUCCESS);
		}
		long tradeNoLong = BillIdUtil.getInnerBillId(tradeNo);
		tradeChargeService.updatelog(tradeNoLong, JSON.toJSONString(paramsMap));
		TradeCharge charge = tradeChargeService.getById(tradeNoLong);
		if (charge == null) {
			logger.info("处理失败,不存在的预处理订单:" + paramsMap);
			return createResultStr(response, AliPayState.FAIL);
		}
		int resx = tradeChargeService.updateState(tradeNoLong,
				ChargeState.PAYED);
		if (resx <= 0) {
			logger.info("处理失败,订单已处理或更新订单失败:" + paramsMap + ",resx=" + resx);
			return createResultStr(response, AliPayState.FAIL);
		}
		payBackExcuteService.commonExcute(charge);
		return createResultStr(response, AliPayState.SUCCESS);
	}
	/**
	 * 返回相应结果
	 * 
	 * @param response
	 * @param state
	 * @param msg
	 * @return
	 */
	private String createResultStr(HttpServletResponse response,
			AliPayState state) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.print(state.state);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e);
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		return state.state;
	}
}
