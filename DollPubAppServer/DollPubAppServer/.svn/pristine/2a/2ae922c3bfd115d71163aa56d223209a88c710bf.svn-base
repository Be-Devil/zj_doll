package com.imlianai.dollpub.app.modules.support.exmaple.domain;

import com.imlianai.rpc.support.common.BaseModel;

public class Relation extends BaseModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 关系id
	 */
	private long id;
	/**
	 * 用户id
	 */
	private long uid;
	/**
	 * 对方id
	 */
	private long tid;
	/**
	 * 答题状态
	 */
	private int state;
	/**
	 * 出题轮次
	 */
	private int round;

	public Relation() {
	}

	public Relation(long id, long uid, long tid) {
		this.id = id;
		this.uid = uid;
		this.tid = tid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public enum RelationState {

		MY_TURN_ANS(1, "我答题"),
		
		MY_TURN_ASK(2, "我出题"),
		
		HIS_TURN(3, "对方出题"), ;

		public int type;

		private RelationState(int type, String des) {
			this.type = type;
		}

	}
}
