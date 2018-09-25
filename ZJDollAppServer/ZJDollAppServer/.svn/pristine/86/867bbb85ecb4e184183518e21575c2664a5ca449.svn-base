package com.imlianai.zjdoll.app.modules.support.share.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.share.ShareInfo;
import com.imlianai.zjdoll.app.modules.support.share.dao.ShareDao;

@Service
public class ShareServiceImpl implements ShareService {
	@Resource
	ShareDao shareDao;

	@Override
	public ShareInfo getShareInfo(String shareCode) {
		return shareDao.getShareInfo(shareCode);
	}

}
