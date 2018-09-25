package com.imlianai.dollpub.app.modules.support.sys.vo;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
@SuppressWarnings("serial")
public class SysDataRefreshReqVO extends BaseReqVO {

	/**
	 * 刷新类型
	 */
	private Integer type;

	/**
	 * 刷新密钥
	 */
	private String key;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
