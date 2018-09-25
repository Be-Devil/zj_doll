package com.imlianai.dollpub.app.modules.support.pack.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.core.doll.info.DollInfoService;
import com.imlianai.dollpub.app.modules.core.doll.list.DollListService;
import com.imlianai.dollpub.app.modules.core.doll.record.DollRecordService;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.core.user.customer.opt.dao.CustomerOptRecordDao;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.app.modules.support.exchange.service.DollComposeService;
import com.imlianai.dollpub.app.modules.support.exchange.service.DollExchangeSevice;
import com.imlianai.dollpub.app.modules.support.exchange.service.DollGetService;
import com.imlianai.dollpub.app.modules.support.pack.constants.PackConstants;
import com.imlianai.dollpub.app.modules.support.pack.domain.GrabDollRecord;
import com.imlianai.dollpub.app.modules.support.pack.vo.AppealInfo;
import com.imlianai.dollpub.app.modules.support.pack.vo.GetDescRespVO;
import com.imlianai.dollpub.app.modules.support.pack.vo.OptRecordInfo;
import com.imlianai.dollpub.app.modules.support.pack.vo.ReceiveReqVO;
import com.imlianai.dollpub.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.BaseDollInfo;
import com.imlianai.dollpub.domain.doll.DollAppealRecord;
import com.imlianai.dollpub.domain.doll.DollDetails;
import com.imlianai.dollpub.domain.doll.DollExchangeRecord;
import com.imlianai.dollpub.domain.doll.DollGetRecord;
import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.dollpub.domain.doll.DollRecycleRecord;
import com.imlianai.dollpub.domain.doll.LogisticsInfo;
import com.imlianai.dollpub.domain.doll.user.UserDoll;
import com.imlianai.dollpub.domain.msg.Msg;
import com.imlianai.dollpub.domain.msg.MsgType;
import com.imlianai.dollpub.domain.optrecord.OptRecord;
import com.imlianai.dollpub.domain.trade.TradeCostType;
import com.imlianai.dollpub.domain.trade.TradeRecord;
import com.imlianai.dollpub.domain.trade.TradeType;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class PackServiceImpl implements PackService {

    private static final BaseLogger LOG = BaseLogger.getLogger(PackServiceImpl.class);

    @Resource
    private CustomerOptRecordDao customerOptRecordDao;

    @Resource
    private UserService userService;

    @Resource
    private CustomerService customerService;

    @Resource
    private DollInfoService dollInfoService;

    @Resource
    private DollListService dollListService;

    @Resource
    private UserDollService userDollService;

    @Resource
    private DollExchangeSevice dollExchangeSevice;

    @Resource
    private DollRecordService dollRecordService;

    @Resource
    private MsgService msgService;

    @Resource
    private TradeService tradeService;

    @Resource
    private DollComposeService dollComposeService;
    
    @Resource
    private DollGetService dollGetService;

    @Override
    public List<GrabDollRecord> getGrabRecord(long uid) {
        LOG.info("获取用户抓取记录==>" + uid);
        UserBase userBase = userService.getUserBase(uid);
        if (userBase != null) {
            Customer customer = customerService.getCustomerById(userBase.getCustomerId());
            if (customer != null && customer.getLevel() == 1) {
                List<OptRecord> optRecords = customerOptRecordDao.getOptRecordByUid(customer.getGroupId(),
                        userBase.getUid());
                if (!StringUtil.isNullOrEmpty(optRecords)) {
                    LOG.info("用户=>" + uid + ",抓取次数=>" + optRecords.size());
                    Map<Integer, DollInfo> dollInfoMap = dollInfoService.getDollInfos(optRecords, "dollId");
                    //组装数据
                    List<GrabDollRecord> grabDollRecords = new ArrayList<>();
                    for (OptRecord optRecord : optRecords) {
                        GrabDollRecord grabDollRecord = new GrabDollRecord();
                        grabDollRecord.setDollId(optRecord.getDollId());
                        grabDollRecord.setResult(optRecord.getResult());
                        grabDollRecord.setStartTime(optRecord.getStartTime());
                        if (!StringUtil.isNullOrEmpty(dollInfoMap)) {
                            DollInfo dollInfo = dollInfoMap.get(optRecord.getDollId());
                            if (dollInfo != null) {
                                grabDollRecord.setDollName(dollInfo.getName());
                                grabDollRecord.setImgCover(dollInfo.getImgCover());
                            }
                        }
                        grabDollRecords.add(grabDollRecord);
                    }
                    return grabDollRecords;
                }
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getDollList(Long uid, long lastId, int status) {
        LOG.info("getDollList:uid-" + uid + ",lastId-" + lastId + ",status-" + status);
        Map<String, Object> map = new HashMap<String, Object>();
        List<BaseDollInfo> dollInfoList = new ArrayList<BaseDollInfo>();
        int size = userDollService.getUserDollSizeByParams(uid, status);
        List<UserDoll> userDolls = dollListService.getDollListByPage(uid, lastId, PackConstants.DOLL_PAGE_SIZE, status); // 用户娃娃列表
        if (!StringUtil.isNullOrEmpty(userDolls)) {
            for (UserDoll userDoll : userDolls) {
                DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
                if (dollInfo != null) {
                    int curDay = DateUtils.daysBetweenNow(DateUtils.dateToString(userDoll.getCreateTime(), "yyyy-MM-dd")) + 1;
                    BaseDollInfo baseDollInfo = new BaseDollInfo(userDoll, dollInfo.getName(), dollInfo.getImgSummry(), dollInfo.getCoin());
                    if (userDoll.getStatus() == 3) { // 已兑换
                        DollExchangeRecord eRecord = dollExchangeSevice.getExchangeRecordByUserDollId(userDoll.getId());
                        baseDollInfo.setEcoin(eRecord == null ? 0 : eRecord.getCoin());
                    } else if (userDoll.getStatus() == 5) { // 已回收
                        DollRecycleRecord rRecord = dollComposeService.getRecycleRecordByUDollId(userDoll.getId());
                        baseDollInfo.setRjewel(rRecord == null ? 0 : rRecord.getJewel());
                    }
                    int totalDay = PackConstants.SAVE_MAX_DAYS;
                    baseDollInfo.setCurDay(curDay);
                    baseDollInfo.setTotalDay(totalDay);
                    baseDollInfo.setDollType(userDoll.getType());
                    baseDollInfo.setGoodsType(dollInfo.getGoodsType());
                    dollInfoList.add(baseDollInfo);
                }
            }
        }
        map.put("dollInfoList", dollInfoList);
        map.put("size", size);
        return map;
    }

    @Override
    public DollDetails getDollDetail(long id) {
        DollDetails dollDetail = new DollDetails();
        UserDoll userDoll = userDollService.getUserDollById(id); // 用户某个娃娃
        if (userDoll != null) {
            DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId()); // 娃娃信息
            if (dollInfo != null) {
                long exchangeTime = 0;
                int ecoin = 0;
                int rjewel = 0;
                long recycleTime = 0;
                String rejectReason = "";
                List<LogisticsInfo> logisticsInfos = null;
                if (userDoll.getStatus() == 2) { // 已发货

                }
                if (userDoll.getStatus() == 3) { // 已兑换
                    DollExchangeRecord record = dollExchangeSevice.getExchangeRecordByUserDollId(userDoll.getId());
                    if (record != null) {
                        exchangeTime = record.getCreateTime().getTime();
                        ecoin = record.getCoin();
                    }
                }
                if (userDoll.getStatus() == 4) { // 已拒绝
                    rejectReason = userDoll.getReason();
                }
                if (userDoll.getStatus() == 5) { // 已回收
                    DollRecycleRecord rRecord = dollComposeService.getRecycleRecordByUDollId(userDoll.getId());
                    if (rRecord != null) {
                        rjewel = rRecord.getJewel();
                        recycleTime = rRecord.getCreateTime().getTime();
                    }
                }
                dollDetail = new DollDetails(userDoll, dollInfo, exchangeTime);
                int curDay = DateUtils.daysBetweenNow(DateUtils.dateToString(userDoll.getCreateTime(), "yyyy-MM-dd")) + 1;
                dollDetail.setCurDay(curDay);
                dollDetail.setTotalDay(PackConstants.SAVE_MAX_DAYS);
                dollDetail.setOptId(userDoll.getOptId());
                dollDetail.setEcoin(ecoin);
                dollDetail.setRejectReason(rejectReason);
                dollDetail.setRjewel(rjewel);
                dollDetail.setRecycleTime(recycleTime);
                dollDetail.setDollType(userDoll.getType());
                dollDetail.setGoodsType(dollInfo.getGoodsType());
            }
        }
        return dollDetail;
    }

    @Override
    public BaseRespVO exchange(long id, Long uid) {
        try {
            UserDoll userDoll = userDollService.getUserDollById(id);
            if (userDoll == null || userDoll.getUid() != uid) {
                return new BaseRespVO(-1, false, "娃娃不存在~");
            }
            if (userDoll.getStatus() == 3) { // 已兑换
                return new BaseRespVO(-1, false, "娃娃已兑换，不可重复兑换~");
            }
            DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
            if (dollInfo == null) {
                return new BaseRespVO(-1, false, "娃娃已下架，不可兑换~");
            }
            if (dollInfo.getType() != 0) {
                return new BaseRespVO(-1, false, "该娃娃不可兑换~");
            }
            if (userDollService.updateUserDollStatus(id, 3) > 0) {
                dollExchangeSevice.saveExchangeRecord(id, dollInfo.getCoin());
                TradeRecord tradeRecord = new TradeRecord(uid, uid, TradeType.EXCHANGE_RETURN.type, 0, dollInfo.getCoin(),
                        TradeCostType.COST_COIN.type, "兑换" + dollInfo.getCoin() + "个游戏币");

                tradeService.charge(tradeRecord);
                Msg msg = new Msg(uid, MsgType.NOTICE_SYS.type,
                        "小主成功把" + dollInfo.getName() + "兑换成" + dollInfo.getCoin() + "币");
                msgService.sendMsg(msg);
                return new BaseRespVO(0, true, "成功兑换" + dollInfo.getCoin() + "币!");
            } else {
                return new BaseRespVO(-1, false, "兑换失败，请重试~");
            }
        } catch (Exception e) {
            PrintException.printException(LOG, e);
            return new BaseRespVO(-1, false, "兑换失败，请重试~");
        }
    }

    @Override
    public List<OptRecordInfo> getOptRecords(long lastId, Long uid, int customerId) {
        List<OptRecordInfo> recordInfoList = new ArrayList<OptRecordInfo>();
        Customer customer = customerService.getCustomerById(customerId);
        if (customer != null && customer.getLevel() == 1) {
            List<OptRecord> records = customerOptRecordDao.getOptRecordsByPaging(customer.getGroupId(), uid, lastId, PackConstants.RECORD_PAGE_SIZE);
            Map<Integer, DollInfo> dollInfoMap = dollInfoService.getDollInfos(records, "dollId");
            if (!StringUtil.isNullOrEmpty(records)) {
                for (OptRecord optRecord : records) {
                    if (!StringUtil.isNullOrEmpty(dollInfoMap)) {
                        DollInfo dollInfo = dollInfoMap.get(optRecord.getDollId());
                        if (dollInfo != null) {
                            OptRecordInfo recordInfo = new OptRecordInfo(optRecord.getOptId(), dollInfo.getName(), dollInfo.getImgSummry(),
                                    optRecord.getStartTime().getTime(), optRecord.getResult(), optRecord.getBusId());
                            recordInfoList.add(recordInfo);
                        }
                    }
                }
            }
        }
        return recordInfoList;
    }

    @Override
    public BaseRespVO appeal(long optId, int customerId, Long uid, String reason) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
            if (customer != null) {
                OptRecord record = customerOptRecordDao.getEntityByOptId(customer.getGroupId(), optId);
                if (record == null || record.getUid() != uid) {
                    return new BaseRespVO(-1, false, "操作记录不存在，不可申诉~");
                }
                if (record.getResult() == 1) {
                    return new BaseRespVO(-1, false, "已成功抓取娃娃，不可申诉~");
                }
                if (StringUtils.isBlank(reason)) {
                    return new BaseRespVO(-1, false, "申诉理由不可为空~");
                }
                //dollRecordService.saveDollAppealRecord(customer.getId(),customer.getGroupId(),optId, uid, reason);

                DollAppealRecord dollAppealRecord = new DollAppealRecord();
                dollAppealRecord.setUid(uid);
                dollAppealRecord.setCustomerId(customer.getId());
                dollAppealRecord.setGroupId(customer.getGroupId());
                dollAppealRecord.setDollId(record.getDollId());
                dollAppealRecord.setOptId(optId);
                dollAppealRecord.setResult(record.getResult());
                dollAppealRecord.setReason(reason);
                dollRecordService.saveDollAppealRecord(dollAppealRecord);
            }else{
                return new BaseRespVO(-1, false, "商户不存在~");
            }
        } catch (Exception e) {
            PrintException.printException(LOG, e);
            return new BaseRespVO(-1, false, "申诉失败，请稍后再试~");
        }
        return new BaseRespVO();
    }

    @Override
    public AppealInfo appealStatus(long optId) {
        AppealInfo info = null;
        DollAppealRecord record = dollRecordService.getAppealRecord(optId);
        if (record != null) {
            info = new AppealInfo(optId, record.getStatus(), record.getReason());
        }
        return info;
    }

    @Override
    public BaseRespVO recycle(long id, Long uid) {
        try {
            UserDoll userDoll = userDollService.getUserDollById(id);
            if (userDoll == null || userDoll.getUid() != uid.longValue()) {
                return new BaseRespVO(-1, false, "娃娃不存在~");
            }
            if (userDoll.getStatus() == 5) { // 已回收
                return new BaseRespVO(-1, false, "娃娃已回收，不可重复回收~");
            }
            DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
            if (dollInfo == null) {
                return new BaseRespVO(-1, false, "娃娃已下架，不可回收~");
            }
            if (dollInfo.getType() != 1 && dollInfo.getType() != 0) {
                return new BaseRespVO(-1, false, "该娃娃不可回收~");
            }
            if (userDollService.updateUserDollStatus(id, 5) > 0) {
                dollComposeService.saveRecycleRecord(id, dollInfo.getReturnJewel());
                TradeRecord tradeRecord = new TradeRecord(uid, uid,
                        TradeType.RECYCLE_RETURN.type, 0, dollInfo.getReturnJewel(),
                        TradeCostType.COST_JEWEL.type, "回收" + dollInfo.getReturnJewel() + "钻");
                tradeService.charge(tradeRecord);
                return new BaseRespVO(0, true, "已回收！获得" + dollInfo.getReturnJewel() + "钻！");
            } else {
                return new BaseRespVO(-1, false, "回收失败，请重试~");
            }
        } catch (Exception e) {
            PrintException.printException(LOG, e);
            return new BaseRespVO(-1, false, "回收失败，请重试~");
        }
    }

    @Override
	public BaseRespVO getNotice(int dollId) {
		GetDescRespVO respVO = new GetDescRespVO();
		DollInfo dollInfo = dollInfoService.getDollInfo(dollId);
		respVO.setContent(dollInfo == null ? "" : dollInfo.getApplyNotice());
		return respVO;
	}

	@Override
	public BaseRespVO receive(ReceiveReqVO reqVO) {
		try {
			Long uid = reqVO.getUid();
			long id = reqVO.getId();
			UserBase userBase = userService.getUserBase(uid);
			if(userBase == null || (userBase != null && userBase.getNumber() == 0)) { // 验证手机绑定
				return new BaseRespVO(ResCodeEnum.PHONE_BIND);
			}
			UserDoll userDoll = userDollService.getUserDollById(id);
			if(userDoll.getStatus() != 0 ) {
				return new BaseRespVO(-1, false, "该娃娃不满足领取条件~");
			}
			DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
			if(dollInfo == null) {
				return new BaseRespVO(-1, false, "娃娃已下架，不可领取~");
			}
			if(dollInfo.getGoodsType() != 1) {
				return new BaseRespVO(-1, false, "该娃娃不是虚拟物品，不可领取~");
			}
			DollGetRecord record = new DollGetRecord();
			record.setuDollId(id);
			record.setUid(uid);
			record.setPhone(reqVO.getPhone());
			record.setType(dollInfo.getSpecType());
			record.setStatus(0);
			record.setRemark(reqVO.getRemark());
			record.setCustomerId(userService.getUserBase(uid).getCustomerId());
			if(dollGetService.saveDollGetRecord(record) > 0) {
				userDollService.updateUserDollStatus(id, 1);
				return new BaseRespVO();
			} else {
				return new BaseRespVO(-1, false, "网络异常，请重试~");
			}
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "领取失败，请重试~");
		}
	}
}
