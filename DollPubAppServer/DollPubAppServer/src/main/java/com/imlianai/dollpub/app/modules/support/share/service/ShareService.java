package com.imlianai.dollpub.app.modules.support.share.service;

import com.imlianai.dollpub.domain.share.ShareInfo;

public interface ShareService {

	/**
	 * 获取分享信息
	 * @param shareCode
	 * @return
	 */
	public ShareInfo getShareInfo(String shareCode);
}
