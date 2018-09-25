package com.imlianai.dollpub.app.modules.support.shopkeeper.vo;

import com.imlianai.dollpub.app.modules.support.shopkeeper.dto.ShopkeeperDayRankDTO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wurui
 * @create 2018-05-02 10:33
 **/
@ApiModel(value = "店主每日榜单响应对象")
public class RankRespVO extends BaseRespVO {

    @ApiModelProperty(value = "榜单列表")
    private List<ShopkeeperDayRankDTO> rankList;

    public List<ShopkeeperDayRankDTO> getRankList() {
        return rankList;
    }

    public void setRankList(List<ShopkeeperDayRankDTO> rankList) {
        this.rankList = rankList;
    }

    public RankRespVO() {
    }

    public RankRespVO(List<ShopkeeperDayRankDTO> rankList) {
        super(100,true,"数据请求成功");
        this.rankList = rankList;
    }

}
