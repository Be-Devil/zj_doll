package com.imlianai.dollpub.app.modules.core.trade.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.app.modules.core.trade.dao.TradeDAO;
import com.imlianai.dollpub.domain.trade.TradeAccount;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class TradeDAOImpl implements TradeDAO{
	
	private static final BaseLogger logger = BaseLogger.getLogger(TradeDAOImpl.class);

	@Resource
	private JdbcHandler jdbcHandler;
	
	private String initAccount = "insert into trade_account(uid,time) values(?,now()) ";
	@Override
	public int initAccount(long uid) {
		try {
			return jdbcHandler.executeSql(initAccount, uid);
		} catch (Exception e) {
            logger.error("TradeDAO.initAccount error:"+e);
            return -1;
		}
	}

	private String getAccount = "select * from trade_account where uid=? ";
	@Override
	public TradeAccount getAccount(long uid) {
		try {
			return jdbcHandler.queryOneBySql(TradeAccount.class, getAccount, uid);
		} catch (Exception e) {
            logger.error("TradeDAO.getAccount error:"+e);
            return null;
		}
	}

	private String updateCoin = "update trade_account set coin=coin+?,time=now() where uid=? and coin+?>=0";
	@Override
	public int updateCoin(long uid, int coin) throws TradeOperationException {
		try {
			return jdbcHandler.executeSql(updateCoin, coin, uid, coin);
		} catch (Exception e) {
            logger.error("TradeDAO.updateCoin error:"+e);
            return -1;
		}
	}
	
	String updateJewel = "update trade_account set jewel=jewel+?,time=now() where uid=? and jewel+?>=0";
	@Override
	public int updateJewel(long uid, int jewel) throws TradeOperationException {
		try {
			return jdbcHandler.executeSql(updateJewel, jewel, uid, jewel);
		} catch (Exception e) {
            logger.error("TradeDAO.updateJewel error:"+e);
            return -1;
		}
	}
	
	String updateScore = "update trade_account set score=score+?,time=now() where uid=? and score+?>=0";
	@Override
	public int updateScore(long uid, int score) {
		try {
			return jdbcHandler.executeSql(updateScore, score, uid, score);
		} catch (Exception e) {
            logger.error("TradeDAO.updateScore error:"+e);
            return -1;
		}
	}
}
