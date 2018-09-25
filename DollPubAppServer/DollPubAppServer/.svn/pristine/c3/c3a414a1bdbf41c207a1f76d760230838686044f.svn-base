package com.imlianai.dollpub.app.modules.support.userdoll.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.core.doll.info.DollInfoService;
import com.imlianai.dollpub.app.modules.core.doll.record.DollRecordDao;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeService;
import com.imlianai.dollpub.app.modules.core.user.customer.opt.dao.CustomerOptRecordDao;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.core.user.value.UserValueDAO;
import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;
import com.imlianai.dollpub.app.modules.support.address.service.UserAddressService;
import com.imlianai.dollpub.app.modules.support.exchange.service.DollComposeService;
import com.imlianai.dollpub.app.modules.support.exchange.service.DollExchangeSevice;
import com.imlianai.dollpub.app.modules.support.pack.constants.PackConstants;
import com.imlianai.dollpub.app.modules.support.ranking.vo.GetRankingRespVO;
import com.imlianai.dollpub.app.modules.support.userdoll.dao.UserDollDao;
import com.imlianai.dollpub.app.modules.support.userdoll.vo.GetDollSizeInfo;
import com.imlianai.dollpub.app.modules.support.userdoll.vo.UserDollStateRespVO;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.BaseDollInfo;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.doll.DollDetails;
import com.imlianai.dollpub.domain.doll.DollExchangeRecord;
import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.dollpub.domain.doll.LogisticsInfo;
import com.imlianai.dollpub.domain.doll.user.RankingItem;
import com.imlianai.dollpub.domain.doll.user.UserDoll;
import com.imlianai.dollpub.domain.doll.user.UserDollDebris;
import com.imlianai.dollpub.domain.doll.user.UserDollMonthCount;
import com.imlianai.dollpub.domain.doll.user.UserDollWeekCount;
import com.imlianai.dollpub.domain.msg.Msg;
import com.imlianai.dollpub.domain.msg.MsgType;
import com.imlianai.dollpub.domain.optrecord.OptRecord;
import com.imlianai.dollpub.domain.trade.TradeCostType;
import com.imlianai.dollpub.domain.trade.TradeRecord;
import com.imlianai.dollpub.domain.trade.TradeType;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserCommon;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.dollpub.domain.user.UserValue;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class UserDollServiceImpl implements UserDollService {

    private static final BaseLogger LOG = BaseLogger.getLogger(UserDollServiceImpl.class);

    private static final int PAGE_SIZE = 100; // 用户娃娃列表每页显示的个数

    @Resource
    private UserDollDao userDollDao;

    @Resource
    private DollInfoService dollInfoService;

    @Resource
    private UserService userService;

    @Resource
    private UserValueDAO userValueDao;

    @Resource
    private DollRecordDao dollRecordDao;

    @Resource
    private MsgService msgService;

    @Resource
    private TradeService tradeService;

    @Resource
    private DollExchangeSevice dollExchangeSevice;

    @Resource
    private UserDollService userDollService;

    @Resource
    private UserAddressService userAddressService;

    @Resource
    private CustomerService customerService;

    @Resource
    private CustomerOptRecordDao customerOptRecordDao;

    @Resource
    UserValueDAO userValueDAO;

    private static Map<Long, Integer> weekRankMapCache = new ConcurrentHashMap<Long, Integer>();

    List<RankingItem> weekRankingCache = new ArrayList<RankingItem>();

    private static Map<Long, Integer> monthRankMapCache = new ConcurrentHashMap<Long, Integer>();

    List<RankingItem> monthRankingCache = new ArrayList<RankingItem>();

    private static Date weekLastUpdateTime = new Date();

    private static Date monthLastUpdateTime = new Date();
    @Resource
    DollComposeService dollComposeService;
    @Override
    public UserDoll getUserDollById(long id) {
        return userDollDao.getUserDollById(id);
    }

    @Override
    public int updateUserDollStatus(long id, int status) {
        return userDollDao.updateUserDollStatus(id, status);
    }

    @Override
    public List<UserDoll> getExchangeUserDollList(int otherDays) {
        return userDollDao.getExchangeUserDollList(otherDays);
    }

    @Override
    public GetRankingRespVO getRanking(int type, Long userId, int customerId) {
        GetRankingRespVO respVO = new GetRankingRespVO();
        Customer customer =  customerService.getCustomerById(customerId);
        if(customer == null) return respVO;
        int groupId = customer.getGroupId();
        List<RankingItem> rankingItems = new ArrayList<RankingItem>();
        RankingItem selfRankingInfo = new RankingItem();
        int code = 0;
        initRank(type, groupId);
        if (type == 0) { // 周榜
            code = Integer.parseInt(DateUtils.dateToString(DateUtils.getWeekFirstTime(), "yyyyMMdd")); // 星期一的日期
            UserDollWeekCount weekCount = userDollDao.getUserDollWeekCountByUidAndCode(userId, code, groupId);
            int num = 0;
            int rank = weekRankMapCache.get(userId) == null ? 0 : weekRankMapCache.get(userId);
            if (weekCount != null) {
                num = weekCount.getNum();
            }
            selfRankingInfo = new RankingItem(
                    new UserCommon(userService.getUserGeneral(userId), userService.getUserBase(userId)), num, rank);
            rankingItems.addAll(weekRankingCache);
        } else if (type == 1) { // 月榜
            code = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMM")); // 月份
            UserDollMonthCount monthCount = userDollDao.getUserDollMonthCountByUidAndCode(userId, code, groupId);
            int num = 0;
            int rank = monthRankMapCache.get(userId) == null ? 0 : monthRankMapCache.get(userId);
            if (monthCount != null) {
                num = monthCount.getNum();
            }
            selfRankingInfo = new RankingItem(
                    new UserCommon(userService.getUserGeneral(userId), userService.getUserBase(userId)), num, rank);
            rankingItems.addAll(monthRankingCache);
        }
        respVO.setRankingItems(rankingItems);
        respVO.setSelfRankingInfo(selfRankingInfo);
        LOG.info("getRanking-selfRankingInfo:" + JSON.toJSONString(selfRankingInfo));
        return respVO;
    }

    private void initRank(int type, int groupId) {
        List<RankingItem> rankingItems = new ArrayList<RankingItem>();
        int code = 0;
        if (type == 0) { // 周榜
            if (StringUtil.isNullOrEmpty(weekRankMapCache) || DateUtils.secondBetween(weekLastUpdateTime) > 10) {
                weekLastUpdateTime = new Date();
                Map<Long, Integer> weekRankMap = new ConcurrentHashMap<Long, Integer>(); // 周榜排名，key-用户ID
                // value-排名
                code = Integer.parseInt(DateUtils.dateToString(DateUtils.getWeekFirstTime(), "yyyyMMdd")); // 星期一的日期
                List<UserDollWeekCount> weekCounts = userDollDao.getUserDollWeekCountList(code, 50, groupId);
                if (!StringUtil.isNullOrEmpty(weekCounts)) {
                    List<Long> uidsList = new ArrayList<Long>();
                    for (UserDollWeekCount userDollWeekCount : weekCounts) {
                        long uid = userDollWeekCount.getUid();
                        uidsList.add(uid);
                    }
                    Map<Long, UserGeneral> userMap = userService.getUserGeneralMap(uidsList);
                    Map<Long, UserBase> baseMap = userService.getUserBaseMap(uidsList);
                    int i = 1;
                    for (UserDollWeekCount userDollWeekCount : weekCounts) {
                        long uid = userDollWeekCount.getUid();
                        UserGeneral userGeneral = userMap.get(uid);
                        UserBase base = baseMap.get(uid);
                        if (userGeneral != null && base != null) {
                            RankingItem RankingItem = new RankingItem(new UserCommon(userGeneral, base),
                                    userDollWeekCount.getNum(), i);
                            rankingItems.add(RankingItem);
                            weekRankMap.put(uid, i);
                            i++;
                        }
                    }
                }
                // 将周榜补充
                weekRankMapCache.clear();
                weekRankMapCache.putAll(weekRankMap);
                weekRankingCache = rankingItems;
            }
        } else if (type == 1) { // 月榜
            if (StringUtil.isNullOrEmpty(monthRankMapCache) || DateUtils.secondBetween(monthLastUpdateTime) > 10) {
                monthLastUpdateTime = new Date();
                Map<Long, Integer> monthRankMap = new ConcurrentHashMap<Long, Integer>(); // 月榜排名，key-用户ID
                // value-排名
                code = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMM")); // 月份
                List<UserDollMonthCount> monthCounts = userDollDao.getUserDollMonthCountList(code, 50, groupId);
                if (!StringUtil.isNullOrEmpty(monthCounts)) {
                    List<Long> uidsList = new ArrayList<Long>();
                    for (UserDollMonthCount userDollMonthCount : monthCounts) {
                        long uid = userDollMonthCount.getUid();
                        uidsList.add(uid);
                    }
                    Map<Long, UserGeneral> userMap = userService.getUserGeneralMap(uidsList);
                    Map<Long, UserBase> baseMap = userService.getUserBaseMap(uidsList);
                    int i = 1;
                    for (UserDollMonthCount userDollMonthCount : monthCounts) {
                        long uid = userDollMonthCount.getUid();
                        UserGeneral userGeneral = userMap.get(uid);
                        UserBase base = baseMap.get(uid);
                        if (userGeneral != null && base != null) {
                            RankingItem RankingItem = new RankingItem(new UserCommon(userGeneral, base),
                                    userDollMonthCount.getNum(), i);
                            rankingItems.add(RankingItem);
                            monthRankMap.put(uid, i);
                            i++;
                        }
                    }
                }
                // 将月榜补充
                monthRankMapCache.clear();
                monthRankMapCache.putAll(monthRankMap);
                monthRankingCache = rankingItems;
            }
        }
    }

    @Override
    public int getRankingNum(int type, Long userId, int customerId) {
        int res = 0;
        if (type == 0) { // 周榜
            if (StringUtil.isNullOrEmpty(weekRankMapCache)) {
                getRanking(type, userId, customerId);
            }
            if (!StringUtil.isNullOrEmpty(weekRankMapCache)) {
            	Integer rank = weekRankMapCache.get(userId);
                if (rank != null) {
                    res = rank;
                }
			}
        } else if (type == 1) { // 月榜
            if (StringUtil.isNullOrEmpty(monthRankMapCache)) {
                getRanking(type, userId, customerId);
            }
            if (!StringUtil.isNullOrEmpty(monthRankMapCache)) {
				Integer rank = monthRankMapCache.get(userId);
				if (rank != null) {
					res = rank;
				}
			}
        }
        return res;
    }

    @Override
    public List<BaseDollInfo> getUserDollList(Long userId, long lastId) {
        List<BaseDollInfo> dollList = new ArrayList<BaseDollInfo>();
        List<UserDoll> userDolls = userDollDao.getUserDollList(userId, lastId, PAGE_SIZE);
        Map<Integer, DollInfo> dollInfoMap = dollInfoService.getDollInfos(userDolls, "dollId");
        if (!StringUtil.isNullOrEmpty(userDolls)) {
            for (UserDoll userDoll : userDolls) {
                if (!StringUtil.isNullOrEmpty(dollInfoMap)) {
                    DollInfo dollInfo = dollInfoMap.get(userDoll.getDollId());
                    if (dollInfo != null) {
                        BaseDollInfo baseDollInfo = new BaseDollInfo(userDoll, dollInfo);
                        baseDollInfo.setOptId(userDoll.getOptId());
                        if (userDoll.getStatus() == 0) {
                            int curDay = DateUtils.daysBetweenNow(DateUtils.dateToString(userDoll.getCreateTime(), "yyyy-MM-dd")) + 1;
                            baseDollInfo.setCurDay(curDay);//已寄存的天数
                            baseDollInfo.setTotalDay(PackConstants.SAVE_MAX_DAYS);//最大可寄存天数
                        }
                        dollList.add(baseDollInfo);
                    }
                }
            }
        }
        return dollList;
    }

    @Override
    public RankingItem getUserDollCount(Long userId) {
        try {
            int count = userDollDao.getDollCountByUid(userId);
            LOG.info("用户捉取到的娃娃数量=>" + count);
            RankingItem rankingItem = new RankingItem(
                    new UserCommon(userService.getUserGeneral(userId), userService.getUserBase(userId)), count, null);
            return rankingItem;
        } catch (Exception e) {
            LOG.info(e.toString());
        }
        return null;
    }

    @Override
    public List<UserDoll> getShippingList(Long uid) {
        return userDollDao.getShippingList(uid);
    }

    @Override
    public int getDollCountByUserIdAndDollId(Long uid, long dollId) {
        return userDollDao.getDollCountByUserIdAndDollId(uid, dollId);
    }

    @Override
    public List<UserDollWeekCount> getUserDollWeekCountList(int code, int num, Integer groupId) {
        return userDollDao.getUserDollWeekCountList(code, num, groupId);
    }

    @Override
    public int saveUserDoll(UserDoll userDoll) {
    	int result = userDollDao.saveUserDoll(userDoll);
    	if(result > 0) {
    		 userValueDAO.updateDollNum(userDoll.getUid(), 1);
    	}
        return result;
    }

    @Override
    public int getUserDollSizeByParams(Long uid, int status) {
        return userDollDao.getUserDollSizeByParams(uid, status);
    }

    @Override
    public GetDollSizeInfo getDollSize(Long uid) {
        GetDollSizeInfo info = null;
        UserValue userValue = userValueDao.getUserValueInDb(uid);
        UserGeneral userGeneral = userService.getUserGeneral(uid);
        if (userValue != null && userGeneral != null && userGeneral.getValid() == 0) {
            info = new GetDollSizeInfo(userGeneral.getUid(), userGeneral.getName(), userGeneral.getHead(),
                    userValue.getDollNum());
        }
        return info;
    }

    @Override
    public List<UserDoll> getUserDollByParams(Long uid, long dollId, int num) {
        return userDollDao.getUserDollByParams(uid, dollId, num);
    }

    @Override
    public int saveOrUpdateUserDollWeekCount(long uid, int weekCode, int num, Integer groupId) {
        return userDollDao.saveOrUpdateUserDollWeekCount(uid, weekCode, num, groupId);
    }

    @Override
    public int saveOrUpdateUserDollMonthCount(long uid, int monthCode, int num, Integer groupId) {
        return userDollDao.saveOrUpdateUserDollMonthCount(uid, monthCode, num, groupId);
    }

    @Override
    public List<LogisticsInfo> getLogistics(Long uDollId) {
        // test
        List<LogisticsInfo> logisticsInfos = new ArrayList<LogisticsInfo>();
//		LogisticsInfo info1 = new LogisticsInfo("等待揽件", 1512087142000l);
//		LogisticsInfo info2 = new LogisticsInfo("快件已由物流公司揽收", 1512090742000l);
//		LogisticsInfo info3 = new LogisticsInfo("快件已由[杭州分拨中心]发往[广州分拨中心]", 1512263542000l);
//		LogisticsInfo info4 = new LogisticsInfo("康龙已签收", 1512349942000l);
//		logisticsInfos.add(info4);
//		logisticsInfos.add(info3);
//		logisticsInfos.add(info2);
//		logisticsInfos.add(info1);
        return logisticsInfos;
    }

    @Override
    public BaseRespVO returnDoll(Long optId, long uid) {
        try {

            UserBase userBase = userService.getUserBase(uid);
            if (userBase != null) {
                Customer customer = customerService.getCustomerById(userBase.getCustomerId());
                if (customer != null) {
                    OptRecord record = customerOptRecordDao.getEntityByOptId(customer.getGroupId(), optId);
//                    if (record == null || record.getResult() == 1 || uid != record.getUid()) {
//                        return new BaseRespVO(-1, false, "归还条件不符合~");
//                    }
                    if (record == null || uid != record.getUid()) {
                        return new BaseRespVO(-1, false, "归还条件不符合~");
                    }
                    DollInfo dollInfo = dollInfoService.getDollInfo(record.getDollId());
                    if (dollInfo == null) {
                        return new BaseRespVO(-1, false, "娃娃不存在或下架，请联系客服处理~");
                    }
                    UserDoll userDoll = new UserDoll();
                    userDoll.setUid(uid);
                    userDoll.setDollId(record.getDollId());
                    userDoll.setOptId(record.getOptId());
                    userDoll.setStatus(0);
                    userDoll.setGoodsType(dollInfo.getGoodsType());
                    userDoll.setRemark("娃娃归还");
                    long uDollId = saveUserDoll(userDoll);
                    record.setResult(1);
                    customerOptRecordDao.updateEntityById(customer.getGroupId(), record); // 操作记录改为成功
        			dollRecordDao.saveDollSucOptRecord(optId, uid, record.getDollId(), record.getBusId(), customer.getGroupId(), userBase.getCustomerId(), userBase.getAgentId()==null?0:userBase.getAgentId()); // 保存成功捉取娃娃记录
        			// 保存用户娃娃个数周统计、月统计
        			int weekCode = Integer.parseInt(DateUtils.dateToString(DateUtils.getWeekFirstTime(), "yyyyMMdd"));
        			int monthCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMM"));
        			userDollService.saveOrUpdateUserDollWeekCount(record.getUid(), weekCode, 1, customer.getGroupId());
                    userDollService.saveOrUpdateUserDollMonthCount(record.getUid(), monthCode, 1, customer.getGroupId());
                }
            }
            // 系统通知
//            Msg msg = new Msg(userDoll.getUid(), MsgType.NOTICE_SYS.type,
//                    "您抓取的" + dollInfo.getName() + "（游戏编号：" + record.getOptId() + "）的申诉已处理，宝贝已放进您的背包。");
//            msgService.sendMsg(msg);
            // TODO 改状态
            return new BaseRespVO();
        } catch (Exception e) {
            PrintException.printException(LOG, e);
            return new BaseRespVO(-1, false, "归还娃娃失败，请重试~");
        }
    }

    @Override
    public BaseRespVO refundCoin(Long optId, long uid) {
        try {
            UserBase userBase = userService.getUserBase(uid);
            if (userBase != null) {
                Customer customer = customerService.getCustomerById(userBase.getCustomerId());
                if (customer != null) {
                    OptRecord record = customerOptRecordDao.getEntityByOptId(customer.getGroupId(), optId);

                    if (record == null) {
                        return new BaseRespVO(-1, false, "操作记录不存在~");
                    }
                    DollInfo dollInfo = dollInfoService.getDollInfo(record.getDollId());
                    if (dollInfo == null) {
                        return new BaseRespVO(-1, false, "娃娃不存在或下架，请联系客服处理~");
                    }
                    TradeRecord tradeRecord = new TradeRecord(uid, uid, TradeType.APPEAL_REFUND.type, 0, dollInfo.getCoin(),
                            TradeCostType.COST_COIN.type, "申诉退币" + dollInfo.getCoin());
                    tradeService.charge(tradeRecord);
                    // 系统通知
//                    Msg msg = new Msg(uid, MsgType.NOTICE_SYS.type, "您抓取的" + dollInfo.getName() + "（游戏编号：" + record.getOptId()
//                            + "）的申诉已处理，" + dollInfo.getCoin() + "币已退回到您的账户。");
//                    msgService.sendMsg(msg);
                }
            }
            return new BaseRespVO();
        } catch (Exception e) {
            PrintException.printException(LOG, e);
            return new BaseRespVO(-1, false, "退币失败，请重试~");
        }
    }

    @Override
    public BaseRespVO getUserDollByState(long uid, Integer status) {
        UserDollStateRespVO userDollStateRespVO = new UserDollStateRespVO();

        if (userService.getUserBase(uid) == null) {
            userDollStateRespVO.setMsg("当前用户不存在：" + uid);
            userDollStateRespVO.setState(false);
            return userDollStateRespVO;
        }
        // 0：寄存中 ，1： 成功申请发货 ，2： 已发货 ，3： 已兑换 ，4： 已拒绝
        if (status == 0 || status == 1 || status == 2 || status == 3 || status == 4) {
            if (status == 1 || status == 2 || status == 4) {
                status = 8; // 包括 成功申请发货、已发货、已拒绝
            }
            List<UserDoll> userDolls = userDollDao.getUserDollByState(uid, status);
            if (!StringUtil.isNullOrEmpty(userDolls)) {
                List<BaseDollInfo> baseDollInfos = new ArrayList<>();
                Map<Integer, DollInfo> dollInfoMap = dollInfoService.getDollInfos(userDolls, "dollId");
                for (UserDoll userDoll : userDolls) {
                    //DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
                    if (!StringUtil.isNullOrEmpty(dollInfoMap)) {
                        DollInfo dollInfo = dollInfoMap.get(userDoll.getDollId());
                        if (dollInfo != null) {
                            BaseDollInfo baseDollInfo = new BaseDollInfo(userDoll.getId(), dollInfo.getName(),
                                    dollInfo.getImgCover(), userDoll.getOptId());
                            baseDollInfo.setDollId(dollInfo.getDollId());
                            baseDollInfo.setCoin(dollInfo.getCoin());// 可兑换金币
                            baseDollInfo.setGetTime(userDoll.getCreateTime().getTime());// 获取时间
                            baseDollInfo.setStatus(userDoll.getStatus());
                            if (status == 0) {
                                int curDay = DateUtils.daysBetweenNow(DateUtils.dateToString(userDoll.getCreateTime(), "yyyy-MM-dd")) + 1;
                                baseDollInfo.setCurDay(curDay);//已寄存的天数
                                baseDollInfo.setTotalDay(PackConstants.SAVE_MAX_DAYS);//最大可寄存天数
                            }
                            baseDollInfos.add(baseDollInfo);
                        }
                    }
                }
                userDollStateRespVO.setCount(userDollDao.getUserDollSizeByParams(uid, status));
                userDollStateRespVO.setTotal(userDollDao.getUserDollSizeByParams(uid, -1));
                userDollStateRespVO.setBaseDollInfos(baseDollInfos);
                userDollStateRespVO.setState(true);
                return userDollStateRespVO;
            }
            if (status == 0)
                return new BaseRespVO(0, false, "暂无寄存中的宝贝");
            if (status == 8)
                return new BaseRespVO(0, false, "暂无已发货的宝贝");
            if (status == 3)
                return new BaseRespVO(0, false, "暂无已兑换的宝贝");
        } else {
            userDollStateRespVO.setState(false);
            userDollStateRespVO.setMsg("状态错误！");
        }
        return userDollStateRespVO;
    }

    @Override
    public UserDoll getUserDollById(long uid, int id) {
        return userDollDao.getUserDollById(uid, id);
    }

    @Transactional
    @Override
    public BaseRespVO exchange(long uid, int id) {
        BaseRespVO baseRespVO = new BaseRespVO();
        UserDoll userDoll = userDollService.getUserDollById(uid, id);
        if (userDoll != null) {
            // 不满足可兑换条件
            if (userDoll.getStatus() == 3) {
                return new BaseRespVO(-1, false, "当前娃娃已被兑换~");
            }
            if (userDoll.getStatus() == 1 || userDoll.getStatus() == 2) {
                return new BaseRespVO(-1, false, "发货中的娃娃不可被兑换");
            }
            // 只有满足寄存中的娃娃才能被兑换
            if (userDoll.getStatus() == 0) {
                if (userDollService.updateUserDollStatus(id, 3) > 0) {
                    DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
                    if (dollInfo != null) {
                        try {
                            if (dollExchangeSevice.saveExchangeRecord(userDoll.getUid(), dollInfo.getCoin()) > 0) {
                                LOG.info("saveExchangeRecord=>" + userDoll.getUid() + ",兑换金币=>" + dollInfo.getCoin());
                            }
                            // +用户金币
                            TradeRecord tradeRecord = new TradeRecord(userDoll.getUid(), userDoll.getUid(),
                                    TradeType.EXCHANGE_RETURN.type, 0, dollInfo.getCoin(), TradeCostType.COST_COIN.type,
                                    "自动兑换" + dollInfo.getCoin() + "个游戏币");
                            if (tradeService.charge(tradeRecord)) {
                                baseRespVO.setMsg("成功兑换" + dollInfo.getCoin() + "金币");
                                baseRespVO.setResult(100);
                                baseRespVO.setState(true);
                                return baseRespVO;
                            }
                        } catch (TradeOperationException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    return new BaseRespVO(-1, false, "兑换失败，请重试！");
                }
            } else {
                return new BaseRespVO(-1, false, "用户娃娃状态错误");
            }
        }
        baseRespVO.setMsg("当前用户没有这个娃娃！");
        baseRespVO.setResult(0);
        baseRespVO.setState(false);
        return baseRespVO;
    }

    @Override
    public BaseRespVO applyForShipments(long uid, int id, int addId, List<Integer> dollId) {
        BaseRespVO baseRespVO = new BaseRespVO();
        UserDoll userDoll = userDollService.getUserDollById(uid, id);
        if (userDoll != null) {

            if (userDoll.getStatus() != 0) {
                return new BaseRespVO(-1, false, "当前娃娃状态错误=>" + userDoll.getStatus());
            }

            // 无收货地址
            if (StringUtil.isNullOrEmpty(userAddressService.getList(uid))) {
                return new BaseRespVO(-1, false, "请先填写收货地址");
            }

        }

        return baseRespVO;
    }

    @Override
    public DollDetails getDollInfo(long uid, int id) {
        LOG.info("getDollInfo: 娃娃id==>" + id + ",用户id==>" + uid);
        UserDoll userDoll = userDollService.getUserDollById(uid, id);
        if (userDoll != null) {
            DollInfo dollInfo = dollInfoService.getDollInfo(userDoll.getDollId());
            LOG.info("单个娃娃信息==>" + JSON.toJSONString(dollInfo));

            if (dollInfo != null) {
                DollDetails dollDetails = new DollDetails();
                dollDetails.setId(userDoll.getId());
                dollDetails.setName(dollInfo.getName());
                dollDetails.setPath(dollInfo.getImgCover());
                dollDetails.setOptId(userDoll.getOptId());
                dollDetails.setDollId(dollInfo.getDollId());
                dollDetails.setGetTime(userDoll.getCreateTime().getTime());// 用户娃娃获取时间
                dollDetails.setStatus(userDoll.getStatus());// 用户娃娃状态
                dollDetails.setGoodsType(dollInfo.getGoodsType());
                dollDetails.setDollType(dollInfo.getType());

                // 0:寄存中
                if (userDoll.getStatus() == 0) {
                    int curDay = DateUtils.daysBetweenNow(DateUtils.dateToString(userDoll.getCreateTime(), "yyyy-MM-dd")) + 1;
                    dollDetails.setCurDay(curDay);//已寄存的天数
                    dollDetails.setTotalDay(PackConstants.SAVE_MAX_DAYS);//最大可寄存天数
                    dollDetails.setCoin(dollInfo.getCoin());// 娃娃可兑换金币
                }

                // 1:成功申请发货 2:已发货 4:已拒绝
                if(userDoll.getStatus() == 1 || userDoll.getStatus() == 2 || userDoll.getStatus() == 4){
                    if (userDoll.getStatus() == 2) {
                        dollDetails.setExpressName(userDoll.getExpressName());
                        dollDetails.setTrackingNum(userDoll.getTrackingNum());
                    }
                    if (userDoll.getStatus() == 4) {
                        dollDetails.setRejectReason(userDoll.getReason());
                    }
                }

                // 3:已兑换
                if (userDoll.getStatus() == 3) {

                    DollExchangeRecord record = dollExchangeSevice.getExchangeRecordByUserDollId(userDoll.getId());
                    if (record != null) {
                        dollDetails.setEcoin(record.getCoin());
                        dollDetails.setExchangeTime(record.getCreateTime().getTime());
                    }

                }
                return dollDetails;
            }
        }
        return null;
    }

    @Override
    public List<UserDollDebris> getUserDollDebrisByUid(Long uid) {
        return userDollDao.getUserDollDebrisByUid(uid);
    }

    @Override
    public void saveOrUpdateUserDollDebris(Long uid, int type, double num, String remark) {
        if (userDollDao.saveOrUpdateUserDollDebris(uid, type, num) > 0) {
            userDollDao.saveUserDollDebrisLog(uid, type, num, remark);
        }
    }

    @Override
    public long saveUserDoll(Long uid, int dollId, Long optId, String remark, int type) {
    	DollInfo dollInfo = dollInfoService.getDollInfo(dollId);
        UserDoll userDoll = new UserDoll();
        userDoll.setUid(uid);
        userDoll.setDollId(dollId);
        userDoll.setOptId(optId);
        userDoll.setStatus(0);
        userDoll.setRemark(remark);
        userDoll.setType(type);
        userDoll.setGoodsType(dollInfo.getGoodsType());
        long uDollId = saveUserDoll(userDoll);
        // 保存用户娃娃个数周统计、月统计
        int weekCode = Integer.parseInt(DateUtils.dateToString(DateUtils.getWeekFirstTime(), "yyyyMMdd"));
        int monthCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMM"));
        userDollDao.saveOrUpdateUserDollWeekCount(uid, weekCode, 1, null);
        userDollDao.saveOrUpdateUserDollMonthCount(uid, monthCode, 1, null);
        return uDollId;
    }

    @Override
    public void resultUserDoll(OptRecord optRecord, DollBus dollBus, int groupId) {
        LOG.info("resultUserDoll: 操作记录==>" + JSON.toJSONString(optRecord) + ",娃娃机==>" + JSON.toJSONString(dollBus) + ",所属商户组==>" + groupId);
        if (optRecord != null) {

            List<UserDoll> userDollList = userDollDao.getUserDollByOptId(optRecord.getOptId());
            if (StringUtil.isNullOrEmpty(userDollList)){
                // 记录到用户背包
                UserDoll userDoll = new UserDoll();

                UserBase userBase = userService.getUserBase(optRecord.getUid());
                userDoll.setAgentId(null == userBase.getAgentId()?0:userBase.getAgentId());
                userDoll.setOptId(optRecord.getOptId());
                userDoll.setUid(optRecord.getUid());
                if (dollBus != null) {
                    userDoll.setDollId(dollBus.getDollId());
                }
                userDoll.setStatus(0);// 寄存中
                if (userDollService.saveUserDoll(userDoll) > 0) {
                    // 保存用户娃娃个数周统计、月统计
                    int weekCode = Integer.parseInt(DateUtils.dateToString(DateUtils.getWeekFirstTime(), "yyyyMMdd"));
                    int monthCode = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMM"));
                    userDollService.saveOrUpdateUserDollWeekCount(optRecord.getUid(), weekCode, 1, groupId);
                    userDollService.saveOrUpdateUserDollMonthCount(optRecord.getUid(), monthCode, 1, groupId);
                    LOG.info("保存到用户背包=>" + JSON.toJSONString(userDoll));
                }

                if(dollRecordDao.saveDollSucOptRecord(optRecord.getOptId(),optRecord.getUid(),optRecord.getDollId(),optRecord.getBusId(),groupId,userBase.getCustomerId(),userBase.getAgentId()==null?0:userBase.getAgentId()) > 0){
                    LOG.info("保存娃娃抓取成功记录");
                }
            }else {
                LOG.info("resultUserDoll 用户背包出现多个相同optId=>" + optRecord.getOptId());
            }

        }
    }
    
    @Override
	public void handleAutoExchange(UserDoll userDoll, DollInfo dollInfo) {
		try {
			if(userDoll.getType() == 0) {
				if(updateUserDollStatus(userDoll.getId(), 3) > 0) {
					dollExchangeSevice.saveExchangeRecord(userDoll.getId(), dollInfo.getCoin());
					TradeRecord tradeRecord = new TradeRecord(userDoll.getUid(), userDoll.getUid(),
							TradeType.EXCHANGE_RETURN.type, 0, dollInfo.getCoin(),
							TradeCostType.COST_COIN.type, "自动兑换" + dollInfo.getCoin() + "个游戏币");
					tradeService.charge(tradeRecord);
					// 系统通知
					Msg msg = new Msg(userDoll.getUid() , MsgType.NOTICE_SYS.type,
							"逾期未申请发货，" + dollInfo.getName() +"自动兑换成" + dollInfo.getCoin() + "币。");
					msgService.sendMsg(msg);
				}
			} else if(userDoll.getType() == 1 || userDoll.getType() == 2) {
				if(dollInfo.getReturnJewel() > 0 && updateUserDollStatus(userDoll.getId(), 5) > 0) {
					dollComposeService.saveRecycleRecord(userDoll.getId(), dollInfo.getReturnJewel());
					TradeRecord tradeRecord = new TradeRecord(userDoll.getUid(), userDoll.getUid(),
							TradeType.RECYCLE_RETURN.type, 0, dollInfo.getReturnJewel(),
							TradeCostType.COST_JEWEL.type, "自动回收成" + dollInfo.getReturnJewel() + "钻");
					tradeService.charge(tradeRecord);
					// 系统通知
					String textString = dollInfo.getName() + "已被回收并获得" + dollInfo.getReturnJewel() + "钻";
				}
			}
		} catch (Exception e) {
			PrintException.printException(LOG, e);
		}
	}
}
