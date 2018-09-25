package com.imlianai.dollpub.app.modules.support.share.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.support.share.dao.ShareDao;
import com.imlianai.dollpub.domain.share.ShareInfo;

@Service
public class ShareServiceImpl implements ShareService {
	@Resource
	ShareDao shareDao;

	@Override
	public ShareInfo getShareInfo(String shareCode) {
		return shareDao.getShareInfo(shareCode);
	}

}
