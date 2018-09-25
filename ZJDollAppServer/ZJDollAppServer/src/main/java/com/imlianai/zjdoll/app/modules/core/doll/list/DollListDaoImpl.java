package com.imlianai.zjdoll.app.modules.core.doll.list;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.DollClassify;
import com.imlianai.zjdoll.domain.doll.DollClassifyRes;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class DollListDaoImpl implements DollListDao {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(DollListDaoImpl.class);
	
	@Resource
	private JdbcHandler jdbcHandler;

	String getDollListByPage = "select * from user_doll where uid=?";

	@Override
	public List<UserDoll> getDollListByPage(Long uid, long lastId,
			int pageSize, int status) {
		StringBuffer sb = new StringBuffer();
		sb.append(getDollListByPage);
		if (lastId == 0) {
			sb.append(" and id>?");
		} else {
			sb.append(" and id<?");
		}
		if (status == -1) { // 全部
			sb.append(" and status>=?");
			status = 0;
		} else {
			if (status == 8) { // 成功申请发货、已发货、已拒绝
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
		LOG.info("getDollListByPage:uid-" + uid + ",sql-" + sb.toString());
		return jdbcHandler.queryBySql(UserDoll.class, sb.toString(), uid,
				lastId, status, pageSize);
	}

	@Override
	public List<DollClassifyRes> getDollClassifies() {
		return jdbcHandler.queryBySql(DollClassifyRes.class,
				"select * from doll_classify where valid=1 and endTime>now() and startTime<now() order by `sortIndex` asc");
	}

	@Override
	public List<Integer> getBusIdsByClassify(int classify) {
		DollClassify res = jdbcHandler.queryOneBySql(DollClassify.class,
				"select * from doll_classify where type=? and valid=1 and endTime>now() and startTime<now() limit 1", classify);
		if (res != null) {
			String busIds = res.getBusIds();
			if (busIds != null) {
				try {
					List<Integer> busIdArrIntegers = JSON.parseArray(busIds,
							Integer.class);
					return busIdArrIntegers;
				} catch (Exception e) {

				}
			}
		}
		return null;
	}


	@Override
	public List<DollClassify> getAllClassify() {
		List<DollClassify> res = jdbcHandler.queryBySql(DollClassify.class,
				"select * from doll_classify where valid=1 and endTime>now() and startTime<now() ");
		return res;
	}

	String getDollListByPageAfter130 = "select * from user_doll where uid=?";
	@Override
	public List<UserDoll> getDollListByPageAfter130(Long uid, long lastId, int pageSize, int status) {
		StringBuffer sb = new StringBuffer();
		sb.append(getDollListByPage);
		if (lastId == 0) {
			sb.append(" and id>?");
		} else {
			sb.append(" and id<?");
		}
		if (status == -1) { // 全部
			sb.append(" and status>=?");
			status = 0;
		} else {
			if (status == 8) { // 成功申请发货、已发货、已拒绝
				sb.append(" and (status=? or status=2 or status=4)");
				status = 1;
			} else if(status == 3){ 
				//sb.append(" and (status=? or status=5 or status=1 or status=2 or status=4)"); // 已兑换、已回收、成功申请发货、已发货、已拒绝
				sb.append(" and (status=? or status=5)"); // 已兑换、已回收
			} else {
				sb.append(" and status=?");
			}
		}
		sb.append(" order by createTime desc");
		sb.append(" limit ?");
		LOG.info("getDollListByPage:uid-" + uid + ",sql-" + sb.toString());
		return jdbcHandler.queryBySql(UserDoll.class, sb.toString(), uid,
				lastId, status, pageSize);
	}
}
