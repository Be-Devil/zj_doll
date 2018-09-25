package com.imlianai.zjdoll.app.modules.support.userdoll.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.zjdoll.domain.doll.user.UserDollDebris;
import com.imlianai.zjdoll.domain.doll.user.UserDollMonthCount;
import com.imlianai.zjdoll.domain.doll.user.UserDollWeekCount;

public interface UserDollDao {

	/**
	 * 获取用户某个娃娃信息
	 * @param id
	 * @return
	 */
	UserDoll getUserDollById(long id);

	/**
	 * 修改用户娃娃状态
	 * @param id
	 * @param status
	 * @return
	 */
	int updateUserDollStatus(long id, int status);

	/**
	 * 获取可自动兑换的娃娃
	 * @param otherDays
	 * @return
	 */
	List<UserDoll> getExchangeUserDollList(int otherDays);

	/**
	 * 用户娃娃列表
	 * @param userId
	 * @param lastId
	 * @param pageSize
	 * @return
	 */
	List<UserDoll> getUserDollList(Long userId, long lastId, int pageSize);

	/**
	 * 用户捉取到的娃娃数量
	 * @param userId
	 * @return
	 */
	int getDollCountByUid(Long userId);
	
	/**
	 * 获取用户可发货的娃娃列表
	 * @param uid
	 * @return
	 */
	public List<UserDoll> getShippingList(Long uid);

	/**
	 * 获取用户某个未邮寄娃娃数量
	 * @param uid
	 * @param dollId
	 * @return
	 */
	int getDollCountByUserIdAndDollId(Long uid, long dollId);

	/**
	 * 获取娃娃周榜统计
	 * @param code
	 * @param num
	 * @return
	 */
	List<UserDollWeekCount> getUserDollWeekCountList(int code, int num);

	/**
	 * 获取娃娃月榜统计
	 * @param code
	 * @param num
	 * @return
	 */
	List<UserDollMonthCount> getUserDollMonthCountList(int code, int num);

	/**
	 * 获取用户某周的娃娃统计
	 * @param uid
	 * @param code
	 * @return
	 */
	UserDollWeekCount getUserDollWeekCountByUidAndCode(Long uid, int code);

	/**
	 * 获取用户月的娃娃统计
	 * @param uid
	 * @param code
	 * @return
	 */
	UserDollMonthCount getUserDollMonthCountByUidAndCode(Long uid, int code);

	/**
	 * 保存用户抓到的娃娃
	 * @param userDoll
	 * @return
	 */
	int saveUserDoll(UserDoll userDoll);

	/**
	 * 获取某个状态下用户的娃娃数量
	 * @param uid
	 * @param status
	 * @return
	 */
	int getUserDollSizeByParams(Long uid, int status);

	/**
	 * 获取用户未发货的娃娃
	 * @param uid
	 * @param dollId
	 * @param num
	 * @return
	 */
	List<UserDoll> getUserDollByParams(Long uid, long dollId, int num);

	/**
	 * 保存or修改用户娃娃个数(周统计)
	 * @param uid
	 * @param weekCode
	 * @param num
	 * @return
	 */
	int saveOrUpdateUserDollWeekCount(long uid, int weekCode, int num);

	/**
	 * 保存or修改用户娃娃个数(月统计)
	 * @param uid
	 * @param monthCode
	 * @param num
	 * @return
	 */
	int saveOrUpdateUserDollMonthCount(long uid, int monthCode, int num);

	/**
	 * 保存or修改娃娃碎片
	 * @param uid
	 * @param type
	 * @param num
	 * @return
	 */
	int saveOrUpdateUserDollDebris(Long uid, int type, double num);

	/**
	 * 保存娃娃碎片流水
	 * @param uid
	 * @param type
	 * @param num
	 * @param remark
	 * @return
	 */
	int saveUserDollDebrisLog(Long uid, int type, double num, String remark);
	/**
	 * 更新物流信息
	 * @param id
	 * @param expressName
	 * @param trackingNum
	 * @return
	 */
	public int updateUserDollShipping(long id,String expressName,String trackingNum,int state);

	/**
	 * 用户娃娃碎片列表
	 * @param uid
	 * @return
	 */
	List<UserDollDebris> getUserDollDebrisByUid(Long uid);

	/**
	 * 修改用户娃娃碎片
	 * @param uid
	 * @param type
	 * @param num
	 * @return
	 */
	int updateUserDollDebris(Long uid, int type, int num);

	/**
	 * 获取用户某种娃娃碎片信息
	 * @param uid
	 * @param type
	 * @return
	 */
	UserDollDebris getUserDollDebrisByParams(Long uid, int type);

	/**
	 * 获取最新的用钻石兑换的用户娃娃
	 * @param dollId
	 * @param type
	 * @return
	 */
	UserDoll getNewestUserDollByType(Integer dollId, int type);

	/**
	 * 获取某个状态下用户的娃娃数量(130版本以后)
	 * @param uid
	 * @param status
	 * @return
	 */
	int getUserDollSizeByParamsAfter130(Long uid, int status);

}
