package com.imlianai.zjdoll.app.modules.support.log.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

public class LogReadTimeReqVO extends BaseReqVO {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 秒钟
	 */
	private Integer seconds;

	/**
	 * 章节ID
	 */
	private Integer chapterId;
	/**
	 * 阅读模式
	 */
	private Integer mode;

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}

	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

}
