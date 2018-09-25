package com.imlianai.zjdoll.app.modules.support.repair.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.support.redpacket.certification.service.CertificationService;
import com.imlianai.zjdoll.app.modules.support.repair.service.RepairService;
import com.imlianai.zjdoll.app.modules.support.repair.vo.RepairReqVo;

@Api("报修")
@Component("repair")
public class CmdRepair extends RootCmd {

	@Resource
	RepairService repairService;
	@Resource
	CertificationService certificationService;

	@ApiHandle
	@Path("api/repair/report")
	@ApiOperation(value = "【1.1.0】报修申请", notes = "报修申请", httpMethod = "POST", response = BaseRespVO.class)
	@LogHead("报修申请")
	public BaseRespVO report(RepairReqVo reqVO) {
		if(repairService.addReocrd(reqVO.getBusId(), reqVO.getUid(), reqVO.getReason())==1){
			return new BaseRespVO(0,true,"提交成功");
		}
		return new BaseRespVO(0, false, "报修处理中，请勿重复提交");
	}
}
