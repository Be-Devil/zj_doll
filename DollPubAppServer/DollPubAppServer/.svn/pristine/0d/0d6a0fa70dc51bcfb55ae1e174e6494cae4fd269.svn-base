package com.imlianai.dollpub.app.modules.support.pack.vo;

import com.imlianai.dollpub.app.modules.core.doll.vo.DollSuccessRes;
import com.imlianai.dollpub.domain.doll.DollDetails;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "娃娃详情返回对象")
public class GetDollDetailRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "娃娃详情")
	private DollDetails dollDetail;
	
	@ApiModelProperty(value = "发货须知")
	private String notice;
	
	@ApiModelProperty(value = "分享代码")
	private String shareCode = "share_game_record";
	
	private DollSuccessRes dollSucceRes;
	
	@ApiModelProperty(value = "虚拟商品申请发货跳转的h5地址")
	private String pageUrl;
	
	@ApiModelProperty(value = "h5标题")
	private String pageTitle;

	public DollDetails getDollDetail() {
		return dollDetail;
	}

	public void setDollDetail(DollDetails dollDetail) {
		this.dollDetail = dollDetail;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}

	public DollSuccessRes getDollSucceRes() {
		return dollSucceRes;
	}

	public void setDollSucceRes(DollSuccessRes dollSucceRes) {
		this.dollSucceRes = dollSucceRes;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
}
