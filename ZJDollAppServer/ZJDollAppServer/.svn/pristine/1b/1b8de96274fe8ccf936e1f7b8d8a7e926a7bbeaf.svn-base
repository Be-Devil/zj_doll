package com.imlianai.zjdoll.app.modules.support.pack.service;

import java.util.List;
import java.util.Map;

import com.imlianai.zjdoll.domain.doll.DollDetails;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.pack.vo.AppealInfo;
import com.imlianai.zjdoll.app.modules.support.pack.vo.GetDollListReqVO;
import com.imlianai.zjdoll.app.modules.support.pack.vo.OptRecordInfo;
import com.imlianai.zjdoll.app.modules.support.pack.vo.ReceiveReqVO;

public interface PackService {

	/**
	 * 获取用户抓取到娃娃列表
	 * @param reqVO
	 * @return
	 */
	public Map<String, Object> getDollList(GetDollListReqVO reqVO);

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
	 * @return
	 */
	public List<OptRecordInfo> getOptRecords(long lastId, Long uid);

	/**
	 * 申诉
	 * @param optId
	 * @param uid 
	 * @param reason 
	 * @return
	 */
	public BaseRespVO appeal(long optId, Long uid, String reason);

	/**
	 * 获取操作记录申诉信息
	 * @param optId
	 * @param version 
	 * @return
	 */
	public AppealInfo appealStatus(long optId, Integer version);

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
