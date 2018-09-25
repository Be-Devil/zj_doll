package com.imlianai.dollpub.app.modules.core.trade.dao;

import com.imlianai.dollpub.domain.trade.TradeAccount;
import com.imlianai.rpc.support.common.exception.TradeOperationException;

/**
 * 交易相关
 * @author Max
 *
 */
public interface TradeDAO {

	/**
	 * 创建帐户
	 * @param uid
	 * @return
	 */
	public int initAccount(long uid);
	/**
	 * 获取帐户
	 * @param uid
	 * @return
	 */
	public TradeAccount getAccount(long uid);
	/**
	 * 更新游戏币数
	 * @param uid
	 * @param coin
	 * @return
	 * @throws TradeOperationException 
	 */
	public int updateCoin(long uid, int coin) throws TradeOperationException;
	
	/**
	 * 更新钻石数
	 * @param uid
	 * @param jewel
	 * @return
	 */
	public int updateJewel(long uid, int jewel) throws TradeOperationException;
	
	/**
	 * 更新用户积分
	 * @param uid
	 * @param cost
	 * @return
	 */
	public int updateScore(long uid, int score);
}
