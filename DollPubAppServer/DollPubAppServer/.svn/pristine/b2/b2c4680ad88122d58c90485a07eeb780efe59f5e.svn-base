package com.imlianai.dollpub.app.pvd;

import java.util.TimerTask;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.imlianai.dollpub.app.iface.IAppTaskRemoteService;
import com.imlianai.dollpub.app.modules.core.api.schedule.DollBusStatusTask;
import com.imlianai.dollpub.app.modules.support.robot.service.DollRobotService;
import com.imlianai.dollpub.app.modules.support.shipping.service.ShippingService;
import com.imlianai.dollpub.app.modules.support.withdraw.service.WithdrawService;
import com.imlianai.dollpub.app.modules.support.xxrecharge.service.XxingRechargeService;
import com.imlianai.dollpub.app.schedule.*;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.SysTimerHandle;

/**
 * Created by sam on 17-7-25.
 */
@Service(interfaceClass = IAppTaskRemoteService.class)
public class AppTaskRemoteServiceImpl implements IAppTaskRemoteService {
	private final BaseLogger logger = BaseLogger.getLogger(this.getClass());

	@Resource
	DollBusTask dollBusTask;
	@Resource
	private DollExchangeTask dollExchangeTask;
	@Resource
	private HandleWeekRewardTask handleWeekRewardTask;

	@Resource
	private DollBusStatusTask busStatusTask;

	@Resource
	private MachineTask machineTask;

	@Resource
	private PushCoinBusTask pushCoinBusTask;
	
	@Resource
	private ShippingService shippingService;
	@Resource
	private WithdrawService withdrawService;

	@Resource
	private DollRobotService dollRobotService;
	
	@Resource
	private XxingRechargeService xxingRechargeService;

	@Resource
	private DiceTask diceTask;

	@Override
	public void testTaskMsg() {
		logger.info("testTaskMsg now:" + DateUtils.getNowTime());
	}
	
	@Override
	public void busHandleOptInvalid() {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}

	@Override
	public void handleExchange() {
		logger.info("handleExchange now:" + DateUtils.getNowTime());
		dollExchangeTask.handleExchange();
	}

	@Override
	public void handleWeekReward() {
		logger.info("handleWeekReward now:" + DateUtils.getNowTime());
		handleWeekRewardTask.handleReward();
	}

	@Override
	public void dollBusHandleInvalid() {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					busStatusTask.invalidBusMaxPlayTimeOpt();
					busStatusTask.invalidBusKeepTimeOpt();
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}

	@Override
	public void shippingExpireHandleInvalid() {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					shippingService.handleShippingInvalid();
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}
	
	@Override
	public void handleUpdateMachineSetting() {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					machineTask.handleUpdataMachineSetting();
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}

	@Override
	public void handlePushCoinTask() {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				logger.info("handlePushCoinTask now:" + DateUtils.getNowTime());
				try {
					pushCoinBusTask.handleInvalidPushCoinBusOpt();
					pushCoinBusTask.handleQueryPushCoinResult();
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}
	
	@Override
	public void withdrawDoPayWechatMoneySchedule() {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				logger.info("withdrawDoPayWechatMoneySchedule now:" + DateUtils.getNowTime());
				try {
					withdrawService.doPayWechatMoneySchedule();
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}
	
	@Override
	public void withdrawDoPayWechatMoneyResultCheckSchedule() {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				logger.info("doPayWechatMoneyResultCheckSchedule now:" + DateUtils.getNowTime());
				try {
					withdrawService.doPayWechatMoneyResultCheckSchedule();
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}
	@Override
	public void dollRobotandleShopRobot() {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				logger.info("handleShopRobot now:" + DateUtils.getNowTime());
				try {
					dollRobotService.handleShopRobot();
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}

	@Override
	public void xinxingRechargeQuery() {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					logger.info("xinxingRechargeQuery now:" + DateUtils.getNowTime());
					xxingRechargeService.xinxingRechargeQuery();
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}

	@Override
	public void DiceHandleInvalid() {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					logger.info("handleInvalidDiceBusOpt now:" + DateUtils.getNowTime());
					diceTask.handleInvalidDiceBusOpt();
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}

}
