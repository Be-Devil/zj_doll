package com.imlianai.dollpub.app.modules.support.sys.cmd;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.publics.kws.service.KeyWordService;
import com.imlianai.dollpub.app.modules.support.banner.service.BannerService;
import com.imlianai.dollpub.app.modules.support.cnf.service.GlobalCnfService;
import com.imlianai.dollpub.app.modules.support.sys.vo.SysDataRefreshReqVO;
import com.imlianai.dollpub.app.modules.support.version.service.VersionService;
import com.imlianai.dollpub.domain.sys.SysDataRefreshType;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;

@Component("sysDataRefresh")
public class CmdSysDataRefresh extends RootCmd {

	@Resource
	private VersionService versionService;
	@Resource
	private BannerService bannerService;
	@Resource
	private GlobalCnfService globalCnfService;
	@Resource
	private KeyWordService keyWordService;

	@ApiHandle
	public BaseRespVO refresh(SysDataRefreshReqVO vo) {
		SysDataRefreshType srt = null;
		BaseRespVO respVo = null;
		if (vo.getUid() == 10000 && vo.getType() != null
				&& StringUtils.equals(vo.getKey(), "10000")
				&& (srt = SysDataRefreshType.getType(vo.getType())) != null) {
			Map<String, Object> result = new HashMap<String, Object>();
			switch (srt) {
				case VERSION :
					result.put("type", "版本升级");
					result.put("server", AppUtils.getServerName());
					break;
				case VERSION_AUDIT :
					result.put("type", "版本隐藏");
					result.put("server", AppUtils.getServerName());
					break;
				case GLOBAL_CNF :
					globalCnfService.init();
					result.put("type", "app全局变量");
					result.put("server", AppUtils.getServerName());
					break;
				case KEYWORD :
					keyWordService.init();
					result.put("type", "关键字库");
					result.put("server", AppUtils.getServerName());
					break;
				default :
					break;
			}
			respVo = new BaseRespVO(result);
		} else {
			respVo = BaseRespVO.getBaseRespVO(ResCodeEnum.SYSTEM_ERROR);
		}
		return respVo;
	}
}
