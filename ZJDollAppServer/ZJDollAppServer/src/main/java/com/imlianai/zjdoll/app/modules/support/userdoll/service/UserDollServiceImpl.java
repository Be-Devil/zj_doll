package com.imlianai.zjdoll.app.modules.support.userdoll.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.BaseDollInfo;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.zjdoll.domain.doll.LogisticsInfo;
import com.imlianai.zjdoll.domain.doll.user.RankingItem;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.zjdoll.domain.doll.user.UserDollDebris;
import com.imlianai.zjdoll.domain.doll.user.UserDollMonthCount;
import com.imlianai.zjdoll.domain.doll.user.UserDollWeekCount;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserCommon;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.domain.user.UserValue;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.enm.VirtualGoodType;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordDao;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.dao.UserDAO;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.core.user.value.UserValueDAO;
import com.imlianai.zjdoll.app.modules.core.user.value.UserValueService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.dailytask.service.DailytaskService;
import com.imlianai.zjdoll.app.modules.support.event.lanternFestival20180227.service.EventLanternFestivalService;
import com.imlianai.zjdoll.app.modules.support.exchange.service.DollComposeService;
import com.imlianai.zjdoll.app.modules.support.exchange.service.DollExchangeSevice;
import com.imlianai.zjdoll.app.modules.support.ranking.service.RankingService;
import com.imlianai.zjdoll.app.modules.support.ranking.vo.GetRankingRespVO;
import com.imlianai.zjdoll.app.modules.support.record.service.FirsttimeMsgPushRecordService;
import com.imlianai.zjdoll.app.modules.support.signin.contants.UserSigninContants;
import com.imlianai.zjdoll.app.modules.support.userdoll.dao.UserDollDao;
import com.imlianai.zjdoll.app.modules.support.userdoll.enm.FirsttimePushType;
import com.imlianai.zjdoll.app.modules.support.userdoll.vo.GetDollSizeInfo;

@Service
public class UserDollServiceImpl implements UserDollService {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(UserDollServiceImpl.class);

	private static final int PAGE_SIZE = 10; // 用户娃娃列表每页显示的个数

	@Resource
	UserDollDao userDollDao;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	UserService userService;
	@Resource
	UserValueDAO userValueDao;
	@Resource
	DollRecordDao dollRecordDao;
	@Resource
	MsgService msgService;
	@Resource
	TradeService tradeService;
	@Resource
	UserValueDAO userValueDAO;
	@Resource
	DollRecordService dollRecordService;
	@Resource
	EventLanternFestivalService eventLanternFestivalService;
	@Resource
	FirsttimeMsgPushRecordService firsttimeMsgPushRecordService;
	@Resource
	UserDAO userDAO;
	@Resource
	UserValueService userValueService;
	@Resource
	DailytaskService dailytaskService;
	@Resource
	DollExchangeSevice dollExchangeSevice;
	@Resource
	DollComposeService dollComposeService;
	@Resource
	RankingService rankingService;

	private static Map<Long, Integer> weekRankMapCache = new ConcurrentHashMap<Long, Integer>();
	
	List<RankingItem> weekRankingCache=new ArrayList<RankingItem>();

	private static Map<Long, Integer> monthRankMapCache = new ConcurrentHashMap<Long, Integer>();

	List<RankingItem> monthRankingCache=new ArrayList<RankingItem>();
	
	private static Date weekLastUpdateTime = new Date();

	private static Date monthLastUpdateTime = new Date();

	@Override
	public UserDoll getUserDollById(long id) {
		return userDollDao.getUserDollById(id);
	}

	@Override
	public int updateUserDollStatus(long id, int status) {
		return userDollDao.updateUserDollStatus(id, status);
	}

	@Override
	public List<UserDoll> getExchangeUserDollList(int otherDays) {
		return userDollDao.getExchangeUserDollList(otherDays);
	}

	@Override
	public GetRankingRespVO getRanking(int type, Long userId) {
		GetRankingRespVO respVO = new GetRankingRespVO();
		List<RankingItem> rankingItems = new ArrayList<RankingItem>();
		RankingItem selfRankingInfo = new RankingItem();
		int code = 0;
		initRank(type);
		if (type == 0) { // 周榜
			code = Integer.parseInt(DateUtils.dateToString(
					DateUtils.getWeekFirstTime(), "yyyyMMdd")); // 星期一的日期
			UserDollWeekCount weekCount = userDollDao
					.getUserDollWeekCountByUidAndCode(userId, code);
			int num = 0;
			int rank = weekRankMapCache.get(userId) == null ? 0
					: weekRankMapCache.get(userId);
			if (weekCount != null) {
				num = weekCount.getNum();
			}
			selfRankingInfo = new RankingItem(new UserCommon(
					userService.getUserGeneral(userId),
					userService.getUserBase(userId)), num, rank);
			rankingItems.addAll(weekRankingCache);
		} else if (type == 1) { // 月榜
			code = Integer.parseInt(DateUtils.dateToString(new Date(),
					"yyyyMM")); // 月份
			UserDollMonthCount monthCount = userDollDao
					.getUserDollMonthCountByUidAndCode(userId, code);
			int num = 0;
			int rank = monthRankMapCache.get(userId) == null ? 0
					: monthRankMapCache.get(userId);
			if (monthCount != null) {
				num = monthCount.getNum();
			}
			selfRankingInfo = new RankingItem(new UserCommon(
					userService.getUserGeneral(userId),
					userService.getUserBase(userId)), num, rank);
			rankingItems.addAll(monthRankingCache);
		}
		if (!StringUtil.isNullOrEmpty(rankingItems)) {
			try {
				if (rankingItems.size()<5) {
					for (int i = rankingItems.size(); i < 5; i++) {
						rankingItems.add(new RankingItem("虚位以待", (i+1)));
					}
				}
			} catch (Exception e) {
			}
			rankingItems=rankingService.getRankListWithReward(rankingItems, 1);
		}
		respVO.setRankingItems(rankingItems);
		respVO.setSelfRankingInfo(selfRankingInfo);
		return respVO;
	}

	private void initRank(int type) {
		List<RankingItem> rankingItems = new ArrayList<RankingItem>();
		int code = 0;
		if (type == 0) { // 周榜
			if (StringUtil.isNullOrEmpty(weekRankMapCache)
					|| DateUtils.secondBetween(weekLastUpdateTime) > 10) {
				weekLastUpdateTime = new Date();
				Map<Long, Integer> weekRankMap = new ConcurrentHashMap<Long, Integer>(); // 周榜排名，key-用户ID
																							// value-排名
				code = Integer.parseInt(DateUtils.dateToString(
						DateUtils.getWeekFirstTime(), "yyyyMMdd")); // 星期一的日期
				List<UserDollWeekCount> weekCounts = userDollDao
						.getUserDollWeekCountList(code, 50);
				if (!StringUtil.isNullOrEmpty(weekCounts)) {
					List<Long> uidsList = new ArrayList<Long>();
					for (UserDollWeekCount userDollWeekCount : weekCounts) {
						long uid = userDollWeekCount.getUid();
						uidsList.add(uid);
					}
					Map<Long, UserGeneral> userMap = userService
							.getUserGeneralMap(uidsList);
					Map<Long, UserBase> baseMap = userService
							.getUserBaseMap(uidsList);
					int i = 1;
					for (UserDollWeekCount userDollWeekCount : weekCounts) {
						long uid = userDollWeekCount.getUid();
						UserGeneral userGeneral = userMap.get(uid);
						UserBase base = baseMap.get(uid);
						if (userGeneral != null && base != null) {
							RankingItem RankingItem = new RankingItem(
									new UserCommon(userGeneral, base),
									userDollWeekCount.getNum(), i);
							rankingItems.add(RankingItem);
							weekRankMap.put(uid, i);
							i++;
						}
					}
				}
				// 将周榜补充
				weekRankMapCache.clear();
				weekRankMapCache.putAll(weekRankMap);
				weekRankingCache=rankingItems;
			}
		} else if (type == 1) { // 月榜
			if (StringUtil.isNullOrEmpty(monthRankMapCache)
					|| DateUtils.secondBetween(monthLastUpdateTime) > 10) {
				monthLastUpdateTime = new Date();
				Map<Long, Integer> monthRankMap = new ConcurrentHashMap<Long, Integer>(); // 月榜排名，key-用户ID
																							// value-排名
				code = Integer.parseInt(DateUtils.dateToString(new Date(),
						"yyyyMM")); // 月份
				List<UserDollMonthCount> monthCounts = userDollDao
						.getUserDollMonthCountList(code, 50);
				if (!StringUtil.isNullOrEmpty(monthCounts)) {
					List<Long> uidsList = new ArrayList<Long>();
					for (UserDollMonthCount userDollMonthCount : monthCounts) {
						long uid = userDollMonthCount.getUid();
						uidsList.add(uid);
					}
					Map<Long, UserGeneral> userMap = userService
							.getUserGeneralMap(uidsList);
					Map<Long, UserBase> baseMap = userService
							.getUserBaseMap(uidsList);
					int i = 1;
					for (UserDollMonthCount userDollMonthCount : monthCounts) {
						long uid = userDollMonthCount.getUid();
						UserGeneral userGeneral = userMap.get(uid);
						UserBase base = baseMap.get(uid);
						if (userGeneral != null && base != null) {
							RankingItem RankingItem = new RankingItem(
									new UserCommon(userGeneral, base),
									userDollMonthCount.getNum(), i);
							rankingItems.add(RankingItem);
							monthRankMap.put(uid, i);
							i++;
						}
					}
				}
				// 将月榜补充
				monthRankMapCache.clear();
				monthRankMapCache.putAll(monthRankMap);
				monthRankingCache=rankingItems;
			}
		}
	}

	@Override
	public int getRankingNum(int type, Long userId) {
		int res = 0;
		if (type == 0) { // 周榜
			if (StringUtil.isNullOrEmpty(weekRankMapCache)) {
				getRanking(type, userId);
			}
			if (!StringUtil.isNullOrEmpty(weekRankMapCache)) {
				Integer rank = weekRankMapCache.get(userId);
				if (rank != null) {
					res = rank;
				}
			}
			LOG.info("getRankingNum-weekRankMapCache:"+JSON.toJSONString(weekRankMapCache)+",userId="+userId);
		} else if (type == 1) { // 月榜
			if (StringUtil.isNullOrEmpty(monthRankMapCache)) {
				getRanking(type, userId);
			}
			if (!StringUtil.isNullOrEmpty(monthRankMapCache)) {
				Integer rank = monthRankMapCache.get(userId);
				if (rank != null) {
					res = rank;
				}
			}
		}
		return res;
	}

	@Override
	public List<BaseDollInfo> getUserDollList(Long userId, long lastId) {
		LOG.info("getUserDollList:userId" + "-" + userId + ",lastId-" + lastId);
		List<BaseDollInfo> dollList = new ArrayList<BaseDollInfo>();
		List<UserDoll> userDolls = userDollDao.getUserDollList(userId, lastId,
				PAGE_SIZE);
		if (!StringUtil.isNullOrEmpty(userDolls)) {
			for (UserDoll userDoll : userDolls) {
				DollInfo dollInfo = dollInfoService.getDollInfo(userDoll
						.getDollId());
				if (dollInfo == null)
					continue;
				BaseDollInfo baseDollInfo = new BaseDollInfo(userDoll,
						dollInfo.getName(), dollInfo.getImgSummry(),
						dollInfo.getCoin());
				dollList.add(baseDollInfo);
			}
		}
		return dollList;
	}

	@Override
	public RankingItem getUserDollCount(Long userId) {
		int count = userDollDao.getDollCountByUid(userId);
		RankingItem rankingItem = new RankingItem(new UserCommon(
				userService.getUserGeneral(userId),
				userService.getUserBase(userId)), count, null);
		return rankingItem;
	}

	@Override
	public List<UserDoll> getShippingList(Long uid) {
		return userDollDao.getShippingList(uid);
	}

	@Override
	public int getDollCountByUserIdAndDollId(Long uid, long dollId) {
		return userDollDao.getDollCountByUserIdAndDollId(uid, dollId);
	}

	@Override
	public List<UserDollWeekCount> getUserDollWeekCountList(int code, int num) {
		return userDollDao.getUserDollWeekCountList(code, num);
	}

	@Override
	public int saveUserDoll(UserDoll userDoll) {
		int uDollId = userDollDao.saveUserDoll(userDoll);
		if(uDollId > 0) {
			userValueService.addDollNum(userDoll.getUid(), 1); // 修改用户捉取到的娃娃个数
			// 增加娃娃消息
			UserGeneral userGeneral = userService.getUserGeneral(userDoll.getUid());
			Msg msg = new Msg(userGeneral, userDoll.getUid(), MsgType.ADD_DOLL.type,
					"增加娃娃消息");
			msgService.sendMsg(msg);
		}
		return uDollId;
	}

	@Override
	public int saveUserDoll(UserDoll userDoll,String privateMsg) {
		int uDollId = userDollDao.saveUserDoll(userDoll);
		if(uDollId > 0) {
			userValueService.addDollNum(userDoll.getUid(), 1); // 修改用户捉取到的娃娃个数
			// 增加娃娃消息
			UserGeneral userGeneral = userService.getUserGeneral(userDoll.getUid());
			Msg msg = new Msg(userGeneral, userDoll.getUid(), MsgType.ADD_DOLL.type,
					privateMsg);
			msgService.sendMsg(msg);
		}
		return uDollId;
	}


	@Override
	public int getUserDollSizeByParams(Long uid, int status) {
		return userDollDao.getUserDollSizeByParams(uid, status);
	}

	@Override
	public GetDollSizeInfo getDollSize(Long uid) {
		GetDollSizeInfo info = null;
		UserValue userValue = userValueDao.getUserValueInDb(uid);
		UserGeneral userGeneral = userService.getUserGeneral(uid);
		if (userValue != null && userGeneral != null
				&& userGeneral.getValid() == 0) {
			info = new GetDollSizeInfo(userGeneral.getUid(),
					userGeneral.getName(), userGeneral.getHead(),
					userValue.getDollNum());
		}
		return info;
	}

	@Override
	public List<UserDoll> getUserDollByParams(Long uid, long dollId, int num) {
		return userDollDao.getUserDollByParams(uid, dollId, num);
	}

	@Override
	public int saveOrUpdateUserDollWeekCount(long uid, int weekCode, int num) {
		return userDollDao.saveOrUpdateUserDollWeekCount(uid, weekCode, num);
	}

	@Override
	public int saveOrUpdateUserDollMonthCount(long uid, int monthCode, int num) {
		return userDollDao.saveOrUpdateUserDollMonthCount(uid, monthCode, num);
	}

	@Override
	public List<LogisticsInfo> getLogistics(Long uDollId) {
		// test
		List<LogisticsInfo> logisticsInfos = new ArrayList<LogisticsInfo>();
		/*LogisticsInfo info1 = new LogisticsInfo("等待揽件", 1512087142000l);
		LogisticsInfo info2 = new LogisticsInfo("快件已由物流公司揽收", 1512090742000l);
		LogisticsInfo info3 = new LogisticsInfo("快件已由[杭州分拨中心]发往[广州分拨中心]",
				1512263542000l);
		LogisticsInfo info4 = new LogisticsInfo("康龙已签收", 1512349942000l);
		logisticsInfos.add(info4);
		logisticsInfos.add(info3);
		logisticsInfos.add(info2);
		logisticsInfos.add(info1);*/
		return logisticsInfos;
	}

	@Override
	public BaseRespVO returnDoll(Long optId, long uid) {
		try {
			DollOptRecord record = dollRecordDao.getOptRecord(optId);
			if(record == null || uid != record.getUid()) {
				return new BaseRespVO(-1, false, "归还条件不符合~");
			}
			DollInfo dollInfo = dollInfoService.getDollInfo(record.getDollId());
			if(dollInfo == null) {
				return new BaseRespVO(-1, false, "娃娃不存在或下架，请联系客服处理~");
			}
			//int status = dollRecordService.getSpecDollStatus(record.getDollId());
			int status = getUserDollDefaultStatus(dollInfo);
			UserDoll userDoll = new UserDoll();
			userDoll.setUid(uid);
			userDoll.setDollId(record.getDollId());
			userDoll.setOptId(record.getOptId());
			userDoll.setStatus(status);
			userDoll.setGoodsType(dollInfo.getGoodsType());
			userDoll.setRemark("娃娃归还");
			long uDollId = saveUserDoll(userDoll);
			/*if(status > 0) {
				eventLanternFestivalService.handleCatchDoll(status, uDollId, userDoll.getUid(), dollInfo);
			}*/
			if(dollInfo.getGoodsType() == 1) { // 虚拟
				handleVirtualDoll(dollInfo, status, uDollId, record.getUid());
			}
			record.setResult(1);
			dollRecordDao.updateDollOptRecord(record); // 操作记录改为成功
			dollRecordDao.saveDollSucOptRecord(optId, uid, record.getDollId(), record.getBusId()); // 保存成功捉取娃娃记录
			//userValueService.addDollNum(record.getUid(), 1); // 修改用户捉取到的娃娃个数
			// 保存用户娃娃个数周统计、月统计
			int weekCode = Integer.parseInt(DateUtils.dateToString(DateUtils.getWeekFirstTime(), "yyyyMMdd"));
			int monthCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMM"));
			userDollDao.saveOrUpdateUserDollWeekCount(record.getUid(), weekCode, 1);
			userDollDao.saveOrUpdateUserDollMonthCount(record.getUid(), monthCode, 1);
			// 系统通知
			Msg msg = new Msg(userDoll.getUid() , MsgType.NOTICE_SYS.type,
					"您抓取的" + dollInfo.getName() + "（游戏编号：" + record.getOptId() + "）的申诉已处理，宝贝已放进您的背包。");
			msgService.sendMsg(msg);
			// 每日任务抓中娃娃处理
			dailytaskService.handleSuccCatchDoll(record);
			return new BaseRespVO();
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "归还娃娃失败，请重试~");
		}
	}

	@Override
	public int getUserDollDefaultStatus(DollInfo dollInfo) {
		int status = 0;
		int goodsType = dollInfo.getGoodsType();
		if(goodsType == 1) { // 虚拟
			int specType = dollInfo.getSpecType();
			VirtualGoodType virGoodType = VirtualGoodType.getVirGoodType(specType);
			switch (virGoodType) {
			case COIN:
				status = 3;
				break;
			case JEWEL:
				status = 5;
				break;
			}
		}
		return status;
	}

	@Override
	public BaseRespVO refundCoin(Long optId, long uid) {
		try {
			DollOptRecord record = dollRecordDao.getOptRecord(optId);
			if(record == null) {
				return new BaseRespVO(-1, false, "操作记录不存在~");
			}
			DollInfo dollInfo = dollInfoService.getDollInfo(record.getDollId());
			if(dollInfo == null) {
				return new BaseRespVO(-1, false, "娃娃不存在或下架，请联系客服处理~");
			}
			TradeRecord tradeRecord = new TradeRecord(uid, uid,
					TradeType.APPEAL_REFUND.type, 0, record.getCost(),
					TradeCostType.COST_COIN.type, "申诉退币" + record.getCost());
			tradeService.charge(tradeRecord);
			// 系统通知
			Msg msg = new Msg(uid, MsgType.NOTICE_SYS.type,
					"您抓取的" + dollInfo.getName() + "（游戏编号：" + record.getOptId() + "）的申诉已处理，" + record.getCost() + "币已退回到您的账户。");
			msgService.sendMsg(msg);
			return new BaseRespVO();
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "退币失败，请重试~");
		}
	}

	@Override
	public void saveOrUpdateUserDollDebris(Long uid, int type, double num, String remark) {
		if(userDollDao.saveOrUpdateUserDollDebris(uid, type, num) > 0) {
			userDollDao.saveUserDollDebrisLog(uid, type, num, remark);
		}
		if(num > 0 && type == 0) {
			List<FirsttimePushType> list = FirsttimePushType.getFirsttimePushTypeArry(0);
			if(!StringUtil.isNullOrEmpty(list)) {
				UserDollDebris userDollDebris = userDollDao.getUserDollDebrisByParams(uid, type);
				int currNum = userDollDebris == null ? 0 : userDollDebris.getNum();
				for(FirsttimePushType pushType : list) {
					boolean isPush = firsttimeMsgPushRecordService.isPush(uid, pushType.getType(), pushType.getNum());
					if(isPush) break;
					if(currNum >= pushType.getNum()) {
						// 消息推送
						String textString = "小主拥有" + currNum + "块普通碎片，集齐指定数量的碎片可合成娃娃哟~~";
						MsgNotice msg=new MsgNotice(uid, MsgType.NOTICE_SYS.type, textString);
						UserBase userBase = userDAO.getUserBase(uid);
						msg.setJumpWeb(UserSigninContants.SHOP_URL + "?uid=" + uid + "&loginKey=" + userBase.getLoginKey());
						msg.setPushMsg(textString);
						msg.setTargetTitle("兑换商城");
						msg.setTargetName("兑换商城");
						msgService.sendMsg(msg);
						// 保存首次推送记录
						firsttimeMsgPushRecordService.saveFirsttimeMsgPushRecord(uid, pushType.getType(), pushType.getNum());
						break;
					}
				}
			}
		}
	}

	@Override
	public List<UserDollDebris> getUserDollDebrisByUid(Long uid) {
		return userDollDao.getUserDollDebrisByUid(uid);
	}

	@Override
	public int updateUserDollDebris(Long uid, int type, int num) {
		return userDollDao.updateUserDollDebris(uid, type, num);
	}

	@Override
	public int updateUserDollShipping(long id, String expressName,
			String trackingNum, int state) {
		return userDollDao.updateUserDollShipping(id, expressName, trackingNum, state);
	}

	@Override
	public long saveUserDoll(Long uid, int dollId, Long optId, String remark, int type) {
		DollInfo dollInfo = dollInfoService.getDollInfo(dollId);
		UserDoll userDoll = new UserDoll();
		userDoll.setUid(uid);
		userDoll.setDollId(dollId);
		userDoll.setOptId(optId);
		userDoll.setStatus(0);
		userDoll.setRemark(remark);
		userDoll.setType(type);
		userDoll.setGoodsType(dollInfo.getGoodsType());
		long uDollId = saveUserDoll(userDoll);
		//userValueService.addDollNum(uid, 1); // 修改用户捉取到的娃娃个数
		// 保存用户娃娃个数周统计、月统计
		int weekCode = Integer.parseInt(DateUtils.dateToString(DateUtils.getWeekFirstTime(), "yyyyMMdd"));
		int monthCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMM"));
		userDollDao.saveOrUpdateUserDollWeekCount(uid, weekCode, 1);
		userDollDao.saveOrUpdateUserDollMonthCount(uid, monthCode, 1);
		return uDollId;
	}

	@Override
	public void handleVirtualDoll(DollInfo doll, int status, long uDollId, long uid) {
		try {
			TradeRecord tradeRecord = null;
			switch (status) {
			case 3:
				dollExchangeSevice.saveExchangeRecord(uDollId, doll.getCoin());
				tradeRecord = new TradeRecord(uid, uid,
						TradeType.EXCHANGE_RETURN.type, 0, doll.getCoin(),
						TradeCostType.COST_COIN.type, "兑换" + doll.getCoin() + "个游戏币");
				tradeService.charge(tradeRecord);
				break;
			case 5:
				dollComposeService.saveRecycleRecord(uDollId, doll.getReturnJewel());
				tradeRecord = new TradeRecord(uid, uid,
						TradeType.RECYCLE_RETURN.type, 0, doll.getReturnJewel(),
						TradeCostType.COST_JEWEL.type, "回收" + doll.getReturnJewel() + "钻");
				tradeService.charge(tradeRecord);
				break;
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}

	@Override
	public UserDoll getNewestUserDollByType(Integer dollId, int type) {
		return userDollDao.getNewestUserDollByType(dollId, type);
	}

	@Override
	public int getUserDollSizeByParamsAfter130(Long uid, int status) {
		return userDollDao.getUserDollSizeByParamsAfter130(uid, status);
	}
	
	@Override
	public void handleAutoExchange(UserDoll userDoll, DollInfo dollInfo) {
		try {
			if(userDoll.getType() == 0) {
				if(updateUserDollStatus(userDoll.getId(), 3) > 0) {
					dollExchangeSevice.saveExchangeRecord(userDoll.getId(), dollInfo.getCoin());
					TradeRecord tradeRecord = new TradeRecord(userDoll.getUid(), userDoll.getUid(),
							TradeType.EXCHANGE_RETURN.type, 0, dollInfo.getCoin(),
							TradeCostType.COST_COIN.type, "自动兑换" + dollInfo.getCoin() + "个游戏币");
					tradeService.charge(tradeRecord);
					// 系统通知
					Msg msg = new Msg(userDoll.getUid() , MsgType.NOTICE_SYS.type,
							"逾期未申请发货，" + dollInfo.getName() +"自动兑换成" + dollInfo.getCoin() + "币。");
					msgService.sendMsg(msg);
				}
			} else if(userDoll.getType() == 1 || userDoll.getType() == 2) {
				if(dollInfo.getReturnJewel() > 0 && updateUserDollStatus(userDoll.getId(), 5) > 0) {
					dollComposeService.saveRecycleRecord(userDoll.getId(), dollInfo.getReturnJewel());
					TradeRecord tradeRecord = new TradeRecord(userDoll.getUid(), userDoll.getUid(),
							TradeType.RECYCLE_RETURN.type, 0, dollInfo.getReturnJewel(),
							TradeCostType.COST_JEWEL.type, "自动回收成" + dollInfo.getReturnJewel() + "钻");
					tradeService.charge(tradeRecord);
					// 系统通知
					String textString = dollInfo.getName() + "已被回收并获得" + dollInfo.getReturnJewel() + "钻";
					MsgNotice msg = new MsgNotice(userDoll.getUid(), MsgType.NOTICE_SYS.type, textString);
					msg.setJumpDoll(userDoll.getId());
					msgService.sendMsg(msg);
				}
			}
		} catch (Exception e) {
			PrintException.printException(LOG, e);
		}
	}
}
