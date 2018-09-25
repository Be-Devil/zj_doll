package com.imlianai.dollpub.app.modules.support.log.read;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.publics.log.service.LogService;
import com.imlianai.dollpub.app.modules.support.log.vo.LogReadModeReqVO;
import com.imlianai.dollpub.app.modules.support.log.vo.LogReadTimeReqVO;
import com.imlianai.dollpub.domain.log.LogPage;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
@Component("logRead")
public class CmdLogRead extends RootCmd {

	@Resource
	private LogService logService;
//	@Resource
//	private ArticleChapterService articleChapterService;
//	@Resource
//	private ArticleService articleService;
	@Resource
	private UserService userService;

	@ApiHandle
	public BaseRespVO time(LogReadTimeReqVO vo) {
//		LogPage page = LogPage.LOG_READ_TIME;
//		long chapterId = vo.getChapterId();
//		ArticleChapter chapter = articleChapterService
//				.getChapterByIdNoLimit(chapterId);
//		Article article = null;
//		Map<String, Object> logs = new HashMap<String, Object>();
//		if (chapter != null
//				&& (article = articleService.get(chapter.getArtId())) != null) {
//			logs.put("artId", chapter.getArtId());
//			logs.put("category", article.getCategory());
//			logs.put("payPoint", chapter.getPayPoint());
//		}
//		logService.addMoreDate(page, vo, null, this.getClass());
		return new BaseRespVO(ResCodeEnum.SUCCESS);
	}

	@ApiHandle
	public BaseRespVO mode(LogReadModeReqVO vo) {
		UserGeneral user = null;
		if (vo.getUid() != null && vo.getUid() > 0) {
			user = userService.getUserGeneral(vo.getUid());
		}
		LogPage page = LogPage.LOG_READ_MODE;
		logService.addMoreDate(page, vo, user, this.getClass());
		return new BaseRespVO(ResCodeEnum.SUCCESS);
	}
}
