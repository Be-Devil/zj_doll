package com.imlianai.dollpub.app.modules.core.user.dao;

import com.imlianai.dollpub.app.modules.core.user.domain.CustomerAuthToken;
import com.imlianai.dollpub.domain.user.UserSrc;

/**
 * @author wurui
 * @create 2018-01-24 11:03
 **/
public interface UserSrcDao {

    public int saveSrcUser(UserSrc userSrc);
    public int updateSrcUser(UserSrc userSrc);

    public UserSrc getUserSrcBySrcUidAndCusId(String srcId,int customerId);
    
    /**
     * 获取源系统授权信息是否存在
     * @param customerId
     * @param uid
     * @return
     */
    public CustomerAuthToken getCustomerAuthToken(int authCustomerId,String srcUid);
    
    /**
     * 根据内部uid获取用户信息
     * @param innerUid
     * @return
     */
    public CustomerAuthToken getCustomerAuthToken(long innerUid);

    /**
     * 更新系统内部uid
     * @param authCustomerId
     * @param srcUid
     * @param uid
     * @return
     */
    public int updateInnerUid(int authCustomerId,String srcUid,long uid);
    
    /**
     * 增加用户token
     * @param customerId
     * @param uid
     * @param token
     * @return
     */
    public int addCustomerAuthToken(int customerId,String uid,String token);
}
