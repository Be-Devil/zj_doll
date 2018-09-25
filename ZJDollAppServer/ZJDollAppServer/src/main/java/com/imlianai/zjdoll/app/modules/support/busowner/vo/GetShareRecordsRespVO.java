package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;

@ApiModel("查看分享值记录返回对象")
public class GetShareRecordsRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	private List<ShareRecordRes> shareRecords;

	public List<ShareRecordRes> getShareRecords() {
		return shareRecords;
	}

	public void setShareRecords(List<ShareRecordRes> shareRecords) {
		this.shareRecords = shareRecords;
	}
}
