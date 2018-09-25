package com.imlianai.dollpub.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.imlianai.dollpub.domain.banner.BannerRes;
import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.dollpub.domain.doll.DollRewardRecord;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel("娃娃机详情返回对象")
public class DollInfoRespVO extends BaseRespVO {

	@ApiModelProperty("最近抓中记录")
	private List<DollRewardRecord> records;
	@ApiModelProperty("滚动banner")
	private List<BannerRes> banners;
	@ApiModelProperty("娃娃信息")
	private DollInfo dollInfo;
	public List<DollRewardRecord> getRecords() {
		return records;
	}
	public void setRecords(List<DollRewardRecord> records) {
		this.records = records;
	}
	public List<BannerRes> getBanners() {
		return banners;
	}
	public void setBanners(List<BannerRes> banners) {
		this.banners = banners;
	}
	public DollInfo getDollInfo() {
		return dollInfo;
	}
	public void setDollInfo(DollInfo dollInfo) {
		this.dollInfo = dollInfo;
	}
	
}
