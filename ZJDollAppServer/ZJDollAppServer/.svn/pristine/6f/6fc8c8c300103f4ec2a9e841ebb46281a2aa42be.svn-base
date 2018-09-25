package com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain;

import java.util.Map;

import com.imlianai.rpc.support.common.BaseModel;
import com.imlianai.rpc.support.utils.StringUtil;

/**
 * 微信用户
 * 
 * @author lenovo
 * 
 */
@SuppressWarnings("serial")
public class UserWechat extends BaseModel {

	private String unionId;

	private String openId;

	private String name;

	private String head;
	
	private String appid;

	public UserWechat() {
	}

	public UserWechat(Map<String, Object> map) {
		if (StringUtil.isNullOrEmpty(map)) {
			return ;
		}
		if (map.get("unionid")!=null&&map.get("unionid") instanceof String) {
			this.unionId=(String)map.get("unionid");
		}
		if (map.get("openid")!=null&&map.get("openid") instanceof String) {
			this.openId=(String)map.get("openid");
		}
		if (map.get("nickname")!=null&&map.get("nickname") instanceof String) {
			this.name=(String)map.get("nickname");
		}
		if (map.get("headimgurl")!=null&&map.get("headimgurl") instanceof String) {
			this.head=(String)map.get("headimgurl");
		}
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

}
