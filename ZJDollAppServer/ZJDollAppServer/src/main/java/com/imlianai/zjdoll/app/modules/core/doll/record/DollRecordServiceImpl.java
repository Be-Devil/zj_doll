package com.imlianai.zjdoll.app.modules.core.doll.record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.constants.DollCacheConst;
import com.imlianai.zjdoll.domain.doll.DollAppealRecord;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.zjdoll.domain.doll.DollRewardRecord;
import com.imlianai.zjdoll.domain.doll.DollSuccessOptRecord;
import com.imlianai.zjdoll.domain.doll.WatchRecord;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgRoom;
import com.imlianai.zjdoll.domain.msg.MsgRoomJump;
import com.imlianai.zjdoll.domain.msg.MsgRoomType;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserCommon;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusDao;
import com.imlianai.zjdoll.app.modules.core.doll.enm.VirtualGoodType;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollListService;
import com.imlianai.zjdoll.app.modules.core.doll.vo.UserAbandonSummry;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.core.user.value.UserValueDAO;
import com.imlianai.zjdoll.app.modules.core.user.value.UserValueService;
import com.imlianai.zjdoll.app.modules.publics.mail.TestSendDome;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.event.lanternFestival20180227.service.EventLanternFestivalService;
import com.imlianai.zjdoll.app.modules.support.event.lanternFestival20180227.utils.LanternFestivalUtils;
import com.imlianai.zjdoll.app.modules.support.exchange.service.DollComposeService;
import com.imlianai.zjdoll.app.modules.support.exchange.service.DollExchangeSevice;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;

@Service
public class DollRecordServiceImpl implements DollRecordService {
	
	private static List<Integer> fiveTimesExDollIds = LanternFestivalUtils.getDollIds(0, 5);
	private static List<Integer> eightTimesExDollIds = LanternFestivalUtils.getDollIds(0, 8);
	private static List<Integer> reDollIds = LanternFestivalUtils.getDollIds(1, 0);

	@Resource
	DollRecordDao dollRecordDao;
	@Resource
	UserDollService userDollService;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	UserValueDAO userValueDAO;
	@Resource
	UserService userService;
	@Resource
	MsgService msgService;
	@Resource
	DollBusDao dollBusDao;
	@Resource
	DollListService dollListService;
	@Resource
	TestSendDome testSendDome;
	@Resource
	EventLanternFestivalService eventLanternFestivalService;
	@Resource
	DollExchangeSevice dollExchangeSevice;
	@Resource
	TradeService tradeService;
	@Resource
	DollComposeService dollComposeService;
	@Resource
	UserValueService userValueService;
	
	private static final BaseLogger LOG = BaseLogger
			.getLogger(DollRecordServiceImpl.class);

	@Override
	public int saveDollOptRecord(DollOptRecord record) {
		return dollRecordDao.saveDollOptRecord(record);
	}

	@Override
	public int updateDollOptRecord(DollOptRecord record) {
		int result = dollRecordDao.updateDollOptRecord(record);
		if (result > 0 && record.getResult() == 1) {
			DollInfo doll = dollInfoService.getDollInfo(record.getDollId());
			
			//int status = getSpecDollStatus(record.getDollId());
			int status = userDollService.getUserDollDefaultStatus(doll);
			UserDoll userDoll = new UserDoll();
			userDoll.setDollId(record.getDollId());
			userDoll.setUid(record.getUid());
			userDoll.setOptId(record.getOptId());
			userDoll.setStatus(status); 
			userDoll.setGoodsType(doll.getGoodsType());
			long uDollId = userDollService.saveUserDoll(userDoll); // 保存用户抓到的娃娃
			/*if(status > 0) {
				eventLanternFestivalService.handleCatchDoll(status, uDollId, userDoll.getUid(), doll);
			}*/
			
			if(doll.getGoodsType() == 1) { // 虚拟
				userDollService.handleVirtualDoll(doll, status, uDollId, record.getUid());
			}
			
			dollRecordDao.saveDollSucOptRecord(record.getOptId(),
					record.getUid(), record.getDollId(), record.getBusId()); // 保存操作成功记录
			//userValueService.addDollNum(record.getUid(), 1);
			// 保存用户娃娃个数周统计、月统计
			int weekCode = Integer.parseInt(DateUtils.dateToString(DateUtils.getWeekFirstTime(), "yyyyMMdd"));
			int monthCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMM"));
			userDollService.saveOrUpdateUserDollWeekCount(record.getUid(), weekCode, 1);
			userDollService.saveOrUpdateUserDollMonthCount(record.getUid(), monthCode, 1);
			// 消息推送
			MsgNotice msgsuccess = new MsgNotice(record.getUid(), MsgType.NOTICE_SYS.type,
					"恭喜小主抓到了 "+doll.getName()+"，棒棒哒~~");
			msgsuccess.setJumpDoll(uDollId);
			msgService.sendMsg(msgsuccess);
		}
		return result;
	}

	@Override
	public int getSpecDollStatus(int dollId) {
		int status = 0;
		if((!StringUtil.isNullOrEmpty(fiveTimesExDollIds) && fiveTimesExDollIds.contains(dollId))
				|| (!StringUtil.isNullOrEmpty(eightTimesExDollIds) && eightTimesExDollIds.contains(dollId))) {
			status = 3;
		}
		if(!StringUtil.isNullOrEmpty(reDollIds) && reDollIds.contains(dollId)) {
			status = 5;
		}
		return status;
	}

	@Override
	public List<DollOptRecord> getOptRecords(long lastId, Long uid, int pageSize) {
		return dollRecordDao.getOptRecords(lastId, uid, pageSize);
	}

	@Override
	public DollOptRecord getOptRecord(long optId) {
		return dollRecordDao.getOptRecord(optId);
	}

	@Override
	public int saveDollAppealRecord(long optId, Long uid, String reason) {
		return dollRecordDao.saveDollAppealRecord(optId, uid, reason);
	}

	@Override
	public List<DollRewardRecord> getRecentlyDollList(int num) {
		return convertSuccessRecord(dollRecordDao.getSuccOptRecord(num));
	}

	@Override
	public int addWatchRecord(long uid, int busId) {
		return dollRecordDao.addWatchRecord(uid, busId);
	}

	@Override
	public DollAppealRecord getAppealRecord(long optId) {
		return dollRecordDao.getAppealRecord(optId);
	}

	@Override
	public DollOptRecord getOptRecordByLogId(long logId, int deviceCompany) {
		return dollRecordDao.getOptRecordByLogId(logId, deviceCompany);
	}

	@Override
	public List<DollRewardRecord> getBusRewardRecords(int busId, int page,
			int pageSize) {
		return convertSuccessRecord(dollRecordDao.getBusRewardRecords(busId,
				page, pageSize));
	}

	/**
	 * 组装成功捉取记录
	 * 
	 * @param list
	 * @return
	 */
	private List<DollRewardRecord> convertSuccessRecord(
			List<DollSuccessOptRecord> list) {
		List<DollRewardRecord> records = new ArrayList<DollRewardRecord>();
		if (!StringUtil.isNullOrEmpty(list)) {
			List<Long> uids = new ArrayList<Long>();
			List<Integer> dollIds = new ArrayList<Integer>();
			for (DollSuccessOptRecord succOptRecord : list) {
				uids.add(succOptRecord.getUid());
				dollIds.add(succOptRecord.getDollId());
			}
			Map<Long, UserGeneral> userGeneralMap = userService
					.getUserGeneralMap(uids); // 用户信息
			Map<Integer, DollInfo> dollInfoMap = dollInfoService
					.getDollInfos(dollIds); // 娃娃信息
			for (DollSuccessOptRecord succOptRecord : list) {
				UserGeneral userGeneral = userGeneralMap.get(succOptRecord
						.getUid());
				DollInfo dollInfo = dollInfoMap.get(succOptRecord.getDollId());
				if (userGeneral != null && dollInfo != null) {
					DollRewardRecord rewardRecord = new DollRewardRecord(
							succOptRecord.getDollId(), succOptRecord.getUid(),
							userGeneral.getName(), userGeneral.getHead(),
							dollInfo.getDollId(), dollInfo.getName(),
							succOptRecord.getCreateTime(),succOptRecord.getBusId());
					records.add(rewardRecord);
				}
			}
		}
		return records;
	}

	@Override
	@CacheWrite(key=DollCacheConst.DOLL_BUS_WATCHERS,validTime=10)
	public List<UserCommon> getWatchList(int busId, int page, int pageSize) {
		List<WatchRecord> list = dollRecordDao.getWatchList(busId, page,
				pageSize);
		List<UserCommon> resList=new ArrayList<UserCommon>();
		if (!StringUtil.isNullOrEmpty(list)) {
			List<Long> uids = new ArrayList<Long>();
			for (WatchRecord watchRecord : list) {
				uids.add(watchRecord.getUid());
			}
			Map<Long, UserGeneral> userMap = userService
					.getUserGeneralMap(uids);
			Map<Long, UserBase> userBaseMap =userService.getUserBaseMap(uids);
			for (WatchRecord watchRecord : list) {
				UserGeneral user = userMap.get(watchRecord.getUid());
				UserBase userBase = userBaseMap.get(watchRecord.getUid());
				if(user!=null){
					resList.add(new UserCommon(user, userBase));
				}
			}
		}
		return resList;
	}

	@Override
	public int getWatchBus(long uid) {
		return dollRecordDao.getWatchBus(uid);
	}

	@Override
	public int deleteWatchRecord(long uid, int busId) {
		return dollRecordDao.deleteWatchRecord(uid, busId);
	}

	@Override
	public void addDailyPlayRecord(long uid, int isSuccess) {
		dollRecordDao.addDailyPlayRecord(uid, isSuccess);
	}

	@Override
	public int getDailyPlayCount(long uid) {
		return dollRecordDao.getDailyPlayCount(uid);
	}

	@Override
	public int updateVideoRecord(long optId, String url1, String url2, int state) {
		return dollRecordDao.updateVideoRecord(optId, url1, url2, state);
	}

	@Override
	public void addUserPlaySummry(long uid, long optId, int isSuccess) {
		dollRecordDao.addUserPlaySummry(uid, optId, isSuccess);
	}

	@Override
	public int getUserTotalPlayCount(long uid) {
		return dollRecordDao.getUserTotalPlayCount(uid);
	}
	@Override
	public UserAbandonSummry getUserAbandonSummry(long uid){
		return dollRecordDao.getUserAbandonSummry(uid);
	}

	@Override
	public void addUserAbandonSummry(long uid, long optId) {
		dollRecordDao.addUserAbandonSummry(uid, optId);
	}

	@Override
	public void addUserContinueTime(long uid, boolean isInit) {
		dollRecordDao.addUserContinueTime(uid, isInit);
	}

	@Override
	public int getUserContinueTime(long uid) {
		return dollRecordDao.getUserContinueTime(uid);
	}

	@Override
	public void updateUserAbandonSummry(long uid, int lastTotalNum) {
		dollRecordDao.updateUserAbandonSummry(uid, lastTotalNum);
	}

	@Override
	public int updateOptHasHandle(long optId) {
		return dollRecordDao.updateOptHasHandle(optId);
	}

	@Override
	public void handleUserSuccessError(long uid, int busId, long optId) {
		if (AppUtils.isTestEnv()) {
			return ;
		}
		try {
			int count=dollRecordDao.addAndGetSuccess(busId,15);
			
			if (count>=3) {
				DollBus bus=dollListService.getDollBus(busId, false);
				dollBusDao.updateBusValid(busId, 0, "连续抓中太多，暂时下架");
				if (AppUtils.isTestEnv()) {
					String msgText="<font color='#ffe003'>自动下架通告: </font><font color='#ffffff'>连续抓中太多，暂时下架</font>";
					MsgRoom msgRoom = new MsgRoomJump(bus,
							MsgRoomType.NORMAL_MSG.type, msgText);
					msgRoom.setSave(true);
					msgService.sendMsgRoom(msgRoom);
				}else{
					testSendDome.doSendDoll("连续捉中太多", bus.getBusId()+"号机,"+bus.getNickName()+","+bus.getName());
				}
				return ;
			}
			count=dollRecordDao.addAndGetSuccess(busId,30);
			if (count>=5) {
				DollBus bus=dollListService.getDollBus(busId, false);
				dollBusDao.updateBusValid(busId, 0, "连续抓中太多，暂时下架");
				if (AppUtils.isTestEnv()) {
					String msgText="<font color='#ffe003'>自动下架通告: </font><font color='#ffffff'>连续抓中太多，暂时下架</font>";
					MsgRoom msgRoom = new MsgRoomJump(bus,
							MsgRoomType.NORMAL_MSG.type, msgText);
					msgRoom.setSave(true);
					msgService.sendMsgRoom(msgRoom);
				}else{
					testSendDome.doSendDoll("连续捉中太多", bus.getBusId()+"号机,"+bus.getNickName()+","+bus.getName());
				}
			}
		} catch (Exception e) {
			PrintException.printException(LOG, e);
		}
	}

	@Override
	public void updateOptRecordStrong(long optId, int isStrong) {
		dollRecordDao.updateOptRecordStrong(optId, isStrong);
	}
}
