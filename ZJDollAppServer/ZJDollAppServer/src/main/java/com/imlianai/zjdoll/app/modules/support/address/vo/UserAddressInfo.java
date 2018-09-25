package com.imlianai.zjdoll.app.modules.support.address.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户地址信息对象")
public class UserAddressInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "ID")
	private Long id;
	
	@ApiModelProperty(value = "收货人")
	private String receiver;
	
	@ApiModelProperty(value = "联系电话")
	private long phone;
	
	@ApiModelProperty(value = "省")
	private String province;
	
	@ApiModelProperty(value = "市")
	private String city;;
	
	@ApiModelProperty(value = "区")
	private String district;
	
	@ApiModelProperty(value = "详细地址")
	private String fullAddress;
	
	public UserAddressInfo() {
	}
	
	public UserAddressInfo(Long id, String receiver, long phone, String province, String city, String district,
			String fullAddress) {
		this.id = id;
		this.receiver = receiver;
		this.phone = phone;
		this.province = province;
		this.city = city;
		this.district = district;
		this.fullAddress = fullAddress;
	}

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
}
