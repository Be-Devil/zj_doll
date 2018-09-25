package com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain;

import com.imlianai.rpc.support.common.BaseModel;

public class WeiXinArticles extends BaseModel {

	public String Title;
	public String Description;
	public String PicUrl;
	public String Url;

	public WeiXinArticles() {
	}

	public WeiXinArticles(String Title, String Description, String PicUrl,
			String Url) {
		this.Title=Title;
		this.Description=Description;
		this.PicUrl=PicUrl;
		this.Url=Url;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

}
