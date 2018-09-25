package com.imlianai.zjdoll.app.modules.support.agency.dto;

import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.zjdoll.domain.agency.UserAgency;

import java.util.List;

/**
 * @author wurui
 * @create 2018-08-15 21:50
 **/
public class UserAgencyListDTO {

    private long uid;
    private String name;
    private String time;
    private long phone;
    private int status;// 0:正在审核 1:已成为代理商 2:已取消代理
    private int totalInvite;//总邀请
    private double totalCharge;//总充值

    private List<UserAgencyDTO> list;

    public UserAgencyListDTO(UserAgency userAgency) {
        if (userAgency != null) {
            setUid(userAgency.getUid());
            setName(userAgency.getName());
            setPhone(userAgency.getPhone());
            setStatus(userAgency.getStatus());
            setTime(DateUtils.dateToString(userAgency.getTime(), "yyyy-MM-dd HH:mm:ss"));
        }
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalInvite() {
        return totalInvite;
    }

    public void setTotalInvite(int totalInvite) {
        this.totalInvite = totalInvite;
    }

    public List<UserAgencyDTO> getList() {
        return list;
    }

    public void setList(List<UserAgencyDTO> list) {
        this.list = list;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}


