package com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain;


import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.OauthManagerException;

public class OauthUserBean implements Serializable {

	private static final BaseLogger logger = BaseLogger.getLogger(OauthUserBean.class);
	/**
	 * openid
	 */
	private String openId;

	/**
	 * 昵称
	 */
	private String name;

	/**
	 * 头像
	 */
	private String head;

	/**
	 * 性别
	 */
	private int gender;

	/**
	 * 地市
	 */
	private String province;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 错误信息
	 */
	private String errmsg;

	/**
	 * 错误码
	 */
	private int errcode;

	/**
	 * 微信unionId
	 */
	private String unionId;
	
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

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
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

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public OauthUserBean() {

	}

	public OauthUserBean(String jsonStr, int type) throws OauthManagerException {
		init(jsonStr, type);
	}

	private void init(String jsonStr, int type) throws OauthManagerException {
		if (StringUtils.isNotBlank(jsonStr)) {
			try {
				logger.info("jsonStr=" + jsonStr);
				JSONObject json = new JSONObject(jsonStr);
				// QQ用户
				if (type == 1) {
					errcode = json.getInt("ret");
					if (0 != errcode) {
						errmsg = json.getString("msg");
					} else {
						errmsg = "";
						name = json.getString("nickname");
						String genderStr = json.getString("gender");
						if (StringUtils.equals(genderStr, "男"))
							gender = 1;
						else if (StringUtils.equals(genderStr, "女"))
							gender = 2;
						else if (StringUtils.isNotBlank(genderStr))
							gender = Integer.parseInt(genderStr);
						if (json.has("figureurl_qq_2")) {
							head = json.getString("figureurl_qq_2");
						} else if (json.has("figureurl_qq_1")) {
							head = json.getString("figureurl_qq_1");
						} else {
							head = json.getString("figureurl_2");
						}
						province = json.getString("province");
						city = json.getString("city");

					}
				} else if (type == 2) {
					// 微博
					boolean hasError = json.has("error");
					if (hasError
							&& StringUtils.isNotBlank(json.getString("error"))) {
						errmsg = json.getString("msg");
						errcode = json.getInt("error_code");
					} else {
						errmsg = "";
						openId = json.getInt("id") + "";
						name = json.getString("name");
						String location = json.getString("location");
						String locationArr[] = StringUtils.split(location, " ");
						if (locationArr == null || locationArr.length != 2) {
							province = json.getInt("province") + "";
							city = json.getInt("city") + "";
						} else {
							province = locationArr[0];
							city = locationArr[1];
						}
						String genderStr = json.getString("gender");
						if (StringUtils.equalsIgnoreCase(genderStr, "m"))
							gender = 1;
						else if (StringUtils.equalsIgnoreCase(genderStr, "f"))
							gender = 2;
						else
							gender = 0;
						head = json.getString("avatar_hd");
					}
				} else if (type == 3||type == 4||type == 5) {
					// 3-微信app授权 4-微信web授权 5-小程序授权
					boolean hasError = json.has("errmsg");
					if (hasError) {
						errmsg = json.getString("errmsg");
						errcode = json.getInt("errcode");
					} else {
						errmsg = "";
						name = json.getString("nickname");
						province = json.getString("province");
						city = json.getString("city");
						head = json.getString("headimgurl");
						openId = json.getString("openid");
						gender = json.getInt("sex");
						unionId=json.getString("unionid");
					}
				}
			} catch (org.json.JSONException jsone) {
				throw new OauthManagerException((new StringBuilder()).append(
						jsone.getMessage()).append(":").append(jsonStr)
						.toString(), jsone);
			}
		}

	}

	@Override
	public String toString() {
		return "OauthUserBean [city=" + city + ", errcode=" + errcode
				+ ", errmsg=" + errmsg + ", gender=" + gender + ", head="
				+ head + ", name=" + name + ", openId=" + openId
				+ ", province=" + province + "]";
	}

}
