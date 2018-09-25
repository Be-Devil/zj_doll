package com.imlianai.zjdoll.app.modules.support.address.service;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.user.UserAddress;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.address.vo.UserAddressAddReqVO;
import com.imlianai.zjdoll.app.modules.support.address.vo.UserAddressInfo;

public interface UserAddressService {

	/**
	 * 新增收货地址
	 * @param reqVO
	 * @return
	 */
	BaseRespVO add(UserAddressAddReqVO reqVO);

	/**
	 * 获取收货地址列表
	 * @param uid
	 * @return
	 */
	List<UserAddressInfo> getList(Long uid);

	/**
	 * 修改收货地址
	 * @param reqVO
	 * @return
	 */
	BaseRespVO update(UserAddressAddReqVO reqVO);

	/**
	 * 获取默认收货地址
	 * @param uid
	 * @return
	 */
	UserAddressInfo getDefAddress(Long uid);
	
	/**
	 * 根据地址id获取地址
	 * @param id
	 * @return
	 */
	UserAddress getAddressByAddrId(long id);
	
	/**
	 * 获取自定义地址
	 * @param id
	 * @return
	 */
	UserAddress getCustomAddressByAddrId(long id);

	/**
	 * 修改默认收货地址
	 * @param id
	 * @return
	 */
	BaseRespVO updateDefAddress(Long id);

}
