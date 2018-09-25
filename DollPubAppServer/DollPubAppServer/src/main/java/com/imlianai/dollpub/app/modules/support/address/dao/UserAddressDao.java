package com.imlianai.dollpub.app.modules.support.address.dao;

import java.util.List;

import com.imlianai.dollpub.domain.doll.user.UserAddress;

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
	 * 获取默认收货地址(爽猪，增景app)
	 * @param uid
	 * @return
	 */
	UserAddress getDefAddress(Long uid);

	/**
	 * 修改UID的默认地址为非默认
	 * @param uid
	 * @return
	 */
	int resetDefaultAddress(Long uid);

	/**
	 * 获取默认收货地址
	 * @param uid
	 * @param isDefault
	 * @return
	 */
	UserAddress getDefAddress(Long uid, int isDefault);

}
