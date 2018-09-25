package com.imlianai.dollpub.app.modules.core.api.cmd;

import com.imlianai.dollpub.app.modules.core.api.vo.*;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.support.machine.service.MachineService;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.machine.iface.IMachineConnectRemoteService;
import com.imlianai.rpc.support.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.api.dto.DollBusDTO;
import com.imlianai.dollpub.app.modules.core.api.dto.OptRecordDTO;
import com.imlianai.dollpub.app.modules.core.api.service.CustomerDollBusService;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.app.modules.publics.qiniu.pili.PiliException;
import com.imlianai.dollpub.app.modules.publics.qiniu.pili.QNHandle;
import com.imlianai.dollpub.app.modules.publics.qiniu.pili.SaveRet;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.optrecord.OptRecord;
import com.imlianai.dollpub.machine.iface.IMachineRemoteService;
import com.imlianai.dollpub.machine.iface.domain.MachineDevice;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.SignCheck;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.StringUtil;

@Api("娃娃机操控相关接口")
@Component("wawa")
public class CmdApi extends RootCmd {

    @Resource
    private CustomerService customerService;

    @Resource
    private CustomerDollBusService customerDollBusService;

    @Reference
    private IMachineRemoteService iMachineRemoteService;

    @Resource
    private MachineService machineService;

    @Resource
    private DollBusService dollBusService;

    @Reference
    private IMachineConnectRemoteService iMachineConnectRemoteService;


    @ApiHandle
    @LogHead("获取商户娃娃机列表接口")
    @Path("api/wawa/list")
    @ApiOperation(value = "获取商户娃娃机列表接口", notes = "获取商户娃娃机列表", httpMethod = "POST", response = DollBusListRespVO.class)
    public BaseRespVO list(DollBusListReqVO vo) {
        logger.info("api/wawa/list");
        if (vo.getAppId() == null || vo.getAppId().equals("")) {
            return new BaseRespVO(-1, false, "Appid cannot be empty");
        }
        Customer customer = customerService.getCustomer(vo.getAppId());
        logger.info("api/wawa/list " + JSON.toJSONString(customer));
        if (customer == null) {
            return new BaseRespVO(-1, false, "获取商户娃娃机列表失败：商户不存在=>"
                    + vo.getAppId());
        }
        DollBusListRespVO DollBusListRespVO = new DollBusListRespVO(
                DollBusDTO.toDollBusDTO(customerDollBusService
                        .getCustomerDollBusList(customer, vo.getPage(),
                                vo.getSize())));
        logger.info("api/wawa/list " + JSON.toJSONString(DollBusListRespVO));
        return DollBusListRespVO;
    }

    @SignCheck
    @ApiHandle
    @LogHead("申请上机接口")
    @Path("api/wawa/apply")
    @ApiOperation(value = "申请上机接口", notes = "申请上机接口", httpMethod = "POST", response = DollBusApplyMachineRespVO.class)
    public BaseRespVO apply(DollBusApplyMachineReqVO vo) {
        if (vo.getAppId() == null || vo.getAppId().equals("")) {
            return new BaseRespVO(-1, false, "Appid cannot be empty");
        }
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "上机失败：商户不存在=>" + vo.getAppId());
        }
        return customerDollBusService.applyMachine(vo.getUid(), vo.getBusId(),
                customer, vo.getRemark(),0);
    }


    @SignCheck
    @ApiHandle
    @LogHead("线下机上机接口(按类型上机)")
    @Path("api/wawa/applyType")
    @ApiOperation(value = "线下机上机接口(新版子)", notes = "线下机上机接口(新版子)", httpMethod = "POST", response = DollBusApplyMachineRespVO.class)
    public BaseRespVO applyType(DollBusApplyTypeReqVO vo) {
        if (vo.getAppId() == null || vo.getAppId().equals("")) {
            return new BaseRespVO(-1, false, "Appid cannot be empty");
        }
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "上机失败：商户不存在=>" + vo.getAppId());
        }
        return customerDollBusService.applyMachine(vo.getUid(), vo.getBusId(),
                customer, vo.getRemark(),vo.getType());

//        if (vo.getType() != 0){
//
//            DollBus dollBus = dollBusService.getDollBus(vo.getBusId());
//            if (dollBus != null){
//
//                if (dollBus.getGroupId() == customer.getGroupId()){
//
//                    MachineDevice machineDevice = iMachineRemoteService.getMachineState(dollBus.getBusId());
//
//                    if (machineDevice != null && machineDevice.getStatus() != 2){
//                        try {
//
//                            //投币指令
//                            byte[] cast = {(byte) 0x8a, (byte) 0x04, (byte) 0x0f, (byte) 0x01, (byte) 0x01,(byte) 0x15, (byte) 0x55};
//
//                            if (vo.getType() == 1){
//                                byte[] data2_default = {(byte) 0x8a, (byte) 0x04, (byte) 0x02, (byte) 0x01, (byte) 0xff,(byte) 0x06, (byte) 0x55};
//                                iMachineConnectRemoteService.handleDirective(dollBus.getBusId(),cast,"");
//
//                                Thread.sleep(200);
//                                iMachineConnectRemoteService.handleDirective(dollBus.getBusId(),data2_default,"");
//
//                                return new BaseRespVO(200,true,"上机成功,默认爪力");
//
//                            }else if (vo.getType() == 2){
//
//                                byte[] data2_strong = {(byte) 0x8a, (byte) 0x04, (byte) 0x02, (byte) 0x01, (byte) 0x00,(byte) 0x07, (byte) 0x55};
//                                iMachineConnectRemoteService.handleDirective(dollBus.getBusId(),cast,"");
//
//                                Thread.sleep(200);
//                                iMachineConnectRemoteService.handleDirective(dollBus.getBusId(),data2_strong,"");
//
//                                return new BaseRespVO(200,true,"上机成功,强爪爪力");
//
//                            }else {
//                                return new BaseRespVO(0,false,"无效的上机下爪类型");
//                            }
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }else {
//                        return new BaseRespVO(0,false,"机器维护中");
//                    }
//                }else {
//                    return new BaseRespVO(0,false,"机器不属于该商户");
//                }
//            }else {
//                return new BaseRespVO(0,false,"机器不存在或已下架");
//            }
//        }else {
//            return new BaseRespVO(0,false,"类型不能为空");
//        }
//        return new BaseRespVO(0,false,"上机操作异常，稍后重试");
    }


    @SignCheck
    @ApiHandle
    @LogHead("娃娃机操作接口")
    @Path("api/wawa/control")
    @ApiOperation(value = "娃娃机操作接口", notes = "娃娃机操作接口", httpMethod = "POST", response = BaseRespVO.class)
    public BaseRespVO control(DollBusOperateReqVO vo) {
        return customerDollBusService.controlMachine(vo.getBusId(),
                vo.getUid(), vo.getOptId(), vo.getType());
    }

    @SignCheck
    @ApiHandle
    @LogHead("娃娃机下爪操作接口")
    @Path("api/wawa/downClaw")
    @ApiOperation(value = "娃娃机下爪操作接口", notes = "娃娃机下爪操作接口", httpMethod = "POST", response = BaseRespVO.class)
    public BaseRespVO downClaw(DollBusDownClawReqVO vo) {
        return customerDollBusService.downClaw(vo.getBusId(), vo.getUid(),
                vo.getOptId(), vo.getType());
    }

    @SignCheck
    @ApiHandle
    @LogHead("查询捉取结果接口")
    @Path("api/wawa/queryResult")
    @ApiOperation(value = "查询捉取结果接口", notes = "查询捉取结果接口", httpMethod = "POST", response = DollBusResultRespVO.class)
    public BaseRespVO queryResult(DollOptResultReqVO reqVo) {
        OptRecord optRecord = customerDollBusService.queryOptResult(
                reqVo.getOptId(), reqVo.getAppId());
        if (optRecord != null) {
            return new DollBusResultRespVO(new OptRecordDTO(optRecord));
        }
        return new BaseRespVO(-1, false, "无抓取记录");
    }

    @SignCheck
    @ApiHandle
    @LogHead("查询机器配置接口")
    @Path("api/wawa/querySetting")
    @ApiOperation(value = "查询机器配置接口(弃用)", notes = "查询机器配置接口(弃用)", httpMethod = "POST", response = DollSettingQueryRespVO.class)
    public BaseRespVO querySetting(DollSettingQueryReqVO reqVo) {
        MachineDevice device = iMachineRemoteService.getMachineState(reqVo
                .getBusId());
        if (device != null) {
            return new DollSettingQueryRespVO(device);
        }
        return new BaseRespVO(0, false, "机器不存在");
    }

    @SignCheck
    @ApiHandle
    @LogHead("设置机器配置接口")
    @Path("api/wawa/setting")
    @ApiOperation(value = "设置机器配置接口(弃用)", notes = "设置机器配置接口(弃用)", httpMethod = "POST", response = DollSettingQueryRespVO.class)
    public BaseRespVO setting(DollSettingReqVO reqVo) {
       /* MachineDevice device = iMachineRemoteService.updateDeviceSetting(
                reqVo.getBusId(), reqVo.getType(), reqVo.getValue());
        if (device != null) {
            return new DollSettingQueryRespVO(device);
        }*/
        return new BaseRespVO(0, false, "机器不存在");
    }

    //
    //
    // @ApiHandle
    // @LogHead("第三方应用登陆")
    // @Path("api/wawa/login")
    // @ApiOperation(value = "第三方应用登陆", notes = "第三方应用登陆", httpMethod = "POST",
    // response = UserSrcLoginRespVO.class)
    // public BaseRespVO login(UserSrcLoginReqVO reqVo) {
    // return
    // customerDollBusService.srcUserLogin(reqVo.getSrcId(),reqVo.getSrcCode(),reqVo.getCustomerId());
    // }

    @ApiHandle
    @LogHead("获取录播")
    @Path("api/wawa/video")
    @ApiOperation(value = "获取录播接口", notes = "获取录播", httpMethod = "POST", response = DollVideoRespVO.class)
    public BaseRespVO video(DollVideoReqVO reqVo) {
        String hubName = "suanguolive";
        if (reqVo.getStreamUrl().contains("laizhuawa")) {
        	hubName = "laizhuawa";
		}
        if (reqVo.getStreamUrl().contains("zengjjing")) {
        	hubName = "zengjjing";
		}
        SaveRet res = null;
        try {
            if (!StringUtil.isNullOrEmpty(reqVo.getStreamUrl())) {
                String urlString = reqVo.getStreamUrl().split(hubName+"/")[1];
                res = QNHandle.getVideoUrl(hubName, urlString,
                        reqVo.getStart(), reqVo.getEnd());
            }
        } catch (UnsupportedEncodingException e) {
            PrintException.printException(logger, e);
        } catch (PiliException e) {
            PrintException.printException(logger, e);
        }
        DollVideoRespVO respVO = new DollVideoRespVO();
        if (res != null) {
            respVO.setSaveRes(res);
        } else {
            return new BaseRespVO(0, false, "无法生成该录播，请稍后再试");
        }
        return respVO;
    }



    public static void main(String[] args) {

        //System.out.println(DateUtils.getNumberLong());

        Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("start", 1531713556);
        postData.put("end", 1531713617);
        postData.put("streamUrl", "rtmp://pili-live-rtmp-zj.realgamecloud.com/zengjjing/tuibihuang05");
        HttpEntity httpEntity = HttpUtil.Post("http://www.realgamecloud.com/api/wawa/video", JSON.toJSONString(postData));
        System.out.println(httpEntity.getHtml());
    }
}
