package com.imlianai.zjdoll.app.modules.core.egg.trade;

import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.NotEnoughBeanException;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.ChargeCatalog;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.service.ChargeCatalogService;
import com.imlianai.zjdoll.app.modules.core.trade.dao.TradeDAO;
import com.imlianai.zjdoll.app.modules.core.trade.record.TradeRecordService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.domain.egg.EggMachineUserAccount;
import com.imlianai.zjdoll.domain.egg.EggMachineUserAccountRecord;
import com.imlianai.zjdoll.domain.egg.EggMachineUserPlayRecord;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.trade.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EggTradeServiceImpl implements EggTradeService {

	private static BaseLogger logger = BaseLogger
			.getLogger(EggTradeServiceImpl.class);
	@Resource
	private EggTradeDao eggTradeDao;
	@Resource
	private TradeRecordService tradeRecordService;
	@Resource
	private ChargeCatalogService chargeCatalogService;
	@Resource
	private MsgService msgService;

	@Resource
	private TradeService tradeService;

	@Resource
	private TradeDAO tradeDAO;


	@Override
	public int initAccount(long uid) {
		return eggTradeDao.initAccount(uid);
	}

	@Override
	public EggMachineUserAccount getAccount(long uid) {
		EggMachineUserAccount account = eggTradeDao.getUserAccount(uid);
		if (account == null) {
			initAccount(uid);
			return eggTradeDao.getUserAccount(uid);
		}
		return account;
	}

	@Override
	@Transactional(rollbackFor = { TradeOperationException.class,
			NotEnoughBeanException.class, Exception.class })
	public boolean consume(EggMachineUserAccountRecord record)
			throws TradeOperationException, NotEnoughBeanException {
		if (record.getNum() <= 0) {
			throw new TradeOperationException("交易金额异常");
		}
		try {
			int flag = 0;
			if (record.getType() == EggMachineUserAccountRecord.EggAccountRecordType.BALANCE.type) {
				flag = eggTradeDao.updateBalance(record.getUid(),
						-record.getNum());
			} else if (record.getType() == EggMachineUserAccountRecord.EggAccountRecordType.TIMECOUPON.type) {
				flag = eggTradeDao.updateTimeCoupon(record.getUid(),
						-record.getNum());
			} else {
				throw new TradeOperationException("交易类型异常");
			}
			if (flag > 0) {
				eggTradeDao.addRecord(record);
				return true;
			} else {
				throw new NotEnoughBeanException("余额不足");
			}
		} catch (NotEnoughBeanException e) {
			throw e;
		} catch (TradeOperationException e) {
			throw e;
		} catch (Exception e) {
			throw new TradeOperationException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = { TradeOperationException.class,
			NotEnoughBeanException.class, Exception.class })
	public boolean charge(EggMachineUserAccountRecord record)
			throws TradeOperationException {
		if (record.getNum() < 0) {
			throw new TradeOperationException("交易金额异常");
		}
		record.setDirection(1);
		try {
			int flag = 0;
			if (record.getType() == EggMachineUserAccountRecord.EggAccountRecordType.BALANCE.type) {
				flag = eggTradeDao.updateBalance(record.getUid(),
						record.getNum());
			} else if (record.getType() == EggMachineUserAccountRecord.EggAccountRecordType.TIMECOUPON.type) {
				flag = eggTradeDao.updateTimeCoupon(record.getUid(),
						record.getNum());
			} else {
				throw new TradeOperationException("交易类型异常");
			}
			if (flag > 0) {
				eggTradeDao.addRecord(record);
				return true;
			} else {
				throw new NotEnoughBeanException("余额不足");
			}
		} catch (TradeOperationException e) {
			throw e;
		} catch (Exception e) {
			throw new TradeOperationException(e);
		}
	}

	@Override
	public void handlePayment(TradeCharge charge) {
		if (charge != null) {
			ChargeCatalog item = chargeCatalogService.getCatalog(charge
					.getItemCode());
			if (item != null) {

				StringBuffer msgBuffer = new StringBuffer();
				StringBuffer tradeBuffer = new StringBuffer();
				msgBuffer = new StringBuffer("恭喜，充值成功！充值金额：");
				tradeBuffer = new StringBuffer("充值" + charge.getRealyCost()
						+ "元，获得" + item.getCoin() + "币");
				msgBuffer.append(charge.getRealyCost() + "元，获得"
						+ item.getCoin() + "币");
				if (item.getTimeCoupons() > 0) {
					msgBuffer.append("，额外赠送");
					msgBuffer.append(item.getTimeCoupons());
					msgBuffer.append("时光券");
					tradeBuffer.append("，额外赠送");
					tradeBuffer.append(item.getTimeCoupons());
					tradeBuffer.append("时光券");
				}
				EggMachineUserAccountRecord record = new EggMachineUserAccountRecord(
						charge.getUid(), item.getCoin(), EggMachineUserAccountRecord.EggAccountRecordTradeType.CHARGE.type,
						EggMachineUserAccountRecord.EggAccountRecordType.BALANCE.type,
						tradeBuffer.toString());
				try {
					charge(record);
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
				if (item.getTimeCoupons() > 0) {
					EggMachineUserAccountRecord recordTicket = new EggMachineUserAccountRecord(
							charge.getUid(), item.getTimeCoupons(), EggMachineUserAccountRecord.EggAccountRecordTradeType.CHARGE.type,
							EggMachineUserAccountRecord.EggAccountRecordType.TIMECOUPON.type,
							tradeBuffer.toString());
					try {
						charge(recordTicket);
					} catch (Exception e) {
						PrintException.printException(logger, e);
					}

					//充值到账户
//					TradeRecord tradeRecord = new TradeRecord(charge.getUid(),charge.getUid(), TradeType.CHARGE.type,0,item.getTimeCoupons(), TradeCostType.TIMECOUPON.type,"充值"+charge.getRealyCost()+"元，额外赠送" + item.getTimeCoupons() + "张时光卷");
//					try {
//						tradeService.charge(tradeRecord);
//					}catch (Exception e){
//						PrintException.printException(logger, e);
//					}

					try {
						tradeDAO.updateCoupon(charge.getUid(),item.getTimeCoupons());
					}catch (Exception e){
						PrintException.printException(logger, e);
					}

				}
				EggMachineUserAccount account = getAccount(charge.getUid());
				TradeAccount tradeAccount = tradeService.getAccount(charge.getUid());
				if (account != null && tradeAccount != null) {
					msgBuffer.append("，当前账户余额：");
					msgBuffer.append(tradeAccount.getCoin());
					msgBuffer.append("游戏币，时光券");
					msgBuffer.append(account.getNum());
					msgBuffer.append("张");
				}
				MsgNotice msg = new MsgNotice(charge.getUid(),
						MsgType.NOTICE_SYS.type, msgBuffer.toString());
				msg.setPushMsg(msgBuffer.toString());
				msgService.sendMsg(msg);
			}
		}
	}
}
