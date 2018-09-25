package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgRoom;
import com.imlianai.zjdoll.domain.msg.MsgRoomJump;
import com.imlianai.zjdoll.domain.msg.MsgRoomType;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.NotEnoughBeanException;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.ctrip.framework.apollo.util.PropertiesUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.dao.UserDAO;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.service.EventTwistedEgg20180305Service;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.constants.TwistedEggMachineConstants;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.dao.EventTwistedEggMachineDao;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.domain.EventTwistedEggMachineUserFortune;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.domain.EventTwistedEggMachineUserIncome;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.domain.EventTwistedEggMachineUserOptRecord;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.enm.AwardEnm;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.enm.AwardType;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.enm.Every15MinsMsg;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.enm.RewardMsg;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.utils.TwistedEggMachineUtils;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.AwardItem;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.GetInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.PlayReqVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.PlayRespVO;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.UserAwardInfo;
import com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo.UserFortuneInfo;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;

@Service
public class EventTwistedEggMachineServiceImpl implements EventTwistedEggMachineService {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(EventTwistedEggMachineServiceImpl.class);
	
	public static int luckyBagId = Integer.parseInt(PropertiesUtil.getString("application","twistedEggMachine.luckyBagId")); // 福袋ID
	
	public static String H5_URL = PropertiesUtil.getString("application","twistedEggMachine.url"); // h5地址

	@Resource
	EventTwistedEggMachineDao eventTwistedEggMachineDao;
	@Resource
	TradeService tradeService;
	@Resource
	UserService userService;
	@Resource
	EventTwistedEgg20180305Service eventTwistedEgg20180305Service;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	UserDollService userDollService;
	@Resource
	MsgService msgService;
	@Resource
	UserDAO userDAO;
	
	@Override
	public BaseRespVO getInfo(Long uid) {
		LOG.info("getInfo-uid:" + uid);
		if(uid == null || uid.longValue() <= 0) {
			return new BaseRespVO(-1, false, "用户不存在~");
		}
		GetInfoRespVO respVO = new GetInfoRespVO();
		// 用户游戏币
		TradeAccount tradeAccount = tradeService.getAccount(uid);
		respVO.setCoin(tradeAccount == null ? 0 : tradeAccount.getCoin());
		// 用户任务扭蛋数量
		respVO.setTeggNum(eventTwistedEggMachineDao.getTeggNum(uid));
		// 最近抽中奖品100条记录
		respVO.setAwardRecords(getTwistedEggRecords(null, 1));
		// 手气值榜单列表
		int dateCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
		if(dateCode <= 20180312) dateCode = 0;
		List<UserFortuneInfo> rankingList = new ArrayList<UserFortuneInfo>();
		EventTwistedEggMachineUserFortune selfUserFortune = eventTwistedEggMachineDao.getUserFortuneByUid(uid, dateCode);
		UserFortuneInfo selfInfo = new UserFortuneInfo(userService.getUserGeneral(uid), 
				selfUserFortune == null ? 0 : selfUserFortune.getValue(), 0);
		rankingList.add(selfInfo);
		List<EventTwistedEggMachineUserFortune> userFortunes = eventTwistedEggMachineDao.getPreviousUserFortune(10, dateCode);
		if(!StringUtil.isNullOrEmpty(userFortunes)) {
			List<Long> uids = new ArrayList<Long>();
			Map<Long, Integer> rankingMap = new HashMap<Long, Integer>();
			int ranking = 1;
			for(EventTwistedEggMachineUserFortune userFortune : userFortunes) {
				uids.add(userFortune.getUid());
				rankingMap.put(userFortune.getUid(), ranking);
				ranking++;
			}
			Map<Long, UserGeneral> userGeneralMap = userService.getUserGeneralMap(uids);
			if(userGeneralMap != null) {
				for(EventTwistedEggMachineUserFortune userFortune : userFortunes) {
					rankingList.add(new UserFortuneInfo(userGeneralMap.get(userFortune.getUid()), userFortune.getValue(), 
							rankingMap.get(userFortune.getUid()) == null ? 0 : rankingMap.get(userFortune.getUid())));
				}
			}
			rankingList.get(0).setRanking(rankingMap.get(uid) == null ? 0 : rankingMap.get(uid)); // 自身排名信息
		}
		respVO.setRankingList(rankingList);
		return respVO;
	}

	@Override
	public List<UserAwardInfo> getTwistedEggRecords(Long uid, int limit) {
		List<UserAwardInfo> awardRecords = new ArrayList<UserAwardInfo>();
		List<EventTwistedEggMachineUserOptRecord> optRecords = eventTwistedEggMachineDao.getRecentlyUserOptRecords(100, uid);
		if(!StringUtil.isNullOrEmpty(optRecords)) {
			List<Long> uids = new ArrayList<Long>();
			for(EventTwistedEggMachineUserOptRecord record : optRecords) {
				uids.add(record.getUid());
			}
			Map<Long, UserGeneral> userGeneralMap = userService.getUserGeneralMap(uids);
			if(userGeneralMap != null) {
				for(EventTwistedEggMachineUserOptRecord record : optRecords) {
					if(record.getAwardType() == 0 && record.getAwardCoin() <= 21 && limit == 1) continue;
					awardRecords.add(new UserAwardInfo(userGeneralMap.get(record.getUid()).getName(), record.getAwardName(), DateUtils.dateToString(record.getCreateTime(), "HH:mm")));
				}
			}
		}
		return awardRecords;
	}

	@Override
	public BaseRespVO play(PlayReqVO reqVO) {
		PlayRespVO respVO = new PlayRespVO();
		try {
			List<AwardItem> awardItems = new ArrayList<AwardItem>();
			Long uid = reqVO.getUid();
			int type = reqVO.getType();
			int flag = reqVO.getFlag();
			UserGeneral userGeneral = userService.getUserGeneral(uid);
			int dateCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
			if(type == 0) { // 使用金币抽奖
				TradeAccount tradeAccount = tradeService.getAccount(uid);
				int times = flag == 1 ? TwistedEggMachineConstants.CONTINUOUS_PLAY_TIMES : 1;
				if(tradeAccount.getCoin() < times * TwistedEggMachineConstants.PLAY_COIN) {
					return new BaseRespVO(601, false, "游戏币不足~");
				}
				for(int i = 1; i <= times; i++) {
					int newTimes = eventTwistedEggMachineDao.getCurrTimes(uid, 0) + 1;
					AwardEnm awardEnm = null;
					if(newTimes <= 2) {
						awardEnm = AwardEnm.getAwardEnmById(TwistedEggMachineUtils.getAwardType(1));
					} else {
						EventTwistedEggMachineUserIncome userIncome = eventTwistedEggMachineDao.getUserIncome(0l); // 平台收益
						if(userIncome != null && userIncome.getValue() <= -TwistedEggMachineConstants.LUCKY_BAG_MIN_INCOME_LOSS) { // 可中福袋
							awardEnm = AwardEnm.getAwardEnmById(TwistedEggMachineUtils.getAwardType(3));
						} else {
							awardEnm = AwardEnm.getAwardEnmById(TwistedEggMachineUtils.getAwardType(2));
						}
					}
					if(awardEnm != null) {
						DollInfo dollInfo = null;
						int awardId = 0;
						int awardCoin = 0;
						String awardName = "";
						String icon = "";
						int awardType = awardEnm.getType();
						if(awardEnm.getType() == AwardType.COIN.type) { // 金币
							awardCoin = awardEnm.getNum();
							awardName = awardEnm.getNum() + "币";
							icon = TwistedEggMachineConstants.COIN_ICON;
						} else if(awardEnm.getType() == AwardType.LUCKYBAG.type) { // 福袋
							awardId = luckyBagId;
							dollInfo = dollInfoService.getDollInfo(awardId);
							awardName = dollInfo == null ? "" : dollInfo.getName();
							icon = TwistedEggMachineConstants.LUCKY_BAG_ICON;
						}
						
						TradeRecord tradeRecord = new TradeRecord(uid, 0,
								TradeType.TWISTEDEGG_MACHINE_COST_COIN.type, 0,
								TwistedEggMachineConstants.PLAY_COIN,
								TradeCostType.COST_COIN.type, "扭蛋机抽奖扣取游戏币"
										+ TwistedEggMachineConstants.PLAY_COIN);
						boolean result = tradeService.consume(tradeRecord);
						if(!result) {
							return new BaseRespVO(601, false, "游戏币不足~");
						}
						if(!StringUtil.isNullOrEmpty(awardName) 
								&& saveUserOptRecord(uid, 0, awardType, awardId, awardName, awardCoin , "使用金币抽奖获得【" + awardName + "】", dateCode, 0) > 0) {
							if(awardEnm.getType() == AwardType.COIN.type) { // 金币
								tradeRecord = new TradeRecord(uid, uid,
										TradeType.TWISTEDEGG_MACHINE_GET_COIN.type, 0, awardEnm.getNum(),
										TradeCostType.COST_COIN.type, "扭蛋机抽奖获得" + awardEnm.getNum() + "币");
								tradeService.charge(tradeRecord);
							} else if(awardEnm.getType() == AwardType.LUCKYBAG.type && dollInfo != null) { // 福袋
								long uDollId = userDollService.saveUserDoll(uid, awardId, 0l, "扭蛋机抽奖获得【" + dollInfo.getName() + "】", 0);
								saveUserIncome(0l, TwistedEggMachineConstants.LUCKY_BAG_MIN_INCOME_LOSS, dateCode, "抽奖获得福袋得到" + TwistedEggMachineConstants.LUCKY_BAG_MIN_INCOME_LOSS + "收益"); // 用户
								//eventTwistedEggMachineDao.saveOrUpdateUserIncome(0l, TwistedEggMachineConstants.LUCKY_BAG_MIN_INCOME_LOSS);
								// 系统通知
								String textString = "恭喜小主在扭蛋机扭出  " + dollInfo.getName();
								MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type, textString);
								msg.setJumpDoll(uDollId);
								msgService.sendMsg(msg);
							}
							// 收益
							int allCoinSum = awardCoin;
							int incomeValue = allCoinSum - TwistedEggMachineConstants.PLAY_COIN * 1;
							saveUserIncome(uid, incomeValue, dateCode, "金币抽奖一次获得" + incomeValue + "收益"); // 用户
							saveUserIncome(0l, incomeValue, dateCode, "金币抽奖一次获得" + incomeValue + "收益"); // 平台
							// 手气
							int userCurrCoinSum = awardCoin;
							int fortuneValue = TwistedEggMachineUtils.getFortuneValue(userCurrCoinSum);
							saveUserFortune(uid, dateCode, fortuneValue, "使用金币抽奖获得" + fortuneValue + "手气值"); // 用户手气
							// 消息推送
							if((awardType == 0 && awardCoin >= 33) 
									|| awardType != 0) {
								sendRewardMsg(userGeneral, awardName);
							}
							
							awardItems.add(new AwardItem(awardType, awardName, awardCoin, icon));
						}
					}
				}
			} else if(type == 1) { // 使用任务扭蛋抽奖
				if(eventTwistedEggMachineDao.updateUserTwistedEggNum(uid, 1) > 0) {
					eventTwistedEggMachineDao.saveUserTwistedEggRecord(uid, dateCode, -1, "使用任务扭蛋抽奖1次"); // 使用任务扭蛋记录
					AwardItem awardItem = eventTwistedEgg20180305Service.play(uid);
					// 消息推送
					if((awardItem.getType() == 0 && awardItem.getCoin() >= 33) 
							|| awardItem.getType() != 0) {
						sendRewardMsg(userGeneral, awardItem.getAwardName());
					}
					awardItems.add(awardItem);
				} else {
					return new BaseRespVO(600, false, "任务扭蛋数不足");
				}
			}
			respVO.setAwardItems(awardItems);
		} catch (TradeOperationException e) {
			return new BaseRespVO(-1, false, "交易失败,请重试");
		} catch (NotEnoughBeanException e) {
			return new BaseRespVO(601, false, "游戏币不足~");
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统异常，请重试~");
		}
		return respVO;
	}

	private void sendRewardMsg(UserGeneral userGeneral, String awardName) {
		try {
			int dateCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
			if(dateCode <= 20180312) dateCode = 0;
			final int SIZE = RewardMsg.values().length;
			List<EventTwistedEggMachineUserFortune> userFortunes = eventTwistedEggMachineDao.getPreviousUserFortune(10, dateCode);
			int ranking = 0;
			if(!StringUtil.isNullOrEmpty(userFortunes)) {
				for(EventTwistedEggMachineUserFortune userFortune : userFortunes) {
					if(userGeneral.getUid() == userFortune.getUid().longValue()) {
						ranking++;
						break;
					}
				}
			}
			String msgString = "";
			if(ranking > 0) {
				msgString = "恭喜 %s 在扭蛋机扭出了 %s ，位列今日手气王第" + ranking +"名";
			} else {
				msgString = RewardMsg.values()[new Random().nextInt(SIZE)].getDesc();
			}
			msgString = String.format(msgString, userGeneral.getName(), awardName);
			MsgRoom msgRoomWebp = new MsgRoom(null, userGeneral, MsgRoomType.BUS_WEBP.type, msgString);
			msgRoomWebp.initWebPMsg("http://lianai-image-sys.qiniudn.com/e20180125/xinnianpiaopin.webp");
			msgService.sendMsgRoomAll(msgRoomWebp);
			MsgRoomJump msgRoomSys = new MsgRoomJump(null, userGeneral, MsgRoomType.BUS_SYS.type, "<font color='#FF7B7B'>"+ msgString+"</font>");
			msgService.sendMsgRoomAll(msgRoomSys);
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}

	private void saveUserIncome(Long uid, int value, int dateCode, String remark) {
		if(eventTwistedEggMachineDao.saveOrUpdateUserIncome(uid, value) > 0) {
			eventTwistedEggMachineDao.saveUserIncomeRecord(uid, value, dateCode, remark);
		}
	}

	@Override
	public void updateUserTwistedeggNum(Long uid, int dateCode, int num, String remark) {
		if(eventTwistedEggMachineDao.saveOrUpdateUserTwistedEggNum(uid, num) > 0) {
			eventTwistedEggMachineDao.saveUserTwistedEggRecord(uid, dateCode, num, remark);
		}
	}

	@Override
	public int saveUserOptRecord(Long uid, int type, int awardType, int awardId, String awardName, int awardCoin,
			String remark, int dateCode, int isRobot) {
		int currTimes = eventTwistedEggMachineDao.getCurrTimes(uid, type) + 1;
		return eventTwistedEggMachineDao.saveUserOptRecord(uid, currTimes, type, awardType, awardId, awardName, awardCoin, remark, dateCode, isRobot);
	}

	@Override
	public int getCurrCoinAwardSum(Long uid, int dateCode, int type) {
		return eventTwistedEggMachineDao.getCurrCoinAwardSum(uid, dateCode, type);
	}

	@Override
	public void saveUserFortune(Long uid, int dateCode, int value, String remark) {
		int currDateCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
		if(currDateCode <= 20180312) currDateCode = 0;
		if(eventTwistedEggMachineDao.saveOrUpdateUserFortune(uid, value, currDateCode) > 0) {
			eventTwistedEggMachineDao.saveUserFortuneRecord(uid, dateCode, value, remark);
		}
	}

	@Override
	public void saveUserwistedEgg(Long uid, int dateCode, int num, String remark) {
		if(eventTwistedEggMachineDao.saveOrUpdateUserTwistedEggNum(uid, num) > 0) {
			eventTwistedEggMachineDao.saveUserTwistedEggRecord(uid, dateCode, num, remark);
		}
	}

	@Override
	public void twistedEggMachineEvery15MinsMsg() {
		final int SIZE = Every15MinsMsg.values().length;
		String msgString = Every15MinsMsg.values()[new Random().nextInt(SIZE)].getDesc();
		MsgRoomJump msgRoomSys = new MsgRoomJump(null, null, MsgRoomType.BUS_SYS.type, "<font color='#fff203'>"+ msgString+"</font>");
		msgRoomSys.setJumpUrl(H5_URL);
		msgService.sendMsgRoomAll(msgRoomSys);
	}

	@Override
	public void twistedEggMachineRankingMsg() {
		int dateCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
		if(dateCode <= 20180312) dateCode = 0;
		List<EventTwistedEggMachineUserFortune> userFortunes = eventTwistedEggMachineDao.getPreviousUserFortune(10, dateCode);
		int ranking = 1;
		if(!StringUtil.isNullOrEmpty(userFortunes)) {
			for(EventTwistedEggMachineUserFortune userFortune : userFortunes) {
				String textString = "小主暂列扭蛋机的手气王榜单第" + ranking + "名，“任务扭蛋”开出的奖品更丰富哟~";
				MsgNotice msg=new MsgNotice(userFortune.getUid(), MsgType.NOTICE_SYS.type, textString);
				UserBase userBase = userDAO.getUserBase(userFortune.getUid());
				msg.setJumpWeb(H5_URL + "?uid=" + userFortune.getUid() + "&loginKey=" + userBase.getLoginKey());
				msg.setPushMsg(textString);
				msg.setTargetTitle("扭蛋机");
				msg.setTargetName("扭蛋机");
				msgService.sendMsg(msg);
				ranking++;
			}
		}
	}
}
