package com.imlianai.zjdoll.app.modules.publics.qiniu.cmd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.log.LogPage;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.publics.log.service.LogService;
import com.imlianai.zjdoll.app.modules.publics.qiniu.service.QiNiuService;
import com.imlianai.zjdoll.app.modules.publics.qiniu.vo.QiniuDelImageReqVO;
import com.imlianai.zjdoll.app.modules.publics.qiniu.vo.QiniuGetTokenReqVO;
import com.imlianai.zjdoll.app.modules.publics.qiniu.vo.QiniuTokenRespVO;

@Component("qiniu")
@Api("七牛相关")
public class CmdQiniu extends RootCmd {

	@Resource
	private LogService logService;

	@Resource
	private QiNiuService qiNiuService;

	@ApiHandle
	@Path("/api/qiniu/getToken")
	@ApiOperation(value = "【1.0.0】获取七牛上传token接口", notes = "获取七牛上传token", httpMethod = "POST", response = QiniuTokenRespVO.class)
	public BaseRespVO getToken(QiniuGetTokenReqVO vo) {
		int type = vo.getType();
		long uid = vo.getUid();
		logService.add(LogPage.IMAGE_UPLOAD_TOKEN, vo, this.getClass());
		return qiNiuService.getToken(uid, type);
	}

	@ApiHandle
	public BaseRespVO delImage(QiniuDelImageReqVO vo) {
		String url = vo.getUrl();
		long uid = vo.getUid();
		BaseRespVO resVO = null;
		String key = AppUtils.getQiniuKeyUid(uid);
		String uidUrl = StringUtils.substringAfter(url, ".com/");
		uidUrl = StringUtils.substringBeforeLast(uidUrl, "/") + "/";
		if (!StringUtils.equals(uidUrl, key)) {
			return resVO = new BaseRespVO().getRespVO(ResCodeEnum.NOT_PROWER);
		}
		boolean rs = qiNiuService.delFileByUrl(url);
		if (rs) {
			resVO = new BaseRespVO().getRespVO(ResCodeEnum.SUCCESS);
		} else {
			resVO = new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		}
		return resVO;
	}

}
