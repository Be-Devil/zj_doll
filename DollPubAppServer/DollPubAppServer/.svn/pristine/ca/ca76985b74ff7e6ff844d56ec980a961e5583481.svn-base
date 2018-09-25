package com.imlianai.dollpub.app.modules.support.pack.domain;

import java.util.Date;

import com.imlianai.rpc.support.common.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="娃娃抓取记录")
public class GrabDollRecord extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 娃娃id
	 */
	@ApiModelProperty(value="娃娃id")
	private long dollId;
	
	/**
	 * 娃娃名称
	 */
	@ApiModelProperty(value="娃娃名称")
	private String dollName;
	
	/**
	 * 娃娃抓取时间
	 */
	@ApiModelProperty(value="娃娃抓取时间")
	private Date startTime;
	
	/**
	 * 抓取结果
	 */
	@ApiModelProperty(value="抓取结果：0失败，1成功")
	private int result;

	/**
	 * 封面图片
	 */
	@ApiModelProperty(value="娃娃封面图片")
	private String imgCover;
	
	public long getDollId() {
		return dollId;
	}

	public void setDollId(long dollId) {
		this.dollId = dollId;
	}

	public String getDollName() {
		return dollName;
	}

	public void setDollName(String dollName) {
		this.dollName = dollName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getImgCover() {
		return imgCover;
	}

	public void setImgCover(String imgCover) {
		this.imgCover = imgCover;
	}
	
	
}
