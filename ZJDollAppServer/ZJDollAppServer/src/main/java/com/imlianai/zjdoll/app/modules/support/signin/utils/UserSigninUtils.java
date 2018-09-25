package com.imlianai.zjdoll.app.modules.support.signin.utils;

import java.util.ArrayList;
import java.util.List;

import com.imlianai.zjdoll.app.modules.core.doll.utils.AliasMethod;


public class UserSigninUtils {
	
	/**
	 * 获取奖品类型
	 * @param rpAmount
	 * @return
	 */
	public static int getAwardType(double rpAmount) {
		List<Double> chanceList = new ArrayList<Double>();
		chanceList.add(0.25); // 2币
		chanceList.add(0.25); // 5币
		chanceList.add(0.20); // 10币
		chanceList.add(0.01); // 50币
		chanceList.add(0.14); // 普通娃娃布料
		chanceList.add(0.10); // 稀有娃娃布料
		chanceList.add(0.05); // 0.01元红包
		/*if(rpAmount >= 40) {
			chanceList.add(0.0); // 1.3元红包
		} else {
			chanceList.add(0.05); // 1.3元红包
		}
		if(rpAmount >= 35) {
			chanceList.add(0.0); // 5.0元红包
			chanceList.add(0.0); // 8.8元红包
		} else {
			chanceList.add(0.02); // 5.0元红包
			chanceList.add(0.01); // 8.8元红包
		}*/
		AliasMethod aliasMethod = new AliasMethod(chanceList);
		return aliasMethod.next() + 1;
	}

	public static void main(String[] args) {
	}
}
