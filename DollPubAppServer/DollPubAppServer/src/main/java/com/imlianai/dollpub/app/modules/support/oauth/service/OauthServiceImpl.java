package com.imlianai.dollpub.app.modules.support.oauth.service;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.support.oauth.dao.OauthDao;
import com.imlianai.dollpub.app.modules.support.oauth.domain.WeiXinUser;

@Service
public class OauthServiceImpl implements OauthService {

	@Resource
	private OauthDao oauthDao;

	@Override
	public int addWeiXinUser(WeiXinUser user) {
		return oauthDao.addWeiXinUser(user);
	}
}
