package com.imlianai.dollpub.app.modules.publics.qiniu.pili;

import io.swagger.annotations.ApiModelProperty;

public class SaveRet {
	@ApiModelProperty("文件名")
	private String fname;
	@ApiModelProperty("错误原因")
	private String error;
	
	private String persistentID;

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPersistentID() {
		return persistentID;
	}

	public void setPersistentID(String persistentID) {
		this.persistentID = persistentID;
	}
	
	
}
