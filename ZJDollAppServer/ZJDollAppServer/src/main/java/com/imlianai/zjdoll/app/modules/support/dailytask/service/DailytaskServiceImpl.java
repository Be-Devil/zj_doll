package com.imlianai.zjdoll.app.modules.support.dailytask.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.dailytask.DailytaskTasks;
import com.imlianai.zjdoll.domain.dailytask.DailytaskUserActiveness;
import com.imlianai.zjdoll.domain.dailytask.DailytaskUserBox;
import com.imlianai.zjdoll.domain.dailytask.DailytaskUserTask;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.zjdoll.domain.invite.InviteRelation;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacket;
import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.publics.utils.ArithmeticUtils;
import com.imlianai.zjdoll.app.modules.support.dailytask.dao.DailytaskDao;
import com.imlianai.zjdoll.app.modules.support.dailytask.enm.BoxRewardType;
import com.imlianai.zjdoll.app.modules.support.dailytask.enm.BoxType;
import com.imlianai.zjdoll.app.modules.support.dailytask.enm.TaskType;
import com.imlianai.zjdoll.app.modules.support.dailytask.utils.DailytaskUtils;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.AwardInfo;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.BoxInfo;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.DailytaskInfoRespVO;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.HandleInviteReqVO;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.InviteInfo;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.OpenBoxRespVO;
import com.imlianai.zjdoll.app.modules.support.dailytask.vo.TaskInfo;
import com.imlianai.zjdoll.app.modules.support.invite.service.InviteService;
import com.imlianai.zjdoll.app.modules.support.redpacket.enm.RedpacketEnm;
import com.imlianai.zjdoll.app.modules.support.redpacket.service.RedpacketService;
import com.imlianai.zjdoll.app.modules.support.version.service.VersionService;

@Service
public class DailytaskServiceImpl implements DailytaskService {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(DailytaskServiceImpl.class);
	
	@Resource
	DailytaskDao dailytaskDao;
	@Resource
	RedpacketService redpacketService;
	@Resource
	TradeService tradeService;
	@Resource
	UserService userService;
	@Resource
	MsgService msgService;
	@Resource
	InviteService inviteService;
	@Resource
	VersionService versionService;
	
	List<BoxInfo> boxInfoList = new ArrayList<BoxInfo>(); // 宝箱信息列表
	
	List<TaskInfo> taskInfoList = new ArrayList<TaskInfo>(); // 任务信息列表
	
	Map<Long, DailytaskTasks> taskMap = new HashMap<Long, DailytaskTasks>(); // key-taskId

	
	@Override
	public BaseRespVO getActiveness(Long uid, long taskId) {
		try {
			int dateCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
			DailytaskUserTask userTask = dailytaskDao.getUserTask(uid, dateCode, taskId);
			if(userTask == null) {
				return new BaseRespVO(-1 ,false, "未完成任务，不可领取~");
			}
			if(userTask.getStatus() == 2) {
				return new BaseRespVO(-1 ,false, "不可重复领取~");
			}
			if(dailytaskDao.updateUserTaskStatus(dateCode, uid, taskId, 2) > 0) {
				DailytaskTasks taskInfo = taskMap.get(taskId);
				if(taskInfo != null) {
					if(dailytaskDao.saveOrUpdateUserActiveness(uid, dateCode, taskInfo.getAwardValue()) > 0) {
						dailytaskDao.saveUserActivenessRecord(uid, taskInfo.getAwardValue(), taskInfo.getTaskDesc() + "获取活跃度+" + taskInfo.getAwardValue());
						DailytaskUserActiveness userActiveness = dailytaskDao.getUserActiveness(uid, dateCode);
						int newValue = (userActiveness == null ? 0 : userActiveness.getValue());
						List<Integer> valueList = new ArrayList<Integer>();
						for(BoxType boxType : BoxType.values()) {
							valueList.add(boxType.getActiveness());
						}
						Collections.reverse(valueList);
						int maxBoxId = 0;
						for(Integer value : valueList) {
							if(newValue >= value) {
								BoxType boxType = BoxType.getBoxTypeByActiveness(value);
								if(boxType != null) {
									maxBoxId = boxType.getBoxId();
									break;
								}
							}
						}
						if(maxBoxId > 0) {
							for(int i = 1; i <= maxBoxId; i++) {
								dailytaskDao.saveOrUpdateUserBox(uid, dateCode, i);
							}
						}
					}
					return new BaseRespVO(0 ,true, taskInfo.getActivenessDesc());
				} else {
					return new BaseRespVO(-1 ,false, "任务不存在");
				}
			} else {
				return new BaseRespVO(-1 ,false, "网络异常，请重试~");
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1 ,false, "系统异常，请重试~");
		}
	}
	
	@Override
	public BaseRespVO openBox(Long uid, int boxId) {
		try {
			int dateCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
			DailytaskUserBox userBox = dailytaskDao.getUserBox(uid, dateCode, boxId);
			if(userBox == null) {
				return new BaseRespVO(-1 ,false, "宝箱不存在~");
			}
			if(userBox.getStatus() == 2) {
				return new BaseRespVO(-1 ,false, "不可重复开启宝箱~");
			}
			if(dailytaskDao.updateUserBoxStatus(uid, dateCode, boxId) > 0) {
				int award_coin = 0; // 奖励的金币
				double award_amt = 0; // 奖励的红包金额
				if(boxId == BoxType.FIRST_BOX.boxId) {
					//award_coin = new Random().nextInt(3) + 1; // 1-3币
					award_coin = 3; // 3币
				} else if(boxId == BoxType.SECOND_BOX.boxId) {
					award_coin = new Random().nextInt(5) + 4; // 4-8币
				} else if(boxId == BoxType.THIRTD_BOX.boxId) {
					award_coin = new Random().nextInt(11) + 25; // 25-35币
				} else if(boxId == BoxType.FOURTH_BOX.boxId) {
					award_coin = 100;
					if(DailytaskUtils.getWin() == 1) {
						award_amt = ArithmeticUtils.add(new Random().nextInt(81) * 0.01, 1.2, 2); // 1.20-2.00元
					}
				} else if(boxId == BoxType.FIFTH_BOX.boxId) {
					award_coin = 300;
					award_amt = ArithmeticUtils.add(new Random().nextInt(101) * 0.01, 2, 2); // 2.00-3.00元
				}
				StringBuilder awardSb = new StringBuilder();
				if(award_coin > 0) {
					TradeRecord tradeRecord = new TradeRecord(uid, uid,
							TradeType.DAILYTASK_GET_COIN.type, 0, award_coin,
							TradeCostType.COST_COIN.type, "每日任务开启宝箱获得" + award_coin + "币");
					tradeService.charge(tradeRecord);
					awardSb.append("恭喜获得了" + award_coin + "币");
				}
				if(award_amt > 0) {
					redpacketService.saveOrUpdateUserRedpacket(uid, award_amt, "每日任务开启宝箱获得" + award_amt + "元", RedpacketEnm.DAILYTASK.type, 0);
					awardSb.append("、" + award_amt + "元红包");
				}
				if(dailytaskDao.getUserBoxCountByParams(uid, dateCode, 2) == 1) {
					String textString = "恭喜小主打开了第1个宝箱，今日首充任意金额可再获5~25点活跃度哟~~";
					MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type, textString);
					msg.setJumpDailytask();
					msg.setPushMsg(textString);
					msgService.sendMsg(msg);
				}
				OpenBoxRespVO respVO = new OpenBoxRespVO();
				respVO.setAwardDesc(awardSb.toString());
				TradeAccount account = tradeService.getAccount(uid);
				respVO.setCoin(account == null ? 0 : account.getCoin());
				UserRedpacket userRedpacket = redpacketService.getUserRedpacket(uid);
				respVO.setRedpacketAmt(userRedpacket == null ? 0 : userRedpacket.getAmount());
				return respVO;
			} else {
				return new BaseRespVO(-1 ,false, "网络异常，请重试~");
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1 ,false, "系统异常，请重试~");
		}
	}

	@Override
	public BaseRespVO getDailytaskInfo(BaseReqVO reqVO) {
		Long uid = reqVO.getUid();
		DailytaskInfoRespVO respVO = new DailytaskInfoRespVO();
		int dateCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
		// 用户活跃度
		DailytaskUserActiveness userActiveness = dailytaskDao.getUserActiveness(uid, dateCode);
		respVO.setCurrActiveness(userActiveness == null ? 0 : userActiveness.getValue());
		// 宝箱信息列表
		List<BoxInfo> userBoxInfoList = boxInfoList;
		if(!StringUtil.isNullOrEmpty(userBoxInfoList)) {
			for(BoxInfo boxInfo : userBoxInfoList) {
				int boxId = boxInfo.getBoxId();
				DailytaskUserBox userBox = dailytaskDao.getUserBox(uid, dateCode, boxId);
				boxInfo.setStatus(userBox == null ? 0 : userBox.getStatus());
			}
		}
		respVO.setBoxInfoList(userBoxInfoList);
		boolean isAudit = versionService.isAudit(reqVO.getOsType(), reqVO.getChannel(), reqVO.getVersion()); // 判断是否在审核中
		// 任务信息列表
		List<TaskInfo> notGetList = new ArrayList<TaskInfo>(); // 未领取的任务列表
		List<TaskInfo> notCompleteList = new ArrayList<TaskInfo>(); // 未完成的任务列表
		List<TaskInfo> getList = new ArrayList<TaskInfo>(); // 已领取的任务列表
		List<TaskInfo> resultList = new ArrayList<TaskInfo>(); // 返回的任务列表 
		List<TaskInfo> userTaskInfoList = taskInfoList;
		if(!StringUtil.isNullOrEmpty(userTaskInfoList)) {
			for(TaskInfo taskInfo : userTaskInfoList) {
				DailytaskUserTask userTask = dailytaskDao.getUserTask(uid, dateCode, taskInfo.getTaskId());
				taskInfo.setStatus(userTask == null ? 0 : userTask.getStatus());
				taskInfo.setCurrNum(userTask == null ? 0 : userTask.getCurrNum());
				if(taskInfo.getStatus() == 0) {
					if(!(isAudit && (taskInfo.getTaskId() == TaskType.WECHATGROUP.taskId || taskInfo.getTaskId() == TaskType.MOMENTS.taskId))) {
						notCompleteList.add(taskInfo);
					}
				} else if(taskInfo.getStatus() == 1) {
					if(!(isAudit && (taskInfo.getTaskId() == TaskType.WECHATGROUP.taskId || taskInfo.getTaskId() == TaskType.MOMENTS.taskId))) {
						notGetList.add(taskInfo);
					}
				} else {
					if(!(isAudit && (taskInfo.getTaskId() == TaskType.WECHATGROUP.taskId || taskInfo.getTaskId() == TaskType.MOMENTS.taskId))) {
						getList.add(taskInfo);
					}
				}
			}
		}
		if(!StringUtil.isNullOrEmpty(notGetList)) {
			resultList.addAll(notGetList);
		}
		if(!StringUtil.isNullOrEmpty(notCompleteList)) {
			resultList.addAll(notCompleteList);
		}
		if(!StringUtil.isNullOrEmpty(getList)) {
			resultList.addAll(getList);
		}
		respVO.setTaskInfoList(resultList);
		// 分享邀请信息
		InviteInfo inviteInfo = new InviteInfo();
		InviteRelation relation = inviteService.getInviteRelationByUid(uid);
		if (relation != null) {
			inviteInfo.setInvitedCode(relation.getCode());
		}
		UserGeneral userGeneral = userService.getUserGeneral(uid);
		if(userGeneral != null) {
			inviteInfo.setUhead(userGeneral.getHead());
			inviteInfo.setUname(userGeneral.getName());
		}
		respVO.setInviteInfo(inviteInfo);
		return respVO;
	}
	
	@Override
	public void saveOrUpdateUserTask(Long uid, long taskId, int num) {
		try {
			int dateCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
			DailytaskTasks task = taskMap.get(taskId);
			if(task != null) {
				DailytaskUserTask userTask = dailytaskDao.getUserTask(uid, dateCode, taskId);
				if(userTask == null || 
						(userTask.getStatus() == 0 && userTask.getCurrNum() < task.getMaxNum())) {
					int status = 0;
					int newNum = userTask == null ? num : userTask.getCurrNum()+num;
					if(newNum >= task.getMaxNum()) {
						status = 1;
						newNum = task.getMaxNum();
					}
					dailytaskDao.saveOrUpdateUserTask(uid, dateCode, taskId, status, newNum);
					if(status == 1) {
						String textString = "小主好棒棒惹，完成了任务【" + task.getTaskDesc() + "】，快去领取" + task.getAwardValue() + "点活跃度吧~~";
						MsgNotice msg = new MsgNotice(uid, MsgType.NOTICE_SYS.type, textString);
						msg.setJumpDailytask();
						msg.setPushMsg(textString);
						msgService.sendMsg(msg);
					}
				}
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}
	
	@Override
	public void handleCatchDoll(DollOptRecord record) {
		try {
			UserBase userBase = userService.getUserBase(record.getUid());
			if(userBase != null && userBase.getVersion() >= 120) {
				saveOrUpdateUserTask(record.getUid(), TaskType.CATCH_DOLL_1.taskId, 1);
				saveOrUpdateUserTask(record.getUid(), TaskType.CATCH_DOLL_6.taskId, 1);
				saveOrUpdateUserTask(record.getUid(), TaskType.CATCH_DOLL_30.taskId, 1);
				if(record.getResult() == 1) {
					handleSuccCatchDoll(record);
				}
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}
	
	@Override
	public void handleSuccCatchDoll(DollOptRecord record) {
		saveOrUpdateUserTask(record.getUid(), TaskType.CATCH_DOLL_SUCC_1.taskId, 1);
		saveOrUpdateUserTask(record.getUid(), TaskType.CATCH_DOLL_SUCC_3.taskId, 1);
		saveOrUpdateUserTask(record.getUid(), TaskType.CATCH_DOLL_SUCC_10.taskId, 1);
	}
	
	@Override
	public void handleRecharge(double cost, long uid) {
		try {
			UserBase userBase = userService.getUserBase(uid);
			if(userBase != null && userBase.getVersion() >= 120) {
				int num = new Double(cost).intValue();
				saveOrUpdateUserTask(uid, TaskType.RECHARGE_RANDOM_AMT.taskId, 1);
				saveOrUpdateUserTask(uid, TaskType.RECHARGE_AMT_10.taskId, num);
				saveOrUpdateUserTask(uid, TaskType.RECHARGE_AMT_30.taskId, num);
				saveOrUpdateUserTask(uid, TaskType.RECHARGE_AMT_50.taskId, num);
				saveOrUpdateUserTask(uid, TaskType.RECHARGE_AMT_100.taskId, num);
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
		}
	}
	
	@Override
	public int getCount(Long uid) {
		int dateCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
		return dailytaskDao.getUserTaskCount(uid, dateCode, 1);
	}
	
	@Override
	public BaseRespVO handleInvite(HandleInviteReqVO reqVO) {
		TaskType taskType = null;
		if(reqVO.getTarget() == 0) {
			taskType = TaskType.WECHATGROUP;
		} else if(reqVO.getTarget() == 1) {
			taskType = TaskType.MOMENTS;
		}
		if(taskType != null) {
			saveOrUpdateUserTask(reqVO.getUid(), taskType.getTaskId(), 1);
		}
		return new BaseRespVO();
	}
	
	@Override
	public List<DailytaskUserActiveness> getUserActivenessListByParams(int dateCode, int value) {
		return dailytaskDao.getUserActivenessListByParams(dateCode, value);
	}
	
	@PostConstruct
	private void initData() {
		Map<Integer, List<AwardInfo>> rewardMap = new HashMap<Integer, List<AwardInfo>>(); // key-boxId,value-奖品列表
		for(BoxRewardType rewardType : BoxRewardType.values()) {
			List<AwardInfo> awardList = rewardMap.get(rewardType.getBoxId());
			if(StringUtil.isNullOrEmpty(awardList)) {
				awardList = new ArrayList<AwardInfo>();
			}
			AwardInfo awardInfo = new AwardInfo(rewardType.getRewardType(), rewardType.getRewardDesc());
			awardList.add(awardInfo);
			rewardMap.put(rewardType.getBoxId(), awardList);
		}
		
		for(BoxType boxType : BoxType.values()) {
			BoxInfo boxInfo = new BoxInfo(boxType.getBoxId(), boxType.getActiveness(), boxType.getActivenessDesc());
			boxInfo.setAwardList(rewardMap.get(boxType.getBoxId()));
			boxInfoList.add(boxInfo);
		}
		
		List<DailytaskTasks> taskList = dailytaskDao.getDailytaskTasks();
		if(!StringUtil.isNullOrEmpty(taskList)) {
			for(DailytaskTasks task : taskList) {
				taskInfoList.add(new TaskInfo(task));
				taskMap.put(task.getTaskId(), task);
			}
		}
	}
}
