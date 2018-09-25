package com.imlianai.dollpub.app.modules.core.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.core.user.dao.UserSrcDao;
import com.imlianai.dollpub.app.modules.core.user.domain.CustomerAuthToken;
import com.imlianai.dollpub.app.modules.core.user.service.UserSrcService;
import com.imlianai.dollpub.domain.user.UserSrc;

/**
 * @author wurui
 * @create 2018-01-24 11:49
 **/
@Service
public class UserSrcServiceImpl implements UserSrcService {

    @Resource
    private UserSrcDao userSrcDao;

    @Override
    public int saveUserSrc(UserSrc userSrc) {
        return userSrcDao.saveSrcUser(userSrc);
    }

    @Override
    public int updateUserSrc(UserSrc userSrc) {
        return userSrcDao.updateSrcUser(userSrc);
    }

    @Override
    public UserSrc getUserSrcBySrcUidAndCusId(String srcId,int customerId) {
        return userSrcDao.getUserSrcBySrcUidAndCusId(srcId,customerId);
    }

	@Override
	public long getCustomerAuthUser(String srcUid, int authCustomerId) {
		//查询中间表
		CustomerAuthToken token=userSrcDao.getCustomerAuthToken(authCustomerId, srcUid);
		if (token!=null&&token.getInnerUid()>0) {
			return token.getInnerUid();
		}
		return 0;
	}

	@Override
	public CustomerAuthToken getCustomerAuthToken(long innerUid) {
		return userSrcDao.getCustomerAuthToken(innerUid);
	}
}
