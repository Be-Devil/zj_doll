package com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.utils;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.imlianai.rpc.support.utils.DateUtils;

public class NewyearRechargeUtils {

	private static String FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 充值活动开始时间 **/
	public static final String RECHARGE_EVENT_STARTDATE = "2018-04-21 00:00:00";
	/** 充值活动结束时间 **/
	public static final String RECHARGE_EVENT_ENDDATE = "2018-05-07 23:59:59";
	
	/**
	 * 充值活动状态
	 * @return
	 */
	public static int eventStatus() {
		Date eventStartDate = DateUtils.string2Date(RECHARGE_EVENT_STARTDATE, FORMAT);
		Date eventEndDate = DateUtils.string2Date(RECHARGE_EVENT_ENDDATE, FORMAT);
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
	 * 获取剩余秒数
	 * @return
	 */
	public static long getResidueTime() {
		long currentMi = new Date().getTime();
		long endMi = getMillis(RECHARGE_EVENT_ENDDATE);
		return (endMi-currentMi)/1000;
	}
	
	public static long getMillis(String dateStr) {
		if(StringUtils.isBlank(dateStr)) {
			return 0;
		}
		return DateUtils.string2Date(dateStr, FORMAT).getTime();
	}
}
