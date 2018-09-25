package com.imlianai.zjdoll.app.modules.support.playground.cmd;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.banner.Banner.BannerLocationType;
import com.imlianai.zjdoll.domain.banner.BannerRes;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.support.banner.service.BannerService;
import com.imlianai.zjdoll.app.modules.support.playground.service.PlaygroundService;
import com.imlianai.zjdoll.app.modules.support.playground.vo.BusOwnerListRespVO;
import com.imlianai.zjdoll.app.modules.support.playground.vo.PlaygroundIndexRespVO;
import com.imlianai.zjdoll.app.modules.support.version.service.VersionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("广场")
@Component("playground")
public class CmdPlayground extends RootCmd {

	@Resource
	private BannerService bannerService;

	@Resource
	private UserService userService;

	@Resource
	private DollRecordService dollRecordService;
	
	@Resource
	private VersionService versionService;
	
	@Resource
	private PlaygroundService playgroundService;

	@ApiHandle
	@Path("/api/playground/index")
	@ApiOperation(value = "【1.3.0】获取广场页面接口", notes = "获取广场banner接口", httpMethod = "POST", response = PlaygroundIndexRespVO.class)
	public BaseRespVO index(BaseReqVO vo) {
		PlaygroundIndexRespVO resp = new PlaygroundIndexRespVO();
		List<BannerRes> banners = bannerService.getBanners(vo.getOsType(),
				vo.getVersion(), vo.getChannel(),
				BannerLocationType.PLAY_GROUND.type,vo.getUid(),vo.getLoginKey());
		resp.setBanners(banners);
		return resp;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/playground/shopList")
	@ApiOperation(value = "【1.3.0】萌店列表信息", notes = "萌店列表信息", httpMethod = "POST", response = BusOwnerListRespVO.class)
	@LogHead("萌店列表信息")
	public BaseRespVO shopList(BaseReqVO reqVO) {
		BusOwnerListRespVO respVO = new BusOwnerListRespVO();
		respVO.setBusOwnerItems(playgroundService.shopList());
		return respVO;
	}

}
