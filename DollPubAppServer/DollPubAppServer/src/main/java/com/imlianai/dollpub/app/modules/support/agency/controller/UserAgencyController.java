package com.imlianai.dollpub.app.modules.support.agency.controller;

import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.support.agency.service.UserAgencyService;
import com.imlianai.dollpub.app.modules.support.agency.vo.UserAgencyReqVO;
import com.imlianai.dollpub.domain.agency.UserAgency;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;

/**
 * @author wurui
 * @create 2018-08-14 23:02
 **/
@Component("uAgency")
public class UserAgencyController extends RootCmd {

    @Resource
    private UserAgencyService userAgencyService;

    @LoginCheck
    @ApiHandle
    @LogHead("新增代理接口")
    @Path("api/uAgency/applyAgency")
    public BaseRespVO applyAgency(UserAgencyReqVO reqVO) {
        return userAgencyService.addRecord(reqVO);

    }

    @LoginCheck
    @ApiHandle
    @LogHead("查询代理列表接口")
    @Path("api/uAgency/getList")
    public BaseRespVO getList(UserAgencyReqVO reqVO) {
        return userAgencyService.query(reqVO);
    }

    @LoginCheck
    @ApiHandle
    @LogHead("代理登录接口")
    @Path("api/uAgency/login")
    public BaseRespVO login(UserAgencyReqVO reqVO) {
        return userAgencyService.login(reqVO);
    }

    @LoginCheck
    @ApiHandle
    @LogHead("代理入口查询接口")
    @Path("api/uAgency/index")
    public BaseRespVO index(BaseReqVO reqVO){
        UserAgency userAgency = userAgencyService.getUserAgency(reqVO.getUid());
        if (userAgency == null){
            return new BaseRespVO(0,false,"当前用户未申请过代理");
        }

        if (userAgency.getStatus() == 0) {
            return new BaseRespVO(-18, false, "正在审核中哦,再等等吧。");
        }

        return new BaseRespVO(200,true,"代理用户");

    }




    //==================================================后台功能==================================================



    @ApiHandle
    @LogHead("代理注册接口")
    @Path("api/uAgency/register")
    public BaseRespVO register(UserAgencyReqVO reqVO){
        return userAgencyService.register(reqVO);
    }

    @ApiHandle
    @LogHead("代理商户列表接口")
    @Path("api/uAgency/getAgencyList")
    public BaseRespVO getAgencyList(UserAgencyReqVO reqVO){
        if (reqVO.getPhone() == 159753){
            if (null != reqVO.getPwd() && !"".equals(reqVO.getPwd()) && reqVO.getPwd().equals("f340663fcb254ebdb54ff42f00f80f45")){
                return userAgencyService.getAgencyList(reqVO);
            }
        }
        return new BaseRespVO(-200,false,"系统异常");
    }



}
