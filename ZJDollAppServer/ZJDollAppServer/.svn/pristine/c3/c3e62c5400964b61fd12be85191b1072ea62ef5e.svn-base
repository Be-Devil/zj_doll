package com.imlianai.zjdoll.app.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCardLog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.enm.ChargeCardRewardType;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.enm.RemindRewardMsg;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.service.ChargeCatalogService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;

/**
 * 没领周月卡奖励时消息推送
 * @author cls
 *
 */
@Component
public class WeekMonthCardTask {
	
	@Resource
	ChargeCatalogService chargeCatalogService;
	@Resource
	MsgService msgService;

	public void pushRemindRewardMsg() {
		int dateCode = Integer.parseInt(DateUtils.getCurrentDateString("yyyyMMdd")); 
		List<ChargeCardLog> weekChargeCardLogs = chargeCatalogService.getValidChargeCardLogsByType(2, dateCode);
		List<ChargeCardLog> monthChargeCardLogs = chargeCatalogService.getValidChargeCardLogsByType(3, dateCode);
		List<Long> weekCardUids = new ArrayList<Long>(); // 开通周卡的用户
		List<Long> monthCardUids = new ArrayList<Long>(); // 开通月卡的用户
		if(!StringUtil.isNullOrEmpty(weekChargeCardLogs)) {
			for(ChargeCardLog log : weekChargeCardLogs) {
				weekCardUids.add(log.getUid());
			}
		}
		if(!StringUtil.isNullOrEmpty(monthChargeCardLogs)) {
			for(ChargeCardLog log : monthChargeCardLogs) {
				monthCardUids.add(log.getUid());
			}
		}
		// 未领取奖励的用户
		weekCardUids = getNotReceiveUids(weekCardUids, dateCode, 2);
		monthCardUids = getNotReceiveUids(monthCardUids, dateCode, 3);
		pushMsg(monthCardUids, 3); // 月卡
		List<Long> newWeekCardUids = new ArrayList<Long>();
		// 同时充了周卡和月卡，只推送一条月卡
		if(!StringUtil.isNullOrEmpty(monthCardUids)) {
			if(!StringUtil.isNullOrEmpty(weekCardUids)) {
				for(Long uid : weekCardUids) {
					if(!monthCardUids.contains(uid)) {
						newWeekCardUids.add(uid);
					}
				}
			}
		} else {
			newWeekCardUids = weekCardUids;
		}
		pushMsg(newWeekCardUids, 2); // 周卡
	}

	private List<Long> getNotReceiveUids(List<Long> uids, int dateCode, int type) {
		if(!StringUtil.isNullOrEmpty(uids)) {
			List<Long> receiveUids = chargeCatalogService.getNotReceiveAwardUids(uids, type, dateCode);
			List<Long> uidsTmp = new ArrayList<Long>();
			if(!StringUtil.isNullOrEmpty(receiveUids)) {
				for(Long uid : uids) {
					if(!receiveUids.contains(uid.longValue())) {
						uidsTmp.add(uid);
					}
				}
				uids = uidsTmp;
			} 
		}
		return uids;
	}

	private void pushMsg(List<Long> uids, int type) {
		ChargeCardRewardType rewardType = ChargeCardRewardType.getRewardType(type);
		final int msgSize = RemindRewardMsg.values().length;
		String expireTime = DateUtils.getCurrentDateString("yyyy-MM-dd") + " 23:59:59"; // 过期时间
		if(!StringUtil.isNullOrEmpty(uids)) {
			for(Long uid : uids) {
				String body = RemindRewardMsg.getDesc(new Random().nextInt(msgSize) + 1);
				body = body.replace("X", rewardType.getDesc());
				body = body.replace("Y", rewardType.getCoin()+"");
				// 系统通知
				Msg msg = new Msg(uid , MsgType.NOTICE_SYS.type, body);
				msg.setExpireTime(expireTime);
				msgService.sendMsg(msg);
			}
		}
	}
}
