package com.imlianai.zjdoll.app.modules.publics.rpctest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.constants.CacheConst;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.cache.CacheManager;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.WebCmd;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.event.newyearRecharge20180207.service.EventNewyearRecharge20180207ServiceImpl;

@Component("test.webTest")
public class CmdWebTest extends WebCmd {
	
	@Resource
	UserService userService;
	@Resource
	private	CacheManager cacheManager;
	@Resource
	MsgService msgService;
	@ApiHandle
	public BaseRespVO banUid(long uid,String time) {
		List<Long> uidList=new ArrayList<Long>();
		uidList.add(uid);
		if (StringUtil.isNullOrEmpty(time)) {
			time="2050-01-01 00:00:00";
		}
		userService.handleUserBan(uidList, null, "客户端封号测试", time);
		return new BaseRespVO();
	}
	
	@ApiHandle
	public BaseRespVO banImei(long uid,String imei,String time) {
		List<Long> uidList=new ArrayList<Long>();
		uidList.add(uid);
		if (StringUtil.isNullOrEmpty(time)) {
			time="2050-01-01 00:00:00";
		}
		userService.handleUserBan(uidList, imei, "客户端封号测试", time);
		return new BaseRespVO();
	}
	
	@ApiHandle
	public BaseRespVO unbanUid(long uid) {
		List<Long> uidList=new ArrayList<Long>();
		uidList.add(uid);
		userService.removeUserBan(uidList, null);
		return new BaseRespVO();
	}
	
	@ApiHandle
	public BaseRespVO unbanImei(String imei) {
		userService.removeUserBan(null, imei);
		return new BaseRespVO();
	}
	
	@ApiHandle
	public BaseRespVO cleanUserCache(long uid) {
		cacheManager.delCache(CacheConst.USER_GENERAL_CACHE + ":"+uid);
		cacheManager.delCache(CacheConst.USER_BASE_CACHE + ":"+uid);
		cacheManager.delCache(CacheConst.USER_VALUE_CACHE + ":"+uid);
		return new BaseRespVO();
	}
	
	@ApiHandle
	public BaseRespVO cleanCache(String key) {
		cacheManager.delCache(key);
		return new BaseRespVO();
	}
	
	@ApiHandle
	public BaseRespVO cleanCacheFuzz(String key) {
		cacheManager.delCacheFuzz(key);
		return new BaseRespVO();
	}
	
	@ApiHandle
	public BaseRespVO sendPushMsg(long uid) {
		String bodyString = "#春暖花开，充值送礼# 小主福气值为" + 0
				+ "，暂列贵人榜第100名。";
		MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type,
				bodyString);
		msg.setPushMsg(bodyString);
		msg.setJumpWeb(EventNewyearRecharge20180207ServiceImpl.EVENT_URL + "?uid=" + uid);
		msg.setTargetTitle("春暖花开，充值送礼");
		msg.setTargetName("春暖花开，充值送礼");
		msgService.pushUMengMsg(msg);
		return new BaseRespVO();
	}
	
	@ApiHandle
	public BaseRespVO sendJumpCharge(long uid) {
		String bodyString = "#春暖花开，充值送礼# 跳转充值周卡";
		MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type,
				bodyString);
		msg.setPushMsg(bodyString);
		msg.setJumpCharge(2004);
		msgService.pushUMengMsg(msg);
		return new BaseRespVO();
	}
}
