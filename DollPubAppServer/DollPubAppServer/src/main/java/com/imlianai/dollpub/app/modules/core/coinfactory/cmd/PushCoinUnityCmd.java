package com.imlianai.dollpub.app.modules.core.coinfactory.cmd;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.coinfactory.service.PushCoinUnity3DService;
import com.imlianai.dollpub.app.modules.core.coinfactory.vo.*;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.user.customer.service.CustomerService;
import com.imlianai.dollpub.domain.coinfactory.virtual.base.PushCoinVirtualConfig;
import com.imlianai.dollpub.domain.customer.Customer;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.SignCheck;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;


/**
 * @author wurui
 * @create 2018-05-19 16:47
 **/
@Component("pushCoinVirtual")
public class PushCoinUnityCmd extends RootCmd {

    @Resource
    private CustomerService customerService;

    @Resource
    private DollBusService dollBusService;

    @Resource
    private PushCoinUnity3DService pushCoinUnity3DService;


    @SignCheck
    @LogHead("进入虚拟推币机房间接口")
    @ApiHandle
    @Path("api/pushCoinVirtual/entryRoom")
    @ApiOperation(value = "进入虚拟房间", notes = "进入房间", httpMethod = "POST", response = BaseRespVO.class, position = 1)
    public BaseRespVO entryRoom(EntryVirtualRoomReqVO reqVO) {
        return pushCoinUnity3DService.entryVirtualCoinPushRoom(reqVO);
    }


    @SignCheck
    @ApiHandle
    @LogHead("虚拟推币机上机接口")
    @Path("api/pushCoinVirtual/apply")
    @ApiOperation(value = "上机", notes = "上机成功后返回操作ID", httpMethod = "POST", response = OperateRespVO.class, position = 2)
    public BaseRespVO apply(OperateVirtualPlayReqVO vo) {
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "上机失败,商户不存在");
        }
        if (vo.getWeight() == 0) {
            vo.setWeight(1);
        }

        return pushCoinUnity3DService.apply(vo.getUid(), vo.getBusId(), customer, vo.getWeight());
    }


    @SignCheck
    @ApiHandle
    @LogHead("虚拟推币机投币接口")
    @Path("api/pushCoinVirtual/push")
    @ApiOperation(value = "投币", notes = "投币相当于上机，投币之后超过30秒未继续投币则自动结束上机", httpMethod = "POST", response = OperateRespVO.class, position = 3)
    public BaseRespVO push(OperateVirtualPlayReqVO vo) {
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "商户不存在");
        }
        if (vo.getWeight() == 0) {
            vo.setWeight(1);
        }

        return pushCoinUnity3DService.putCoin(vo.getUid(), vo.getBusId(), customer, vo.getWeight());
    }


    @SignCheck
    @ApiHandle
    @LogHead("虚拟推币机摆动接口")
    @Path("api/pushCoinVirtual/operate")
    @ApiOperation(value = "摆动", notes = "摆动", httpMethod = "POST", response = BaseRespVO.class, position = 4)
    public BaseRespVO operate(OperateVirtualReqVO vo) {
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "商户不存在");
        }
        return pushCoinUnity3DService.operate(vo.getUid(), vo.getBusId(), customer);
    }


    @SignCheck
    @ApiHandle
    @LogHead("虚拟推币机下机接口")
    @Path("api/pushCoinVirtual/finish")
    @ApiOperation(value = "主动下机", notes = "主动下机", httpMethod = "POST", response = BaseRespVO.class, position = 5)
    public BaseRespVO finish(OperateVirtualReqVO vo) {
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer != null) {
            return pushCoinUnity3DService.finish(vo.getUid(), vo.getBusId(), customer);
        }
        return new BaseRespVO(0, false, "当前商户不存在");
    }

    @SignCheck
    @ApiHandle
    @LogHead("离开虚拟推币机房间接口")
    @Path("api/pushCoinVirtual/leave")
    @ApiOperation(value = "离开虚拟房间", notes = "离开房间", httpMethod = "POST", response = BaseRespVO.class, position = 6)
    public BaseRespVO leave(EntryVirtualRoomReqVO reqVO) {
        return pushCoinUnity3DService.laveVirtualCoinPushRoom(reqVO);
    }

    @SignCheck
    @ApiHandle
    @LogHead("获取虚拟推币机配置接口")
    @Path("api/pushCoinVirtual/config")
    @ApiOperation(value = "获取虚拟推币机配置", notes = "获取虚拟推币机配置", httpMethod = "POST", response = BaseRespVO.class, position = 7)
    public BaseRespVO config(EntryVirtualRoomReqVO reqVO) {
        DollBus dollBus = dollBusService.getDollBus(reqVO.getBusId());
        if (dollBus != null && dollBus.getVirtual() == 1 && dollBus.getType() == 1) {
            PushCoinVirtualConfig config = pushCoinUnity3DService.getVirtualPushCoinConfig(reqVO.getBusId());
            if (config != null) {
                BaseRespVO respVO = new BaseRespVO(200, true, "数据请求成功");
                respVO.setData(config);
                return respVO;
            }
        }
        return new BaseRespVO(0, false, "机器不存在或类型不正确");
    }

    @SignCheck
    @ApiHandle
    @LogHead("虚拟推币机刷新时间接口")
    @Path("api/pushCoinVirtual/refresh")
    @ApiOperation(value = "刷新时间", notes = "刷新到最新时间", httpMethod = "POST", response = OperateRespVO.class, position = 8)
    public BaseRespVO refresh(OperateVirtualRefreshReqVO vo) {
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer == null) {
            return new BaseRespVO(-1, false, "商户不存在");
        }
        BaseRespVO respVO = new BaseRespVO();


        int result = pushCoinUnity3DService.updateEndTime(vo.getUid(), vo.getOptId(), vo.getBusId(), customer);
        switch (result) {
            case 1:
                respVO.setMsg("刷新时间成功");
                respVO.setResult(200);
                respVO.setData(pushCoinUnity3DService.getGameTime(vo.getBusId()));
                respVO.setState(true);
                break;
            case 0:
                respVO.setMsg("刷新时间过快");
                respVO.setResult(100);
                respVO.setData(pushCoinUnity3DService.getGameTime(vo.getBusId()));
                respVO.setState(true);
                break;
            case -1:
            case -2:
            case -3:
            case -4:
                respVO.setMsg("刷新时间失败");
                respVO.setResult(result);
                respVO.setState(false);
                break;
        }
        return respVO;
    }


    @SignCheck
    @ApiHandle
    @LogHead("获取虚拟推币机用户上机状态接口")
    @Path("api/pushCoinVirtual/getStatus")
    @ApiOperation(value = "用户上机状态", notes = "用户上机状态", httpMethod = "POST", response = BaseRespVO.class, position = 7)
    public BaseRespVO getStatus(OperateVirtualReqVO reqVO) {
        Customer customer = customerService.getCustomer(reqVO.getAppId());
        if (customer != null) {
            return pushCoinUnity3DService.getVirtualByUser(reqVO.getUid(), reqVO.getBusId(), customer);
        }
        return new BaseRespVO(0, false, "当前商户不存在");
    }


    @SignCheck
    @ApiHandle
    @LogHead("虚拟推币机回调接口")
    @Path("api/pushCoinVirtual/callback")
    public BaseRespVO callback(VirtualCallBackReqVO vo) {
        Customer customer = customerService.getCustomer(vo.getAppId());
        if (customer != null) {
            DollBus dollBus = dollBusService.getDollBus(vo.getBusId());
            if (dollBus != null) {
                return pushCoinUnity3DService.handleCallback(vo.getP1(), vo.getP2(), vo.getP3(), vo.getP4(), vo.getP5(), vo.getP6(), vo.getP7(), vo.getP8(), vo.getP9(), vo.getP10(), vo.getP11(), dollBus, vo.getUid(), customer);
            } else {
                return new BaseRespVO(0, false, "当前机器不存在");
            }
        }
        return new BaseRespVO(0, false, "当前商户不存在");
    }


}
