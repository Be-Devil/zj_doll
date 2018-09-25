package com.imlianai.zjdoll.app.modules.core.user.attribute;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.user.UserAttribute;

@Service
public class UserAttributeServiceImpl implements UserAttributeService {

	@Resource
	UserAttributeDao userAttributeDao;
	
	@Override
	public UserAttribute getUserAttribute(Long uid) {
		UserAttribute userAttribute=userAttributeDao.getUserAttribute(uid);
		if (userAttribute==null) {
			userAttributeDao.initUserAttribute(uid);
			userAttribute=userAttributeDao.getUserAttribute(uid);
		}
		return userAttribute;
	}

	@Override
	public void updateUserBan(List<Long> uids, String reason, String time) {
		userAttributeDao.updateUserBan(uids, reason, time);
	}

	@Override
	public void removeUserBan(List<Long> uids) {
		userAttributeDao.removeUserBan(uids);
	}

	@Override
	public void incTotalCharge(long uid, int inc) {
		userAttributeDao.incTotalCharge(uid, inc);
	}

	@Override
	public void updateWithdraw(long uid, int redpacket) {
		userAttributeDao.updateWithdraw(uid, redpacket);
	}

}
