package com.imlianai.dollpub.app.modules.core.doll.list;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.doll.user.UserDoll;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class DollListDaoImpl implements DollListDao{
	@Resource
	private JdbcHandler jdbcHandler;

	
	String getDollListByPage = "select * from user_doll where uid=?";
	@Override
	public List<UserDoll> getDollListByPage(Long uid, long lastId, int pageSize, int status) {
		StringBuffer sb = new StringBuffer();
		sb.append(getDollListByPage);
		if(lastId == 0) {
			sb.append(" and id>?");
		} else {
			sb.append(" and id<?");
		}
		if(status == -1) { // 全部
			sb.append(" and status>=?");
			status = 0;
		} else {
			if(status == 8) { // 成功申请发货、已发货、已拒绝
				sb.append(" and (status=? or status=2 or status=4)");
				status = 1;
			} else if(status == 3){ // 已兑换，已回收
				sb.append(" and (status=? or status=5)");
			} else {
				sb.append(" and status=?");
			}
		}
		sb.append(" order by createTime desc");
		sb.append(" limit ?");
		return jdbcHandler.queryBySql(UserDoll.class, sb.toString(), uid, lastId, status, pageSize);
	}

}
