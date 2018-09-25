package com.imlianai.zjdoll.app.modules.core.trade.callback;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.trade.ChargeState;
import com.imlianai.zjdoll.domain.trade.TradeCharge;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.trade.callback.service.PayBackExcuteService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.zjdoll.app.modules.core.trade.util.BillIdUtil;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.CommonUtil;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.MD5Util;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.XmlUtil;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.PayStateEnum.WeiXinPayState;
import com.imlianai.zjdoll.app.modules.core.trade.util.weixin.config.WeiXinPayH5Config;

@Component("paywxjs")
public class WeiXinJSPayBack extends RootCmd {

	private static final Logger logger = Logger.getLogger("payLog");

	@Resource
	private TradeChargeService tradeChargeService;
	@Resource
	private PayBackExcuteService payBackExcuteService;

	@ApiHandle
	public String doBack(HttpServletRequest request,
			HttpServletResponse response) {
		String xmlString = null;
		try {
			xmlString = CommonUtil.getInputStreamParamsStr(request);
			logger.info("【微信支付】获取input参数:" + xmlString);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【微信支付】获取POST,XML失败:xml=" + xmlString);
			createResultXmlStr(response, WeiXinPayState.FAIL, null);
			return null;
		}
		Map<String, String> xmlMap = null;
		try {
			xmlMap = XmlUtil.doXMLParse(xmlString);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【微信支付】解析,XML失败:xml=" + xmlString);
			createResultXmlStr(response, WeiXinPayState.FAIL, null);
			return null;
		}
		String returnCodeStr = xmlMap.get("return_code");
		if (!StringUtils.equals(returnCodeStr, WeiXinPayState.SUCCESS.state)) {
			logger.info("【微信支付】获取返回码不正确:xml=" + xmlString);
			createResultXmlStr(response, WeiXinPayState.FAIL, null);
			return null;
		}
		long tradeNoLong = 0;
		try {
			String out_trade_no=xmlMap.get("out_trade_no");
			tradeNoLong=BillIdUtil.getInnerBillId(out_trade_no);
			tradeChargeService.updatelog(tradeNoLong, xmlString);
		} catch (Exception e) {
		}
		String signwx = xmlMap.get("sign");
		// 不参与签名验证
		xmlMap.remove("sign");
		StringBuffer signBuffer = new StringBuffer(
				CommonUtil.createUrlParamFormat(xmlMap));
		signBuffer.append("&key=");
		signBuffer.append(WeiXinPayH5Config.apiKey);
		String charsetname = CommonUtil.getCharacterEncoding(request, response);
		String md5Sign = StringUtils.upperCase(MD5Util.MD5Encode(
				signBuffer.toString(), charsetname));
		if (!StringUtils.equals(signwx, md5Sign)) {
			logger.info("【微信支付】签名验证失败:signwx=" + signwx + ",md5Sign=" + md5Sign
					+ ",xml=" + xmlString);
			createResultXmlStr(response, WeiXinPayState.FAIL, null);
			return null;
		}
		TradeCharge charge = tradeChargeService.getById(tradeNoLong);
		if (charge == null) {
			logger.info("处理失败,不存在的预处理订单:" + xmlString);
			return createResultXmlStr(response, WeiXinPayState.FAIL, null);
		}
		int res = tradeChargeService
				.updateState(tradeNoLong, ChargeState.PAYED);
		if (res <= 0) {
			logger.info("处理失败,订单已处理或更新订单失败:" + xmlString);
			return createResultXmlStr(response, WeiXinPayState.FAIL, null);
		}
		payBackExcuteService.commonExcute(charge);
		return createResultXmlStr(response, WeiXinPayState.SUCCESS, "OK");
	}

	/**
	 * 生成应答结果
	 * 
	 * @param state
	 *            (必须为SUCCESS或FAIL)
	 * @param msg
	 * @return
	 */
	private String createResultXmlStr(HttpServletResponse response,
			WeiXinPayState state, String msg) {
		StringBuffer xml = new StringBuffer();
		xml.append("<xml>");
		xml.append("<return_code><![CDATA[");
		xml.append(state.state);
		xml.append("]]></return_code>");
		xml.append("<return_msg><![CDATA[");
		xml.append(msg);
		xml.append("]]></return_msg>");
		xml.append("</xml>");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.print(xml.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e);
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		return xml.toString();
	}

	public static void main(String[] args) {
		String xmlString = "<xml><appid><![CDATA[wx72f7d35891f70172]]></appid><attach><![CDATA[00000000-0000-0000-0000-000000000000]]></attach><bank_type><![CDATA[CITIC_CREDIT]]></bank_type><cash_fee><![CDATA[600]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1458868102]]></mch_id><nonce_str><![CDATA[YJee8HvhcZ5tVO38hEi5]]></nonce_str><openid><![CDATA[ob0j41Hd7-kEtjhIzeKg5Of3wdd8]]></openid><out_trade_no><![CDATA[320594]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[63E30426220DB74755D4F88CBDF2FDC9]]></sign><time_end><![CDATA[20170414153524]]></time_end><total_fee>600</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4003562001201704146972596136]]></transaction_id></xml>";
		Map<String, String> xmlMap = null;
		try {
			xmlMap = XmlUtil.doXMLParse(xmlString);
		} catch (Exception e) {
		}
		String signwx = xmlMap.get("sign");
		// 不参与签名验证
		xmlMap.remove("sign");
		StringBuffer signBuffer = new StringBuffer(
				CommonUtil.createUrlParamFormat(xmlMap));
		signBuffer.append("&key=");
		signBuffer.append("wx72f7d35891f70172LianlianXiehou");
		System.out.println("待签名字符串:" + signBuffer.toString());
		String charsetname = "utf-8";
		String md5Sign = StringUtils.upperCase(MD5Util.MD5Encode(
				signBuffer.toString(), charsetname));
		System.out.println(signwx);
		System.out.println(md5Sign);
	}
}
