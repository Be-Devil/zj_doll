package com.imlianai.zjdoll.app.modules.support.address.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("新增地址请求对象")
public class UserAddressAddReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID(编辑的时候传值)")
	private Long id;
	
	@ApiModelProperty(value = "收货人", required = true)
	private String receiver;
	
	@ApiModelProperty(value = "联系电话", required = true)
	private long phone;
	
	@ApiModelProperty(value = "省", required = true)
	private String province;
	
	@ApiModelProperty(value = "市", required = true)
	private String city;
	
	@ApiModelProperty(value = "区")
	private String district;
	
	@ApiModelProperty(value = "详细地址", required = true)
	private String fullAddress;
	
	@ApiModelProperty(value = "是否为默认地址(0:否，1:是)")
	private int isDefault;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
}

