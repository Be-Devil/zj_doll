package com.imlianai.zjdoll.app.modules.support.shipping.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.constants.JumpTarget;
import com.imlianai.zjdoll.domain.doll.BaseDollInfo;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.DollOrderExpress;
import com.imlianai.zjdoll.domain.doll.DollOrderInfo;
import com.imlianai.zjdoll.domain.doll.DollOrderRecord;
import com.imlianai.zjdoll.domain.doll.DollOrderRecord.OrderPayStatus;
import com.imlianai.zjdoll.domain.doll.DollOrderRecordBill;
import com.imlianai.zjdoll.domain.doll.user.UserAddress;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.zjdoll.domain.doll.user.UserDoll.DollStatus;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.shipping.DollShippingNotice;
import com.imlianai.zjdoll.domain.trade.TradeCharge;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.NotEnoughBeanException;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.address.service.UserAddressService;
import com.imlianai.zjdoll.app.modules.support.pack.constants.PackConstants;
import com.imlianai.zjdoll.app.modules.support.shipping.constants.ShippingConstants;
import com.imlianai.zjdoll.app.modules.support.shipping.dao.ShippingDao;
import com.imlianai.zjdoll.app.modules.support.shipping.vo.ApplyDollInfo;
import com.imlianai.zjdoll.app.modules.support.shipping.vo.ApplyShippingRespVO;
import com.imlianai.zjdoll.app.modules.support.shipping.vo.PhonebindRespVO;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;

@Service
public class ShippingServiceImpl implements ShippingService {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(ShippingServiceImpl.class);

	@Resource
	UserDollService userDollService;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	ShippingDao shippingDao;
	@Resource
	TradeService tradeService;
	@Resource
	TradeChargeService tradeChargeService;
	@Resource
	UserService userService;
	
	@Resource
	UserAddressService userAddressService;
	@Resource
	MsgService msgService;
	@Override
	public List<BaseDollInfo> getShippingList(Long uid, Long uDollId) {
		LOG.info("getShippingList:uid-" + uid + ",uDollId-" + uDollId);
		List<BaseDollInfo> dollInfoList = new ArrayList<BaseDollInfo>();
		List<UserDoll> dollList = userDollService.getShippingList(uid);
		if(!StringUtil.isNullOrEmpty(dollList)) {
			for(UserDoll userDoll : dollList) {
				DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
				if(dollInfo == null || dollInfo.getGoodsType() == 1) continue;
				BaseDollInfo baseDollInfo = new BaseDollInfo(userDoll.getId(), dollInfo.getName(), dollInfo.getImgSummry(), userDoll.getOptId());
				baseDollInfo.setSpecType(dollInfo.getSpecType());
				dollInfoList.add(baseDollInfo);
			}
		}
		// 默认把某个用户娃娃放在列表的第一位
		if(uDollId != null && uDollId.longValue() > 0 
				&& !StringUtil.isNullOrEmpty(dollInfoList)) {
			for(int i = 0; i < dollInfoList.size(); i++) {
				if(dollInfoList.get(i).getId().longValue() == uDollId.longValue() && i > 0) {
					BaseDollInfo doolInfoTemp = dollInfoList.get(0);
					dollInfoList.add(0, dollInfoList.get(i));
					dollInfoList.add(i, doolInfoTemp);
					dollInfoList.remove(1);
					dollInfoList.remove(i+1);
				}
			}
		}
		return dollInfoList;
	}

	@Override
	public BaseRespVO applyShipping(List<ApplyDollInfo> dollList, Long uid, Long addressId, String remark) {
		try {
			LOG.info("applyShipping:" + JSON.toJSONString(dollList) + ",uid=" + uid + ",addressId=" + addressId);
			UserBase userBase = userService.getUserBase(uid);
			if(userBase == null || (userBase != null && userBase.getNumber() == 0)) { // 验证手机绑定
				return new BaseRespVO(ResCodeEnum.PHONE_BIND);
			}
			if(StringUtil.isNullOrEmpty(dollList)) {
				return new BaseRespVO(-1, false, "发货的娃娃列表不能为空~");
			}
			UserAddress userAddress = userAddressService.getAddressByAddrId(addressId);
			if(userAddress == null || userAddress.getUid().longValue() != uid) {
				return new BaseRespVO(-1, false, "收货地址不存在，请刷新重试~");
			}
			List<Long> uDollIds = new ArrayList<Long>(); // 申请发货的用户娃娃ID
			/*for(ApplyDollInfo dollInfo : dollList) {
				List<UserDoll> userDolls = userDollService.getUserDollByParams(uid, dollInfo.getDollId(), dollInfo.getNum());
				if(userDolls == null || userDolls.size() < dollInfo.getNum()) {
					return new BaseRespVO(-1, false, "娃娃库存量不足，请刷新重试~");
				}
				for(UserDoll userDoll : userDolls) {
					uDollIds.add(userDoll.getId());
				}
			}*/
			Map<Long, UserDoll> userDollMap = new HashMap<Long, UserDoll>();
			List<ApplyDollInfo> inDollInfos = new ArrayList<ApplyDollInfo>(); // 允许单独发货的娃娃数量
			for(ApplyDollInfo applyDollInfo : dollList) {
				UserDoll userDoll = userDollService.getUserDollById(applyDollInfo.getId());
				if(userDoll == null) {
					return new BaseRespVO(-1, false, "部分娃娃不存在，请刷新重试~");
				}
				if(userDoll.getStatus() != DollStatus.STORE.status) {
					return new BaseRespVO(-1, false, "部分娃娃不满足发货条件，请刷新重试~");
				}
				uDollIds.add(applyDollInfo.getId());
				userDollMap.put(applyDollInfo.getId(), userDoll);
				// 获取可单独发货的娃娃
				DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId()); 
				if(dollInfo.getGoodsType() == 0 && dollInfo.getSpecType() == 0) {
					inDollInfos.add(applyDollInfo);
				}
			}
			if(inDollInfos.size() == 0) {
				return new BaseRespVO(-1, false, "没有可发货的娃娃~");
			}
			int postage = getPostage(dollList, uid); // 获取邮费
			boolean isPay = false;
			if(postage == 0) {
				isPay = true;
			} else {
				if (true) {
					return new BaseRespVO(-1, false, "清更新到最新版本继续申请发货");
				}
				TradeRecord tradeRecord = new TradeRecord(uid, 0, TradeType.SHIPPING_CONSUME.type, 0, 
						postage, TradeCostType.COST_COIN.type, "申请发货消费游戏币" + postage);
				isPay = tradeService.consume(tradeRecord);
			}
			if(isPay) {
				long orderNum = 0;
				for(int i = 1; i <= 3; i++) { // 订单号重复时重试次数
					orderNum = generateOrderNum(); // 订单号
					int result = shippingDao.saveDollOrderRecord(orderNum, uid, addressId, remark,OrderPayStatus.PAYED.status,0);
					if(result > 0) break;
					if(result <= 0 && i == 3) {
						return new BaseRespVO(-1, false, "生成订单失败，请重试~");
					}
				}
				if(shippingDao.saveShippingRecord(uid, orderNum) > 0) { // 保存娃娃发货记录
					for(Long uDollId : uDollIds) {
						UserDoll userDoll = userDollMap.get(uDollId);
						DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
						shippingDao.saveDollOrderInfo(uid, uDollId, orderNum, userDoll.getDollId(), userDoll.getOptId(), dollInfo.getCompany()); // 娃娃订单信息
						userDollService.updateUserDollStatus(uDollId, DollStatus.APPLY_SUCC.status); // 修改用户娃娃状态
					}
				}
				ApplyShippingRespVO respVO = new ApplyShippingRespVO();
				respVO.setCoin(tradeService.getAccount(uid).getCoin());
				respVO.setOrderNum(orderNum);
				return respVO;
			} else {
				return new ApplyShippingRespVO(-1, false, "交易失败,请重试"); 
			}
		} catch (TradeOperationException e) {
			return new ApplyShippingRespVO(-1, false, "交易失败,请重试"); 
		} catch (NotEnoughBeanException e) {
			return new ApplyShippingRespVO(600, false, "余额不足"); 
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统异常，请稍后再试~");
		}
	}
	
	@Override
	public BaseRespVO applyShippingBill(List<ApplyDollInfo> dollList, Long uid, Long addressId, String remark) {
		try {
			LOG.info("applyShipping:" + JSON.toJSONString(dollList) + ",uid=" + uid + ",addressId=" + addressId);
			UserBase userBase = userService.getUserBase(uid);
			if(userBase == null || (userBase != null && userBase.getNumber() == 0)) { // 验证手机绑定
				return new BaseRespVO(ResCodeEnum.PHONE_BIND);
			}
			if(StringUtil.isNullOrEmpty(dollList)) {
				return new BaseRespVO(-1, false, "发货的娃娃列表不能为空~");
			}
			LOG.info("applyShipping:next");
			List<Long> uDollIds = new ArrayList<Long>(); // 申请发货的用户娃娃ID
			Map<Long, UserDoll> userDollMap = new HashMap<Long, UserDoll>();
			List<ApplyDollInfo> inDollInfos = new ArrayList<ApplyDollInfo>(); // 允许单独发货的娃娃数量
			for(ApplyDollInfo applyDollInfo : dollList) {
				UserDoll userDoll = userDollService.getUserDollById(applyDollInfo.getId());
				if(userDoll == null) {
					return new BaseRespVO(-1, false, "部分娃娃不存在，请刷新重试~");
				}
				if(userDoll.getStatus() != DollStatus.STORE.status) {
					return new BaseRespVO(-1, false, "部分娃娃不满足发货条件，请刷新重试~");
				}
				uDollIds.add(applyDollInfo.getId());
				userDollMap.put(applyDollInfo.getId(), userDoll);
				// 获取可单独发货的娃娃
				DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId()); 
				if(dollInfo.getGoodsType() == 0 && dollInfo.getSpecType() == 0) {
					inDollInfos.add(applyDollInfo);
				}
			}
			if(inDollInfos.size() == 0) {
				return new BaseRespVO(-1, false, "没有可发货的娃娃~");
			}
			int postage = getPostageRmb(dollList, uid); // 获取邮费
			boolean needPay = true;
			if(postage == 0) {//无需油费，直接已支付状态
				needPay = false;
			} else {//需要油费，跳转待支付
				
			}
				long orderNum = 0;
				for(int i = 1; i <= 3; i++) { // 订单号重复时重试次数
					orderNum = generateOrderNum(); // 订单号
					int result = shippingDao.saveDollOrderRecord(orderNum, uid, addressId, remark,needPay?OrderPayStatus.INIT.status:OrderPayStatus.PAYED.status,postage);
					if(result > 0) break;
					if(result <= 0 && i == 3) {
						return new BaseRespVO(-1, false, "生成订单失败，请重试~");
					}
				}
				if(shippingDao.saveShippingRecord(uid, orderNum) > 0) { // 保存娃娃发货记录
					for(Long uDollId : uDollIds) {
						UserDoll userDoll = userDollMap.get(uDollId);
						DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
						shippingDao.saveDollOrderInfo(uid, uDollId, orderNum, userDoll.getDollId(), userDoll.getOptId(), dollInfo.getCompany()); // 娃娃订单信息
						userDollService.updateUserDollStatus(uDollId, DollStatus.APPLY_SUCC.status); // 修改用户娃娃状态
					}
				}
				ApplyShippingRespVO respVO = new ApplyShippingRespVO();
				respVO.setCoin(tradeService.getAccount(uid).getCoin());
				respVO.setOrderNum(orderNum);
				respVO.setNeedPay(needPay);
				return respVO;
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统异常，请稍后再试~");
		}
	}
	
	
	private long generateOrderNum() {
		String currDateStr = DateUtils.getCurrentDateString("yyyyMMddHHmmss");
		String randomNum = new Long(new Random().nextInt(900)+100).toString();
		return Long.parseLong((currDateStr + randomNum));
	}

	@Override
	public int getPostage(List<ApplyDollInfo> dollList, Long uid) {
		/*if(shippingDao.getShippingTimes(uid) == 0 
				|| dollList.size() >= ShippingConstants.FREE_SHIPPING_SIZE) { // 首次发货or超过指定免邮的娃娃个数*/
		//LOG.info("getPostage-dollList:" + JSON.toJSONString(dollList));
		List<ApplyDollInfo> inDollInfos = new ArrayList<ApplyDollInfo>(); // 允许单独发货的娃娃数量
		if(!StringUtil.isNullOrEmpty(dollList)) {
			for(ApplyDollInfo doll : dollList) {
				UserDoll userDoll = userDollService.getUserDollById(doll.getId());
				if(userDoll != null) {
					DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId()); 
					if(dollInfo.getGoodsType() == 0 && dollInfo.getSpecType() == 0) {
						inDollInfos.add(doll);
					}
				}
			}
		}
		//LOG.info("getPostage-size:" + inDollInfos.size() + ",inDollInfos:" + JSON.toJSONString(inDollInfos));
		if(inDollInfos.size() >= ShippingConstants.FREE_SHIPPING_SIZE) {
			return 0;
		} else {
			return 120;
		}
	}

	@Override
	public int getPostageRmb(List<ApplyDollInfo> dollList, Long uid) {
		/*if(shippingDao.getShippingTimes(uid) == 0 
				|| dollList.size() >= ShippingConstants.FREE_SHIPPING_SIZE) { // 首次发货or超过指定免邮的娃娃个数*/
		//LOG.info("getPostage-dollList:" + JSON.toJSONString(dollList));
		List<ApplyDollInfo> inDollInfos = new ArrayList<ApplyDollInfo>(); // 允许单独发货的娃娃数量
		if(!StringUtil.isNullOrEmpty(dollList)) {
			for(ApplyDollInfo doll : dollList) {
				UserDoll userDoll = userDollService.getUserDollById(doll.getId());
				if(userDoll != null) {
					DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId()); 
					if(dollInfo.getGoodsType() == 0 && dollInfo.getSpecType() == 0) {
						inDollInfos.add(doll);
					}
				}
			}
		}
		//LOG.info("getPostage-size:" + inDollInfos.size() + ",inDollInfos:" + JSON.toJSONString(inDollInfos));
		if(inDollInfos.size() >= ShippingConstants.FREE_SHIPPING_SIZE) {
			return 0;
		} else {
			return 12;
		}
	}
	
	@Override
	public BaseRespVO phonebind(Long uid) {
		PhonebindRespVO resp = new PhonebindRespVO();
		UserBase userBase = userService.getUserBase(uid);
		LOG.info("phonebind:" + JSON.toJSONString(userBase));
		if(userBase == null || (userBase != null && userBase.getNumber() == 0)) {
			return new BaseRespVO(ResCodeEnum.PHONE_BIND);
		}
		resp.setPhone(userBase.getNumber());
		return resp;
	}

	@Override
	public String getShippingNotice() {
		DollShippingNotice shippingNotice = shippingDao.getShippingNotice();
		return shippingNotice == null ? "" : shippingNotice.getContent();
	}

	@Override
	public List<DollOrderRecord> getDollOrderRecords(int intervalMinutes) {
		return shippingDao.getDollOrderRecords(intervalMinutes);
	}

	@Override
	public List<DollOrderInfo> getDollOrderInfos(long orderNum) {
		return shippingDao.getDollOrderInfos(orderNum);
	}

	@Override
	public List<DollOrderInfo> getDollOrderInfosAll(long orderNum) {
		return shippingDao.getDollOrderInfosAll(orderNum);
	}
	
	@Override
	public int updateDollOrderRecordStatus(long orderNum, int status) {
		return shippingDao.updateDollOrderRecordStatus(orderNum, status);
	}

	@Override
	public int initDollOrderExpress(long orderNum, long uid, long orderId) {
		return shippingDao.initDollOrderExpress(orderNum, uid, orderId);
	}

	@Override
	public int updateDollOrderRecordReason(long orderNum, String reason) {
		return shippingDao.updateDollOrderRecordReason(orderNum, reason);
	}

	@Override
	public int addDollOrderRecordTaskLog(long orderNum, long uid, String req,
			String resp) {
		return shippingDao.addDollOrderRecordTaskLog(orderNum, uid, req, resp);
	}

	@Override
	public DollOrderExpress getDollOrderExpress(long orderId) {
		return shippingDao.getDollOrderExpress(orderId);
	}

	@Override
	public int updateDollOrderExpress(long orderNum, String expressNum,
			String expressCom, String expressName,String expressRemark) {
		return shippingDao.updateDollOrderExpress(orderNum, expressNum, expressCom, expressName, expressRemark);
	}

	@Override
	public void addShippingCallbackLog(String content) {
		shippingDao.addShippingCallbackLog(content);
	}

	@Override
	public DollOrderRecord getDollOrderRecord(long orderNum) {
		return shippingDao.getDollOrderRecord(orderNum);
	}

	@Override
	public List<DollOrderRecord> getDollOrderRecordsByUid(long uid,int page,int pageSize) {
		return shippingDao.getDollOrderRecordsByUid(uid, page, pageSize);
	}
	
	@Override
	public List<DollOrderRecordBill> getDollOrderRecordBill(long uid,int page,int pageSize) {
		 List<DollOrderRecord> list= getDollOrderRecordsByUid(uid, page, pageSize);
		 List<DollOrderRecordBill> res=new ArrayList<DollOrderRecordBill>();
		 if (!StringUtil.isNullOrEmpty(list)) {
			for (DollOrderRecord dollOrderRecord : list) {
				if (dollOrderRecord==null) {
					continue;
				}
				DollOrderRecordBill bill=new DollOrderRecordBill(dollOrderRecord);
				UserAddress adress = null;
				if (dollOrderRecord.getNewAddressId() == 0) {
					adress = userAddressService
							.getAddressByAddrId(dollOrderRecord
									.getAddressId());
				} else {
					adress = userAddressService
							.getCustomAddressByAddrId(dollOrderRecord
									.getNewAddressId());
				}
				if (adress!=null) {
					bill.updateAddressInfo(adress.getShippingAddr(), adress.getReceiver(), adress.getPhone());
				}
				//不管state
				List<DollOrderInfo> infos =getDollOrderInfosAll(dollOrderRecord
								.getOrderNum());
				List<UserDoll> userDolls=new ArrayList<UserDoll>();
				LOG.info("getDollOrderRecordBill DollOrderInfo infos:"+JSON.toJSONString(infos));
				if (!StringUtil.isNullOrEmpty(infos)) {
					for (DollOrderInfo dollOrderInfo : infos) {
						UserDoll udDoll=userDollService.getUserDollById(dollOrderInfo.getuDollId());
						if (udDoll!=null) {
							userDolls.add(udDoll);
						}
					}
				}
				LOG.info("getDollOrderRecordBill userDolls:"+JSON.toJSONString(userDolls));
				List<BaseDollInfo> dollList = new ArrayList<BaseDollInfo>();
				if (!StringUtil.isNullOrEmpty(userDolls)) {
					for (UserDoll userDoll : userDolls) {
						DollInfo dollInfo = dollInfoService.getDollInfo(userDoll
								.getDollId());
						if (dollInfo == null)
							continue;
						BaseDollInfo baseDollInfo = new BaseDollInfo(userDoll,
								dollInfo.getName(), dollInfo.getImgSummry(),
								dollInfo.getCoin());
						dollList.add(baseDollInfo);
					}
				}
				if (!StringUtil.isNullOrEmpty(dollList)) {
					bill.setDollList(dollList);
				}
				res.add(bill);
			}
		 }
		 return res;
	}

	@Override
	public BaseRespVO cancelShippingBill(long uid, long orderNum) {
		DollOrderRecord record=getDollOrderRecord(orderNum);
		if (record!=null) {
			LOG.info("cancelShippingBill orderNum:"+orderNum+" record:"+JSON.toJSONString(record));
			if (record.getUid()==uid) {
				if(record.getPayStatus()==OrderPayStatus.PAYED.status){
					return new BaseRespVO(0, false, "当前订单已支付，不能取消");
				}else{
					if (record.getPayStatus()==OrderPayStatus.INIT.status) {
						//娃娃重置状态
						resetDollStatus(orderNum);
					}
					updateOrderRecordPayStatus(orderNum, OrderPayStatus.CANCEL.status);
					return new BaseRespVO(0, true, "取消成功");
				}
			}else{
				return new BaseRespVO(0, false, "只能操作自己的订单");
			}
		}
		return new BaseRespVO(0, false, "不存在该订单");
	}

	/**
	 * 根据订单id重置娃娃状态
	 * @param orderNum
	 */
	public void resetDollStatus(long orderNum){
		List<DollOrderInfo> infos = getDollOrderInfos(orderNum);
		if (!StringUtil.isNullOrEmpty(infos)) {
			for (DollOrderInfo dollOrderInfo : infos) {
				if(dollOrderInfo!=null&&dollOrderInfo.getuDollId()>0){
					UserDoll userDoll = userDollService.getUserDollById(dollOrderInfo.getuDollId());
					if (userDoll!=null) {
						if(userDoll.getStatus()==DollStatus.APPLY_SUCC.status){
							int curDay = DateUtils.daysBetweenNow(DateUtils.dateToString(userDoll.getCreateTime(), "yyyy-MM-dd")) + 1; // 已寄存的天数
							if(curDay > PackConstants.SAVE_MAX_DAYS) { // 超过可以寄存的最大天数自动兑换
								DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
								userDollService.handleAutoExchange(userDoll, dollInfo);
							} else {
								userDollService.updateUserDollStatus(userDoll.getId(), DollStatus.STORE.status);
							}
						}
					}
				}
			}
			//取消消息
			DollOrderInfo info=infos.get(0);
			MsgNotice msg=new MsgNotice(info.getUid(), MsgType.NOTICE_SYS.type, "小主刚刚取消了一个发货订单哟>");
			msg.setPushMsg("小主刚刚取消了一个发货订单哟>");
			msg.setTarget(JumpTarget.PACK_BILL.target);
			msgService.sendMsg(msg);
		}
	}
	
	@Override
	public int updateOrderRecordPayStatus(long orderNum, int payStatus) {
		return shippingDao.updateOrderRecordPayStatus(orderNum, payStatus);
	}

	@Override
	public boolean handleShippingPayed(TradeCharge charge) {
		long orderNum=charge.getOrderNum();
		DollOrderRecord record=getDollOrderRecord(orderNum);
		if (record!=null) {
			if(record.getPayStatus()==OrderPayStatus.PAYED.status){
				LOG.info("handleShippingPayed 订单重复支付 orderNum："+orderNum+" uid:"+charge.getUid());
			}else if(record.getPayStatus()==OrderPayStatus.CANCEL.status){
				LOG.info("handleShippingPayed 订单已取消 orderNum："+orderNum+" uid:"+charge.getUid());
			}else if (record.getPayStatus()==OrderPayStatus.INIT.status) {
				LOG.info("handleShippingPayed 订单成功 orderNum："+orderNum+" uid:"+charge.getUid());
				LOG.info("handleShippingPayed 订单成功 orderNum："+orderNum+" uid:"+charge.getUid()+" OrderPayStatus.PAYED.status:"+OrderPayStatus.PAYED.status);
				updateOrderRecordPayStatus(orderNum, OrderPayStatus.PAYED.status);
				return true;
			}else{
				LOG.info("handleShippingPayed 订单异常状态 orderNum："+orderNum+" uid:"+charge.getUid()+" getPayStatus:"+record.getPayStatus());
			}
		}
		return false;
	}
	
	@Override
	public void handleShippingInvalid(){
		//查询出即将过期的订单
		DollOrderRecord record=shippingDao.getInvalidRecord(15);
		while (record!=null) {
			try {
				LOG.info("handleShippingInvalid 处理过期未支付订单 orderNum"+record.getOrderNum());
				if(updateOrderRecordPayStatus(record.getOrderNum(), OrderPayStatus.CANCEL.status)>0){
					resetDollStatus(record.getOrderNum());
				}
			} catch (Exception e) {
				PrintException.printException(LOG, e);
			}
			record=shippingDao.getInvalidRecord(15);
		}
	}
}
