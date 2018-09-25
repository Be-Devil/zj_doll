package com.imlianai.zjdoll.app.modules.core.doll.list;

import java.util.List;

import com.imlianai.zjdoll.domain.doll.DollBus;

public interface DollRecommendDao {

	/**
	 * 获取推荐的娃娃机
	 * @return
	 */
	public List<DollBus> getBusList();
	
	/**
	 * 获取推荐搜索词
	 * @return
	 */
	public List<String> getSearchWord(int size);
	
	/**
	 * 增加热捉值
	 * @param busId
	 */
	public void addHotRank(int busId,int value);

	/**
	 * 获取热门捉取排名
	 * @return
	 */
	public List<Integer> getHotRank();
}
