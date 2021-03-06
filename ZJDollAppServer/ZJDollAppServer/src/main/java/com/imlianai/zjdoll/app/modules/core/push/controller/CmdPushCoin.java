package com.imlianai.zjdoll.app.modules.core.push.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.iface.IAppPushCoinRemoteService;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.ZengjingUtils;
import com.imlianai.zjdoll.app.modules.core.push.consts.PushCoinConsts;
import com.imlianai.zjdoll.app.modules.core.push.service.PushCoinService;
import com.imlianai.zjdoll.app.modules.core.push.vo.*;
import com.imlianai.zjdoll.app.modules.core.trade.service.TradeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;

/**
 * @author wurui
 * @create 2018-05-17 22:01
 **/
@Component("pushCoin")
public class CmdPushCoin extends RootCmd {

    @Reference
    private IAppPushCoinRemoteService iAppPushCoinRemoteService;

    @Resource
    private TradeService  tradeService;

    @Resource
    private PushCoinService pushCoinService;

    /**
     * 上机
     *
     * @return
     */
    @LoginCheck
    @ApiHandle
    @Path("api/pushCoin/apply")
    public BaseRespVO apply(OperateReqVO vo) {
        return pushCoinService.apply(vo.getUid(),vo.getBusId(),vo.getCustomerId());
    }

    /**
     * 投币
     *
     * @return
     */
    @LoginCheck
    @ApiHandle
    @Path("api/pushCoin/push")
    public BaseRespVO push(OperateReqVO vo) {
        return pushCoinService.push(vo.getUid(),vo.getBusId(),vo.getCustomerId(),vo.getOptId());
    }

    /**
     * 操作(摆动)
     *
     * @return
     */
    @LoginCheck
    @ApiHandle
    @Path("api/pushCoin/operate")
    public BaseRespVO operate(OperateReqVO vo) {
        return pushCoinService.operate(vo.getUid(),vo.getBusId());
    }

    /**
     * 主动下机
     *
     * @return
     */
    @LoginCheck
    @ApiHandle
    @Path("api/pushCoin/finish")
    public BaseRespVO finish(OperateReqVO vo) {
        return pushCoinService.finish(vo.getUid(),vo.getBusId(),vo.getCustomerId());
    }

    /**
     * 查询流水
     *
     * @param vo
     * @return
     */
    @LoginCheck
    @ApiHandle
    @Path("api/pushCoin/query")
    public BaseRespVO query(PushCoinBusQueryReqVO vo) {
        return pushCoinService.query(vo.getOptId(),vo.getCustomerId());
    }

    /**
     * 查账
     * @param vo
     * @return
     */
    @LoginCheck
    @ApiHandle
    @Path("api/pushCoin/queryAccount")
    public BaseRespVO queryAccount(BaseReqVO vo) {
        BaseRespVO respVO = new BaseRespVO(100,true,"数据请求成功");
        respVO.setData(tradeService.getAccount(vo.getUid()));
        return respVO;
    }


    /**
     * 开宝箱
     *
     * @param vo
     * @return
     */
    @LoginCheck
    @ApiHandle
    @Path("api/pushCoin/openBox")
    public BaseRespVO openBox(OpenBoxReqVO vo) {
        return pushCoinService.openBox(vo.getBusId(),vo.getUid());
    }

    /**
     * 查看宝箱值
     *
     * @param vo
     * @return
     */
    @LoginCheck
    @ApiHandle
    @Path("api/pushCoin/queryBoxValue")
    public BaseRespVO queryBoxValue(OperateReqVO vo) {
        return pushCoinService.getBoxValue(vo.getBusId());
    }


    @ApiHandle
    @Path("api/pushCoin/CoinToJewel")
    public BaseRespVO CoinToJewel(CallbackReqVO vo){
        if (!StringUtil.isNullOrEmpty(vo)){
            logger.info("CoinToJewel=>" + JSON.toJSONString(vo));
            if (null != vo.getAuto() && vo.getAuto().equals(PushCoinConsts.SERVICE_AUTO)){
               if(pushCoinService.CoinToJewel(vo.getUid(),vo.getBusId(),vo.getOptId(),vo.getOutCoin(),vo.getIsFinal()) == 1) {
                   return new BaseRespVO(200,true,"数据请求成功");
               }else {
                   return new BaseRespVO(100,true,"数据请求成功");
               }
            }
        }
        return new BaseRespVO(0,false,"非法操作");
    }


    @ApiHandle
    @Path("api/pushCoin/getStatus")
    public BaseRespVO getStatus(OperateReqVO vo){
        if (!StringUtil.isNullOrEmpty(vo)){
            return pushCoinService.getStatus(vo.getBusId(),vo.getCustomerId());
        }
        return new BaseRespVO(0,false,"非法操作");
    }


    @ApiHandle
    @Path("api/pushCoin/getOperatorRecord")
    public BaseRespVO getOperatorRecord(OperatorRecordReqVO vo){
        if (!StringUtil.isNullOrEmpty(vo)){
            return pushCoinService.getOperatorRecord(vo.getBusId(),vo.getOptId(),vo.getUid());
        }
        return new BaseRespVO(0,false,"非法操作");
    }

    @ApiHandle
    @Path("api/pushCoin/getOperatorRecordList")
    public BaseRespVO getOperatorRecordList(OperatorRecordReqVO vo){
        if (!StringUtil.isNullOrEmpty(vo)){
            return pushCoinService.getOperatorRecordList(vo.getBusId(),vo.getOptId(),vo.getUid());
        }
        return new BaseRespVO(0,false,"非法操作");
    }



    @ApiHandle
    @Path("api/pushCoin/addPointGiveRecord")
    public BaseRespVO addPointGiveRecord(BoxPointGiveReqVO vo){
        if (!StringUtil.isNullOrEmpty(vo)){
            return pushCoinService.addPointGiveBoxRecord(vo.getBusId(),vo.getUid(),vo.getExJewel(),vo.getExCoin(),vo.getExScore(),vo.getExCoupon(),vo.getExDoll());
        }
        return new BaseRespVO(0,false,"非法操作");
    }

    @ApiHandle
    @Path("api/pushCoin/getPointGiveRecordList")
    public BaseRespVO getPointGiveRecordList(BaseReqVO vo){
        if (!StringUtil.isNullOrEmpty(vo)){
            return pushCoinService.getPointGiveBoxRecordList(vo.getUid());
        }
        return new BaseRespVO(0,false,"非法操作");
    }

    @ApiHandle
    @Path("api/pushCoin/deletePointGiveRecord")
    public BaseRespVO deletePointGiveRecord(BoxPointGiveReqVO vo){
        if (!StringUtil.isNullOrEmpty(vo)){
            return pushCoinService.deletePointGiveBoxRecordById(vo.getId());
        }
        return new BaseRespVO(0,false,"非法操作");
    }


}
