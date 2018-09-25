package com.imlianai.dollpub.app.modules.support.invite.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.app.modules.support.invite.dao.InviteDAO;
import com.imlianai.dollpub.app.modules.support.invite.util.InviteUtil;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.invite.InviteRelation;
import com.imlianai.dollpub.domain.invite.InviteRewardRecord;
import com.imlianai.dollpub.domain.invite.InviteRewardRecord.InviteRewardState;
import com.imlianai.dollpub.domain.invite.OfficialCode;
import com.imlianai.dollpub.domain.msg.MsgNotice;
import com.imlianai.dollpub.domain.msg.MsgType;
import com.imlianai.dollpub.domain.trade.TradeCostType;
import com.imlianai.dollpub.domain.trade.TradeRecord;
import com.imlianai.dollpub.domain.trade.TradeType;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserBase.UserSrcType;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class InviteServiceImpl implements InviteService {

	private final BaseLogger logger = BaseLogger
			.getLogger(InviteServiceImpl.class);
	@Resource
	private InviteDAO inviteDAO;
	@Resource
	private UserService userService;

	@Resource
	private TradeService tradeService;

	@Resource
	private MsgService msgService;
	@Resource
	CustomerService customerService;

	@Override
	public BaseRespVO addInvite(long uid, long inviteUid, long code) {
		if (inviteDAO.updateInviteUsedTimes(inviteUid) > 0) {
			if (inviteDAO.addInvite(uid, inviteUid) > 0) {
				InviteRewardRecord record = new InviteRewardRecord(uid,
						inviteUid, InviteUtil.getRegReward(), 1, "邀请注册");
				record.setState(InviteRewardState.WAIT_GAIN.type);
				inviteDAO.addRewardRecord(record);
				inviteDAO.addInviteTotalRecord(record);
				record = new InviteRewardRecord(uid, inviteUid,
						InviteUtil.getSuccessReward(), 2, "邀请助攻");
				inviteDAO.addRewardRecord(record);
				TradeRecord tradeRecord = new TradeRecord(uid, uid,
						TradeType.INVITE_REWARD.type, (int) code,
						InviteUtil.getRegReward(),
						TradeCostType.COST_COIN.type, "输入好友邀请码" + code);
				try {
					tradeService.charge(tradeRecord);
					String bodyString = "使用了邀请码，获得" + InviteUtil.getRegReward()
							+ "币。";
					MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type,
							bodyString);
					msg.setPushMsg(bodyString);
					msg.setJumpInvite();
					msgService.sendMsg(msg);
				} catch (TradeOperationException e) {
					PrintException.printException(logger, e);
				}
				UserGeneral user = userService.getUserGeneral(uid);
				String bodyString = "成功邀请朋友 " + user.getName() + " 加入，快来领取"
						+ InviteUtil.getRegReward() + "币吧~~朋友抓到第1个娃娃您还会有"
						+ InviteUtil.getSuccessReward() + "币奖励哟~~";
				MsgNotice msg = new MsgNotice(inviteUid,
						MsgType.NOTICE_SYS.type, bodyString);
				msg.setPushMsg(bodyString);
				msg.setJumpInvite();
				msgService.sendMsg(msg);
				BaseRespVO respVO = new BaseRespVO(0, true, "成功输入邀请码，获得"
						+ InviteUtil.getRegReward() + "币奖励~~");
				respVO.setData(InviteUtil.getRegReward());
				userService.updateInviteNotice(inviteUid, 1);
				return respVO;
			} else {
				return new BaseRespVO(ResCodeEnum.INVITE_EXHAUST);
			}
		} else {
			return new BaseRespVO(ResCodeEnum.INVITE_EXHAUST);
		}
	}

	@Override
	public long getInviteId(long uid) {
		logger.info("getInviteId uid:" + uid);
		initInviteCode(uid);
		return inviteDAO.getInviteId(uid);
	}

	@Override
	public void handleCatchDollSuccess(long uid) {
		InviteRewardRecord record = inviteDAO.getInviteRewardRecord(uid, 2);
		if (record != null
				&& record.getState() == InviteRewardState.NO_OAUTH.type) {
			if (inviteDAO.updateRewardRecord(record.getId(),
					InviteRewardState.WAIT_GAIN.type) > 0) {
				UserGeneral user = userService.getUserGeneral(uid);
				String bodyString = "您邀请的朋友 " + user.getName()
						+ " 抓到第1个娃娃啦，快来领取" + InviteUtil.getSuccessReward()
						+ "币奖励吧~~";
				MsgNotice msg = new MsgNotice(record.getInviteUid(),
						MsgType.NOTICE_SYS.type, bodyString);
				msg.setPushMsg(bodyString);
				msg.setJumpInvite();
				msgService.sendMsg(msg);
				userService.updateInviteNotice(record.getInviteUid(), 1);
				user = userService.getUserGeneral(record.getInviteUid());
				bodyString = "您抓到了第1个娃娃，邀请您的好友 " + user.getName()
						+ " 获得了" + InviteUtil.getSuccessReward() + "币奖励~~";
				msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type, bodyString);
				msg.setPushMsg(bodyString);
				msg.setJumpInvite();
				msgService.sendMsg(msg);
				inviteDAO.addInviteTotalRecord(record);
			}
		}
	}

	@Override
	public BaseRespVO gainInviteReward(long uid, long rewardId) {
		InviteRewardRecord record = inviteDAO.getInviteRewardRecord(rewardId);
		if (record != null && record.getInviteUid() == uid) {
			if (record.getState() == InviteRewardState.WAIT_GAIN.type) {
				if (inviteDAO.updateRewardRecord(rewardId,
						InviteRewardState.GAINED.type) > 0) {
					UserGeneral user = userService.getUserGeneral(record
							.getUid());
					TradeRecord tradeRecord = new TradeRecord(uid, uid,
							TradeType.INVITE_REWARD.type,
							(int) record.getUid(), record.getReward(),
							TradeCostType.COST_COIN.type, "成功邀请好友"
									+ user.getName());
					try {
						tradeService.charge(tradeRecord);
						String bodyString = "恭喜你~成功邀请好友 " + user.getName()
								+ "，获得" + record.getReward() + "币。";
						MsgNotice msg = new MsgNotice(uid,
								MsgType.NOTICE_SYS.type, bodyString);
						msg.setPushMsg(bodyString);
						msg.setJumpInvite();
						msgService.sendMsg(msg);
					} catch (TradeOperationException e) {
						PrintException.printException(logger, e);
					}
					BaseRespVO respVO = new BaseRespVO(0, true, "成功领取"
							+ record.getReward() + "币");
					respVO.setData(record.getReward());
					userService.updateInviteNotice(uid, -1);
					return respVO;
				} else {
					return new BaseRespVO(0, false, "领取失败");
				}
			} else {
				return new BaseRespVO(0, false, "您尚未达到领取条件或已领取");
			}
		}
		return new BaseRespVO(0, false, "您不能领取该笔奖励");
	}

	@Override
	public List<InviteRewardRecord> getInviteRewardRecords(long uid, int type,
			int page, int pageSize) {
		List<InviteRewardRecord> res = new ArrayList<InviteRewardRecord>();
		List<InviteRewardRecord> list = inviteDAO.getInviteRewardRecords(uid,
				type, page, pageSize);
		if (!StringUtil.isNullOrEmpty(list)) {
			List<Long> uidsList = new ArrayList<Long>();
			for (InviteRewardRecord inviteRewardRecord : list) {
				uidsList.add(inviteRewardRecord.getUid());
			}
			Map<Long, UserGeneral> userMap = userService
					.getUserGeneralMap(uidsList);
			for (InviteRewardRecord inviteRewardRecord : list) {
				UserGeneral userGeneral = userMap.get(inviteRewardRecord
						.getUid());
				if (userGeneral != null) {
					inviteRewardRecord.setName(userGeneral.getName());
					inviteRewardRecord.setHead(userGeneral.getHead());
					res.add(inviteRewardRecord);
				}
			}
		}
		return res;
	}

	private static final int rewardCoin = 60;

	@Override
	public int handleRegReward(UserGeneral user, UserBase base) {
		logger.info("handleRegReward uid:"+base.getUid()+" imei:"+base.getImei());
		int reward = 0;
		if (base != null) {
			Customer customer =customerService.getCustomerById(base.getCustomerId()); 
			if (customer!=null&&customer.getRegReward()>0) {
				if (inviteDAO.hasGainRegReward(base.getImei(),base.getCustomerId()) == 0) {
					reward = customer.getRegReward();
					logger.info("handleRegReward uid:"+base.getUid()+" imei:"+base.getImei()+" reward:"+reward);
					inviteDAO.addGainRegReward(base.getUid(), base.getImei(),base.getCustomerId());
					TradeRecord record = new TradeRecord(base.getUid(),
							base.getUid(), TradeType.REG_REWARD.type, 0, reward,
							TradeCostType.COST_COIN.type, "新用户注册奖励" + reward + "币");
					try {
						tradeService.charge(record);
						String bodyString = "欢迎来到"+customer.getName()+"抓娃娃，新用户奖励"+customer.getRegReward()+"币，邀请好友注册可获更多币哟~~";
						MsgNotice msg = new MsgNotice(base.getUid(),
								MsgType.NOTICE_SYS.type, bodyString);
						msg.setPushMsg(bodyString);
						msg.setJumpInvite();
						msgService.sendMsg(msg);
					} catch (TradeOperationException e) {
						PrintException.printException(logger, e);
					}
				}
			}
		}
		return reward;
	}

	/**
	 * 初始化邀请码
	 * 
	 * @param uid
	 */
	private void initInviteCode(long uid) {
		InviteRelation inviteRelation = inviteDAO.getInviteRelationByUid(uid);
		logger.info("initInviteCode uid:" + uid + " inviteRelation:"
				+ JSON.toJSONString(inviteRelation));
		if (inviteRelation == null || inviteRelation.getCode() == 0) {
			UserBase base = userService.getUserBase(uid);
			if (base != null && base.getSrcType() == UserSrcType.WECHAT.type) {
				long code = 0;
				for (int i = 0; i < 10; i++) {// 最多重试10次
					long location = StringUtil.getRanDom(1000000, 1999999);
					code = inviteDAO.getInviteCode(location);
					if (code > 0)
						break;
				}
				if (code > 0) {
					inviteDAO.initInviteCode(uid, code);
				}
			}
		}
	}

	@Override
	public InviteRelation getInviteRelationByCode(long code) {
		return inviteDAO.getInviteRelationByCode(code);
	}

	@Override
	public InviteRelation getInviteRelationByUid(long uid) {
		initInviteCode(uid);
		return inviteDAO.getInviteRelationByUid(uid);
	}

	@Override
	public BaseRespVO addOfficalCode(long uid, String imei, long code) {
		OfficialCode officialCode = inviteDAO.getOfficialCode(code);
		if (officialCode != null) {
			if (officialCode.getNumber() > officialCode.getConsumeNum()) {
				if (inviteDAO.hasGainOfficialCode(uid, officialCode.getId()) == 0
						&& inviteDAO.hasGainOfficialCode(imei,
								officialCode.getId()) == 0) {
					if (inviteDAO.consumeOfficialCode(officialCode.getId(),
							code) > 0) {
						int coin = 0;
						try {
							coin = new Random().nextInt(officialCode
									.getCoinMax() - officialCode.getCoinMin())
									+ officialCode.getCoinMin();
						} catch (Exception e) {
							PrintException.printException(logger, e);
						}
						if (coin == 0) {
							return new BaseRespVO(ResCodeEnum.INVITE_EXHAUST);
						}
						TradeRecord record = new TradeRecord(uid, uid,
								TradeType.INVITE_OFFICAL_REWARD.type,
								(int) code, coin, TradeCostType.COST_COIN.type,
								"使用了邀请码" + code + "，获得" + coin + "币。");
						try {
							tradeService.charge(record);
							inviteDAO.addGainOfficialRecord(uid, imei,
									officialCode.getId(), code, coin);
							inviteDAO.updateOfficialCodeCoin(
									officialCode.getId(), coin);
							String bodyString = "使用了邀请码，获得" + coin + "币。";
							MsgNotice msg = new MsgNotice(uid,
									MsgType.NOTICE_SYS.type, bodyString);
							msg.setPushMsg(bodyString);
							msg.setJumpInvite();
							msgService.sendMsg(msg);
							BaseRespVO respVO = new BaseRespVO(0, true, "成功领取"
									+ coin + "币");
							respVO.setData(coin);
							return respVO;
						} catch (Exception e) {
							PrintException.printException(logger, e);
						}
					}
				} else {
					return new BaseRespVO(0, false, "您已使用过该福利码");
				}
			}
			return new BaseRespVO(ResCodeEnum.INVITE_EXHAUST);
		} else {
			return new BaseRespVO(ResCodeEnum.INVITE_ERROR_DOLL);
		}
	}

	@Override
	public List<InviteRewardRecord> getInviteRewardRecords(int type, int page,
			int pageSize) {
		List<InviteRewardRecord> list = inviteDAO.getInviteRewardRecords(type,
				page, pageSize);
		List<InviteRewardRecord> res=new ArrayList<InviteRewardRecord>();
		if (!StringUtil.isNullOrEmpty(list)) {
			List<Long> uidsList = new ArrayList<Long>();
			for (InviteRewardRecord inviteRewardRecord : list) {
				uidsList.add(inviteRewardRecord.getUid());
				uidsList.add(inviteRewardRecord.getInviteUid());
			}
			Map<Long, UserGeneral> userMap = userService
					.getUserGeneralMap(uidsList);
			for (InviteRewardRecord inviteRewardRecord : list) {
				UserGeneral userGeneral = userMap.get(inviteRewardRecord
						.getUid());
				UserGeneral userGenerali = userMap.get(inviteRewardRecord
						.getInviteUid());
				if (userGeneral != null&&userGenerali!=null) {
					inviteRewardRecord.setName(userGeneral.getName());
					inviteRewardRecord.setHead(userGenerali.getName());
					res.add(inviteRewardRecord);
				}
			}
		}
		return res;
	}

}
