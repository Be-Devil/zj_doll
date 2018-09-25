package com.imlianai.zjdoll.app.modules.core.trade.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.trade.ChargeWay;
import com.imlianai.zjdoll.domain.trade.TradeCharge;
import com.imlianai.zjdoll.domain.trade.TradeChargeType;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.WebCmd;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog.ChargeCatalogType;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.service.ChargeCatalogService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.trade.util.BillIdUtil;
import com.imlianai.zjdoll.app.modules.core.trade.util.ali.AliPay;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.Rsa;
import com.imlianai.zjdoll.app.modules.core.trade.util.common.XmlUtil;
import com.imlianai.zjdoll.app.modules.core.trade.util.weixin.WeiXinPayUtil;
import com.imlianai.zjdoll.app.modules.core.trade.util.weixin.WeixinPreOrderRes;
import com.imlianai.zjdoll.app.modules.core.trade.util.weixin.config.WeiXinPayConfig;
import com.imlianai.zjdoll.app.modules.core.trade.vo.ChargeGetChargeBillReqVO;
import com.imlianai.zjdoll.app.modules.core.trade.vo.ChargeGetChargeBillRespVO;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;

/**
 * 
 * @author tensloveq
 */
@Component("chargeweb")
@Api("充值相关")
public class CmdHandleChargeWeb extends WebCmd {


	@Resource
	private ChargeCatalogService chargeCatalogService;

	@Resource
	private TradeChargeService tradeChargeService;

	@Resource
	private TradeService tradeService;

	@Resource
	private UserService userService;

	@ApiHandle
	public BaseRespVO getHoneyList(long uid,int itemCode){
		UserGeneral user=userService.getUserGeneral(uid);
		Map<String, Object> resMap=new HashMap<String, Object>();
		if (user!=null) {
			resMap.put("user", user);
		}
		List<ChargeCatalog> catalogs = chargeCatalogService.getCatalogs(
				ChargeCatalogType.PAY_4_OTHER.type, 0);
		if (!StringUtil.isNullOrEmpty(catalogs)) {
			resMap.put("catalogs", catalogs);
		}
		return new BaseRespVO(resMap);
	}

	@ApiHandle
	@Path("api/chargeweb/orderH5")
	@ApiOperation(value = "【1.0.0】获取支付订单接口", notes = "", httpMethod = "POST", response = ChargeGetChargeBillRespVO.class)
	public BaseRespVO orderH5(ChargeGetChargeBillReqVO vo) {
		// 校验商品
		ChargeCatalog catalog = chargeCatalogService.getCatalog(vo.getCode());
		if (catalog != null) {
			ChargeWay way=ChargeWay.WEXIN_JS;
			TradeCharge c = new TradeCharge(vo.getUid(), way, vo.getCode(), catalog.getPrice(),
					"wechat", 0, vo.getUnionId());
			c.setChargeType(TradeChargeType.PAY4ME.type);
			c.setRemark("亲密付uid:"+vo.getUid()+",openId:"+vo.getOpenId()+",unionId:"+vo.getUnionId());
			// 生成订单号
			long orderId = tradeChargeService.add(c);
			if (orderId <= 0) {
				return BaseRespVO.getBaseRespVO(ResCodeEnum.SYSTEM_ERROR);
			}
			String outId = BillIdUtil.getOutsideBillId(orderId);
			if (ChargeWay.WEXIN.type == vo.getChargeType()) {// 微信支付
				String xmlRes = WeiXinPayUtil.createOrderXml4JS(vo,
						vo.getOpenId(), vo.getUid(), outId, catalog,
						vo.getImei());
				logger.info("微信预订单请求 xmlRes:" + xmlRes);
				if (StringUtils.isBlank(xmlRes)) {
					return BaseRespVO.getBaseRespVO(ResCodeEnum.SYSTEM_ERROR);
				}
				HttpEntity resp = HttpUtil.Post(WeiXinPayConfig.unifiedOrder,
						xmlRes);
				logger.info("resp:" + resp.getHtml());
				try {
					Map<String, String> xmlMap = XmlUtil.doXMLParse(resp
							.getHtml());
					if (xmlMap == null
							|| !StringUtils
									.equals(xmlMap.get("return_code"), "SUCCESS")) {
						BaseRespVO respVo = BaseRespVO
								.getBaseRespVO(ResCodeEnum.SYSTEM_ERROR);
						respVo.setMsg(xmlMap.get("return_msg"));
						return respVo;
					}
					String resultCode = xmlMap.get("result_code");
					if (!StringUtils.equals(resultCode, "SUCCESS")) {
						BaseRespVO respVo = BaseRespVO
								.getBaseRespVO(ResCodeEnum.SYSTEM_ERROR);
						respVo.setMsg(xmlMap.get("err_code_des"));
						return respVo;
					}
					WeixinPreOrderRes wxRes = WeiXinPayUtil
							.createPrepayOrderInfo4JS(xmlMap);
					tradeChargeService.addLog(orderId, vo.getUid(), xmlRes,
							JSON.toJSONString(wxRes));
					ChargeGetChargeBillRespVO respVO = new ChargeGetChargeBillRespVO();
					respVO.setWxRes(wxRes);
					return respVO;
				} catch (Exception e) {
					logger.error("uid=" + vo.getUid() + ",parse xml error xml="
							+ xmlRes + ", msg=" + e.getMessage(), e);
				}
			} else if (ChargeWay.ALI.type == vo.getChargeType()) {
				ChargeGetChargeBillRespVO respVO = new ChargeGetChargeBillRespVO();
				String info;
				try {
					info = AliPay.getNewOrderInfo(catalog, outId);
					String sign = Rsa.sign(info, AliPay.privateKey);
					sign = URLEncoder.encode(sign, "UTF-8");
					info += "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\"";
					respVO.setAliRes(info);
					tradeChargeService.addLog(orderId, vo.getUid(), info, null);
				} catch (UnsupportedEncodingException e) {
					PrintException.printException(logger, e);
				}
				return respVO;
			} else {
				return new BaseRespVO(ResCodeEnum.PARA_ERROR);
			}
		} else {
			return new BaseRespVO(ResCodeEnum.PARA_ERROR);
		}
		return new BaseRespVO();
	}

}
