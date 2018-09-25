package com.imlianai.zjdoll.app.modules.support.exchange.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.doll.DollRecycleRecord;
import com.imlianai.zjdoll.domain.doll.user.UserDollDebris;
import com.imlianai.zjdoll.domain.msg.Msg;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.PropertUtil;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.info.DollInfoService;
import com.imlianai.zjdoll.app.modules.core.user.value.UserValueDAO;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.exchange.dao.DollComposeDao;
import com.imlianai.zjdoll.app.modules.support.exchange.dao.DollExchangeDao;
import com.imlianai.zjdoll.app.modules.support.exchange.enm.DebrisTypeEnm;
import com.imlianai.zjdoll.app.modules.support.exchange.vo.ComposeDollInfo;
import com.imlianai.zjdoll.app.modules.support.exchange.vo.ComposeGetListRespVO;
import com.imlianai.zjdoll.app.modules.support.exchange.vo.ExchangeDollInfo;
import com.imlianai.zjdoll.app.modules.support.userdoll.dao.UserDollDao;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;


@Service
public class DollComposeServiceImpl implements DollComposeService {
	
	private static final BaseLogger LOG = BaseLogger.getLogger(DollComposeServiceImpl.class);
	
	@Resource
	DollInfoService dollInfoService;
	@Resource
	UserDollService userDollService;
	@Resource
	UserDollDao userDollDao;
	@Resource
	UserValueDAO userValueDAO;
	@Resource
	DollComposeDao dollComposeDao;
	@Resource
	DollExchangeDao dollExchangeDao;
	@Resource
	MsgService msgService;

	@SuppressWarnings("unchecked")
	@Override
	public ComposeGetListRespVO getList(Long uid) {
		ComposeGetListRespVO respVO = new ComposeGetListRespVO();
		// 用户娃娃碎片
		List<UserDollDebris> udollDebrisList = userDollService.getUserDollDebrisByUid(uid);
		if(!StringUtil.isNullOrEmpty(udollDebrisList)) {
			for(UserDollDebris uDollDebris : udollDebrisList) {
				if(uDollDebris.getType() == 0) { // 普通
					respVO.setCommNum(uDollDebris.getNum());
				} else if(uDollDebris.getType() == 1) { // 稀有
					respVO.setRareNum(uDollDebris.getNum());
				}
			}
		}
		// 获取可合成的娃娃列表
		List<DollInfo> composeDolls = dollInfoService.getComposeDolls();
		composeDolls = (List<DollInfo>) PropertUtil.doSeq(composeDolls, "sortIndex");
		List<ComposeDollInfo> dollInfos = new ArrayList<ComposeDollInfo>();
		if(!StringUtil.isNullOrEmpty(composeDolls)) {
			for(DollInfo dollInfo : composeDolls) {
				int exchangeNum = dollExchangeDao.getShoppingExchangeNum(dollInfo.getDollId());
				ComposeDollInfo cDollInfo = new ComposeDollInfo(dollInfo, dollInfo.getInventory()-exchangeNum);
				cDollInfo.setExchangedNum(exchangeNum);
				dollInfos.add(cDollInfo);
			}
		}
		respVO.setComposeDolls(dollInfos);
		return respVO;
	}

	@Override
	public BaseRespVO compose(Long uid, int dollId) {
		try {
			LOG.info("compose:uid-" + uid + ",dollId-" + dollId);
			DollInfo dollInfo = dollInfoService.getDollInfo(dollId);
			if(dollInfo == null) {
				return new BaseRespVO(-1, false, "娃娃不存在~");
			}
			if(dollInfo.getType() != 1) { 
				return new BaseRespVO(-1, false, "该娃娃不可合成~");
			}
			int exchangeNum = dollExchangeDao.getShoppingExchangeNum(dollInfo.getDollId());
			if(dollInfo.getInventory() == 0 || exchangeNum >= dollInfo.getInventory()) {
				dollInfoService.updateDollValidById(dollInfo.getDollId(), 0);
				return new BaseRespVO(600, false, "该娃娃库存为0，不可合成~");
			}
			int needCommNum = dollInfo.getCommDebrisNum();
			int needRareNum = dollInfo.getRareDebrisNum();
			// 用户娃娃碎片
			List<UserDollDebris> udollDebrisList = userDollService.getUserDollDebrisByUid(uid);
			int uCommNum = 0;
			int uRareNum = 0;
			if(!StringUtil.isNullOrEmpty(udollDebrisList)) {
				for(UserDollDebris uDollDebris : udollDebrisList) {
					if(uDollDebris.getType() == 0) { // 普通
						uCommNum = uDollDebris.getNum();
					} else if(uDollDebris.getType() == 1) { // 稀有
						uRareNum = uDollDebris.getNum();
					}
				}
			}
			if(needCommNum > uCommNum || needRareNum > uRareNum) {
				return new BaseRespVO(-1, false, "娃娃碎片不足，合成失败~");
			}
			for(DebrisTypeEnm debrisTypeEnm : DebrisTypeEnm.values()) {
				int num = 0;
				if(debrisTypeEnm.getType() == 0) {
					num = needCommNum;
				} else if(debrisTypeEnm.getType() == 1){
					num = needRareNum;
				}
				if(num > 0) {
					userDollService.saveOrUpdateUserDollDebris(uid, debrisTypeEnm.getType(), -num, "娃娃合成使用" + debrisTypeEnm.getDesc() + "*" + num);
				}
			}
			long udollId = userDollService.saveUserDoll(uid, dollInfo.getDollId(), 0l, "碎片合成(普通碎片*" + needCommNum + ",稀有碎片*" + needRareNum + ")", 1); // 保存用户娃娃
			dollExchangeDao.saveShoppingExchangeRecord(uid, udollId, 0, "使用普通碎片*" + needCommNum + ",稀有碎片*" + needRareNum + "合成娃娃", 1, needCommNum, needRareNum);
			dollExchangeDao.saveOrUpdateShoppingExchangeNum(dollInfo.getDollId());
			dollInfoService.updateDollExchangeNum(dollInfo.getDollId());
			dollInfoService.updateUserDollLastComposeTime(dollInfo.getDollId());
			if(exchangeNum + 1 == dollInfo.getInventory()) { // 下架
				dollInfoService.updateDollValidById(dollInfo.getDollId(), 0);
			}
			// 系统通知
			StringBuilder sb = new StringBuilder();
			if(needCommNum == 0 && needRareNum > 0) {
				sb.append("成功使用" + needRareNum + "块稀有娃娃碎片合成");
			} else if(needCommNum > 0 && needRareNum == 0) {
				sb.append("成功使用" + needCommNum + "块普通娃娃碎片合成");
			} else if(needCommNum > 0 && needRareNum > 0) {
				sb.append("成功使用" + needCommNum + "块普通娃娃碎片和" + needRareNum + "块稀有娃娃碎片合成");
			}
			sb.append(dollInfo.getName()+"。");
			Msg msg = new Msg(uid, MsgType.NOTICE_SYS.type, sb.toString());
			msgService.sendMsg(msg);
			return new BaseRespVO();
		} catch(Exception e) {
			PrintException.printException(LOG, e);
			return new BaseRespVO(-1, false, "系统异常，请重试~");
		}
	}

	@Override
	public int saveRecycleRecord(long uDollId, int jewel) {
		return dollComposeDao.saveRecycleRecord(uDollId, jewel);
	}

	@Override
	public DollRecycleRecord getRecycleRecordByUDollId(Long uDollId) {
		return dollComposeDao.getRecycleRecordByUDollId(uDollId);
	}

	@Override
	public BaseRespVO getDollDetail(int type, int id) {
		LOG.info("getDollDetail:type-" + type + ",id-" + id);
		BaseRespVO respVO = new BaseRespVO();
		DollInfo dollInfo = dollInfoService.getDollInfo(id);
		if(dollInfo != null) {
			if(type == 0) {
				ComposeDollInfo cDollInfo = new ComposeDollInfo(dollInfo.getDollId(), dollInfo.getName(), dollInfo.getImgCompose(), 
						dollInfo.getInventory(), dollInfo.getCommDebrisNum(), dollInfo.getRareDebrisNum(), dollInfo.getImgRoom());
				respVO.setData(cDollInfo);
			} else if(type == 1) {
				ExchangeDollInfo eDollInfo = new ExchangeDollInfo(dollInfo.getDollId(), dollInfo.getName(), dollInfo.getImgExchange(), 
						dollInfo.getInventory(), dollInfo.getJewelNum(), dollInfo.getImgRoom());
				respVO.setData(eDollInfo);
			}
		}
		return respVO;
	}

	@Override
	public List<ComposeDollInfo> getRecentComList() {
		List<ComposeDollInfo> cdolls = new ArrayList<ComposeDollInfo>();
		List<DollInfo> dollInfoList = dollInfoService.getRecentComposeDollInfos(6);
		if(!StringUtil.isNullOrEmpty(dollInfoList)) {
			for(DollInfo dollInfo : dollInfoList) {
				int exchangeNum = dollExchangeDao.getShoppingExchangeNum(dollInfo.getDollId());
				if(dollInfo.getInventory()-exchangeNum <= 0) continue;
				ComposeDollInfo cDollInfo = new ComposeDollInfo(dollInfo, dollInfo.getInventory()-exchangeNum);
				cDollInfo.setExchangedNum(exchangeNum);
				cdolls.add(cDollInfo);
				if(cdolls.size() == 3) break;
			}
		}
		return cdolls;
	}
}
