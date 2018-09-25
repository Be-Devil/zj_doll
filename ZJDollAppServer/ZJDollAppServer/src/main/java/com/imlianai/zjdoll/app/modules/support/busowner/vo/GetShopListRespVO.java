package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;

@ApiModel("萌店列表返回对象")
public class GetShopListRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	private List<ShopItem> shopItems;

	public List<ShopItem> getShopItems() {
		return shopItems;
	}

	public void setShopItems(List<ShopItem> shopItems) {
		this.shopItems = shopItems;
	}
}
