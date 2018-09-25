package com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.core.user.dao.UserDAO;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.dao.EventNewyearRecharge20180207Dao;
import com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.domain.Event20180207NewyearRechargeUserBlessing;
import com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.utils.ArithmeticUtils;
import com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.utils.NewyearRechargeUtils;
import com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.vo.BaseUserInfo;
import com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.vo.GetRankingRespVO;
import com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.vo.GetStatusRespVO;
import com.imlianai.dollpub.domain.msg.MsgNotice;
import com.imlianai.dollpub.domain.msg.MsgRoomJump;
import com.imlianai.dollpub.domain.msg.MsgType;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.LivePropsUtil;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class EventNewyearRecharge20180207ServiceImpl implements
		EventNewyearRecharge20180207Service {

	private static final BaseLogger LOG = BaseLogger
			.getLogger(EventNewyearRecharge20180207ServiceImpl.class);

	public static String EVENT_URL = LivePropsUtil
			.getString("newYearEvent.url"); // 新春充值活动h5地址

	@Resource
	EventNewyearRecharge20180207Dao eventNewyearRecharge20180207Dao;
	@Resource
	UserService userService;
	@Resource
	UserDAO userDAO;

	@Override
	public void handleRecharge(double cost, long uid) {
		try {
			UserBase userBase=userService.getUserBase(uid);
			if (userBase!=null&&userBase.getCustomerId()==84&&NewyearRechargeUtils.eventStatus() == 1) { // 活动期间
				eventNewyearRecharge20180207Dao
						.saveOrUpdateNewyearrechargeUserRechargeSum(cost, uid);
				double amtSum = eventNewyearRecharge20180207Dao
						.getRechargeAmountSum(uid); // 用户活动期间充值金额
				double blessingValue = ArithmeticUtils.add(amtSum == 0 ? 0
						: 11 * Math.log(amtSum), 20, 2);
				if (eventNewyearRecharge20180207Dao
						.saveOrUpdateNewyearRechargeUserBlessing(uid,
								blessingValue) > 0) {
					eventNewyearRecharge20180207Dao
							.saveNewyearRechargeUserBlessingRecord(uid,
									blessingValue, "充值" + cost + "元获得"
											+ blessingValue + "福气值");
				}
				int rank = getMyRank(uid);
				if (rank >= 0 && rank < 50) {
					// 相关私信
				}

			}
		} catch (Exception e) {
			PrintException.printException(LOG, e);
		}
	}

	@Override
	public BaseRespVO getStatus() {
		int eventStatus = NewyearRechargeUtils.eventStatus();
		long residueTime = 0;
		if (eventStatus == 1) {
			residueTime = NewyearRechargeUtils.getResidueTime();
		}
		return new GetStatusRespVO(eventStatus, residueTime);
	}

	private int getMyRank(long uid) {
		Event20180207NewyearRechargeUserBlessing selfBlessing = eventNewyearRecharge20180207Dao
				.getUserBlessingByUid(uid);
		if (selfBlessing != null && selfBlessing.getValue() > 0) {
			int rank = eventNewyearRecharge20180207Dao
					.getMyRankCount(selfBlessing.getValue());
			return rank;
		}
		return -1;
	}

	@Override
	public BaseRespVO getRanking(Long uid) {
		GetRankingRespVO respVO = new GetRankingRespVO();
		List<BaseUserInfo> userInfos = new ArrayList<BaseUserInfo>();
		// 用户自身排名信息
		Event20180207NewyearRechargeUserBlessing selfBlessing = eventNewyearRecharge20180207Dao
				.getUserBlessingByUid(uid);
		UserGeneral userGeneral = userService.getUserGeneral(uid);
		BaseUserInfo selfInfo = new BaseUserInfo();
		if (userGeneral != null) {
			selfInfo = new BaseUserInfo(userGeneral, selfBlessing == null ? 0
					: selfBlessing.getValue());
		}
		userInfos.add(selfInfo);
		// 前50排名信息
		List<Event20180207NewyearRechargeUserBlessing> userBlessingList = eventNewyearRecharge20180207Dao
				.getUserBlessingList(50); // 前50位用户福气值列表
		List<Long> uids = new ArrayList<Long>();
		Map<Long, UserGeneral> userGeneralMap = new HashMap<Long, UserGeneral>();
		Map<Long, Integer> rankingMap = new HashMap<Long, Integer>();
		int ranking = 1;
		if (!StringUtil.isNullOrEmpty(userBlessingList)) {
			for (Event20180207NewyearRechargeUserBlessing userBlessing : userBlessingList) {
				uids.add(userBlessing.getUid());
				rankingMap.put(userBlessing.getUid(), ranking);
				ranking++;
			}
		}
		if (!StringUtil.isNullOrEmpty(uids)) {
			userGeneralMap = userService.getUserGeneralMap(uids);
		}
		if (!StringUtil.isNullOrEmpty(userBlessingList)) {
			for (Event20180207NewyearRechargeUserBlessing userBlessing : userBlessingList) {
				Long userId = userBlessing.getUid();
				BaseUserInfo userInfo = new BaseUserInfo(
						userGeneralMap.get(userId), userBlessing.getValue());
				userInfo.setRanking(rankingMap.get(userId) == null ? 0
						: rankingMap.get(userId));
				userInfos.add(userInfo);
			}
		}
		if (rankingMap.size() > 0) {
			userInfos.get(0).setRanking(
					rankingMap.get(uid) == null ? 0 : rankingMap.get(uid));
		}
		respVO.setRankingList(userInfos);
		return respVO;
	}

	@Override
	public BaseRespVO addValue(Long uid, double value) {
		if (eventNewyearRecharge20180207Dao
				.saveOrUpdateNewyearRechargeUserBlessing(uid, value) > 0) {
			eventNewyearRecharge20180207Dao
					.saveNewyearRechargeUserBlessingRecord(uid, value, "运营添加"
							+ value + "福气值");
		}
		return new BaseRespVO();
	}

	@Override
	public void newYearEventPushRankingMsg() {
		if (NewyearRechargeUtils.eventStatus() == 1) {
			// 前31排名信息
			List<Event20180207NewyearRechargeUserBlessing> userBlessingList = eventNewyearRecharge20180207Dao
					.getUserBlessingList(31); // 前31位用户福气值列表
			if (!StringUtil.isNullOrEmpty(userBlessingList)) {
				int ranking = 1;
				final int SIZE = userBlessingList.size();
				for (int i = 0; i < SIZE; i++) {
					if (ranking <= 30) {
						Event20180207NewyearRechargeUserBlessing userBlessing = userBlessingList
								.get(i);
						Long uid = userBlessing.getUid();
						StringBuilder bodySb = new StringBuilder(
								"#春暖花开，充值送礼  小主在贵人榜暂列第" + ranking + "名");
						if (ranking == 1 && (i + 1) < SIZE) {
							bodySb.append("，超越下一名"
									+ ArithmeticUtils.sub(userBlessing
											.getValue(),
											userBlessingList.get(i + 1)
													.getValue(), 2) + "福气值。");
						} else {
							bodySb.append("，距离上一名"
									+ ArithmeticUtils.sub(
											userBlessingList.get(i - 1)
													.getValue(), userBlessing
													.getValue(), 2) + "福气值");
							if ((i + 1) < SIZE) {
								bodySb.append("，超越下一名"
										+ ArithmeticUtils.sub(userBlessing
												.getValue(), userBlessingList
												.get(i + 1).getValue(), 2)
										+ "福气值。");
							} else {
								bodySb.append("。");
							}
						}
						MsgNotice msg = new MsgNotice(uid,
								MsgType.NOTICE_SYS.type, bodySb.toString());
						msg.setJumpWeb(EVENT_URL + "?uid=" + uid);
						msg.setPushMsg(bodySb.toString());
						ranking++;
					}
				}
			}
		}
	}

	@Override
	public void newYearEventEndPushMsg() {
		// 前10排名信息
		List<Event20180207NewyearRechargeUserBlessing> userBlessingList = eventNewyearRecharge20180207Dao
				.getUserBlessingList(10); // 前10位用户福气值列表
		if (!StringUtil.isNullOrEmpty(userBlessingList)) {
			int ranking = 1;
			for (Event20180207NewyearRechargeUserBlessing userBlessing : userBlessingList) {
				Long uid = userBlessing.getUid();
				String body = "#春暖花开，充值送礼  恭喜小主获得贵人榜第" + ranking
						+ "名，奖品将在活动结束后7个工作日内发放到背包。（官方Q群：517384048）";
				MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type,
						body);
				msg.setJumpWeb(EVENT_URL + "?uid=" + uid);
				msg.setPushMsg(body);
				ranking++;
			}
		}
	}

	@Override
	public void handleMsgTask() {
		boolean msg1 = true;
		int vaildNum = 0;
		MsgRoomJump msgRoomJump = null;
		if (msg1) {
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(new Random().nextInt(2));
		}

	}
}
