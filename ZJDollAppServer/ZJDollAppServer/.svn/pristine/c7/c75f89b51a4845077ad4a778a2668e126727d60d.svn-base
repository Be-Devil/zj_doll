package com.imlianai.zjdoll.app.modules.core.doll.robot.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.msg.MsgRoom;
import com.imlianai.zjdoll.domain.msg.MsgRoomType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.rpc.support.utils.SysTimerHandle;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.core.doll.robot.dao.DollRobotDao;
import com.imlianai.zjdoll.app.modules.core.doll.robot.domain.BusRobotStat;
import com.imlianai.zjdoll.app.modules.core.doll.service.DollService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;

@Service
public class DollRobotServiceImpl implements DollRobotService {
	private static final BaseLogger logger = BaseLogger
			.getLogger(DollRobotServiceImpl.class);

	@Resource
	DollRobotDao dollRobotDao;

	@Resource
	UserService userService;
	@Resource
	DollBusService dollBusService;
	@Resource
	DollService dollService;

	@Resource
	DollRecordService dollRecordService;
	LinkedList<Long> robots = null;
	@Resource
	MsgService msgService;
	private static final int maxWatchNum = 70;
	private static final int maxWatchNumFake = 300;
	private static final int minRobotNum = 45;

	@Override
	public void addRobot(final DollBus bus, final int num) {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i < num; i++) {
					handleRobotEnter(bus, true);
					try {
						Thread.sleep(2500);
					} catch (InterruptedException e) {
					}
				}
				/*try {
					if (bus.getWatchNum() <= maxWatchNum) {
						boolean onlyMsg=true;
						if(dollRobotDao.getRobotCount(bus.getBusId())<minRobotNum){
							onlyMsg=false;
						}else {
							//dollBusService.updateBusWatchCount(bus.getBusId(), num);
							//dollRobotDao.incFakeRobotNum(bus.getBusId(), num);
						}
						for (int i = 0; i < num; i++) {
							handleRobotEnter(bus, onlyMsg);
							Thread.sleep(2500);
						}
					} else {// 仅发消息
						if (bus.getWatchNum()<maxWatchNumFake) {
							//dollBusService.updateBusWatchCount(bus.getBusId(), num);
							//dollRobotDao.incFakeRobotNum(bus.getBusId(), num);
						}
						for (int i = 0; i < num; i++) {
							handleRobotEnter(bus, true);
							Thread.sleep(2500);
						}
					}
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}*/
			}
		}, 1);
	}

	private void handleRobotEnter(DollBus bus, boolean onlyMsg) {
		Long uid = getRandomRobot();
		if (uid != null) {
			UserGeneral user = userService.getUserGeneral(uid);
			MsgRoom msgRoom = new MsgRoom(bus, user,
					MsgRoomType.ENTER_ROOM.type, user.getName() + " 来了");
			msgService.sendMsgRoom(msgRoom);
			if (!onlyMsg) {
				//dollRecordService.addWatchRecord(uid, bus.getBusId());
				//dollRobotDao.addRobotBusRecord(uid, bus.getBusId());
				//dollBusService.updateBusWatchCount(bus.getBusId(), 1);
			}
		}
	}
	
	@Override
	public void handleRobotCatchResultMsg(final DollBus bus,final boolean success){
		if (bus!=null) {
			if (success) {
				if (new Random().nextInt(100)<50) {
					return ;
				}
			}else{
				if (new Random().nextInt(100)<70) {
					return ;
				}
			}
			int ran=new Random().nextInt(7)+3;
			SysTimerHandle.execute(new TimerTask() {
				@Override
				public void run() {
					try {
						logger.info("handleRobotCatchResultMsg now:" + DateUtils.getNowTime());
						handleRobotActionMsg(bus, success);
					} catch (Exception e) {
						PrintException.printException(logger, e);
					}
				}
			}, ran);
		}
	}
	private static String[] successMsg={"厉害！","等下让我试一下","我也来抓一下","中了，不错啊","这么好抓吗","你抓了好多了","牛啊，中了","中了几个了","我靠，抓到了呀","我刚抓了几次都没中，你怎么一下就中了","送给我吧","我刚都充了20多块了，中了2个","送点币我抓一次，这么好中","今天这么好抓吗","运气这么好","你运气好好啊！","捡漏的吧","甩起来啊，中了","钩子这么好","加油啊，再抓一个送我把","好手法，抓旁边的","周卡比较划算，送50%的","刚充了月卡，反正都要抓，送的多啊","你转我点钱，我去充个周卡，20就好","我想去兑换商城去换那个hello kitty的相机","刚提现了10块钱，我微信转你","你今天烧香了么，这么好中","帮我抓一个吧","你充了多少抓中的","刚在用碎片去兑换商城换了1个娃娃","我咋抓不中，你一下就中了","送我点币，我也要抓个","明天再充钱抓吧","我觉得比其他的抓娃娃好抓多了","发现这个比线下的好抓一些啊","这么厉害","抓到的这个送我吧","我想充周卡","充月卡更划算吧","捡个漏","你这妥妥的捡漏","气人，我抓3次没中，你中了","充个6块钱玩玩","今天冲了50多了，去商城换了几个大娃娃","老天开眼了，哈哈","哈哈哈，中了","今天才中了2个","我都花了200多了","一起冲了150大洋，哈哈哈","填我邀请码呀","兑换商城里面的不错","我也想换兑换商城的娃娃","你充周卡才28，都420币了","厉害了","恭喜中了，哈哈","牛逼了","不错，中了一个啊","教我怎么抓吧","我朋友都抓了10好几个了","大神来了","亲密付帮我充点吧"};
	private static String[] failMsg={"加油哦","继续呀","没中，呃呃","这么菜，让我来","邀请用户得20币，填我的吧","填我的邀请码","这音乐不错","关门，放娃娃","送女朋友的，邮寄给他啊","明天再去吧，刚抓了几次没中","技术不行呀","加油啊啊啊","！！！这都没中","神操作，哈哈","我帮你抓得了","让我来一次吧","可惜啊","美女都抓不中么","女神好啊","明天再来抓吧","睡一会去了","充钱再来","没充多少，就26","你还不如去充周卡呢","邀请码我的啊","不知道呢","我班上好几个都喜欢抓","家里都十几个娃娃了，还要抓","基本天天抓吧","差一点了","就差一丢丢啊","毛都没抓到","兑换商城去换一个啊","碎片多就去兑换商城换啊","我都40多现金了，马上提现","不是10块钱就可以提现吗","继续抓啊","再抓就中了","快强力了吧","点了吗","没币了","废了，哈哈","签到去得币吧","刚签到得了50币","是微信邀请才得到20元的","扯淡，不中啊","在不中就去玩吧","毛呢","可以送币的吗","要捡漏才行","不中啊","呃呃呃","充月卡再来吧，反正送币翻倍","榜单也送啊","让你妹妹抓一次","刚中了1个","一直抓就中的机会大啊","充了6块","刚充了个9块钱，送10币啊","20块块比较划算吧","首充也有优惠啊，6块的"};
	public void handleRobotActionMsg(DollBus bus, boolean success){
		Long uid = getRandomRobot();
		if (uid != null) {
			String msgText=null;
			if (success) {
				int succRand=new Random().nextInt(successMsg.length-1);
				msgText=successMsg[succRand];
			}else{
				int failRand=new Random().nextInt(failMsg.length-1);
				msgText=failMsg[failRand];
			}
			if (!StringUtil.isNullOrEmpty(msgText)) {
				UserGeneral user = userService.getUserGeneral(uid);
				msgText="<font color='#ffe003'>"+user.getName()+": </font><font color='#ffffff'>"+ msgText+"</font>";
				MsgRoom msgRoom = new MsgRoom(bus, user,
						MsgRoomType.NORMAL_MSG.type, msgText);
				msgRoom.setSave(true);
				msgService.sendMsgRoom(msgRoom);
			}
		}
	}

	private void handleRobotLeft(int busId) {
		BusRobotStat stat = dollRobotDao.getBusRobotStat(busId);
		int num=new Random().nextInt(4);
		/*if (stat.getFakeNum()<num) {
			int nowRob=dollRobotDao.getRobotCount(busId);
			DollBus bus=dollBusService.getDollBus(busId);
			if (nowRob>num&&bus.getWatchNum()>20) {
				List<Long> list=dollRobotDao.getRobotBusRecord(busId, num);
				dollRobotDao.removeRobotBusRecord(busId, num);
				if (!StringUtil.isNullOrEmpty(list)) {
					for (Long uid : list) {
						dollService.leaveBus(uid, busId,true);
					}
				}
			}
		}else {
			dollRobotDao.decFakeRobotNum(busId, num);
			dollBusService.updateBusWatchCount(busId, -num);
		}*/
	}

	@Override
	public void removeRobot(final int busId) {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					handleRobotLeft(busId);
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}

	@Override
	public void initRobotUser() {
		List<UserGeneral> list = dollRobotDao.getInitUserData();
		if (!StringUtil.isNullOrEmpty(list)) {
			for (UserGeneral userGeneral : list) {
				if (userGeneral != null) {
					UserBase userBase = new UserBase(13800138000l, "wwrobot");
					UserGeneral user = new UserGeneral(userGeneral.getName(),
							userGeneral.getHead(), userGeneral.getGender(), 18);
					long uid = userService.initUser(userBase, user);
					dollRobotDao.addRobotPool(uid, userGeneral.getGender());
				}
			}
		}
	}

	private void initRobotPoll() {
		List<Long> list = dollRobotDao.getRobotPool();
		if (!StringUtil.isNullOrEmpty(list)) {
			Collections.shuffle(list);
			robots = new LinkedList<Long>();
			for (Long uid : list) {
				robots.add(uid);
			}
		}
	}

	@Override
	public Long getRandomRobot() {
		if (robots == null) {
			initRobotPoll();
		}
		Long uid = robots.poll();
		if (uid == null) {
			initRobotPoll();
			uid = robots.poll();
		}
		if (uid != null) {
			robots.add(uid);
		}
		return uid;
	}

}
