package com.imlianai.dollpub.app.modules.support.log.vo;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
public class LogReadModeReqVO extends BaseReqVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int mode;

	/**
	 * 章节ID
	 */
	private long chapterId;

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public long getChapterId() {
		return chapterId;
	}

	public void setChapterId(long chapterId) {
		this.chapterId = chapterId;
	}

}
