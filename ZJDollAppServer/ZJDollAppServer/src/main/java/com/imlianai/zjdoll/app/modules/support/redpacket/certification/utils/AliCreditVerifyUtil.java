package com.imlianai.zjdoll.app.modules.support.redpacket.certification.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.dubbo.common.URL;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCustomerCertificationCertifyRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCustomerCertificationInitializeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCustomerCertificationQueryRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCustomerCertificationInitializeResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCustomerCertificationQueryResponse;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.Rsa;

public class AliCreditVerifyUtil {
	private static final Logger logger = Logger
			.getLogger(AliCreditVerifyUtil.class);
	private static final String gatewayUrl = "https://zmopenapi.zmxy.com.cn/openapi.do";

	private static final String creditVerifyAppid = "1002300";

	private static final String appPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZIRdrq+QnC0uN4GD5e3I+i9OW202lTTAOkYxugiu87/2OO4xF8EWonwYpMIJiPXTGbIRhed2pNabRuatS05FtewoVgT1HtfPVyxL9R/dNeJsQNofoJ/FW8iA8dmIsfMWD7dzscDZu820D0BE/rjmwITGYjS8a6MrnjqhLTvGARQIDAQAB";

	private static final String appPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJkhF2ur5CcLS43gYPl7cj6L05bbTaVNMA6RjG6CK7zv/Y47jEXwRaifBikwgmI9dMZshGF53ak1ptG5q1LTkW17ChWBPUe189XLEv1H9014mxA2h+gn8VbyIDx2Yix8xYPt3OxwNm7zbQPQET+uObAhMZiNLxroyueOqEtO8YBFAgMBAAECgYBJcIRyACtYymL+9TitDhZZhugAdscDW0SMCCQIr+dYIuVA/LAmrrhJyZLD2F7/zKefrlXDBhZX28JY3SocfBlHYWInkVGAJMcgwmAUeWpGxXmid2CK5EBMb0QhHyJesQ+DPH6H4e5lYf7gQc5GOwkGFsNLe9B4VB7WIIU1wyyPFQJBAM5iUU0fxYx5ZFLvGNefg83XHwO5bG2uLnAIBBfCbA0SIYIh59LVy93s+yYlQTF/Q8Y1RanuwKf8ahRevl2GQq8CQQC98UQGTNFmmEgQp0LuWoGxeTKVxCEJYS7AK3zZYxDnWLa+gAnMm2hVCSqMf5glHL0KL5PAkB0Y1JjEoWolLTlLAkEAs28Rhn9QDvDfw6czLwz12DnnMiK9XaKppyeg/1M/BCI2VeDKoCjXZayLMsADLVibgTGBztvQTn0LcYhp9AAt6wJAWyoUay2th59pX0zYTAx2mRbBl65V6OmTBbsZpkDtGQy0UUSk72Zbsgy/NkX7YTyk4uGo+dBxlrxbQmzyxDsrdwJAOtYMks/c0NZ7mLwvsaUPCSy6SUBcckWXsKjilvRP/wPejMBYKuamMFdP4ze2eO0iBu5GIZzs+btcUAgt/7evSw==";

	private static final String aliPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCf/C4SGeICWTwpJPLMGLbqLXRVaK598K29YC6ijZjYMb3KRARzhs8L6fI+jF5BsrqGQ0Ol7kXOhCr32AYBb0fXRtIr5Ej6w/BfcNHaBG8MaaZblriTXPZKwd2bsjtNir7cehc1J37eegqyHwXK3O/i+2chjbATEio4TzO/4VXALwIDAQAB";

	private static final String faceCallback="doll_real_name://platformapi/startapp?type=1";
	/**
	 * 创建实名信息查询订单
	 * 
	 * @param billId
	 * @param cert_no
	 * @param name
	 * @return
	 */
	private static String getCreditVerifyOrderParam(String billId,
			String cert_no, String name) {
		StringBuilder sb = new StringBuilder();

		Map<String, String> bizMap = new HashMap<String, String>();
		bizMap.put("product_code", "w1010100000000002859");
		bizMap.put("transaction_id", billId);
		bizMap.put("cert_no", cert_no);
		bizMap.put("name", name);
		bizMap.put("cert_type", "IDENTITY_CARD");

		sb.append("app_id=");
		sb.append(URL.encode(creditVerifyAppid));
		sb.append("&charset=" + URL.encode("UTF-8"));
		sb.append("&method=" + URL.encode("zhima.credit.antifraud.verify"));
		sb.append("&version=" + URL.encode("1.0"));
		String paramsString = getParams(bizMap);
		String encryptString = Rsa.encryptIgnoreLength(paramsString,
				aliPublicKey);
		sb.append("&params=");
		// sb.append(URL.encode(encryptString));// 参数正文
		sb.append(URL.encode(encryptString));// 参数正文
		String sign = Rsa.sign(paramsString, appPrivateKey);
		sb.append("&sign=");
		sb.append(URL.encode(sign));
		// sb.append(sign);
		return new String(sb);
	}

	/**
	 * 根据芝麻订单号获取接口url
	 * @param billId
	 * @return
	 */
	private static String getFaceVerifyOrderParam(String billId) {
		ZhimaCustomerCertificationCertifyRequest request = new ZhimaCustomerCertificationCertifyRequest();
		request.setChannel("apppc");
		request.setPlatform("zmop");
		request.setBizNo(billId);// 必要参数
		// 设置回调地址,必填. 如果需要直接在支付宝APP里面打开回调地址使用alipay协议
		// alipay://www.taobao.com 或者 alipays://www.taobao.com,分别对应http和https请求
		request.setReturnUrl(faceCallback);// 必要参数
		DefaultZhimaClient client = new DefaultZhimaClient(
				"https://zmopenapi.zmxy.com.cn/openapi.do", creditVerifyAppid,
				appPrivateKey, aliPublicKey);
		try {
			String url = client.generatePageRedirectInvokeUrl(request);
			logger.info("getFaceVerifyOrderParam generateCertifyUrl url:" + url);
			return url;
		} catch (ZhimaApiException e) {
			PrintException.printException(logger, e);
		}
		return null;
	}

	/**
	 * 获取芝麻认证的订单号
	 * 
	 * @param billId
	 * @return
	 */
	private static VerificationResult getZhimaBillId(String billId,
			String cert_no, String name) {
		ZhimaCustomerCertificationInitializeRequest request = new ZhimaCustomerCertificationInitializeRequest();
		request.setPlatform("zmop");
		request.setTransactionId(billId);// 必要参数
		request.setProductCode("w1010100000000002978");// 必要参数
		request.setBizCode("FACE");// 必要参数
		request.setIdentityParam("{\"identity_type\":\"CERT_INFO\",\"cert_type\":\"IDENTITY_CARD\",\"cert_name\":\""
				+ name + "\",\"cert_no\":\"" + cert_no + "\"}");// 必要参数
		request.setExtBizParam("{}");// 必要参数
		DefaultZhimaClient client = new DefaultZhimaClient(
				"https://zmopenapi.zmxy.com.cn/openapi.do", creditVerifyAppid,
				appPrivateKey, aliPublicKey);
		try {
			ZhimaCustomerCertificationInitializeResponse response = (ZhimaCustomerCertificationInitializeResponse) client
					.execute(request);
			if (response.isSuccess()) {
				return new VerificationResult(true, "", response.getBizNo());
			} else {
				return new VerificationResult(false, response.getErrorMessage());
			}
		} catch (ZhimaApiException e) {
			PrintException.printException(logger, e);
		}
		return new VerificationResult(false, "未知错误");
	}

	public static String getParams(Map<String, String> receiveObj) {
		List<String> keys = new ArrayList<String>(receiveObj.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = receiveObj.get(key) + "";
			prestr = prestr + key + "=" + URL.encode(value) + "&";
		}
		if (!StringUtils.isBlank(prestr)) {
			prestr = prestr.substring(0, prestr.length() - 1);
		}
		return prestr;
	}

	public static void main(String[] args) {
		String name="曾琨鹏";
		String cert_no="440602199201301511";
		String udrlString="http://t.xiehou360.com/LiveWebServer/call.do?cmd=score.handleFaceCertificationWeb&certificationId="+cert_no+"&name="+name+"&uid=4483823";
		//System.out.println(HttpUtil.Get(udrlString).getHtml());
		/*
		VerificationResult  verificationResult =createFaceCreditVerifyOrder(System.currentTimeMillis()+"", cert_no, name);
		System.out.println(verificationResult.getMsg());
		System.out.println(verificationResult.getZmBillid());
		*/
		VerificationResult verificationResult=QueryCertificationResult("ZM201705043000000686800101858033");
		System.out.println(verificationResult);
	}
	
	/**
	 * 按订单号查询认证结果
	 * @param zmBillId
	 * @return
	 */
	public static VerificationResult  QueryCertificationResult(String zmBillId) {
        ZhimaCustomerCertificationQueryRequest req = new ZhimaCustomerCertificationQueryRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setBizNo(zmBillId);// 必要参数 
        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, creditVerifyAppid,
				appPrivateKey, aliPublicKey);
        try {
            ZhimaCustomerCertificationQueryResponse response =(ZhimaCustomerCertificationQueryResponse)client.execute(req);
            if(response.isSuccess()){
            	if (response.getPassed() != null&&response.getPassed().equals("true")) {
            		return new VerificationResult(true, "认证成功");
				}else{
					return new VerificationResult(false, response.getFailedReason());
				}
            }else{
            	return new VerificationResult(false, response.getErrorMessage());
            }
        } catch (ZhimaApiException e) {
        	PrintException.printException(logger, e);
        }
        return new VerificationResult(false, "认证失败，请稍后重试");
    }
	
	/**
	 * 创建人脸实名认证订单
	 * 
	 * @param billId
	 * @param cert_no
	 * @param name
	 * @return
	 */
	public static VerificationResult createFaceCreditVerifyOrder(String billId,
			String cert_no, String name) {
		VerificationResult zmbillId = getZhimaBillId(billId, cert_no, name);
		logger.info("createFaceCreditVerifyOrder billId:"+billId+" cert_no:"+cert_no+" name:"+name+" zmbillId:"+JSON.toJSONString(zmbillId));
		if (zmbillId.isState()) {
			String urlString = getFaceVerifyOrderParam(zmbillId.getZmBillid());
			logger.info("createFaceCreditVerifyOrder billId:"+billId+" cert_no:"+cert_no+" name:"+name+" urlString:"+urlString);
			
			if (urlString!=null&&!urlString.isEmpty()) {
				return new VerificationResult(true, urlString,
						zmbillId.getZmBillid());
			}
			return new VerificationResult(false, "系统繁忙，请稍后再试");
		} else {
			return zmbillId;
		}
	}

	/**
	 * 创建实名查询订单
	 * 
	 * @param billId
	 * @param cert_no
	 * @param name
	 * @return
	 */
	public static VerificationResult createCreditVerifyOrder(String billId,
			String cert_no, String name) {
		String postData = getCreditVerifyOrderParam(billId, cert_no, name);
		HttpEntity httpEntity = HttpUtil.Post(gatewayUrl, postData);
		VerificationResult verificationResult = new VerificationResult(false,
				"未查询到相关实名记录");
		logger.info("createCreditVerifyOrder httpEntity:"
				+ JSON.toJSONString(httpEntity));
		if (httpEntity != null && httpEntity.getCode() != null
				&& httpEntity.getCode() == 200) {
			if (httpEntity.getHtml() != null) {
				Map<String, Object> resMap = JSON.parseObject(
						httpEntity.getHtml(),
						new TypeReference<Map<String, Object>>() {
						});
				String biz_response = (String) resMap.get("biz_response");
				if (resMap.get("encrypted") != null
						&& (boolean) resMap.get("encrypted")) {
					try {
						biz_response = RSACoderUtil.decrypt(biz_response,
								appPrivateKey, "utf-8");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				Map<String, Object> decryptedMap = JSON.parseObject(
						biz_response, new TypeReference<Map<String, Object>>() {
						});

				verificationResult = checkVerificationResult(decryptedMap);
			}
		}
		return verificationResult;
	}

	private static VerificationResult checkVerificationResult(
			Map<String, Object> decryptedMap) {
		logger.info("checkVerificationResult decryptedMap:"
				+ JSON.toJSONString(decryptedMap));
		if (decryptedMap.get("success") != null
				&& (boolean) decryptedMap.get("success")) {
			String verify_code = decryptedMap.get("verify_code").toString();
			if (verify_code.contains("V_CN_NM_MA")) {
				return new VerificationResult(true, "身份验证通过", decryptedMap.get(
						"biz_no").toString());
			} else if (verify_code.contains("V_CN_NM_UM")) {
				return new VerificationResult(false, "姓名与身份证号不匹配");
			} else {
				return new VerificationResult(false, "查询不到身份证信息");
			}
		} else {
			return new VerificationResult(false, decryptedMap.get(
					"error_message").toString());
		}
	}
}
