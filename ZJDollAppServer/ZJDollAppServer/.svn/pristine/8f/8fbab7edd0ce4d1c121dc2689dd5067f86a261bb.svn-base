package com.imlianai.zjdoll.app.modules.support.event.invite20180320.vo;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteEnergyRecord;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 能量获取记录响应对象
 * @author wurui
 * @create 2018-03-21 20:19
 **/
public class Invite20180320RecordRespVO extends BaseRespVO {

    @ApiModelProperty(value = "当前用户UID")
    private long uid;

    @ApiModelProperty(value = "当前用户头像")
    private String headImg;

    @ApiModelProperty(value = "当前用户名")
    private String name;

    @ApiModelProperty(value = "当期用户总能量")
    private int totalEnergy;

    @ApiModelProperty(value = "当期用户排名")
    private int rank;

    @ApiModelProperty(value = "能量获取记录列表")
    private List<Event20180320InviteEnergyRecord> recordList;


    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(int totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<Event20180320InviteEnergyRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Event20180320InviteEnergyRecord> recordList) {
        this.recordList = recordList;
    }

    public Invite20180320RecordRespVO(long uid, String headImg, String name, List<Event20180320InviteEnergyRecord> recordList) {
        this.uid = uid;
        this.headImg = headImg;
        this.name = name;
        this.recordList = recordList;
    }

    public Invite20180320RecordRespVO() {
    }
    public Invite20180320RecordRespVO(UserGeneral userGeneral,List<Event20180320InviteEnergyRecord> recordList) {

        if (userGeneral != null){
            this.uid = userGeneral.getUid();
            this.headImg = userGeneral.getHead();
            this.name = userGeneral.getName();

            this.setRecordList(recordList);
        }
    }
}
