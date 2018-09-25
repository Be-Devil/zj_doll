package com.imlianai.zjdoll.app.modules.support.busowner.service;

import java.util.List;
import java.util.Map;

import com.imlianai.zjdoll.domain.busowner.BusOwner;
import com.imlianai.zjdoll.domain.busowner.BusOwnerBusIncome;
import com.imlianai.zjdoll.domain.busowner.BusOwnerRes;
import com.imlianai.zjdoll.domain.busowner.BusOwnerUserIndex;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.AddShareValueReqVO;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.BusOwnerInfo;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.PlayerRankingItem;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.ShareRecordRes;
import com.imlianai.zjdoll.app.modules.support.busowner.vo.ShopItem;

public interface BusOwnerService {
	
	/**
	 * 更新萌主
	 */
	public void initBusOwners();

	/**
	 * 获取萌主对象
	 * @param busId
	 * @return
	 */
	BusOwner getBusOwnerCache(int busId);
	
	/**
	 * 获取某台萌店的萌主排名前三信息
	 * @param busId
	 * @return
	 */
	List<BusOwnerInfo> getBusOwnerInfos(int busId);

	/**
	 * 机主指数处理
	 * @param record
	 */
	void handleUserIndex(DollOptRecord record);

	/**
	 * 增加分享值
	 * @param reqVO
	 * @return
	 */
	BaseRespVO addShareValue(AddShareValueReqVO reqVO);
	
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
	 * 萌店收入处理
	 * @param busId
	 * @param coin
	 * @param optId
	 * @param remark
	 */
	void handleBusIncome(int busId, int coin, long optId, String remark);

	/**
	 * 判断用户是否为萌主
	 * @param uid
	 * @return
	 */
	boolean isBusOwner(long uid);
	
	/**
	 * 获取某台萌店当前轮的收入
	 * @param busId
	 * @return
	 */
	BusOwnerBusIncome getBusIncome(int busId);

	/**
	 * 获取用户所有的萌店房间号
	 * @param uid
	 * @return
	 */
	String getBusOwnerRoomNumsByUid(Long uid);

	/**
	 * 获取萌主列表
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	List<BusOwner> getBusOwner(int startCode, int endCode);

	/**
	 * 获取处理过的萌店名称
	 * @param info
	 * @param dollBus
	 * @return
	 */
	String getBusName(DollInfo info, DollBus dollBus);
	
	/**
	 * 获取未处理过的萌店名称
	 * @param info
	 * @param dollBus
	 * @return
	 */
	public String getNotHandledBusName(DollInfo info, DollBus dollBus);

	/**
	 * 获取萌店收入列表
	 * @param mdDollBusIds
	 * @return
	 */
	List<BusOwnerBusIncome> getBusIncomeByBusIds(List<Integer> mdDollBusIds);

	/**
	 * 获取某台萌店机主指数排名前几列表
	 * @param busId
	 * @param size
	 * @param dateNum1
	 * @param dateNum2
	 * @param dateNum3
	 * @param dateNum4
	 * @return
	 */
	List<BusOwnerUserIndex> getBusOwnerUserIndexList(int busId, int size, int dateNum1, int dateNum2, int dateNum3, int dateNum4);

	/**
	 * 获取用户分享值记录
	 * @param uid
	 * @param unionId 
	 * @param busId 
	 * @return
	 */
	List<ShareRecordRes> getShareRecords(Long uid, String unionId, Integer busId);

	/**
	 * 玩家排行榜单列表
	 * @param uid
	 * @param busId 
	 * @return
	 */
	List<PlayerRankingItem> getPlayerRankings(Long uid, int busId);

	/**
	 * 当前轮时间信息
	 * @param reqVO
	 * @return
	 */
	BaseRespVO getTimeInfo(BaseReqVO reqVO);

	/**
	 * 萌店列表
	 * @param reqVO
	 * @return
	 */
	List<ShopItem> getShopList(BaseReqVO reqVO);

	/**
	 * 获取萌主信息返回对象
	 * @param uid
	 * @return
	 */
	BusOwnerRes getBusOwnerRes(Long uid);

	/**
	 * 获取萌主的busId
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	List<Integer> getBusOwnerBusIds(int startCode, int endCode);

	/**
	 * 经营收益信息
	 * @param uid
	 * @param busId
	 * @return
	 */
	BaseRespVO getIncomeInfo(Long uid, int busId);

	/**
	 * 获取某轮萌店排名前几位的用户指数
	 * @param busId
	 * @param size
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	List<BusOwnerUserIndex> getBusOwnerUserIndexByParams(int busId, int size, int startCode, int endCode);

	/**
	 * 获取某轮萌店的收入
	 * @param busId
	 * @param startCode
	 * @param endCode
	 * @return
	 */
	BusOwnerBusIncome getBusIncomeByParams(int busId, int startCode, int endCode);

	/**
	 * 榜单排名消息推送
	 */
	void pushRankingMsg();

	/**
	 * 判断某台机器是否为萌店
	 * @param busId
	 * @return
	 */
	public boolean isMengDian(int busId);

	/**
	 * 获取萌主h5页面信息
	 * @param uid
	 * @param busId 
	 * @return
	 */
	public Map<String, String> getBusOwnerH5Map(Long uid, Integer busId);
}
