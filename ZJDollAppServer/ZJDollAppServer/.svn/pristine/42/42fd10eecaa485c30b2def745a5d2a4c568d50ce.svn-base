package com.imlianai.zjdoll.app.modules.publics.log.service;
import java.util.Map;

import com.imlianai.zjdoll.domain.log.LogPage;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
/**
 * 日志统计相关
 * 
 * @author Max
 * 
 */
public interface LogService {

	/**
	 * 新增日志
	 * 
	 * @param page
	 * @param data
	 */
	void add(LogPage page, Object data, Class<?> clazz);
	/**
	 * 带城市的日志
	 * 
	 * @param page
	 * @param data
	 */
	void addWithCity(LogPage page, Object data, Class<?> clazz);
	/**
	 * 包括了年月日的统计
	 * 
	 * @param page
	 * @param data
	 * @param clazz
	 */
	void addMoreDate(LogPage page, Object data, Class<?> clazz);
	/**
	 * 包括了年月日的统计
	 * 
	 * @param page
	 * @param vo
	 * @param data
	 * @param clazz
	 */
	void addMoreDate(LogPage page, BaseReqVO vo, Object data, Class<?> clazz);
	/**
	 * 向有唯一约束的表中信息数据/如果存在则什么都不做
	 * 
	 * @param page
	 * @param key
	 * @param data
	 * @param clazz
	 */
	void addUnique(LogPage page, Map<String, Object> key, Object data,
			Class<?> clazz);
}
