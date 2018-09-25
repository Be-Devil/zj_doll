package com.imlianai.zjdoll.app.modules.core.trade.dao;

import java.sql.SQLException;
import java.util.List;

import com.imlianai.zjdoll.domain.trade.ChargeState;
import com.imlianai.zjdoll.domain.trade.ChargeWay;
import com.imlianai.zjdoll.domain.trade.TradeCharge;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.UserFirstChargeTarget;
import com.imlianai.zjdoll.app.modules.core.trade.util.ali.domain.AlipayH5Body;

/**
 * 交易充值相关
 * 
 * @author Max
 * 
 */
public interface TradeChargeDAO {

	/**
	 * 新增充值记录
	 * 
	 * @param c
	 * @return
	 */
	long add(TradeCharge c);

	/**
	 * 更新充值状态
	 * 
	 * @param id
	 * @param state
	 * @return
	 * @throws SQLException
	 */
	int updateState(long id, ChargeState state);

	/**
	 * 拉取充值
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	TradeCharge getById(long id);

	/**
	 * 新增充值日志
	 * 
	 * @param chargeId
	 * @param uid
	 * @param createParams
	 * @param httpParams
	 */
	void addLog(long chargeId, long uid, String createParams, String httpParams);

	/**
	 * 更新回调日志
	 * 
	 * @param chargeId
	 * @param callBackValue
	 */
	void updatelog(long chargeId, String callBackValue);
	/**
	 * 通过第三方ID获取充值
	 * 
	 * @param otherId
	 * @param way
	 * @return
	 * @throws SQLException
	 */
	TradeCharge getByOtherId(String otherId, ChargeWay way);

	/**
	 * 加入测试充值
	 * 
	 * @param c
	 * @return
	 */
	long addTemp(TradeCharge c);

	/**
	 * 查询测试充值
	 * 
	 * @param otherId
	 * @param way
	 * @return
	 */
	TradeCharge getTempByOtherId(String otherId, ChargeWay way);
	
	/**
	 * 是否有充值特定金额订单
	 * @param uid
	 * @param amt
	 * @return
	 */
	public long hasChargeSpecialAmt(long uid,int amt);

	/**
	 * 记录支付宝订单body
	 * @param id
	 * @param body
	 * @return
	 */
	public int addAlipayH5Body(long id, String body,long uid,int price);
	
	/**
	 * 获取支付宝
	 * @param id
	 * @return
	 */
	public AlipayH5Body getAlipayH5Body(long id);

	/**
	 * 获取用户总充值金额
	 * @param uid
	 * @param eVENT_START_DATE 
	 * @return
	 */
	double getUserAllCost(Long uid, String startDate);
	
	/**
	 * 判断用户是否首冲
	 * @param uid
	 * @return
	 */
	public int hasCharge(long uid);
	
	
	/**
	 * 添加用户首冲消息记录
	 * @param uid
	 * @return
	 */
	public int addUserFirstChargeMsg(long uid);
	/**
	 * 删除用户首冲记录
	 * @param uid
	 * @return
	 */
	public int delUserFirstChargeMsg(long uid);
	/**
	 * 获取符合推送条件的用户
	 * @return
	 */
	public List<Long> getUserFirstChargeMsg();
	
	/**
	 * 下次推送时间
	 * @param uid
	 * @param nextPushHour
	 */
	public void incUserFirstChargeMsg(long uid,int nextPushHour);
	
	/**
	 * 增加首冲推送资格
	 * @param uid
	 * @param code
	 * @return
	 */
	public int addFirstChargeTarget(long uid, int code,int nextPushHour);
	
	/**
	 * 取消首冲资格
	 * @param uid
	 * @return
	 */
	public int removeFirstChargeTarget(long uid);
	
	
	/**
	 * 首冲推送用户
	 * @param uid
	 * @return
	 */
	public int isFirstChargePushTarget(long uid);

	/**
	 * 获取当前应推送的首冲列表
	 * @return
	 */
	public List<UserFirstChargeTarget> getUserFirstChargeTarget();
	
	/**
	 * 获取用户首冲资格
	 * @param uid
	 * @return
	 */
	public UserFirstChargeTarget getUserFirstChargeTarget(long uid);

	/**
	 * 获取首冲登录消息
	 * @param uid
	 * @return
	 */
	public UserFirstChargeTarget getFirstChargeMsg(long uid);


	/**
	 * 获取充值列表
	 * @param uIds
	 * @param start
	 * @param end
	 * @return
	 */
	List<TradeCharge> getListByUidsAndTime(List<Long> uIds, String start, String end);
}
