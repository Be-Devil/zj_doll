package com.imlianai.zjdoll.app.modules.publics.umeng.common;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONObject;

import com.imlianai.rpc.support.common.BaseLogger;

public class PushClient {

	private final BaseLogger logger = BaseLogger.getLogger(PushClient.class);

	// The user agent
	protected final String USER_AGENT = "Mozilla/5.0";

	// The host
	protected static final String host = "http://msg.umeng.com";

	// The upload path
	protected static final String uploadPath = "/upload";

	// The post path
	protected static final String postPath = "/api/send";

	public boolean send(UmengNotification msg) throws Exception {
		String timestamp = Integer
				.toString((int) (System.currentTimeMillis() / 1000));
		msg.setPredefinedKeyValue("timestamp", timestamp);
		String url = host + postPath;
		String postBody = msg.getPostBody();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg
				.getAppMasterSecret()).getBytes("utf8"));
		url = url + "?sign=" + sign;
		Map<String, String> header = new HashMap<String, String>();
		header.put("User-Agent", USER_AGENT);
		String result = post(url, postBody, header);
		logger.info("send: postBody:"+postBody+" result:"+result);
		return true;
	}

	public String uploadContents(String appkey, String appMasterSecret,
			String contents) throws Exception {
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer
				.toString((int) (System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", contents);
		String url = host + uploadPath;
		String postBody = uploadJson.toString();
		String sign = DigestUtils
				.md5Hex(("POST" + url + postBody + appMasterSecret)
						.getBytes("utf8"));
		url = url + "?sign=" + sign;
		Map<String, String> header = new HashMap<String, String>();
		header.put("User-Agent", USER_AGENT);
		String result = post(url, postBody, header);
		System.out.println(result.toString());
		JSONObject respJson = new JSONObject(result);
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("Failed to upload file");
		}
		JSONObject data = respJson.getJSONObject("data");
		String fileId = data.getString("file_id");
		return fileId;
	}

	public String post(String url, String context, Map<String, String> header) {
		HttpClient client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(2000);
		client.getHttpConnectionManager().getParams().setSoTimeout(5000);
		PostMethod postMethod = new PostMethod(url);
		try {
			StringRequestEntity entiry = new StringRequestEntity(context,
					"application/json", "utf-8");
			Iterator<Entry<String, String>> ite = header.entrySet().iterator();
			while (ite.hasNext()) {
				Entry<String, String> entry = ite.next();
				postMethod.addRequestHeader(entry.getKey(), entry.getValue());
			}
			postMethod.setRequestEntity(entiry);
			client.executeMethod(postMethod);
			return postMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			logger.error(e);
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e);
			logger.error(e, e);
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}
}
