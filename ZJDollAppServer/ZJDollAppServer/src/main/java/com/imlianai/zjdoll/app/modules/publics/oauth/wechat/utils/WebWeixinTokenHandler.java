package com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.zjdoll.app.modules.publics.oauth.weixin.config.OauthWeiXinConfig;

public class WebWeixinTokenHandler {

	private static final BaseLogger logger = BaseLogger
			.getLogger(WebWeixinTokenHandler.class);

	private static Map<String, Map<Integer, WebWeixinToken>> tokenMap = null;

	
	public static final String ACCESS_TOKEN = "access_token";
	public static final String ACCESS_TICKET = "ticket";
	public static final String EXPIRES_IN = "expires_in";
	public static final int ACCESS_TOKEN_TYPE = 1;
	public static final int ACCESS_TICKET_TYPE = 2;

	
	private static Map<String, String> idSecretMap=null;

	private static final String AppID = OauthWeiXinConfig.ACCOUNT_APP_ID;
	
	private static final String AppSecret = OauthWeiXinConfig.ACCOUNT_SECRET;
	
	
	private static long lastGetTokenTime = 0;

	private static final String gain_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	/**
	 * 初始化id密钥
	 */
	public static void initIdSecretMap(){
		logger.info("-------initIdSecretMap");
		idSecretMap=new HashMap<String, String>();
		idSecretMap.put(AppID, AppSecret);
		idSecretMap.put(OauthWeiXinConfig.ZS_ACCOUNT_APP_ID, OauthWeiXinConfig.ZS_ACCOUNT_SECRET);
		idSecretMap.put("wx8c5308361b165de9","9f7484bd7c97a7e841c7248e24a4d893");
	}
	
	public static String getDefaultAppid(){
		return OauthWeiXinConfig.ZS_ACCOUNT_APP_ID;
	}
	/**
	 * 根据appid获取密钥
	 * @param appid
	 * @return
	 */
	public static String getAppSecretByAppID(String appid){
		if(idSecretMap==null||idSecretMap.isEmpty()){
			logger.info("-------getAppSecretByAppID appid:"+appid+" idSecretMap==null");
			initIdSecretMap();
		}
		return idSecretMap.get(appid);
	}
	/**
	 * 
	 * 获取tokenMap
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static Map<String, Map<Integer, WebWeixinToken>> getTokenMap() {
		return tokenMap;
	}
	
	/**
	 * 将token设置进tokenMap
	 * @param appid
	 * @param newTokenMap
	 */
	public static void setTokenMap(String appid,Map<Integer, WebWeixinToken> newTokenMap){
		if(tokenMap==null||tokenMap.isEmpty()){
			tokenMap=new HashMap<String, Map<Integer,WebWeixinToken>>();
		}
		tokenMap.put(appid, newTokenMap);
	}

	/**
	 * 获取tokenMap
	 * @param appid
	 * @return
	 */
	public static Map<Integer,WebWeixinToken> getTokenMap(String appid){
		if(tokenMap==null||tokenMap.isEmpty()){
			tokenMap=new HashMap<String, Map<Integer,WebWeixinToken>>();
		}
		return tokenMap.get(appid);
	}
	
	/**
	 * 通用接口获取Token凭证
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static Map<String, Object> getToken(String appId, String appSecret) {
		logger.info("--------------------start getToken appId:"+appId+" appSecret:"+appSecret);
		if (appId == null || appSecret == null) {
			return null;
		}
		String token = null;
		String tockenUrl = WebWeixinTokenHandler.getTokenUrl(appId, appSecret);
		logger.info("--------------------getToken tockenUrl:"+tockenUrl);
		String response ="";
		//共3次获取token，成功一次则跳出
		for (int i = 0; i <= 3; i++) {
			response = HttpUtil.Get(tockenUrl).getHtml();
			logger.info("getToken the " +i+1+" time "+response);
			if(null!=response&&!response.trim().equals("")){
				break;
			}
		}
		logger.info("token info:" + response);
		lastGetTokenTime = System.currentTimeMillis();
		System.out.println("lastGetTokenTime:" + lastGetTokenTime);
		Map<String, Object> resMap = JSON.parseObject(response,
				new TypeReference<Map<String, Object>>() {
				});
		if (null != tokenMap) {
			token = (String) resMap.get(ACCESS_TOKEN);
		}
		logger.info("==token" + token);
		return resMap;
	}

	public static String getTokenUrl(String appId, String appSecret) {
		return String.format(gain_ACCESS_TOKEN, appId, appSecret);
	}

	/**
	 * 判断token是否当前有效
	 * @param create_time
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean isTokenInVaildTime(long create_time){
		logger.info("create_time"+create_time);
		logger.info("System.currentTimeMillis()"+System.currentTimeMillis());
		logger.info(System.currentTimeMillis() - create_time);
		//logger.info((7200-60) * 1000);
		if((System.currentTimeMillis() - create_time)/1000 > (7200-60) ){
			logger.info("isTokenInVaildTime token is expired");
			return false;
		}else {
			logger.info("isTokenInVaildTime token is vaild");
			return true;
		}
		
	}
}
