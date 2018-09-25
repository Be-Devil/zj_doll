package com.imlianai.zjdoll.app.modules.core.trade.callback.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.annotation.Resource;

import com.imlianai.zjdoll.app.modules.core.egg.trade.EggTradeService;
import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.zjdoll.domain.log.LogPage;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.zjdoll.domain.trade.TradeCharge;
import com.imlianai.zjdoll.domain.trade.TradeChargeType;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserAttribute;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.rpc.support.utils.SysTimerHandle;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.constants.CatalogConstants;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.dao.ChargeCatalogDao;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeDayAwardRecord;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeDayRecord;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog.ChargeCatalogType;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.enm.NewChargeCardRewardType;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.service.ChargeCatalogService;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.utils.CatalogUtils;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.attribute.UserAttributeService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.core.user.value.UserValueDAO;
import com.imlianai.zjdoll.app.modules.core.user.value.UserValueService;
import com.imlianai.zjdoll.app.modules.publics.log.service.LogService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.UserWechat;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.service.WechatService;
import com.imlianai.zjdoll.app.modules.support.dailytask.service.DailytaskService;
import com.imlianai.zjdoll.app.modules.support.event.newyearRecharge20180207.service.EventNewyearRecharge20180207Service;
import com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.service.EventTwistedEgg20180305Service;
import com.imlianai.zjdoll.app.modules.support.ranking.service.RankingService;
import com.imlianai.zjdoll.app.modules.support.shipping.service.ShippingService;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;

@Service
public class PayBackExcuteServiceImpl implements PayBackExcuteService {

	private final BaseLogger logger = BaseLogger
			.getLogger(PayBackExcuteServiceImpl.class);

	@Resource
	private UserService userService;

	@Resource
	private UserAttributeService userAttributeService;
	@Resource
	private TradeChargeService tradeChargeService;
	@Resource
	private ChargeCatalogService chargeCatalogService;
	@Resource
	private TradeService tradeService;
	@Resource
	private LogService logService;

	@Resource
	private MsgService msgService;
	@Resource
	ChargeCatalogDao chargeCatalogDao;
	@Resource
	UserDollService userDollService;
	@Resource
	UserValueDAO userValueDAO;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	WechatService wechatService;
	@Resource
	EventNewyearRecharge20180207Service eventNewyearRecharge20180207Service;
	@Resource
	DailytaskService dailytaskService;
	@Resource
	UserValueService userValueService;
	@Resource
	EventTwistedEgg20180305Service eventTwistedEgg20180305Service;

	@Resource
	RankingService rankingService;
	
	@Resource
	ShippingService shippingService;

	@Resource
	EggTradeService eggTradeService;

	@Override
	public void commonExcute(TradeCharge c) {
		try {
			logService.add(LogPage.CHARGE, c, this.getClass());
			ChargeCatalog item = chargeCatalogService.getCatalog(c
					.getItemCode());
			StringBuffer msgBuffer = new StringBuffer();
			StringBuffer tradeBuffer = new StringBuffer();
			boolean jumpCharg = true;
			int isFirst = item.getIsFirst();
			if (c.getChargeType() == TradeChargeType.SHIPPING_BILL.type) {//发货订单付费
				shippingService.handleShippingPayed(c);
				//eggTradeService.handlePayment(c);
			} else {
				if (isFirst == 2 || isFirst == 3) { // 周月卡
					handleWeekMonthCard(isFirst, c, item);
				} else {
					if (isFirst == 1) {
						long lastId = tradeChargeService.hasChargeSpecialAmt(
								c.getUid(), item.getCode());
						if (lastId != c.getId()) {// 成功的订单不是此笔订单
							item.setAwardExtra(0);
						}
					}
					if (c.getChargeType() == TradeChargeType.PAY4ME.type) {// 亲密付
						UserWechat userWechat = wechatService.getUserWechat(c
								.getImei());
						tradeBuffer = new StringBuffer("充值 获得亲密付"
								+ item.getCoin() + "币");
						if (userWechat == null) {
							msgBuffer = new StringBuffer("您不愿透露姓名的朋友给您充值了"
									+ c.getCost() + "元，"
									+ (item.getCoin() + item.getAwardExtra())
									+ "游戏币已发放到您的账户");
						} else {
							msgBuffer = new StringBuffer("您的朋友 "
									+ userWechat.getName() + " 给您充值了"
									+ c.getCost() + "元，"
									+ (item.getCoin() + item.getAwardExtra())
									+ "游戏币已发放到您的账户");
						}
					} else {
						msgBuffer = new StringBuffer("恭喜，充值成功！充值金额：");
						tradeBuffer = new StringBuffer("充值" + c.getCost()
								+ "元，获得" + item.getCoin() + "币");
						msgBuffer.append(c.getCost());
						msgBuffer.append("元，获得" + item.getCoin() + "币");
						if (item.getAwardExtra() > 0) {
							msgBuffer.append("，额外赠送");
							msgBuffer.append(item.getAwardExtra());
							msgBuffer.append("币");
							tradeBuffer.append("，额外赠送");
							tradeBuffer.append(item.getAwardExtra());
							tradeBuffer.append("币");
						}
						//时光卷
						if (item.getTimeCoupons() > 0) {
							msgBuffer.append("，额外赠送");
							msgBuffer.append(item.getTimeCoupons());
							msgBuffer.append("时光券");
							tradeBuffer.append("+");
							tradeBuffer.append(item.getTimeCoupons());
							tradeBuffer.append("时光券");
							TradeRecord record = new TradeRecord(c.getUid(),
									c.getUid(), TradeType.CHARGE.type, item.getCode(),
									item.getTimeCoupons(),
									TradeCostType.TIMECOUPON.type,
									"充值额外赠送 " + item.getTimeCoupons() + " 张时光卷");
							tradeService.charge(record);
						}
					}
					TradeRecord record = new TradeRecord(c.getUid(),
							c.getUid(), TradeType.CHARGE.type, item.getCode(),
							item.getCoin() + item.getAwardExtra(),
							TradeCostType.COST_COIN.type,
							tradeBuffer.toString());
					tradeService.charge(record);
					TradeAccount account = tradeService.getAccount(c.getUid());
					if (account != null) {
						msgBuffer.append("，当前游戏币余额：");
						msgBuffer.append(account.getCoin());
					}
					MsgNotice msg = new MsgNotice(c.getUid(),
							MsgType.NOTICE_SYS.type, msgBuffer.toString());
					msg.setPushMsg(msgBuffer.toString());
					if (jumpCharg) {
						msg.setJumpCharge();
					}
					msgService.sendMsg(msg);
				}
				handleChargePush(c, item);
				userAttributeService
						.incTotalCharge(c.getUid(), item.getPrice());
				rankingService.handleRecharge(c.getCost(), c.getUid());
				eventNewyearRecharge20180207Service.handleRecharge(c.getCost(),
						c.getUid()); // 新春充值活动
				eventTwistedEgg20180305Service.handleRecharge(c.getCost(),
						c.getUid()); // 活动-任务扭蛋
				dailytaskService.handleRecharge(c.getCost(), c.getUid()); // 每日任务
			}
		} catch (Exception e) {
			logger.info(e);
			logger.error(e, e);
		}
	}

	private void handleChargePush(TradeCharge c, ChargeCatalog item) {
		try {
			tradeChargeService.delUserFirstChargeMsg(c.getUid());
			if (item.getIsFirst() == 1) {
				if (item.getType() == ChargeCatalogType.CHARGE_PUSH.type) {
					tradeChargeService.removeFirstChargeTarget(c.getUid());
				}
				if (item.getPrice() == 6) {// 6元档位
					UserAttribute userAttribute = userAttributeService
							.getUserAttribute(c.getUid());
					int type = CatalogUtils.code20;
					if (userAttribute.getTotalCharge() > 300) {
						type = CatalogUtils.code50;
					}
					tradeChargeService
							.addFirstChargeTarget(c.getUid(), type, 6);
				}
			}
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
	}

	private void handleWeekMonthCard(int isFirst, TradeCharge c,
			ChargeCatalog item) {
		try {
			StringBuffer msgBuffer = new StringBuffer();
			boolean jumpCharg = true;
			StringBuffer loginRewardSbuf = new StringBuffer(); // 周月卡登录奖励消息
			int validDays = isFirst == 2 ? 7 : 30; // 有效天数
			int startDate = Integer.parseInt(DateUtils
					.getCurrentDateString("yyyyMMdd"));
			int endDate = Integer.parseInt(DateUtils.dateToString(
					DateUtils.addDate(new Date(), validDays - 1), "yyyyMMdd"));
			if (chargeCatalogService.saveChargeCardLog(isFirst, c.getUid(),
					startDate, endDate) > 0) { // 保存开通周月卡的记录
				String cardType = isFirst == 2 ? "周卡" : "月卡";
				int dayReward = NewChargeCardRewardType.getCoin(isFirst); // 登录可领取的奖励(币)
				TradeRecord record = new TradeRecord(c.getUid(), c.getUid(),
						TradeType.CHARGE.type, item.getCode(), item.getCoin()
								+ item.getAwardExtra(),
						TradeCostType.COST_COIN.type, cardType + "充值"
								+ c.getCost() + "元，获得" + item.getCoin() + "币");
				tradeService.charge(record);
				TradeAccount account = tradeService.getAccount(c.getUid());
				msgBuffer = new StringBuffer("恭喜你成功开通");
				msgBuffer.append(isFirst == 2 ? "劲爆周卡" : "双倍月卡");
				String startDateStr = DateUtils.dateToString(new Date(),
						"yyyy.MM.dd");
				String endDateStr = DateUtils.dateToString(
						DateUtils.addDate(new Date(), validDays), "yyyy.MM.dd");
				msgBuffer.append("（有效期：" + startDateStr + "-" + endDateStr
						+ "），");
				msgBuffer.append("到账" + (isFirst == 2 ? 280 : 980) + "币，账户余额"
						+ account.getCoin() + "币；");
				msgBuffer.append("有效期内，每日登录还可领取" + dayReward + "币奖励~");
				// 周月卡充值-登录领取的奖励
				record = new TradeRecord(c.getUid(), c.getUid(),
						TradeType.CHARGE.type, 0, dayReward,
						TradeCostType.COST_COIN.type, "开通" + cardType
								+ "每天登录领取奖励,获得" + dayReward + "币");
				tradeService.charge(record);
				int currDate = Integer.parseInt(DateUtils
						.getCurrentDateString("yyyyMMdd"));
				chargeCatalogDao.saveChargeCardReceiveAwardLog(c.getUid(),
						currDate, isFirst); // 保存奖励领取记录
				account = tradeService.getAccount(c.getUid());
				loginRewardSbuf = new StringBuffer("恭喜你领取了");
				loginRewardSbuf.append(cardType + "每日登录奖励" + dayReward
						+ "币，账户余额" + account.getCoin() + "币；");
				loginRewardSbuf.append("还有" + (validDays - 1)
						+ "天可领取，明天记得登录以领取奖励哦~");
				jumpCharg = false;
			}
			MsgNotice msg = new MsgNotice(c.getUid(), MsgType.NOTICE_SYS.type,
					msgBuffer.toString());
			msg.setPushMsg(msgBuffer.toString());
			if (jumpCharg) {
				msg.setJumpCharge();
			}
			msgService.sendMsg(msg);
			// 周月卡登录奖励推送
			if (!StringUtil.isNullOrEmpty(loginRewardSbuf.toString())) {
				final long uid = c.getUid();
				final String body = loginRewardSbuf.toString();
				SysTimerHandle.execute(new TimerTask() {
					@Override
					public void run() {
						MsgNotice rewardMsg = new MsgNotice(uid,
								MsgType.NOTICE_SYS.type, body);
						rewardMsg.setPushMsg(body);
						msgService.sendMsg(rewardMsg);
					}
				}, 3);
			}
		} catch (Exception e) {
			logger.info(e);
			logger.error(e, e);
		}
	}

	private void handleRechargeEvent(long uid, double cost) {
		try {
			int dateCode = Integer.parseInt(DateUtils
					.getCurrentDateString("yyyyMMdd"));
			ChargeDayRecord chargeDayRecord = chargeCatalogService
					.getChargeDayRecord(uid, dateCode);
			double rechargeAmt = chargeDayRecord == null ? 0 : chargeDayRecord
					.getCost();
			double newRechargeAmt = rechargeAmt + cost;
			ChargeDayAwardRecord chargeDayAwardRecord = null;
			StringBuilder rewardSb = new StringBuilder();
			if (newRechargeAmt >= 188) {
				chargeDayAwardRecord = chargeCatalogService
						.getChargeDayAwardRecord(uid, dateCode, 188);
				if (chargeDayAwardRecord == null) { // 充值满188的奖励还未发放
					chargeDayAwardRecord = chargeCatalogService
							.getChargeDayAwardRecord(uid, dateCode, 88);
					List<Integer> dollIds = new ArrayList<Integer>();
					if (chargeDayAwardRecord == null) { // 充值满88的奖励还未发放
						dollIds.add(CatalogConstants.RECHARGE_88_DOLLID);
						dollIds.add(CatalogConstants.RECHARGE_188_DOLLID1);
						dollIds.add(CatalogConstants.RECHARGE_188_DOLLID2);
					} else {
						dollIds.add(CatalogConstants.RECHARGE_188_DOLLID1);
						dollIds.add(CatalogConstants.RECHARGE_188_DOLLID2);
					}
					StringBuilder dollsSb = new StringBuilder();
					for (int i = 0; i < dollIds.size(); i++) {
						DollInfo dollInfo = dollInfoService.getDollInfo(dollIds
								.get(i));
						if (dollInfo == null)
							continue;
						saveUserDoll(dollInfo, uid);
						if (i == 0) {
							rewardSb.append("日充值满188元，恭喜小主获得充值大礼包，"
									+ dollInfo.getName());
							dollsSb.append("奖励娃娃-" + dollInfo.getName() + "、");
						} else if (i == dollIds.size() - 1) {
							rewardSb.append(dollInfo.getName());
							dollsSb.append(dollInfo.getName());
						} else {
							rewardSb.append(dollInfo.getName() + "、");
							dollsSb.append(dollInfo.getName() + "、");
						}
						if (i == dollIds.size() - 1) {
							rewardSb.append("已发放到背包~~");
						}
					}
					if (!StringUtil.isNullOrEmpty(dollsSb.toString())) {
						chargeCatalogService.saveChargeDayAwardRecord(uid,
								dateCode, 188, dollsSb.toString());
					}
				}
			} else if (newRechargeAmt >= 88 && newRechargeAmt < 188) {
				chargeDayAwardRecord = chargeCatalogService
						.getChargeDayAwardRecord(uid, dateCode, 88);
				if (chargeDayAwardRecord == null) { // 充值满88的奖励还未发放
					DollInfo dollInfo = dollInfoService
							.getDollInfo(CatalogConstants.RECHARGE_88_DOLLID);
					if (dollInfo != null) {
						saveUserDoll(dollInfo, uid);
						rewardSb.append("日充值满88元，恭喜小主获得充值大礼包，"
								+ dollInfo.getName() + "已发放到背包~~");
						chargeCatalogService.saveChargeDayAwardRecord(uid,
								dateCode, 88, "奖励娃娃-" + dollInfo.getName());
					}
				}
			}
			chargeCatalogService.saveOrUpdateChargeDayRecord(uid, dateCode,
					cost);
			if (!StringUtil.isNullOrEmpty(rewardSb.toString())) {
				// 系统通知
				Msg msg = new Msg(uid, MsgType.NOTICE_SYS.type,
						rewardSb.toString());
				msgService.sendMsg(msg);
			}
		} catch (Exception e) {
			logger.info("充值活动奖励处理异常:" + e);
			logger.error(e, e);
		}
	}

	private void saveUserDoll(DollInfo dollInfo, long uid) {
		UserDoll userDoll = new UserDoll();
		userDoll.setDollId(dollInfo.getDollId());
		userDoll.setUid(uid);
		userDoll.setOptId(0);
		userDoll.setStatus(0); // 寄存中
		userDoll.setRemark("充值活动奖励" + dollInfo.getName());
		userDoll.setGoodsType(dollInfo.getGoodsType());
		userDollService.saveUserDoll(userDoll); // 保存用户抓到的娃娃
		//userValueService.addDollNum(uid, 1);
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.dateToString(new Date(1477324799000l),
				DateUtils.DATETIME_PATTERN));
		System.out.println(Integer.parseInt(DateUtils.dateToString(
				DateUtils.addDate(new Date(), 7), "yyyyMMdd")));
	}
}
