package com.imlianai.zjdoll.app.modules.support.event.lanternFestival20180227.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.imlianai.rpc.support.utils.DateUtils;
import com.ctrip.framework.apollo.util.PropertiesUtil;
import com.imlianai.rpc.support.utils.StringUtil;

public class LanternFestivalUtils {
	
	public static String fiveTimesDollIdsStr = PropertiesUtil.getString("application","lanFestivalEvent.fiveTimesDollIds"); // 5倍场直接返币的娃娃ID
	public static String eightTimesDollIdsStr = PropertiesUtil.getString("application","lanFestivalEvent.eightTimesDollIds"); // 8倍场直接返币的娃娃ID
	public static String reDollIdsStr = PropertiesUtil.getString("application","lanFestivalEvent.recycleDollIds"); // 直接回收成钻石的娃娃ID
	
	private static String FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 活动开始时间 **/
	public static final String EVENT_STARTDATE = "2018-03-02 00:00:00";
	/** 活动结束时间 **/
	public static final String EVENT_ENDDATE = "2018-03-05 23:59:59";
	
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
	 * 娃娃ID列表
	 * @param type 类型:0-兑换成游戏币 1-回收成钻石
	 * @param times 倍场
	 * @return
	 */
	public static List<Integer> getDollIds(int type, int times) {
		String dollIdsStr = "";
		if(type == 0) {
			if(times == 5) {
				dollIdsStr = fiveTimesDollIdsStr;
			} else if(times == 8) {
				dollIdsStr = eightTimesDollIdsStr;
			}
		} else if(type == 1) {
			dollIdsStr = reDollIdsStr;
		}
		List<Integer> dollIds = new ArrayList<Integer>();
		if(!StringUtil.isNullOrEmpty(dollIdsStr)) {
			String[] dollIdsArr = dollIdsStr.split(",");
			if(dollIdsArr != null && dollIdsArr.length > 0) {
				for(String dollId : dollIdsArr) {
					if(StringUtil.isNumber(dollId)) {
						dollIds.add(new Integer(dollId));
					}
				}
			}
		}
		return dollIds;
	}
}
