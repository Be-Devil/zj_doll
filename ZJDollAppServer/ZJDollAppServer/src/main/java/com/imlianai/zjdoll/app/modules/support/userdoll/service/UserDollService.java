package com.imlianai.zjdoll.app.modules.support.userdoll.service;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.BaseDollInfo;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.LogisticsInfo;
import com.imlianai.zjdoll.domain.doll.user.RankingItem;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.zjdoll.domain.doll.user.UserDollDebris;
import com.imlianai.zjdoll.domain.doll.user.UserDollWeekCount;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.ranking.vo.GetRankingRespVO;
import com.imlianai.zjdoll.app.modules.support.userdoll.vo.GetDollSizeInfo;

public interface UserDollService {

	/**
	 * 获取用户某个娃娃信息
	 * @param id
	 * @return
	 */
	public UserDoll getUserDollById(long id);

	/**
	 * 修改用户娃娃状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateUserDollStatus(long id, int status);

	/**
	 * 获取可自动兑换的娃娃
	 * @param otherDays
	 * @return
	 */
	public List<UserDoll> getExchangeUserDollList(int otherDays);

	/**
	 * 获取榜单列表
	 * @param type
	 * @param uid 
	 * @return
	 */
	public GetRankingRespVO getRanking(int type, Long uid);

	/**
	 * 获取用户娃娃列表
	 * @param userId
	 * @param lastId 
	 * @return
	 */
	public List<BaseDollInfo> getUserDollList(Long userId, long lastId);

	/**
	 * 用户娃娃统计信息
	 * @param userId
	 * @return
	 */
	public RankingItem getUserDollCount(Long userId);

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
	public int getDollCountByUserIdAndDollId(Long uid, long dollId);

	/**
	 * 获取娃娃周榜统计
	 * @param code
	 * @param num
	 * @return
	 */
	List<UserDollWeekCount> getUserDollWeekCountList(int code, int num);

	/**
	 * 保存用户抓到的娃娃
	 * @param userDoll 
	 * @return
	 */
	public int saveUserDoll(UserDoll userDoll);

	/**
	 * 保存用户抓到的娃娃(自定义消息)
	 * @param userDoll
	 * @return
	 */
	public int saveUserDoll(UserDoll userDoll,String msg);


	/**
	 * 获取某个状态下用户的娃娃数量
	 * @param uid
	 * @param status
	 * @return
	 */
	public int getUserDollSizeByParams(Long uid, int status);

	/**
	 * 用户娃娃个数信息
	 * @param uid
	 * @return
	 */
	public GetDollSizeInfo getDollSize(Long uid);

	/**
	 * 获取用户未发货的娃娃
	 * @param uid
	 * @param dollId
	 * @param num
	 * @return
	 */
	public List<UserDoll> getUserDollByParams(Long uid, long dollId, int num);

	/**
	 * 保存or修改用户娃娃个数(周统计)
	 * @param uid
	 * @param weekCode
	 * @param num
	 * @return
	 */
	public int saveOrUpdateUserDollWeekCount(long uid, int weekCode, int num);

	/**
	 * 保存or修改用户娃娃个数(月统计)
	 * @param uid
	 * @param monthCode
	 * @param i
	 * @return
	 */
	public int saveOrUpdateUserDollMonthCount(long uid, int monthCode, int i);

	/**
	 * 获取用户娃娃物流信息
	 * @param uDollId
	 * @return
	 */
	public List<LogisticsInfo> getLogistics(Long uDollId);

	/**
	 * 获取个人排名
	 * @param type
	 * @param userId
	 * @return
	 */
	public int getRankingNum(int type, Long userId);

	/**
	 * 娃娃归还
	 * @param optId
	 * @param uid
	 * @return
	 */
	public BaseRespVO returnDoll(Long optId, long uid);

	/**
	 * 退币
	 * @param optId
	 * @param uid
	 * @return
	 */
	public BaseRespVO refundCoin(Long optId, long uid);

	/**
	 * 保存or修改娃娃碎片
	 * @param uid
	 * @param type
	 * @param num
	 * @param remark
	 * @return
	 */
	public void saveOrUpdateUserDollDebris(Long uid, int type, double num, String remark);

	/**
	 * 获取用户娃娃碎片列表
	 * @param uid
	 * @return
	 */
	public List<UserDollDebris> getUserDollDebrisByUid(Long uid);

	/**
	 * 修改娃娃碎片
	 * @param uid
	 * @param type
	 * @param i
	 * @return
	 */
	public int updateUserDollDebris(Long uid, int type, int num);
	
	/**
	 * 更新物流信息
	 * @param id
	 * @param expressName
	 * @param trackingNum
	 * @return
	 */
	public int updateUserDollShipping(long id,String expressName,String trackingNum,int state);
	
	/**
	 * 保存用户娃娃
	 * @param uid
	 * @param dollId
	 * @param optId
	 * @param remark
	 * @param type
	 */
	public long saveUserDoll(Long uid, int dollId, Long optId, String remark, int type);
	
	/**
	 * 获取用户娃娃的默认状态
	 * @param dollInfo
	 * @return
	 */
	public int getUserDollDefaultStatus(DollInfo dollInfo);

	/**
	 * 虚拟娃娃处理
	 * @param doll
	 * @param status
	 * @param uDollId
	 * @param uid
	 */
	public void handleVirtualDoll(DollInfo doll, int status, long uDollId, long uid);

	/**
	 * 获取最新的用钻石兑换的用户娃娃
	 * @param dollId
	 * @param type
	 * @return
	 */
	public UserDoll getNewestUserDollByType(Integer dollId, int type);

	/**
	 * 获取某个状态下用户的娃娃数量(130版本以后)
	 * @param uid
	 * @param status
	 * @return
	 */
	public int getUserDollSizeByParamsAfter130(Long uid, int status);
	
	/**
	 * 用户娃娃自动兑换处理
	 * @param userDoll
	 * @param dollInfo
	 */
	public void handleAutoExchange(UserDoll userDoll, DollInfo dollInfo);
}
