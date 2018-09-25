package com.imlianai.dollpub.app.modules.support.yy.controller;

import com.google.common.collect.Maps;
import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.support.yy.service.YYService;
import com.imlianai.rpc.support.common.BaseModel;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.Map;

/**
 * yy登录
 * @author wurui
 * @create 2018-07-18 15:17
 **/
@Component("yyLogin")
public class YYLoginCmd extends RootCmd{

    @Resource
    private YYService yyService;

    @ApiHandle
    @Path("api/yyLogin/doLogin")
    public BaseRespVO doLogin(YYRequert requert){
        try {
            String resp = yyService.handleLogin(requert.getUsername(),requert.getPwdencrypt(),requert.getOauth_token(),requert.getIsRemMe(),requert.getHiido());
            Map<String,Object> map = Maps.newHashMap();
            map.put("resp",resp);


            System.out.println(resp);
            return new BaseRespVO(map);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BaseRespVO(0,false,"请求失败");
    }





}

class YYRequert extends BaseModel{

    private String username;
    private String pwdencrypt;
    private String oauth_token;
    private String isRemMe;
    private String hiido;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwdencrypt() {
        return pwdencrypt;
    }

    public void setPwdencrypt(String pwdencrypt) {
        this.pwdencrypt = pwdencrypt;
    }

    public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public String getIsRemMe() {
        return isRemMe;
    }

    public void setIsRemMe(String isRemMe) {
        this.isRemMe = isRemMe;
    }

    public String getHiido() {
        return hiido;
    }

    public void setHiido(String hiido) {
        this.hiido = hiido;
    }
}
