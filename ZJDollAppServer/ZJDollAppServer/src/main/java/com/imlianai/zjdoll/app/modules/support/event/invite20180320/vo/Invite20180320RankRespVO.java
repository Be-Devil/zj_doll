package com.imlianai.zjdoll.app.modules.support.event.invite20180320.vo;

import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.dto.Event20180320InviteEnergyRankDTO;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.util.Event20180320InviteUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wurui
 * @create 2018-03-20 20:01
 **/
@ApiModel(value = "获取邀请奖励榜单对象")
public class Invite20180320RankRespVO extends BaseRespVO{

    /**
     * 活动时间
     */
    @ApiModelProperty(value = "活动时间")
    private long residueTime;

    @ApiModelProperty(value = "当前用户UID")
    private long uid;

    @ApiModelProperty(value = "当前用户头像")
    private String headImg;

    @ApiModelProperty(value = "当前用户名")
    private String name;

    @ApiModelProperty(value = "当期用户总能量")
    private int TotalEnergy;

    @ApiModelProperty(value = "当期用户排名")
    private int rank;

    @ApiModelProperty(value = "榜单列表")
    private List<Event20180320InviteEnergyRankDTO> rankList;

    public long getResidueTime() {
        return residueTime;
    }

    public void setResidueTime(long residueTime) {
        this.residueTime = residueTime;
    }

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
        return TotalEnergy;
    }

    public void setTotalEnergy(int totalEnergy) {
        TotalEnergy = totalEnergy;
    }

    public List<Event20180320InviteEnergyRankDTO> getRankList() {
        return rankList;
    }

    public void setRankList(List<Event20180320InviteEnergyRankDTO> rankList) {
        this.rankList = rankList;
    }

    public Invite20180320RankRespVO(long residueTime, long uid, String headImg, String name, int totalEnergy) {
        this.residueTime = residueTime;
        this.uid = uid;
        this.headImg = headImg;
        this.name = name;
        this.TotalEnergy = totalEnergy;
    }


    public Invite20180320RankRespVO(UserGeneral userGeneral) {
        this.residueTime = Event20180320InviteUtil.getResidueTime();
        if (userGeneral != null){
            this.uid = userGeneral.getUid();
            this.headImg = userGeneral.getHead();
            this.name = userGeneral.getName();
        }
    }

    public Invite20180320RankRespVO() {
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
