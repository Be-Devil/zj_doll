package com.imlianai.zjdoll.app.modules.publics.security;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.constants.CacheConst;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.cache.CacheManager;
import com.imlianai.rpc.support.utils.JUUIDUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;

@Component
public class SecurityManagerImpl implements SecurityManager{

	private final BaseLogger logger = BaseLogger.getLoggerSwitch(this.getClass());
	
	@Resource
	private CacheManager cacheManager;
	@Resource
	private UserService userService;
	
    @Override
    public String getLoginSecurityCode(long uid) {
    	String key = CacheConst.USER_UUID_CACHE+"-"+uid;
    	String loginKey = cacheManager.getCache(key,String.class);
    	if(!StringUtil.isNullOrEmpty(loginKey))
    		return loginKey;
    	UserBase userBase=userService.getUserBase(uid);
    	if(userBase!=null && !StringUtil.isNullOrEmpty(userBase.getLoginKey())){
    		cacheManager.setCache(key, userBase.getLoginKey(), 604800);
    		return userBase.getLoginKey();
    	}
    	return loginKey;
    }
    
    @Override
    public String getLoginSecurityCodeNew(long uid) {
    	String loginKey = JUUIDUtil.createUuid();
		userService.updateLoginKey(uid, loginKey);
		String key = CacheConst.USER_UUID_CACHE+"-"+uid;
		cacheManager.setCache(key, loginKey, 604800);
		logger.info("newLoginKey uid:"+uid+" loginKey:"+loginKey);
    	return loginKey;
    }

    @Override
    public boolean isLoginSecurityCodeValid(Long uid, String securityCode) {
    	if(uid==null || StringUtil.isNullOrEmpty(securityCode))
    		return false;
        String key = getLoginSecurityCode(uid);
        if(securityCode.equals(key)){
        	return true;
        }else{
        	return false;
        }
    }
    
    public static void main(String[] args) {
//    	SecurityManagerImpl s = new SecurityManagerImpl();
//    	System.out.println(s.getLoginSecurityCode(533979));
//    	System.out.println(MD5_HexUtil.md5Hex(s.getLoginSecurityCode(533979)));
//    	System.out.println(s.isLoginSecurityCodeValid(577203, "3662ecf3c925c24a32ea8815ec35c3cb"));
//    	System.out.println(s.isBuySecurityCodeValid(757921, 2601, "1f7fd47c1c46cb1bcfe6dab4737e48df"));
//    	System.out.println(s.getBuySecurityCode(1799274, 2601));
	}


}
