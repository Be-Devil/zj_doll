package com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class WebWeixinDataHandler {

	private static Pattern typePattern = Pattern
			.compile("<MsgType>.*</MsgType>");
	private static Pattern contentPattern = Pattern
			.compile("<Content>.*</Content>");
	private static Pattern twidPattern = Pattern
			.compile("<ToUserName>.*</ToUserName>");
	private static Pattern widPattern = Pattern
			.compile("<FromUserName>.*</FromUserName>");
	private static Pattern picPattern = Pattern.compile("<PicUrl>.*</PicUrl>");
	private static Pattern eventPattern = Pattern.compile("<Event>.*</Event>");
	private static Pattern eventKeyPattern = Pattern
			.compile("<EventKey>.*</EventKey>");
	private static Pattern latitudePattern = Pattern
			.compile("<Location_X>.*</Location_X>");
	private static Pattern longitudePattern = Pattern
			.compile("<Location_Y>.*</Location_Y>");
	private static Pattern labelPattern = Pattern.compile("<Label>.*</Label>");
	private static Pattern uidPattern = Pattern.compile("[0-9]");

	/* 加解密部分 */
	private static Pattern encryptPattern = Pattern
			.compile("<Encrypt>.*</Encrypt>");
	private static Pattern msgSignaturePattern = Pattern
			.compile("<MsgSignature>.*</MsgSignature>");
	private static Pattern noncePattern = Pattern.compile("<Nonce>.*</Nonce>");
	/* 授权票据部分*/
	private static Pattern infoTypePattern = Pattern.compile("<InfoType>.*</InfoType>");
	private static Pattern componentVerifyTicketPattern = Pattern.compile("<ComponentVerifyTicket>.*</ComponentVerifyTicket>");
	private static Pattern appIdPattern = Pattern.compile("<AppId>.*</AppId>");
	private static Pattern authorizerAppidPattern = Pattern.compile("<AuthorizerAppid>.*</AuthorizerAppid>");
	
	
	public static Map<String, String> getMapFromWeixin(
			HttpServletRequest request) {
		String line = null;
		StringBuilder json = new StringBuilder();
		BufferedReader reader = null;
		//logger.info("====requestID:" + request);
		try {
			reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				json.append(line);
				//logger.info("----" + request + "-------" + line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//logger.info("====requestID:" + request + " finish");
		String str = json.toString();
		return xmlElements(str);
	}

	public static Map<String, String> getEncryptMapFromWeixin(
			HttpServletRequest request){
		String line = null;
		StringBuilder json = new StringBuilder();
		BufferedReader reader = null;
		//logger.info("====requestID:" + request);
		try {
			reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				json.append(line);
				//logger.info("----" + request + "-------" + line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//logger.info("====requestID:" + request + " finish");
		String str = json.toString();
		Map<String, String> resultMap=xmlEncryptElements(str);
		resultMap.put("sourceMsg", str);
		return resultMap;
	}

	/**
	 * 
	 * 将非加密信息转换成Map
	 * 
	 * @param str
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static Map<String, String> xmlElements(String str) {
		Map<String, String> map = null;
		Matcher typeMatcher = typePattern.matcher(str);
		String typeStr = "";
		if (typeMatcher.find()) {
			map = new HashMap<String, String>();
			typeStr = typeMatcher.group();
		}
		// typeStr 就是 <MsgType><![CDATA[event]]></MsgType> 用于找出对应信息类型
		// 接着开始找出MsgType类型并加以转换
		if (typeStr.indexOf(WebWeixinDATA.Text.DATA) > 0) {// 文字
			map.put(WebWeixinDATA.MsgType.DATA, WebWeixinDATA.TextType.DATA);
			Matcher contentMatcher = contentPattern.matcher(str);
			if (contentMatcher.find()) {
				String contentStr = contentMatcher.group();
				contentStr = getValue(contentStr);
				map.put(WebWeixinDATA.Content.DATA, contentStr);
			}
		} else if (typeStr.indexOf(WebWeixinDATA.Image.DATA) > 0) {// 图片
			map.put(WebWeixinDATA.MsgType.DATA, WebWeixinDATA.ImageType.DATA);
			Matcher picMatcher = picPattern.matcher(str);
			if (picMatcher.find()) {
				String picStr = picMatcher.group();
				picStr = getValue(picStr);
				map.put("pic", picStr);
			}
		} else if (typeStr.indexOf(WebWeixinDATA.Event.DATA) > 0) {// 事件
			Matcher eventMatcher = eventPattern.matcher(str);
			if (eventMatcher.find()) {				
				String event = eventMatcher.group();
				event = getValue(event);
				//TODO 为通过全网发布新增事件监测
				map.put("eventFlag", "true");
				map.put("eventValues", event);
				//TODO 为通过全网发布新增事件监测
				if (event.equals(WebWeixinDATA.Event_Click.DATA)) {// 点击菜单拉取消息
					map.put(WebWeixinDATA.MsgType.DATA,
							WebWeixinDATA.ClickType.DATA);
				}
				if (event.equals(WebWeixinDATA.Event_View.DATA)) {// 点击菜单跳转链接
					map.put(WebWeixinDATA.MsgType.DATA,
							WebWeixinDATA.ViewType.DATA);
				}
				if (event.equals(WebWeixinDATA.Event_Subscribe.DATA)) {// 订阅
					map.put(WebWeixinDATA.MsgType.DATA,
							WebWeixinDATA.SubscribeType.DATA);
				}
				if (event.equals(WebWeixinDATA.Event_Unsubscribe.DATA)) {// 取消订阅
					map.put(WebWeixinDATA.MsgType.DATA,
							WebWeixinDATA.UnsubscribeType.DATA);
				}
				if (event.equals(WebWeixinDATA.Event_Location.DATA)) {// 位置
					map.put(WebWeixinDATA.MsgType.DATA,
							WebWeixinDATA.LocationType.DATA);
					Matcher latitudeMatcher = latitudePattern.matcher(str);
					if (latitudeMatcher.find()) {
						String latitude = latitudeMatcher.group();
						// latitude = getValue(latitude);
						map.put(WebWeixinDATA.Loc_Latitude.DATA, latitude);
					}
					Matcher longitudeMatcher = longitudePattern.matcher(str);
					if (longitudeMatcher.find()) {
						String longitude = longitudeMatcher.group();
						// longitude = getValue(longitude);
						map.put(WebWeixinDATA.Loc_Longitude.DATA, longitude);
					}
				}
			}
			Matcher eventKeyMatcher = eventKeyPattern.matcher(str);
			if (eventKeyMatcher.find()) {
				String eventKey = eventKeyMatcher.group();
				eventKey = getValue(eventKey);
				map.put(WebWeixinDATA.EventKey.DATA, eventKey);
			}

		} else if (typeStr.indexOf(WebWeixinDATA.Location.DATA) > 0) {
			map.put(WebWeixinDATA.MsgType.DATA, WebWeixinDATA.LocationType.DATA);
			Matcher latitudeMatcher = latitudePattern.matcher(str);
			if (latitudeMatcher.find()) {
				String latitude = latitudeMatcher.group();
				latitude = getValueSingle(latitude, latitudePattern);
				map.put(WebWeixinDATA.Loc_Latitude.DATA, latitude);
			}
			Matcher longitudeMatcher = longitudePattern.matcher(str);
			if (longitudeMatcher.find()) {
				String longitude = longitudeMatcher.group();
				longitude = getValueSingle(longitude, longitudePattern);
				map.put(WebWeixinDATA.Loc_Longitude.DATA, longitude);
			}
			Matcher labelMatcher = labelPattern.matcher(str);
			if (labelMatcher.find()) {
				String label = labelMatcher.group();
				label = getValue(label);
				map.put(WebWeixinDATA.Label.DATA, label);
			}

		}
		Matcher twidMatcher = twidPattern.matcher(str);
		if (twidMatcher.find()) {// ToUserName
			String twidStr = twidMatcher.group();
			twidStr = getValue(twidStr);
			map.put(WebWeixinDATA.ToUserName.DATA, twidStr);
		}
		Matcher widMatcher = widPattern.matcher(str);
		if (widMatcher.find()) {// FromUserName
			String widStr = widMatcher.group();
			widStr = getValue(widStr);
			map.put(WebWeixinDATA.FromUserName.DATA, widStr);
		}
		return map;
	}

	/**
	 * 将XML内容转变成可读内容
	 * 
	 * @param str
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String getValue(String str) {
		int start = str.indexOf("CDATA[");
		int end = str.indexOf("]]>");
		return str.substring(start + 6, end);
	}

	/**
	 * 取出匹配内容内的内容
	 * 
	 * @param str
	 * @param pattern
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private static String getValueSingle(String str, Pattern pattern) {
		String patternString = pattern.toString();
		String split[] = patternString.split("\\.\\*");
		int start = str.indexOf(split[0]);
		int end = str.indexOf(split[1]);
		return str.substring(start + split[0].length(), end);
	}

	public static String getUidStr(String str) {
		if (str.toLowerCase().indexOf("id") >= 0) {
			StringBuilder uidBuilder = new StringBuilder();
			for (int i = 0, len = str.length(); i < len; i++) {
				String cha = str.substring(i, i + 1);
				if (uidPattern.matcher(cha).matches()) {
					uidBuilder.append(cha);
				}
			}
			return uidBuilder.toString();
		}
		return null;
	}

	/**
	 * 将加密消息转化成map
	 * 
	 * @param xmltext
	 *            待提取的xml字符串
	 * @return 提取出的加密消息字符串
	 * @throws AesException
	 */
	public static Map<String, String> xmlEncryptElements(String xmltext){
		Map<String, String> map = new HashMap<String, String>();
		Matcher twidMatcher = twidPattern.matcher(xmltext);
		if (twidMatcher.find()) {// ToUserName
			String twidStr = twidMatcher.group();
			twidStr = getValue(twidStr);
			map.put(WebWeixinDATA.ToUserName.DATA, twidStr);
		}
		Matcher encryptMatcher = encryptPattern.matcher(xmltext);
		if (encryptMatcher.find()) {// Encrypt
			String encryptStr = encryptMatcher.group();
			encryptStr = getValue(encryptStr);
			map.put(WebWeixinDATA.Encrypt.DATA, encryptStr);
		}
		Matcher msgSignatureMatcher = msgSignaturePattern.matcher(xmltext);
		if (msgSignatureMatcher.find()) {// MsgSignature
			String msgSignatureStr = msgSignatureMatcher.group();
			msgSignatureStr = getValue(msgSignatureStr);
			map.put(WebWeixinDATA.MsgSignature.DATA, msgSignatureStr);
		}
		Matcher nonceMatcher = noncePattern.matcher(xmltext);
		if (nonceMatcher.find()) {// nonce
			String nonceStr = nonceMatcher.group();
			nonceStr = getValue(nonceStr);
			map.put(WebWeixinDATA.Nonce.DATA, nonceStr);
		}
		return map;
	}
	
	/**
	 * 
	 * 将非加密信息转换成Map
	 * 
	 * @param str
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static Map<String, String> xmlElementsOfComponentVerify(String str) {
		Map<String, String> map = null;
		Matcher infoTypeMatcher = infoTypePattern.matcher(str);
		String infoTypeStr = "";
		if (infoTypeMatcher.find()) {
			map = new HashMap<String, String>();
			infoTypeStr = infoTypeMatcher.group();
			infoTypeStr = getValue(infoTypeStr);
			map.put(WebWeixinDATA.InfoType.DATA, infoTypeStr);
		}
		Matcher componentVerifyTicketMatcher = componentVerifyTicketPattern.matcher(str);
		if (componentVerifyTicketMatcher.find()) {// ToUserName
			String componentVerifyTicketStr = componentVerifyTicketMatcher.group();
			componentVerifyTicketStr = getValue(componentVerifyTicketStr);
			map.put(WebWeixinDATA.ComponentVerifyTicket.DATA, componentVerifyTicketStr);
		}
		Matcher authorizerAppidMatcher = authorizerAppidPattern.matcher(str);
		if (authorizerAppidMatcher.find()) {// ToUserName
			String authorizerAppid = authorizerAppidMatcher.group();
			authorizerAppid = getValue(authorizerAppid);
			map.put(WebWeixinDATA.AuthorizerAppid.DATA, authorizerAppid);
		}
		Matcher appIdMatcher = appIdPattern.matcher(str);
		if (appIdMatcher.find()) {// FromUserName
			String appIdStr = appIdMatcher.group();
			appIdStr = getValue(appIdStr);
			map.put(WebWeixinDATA.AppId.DATA, appIdStr);
		}
		return map;
	}

}
