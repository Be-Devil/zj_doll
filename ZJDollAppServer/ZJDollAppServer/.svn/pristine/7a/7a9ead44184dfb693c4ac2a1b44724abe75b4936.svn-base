package com.imlianai.zjdoll.app.modules.core.trade.service;

import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.rpc.support.common.exception.NotEnoughBeanException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;

/**
 * 交易相关
 * @author Max
 *
 */
public interface TradeService {

	/**
	 * 初始化用户帐户
	 * @param uid
	 * @return
	 */
	public int initAccount(long uid);
	/**
	 * 获取用户帐户
	 * @param uid
	 * @return
	 */
	public TradeAccount getAccount(long uid);
    /**
     * 消费
     * @param mobileTradeRecord
     * @return
     * @throws TradeOperationException
     * @throws NotEnoughBeanException 
     */
    public boolean consume(TradeRecord record) throws TradeOperationException, NotEnoughBeanException;
    /**
     * 充值
     * @param record
     * @return
     * @throws TradeOperationException
     */
    public boolean charge(TradeRecord record) throws TradeOperationException;
}
