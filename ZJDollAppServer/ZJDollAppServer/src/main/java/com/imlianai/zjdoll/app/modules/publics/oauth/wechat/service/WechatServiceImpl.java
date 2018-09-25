package com.imlianai.zjdoll.app.modules.publics.oauth.wechat.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.dao.WechatDao;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.UserWechat;
@Service
public class WechatServiceImpl implements WechatService{

	@Resource
	WechatDao wechatDao;
	@Override
	public void saveWechatUserInfo(String openId, String unionId, String name,
			String head,String appid) {
		wechatDao.saveWechatUserInfo(openId, unionId, name, head,appid);
	}
	@Override
	public UserWechat getUserWechat(String unionId) {
		return wechatDao.getUserWechat(unionId);
	}
	@Override
	public void saveWechatUserInfo(UserWechat userWechat) {
		wechatDao.saveWechatUserInfo(userWechat.getOpenId(), userWechat.getUnionId(), userWechat.getName(), userWechat.getHead(),userWechat.getAppid());
	}

}
