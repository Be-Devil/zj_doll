package com.imlianai.dollpub.app.modules.core.trade.util.weixin;


public class WeixinPreOrderRes {

	
	private String appid;
	
	private String partnerid;
	private String prepayid;
	private String _package;
	private String noncestr;
	private String timestamp;
	private String sign;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	public String getPackage() {
		return _package;
	}
	public void setPackage(String _package) {
		this._package = _package;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public WeixinPreOrderRes(String appid, String partnerid, String prepayid,
			String _package, String noncestr, String timestamp, String sign) {
		super();
		this.appid = appid;
		this.partnerid = partnerid;
		this.prepayid = prepayid;
		this._package = _package;
		this.noncestr = noncestr;
		this.timestamp = timestamp;
		this.sign = sign;
	}
	
	
}
