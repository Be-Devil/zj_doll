package com.imlianai.dollpub.app.modules.support.agency.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

/**
 * 代理查询请求对象
 * @author wurui
 * @create 2018-08-14 22:48
 **/
public class UserAgencyReqVO extends BaseReqVO {

    private long phone;
    private String start = "";
    private String end = "";
    private String pwd;

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
