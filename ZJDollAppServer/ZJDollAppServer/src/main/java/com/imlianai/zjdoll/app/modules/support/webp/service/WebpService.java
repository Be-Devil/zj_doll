package com.imlianai.zjdoll.app.modules.support.webp.service;

import java.util.List;

import com.imlianai.zjdoll.domain.webp.Webp;

public interface WebpService {

	/**
	 * 获取萌主动画列表
	 * @return
	 */
	List<Webp> getWebpList();

}
