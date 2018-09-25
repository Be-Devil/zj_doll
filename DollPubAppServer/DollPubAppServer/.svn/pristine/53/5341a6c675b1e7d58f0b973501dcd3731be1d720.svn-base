package com.imlianai.dollpub.app.modules.core.user.customer.opt.dao;

import java.util.Date;
import java.util.List;

import com.imlianai.dollpub.domain.optrecord.OptRecord;

/**
 * 商户机器对应流水
 * @author wurui
 */
public interface CustomerOptRecordDao {

	/**
	 * 新增操作记录
	 * @param groupId 商户对应组(用于分表)
	 * @param optRecord
	 * @return
	 */
	public int insertEntity(int groupId,OptRecord optRecord);

	/**
	 * 通过id更新对应操作记录
	 * @param groupId 商户对应组(用于分表)
	 * @param optRecord
	 * @return
	 */
	public int updateEntityById(int groupId,OptRecord optRecord);

	/**
	 * 通过optId获取对应的操作记录
	 * @param groupId 商户对应组(用于分表)
	 * @param optId
	 * @return
	 */
	public OptRecord getEntityByOptId(int groupId, long optId);

	/**
	 * 通过开始游戏时间获取操作记录列表
	 * @param groupId 商户对应组(用于分表)
	 * @param startTime 开始游戏时间
	 * @return
	 */
	public List<OptRecord> getEntitysByStartTime(int groupId, Date startTime);

	/**
	 * 通过结果获取操作记录列表
	 * @param groupId 商户对应组(用于分表)
	 * @param result 抓取结果 0失败，1成功
	 * @return
	 */
	public List<OptRecord> getEntitysByResult(int groupId, int result);

	/**
	 * 获取所有操作记录列表
	 * @param groupId 商户对应组(用于分表)
	 * @param page 当前页
	 * @param pageSize 每页显示多少条记录
	 * @return
	 */
	public List<OptRecord> getOptRecords(int groupId, int page, int pageSize);

	/**
	 * 通过用户id获取对应的操作记录列表
	 * @param groupId 商户对应组(用于分表)
	 * @param uid 用户id
	 * @return
	 */
	public List<OptRecord> getOptRecordByUid(int groupId,long uid);
	
	public List<OptRecord> getOptRecordsByPaging(int groupId, long uid, long lastId, int pageSize);
	
}
