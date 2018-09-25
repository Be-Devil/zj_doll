package com.imlianai.zjdoll.app.modules.core.trade.util;

import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;

public class BillIdUtil {

	private static final String billPreFix = "ZJD";

	private static final BaseLogger logger = BaseLogger
			.getLogger(BillIdUtil.class);

	/**
	 * 获取对外商户订单号
	 * 
	 * @param id
	 * @return
	 */
	public static String getOutsideBillId(long id) {
		return billPreFix + id;
	}

	/**
	 * 对外商户订单号转换为内部订单号
	 * 
	 * @param outId
	 * @return
	 */
	public static long getInnerBillId(String outId) {
		long id = -1;
		try {
			String tmpString = outId.replaceAll(billPreFix, "");
			id = Long.parseLong(tmpString);
		} catch (Exception e) {
			logger.error("getInnerBillId outId:" + outId);
			PrintException.printException(logger, e);
		}
		return id;
	}
}
