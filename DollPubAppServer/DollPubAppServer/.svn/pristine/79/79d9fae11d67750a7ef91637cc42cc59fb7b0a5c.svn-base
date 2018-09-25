package com.imlianai.dollpub.app.modules.core.doll.record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.imlianai.dollpub.machine.iface.IMachineRemoteService;
import com.imlianai.dollpub.machine.iface.domain.MachineOptRecord;
import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.core.doll.info.DollInfoService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.core.user.value.UserValueDAO;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.dollpub.constants.DollCacheConst;
import com.imlianai.dollpub.constants.JumpTarget;
import com.imlianai.dollpub.domain.doll.DollAppealRecord;
import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.dollpub.domain.doll.DollOptRecord;
import com.imlianai.dollpub.domain.doll.DollRewardRecord;
import com.imlianai.dollpub.domain.doll.DollSuccessOptRecord;
import com.imlianai.dollpub.domain.doll.WatchRecord;
import com.imlianai.dollpub.domain.doll.user.UserDoll;
import com.imlianai.dollpub.domain.msg.MsgNotice;
import com.imlianai.dollpub.domain.msg.MsgType;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserCommon;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class DollRecordServiceImpl implements DollRecordService {

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

	@Reference
	IMachineRemoteService iMachineRemoteService;

	private static final BaseLogger LOG = BaseLogger
			.getLogger(DollRecordServiceImpl.class);

	@Override
	public int saveDollOptRecord(DollOptRecord record) {
		return dollRecordDao.saveDollOptRecord(record);
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
	public int saveDollAppealRecord(int customerId,int groupId,long optId, Long uid, String reason) {
		return dollRecordDao.saveDollAppealRecord(customerId,groupId,optId, uid, reason);
	}

	@Override
	public int saveDollAppealRecord(DollAppealRecord dollAppealRecord) {
//		//查结果
//		MachineOptRecord machineOptRecord = iMachineRemoteService.queryMachineOptResult(dollAppealRecord.getOptId());
//		dollAppealRecord.setResult(machineOptRecord.getResult());
		return dollRecordDao.saveDollAppealRecord(dollAppealRecord);
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
	public DollOptRecord getOptRecordByLogId(long logId) {
		return dollRecordDao.getOptRecordByLogId(logId);
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
							succOptRecord.getCreateTime());
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
	public void deleteWatchRecord(long uid, int busId) {
		dollRecordDao.deleteWatchRecord(uid, busId);
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
	public List<DollRewardRecord> getRecentlyDollList(int customerId, int num) {
		return convertSuccessRecord(dollRecordDao.getSuccOptRecord(customerId, num));
	}

}
