package com.imlianai.dollpub.app.modules.support.shipping.vo;

import io.swagger.annotations.ApiModel;

import java.util.List;

import com.imlianai.dollpub.domain.shipping.DollOrderRecordBill;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@ApiModel(value = "历史订单返回对象")
public class GetBillsRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	private List<DollOrderRecordBill> list;

	public List<DollOrderRecordBill> getList() {
		return list;
	}

	public void setList(List<DollOrderRecordBill> list) {
		this.list = list;
	}
	
}
