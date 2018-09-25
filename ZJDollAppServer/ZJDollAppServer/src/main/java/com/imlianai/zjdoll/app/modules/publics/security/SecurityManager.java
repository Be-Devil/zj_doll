package com.imlianai.zjdoll.app.modules.publics.security;


/**
 * 校验
 * @author Max
 *
 */
public interface SecurityManager {
   
    /**
     * 获取登陆码
     * @param uid
     * @return
     */
    public String getLoginSecurityCode(long uid);
    /**
     * 获取登陆码-强制更新
     * @param uid
     * @return
     */
    public String getLoginSecurityCodeNew(long uid);
    /**
     * 判断登陆码是否有效
     * @param uid
     * @param securityCode
     * @return
     */
    public boolean isLoginSecurityCodeValid(Long uid, String securityCode);
}
