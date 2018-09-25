package com.imlianai.zjdoll.app.modules.core.trade.util.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

	/**
	 * get请求
	 * 
	 * @param httpUrl
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String httpUrl) {
		BufferedReader reader = null;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection httpUrlCon = (HttpURLConnection) url
					.openConnection();
			httpUrlCon.setRequestMethod("GET");
			httpUrlCon.connect();
			reader = new BufferedReader(new InputStreamReader(httpUrlCon
					.getInputStream()));
			String line = "";
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			httpUrlCon.disconnect();
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * post请求
	 * 
	 * @param httpUrl
	 * @param param
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	public static String doPost(String httpUrl, String param) throws IOException {
		StringBuffer result = new StringBuffer();
		BufferedReader in = null;
		HttpURLConnection httpConn = null;
		try {
			URL url = new URL(httpUrl);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			PrintWriter out = new PrintWriter(httpConn.getOutputStream());
			out.print(param);
			out.flush();
			out.close();
			int code = httpConn.getResponseCode();
			if (code == 200) {
				in = new BufferedReader(new InputStreamReader(httpConn
						.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result.append(line);
				}
				return result.toString();
			}
			return null;
		} catch (IOException e) {
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpConn != null) {
				httpConn.disconnect();
			}
		}
	}
}
