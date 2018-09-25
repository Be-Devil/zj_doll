package com.imlianai.zjdoll.app.modules.core.egg.vo;

import com.imlianai.zjdoll.domain.doll.DollInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class RewardInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("图片")
	private String pic;
	
	@ApiModelProperty("名称")
	private String name;
	
	public RewardInfo() {
		
	}
	
	public RewardInfo(DollInfo dollInfo) {
		this.pic = dollInfo.getImgSuccess();
		this.name = dollInfo.getName();
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
