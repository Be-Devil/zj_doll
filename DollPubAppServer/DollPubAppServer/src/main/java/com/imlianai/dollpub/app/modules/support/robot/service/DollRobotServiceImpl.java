package com.imlianai.dollpub.app.modules.support.robot.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.core.trade.catalog.service.ChargeCatalogService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.support.robot.dao.DollRobotDao;
import com.imlianai.dollpub.app.modules.support.robot.domain.DollShopRobot;
import com.imlianai.dollpub.domain.trade.ChargeCatalog;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.sun.tools.javac.code.Printer;

@Service
public class DollRobotServiceImpl implements DollRobotService {

	private static final BaseLogger logger = BaseLogger
			.getLogger(DollRobotServiceImpl.class);

	@Resource
	private DollRobotDao dollRobotDao;

	@Resource
	private UserService userService;
	
	@Resource
	private ChargeCatalogService chargeCatalogService;

	private LinkedList<Long> robots = null;

	@Override
	public void initRobotUser() {
		List<UserGeneral> list = dollRobotDao.getInitUserData();
		if (!StringUtil.isNullOrEmpty(list)) {
			for (UserGeneral userGeneral : list) {
				if (userGeneral != null) {
					UserBase userBase = new UserBase(13800138000L, "wwrobot");
					UserGeneral user = new UserGeneral(userGeneral.getName(),
							userGeneral.getHead(), userGeneral.getGender(), 18);
					userBase.setCustomerId(84);
					userBase.setAgentId(1l);
					user.setCustomerId(84);
					user.setAgentId(1l);
					long uid = userService.initUser(userBase, user);
					logger.info("uid=>" + uid);
					dollRobotDao.addRobotPool(uid, userGeneral.getGender());
				}
			}
		}
	}

	private void initRobotUidPoll() {
		List<Long> list = dollRobotDao.getRobotPool();
		if (!StringUtil.isNullOrEmpty(list)) {
			Collections.shuffle(list);
			robots = new LinkedList<>(list);
		}
	}

	@Override
	public Long getRandomRobotUid() {
		if (robots == null) {
			initRobotUidPoll();
		}
		Long uid = robots.poll();
		if (uid == null) {
			initRobotUidPoll();
			uid = robots.poll();
		}
		if (uid != null) {
			robots.add(uid);
		}
		return uid;

	}

	@Override
	public int initShopkeeperUid() {
		if (StringUtil.isNullOrEmpty(getShopkeeperUid())) {
			List<Long> robotUids = new ArrayList<Long>();
			int breakCount = 0;
			// TODO 每日收入补机器人逻辑
			while (robotUids.size() < 20 && breakCount < 100) {
				Long uid = getRandomRobotUid();
				if (uid != null) {
					if (!robotUids.contains(uid)) {
						robotUids.add(uid);
					}
				}
				breakCount++;
			}
			Map<Integer, Integer> targetMap = new HashMap<Integer, Integer>();
			targetMap.put(30, 4);
			targetMap.put(100, 6);
			targetMap.put(200, 7);
			targetMap.put(300, 3);
			List<Integer> numList = new ArrayList<Integer>();
			for (Integer num : targetMap.keySet()) {
				Integer target = targetMap.get(num);
				if (target != null) {
					for (int i = 0; i < target; i++) {
						numList.add(num);
					}
				}
			}
			if (!StringUtil.isNullOrEmpty(robotUids)) {
				for (int i = 0; i < robotUids.size(); i++) {
					Integer targert = numList.size() > 0 ? numList.get(0) : 100;
					if (numList.size() > i) {
						targert = numList.get(i);
					}
					targert = targert
							- new Random().nextInt((int) (targert / 3));
					DollShopRobot robot = new DollShopRobot(robotUids.get(i),
							targert);
					dollRobotDao.addShopkeeperUid(robot);
				}
			}
		}
		return 0;
	}

	@Override
	public void handleShopRobot(){
		List<DollShopRobot> list=getShopkeeperUid();
		if (StringUtil.isNullOrEmpty(list)) {
			initShopkeeperUid();
			list=getShopkeeperUid();
		}
		int hour=DateUtils.hourBetween(DateUtils.getCurrentDateString()+" 00:00:00")+1;
		if (!StringUtil.isNullOrEmpty(list)) {
			for (DollShopRobot dollShopRobot : list) {
				try {
					int hourTotal=dollShopRobot.getDailyLimit()*100*hour/24;
					if(dollShopRobot.getAmt()<hourTotal){
						if (new Random().nextInt(10)>3) {
							continue;
						}
						//满足条件应该充值
						List<ChargeCatalog> catalogs=chargeCatalogService.getCatalogs(0, 0, 84);
						if (!StringUtil.isNullOrEmpty(catalogs)) {
							ChargeCatalog catalog=catalogs.get(new Random().nextInt(catalogs.size()-1));
							int r=new Random().nextInt(3);
							if (r==0) {
								r=15;
							}else if (r==1) {
								r=5;
							}else {
								r=10;
							}
							dollRobotDao.addDailyAmt(dollShopRobot.getUid(),(catalog.getUnit()==0?catalog.getPrice()*100:catalog.getPrice())*r/100);
						}
					}
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}
	}
	@Override
	public List<DollShopRobot> getShopkeeperUid() {
		return dollRobotDao.getShopkeeperUid();
	}

}
