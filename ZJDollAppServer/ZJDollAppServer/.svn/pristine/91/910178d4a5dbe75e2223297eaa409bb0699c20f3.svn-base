package com.imlianai.zjdoll.app.modules.support.busowner.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.busowner.BusOwner;
import com.imlianai.zjdoll.domain.busowner.BusOwnerBusIncome;
import com.imlianai.zjdoll.domain.busowner.BusOwnerRes;
import com.imlianai.zjdoll.domain.busowner.BusOwnerUserIndex;
import com.imlianai.zjdoll.domain.busowner.BusOwnerUserIndexRecord;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.domain.webp.Webp;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.PropertUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollListService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.UserWechat;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.service.WechatService;
import com.imlianai.zjdoll.app.modules.publics.utils.ArithmeticUtils;
import com.imlianai.zjdoll.app.modules.support.busowner.constants.BusOwnerConstants;
import com.imlianai.zjdoll.app.modules.support.busowner.dao.BusOwnerDao;
import com.imlianai.zjdoll.app.modules.support.busowner.enm.RewardRatioEnm;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.AddShareValueReqVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.BusOwnerInfo;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetIncomeInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.GetTimeInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.PlayerRankingItem;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.RatioInfo;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.ShareRecordRes;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.ShopItem;
import com.imlianai.zjdoll.app.modules.support.common.CommConstants;
import com.imlianai.zjdoll.app.modules.support.webp.service.WebpService;

@Service
public class BusOwnerServiceImpl implements BusOwnerService {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(BusOwnerServiceImpl.class);

	private Map<Integer, BusOwner> busOwnerMap = null;
	
	private static Date refreshTime = new Date();
	
	@Resource
	BusOwnerDao busOwnerDao;
	@Resource
	UserService userService;
	@Resource
	WechatService wechatService;
	@Resource
	DollBusService dollBusService;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	WebpService webpService;
	@Resource
	DollListService dollListService;
	@Resource
	MsgService msgService;
	
	/**
	 * 获取萌主对象
	 * 
	 * @param dollId
	 * @return
	 */
	@Override
	public BusOwner getBusOwnerCache(int busId) {
		if (StringUtil.isNullOrEmpty(busOwnerMap) || DateUtils.secondBetween(refreshTime) >= 30) {
			initBusOwners();
		}
		if (!StringUtil.isNullOrEmpty(busOwnerMap)) {
			return busOwnerMap.get(busId);
		}
		return null;
	}

	@Override
	public void initBusOwners() {
		Map<String, Integer> codeMap = getDateCodeMap(-2, -1, -1, 0); // 获取开始、结束日期code
		int startCode = codeMap.get("startCode");
		int endCode = codeMap.get("endCode");
		List<BusOwner> busOwners = busOwnerDao.getBusOwners(startCode, endCode);
		if(!StringUtil.isNullOrEmpty(busOwners)) {
			busOwnerMap = new HashMap<Integer, BusOwner>();
			for(BusOwner busOwner : busOwners) {
				busOwnerMap.put(busOwner.getBusId(), busOwner);
			}
		}
		refreshTime = new Date();
	}

	private UserGeneral getBusOwnerInfo(int busId) {
		BusOwner busOwner = getBusOwnerCache(busId);
		if(busOwner != null) {
			return userService.getUserGeneral(busOwner.getUid());
		}
		return null;
	}

	@Override
	public void handleUserIndex(DollOptRecord record) {
		try {
			if(isMengDian(record.getBusId())) {
				Map<String, Integer> codeMap = getDateCodeMap(-1, 0, 0, 1); // 获取开始、结束日期code
				int startCode = codeMap.get("startCode");
				int endCode = codeMap.get("endCode");
				int increSuccCatTimes = record.getResult() == 1 ? 1 : 0;
				if(busOwnerDao.saveOrUpdateCatTimes(record.getBusId(), record.getUid(), 
						startCode, endCode, 1, increSuccCatTimes) > 0) { // 保存用户当前周期抓取次数
					BusOwnerUserIndex busOwnerUserIndex = busOwnerDao.getBusOwnerUserIndex(record.getBusId(), record.getUid(), 
							startCode, endCode); // 获取用户指数
					int increValue = getCatchDollIndex(busOwnerUserIndex.getCatTimes(), busOwnerUserIndex.getSucCatTimes(), record.getResult());
					BusOwnerUserIndexRecord userIndexRecord = new BusOwnerUserIndexRecord();
					userIndexRecord.setBusId(record.getBusId());
					userIndexRecord.setOptId(record.getOptId());
					userIndexRecord.setUid(record.getUid());
					userIndexRecord.setStartCode(startCode);
					userIndexRecord.setEndCode(endCode);
					userIndexRecord.setRemark("抓娃娃获得指数+" + increValue);
					userIndexRecord.setType(0);
					saveOrUpdateUserIndex(userIndexRecord, increValue);
				}
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}
	
	private void saveOrUpdateUserIndex(BusOwnerUserIndexRecord userIndexRecord, int increValue) {
		if(busOwnerDao.saveOrUpdateUserIndex(userIndexRecord.getBusId(), userIndexRecord.getUid(), userIndexRecord.getStartCode(), 
				userIndexRecord.getEndCode(), increValue) > 0) {
			busOwnerDao.saveUserIndexRecord(userIndexRecord, increValue);
		}
	}

	private static Map<String, Integer> getDateCodeMap(int dateNum1, int dateNum2, int dateNum3, int dateNum4) {
		Map<String, Integer> codeMap = new HashMap<String, Integer>();
		int startCode = 0;
		int endCode = 0;
		int currHour = Integer.parseInt(DateUtils.dateToString(new Date(), "HH"));
		if(currHour < 12) {
			startCode = Integer.parseInt(DateUtils.dateToString(DateUtils.addDate(new Date(), dateNum1), "yyyyMMdd"));
			endCode = Integer.parseInt(DateUtils.dateToString(DateUtils.addDate(new Date(), dateNum2), "yyyyMMdd"));
		} else {
			startCode = Integer.parseInt(DateUtils.dateToString(DateUtils.addDate(new Date(), dateNum3), "yyyyMMdd"));
			endCode = Integer.parseInt(DateUtils.dateToString(DateUtils.addDate(new Date(), dateNum4), "yyyyMMdd"));
		}
		codeMap.put("startCode", startCode);
		codeMap.put("endCode", endCode);
		return codeMap;
	}
	
	
	private static int getCatchDollIndex(int catTimes, int sucCatTimes, int isSuc) {
		int userIndex = 0;
		if(1 <= catTimes && catTimes <= 10) {
			userIndex = 50;
		} else if(11 <= catTimes && catTimes <= 20) {
			userIndex = 100;
		} else if(21 <= catTimes && catTimes <= 50) {
			userIndex = 120;
		} else if(51 <= catTimes && catTimes <= 100) {
			userIndex = 150;
		} else {
			userIndex = 200;
		}
		if(isSuc == 1) {
			if(1 <= sucCatTimes && sucCatTimes <= 3) {
				userIndex += 150;
			} else if(4 <= sucCatTimes && sucCatTimes <= 10) {
				userIndex += 180;
			} else if(11 <= sucCatTimes && sucCatTimes <= 20) {
				userIndex += 200;
			} else {
				userIndex += 300;
			}
		}
		return userIndex;
	}

	@Override
	public BaseRespVO addShareValue(AddShareValueReqVO reqVO) {
		int randomValue = 0;
		try {
			String unionId = reqVO.getUnionId();
			int busId = reqVO.getBusId();
			if(!isMengDian(busId)) {
				return new BaseRespVO(-1, false, "机子不是萌店~~");
			}
			Long uid = reqVO.getUid();
			UserGeneral userGeneral = userService.getUserGeneral(uid);
			if(userGeneral == null) {
				return new BaseRespVO(-1, false, "支持的好友不存在哦~~");
			}
			if(StringUtil.isNullOrEmpty(unionId)) {
				return new BaseRespVO(-1, false, "微信用户不存在哦~~");
			}
			Map<String, Integer> codeMap = getDateCodeMap(-1, 0, 0, 1); // 获取开始、结束日期code
			int startCode = codeMap.get("startCode");
			int endCode = codeMap.get("endCode");
			if(wechatService.getUserWechat(unionId) != null) {
				if(busOwnerDao.saveBusOwnerUserSupportRecord(busId, uid, unionId, startCode, endCode) > 0) {
					int sumValue = busOwnerDao.getCurrCycleValue(uid, startCode, endCode, busId);
					if(sumValue >= BusOwnerConstants.CYCLE_MAX_VALUE) {
						return new BaseRespVO(-1, false, "好友魅力已爆表，明天再来支持TA吧~~");
					} else {
						randomValue = new Random().nextInt(6) + 10;
						if(sumValue + randomValue > BusOwnerConstants.CYCLE_MAX_VALUE) {
							randomValue = BusOwnerConstants.CYCLE_MAX_VALUE - sumValue;
						}
						BusOwnerUserIndexRecord userIndexRecord = new BusOwnerUserIndexRecord();
						userIndexRecord.setBusId(busId);
						userIndexRecord.setUid(uid);
						userIndexRecord.setStartCode(startCode);
						userIndexRecord.setEndCode(endCode);
						userIndexRecord.setUnionId(unionId);
						userIndexRecord.setRemark("好友支持获得指数+" + randomValue);
						userIndexRecord.setType(1);
						saveOrUpdateUserIndex(userIndexRecord, randomValue);
						List<BusOwnerUserIndex> userIndexList = busOwnerDao.getBusOwnerUserIndexList(busId, startCode, endCode, 50);
						Map<Long, Integer> rankingMap = new HashMap<Long, Integer>(); // 排名map
						Map<Long, BusOwnerUserIndex> userIndexMap = new HashMap<Long, BusOwnerUserIndex>();
						if(!StringUtil.isNullOrEmpty(userIndexList)) {
							int ranking = 1;
							for(BusOwnerUserIndex userIndex : userIndexList) {
								rankingMap.put(userIndex.getUid(), ranking);
								userIndexMap.put(userIndex.getUid(), userIndex);
								ranking++;
							}
						}
						int selfRanking = rankingMap.get(uid) == null ? 0 : rankingMap.get(uid);
						BusOwnerUserIndex userIndex = busOwnerDao.getBusOwnerUserIndex(busId, uid, startCode, endCode);
     					StringBuilder msgStringSb = new StringBuilder("小主在" + busId + "号机获得" + randomValue + "分享值，机主指数为" + userIndex.getValue());
						if(selfRanking > 0) {
							msgStringSb.append("，暂列榜单第" + selfRanking + "名。");
						} else {
							msgStringSb.append("。");
						}
						/*Msg msg = new Msg(uid, MsgType.NOTICE_SYS.type, msgStringSb.toString());
						msgService.sendMsg(msg);*/
						MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type, msgStringSb.toString());
						UserBase userBase = userService.getUserBase(uid);
						msg.setJumpWeb(BusOwnerConstants.HOMEPAGE_H5_URL + "?uid=" + uid + "&loginKey=" + userBase.getLoginKey() + "&busId=" + busId);
						msg.setPushMsg(msgStringSb.toString());
						msg.setTargetTitle(BusOwnerConstants.HOMEPAGE_H5_TITLE);
						msg.setTargetName(BusOwnerConstants.HOMEPAGE_H5_TITLE);
						msgService.sendMsg(msg);
						return new BaseRespVO(200, true, "感谢您的支持！好友增加了" + randomValue +"分享值");
					}
				} else {
					return new BaseRespVO(-1, false, "好友分享值已爆表，明天再来支持TA吧~~");
				}
			} else {
				return new BaseRespVO(-1, false, "请先微信授权，再来支持TA吧~~");
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统异常，请稍后再试~");
		}
	}

	@Override
	public BusOwnerUserIndex getNewestBusOwner(int busId, int startCode, int endCode) {
		return busOwnerDao.getNewestBusOwner(busId, startCode, endCode);
	}

	@Override
	public int saveBusOwner(Long uid, int busId, int startCode, int endCode) {
		return busOwnerDao.saveBusOwner(uid, busId, startCode, endCode);
	}

	@Override
	public void handleBusIncome(int busId, int coin, long optId, String remark) {
		if(isMengDian(busId)) {
			Map<String, Integer> codeMap = getDateCodeMap(-1, 0, 0, 1); // 获取开始、结束日期code
			int startCode = codeMap.get("startCode");
			int endCode = codeMap.get("endCode");
			if(busOwnerDao.saveOrUpdateBusIncome(busId, startCode, endCode, coin) > 0) {
				busOwnerDao.saveBusIncomeRecord(busId, optId, coin, startCode, endCode, remark);
			}
		}
	}

	@Override
	public boolean isBusOwner(long uid) {
		Map<String, Integer> codeMap = getDateCodeMap(-2, -1, -1, 0); // 获取开始、结束日期code
		int startCode = codeMap.get("startCode");
		int endCode = codeMap.get("endCode");
		if(!StringUtil.isNullOrEmpty(busOwnerDao.getBusOwnersByUid(uid, startCode, endCode))) {
			return true;
		}
		return false;
	}

	@Override
	public BusOwnerBusIncome getBusIncome(int busId) {
		Map<String, Integer> codeMap = getDateCodeMap(-1, 0, 0, 1); // 获取开始、结束日期code
		int startCode = codeMap.get("startCode");
		int endCode = codeMap.get("endCode");
		return busOwnerDao.getBusIncome(busId, startCode, endCode);
	}

	@Override
	public String getBusOwnerRoomNumsByUid(Long uid) {
		Map<String, Integer> codeMap = getDateCodeMap(-2, -1, -1, 0); // 获取开始、结束日期code
		int startCode = codeMap.get("startCode");
		int endCode = codeMap.get("endCode");
		List<BusOwner> busOwners = busOwnerDao.getBusOwnersByUid(uid, startCode, endCode);
		StringBuilder roomNumsSb = new StringBuilder();
		List<Integer> mdBusIds = new ArrayList<Integer>(); // 萌店ID
		if(!StringUtil.isNullOrEmpty(busOwners)) {
			for(BusOwner busOwner : busOwners) {
				DollBus dollBus = dollBusService.getDollBus(busOwner.getBusId());
				/*if(dollBus.getBusType() == DollBus.DollBusType.MENGDIAN.type) {
					mdBusIds.add(dollBus.getBusId());
				}*/
				mdBusIds.add(dollBus.getBusId());
			}
		}
		if(!StringUtil.isNullOrEmpty(mdBusIds)) {
			final int SIZE = mdBusIds.size();
			for(int i = 0 ; i < SIZE; i++) {
				if(i == SIZE - 1) {
					roomNumsSb.append(mdBusIds.get(i) + "号机");
				} else {
					roomNumsSb.append(mdBusIds.get(i) + "号机、");
				}
			}
		}
		return roomNumsSb.toString();
	}

	@Override
	public List<BusOwner> getBusOwner(int startCode, int endCode) {
		return busOwnerDao.getBusOwners(startCode, endCode);
	}

	@Override
	public String getBusName(DollInfo info, DollBus dollBus) {
		String name = info.getName().length() > 10 ? info.getName().substring(0, 10) + "..." : info.getName(); // 房间名称
		if(StringUtil.isNullOrEmpty(dollBus.getName())) {
			UserGeneral userGeneral = getBusOwnerInfo(dollBus.getBusId());
			if(dollBus.getBusType() == DollBus.DollBusType.MENGDIAN.type && userGeneral != null) {
				name = userGeneral.getName().length() > 7 ? userGeneral.getName().substring(0, 6) + "...の萌店" 
						: userGeneral.getName() + "の萌店";
			}
		} else {
			name = dollBus.getName().length() > 10 ? dollBus.getName().substring(0, 10) + "..." 
					: dollBus.getName();
		}
		return name;
	}
	
	@Override
	public String getNotHandledBusName(DollInfo info, DollBus dollBus) {
		String name = info.getName(); // 房间名称
		LOG.info("getNotHandledBusName:" + dollBus.getName());
		if(StringUtil.isNullOrEmpty(dollBus.getName())) {
			UserGeneral userGeneral = getBusOwnerInfo(dollBus.getBusId());
			if(dollBus.getBusType() == DollBus.DollBusType.MENGDIAN.type && userGeneral != null) {
				name = userGeneral.getName() + "の萌店";
			}
		} else {
			name = dollBus.getName();
		}
		return name;
	}

	@Override
	public List<BusOwnerBusIncome> getBusIncomeByBusIds(List<Integer> mdDollBusIds) {
		Map<String, Integer> codeMap = getDateCodeMap(-1, 0, 0, 1); // 获取开始、结束日期code
		int startCode = codeMap.get("startCode");
		int endCode = codeMap.get("endCode");
		return busOwnerDao.getBusIncomeByBusIds(mdDollBusIds, startCode, endCode);
	}

	@Override
	public List<BusOwnerUserIndex> getBusOwnerUserIndexList(int busId, int size, int dateNum1, int dateNum2, int dateNum3, int dateNum4) {
		Map<String, Integer> codeMap = getDateCodeMap(dateNum1, dateNum2, dateNum3, dateNum4); // 获取开始、结束日期code
		int startCode = codeMap.get("startCode");
		int endCode = codeMap.get("endCode");
		return busOwnerDao.getBusOwnerUserIndexList(busId, startCode, endCode, size);
	}

	@Override
	public List<ShareRecordRes> getShareRecords(Long uid, String unionId, Integer busId) {
		List<ShareRecordRes> shareRecordResList = new ArrayList<ShareRecordRes>();
		Map<String, Integer> codeMap = getDateCodeMap(-1, 0, 0, 1); // 获取开始、结束日期code
		int startCode = codeMap.get("startCode");
		int endCode = codeMap.get("endCode");
		if(unionId != null) {
			BusOwnerUserIndexRecord userIndexRecord = busOwnerDao.getUserIndexRecordByParams(unionId, uid, startCode, endCode, busId);
			UserWechat userWechat = wechatService.getUserWechat(unionId);
			ShareRecordRes shareRecordRes = null;
			if(userIndexRecord != null) {
				shareRecordRes = new ShareRecordRes(userWechat, userIndexRecord.getValue(), 
						userIndexRecord.getBusId()+"号机", DateUtils.dateToString(userIndexRecord.getCreateTime(), "MM-dd HH:mm"));
			} else {
				shareRecordRes = new ShareRecordRes(userWechat);
			}
			shareRecordResList.add(shareRecordRes);
		}
		List<BusOwnerUserIndexRecord> records = busOwnerDao.getBusOwnerUserIndexRecordByParams(uid, startCode, endCode, 1, busId);
		if(!StringUtil.isNullOrEmpty(records)) {
			for(BusOwnerUserIndexRecord record : records) {
				UserWechat userWechat = wechatService.getUserWechat(record.getUnionId());
				if(userWechat != null) {
					ShareRecordRes shareRecordRes = new ShareRecordRes(userWechat, record.getValue(), record.getBusId()+"号机", DateUtils.dateToString(record.getCreateTime(), "MM-dd HH:mm"));
					shareRecordResList.add(shareRecordRes);
					if(unionId != null && shareRecordResList.size() == 51 ) break;
				}
			}
		} else {
			UserWechat userWechat = new UserWechat();
			userWechat.setName("虚位以待");
			userWechat.setHead(CommConstants.DEFAULT_HEAD);
			shareRecordResList.add(new ShareRecordRes(userWechat, 0, "", ""));
		}
		return shareRecordResList;
	}

	@Override
	public List<PlayerRankingItem> getPlayerRankings(Long uid, int busId) {
		LOG.info("getPlayerRankings:uid-" + uid + ",busId=" + busId);
		List<PlayerRankingItem> items = new ArrayList<PlayerRankingItem>();
		Map<String, Integer> codeMap = getDateCodeMap(-1, 0, 0, 1); // 获取开始、结束日期code
		int startCode = codeMap.get("startCode");
		int endCode = codeMap.get("endCode");
		// 用户自身指数
		UserGeneral userGeneral = userService.getUserGeneral(uid);
		BusOwnerUserIndex selfUserIndex = busOwnerDao.getBusOwnerUserIndex(busId, uid, startCode, endCode);
		int selfCatDollIndex = 0, selfShareValue = 0, selfUindex = 0;
		if(selfUserIndex != null && selfUserIndex.getValue() > 0) {
			selfUindex= selfUserIndex.getValue();
			selfCatDollIndex = busOwnerDao.getUserIndexByType(uid, startCode, endCode, 0, busId);
			selfShareValue = busOwnerDao.getUserIndexByType(uid, startCode, endCode, 1, busId);
		}
		items.add(new PlayerRankingItem(userGeneral, selfCatDollIndex, selfShareValue, selfUindex, 0));
		// 榜单列表
		List<BusOwnerUserIndex> userIndexList = busOwnerDao.getBusOwnerUserIndexList(busId, startCode, endCode, 50);
		int ranking = 1;
		Map<Long, Integer> rankingMap = new HashMap<Long, Integer>();
		if(!StringUtil.isNullOrEmpty(userIndexList)) {
			List<Long> uids = new ArrayList<Long>();
			for(BusOwnerUserIndex userIndex : userIndexList) {
				uids.add(userIndex.getUid());
			}
			Map<Long, UserGeneral> userGeneralMap = userService.getUserGeneralMap(uids);
			for(BusOwnerUserIndex userIndex : userIndexList) {
				int catDollIndex = 0, shareValue = 0, uindex = 0;
				if(userIndex != null && userIndex.getValue() > 0) {
					uindex= userIndex.getValue();
					catDollIndex = busOwnerDao.getUserIndexByType(userIndex.getUid(), startCode, endCode, 0, busId);
					shareValue = busOwnerDao.getUserIndexByType(userIndex.getUid(), startCode, endCode, 1, busId);
				}
				items.add(new PlayerRankingItem(userGeneralMap.get(userIndex.getUid()), catDollIndex, shareValue, uindex, ranking));
				rankingMap.put(userIndex.getUid(), ranking);
				ranking++;
			}
		} else {
			UserGeneral dummyUserGeneral = new UserGeneral();
			dummyUserGeneral.setName("虚位以待");
			dummyUserGeneral.setHead(CommConstants.DEFAULT_HEAD);
			items.add(new PlayerRankingItem(dummyUserGeneral , 0, 0, 0, 0));
		}
		items.get(0).setRanking(rankingMap.get(uid) == null ? 0 : rankingMap.get(uid)); // 用户自身排名
		return items;
	}

	@Override
	public BaseRespVO getTimeInfo(BaseReqVO reqVO) {
		GetTimeInfoRespVO respVO = new GetTimeInfoRespVO();
		Map<String, Integer> codeMap = getDateCodeMap(-1, 0, 0, 1); // 获取开始、结束日期code
		int startCode = codeMap.get("startCode");
		int endCode = codeMap.get("endCode");
		String startCodeStr = startCode + "";
		String endCodeStr = endCode + "";
		respVO.setSMonthStr(startCodeStr.substring(4, 6));
		respVO.setSDayStr(startCodeStr.substring(6));
		respVO.setEMonthStr(endCodeStr.substring(4, 6));
		respVO.setEDayStr(endCodeStr.substring(6));
		Date endDate = null;
		int currHour = Integer.parseInt(DateUtils.dateToString(new Date(), "HH"));
		if(currHour < 12) {
			endDate = DateUtils.string2Date(DateUtils.dateToString(new Date(), "yyyy-MM-dd 11:59:59"), "yyyy-MM-dd HH:mm:ss");
		} else {
			endDate = DateUtils.string2Date(DateUtils.dateToString(DateUtils.addDate(new Date(), 1),
					"yyyy-MM-dd 11:59:59"), "yyyy-MM-dd HH:mm:ss");
		}
		respVO.setRemainingTime((endDate.getTime()-new Date().getTime())/1000);
		return respVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShopItem> getShopList(BaseReqVO reqVO) {
		List<ShopItem> shopItems = new ArrayList<ShopItem>();
		List<DollBus> dollBusList = dollBusService.getDollBus(new Integer(DollBus.DollBusType.MENGDIAN.type));
		if(!StringUtil.isNullOrEmpty(dollBusList)) {
			for(DollBus dollBus : dollBusList) {
				DollInfo dollInfo = dollInfoService.getDollInfo(dollBus.getDollId());
				ShopItem shopItem = new ShopItem(dollBus.getBusId(), dollInfo.getImgCover());
				shopItems.add(shopItem);
			}
		}
		shopItems = (List<ShopItem>) PropertUtil.doSeq(shopItems, "busId", false);
		return shopItems;
	}

	@Override
	public BusOwnerRes getBusOwnerRes(Long uid) {
		List<Webp> webpList = webpService.getWebpList();
		Webp webp = new Webp();
		if(!StringUtil.isNullOrEmpty(webpList)) {
			webp = webpList.get(new Random().nextInt(webpList.size()));
		}
		return new BusOwnerRes(userService.getUserGeneral(uid), getBusOwnerRoomNumsByUid(uid), webp.getUrl(), webp.getTime());
	}

	@Override
	public List<Integer> getBusOwnerBusIds(int startCode, int endCode) {
		return busOwnerDao.getBusOwnerBusIds(startCode, endCode);
	}

	@Override
	public BaseRespVO getIncomeInfo(Long uid, int busId) {
		GetIncomeInfoRespVO respVO = new GetIncomeInfoRespVO();
		Map<String, Integer> codeMap = getDateCodeMap(-1, 0, 0, 1); // 获取开始、结束日期code
		int startCode = codeMap.get("startCode");
		int endCode = codeMap.get("endCode");
		respVO.setMonthStr((startCode+"").substring(4, 6));
		respVO.setDayStr((startCode+"").substring(6, 8));
		BusOwnerBusIncome busIncome = busOwnerDao.getBusIncome(busId, startCode, endCode);
		int totalIncome = busIncome == null ? 0 : busIncome.getCoin();
		respVO.setTotalIncome(totalIncome);
		List<BusOwnerUserIndex> userIndexList = getBusOwnerUserIndexList(busId, 3, -2, -1, -1, 0);
		double rewardRatio = 0;
		if(!StringUtil.isNullOrEmpty(userIndexList)) {
			for(int i = 0; i < userIndexList.size(); i++) {
				if(userIndexList.get(i).getUid().longValue() == uid.longValue()) {
					rewardRatio = RewardRatioEnm.getRewardRatioEnm(i+1).getRatio();
				}
			}
				
		}
		if(rewardRatio == 0) {
			return new BaseRespVO(700, false, uid + "上一轮的机器指数【" + busId + "号机】的排名在3名外");
		}
		respVO.setRewardRatio(rewardRatio*100);
		respVO.setGetIncome(new Double(ArithmeticUtils.multiply(rewardRatio, totalIncome, 0)).intValue());
		List<RatioInfo> ratioInfoList = new ArrayList<RatioInfo>();
		for(RewardRatioEnm rewardRatioEnm : RewardRatioEnm.values()) {
			RatioInfo ratioInfo = new RatioInfo();
			ratioInfo.setRanking(rewardRatioEnm.getRanking());
			ratioInfo.setRewardRatio(rewardRatioEnm.getRatio()*100);
			ratioInfoList.add(ratioInfo);
		}
		respVO.setRatioInfos(ratioInfoList);
		return respVO;
	}

	@Override
	public List<BusOwnerUserIndex> getBusOwnerUserIndexByParams(int busId, int size, int startCode, int endCode) {
		return busOwnerDao.getBusOwnerUserIndexList(busId, startCode, endCode, size);
	}

	@Override
	public BusOwnerBusIncome getBusIncomeByParams(int busId, int startCode, int endCode) {
		return busOwnerDao.getBusIncome(busId, startCode, endCode);
	}

	@Override
	public void pushRankingMsg() {
		List<DollBus> dollBusList = dollListService.getBusList(new Integer(DollBus.DollBusType.MENGDIAN.type));
		if(!StringUtil.isNullOrEmpty(dollBusList)) {
			for(DollBus dollBus : dollBusList) {
				Map<String, Integer> codeMap = getDateCodeMap(-1, 0, 0, 1); // 获取开始、结束日期code
				int startCode = codeMap.get("startCode");
				int endCode = codeMap.get("endCode");
				//int startCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
				//int endCode = Integer.parseInt(DateUtils.dateToString(DateUtils.addDate(new Date(), 1), "yyyyMMdd"));
				List<BusOwnerUserIndex> userIndexList = busOwnerDao.getBusOwnerUserIndexList(dollBus.getBusId(), startCode, endCode, 20);
				if(!StringUtil.isNullOrEmpty(userIndexList)) {
					int ranking = 1;
					for(BusOwnerUserIndex userIndex : userIndexList) {
						int catDollValue = busOwnerDao.getUserIndexByType(userIndex.getUid(), startCode, endCode, 0, userIndex.getBusId());
						int shareValue = busOwnerDao.getUserIndexByType(userIndex.getUid(), startCode, endCode, 1, userIndex.getBusId());
						Msg msg = new Msg(userIndex.getUid(), MsgType.NOTICE_SYS.type,
								"小主在" + userIndex.getBusId() + "号机的抓娃娃指数为" + catDollValue + "，分享值为" + shareValue + 
								"，机主指数为" + userIndex.getValue() + "，暂列榜单第" + ranking + "名。");
						msgService.sendMsg(msg);
						ranking++;
					}
				}
			}
		}
	}

	@Override
	public boolean isMengDian(int busId) {
		List<DollBus> dollBusList = dollListService.getBusList(new Integer(DollBus.DollBusType.MENGDIAN.type));
		if(!StringUtil.isNullOrEmpty(dollBusList)) {
			for(DollBus dollBus : dollBusList) {
				if(dollBus.getBusId() == busId) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Map<String, String> getBusOwnerH5Map(Long uid, Integer busId) {
		Map<String, String>  busOwnerH5Map = new HashMap<String, String>();
		/*List<BusOwnerUserIndex> busOwnerUserIndexList = getBusOwnerUserIndexList(busId, 3, -2, -1, -1, 0); // 上一轮机器指数在萌店排名前三的用户指数
		List<Long> uids = new ArrayList<Long>();
		if(!StringUtil.isNullOrEmpty(busOwnerUserIndexList)) {
			for(BusOwnerUserIndex userIndex : busOwnerUserIndexList) {
				uids.add(userIndex.getUid());
			}
		}
		String busOwnerUrl = "";
		if(!StringUtil.isNullOrEmpty(uids) 
				&& uids.contains(uid)) { 
			busOwnerUrl = BusOwnerConstants.IS_BUSOWNER_URL;
		} else {
			busOwnerUrl = BusOwnerConstants.ISNOT_BUSOWNER_URL;
		}*/
		busOwnerH5Map.put("busOwnerUrl", BusOwnerConstants.HOMEPAGE_H5_URL);
		busOwnerH5Map.put("busOwnerTitle", BusOwnerConstants.HOMEPAGE_H5_TITLE);
		return busOwnerH5Map;
	}

	@Override
	public List<BusOwnerInfo> getBusOwnerInfos(int busId) {
		List<BusOwnerInfo> busOwnerInfos = new ArrayList<BusOwnerInfo>();
		Map<String, Integer> codeMap = getDateCodeMap(-2, -1, -1, 0); // 获取开始、结束日期code
		int startCode = codeMap.get("startCode");
		int endCode = codeMap.get("endCode");
		List<BusOwnerUserIndex> userIndexList = busOwnerDao.getBusOwnerUserIndexList(busId, startCode, endCode, 3);
		if(!StringUtil.isNullOrEmpty(userIndexList)) {
			for(BusOwnerUserIndex userIndex : userIndexList) {
				busOwnerInfos.add(new BusOwnerInfo(userService.getUserGeneral(userIndex.getUid())));
			}
		}
		
		if(busOwnerInfos.size() < 3) {
			int size = 3-busOwnerInfos.size();
			for(int i = 1; i <= size; i++) {
				UserGeneral userGeneral = new UserGeneral();
				userGeneral.setName("虚位以待");
				userGeneral.setHead(CommConstants.DEFAULT_HEAD);
				busOwnerInfos.add(new BusOwnerInfo(userGeneral));
			}
		}
		return busOwnerInfos;
	}
}
