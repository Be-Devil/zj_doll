package com.imlianai.zjdoll.app.modules.core.trade.catalog.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.constants.CatalogConstants;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.dao.ChargeCatalogDao;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCardExpirePushLog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCardLog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCardReceiveAwardLog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeDayAwardRecord;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeDayRecord;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.UserFirstChargeTarget;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.enm.ChargeCardRewardType;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.enm.NewChargeCardRewardType;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;

@Service
public class ChargeCatalogServiceImpl implements ChargeCatalogService {
	// 苹果大号起始编号
	public static final int APP_STORE_INIT_CODE = 1100;
	@Resource
	private ChargeCatalogDao chargeCatalogDao;

	@Resource
	private TradeChargeService tradeChargeService;
	@Resource
	TradeService tradeService;
	@Resource
	MsgService msgService;
	private final BaseLogger logger = BaseLogger
			.getLogger(ChargeCatalogServiceImpl.class);

	@Override
	public List<ChargeCatalog> getCatalogs(int type, int isFirst) {
		return chargeCatalogDao.getCatalogs(type, isFirst);
	}

	@Override
	public List<ChargeCatalog> getFirstPayCatalogs(int type, long uid) {
		List<ChargeCatalog> reslist =new ArrayList<ChargeCatalog>();
		List<ChargeCatalog> list = getCatalogs(type, 1);
		if (!StringUtil.isNullOrEmpty(list)) {
			for (ChargeCatalog chargeCatalog : list) {
				if(tradeChargeService.hasChargeSpecialAmt(uid, chargeCatalog.getCode())==0){
					//特殊显示用户
					reslist.add(chargeCatalog);
				}
			}
		}
		//首冲衍生特殊充值
		UserFirstChargeTarget code=tradeChargeService.getUserFirstChargeTarget(uid);
		if (code!=null&&code.getCode()>0) {
			ChargeCatalog chargeCatalog=getCatalog(code.getCode());
			if (chargeCatalog!=null) {
				reslist.add(chargeCatalog);
			}
		}
		return reslist;
	}

	@Override
	public ChargeCatalog getCatalog(int code) {
		ChargeCatalog chargeCatalog = chargeCatalogDao.getCatalog(code);
		logger.info("getCatalog code:" + code + " chargeCatalog:"
				+ JSON.toJSONString(chargeCatalog));
		return chargeCatalog;
	}

	@Override
	public List<ChargeCatalog> handleIosCatalogs(String channel,
			List<ChargeCatalog> list) {
		int initCode = chargeCatalogDao.getChargeCodeByChannel(channel);// 初始枚举值
		logger.info("CmdGetChargeCatalog channel:" + channel + " initCode:"
				+ initCode);
		if (initCode != 0 && list != null) {
			List<ChargeCatalog> reslist = new ArrayList<ChargeCatalog>();
			for (ChargeCatalog chargeCatalog : list) {
				if (chargeCatalog.getCode() == 1100
						|| chargeCatalog.getCode() == 1122) {
					continue;
				}
				int currentCode = initCode + chargeCatalog.getCode()
						- APP_STORE_INIT_CODE;
				logger.info("CmdGetChargeCatalog channel:" + channel
						+ " initCode:" + initCode + " chargeCatalog.getCode():"
						+ chargeCatalog.getCode() + " currentCode:"
						+ currentCode);
				if (currentCode > 0) {
					chargeCatalog.setCode(currentCode);
				}
				reslist.add(chargeCatalog);
			}
			return reslist;
		}
		return list;
	}

	@Override
	public List<ChargeCatalog> getQuickCatalogs(int type) {
		return chargeCatalogDao.getCatalogs(type, 1, 0);
	}

	@Override
	public List<ChargeCatalog> getWeekMonthCardCatalogs(int chargeOsType, Long uid) {
		List<ChargeCatalog> reslist = new ArrayList<ChargeCatalog>();
		List<ChargeCatalog> list = chargeCatalogDao.getWeekMonthCardCatalogs(chargeOsType);
		if(!StringUtil.isNullOrEmpty(list)) {
			int dateCode = Integer.parseInt(DateUtils.getCurrentDateString("yyyyMMdd"));
			for(ChargeCatalog chargeCatalog : list) {
				ChargeCardLog chargeCardLog = chargeCatalogDao.getNewestChargeCardLog(uid, chargeCatalog.getIsFirst());
				if(chargeCardLog == null 
						|| chargeCardLog.getEndDate() < dateCode) { // 没有周月卡充值记录or已过期
					reslist.add(chargeCatalog);
				}
			}
		}
		return reslist;
	}

	@Override
	public int saveChargeCardLog(int isFirst, long uid, int startDate, int endDate) {
		return chargeCatalogDao.saveChargeCardLog(isFirst, uid, startDate, endDate);
	}

	@Override
	public void handleLoginReward(Long uid) {
		try {
			grantReward(2, "周卡", ChargeCardRewardType.getCoin(2), uid); // 发放周奖励
			grantReward(3, "月卡", ChargeCardRewardType.getCoin(3), uid); // 发放月奖励
		} catch(Exception e) {
			PrintException.printException(logger, e);
		}
	}
	
	private void grantReward(int isFirst, String cardType, int dayReward, long uid) throws Exception {
		int currDate = Integer.parseInt(DateUtils.getCurrentDateString("yyyyMMdd"));
		ChargeCardLog chargeCardLog = chargeCatalogDao.getNewestChargeCardLog(uid, isFirst); // 开通周月卡信息
		if(chargeCardLog != null && chargeCardLog.getCreateTime().getTime() >= DateUtils.stringToDate(CatalogConstants.NEW_WEEKMONTHCARD_TIME, "yyyy-MM-dd HH:mm:ss").getTime()) {
			dayReward = NewChargeCardRewardType.getCoin(isFirst);
		}
		if(chargeCardLog != null && chargeCardLog.getEndDate() >= currDate) {
			ChargeCardReceiveAwardLog reAwardLog = chargeCatalogDao.getChargeCardReceiveAwardLog(uid, currDate, isFirst);
			if(reAwardLog == null) { // 今日还未领取奖励
				int days = daysBetweenNow(currDate+"", chargeCardLog.getEndDate()+"");
				TradeRecord record = new TradeRecord(uid, uid,
						TradeType.CHARGE.type, 0, dayReward,
						TradeCostType.COST_COIN.type, "开通" + cardType + "每天登录领取奖励,获得" + dayReward + "币");
				tradeService.charge(record);
				chargeCatalogDao.saveChargeCardReceiveAwardLog(uid, currDate, isFirst); // 保存奖励领取记录
				TradeAccount account = tradeService.getAccount(uid);
				StringBuffer rewardMsgSb = new StringBuffer("恭喜你领取了");
				rewardMsgSb.append(cardType + "每日登录奖励" + dayReward + "币，账户余额" + account.getCoin() + "币；");
				if(days > 0) {
					rewardMsgSb.append("还有" + days + "天可领取，明天记得登录以领取奖励哦~");
				} else if(days == 0) { // 今天最后一天
					rewardMsgSb.append("你的" + cardType + "即将过期，明天可以继续购买哦~");
				}
				MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type,
						rewardMsgSb.toString());
				msg.setPushMsg(rewardMsgSb.toString());
				msgService.sendMsg(msg);
			}
		} else if(chargeCardLog != null && chargeCardLog.getEndDate() < currDate) {
			ChargeCardExpirePushLog log = chargeCatalogDao.getChargeCardExpirePushLog(chargeCardLog.getId());
			if(log == null) {
				if(chargeCatalogDao.saveChargeCardExpirePushLog(chargeCardLog.getId(), uid) > 0) {
					String body = "亲，你的" + cardType + "已失效，前往充值页面重新购买" + cardType + "可继续享受优惠哦~";
					MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type, body);
					msg.setPushMsg(body);
					msg.setJumpCharge();
					msgService.sendMsg(msg);
				}
			}
		}
	}
	
	public static int daysBetweenNow(String startDateStr, String endDateStr) throws ParseException {
		if (StringUtils.isBlank(endDateStr) || StringUtils.isBlank(startDateStr))
			return 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(endDateStr));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(startDateStr));
		long time2 = cal.getTimeInMillis();
		long between_days = (time1 - time2) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	@Override
	public List<ChargeCardLog> getValidChargeCardLogsByType(int type, int dateCode) {
		return chargeCatalogDao.getValidChargeCardLogsByType(type, dateCode);
	}

	@Override
	public List<Long> getNotReceiveAwardUids(List<Long> uids, int type, int dateCode) {
		return chargeCatalogDao.getNotReceiveAwardUids(uids, type, dateCode);
	}

	@Override
	public ChargeDayRecord getChargeDayRecord(long uid, int dateCode) {
		return chargeCatalogDao.getChargeDayRecord(uid, dateCode);
	}

	@Override
	public ChargeDayAwardRecord getChargeDayAwardRecord(long uid, int dateCode, int amount) {
		return chargeCatalogDao.getChargeDayAwardRecord(uid, dateCode, amount);
	}

	@Override
	public int saveChargeDayAwardRecord(long uid, int dateCode, int amount, String remark) {
		return chargeCatalogDao.saveChargeDayAwardRecord(uid, dateCode, amount, remark);
	}

	@Override
	public int saveOrUpdateChargeDayRecord(long uid, int dateCode, double cost) {
		return chargeCatalogDao.saveOrUpdateChargeDayRecord(uid, dateCode, cost);
	}

	@Override
	public boolean isValid(Long uid) {
		int dateCode = Integer.parseInt(DateUtils.getCurrentDateString("yyyyMMdd"));
		List<ChargeCardLog> logs = chargeCatalogDao.getUserValidChargeCardLogsByUid(uid, dateCode);
		if(!StringUtil.isNullOrEmpty(logs)) {
			return true;
		}
		return false;
	}
}
