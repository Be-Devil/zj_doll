package com.imlianai.zjdoll.app.modules.support.address.cmd;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.support.address.service.UserAddressService;
import com.imlianai.zjdoll.app.modules.support.address.vo.GetDefAddressRespVO;
import com.imlianai.zjdoll.app.modules.support.address.vo.UpdateDefAddressReqVO;
import com.imlianai.zjdoll.app.modules.support.address.vo.UserAddressAddReqVO;
import com.imlianai.zjdoll.app.modules.support.address.vo.UserAddressAddRespVO;
import com.imlianai.zjdoll.app.modules.support.address.vo.UserAddressInfo;

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
	@ApiOperation(value = "【1.0.0】新增收货地址", notes = "新增收货地址", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("新增收货地址")
	public BaseRespVO add(UserAddressAddReqVO reqVO) {
		return userAddressService.add(reqVO);
	}
	
	@ApiHandle
	@Path("api/userAddress/getList")
	@ApiOperation(value = "【1.0.0】获取收货地址列表", notes = "获取收货地址列表", httpMethod = "POST", response = UserAddressAddRespVO.class)
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
	@ApiOperation(value = "【1.0.0】修改收货地址", notes = "修改收货地址", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("修改收货地址")
	public BaseRespVO update(UserAddressAddReqVO reqVO) {
		return userAddressService.update(reqVO);
	}
	
	@ApiHandle
	@Path("api/userAddress/getDefAddress")
	@ApiOperation(value = "【1.0.0】获取默认收货地址", notes = "获取默认收货地址", httpMethod = "POST", response = GetDefAddressRespVO.class)
	@LogHead("获取默认收货地址")
	public BaseRespVO getDefAddress(BaseReqVO reqVO) {
		GetDefAddressRespVO respVO = new GetDefAddressRespVO();
		UserAddressInfo addressInfo = userAddressService.getDefAddress(reqVO.getUid());
		respVO.setAddressInfo(addressInfo);
		return respVO;
	}
	
	@ApiHandle
	@LoginCheck
	@Path("api/userAddress/updateDefAddress")
	@ApiOperation(value = "【1.3.0】修改默认收货地址", notes = "修改默认收货地址", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("修改默认收货地址")
	public BaseRespVO updateDefAddress(UpdateDefAddressReqVO reqVO) {
		return userAddressService.updateDefAddress(reqVO.getId());
	}
	
}
