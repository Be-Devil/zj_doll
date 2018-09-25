package com.imlianai.dollpub.app.modules.support.test.cmd;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.WebCmd;
import com.imlianai.dollpub.app.modules.support.shopkeeper.service.ShopkeeperService;
import com.imlianai.dollpub.app.modules.support.withdraw.service.WithdrawService;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@LogHead("店主测试")
@Component("shopkeepTest")
public class CmdShopKeeperTest extends WebCmd {

	@Resource
	private ShopkeeperService shopkeeperService;
	
	@Resource
	private WithdrawService withdrawService;
	@Resource
	JdbcHandler jdbcHandler;

	@ApiHandle
	@Path("api/shopkeepTest/charge")
	@LogHead("娃娃自动兑换成游戏币任务")
	public BaseRespVO charge(long uid,int chargeMoney) {
		shopkeeperService.chargeHandle(uid, chargeMoney);
		return new BaseRespVO();
	}

	@ApiHandle
	@Path("api/shopkeepTest/delaccount")
	@LogHead("提现")
	public BaseRespVO delaccount(long uid,String key) {
		if (key.equals("removfdsf456eUidBoundsadsa323")) {
			jdbcHandler.executeSql("update user_base set srcId='', srcUnionId='' where uid=?", uid);
		}
		return new BaseRespVO();
	}
	
	@ApiHandle
	@Path("api/shopkeepTest/withdraw")
	@LogHead("提现")
	public BaseRespVO withdraw(long uid,int chargeMoney) {
		withdrawService.doPayWechatMoneySchedule();
		return new BaseRespVO();
	}
	
	@ApiHandle
	@Path("api/shopkeepTest/withdrawCheck")
	@LogHead("提现检查")
	public BaseRespVO withdrawCheck(long uid,int chargeMoney) {
		withdrawService.doPayWechatMoneyResultCheckSchedule();
		return new BaseRespVO();
	}
}
