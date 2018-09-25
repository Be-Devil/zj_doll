package com.imlianai.zjdoll.app.modules.support.oauth.domain;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class WeiXinUser {

	/**
	 * 用户openId
	 */
	private String openid;

	/**
	 * 用户昵称
	 */
	private String nickname;

	/**
	 * 用户性别
	 */
	private String sex;

	/**
	 * 用户省份
	 */
	private String province;

	/**
	 * 用户城市
	 */
	private String city;

	/**
	 * 用户国家
	 */
	private String country;

	/**
	 * 用户头像
	 */
	private String headimgurl;

	/**
	 * 用户特权
	 */
	private String privilege;

	/**
	 * 用户unionid
	 */
	private String unionid;

	/**
	 * 授权事件
	 */
	private Date time;

	/**
	 * 分享ID
	 */
	private long shareId;

	public WeiXinUser() {

	}

	public WeiXinUser(JSONObject json, long shareId) {
		this.city = json.getString("city");
		this.country = json.getString("country");
		this.headimgurl = json.getString("headimgurl");
		this.nickname = json.getString("nickname");
		this.openid = json.getString("openid");
		this.privilege = json.getString("privilege");
		this.province = json.getString("province");
		this.sex = json.getString("sex");
		this.shareId = shareId;
		this.unionid = json.getString("unionid");;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
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

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public long getShareId() {
		return shareId;
	}

	public void setShareId(long shareId) {
		this.shareId = shareId;
	}

	@Override
	public String toString() {
		return "WeiXinUser [openid=" + openid + ", nickname=" + nickname
				+ ", sex=" + sex + ", province=" + province + ", city=" + city
				+ ", country=" + country + ", headimgurl=" + headimgurl
				+ ", privilege=" + privilege + ", unionid=" + unionid
				+ ", time=" + time + ", shareId=" + shareId + "]";
	}

}
