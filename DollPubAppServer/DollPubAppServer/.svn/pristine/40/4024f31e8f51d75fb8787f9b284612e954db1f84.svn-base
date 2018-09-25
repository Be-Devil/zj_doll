package com.imlianai.dollpub.app.modules.core.pinball.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.pinball.consts.PinballConsts;
import com.imlianai.dollpub.app.modules.core.pinball.service.PinballService;
import com.imlianai.dollpub.app.modules.core.pinball.vo.OperatePinballReqVo;
import com.imlianai.dollpub.app.modules.core.pinball.vo.PinballStartGameReqVO;
import com.imlianai.dollpub.machine.iface.IMachineConnectRemoteService;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;

/**
 * 弹珠机请求入口
 * @author wurui
 * @create 2018-07-13 22:21
 **/
@Component("pinball")
public class PinballCmd extends RootCmd {

    @Resource
    private PinballService pinballService;

    @ApiHandle
    @Path("api/pinball/start")
    public BaseRespVO start(PinballStartGameReqVO vo) {
        return pinballService.start(vo.getBusId(), vo.getUid(), vo.getGameId(), vo.getTimeOut(), vo.getBet());
    }

    @ApiHandle
    @Path("api/pinball/bet")
    public BaseRespVO bet(PinballStartGameReqVO vo) {
        return pinballService.bet(vo.getBusId(),vo.getUid(), vo.getBet());
    }

    @ApiHandle
    @Path("api/pinball/ball")
    public BaseRespVO ball(OperatePinballReqVo vo){
        return pinballService.ball(vo.getBusId(),vo.getUid());
    }

    @ApiHandle
    @Path("api/pinball/force")
    public BaseRespVO force(OperatePinballReqVo vo){
        return pinballService.force(vo.getBusId(),vo.getUid(),vo.getPower());
    }

    @ApiHandle
    @Path("api/pinball/launch")
    public BaseRespVO launch(OperatePinballReqVo vo){
        return pinballService.launchCmd(vo.getBusId(),vo.getUid(),vo.getPower(),vo.getBet());
    }

    @ApiHandle
    @Path("api/pinball/set")
    public BaseRespVO set(OperatePinballReqVo vo){
        return pinballService.set(vo.getBusId(),vo.getUid(),vo.getItem(),vo.getLow(),vo.getHigh());
    }



}
