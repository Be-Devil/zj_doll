package com.imlianai.dollpub.app.modules.support.ranking.cmd;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.support.banner.service.BannerService;
import com.imlianai.dollpub.app.modules.support.ranking.vo.GetBannersRespVO;
import com.imlianai.dollpub.app.modules.support.ranking.vo.GetRankingReqVO;
import com.imlianai.dollpub.app.modules.support.ranking.vo.GetRankingRespVO;
import com.imlianai.dollpub.app.modules.support.userdoll.service.UserDollService;
import com.imlianai.dollpub.domain.banner.BannerRes;
import com.imlianai.dollpub.domain.banner.Banner.BannerLocationType;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("榜单")
@LogHead("榜单模块")
@Component("ranking")
public class CmdRanking extends RootCmd{

	@Resource
	UserDollService userDollService;
	@Resource
	BannerService bannerService;
	
	@ApiHandle
	@Path("api/ranking/getRanking")
	@ApiOperation(value = "【1.0.0】榜单", notes = "榜单", httpMethod = "POST", response = GetRankingRespVO.class)
	@LogHead("榜单")
	public BaseRespVO getRanking(GetRankingReqVO reqVO) {
		return userDollService.getRanking(reqVO.getType(),reqVO.getUid(),reqVO.getCustomerId());
	}
	
	@ApiHandle
	@Path("api/ranking/getBanners")
	@ApiOperation(value = "【1.0.0】获取榜单banner", notes = "获取榜单banner", httpMethod = "POST", response = GetBannersRespVO.class)
	@LogHead("获取榜单banner")
	public BaseRespVO getBanners(BaseReqVO vo) {
		GetBannersRespVO respVO = new GetBannersRespVO();
		List<BannerRes> banners = bannerService.getBanners(vo.getOsType(),
				vo.getVersion(), vo.getChannel(), BannerLocationType.RANKING_BANNER.type,76);
		respVO.setBanners(banners);
		return respVO;
	}
	
	
}
