package com.imlianai.dollpub.app.modules.publics.oauth.merchant.service;

public interface MerchantOauthService {

	/**
	 * 使用原系统账号密码登录
	 * @param srcId
	 * @param srcPwd
	 * @return
	 */
	public long loginWithSrcPwd(int authCustomerId,String srcId,String srcPwd); 
	
}
