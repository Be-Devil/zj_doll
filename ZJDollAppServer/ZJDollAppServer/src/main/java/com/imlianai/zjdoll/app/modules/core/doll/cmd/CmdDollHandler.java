package com.imlianai.zjdoll.app.modules.core.doll.cmd;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.imlianai.zjdoll.app.modules.core.doll.dto.DollBusDto;
import com.imlianai.zjdoll.app.modules.core.push.service.PushCoinService;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.banner.Banner;
import com.imlianai.zjdoll.domain.banner.Banner.BannerLocationType;
import com.imlianai.zjdoll.domain.banner.BannerRes;
import com.imlianai.zjdoll.domain.busowner.BusOwner;
import com.imlianai.zjdoll.domain.busowner.BusOwnerBusIncome;
import com.imlianai.zjdoll.domain.doll.BusOperatingRecord;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollBus.DollBusCompany;
import com.imlianai.zjdoll.domain.doll.DollBus.DollBusStatus;
import com.imlianai.zjdoll.domain.doll.DollClassifyRes;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.DollRewardRecord;
import com.imlianai.zjdoll.domain.msg.MsgRoomJump;
import com.imlianai.zjdoll.domain.msg.MsgRoomType;
import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserCommon;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.domain.user.UserSimple;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.NotEnoughBeanException;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollListService;
import com.imlianai.zjdoll.app.modules.core.doll.pattern.service.DollBusPatternService;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.core.doll.service.DollService;
import com.imlianai.zjdoll.app.modules.core.doll.utils.DollUtil;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.ZengjingUtils;
import com.imlianai.zjdoll.app.modules.core.doll.vo.BusAbandonReqVo;
import com.imlianai.zjdoll.app.modules.core.doll.vo.BusAbandonRespVo;
import com.imlianai.zjdoll.app.modules.core.doll.vo.BusEnterRespVO;
import com.imlianai.zjdoll.app.modules.core.doll.vo.BusWatchersRespVO;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollApplyReqVO;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollApplyRes;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollApplyRespVO;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollClassifyReqVO;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollInfoReqVO;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollInfoRespVO;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollListReqVO;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollListRespVO;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollOperateReqVO;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.domain.UserFirstChargeTarget;
import com.imlianai.zjdoll.app.modules.core.trade.catalog.service.ChargeCatalogService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.support.banner.service.BannerService;
import com.imlianai.zjdoll.app.modules.support.busowner.service.BusOwnerService;
import com.imlianai.zjdoll.app.modules.support.redpacket.service.RedpacketService;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BusRedpacket;
import com.imlianai.zjdoll.app.modules.support.version.service.VersionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Component("doll")
@Api("娃娃机相关")
public class CmdDollHandler extends RootCmd {

	@Resource
	DollListService dollListService;
	@Resource
	DollRecordService dollRecordService;
	@Resource
	DollService dollService;
	@Resource
	TradeService tradeService;
	@Resource
	BannerService bannerService;
	@Resource
	DollInfoService dollInfoService;
	@Resource
	DollBusService dollBusService;
	@Resource
	UserService userService;
	@Resource
	DollBusPatternService dollBusPatternService;
	@Resource
	RedpacketService redpacketService;
	@Resource
	TradeChargeService tradeChargeService;
	@Resource
	VersionService versionService;
	@Resource
	ChargeCatalogService chargeCatalogService;
	@Resource
	BusOwnerService busOwnerService;

	@Resource
	private PushCoinService pushCoinService;

	@ApiHandle
	@LogHead("获取娃娃机列表接口")
	@Path("api/doll/list")
	@ApiOperation(position = 1, value = "【1.0.0】1、获取娃娃机列表接口", notes = "获取娃娃机列表", httpMethod = "POST", response = DollListRespVO.class)
	public BaseRespVO list(DollListReqVO reqVO) {
		List<DollBus> list = null;
		if (reqVO.getType()==0) {
			list = dollListService.getBusList(null);
		}else {
			list = dollListService.getDollBusByClassify(reqVO.getType());
		}
		DollListRespVO respVO = new DollListRespVO();
		if (!StringUtil.isNullOrEmpty(list)) {
			respVO.setList(list);
		}
		List<DollClassifyRes> classifyList=dollListService.getDollClassifies();
		if (!StringUtil.isNullOrEmpty(classifyList)) {
			respVO.setClassifyList(classifyList);
		}
		return respVO;
	}

	@ApiHandle
	@LogHead("获取娃娃机分类清单接口")
	@Path("api/doll/classify")
	@ApiOperation(position = 1, value = "【1.0.0】1、获取娃机分类清单接口", notes = "获取娃机分类清单", httpMethod = "POST", response = DollListRespVO.class)
	public BaseRespVO classify(DollClassifyReqVO reqVO) {
		DollListRespVO respVO = new DollListRespVO();
		List<DollClassifyRes> classifyList=dollListService.getDollClassifies();
		if (!StringUtil.isNullOrEmpty(classifyList)) {
			respVO.setClassifyList(classifyList);
		}
		return respVO;
	}
	
	@ApiHandle
	@LogHead("进入娃娃机房间接口")
	@Path("api/doll/enter")
	@LoginCheck
	@ApiOperation(position = 2, value = "【1.0.0】进入娃娃机房间接口", notes = "进入娃娃机房间,特殊返回码146-娃娃机维护中，去试试其他机器吧", httpMethod = "POST", response = BusEnterRespVO.class)
	public BaseRespVO enter(DollApplyReqVO reqVO) {
		// 娃娃机信息
		DollBus bus = dollListService.getDollBus(reqVO.getBusId(), false);
		BusEnterRespVO respVO = new BusEnterRespVO();
		if (bus != null) {
			DollInfo info = dollInfoService.getDollInfo(bus.getDollId()); // 娃娃信息
			bus.setImgRoomDialog(info.getImgRoomDialog()); // 进入房间弹框图片
			bus.setGoodsType(info.getGoodsType());
			bus.setSpecType(info.getSpecType());
			String name = busOwnerService.getNotHandledBusName(info, bus);
			bus.setName(name);
			respVO.setInfo(bus);
			dollService.enterBus(reqVO.getUid(), reqVO.getBusId());
			TradeAccount tradeAccount = tradeService.getAccount(reqVO.getUid());
			if (tradeAccount != null) {
				respVO.setCoin(tradeAccount.getCoin());
			}
			List<Banner> banners = bannerService.getBannerByLocation(
					reqVO.getUid(), BannerLocationType.BUS_FLOAT.type,
					reqVO.getOsType(), reqVO.getChannel(),reqVO.getLoginKey(),reqVO.getVersion());
			if (!StringUtil.isNullOrEmpty(banners)) {
				Banner bannerRes = banners.get(0);
				MsgRoomJump msgRoom = new MsgRoomJump(bus,
						MsgRoomType.DANMU.type, bannerRes.getFloatDesc());
				msgRoom.setJumpUrl(bannerRes.getUrl());
				msgRoom.setTargetTitle(bannerRes.getTitle());
				respVO.setAd(msgRoom);
			}
			banners = bannerService.getBannerByLocation(reqVO.getUid(),
					BannerLocationType.BUS_NOTICE.type, reqVO.getOsType(),
					reqVO.getChannel(),reqVO.getLoginKey(),reqVO.getVersion());
			if (!StringUtil.isNullOrEmpty(banners)) {
				Banner bannerRes = banners.get(0);
				MsgRoomJump msgRoom = new MsgRoomJump(bus,
						MsgRoomType.NORMAL_MSG.type, bannerRes.getFloatDesc());
				respVO.setBroad(msgRoom);
			}
			BusOperatingRecord record = dollBusService
					.getBusOperatingRecord(bus.getBusId());
			if (record != null) {
				UserGeneral user = userService.getUserGeneral(record.getUid());
				if (user != null) {
					respVO.setPlayer(new UserSimple(user));
				}
			}
			if (!StringUtil.isNullOrEmpty(bus.getPaster())) {
				respVO.setWallPaint(bus.getPaster());
			}
			boolean isOpen = false; // 是否开通周月卡
			if (versionService.isAuditChannel(reqVO.getChannel(), reqVO.getVersion())) {
				respVO.setMsgNum(0);
				respVO.setIsPay(true);
			}else{
				int payFlag=tradeChargeService.hasCharge(reqVO.getUid());
				respVO.setIsPay(payFlag==1?true:false);
				UserFirstChargeTarget code=tradeChargeService.getUserFirstChargeTarget(reqVO.getUid());
				if (code!=null&&code.getPushCount()>0&&code.getCode()>0) {
					respVO.setIsShowQPay(true);
				}
				isOpen = chargeCatalogService.isValid(reqVO.getUid());
				respVO.setMsgNum(20);
			}
			respVO.setIsOpen(isOpen);
			if(bus.getBusType() == DollBus.DollBusType.MENGDIAN.type) {
				BusOwner busOwner = busOwnerService.getBusOwnerCache(reqVO.getBusId());
				if(busOwner != null) {
					respVO.setBusOwnerRes(busOwnerService.getBusOwnerRes(busOwner.getUid()));
					if(reqVO.getUid().longValue() == busOwner.getUid()) {
						BusOwnerBusIncome busIncome = busOwnerService.getBusIncome(reqVO.getBusId());
						respVO.setWelcomes("喜迎主人回家~~娃娃机收益暂为" + (busIncome == null ? 0 : busIncome.getCoin()) + "币");
					} else {
						respVO.setWelcomes("欢迎来到 " + name);
					}
				}
				UserBase userBase = userService.getUserBase(reqVO.getUid());
				Map<String, String> busOwnerH5Map = busOwnerService.getBusOwnerH5Map(reqVO.getUid(), reqVO.getBusId());
				respVO.setBusOwnerUrl(busOwnerH5Map.get("busOwnerUrl") + "?uid=" + reqVO.getUid() + "&loginKey=" + userBase.getLoginKey() + "&busId=" + bus.getBusId());
				respVO.setBusOwnerTitle(busOwnerH5Map.get("busOwnerTitle"));
			}
		} else {
			return new BaseRespVO(ResCodeEnum.DOLL_BUS_MATIAN);
		}
		return respVO;
	}

	@ApiHandle
	@LogHead("申请上机接口")
	@Path("api/doll/apply")
	@LoginCheck
	@ApiOperation(position = 3, value = "【1.0.0】3、申请上机接口", notes = "申请上机,特殊返回码146-娃娃机维护中，去试试其他机器吧 147-有人先你一步上机，请稍后 148-上机失败", httpMethod = "POST", response = DollApplyRespVO.class)
	public BaseRespVO apply(DollApplyReqVO reqVO) {
		// 娃娃机信息
		DollBus bus = dollListService.getDollBus(reqVO.getBusId(), true);
		if (bus != null&&bus.getValid()==1) {
			DollInfo info = dollInfoService.getDollInfo(bus.getDollId());
			if(info.getGradeType() == 1 && tradeChargeService.hasCharge(reqVO.getUid()) == 0) { // 土豪类型
				return new BaseRespVO(ResCodeEnum.DOLL_BUS_MUST_RECHARGE);
			}
			if (bus.getStatus() == DollBusStatus.FREE.type) {
				boolean isContinue=false;
				BusOperatingRecord operatingRecord = dollBusService
						.getBusOperatingRecord(reqVO.getBusId());
				if (operatingRecord != null&&operatingRecord.getUid()!=reqVO.getUid().longValue()) {
					return new BaseRespVO(ResCodeEnum.DOLL_BUS_OCCUPY);
				}
				if (operatingRecord!=null&&operatingRecord.getUid()==reqVO.getUid().longValue()) {
					isContinue=true;
				}
				TradeRecord record = new TradeRecord(reqVO.getUid(), 0,
						TradeType.PLAY.type, reqVO.getBusId(), bus.getPrice(),
						TradeCostType.COST_COIN.type, "房间 " + info.getName());
				try {
					tradeService.consume(record);
				} catch (TradeOperationException e) {
					logger.info("上机交易失败 uid:"+reqVO.getUid()+" busId:"+reqVO.getBusId()+" "+e.getMessage());
					return new BaseRespVO(ResCodeEnum.JEWEL_TOO_LESS);
				} catch (NotEnoughBeanException e) {
					logger.info("上机余额不足 uid:"+reqVO.getUid()+" busId:"+reqVO.getBusId()+" "+e.getMessage());
					return new BaseRespVO(ResCodeEnum.JEWEL_TOO_LESS);
				}
				DollApplyRes opt = dollService.applyMachine(reqVO.getUid()
						.longValue(), bus);
				logger.info("apply opt:" + JSON.toJSONString(opt));
				if(opt == null) {
					return new BaseRespVO(ResCodeEnum.DOLL_BUS_APPLY_FAIL);
				}
				if (!opt.isSuccess()) {
					TradeRecord feedBack = new TradeRecord(reqVO.getUid(), 0,
							TradeType.PLAY_FAIL.type, reqVO.getBusId(),
							bus.getPrice(), TradeCostType.COST_COIN.type, "房间 "
									+ info.getName());
					try {
						tradeService.charge(feedBack);
					} catch (TradeOperationException e) {
						PrintException.printException(logger, e);
					}
					return new BaseRespVO(ResCodeEnum.DOLL_BUS_OCCUPY);
				} else {
					DollApplyRespVO respVO = new DollApplyRespVO();
					respVO.setOptId(opt.getRecord().getOptId());
					respVO.setLogKey(opt.getRecord().getLogId());
					respVO.setRemainSecond(30);
					respVO.setBusInfo(bus);
					if(reqVO.getVersion() >= 110) { // 版本1.1.0以上
						respVO.setReward(redpacketService.handleApplyReward(reqVO.getUid(), bus.getPrice()));
					}else{
						respVO.setReward("上机成功");
					}
					// 处理上机捉力逻辑
					dollRecordService.addUserContinueTime(reqVO.getUid(), !isContinue);
					// 萌店收入处理
					busOwnerService.handleBusIncome(bus.getBusId(), bus.getPrice(), 
							opt.getRecord().getOptId(), "上机成功收入+" + bus.getPrice() + "币");
					return respVO;
				}
			} else {
				return new BaseRespVO(ResCodeEnum.DOLL_BUS_OCCUPY);
			}
		} else {
			return new BaseRespVO(ResCodeEnum.DOLL_BUS_MATIAN);
		}
	}

	@ApiHandle
	@LogHead("操作娃娃机接口")
	@Path("api/doll/operate")
	@ApiOperation(position = 4, value = "【1.0.0】4、操作娃娃机接口", notes = "操作娃娃机 1-前进，2-后退，3,-左移，4-右移，5-抓取娃娃 6-前进终止，7-后退终止，8-左移终止，9-右移终止", httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO operate(DollOperateReqVO reqVO) {
		DollApplyRespVO respVO = new DollApplyRespVO();
		DollBus dollBus = dollListService.getDollBus(reqVO.getBusId(), false);
		if (dollBus!=null) {
			if (dollBus.getDeviceCompany()==DollBusCompany.QIYIGUO.type) {
				DollUtil.controlMachine(reqVO.getUid(), dollBus.getDeviceId(),
						reqVO.getOpt());
			}else if (dollBus.getDeviceCompany()==DollBusCompany.ZENGJING.type) {
				BusOperatingRecord operatingRecord = dollBusService
						.getBusOperatingRecordLocal(reqVO.getBusId());
				if (operatingRecord==null) {
					return respVO;
				}
				int action=7;
				//【1】向前移动，【2】向后移动，【3】向左移动，【4】向右移动，【6】下抓，【7】停止移动
				switch (reqVO.getOpt()) {
				case 1:
					action=2;
					break;
				case 2:
					action=1;
					break;
				case 3:
					action=3;
					break;
				case 4:
					action=4;
					break;
				case 5:
					action=6;
					break;
				default:
					action=7;
					break;
				}
				if (action==6) {
					dollBusPatternService.handleZengjingCatch(operatingRecord.getOptId(),reqVO, dollBus, operatingRecord);
				}else{
					ZengjingUtils.controlMachine(operatingRecord.getUid(), dollBus.getDeviceId(), operatingRecord.getLogId(), action);
				}
			}
		}
		return respVO;
	}

	@ApiHandle
	@LogHead("离开娃娃机房间接口")
	@Path("api/doll/leave")
	@ApiOperation(position = 5, value = "【1.0.0】5、离开娃娃机房间接口", notes = "离开娃娃机房间", httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO leave(DollApplyReqVO reqVO) {
		dollService.leaveBus(reqVO.getUid(), reqVO.getBusId(),false);
		return new BaseRespVO();
	}

	@ApiHandle
	@LogHead("放弃上机接口")
	@Path("api/doll/abandon")
	@ApiOperation(position = 6, value = "【1.0.0】6、放弃上机接口", notes = "放弃上机", httpMethod = "POST", response = BusAbandonRespVo.class)
	public BaseRespVO abandon(BusAbandonReqVo reqVO) {
		BusRedpacket busRedpacket=dollService.abandonMachine(reqVO.getUid(), reqVO.getBusId());
		if (busRedpacket!=null) {
			return new BusAbandonRespVo(busRedpacket);
		}
		return new BaseRespVO();
	}

	private final int pageSize = 10;

	@ApiHandle
	@LogHead("获取娃娃机详情接口")
	@Path("api/doll/info")
	@ApiOperation(position = 4, value = "【1.0.0】获取娃娃机详情接口", notes = "获取娃娃机详细信息（包括广告banner、捉取记录、娃娃图片）", httpMethod = "POST", response = DollInfoRespVO.class)
	public BaseRespVO info(DollInfoReqVO reqVO) {
		DollInfoRespVO respVO = new DollInfoRespVO();
		DollBus bus = dollListService.getDollBus(reqVO.getBusId(), false);
		if (bus != null) {
			int dollId = bus.getDollId();
			DollInfo dollInfo = dollInfoService.getDollInfo(dollId);
			if (!StringUtil.isNullOrEmpty(dollInfo)) {
				respVO.setDollInfo(dollInfo);
			}
		}
		List<BannerRes> banners = bannerService.getBanners(reqVO.getOsType(),
				reqVO.getVersion(), reqVO.getChannel(),
				BannerLocationType.BUS_BANNER.type,reqVO.getUid(),reqVO.getLoginKey());
		if (!StringUtil.isNullOrEmpty(banners)) {
			respVO.setBanners(banners);
		}
		List<DollRewardRecord> records = dollRecordService.getBusRewardRecords(
				reqVO.getBusId(), reqVO.getPage(), pageSize);
		if (!StringUtil.isNullOrEmpty(records)) {
			respVO.setRecords(records);
		}
		return respVO;
	}

	@ApiHandle
	@LogHead("查询娃娃机详情接口")
	@Path("api/doll/query")
	@ApiOperation(value = "【1.0.0】查询娃娃机详情接口", notes = "获取娃娃机详细信息（只包含流地址封面等简要信息）,特殊返回码 146-娃娃机维护中，去试试其他机器吧╮(￣▽￣)╭", httpMethod = "POST", response = BusEnterRespVO.class)
	public BaseRespVO query(DollApplyReqVO reqVO) {
		BusEnterRespVO respVO = new BusEnterRespVO();
		DollBus bus = dollListService.getDollBus(reqVO.getBusId(), false);
		if (bus != null) {
			respVO.setInfo(bus);
		} else {
			return new BaseRespVO(ResCodeEnum.DOLL_BUS_MATIAN);
		}
		return respVO;
	}

	@ApiHandle
	@LogHead("获取观众列表接口")
	@Path("api/doll/watchers")
	@ApiOperation(position = 4, value = "【1.0.0】获取观众列表接口", notes = "观众列表", httpMethod = "POST", response = BusWatchersRespVO.class)
	public BaseRespVO watchers(DollInfoReqVO reqVO) {
		BusWatchersRespVO respVO = new BusWatchersRespVO();
		List<UserCommon> watchers = dollRecordService.getWatchList(
				reqVO.getBusId(), reqVO.getPage(), pageSize);
		if (!StringUtil.isNullOrEmpty(watchers)) {
			respVO.setWatchers(watchers);
		}
		DollBus bus = dollListService.getDollBus(reqVO.getBusId(), false);
		if (bus!=null) {
			respVO.setWatcherNum(bus.getWatchNum());
		}
		return respVO;
	}

	@ApiHandle
	@LogHead("获取娃娃机列表接口")
	@Path("api/doll/getDollBusList")
	public BaseRespVO getDollBusList(){
		Map<String, Object> map = Maps.newHashMap();
		List<DollBus> dollBusList = dollBusService.getAllDollBus();
		List<DollBusDto> dollBusDtoList = Lists.newArrayList();
		if (!StringUtil.isNullOrEmpty(dollBusList)){
			for (DollBus dollBus : dollBusList){
				dollBusDtoList.add(DollBusDto.adapter(dollBus,pushCoinService.getPushCoinBox(dollBus.getBusId())));
			}
		}
		//机器列表
		map.put("busList",dollBusDtoList);
		return new BaseRespVO(map);
	}


}
