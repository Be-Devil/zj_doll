package com.imlianai.zjdoll.app.modules.support.address.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.doll.user.UserAddress;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class UserAddressDaoImpl implements UserAddressDao {

	@Resource
	JdbcHandler jdbcHandler;
	
	String add = "insert into user_address(uid,receiver,phone,province,city,district,fullAddress,isDefault,createTime,updateTime) values(?,?,?,?,?,?,?,?,now(),now())";
	@Override
	public int add(UserAddress userAddress) {
		return jdbcHandler.executeSql(add, userAddress.getUid(), userAddress.getReceiver(), userAddress.getPhone(),userAddress.getProvince(),
				userAddress.getCity(), userAddress.getDistrict(), userAddress.getFullAddress(), userAddress.getIsDefault());
	}
	
	String getList = "select * from user_address where uid=? order by isDefault desc, createTime desc";
	@Override
	public List<UserAddress> getList(Long uid) {
		return jdbcHandler.queryBySql(UserAddress.class, getList, uid);
	}
	
	String update = "update user_address set receiver=?,phone=?,province=?,city=?,district=?,fullAddress=?,updateTime=now() where id=?";
	@Override
	public int update(UserAddress userAddress) {
		return jdbcHandler.executeSql(update, userAddress.getReceiver(), userAddress.getPhone(), userAddress.getProvince(),
				userAddress.getCity(), userAddress.getDistrict(), userAddress.getFullAddress(), userAddress.getId());
	}
	
	String getDefAddress = "select * from user_address where uid=? order by isDefault desc,createTime desc limit 1";
	@Override
	public UserAddress getDefAddress(Long uid) {
		return jdbcHandler.queryOneBySql(UserAddress.class, getDefAddress, uid);
	}
	
	String getAddressByAddrId = "select * from user_address where id=? ";
	@Override
	public UserAddress getAddressByAddrId(long id) {
		return jdbcHandler.queryOneBySql(UserAddress.class, getAddressByAddrId,id);
	}
	
	String getCustomAddressByAddrId = "select * from custom_address where id=? ";
	@Override
	public UserAddress getCustomAddressByAddrId(long id) {
		return jdbcHandler.queryOneBySql(UserAddress.class, getCustomAddressByAddrId,id);
	}
	
	String updateDefAddress = "update user_address set isDefault = 1 where id=?";
	@Override
	public int updateDefAddress(Long id) {
		return jdbcHandler.executeSql(updateDefAddress, id);
	}
	
	String updateOtherUserAddress = "update user_address set isDefault = 0 where id != ?";
	@Override
	public int updateOtherUserAddress(Long id) {
		return jdbcHandler.executeSql(updateOtherUserAddress, id);
	}

}
