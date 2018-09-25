package com.imlianai.zjdoll.app.modules.publics.qiniu.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.constants.FormatFinal;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

public class QiniuDelImageReqVO extends BaseReqVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@ParamCheck(allowNull = false)
	private Long uid;

	/**
	 * 图片链接
	 */
	@ParamCheck(format = FormatFinal.URL)
	private String url;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "QiniuDelImageReqVO [uid=" + uid + ", url=" + url + "]";
	}

}
