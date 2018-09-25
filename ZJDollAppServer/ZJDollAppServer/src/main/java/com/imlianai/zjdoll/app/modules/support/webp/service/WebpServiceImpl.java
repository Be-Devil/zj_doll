package com.imlianai.zjdoll.app.modules.support.webp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.webp.Webp;
import com.imlianai.zjdoll.app.modules.support.webp.dao.WebpDao;

@Service
public class WebpServiceImpl implements WebpService {

	@Resource
	WebpDao webpDao;
	
	@Override
	public List<Webp> getWebpList() {
		return webpDao.getWebpList();
	}

}
