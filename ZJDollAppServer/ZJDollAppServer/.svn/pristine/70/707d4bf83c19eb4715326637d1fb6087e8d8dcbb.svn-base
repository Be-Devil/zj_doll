package com.imlianai.zjdoll.app.modules.publics.mail;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.zjdoll.app.controller.RootCmd;

@Component("testMail")
public class CmdTestMail extends RootCmd{

	@ApiHandle
	public BaseRespVO send(String title,String content){
		new TestSendDome().doSendDoll(title, content);
		return new BaseRespVO();
	}
}
