package com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class WebWeixinUserInfo {
	private String openid;
	private String nickname;
	private int sex;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	private String unionid;
	
	public WebWeixinUserInfo() {
	}
	
	public WebWeixinUserInfo(ResultSet rs) throws SQLException {
		this.openid = rs.getString("openid");
		this.country = rs.getString("country");
		this.headimgurl = rs.getString("headimgurl");
		this.nickname = rs.getString("nickname");
		this.province = rs.getString("province");
		this.sex = rs.getInt("sex");
		this.unionid = rs.getString("unionid");
		this.city = rs.getString("city");
	}
	
	public WebWeixinUserInfo(Map<String,Object> weixinMap) {
		this.openid = (String)weixinMap.get("openid");
		this.country = (String)weixinMap.get("country");
		this.headimgurl = (String)weixinMap.get("headimgurl");
		this.nickname = (String)weixinMap.get("nickname");
		this.province = (String)weixinMap.get("province");
		if(weixinMap.get("sex")!=null)
		this.sex = (Integer)weixinMap.get("sex");
		this.unionid = (String)weixinMap.get("unionid");
		this.city = (String)weixinMap.get("city");
		
	}
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	@Override
	public String toString() {
		return "WebWeixinUserInfo [openid=" + openid + ", nickname=" + nickname
				+ ", sex=" + sex + ", province=" + province + ", city=" + city
				+ ", country=" + country + ", headimgurl=" + headimgurl
				+ ", unionid=" + unionid + "]";
	}
	
	
}
