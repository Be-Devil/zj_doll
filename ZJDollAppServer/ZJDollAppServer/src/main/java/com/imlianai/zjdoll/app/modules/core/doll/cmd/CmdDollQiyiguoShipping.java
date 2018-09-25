package com.imlianai.zjdoll.app.modules.core.doll.cmd;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.DollOrderExpress;
import com.imlianai.zjdoll.domain.doll.DollOrderInfo;
import com.imlianai.zjdoll.domain.doll.DollBus.DollBusCompany;
import com.imlianai.zjdoll.domain.doll.DollOrderRecord.OrderStatus;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.core.doll.service.DollService;
import com.imlianai.zjdoll.app.modules.support.shipping.service.ShippingService;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;

@Component("qygshipping")
public class CmdDollQiyiguoShipping extends RootCmd {

	@Resource
	DollRecordService dollRecordService;
	@Resource
	DollService dollService;
	@Resource
	ShippingService shippingService;
	@Resource
	UserDollService userDollService;

	@Override
	public String doCommand(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("qygshipping:" + request.getQueryString());
		shippingService.addShippingCallbackLog(request.getQueryString());
		String orderIdStr = request.getParameter("order_id");
		long orderId = Long.parseLong(orderIdStr);
		DollOrderExpress dollOrderExpress = shippingService
				.getDollOrderExpress(orderId);
		if (dollOrderExpress != null) {
			String shipping_no = request.getParameter("shipping_no");
			String shipping_com = request.getParameter("shipping_com");
			String shipping_memo = request.getParameter("shipping_memo")!=null?request.getParameter("shipping_memo"):"";
			String mode = request.getParameter("mode")==null?"":request.getParameter("mode");
			String shipping_name = request.getParameter("shipping_name");
			if (!StringUtil.isNullOrEmpty(shipping_name)) {
				shipping_name = new String(shipping_name.getBytes("iso-8859-1"),"UTF-8");
			}
			logger.info("CmdDollQiyiguoShipping 更新订单发货状态，订单号："+ dollOrderExpress.getOrderNum()+" 快递单号："+shipping_no+" shipping_name:"+shipping_name);
			shippingService.updateDollOrderExpress(
					dollOrderExpress.getOrderNum(), shipping_no, shipping_com,
					shipping_name, mode + shipping_memo);
			shippingService.updateDollOrderRecordStatus(
					dollOrderExpress.getOrderNum(),
					OrderStatus.SHIPPING_COMMIT.status);
			List<DollOrderInfo> infos = shippingService
					.getDollOrderInfos(dollOrderExpress.getOrderNum());
			if (!StringUtil.isNullOrEmpty(infos)) {
				for (DollOrderInfo dollOrderInfo : infos) {
					if (dollOrderInfo!=null&&(dollOrderInfo.getCompany()==DollBusCompany.QIYIGUO.type)) {
						UserDoll userDoll =userDollService.getUserDollById(dollOrderInfo.getuDollId());
						if (userDoll!=null) {
							logger.info("CmdDollQiyiguoShipping 更新订单发货状态，订单号："+ dollOrderExpress.getOrderNum()+" userDollId:"+userDoll.getId()+" 快递单号："+shipping_no);
							userDollService.updateUserDollShipping(userDoll.getId(), shipping_name, shipping_no, 2);
						}else {
							logger.info("CmdDollQiyiguoShipping 更新订单发货状态，订单号："+ dollOrderExpress.getOrderNum()+" 没有对应userDollId 快递单号："+shipping_no);
						}
					}
				}
			}
		}
		return responseJson(response, "ok", "响应请求");
	}

	public static void main(String[] args) {
		Map<String, Object> postMap = new HashMap<String, Object>();
		postMap.put("live", 1);
		HttpUtil.Post(
				"http://122.11.48.251:6639/DollAppServer/api/qiyiguo/back",
				JSON.toJSONString(postMap));
	}
}
