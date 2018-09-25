package com.imlianai.zjdoll.app.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.DollOrderInfo;
import com.imlianai.zjdoll.domain.doll.DollOrderRecord;
import com.imlianai.zjdoll.domain.doll.DollBus.DollBusCompany;
import com.imlianai.zjdoll.domain.doll.DollOrderRecord.OrderStatus;
import com.imlianai.zjdoll.domain.doll.user.UserAddress;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.doll.service.DollService;
import com.imlianai.zjdoll.app.modules.core.doll.utils.DollUtil;
import com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo.QiyiguoGoods;
import com.imlianai.zjdoll.app.modules.support.address.service.UserAddressService;
import com.imlianai.zjdoll.app.modules.support.shipping.service.ShippingService;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;

@Component
public class DollShippingTask {

	@Resource
	UserAddressService userAddressService;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	DollService dollService;
	@Resource
	ShippingService shippingService;
	@Resource
	UserDollService userDollService;

	BaseLogger logger = BaseLogger.getLoggerSwitch(this.getClass());

	public void handleDollShippingInvalid() {
		shippingService.handleShippingInvalid();
	}
	/**
	 * 定时检查已通过审核的订单以发货
	 */
	public void handleDollShipping() {
//		List<DollOrderRecord> list = shippingService.getDollOrderRecords(60);
//		if (!StringUtil.isNullOrEmpty(list)) {
//			for (DollOrderRecord dollOrderRecord : list) {
//				try {
//					if (dollOrderRecord != null) {
//						logger.info("当前准备发货订单号："
//								+ dollOrderRecord.getOrderNum() + " uid:"
//								+ dollOrderRecord.getUid() + " auditId："
//								+ dollOrderRecord.getAuditId() + " 状态："
//								+ dollOrderRecord.getStatus() + " remark:"
//								+ dollOrderRecord.getRemark());
//						List<DollOrderInfo> infos = shippingService
//								.getDollOrderInfos(dollOrderRecord
//										.getOrderNum());
//						if (!StringUtil.isNullOrEmpty(infos)) {
//							Map<Long, QiyiguoGoods> goodsMap = new HashMap<Long, QiyiguoGoods>();
//							// 商品归类汇总
//							for (DollOrderInfo dollOrderInfo : infos) {
//								if(dollOrderInfo.getCompany()==DollBusCompany.QIYIGUO.type){
//									DollInfo dollInfo = dollInfoService
//											.getDollInfo(dollOrderInfo.getDollId());
//									if (dollInfo!=null&&dollInfo.getGoodsType()==0) {//实物
//										long goodId = Long.parseLong(dollInfo
//												.getGoodsId());
//										QiyiguoGoods goods = goodsMap.get(goodId);
//										if (goods != null) {
//											goods.setNum(goods.getNum() + 1);
//										} else {
//											goods = new QiyiguoGoods(goodId, 1);
//										}
//										goodsMap.put(goodId, goods);
//									}else{
//										logger.info("当前准备发货订单号："
//												+ dollOrderRecord.getOrderNum() + " uid:"
//												+ dollOrderRecord.getUid() + " 存在奇艺果非实物订单，不需发货，dollOrderInfo id:"+dollOrderInfo.getId()+" uDollId:"+dollOrderInfo.getuDollId());
//									}
//								}else {
//									logger.info("当前准备发货订单号："
//											+ dollOrderRecord.getOrderNum() + " uid:"
//											+ dollOrderRecord.getUid() + " 存在非奇艺果订单，不需发货，dollOrderInfo id:"+dollOrderInfo.getId()+" uDollId:"+dollOrderInfo.getuDollId());
//								}
//							}
//							// 发货
//							if (!StringUtil.isNullOrEmpty(goodsMap)) {
//								List<QiyiguoGoods> goodsList = new ArrayList<QiyiguoGoods>();
//								for (Long goodsId : goodsMap.keySet()) {
//									QiyiguoGoods goods = goodsMap.get(goodsId);
//									goodsList.add(goods);
//								}
//								if (!StringUtil.isNullOrEmpty(goodsList)) {
//									// 默认地址与自定义地址
//									UserAddress adress = null;
//									if (dollOrderRecord.getNewAddressId() == 0) {
//										adress = userAddressService
//												.getAddressByAddrId(dollOrderRecord
//														.getAddressId());
//
//									} else {
//										adress = userAddressService
//												.getCustomAddressByAddrId(dollOrderRecord
//														.getNewAddressId());
//									}
//									logger.info("当前准备发货订单号："
//											+ dollOrderRecord.getOrderNum() + " uid:"
//											+ dollOrderRecord.getUid() + " adress："
//											+ JSON.toJSONString(adress) + " finalAddress："
//											+ adress
//											.getShippingAddr());
//									if (adress != null) {
//										String finalAddress = adress
//												.getShippingAddr();
//										if (!StringUtil
//												.isNullOrEmpty(finalAddress)) {
//											logger.info("完整发货信息 发货订单号："
//													+ dollOrderRecord
//															.getOrderNum()
//													+ " uid:"
//													+ dollOrderRecord.getUid()
//													+ " 地址:"
//													+ finalAddress
//													+ " 号码:"
//													+ adress.getPhone()
//													+ " 收件人:"
//													+ adress.getReceiver()
//													+ " 货物清单:"
//													+ JSON.toJSONString(goodsList));
//											Map<String, Object> res = DollUtil
//													.createOrder(
//															dollOrderRecord
//																	.getUid(),
//															adress.getReceiver(),
//															finalAddress,
//															adress.getPhone(),
//															adress.getReceiver(),
//															goodsList);
//											String resp = JSON
//													.toJSONString(res);
//											logger.info("完整发货信息 发货订单号："
//													+ dollOrderRecord
//															.getOrderNum()
//													+ " uid:"
//													+ dollOrderRecord.getUid()
//													+ " 发货结果:" + resp);
//											shippingService
//													.addDollOrderRecordTaskLog(
//															dollOrderRecord
//																	.getOrderNum(),
//															dollOrderRecord
//																	.getUid(),
//															"发货订单号："
//																	+ dollOrderRecord
//																			.getOrderNum()
//																	+ " uid:"
//																	+ dollOrderRecord
//																			.getUid()
//																	+ " 地址:"
//																	+ finalAddress
//																	+ " 号码:"
//																	+ adress.getPhone()
//																	+ " 收件人:"
//																	+ adress.getReceiver()
//																	+ " 货物清单:"
//																	+ JSON.toJSONString(goodsList),
//															resp);
//											if (DollUtil.checkDone(resp)) {
//												long shippingId=DollUtil.getShippingOrderId(resp);
//												shippingService
//														.initDollOrderExpress(
//																dollOrderRecord
//																		.getOrderNum(),
//																dollOrderRecord
//																		.getUid(),
//																		shippingId);
//												shippingService.updateDollOrderRecordStatus(dollOrderRecord
//														.getOrderNum(), OrderStatus.SHIPPING_SUBMIT.status);
//											} else {
//												shippingService.updateDollOrderRecordReason(dollOrderRecord
//														.getOrderNum(), DollUtil.getRespMsg(resp));
//											}
//
//										}
//
//									}
//								}
//							} else {
//								logger.info("不存在可发货的商品 订单号："
//										+ dollOrderRecord.getOrderNum()
//										+ " uid:" + dollOrderRecord.getUid()
//										+ " auditId："
//										+ dollOrderRecord.getAuditId() + " 状态："
//										+ dollOrderRecord.getStatus()
//										+ " remark:"
//										+ dollOrderRecord.getRemark());
//							}
//						}
//					}
//				} catch (Exception e) {
//					logger.info("发货报错 订单号："
//							+ dollOrderRecord.getOrderNum()
//							+ " uid:" + dollOrderRecord.getUid()
//							+ " auditId："
//							+ dollOrderRecord.getAuditId() + " 状态："
//							+ dollOrderRecord.getStatus()
//							+ " remark:"
//							+ dollOrderRecord.getRemark());
//					logger.error("发货报错 订单号："
//							+ dollOrderRecord.getOrderNum()
//							+ " uid:" + dollOrderRecord.getUid()
//							+ " auditId："
//							+ dollOrderRecord.getAuditId() + " 状态："
//							+ dollOrderRecord.getStatus()
//							+ " remark:"
//							+ dollOrderRecord.getRemark());
//					PrintException.printException(logger, e);
//				}
//			}
//		}
	}
}
