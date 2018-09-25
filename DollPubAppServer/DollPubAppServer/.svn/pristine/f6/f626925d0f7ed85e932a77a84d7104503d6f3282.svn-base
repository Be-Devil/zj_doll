package com.imlianai.dollpub.app.modules.publics.oauth.weixin;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinShareOauthUtil;
import com.imlianai.dollpub.app.modules.publics.oauth.weixin.config.OauthWeiXinConfig;
import com.imlianai.dollpub.app.modules.publics.qiniu.service.QiNiuService;
import com.imlianai.dollpub.domain.user.UserOauth;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.OauthManagerException;
import com.imlianai.rpc.support.utils.HttpUtil;

@Service
public class OauthWeiXinManagerImpl implements OauthWeiXinManager {

	private final BaseLogger logger = BaseLogger
			.getLogger(OauthWeiXinManagerImpl.class);

	@Resource
	private QiNiuService qiNiuService;

	@Override
	public UserOauth getUserInfo(String access_token, String openId)
			throws OauthManagerException {
		String reqUrl = getReqUrl(access_token, openId);
		HttpEntity httpEntity = HttpUtil.Get(reqUrl);
		String res = httpEntity.getHtml();
		UserOauth userInfo = new UserOauth(res, 3);
		logger.info(",openId=>" + openId + ",res=>" + res);
		if (StringUtils.isNotBlank(userInfo.getErrmsg())) {
			logger.info("error:" + userInfo);
			return userInfo;
		}
		String key = AppUtils.proNewName();
		if (StringUtils.isNotBlank(openId)) {
			key = openId;
		}
		String url = qiNiuService.captureAndGetFile(userInfo.getHead(),
				OauthWeiXinConfig.BUCKET_NAME, OauthWeiXinConfig.KEY_FLAG + "/"
						+ key);
		if (StringUtils.isNotBlank(url)) {
			userInfo.setHead(url);
		}
		logger.info("userInfo=>" + userInfo);
		return userInfo;
	}
	/**
	 * 获取请求授权url
	 * 
	 * @param request
	 * @return
	 */
	public static String getReqUrl(String token, String openId) {
		StringBuffer res = new StringBuffer();
		res = new StringBuffer(OauthWeiXinConfig.GET_USER_INFO_URL);
		res.append("?").append("oauth_consumer_key=");
		res.append(OauthWeiXinConfig.APP_ID);
		res.append("&access_token=");
		res.append(token);
		res.append("&openid=");
		res.append(openId);
		return res.toString();
	}
	@Override
	public UserOauth getUserInfo(String appid, String appSecret, String code) {
		UserOauth userInfo= WebWeixinShareOauthUtil.getUserInfoByCode(appid, appSecret, code);
		logger.info(",openId=>" + code + ",res=>" + JSON.toJSONString(userInfo));
		if (StringUtils.isNotBlank(userInfo.getErrmsg())) {
			logger.info("error:" + userInfo);
			return userInfo;
		}
		String key = AppUtils.proNewName();
		if (StringUtils.isNotBlank(userInfo.getOpenId())) {
			key = userInfo.getOpenId();
		}
		String url = qiNiuService.captureAndGetFile(userInfo.getHead(),
				OauthWeiXinConfig.BUCKET_NAME, OauthWeiXinConfig.KEY_FLAG + "/"
						+ key);
		if (StringUtils.isNotBlank(url)) {
			userInfo.setHead(url);
		}
		logger.info("userInfo=>" + userInfo);
		return userInfo;
	}
}
