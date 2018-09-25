package com.imlianai.dollpub.app.modules.core.api.vo;

import com.imlianai.dollpub.domain.user.UserCommon;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wurui
 * @create 2018-01-24 16:13
 **/
@ApiModel("登录返回对象")
public class UserSrcLoginRespVO extends BaseRespVO {

    @ApiModelProperty("登录校验码")
    private String loginKey;
    @ApiModelProperty("用户id")
    private long uid;
    @ApiModelProperty("用户基础信息")
    private UserCommon userCommon;

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public UserCommon getUserCommon() {
        return userCommon;
    }

    public void setUserCommon(UserCommon userCommon) {
        this.userCommon = userCommon;
    }
}
