package com.imlianai.zjdoll.app.modules.core.trade.catalog.domain;

import java.util.Date;

import com.imlianai.rpc.support.common.BaseModel;

/**
 * 用户周月卡使用记录
 * @author cls
 *
 */
public class ChargeCardLog extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	/**
	 * 用户ID
	 */
	private Long uid;
	/**
	 * 开始日期
	 */
	private int startDate;
	/**
	 * 结束日期
	 */
	private int endDate;
	/**
	 * 类型(2:周卡，3:月卡)
	 */
	private int type;
	
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
