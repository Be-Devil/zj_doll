package com.imlianai.zjdoll.app.modules.support.address.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.user.UserAddress;

public interface UserAddressDao {

	/**
	 * 保存收货地址
	 * @param userAddress
	 * @return
	 */
	int add(UserAddress userAddress);

	/**
	 * 获取收货地址列表
	 * @param uid
	 * @return
	 */
	List<UserAddress> getList(Long uid);
	
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
	 * 修改收货地址
	 * @param userAddress
	 * @return
	 */
	int update(UserAddress userAddress);

	/**
	 * 获取默认收货地址
	 * @param uid
	 * @return
	 */
	UserAddress getDefAddress(Long uid);

	/**
	 * 修改默认收货地址
	 * @param id
	 * @return
	 */
	int updateDefAddress(Long id);

	/**
	 * 修改其他地址为非默认地址
	 * @param id
	 * @return
	 */
	int updateOtherUserAddress(Long id);

}
