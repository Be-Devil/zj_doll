package com.imlianai.zjdoll.app.modules.support.ranking.cmd;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.zjdoll.app.controller.WebCmd;
import com.imlianai.zjdoll.app.modules.support.ranking.service.RankingService;

@Component("rankingSetting")
public class CmdRankingSetting extends WebCmd {

	@Resource
	RankingService rankingService;

	@ApiHandle
	@Path("api/rankingSetting/updateRanking")
	@LogHead("榜单更改")
	public BaseRespVO updateRanking(long uid, String key, double cost) {
		if (key != null && key.equals("mengqu666ww")) {
			String weekCode = DateUtils.dateToString(DateUtils
					.getWeekFirstTime());
			rankingService.addRichValue(cost, uid, weekCode);
			logger.info("rankingSetting updateRanking uid:"+uid+" cost:"+cost);
		}
		return new BaseRespVO();
	}

}
