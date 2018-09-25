package com.imlianai.dollpub.app.modules.support.version.cmd;

import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.support.version.service.VersionService;
import com.imlianai.dollpub.app.modules.support.version.vo.VersionAuditRespVO;
import com.imlianai.dollpub.app.modules.support.version.vo.VersionReqVO;
import com.imlianai.dollpub.app.modules.support.version.vo.VersionRespVO;
import com.imlianai.dollpub.app.modules.support.version.vo.VersionRespVO.VersionAboutInfo;
import com.imlianai.dollpub.domain.version.Version;
import com.imlianai.dollpub.domain.version.VersionBootimg;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;

/**
 * 自动检查是否有版本更新
 * 
 * @author tensloveq
 * 
 */
//@Api("版本检查")
@Component("version")
public class CmdVersion extends RootCmd {

	@Resource
	private VersionService versionService;

	@ApiHandle
	@Path("/api/version/info")
	@ApiOperation(value = "【1.0.0】检查版本更新接口", notes = "获取新版本信息", httpMethod = "POST", response = VersionRespVO.class)
	public VersionRespVO info(VersionReqVO vo) {
		VersionRespVO respVO = new VersionRespVO();
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
				vo.getOsType(), vo.getChannel(),vo.getCustomerId());
		if (bootimg != null) {
			respVO.setBootimgInfo(bootimg);
		}
		bootimg = versionService.getCenterAd(vo.getUid(),vo.getOsType(), vo.getChannel(),vo.getCustomerId());
		if (bootimg != null) {
			bootimg.setLocationName("弹窗广告");
			respVO.setAd(bootimg);
		}
		if (vo.getCustomerId()==79) {
			respVO.setSzAboutInfo();
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
		VersionAuditRespVO respVO = new VersionAuditRespVO();
		boolean audit = versionService.isAudit(vo.getOsType(), vo.getChannel(),
				vo.getVersion());
		respVO.setAudit(audit);
		return respVO;
	}

}
