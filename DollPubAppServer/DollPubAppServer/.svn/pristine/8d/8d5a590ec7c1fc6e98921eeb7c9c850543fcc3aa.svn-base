package com.imlianai.dollpub.app.modules.support.exchange.service;

import java.util.List;

import com.imlianai.dollpub.app.modules.support.exchange.vo.ExchangeDollInfo;
import com.imlianai.dollpub.app.modules.support.exchange.vo.ExchangeGetListRespVO;
import com.imlianai.dollpub.app.modules.support.exchange.vo.ExchangeRecord;
import com.imlianai.dollpub.domain.doll.DollExchangeRecord;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public interface DollExchangeSevice {

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
	 * 获取可钻石兑换的娃娃列表
	 * @param uid
	 * @return
	 */
	public ExchangeGetListRespVO getList(Long uid);
	
	/**
	 * 钻石兑换成娃娃
	 * @return
	 */
	public BaseRespVO jewelExchangeToDoll(Long uid, int dollId);

	/**
	 * 获取可回收成钻石的用户娃娃列表
	 * @param uid
	 * @return
	 */
	BaseRespVO getRecycleList(Long uid);

	/**
	 * 回收娃娃列表
	 * @param uid
	 * @param dollList
	 * @return
	 */
	BaseRespVO recycleList(Long uid, List<Long> dollList);

	/**
	 * 获取最近对换的娃娃列表
	 * @return
	 */
	List<ExchangeDollInfo> getRecentExcList(int customerId);

	/**
	 * 获取兑换记录
	 * @param uid
	 * @return
	 */
	List<ExchangeRecord> getExcRecords(Long uid);
}
