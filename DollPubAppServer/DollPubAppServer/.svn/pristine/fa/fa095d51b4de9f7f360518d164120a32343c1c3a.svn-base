package com.imlianai.dollpub.app.modules.core.trade.util.ali;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.app.modules.core.trade.util.ali.config.AliPayConfig;
import com.imlianai.dollpub.app.modules.core.trade.util.ali.config.AliPaySaisiConfig;
import com.imlianai.dollpub.domain.trade.ChargeCatalog;
import com.imlianai.rpc.support.common.BaseLogger;

public class AliPayUtil {

	private static final BaseLogger logger = BaseLogger
			.getLogger(AliPayUtil.class);

	public static String createOrderNew(ChargeCatalog c, long orderId) {
		AlipayClient alipayClient = new DefaultAlipayClient(
				AliPayConfig.reqUrl, AliPayConfig.appId,
				AliPayConfig.rsaPrivate, "json", AliPayConfig.charSet,
				AliPayConfig.rsaPublicAliPay, "RSA2");
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(c.getName());
		model.setSubject(c.getName());
		model.setOutTradeNo(orderId + "");
		model.setTimeoutExpress("30m");
		model.setTotalAmount(c.getPrice() + "");
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl(AliPayConfig.notifyUrl);
		if (AppUtils.isTestEnv()) {
			request.setNotifyUrl(AliPayConfig.notifyUrlTest);
			model.setTotalAmount("0.01");
		}
		try {
			AlipayTradeAppPayResponse response = alipayClient
					.sdkExecute(request);
			logger.info("response=>" + response.getBody());
			return response.getBody();
		} catch (AlipayApiException e) {
			logger.info(e);
			logger.error(e, e);
			return null;
		}
	}

	/**
	 * 获取支付宝回到数据
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getParamUseCharset(HttpServletRequest request,
			String paramName) throws UnsupportedEncodingException {
		return new String(request.getParameter(paramName)
				.getBytes("ISO-8859-1"), "UTF-8");
	}

	/**
	 * 获取来源认证
	 * 
	 * @param notifyId
	 * @return
	 */
	public static String getVerifyAliPayUrl(String notifyId) {
		return new StringBuffer(
				"https://mapi.alipay.com/gateway.do?service=notify_verify&partner=")
				.append(AliPayConfig.appId).append("&notify_id=")
				.append(notifyId).toString();
	}
	
	public static String getAlipayOrder(String appid, String privateKey,String publicKey,
			ChargeCatalog c, String orderId) {
		AlipayClient client = new DefaultAlipayClient(AliPaySaisiConfig.reqUrl,
				appid, privateKey, AliPaySaisiConfig.format, AliPaySaisiConfig.charSet,
				publicKey, AliPaySaisiConfig.signType);
		AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

		// 封装请求支付信息
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(orderId+"");
		model.setSubject(c.getName());
		if (AppUtils.isTestEnv()) {//如果是测试环境则订单金额为1分钱
			model.setTotalAmount(0.01 + "");
		}else{
			model.setTotalAmount(c.getPrice() + ".00");
		}
		String totalFee= c.getPrice() + ".00";
		if (c.getUnit()==1) {//分
			java.text.DecimalFormat df = new java.text.DecimalFormat(
					"#.00");
			String amtS = df.format(c.getPrice()/100);
			totalFee = 0.01+"";
		}
		model.setTotalAmount(totalFee);
		model.setProductCode("QUICK_WAP_PAY");
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(AliPaySaisiConfig.notifyUrl);
		// 设置同步地址
		alipay_request.setReturnUrl(AliPaySaisiConfig.returnUrl);
		logger.info("getAlipayOrder alipay_request:"+JSON.toJSONString(alipay_request));
		// form表单生产
		String form = "";
		try {
			// 调用SDK生成表单
			return form = client.pageExecute(alipay_request).getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return null;
	}
}
