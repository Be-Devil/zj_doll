package com.imlianai.zjdoll.app.modules.support.ranking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.DollRankReward;
import com.imlianai.zjdoll.domain.doll.user.RankingItem;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.utils.ArithmeticUtils;
import com.imlianai.zjdoll.app.modules.support.ranking.dao.RankingDao;

@Service
public class RankingServiceImpl implements RankingService {

	@Resource
	RankingDao rankingDao;
	@Resource
	UserService userService;
	@Resource
	DollInfoService dollInfoService;

	static ConcurrentHashMap<Long, Integer> rankMap = new ConcurrentHashMap<Long, Integer>();

	static List<RankingItem> rankList = new ArrayList<RankingItem>();
	static Date lastUpdateDate = null;

	@Override
	public void handleRecharge(double cost, long uid) {
		try {
			String weekCode = DateUtils.dateToString(DateUtils
					.getWeekFirstTime());
			addChargeSum(cost, uid, weekCode);
			int total = rankingDao.getChargeSum(uid, weekCode);
			double blessingValue = ArithmeticUtils.add(total == 0 ? 0
					: 22 * Math.log(total), 33, 2);
			if (blessingValue > 0) {
				addRichValue(blessingValue, uid, weekCode);
			}
		} catch (Exception e) {
		}
	}
	@Override
	public void addChargeSum(double cost, long uid,String weekCode){
		rankingDao.addChargeSum((int) cost, uid, weekCode);
	}
	@Override
	public List<RankingItem> getChargeRanks(int size) {
		if (lastUpdateDate != null
				&& DateUtils.secondBetween(lastUpdateDate) < 10) {
			return rankList;
		}
		lastUpdateDate = new Date();
		String weekCode = DateUtils.dateToString(DateUtils.getWeekFirstTime());
		List<RankingItem> list = rankingDao.getRichRank(weekCode, size);
		if (!StringUtil.isNullOrEmpty(list)) {
			Map<Long, Integer> rankMapTemp = new HashMap<Long, Integer>();
			List<Long> uids = new ArrayList<Long>();
			for (RankingItem rankingItem : list) {
				uids.add(rankingItem.getUid());
			}
			Map<Long, UserGeneral> userMap = userService
					.getUserGeneralMap(uids);
			int rank = 1;
			for (RankingItem rankingItem : list) {
				UserGeneral userGeneral = userMap.get(rankingItem.getUid());
				if (userGeneral != null) {
					rankingItem.setName(userGeneral.getName());
					rankingItem.setHead(userGeneral.getHead());
				}
				rankingItem.setRank(rank);
				rankMapTemp.put(rankingItem.getUid(), rank);
				rank++;
			}
			try {
				if (list.size()<5) {
					for (int i = list.size(); i < 5; i++) {
						list.add(new RankingItem("虚位以待",  (i+1)));
					}
				}
			} catch (Exception e) {
			}
			list=getRankListWithReward(list, 2);
			rankMap.clear();
			rankMap.putAll(rankMapTemp);
			rankList.clear();
			rankList.addAll(list);
		} else {
			rankMap.clear();
			rankList.clear();
		}
		return list;
	}

	@Override
	public RankingItem getMyChargeRank(long uid) {
		try {
			String weekCode = DateUtils.dateToString(DateUtils.getWeekFirstTime());
			if (StringUtil.isNullOrEmpty(rankMap)) {
				getChargeRanks(50);
			}
			RankingItem rankingItem = rankingDao.getRichValue(uid, weekCode);
			if (rankingItem == null) {
				rankingItem = new RankingItem();
				rankingItem.setUid(uid);
			}
			UserGeneral userGeneral = userService.getUserGeneral(uid);
			if (userGeneral!=null) {
				rankingItem.setName(userGeneral.getName());
				rankingItem.setHead(userGeneral.getHead());
			}
			Integer rankInteger = rankMap.get(uid);
			if (rankInteger != null) {
				rankingItem.setRank(rankInteger);
			}
			return rankingItem;
		} catch (Exception e) {
		}
		return null;
	}
	
	@Override 
	public List<RankingItem> getRankListWithReward(List<RankingItem> list,int type){
		if (!StringUtil.isNullOrEmpty(list)) {
			List<DollRankReward> rewardList=rankingDao.getRewardListByType(type);
			Map<Integer, DollRankReward> rewardMap=new HashMap<Integer, DollRankReward>();
			if (!StringUtil.isNullOrEmpty(rewardList)) {
				for (DollRankReward dollRankReward : rewardList) {
					rewardMap.put(dollRankReward.getRank(), dollRankReward);
				}
				for (RankingItem item : list) {
					DollRankReward rewardInfo=rewardMap.get(item.getRank());
					if (rewardInfo!=null) {
						int dollId=rewardInfo.getDollId();
						DollInfo info=dollInfoService.getDollInfo(dollId);
						if (info!=null) {
							item.setRewardStr("奖励:"+info.getName());
						}
					}
				}
			}
		}
		return list;
	}
	@Override
	public int addRichValue(double cost, long uid, String weekCode) {
		return rankingDao.addRichValue(cost, uid, weekCode);
	}
}
