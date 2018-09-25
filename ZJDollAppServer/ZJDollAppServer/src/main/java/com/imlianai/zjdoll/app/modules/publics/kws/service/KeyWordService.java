package com.imlianai.zjdoll.app.modules.publics.kws.service;
import com.imlianai.zjdoll.app.modules.publics.kws.mr.Result;
public interface KeyWordService {
	/**
	 * 初始化字库
	 */
	void init();
	/**
	 * 匹配A类关键字
	 * 
	 * @param target
	 * @return
	 */
	Result matchA(String target);

	/**
	 * 匹配B类关键字
	 * 
	 * @param target
	 * @return
	 */
	Result matchB(String target);

	/**
	 * 匹配S类关键字
	 * 
	 * @param target
	 * @return
	 */
	Result matchS(String target);

}
