package com.imlianai.zjdoll.app.modules.support.busowner.constants;

import com.ctrip.framework.apollo.util.PropertiesUtil;

public class BusOwnerConstants {

	public static final int CYCLE_MAX_VALUE = 1500; // 每轮好友支持最高分享值
	
	public static String HOMEPAGE_H5_TITLE = "我要当萌主"; // 萌主主页h5页面标题
	
	public static String HOMEPAGE_H5_URL = PropertiesUtil.getString("application","busOwner.homePage.url"); // 萌主主页h5
	
	public static String RULES_H5_TITLE = "我要当萌主"; // 萌主规则页面标题
	
	public static String RULES_H5_URL = PropertiesUtil.getString("application","busOwner.rules.url"); // 萌主规则h5
	
}
