package com.imlianai.dollpub.app.modules.core.doll.info;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;

import com.imlianai.rpc.support.common.BaseLogger;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.PropertUtil;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class DollInfoServiceImpl implements DollInfoService {

	private BaseLogger logger = BaseLogger.getLogger(getClass());

	@Resource
	private DollInfoDao dollInfoDao;

	private Map<Integer, DollInfo> dollInfoMap = null;

	private List<DollInfo> composeDolls = new ArrayList<DollInfo>(); // 可合成的娃娃列表

	private List<DollInfo> exchangeDolls = new ArrayList<DollInfo>(); // 可钻石兑换的娃娃列表

	private static Date refreshTime = new Date();

	/**
	 * 获取内存中娃娃对象
	 * 
	 * @param dollId
	 * @return
	 */
	private DollInfo getDollInfoCache(int dollId) {
		if (StringUtil.isNullOrEmpty(dollInfoMap) || DateUtils.secondBetween(refreshTime) >= 5) {
			initDollInfos();
		}
		if (!StringUtil.isNullOrEmpty(dollInfoMap)) {
			return dollInfoMap.get(dollId);
		}
		return null;
	}

	@Override
	public DollInfo getDollInfo(int dollId) {
		DollInfo dollInfo = this.getDollInfoCache(dollId);
		if(dollInfo != null){
			return dollInfo;
		}
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
				if (dollInfo.getType() == 1 && dollInfo.getInventory() > 0
						&& dollInfo.getValid() == 1) {
					composeDollsTmp.add(dollInfo);
				}
				if (dollInfo.getType() == 2 && dollInfo.getInventory() > 0
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
	public void updateDollGoodsId(int dollId, String goodsId) {
		dollInfoDao.updateDollGoodsId(dollId, goodsId);
	}

	@Override
	public List<DollInfo> getComposeDolls() {
		return composeDolls;
	}

	@Override
	public List<DollInfo> getExchangeDolls(int customerId) {
		List<DollInfo> resDollInfos=new ArrayList<DollInfo>();
		if (!StringUtil.isNullOrEmpty(exchangeDolls)) {
			for (DollInfo doll : exchangeDolls) {
				if (doll.getCustomerId()==customerId) {
					resDollInfos.add(doll);
				}
			}
		}
		return resDollInfos;
	}

	@Override
	public Map<Integer, DollInfo> getDollInfos(List<?> list, String fieldNames) {
		Map<Integer, DollInfo> map = new ConcurrentHashMap<>();
		try{
			if (!StringUtil.isNullOrEmpty(list)) {
				Set<Integer> dollIds = new CopyOnWriteArraySet<>();
				for (Object object : list) {
					Object obj = PropertUtil.getFieldValue(object, fieldNames);
					if (obj != null && obj instanceof Integer) {
						dollIds.add((Integer) obj);
					}
				}
				if(!StringUtil.isNullOrEmpty(dollIds)) {
					map = this.getDollInfos(new CopyOnWriteArrayList<>(dollIds));
				}
			}
		}catch (Exception e){
			logger.info(e.getMessage());
		}
		return map;
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
	public List<DollInfo> getRecentExchangeDollInfos(int customerId,int size) {
		return dollInfoDao.getRecentExchangeDollInfos(customerId,size);
	}
	
	@Override
	public List<DollInfo> getRecentComposeDollInfos(int size) {
		return dollInfoDao.getRecentComposeDollInfos(size);
	}
	
	@Override
	public int updateUserDollLastComposeTime(int dollId) {
		return dollInfoDao.updateUserDollLastComposeTime(dollId);
	}
}
