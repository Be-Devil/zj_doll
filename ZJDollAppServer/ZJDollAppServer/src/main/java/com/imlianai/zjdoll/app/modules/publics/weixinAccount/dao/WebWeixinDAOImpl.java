package com.imlianai.zjdoll.app.modules.publics.weixinAccount.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain.WebUserInfoToken;
import com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain.WebWeixinUserInfo;

@Repository
public class WebWeixinDAOImpl implements WebWeixinDAO {
	
	private static final BaseLogger logger  = BaseLogger.getLogger(WebWeixinDAOImpl.class);
	
	@Resource
	private JdbcHandler jdbcHandler;
	
	
	private static String addMpWeixinToken4LianaiSql = "insert into web_wx_token_total(appid,`type`,token_ticket,expires_in,update_time) values(?,?,?,?,now()) on duplicate key update token_ticket=?,expires_in=?,update_time=now()";
	@SuppressWarnings("unchecked")
	@Override
	public int addMpWeixinToken(String appid,String token,int expires,int type) {
		try {
			return jdbcHandler.executeSql(addMpWeixinToken4LianaiSql,appid, type,token,expires,token,expires);
		} catch (Exception e) {
			logger.error("addMpWeixinToken-e:" + e.getMessage(),e);
		}
		return 0;
	}
	
	private static String getWebToken4LianaiSql = "select * from web_wx_token_total where appid=?";
	@Override
	public Map<Integer, WebWeixinToken> getWebToken(String appid) {
		try {
			Map<Integer, WebWeixinToken> result = null;
			List<WebWeixinToken> webWeixinTokens = jdbcHandler.queryBySql(WebWeixinToken.class, getWebToken4LianaiSql,appid);
			if(webWeixinTokens!=null && webWeixinTokens.size()>0){
				result = new HashMap<Integer, WebWeixinToken>();
				for(WebWeixinToken webWeixinToken : webWeixinTokens){
					result.put(webWeixinToken.getType(), webWeixinToken);
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("getWebToken-e:" + e.getMessage(),e);
		}
		return null;
	}
	
	private static String addMpWeixinUserInfoTokenSql = "insert into wx_web_user_info(openid,access_token,expires_in,refresh_token,update_time) values(?,?,?,?,now()) on duplicate key update access_token=?,expires_in=?,refresh_token=?,update_time=now()";
	@SuppressWarnings("unchecked")
	@Override
	public int addMpWeixinUserInfoToken(String openid, String access_token,
			String refresh_token, int expire_in) {
		try {
			return jdbcHandler.executeSql(addMpWeixinUserInfoTokenSql, openid,access_token,expire_in,refresh_token,access_token,expire_in,refresh_token);
		} catch (Exception e) {
			logger.error("addMpWeixinUserInfoToken-e:" + e.getMessage(),e);
		}
		return 0;
	}
	
	private static String getUserInfoTokenSql = "select * from wx_web_user_info where openid=?";
	@Override
	public WebUserInfoToken getUserInfoToken(String openid) {
		try {
			return jdbcHandler.queryOneBySql(WebUserInfoToken.class, getUserInfoTokenSql, openid);
		} catch (Exception e) {
			logger.error("getUserInfoToken-e:" + e.getMessage(),e);
		}
		return null;
	}
	
	private static String addWeixinUserInfoSql = "insert into wx_web_user_info(openid,nickname,sex,province,city,country,headimgurl,unionid,update_time)"
			+ "values(?,?,?,?,?,?,?,?,now()) on duplicate key update nickname=?,sex=?,province=?,city=?,country=?,headimgurl=?,unionid=?,update_time=now()";
	@SuppressWarnings("unchecked")
	@Override
	public int addWeixinUserInfo(WebWeixinUserInfo info) {
		try {
			return jdbcHandler.executeSql(addWeixinUserInfoSql, info.getOpenid(),info.getNickname(),info.getSex(),info.getProvince(),info.getCity(),info.getCountry(),info.getHeadimgurl(),info.getUnionid()
					,info.getNickname(),info.getSex(),info.getProvince(),info.getCity(),info.getCountry(),info.getHeadimgurl(),info.getUnionid());
		} catch (Exception e) {
			logger.error("addWeixinUserInfo-e:" + e.getMessage(),e);
		}
		return 0;
	}
	
	private static String addWeixinUserInfoWithAppidSql = "insert into wx_web_user_info_total(appid,openid,nickname,sex,province,city,country,headimgurl,unionid,update_time)"
			+ "values(?,?,?,?,?,?,?,?,?,now()) on duplicate key update nickname=?,sex=?,province=?,city=?,country=?,headimgurl=?,unionid=?,update_time=now()";
	@SuppressWarnings("unchecked")
	@Override
	public int addWeixinUserInfo(String appid,WebWeixinUserInfo info) {
		try {
			return jdbcHandler.executeSql(addWeixinUserInfoWithAppidSql,appid, info.getOpenid(),info.getNickname(),info.getSex(),info.getProvince(),info.getCity(),info.getCountry(),info.getHeadimgurl(),info.getUnionid()
					,info.getNickname(),info.getSex(),info.getProvince(),info.getCity(),info.getCountry(),info.getHeadimgurl(),info.getUnionid());
		} catch (Exception e) {
			logger.error("addWeixinUserInfo-e:" + e.getMessage(),e);
		}
		return 0;
	}
	
	private static String getWeixinUserInfoSql = "select * from wx_web_user_info where openid=?";
	@Override
	public WebWeixinUserInfo getWeixinUserInfo(String openid) {
		try {
			return jdbcHandler.queryOneBySql(WebWeixinUserInfo.class, getWeixinUserInfoSql, openid);
		} catch (Exception e) {
			logger.error("getWeixinUserInfo-e:" + e.getMessage(),e);
		}
		return null;
	}
	
	private static String getWeixinUserInfoWithAppidSql = "select * from wx_web_user_info_total where appid=? and openid=?";
	@Override
	public WebWeixinUserInfo getWeixinUserInfo(String appid,String openid) {
		try {
			return jdbcHandler.queryOneBySql(WebWeixinUserInfo.class, getWeixinUserInfoWithAppidSql,appid, openid);
		} catch (Exception e) {
			logger.error("getWeixinUserInfo-e:" + e.getMessage(),e);
		}
		return null;
	}
	
	private static String isExitUserInfoSql = "select count(1) from wx_web_user_info where openid=? limit 1";
	@Override
	public boolean isExitUserInfo(String openid) {
		try {
			return jdbcHandler.queryCount(isExitUserInfoSql, openid)>0;
		} catch (Exception e) {
			logger.error("isExitUserInfo-e:" + e.getMessage(),e);
		}
		return false;
	}
	
	private static String isExitUserInfoWithAppidSql = "select 1 from wx_web_user_info_total where appid=? and openid=? limit 1";
	@Override
	public boolean isExitUserInfo(String appid,String openid) {
		try {
			return jdbcHandler.queryRowCount(isExitUserInfoWithAppidSql,appid, openid)>0;
		} catch (Exception e) {
			logger.error("isExitUserInfo-e:" + e.getMessage(),e);
		}
		return false;
	}

}
