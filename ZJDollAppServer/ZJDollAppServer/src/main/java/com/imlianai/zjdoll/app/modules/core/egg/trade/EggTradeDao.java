package com.imlianai.zjdoll.app.modules.core.egg.trade;


import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.zjdoll.domain.egg.EggMachineUserAccount;
import com.imlianai.zjdoll.domain.egg.EggMachineUserAccountRecord;

public interface EggTradeDao {

	/**
	 * 初始化账户
	 * @param uid
	 * @return
	 */
	int initAccount(long uid);

	/**
	 * 获取账户
	 * @param uid
	 * @return
	 */
	EggMachineUserAccount getUserAccount(Long uid);

	/**
	 * 更新余额
	 * @param uid
	 * @param coin
	 * @return
	 * @throws TradeOperationException
	 */
	int updateBalance(long uid, int coin) throws TradeOperationException;

	/**
	 * 更新时光圈
	 * @param uid
	 * @param jewel
	 * @return
	 * @throws TradeOperationException
	 */
	int updateTimeCoupon(long uid, int jewel) throws TradeOperationException;
	
	/**
	 * 增加交易记录
	 * @param record
	 * @return
	 * @throws TradeOperationException
	 */
	public long addRecord(EggMachineUserAccountRecord record) throws TradeOperationException;;

}
