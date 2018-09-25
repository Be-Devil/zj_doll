package com.imlianai.dollpub.app.modules.core.api.cmd;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.api.vo.CmsOperateReqVO;
import com.imlianai.dollpub.app.modules.core.api.vo.CmsPushCoinBusQueryReqVO;
import com.imlianai.dollpub.app.modules.core.coinfactory.service.PushCoinMachineService;
import com.imlianai.dollpub.app.modules.core.coinfactory.vo.OperateRespVO;
import com.imlianai.dollpub.app.modules.core.coinfactory.vo.PushCoinBusQueryRespVO;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.domain.coinfactory.MachinePushCoin;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.SignCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;

/**
 * @author wurui
 * @create 2018-04-17 14:50
 **/
@Component("cmsPush")
@Api(value = "推币机")
public class CmdPushApi extends RootCmd {

    @Resource
    private PushCoinMachineService pushCoinMachineService;

    @Resource
    private CustomerService customerService;

    @SignCheck
    @ApiHandle
    @Path("api/cmsPush/apply")
    @ApiOperation(value = "上机",notes = "上机成功后返回操作ID",httpMethod = "POST",response = OperateRespVO.class,position = 1)
    public BaseRespVO apply(CmsOperateReqVO vo){
        if (vo.getAppId() == null || vo.getAppId().equals("")) {
            return new BaseRespVO(-1, false, "AppId cannot be empty");
        }

        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "上机失败：商户不存在=>" + vo.getAppId());
        }
        return pushCoinMachineService.apply(vo.getUid(),vo.getBusId(),customer);
    }

    @SignCheck
    @ApiHandle
    @Path("api/cmsPush/push")
    @ApiOperation(value = "投币",notes = "投币相当于上机，投币之后超过30秒未继续投币则自动结束上机",httpMethod = "POST",response = OperateRespVO.class,position = 2)
    public BaseRespVO push(CmsOperateReqVO vo){
        if (vo.getAppId() == null || vo.getAppId().equals("")) {
            return new BaseRespVO(-1, false, "AppId cannot be empty");
        }
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "上机失败：当前商户不存在=>" + vo.getAppId());
        }
        return pushCoinMachineService.putCoin(vo.getUid(),vo.getBusId(),customer);
    }

    @SignCheck
    @ApiHandle
    @Path("api/cmsPush/operate")
    @ApiOperation(value = "摆动",notes = "摆动",httpMethod = "POST",response = BaseRespVO.class,position = 3)
    public BaseRespVO operate(CmsOperateReqVO vo){
        return pushCoinMachineService.operate(vo.getUid(),vo.getBusId());
    }

    @ApiHandle
    @Path("api/cmsPush/query")
    @ApiOperation(value = "查询结果",notes = "查询结果",httpMethod = "POST",response = PushCoinBusQueryRespVO.class,position = 4)
    public BaseRespVO query(CmsPushCoinBusQueryReqVO vo){
        if (vo.getAppId() == null || vo.getAppId().equals("")) {
            return new BaseRespVO(-1, false, "AppId cannot be empty");
        }
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "当前商户不存在=>" + vo.getAppId());
        }
        MachinePushCoin machinePushCoin = pushCoinMachineService.getOptRecordRouter(customer.getGroupId(),vo.getOptId());
        if (machinePushCoin != null){
            PushCoinBusQueryRespVO pushCoinBusQueryRespVO = new PushCoinBusQueryRespVO(machinePushCoin);
            pushCoinBusQueryRespVO.setResult(100);
            pushCoinBusQueryRespVO.setState(true);
            pushCoinBusQueryRespVO.setMsg("数据请求成功!");
            return pushCoinBusQueryRespVO;
        }
        return new BaseRespVO(0,false,"无操作记录!");
    }


    @SignCheck
    @ApiHandle
    @Path("api/cmsPush/finish")
    @ApiOperation(value = "主动下机",notes = "主动下机操作",httpMethod = "POST",response = BaseRespVO.class,position = 5)
    public BaseRespVO finish(CmsOperateReqVO vo){
        if (vo.getAppId() == null || vo.getAppId().equals("")) {
            return new BaseRespVO(-1, false, "AppId cannot be empty");
        }
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "当前商户不存在=>" + vo.getAppId());
        }
        return pushCoinMachineService.finish(vo.getUid(),vo.getBusId());
    }


    @SignCheck
    @ApiHandle
    @Path("api/cmsPush/getStatus")
    @ApiOperation(value = "推币机状态",notes = "推币机状态",httpMethod = "POST",response = BaseRespVO.class,position = 6)
    public BaseRespVO getStatus(CmsOperateReqVO vo){
        if (vo.getAppId() == null || vo.getAppId().equals("")) {
            return new BaseRespVO(-1, false, "AppId cannot be empty");
        }
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer == null) {
            return pushCoinMachineService.getPushCoinStatus(vo.getBusId());
        }
        return new BaseRespVO(0,false,"当前商户不存在");
    }


}
