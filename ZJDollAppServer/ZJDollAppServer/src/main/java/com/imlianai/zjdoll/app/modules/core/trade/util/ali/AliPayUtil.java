package com.imlianai.zjdoll.app.modules.core.trade.util.ali;

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
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog;
import com.imlianai.zjdoll.app.modules.core.trade.util.ali.config.AliPayConfig;
import com.imlianai.zjdoll.app.modules.core.trade.util.ali.config.AliPaySaisiConfig;

public class AliPayUtil {

	private static final BaseLogger logger = BaseLogger
			.getLogger(AliPayUtil.class);


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
	
	public static String getAlipayOrder(String appid, String privateKey,String publicKey,
			ChargeCatalog c, String orderId) {
		logger.info("getAlipayOrder appid:"+appid+" privateKey:"+privateKey+"publicKey:"+publicKey+" orderId:"+orderId);
		AlipayClient client = new DefaultAlipayClient(AliPayConfig.reqUrl,
				appid, privateKey, AliPayConfig.format, AliPayConfig.charSet,
				publicKey, AliPayConfig.signType);
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
