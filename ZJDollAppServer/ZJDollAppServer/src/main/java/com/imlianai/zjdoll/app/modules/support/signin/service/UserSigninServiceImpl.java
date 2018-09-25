package com.imlianai.zjdoll.app.modules.support.signin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacket;
import com.imlianai.zjdoll.domain.signin.SigninAward;
import com.imlianai.zjdoll.domain.signin.UserSignin;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.dao.UserDAO;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.publics.utils.ArithmeticUtils;
import com.imlianai.zjdoll.app.modules.support.dailytask.enm.TaskType;
import com.imlianai.zjdoll.app.modules.support.dailytask.service.DailytaskService;
import com.imlianai.zjdoll.app.modules.support.redpacket.enm.RedpacketEnm;
import com.imlianai.zjdoll.app.modules.support.redpacket.service.RedpacketService;
import com.imlianai.zjdoll.app.modules.support.signin.contants.UserSigninContants;
import com.imlianai.zjdoll.app.modules.support.signin.dao.UserSigninDao;
import com.imlianai.zjdoll.app.modules.support.signin.enm.AwardContentEnm;
import com.imlianai.zjdoll.app.modules.support.signin.enm.AwardEnm;
import com.imlianai.zjdoll.app.modules.support.signin.enm.AwardTypeEnm;
import com.imlianai.zjdoll.app.modules.support.signin.utils.UserSigninUtils;
import com.imlianai.zjdoll.app.modules.support.signin.vo.GetBoxContentRespVO;
import com.imlianai.zjdoll.app.modules.support.signin.vo.SigninAwardInfo;
import com.imlianai.zjdoll.app.modules.support.signin.vo.SigninRespVO;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.zjdoll.app.modules.support.version.service.VersionService;

@Service
public class UserSigninServiceImpl implements UserSigninService {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(UserSigninServiceImpl.class);
	
	@Resource
	UserSigninDao userSigninDao;
	@Resource
	UserDAO userDAO;
	@Resource
	TradeService tradeService;
	@Resource
	UserDollService userDollService;
	@Resource
	VersionService versionService;
	@Resource
	MsgService msgService;
	@Resource
	RedpacketService redpacketService;
	@Resource
	DailytaskService dailytaskService;
	
	static List<SigninAwardInfo> signinAwardList = new ArrayList<SigninAwardInfo>(); // 签到奖励列表

	@Override
	public GetBoxContentRespVO getBoxContent(BaseReqVO reqVo) {
		GetBoxContentRespVO respVO = new GetBoxContentRespVO();
		respVO.setAwards(signinAwardList);
		respVO.setPlayingDesc(UserSigninContants.playingDesc);
		respVO.setTimes(userSigninDao.getSigninTimes(reqVo.getUid()));
		int signinStatus = 0;
		/*if(versionService.isAudit(reqVo.getOsType(), reqVo.getChannel(), reqVo.getVersion())) {
			signinStatus = 2;
		} else {
			if(isSignin(reqVo.getUid())) {
				signinStatus = 1;
			}
		}*/
		if(isSignin(reqVo.getUid())) {
			signinStatus = 1;
			int dateCode = Integer.parseInt(DateUtils.getCurrentDateString("yyyyMMdd")); 
			UserSignin userSignin = userSigninDao.getUserSigninByDateCode(reqVo.getUid(), dateCode);
			respVO.setId(userSignin.getAwardId());
		}
		respVO.setSigninStatus(signinStatus);
		return respVO;
	}
	
	@Override
	public BaseRespVO signin(Long uid, int version) {
		SigninRespVO respVO = new SigninRespVO();
		SigninAwardInfo awardInfo = null;
		double num = 0;
		int type = 0;
		try {
			int dateCode = Integer.parseInt(DateUtils.getCurrentDateString("yyyyMMdd")); 
			UserSignin userSignin = userSigninDao.getUserSigninByDateCode(uid, dateCode);
			if(userSignin == null) { // 今日未签到
				int times  = userSigninDao.getSigninTimes(uid);
				Map<String, Object> dataMap = null;
				if(times == 0) {
					dataMap = getAwardInfoMap(1, 1.3);
					num = 1.3;
				} else if(times == 1) {
					dataMap = getAwardInfoMap(4, 10);
					num = 10;
					type = 1;
				} else if(times == 2) {
					dataMap = getAwardInfoMap(2, 5);
					num = 5;
				} else if((times + 1) % 7 == 0) { // 每累计签到7天必得红包
					num = ArithmeticUtils.add(new Random().nextInt(21) * 0.01, 0.15, 2);
					dataMap = getAwardInfoMap(1, num);
				} else {
					UserRedpacket userRedPacket = redpacketService.getUserRedpacket(uid); // 用户红包
					int awardType = UserSigninUtils.getAwardType(userRedPacket.getAmount());
					AwardEnm awardEnm = AwardEnm.values()[awardType-1]; // 获得的奖品
					num = awardEnm.getNum();
					switch (awardEnm.getType()) {
					case 1: // 红包
						dataMap = getAwardInfoMap(1, num);
						break;
					case 2: // 游戏币
						dataMap = getAwardInfoMap(2, num);
						break;
					case 3:case 4: // 普通娃娃、稀有娃娃碎片
						int materialType = 3;
						int maxNum = 5; // 最大娃娃碎片数
						if(awardEnm.getType() == 4) { // 稀有
							type = 1;
							materialType = 4;
							maxNum = 3;
						}
						num = new Random().nextInt(maxNum) + 1; // 随机的娃娃碎片数
						dataMap = getAwardInfoMap(materialType, num);
						break;
					}
				}
				LOG.info("signin:dataMap-" + JSON.toJSONString(dataMap));
				if(dataMap != null) {
					awardInfo = new SigninAwardInfo((Long)dataMap.get("id"), (Integer)dataMap.get("type"), num, 
							(String)dataMap.get("content"), (String)dataMap.get("url"), (String)dataMap.get("succDesc"));
				}
				if(awardInfo != null) {
					String succRemark = "";
					if((AppUtils.isTestEnv() && uid.longValue() == 1600150) 
							|| userSigninDao.saveUserSiginin(uid, dateCode, "签到获得" + awardInfo.getNum() + AwardTypeEnm.getDesc(awardInfo.getType()), times+1, awardInfo.getId()) > 0) {
						num = awardInfo.getNum();
						String[] numStr = new String(num+"").split("\\.");
						if(awardInfo.getType() == AwardTypeEnm.REDPACKET.type) { // 红包
							succRemark = "红包可提现哟！";
							redpacketService.saveOrUpdateUserRedpacket(uid, num, "每日签到获得" + num + "元", RedpacketEnm.SIGNIN.type, 0);
							UserRedpacket userRedpacket = redpacketService.getUserRedpacket(uid);
							String textString = "恭喜小主获得" + num +"元红包，合计可提现金额为" + userRedpacket.getAmount() + "元。";
							MsgNotice msg=new MsgNotice(uid, MsgType.NOTICE_SYS.type, textString);
							msg.setJumpWithdraw();
							msg.setPushMsg(textString);
							msgService.sendMsg(msg);
						} else if(awardInfo.getType() == AwardTypeEnm.COIN.type) { // 游戏币
							String coinNumStr = num + "";
							if(numStr != null && numStr.length == 2 && numStr[1].equals("0")) {
								coinNumStr = numStr[0];
							}
							TradeRecord tradeRecord = new TradeRecord(uid, uid,
									TradeType.SIGNIN_GET_COIN.type, 0, new Double(num).intValue(),
									TradeCostType.COST_COIN.type, "签到获得" + coinNumStr + "币");
							tradeService.charge(tradeRecord);
						} else if(awardInfo.getType() == AwardTypeEnm.COMMDEBRIS.type
								|| awardInfo.getType() == AwardTypeEnm.RAREDEBRIS.type) { // 娃娃碎片
							succRemark = "集齐碎片可到兑换商城合成娃娃哟！";
							userDollService.saveOrUpdateUserDollDebris(uid, type, num, "签到获取娃娃碎片*" + num);
							String debrisNumStr = num + "";
							if(numStr != null && numStr.length == 2 && numStr[1].equals("0")) {
								debrisNumStr = numStr[0];
							}
							String textString = "每日签到，小主抽到了" + debrisNumStr + "块" + AwardTypeEnm.getDesc(awardInfo.getType()) + "，集齐碎片可合成娃娃哟~~";
							MsgNotice msg=new MsgNotice(uid, MsgType.NOTICE_SYS.type, textString);
							UserBase userBase = userDAO.getUserBase(uid);
							msg.setJumpWeb(UserSigninContants.SHOP_URL + "?uid=" + uid + "&loginKey=" + userBase.getLoginKey());
							msg.setPushMsg(textString);
							msg.setTargetTitle("兑换商城");
							msg.setTargetName("兑换商城");
							msgService.sendMsg(msg);
						}
						if(version >= 120) {
							dailytaskService.saveOrUpdateUserTask(uid, TaskType.SIGNIN.taskId, 1);
						}
						awardInfo.setSuccRemark(succRemark);
					} else {
						return new BaseRespVO(-1, false, "今日已签到~");
					}
				}
			} else {
				return new BaseRespVO(-1, false, "今日已签到~");
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统错误，请重试~");
		}
		respVO.setAwardInfo(awardInfo);
		LOG.info("signin:awardInfo-" + JSON.toJSONString(awardInfo));
		return respVO;
	}
	
	@Override
	public boolean isSignin(Long uid) {
		int dateCode = Integer.parseInt(DateUtils.getCurrentDateString("yyyyMMdd")); 
		UserSignin userSignin = userSigninDao.getUserSigninByDateCode(uid, dateCode);
		if(userSignin != null) {
			return true;
		}
		return false;
	}
	
	private static Map<String, Object> getAwardInfoMap(int type, double num) {
		Map<String, Object> dataMap = null;
		LOG.info("getAwardId:type-" + type +",num-" + num + ",signinAwardList-" + JSON.toJSONString(signinAwardList));
		for(SigninAwardInfo awardInfo : signinAwardList) {
			if(awardInfo.getType() == type && awardInfo.getNum() == num) {
				dataMap = new HashMap<String, Object>();
				dataMap.put("id", awardInfo.getId());
				dataMap.put("type", type);
				dataMap.put("content", awardInfo.getContent());
				dataMap.put("url", awardInfo.getUrl());
				dataMap.put("succDesc", awardInfo.getSuccDesc());
				return dataMap;
			}
		}
		if(dataMap == null) {
			for(SigninAwardInfo awardInfo : signinAwardList) {
				if(awardInfo.getType() == type && awardInfo.getNum() == 0) {
					String[] numStr = new String(num+"").split("\\.");
					dataMap = new HashMap<String, Object>();
					AwardContentEnm contentEnm = AwardContentEnm.values()[type-1];
					String content = contentEnm.getContent();
					String succDesc = contentEnm.getSuccDesc();
					dataMap.put("id", awardInfo.getId());
					dataMap.put("type", type);
					dataMap.put("url", awardInfo.getUrl());
					if(numStr != null && numStr.length == 2 && numStr[1].equals("0")) {
						dataMap.put("content", content.replace("?", numStr[0]));
						dataMap.put("succDesc", succDesc.replace("?", numStr[0]));
					} else {
						dataMap.put("content", content.replace("?", num+""));
						dataMap.put("succDesc", succDesc.replace("?", num+""));
					}
					return dataMap;
				}
			}
		}
		return null;
	}

	@PostConstruct
	public void initAwardList() {
		List<SigninAward> awards = userSigninDao.getSigninAwards(); // 奖品列表
		if(!StringUtil.isNullOrEmpty(awards)) {
			for(SigninAward award : awards) {
				SigninAwardInfo awardInfo = new SigninAwardInfo(award.getId(), award.getType(), award.getNum(), 
						award.getContent(), award.getUrl(), award.getSuccDesc());
				signinAwardList.add(awardInfo);
			}
		}
	}
}
