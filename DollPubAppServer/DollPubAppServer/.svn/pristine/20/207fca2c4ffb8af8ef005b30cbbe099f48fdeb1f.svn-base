package com.imlianai.dollpub.app.modules.publics.sms.tuojin;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpSender {
	/**
	 * @param uri
	 * @param account
	 * @param pswd
	 * @param mobiles
	 * @param content
	 * @param needstatus
	 * @param product
	 * @param extno
	 * @return
	 * @throws Exception
	 * 
	 * 
	 *返回值说明
0	提交成功
101	无此用户
102	密码错
103	提交过快（提交速度超过流速限制）
104	系统忙（因平台侧原因，暂时无法处理提交的短信）
105	敏感短信（短信内容包含敏感词）
106	消息长度错（>536或<=0）
107	包含错误的手机号码
108	手机号码个数错（群发>50000或<=0;单发>200或<=0）
109	无发送额度（该用户可用短信数已使用完）
110	不在发送时间内
111	超出该账户当月发送额度限制
112	无此产品，用户没有订购该产品
113	extno格式错（非数字或者长度不对）
115	自动审核驳回
116	签名不合法，未带签名（用户必须带签名的前提下）
117	IP地址认证错,请求调用的IP地址不是系统登记的IP地址
118	用户没有相应的发送权限
119	用户已过期
	 */
	public static String send(String uri, String account, String pswd,
			String mobiles, String content, boolean needstatus, String product,
			String extno) throws Exception {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(uri, false);
			method.setURI(new URI(base, "HttpSendSM", false));
			method.setQueryString(new NameValuePair[] {
					new NameValuePair("account", account),
					new NameValuePair("pswd", pswd),
					new NameValuePair("mobile", mobiles),
					new NameValuePair("needstatus", String.valueOf(needstatus)),
					new NameValuePair("msg", content),
					new NameValuePair("product", product),
					new NameValuePair("extno", extno) });

			int result = client.executeMethod(method);
			System.out.println("result:"+result);
			if (result == 200) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				String str = URLDecoder.decode(baos.toString(), "UTF-8");
				return str;
			}
			throw new Exception("HTTP ERROR Status: " + method.getStatusCode()
					+ ":" + method.getStatusText());
		} finally {
			method.releaseConnection();
		}
	}

	public static String batchSend(String uri, String account, String pswd,
			String mobiles, String content, boolean needstatus, String product,
			String extno) throws Exception {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(uri, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] {
					new NameValuePair("account", account),
					new NameValuePair("pswd", pswd),
					new NameValuePair("mobile", mobiles),
					new NameValuePair("needstatus", String.valueOf(needstatus)),
					new NameValuePair("msg", content),
					new NameValuePair("product", product),
					new NameValuePair("extno", extno) });

			int result = client.executeMethod(method);
			if (result == 200) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				String str = URLDecoder.decode(baos.toString(), "UTF-8");
				return str;
			}
			throw new Exception("HTTP ERROR Status: " + method.getStatusCode()
					+ ":" + method.getStatusText());
		} finally {
			method.releaseConnection();
		}
	}
}
