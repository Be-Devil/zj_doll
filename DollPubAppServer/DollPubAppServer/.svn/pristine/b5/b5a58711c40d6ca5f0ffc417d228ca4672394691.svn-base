package com.imlianai.dollpub.app.modules.support.address.cmd;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.support.address.service.UserAddressService;
import com.imlianai.dollpub.app.modules.support.address.vo.GetDefAddressReqVO;
import com.imlianai.dollpub.app.modules.support.address.vo.GetDefAddressRespVO;
import com.imlianai.dollpub.app.modules.support.address.vo.UserAddressAddReqVO;
import com.imlianai.dollpub.app.modules.support.address.vo.UserAddressAddRespVO;
import com.imlianai.dollpub.app.modules.support.address.vo.UserAddressInfo;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("用户收货地址")
@LogHead("用户收货地址")
@Component("userAddress")
public class CmdUserAddress extends RootCmd{

	@Resource
	UserAddressService userAddressService;
	
	@ApiHandle
	@LoginCheck
	@Path("api/userAddress/add")
	@ApiOperation(value = "新增收货地址", notes = "新增收货地址", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("新增收货地址")
	public BaseRespVO add(UserAddressAddReqVO reqVO) {
		return userAddressService.add(reqVO);
	}
	
	@ApiHandle
	@Path("api/userAddress/getList")
	@ApiOperation(value = "获取收货地址列表", notes = "获取收货地址列表", httpMethod = "POST", response = UserAddressAddRespVO.class)
	@LogHead("获取收货地址列表")
	public BaseRespVO getList(BaseReqVO reqVO) {
		UserAddressAddRespVO respVO = new UserAddressAddRespVO();
		List<UserAddressInfo> addressInfoList = userAddressService.getList(reqVO.getUid());
		respVO.setAddressInfos(addressInfoList);
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/userAddress/update")
	@ApiOperation(value = "修改收货地址", notes = "修改收货地址", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("修改收货地址")
	public BaseRespVO update(UserAddressAddReqVO reqVO) {
		return userAddressService.update(reqVO);
	}
	
	@ApiHandle
	@Path("api/userAddress/getDefAddress")
	@ApiOperation(value = "获取默认收货地址", notes = "获取默认收货地址", httpMethod = "POST", response = GetDefAddressRespVO.class)
	@LogHead("获取默认收货地址")
	public BaseRespVO getDefAddress(GetDefAddressReqVO reqVO) {
		GetDefAddressRespVO respVO = new GetDefAddressRespVO();
		UserAddressInfo addressInfo = userAddressService.getDefAddress(reqVO.getUid(), reqVO.getCustomerId());
		respVO.setAddressInfo(addressInfo);
		return respVO;
	}
	
}
