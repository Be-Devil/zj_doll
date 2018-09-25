package com.imlianai.dollpub.app.modules.core.trade.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.doll.info.DollInfoService;
import com.imlianai.dollpub.app.modules.core.trade.catalog.service.ChargeCatalogService;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.core.trade.vo.CatalogListReqVO;
import com.imlianai.dollpub.app.modules.core.trade.vo.CatalogListRespVO;
import com.imlianai.dollpub.app.modules.core.trade.vo.ChargeGetChargeBillReqVO;
import com.imlianai.dollpub.app.modules.core.trade.vo.ChargeGetChargeBillRespVO;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.app.modules.core.user.domain.CustomerAuthToken;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.core.user.service.UserSrcService;
import com.imlianai.dollpub.app.modules.publics.log.service.LogService;
import com.imlianai.dollpub.app.modules.support.share.service.ShareService;
import com.imlianai.dollpub.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.dollpub.domain.doll.user.UserDoll;
import com.imlianai.dollpub.domain.trade.ChargeCatalog;
import com.imlianai.dollpub.domain.trade.TradeAccount;
import com.imlianai.dollpub.domain.trade.TradeCostType;
import com.imlianai.dollpub.domain.trade.TradeRecord;
import com.imlianai.dollpub.domain.trade.TradeType;
import com.imlianai.dollpub.domain.trade.ChargeCatalog.ChargeCatalogType;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserBase.UserSrcType;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.StringUtil;

/**
 * 
 * @author tensloveq
 */
@Component("chargesrc")
@Api("充值相关")
public class CmdHandleChargeSrc extends RootCmd {

	@Resource
	private LogService logService;

	@Resource
	ShareService shareService;

	@Resource
	private ChargeCatalogService chargeCatalogService;

	@Resource
	private TradeChargeService tradeChargeService;

	@Resource
	private TradeService tradeService;

	@Resource
	private UserService userService;

	@Resource
	private CustomerService customerService;
	
	@Resource
	private UserSrcService userSrcService;

	@Resource
	UserDollService userDollService;
	@Resource
	DollInfoService dollInfoService;
	
	@ApiHandle
	@Path("api/chargesrc/list")
	@ApiOperation(value = "【1.2.0】获取充值列表接口", notes = "在其他地方获取快捷充值列表需要传isQuickPay=1", httpMethod = "POST", response = CatalogListRespVO.class)
	public BaseRespVO list(CatalogListReqVO vo) {
		CatalogListRespVO respVo = new CatalogListRespVO();
		int chargeOsType = 0;
		long uid = vo.getUid();
		UserBase user = userService.getUserBase(uid);
		int customerId = vo.getCustomerId();
		if (user != null) {
			if(user.getSrcType()==UserSrcType.SRC_APP.type){
				customerId = (int)user.getAgentId().intValue();
			}
		}
		if (vo.getIsQuickPay() == 0) {
			// 普通充值列表
			List<ChargeCatalog> catalogs = chargeCatalogService.getCatalogs(
					chargeOsType, 0, customerId);
			respVo.setCatalogs(catalogs);

			// 首冲充值列表
			List<ChargeCatalog> firstCatalogs = chargeCatalogService
					.getFirstPayCatalogs(chargeOsType, vo.getUid(), customerId);
			respVo.setSpecialCatalogs(firstCatalogs);

		} else {
			// 普通充值列表
			List<ChargeCatalog> catalogs = chargeCatalogService
					.getQuickCatalogs(chargeOsType, customerId);
			respVo.setCatalogs(catalogs);
		}
		TradeAccount account = tradeService.getAccount(vo.getUid());
		if (account != null) {
			respVo.setCoin(account.getCoin());
		}
		respVo.setMsg("this uid is " + vo.getUid());
		return respVo;
	}
	
	@ApiHandle
	@Path("api/chargesrc/specialPromotion")
	@ApiOperation(value = "【1.2.0】获取充值列表接口", notes = "在其他地方获取快捷充值列表需要传isQuickPay=1", httpMethod = "POST", response = CatalogListRespVO.class)
	public BaseRespVO specialPromotion(CatalogListReqVO vo) {
		CatalogListRespVO respVo = new CatalogListRespVO();
		long uid = vo.getUid();
		UserBase user = userService.getUserBase(uid);
		int customerId = vo.getCustomerId();
		if (user != null) {
			customerId = user.getAgentId().intValue();
		}
		// 普通充值列表
		List<ChargeCatalog> catalogsDaily = chargeCatalogService.getSpecialPromotionSrc(ChargeCatalogType.DAILY_CHARGE.type, uid, customerId);
		List<ChargeCatalog> catalogsForever = chargeCatalogService.getSpecialPromotionSrc(ChargeCatalogType.FOREVER_CHARGE.type, uid, customerId);
		catalogsForever.addAll(catalogsDaily);
		respVo.setCatalogs(catalogsForever);
		TradeAccount account = tradeService.getAccount(vo.getUid());
		if (account != null) {
			respVo.setCoin(account.getCoin());
		}
		respVo.setMsg("this uid is " + vo.getUid());
		return respVo;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/chargesrc/order")
	@ApiOperation(value = "【1.0.0】获取代理H5支付订单接口", notes = "", httpMethod = "POST", response = ChargeGetChargeBillRespVO.class)
	public BaseRespVO order(ChargeGetChargeBillReqVO vo) {
		// 校验商品
		logger.info("orderH5Agent:" + JSON.toJSONString(vo));
		ChargeCatalog item = chargeCatalogService.getCatalog(vo.getCode());
		if (item != null) {
			UserBase user = userService.getUserBase(vo.getUid());
			logger.info("user:" + JSON.toJSONString(user));
			if (user != null&&user.getSrcType()==UserSrcType.SRC_APP.type && user.getAgentId() == item.getCustomerId()) {
				Customer customer=customerService.getCustomerById(user.getAgentId().intValue());
				String url=customer.getChargeUrl();
				CustomerAuthToken customerAuthToken =userSrcService.getCustomerAuthToken(vo.getUid());
				if (customerAuthToken==null) {
					return new BaseRespVO(0,false,"您的登录信息已过期");
				}
				if (item.getType()==ChargeCatalogType.DAILY_CHARGE.type) {
					int flag=tradeChargeService.hasChargeSrcToday(vo.getUid(), vo.getCode());
					if (flag>0) {
						return new BaseRespVO(301,false,"您已充值过该商品，不能再次充值");
					}
				}else if (item.getType()==ChargeCatalogType.FOREVER_CHARGE.type) {
					int flag=tradeChargeService.hasChargeSrc(vo.getUid(), vo.getCode());
					if (flag>0) {
						return new BaseRespVO(301,false,"您已充值过该商品，不能再次充值");
					}
				}
				Map<String, Object> postDate=new HashMap<String, Object>();
				postDate.put("userId", customerAuthToken.getSrcUid());
				postDate.put("token", customerAuthToken.getToken());
				postDate.put("cost", item.getPrice());
				postDate.put("action", "consume");
				HttpEntity httpEntity =HttpUtil.Post(url, JSON.toJSONString(postDate));
				if (httpEntity!=null) {
					if (!StringUtil.isNullOrEmpty(httpEntity.getHtml())) {
						logger.info("order src uid:"+vo.getUid()+" srcUid:"+ customerAuthToken.getSrcUid()+customerAuthToken.getToken()+" resp:"+httpEntity.getHtml());
						BaseRespVO resp=JSON.parseObject(httpEntity.getHtml(), BaseRespVO.class);
						if (resp.isState()) {
							if (item.getIsFirst() == 1) {
								int flag=tradeChargeService.hasChargeSrc(vo.getUid(), vo.getCode());
								if (flag>0) {
									item.setAwardExtra(0);
									item.setDollId(0);
								}
							}
							if (item.getType()==ChargeCatalogType.DAILY_CHARGE.type) {
								int flag=tradeChargeService.hasChargeSrcToday(vo.getUid(), vo.getCode());
								if (flag>0) {
									item.setAwardExtra(0);
									item.setDollId(0);
								}
							}
							if (item.getType()==ChargeCatalogType.FOREVER_CHARGE.type) {
								int flag=tradeChargeService.hasChargeSrc(vo.getUid(), vo.getCode());
								if (flag>0) {
									item.setAwardExtra(0);
									item.setDollId(0);
								}
							}
							
							StringBuffer tradeBuffer = new StringBuffer("充值" + item.getDes()
									+ "，获得" + item.getCoin() + "币");
							if (item.getAwardExtra() > 0) {
								tradeBuffer.append("，额外赠送");
								tradeBuffer.append(item.getAwardExtra());
								tradeBuffer.append("币");
							}
							TradeRecord record = new TradeRecord(vo.getUid(), vo.getUid(),
									TradeType.CHARGE_SRC.type, item.getCode(), item.getCoin()
											+ item.getAwardExtra(),
									TradeCostType.COST_COIN.type, tradeBuffer.toString());
							try {
								tradeService.charge(record);
								tradeChargeService.addTradeChargeSrc(vo.getUid(), item.getCode(), item.getPrice(), item.getCoin(), item.getCustomerId(),customerAuthToken.getSrcUid() );
								if (item.getDollId()>0) {
									DollInfo dollInfo = dollInfoService.getDollInfo(item.getDollId());
				                    if (dollInfo != null) {
				                    	 UserDoll userDoll = new UserDoll();
				                         userDoll.setUid(vo.getUid());
				                         userDoll.setDollId(item.getDollId());
				                         userDoll.setOptId(0);
				                         userDoll.setStatus(0);
				                         userDoll.setRemark("充值赠送");
				     					userDollService.saveUserDoll(userDoll);
				                    }
								}
							} catch (TradeOperationException e) {
							}
							return new BaseRespVO();
						}else{
							return resp;
						}
					}
				}
				return new BaseRespVO(0,false,"扣费失败");
			}
			return new BaseRespVO(0,false,"商户信息配置错误");
		} else {
			return new BaseRespVO(ResCodeEnum.PARA_ERROR);
		}
	}
}
