package com.imlianai.zjdoll.app.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.busowner.BusOwner;
import com.imlianai.zjdoll.domain.busowner.BusOwnerBusIncome;
import com.imlianai.zjdoll.domain.busowner.BusOwnerUserIndex;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgRoom;
import com.imlianai.zjdoll.domain.msg.MsgRoomJump;
import com.imlianai.zjdoll.domain.msg.MsgRoomType;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.publics.utils.ArithmeticUtils;
import com.imlianai.zjdoll.app.modules.support.busowner.constants.BusOwnerConstants;
import com.imlianai.zjdoll.app.modules.support.busowner.enm.RewardRatioEnm;
import com.imlianai.zjdoll.app.modules.support.busowner.service.BusOwnerService;

/**
 * 产生萌主、奖励发放的定时任务
 * @author cls
 *
 */
@Component
public class BusOwnerTask {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(BusOwnerTask.class);
	
	@Resource
	BusOwnerService busOwnerService;
	@Resource
	UserService userService;
	@Resource
	MsgService msgService;
	@Resource
	DollBusService dollBusService;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	TradeService tradeService;
	
	public void busOwnerTask() {
		generateBusOwners(); // 产生萌主
		handleReward(); // 奖励处理
	}

	private void generateBusOwners() {
		try {
			LOG.info("BusOwnerTask-generateBusOwners");
			List<BusOwnerUserIndex> busOwners = new ArrayList<BusOwnerUserIndex>();
			Map<Integer, UserGeneral> newBusOwnerMap = new HashMap<Integer, UserGeneral>(); // 最新萌主Map
			Map<Integer, UserGeneral> oldBusOwnerMap = new HashMap<Integer, UserGeneral>(); // 上一轮萌主Map
			Map<Integer, UserGeneral> changeBusOwnerMap = new HashMap<Integer, UserGeneral>(); // 改变的萌主Map
			int startCode = Integer.parseInt(DateUtils.dateToString(DateUtils.addDate(new Date(), -1), "yyyyMMdd"));
			int endCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
			List<Integer> busIds = busOwnerService.getBusOwnerBusIds(startCode, endCode);
			if(!StringUtil.isNullOrEmpty(busIds)) {
				for(Integer busId : busIds) {
					List<BusOwnerUserIndex> userIndexList = busOwnerService.getBusOwnerUserIndexByParams(busId, 3, startCode, endCode);
					if(!StringUtil.isNullOrEmpty(userIndexList)) {
						for(int i = 0; i < userIndexList.size(); i++) {
							BusOwnerUserIndex userIndex = userIndexList.get(i);
							String msg = "";
							RewardRatioEnm rewardRatioEnm = RewardRatioEnm.getRewardRatioEnm(i+1);
							if(i == 0) { // 机器的萌主
								busOwners.add(userIndex);
								UserGeneral userGeneral = userService.getUserGeneral(userIndex.getUid());
								busOwnerService.saveBusOwner(userIndex.getUid(), userIndex.getBusId(), startCode, endCode); // 保存萌主
								newBusOwnerMap.put(userIndex.getBusId(), userGeneral);
								msg = "恭喜小主成为" + busId + "号机的萌主，可享受全站入场座驾、24小时后将获得房间游戏币收入的" + rewardRatioEnm.getRatio()*100 +"%奖励！";
							} else {
								msg = "恭喜小主获得" + busId + "号机的机主指数第" +(i+1) + "名，24小时后可获得房间游戏币收入的" + rewardRatioEnm.getRatio()*100 +"%奖励！";
							}
							pushJumpMsg(userIndex.getUid(), msg, BusOwnerConstants.HOMEPAGE_H5_URL, userIndex.getBusId());
						}
					}
				}
				busOwnerService.initBusOwners(); // 更新萌主
			}
			endCode = startCode;
			startCode = Integer.parseInt(DateUtils.dateToString(DateUtils.addDate(new Date(), -2), "yyyyMMdd"));
			List<BusOwner> busOwnerList = busOwnerService.getBusOwner(startCode, endCode); // 上一轮萌主列表
			if(!StringUtil.isNullOrEmpty(busOwnerList)) {
				if(!StringUtil.isNullOrEmpty(busOwners)) {
					for(BusOwner busOwner : busOwnerList) {
						UserGeneral userGeneral = userService.getUserGeneral(busOwner.getUid());
						oldBusOwnerMap.put(busOwner.getBusId(), userGeneral);
					}
					for(Entry<Integer, UserGeneral> entry : oldBusOwnerMap.entrySet()) {
						Integer busId = entry.getKey();
						UserGeneral userGeneral = entry.getValue();
						if(!newBusOwnerMap.containsKey(busId) 
								|| newBusOwnerMap.get(busId).getUid() != userGeneral.getUid()) { // 1:萌主->无萌主  2:萌主A->萌主B
							changeBusOwnerMap.put(busId, newBusOwnerMap.get(busId));
						}
					}
					for(Entry<Integer, UserGeneral> entry : newBusOwnerMap.entrySet()) {
						Integer busId = entry.getKey();
						UserGeneral userGeneral = entry.getValue();
						if(!oldBusOwnerMap.containsKey(busId)) { // 3:无萌主->萌主
							changeBusOwnerMap.put(busId, userGeneral);
						}
					}
				} else {
					for(Entry<Integer, UserGeneral> entry : oldBusOwnerMap.entrySet()) {
						oldBusOwnerMap.put(entry.getKey(), null);
					}
				}
			} else {
				changeBusOwnerMap = newBusOwnerMap;
			}
			if(!StringUtil.isNullOrEmpty(newBusOwnerMap)) {
				pushBusOwnerNotice(newBusOwnerMap); // 萌主全站弹幕
			}
			if(!StringUtil.isNullOrEmpty(changeBusOwnerMap)) {
				pushUpdateBusOwnerMsg(changeBusOwnerMap); // 更新房间萌主信息
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}

	private void handleReward() {
		try {
			LOG.info("BusOwnerTask-handleReward");
			int startCode = Integer.parseInt(DateUtils.dateToString(DateUtils.addDate(new Date(), -2), "yyyyMMdd"));
			int endCode = Integer.parseInt(DateUtils.dateToString(DateUtils.addDate(new Date(), -1), "yyyyMMdd"));
			int inComeStartCode = Integer.parseInt(DateUtils.dateToString(DateUtils.addDate(new Date(), -1), "yyyyMMdd"));
			int inComeEndCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
			List<BusOwner> busOwnerList = busOwnerService.getBusOwner(startCode, endCode); // 上一轮萌主列表
			if(!StringUtil.isNullOrEmpty(busOwnerList)) {
				for(BusOwner busOwner : busOwnerList) {
					List<BusOwnerUserIndex> userIndexList = busOwnerService.getBusOwnerUserIndexByParams(busOwner.getBusId(), 3, startCode, endCode);
					if(!StringUtil.isNullOrEmpty(userIndexList)) {
						BusOwnerBusIncome busIncome = busOwnerService.getBusIncomeByParams(busOwner.getBusId(), inComeStartCode, inComeEndCode);
						if(busIncome != null && busIncome.getCoin() > 0) {
							for(int i = 0; i < userIndexList.size(); i++) {
								BusOwnerUserIndex userIndex = userIndexList.get(i);
								double ratio = RewardRatioEnm.getRewardRatioEnm(i+1).getRatio(); // 奖励比例
								int rewardCoin = new Double(ArithmeticUtils.multiply(busIncome.getCoin(), ratio, 0)).intValue(); // 奖励币数
								TradeRecord tradeRecord = new TradeRecord(userIndex.getUid(), userIndex.getUid(), TradeType.BUSOWNER_GET_COIN.type, 0, 
									rewardCoin, TradeCostType.COST_COIN.type, userIndex.getBusId() + "号机的机主指数第" + (i+1) + "名，奖励" + rewardCoin + "币");
								boolean result = tradeService.charge(tradeRecord);
								if(result) {
									Msg msg = new Msg(userIndex.getUid(), MsgType.NOTICE_SYS.type,
											"小主经营萌店辛苦啦，您所花的每一份心思，都会有丰厚收获，" + rewardCoin + "游戏币奖励已发放。");
									msgService.sendMsg(msg);
								}
							}
						}
					}
				}
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}

	private void pushJumpMsg(Long uid, String body, String url, int busId) {
		MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type, body);
		UserBase userBase = userService.getUserBase(uid);
		msg.setJumpWeb(url + "?uid=" + uid + "&loginKey=" + userBase.getLoginKey() + "&busId=" + busId);
		msg.setPushMsg(body);
		msg.setTargetTitle(BusOwnerConstants.HOMEPAGE_H5_TITLE);
		msg.setTargetName(BusOwnerConstants.HOMEPAGE_H5_TITLE);
		msgService.sendMsg(msg);
	}

	private void pushBusOwnerNotice(Map<Integer, UserGeneral> busOwnerMap) {
		for(Entry<Integer, UserGeneral> entry : busOwnerMap.entrySet()) {
			UserGeneral userGeneral = entry.getValue();
			if(userGeneral != null) {
				MsgRoomJump msgRoom = new MsgRoomJump(MsgRoomType.BUS_OWNER_NOTICE.type, 
						"<font color=\"#FFF203\">恭喜 " + userGeneral.getName() +" 成为 " + entry.getKey() + "号机 的萌主</font>", userGeneral); // 全站弹幕
				msgService.sendMsgRoomAll(msgRoom);
			}
		}
	}
	
	private void pushUpdateBusOwnerMsg(Map<Integer, UserGeneral> busOwnerMap) {
		for(Entry<Integer, UserGeneral> entry : busOwnerMap.entrySet()) {
			Integer busId = entry.getKey();
			UserGeneral userGeneral = entry.getValue();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			DollBus dollBus = dollBusService.getDollBus(busId.intValue());
			if(dollBus != null) {
				DollInfo info = dollInfoService.getDollInfo(dollBus.getDollId());
				dataMap.put("busName", busOwnerService.getNotHandledBusName(info, dollBus));
				if(dollBus.getBusType() == DollBus.DollBusType.MENGDIAN.type && userGeneral != null) {
					dataMap.put("busOwnerRes", busOwnerService.getBusOwnerRes(userGeneral.getUid()));
				}
				MsgRoom msgRoom = new MsgRoom(dollBus, MsgRoomType.UPDATE_BUS_OWNER.type, null);
				msgRoom.setData(JSON.toJSONString(dataMap));
				msgService.sendMsgRoom(msgRoom);
			}
		}
	}
}
