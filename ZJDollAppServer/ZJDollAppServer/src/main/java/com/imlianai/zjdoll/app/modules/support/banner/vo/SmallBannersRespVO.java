package com.imlianai.zjdoll.app.modules.support.banner.vo;

import java.util.List;

import com.imlianai.zjdoll.domain.banner.BannerRes;
import com.imlianai.rpc.support.common.BaseModel;

import io.swagger.annotations.ApiModelProperty;

public class SmallBannersRespVO extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="小banner列表")
	private List<BannerRes> banners;

	public List<BannerRes> getBanners() {
		return banners;
	}

	public void setBanners(List<BannerRes> banners) {
		this.banners = banners;
	}
}
