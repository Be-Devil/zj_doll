package com.imlianai.zjdoll.app.modules.core.egg.vo;


import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class RechargeItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("价格")
	private String priceDesc;
	
	@ApiModelProperty("时光劵数量")
	private int num;
	
	@ApiModelProperty("编码")
	private int code;
	
	public RechargeItem() {
		
	}

	public RechargeItem(ChargeCatalog chargeCatalog) {
		this.priceDesc = chargeCatalog.getPrice() + "元";
		this.num = chargeCatalog.getTimeCoupons();
		this.code = chargeCatalog.getCode();
	}

	public String getPriceDesc() {
		return priceDesc;
	}

	public void setPriceDesc(String priceDesc) {
		this.priceDesc = priceDesc;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
