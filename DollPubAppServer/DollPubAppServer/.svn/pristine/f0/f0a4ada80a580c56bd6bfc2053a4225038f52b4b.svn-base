package com.imlianai.dollpub.app.modules.support.exmaple.domain;


import com.imlianai.rpc.support.common.BaseModel;

/**
 * 出题
 * @author Max
 *
 */
public class Guess extends BaseModel{

	private static final long serialVersionUID = 1L;
	/**
	 * 出题id
	 */
	private long id;
	/**
	 * 关系id
	 */
	private long relaionId;
	/**
	 * 出题轮次
	 */
	private int round;
	/**
	 * 出题者id
	 */
	private long uid;
	/**
	 * 答题者id
	 */
	private long tid;
	/**
	 * 问题id
	 */
	private long questionId;
	/**
	 * 正确选项
	 */
	private int option;
	/**
	 * 回答
	 */
	private int answer;
	
	public Guess(){
	}

	public Guess(Relation relation, long questionId, int option){
		this.relaionId = relation.getId();
		this.round = relation.getRound();
		this.uid = relation.getUid();
		this.tid = relation.getTid();
		this.questionId = questionId;
		this.option = option;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRelaionId() {
		return relaionId;
	}
	public void setRelaionId(long relaionId) {
		this.relaionId = relaionId;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public long getTid() {
		return tid;
	}
	public void setTid(long tid) {
		this.tid = tid;
	}
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public int getOption() {
		return option;
	}
	public void setOption(int option) {
		this.option = option;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
}
