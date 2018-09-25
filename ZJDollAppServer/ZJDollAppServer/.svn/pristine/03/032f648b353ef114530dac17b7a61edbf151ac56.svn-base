package com.imlianai.zjdoll.app.modules.core.user.attribute;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.user.UserAttribute;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.rpc.support.utils.StringUtil;

@Repository
public class UserAttributeDaoImpl implements UserAttributeDao {

	@Resource
	JdbcHandler jdbcHandler;

	String getUserAttribute = "select * from user_attribute where uid=?";

	@Override
	public UserAttribute getUserAttribute(Long uid) {
		return jdbcHandler.queryOneBySql(UserAttribute.class, getUserAttribute,
				uid);
	}

	@Override
	public void updateUserBan(List<Long> uids, String reason, String time) {
		if (StringUtil.isNullOrEmpty(uids)) {
			return;
		}
		StringBuilder sb = new StringBuilder(
				"update user_attribute set banReason=? ,banTime=? where uid in(");
		for (int i = 0, length = uids.size(); i < length; i++) {
			if (i != length - 1) {
				sb.append(uids.get(i)).append(",");
			} else {
				sb.append(uids.get(i)).append(")");
			}
		}
		String sql = sb.toString();
		jdbcHandler.executeSql(sql,reason,time);
	}

	@Override
	public void initUserAttribute(Long uid) {
		jdbcHandler.executeSql("insert into user_attribute(uid) value (?)", uid);
	}

	@Override
	public void removeUserBan(List<Long> uids) {
		if (StringUtil.isNullOrEmpty(uids)) {
			return;
		}
		StringBuilder sb = new StringBuilder(
				"update user_attribute set banReason=null ,banTime=null where uid in(");
		for (int i = 0, length = uids.size(); i < length; i++) {
			if (i != length - 1) {
				sb.append(uids.get(i)).append(",");
			} else {
				sb.append(uids.get(i)).append(")");
			}
		}
		String sql = sb.toString();
		jdbcHandler.executeSql(sql);
	}

	@Override
	public void incTotalCharge(long uid, int inc) {
		jdbcHandler.executeSql("update user_attribute set totalCharge=totalCharge+? where uid=?", inc,uid);
	}
	
	/**
	 * 限制提现(0:可提现 1:不可提现)
	 */
	@Override
	public void updateWithdraw(long uid,int redpacket) {
		jdbcHandler.executeSql("update user_attribute set redpacket=? where uid=? limit 1", redpacket,uid);
	}
}
