package com.imlianai.zjdoll.app.modules.support.playground.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.imlianai.zjdoll.domain.banner.BannerRes;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public class PlaygroundIndexRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="banner列表")
	private List<BannerRes> banners;

	public List<BannerRes> getBanners() {
		return banners;
	}

	public void setBanners(List<BannerRes> banners) {
		this.banners = banners;
	}
}
