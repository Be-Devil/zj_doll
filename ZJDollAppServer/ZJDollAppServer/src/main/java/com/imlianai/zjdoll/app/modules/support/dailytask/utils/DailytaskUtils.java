package com.imlianai.zjdoll.app.modules.support.dailytask.utils;

import java.util.ArrayList;
import java.util.List;

import com.imlianai.zjdoll.app.modules.core.doll.utils.AliasMethod;


public class DailytaskUtils {
	
	/**
	 * 是否可获得现金红包
	 * @return
	 */
	public static int getWin() {
		List<Double> chanceList = new ArrayList<Double>();
		chanceList.add(0.70); // 未获得
		chanceList.add(0.30); // 获得
		AliasMethod aliasMethod = new AliasMethod(chanceList);
		return aliasMethod.next();
	}
}
