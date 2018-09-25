package com.imlianai.dollpub.app.modules.core.doll.pattern.service;

import java.util.TimerTask;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.core.doll.pattern.dao.DollBusPatternDao;
import com.imlianai.dollpub.app.modules.core.doll.utils.DollUtil;
import com.imlianai.dollpub.app.modules.core.doll.utils.qiyiguo.QiyiguoDeviceSetting;
import com.imlianai.dollpub.app.modules.core.doll.utils.qiyiguo.QiyiguoMachine.MachineMode;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.doll.DollOptRecord;
import com.imlianai.dollpub.domain.doll.DollBus.DollBusCompany;
import com.imlianai.dollpub.domain.doll.pattern.BusPatternStat;
import com.imlianai.dollpub.domain.msg.MsgRoom;
import com.imlianai.dollpub.domain.msg.MsgRoomJump;
import com.imlianai.dollpub.domain.msg.MsgRoomType;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.rpc.support.utils.SysTimerHandle;

@Service
public class DollBusPatternServiceImpl implements DollBusPatternService {
	protected final BaseLogger logger = BaseLogger.getLogger(this.getClass());
	@Resource
	DollBusPatternDao dollBusPatternDao;
	@Resource
	MsgService msgService;
	private static final int strongLimit = 3;

	@Override
	public void handleCatchSuccess(DollBus bus, DollOptRecord record) {
		// 强着力次数
		int strong = bus.getStrong();
		if (strong > 0) {
			resetWeak(bus, record);
			dollBusPatternDao.resetStatRound(bus.getBusId());
		}
	}

	@Override
	public void handleStartPlay(DollBus bus, DollOptRecord record) {
		try {
			long uid = record.getUid();
			int busId = bus.getBusId();
			// 强着力次数
			int strong = bus.getStrong();
			// 当前累计次数
			BusPatternStat stat = getStat(busId);

			boolean startNewRound = false;
			if (stat.getCurrent() > strong
					&& stat.getCurrent() < (strong + strongLimit)) {// 满足出强力抓
				dealStrong(bus, record, stat);
			} else {
				MsgRoom msgRoom2 = new MsgRoom(bus,
						MsgRoomType.NORMAL_MSG.type, "当前娃娃机正处于弱爪力！！！！来氪金");
				msgService.sendMsgRoom(msgRoom2);
			}
			if (stat.getCurrent() >= (strong + strongLimit)) {
				resetWeak(bus, record);
				startNewRound = true;
			}
			dollBusPatternDao.updateStat(busId, startNewRound ? 1 : 0);
			final String deviceId = bus.getDeviceId();
			final DollBus finalBus=bus;
			SysTimerHandle.execute(new TimerTask() {
				@Override
				public void run() {
					try {
						QiyiguoDeviceSetting setting = DollUtil
								.getDeviceSetting(deviceId);
						MsgRoomJump msgRoom = new MsgRoomJump(finalBus, MsgRoomType.DANMU.type,
								"当前娃娃机 概率为"+setting.getWinning_probability()+" 捉力模式为："+MachineMode.getMachineMode(setting.getHolding_mode()).des );
						msgRoom.setJumpUrl("https://www.taobao.com/");
						msgService.sendMsgRoom(msgRoom);
					} catch (Exception e) {
						PrintException.printException(logger, e);
					}
				}
			}, 0);
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
	}

	/**
	 * 发动强抓力
	 * 
	 * @param bus
	 * @param record
	 */
	private void dealStrong(DollBus bus, DollOptRecord record,
			BusPatternStat stat) {
		if (bus != null
				&& bus.getDeviceCompany() == DollBusCompany.QIYIGUO.type
				&& !StringUtil.isNullOrEmpty(bus.getDeviceId())) {
			final String deviceId = bus.getDeviceId();
			final DollBus finalBus=bus;
			SysTimerHandle.execute(new TimerTask() {
				@Override
				public void run() {
					try {
						long ts = System.currentTimeMillis();
						logger.info("dealStrong 开始设置强抓力" + deviceId);
						DollUtil.setProbability(deviceId, 20);
						DollUtil.setMode(deviceId, MachineMode.ALL_STRONG.type);
						logger.info("dealStrong 完成设置强抓力" + deviceId + " 持续时间："
								+ (System.currentTimeMillis() - ts));
						QiyiguoDeviceSetting setting = DollUtil
								.getDeviceSetting(deviceId);
						MsgRoomJump msgRoom = new MsgRoomJump(finalBus, MsgRoomType.DANMU.type,
								"设置强力 完成 当前娃娃机 概率为"+setting.getWinning_probability()+" 捉力模式为："+MachineMode.getMachineMode(setting.getHolding_mode()).des );
						msgRoom.setJumpUrl("https://www.taobao.com/");
						msgService.sendMsgRoom(msgRoom);
					} catch (Exception e) {
						PrintException.printException(logger, e);
					}
				}
			}, 0);
			// 记录强爪
			dollBusPatternDao.addStrongRecord(record.getOptId(),
					record.getLogId(), record.getUid(), bus.getBusId(),
					bus.getDeviceId(), stat.getRound(), stat.getCurrent(),
					stat.getStrong());
			MsgRoomJump msgRoom = new MsgRoomJump(bus, MsgRoomType.DANMU.type,
					"当前娃娃机触发了强爪力！！！！各单位注意");
			msgRoom.setJumpUrl("https://www.taobao.com/");
			msgService.sendMsgRoom(msgRoom);
			MsgRoom msgRoom2 = new MsgRoom(bus, MsgRoomType.NORMAL_MSG.type,
					"当前娃娃机触发了强爪力！！！！各单位注意");
			msgService.sendMsgRoom(msgRoom2);
		}
	}

	private void resetWeak(DollBus bus, DollOptRecord record) {
		final String deviceId = bus.getDeviceId();
		final DollBus finalBus=bus;
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					long ts = System.currentTimeMillis();
					logger.info("resetWeak 开始恢复弱抓力" + deviceId);
					DollUtil.setMode(deviceId, MachineMode.ALL_WEAK.type);
					logger.info("resetWeak 完成恢复弱抓力" + deviceId + " 持续时间："
							+ (System.currentTimeMillis() - ts));
					QiyiguoDeviceSetting setting = DollUtil
							.getDeviceSetting(deviceId);
					MsgRoomJump msgRoom = new MsgRoomJump(finalBus, MsgRoomType.DANMU.type,
							"恢复弱爪力 完成 当前娃娃机 概率为"+setting.getWinning_probability()+" 捉力模式为："+MachineMode.getMachineMode(setting.getHolding_mode()).des );
					msgRoom.setJumpUrl("https://www.taobao.com/");
					msgService.sendMsgRoom(msgRoom);
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
		MsgRoomJump msgRoom = new MsgRoomJump(bus, MsgRoomType.DANMU.type,
				"当前娃娃机恢复弱爪力！！！！弃坑了");
		msgRoom.setJumpUrl("http://mall.loveq.cn/");
		msgService.sendMsgRoom(msgRoom);
		MsgRoom msgRoom2 = new MsgRoom(bus, MsgRoomType.NORMAL_MSG.type,
				"当前娃娃机恢复弱爪力！！！！弃坑了");
		msgService.sendMsgRoom(msgRoom2);
	}

	@Override
	public BusPatternStat getStat(int busId) {
		BusPatternStat stat = dollBusPatternDao.getStat(busId);
		if (stat == null) {
			dollBusPatternDao.initStat(busId);
			stat = new BusPatternStat(busId);
		}
		return stat;
	}

}
