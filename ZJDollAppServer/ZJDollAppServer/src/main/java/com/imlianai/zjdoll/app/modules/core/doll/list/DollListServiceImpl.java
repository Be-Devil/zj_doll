package com.imlianai.zjdoll.app.modules.core.doll.list;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollBus.DollBusCompany;
import com.imlianai.zjdoll.domain.doll.DollBus.DollBusStatus;
import com.imlianai.zjdoll.domain.doll.DollClassify;
import com.imlianai.zjdoll.domain.doll.DollClassifyRes;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.user.UserDoll;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.doll.service.DollService;
import com.imlianai.zjdoll.app.modules.core.doll.utils.DollUtil;
import com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo.QiyiguoMachine;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.ZengjingUtils;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.domain.ZengjingDollBusDTO;
import com.imlianai.zjdoll.app.modules.support.busowner.service.BusOwnerService;

@Service
public class DollListServiceImpl implements DollListService {
	protected final BaseLogger logger = BaseLogger.getLogger(this.getClass());
	@Resource
	DollListDao dollListDao;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	DollService dollService;
	@Resource
	DollBusService dollBusService;
	@Resource
	BusOwnerService busOwnerService;
	private Map<String, QiyiguoMachine> machineStatusMap = null;

	private Map<String,ZengjingDollBusDTO> zengjingMachineStatusMap = null;
	
	private ConcurrentHashMap<Integer, DollBus> dollBusMap = null;

	private static Date refreshTime = new Date();
	private static Date zengjingRefreshTime = new Date();
	private static Date appRefreshTime = new Date();

	private static Date queryRefreshTime = new Date();
	
	private static ConcurrentHashMap<Integer, String> dollBusQueryMap=new ConcurrentHashMap<Integer, String>();
	
	/**
	 * 初始化搜索
	 * @param list
	 */
	public void initQueryMap(List<DollBus> list){
		if (!StringUtil.isNullOrEmpty(list)&&DateUtils.secondBetween(queryRefreshTime)>10) {
			List<DollClassify> classifyList=dollListDao.getAllClassify();
			Map<Integer, String> tagMap=new HashMap<Integer, String>();
			if (!StringUtil.isNullOrEmpty(classifyList)) {
				for (DollClassify dollClassify : classifyList) {
					if (dollClassify != null) {
						String busIds = dollClassify.getBusIds();
						if (busIds != null) {
							try {
								List<Integer> busIdArrIntegers = JSON.parseArray(busIds,
										Integer.class);
								if (!StringUtil.isNullOrEmpty(busIdArrIntegers)) {
									for (Integer busid : busIdArrIntegers) {
										String tagString=tagMap.get(busid);
										if (StringUtil.isNullOrEmpty(tagString)) {
											if (!StringUtil.isNullOrEmpty(dollClassify.getName())) {
												tagMap.put(busid, dollClassify.getName());
											}
										}else{
											tagMap.put(busid, tagString+","+(dollClassify.getName()==null?"":dollClassify.getName()));
										}
									}
								}
							} catch (Exception e) {
								PrintException.printException(logger, e);
							}
						}
					}
				}
				
			}
			for (DollBus dollBus : list) {
				try {
					String keyword=dollBus.searchWord()==null?"":dollBus.searchWord();
					String tagString=tagMap.get(dollBus.getBusId());
					if (!StringUtil.isNullOrEmpty(tagString)) {
						keyword=keyword+tagString;
					}
					dollBusQueryMap.put(dollBus.getBusId(), keyword);
					logger.info("initQueryMap busid:"+dollBus.getBusId()+" keyword:"+keyword);
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}
	}
	
	
	@Override
	public List<DollBus> getBusList(Integer type) {
		List<DollBus> res = new ArrayList<DollBus>();
		List<DollBus> buses = dollBusService.getDollBus(type);
		if (!StringUtil.isNullOrEmpty(buses)) {
			List<Integer> dollIds = new ArrayList<Integer>();
			for (DollBus dollBus : buses) {
				dollIds.add(dollBus.getDollId());
				if (StringUtil.isNullOrEmpty(dollBus.getConversationId())) {
					dollBus = dollService.initBusConversationId(dollBus);
				}
			}
			Map<Integer, DollInfo> infos = dollInfoService
					.getDollInfos(dollIds);
			for (DollBus dollBus : buses) {
				DollInfo info = infos.get(dollBus.getDollId());
				if (!StringUtil.isNullOrEmpty(info)) {
					dollBus.setDollInfo(info);
					dollBus.setName(busOwnerService.getBusName(info, dollBus));
					//娃娃名字作为搜索关键词
					dollBus.setKeyWord(info.getName());
					try {
						if (DollBusCompany.ZENGJING.type == dollBus
								.getDeviceCompany()) {
							ZengjingDollBusDTO zjBusDTO=getZengjingMachineByDeviceId(dollBus.getDeviceId(), false);
							if (zjBusDTO != null &&zjBusDTO.getStatus()<2) {
								dollBus.setStatus(zjBusDTO.getStatus());
								res.add(dollBus);
							}else{
								dollBus.setStatus(2);
								res.add(dollBus);
							}
						}
					} catch (Exception e) {
						PrintException.printException(logger, e);
					}
				}
			}
		}
		initQueryMap(res);
		return res;
	}

	private void syncDollBusMap(List<DollBus> list) {
		if (dollBusMap == null || DateUtils.secondBetween(appRefreshTime) >= 5) {
			appRefreshTime = new Date();
			ConcurrentHashMap<Integer, DollBus> dollBusMapTmp = new ConcurrentHashMap<Integer, DollBus>();
			if (!StringUtil.isNullOrEmpty(list)) {
				for (DollBus dollBus : list) {
					dollBusMapTmp.put(dollBus.getBusId(), dollBus);
				}
			}
			dollBusMap = dollBusMapTmp;
		}
	}

	@Override
	public void refreshBusStatus() {
		refreshTime = new Date();
		List<QiyiguoMachine> list = DollUtil.getDeviceList();
		if (!StringUtil.isNullOrEmpty(list)) {
			Map<String, QiyiguoMachine> tmpMap = new HashMap<String, QiyiguoMachine>();
			for (QiyiguoMachine qiyiguoMachine : list) {
				tmpMap.put(qiyiguoMachine.getDevice_id(), qiyiguoMachine);
			}
			if (machineStatusMap == null) {
				machineStatusMap = new ConcurrentHashMap<String, QiyiguoMachine>();
			}
			machineStatusMap.clear();
			machineStatusMap.putAll(tmpMap);
		}else {
			machineStatusMap.clear();
		}
	}
	private void refreshZengjingBusStatus() {
		zengjingRefreshTime = new Date();
		List<ZengjingDollBusDTO> list = ZengjingUtils.getList() ;
		if (!StringUtil.isNullOrEmpty(list)) {
			Map<String, ZengjingDollBusDTO> tmpMap = new HashMap<String, ZengjingDollBusDTO>();
			for (ZengjingDollBusDTO zjBusDTO : list) {
				tmpMap.put(zjBusDTO.getBusId()+"", zjBusDTO);
			}
			if (zengjingMachineStatusMap == null) {
				zengjingMachineStatusMap = new ConcurrentHashMap<String, ZengjingDollBusDTO>();
			}
			zengjingMachineStatusMap.clear();
			zengjingMachineStatusMap.putAll(tmpMap);
		}else {
			zengjingMachineStatusMap.clear();
		}
	}

	/**
	 * 获取娃娃机状态
	 * 
	 * @param deviceId
	 * @return
	 */
	@Override
	public QiyiguoMachine getQiyiguoMachineByDeviceId(String deviceId,
			boolean refresh) {
		if (machineStatusMap == null || refresh
				|| DateUtils.secondBetween(refreshTime) >= 5) {
			refreshBusStatus();
		}
		if (machineStatusMap != null) {
			return machineStatusMap.get(deviceId);
		}
		return null;
	}

	/**
	 * 获取娃娃机状态
	 * 
	 * @param deviceId
	 * @return
	 */
	private ZengjingDollBusDTO getZengjingMachineByDeviceId(String deviceId,
			boolean refresh) {
		if (zengjingMachineStatusMap == null || refresh
				|| DateUtils.secondBetween(zengjingRefreshTime) >= 5) {
			refreshZengjingBusStatus();
		}
		if (zengjingMachineStatusMap != null) {
			return zengjingMachineStatusMap.get(deviceId);
		}
		return null;
	}
	
	@Override
	public List<UserDoll> getDollListByPage(Long uid, long lastId,
			int pageSize, int status) {
		return dollListDao.getDollListByPage(uid, lastId, pageSize, status);
	}

	@Override
	public DollBus getDollBus(int busId, boolean refresh) {
		DollBus dollBus = dollBusService.getDollBus(busId);
		logger.info("getDollBus dollBus:"+JSON.toJSONString(dollBus));
		if (dollBus != null) {
			if (DollBusCompany.QIYIGUO.type == dollBus.getDeviceCompany()) {
				QiyiguoMachine machine = getQiyiguoMachineByDeviceId(
						dollBus.getDeviceId(), refresh);
				if (machine != null) {
					dollBus.setStatus(machine.getStatus());
				}else{
					dollBus.setStatus(DollBusStatus.MAINTAIN.type);
				}
				return dollBus;
			}else if (DollBusCompany.ZENGJING.type == dollBus
					.getDeviceCompany()) {
				ZengjingDollBusDTO zjBusDTO=getZengjingMachineByDeviceId(dollBus.getDeviceId(), refresh);
				if (zjBusDTO != null &&zjBusDTO.getStatus()<2) {
					dollBus.setStatus(zjBusDTO.getStatus());
				}else{
					dollBus.setStatus(DollBusStatus.MAINTAIN.type);
				}
				return dollBus;
			}
		}
		logger.info("getDollBus null "+JSON.toJSONString(dollBus));
		return null;
	}

	@Override
	public List<DollBus> getDollBusByClassify(int classify) {
		List<Integer> list = dollListDao.getBusIdsByClassify(classify);
		if (!StringUtil.isNullOrEmpty(list)) {
			 List<DollBus> res=getDollBus(list);
			 if (!StringUtil.isNullOrEmpty(res)) {
				return res;
			}
		}
		return null;
	}

	@Override
	public List<DollBus> getDollBus(List<Integer> busIds) {
		List<DollBus> resBus = new ArrayList<DollBus>();
		if (!StringUtil.isNullOrEmpty(busIds)) {
			if (dollBusMap == null
					|| DateUtils.secondBetween(appRefreshTime) >= 5) {
				List<DollBus> cacheBus = getBusList(null);
				syncDollBusMap(cacheBus);
			}
			for (Integer busId : busIds) {
				DollBus bus = dollBusMap.get(busId);
				if (bus != null) {
					resBus.add(bus);
				}
			}
		}
		return resBus;
	}

	@Override
	public List<DollClassifyRes> getDollClassifies() {
		return dollListDao.getDollClassifies();
	}


	@Override
	public List<Integer> searchBus(String keyword) {
		List<Integer> busIds=new ArrayList<Integer>();
		if (StringUtil.isNullOrEmpty(dollBusQueryMap)) {
			getBusList(null);
		}
		if (!StringUtil.isNullOrEmpty(dollBusQueryMap)) {
			for (Integer busId : dollBusQueryMap.keySet()) {
				String value=dollBusQueryMap.get(busId);
				if (value!=null&&value.contains(keyword)) {
					busIds.add(busId);
				}
			}
		}
		return busIds;
	}


	@Override
	public List<UserDoll> getDollListByPageAfter130(Long uid, long lastId, int pageSize, int status) {
		return dollListDao.getDollListByPageAfter130(uid, lastId, pageSize, status);
	}

}
