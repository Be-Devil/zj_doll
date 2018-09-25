package com.imlianai.zjdoll.app.modules.support.address.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.doll.user.UserAddress;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.support.address.dao.UserAddressDao;
import com.imlianai.zjdoll.app.modules.support.address.vo.UserAddressAddReqVO;
import com.imlianai.zjdoll.app.modules.support.address.vo.UserAddressInfo;

@Service
public class UserAddressServiceImpl implements UserAddressService {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(UserAddressServiceImpl.class);
	
	@Resource
	UserAddressDao userAddressDao;

	@Override
	public BaseRespVO add(UserAddressAddReqVO reqVO) {
		try {
			String numberStr = reqVO.getPhone() + "";
			if(!verifyPhone(numberStr)) {
				return new BaseRespVO(-1, false, "该手机号码不正确");
			}
			UserAddress userAddress = new UserAddress(reqVO.getUid(), reqVO.getReceiver(), reqVO.getPhone(), 
					reqVO.getProvince(), reqVO.getCity(), reqVO.getDistrict(), reqVO.getFullAddress(), reqVO.getIsDefault());
			userAddressDao.add(userAddress);
		} catch (Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统异常，请稍后再试~");
		}
		return new BaseRespVO();
	}

	private boolean verifyPhone(String numberStr) {
		Pattern p = Pattern.compile("^[1][0-9]{10}$");
		Matcher m = p.matcher(numberStr);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	@Override
	public List<UserAddressInfo> getList(Long uid) {
		List<UserAddressInfo> addressInfos = new ArrayList<UserAddressInfo>();
		List<UserAddress> addressList = userAddressDao.getList(uid); // 用户收货地址列表
		if(!StringUtil.isNullOrEmpty(addressList)) {
			for(UserAddress userAddress : addressList) {
				UserAddressInfo addressInfo = new UserAddressInfo();
				addressInfo.setId(userAddress.getId());
				addressInfo.setReceiver(userAddress.getReceiver());
				addressInfo.setPhone(userAddress.getPhone());
				addressInfo.setProvince(userAddress.getProvince());
				addressInfo.setCity(userAddress.getCity());
				addressInfo.setDistrict(userAddress.getDistrict());
				addressInfo.setFullAddress(userAddress.getFullAddress());
				addressInfos.add(addressInfo);
			}
		}
		return addressInfos;
	}

	@Override
	public BaseRespVO update(UserAddressAddReqVO reqVO) {
		String numberStr = reqVO.getPhone() + "";
		if(!verifyPhone(numberStr)) {
			return new BaseRespVO(-1, false, "该手机号码不正确");
		}
		UserAddress userAddress = new UserAddress(reqVO.getUid(), reqVO.getReceiver(), reqVO.getPhone(), 
				reqVO.getProvince(), reqVO.getCity(), reqVO.getDistrict(), reqVO.getFullAddress(), reqVO.getIsDefault());
		userAddress.setId(reqVO.getId());
		userAddressDao.update(userAddress);
		return new BaseRespVO();
	}

	@Override
	public UserAddressInfo getDefAddress(Long uid) {
		UserAddressInfo addressInfo = null;
		UserAddress userAddress = userAddressDao.getDefAddress(uid);
		if(userAddress != null) {
			addressInfo = new UserAddressInfo(userAddress.getId(), userAddress.getReceiver(), userAddress.getPhone(), 
					userAddress.getProvince(), userAddress.getCity(), userAddress.getDistrict(), userAddress.getFullAddress());
		}
		return addressInfo;
	}

	@Override
	public UserAddress getAddressByAddrId(long id) {
		return userAddressDao.getAddressByAddrId(id);
	}

	@Override
	public UserAddress getCustomAddressByAddrId(long id) {
		return userAddressDao.getCustomAddressByAddrId(id);
	}

	@Override
	public BaseRespVO updateDefAddress(Long id) {
		try {
			UserAddress userAddress = userAddressDao.getAddressByAddrId(id);
			if(userAddress == null) {
				return new BaseRespVO(-1, false, "收货地址不存在~");
			}
			if(userAddressDao.updateDefAddress(id) > 0) {
				userAddressDao.updateOtherUserAddress(id);
				return new BaseRespVO();
			} else {
				return new BaseRespVO(-1, false, "网络异常，请稍后再试~");
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统异常，请稍后再试~");
		}
	}
}
