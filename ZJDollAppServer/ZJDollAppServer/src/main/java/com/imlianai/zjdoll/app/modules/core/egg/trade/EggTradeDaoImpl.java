package com.imlianai.zjdoll.app.modules.core.egg.trade;


import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.domain.egg.EggMachineUserAccount;
import com.imlianai.zjdoll.domain.egg.EggMachineUserAccountRecord;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class EggTradeDaoImpl implements EggTradeDao {

	@Resource
	private JdbcHandler jdbcHandler;

	private static final BaseLogger logger = BaseLogger
			.getLogger(EggTradeDaoImpl.class);

	private String initAccount = "insert into egg_machine_user_account(uid,createTime,updateTime) values(?,now(),now()) ";

	@Override
	public int initAccount(long uid) {
		try {
			return jdbcHandler.executeSql(initAccount, uid);
		} catch (Exception e) {
			logger.error("EggTradeDao.initAccount error:" + e);
			return -1;
		}
	}

	private String getUserAccount = "select * from egg_machine_user_account where uid=?";

	@Override
	public EggMachineUserAccount getUserAccount(Long uid) {
		return jdbcHandler.queryOneBySql(EggMachineUserAccount.class,
				getUserAccount, uid);
	}

	private String updateUserBalance = "update egg_machine_user_account set amount=amount+?,updateTime=now() where uid=? and amount+?>=0";

	@Override
	public int updateBalance(long uid, int coin) throws TradeOperationException {
		try {
			return jdbcHandler.executeSql(updateUserBalance, coin, uid, coin);
		} catch (Exception e) {
			logger.error("EggTradeDao.updateUserBalance error:" + e);
			return -1;
		}
	}

	private String updateUserTimeCoupon = "update egg_machine_user_account set num=num+?,updateTime=now() where uid=? and num+?>=0";

	@Override
	public int updateTimeCoupon(long uid, int jewel)
			throws TradeOperationException {
		try {
			return jdbcHandler.executeSql(updateUserTimeCoupon, jewel, uid,
					jewel);
		} catch (Exception e) {
			logger.error("EggTradeDao.updateTimeCoupon error:" + e);
			return -1;
		}
	}

	@Override
	public long addRecord(EggMachineUserAccountRecord record)
			throws TradeOperationException {
		return jdbcHandler
				.executeSql(
						"INSERT INTO egg_machine_user_account_record(`uid`,`dateCode`,`num`,`remark`,`type`,`createTime`,`direction`,`tradeType`)VALUES(?,DATE_FORMAT(now(),'%Y%m%d'),?,?,?,now(),?,?)",
						record.getUid(), record.getNum(),record.getRemark(),record.getType(),record.getDirection(),record.getTradeType());
	}

}
