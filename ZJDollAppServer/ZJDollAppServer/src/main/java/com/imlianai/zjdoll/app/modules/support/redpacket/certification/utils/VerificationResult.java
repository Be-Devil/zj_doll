package com.imlianai.zjdoll.app.modules.support.redpacket.certification.utils;

public class VerificationResult {

	private boolean state;
	
	private String msg;
	/**
	 * 芝麻订单号
	 */
	private String zmBillid;

	public VerificationResult(){}
	
	public VerificationResult(boolean state,String msg){
		this.state=state;
		this.msg=msg;
	}
	public VerificationResult(boolean state,String msg,String zmBillid){
		this.state=state;
		this.msg=msg;
		this.zmBillid=zmBillid;
	}
	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getZmBillid() {
		return zmBillid;
	}

	public void setZmBillid(String zmBillid) {
		this.zmBillid = zmBillid;
	}

	@Override
	public String toString() {
		return "VerificationResult [state=" + state + ", msg=" + msg + "]";
	}
	
	
}
