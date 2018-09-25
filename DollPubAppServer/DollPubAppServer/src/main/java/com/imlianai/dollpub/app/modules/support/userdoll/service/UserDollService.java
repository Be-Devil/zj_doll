package com.imlianai.dollpub.app.modules.support.userdoll.service;

import java.util.List;

import com.imlianai.dollpub.app.modules.support.ranking.vo.GetRankingRespVO;
import com.imlianai.dollpub.app.modules.support.userdoll.vo.GetDollSizeInfo;
import com.imlianai.dollpub.domain.doll.BaseDollInfo;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.doll.DollDetails;
import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.dollpub.domain.doll.LogisticsInfo;
import com.imlianai.dollpub.domain.doll.user.RankingItem;
import com.imlianai.dollpub.domain.doll.user.UserDoll;
import com.imlianai.dollpub.domain.doll.user.UserDollDebris;
import com.imlianai.dollpub.domain.doll.user.UserDollWeekCount;
import com.imlianai.dollpub.domain.optrecord.OptRecord;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

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
	 * @param customerId 
	 * @return
	 */
	public GetRankingRespVO getRanking(int type, Long uid, int customerId);

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
	 * @param groupId
	 * @return
	 */
	List<UserDollWeekCount> getUserDollWeekCountList(int code, int num, Integer groupId);

	/**
	 * 保存用户抓到的娃娃
	 * @param userDoll 
	 * @return
	 */
	public int saveUserDoll(UserDoll userDoll);

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
	 * @param groupId 
	 * @return
	 */
	public int saveOrUpdateUserDollWeekCount(long uid, int weekCode, int num, Integer groupId);

	/**
	 * 保存or修改用户娃娃个数(月统计)
	 * @param uid
	 * @param monthCode
	 * @param num
	 * @param groupId 
	 * @return
	 */
	public int saveOrUpdateUserDollMonthCount(long uid, int monthCode, int num, Integer groupId);

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
	 * @param customerId 
	 * @return
	 */
	public int getRankingNum(int type, Long userId, int customerId);

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
	 * 获取某个状态下的娃娃列表
	 * @param uid
	 * @param state
	 */
	public BaseRespVO getUserDollByState(long uid, Integer status);
	
	/**
	 * 获取用户某个娃娃信息
	 * @param uid
	 * @param id
	 * @return
	 */
	public UserDoll getUserDollById(long uid,int id);
	
	/**
	 * 娃娃兑换
	 * @param uid
	 * @param id
	 * @return
	 */
	public BaseRespVO exchange(long uid,int id) ;
	
	/**
	 * 申请发货
	 * @param uid
	 * @param id
	 * @return
	 */
	public BaseRespVO applyForShipments(long uid,int id,int addId,List<Integer> dollId);
	
	/**
	 * 获取娃娃信息
	 * @param uid
	 * @param id
	 * @return
	 */
	public DollDetails getDollInfo(long uid, int id);
	
	/**
	 * 获取用户娃娃碎片列表
	 * @param uid
	 * @return
	 */
	public List<UserDollDebris> getUserDollDebrisByUid(Long uid);
	
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
	 * 保存用户娃娃
	 * @param uid
	 * @param dollId
	 * @param optId
	 * @param remark
	 * @param type
	 */
	public long saveUserDoll(Long uid, int dollId, Long optId, String remark, int type);


	/**
	 * 机器回调存用户背包
	 * @param optRecord
	 * @param dollBus
	 * @param groupId
	 */
	public void resultUserDoll(OptRecord optRecord, DollBus dollBus,int groupId);

	/**
	 * 处理自动兑换
	 * @param userDoll
	 * @param dollInfo
	 */
	public void handleAutoExchange(UserDoll userDoll, DollInfo dollInfo);

}
