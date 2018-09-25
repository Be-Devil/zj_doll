package com.imlianai.zjdoll.app.modules.support.redpacket.utils;

import java.util.ArrayList;
import java.util.List;

import com.imlianai.zjdoll.app.modules.core.doll.utils.AliasMethod;


public class RedpacketUtils {
	
	/**
	 * 上机获得娃娃碎片
	 * @param rpAmount
	 * @return
	 */
	public static int getApplyDebris() {
		List<Double> chanceList = new ArrayList<Double>();
		chanceList.add(0.45); // 1块普通娃娃碎片
		chanceList.add(0.07); // 1块稀有娃娃碎片
		chanceList.add(0.45); // 2块普通娃娃碎片
		chanceList.add(0.03); // 2块稀有娃娃碎片
		
		AliasMethod aliasMethod = new AliasMethod(chanceList);
		return aliasMethod.next() + 1;
	}
	
	/**
	 * 开启红包获得的奖励类型
	 * @param isFree
	 * @return
	 */
	public static int getOpenRedpackRewardType(int isFree) {
		List<Double> chanceList = new ArrayList<Double>();
		if(isFree == 1) { // 免费
			chanceList.add(0.70); // 金币
			chanceList.add(0.30); // 红包
		} else {
			chanceList.add(0.30); // 金币
			chanceList.add(0.70); // 红包
		}
		AliasMethod aliasMethod = new AliasMethod(chanceList);
		return aliasMethod.next() + 1;
	}
	
	/**
	 * 是否为暴击红包
	 */
	public static boolean isCrit() {
		List<Double> chanceList = new ArrayList<Double>();
		chanceList.add(0.50); // 暴击红包
		chanceList.add(0.50); // 普通红包
		AliasMethod aliasMethod = new AliasMethod(chanceList);
		if(aliasMethod.next() == 0) {
			return true;
		}
		return false;
	}
}
