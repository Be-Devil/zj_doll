package com.imlianai.dollpub.app.modules.core.trade.util.ali;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.app.modules.core.trade.util.ali.config.AliPayConfig;
import com.imlianai.dollpub.domain.trade.ChargeCatalog;
import com.imlianai.rpc.support.utils.StringUtil;

public class AliPay {

	public static final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN6zNNgyggOINymowl53A9N2acDE6A49Qi6xKZuhwx7d+k+TAFEcmvx5ABYHvmbCp/EWyjmOdJM1nrVV1D8v1bDeez80Q8zagE76eQiFTrCa8UWMMxxkzoN9giGtYnP3PQG3Mh3CfUseJvNun6R/VdTNYsB9t6eGDPY5oMtm/ZcVAgMBAAECgYEAq0NmAF3XXqACzCbcU4lzh8wox/al/01c+EuGlAly5OoQ9eE2/SnihwcG6epKovxP6zh7jIfhXtR9mTbIY0+GJW8JHYewLNVlVcdCTVBoRwdICESy5xaApLEvr8w+rHcOq3L3ro3RbMAaierDwDLlMMDCB0qoJKy77PTLuZ2OWAECQQD9mXUWn+5mCNl6XD5bXPJoPBSIq1Eo51LcFwCRHAxy4doPszqaPN9xc0WRRVr8svGA1QNPw4way+FjF1tGizHRAkEA4M7e/4ArJTevych9WZ8Nw3RCV0NOB9ni2JQVlz56GURi+RMdpMHUbHYwMc3ME8HAfD60LOZgOtB5M98vjSU+BQJAVdC1B4ftI0gBV9+ZFk+Yk98oz6qxVxcYOfPqtC/6onF82e07em/46KoqM2N1y6c+A+uRKE+yvGqbhW2k7HUz0QJABc+TGAuaEeY0lwSlqNFhq7GvnU3SEIXjRTHetQAaa9jGriV+WKnh8cPETyVApyoU7aE5SwfRaPPGjj6wnfdeeQJBANGSg+jfaaa6yRSwpCSbnSgJL0XmAi5Xy7fau4RovDGFMCRegNsXLHoDWh73k7f5j8L2xJ/pCaH9yfVD8auOjQQ=";

	public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDeszTYMoIDiDcpqMJedwPTdmnAxOgOPUIusSmbocMe3fpPkwBRHJr8eQAWB75mwqfxFso5jnSTNZ61VdQ/L9Ww3ns/NEPM2oBO+nkIhU6wmvFFjDMcZM6DfYIhrWJz9z0BtzIdwn1LHibzbp+kf1XUzWLAfbenhgz2OaDLZv2XFQIDAQAB";

	public static final String publicKeyAli = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	public static final String ALI_PARTNER_NEW = "2088211432016080";
	public static final String ALI_SELLER_NEW = "vincent@imlianai.com";

	// 检查商户文件的配置参数
	public static boolean checkInfo() {
		String partner = ALI_PARTNER_NEW;
		String seller = ALI_SELLER_NEW;
		// 如果合作商户ID为空或者账号ID为空返回false
		if (StringUtil.isNullOrEmpty(partner) || StringUtil.isNullOrEmpty(seller)) {
			return false;
		}
		return true;
	}

	/**
	 * 数据拼接
	 * 
	 * @param item
	 * @param orderId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getNewOrderInfo(ChargeCatalog item,
			String orderId) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		String totalFee = item.getPrice() + ".00";
		String notfyUrl = URLEncoder.encode(
				AliPayConfig.notifyUrl, "UTF-8");
		
		if (item.getUnit()==0) {//元
		}else if (item.getUnit()==1) {//分
			totalFee = String.valueOf(item.getPrice()/100);
		}
		if (AppUtils.isTestEnv()) {
			notfyUrl= URLEncoder.encode(
					AliPayConfig.notifyUrlTest, "UTF-8");
			totalFee = "0.01";
		}
		if (item.getUnit()==0) {//元
			totalFee = String.valueOf(item.getPrice());
		}else if (item.getUnit()==1) {//分
			java.text.DecimalFormat df = new java.text.DecimalFormat(
					"#.00");
			String amtS = df.format(item.getPrice()/100);
			totalFee = amtS;
		}
		sb.append("partner=\"");
		sb.append("2088211432016080");
		sb.append("\"&out_trade_no=\"");
		sb.append(orderId);
		sb.append("\"&subject=\"");
		sb.append(item.getName());
		sb.append("\"&body=\"");
		sb.append(item.getName());
		sb.append("\"&total_fee=\"");
		sb.append(totalFee);
		sb.append("\"&notify_url=\"");
		sb.append(notfyUrl);
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append("vincent@imlianai.com");
		sb.append("\"");
		return new String(sb);
	}

}
