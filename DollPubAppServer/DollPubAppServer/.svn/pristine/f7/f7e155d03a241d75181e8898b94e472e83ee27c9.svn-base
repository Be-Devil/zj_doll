package com.imlianai.dollpub.app.modules.support.banner.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.imlianai.dollpub.domain.banner.BannerRes;
import com.imlianai.dollpub.domain.doll.DollRewardRecord;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel(value="轮播图列表返回")
public class BannerListRespVO extends BaseRespVO{
	
	@ApiModelProperty(value="轮播图信息")
	private List<BannerRes> banners;
	@ApiModelProperty(value="夹中娃娃通知")
	private List<DollRewardRecord> records;

	public List<BannerRes> getBanners() {
		return banners;
	}
	public void setBanners(List<BannerRes> banners) {
		this.banners = banners;
	}
	public List<DollRewardRecord> getRecords() {
		return records;
	}
	public void setRecords(List<DollRewardRecord> records) {
		this.records = records;
	}
	
}
