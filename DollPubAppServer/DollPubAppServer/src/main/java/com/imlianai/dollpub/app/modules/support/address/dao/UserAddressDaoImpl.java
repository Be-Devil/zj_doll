package com.imlianai.dollpub.app.modules.support.address.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.doll.user.UserAddress;
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
	
	String getList = "select * from user_address where uid=? order by createTime desc";
	@Override
	public List<UserAddress> getList(Long uid) {
		return jdbcHandler.queryBySql(UserAddress.class, getList, uid);
	}
	
	String update = "update user_address set receiver=?,phone=?,province=?,city=?,district=?,fullAddress=?,updateTime=now(),isDefault=? where id=?";
	@Override
	public int update(UserAddress userAddress) {
		return jdbcHandler.executeSql(update, userAddress.getReceiver(), userAddress.getPhone(), userAddress.getProvince(),
				userAddress.getCity(), userAddress.getDistrict(), userAddress.getFullAddress(), userAddress.getIsDefault(), userAddress.getId());
	}
	
	String getDefAddress = "select * from user_address where uid=? order by createTime desc limit 1";
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
	
	String resetDefaultAddress = "update user_address set isDefault=0 where uid=? and isDefault=1";
	@Override
	public int resetDefaultAddress(Long uid) {
		return jdbcHandler.executeSql(resetDefaultAddress, uid);
	}
	
	String defAddress = "select * from user_address where uid=? and isDefault=? limit 1 ";
	@Override
	public UserAddress getDefAddress(Long uid, int isDefault) {
		return jdbcHandler.queryOneBySql(UserAddress.class, defAddress, uid, isDefault);
	}

}
