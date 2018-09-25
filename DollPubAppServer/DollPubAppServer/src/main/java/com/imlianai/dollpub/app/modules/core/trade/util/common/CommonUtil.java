package com.imlianai.dollpub.app.modules.core.trade.util.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class CommonUtil {

	public static String randomNonceStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789=|";

	/**
	 * 
	 * @param params
	 * @return
	 */
	public static String createUrlParamFormat(Map<String, String> params) {
		if (params != null) {
			SortedMap<String, String> sort = new TreeMap<String, String>(params);
			StringBuffer sb = new StringBuffer();
			Iterator<Entry<String, String>> ite = sort.entrySet().iterator();
			while (ite.hasNext()) {
				Entry<String, String> entry = ite.next();
				String k = entry.getKey();
				String v = entry.getValue();
				if (StringUtils.isNotBlank(v)) {
					sb.append(k + "=" + v + "&");
				}
			}
			return StringUtils.substringBeforeLast(sb.toString(), "&");
		}
		return null;
	}

	/**
	 * 获取字符编码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getCharacterEncoding(HttpServletRequest request,
			HttpServletResponse response) {
		if (null == request || null == response) {
			return "utf-8";
		}
		String enc = request.getCharacterEncoding();
		if (null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}
		if (null == enc || "".equals(enc)) {
			enc = "utf-8";
		}
		return enc;
	}

	/**
	 * 获取流信息
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getInputStreamParamsStr(HttpServletRequest request)
			throws IOException {
		BufferedReader reader = null;
		try {
			StringBuffer inputStream = new StringBuffer();
			String line = null;
			reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				inputStream.append(line);
			}
			if (inputStream == null || inputStream.length() <= 0)
				return null;
			return inputStream.toString();
		} catch (IOException e) {
			throw e;
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	/**
	 * 格式化form数据
	 * 
	 * @param requestParams
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getFormDateFormat(Map requestParams) {
		if (requestParams != null) {
			Map<String, String> params = new HashMap<String, String>();
			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}
			return params;
		}
		return null;
	}

	/**
	 * 获取当前时间戳
	 * 
	 * @return
	 */
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取map指定类型值
	 * 
	 * @param <T>
	 * @param params
	 * @param name
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getParamIfErrorNull(Map<String, Object> params,
			String name, Class<T> clazz) {
		if (params == null)
			return null;
		Object obj = params.get(name);
		if (obj == null)
			return null;
		String value = obj.toString();
		try {
			if (clazz == String.class)
				return (T) value;
			else if (clazz == Integer.class)
				return (T) Integer.valueOf(value);
			else if (clazz == Long.class)
				return (T) Long.valueOf(value);
			else if (clazz == Boolean.class)
				return (T) Boolean.valueOf(value);
			else if (clazz == Float.class)
				return (T) Float.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
		return null;
	}
}
