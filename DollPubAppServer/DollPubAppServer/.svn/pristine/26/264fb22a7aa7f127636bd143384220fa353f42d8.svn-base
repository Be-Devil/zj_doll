package com.imlianai.dollpub.app.modules.support.pack.service;

import java.util.List;
import java.util.Map;

import com.imlianai.dollpub.app.modules.support.pack.domain.GrabDollRecord;
import com.imlianai.dollpub.app.modules.support.pack.vo.AppealInfo;
import com.imlianai.dollpub.app.modules.support.pack.vo.GetDollGrabRecordRespVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.OptRecordInfo;
import com.imlianai.dollpub.app.modules.support.pack.vo.ReceiveReqVO;
import com.imlianai.dollpub.domain.doll.DollDetails;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public interface PackService {

	/**
	 * 获取游戏记录
	 * @param uid
	 * @return
	 */
	public List<GrabDollRecord> getGrabRecord(long uid);
	
	/**
	 * 获取用户抓取到娃娃列表
	 * @param uid
	 * @param lastId
	 * @param status 
	 * @return
	 */
	public Map<String, Object> getDollList(Long uid, long lastId, int status);

	/**
	 * 获取娃娃详情信息
	 * @param id
	 * @return
	 */
	public DollDetails getDollDetail(long id);

	/**
	 * 娃娃兑换
	 * @param id
	 * @param uid 
	 * @return
	 */
	public BaseRespVO exchange(long id, Long uid);

	/**
	 * 游戏记录
	 * @param lastId
	 * @param uid
	 * @param customerId
	 * @return
	 */
	public List<OptRecordInfo> getOptRecords(long lastId, Long uid, int customerId);

	/**
	 * 申诉
	 * @param optId
	 * @param uid 
	 * @param reason 
	 * @return
	 */
	public BaseRespVO appeal(long optId,int customerId, Long uid, String reason);

	/**
	 * 获取操作记录申诉信息
	 * @param optId
	 * @return
	 */
	public AppealInfo appealStatus(long optId);

	/**
	 * 娃娃回收
	 * @param id
	 * @param uid
	 * @return
	 */
	public BaseRespVO recycle(long id, Long uid);
	
	/**
	 * 获取娃娃的温馨提示
	 * @param dollId
	 * @return
	 */
	public BaseRespVO getNotice(int dollId);

	/**
	 * 娃娃领取
	 * @param reqVO
	 * @return
	 */
	public BaseRespVO receive(ReceiveReqVO reqVO);
	
}
