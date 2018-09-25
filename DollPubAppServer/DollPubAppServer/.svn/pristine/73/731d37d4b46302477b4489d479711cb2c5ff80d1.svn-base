package com.imlianai.dollpub.app.modules.support.exchange.service;

import java.util.List;

import com.imlianai.dollpub.app.modules.support.exchange.vo.ComposeDollInfo;
import com.imlianai.dollpub.app.modules.support.exchange.vo.ComposeGetListRespVO;
import com.imlianai.dollpub.domain.doll.DollRecycleRecord;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public interface DollComposeService {

	/**
	 * 获取可合成的娃娃列表
	 * @param uid
	 * @return
	 */
	public ComposeGetListRespVO getList(Long uid);
	
	
	/**
	 * 娃娃合成
	 * @return
	 */
	public BaseRespVO compose(Long uid, int dollId);

	/**
	 * 保存娃娃回收记录
	 * @param uDollId
	 * @param coin
	 * @return
	 */
	int saveRecycleRecord(long uDollId, int jewel);

	/**
	 * 获取娃娃回收记录
	 * @param id
	 * @return
	 */
	public DollRecycleRecord getRecycleRecordByUDollId(Long id);
	
	/**
	 * 娃娃详情信息
	 * @param type
	 * @param id
	 * @return
	 */
	public BaseRespVO getDollDetail(int type, int id);

	/**
	 * 获取最近合成的娃娃列表
	 * @return
	 */
	public List<ComposeDollInfo> getRecentComList();
}
