package com.imlianai.zjdoll.app.modules.core.doll.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.NotEnoughBeanException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.common.info.PackageMsg;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.dao.DollBusPatternDao;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.domain.DollBusStrongDetailRecord;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.service.DollBusPatternService;
import com.imlianai.zjdoll.app.modules.core.doll.utils.DollUtil;
import com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo.QiyiguoDeviceSetting;
import com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo.QiyiguoMachine;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.support.busowner.service.BusOwnerService;

@Component("busStat")
public class CmdBusStat extends RootCmd {

	@Resource
	DollBusPatternService dollBusPatternService;
	@Resource
	DollBusService dollBusService;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	TradeService tradeService;
	@Resource
	DollBusPatternDao dollBusPatternDao;
	@Resource
	BusOwnerService busOwnerService;
	
	@ApiHandle
	public BaseRespVO query(int busId, String start, String end) {
		List<DollBusStrongDetailRecord> list = dollBusPatternService
				.getDollBusStrongDetailRecord(busId, start, end);
		Map<String, Object> res = PackageMsg.getRightOperCode(1);
		res.put("list", list);
		return new BaseRespVO(res);
	}

	@ApiHandle
	public BaseRespVO list(int all) {
		List<DollBus> list = dollBusService.getCurrentDevice();
		List<DollBus> resl =new ArrayList<DollBus>();
		List<DollBus> innerList = dollBusService.getDollBus(null);
		Map<String, DollBus> busMap = new HashMap<String, DollBus>();
		if (!StringUtil.isNullOrEmpty(innerList)) {
			for (DollBus dollBus : innerList) {
				busMap.put(dollBus.getDeviceId(), dollBus);
			}
		}
		if (!StringUtil.isNullOrEmpty(list)) {
			for (DollBus dollBus : list) {
				if (dollBus != null
						&& !StringUtil.isNullOrEmpty(dollBus.getDeviceId())) {
					DollBus bus = busMap.get(dollBus.getDeviceId());
					if (bus != null) {
						DollInfo info=dollInfoService.getDollInfo(bus.getDollId());
						dollBus.setName(busOwnerService.getNotHandledBusName(info, dollBus));
						dollBus.setCover(info.getImgCover());
					}
					if (all==1) {
						resl.add(dollBus);
					}else {
						if (bus!=null) {
							resl.add(dollBus);
						}
					}
				}
			}
		}
		Map<String, Object> res = PackageMsg.getRightOperCode(1);
		res.put("list", resl);
		return new BaseRespVO(res);
	}

	@ApiHandle
	public BaseRespVO getDeviceStatus(String device) {
		BaseRespVO respVO = new BaseRespVO();
		QiyiguoMachine resMap = DollUtil.getDeviceStatus(device);
		respVO.setData(resMap);
		return respVO;
	}

	@ApiHandle
	public BaseRespVO setProbability(String device, int probability) {
		BaseRespVO respVO = new BaseRespVO();
		Map<String, Object> resMap = DollUtil.setProbability(device,
				probability);
		respVO.setData(resMap);
		return respVO;
	}

	@ApiHandle
	public BaseRespVO setMode(String device, int mode) {
		BaseRespVO respVO = new BaseRespVO();
		Map<String, Object> resMap = DollUtil.setMode(device, mode);
		respVO.setData(resMap);
		return respVO;
	}

	@ApiHandle
	public BaseRespVO setPlayTime(String device, int second) {
		BaseRespVO respVO = new BaseRespVO();
		Map<String, Object> resMap = DollUtil.setPlaytime(device, second);
		respVO.setData(resMap);
		return respVO;
	}

	@ApiHandle
	public BaseRespVO getDeviceSetting(String device) {
		BaseRespVO respVO = new BaseRespVO();
		QiyiguoDeviceSetting resMap = DollUtil.getDeviceSetting(device);
		respVO.setData(resMap);
		return respVO;
	}

	@ApiHandle
	public BaseRespVO setHoldingForce(String device,int strong,int week) {
		BaseRespVO respVO = new BaseRespVO();
		boolean res = DollUtil.setHoldingForce(device, strong, week);
		if (res) {
			return new BaseRespVO();
		}else{
			return new BaseRespVO(0,false,"设置失败");
		}
	}
	
	@ApiHandle
	public BaseRespVO dealCharge(String sign, long uid, int type,
			int tradeCode, int cost, int costType, String remark) {
		BaseRespVO respVO = new BaseRespVO();
		if (sign.equals("nmasdjaoibb12319")) {
			TradeRecord record = new TradeRecord(uid, uid, type, tradeCode,
					cost, costType, remark);
			try {
				tradeService.charge(record);
			} catch (TradeOperationException e) {
				return new BaseRespVO(0, false, "支付异常");
			}
		}else {
			return new BaseRespVO(0, false, "支付密码错误");
		}
		return respVO;
	}

	@ApiHandle
	public BaseRespVO dealConsume(String sign, long uid, int type,
			int tradeCode, int cost, int costType, String remark) {
		BaseRespVO respVO = new BaseRespVO();
		if (sign.equals("qez4893xnhsqesdjaapiui9")) {
			TradeRecord record = new TradeRecord(uid, uid, type, tradeCode,
					cost, costType, remark);
				try {
					tradeService.consume(record);
				} catch (TradeOperationException | NotEnoughBeanException e) {
					return new BaseRespVO(0, false, "该账户余额不足");
				}
		}else {
			return new BaseRespVO(0, false, "支付密码错误");
		}
		return respVO;
	}
	
	@ApiHandle
	public BaseRespVO resetProbability(String device) {
		BaseRespVO respVO = new BaseRespVO();
		List<DollBus> innerList = dollBusService.getDollBus(null);
		boolean isOk=false;
		if (!StringUtil.isNullOrEmpty(innerList)) {
			for (DollBus dollBus : innerList) {
				if (device.equals(dollBus.getDeviceId())) {
					int rand = new Random().nextInt(8);
					int probability=dollBus.getStrong()+rand;
					Map<String, Object> resMap = DollUtil.setProbability(device,
							probability);
					dollBusPatternDao.updateStrongFlag(dollBus.getBusId(),
							probability);
					dollBusPatternDao.resetStatRound(dollBus.getBusId());
					isOk=true;
					respVO.setData(resMap);
				}
			}
		}
		if (!isOk) {
			respVO=new BaseRespVO(0, false, "不存在对应机器");
		}
		return respVO;
	}
}
