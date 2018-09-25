package com.imlianai.dollpub.app.modules.core.doll.list;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.doll.info.DollInfoService;
import com.imlianai.dollpub.app.modules.core.doll.service.DollService;
import com.imlianai.dollpub.app.modules.core.doll.utils.DollUtil;
import com.imlianai.dollpub.app.modules.core.doll.utils.qiyiguo.QiyiguoMachine;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.domain.doll.DollBus.DollBusCompany;
import com.imlianai.dollpub.domain.doll.DollInfo;
import com.imlianai.dollpub.domain.doll.user.UserDoll;
import com.imlianai.dollpub.machine.iface.domain.MachineDevice;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.StringUtil;

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
	private Map<String, QiyiguoMachine> machineStatusMap = null;

	private static Date refreshTime = new Date();

	@Override
	public List<DollBus> getBusList() {
		List<DollBus> res = new ArrayList<DollBus>();
		List<DollBus> buses =dollBusService.getDollBus();
		if (!StringUtil.isNullOrEmpty(buses)) {
			List<Integer> dollIds = new ArrayList<Integer>();
			List<Integer> busIds = new ArrayList<Integer>();
			for (DollBus dollBus : buses) {
				dollIds.add(dollBus.getDollId());
				busIds.add(dollBus.getBusId());
				if (StringUtil.isNullOrEmpty(dollBus.getConversationId())) {
					dollBus = dollService.initBusConversationId(dollBus);
				}
			}
			Map<Integer, MachineDevice> stateMap=dollBusService.getMachineDevice(busIds);
			Map<Integer, DollInfo> infos = dollInfoService
					.getDollInfos(dollIds);
			for (DollBus dollBus : buses) {
				DollInfo info = infos.get(dollBus.getDollId());
				if (!StringUtil.isNullOrEmpty(info)) {
					dollBus.setDollInfo(info);
					try {
						MachineDevice device=stateMap.get(dollBus.getBusId());
						if (device != null) {
							dollBus.setStatus(device.getStatus());
							res.add(dollBus);
						}
					} catch (Exception e) {
						PrintException.printException(logger, e);
					}
				}
			}
		}
		return res;
	}

	@Override
	public void refreshBusStatus() {
		List<QiyiguoMachine> list = DollUtil.getDeviceList();
		if (!StringUtil.isNullOrEmpty(list)) {
			if (machineStatusMap == null) {
				machineStatusMap = new HashMap<String, QiyiguoMachine>();
			}
			for (QiyiguoMachine qiyiguoMachine : list) {
				machineStatusMap.put(qiyiguoMachine.getDevice_id(),
						qiyiguoMachine);
			}
		}
		refreshTime = new Date();
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

	@Override
	public List<UserDoll> getDollListByPage(Long uid, long lastId,
			int pageSize, int status) {
		return dollListDao.getDollListByPage(uid, lastId, pageSize, status);
	}

	@Override
	public DollBus getDollBus(int busId, boolean refresh) {
		DollBus dollBus = dollBusService.getDollBus(busId);
		if (dollBus != null) {
			MachineDevice device=dollBusService.getMachineDevice(busId);
			if (device != null) {
				dollBus.setStatus(device.getStatus());
			}
		}
		return dollBus;
	}

}
