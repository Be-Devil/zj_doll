package com.imlianai.dollpub.app.modules.core.user.customer.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Resource
	JdbcHandler jdbcHandler;

	@Override
	public Customer getCustomer(String appId) {
		return jdbcHandler.queryOneBySql(Customer.class, "select * from customer where appId=?", appId);
	}

	@Override
	public Customer getCustomerById(int id) {
		return jdbcHandler.queryOneBySql(Customer.class, "select * from customer where id=?", id);
	}

	@Override
	public List<Customer> getCustomerByGroupId(int groupId) {
		return jdbcHandler.queryBySql(Customer.class,"select * from customer where groupId=?",groupId);
	}

	@Override
	public List<Customer> getAllCustomer() {
		return jdbcHandler.queryBySql(Customer.class,"select * from customer");
	}

}