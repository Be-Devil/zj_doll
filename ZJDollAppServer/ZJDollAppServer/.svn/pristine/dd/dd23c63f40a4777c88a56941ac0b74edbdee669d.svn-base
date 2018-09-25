package com.imlianai.zjdoll.app.modules.core.trade.catalog.utils;

import java.util.Date;

import com.imlianai.rpc.support.utils.DateUtils;

public class CatalogUtils {
	
	private static String FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 充值活动开始时间 **/
	public static final String RECHARGE_EVENT_STARTDATE = "2018-01-13 00:00:00";
	/** 充值活动结束时间 **/
	public static final String RECHARGE_EVENT_ENDDATE = "2018-01-19 23:59:59";
	
	public static final int code20=2043;
	
	public static final int code50=2042;
	/**
	 * 是否在充值活动期间
	 * @return
	 */
	public boolean isRechargeEventTime() {
		Date eventStartDate = DateUtils.string2Date(RECHARGE_EVENT_STARTDATE, FORMAT);
		Date eventEndDate = DateUtils.string2Date(RECHARGE_EVENT_ENDDATE, FORMAT);
		long currentMill = new Date().getTime();
		if(eventStartDate.getTime() <= currentMill && eventEndDate.getTime() >= currentMill) {
			return true;
		}
		return false;
	}
}
