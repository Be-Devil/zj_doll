package com.imlianai.dollpub.app.modules.publics.qiniu.info;

public enum QiNiuTokenType {

	/**
	 * 用户头像
	 */
	HEAD(30, null),
	/**
	 * 图片消息
	 */
	MEDIA_IMAGE_MSG(102,null),
	/**
	 * 声音消息
	 */
	MEDIA_VOICE_MSG(101,null);

	public String keyword;

	public final int type;

	QiNiuTokenType(int type) {
		this.type = type;
	}

	private QiNiuTokenType(int type, String keyword) {
		this.type = type;
		this.keyword = keyword + "/";
	}

}
