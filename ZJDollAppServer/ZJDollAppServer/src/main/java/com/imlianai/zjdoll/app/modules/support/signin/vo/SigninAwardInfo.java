package com.imlianai.zjdoll.app.modules.support.signin.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "签到奖励对象信息")
public class SigninAwardInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "奖品编号")
	private long id;
	
	@ApiModelProperty(value = "奖品类型(1:红包,2:游戏币,3:普通娃娃布料,4:稀有娃娃布料)")
	private int type;
	
	@ApiModelProperty(value = "数量")
	private double num;
	
	@ApiModelProperty(value = "内容")
	private String content;
	
	@ApiModelProperty(value = "图标地址")
	private String url;
	
	@ApiModelProperty(value = "成功签到文案")
	private String succDesc;
	
	@ApiModelProperty(value = "成功签到说明")
	private String succRemark;
	
	public SigninAwardInfo(){
		
	}
	
	public SigninAwardInfo(long id, int type, double num, String content, String succDesc) {
		this.id = id;
		this.type = type;
		this.num = num;
		this.content = content;
		this.succDesc = succDesc;
	}

	public SigninAwardInfo(long id, int type, double num, String content, String url, String succDesc) {
		this.id = id;
		this.type = type;
		this.num = num;
		this.content = content;
		this.url = url;
		this.succDesc = succDesc;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSuccDesc() {
		return succDesc;
	}

	public void setSuccDesc(String succDesc) {
		this.succDesc = succDesc;
	}

	public String getSuccRemark() {
		return succRemark;
	}

	public void setSuccRemark(String succRemark) {
		this.succRemark = succRemark;
	}
}
