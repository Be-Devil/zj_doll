package com.imlianai.dollpub.app.modules.publics.sms.ali;


import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import java.util.HashMap;
import java.util.Map;

public class AliMessage {

//	private static final String reqUrl = "http://sms.market.alicloudapi.com/singleSendSms";

	public static void main(String[] args) throws ClientException, InterruptedException {
		// 您的迷说验证码是{0}，请在页面中提交验证码完成验证，15分钟内有效。
//		String host = "http://sms.market.alicloudapi.com";
//		String path = "/singleSendSms";
//		String method = "GET";
//		String appcode = "86849951846548239e676903933eda33";
//		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Authorization", "APPCODE " + appcode);
//		Map<String, String> querys = new HashMap<String, String>();
//		querys.put("ParamString", "%7B%22no%22%3A%22123456%22%7D");
//		querys.put("RecNum", "RecNum");
//		querys.put("SignName", "SignName");
//		querys.put("TemplateCode", "TemplateCode");
//		StringBuffer url = new StringBuffer(reqUrl);
//		url.append("?ParamString=");
//		// url.append(ParamString);
//		url.append("&RecNum=");
//		// url.append(RecNum);
//		url.append("&SignName=");
//		// url.append(SignName);
//		url.append("&TemplateCode=");
//		// url.append(TemplateCode);
//		try {
//			// HttpUtil.Get(url, headers);
//		} catch (Exception e) {
//			e.printStackTrace();
//			e.printStackTrace();
//		}

		aliSMSTest();
	}


	/**
	 * 阿里短信服务测试
	 * @throws ClientException
	 * @throws InterruptedException
	 */
	public static void aliSMSTest() throws ClientException, InterruptedException {
		AliSmsSendRequest sendSmsReq = new AliSmsSendRequest("15622349354", "阿里云短信测试专用", "SMS_126280146");
		Map<String, String> templateParam = new HashMap<>();
		templateParam.put("customer", "test");
		sendSmsReq.setTemplateParam(JSON.toJSONString(templateParam));


		//发短信
		SendSmsResponse response = AliSMSApi.sendSMS(sendSmsReq);
		System.out.println("短信接口返回的数据----------------");
		System.out.println("Code=" + response.getCode());
		System.out.println("Message=" + response.getMessage());
		System.out.println("RequestId=" + response.getRequestId());
		System.out.println("BizId=" + response.getBizId());

		Thread.sleep(3000L);

		//查明细
		if (response.getCode() != null && response.getCode().equals("OK")) {

			AliSMSQueryRequest aliSMSQueryRequest = new AliSMSQueryRequest("15622349354", "20180223");
			//aliSMSQueryRequest.setBizId(response.getBizId());

			QuerySendDetailsResponse querySendDetailsResponse = AliSMSApi.querySendDetails(aliSMSQueryRequest);
			System.out.println("\n短信明细查询接口返回数据----------------");
			System.out.println("Code=" + querySendDetailsResponse.getCode());
			System.out.println("Message=" + querySendDetailsResponse.getMessage());
			int i = 0;
			for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs()) {
				System.out.println("\n----------------------------------------------");
				System.out.println("SmsSendDetailDTO[" + i + "]:");
				System.out.println("Content=" + smsSendDetailDTO.getContent());
				System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
				System.out.println("OutId=" + smsSendDetailDTO.getOutId());
				System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
				System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
				System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
				System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
				System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
				System.out.println();
				i++;
			}
			System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
			System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
		}
	}


}
