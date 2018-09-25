package com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.zjdoll.app.modules.core.doll.utils.AliasMethod;

public class TwistedEggUtils {
	
	private static String FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 活动开始时间 **/
	public static final String EVENT_STARTDATE = "2018-03-09 00:00:00";
	/** 活动结束时间 **/
	public static final String EVENT_ENDDATE = "2018-03-31 23:59:59";
	
	/**
	 * 活动状态
	 * @return
	 */
	public static int eventStatus() {
		Date eventStartDate = DateUtils.string2Date(EVENT_STARTDATE, FORMAT);
		Date eventEndDate = DateUtils.string2Date(EVENT_ENDDATE, FORMAT);
		long currentMill = new Date().getTime();
		if(eventStartDate.getTime() > currentMill) {
			return 0;
		}
		if(eventStartDate.getTime() <= currentMill && eventEndDate.getTime() >= currentMill) {
			return 1;
		}
		return 2;
	}

	/**
	 * 抽奖
	 * @param type
	 * @return
	 */
	public static int getAwardType(int type) {
		List<Double> chanceList = new ArrayList<Double>();
		if(type == 4) {
			chanceList.add(0.30); // 25币
			chanceList.add(0.30); // 29币
			chanceList.add(0.15); // 33币
			chanceList.add(0.10); // 49币
			chanceList.add(0.10); // 福袋
			chanceList.add(0.05); // 大奖品
		} else if(type == 3) {
			chanceList.add(0.35); // 25币
			chanceList.add(0.35); // 29币
			chanceList.add(0.15); // 33币
			chanceList.add(0.10); // 49币
			chanceList.add(0.0); // 福袋
			chanceList.add(0.05); // 大奖品
		} else if(type == 2) {
			chanceList.add(0.35); // 25币
			chanceList.add(0.35); // 29币
			chanceList.add(0.20); // 33币
			chanceList.add(0.05); // 49币
			chanceList.add(0.05); // 福袋
		} else if(type == 1) {
			chanceList.add(0.35); // 25币
			chanceList.add(0.35); // 29币
			chanceList.add(0.25); // 33币
			chanceList.add(0.05); // 49币
		}
		
		AliasMethod aliasMethod = new AliasMethod(chanceList);
		return aliasMethod.next() + 1;
	}
}
