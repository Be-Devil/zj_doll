package com.imlianai.zjdoll.app.modules.core.egg.trade;

import com.imlianai.rpc.support.common.exception.NotEnoughBeanException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.zjdoll.domain.egg.EggMachineUserAccount;
import com.imlianai.zjdoll.domain.egg.EggMachineUserAccountRecord;
import com.imlianai.zjdoll.domain.trade.TradeCharge;

public interface EggTradeService {

	int initAccount(long uid);

	/**
	 * 获取账户
	 * @param uid
	 * @return
	 */
	EggMachineUserAccount getAccount(long uid);

	/**
	 * 消耗
	 * @param record
	 * @return
	 * @throws TradeOperationException
	 * @throws NotEnoughBeanException
	 */
	boolean consume(EggMachineUserAccountRecord record)
			throws TradeOperationException, NotEnoughBeanException;

	/**
	 * 充值
	 * @param record
	 * @return
	 * @throws TradeOperationException
	 */
	boolean charge(EggMachineUserAccountRecord record)
			throws TradeOperationException;
	
	/**
	 * 处理支付
	 * @param charge
	 */
	public void handlePayment(TradeCharge charge);

}
