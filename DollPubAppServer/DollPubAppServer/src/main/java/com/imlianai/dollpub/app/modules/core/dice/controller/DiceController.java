package com.imlianai.dollpub.app.modules.core.dice.controller;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.core.dice.service.DiceService;
import com.imlianai.dollpub.app.modules.core.dice.vo.StartOperateReqVO;
import com.imlianai.dollpub.app.modules.core.dice.vo.StopOperateReqVO;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;

/**
 * @author wurui
 * @create 2018-06-01 11:56
 **/
@Component("dice")
public class DiceController extends RootCmd {

    @Resource
    private DiceService diceService;

    @ApiHandle
    @Path("api/dice/start")
    public BaseRespVO start(StartOperateReqVO vo){
        //判断是否可上机
        if(diceService.isPlay(vo.getBusId())){
            long optId = diceService.start(vo.getBusId(),vo.getUid(),vo.getType());
            if (optId > 0){
                BaseRespVO respVO = new BaseRespVO(200,true,"数据请求成功");
                respVO.setData(optId);
                return respVO;
            }else {
                return new BaseRespVO(0,true,"上机失败");
            }
        }else {
            return new BaseRespVO(0,false,"当前机器占用");
        }
    }


    @ApiHandle
    @Path("api/dice/stop")
    public BaseRespVO stop(StopOperateReqVO vo){
        //发送停止摇骰子指令
       return diceService.stop(vo.getOptId(),vo.getBusId(),vo.getUid());
    }


}
