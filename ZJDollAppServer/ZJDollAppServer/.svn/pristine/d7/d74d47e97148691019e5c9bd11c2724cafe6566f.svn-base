package com.imlianai.zjdoll.app.modules.core.trade.callback;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.trade.ChargeWay;
import com.imlianai.zjdoll.domain.trade.TradeCharge;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.common.json.Jackson;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.trade.callback.service.PayBackExcuteService;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.service.ChargeCatalogService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.HttpConnection;
import com.imlianai.zjdoll.app.modules.core.trade.vo.ApplePayBackReqVO;
import com.imlianai.zjdoll.app.modules.core.trade.vo.ChargeGetChargeBillRespVO;
import com.imlianai.zjdoll.app.modules.publics.log.service.LogService;

@Component("applePay")
@Api("苹果支付")
public class ApplePayBack extends RootCmd {
	private static final Logger logger = Logger.getLogger("payLog");
	@Resource
	private TradeChargeService tradeChargeService;
	@Resource
	private ChargeCatalogService chargeCatalogService;
	@Resource
	private PayBackExcuteService payBackExcuteService;
	@Resource
	private LogService logService;

	public static void main(String[] args) {
		String codeStr = "DH1001";
		codeStr = StringUtils.replaceEach(codeStr, new String[] { "DH", "MS" },
				new String[] { "", "" });
		System.out.println(codeStr);
	}

	@SuppressWarnings("unchecked")
	@ApiHandle
	@Path("api/applePay/payBack")
	@ApiOperation(value = "【1.0.0】获取支付订单接口", notes = "", httpMethod = "POST", response = ChargeGetChargeBillRespVO.class)
	public BaseRespVO payBack(ApplePayBackReqVO vo) {
		Map<String, Object> result = new HashMap<String, Object>();
		long uid = vo.getUid();
		logger.info("【苹果支付】 uid:" + uid + " [初始参数]" + vo);
		String receiptData = vo.getTransactionReceipt();
		String crc32 = AppUtils.getCRC32(receiptData);
		Map<String, String> param = new HashMap<String, String>();
		param.put("receipt-data", receiptData);
		String paramStr = Jackson.objDateToJson(param);
		String url = "https://buy.itunes.apple.com/verifyReceipt";
		String resultStr = null;
		try {
			resultStr = HttpConnection.doPost(url, paramStr);
		} catch (Exception e) {
			logger.info("CRC32=>" + crc32 + "e=>" + e.getMessage(), e);
			logger.error("CRC32=>" + crc32 + "e=>" + e.getMessage(), e);
		}
		Map<String, Object> resultMap = Jackson.jsonToObj(resultStr, Map.class);
		int status = (Integer) resultMap.get("status");
		if (status == 0) {// 线上
			try {
				Map<String, Object> receiptMap = (Map<String, Object>) resultMap
						.get("receipt");
				List<Object> inAppList = (List<Object>) receiptMap
						.get("in_app");
				handleChargeBill(inAppList, vo, resultStr);
			} catch (Exception e) {
				logger.info("【苹果支付】失败: uid:" + uid + " jsonMap=" + vo
						+ ",resultStr=" + resultStr);
				logger.error("【苹果支付】失败: uid:" + uid + " jsonMap=" + vo
						+ ",resultStr=" + resultStr);
				PrintException.printException(logger, e);
				result.put("state", false);
				result.put("msg", "失败:原因[" + e.getMessage() + "]");
			}
			return BaseRespVO.getBaseRespVO(ResCodeEnum.SUCCESS);
		} else if (status == 21007) {// 沙盒
			int successBill = 0;
			try {
				String urlTest = "https://sandbox.itunes.apple.com/verifyReceipt";
				resultStr = HttpConnection.doPost(urlTest, paramStr);
				logger.info("【苹果支付】 uid:" + uid + " [验证结果:沙盒]" + resultStr);
				resultMap = Jackson.jsonToObj(resultStr, Map.class);
				status = (Integer) resultMap.get("status");
				Map<String, Object> receiptMap = (Map<String, Object>) resultMap
						.get("receipt");
				List<Object> inAppList = (List<Object>) receiptMap
						.get("in_app");
				successBill = handleChargeSandboxBill(inAppList, vo, resultStr);
				BaseRespVO respVo = BaseRespVO
						.getBaseRespVO(ResCodeEnum.SUCCESS);
				respVo.setMsg(crc32);
				tradeChargeService.addLog(System.currentTimeMillis() / 1000,
						uid, AppUtils.getCRC32(receiptData) + ":" + successBill
								+ ":dev", receiptData);
				logger.info("CRC32=>" + crc32 + ",successBill=>" + successBill
						+ ",res=>" + JSON.toJSONString(respVo));
				return respVo;
			} catch (Exception e) {
				logger.info("CRC32=>" + crc32 + ",e=>" + e.getMessage()
						+ ",result=>" + JSON.toJSONString(result));
				PrintException.printException(logger, e);
				result.put("state", false);
				result.put("msg", "失败:原因[" + e.getMessage() + "]");
			}
			logger.info("CRC32=>" + crc32 + ",successBill=>" + successBill);
			return BaseRespVO.getBaseRespVO(ResCodeEnum.SUCCESS);
		} else {
			logger.info("【苹果支付】 uid:" + uid + " [交易失败,返回未知的状态]:jsonMap=" + vo
					+ ",resultStr=" + resultStr);
			result.put("state", false);
			result.put("msg", "交易失败:未知的交易结果");
			tradeChargeService
					.addLog(System.currentTimeMillis(), uid,
							AppUtils.getCRC32(receiptData) + ":" + status
									+ ":devError", receiptData);
			logger.info("CRC32=>" + crc32 + ",status=>" + status + ",result=>"
					+ JSON.toJSONString(result));
			return new BaseRespVO(result);
		}
	}

	/**
	 * 处理订单列表
	 * 
	 * @param uid
	 * @param channel
	 * @param imei
	 * @param inAppList
	 * @param jsonMap
	 * @param requestResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int handleChargeBill(List<Object> inAppList, ApplePayBackReqVO vo,
			String requestResult) {
		int successBill = 0;
		if (inAppList != null && !inAppList.isEmpty()) {
			int billNum = 0;
			int existBill = 0;
			for (Object object : inAppList) {
				billNum++;
				Map<String, Object> inAppMap = (Map<String, Object>) object;
				logger.info("【苹果支付】 uid:" + vo.getUid() + " 第" + billNum
						+ "笔订单 [验证结果:线上-in_app]" + inAppMap);
				// 订单时间
				String purchaseDate = String.valueOf(inAppMap
						.get("original_purchase_date_ms"));
				if (purchaseDate.compareTo("1488265087569") < 0) {// 2017年02月28日14点58分前不再计算
					continue;
				}
				// 订单唯一标识
				String tradeId = String.valueOf(inAppMap.get("transaction_id"));
				// 产品标识
				String storeCode = String.valueOf(inAppMap.get("product_id"));
				TradeCharge charge = tradeChargeService.getByOtherId(tradeId,
						ChargeWay.APPLE);
				if (charge != null) {// 已经完成充值的订单则跳过
					existBill++;
					continue;
				}
				// 商品信息
				int code = Integer.parseInt(storeCode);
				ChargeCatalog catalog = chargeCatalogService
						.getCatalog(code);
				if (catalog != null) {
					charge = new TradeCharge(vo.getUid(), ChargeWay.APPLE, code, catalog.getPrice(),
							vo.getChannel(), vo.getOsType(), vo.getImei());
					charge.setOtherId(tradeId);
					long id = tradeChargeService.add(charge);
					if (id > 0) {//订单插入成功才继续后续操作，不成功则需要将返回map置为false
						tradeChargeService.addLog(id, vo.getUid(), vo.getTransactionReceipt(), requestResult);
						payBackExcuteService.commonExcute(charge);
						successBill++;
						continue;
					}
				}
			}
			logger.info("【苹果支付】 uid:" + vo.getUid() + " 共" + billNum
					+ "笔订单，成功处理" + successBill + "笔，已经存在的订单" + existBill
					+ "笔，requestResult" + requestResult);
		}
		return successBill;
	}

	/**
	 * 处理沙盒订单列表
	 * 
	 * @param uid
	 * @param channel
	 * @param imei
	 * @param inAppList
	 * @param jsonMap
	 * @param requestResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int handleChargeSandboxBill(List<Object> inAppList,
			ApplePayBackReqVO vo, String requestResult) throws Exception {
		int successBill = 0;
		if (inAppList != null && !inAppList.isEmpty()) {
			int billNum = 0;
			int existBill = 0;
			for (Object object : inAppList) {
				billNum++;
				Map<String, Object> inAppMap = (Map<String, Object>) object;
				logger.info("【苹果支付】 uid:" + vo.getUid() + " [验证结果:沙盒-in_app]"
						+ inAppMap);
				String tradeId = String.valueOf(inAppMap.get("transaction_id"));
				// 产品标识
				String storeCode = String.valueOf(inAppMap.get("product_id"));
				TradeCharge charge = tradeChargeService.getTempByOtherId(
						tradeId, ChargeWay.APPLE);
				if (charge != null) {
					existBill++;
					continue;
				}
				int code = Integer.parseInt(storeCode);
				ChargeCatalog catalog = chargeCatalogService
						.getCatalog(code);
				if (catalog != null) {
					charge = new TradeCharge(vo.getUid(), ChargeWay.APPLE, code, catalog.getPrice(),
							vo.getChannel(), vo.getOsType(), vo.getImei());
					charge.setOtherId(tradeId);
					long id = tradeChargeService.add(charge);
					if (id > 0) {//订单插入成功才继续后续操作，不成功则需要将返回map置为false
						tradeChargeService.addLog(id, vo.getUid(), vo.getTransactionReceipt(), requestResult);
						payBackExcuteService.commonExcute(charge);
						successBill++;
						continue;
					}
				}
			}
			logger.info("【苹果支付】 uid:" + vo.getUid() + " 共" + billNum
					+ "笔订单，成功处理" + successBill + "笔，已经存在的订单" + existBill
					+ "笔，requestResult" + requestResult);
		}
		return successBill;
	}

}
