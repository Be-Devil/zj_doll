package com.imlianai.zjdoll.app.modules.support.signin.contants;

import com.ctrip.framework.apollo.util.PropertiesUtil;

public class UserSigninContants {

	public static String playingDesc = "<font color=\"#a84712\">1、每日签到，抽取现金、金币、娃娃大奖！<br>"
			+ "2、集齐指定数量的 <img style=\"width:16pt;height:13pt\" src=\"http://lianai-image-sys.qiniudn.com/doll/ptSuipian_16@2x.png\"></img> "
			+ "普通娃娃碎片和 <img style=\"width:16pt;height:13pt\" src=\"http://lianai-image-sys.qiniudn.com/doll/xySuipian_16@2x.png\"></img> 稀有娃娃碎片,"
	        + "可直接兑换娃娃！<br>"
	        + "3、每累计签到7天必中现金红包！<br>"
	        + "4、本活动与苹果官方无关，解释权归娃娃星球所有。</font>";
	
	public static String SHOP_URL = PropertiesUtil.getString("application","shop.url"); // 兑换商城
}
