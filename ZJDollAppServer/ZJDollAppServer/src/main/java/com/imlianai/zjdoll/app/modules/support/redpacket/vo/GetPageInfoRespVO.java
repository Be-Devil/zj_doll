package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import java.util.List;

import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserBaseInfoRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "暴击红包分享页面信息返回对象")
public class GetPageInfoRespVO extends UserBaseInfoRespVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户所有红包累计的金额")
	private double amt;
	
	private List<RoomRedpacketRecord> records;
	
	public GetPageInfoRespVO() {
		
	}
	
	public GetPageInfoRespVO(UserGeneral userGeneral, double amt, List<RoomRedpacketRecord> records) {
		this.setName(userGeneral.getName());
		this.setHead(userGeneral.getHead());
		this.amt = amt;
		this.records = records;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public List<RoomRedpacketRecord> getRecords() {
		return records;
	}

	public void setRecords(List<RoomRedpacketRecord> records) {
		this.records = records;
	}
}
