package com.imlianai.zjdoll.app.modules.support.redpacket.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import com.ctrip.framework.apollo.util.PropertiesUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.minlog.Log;
import com.imlianai.zjdoll.constants.ChannelConstants;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.zjdoll.domain.invite.InvitePreRecord;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgRoomJump;
import com.imlianai.zjdoll.domain.msg.MsgRoomType;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.redpacket.BusRedpacketRecord;
import com.imlianai.zjdoll.domain.redpacket.RedpacketRes;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacket;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacketLog;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacketOpenRecord;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacketRewardRecord;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawDetailRecord;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawUserAccount;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawUserCertificationInfo;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawUserPhone;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawWechatBoundMiddleware;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawWechatCommunicationLog;
import com.imlianai.zjdoll.domain.redpacket.withdraw.WithdrawWechatInnerMiddleware;
import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserAttribute;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.domain.user.UserPhone;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.SystemDirHandle;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.NotEnoughBeanException;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.utils.AspectUtil;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.ExecutorServiceUtil;
import com.ctrip.framework.apollo.util.PropertiesUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.rpc.support.utils.SysTimerHandle;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.core.doll.vo.UserAbandonSummry;
import com.imlianai.zjdoll.app.modules.core.trade.dao.TradeChargeDAO;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.trade.util.weixin.config.WeiXinPayJSConfig;
import com.imlianai.zjdoll.app.modules.core.user.attribute.UserAttributeService;
import com.imlianai.zjdoll.app.modules.core.user.phone.UserPhoneService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.core.user.util.UserUtil;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserPhoneReqVO.CheckCodeType;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinCommonUtils;
import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.utils.WebWeixinXmlUtils;
import com.imlianai.zjdoll.app.modules.publics.utils.ArithmeticUtils;
import com.imlianai.zjdoll.app.modules.support.dailytask.enm.TaskType;
import com.imlianai.zjdoll.app.modules.support.dailytask.service.DailytaskService;
import com.imlianai.zjdoll.app.modules.support.invite.dao.InviteDAO;
import com.imlianai.zjdoll.app.modules.support.invite.service.InviteService;
import com.imlianai.zjdoll.app.modules.support.redpacket.certification.dao.CertificationDAO;
import com.imlianai.zjdoll.app.modules.support.redpacket.constants.RedpacketConstants;
import com.imlianai.zjdoll.app.modules.support.redpacket.dao.RedpacketDao;
import com.imlianai.zjdoll.app.modules.support.redpacket.dao.WithdrawDao;
import com.imlianai.zjdoll.app.modules.support.redpacket.enm.ApplyDebrisEnm;
import com.imlianai.zjdoll.app.modules.support.redpacket.enm.GetFriendsRedpackMsg;
import com.imlianai.zjdoll.app.modules.support.redpacket.enm.RedpacketEnm;
import com.imlianai.zjdoll.app.modules.support.redpacket.utils.MobileWithdrawSecure;
import com.imlianai.zjdoll.app.modules.support.redpacket.utils.RedpacketUtils;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BoundWechatReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BusRedpacket;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.GetInviteSituationRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.GetPageInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.GetRedpacketReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.HandleWithdrawReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.InviteRedpacketRes;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.MyRedpacketInfo;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.RedpacketLogRes;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.RedpacketOpenRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.RoomRedpacketRecord;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.WithdrawInfo;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.WithdrawRecord;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.zjdoll.app.modules.support.version.service.VersionService;

@Service
public class RedpacketServiceImpl implements RedpacketService {

	protected final BaseLogger LOG = BaseLogger.getLogger(this.getClass());
	
	private ExecutorService executorService = ExecutorServiceUtil.newNamedFixedThreadPool("DoPayWechatMoneySchedule", 1);
	
	private static KeyStore keyStore = null;
	
	private static final String withdrawFile = SystemDirHandle.rootPath
			+ PropertiesUtil.getString("application","withdraw.wexinCA.file");
	
	private static final String appId=WeiXinPayJSConfig.appId;
	private static final String apiKey=WeiXinPayJSConfig.apiKey;
	
	// 商户号
	private static final String merchantsId = WeiXinPayJSConfig.mchId;
	
	private static Pattern returnCodePattern = Pattern
			.compile("<return_code>.*</return_code>");
	private static Pattern resultCodePattern = Pattern
			.compile("<result_code>.*</result_code>");
	private static Pattern statusPattern = Pattern
			.compile("<status>.*</status>");
	private static Pattern refund_timePattern = Pattern
			.compile("<refund_time>.*</refund_time>");
	private static Pattern returnMsgPattern = Pattern
			.compile("<return_msg>.*</return_msg>");
	private static Pattern errCodePattern = Pattern
			.compile("<err_code>.*</err_code>");
	
	@Resource
	RedpacketDao redpacketDao;
	@Resource
	TradeService tradeService;
	@Resource
	UserDollService userDollService;
	@Resource
	UserService userService;
	@Resource
	InviteService inviteService;
	@Resource
	WithdrawDao withdrawDao;
	@Resource
	UserAttributeService userAttributeService;
	@Resource
	CertificationDAO certificationDao;
	@Resource
	DollRecordService dollRecordService;
	@Resource
	TradeChargeDAO tradeChargeDAO;
	@Resource
	DollBusService dollBusService;
	@Resource
	MsgService msgService;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	InviteDAO inviteDAO;
	@Resource
	VersionService versionService;
	@Resource
	UserPhoneService userPhoneService;
	@Resource
	DailytaskService dailytaskService;
	
	@Override
	public void saveOrUpdateUserRedpacket(long uid, double redpackAmt,
			String remark, int type, long optId) {
		int dateCode = Integer.parseInt(DateUtils
				.getCurrentDateString("yyyyMMdd"));
		if (redpacketDao.saveOrUpdateUserRedpacket(uid, redpackAmt) > 0) {
			redpacketDao.saveUserRedpacketLog(uid, redpackAmt, type, remark,
					dateCode, optId);
		}
	}

	@Override
	public String handleApplyReward(long uid, int score) {
		String reward = "";
		try {
			int type = RedpacketUtils.getApplyDebris(); // 获得的娃娃碎片
			ApplyDebrisEnm applyDebrisEnm = ApplyDebrisEnm.values()[type - 1];
			// 更新用户积分
			TradeRecord tradeRecord = new TradeRecord(uid, uid,
					TradeType.APPLY_RETURN_SCORE.type, 0, score,
					TradeCostType.COST_SCORE.type, "上机获得" + score + "积分");
			tradeService.charge(tradeRecord);
			// 更新用户娃娃碎片
			userDollService
					.saveOrUpdateUserDollDebris(uid, applyDebrisEnm
							.getDebrisType(), applyDebrisEnm.getNum(), "成功上机获得"
							+ (applyDebrisEnm.getDebrisType() == 0 ? "普通"
									: "稀有") + "娃娃碎片*" + applyDebrisEnm.getNum());
			return "+" + score + "积分，" + applyDebrisEnm.getDesc();
		} catch (Exception e) {
			PrintException.printException(LOG, e);
		}
		return reward;
	}

	@Override
	public MyRedpacketInfo getMyRedpacketInfo(BaseReqVO reqVO) {
		Long uid = reqVO.getUid();
		MyRedpacketInfo myRedpacketInfo = new MyRedpacketInfo();
		List<UserRedpacketLog> logs = redpacketDao
				.getLatestUserRedpacketLog(100); // 最近的100条红包流水记录
		List<String> records = new ArrayList<String>(); // 提现、红包领取记录
		if (!StringUtil.isNullOrEmpty(logs)) {
			for (UserRedpacketLog log : logs) {
				UserGeneral userGeneral = userService.getUserGeneral(log
						.getUid());
				if (userGeneral.getValid() != 0)
					continue;
				String amtStr = (log.getAmount() < 0 ? Math
						.abs(log.getAmount()) : log.getAmount()) + "";
				String[] amtStrAry = new String(amtStr).split(".");
				if (amtStrAry != null && amtStrAry.length == 2
						&& amtStrAry[1].equals("0")) {
					amtStr = amtStrAry[0];
				}
				if (log.getType() == RedpacketEnm.WITHDRAW.type) { // 提现
					records.add("<font color=\"#FFFFFF\">" + userGeneral.getName() + "提现了</font><font color=\"#f72626\">" + amtStr + "元</font>");
				} else { // 领取红包
					records.add("<font color=\"#FFFFFF\">" + userGeneral.getName() + "领取了</font><font color=\"#f72626\">" + amtStr + "元</font><font color=\"#FFFFFF\">红包</font>");
				}
			}
			myRedpacketInfo.setRecords(records);
		}
		UserRedpacket userRedpacket = redpacketDao.getUserRedpacket(uid); // 用户红包
		myRedpacketInfo.setAmount(userRedpacket == null ? 0 : userRedpacket
				.getAmount());
		TradeAccount tradeAccount = tradeService.getAccount(uid);
		myRedpacketInfo.setScore(tradeAccount.getScore()); // 用户积分
		if(versionService.isAudit(reqVO.getOsType(), reqVO.getChannel(), reqVO.getVersion())) { // 判断是否在审核中
			myRedpacketInfo.setRemark(RedpacketConstants.AUDIT_MY_REDPACK_CONTENT);
		} else {
			myRedpacketInfo.setRemark(RedpacketConstants.MY_REDPACK_CONTENT);
		}
		
		Map<String, Object> comInfo = comInfo(uid);
		boolean isBefore = (boolean) comInfo.get("isBefore");
		myRedpacketInfo.setIsBefore(isBefore); // 是否在72小时前
		
		// 邀请红包信息
		List<UserRedpacketRewardRecord> rewardRecords = redpacketDao.getUserRedpacketRewardRecordList(uid, RedpacketConstants.HAS_REWARD_NUM); // 邀请奖励记录列表
		if(StringUtil.isNullOrEmpty(rewardRecords) || rewardRecords.size() < RedpacketConstants.HAS_REWARD_NUM) {
			RedpacketRes inviteRedpacketRes = null;
			if (isBefore) {
				double getAmt = 0; // 已领的红包金额
				double notGetAmt = RedpacketConstants.INVITE_REWARD_TOTAL_AMT; // 待领的红包金额
				if(!StringUtil.isNullOrEmpty(rewardRecords)) {
					for (UserRedpacketRewardRecord record : rewardRecords) {
						getAmt = ArithmeticUtils.add(record.getAmount(), getAmt, 2);
					}
					notGetAmt = ArithmeticUtils.sub(RedpacketConstants.INVITE_REWARD_TOTAL_AMT, getAmt, 2);
				}
				inviteRedpacketRes = getInviteRedpacketRes(getAmt, notGetAmt, true, 0, uid);
			} else {
				inviteRedpacketRes = getInviteRedpacketRes(0, 0, false, rewardRecords == null ? RedpacketConstants.HAS_REWARD_NUM : RedpacketConstants.HAS_REWARD_NUM - rewardRecords.size(), uid);
			}
			myRedpacketInfo.setInviteRedpacketRes(inviteRedpacketRes);
		}
		
		List<RedpacketRes> redpacketResList = new ArrayList<RedpacketRes>();
		if (!StringUtil.isNullOrEmpty(rewardRecords)) {
			for (int i = 0; i < rewardRecords.size(); i++) {
				UserRedpacketRewardRecord record = rewardRecords.get(i);
				if (record.getStatus() == 0) {
					RedpacketRes redpacketRes = new RedpacketRes(
							record.getAmount(), "成功邀请了" + (i + 1)
									+ "位好友，送您大红包！",
							RedpacketConstants.REG_AWARD_REDPACK_CONTENT);
					redpacketRes.setTid(record.getTid());
					redpacketResList.add(redpacketRes);
				}
			}
		}
		myRedpacketInfo.setRedpacketResList(redpacketResList);
		LOG.info("getMyRedpacketInfo:myRedpacketInfo-" + JSON.toJSONString(myRedpacketInfo));
		return myRedpacketInfo;
	}

	private RedpacketRes getInviteRedpacketRes(double getAmt, double notGetAmt, boolean isBefore, int size, long uid) {
		RedpacketRes inviteRedpacketRes = null;
		if(isBefore) {
			inviteRedpacketRes = new RedpacketRes(RedpacketConstants.INVITE_REWARD_TOTAL_AMT, 
					"邀请" + RedpacketConstants.HAS_REWARD_NUM +"位好友微信登录领" + RedpacketConstants.INVITE_REWARD_TOTAL_AMT +"元", 
					"<font style=\"text-align: center;\"><font color=\"#FFFFFF\">已领" + getAmt + "元，待领</font><font color=\"#FFFF00\"> " + notGetAmt + " </font><font color=\"#FFFFFF\">元（可提现)</font></font>");
			// 待领红包期限
			Map<String, Object> comInfo = comInfo(uid);
			inviteRedpacketRes.setRemainingTime((long) comInfo.get("remainingTime"));
			//inviteRedpacketRes.setRemainingTime(DateUtils.string2Date("2018-03-16 18:00:00", "yyyy-MM-dd HH:mm:ss").getTime() - new Date().getTime());
			inviteRedpacketRes.setDesc("<p style=\"text-align: center;line-height:0;margin:0;padding:0;\"><font color=\"#222222\">待领</font><font color=\"#f72626\"> " + notGetAmt + " </font><font color=\"#222222\">元(可提现),快邀请好友微信登录吧~</font></p>");
		} else {
			inviteRedpacketRes = new RedpacketRes(5, "邀请好友领红包", "<font style=\"text-align: center;\"><font color=\"#FFFFFF\">再邀请</font><font color=\"#FFFF00\"> " + size + " </font><font color=\"#FFFFFF\">位好友，最高可领5元</font></font>");
			inviteRedpacketRes.setDesc("<p style=\"text-align: center;line-height:0;margin:0;padding:0;\"><font color=\"#222222\">邀请好友微信登录，最高可领5元红包</font></p>");
		}
		return inviteRedpacketRes;
	}

	private Map<Long, UserRedpacketOpenRecord> setUserRedpacketOpenRecordMap(
			List<UserRedpacketOpenRecord> openRecords) {
		Map<Long, UserRedpacketOpenRecord> openRecordMap = new HashMap<Long, UserRedpacketOpenRecord>();
		if (!StringUtil.isNullOrEmpty(openRecords)) {
			for (UserRedpacketOpenRecord record : openRecords) {
				openRecordMap.put(record.getTid(), record);
			}
		}
		return openRecordMap;
	}

	private void setBaseInviteRedpacks(List<InviteRedpacketRes> inviteRedpacks,
			int SIZE, Long uid) {
		String remark = "";
		int dateCode = Integer.parseInt(DateUtils
				.getCurrentDateString("yyyyMMdd"));
		Map<String, Object> comInfo = comInfo(uid);
		boolean isBefore = (boolean) comInfo.get("isBefore");
		if (SIZE < RedpacketConstants.HAS_REWARD_NUM && isBefore) {
			remark = "领20元";
		} else {
			remark = "收红包";
		}
		List<Long> uids = new ArrayList<Long>();
		uids.add(uid);
		uids.add(-1l);
		uids.add(-2l);
		List<UserRedpacketOpenRecord> openRecords = redpacketDao
				.getUserRedpacketOpenRecordList(uid, uids, dateCode);
		Map<Long, UserRedpacketOpenRecord> openRecordMap = setUserRedpacketOpenRecordMap(openRecords);
		List<UserRedpacketOpenRecord> notGetRecords = redpacketDao
				.getNotGetRecordList(uid, uids, dateCode);
		Map<Long, Long> notGetRecordMap = setNotGetRecordMap(notGetRecords);
		inviteRedpacks.add(new InviteRedpacketRes(null, -3l, null, null,
				remark, 0, 0));
		UserGeneral userGeneral = userService.getUserGeneral(uid);
		
		String defMonitor = "大佬";
		String defCustomer = "跟班";
		String defHead = RedpacketConstants.DEFAULT_HEAD;
		String threadChannel = AspectUtil.getChannel();
		ChannelConstants channelConstants = ChannelConstants.getByChannel(threadChannel);
		switch (channelConstants) {
		case MEIBAOWW_FOR_IOS:
		case MEIBAOWW_FOR_ANDROID:
			defMonitor = "大佬";
			defCustomer = "跟班";
			defHead = RedpacketConstants.MB_DEFAULT_HEAD;
			break;
		case MEIRENWW_FOR_IOS:
		case MEIRENWW_FOR_ANDROID:
			defMonitor = "大佬";
			defCustomer = "跟班";
			defHead = RedpacketConstants.MR_DEFAULT_HEAD;
			break;
		}
		inviteRedpacks
				.add(new InviteRedpacketRes(notGetRecordMap.get(uid), uid,
						userGeneral.getHead(), "我", null,
						openRecordMap.get(uid) == null ? 0 : (openRecordMap
								.get(uid).getNextTime().getTime() - new Date()
								.getTime()) / 1000, getFreeFlag(uid, uid)));
		inviteRedpacks
				.add(new InviteRedpacketRes(notGetRecordMap.get(-1l), -1l,
						defHead, defMonitor, null,
						openRecordMap.get(-1l) == null ? 0 : (openRecordMap
								.get(-1l).getNextTime().getTime() - new Date()
								.getTime()) / 1000, getFreeFlag(uid, -1l)));
		inviteRedpacks
				.add(new InviteRedpacketRes(notGetRecordMap.get(-2l), -2l,
						defHead, defCustomer, null,
						openRecordMap.get(-2l) == null ? 0 : (openRecordMap
								.get(-2l).getNextTime().getTime() - new Date()
								.getTime()) / 1000, getFreeFlag(uid, -2l)));
	}

	private Map<Long, Long> setNotGetRecordMap(
			List<UserRedpacketOpenRecord> notGetRecords) {
		Map<Long, Long> notGetRecordMap = new HashMap<Long, Long>();
		if (!StringUtil.isNullOrEmpty(notGetRecords)) {
			for (UserRedpacketOpenRecord record : notGetRecords) {
				notGetRecordMap.put(record.getTid(), record.getId());
			}
		}
		return notGetRecordMap;
	}

	private int getFreeFlag(Long uid, Long tid) {
		int dateCode = Integer.parseInt(DateUtils
				.getCurrentDateString("yyyyMMdd"));
		int time = redpacketDao.getUserOpenTimes(uid, dateCode, tid);
		if (time < RedpacketConstants.FREE_TIMES)
			return 1;
		return 0;
	}

	@Override
	public BaseRespVO getRedpacket(GetRedpacketReqVO reqVO) {
		try {
			Long uid = reqVO.getUid();
			Long tid = reqVO.getTid();
			int type = reqVO.getType();
			double amount = reqVO.getAmount();
			Long id = reqVO.getId();
			LOG.info("getRedpacket:uid-" + uid + ",tid-" + tid + ",type-"
					+ type + ",amount-" + amount + ",id=" + id);
			if (amount <= 0) {
				return new BaseRespVO(-1, false, "红包金额或币数不正确~");
			}
			if (type == 0) {
				UserRedpacketRewardRecord record = redpacketDao
						.getUserRedpacketRewardRecord(uid, tid);
				if (record != null) {
					if (record.getAmount() != amount) {
						return new BaseRespVO(-1, false, "红包金额不正确~");
					}
					if (redpacketDao.updateUserRedpacketRewardRecord(uid, tid) > 0) {
						saveOrUpdateUserRedpacket(uid, record.getAmount(),
								"邀请奖励获得" + record.getAmount() + "元",
								RedpacketEnm.INVITE_REWARD_REDPACKET.type, 0);
						// 系统通知
						UserGeneral userGeneral = userService.getUserGeneral(tid);
						Msg msg = new Msg(uid, MsgType.NOTICE_SYS.type,
								"小主成功邀请好友" + userGeneral.getName() + "，领取了" + amount + "元大红包~~");
						msgService.sendMsg(msg);
						return new BaseRespVO();
					} else {
						return new BaseRespVO(-1, false, "网络异常，请重试~");
					}
				} else {
					return new BaseRespVO(-1, false, "没有可领取的相关红包~");
				}
			} else if (type == 1 || type == 2) {
				if (id == null || id.longValue() <= 0) {
					return new BaseRespVO(-1, false, "没有可领取的相关红包~");
				}
				UserRedpacketOpenRecord openRecord = redpacketDao
						.getUserRedpacketOpenRecordById(id);
				if (openRecord == null || openRecord.getStatus() == 1) {
					return new BaseRespVO(-1, false, "该红包已被领取~");
				}
				if (uid == null
						|| tid == null
						|| (openRecord.getUid().longValue() != uid.longValue() || openRecord
								.getTid().longValue() != tid.longValue())) {
					return new BaseRespVO(-1, false, "用户不存在~");
				}
				type = openRecord.getType();
				amount = openRecord.getNum();
				/*
				 * if(amount > 0.08 && type == 1) { // 好友红包最高0.08元 return new
				 * BaseRespVO(-1, false, "红包金额不正确~"); } if(amount > 4 && type ==
				 * 2) { // 金币最多4个 return new BaseRespVO(-1, false, "游戏币个数不正确~");
				 * }
				 */
				Date nextTime = null;
				int dateCode = Integer.parseInt(DateUtils
						.getCurrentDateString("yyyyMMdd"));
				int openTimes = redpacketDao.getUserOpenTimes(uid, dateCode,
						tid);
				int isFree = openTimes >= RedpacketConstants.FREE_TIMES ? 0 : 1; // 是否可免费领取
				UserRedpacketOpenRecord lastOpenRecord = redpacketDao
						.getUserRedpacketOpenRecord(uid, tid, dateCode, 1);
				if (lastOpenRecord != null
						&& lastOpenRecord.getNextTime().getTime() > new Date()
								.getTime()) {
					return new RedpacketOpenRespVO(-1, false, "该红包还不能开启，请稍后再试~");
				}
				Date openTime = new Date();
				if (openTimes == 0) {
					nextTime = DateUtils.addSECONDToDate(openTime,
							RedpacketConstants.SECOND_COUNTDOWN);
				} else if (openTimes == 1) {
					nextTime = DateUtils.addSECONDToDate(openTime,
							RedpacketConstants.THIRD_COUNTDOWN);
				} else if (openTimes >= 2) {
					nextTime = DateUtils.addSECONDToDate(openTime,
							RedpacketConstants.OTHER_COUNTDOWN);
				}
				if (isFree == 0) {
					TradeRecord tradeRecord = new TradeRecord(uid, 0,
							TradeType.REDPACKET_COST_SCORE.type, 0,
							RedpacketConstants.COSE_SCORE,
							TradeCostType.COST_SCORE.type, "开启好友红包扣取积分"
									+ RedpacketConstants.COSE_SCORE);
					tradeService.consume(tradeRecord);
					if(reqVO.getVersion() >= 120) {
						dailytaskService.saveOrUpdateUserTask(uid, TaskType.OPEN_FRIEND_REDPACK.taskId, 1);
					}
				}

				if (redpacketDao.updateUseRedpacketOpenRecord(id, openTime,
						nextTime) > 0) {
					redpacketDao.saveOrUpdateUserRedpacketOpenTimes(uid, tid,
							dateCode);
					if (type == 1) { // 金额
						saveOrUpdateUserRedpacket(uid, amount, "开启好友红包获得"
								+ amount + "元",
								RedpacketEnm.FRIEND_REDPACKET.type, 0);
						final int size = GetFriendsRedpackMsg.values().length;
						String textString = GetFriendsRedpackMsg.values()[new Random()
								.nextInt(size)].msg;
						textString = textString.replace("X", amount + "");
						MsgNotice msg = new MsgNotice(uid,
								MsgType.NOTICE_SYS.type, textString);
						msg.setJumpMyRedpacket();
						msg.setPushMsg(textString);
					} else if (type == 2) { // 币
						TradeRecord tradeRecord = new TradeRecord(uid, uid,
								TradeType.REDPACKET_GET_COIN.type, 0,
								new Double(amount).intValue(),
								TradeCostType.COST_COIN.type, "开启好友红包获得"
										+ new Double(amount).intValue() + "币");
						tradeService.charge(tradeRecord);
					}
					return new BaseRespVO(200, true, isFree == 0 ? "-"
							+ RedpacketConstants.COSE_SCORE + "积分" : "领取成功");
				} else {
					return new BaseRespVO(-1, false, "网络异常，请重试~");
				}
			} else {
				return new BaseRespVO(-1, false, "红包类型有误~");
			}
		} catch (TradeOperationException e) {
			return new BaseRespVO(-1, false, "交易失败,请重试");
		} catch (NotEnoughBeanException e) {
			return new BaseRespVO(600, false, "呜呜，不足" + RedpacketConstants.COSE_SCORE + "积分无法领取红包，去抓娃娃就可获得积分哟~~");
		} catch (Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统异常，请重试~");
		}
	}

	@Override
	public List<InviteRedpacketRes> getList(Long uid) {
		List<InviteRedpacketRes> inviteRedpacks = new ArrayList<InviteRedpacketRes>();
		List<InvitePreRecord> invitePreRecords = inviteService
				.getInvitePreRecordList(uid, 1, RedpacketConstants.INVITE_SIZE);
		final int SIZE = invitePreRecords == null ? 0 : invitePreRecords.size();
		setBaseInviteRedpacks(inviteRedpacks, SIZE, uid);
		List<Long> uids = new ArrayList<Long>();
		if (!StringUtil.isNullOrEmpty(invitePreRecords)) {
			for (InvitePreRecord record : invitePreRecords) {
				uids.add(record.getRegUid());
			}
		}
		if (uids.size() > 0) {
			Map<Long, UserGeneral> userGeneralMap = userService
					.getUserGeneralMap(uids);
			int dateCode = Integer.parseInt(DateUtils
					.getCurrentDateString("yyyyMMdd"));
			List<UserRedpacketOpenRecord> openRecords = redpacketDao
					.getUserRedpacketOpenRecordList(uid, uids, dateCode);
			Map<Long, UserRedpacketOpenRecord> openRecordMap = setUserRedpacketOpenRecordMap(openRecords);
			List<UserRedpacketOpenRecord> notGetRecords = redpacketDao
					.getNotGetRecordList(uid, uids, dateCode);
			Map<Long, Long> notGetRecordMap = setNotGetRecordMap(notGetRecords);
			for (Long tid : uids) {
				UserGeneral userGeneral = userGeneralMap.get(tid);
				UserRedpacketOpenRecord record = openRecordMap.get(tid);
				if (userGeneral != null) {
					long time = 0;
					if (record != null) {
						time = (record.getNextTime().getTime() - new Date()
								.getTime()) / 1000; // 倒计时时间
					}
					inviteRedpacks.add(new InviteRedpacketRes(notGetRecordMap
							.get(tid), tid, userGeneral.getHead(), userGeneral
							.getName(), null, time, getFreeFlag(uid, tid)));
				}
			}
		}
		return inviteRedpacks;
	}

	@Override
	public BaseRespVO open(GetRedpacketReqVO reqVO) {
		Long uid = reqVO.getUid();
		Long tid = reqVO.getTid();
		Long id = reqVO.getId();
		LOG.info("open:uid-" + uid + ",tid-" + tid + ",id-" + id);
		RedpacketOpenRespVO respVO = new RedpacketOpenRespVO();
		double amount = 0;
		int rewardType = 0;
		int type = 0;
		int isCrit = 0; // 是否为暴击红包
		int dateCode = Integer.parseInt(DateUtils
				.getCurrentDateString("yyyyMMdd"));
		int openTimes = redpacketDao.getUserOpenTimes(uid, dateCode, tid);
		if (openTimes >= RedpacketConstants.FREE_TIMES) { // 需扣取积分
			UserRedpacketOpenRecord openRecord = null;
			if (id != null && id.longValue() > 0) {
				openRecord = redpacketDao.getUserRedpacketOpenRecordById(id);
			} else {
				openRecord = redpacketDao.getUserRedpacketOpenRecord(uid, tid,
						dateCode, 1);
			}
			if (openRecord != null
					&& openRecord.getNextTime() != null
					&& openRecord.getNextTime().getTime() > new Date()
							.getTime()) {
				return new RedpacketOpenRespVO(-1, false, "该红包还不能开启，请稍后再试~");
			}
			TradeAccount tradeAccount = tradeService.getAccount(uid);
			if (tradeAccount.getScore() < RedpacketConstants.COSE_SCORE) {
				return new RedpacketOpenRespVO(-1, false,
						"呜呜，不足" + RedpacketConstants.COSE_SCORE + "积分无法开启红包，去抓娃娃就可获得积分哟~~");
			}
			if (openRecord != null && openRecord.getStatus() == 0) {
				amount = openRecord.getNum();
				rewardType = openRecord.getType();
				id = openRecord.getId();
				isCrit = openRecord.getIsCrit();
			} else {
				int spentScoreCount = redpacketDao.getOpenRedpacketTime(uid,
						dateCode, 0, null);
				int nextCount = spentScoreCount + 1;
				if (nextCount == 3 || nextCount == 5
						|| (nextCount + 2) % 6 == 0) {
					rewardType = 1;
					type = 2;
					double allCost = tradeChargeDAO.getUserAllCost(uid, RedpacketConstants.EVENT_START_DATE);
					if (allCost <= 30) {
						amount = (new Random().nextInt(3) + 1) * 0.1; // 0.1-0.3
					} else if (allCost > 30 && allCost <= 100) {
						amount = (new Random().nextInt(3) + 2) * 0.1; // 0.2-0.4
					} else if (allCost > 100 && allCost <= 200) {
						amount = (new Random().nextInt(2) + 4) * 0.1; // 0.4-0.5
					} else {
						amount = (new Random().nextInt(4) + 5) * 0.1; // 0.5-0.8
					}
					if((ArithmeticUtils.multiply(amount, 100, 2))%2 == 0 
							&& RedpacketUtils.isCrit()
							&& !versionService.isAudit(reqVO.getOsType(), reqVO.getChannel(), reqVO.getVersion())) { // 出现暴击红包
						isCrit = 1;
						amount = ArithmeticUtils.divide(amount, 2, 2);
					}
				} else {
					type = RedpacketUtils.getOpenRedpackRewardType(0);
					if (type == 1) { // 金币
						amount = new Random().nextInt(10) + 5; //扣积分
						rewardType = 2;
					} else if (type == 2) { // 红包
						amount = (new Random().nextInt(4) + 1) * 0.1;
						if((ArithmeticUtils.multiply(amount, 100, 2))%2 == 0 && RedpacketUtils.isCrit()) { // 出现暴击红包
							isCrit = 1;
							amount = ArithmeticUtils.divide(amount, 2, 2);
						}
						rewardType = 1;
					}
				}
				id = redpacketDao.saveUseRedpacketOpenRecord(uid, tid,
						dateCode, 0, rewardType, amount, isCrit);
			}
		} else { // 不需扣取积分
			UserRedpacketOpenRecord openRecord = null;
			if (id != null && id.longValue() > 0) {
				openRecord = redpacketDao.getUserRedpacketOpenRecordById(id);
			} else {
				openRecord = redpacketDao.getUserRedpacketOpenRecord(uid, tid,
						dateCode, 0);
			}
			if (openRecord != null && openRecord.getStatus() == 0) {
				amount = openRecord.getNum();
				rewardType = openRecord.getType();
				id = openRecord.getId();
			} else {
				int freeGetcount = redpacketDao.getOpenRedpacketTime(uid,
						dateCode, 1, 1);
				if (freeGetcount >= 2) {
					amount = new Random().nextInt(2) + 1;
					rewardType = 2;
					type = 1;
				} else {
					type = RedpacketUtils.getOpenRedpackRewardType(1);
					if (type == 1) { // 金币
						amount = new Random().nextInt(4) + 1; //免费开
						rewardType = 2;
					} else if (type == 2) { // 红包
						amount = 0.08;
						rewardType = 1;
					}
				}
				id = redpacketDao.saveUseRedpacketOpenRecord(uid, tid,
						dateCode, 1, rewardType, amount, isCrit);
			}
		}

		respVO.setId(id);
		respVO.setAmount(amount);
		respVO.setRewardType(rewardType);
		respVO.setIsCrit(isCrit);
		respVO.setDesc(isCrit == 0 ? RedpacketConstants.OPEN_REDPACKET_REMARK : RedpacketConstants.OPEN_CRIT_REDPACKET_REMARK);
		respVO.setShareCode(isCrit == 1 ? RedpacketConstants.SUPER_REDPACK_SHARE_CODE : "");
		return respVO;
	}

	@Override
	public void saveUserRedpacketRewardRecord(Long uid, Long tid) {
		List<UserRedpacketRewardRecord> rewardRecords = redpacketDao
				.getUserRedpacketRewardRecordList(uid, RedpacketConstants.HAS_REWARD_NUM);
		final int size = rewardRecords == null ? 0 : rewardRecords.size();
		if (size < RedpacketConstants.HAS_REWARD_NUM) { // 成功邀请前3位好友才有奖励
			double amount = 0;
			Map<String, Object> comInfo = comInfo(uid);
			boolean isBefore = (boolean) comInfo.get("isBefore");
			if (isBefore) { // 3天以内
				if (size == 0) {
					amount = ArithmeticUtils.add(
							new Random().nextInt(201) * 0.01, 3, 2);
				} else if (size == 1) {
					amount = ArithmeticUtils.add(
							new Random().nextInt(201) * 0.01, 5, 2);
				} else {
					double sum = 0;
					for (UserRedpacketRewardRecord record : rewardRecords) {
						sum = ArithmeticUtils.add(record.getAmount(), sum, 2);
					}
					LOG.info("saveUserRedpacketRewardRecord:sum-" + sum);
					amount = ArithmeticUtils.sub(
							RedpacketConstants.INVITE_REWARD_TOTAL_AMT, sum, 2);
				}
			} else {
				amount = new Random().nextInt(5) + 1;
			}
			redpacketDao.saveUserRedpacketRewardRecord(uid, tid, amount);
		}
	}

	@Override
	public WithdrawInfo getWithdrawInfo(Long uid) {
		WithdrawUserAccount withdrawUserAccount = withdrawDao
				.getWithdrawUserAccountByUid(uid);
		boolean boundFlag = false;
		WithdrawWechatBoundMiddleware wechatBoundMidd = withdrawDao
				.getWithdrawWechatBoundMiddlewareByUid(uid);
		if (withdrawUserAccount != null || wechatBoundMidd != null) {
			boundFlag = true;
		}
		boolean followFlag = false;
		if (withdrawUserAccount != null
				&& withdrawUserAccount.getFollowFlag() == 1) {
			followFlag = true;
		}
		boolean certificationFlag = false;
		WithdrawUserCertificationInfo userCerInfo = certificationDao
				.getUserCertificationInfo(uid);
		if (userCerInfo != null
				&& !StringUtil.isNullOrEmpty(userCerInfo.getCertificationId())) {
			certificationFlag = true;
		}
		UserRedpacket userRedpack = redpacketDao.getUserRedpacket(uid);
		double amount = userRedpack == null ? 0 : userRedpack.getAmount();
		// 提现记录
		List<WithdrawRecord> withdrawRecords = new ArrayList<WithdrawRecord>();
		List<UserRedpacketLog> redpackLogs = redpacketDao
				.getUserRedpacketLogsByUid(uid, 100, RedpacketEnm.WITHDRAW.type);
		if (!StringUtil.isNullOrEmpty(redpackLogs)) {
			for (UserRedpacketLog log : redpackLogs) {
				WithdrawRecord record = new WithdrawRecord(log.getRemark(),
						new Double(log.getAmount()).intValue(), log
								.getCreateTime().getTime());
				withdrawRecords.add(record);
			}
		}

		//首次提现需满50元，此后需满10元/次。
		WithdrawInfo withdrawInfo = new WithdrawInfo(boundFlag, followFlag, certificationFlag,
				RedpacketConstants.FOLLOW_URL, amount, "提现需满50元。",
				withdrawRecords);
		LOG.info("getWithdrawInfo:uid-" + uid + ",withdrawInfo-" + JSON.toJSONString(withdrawInfo));
		return withdrawInfo;
	}

	@Override
	public BaseRespVO verifyAmt(Long uid, int amount) {
		if (amount <= 0) {
			return new BaseRespVO(-1, false, "请输入有效金额");
		}
		int times = withdrawDao.getWithdrawTimes(uid,
				RedpacketEnm.WITHDRAW.type);
		if (times == 0 && amount < 50) {
			return new BaseRespVO(-1, false, "满50元才能提现哟~~");
		}
		if (times >= 1 && amount < 50) {
			return new BaseRespVO(-1, false, "满50元才能提现哟~~");
		}
		UserRedpacket userRedpack = redpacketDao.getUserRedpacket(uid);
		double totalAmount = userRedpack == null ? 0 : userRedpack.getAmount();
		if (totalAmount < amount) {
			return new BaseRespVO(-1, false, "请输入有效金额");
		}
		if (amount > 200) {
			return new BaseRespVO(-1, false, "单笔提现不能超过200元哟~~");
		}
		int dateCode = Integer.parseInt(DateUtils
				.getCurrentDateString("yyyyMMdd"));
		int currDateTimes = withdrawDao.getCurrDateWithdrawTimes(uid,
				RedpacketEnm.WITHDRAW.type, dateCode);
		if (currDateTimes >= 1) {
			return new BaseRespVO(-1, false, "今日提现次数已达上限~~");
		}
		UserAttribute userAttribute = userAttributeService
				.getUserAttribute(uid);
		if (userAttribute != null && userAttribute.getRedpacket() == 1) {
			return new BaseRespVO(-1, false, "该账号被禁止提现，如有疑问请联系官方QQ群：790500948");
		}
		return new BaseRespVO();// BaseRespVO(200, true,
								// "申请已提交，红包将于5个工作日内发放到你的微信账号中");
	}

	@Override
	public BaseRespVO boundWechat(BoundWechatReqVO reqVO) {
		try {
			Long uid = reqVO.getUid();
			String unionId = reqVO.getUnionId();
			String openId = reqVO.getOpenId();
			WithdrawUserAccount withdrawUserAccount = withdrawDao
					.getWithdrawUserAccountByUid(uid);
			if (withdrawUserAccount == null
					|| StringUtil
							.isNullOrEmpty(withdrawUserAccount.getOpenId())) { // 未绑定
				WithdrawWechatBoundMiddleware wechatBoundMidd = withdrawDao
						.getWithdrawWechatBoundMiddlewareByUid(uid);
				if (wechatBoundMidd == null
						|| StringUtil.isNullOrEmpty(wechatBoundMidd
								.getUnionId())) {
					wechatBoundMidd = withdrawDao
							.getWithdrawWechatBoundMiddlewareByUnionId(unionId);
					if (wechatBoundMidd != null
							&& wechatBoundMidd.getUid().longValue() == uid
									.longValue()) {
						return new BaseRespVO(-1, false,
								"该微信号已被另一星球ID绑定，仍需绑定请换微信号");
					}
					if (withdrawDao.saveWithdrawWechatBoundMiddleware(uid,
							openId, unionId) > 0) {
						WithdrawWechatInnerMiddleware wechatInnerMidd = withdrawDao
								.getWithdrawWechatInnerMiddlewareByUnionid(unionId);
						if (wechatInnerMidd != null
								&& !StringUtil.isNullOrEmpty(wechatInnerMidd
										.getOpenId())) {
							withdrawDao.addWithdrawUserAccount(uid,
									wechatInnerMidd.getOpenId());
						}
						return new BaseRespVO(200, true, "绑定成功");
					} else {
						return new BaseRespVO(-1, false, "网络异常，请重试~");
					}

				} else {
					return new BaseRespVO(-1, false, "该帐号已绑定过微信，请不要重复绑定");
				}
			} else {
				return new BaseRespVO(-1, false, "该帐号已绑定过微信，请不要重复绑定");
			}
		} catch (Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统异常，请重试~");
		}
	}

	@Override
	public BaseRespVO handleWithdraw(HandleWithdrawReqVO reqVO) {
		try {
			Long uid = reqVO.getUid();
			int withdrawAmt = reqVO.getWithdrawAmt();
			UserAttribute userAttribute = userAttributeService
					.getUserAttribute(uid);
			if (userAttribute != null && userAttribute.getRedpacket() == 1) { // 不可提现
				return new BaseRespVO(-1, false, "该账号提现功能暂被冻结，如有疑问，请联系客服");
			}
			WithdrawUserCertificationInfo userCerInfo = certificationDao
					.getUserCertificationInfo(uid);
			if (userCerInfo == null) {
				return new BaseRespVO(-1, false, "实名后才能提现，请填写实名信息");
			}
			WithdrawUserAccount userAccount = withdrawDao
					.getWithdrawUserAccountByUid(uid);
			if (userAccount == null
					|| StringUtil.isNullOrEmpty(userAccount.getOpenId())) {
				return new BaseRespVO(-1, false, "微信绑定异常，请重新绑定");
			}
			if (userAccount.getFollowFlag() == 0) {
				return new BaseRespVO(-1, false, "请关注公众号后再尝试提现");
			}
			/*if(StringUtil.isNullOrEmpty(userCerInfo.getPhone())) {
				return new BaseRespVO(-1, false, "请在公众号绑定手机再尝试提现");
			}*/
			BaseRespVO respVO = verifyAmt(uid, withdrawAmt);
			if (!respVO.isState()) {
				return respVO;
			}
			int dateCode = Integer.parseInt(DateUtils
					.getCurrentDateString("yyyyMMdd"));
			if (withdrawDao.saveWithdrawRecord(uid, userAccount.getOpenId(),
					withdrawAmt, dateCode, "申请提现" + withdrawAmt + "元") > 0) {
				saveOrUpdateUserRedpacket(uid, -withdrawAmt, "提现" + withdrawAmt
						+ "元", RedpacketEnm.WITHDRAW.type, 0);
				withdrawDao.updateWithdrawAccountTotalAmt(uid,
						withdrawAmt);
				return new BaseRespVO(200, true, "申请已提交，红包将于5个工作日内发放到你的微信账号中");
			} else {
				return new BaseRespVO(-1, false, "网络异常，请重试~");
			}
		} catch (Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统异常，请重试~");
		}
	}

	@Override
	public int handleWechatUnionBound(String openId, String unionId) {
		// 查看是否有记录此公众号内部关系
		String innerOpenId = withdrawDao.getWechatInnerBoundOpenId(unionId);
		if (StringUtil.isNullOrEmpty(innerOpenId)) {
			withdrawDao.addWechatInnerMiddleware(openId, unionId);
			innerOpenId = openId;
		}
		Long boundUid = withdrawDao.getBoundUid(innerOpenId);
		if (boundUid == null || boundUid == 0) {// 安卓
			// 找出开发者union对应绑定的uid
			Long uid = withdrawDao.getAppWechatBoundUidByUnionId(unionId);
			if (uid != null && uid > 0) {// 有记录
				WithdrawUserAccount withdrawUserAccount = withdrawDao
						.getWithdrawUserAccountByUid(uid);
				if (withdrawUserAccount == null
						|| StringUtil.isNullOrEmpty(withdrawUserAccount
								.getOpenId())) {// 未完成绑定
					// 将uid和openId绑定
					withdrawDao.addWithdrawUserAccount(uid, openId);
					withdrawDao.updateWithdrawAccountFollow(uid);
				} else if (withdrawUserAccount != null
						&& withdrawUserAccount.getFollowFlag() == 0) {// 设置为已关注
					withdrawDao.updateWithdrawAccountFollow(uid);
				}
			}
		} else {// 能够找到uid的一般为ios或已绑定过的号
			if (boundUid != null && boundUid > 0) {// 设置为已关注
				withdrawDao.updateWithdrawAccountFollow(boundUid);
			}
		}
		return 1;
	}

	@Override
	public BusRedpacket getBusRedpacket(long uid, int busId, long optId) {
		UserBase userBase=userService.getUserBase(uid);
		if(userBase!=null&&userBase.getVersion()>=110){//110版本后开始红包
			DollOptRecord record = dollRecordService.getOptRecord(optId);
			LOG.info("getBusRedpacket record:" + JSON.toJSONString(record));
			if (record != null && record.getUid() == uid) {
				// 未领过的红包
				if (redpacketDao.hasGetBusRedpacket(optId) == 0) {
					// 具体获奖逻辑
					double amt = 0;
					//获取累计全部未获取次数的红包
					amt =0;// getFailRedpacket(uid, busId, optId,record.getResult() == 1?true:false);
					if (record.getResult() == 1) {// 捉取成功
						amt += new Random().nextInt(10) * 0.01 + 0.2;
					} 
					//实际红包金额
					amt=amt/2;
					java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
					String amtS = df.format(amt);
					amt = new Double(amtS);
					if (amt > 0) {
						int redId=redpacketDao.addBusRedpacket(uid, amt, busId, optId);
						DollInfo dollInfo = dollInfoService.getDollInfo(record.getDollId());
						saveOrUpdateUserRedpacket(uid, amt, "在房间 " + dollInfo.getName()+"获得天降红包",
								RedpacketEnm.BUS_REWARD.type, optId);
						// 消息
						String textString = "运气太好啦，天降" + amt + "元红包，继续抓娃娃还会有红包哟！";
						if (record.getResult() == 1) {// 捉取成功
							DollInfo info = dollInfoService
									.getDollInfoByBusId(busId);
							String dollName = "娃娃";
							if (info != null) {
								dollName = info.getName();
							}
							textString = "成功抓到" + dollName + "并分享成功，送你" + amt
									+ "元红包！";
						}
						MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type,
								textString);
						msg.setJumpWithdraw();
						msg.setPush("天降红包", textString);
						
						msgService.sendMsg(msg);
						//恭喜昵称XXX在XX号机抓娃娃获得XX元天降红包！
						UserGeneral userGeneral=userService.getUserGeneral(uid);
						if (userGeneral!=null) {
							//sendRedpacketRoomMsg(userGeneral, amt, busId,false);
						}
						BusRedpacket busRedpacket=new BusRedpacket(uid, busId,redId, amt);
						if (!versionService.isAuditChannel(userBase.getChannel(), userBase.getVersion())&&new Random().nextInt(2)==1) {//暴击红包
							busRedpacket.updateSuperRedpacket();
							if (userBase.getVersion()<121) {
								busRedpacket.setTitle("运气太好啦，天降红包！");
							}
						}
						return busRedpacket;
					}
				}
			}
		}
		return null;
	}

	private void sendRedpacketRoomMsg(final UserGeneral userGeneral,final double amt,final int busId,final boolean isSuper){
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				String msgText= "<font color='#FF7B7B'>恭喜 "+userGeneral.getName()+" 在"+busId+"号机抓娃娃获得"+amt+"元天降红包！</font>";
				if (isSuper) {
					msgText= "<font color='#FF7B7B'>恭喜 "+userGeneral.getName()+" 成功暴击,再获"+amt+"元红包！</font>";
				}
				MsgRoomJump msgRoomSys = new MsgRoomJump( MsgRoomType.BUS_SYS.type,msgText,null);
				msgRoomSys.setSave(true);
				if (amt>0.2) {//超过0.2发全站
					msgService.sendMsgRoomAll(msgRoomSys);
				}else{
					msgService.sendMsgRoom(msgRoomSys);
				}
			}
		}, 3);
	}
	/**
	 * 失败红包
	 * 
	 * @param uid
	 * @param busId
	 * @param optId
	 * @return
	 */
	private double getFailRedpacket(long uid, int busId, long optId,boolean isIncludeSuccess) {
		double resAmt = 0;
		try {
			UserAbandonSummry abandonSummry = dollRecordService
					.getUserAbandonSummry(uid);
			LOG.info("getFailRedpacket uid:" + uid + " optId:" + optId
					+ " abandonSummry:" + JSON.toJSONString(abandonSummry));
			if (abandonSummry != null) {
				if (abandonSummry.getLastOptId() <= optId) {// 判断是否翻旧操作
					int total = dollRecordService.getUserTotalPlayCount(uid);
					java.text.DecimalFormat df = new java.text.DecimalFormat(
							"#.00");
					LOG.info("getFailRedpacket uid:" + uid + " optId:" + optId
							+ " total:" + total + " abandonSummry:"
							+ JSON.toJSONString(abandonSummry));
					// 前两次判断
					DollBus dollBus = dollBusService.getDollBus(busId);
					int price=1;
					if (dollBus!=null) {
						price=dollBus.getPrice();
					}
					//上次结束上机的累计上机次数
					int lastTotalNum=abandonSummry.getLastTotalNum();
					dollRecordService.updateUserAbandonSummry(uid, total);
					if (isIncludeSuccess) {
						total=total-1;
					}
					for (int i = (lastTotalNum+1); i <= total; i++) {
						try {
							if (i== 1) {// （0.15-0.25元）*X/20
								double amt = (new Random().nextInt(10) * 0.01 + 0.15)
										* price / 20;
								String amtS = df.format(amt);
								resAmt = resAmt + new Double(amtS);
								LOG.info("getFailRedpacket uid:" + uid + " optId:"
										+ optId + " resAmt:" + resAmt + " total==1");
							} else if (i== 3) {// （0.50-0.80元）*X/20
								double amt = (new Random().nextInt(30) * 0.01 + 0.5)
										* price / 20;
								String amtS = df.format(amt);
								resAmt = resAmt + new Double(amtS);
								LOG.info("getFailRedpacket uid:" + uid + " optId:"
										+ optId + " resAmt:" + resAmt + " total==3");
							} else if (i == 5 || i == 10 || i == 15 || i == 20) {// （0.30-0.40元）*X/20
								double amt = (new Random().nextInt(10) * 0.01 + 0.3)
										* price / 20;
								String amtS = df.format(amt);
								resAmt = resAmt + new Double(amtS);
								LOG.info("getFailRedpacket uid:" + uid + " optId:"
										+ optId + " resAmt:" + resAmt + " total==5");
							} else if (i > 20) {// 累计抓满20次之后：每次抓取有30%机会获得红包
								int rand = new Random().nextInt(100);
								if (rand > 54 && rand <= 84) {
									// （0.10-0.15元）*X/20
									double amt = (new Random().nextInt(5) * 0.01 + 0.1)
											* price / 20;
									String amtS = df.format(amt);
									resAmt = resAmt + new Double(amtS);
								}
								LOG.info("getFailRedpacket uid:" + uid + " optId:"
										+ optId + " resAmt:" + resAmt + " total>20");
							}
						} catch (Exception e) {
							PrintException.printException(LOG, e);
						}
					}
				}
			}
		} catch (Exception e) {
			PrintException.printException(LOG, e);
		}
		return resAmt;
	}

	@Override
	public UserRedpacketLog getUserRedpacketLog(long uid, int type) {
		return redpacketDao.getUserRedpacketLog(uid, type);
	}

	@Override
	public BaseRespVO saveInvitePreRecord(Long uid, String unionId) {
		try {
			InvitePreRecord record = inviteDAO
					.getInvitePreRecordByUnionId(unionId);
			LOG.info("saveInvitePreRecord-unionId:" + unionId);
			if (record == null || record.getRegState() == 0) {
				if (inviteDAO.addPreInviteRecord(uid, unionId) > 0) {
					return new BaseRespVO(200, true, "记录保存成功~");
				} else {
					return new BaseRespVO(-1, false, "网络异常，请重试~");
				}
			} else {
				return new BaseRespVO(200, true, "微信号已被注册~");
			}
		} catch (Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "记录保存失败~");
		}
	}

	@Override
	public UserRedpacket getUserRedpacket(Long uid) {
		return redpacketDao.getUserRedpacket(uid);
	}

	@Override
	public void doPayWechatMoneySchedule() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				while(true) {
					WithdrawDetailRecord record = withdrawDao.withdrawDetailRecord(1);
					if (record != null) {
						WithdrawUserAccount mobileBoundInfo = withdrawDao.getWithdrawUserAccountByUid(record.getUid());
						if (mobileBoundInfo == null || mobileBoundInfo.getOpenId() == null
								|| mobileBoundInfo.getOpenId().trim().equals("")) {
							break;
						} else {
							record.setOpenId(mobileBoundInfo.getOpenId().trim());
						}
						// 判断uid一分钟内是否有提现记录
						if (withdrawDao.hasWithdrawInAMinute(record.getUid()) < 1 
								&& withdrawDao.hasWithdrawInAMinute(record.getOpenId().trim()) < 1) {
							// 更新此订单状态
							if (withdrawDao.updateWithdrawRecordsAsPay(record.getId()) > 0) {
								// 发放奖励
								sendMoneyToUid(record.getUid(), record.getAmt(), record.getOpenId(), record.getId());
							}
						} else {
							LOG.info("withdrawDetailRecord:"
									+ record + " 1分钟内有提现，推迟至下了一轮发放");
						}
					} else {
						break;
					}
				}
			}
		});
	}
	
	/**
	 * 发送红包
	 * 
	 * @param uid
	 * @param amt
	 * @param openId
	 * @return
	 */
	private boolean sendMoneyToUid(long uid, double amt, String openId,
			long recordId) {
		LOG.info("begin sendMoneyToUid uid:" + uid + " amt:" + amt
				+ " openId:" + openId + " recordId:" + recordId);
		boolean resultFlag = false;
		if (keyStore == null) {
			init();
		}
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, merchantsId.toCharArray())
					.build();
		} catch (KeyManagementException e) {
			LOG.info("sendMoneyToUid EXCEPTION KeyManagementException e:"
					+ e + " " + e.getMessage());
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			LOG.info("sendMoneyToUid EXCEPTION UnrecoverableKeyException e:"
					+ e + " " + e.getMessage());
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			LOG.info("sendMoneyToUid EXCEPTION NoSuchAlgorithmException e:"
					+ e + " " + e.getMessage());
			e.printStackTrace();
		} catch (KeyStoreException e) {
			LOG.info("sendMoneyToUid EXCEPTION KeyStoreException e:" + e
					+ " " + e.getMessage());
			e.printStackTrace();
		}
		// 指定TLS版本
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		// 设置httpclient的SSLSocketFactory
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).build();
		HttpPost httppost = new HttpPost(
				"https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack");
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("nonce_str", WebWeixinCommonUtils
				.getNonceStr()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 订单号
		String mch_billno = merchantsId + sdf.format(new Date())
				+ WebWeixinCommonUtils.getTimeStamp();
		formparams.add(new BasicNameValuePair("mch_billno", mch_billno));
		formparams.add(new BasicNameValuePair("mch_id", merchantsId));
		formparams.add(new BasicNameValuePair("wxappid",
				appId));
		formparams.add(new BasicNameValuePair("send_name", "娃娃星球"));
		formparams.add(new BasicNameValuePair("re_openid", openId));
		formparams.add(new BasicNameValuePair("total_amount", new Double(amt*100).intValue()  + ""));
		formparams.add(new BasicNameValuePair("total_num", 1 + ""));
		formparams.add(new BasicNameValuePair("wishing", "每次抓娃娃都有机会天降红包哟"));
		formparams.add(new BasicNameValuePair("client_ip", MobileWithdrawSecure
				.getIp()));
		formparams.add(new BasicNameValuePair("act_name", "娃娃星球"));
		formparams.add(new BasicNameValuePair("remark", "娃娃星球"));
		String sign = MobileWithdrawSecure.getSgin(formparams,
				apiKey);
		LOG.info("--------------------sign:" + sign);
		formparams.add(new BasicNameValuePair("sign", sign));
		String dataString = WebWeixinXmlUtils.getXmlMsg(formparams);
		LOG.info("--------------------------------------");
		LOG.info("wechat withdraw mch_billno：" + mch_billno + " 发送给微信提现请求： "
				+ dataString);
		withdrawDao.addWechatCallbackLog(recordId, mch_billno, uid,
				openId, amt, dataString);
		LOG.info("--------------------------------------");
		try {
			StringEntity myEntity = new StringEntity(dataString,
					ContentType.APPLICATION_JSON);// 构造请求数据
			httppost.setEntity(myEntity);// 设置请求体
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String responeString = EntityUtils
							.toString(entity, "UTF-8");
					LOG.info("--------------------------------------");
					LOG.info("wechat withdraw mch_billno：" + mch_billno
							+ " Response content: " + responeString);
					LOG.info("--------------------------------------");
					// 记录返回内容
					Matcher contentMatcher = returnCodePattern
							.matcher(responeString);
					if (contentMatcher.find()) {
						String contentStr = contentMatcher.group();
						if (contentStr.contains("SUCCESS")) {
							Matcher resultCodeMatcher = resultCodePattern
									.matcher(responeString);
							if (resultCodeMatcher.find()) {
								String resultCodeStr = resultCodeMatcher
										.group();
								if (resultCodeStr.contains("SUCCESS")) {
									resultFlag = true;
								}
							}
						}
					}
					int successflag = 2;
					if (resultFlag) {
						successflag = 1;
						withdrawDao.updateReason(recordId, "");
					} else {// 支付不成功则重置
						try {
							withdrawDao.resetWithdrawRecords(recordId);
							Matcher errCodePatternMatcher = errCodePattern
									.matcher(responeString);
							if (errCodePatternMatcher.find()) {
								String errCodePatternMatcherStr = errCodePatternMatcher
										.group();
								if (errCodePatternMatcherStr
										.contains("NOTENOUGH")) {// 余额不足
									Matcher returnMsgPatternMatcher = returnMsgPattern
											.matcher(responeString);
									if (returnMsgPatternMatcher.find()) {
										String msg = returnMsgPatternMatcher
												.group();
										msg = getValue(msg);
										withdrawDao.updateReason(recordId, msg);
									}
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					withdrawDao.updateWechatCallbackLog(recordId,
							mch_billno, responeString, successflag);
					// 在原提现记录中加入微信支付订单号及完成日期
					withdrawDao.updateWithdrawRecordsWithWechatInfo(
							recordId, mch_billno, successflag);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			LOG.info("sendMoneyToUid EXCEPTION ClientProtocolException e:"
					+ e + " " + e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			LOG.info("sendMoneyToUid EXCEPTION UnsupportedEncodingException e:"
					+ e1 + " " + e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e) {
			LOG.info("sendMoneyToUid EXCEPTION IOException e:" + e + " "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultFlag;
	}
	
	@Override
	public void init() {
		keyStore = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e) {
			LOG.info("init EXCEPTION e:" + e + " " + e.getMessage());
			e.printStackTrace();
		}
		// 读取本机存放的PKCS12证书文件
		FileInputStream instream = null;
		try {
			instream = new FileInputStream(new File(withdrawFile));
			try {
				// 指定PKCS12的密码(商户ID)
				keyStore.load(instream, merchantsId.toCharArray());
			} catch (NoSuchAlgorithmException e) {
				LOG.info("init EXCEPTION NoSuchAlgorithmException e:" + e
						+ " " + e.getMessage());
				e.printStackTrace();
			} catch (CertificateException e) {
				LOG.info("init EXCEPTION CertificateException e:" + e + " "
						+ e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				LOG.info("init EXCEPTION open IOException e:" + e + " "
						+ e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					instream.close();
				} catch (IOException e) {
					LOG.info("init EXCEPTION close IOException e:" + e + " "
							+ e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			LOG.info("init EXCEPTION FileNotFoundException e:" + e + " "
					+ e.getMessage());
			e.printStackTrace();
		}
		LOG.info("load keyStore success " + keyStore);
	}
	
	private String getValue(String str) {
		int start = str.indexOf("CDATA[");
		int end = str.indexOf("]]>");
		return str.substring(start + 6, end);
	}

	
	@Override
	public void doPayWechatMoneyResultCheckSchedule() {
		// 先找出4天前需要检查的提现订单号
		List<WithdrawWechatCommunicationLog> list = withdrawDao.getMobileWithdrawWechatCommunicationRecordList();
		if (list != null) {
			for (WithdrawWechatCommunicationLog mobileWithdrawWechatCommunicationRecord : list) {
				LOG.info("mobileWithdrawWechatCommunicationRecord:"
						+ mobileWithdrawWechatCommunicationRecord);
				if (keyStore == null) {
					init();
				}
				SSLContext sslcontext = null;
				try {
					sslcontext = SSLContexts
							.custom()
							.loadKeyMaterial(keyStore,
									merchantsId.toCharArray()).build();
				} catch (KeyManagementException e) {
					LOG.info("sendMoneyToUid EXCEPTION KeyManagementException e:"
							+ e + " " + e.getMessage());
					e.printStackTrace();
				} catch (UnrecoverableKeyException e) {
					LOG.info("sendMoneyToUid EXCEPTION UnrecoverableKeyException e:"
							+ e + " " + e.getMessage());
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					LOG.info("sendMoneyToUid EXCEPTION NoSuchAlgorithmException e:"
							+ e + " " + e.getMessage());
					e.printStackTrace();
				} catch (KeyStoreException e) {
					LOG.info("sendMoneyToUid EXCEPTION KeyStoreException e:"
							+ e + " " + e.getMessage());
					e.printStackTrace();
				}
				// 指定TLS版本
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
						sslcontext,
						new String[] { "TLSv1" },
						null,
						SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
				// 设置httpclient的SSLSocketFactory
				CloseableHttpClient httpclient = HttpClients.custom()
						.setSSLSocketFactory(sslsf).build();
				HttpPost httppost = new HttpPost(
						"https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo");
				// 创建参数队列
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();

				formparams.add(new BasicNameValuePair("nonce_str",
						WebWeixinCommonUtils.getNonceStr()));
				// 订单号
				String mch_billno = mobileWithdrawWechatCommunicationRecord
						.getBillId();
				formparams
						.add(new BasicNameValuePair("mch_billno", mch_billno));
				formparams.add(new BasicNameValuePair("mch_id", merchantsId));
				formparams.add(new BasicNameValuePair("appid",
						appId));
				formparams.add(new BasicNameValuePair("bill_type", "MCHT"));
				String sign = MobileWithdrawSecure.getSgin(formparams,
						apiKey);
				formparams.add(new BasicNameValuePair("sign", sign));
				String dataString = WebWeixinXmlUtils.getXmlMsg(formparams);
				// 插入请求记录
				LOG.info("--------------------------------------");
				LOG.info("wechat withdraw mch_billno：" + mch_billno + " id:"
						+ mobileWithdrawWechatCommunicationRecord.getId()
						+ " 发送给微信查询请求： " + dataString);
				LOG.info("--------------------------------------");
				try {
					StringEntity myEntity = new StringEntity(dataString,
							ContentType.APPLICATION_JSON);// 构造请求数据
					httppost.setEntity(myEntity);// 设置请求体
					CloseableHttpResponse response = httpclient
							.execute(httppost);
					try {
						HttpEntity entity = response.getEntity();
						if (entity != null) {
							String responeString = EntityUtils.toString(entity,
									"UTF-8");
							LOG.info("--------------------------------------");
							LOG.info("wechat withdraw mch_billno："
									+ mch_billno + " Response content: "
									+ responeString);
							LOG.info("--------------------------------------");
							// 记录返回内容
							boolean returnCodeFlag = false;
							boolean resultCodeFlag = false;
							String timeString = "2000-01-01 00:00:00";
							Matcher returnCodeMatcher = returnCodePattern
									.matcher(responeString);
							if (returnCodeMatcher.find()) {
								String returnCodeStr = returnCodeMatcher
										.group();
								if (returnCodeStr.contains("SUCCESS")) {
									returnCodeFlag = true;
								}
							}
							Matcher resultCodeMatcher = resultCodePattern
									.matcher(responeString);
							if (resultCodeMatcher.find()) {
								String resultCodeStr = resultCodeMatcher
										.group();
								if (resultCodeStr.contains("SUCCESS")) {
									resultCodeFlag = true;
								}
							}
							if (returnCodeFlag && resultCodeFlag) {
								Matcher statusMatcher = statusPattern
										.matcher(responeString);
								if (statusMatcher.find()) {
									String statusStr = statusMatcher.group();
									if (statusStr.contains("REFUND")) {
										Matcher refund_timeMatcher = refund_timePattern
												.matcher(responeString);
										if (refund_timeMatcher.find()) {
											String resultCodeStr = refund_timeMatcher
													.group();
											timeString = getValue(resultCodeStr);
											withdrawDao
													.addWechatCallbackLog(
															mobileWithdrawWechatCommunicationRecord
																	.getRecordId(),
															mch_billno,
															mobileWithdrawWechatCommunicationRecord
																	.getUid(),
															mobileWithdrawWechatCommunicationRecord
																	.getOpenId(),
															-mobileWithdrawWechatCommunicationRecord
																	.getAmt(),
															dataString,
															responeString,
															timeString);
											withdrawDao
													.updateWithdrawRecordsWithWechatInfoPayState(
															mobileWithdrawWechatCommunicationRecord
																	.getRecordId(),
															-1);
											withdrawDao
													.resetWithdrawRecords(mobileWithdrawWechatCommunicationRecord
															.getRecordId());// 已退回则重新纳入提现审核
										}
									}
								}
							}
						}
					} finally {
						response.close();
					}
				} catch (ClientProtocolException e) {
					LOG.info("sendMoneyToUid EXCEPTION ClientProtocolException e:"
							+ e + " " + e.getMessage());
					e.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					LOG.info("sendMoneyToUid EXCEPTION UnsupportedEncodingException e:"
							+ e1 + " " + e1.getMessage());
					e1.printStackTrace();
				} catch (IOException e) {
					LOG.info("sendMoneyToUid EXCEPTION IOException e:" + e
							+ " " + e.getMessage());
					e.printStackTrace();
				} finally {
					// 关闭连接,释放资源
					try {
						httpclient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public BaseRespVO checkcode(Long uid, String numberStr) {
		BaseRespVO respVO = new BaseRespVO();
		try {
			if(uid == null || uid.longValue() <= 0) {
				return new BaseRespVO().getRespVO(ResCodeEnum.USER_NOT_FOUND);
			}
			Pattern p = Pattern.compile("^[1][0-9]{10}$");
			Matcher m = p.matcher(numberStr);
			if (!StringUtil.isNumber(numberStr)||!m.matches())
				return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_ERROR);
			WithdrawUserCertificationInfo cerInfo = certificationDao.getWithdrawUserCertificationInfoByPhone(numberStr);
			if(cerInfo != null && cerInfo.getUid().longValue() != uid.longValue()) {
				return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_BIND);
			}
			cerInfo = certificationDao.getUserCertificationInfo(uid);
			if(cerInfo != null && !StringUtil.isNullOrEmpty(cerInfo.getPhone())) {
				return new BaseRespVO().getRespVO(ResCodeEnum.USER_IS_BIND_PHONE);
			}
			long number = Long.valueOf(numberStr);
			WithdrawUserPhone phoneInit = new WithdrawUserPhone(number,uid, 1);
			WithdrawUserPhone phone = withdrawDao.getWithdrawUserPhoneByNumber(number);
			if (phone != null && DateUtils.isSameDay(phone.getSendTime())) {
				if (phone.getCount() >= RedpacketConstants.SENDSMS_DAY_MAX_SIZE && !AppUtils.isTestEnv()) {
					return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_OVER_DOLL);
				} else {
					phoneInit.setCount(phone.getCount() + 1);
				}
			}
			int checkCode = Integer.valueOf(UserUtil.buildDynNmbN(6));
			phoneInit.setCheckCode(checkCode);
			int flag = withdrawDao.initPhone(phoneInit);
			if (flag > 0) {
				userPhoneService.sendSMS(CheckCodeType.BIND.type, number, checkCode,new UserPhone((phone.getCount() + 1)));
				Map<String, Object> map = new HashMap<String, Object>();
				if (AppUtils.isTestEnv()) {
					map.put("checkCode", checkCode);
					respVO.setData(map);
				}
			} else {
				return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		}
		return respVO;
	}

	@Override
	public BaseRespVO bindPhone(Long uid, String numberStr, int checkCode) {
		if(uid == null || uid.longValue() <= 0) {
			return new BaseRespVO().getRespVO(ResCodeEnum.USER_NOT_FOUND);
		}
		Pattern p = Pattern.compile("^[1][0-9]{10}$");
		Matcher m = p.matcher(numberStr);
		if (!m.matches())
			return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_ERROR);
		long number = Long.valueOf(numberStr);
		WithdrawUserCertificationInfo cerInfo = certificationDao.getWithdrawUserCertificationInfoByPhone(numberStr);
		if(cerInfo != null && cerInfo.getUid().longValue() != uid.longValue()) {
			return new BaseRespVO().getRespVO(ResCodeEnum.NUMBER_IS_BIND);
		}
		cerInfo = certificationDao.getUserCertificationInfo(uid);
		if(cerInfo != null && !StringUtil.isNullOrEmpty(cerInfo.getPhone())) {
			return new BaseRespVO().getRespVO(ResCodeEnum.USER_IS_BIND_PHONE);
		}
		WithdrawUserPhone phone = withdrawDao.getWithdrawUserPhoneByNumber(number);
		if (checkCode == phone.getCheckCode()) {
			int flag = certificationDao.updateBindNumber(uid, number);
			if (flag > 0) {
				return new BaseRespVO();
			} else {
				return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
			}
		} else {
			return new BaseRespVO().getRespVO(ResCodeEnum.CHECKCODE_IS_ERROR);
		}
	}

	@Override
	public List<RedpacketLogRes> getRecords(Long uid, int page) {
		List<RedpacketLogRes> records = new ArrayList<RedpacketLogRes>();
		List<UserRedpacketLog> logs = redpacketDao.getRecords(uid, page, 20);
		if(!StringUtil.isNullOrEmpty(logs)) {
			for(UserRedpacketLog log : logs) {
				records.add(new RedpacketLogRes(log));
			}
		}
		return records;
	}

	@Override
	public BaseRespVO handleInvite(Long uid, Long id) {
		try {
			LOG.info("handleInvite:uid-" + uid + ",id-" + id);
			if(uid == null || uid.longValue() <= 0) {
				return new BaseRespVO(-1, false, "用户不存在~");
			}
			UserRedpacketOpenRecord record = redpacketDao.getUserRedpacketOpenRecordById(id);
			if(record == null || record.getUid().longValue() != uid.longValue()) {
				return new BaseRespVO(-1, false, "没有可领取的相关红包~");
			}
			if(record.getStatus() == 1) {
				return new BaseRespVO(-1, false, "红包已领取~");
			}
			double newAmount = ArithmeticUtils.multiply(record.getNum(), 2, 2);
			if(redpacketDao.handleDouble(id, newAmount) > 0) {
				RedpacketOpenRespVO respVO = new RedpacketOpenRespVO();
				respVO.setId(id); 
				respVO.setAmount(newAmount);
				respVO.setRewardType(1);
				respVO.setDesc(RedpacketConstants.CRIT_REDPACKET_INVITE_DESC);
				respVO.setShareDesc(RedpacketConstants.CRIT_REDPACKET_SHARE_DESC);
				return respVO;
			} else {
				return new BaseRespVO(-1, false, "该红包已暴击过，不可重复暴击~");
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统异常，请重试~");
		}
	}

	@Override
	public BaseRespVO getInviteSituation(Long uid) {
		GetInviteSituationRespVO respVO = new GetInviteSituationRespVO();
		boolean isOverNum = isOverNum(uid);
		respVO.setIsOverNum(isOverNum);
		Map<String, Object> comInfo = comInfo(uid);
		boolean isBefore = (boolean) comInfo.get("isBefore");
		respVO.setIsBefore(isBefore);
		RedpacketRes inviteRedpacketRes = null;
		if(!isOverNum) {
			// 邀请红包信息
			List<UserRedpacketRewardRecord> rewardRecords = redpacketDao.getUserRedpacketRewardRecordList(uid, RedpacketConstants.HAS_REWARD_NUM); // 邀请奖励记录列表
			if(isBefore) { // 72小时内
				double getAmt = getUserGetAmt(uid); // 已领的红包金额
				double notGetAmt = ArithmeticUtils.sub(RedpacketConstants.INVITE_REWARD_TOTAL_AMT, getAmt, 2); // 待领的红包金额
				inviteRedpacketRes = getInviteRedpacketRes(getAmt, notGetAmt, isBefore, 0, uid);
			} else {
				inviteRedpacketRes = getInviteRedpacketRes(0, 0, isBefore, rewardRecords == null ? RedpacketConstants.HAS_REWARD_NUM : RedpacketConstants.HAS_REWARD_NUM - rewardRecords.size(), uid);
			}
		} else {
			inviteRedpacketRes = new RedpacketRes();
			inviteRedpacketRes.setDesc("<font color=\"#222222\">邀请好友越多，可领红包越多！</font>");
		}
		respVO.setInviteRedpacketRes(inviteRedpacketRes);
		return respVO;
	}

	@Override
	public boolean isOverNum(Long uid) {
		//int inviteCount = inviteService.getInvitePreRecordByUid(uid, 1);
		List<UserRedpacketRewardRecord> rewardRecords = redpacketDao.getUserRedpacketRewardRecordList(uid, RedpacketConstants.HAS_REWARD_NUM);
		if(!StringUtil.isNullOrEmpty(rewardRecords) && rewardRecords.size() == RedpacketConstants.HAS_REWARD_NUM) {
			return true;
		}
		return false;
	}

	@Override
	public BusRedpacket getSuperBusRedpacket(long redpacketId,long uid) {
		LOG.info("getSuperBusRedpacket redpacketId:"+redpacketId+" uid:"+uid);
		BusRedpacketRecord record=redpacketDao.getBusRedpacket(redpacketId);
		LOG.info("getSuperBusRedpacket redpacketId:"+redpacketId+" uid:"+uid+" record:"+JSON.toJSONString(record));
		if (record!=null&&record.getUid()==uid) {
			LOG.info("getSuperBusRedpacket redpacketId:"+redpacketId+" uid:"+uid+" record!=null&&record.getUid()==uid");
			if(redpacketDao.hasGetSuperBusRedpacket(redpacketId)==0){
				redpacketDao.addGetSuperBusRedpacket(redpacketId, uid);
				double amt=record.getAmount();
				if (amt>0) {
					int redId=redpacketDao.addBusRedpacket(uid, amt, record.getBusId(), record.getOptId());
					saveOrUpdateUserRedpacket(uid, amt, "暴击红包",
							RedpacketEnm.BUS_REWARD.type, record.getOptId());
					BusRedpacket redpacket=new BusRedpacket(uid, record.getBusId(),redId, amt*2);
					redpacket.updateSuperRedpacketShare();
					UserGeneral userGeneral=userService.getUserGeneral(uid);
					if (userGeneral!=null) {
						String textString = "恭喜成功暴击，再获"+amt+"元红包！继续抓娃娃还会有暴击红包哟！";
						MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type,
								textString);
						msg.setJumpWithdraw();
						msg.setPush("暴击红包", textString);
						msgService.sendMsg(msg);
						sendRedpacketRoomMsg(userGeneral, amt*2, record.getBusId(),true);
					}
					return redpacket;
				}
			}
		}
		return null;
	}

	@Override
	public double getUserGetAmt(Long uid) {
		List<UserRedpacketRewardRecord> rewardRecords = redpacketDao.getUserRedpacketRewardRecordList(uid, RedpacketConstants.HAS_REWARD_NUM); // 邀请奖励记录列表
		double getAmt = 0; // 已领的红包金额
		if(!StringUtil.isNullOrEmpty(rewardRecords)) {
			for(UserRedpacketRewardRecord record : rewardRecords) {
				if(record.getStatus() == 1) {
					getAmt = ArithmeticUtils.add(record.getAmount(), getAmt, 2);
				}
			}
		}
		return getAmt;
	}

	@Override
	public BaseRespVO getPageInfo(Long uid) {
		UserGeneral selfUserGeneral = userService.getUserGeneral(uid);
		List<RoomRedpacketRecord> records = new ArrayList<RoomRedpacketRecord>();
		List<UserRedpacketLog> logs = redpacketDao.getRecentUserRedpacketLogs(RedpacketEnm.BUS_REWARD.type, 50); // 最近的房间红包记录
		if(!StringUtil.isNullOrEmpty(logs)) {
			List<Long> uids = new ArrayList<Long>(); // 用户ID
			List<Long> optIds = new ArrayList<Long>(); // 操作ID
			for(UserRedpacketLog log : logs) {
				uids.add(log.getUid());
				optIds.add(log.getOptId());
			}
			
			Map<Long, UserGeneral> userGeneralMap = userService.getUserGeneralMap(uids);
			for(UserRedpacketLog log : logs) {
				UserGeneral userGeneral = userGeneralMap.get(log.getUid());
				DollOptRecord dollOptRecord = dollRecordService.getOptRecord(log.getOptId());
				if(userGeneral != null && dollOptRecord != null) {
					RoomRedpacketRecord record = new RoomRedpacketRecord(userGeneral.getName(), dollOptRecord.getBusId(), 
							log.getAmount() < 0.5 ? ArithmeticUtils.multiply(log.getAmount(), 10, 2) : log.getAmount(), DateUtils.dateToString(log.getCreateTime(), "HH:mm:ss"));
					records.add(record);
				}
			}
		}
		return new GetPageInfoRespVO(selfUserGeneral, getUserAllAmt(uid), records);
	}
	
	private Map<String, Object> comInfo(Long uid) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isBefore = false;
		long remainingTime = 0;
		UserBase userBase = userService.getUserBase(uid);
		Date rewardStartTime = DateUtils.string2Date(RedpacketConstants.GET_ALL_REWARDING_START_DATE, "yyyy-MM-dd HH:mm:ss");
		Date regTime = userBase.getRegTime(); // 注册时间
		Date endTime = null;
		Date comTime = new Date(); // 对比时间
		if(regTime != null && regTime.getTime() >= rewardStartTime.getTime()) {
			endTime = DateUtils.addDate(regTime, RedpacketConstants.GET_ALL_REWARDING_AMOUNT_DAYS);
		} else {
			endTime = DateUtils.addDate(DateUtils.string2Date(RedpacketConstants.GET_ALL_REWARDING_START_DATE, "yyyy-MM-dd HH:mm:ss"), 
					RedpacketConstants.GET_ALL_REWARDING_AMOUNT_DAYS);
		}
		long betweenTime = endTime.getTime()-comTime.getTime();
		
		if(betweenTime >= 0 && betweenTime <= RedpacketConstants.GET_ALL_REWARDING_AMOUNT_DAYS*24*60*60*1000) { // 72小时内
			isBefore = true;
			remainingTime = betweenTime;
		}
		LOG.info("comInfo:uid-" + uid + ",isBefore-" + isBefore + ",remainingTime-" + remainingTime);
		resultMap.put("isBefore", isBefore);
		resultMap.put("remainingTime", remainingTime);
		return resultMap;
	}

	@Override
	public double getUserAllAmt(Long uid) {
		double allGetAmt = redpacketDao.getAllOtherAmt(uid);
		return ArithmeticUtils.add(allGetAmt, 0, 2);
	}
}
