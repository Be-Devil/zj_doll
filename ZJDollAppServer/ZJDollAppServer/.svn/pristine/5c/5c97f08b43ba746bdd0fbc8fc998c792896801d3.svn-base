package com.imlianai.zjdoll.app.modules.support.playground.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.busowner.BusOwnerBusIncome;
import com.imlianai.zjdoll.domain.busowner.BusOwnerUserIndex;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollListService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.support.busowner.enm.RewardRatioEnm;
import com.imlianai.zjdoll.app.modules.support.busowner.service.BusOwnerService;
import com.imlianai.zjdoll.app.modules.support.common.CommConstants;
import com.imlianai.zjdoll.app.modules.support.playground.vo.BusOwnerItem;
import com.imlianai.zjdoll.app.modules.support.playground.vo.BusOwnerRanking;

@Service
public class PlaygroundServiceImpl implements PlaygroundService {
	
	@Resource
	DollListService dollListService;
	@Resource
	BusOwnerService busOwnerService;
	@Resource
	UserService userService;
	
	@Override
	public List<BusOwnerItem> shopList() {
		List<BusOwnerItem> busOwnerItems = new ArrayList<BusOwnerItem>();
		Map<Integer, DollBus> dollBusMap = new HashMap<Integer, DollBus>(); // 萌店机器map
		List<DollBus> mdDollBusList = new ArrayList<DollBus>(); // 萌店机器
		List<Integer> mdDollBusIds = new ArrayList<Integer>(); // 萌店机器Ids
		List<DollBus> dollBusList = dollListService.getBusList(new Integer(DollBus.DollBusType.MENGDIAN.type));
		if(!StringUtil.isNullOrEmpty(dollBusList)) {
			for(DollBus dollBus : dollBusList) {
				mdDollBusList.add(dollBus);
				dollBusMap.put(dollBus.getBusId(), dollBus);
				mdDollBusIds.add(dollBus.getBusId());
			}
		}
		int rank = 1;
		if(!StringUtil.isNullOrEmpty(mdDollBusIds)) {
			List<BusOwnerBusIncome> busIncomeList = busOwnerService.getBusIncomeByBusIds(mdDollBusIds);
			List<Integer> incomeBusId = new ArrayList<Integer>(); // 有收益的萌店
			if(!StringUtil.isNullOrEmpty(busIncomeList)) {
				for(BusOwnerBusIncome busIncome : busIncomeList) {
					BusOwnerItem busOwnerItem = new BusOwnerItem();
					List<BusOwnerRanking> busOwnerRankings = new ArrayList<BusOwnerRanking>();
					List<BusOwnerUserIndex> busOwnerUserIndexList = busOwnerService.getBusOwnerUserIndexList(busIncome.getBusId(), 3, -2, -1, -1, 0); // 上一轮的萌主
					if(StringUtil.isNullOrEmpty(busOwnerUserIndexList)) { // 萌主还未诞生
						busOwnerUserIndexList = busOwnerService.getBusOwnerUserIndexList(busIncome.getBusId(), 3, -1, 0, 0, 1); // 实时机主指数排名前三的用户
					}
					busOwnerRankings = addBusOwnerRanking(busOwnerRankings, busOwnerUserIndexList);
					
					busOwnerItem.setDollBus(dollBusMap.get(busIncome.getBusId()));
					busOwnerItem.setBusOwnerRankings(busOwnerRankings);
					busOwnerItem.setRankingDesc("金币收益NO." + rank);
					busOwnerItems.add(busOwnerItem);
					incomeBusId.add(busIncome.getBusId());
					rank++;
				}
			}
			if(incomeBusId.size() < mdDollBusIds.size()) { // 处理还没有收益的萌店
				for(Integer busId : mdDollBusIds) {
					if(!incomeBusId.contains(busId)) {
						List<BusOwnerUserIndex> busOwnerUserIndexList = busOwnerService.getBusOwnerUserIndexList(busId, 3, -2, -1, -1, 0); // 上一轮的萌主
						BusOwnerItem busOwnerItem = new BusOwnerItem();
						if(!StringUtil.isNullOrEmpty(busOwnerUserIndexList)) {
							List<BusOwnerRanking> busOwnerRankings = new ArrayList<BusOwnerRanking>();
							busOwnerRankings = addBusOwnerRanking(busOwnerRankings, busOwnerUserIndexList);
							busOwnerItem.setDollBus(dollBusMap.get(busId));
							busOwnerItem.setBusOwnerRankings(busOwnerRankings);
							busOwnerItem.setRankingDesc("金币收益NO." + rank);
							busOwnerItems.add(busOwnerItem);
						} else {
							busOwnerItem = getNoIncomeBusOwnerItem(busId, rank, dollBusMap.get(busId));
							busOwnerItems.add(busOwnerItem);
						}
						rank++;
					}
				}
			}
		}
		return busOwnerItems;
	}

	private List<BusOwnerRanking> addBusOwnerRanking(List<BusOwnerRanking> busOwnerRankings, List<BusOwnerUserIndex> busOwnerUserIndexList) {
		if(!StringUtil.isNullOrEmpty(busOwnerUserIndexList)) {
			final int SIZE = busOwnerUserIndexList.size();
			for(int i = 0; i < SIZE; i++) {
				String rewardDesc = "奖励" + new Double(RewardRatioEnm.getRewardRatioEnm(i+1).getRatio()*100).intValue() + "%";
				BusOwnerRanking busOwnerRanking = new BusOwnerRanking(userService.getUserGeneral(busOwnerUserIndexList.get(i).getUid()), rewardDesc);
				busOwnerRankings.add(busOwnerRanking);
			}
		}
		if(busOwnerRankings.size() < 3 && busOwnerRankings.size() > 0) {
			for(int i = 3-busOwnerRankings.size(); i > 0; i--) {
				String rewardDesc = "";
				if(i == 1) {
					rewardDesc = "奖励" + new Double(RewardRatioEnm.getRewardRatioEnm(3).getRatio()*100).intValue() + "%";
				} else if(i == 2) {
					rewardDesc = "奖励" + new Double(RewardRatioEnm.getRewardRatioEnm(2).getRatio()*100).intValue() + "%";
				}
				BusOwnerRanking busOwnerRanking = new BusOwnerRanking(rewardDesc, "虚位以待", CommConstants.DEFAULT_HEAD);
				busOwnerRankings.add(busOwnerRanking);
			}
		}
		return busOwnerRankings;
	}

	private BusOwnerItem getNoIncomeBusOwnerItem(Integer busId, int rank, DollBus dollBus) {
		BusOwnerItem busOwnerItem = new BusOwnerItem();
		List<BusOwnerRanking> busOwnerRankings = new ArrayList<BusOwnerRanking>();
		for(int i = 0; i < 3; i++) {
			String rewardDesc = "奖励" + new Double(RewardRatioEnm.getRewardRatioEnm(i+1).getRatio()*100).intValue() + "%";
			BusOwnerRanking busOwnerRanking = new BusOwnerRanking(rewardDesc, "虚位以待", CommConstants.DEFAULT_HEAD);
			busOwnerRankings.add(busOwnerRanking);
		}
		busOwnerItem.setDollBus(dollBus);
		busOwnerItem.setBusOwnerRankings(busOwnerRankings);
		busOwnerItem.setRankingDesc("金币收益NO." + rank);
		return busOwnerItem;
	}

}
