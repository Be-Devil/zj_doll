package com.imlianai.zjdoll.app.modules.support.ranking.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.banner.Banner.BannerLocationType;
import com.imlianai.zjdoll.domain.banner.BannerRes;
import com.imlianai.zjdoll.domain.doll.user.RankingItem;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.support.banner.service.BannerService;
import com.imlianai.zjdoll.app.modules.support.ranking.service.RankingService;
import com.imlianai.zjdoll.app.modules.support.ranking.vo.GetBannersRespVO;
import com.imlianai.zjdoll.app.modules.support.ranking.vo.GetRankingReqVO;
import com.imlianai.zjdoll.app.modules.support.ranking.vo.GetRankingRespVO;
import com.imlianai.zjdoll.app.modules.support.userdoll.service.UserDollService;

@Api("榜单")
@LogHead("榜单模块")
@Component("ranking")
public class CmdRanking extends RootCmd {

	@Resource
	UserDollService userDollService;
	@Resource
	BannerService bannerService;
	@Resource
	RankingService rankingService;

	@ApiHandle
	@Path("api/ranking/getRanking")
	@ApiOperation(value = "【1.0.0】榜单", notes = "榜单", httpMethod = "POST", response = GetRankingRespVO.class)
	@LogHead("榜单")
	public BaseRespVO getRanking(GetRankingReqVO reqVO) {
		return userDollService.getRanking(reqVO.getType(), reqVO.getUid());
	}

	@ApiHandle
	@Path("api/ranking/getBanners")
	@ApiOperation(value = "【1.0.0】获取榜单banner", notes = "获取榜单banner", httpMethod = "POST", response = GetBannersRespVO.class)
	@LogHead("获取榜单banner")
	public BaseRespVO getBanners(BaseReqVO vo) {
		GetBannersRespVO respVO = new GetBannersRespVO();
		List<BannerRes> banners = bannerService.getBanners(vo.getOsType(),
				vo.getVersion(), vo.getChannel(),
				BannerLocationType.RANKING_BANNER.type, vo.getUid(),
				vo.getLoginKey());
		respVO.setBanners(banners);
		return respVO;
	}

	@ApiHandle
	@Path("api/ranking/getChargeRanking")
	@ApiOperation(value = "【1.3.0】充值榜单", notes = "充值榜单", httpMethod = "POST", response = GetRankingRespVO.class)
	@LogHead("充值榜单")
	public BaseRespVO getChargeRanking(GetRankingReqVO reqVO) {
		GetRankingRespVO respVO = new GetRankingRespVO();
		List<RankingItem> list = rankingService.getChargeRanks(50);
		if (!StringUtil.isNullOrEmpty(list)) {
			respVO.setRankingItems(list);
		}else{
			List<RankingItem> res =new ArrayList<RankingItem>();
			for (int i = 1; i < 5; i++) {
				res.add(new RankingItem("虚位以待",  (i+1)));
			}
			rankingService.getRankListWithReward(res, 2);
			respVO.setRankingItems(res);
		}
		RankingItem item=rankingService.getMyChargeRank(reqVO.getUid());
		if (item!=null) {
			respVO.setSelfRankingInfo(item);
		}
		return respVO;
	}

}
