package com.imlianai.dollpub.app.modules.support.shopkeeper.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperIncomeDay;
import com.imlianai.rpc.support.utils.StringUtil;

/**
 * 店主日收入榜单
 * @author wurui
 * @create 2018-04-27 9:51
 **/
@ApiModel("店主榜单对象")
public class ShopkeeperDayRankDTO implements Comparable<ShopkeeperDayRankDTO>{
    @ApiModelProperty(value = "排名")
    private int rank;
    @ApiModelProperty(value = "头像")
    private String head;
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "当天收益")
    private double income;

    public ShopkeeperDayRankDTO(){}

    public ShopkeeperDayRankDTO(String name,String head,Double income){
    	this.name=name;
    	this.head=head;
    	this.income=income;
    }
    
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public static ShopkeeperDayRankDTO adapter(ShopkeeperIncomeDay incomeDay, int rank){
        ShopkeeperDayRankDTO dto = new ShopkeeperDayRankDTO();
        if (incomeDay != null){
            dto.setRank(rank);
            dto.setHead(incomeDay.getHead());
            if (!StringUtil.isNullOrEmpty(incomeDay.getName())){
                dto.setName(incomeDay.getName());
            }
            dto.setIncome((double)incomeDay.getDayIncome()/100);
        }
        return dto;
    }
    
    @Override
    public int compareTo(ShopkeeperDayRankDTO s) {
          //自定义比较方法，如果认为此实体本身大则返回1，否则返回-1
          if(this.getIncome() < s.getIncome()){
               return 1;
          }
         return -1;
 }
}
