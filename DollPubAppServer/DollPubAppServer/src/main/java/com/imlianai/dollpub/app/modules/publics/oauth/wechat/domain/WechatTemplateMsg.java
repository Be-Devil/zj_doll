package com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain;

import java.util.Map;

import com.imlianai.rpc.support.common.BaseModel;

public class WechatTemplateMsg extends BaseModel{

	private String touser;
	
	private String template_id;
	
	private String url;
	
	private Map<String, Object> data;

	public WechatTemplateMsg(){
		
	}
	
	public WechatTemplateMsg(String touser,String template_id,String url){
		this.touser=touser;
		this.template_id=template_id;
		this.url=url;
	}
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public enum TemplateMsgId {

		SIGN_REWARD("ghaWCI-sy2pp_YHo2p7Kycw9ua0-7yya2-3Pqox3uhA", "到账通知"),;

		public String id;
		public String des;

		private TemplateMsgId(String id, String des) {
			this.id = id;
			this.des = des;
		}

	}
}
