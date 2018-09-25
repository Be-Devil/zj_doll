package com.imlianai.dollpub.app.modules.core.user.value;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.constants.CacheConst;
import com.imlianai.dollpub.domain.user.UserValue;
import com.imlianai.rpc.support.manager.cache.CacheManager;

@Service
public class UserValueServiceImpl implements UserValueService {

	@Resource
	private UserValueDAO userValueDAO;
	@Resource
	private CacheManager cacheManager;

	@Override
	public int initUserValue(long uid) {
		UserValue value = new UserValue(uid);
		return userValueDAO.initUserValue(value);
	}

	@Override
	public int updateInviteNum(long uid, int inviteNum) {
		getUserValue(uid);
		return userValueDAO.updateInviteNum(uid, inviteNum);
	}

	@Override
	public UserValue getUserValue(long uid) {
		UserValue value = userValueDAO.getUserValue(uid);
		if (value == null) {
			value = new UserValue(uid);
			userValueDAO.initUserValue(value);
		}
		return value;
	}

	@Override
	public Map<Long, UserValue> getUserValues(List<Long> uids) {
		return userValueDAO.getUserValues(uids);
	}

	@Override
	public int addDollNum(long uid, int dollNum) {
		getUserValue(uid);
		return userValueDAO.addDollNum(uid, dollNum);
	}

	@Override
	public int addInviteMsgNum(long uid, int inviteNum) {
		getUserValue(uid);
		return userValueDAO.addInviteMsgNum(uid, inviteNum);
	}

	@Override
	public void resetInviteMsgNum(long uid) {
		userValueDAO.resetInviteMsgNum(uid);
	}
}
