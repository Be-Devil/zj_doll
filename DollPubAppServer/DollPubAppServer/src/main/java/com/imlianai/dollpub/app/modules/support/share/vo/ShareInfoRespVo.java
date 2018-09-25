package com.imlianai.dollpub.app.modules.support.share.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.domain.share.ShareInfo;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public class ShareInfoRespVo extends BaseRespVO {

	@ApiModelProperty("分享代码")
	private String shareCode;

	@ApiModelProperty(value = "分享标题")
	private String title;
	@ApiModelProperty(value = "分享描述")
	private String description;
	@ApiModelProperty(value = "分享连接")
	private String link;
	@ApiModelProperty(value = "分享图片")
	private String img;

	public ShareInfoRespVo() {
	}

	public ShareInfoRespVo(ShareInfo shareInfo) {
		this.shareCode=shareInfo.getCode();
		this.title=shareInfo.getTitle();
		this.description=shareInfo.getDescription();
		this.img=shareInfo.getImg();
		this.link=shareInfo.getLink();
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setShareInfo(ShareInfo info) {
		this.title = info.getTitle();
		this.description = info.getDescription();
		this.link = info.getLink();
		this.img = info.getImg();
	}

}
