package com.imlianai.dollpub.app.modules.core.user.service;

import com.imlianai.dollpub.app.modules.core.user.domain.CustomerAuthToken;
import com.imlianai.dollpub.domain.user.UserSrc;

/**
 * @author wurui
 * @create 2018-01-24 11:47
 **/
public interface UserSrcService {
    public int saveUserSrc(UserSrc userSrc);
    public int updateUserSrc(UserSrc userSrc);

    public UserSrc getUserSrcBySrcUidAndCusId(String srcId,int customerId);
    
    /**
     * 获取三方授权用户信息
     * @param srcId
     * @param customerId
     * @return
     */
    public long getCustomerAuthUser(String srcId,int customerId);
    
    /**
     * 根据内部uid获取用户信息
     * @param innerUid
     * @return
     */
    public CustomerAuthToken getCustomerAuthToken(long innerUid);
}
