package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.utils;

import java.util.ArrayList;
import java.util.List;

import com.imlianai.zjdoll.app.modules.core.doll.utils.AliasMethod;

public class TwistedEggMachineUtils {

	/**
	 * 抽奖
	 * @param type
	 * @return
	 */
	public static int getAwardType(int type) {
		List<Double> chanceList = new ArrayList<Double>();
		if(type == 3) {
			chanceList.add(0.32); // 11币
			chanceList.add(0.20); // 15币
			chanceList.add(0.10); // 19币
			chanceList.add(0.32); // 25币
			chanceList.add(0.01); // 33币
			chanceList.add(0.05); // 福袋
		} else if(type == 2) {
			chanceList.add(0.40); // 11币
			chanceList.add(0.14); // 15币
			chanceList.add(0.05); // 19币
			chanceList.add(0.40); // 25币
			chanceList.add(0.01); // 33币
		} else if(type == 1) {
			chanceList.add(0.05); // 11币
			chanceList.add(0.05); // 15币
			chanceList.add(0.25); // 19币
			chanceList.add(0.50); // 25币
			chanceList.add(0.15); // 33币
		}
		
		AliasMethod aliasMethod = new AliasMethod(chanceList);
		return aliasMethod.next() + 1;
	}
	
	/**
	 * 获取手气值
	 * @param coin
	 * @return
	 */
	public static int getFortuneValue(int coin) {
		return (coin + 100) * 8;
	}
}
