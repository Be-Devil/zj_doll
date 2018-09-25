package com.imlianai.dollpub.app.modules.support.banner.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.doll.record.DollRecordService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.support.banner.dao.BannerDAO;
import com.imlianai.dollpub.app.modules.support.banner.service.BannerService;
import com.imlianai.dollpub.app.modules.support.banner.vo.BannerListRespVO;
import com.imlianai.dollpub.app.modules.support.banner.vo.BannerLocationReqVO;
import com.imlianai.dollpub.app.modules.support.banner.vo.BannerReqVO;
import com.imlianai.dollpub.app.modules.support.banner.vo.GetHomePageVideoRespVO;
import com.imlianai.dollpub.constants.JumpTarget;
import com.imlianai.dollpub.domain.banner.Banner.BannerLocationType;
import com.imlianai.dollpub.domain.banner.BannerRes;
import com.imlianai.dollpub.domain.doll.DollRewardRecord;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.utils.StringUtil;

@Api("轮播图")
@Component("banner")
public class CmdBanner extends RootCmd {

	@Resource
	private BannerService bannerService;

	@Resource
	private BannerDAO bannerDAO;

	@Resource
	private UserService userService;

	@Resource
	private DollRecordService dollRecordService;

	@ApiHandle
	@Path("/api/banner/list")
	@ApiOperation(value = "【1.0.0】获取banner接口", notes = "获取轮播图信息", httpMethod = "POST", response = BannerListRespVO.class)
	public BaseRespVO list(BannerReqVO vo) {
		BannerListRespVO resp = new BannerListRespVO();
		List<BannerRes> banners = bannerService.getBanners(0, 0, "",
				BannerLocationType.INDEX_BANNER.type, vo.getCustomerId());
		UserBase userBase=userService.getUserBase(vo.getUid());
		if (!StringUtil.isNullOrEmpty(banners)) {
			List<BannerRes> bannersR =new ArrayList<BannerRes>();
			for (BannerRes bannerRes : banners) {
				if (bannerRes.getTarget()==JumpTarget.WEBVIEW.target) {
					String url=bannerRes.getUrl();
					if (url.contains("?")) {
						url+="&uid="+vo.getUid()+"&loginKey="+vo.getLoginKey();
					}else{
						url+="?uid="+vo.getUid()+"&loginKey="+vo.getLoginKey();
					}
					if ((vo.getAgintId()!=1&&vo.getAgintId()!=120)&&bannerRes.getId()==71) {
						continue;
					}
					if ((userBase.getAgentId()!=91)&&bannerRes.getId()==82) {
						continue;
					}
					bannerRes.setUrl(url);
				}
				bannersR.add(bannerRes);
			}
			resp.setBanners(bannersR);
		}
		List<DollRewardRecord> records = dollRecordService.getRecentlyDollList(
				vo.getCustomerId(), 50);
		if (!StringUtil.isNullOrEmpty(records)) {
			resp.setRecords(records);
		}
		return resp;
	}

	@ApiHandle
	@Path("/api/banner/getHomePageVideo")
	@ApiOperation(value = "获取首页视频", notes = "获取首页视频", httpMethod = "POST", response = GetHomePageVideoRespVO.class)
	public BaseRespVO getHomePageVideo(BannerReqVO vo) {
		GetHomePageVideoRespVO resp = new GetHomePageVideoRespVO();
		List<BannerRes> banners = bannerService.getBanners(0, 0, "",
				BannerLocationType.INDEX_LIVE.type, vo.getCustomerId());
		boolean hasVideo = false;
		BannerRes videoBannerRes = null;
		try {
			if (!StringUtil.isNullOrEmpty(banners)) {
				for (BannerRes bannerRes : banners) {
					if (!StringUtil.isNullOrEmpty(bannerRes.getUrl())
							&& bannerRes.getUrl().contains("rtmp")) {
						hasVideo = true;
						videoBannerRes = bannerRes;
					}
				}
				resp.setPicPath(banners.get(0).getImgUrl());
			}
			if (hasVideo && videoBannerRes != null) {
				resp.setVideoPath(videoBannerRes.getUrl());
				resp.setPicPath(videoBannerRes.getImgUrl());
			}
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
		return resp;
	}

	@ApiHandle
	@Path("/api/banner/locationList")
	@ApiOperation(value = "【1.3.0】获取banner接口", notes = "获取轮播图信息", httpMethod = "POST", response = BannerListRespVO.class)
	public BaseRespVO locationList(BannerLocationReqVO vo) {
		BannerListRespVO resp = new BannerListRespVO();
		UserBase user=userService.getUserBase(vo.getUid());
		List<BannerRes> banners = bannerService.getBanners(0, 0, "",
				BannerLocationType.EXCHANGE_SHOP.type, user==null?vo.getCustomerId():user.getCustomerId());
		if (!StringUtil.isNullOrEmpty(banners)) {
			for (BannerRes bannerRes : banners) {
				if (bannerRes.getTarget()==JumpTarget.WEBVIEW.target) {
					String url=bannerRes.getUrl();
					if (url.contains("?")) {
						url+="&uid="+vo.getUid()+"&loginKey="+vo.getLoginKey();
					}else{
						url+="?uid="+vo.getUid()+"&loginKey="+vo.getLoginKey();
					}
					bannerRes.setUrl(url);
				}
			}
			resp.setBanners(banners);
		}
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
