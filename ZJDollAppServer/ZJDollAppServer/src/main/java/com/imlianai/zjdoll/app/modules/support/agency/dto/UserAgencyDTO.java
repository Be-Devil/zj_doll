package com.imlianai.zjdoll.app.modules.support.agency.dto;

import com.imlianai.zjdoll.domain.trade.TradeCharge;
import com.imlianai.zjdoll.domain.user.UserGeneral;

/**
 * @author wurui
 * @create 2018-08-14 22:48
 **/
public class UserAgencyDTO {

    private long uid;
    private String name;
    private Double charge;
    private String time;

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

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static UserAgencyDTO adapter(UserGeneral userGeneral, TradeCharge tradeCharge){
        UserAgencyDTO userAgencyDTO = new UserAgencyDTO();
        if (userGeneral != null && tradeCharge != null){

            userAgencyDTO.setUid(userGeneral.getUid());
            userAgencyDTO.setName(userGeneral.getName());

            userAgencyDTO.setCharge(tradeCharge.getCost());
            userAgencyDTO.setTime(tradeCharge.getTime());
        }

        return userAgencyDTO;

    }

}
