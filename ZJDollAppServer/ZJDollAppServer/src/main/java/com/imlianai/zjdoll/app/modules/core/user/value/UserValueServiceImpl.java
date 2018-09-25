package com.imlianai.zjdoll.app.modules.core.user.value;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserValue;
import com.imlianai.rpc.support.manager.cache.CacheManager;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.record.service.FirsttimeMsgPushRecordService;
import com.imlianai.zjdoll.app.modules.support.signin.contants.UserSigninContants;
import com.imlianai.zjdoll.app.modules.support.userdoll.enm.FirsttimePushType;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;

@Service
public class UserValueServiceImpl implements UserValueService {

	@Resource
	private UserValueDAO userValueDAO;
	@Resource
	private CacheManager cacheManager;
	@Resource
	private UserDollService userDollService;
	@Resource
	private FirsttimeMsgPushRecordService firsttimeMsgPushRecordService;
	@Resource
	private MsgService msgService;

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
		int result = userValueDAO.addDollNum(uid, dollNum);
		List<FirsttimePushType> list = FirsttimePushType.getFirsttimePushTypeArry(1);
		if(!StringUtil.isNullOrEmpty(list)) {
			int currNum = userDollService.getUserDollSizeByParams(uid, 0);
			for(FirsttimePushType pushType : list) {
				if(currNum >= pushType.getNum() 
						&& !firsttimeMsgPushRecordService.isPush(uid, pushType.getType(), pushType.getNum())) {
					// 消息推送
					Msg msg = new Msg(uid , MsgType.NOTICE_SYS.type,
							"温馨提示：回收背包-寄存中的娃娃可获得钻石，钻石可到兑换商城换取iPhone8哟！");
					msgService.sendMsg(msg);
					// 保存首次推送记录
					firsttimeMsgPushRecordService.saveFirsttimeMsgPushRecord(uid, pushType.getType(), pushType.getNum());
					break;
				}
			}
		}
		return result;
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
