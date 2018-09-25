package com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.UserWechat;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.WechatOpenIdInfo;

public class WebWeixinShareOauthUtil {

	private static final Logger logger = Logger
			.getLogger(WebWeixinShareOauthUtil.class);
	
	/**
	 * 获取需要保留的请求参数
	 * @param map
	 * @param needParam 不需保留的请求参数
	 * @return
	 */
	public static String getQueryParameter(Map<String,String[]> map, String[] noNeedParam) {
		Set<String> keySet = map.keySet();
		StringBuilder paramBuilder = new StringBuilder();
		boolean isContainParam = false;
		for (String key : keySet) {
			if(JSON.toJSONString(noNeedParam).indexOf(key)>=0){
				continue;
			}else{
				if(isContainParam || paramBuilder.length()>0){
					paramBuilder.append("&");
					isContainParam = true;
				}
				paramBuilder.append(key + "=" + map.get(key)[0]);
			}
		}
		return paramBuilder.toString();
	}
	
	/**
	 * 添加cookies
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param expiry
	 */
	public static void setCookies(HttpServletResponse response,String cookieName,String cookieValue,int expiry,String domain){
		Cookie cookie=new Cookie(cookieName, cookieValue); 
		cookie.setMaxAge(expiry); 
		cookie.setPath("/"); 
		cookie.setDomain(domain);
		response.addCookie(cookie); 
	}
	
	/**
	 * 处理网页用户登录cookies
	 * @param response
	 * @param uid
	 * @param loginkey
	 */
	public static void handleUserLoginCookies(HttpServletResponse response,String openId){
		Cookie cookie=new Cookie("MATCH_USER_ID", openId+""); 
		cookie.setPath("/vote/");
		cookie.setMaxAge(3600*24*7); 
		//response.addCookie(cookie); 
		cookie=new Cookie("MATCH_USER_ID", openId+""); 
		cookie.setPath("/");
		cookie.setMaxAge(3600*24*7); 
		cookie.setDomain(".ormatch.com");
		response.addCookie(cookie); 
	}

	/**
	 * 获取Cookies
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookie(HttpServletRequest request,String cookieName){
		Cookie cookies[] = request.getCookies();
		if(cookies!=null){
			for(int i=0,len=cookies.length;i<len;i++){
				if(StringUtils.equals(cookieName, cookies[i].getName())){
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 获取随机字符串
	 * @param length
	 * @return
	 */
	public static String getRandomURL(int length){
		String randomParam = "abcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for(int i=0;i<length;i++){
			int bi = random.nextInt(36);
			sb.append(randomParam.substring(bi, bi+1));
		}
		return sb.toString();
	}	
	
	/**
	 * 获取微信授权code-->获取用户信息第一步
	 * 
	 * @param response
	 * @param appid
	 * @param dirURL
	 * @param isURLEncode
	 */
	public static void redirect4GetCode(HttpServletResponse response,
			String appid, String dirURL, boolean isURLEncode) {
		try {
			if (isURLEncode) {
				dirURL = URLEncoder.encode(dirURL, "UTF-8");
			}
			String wxURL4GETCode = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
					+ appid
					+ "&redirect_uri="
					+ dirURL
					+ "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
			logger.info("redirect4GetCode:"+wxURL4GETCode);
			response.sendRedirect(wxURL4GETCode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取微信授权获取opendID-->获取用户信息第二步
	 * 
	 * @param appid
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static WechatOpenIdInfo getUserOpenid(String appid, String appSecret,
			String code) {
		String wxURL4GETOpenid = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ appSecret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code";
		String openidJSON = HttpUtil.Get(wxURL4GETOpenid).getHtml();
		if (StringUtils.isNotBlank(openidJSON)) {
			@SuppressWarnings("unchecked")
			WechatOpenIdInfo openIdInfo = JSON.parseObject(openidJSON, WechatOpenIdInfo.class);
			logger.info("-------------------getUserOpenid openidJSON:"+openidJSON);
			return openIdInfo;
		}
		return null;
	}
	
	public static Map<String, Object> getUserOpenidUnionId(String appid, String appSecret,
			String code) {
		String wxURL4GETOpenid = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ appSecret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code";
		String openidJSON = HttpUtil.Get(wxURL4GETOpenid).getHtml();
		if (StringUtils.isNotBlank(openidJSON)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = JSON.parseObject(openidJSON, Map.class);
			return  map;
		}
		return null;
	}

	/**
	 * 在确定用户未授权的情况下，获取用户信息的CODE-> 获取用户信息第三步
	 * 
	 * @param response
	 * @param appid
	 * @param dirURL
	 * @param isURLEncode
	 */
	public static void redirect4GetCodeUnauth(HttpServletResponse response,
			String appid, String dirURL, boolean isURLEncode) {
		try {
			if (isURLEncode) {
				dirURL = URLEncoder.encode(dirURL, "UTF-8");
			}
			String wxURL4GETCodeUnauth = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
					+ appid
					+ "&redirect_uri="
					+ dirURL
					+ "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
			logger.info("redirect4GetCodeUnauth appid:"+appid+" dirURL:"+dirURL+" wxURL4GETCodeUnauth:"+wxURL4GETCodeUnauth);
			response.sendRedirect(wxURL4GETCodeUnauth);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据未授权的code获取用户的token，openid信息-->获取用户信息第四步
	 * 
	 * @param appid
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static Map<String, Object> getOpenidTokenMap(String appid,
			String appSecret, String code) {
		String wxURL4GETUserToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ appSecret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code";
		String openidTokenJSON = HttpUtil.Get(wxURL4GETUserToken).getHtml();
		logger.info("getOpenidTokenMap openidTokenJSON:"+openidTokenJSON);
		if (StringUtils.isNotBlank(openidTokenJSON)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = JSON.parseObject(openidTokenJSON,
					Map.class);
			return map;
		}
		return null;
	}
	
	public static UserWechat getUserWechat(String appid,
			String appSecret, String code) {
		String wxURL4GETUserToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ appSecret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code";
		String openidTokenJSON = HttpUtil.Get(wxURL4GETUserToken).getHtml();
		logger.info("getOpenidTokenMap openidTokenJSON:"+openidTokenJSON);
		if (StringUtils.isNotBlank(openidTokenJSON)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = JSON.parseObject(openidTokenJSON,
					Map.class);
			UserWechat userWechat=new UserWechat(map);
			return userWechat;
		}
		return null;
	}

	/**
	 * 获取用户信息，-->获取用户信息第五步，至此完成。（数据固化，信息在JSP中完成）
	 * 
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public static Map<String, Object> getUserInfoMap(String accessToken,
			String openid) {
		String wxURL4GETUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ accessToken + "&openid=" + openid + "&lang=zh_CN";
		String userInfoJSON =HttpUtil.Get(wxURL4GETUserInfo).getHtml();
		if (StringUtils.isNotBlank(userInfoJSON)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = JSON.parseObject(userInfoJSON, Map.class);
			return map;
		}
		return null;
	}
	
	/**
	 * 根据code获取用户信息
	 * @param appid
	 * @param appSecret
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static UserWechat getUserInfoByCode(String appid,
			String appSecret,String code) {
		Map<String, Object> tokenMap=getOpenidTokenMap(appid, appSecret, code);
		String accessToken=(String)tokenMap.get("access_token");
		String openid=(String)tokenMap.get("openid");
		String wxURL4GETUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ accessToken + "&openid=" + openid + "&lang=zh_CN";
		String userInfoJSON =HttpUtil.Get(wxURL4GETUserInfo).getHtml();
		logger.info("getUserInfoByCode code:"+code+" userInfoJSON:"+userInfoJSON);
		if (StringUtils.isNotBlank(userInfoJSON)) {
			Map<String, Object> map = JSON.parseObject(userInfoJSON, Map.class);
			UserWechat userWechat=new UserWechat(map);
			userWechat.setAppid(appid);
			return userWechat;
		}
		return null;
	}
	
}
