package com.imlianai.zjdoll.app.modules.support.event.invite20180320.dto;

import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteEnergyRank;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.util.Event20180320InviteUtil;

import io.swagger.annotations.ApiModel;

/**
 * @author wurui
 * @create 2018-03-20 19:51
 **/
@ApiModel(value = "榜单列表")
public class Event20180320InviteEnergyRankDTO {

    /**
     * uid
     */
    private long uid;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户头像
     */
    private String headImg;

    /**
     * 总能量值
     */
    private int totalEnergy;


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

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(int totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public static Event20180320InviteEnergyRankDTO adapter(Event20180320InviteEnergyRank energyRank,UserGeneral userGeneral){
        Event20180320InviteEnergyRankDTO energyRankDTO = new Event20180320InviteEnergyRankDTO();
        if (energyRank != null){
            energyRankDTO.totalEnergy = energyRank.getTotalEnergy();
            if (userGeneral != null){
                energyRankDTO.uid = userGeneral.getUid();
                energyRankDTO.name = Event20180320InviteUtil.nameFormat(userGeneral.getName());
                energyRankDTO.headImg = userGeneral.getHead();
            }
        }
        return energyRankDTO;
    }

    public Event20180320InviteEnergyRankDTO() {
    }

    public Event20180320InviteEnergyRankDTO(long uid, String name, String headImg, int totalEnergy) {
        this.uid = uid;
        this.name = Event20180320InviteUtil.nameFormat(name);
        this.headImg = headImg;
        this.totalEnergy = totalEnergy;
    }
}
