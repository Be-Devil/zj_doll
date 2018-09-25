package com.imlianai.zjdoll.app.modules.publics.msg.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.leancloud.IMCommonService;
import com.imlianai.zjdoll.app.modules.publics.msg.vo.ImSignReqVO;
import com.imlianai.zjdoll.app.modules.publics.msg.vo.ImSignRespVO;

/**
 * 获取开始聊天的签名
 * 
 * @author tensloveq
 * 
 */
@Api("消息相关")
@Component("imserver")
public class CmdGetStartChatSign extends RootCmd {

	@Resource
	private IMCommonService iMCommonService;
	@Resource
	private UserService userService;

	@ApiHandle
	@LoginCheck
	@Path("api/imserver/sign")
	@ApiOperation(value = "【1.0.0】获取leancloud签名接口", notes = "", httpMethod = "POST", response = ImSignRespVO.class)
	public BaseRespVO sign(ImSignReqVO reqVo) {
		if (reqVo.getUid() == 10000) {
			return new BaseRespVO(ResCodeEnum.USER_IS_EXPIRE);
		}
		ImSignRespVO respVo = new ImSignRespVO();
		if (reqVo.getType() == 1) {
			String self_id = reqVo.getSelf_id();
			@SuppressWarnings("unchecked")
			List<String> watch_ids = (List<String>) reqVo.getWatch_ids();
			if (watch_ids != null) {// 创建聊天
				List<String> watch_idsList = watch_ids;
				String tidString = "";
				for (String watchId : watch_idsList) {
					if (!watchId.equals(self_id)) {
						tidString = watchId;
						break;
					}
				}
				long tid = Long.parseLong(tidString);
				UserGeneral user = userService.getUserGeneral(reqVo.getUid());
				UserGeneral tUser = userService.getUserGeneral(tid);
				if (user == null || tUser == null || user.getValid() != 0
						|| tUser.getValid() != 0) {
					return new BaseRespVO(ResCodeEnum.USER_NOT_FOUND);
				} else {
					Map<String, Object> imSignMap = iMCommonService
							.getCreateRoomSign(reqVo.getUid(), tid);

					respVo.setSign(imSignMap);
				}
			} else {// 登录
				UserBase user = userService.getUserBase(reqVo.getUid());
				UserGeneral userGeneral = userService.getUserGeneral(reqVo
						.getUid());
				if (user == null || userGeneral == null
						|| userGeneral.getValid() != 0) {
					return new BaseRespVO(ResCodeEnum.USER_NOT_FOUND);
				} else {
					Map<String, Object> imSignMap = iMCommonService
							.getLoginSign(user);
					respVo.setSign(imSignMap);
				}
			}
		} else if (reqVo.getType() == 2) {
			UserGeneral userGeneral = userService
					.getUserGeneral(reqVo.getUid());
			if (userGeneral == null || userGeneral.getValid() != 0) {
				return new BaseRespVO(ResCodeEnum.USER_NOT_FOUND);
			} else {
				String convId = reqVo.getConv_id();
				String action = reqVo.getAction();
				Map<String, Object> imSignMap = iMCommonService
						.getEnterRoomSign(convId, reqVo.getUid(), action);
				respVo.setSign(imSignMap);
			}
		}
		return respVo;
	}

}
