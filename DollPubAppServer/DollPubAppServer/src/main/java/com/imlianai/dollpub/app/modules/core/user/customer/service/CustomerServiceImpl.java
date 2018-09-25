package com.imlianai.dollpub.app.modules.core.user.customer.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.core.user.customer.dao.CustomerDao;
import com.imlianai.dollpub.constants.DollCacheConst;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerDao customerDao;

	@Override
	//@CacheWrite(key = DollCacheConst.CUSTOMER_APP_ID, pkField = { "appId" }, validTime = 60)
	public Customer getCustomer(String appId) {
		return customerDao.getCustomer(appId);
	}

	@Override
	public Customer getCustomerById(int id) {
		return customerDao.getCustomerById(id);
	}

	@Override
	public List<Customer> getCustomerByGroupId(int groupId) {
		return customerDao.getCustomerByGroupId(groupId);
	}

	@Override
	public List<Customer> getAllCustomer() {
		return customerDao.getAllCustomer();
	}

}
