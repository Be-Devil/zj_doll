package com.imlianai.zjdoll.app.modules.core.doll.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
@Service
public class DollInfoServiceImpl implements DollInfoService {

	@Resource
	private DollInfoDao dollInfoDao;

	private Map<Integer, DollInfo> dollInfoMap = null;
	
	private List<DollInfo> composeDolls = new ArrayList<DollInfo>(); // 可合成的娃娃列表
	
	private List<DollInfo> exchangeDolls = new ArrayList<DollInfo>(); // 可钻石兑换的娃娃列表

	private static Date refreshTime = new Date();
	@Resource
	DollBusService dollBusService;
	/**
	 * 获取内存中娃娃对象
	 * 
	 * @param dollId
	 * @return
	 */
	private DollInfo getDollInfoCache(int dollId) {
		if (StringUtil.isNullOrEmpty(dollInfoMap)||DateUtils.secondBetween(refreshTime) >= 5) {
			initDollInfos();
		}
		if (!StringUtil.isNullOrEmpty(dollInfoMap)) {
			return dollInfoMap.get(dollId);
		}
		return null;
	}

	@Override
	public DollInfo getDollInfo(int dollId) {
		return dollInfoDao.getDollInfo(dollId);
	}

	@Override
	public void initDollInfos() {
		List<DollInfo> infos = dollInfoDao.getAllDollInfos();
		if (!StringUtil.isNullOrEmpty(infos)) {
			Map<Integer, DollInfo> dollInfoMapTmp = new HashMap<Integer, DollInfo>();
			List<DollInfo> composeDollsTmp = new ArrayList<DollInfo>(); // 可碎片合成的娃娃列表
			List<DollInfo> exchangeDollsTmp = new ArrayList<DollInfo>(); // 可钻石兑换的娃娃列表
			for (DollInfo dollInfo : infos) {
				dollInfoMapTmp.put(dollInfo.getDollId(), dollInfo);
				if(dollInfo.getType() == 1 && dollInfo.getInventory() > 0 
						&& dollInfo.getValid() == 1) {
					composeDollsTmp.add(dollInfo);
				}
				if(dollInfo.getType() == 2 && dollInfo.getInventory() > 0
						&& dollInfo.getValid() == 1) {
					exchangeDollsTmp.add(dollInfo);
				}
			}
			dollInfoMap = dollInfoMapTmp;
			composeDolls = composeDollsTmp;
			exchangeDolls = exchangeDollsTmp;
		}
		refreshTime = new Date();
	}

	@Override
	public Map<Integer, DollInfo> getDollInfos(List<Integer> dollIds) {
		Map<Integer, DollInfo> resMap = new HashMap<Integer, DollInfo>();
		if (!StringUtil.isNullOrEmpty(dollIds)) {
			for (Integer dollId : dollIds) {
				DollInfo dollInfo = getDollInfoCache(dollId);
				if (!StringUtil.isNullOrEmpty(dollInfo)) {
					resMap.put(dollInfo.getDollId(), dollInfo);
				}
			}
		}
		return resMap;
	}

	@Override
	public Map<Integer, DollInfo> getDollInfosNoCache(List<Integer> dollIds) {
		Map<Integer, DollInfo> resMap = new HashMap<Integer, DollInfo>();
		if (!StringUtil.isNullOrEmpty(dollIds)) {
//			for (Integer dollId : dollIds) {
//				DollInfo dollInfo = getDollInfoCache(dollId);
//				if (!StringUtil.isNullOrEmpty(dollInfo)) {
//					resMap.put(dollInfo.getDollId(), dollInfo);
//				}
//			}
		}
		return resMap;
	}

	@Override
	public void updateDollGoodsId(int dollId, String goodsId) {
		dollInfoDao.updateDollGoodsId(dollId, goodsId);
	}
	
	@Override
	public List<DollInfo> getComposeDolls() {
		return composeDolls;
	}

	@Override
	public List<DollInfo> getExchangeDolls() {
		return exchangeDolls;
	}

	@Override
	public DollInfo getDollInfoByBusId(int busId) {
		DollBus bus=dollBusService.getDollBus(busId);
		if (bus!=null&&bus.getDollId()>-1) {
			DollInfo dollInfo=getDollInfo(bus.getDollId());
			return dollInfo;
		}
		return null;
	}

	@Override
	public int updateDollValidById(int dollId, int valid) {
		if(dollInfoDao.updateDollValidById(dollId, valid) > 0) {
			initDollInfos();
			return 1;
		}
		return 0;
	}

	@Override
	public int updateUserDollLastExchangeTime(int dollId) {
		return dollInfoDao.updateUserDollLastExchangeTime(dollId);
	}

	@Override
	public List<DollInfo> getRecentExchangeDollInfos(int size) {
		return dollInfoDao.getRecentExchangeDollInfos(size);
	}

	@Override
	public List<DollInfo> getRecentComposeDollInfos(int size) {
		return dollInfoDao.getRecentComposeDollInfos(size);
	}
	
	@Override
	public int updateUserDollLastComposeTime(int dollId) {
		return dollInfoDao.updateUserDollLastComposeTime(dollId);
	}

	@Override
	public int updateDollExchangeNum(int dollId) {
		return dollInfoDao.updateDollExchangeNum(dollId);
	}

	@Override
	public List<DollInfo> getDollInfoListByDollIds(List<Integer> dollIds) {
		List<DollInfo> dollInfos = new ArrayList<DollInfo>();
		if(!StringUtil.isNullOrEmpty(dollIds)) {
			for(Integer dollId : dollIds) {
				DollInfo dollInfo = getDollInfoCache(dollId);
				if (dollInfo != null){
					dollInfos.add(dollInfo);
				}
			}
		}
		return dollInfos;
	}
}
