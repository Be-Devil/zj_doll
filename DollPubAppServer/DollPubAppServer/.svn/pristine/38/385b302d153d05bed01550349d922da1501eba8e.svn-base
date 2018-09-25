package com.imlianai.dollpub.app.modules.support.exmaple.dao;

import java.util.List;

import com.imlianai.dollpub.app.modules.support.exmaple.domain.Guess;

/**
 * 出题相关
 * @author Max
 *
 */
public interface GuessDAO {

	/**
	 * 创建关系表
	 * @param relationId
	 * @return
	 */
	public void createTable(long relationId);
	/**
	 * 新增出题
	 * @param guess
	 * @return
	 */
	public int addGuess(Guess guess);
	/**
	 * 出题回答
	 * @param id
	 * @param relationId
	 * @param answer
	 * @return
	 */
	public int updateGuess(long id, long relationId, int answer);
	/**
	 * 获取出题
	 * @param id
	 * @param relationId
	 * @return
	 */
	public Guess getGuess(long id, long relationId);
	/**
	 * 获取用户指定轮题目id列表
	 * @param relationId
	 * @param round
	 * @return
	 */
	public List<Long> getGuessIds(long relationId, int round);
	/**
	 * 获取用户指定轮列表
	 * @param relationId
	 * @param round
	 * @return
	 */
	public List<Guess> getGuesses(long relationId, int round);
}
