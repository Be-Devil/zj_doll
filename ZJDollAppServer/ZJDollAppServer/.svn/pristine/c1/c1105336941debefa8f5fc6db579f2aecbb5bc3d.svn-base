package com.imlianai.zjdoll.app.modules.support.banner.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.banner.Banner.BannerLocationType;
import com.imlianai.zjdoll.domain.banner.BannerRes;
import com.imlianai.zjdoll.domain.doll.DollRewardRecord;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.core.user.util.UserUtil;
import com.imlianai.zjdoll.app.modules.support.banner.service.BannerService;
import com.imlianai.zjdoll.app.modules.support.banner.vo.BannerListRespVO;
import com.imlianai.zjdoll.app.modules.support.banner.vo.BannerLocationReqVO;
import com.imlianai.zjdoll.app.modules.support.banner.vo.SmallBannersRespVO;
import com.imlianai.zjdoll.app.modules.support.version.service.VersionService;

@Api("轮播图")
@Component("banner")
public class CmdBanner extends RootCmd {

	@Resource
	private BannerService bannerService;

	@Resource
	private UserService userService;

	@Resource
	private DollRecordService dollRecordService;
	
	@Resource
	private VersionService versionService;

	@ApiHandle
	@Path("/api/banner/list")
	@ApiOperation(value = "【1.0.0】获取banner接口", notes = "获取轮播图信息", httpMethod = "POST", response = BannerListRespVO.class)
	public BaseRespVO list(BaseReqVO vo) {
		BannerListRespVO resp = new BannerListRespVO();
		List<BannerRes> banners = bannerService.getBanners(vo.getOsType(),
				vo.getVersion(), vo.getChannel(),
				BannerLocationType.INDEX_BANNER.type,vo.getUid(),vo.getLoginKey());
		resp.setBanners(banners);
		//中部icon
		List<BannerRes> icons = bannerService.getBanners(vo.getOsType(),
				vo.getVersion(), vo.getChannel(),
				BannerLocationType.INDEX_ICON.type,vo.getUid(),vo.getLoginKey());
		if (!StringUtil.isNullOrEmpty(icons)) {
			resp.setIcons(icons);
		}
		if (versionService.isAudit(vo.getOsType(), vo.getChannel(), vo.getVersion())) {
			List<DollRewardRecord> rewardRecords =new ArrayList<DollRewardRecord>();
			rewardRecords.add(new DollRewardRecord(0, 10000, "萌趣抓娃娃", UserUtil.getDefaultHead(), 0, "快递送到家", new Date(),0));
			resp.setRecords(rewardRecords);
		}else {
			List<DollRewardRecord> rewardRecords =dollRecordService.getRecentlyDollList(50);
			if (!StringUtil.isNullOrEmpty(rewardRecords)) {
				resp.setRecords(rewardRecords);
			}
		}
		return resp;
	}
	
	@ApiHandle
	@Path("/api/banner/smallBanners")
	@ApiOperation(value = "【1.3.0】获取首页小banner接口", notes = "获取首页小banner接口", httpMethod = "POST", response = SmallBannersRespVO.class)
	public BaseRespVO smallBanners(BaseReqVO vo) {
		BannerListRespVO resp = new BannerListRespVO();
		List<BannerRes> banners = bannerService.getBanners(vo.getOsType(),
				vo.getVersion(), vo.getChannel(),
				BannerLocationType.SMALL_BANNER.type,vo.getUid(),vo.getLoginKey());
		resp.setBanners(banners);
		return resp;
	}

	@ApiHandle
	@Path("/api/banner/locationList")
	@ApiOperation(value = "【1.3.0】获取banner接口", notes = "获取轮播图信息", httpMethod = "POST", response = BannerListRespVO.class)
	public BaseRespVO locationList(BannerLocationReqVO vo) {
		BannerListRespVO resp = new BannerListRespVO();
		UserBase user=userService.getUserBase(vo.getUid());
		List<BannerRes> banners = bannerService.getBanners(user!=null?user.getOsType():vo.getOsType(),
				user!=null?user.getVersion():vo.getVersion(), user!=null?user.getChannel():vo.getChannel(),
				vo.getLocation(),vo.getUid(),vo.getLoginKey());
		resp.setBanners(banners);
		return resp;
	}
	
	/*
	 * @ApiHandle
	 * 
	 * @LoginCheck
	 * 
	 * @Path("/api/banner/success")
	 * 
	 * @ApiOperation(value = "【1.0.0】banner分享成功接口", notes = "banner分享成功",
	 * httpMethod = "POST", response = BannerReqVO.class) public BaseRespVO
	 * success(BannerShareReqVO vo) { return new BaseRespVO(); }
	 */
}
