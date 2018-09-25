package com.imlianai.zjdoll.app.modules.support.ranking.utils;

import java.util.Calendar;
import java.util.Date;

import com.imlianai.rpc.support.utils.DateUtils;

public class RankChargeUtil {

	public static Date getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}

	public static void main(String[] args) {
		Date date=(new Date());
		date.setMonth(2);
		System.out.println(date);
		System.out.println(DateUtils.getWeekFirstTime(date));
		System.out.println(DateUtils.getWeekFirstTime());
		String dateString=DateUtils.dateToString(DateUtils.getWeekFirstTime());
		System.out.println(dateString);
	}
}
