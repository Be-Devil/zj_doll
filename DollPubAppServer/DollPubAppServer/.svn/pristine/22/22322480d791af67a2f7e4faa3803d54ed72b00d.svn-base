package com.imlianai.dollpub.app.modules.support.exchange.dao;

import java.util.List;

import com.imlianai.dollpub.domain.doll.DollExchangeRecord;
import com.imlianai.dollpub.domain.exchange.ShoppingExchangeRecord;

public interface DollExchangeDao {

	/**
	 * 获取娃娃兑换记录
	 * @param userDollId
	 * @return
	 */
	DollExchangeRecord getExchangeRecordByUserDollId(long userDollId);

	/**
	 * 保存娃娃兑换记录
	 * @param id
	 * @param currency
	 * @return
	 */
	int saveExchangeRecord(long id, int currency);
	
	/**
	 * 保存商城兑换记录
	 * @param uid
	 * @param udollId
	 * @param cost
	 * @param remark
	 * @param type 
	 * @param commNum 
	 * @param rareNum 
	 * @return
	 */
	int saveShoppingExchangeRecord(Long uid, long udollId, int cost, String remark, int type, int commNum, int rareNum);

	/**
	 * 保存or更新娃娃兑换量
	 * @param dollId
	 * @return
	 */
	int saveOrUpdateShoppingExchangeNum(int dollId);

	/**
	 * 获取娃娃已兑换量
	 * @param dollId
	 * @return
	 */
	int getShoppingExchangeNum(int dollId);

	/**
	 * 获取用户的兑换记录
	 * @param uid
	 * @return
	 */
	List<ShoppingExchangeRecord> getExcRecordsByUid(Long uid);
}
