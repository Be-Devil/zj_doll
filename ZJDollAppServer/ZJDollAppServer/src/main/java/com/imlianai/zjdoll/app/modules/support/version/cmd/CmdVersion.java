package com.imlianai.zjdoll.app.modules.support.version.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.banner.Banner;
import com.imlianai.zjdoll.domain.banner.Banner.BannerLocationType;
import com.imlianai.zjdoll.domain.banner.BannerRes;
import com.imlianai.zjdoll.domain.version.Version;
import com.imlianai.zjdoll.domain.version.VersionBootimg;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.support.banner.service.BannerService;
import com.imlianai.zjdoll.app.modules.support.version.service.VersionService;
import com.imlianai.zjdoll.app.modules.support.version.vo.VersionAuditRespVO;
import com.imlianai.zjdoll.app.modules.support.version.vo.VersionReqVO;
import com.imlianai.zjdoll.app.modules.support.version.vo.VersionRespVO;

/**
 * 自动检查是否有版本更新
 * 
 * @author tensloveq
 * 
 */
@Api("版本检查")
@Component("version")
public class CmdVersion extends RootCmd {

	@Resource
	private VersionService versionService;
	@Resource
	BannerService bannerService;

	@ApiHandle
	@Path("/api/version/info")
	@ApiOperation(value = "【1.0.0】检查版本更新接口", notes = "获取新版本信息", httpMethod = "POST", response = VersionRespVO.class)
	public VersionRespVO info(VersionReqVO vo) {
		VersionRespVO respVO = new VersionRespVO();
		try {
			versionService.updateDeviceInfo(vo.getImei(),vo.getChannel(),vo.getOsType());
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
		// 版本更新
		Version version = versionService.getVersion(vo.getOsType(),
				vo.getChannel());
		if (version != null && version.getVersionCode() > vo.getVersion()) {
			// 判断是否有忽略或强制更新
			if (!versionService.isIgnore(vo.getUid())) {
				respVO.setVersionInfo(version);
			}
			if (vo.getIsCheck() == 1) {
				respVO.setVersionInfo(version);
			}
		}
		// 闪屏
		VersionBootimg bootimg = versionService.getBootimg(vo.getUid(),
				vo.getOsType(), vo.getChannel(),vo.getLoginKey(),vo.getVersion());
		if (bootimg != null) {
			respVO.setBootimgInfo(bootimg);
		}
		Banner banner = versionService.getCenterAd(vo.getUid(),vo.getOsType(), vo.getChannel(),vo.getLoginKey(),vo.getVersion());
		if (banner != null) {
			respVO.setAd(bootimg);
			respVO.setAdJump(new BannerRes(banner));
		}
		List<BannerRes> banners = bannerService.getBanners(vo.getOsType(),
				vo.getVersion(), vo.getChannel(),
				BannerLocationType.BOOTIMG_BANNER.type,vo.getUid(),vo.getLoginKey());
		if (!StringUtil.isNullOrEmpty(banners)) {
			respVO.setBootimgInfoList(banners);
		}
		String bgmUrl=versionService.getBGM();
		if (!StringUtil.isNullOrEmpty(bgmUrl)) {
			respVO.setBgmUrl(bgmUrl);
		}
		if(version != null) {
			String commUrl = "";
			if(version.getChannel().trim().equals("appstore")) {
				commUrl = "http://itunes.apple.com/WebObjects/MZStore.woa/wa/viewContentsUserReviews?id=1311258924&pageNumber=0&sortOrdering=2&type=Purple+Software&mt=8";
			} else if(version.getChannel().trim().equals("appstore2")) {
				commUrl = "http://itunes.apple.com/WebObjects/MZStore.woa/wa/viewContentsUserReviews?id=1344659222&pageNumber=0&sortOrdering=2&type=Purple+Software&mt=8";
			}
			respVO.setCommUrl(commUrl);
		}
		return respVO;
	}

	@ApiHandle
	@Path("/api/version/ignore")
	@ApiOperation(value = "【1.0.0】忽略版本更新接口", notes = "忽略最新版本更新", httpMethod = "POST", response = BaseRespVO.class)
	public BaseRespVO ignore(BaseReqVO vo) {
		Version version = versionService.getVersion(vo.getOsType(),
				vo.getChannel());
		if (version != null)
			versionService.addIgnore(vo.getUid(), version.getVersionCode());
		return new BaseRespVO();
	}

	@ApiHandle
	@Path("/api/version/audit")
	@ApiOperation(value = "【1.0.0】审核状态接口", notes = "审核状态", httpMethod = "POST", response = VersionAuditRespVO.class)
	public BaseRespVO audit(BaseReqVO vo) {
		try {
			versionService.updateDeviceInfo(vo.getImei(),vo.getChannel(),vo.getOsType());
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
		VersionAuditRespVO respVO = new VersionAuditRespVO();
		boolean audit = versionService.isAudit(vo.getOsType(), vo.getChannel(),
				vo.getVersion());
		respVO.setAudit(audit);
		return respVO;
	}

}
