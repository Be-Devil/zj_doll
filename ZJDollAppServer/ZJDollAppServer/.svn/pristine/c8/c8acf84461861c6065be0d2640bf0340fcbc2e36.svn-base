package com.imlianai.zjdoll.app.modules.support.busowner.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.busowner.BusOwner;
import com.imlianai.zjdoll.domain.busowner.BusOwnerBusIncome;
import com.imlianai.zjdoll.domain.busowner.BusOwnerUserIndex;
import com.imlianai.zjdoll.domain.busowner.BusOwnerUserIndexRecord;

public interface BusOwnerDao {

	/**
	 * 获取当前时间下萌主列表
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	List<BusOwner> getBusOwners(int startCode, int endCode);

	/**
	 * 保存or更新抓娃娃次数
	 * @param busId
	 * @param uid
	 * @param startCode
	 * @param endCode
	 * @param increCatTimes
	 * @param increSuccCatTimes
	 * @return
	 */
	int saveOrUpdateCatTimes(int busId, long uid, int startCode, int endCode, int increCatTimes, int increSuccCatTimes);

	/**
	 * 获取用户指数
	 * @param busId
	 * @param uid
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	BusOwnerUserIndex getBusOwnerUserIndex(Integer busId, long uid, int startCode, int endCode);

	/**
	 * 保存or更新用户指数
	 * @param busId
	 * @param uid
	 * @param startCode
	 * @param endCode
	 * @param increValue
	 * @return
	 */
	int saveOrUpdateUserIndex(int busId, long uid, int startCode, int endCode, int increValue);

	/**
	 * 保存用户指数记录
	 * @param userIndexRecord
	 * @param increValue
	 * @return
	 */
	int saveUserIndexRecord(BusOwnerUserIndexRecord userIndexRecord, int increValue);

	/**
	 * 获取用户指数记录
	 * @param unionId
	 * @param uid
	 * @param startCode
	 * @param endCode
	 * @param busId 
	 * @return
	 */
	BusOwnerUserIndexRecord getUserIndexRecordByParams(String unionId, Long uid, int startCode, int endCode, Integer busId);

	/**
	 * 获取用户周期分享值
	 * @param uid
	 * @param startCode
	 * @param endCode
	 * @param busId 
	 * @return
	 */
	int getCurrCycleValue(Long uid, int startCode, int endCode, int busId);

	/**
	 * 获取最新周期的萌主信息
	 * @param busId
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	BusOwnerUserIndex getNewestBusOwner(int busId, int startCode, int endCode);

	/**
	 * 保存萌主
	 * @param uid
	 * @param busId
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	int saveBusOwner(Long uid, int busId, int startCode, int endCode);

	/**
	 * 保存or更新萌店收入
	 * @param busId
	 * @param startCode
	 * @param endCode
	 * @param coin
	 * @return
	 */
	int saveOrUpdateBusIncome(int busId, int startCode, int endCode, int coin);

	/**
	 * 保存萌店收入记录
	 * @param busId
	 * @param optId
	 * @param coin
	 * @param startCode
	 * @param endCode
	 * @param remark
	 */
	int saveBusIncomeRecord(int busId, long optId, int coin, int startCode, int endCode, String remark);

	/**
	 * 获取用户萌店列表
	 * @param uid
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	List<BusOwner> getBusOwnersByUid(long uid, int startCode, int endCode);

	/**
	 * 获取某台萌店的收入
	 * @param busId
	 * @param endCode 
	 * @param startCode 
	 * @return
	 */
	BusOwnerBusIncome getBusIncome(int busId, int startCode, int endCode);

	/**
	 * 获取萌店收入列表
	 * @param mdDollBusIds
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	List<BusOwnerBusIncome> getBusIncomeByBusIds(List<Integer> mdDollBusIds, int startCode, int endCode);

	/**
	 * 获取某台萌店机主指数排名前几列表
	 * @param busId
	 * @param startCode
	 * @param endCode
	 * @param size
	 * @return
	 */
	List<BusOwnerUserIndex> getBusOwnerUserIndexList(int busId, int startCode, int endCode, int size);

	/**
	 * 获取用户指数记录列表
	 * @param uid
	 * @param startCode
	 * @param endCode
	 * @param type
	 * @param busId 
	 * @return
	 */
	List<BusOwnerUserIndexRecord> getBusOwnerUserIndexRecordByParams(Long uid, int startCode, int endCode, int type, Integer busId);

	/**
	 * 获取机主指数排名前几列表
	 * @param startCode
	 * @param endCode
	 * @param size
	 * @return
	 */
	List<BusOwnerUserIndex> getBusOwnerUserIndexList(int startCode, int endCode, int size);

	/**
	 * 获取某轮在不同类型下用户获得的指数
	 * @param uid
	 * @param startCode
	 * @param endCode
	 * @param type
	 * @param busId
	 * @return
	 */
	int getUserIndexByType(Long uid, int startCode, int endCode, int type, int busId);

	/**
	 * 获取萌主的busId
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	List<Integer> getBusOwnerBusIds(int startCode, int endCode);

	/**
	 * 保存好友支持记录
	 * @param busId
	 * @param uid
	 * @param unionId
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	int saveBusOwnerUserSupportRecord(int busId, Long uid, String unionId, int startCode, int endCode);
}
