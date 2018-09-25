package com.imlianai.dollpub.app.modules.core.user.customer.vo;

import com.imlianai.rpc.support.common.BaseModel;

import java.util.List;

/**
 * @author wurui
 * @create 2018-06-13 15:08
 **/
public class CustomerReqVO extends BaseModel {
    private List<String> appIds;

    public List<String> getAppIds() {
        return appIds;
    }

    public void setAppIds(List<String> appIds) {
        this.appIds = appIds;
    }
}
