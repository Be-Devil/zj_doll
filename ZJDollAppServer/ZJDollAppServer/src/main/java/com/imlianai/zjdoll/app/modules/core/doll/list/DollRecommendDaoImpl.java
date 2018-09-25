package com.imlianai.zjdoll.app.modules.core.doll.list;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class DollRecommendDaoImpl implements DollRecommendDao {

	@Resource
	JdbcHandler jdbcHandler;

	@Override
	public List<DollBus> getBusList() {
		return null;
	}

	@Override
	public List<String> getSearchWord(int size) {
		return jdbcHandler.queryBySql(String.class,
				"select word from search_key_word where valid=1 order by sortIndex asc limit ?", size);
	}

	@Override
	public void addHotRank(int busId, int value) {
		jdbcHandler
				.executeSql(
						"insert into doll_recommend_hot (busId,dayCol,value,time)"
								+ " value (?,DATE_FORMAT(now(),'%Y-%m-%d'),?,now()) on duplicate key update value=value+?,time=now()",
						busId, value, value);
	}

	@Override
	public List<Integer> getHotRank() {
		return jdbcHandler
				.queryBySql(
						Integer.class,
						"select busId from doll_recommend_hot where dayCol=DATE_FORMAT(now(),'%Y-%m-%d') order by value desc ");
	}
}
