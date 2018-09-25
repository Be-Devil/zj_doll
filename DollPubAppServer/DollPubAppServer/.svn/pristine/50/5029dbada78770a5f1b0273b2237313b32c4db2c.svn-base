package com.imlianai.dollpub.app.modules.core.trade.catalog.dao;

import java.sql.SQLException;
import java.util.List;

import com.imlianai.dollpub.domain.trade.ChargeCatalog;

public interface ChargeCatalogDao {

	/**
	 * 获取所有产品列表
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<ChargeCatalog> getCatalogs(int type,int isFirst,int customerId);
	
	/**
	 * 获取快速充值列表
	 * @param type
	 * @param tag
	 * @param isFirst
	 * @return
	 */
	List<ChargeCatalog> getCatalogs(int type,int tag,int isFirst,int customerId); 

	/**
	 * 获取指定类型的某个商品
	 * 
	 * @param code
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	ChargeCatalog getCatalog(int code);
	
	/**
	 * 根据渠道号获取充值初始code
	 * @param channel
	 * @return
	 */
	public int getChargeCodeByChannel(String channel);

}
