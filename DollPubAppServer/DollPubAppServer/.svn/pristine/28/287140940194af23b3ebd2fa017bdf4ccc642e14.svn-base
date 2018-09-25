package com.imlianai.dollpub.app.modules.publics.qiniu.vo;

import io.swagger.annotations.ApiModel;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel(value = "七牛token信息返回")
public class QiniuTokenRespVO extends BaseRespVO {

	private String uptoken;
	private String bucketName;
	private String key;
	private String fileName;

	public QiniuTokenRespVO() {
	}

	public QiniuTokenRespVO(String uptoken, String bucketName, String key,
			String fileName) {
		super();
		this.uptoken = uptoken;
		this.bucketName = bucketName;
		this.key = key;
		this.fileName = fileName;
	}

	public String getUptoken() {
		return uptoken;
	}

	public void setUptoken(String uptoken) {
		this.uptoken = uptoken;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
