package com.imlianai.zjdoll.app.modules.support.event.wechat.service;

import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.trade.TradeCostType;
import com.imlianai.zjdoll.domain.trade.TradeRecord;
import com.imlianai.zjdoll.domain.trade.TradeType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserBase.UserSrcType;
import com.imlianai.rpc.support.common.exception.TradeOperationException;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.support.event.wechat.dao.WechatEventDao;

@Service
public class WechatEventServiceImpl implements WechatEventService {

	@Resource
	WechatEventDao wechatEventDao;
	@Resource
	UserService userService;
	@Resource
	TradeService tradeService;

	@Override
	public String getFollowMsg(String unionId, String toUserID) {
		int flag = wechatEventDao.hasGetReward(unionId);
		if (flag == 0) {
			return "输入“我爱萌趣抓娃娃”，领取免费游戏币。输入后获得10-20币";
		}
		return "么么哒，你终于来了！\n\n输入“红包”即可绑定微信提现账号";
	}

	@Override
	public String getMsgReward(String unionId, String openId) {
		int flag = wechatEventDao.hasGetReward(unionId);
		if (flag == 0) {
			UserBase userBase = userService.getUserBaseBySrc(
					UserSrcType.WECHAT.type, openId, unionId);
			if (userBase == null) {
				return "您尚未注册萌趣帐号,赶紧下载萌趣抓娃娃吧<a href='http://www.mengquww.com/download/gw'>打开萌趣</a>";
			} else {
				int reward = new Random().nextInt(10) + 10;
				if (reward > 0) {
					wechatEventDao.addGetReward(userBase.getUid(), openId,
							unionId, reward);
					TradeRecord record = new TradeRecord(userBase.getUid(),
							userBase.getUid(),
							TradeType.WECHAT_FOLLOW_COIN.type, reward, reward,
							TradeCostType.COST_COIN.type, "回复“我爱萌趣抓娃娃”奖励");
					try {
						tradeService.charge(record);
						return "成功领到"
								+ reward
								+ "币,分享邀请码可获得更多奖励喔 <a href='http://www.mengquww.com/share/invite/index.html?uid="
								+ userBase.getUid() + "&d1=2'>获取邀请码</a>";
					} catch (TradeOperationException e) {
						e.printStackTrace();
					}
				}
			}
		}
		UserBase userBase = userService.getUserBaseBySrc(
				UserSrcType.WECHAT.type, openId, unionId);
		if (userBase != null) {
			return "您已领取过奖励~分享邀请码可获得更多奖励喔 <a href='http://www.mengquww.com/share/invite/index.html?uid="
					+ userBase.getUid() + "&d1=2'>获取邀请码</a>";
		}
		return "您已领取过奖励~敬请关注后续活动~";
	}

	@Override
	public String getInviteMsg(String unionId, String openId) {
		UserBase userBase = userService.getUserBaseBySrc(
				UserSrcType.WECHAT.type, openId, unionId);
		if (userBase != null) {
			return "邀请好友互得20币，还有机会获得20元现金奖励哦~ <a href='http://www.mengquww.com/share/invite/index.html?uid="
					+ userBase.getUid() + "&d1=2'>获取邀请码</a>";
		}
		return "您尚未注册萌趣帐号,赶紧下载萌趣抓娃娃吧<a href='http://www.mengquww.com/download/gw'>打开萌趣</a>";
	}
}
