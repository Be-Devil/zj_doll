package com.imlianai.zjdoll.app.pvd;

import java.util.List;
import java.util.TimerTask;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.SysTimerHandle;
import com.imlianai.zjdoll.app.iface.IAppCoreRemoteService;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollListService;
import com.imlianai.zjdoll.app.modules.core.user.attribute.UserAttributeService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.security.SecurityManager;
import com.imlianai.zjdoll.app.modules.support.version.service.VersionService;
import com.imlianai.zjdoll.domain.doll.DollBus;

@Service(interfaceClass = IAppCoreRemoteService.class)
public class AppCoreRemoteServiceImpl implements IAppCoreRemoteService {

	BaseLogger logger = BaseLogger.getLogger(this.getClass());
	@Resource
	private VersionService versionService;
	@Resource
	private UserService userService;
	@Resource
	private SecurityManager securityManager;

	@Resource
	private DollListService dollListService;

	@Resource
	private DollBusService dollBusService;
	
	@Resource
	private UserAttributeService userAttributeService;

	@Override
	public boolean checkLogin(long uid, String loginKey) {
		return securityManager.isLoginSecurityCodeValid(uid, loginKey);
	}

	@Override
	public List<DollBus> busGetDollBus() {
		return dollListService.getBusList(null);
	}
	@Override
	public List<DollBus> busGetDeviceList(){
		return dollBusService.getCurrentDevice();
	}
	
	@Override
	public void userHandleUserBan(final List<Long> uidList,final String imei,
			final String reason,final String time) {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					userService.handleUserBan(uidList, imei, reason, time);
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}

	@Override
	public void userRemoveUserBan(final List<Long> uidList,final String imei) {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					userService.removeUserBan(uidList, imei);
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}

	@Override
	public void userRemoveName(final long uid) {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					userService.removeUserName(uid);
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}

	@Override
	public void updateUserWithdraw(long uid, boolean isBan) {
		userAttributeService.updateWithdraw(uid, isBan?1:0);
	}
}
