package com.imlianai.dollpub.app.schedule;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.modules.publics.msg.service.MsgService;

@Component
public class MsgTask {

	@Resource
	private MsgService msgService;

	/**
	 * 系统消息更新
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void articleTag() {
		System.out.println("test_task01");
//		msgService.init();
	}
}
