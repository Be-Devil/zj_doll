package com.imlianai.dollpub.app.modules.support.log.app;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.publics.log.service.LogService;
import com.imlianai.dollpub.app.modules.support.log.vo.LogAppIndexReqVO;
import com.imlianai.dollpub.domain.log.LogPage;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;

@Component("logApp")
public class CmdLogAppIndex extends RootCmd {

	@Resource
	private LogService logService;
//	@Resource
//	private ArticleService articleService;

	@ApiHandle
	public BaseRespVO select(LogAppIndexReqVO vo) {
		LogPage page = LogPage.APP_INDEX_LOG;
		logService.addMoreDate(page, vo, null, this.getClass());
//		if(vo.getUid() != null)
//			articleService.updateUserType(vo.getUid(), vo.getType());
		return new BaseRespVO(ResCodeEnum.SUCCESS);
	}
}
