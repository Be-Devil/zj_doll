package com.imlianai.zjdoll.app.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.KeyWordsTools;
import com.imlianai.zjdoll.app.modules.support.report.service.ReportService;

@Component
public class MsgTask {

	@Resource
	private ReportService reportService;

	/**
	 * 系统消息更新
	 */
	public void keyWordUpdate() {
		List<String> keyWords=reportService.getWords(0, "", 1);
		KeyWordsTools.setKeyWords(keyWords);
	}
}
