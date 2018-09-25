package com.imlianai.dollpub.app.modules.publics.oauth.merchant.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.app.modules.core.user.domain.CustomerAuthUserInfo;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.core.user.service.UserSrcService;
import com.imlianai.dollpub.app.modules.publics.oauth.merchant.domain.MerchantOauthUserToken;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class MerchantOauthServiceImpl implements MerchantOauthService {

	@Resource
	private CustomerService customerService;

	@Resource
	private UserSrcService userSrcService;
	
	@Resource
	private UserService userService;
	@Override
	public long loginWithSrcPwd(int authCustomerId, String srcId, String srcPwd) {
		Customer customer = customerService.getCustomerById(authCustomerId);
		String url = customer.getLoginUrl();
		Map<String, Object> postDate = new HashMap<String, Object>();
		postDate.put("userId", srcId);
		postDate.put("pwd", srcPwd);
		postDate.put("action", "loginPwd");
		HttpEntity httpEntity = HttpUtil.Post(url, JSON.toJSONString(postDate));
		if (httpEntity!=null) {
			if (!StringUtil.isNullOrEmpty(httpEntity.getHtml())) {
				BaseRespVO resp=JSON.parseObject(httpEntity.getHtml(), BaseRespVO.class);
				if (resp!=null) {
					if (resp.isState()) {
						Object uObject= resp.getData();
						MerchantOauthUserToken token=JSON.parseObject(JSON.toJSONString(uObject), MerchantOauthUserToken.class);
						if (!StringUtil.isNullOrEmpty(token)) {
							long uid=userSrcService.getCustomerAuthUser(token.getUserId(), authCustomerId);
							if (uid>0) {
								return uid;
							}
							uid=userService.initUser(84, authCustomerId, token.getUserId(), token.getToken());
							if (uid>0) {
								return uid;
							}
						}
					}
				}
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		
	}
}
