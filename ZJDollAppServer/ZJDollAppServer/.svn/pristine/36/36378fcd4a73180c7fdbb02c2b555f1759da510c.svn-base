package com.imlianai.zjdoll.app.modules.core.trade.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog;

public class CatalogListRespVO extends BaseRespVO {

	@ApiModelProperty("商品清单")
	private List<ChargeCatalog> catalogs;
	@ApiModelProperty("特殊优惠清单")
	private List<ChargeCatalog> specialCatalogs;
	@ApiModelProperty("余额")
	private int coin;
	@ApiModelProperty("是否审核中")
	private boolean isApplePay=false;

	public List<ChargeCatalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(List<ChargeCatalog> catalogs) {
		this.catalogs = catalogs;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public List<ChargeCatalog> getSpecialCatalogs() {
		return specialCatalogs;
	}

	public void setSpecialCatalogs(List<ChargeCatalog> specialCatalogs) {
		this.specialCatalogs = specialCatalogs;
	}

	public boolean isApplePay() {
		return isApplePay;
	}

	public void setApplePay(boolean isApplePay) {
		this.isApplePay = isApplePay;
	}

}
