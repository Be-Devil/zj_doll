package com.imlianai.zjdoll.app.modules.core.doll.info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
@Repository
public class DollInfoDaoImpl implements DollInfoDao{

	@Resource
	private JdbcHandler jdbcHandler;
	
	@Override
	public DollInfo getDollInfo(int dollId) {
		return jdbcHandler.queryOneBySql(DollInfo.class, "select * from doll_info where dollId=? limit 1",dollId);
	}

	@Override
	public List<DollInfo> getAllDollInfos() {
		return jdbcHandler.queryBySql(DollInfo.class, "select * from doll_info ");
	}

	@Override
	public void updateDollGoodsId(int dollId, String goodsId) {
		jdbcHandler.executeSql("update doll_info set goodsId=? where dollId=?", goodsId,dollId);
		
	}

	@Override
	public int updateDollValidById(int dollId, int valid) {
		return jdbcHandler.executeSql("update doll_info set valid=? where dollId=?", valid, dollId);
	}

	@Override
	public int updateUserDollLastExchangeTime(int dollId) {
		return jdbcHandler.executeSql("update doll_info set lastExchangeTime=now() where dollId=?", dollId);
	}

	@Override
	public List<DollInfo> getRecentExchangeDollInfos(int size) {
		return jdbcHandler.queryBySql(DollInfo.class, "select * from doll_info where type=2 order by lastExchangeTime desc limit ?", size);
	}

	@Override
	public List<DollInfo> getRecentComposeDollInfos(int size) {
		return jdbcHandler.queryBySql(DollInfo.class, "select * from doll_info where type=1 order by lastComposeTime desc limit ?", size);
	}

	@Override
	public int updateUserDollLastComposeTime(int dollId) {
		return jdbcHandler.executeSql("update doll_info set lastComposeTime=now() where dollId=?", dollId);
	}

	@Override
	public int updateDollExchangeNum(int dollId) {
		return jdbcHandler.executeSql("update doll_info set exchangeNum=exchangeNum+1 where dollId=?", dollId);
	}

}
