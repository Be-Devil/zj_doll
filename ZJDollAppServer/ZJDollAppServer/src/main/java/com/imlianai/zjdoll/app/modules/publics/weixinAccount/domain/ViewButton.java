package com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain;

public class ViewButton implements Button {

	private String type;
	private String name;
	private String url;
	
	
	
	
	public ViewButton(String name, String url) {		
		this.type = "view";
		this.name = name;
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ViewButton() {
		super();
	}
	
	

}
