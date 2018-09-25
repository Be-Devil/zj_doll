package com.imlianai.dollpub.app.modules.publics.oauth.wechat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.app.modules.publics.oauth.wechat.dao.WechatDao;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain.WebWeixinToken;
import com.imlianai.dollpub.domain.oauth.WechatAppSetting;
import com.imlianai.dollpub.domain.user.UserWechat;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
@Repository
public class WechatDaoImpl implements WechatDao{

	@Resource
	private JdbcHandler jdbcHandler;
	
	@Override
	public void saveWechatUserInfo(String openId, String unionId, String name,
			String head) {
		jdbcHandler.executeSql("insert into wechat_user_info (unionId,openId,name,head) values (?,?,?,?) on duplicate key update name=? , head=? ",unionId,openId,name,head,name,head);
	}

	@Override
	public UserWechat getUserWechat(String unionId) {
		return jdbcHandler.queryOneBySql(UserWechat.class, "select * from wechat_user_info where unionId=? ", unionId);
	}

	private static String addMpWeixinToken = "insert into wx_token_total(appid,`type`,token_ticket,expires_in,update_time) values(?,?,?,?,now()) on duplicate key update token_ticket=?,expires_in=?,update_time=now()";
	@SuppressWarnings("unchecked")
	@Override
	public int addMpWeixinToken(String appid,String token,int expires,int type) {
		try {
			return jdbcHandler.executeSql(addMpWeixinToken,appid, type,token,expires,token,expires);
		} catch (Exception e) {
		}
		return 0;
	}
	
	private static String getWebToken4LianaiSql = "select * from wx_token_total where appid=?";
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
		}
		return null;
	}
	@Override
	public WechatAppSetting getWechatAppSetting(String appid){
		return jdbcHandler.queryOneBySql(WechatAppSetting.class, "select * from wechat_app_setting where appid=?",appid);
	}

	@Override
	public WechatAppSetting getWechatAppSettingBySrcid(String srcid) {
		return jdbcHandler.queryOneBySql(WechatAppSetting.class, "select * from wechat_app_setting where srcid=?",srcid);
	}
}
