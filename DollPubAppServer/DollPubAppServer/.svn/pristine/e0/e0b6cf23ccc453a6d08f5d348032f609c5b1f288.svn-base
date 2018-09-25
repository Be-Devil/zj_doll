package com.imlianai.dollpub.app.modules.support.exmaple.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.app.modules.support.exmaple.domain.Guess;
import com.imlianai.dollpub.app.modules.support.exmaple.router.GuessRouter;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class GuessDAOImpl implements GuessDAO {

	@Resource
	private JdbcHandler jdbcHandler;
	@Resource
	private GuessRouter guessRouter;

	@Override
	public void createTable(long relationId) {
		guessRouter.getTableName(relationId);
	}

	private String addGuess = "insert into %s (relaionId,`round`,uid,tid,questionId,`option`,answer,time) "
			+ "value (?,?,?,?,?,?,-1,now())";
	public int addGuess(Guess guess){
		String sql = String.format(addGuess, guessRouter.getTableName(guess.getRelaionId()));
		int id = jdbcHandler.executeSql(sql, guess.getRelaionId(), guess.getRound(), 
				guess.getUid(), guess.getTid(), guess.getQuestionId(), guess.getOption());
		guess.setId(id);
		return id;
	}
	
	private String updateGuess = "update %s set answer=? where id=? and answer=-1 ";
	@Override
	public int updateGuess(long id, long relationId, int answer) {
		String sql = String.format(updateGuess, guessRouter.getTableName(relationId));
		return jdbcHandler.executeSql(sql, answer, id);
	}
	
	private String getGuess = "select * from %s where id=? ";
	@Override
	public Guess getGuess(long id, long relationId) {
		String sql = String.format(getGuess, guessRouter.getTableName(relationId));
		return jdbcHandler.queryOneBySql(Guess.class, sql, id);
	}
	
	private String getGuessIds = "select questionId from %s where relaionId=? and `round`=? ";
	@Override
	public List<Long> getGuessIds(long relationId, int round) {
		String sql = String.format(getGuessIds, guessRouter.getTableName(relationId));
		return jdbcHandler.queryBySql(Long.class, sql, relationId, round);
	}
	
	private String getGuesses = "select * from %s where relaionId=? and `round`=? ";
	@Override
	public List<Guess> getGuesses(long relationId, int round) {
		String sql = String.format(getGuesses, guessRouter.getTableName(relationId));
		return jdbcHandler.queryBySql(Guess.class, sql, relationId, round);
	}
}
