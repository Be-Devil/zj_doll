package com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain;

import com.imlianai.rpc.support.common.BaseModel;

import java.util.Date;

/**
 * 邀请获得能量记录
 * @author wurui
 * @create 2018-03-20 18:44
 **/
public class Event20180320InviteEnergyRecord extends BaseModel {
    private int id;
    /**
     * 被邀请者
     */
    private long uid;
    /**
     * 邀请者
     */
    private long inviteUid;

    /**
     * 最初邀请者
     */
    private long inviteUidTop;

    /**
     * 能量
     */
    private int energy;

    /**
     * 邀请码
     */
    private long code;

    /**
     * 类型（0：邀请奖励，1：第一次抓中娃娃）
     */
    private int type;

    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getInviteUid() {
        return inviteUid;
    }

    public void setInviteUid(long inviteUid) {
        this.inviteUid = inviteUid;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Event20180320InviteEnergyRecord() {
    }

    public long getInviteUidTop() {
        return inviteUidTop;
    }

    public void setInviteUidTop(long inviteUidTop) {
        this.inviteUidTop = inviteUidTop;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Event20180320InviteEnergyRecord(long uid, long inviteUid, long inviteUidTop,int energy, long code, String remark) {
        this.uid = uid;
        this.inviteUid = inviteUid;
        this.inviteUidTop = inviteUidTop;
        this.energy = energy;
        this.code = code;
        this.remark = remark;
    }
}
