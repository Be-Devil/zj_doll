package com.imlianai.zjdoll.app.modules.core.doll.dto;


import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.pushcoin.PushCoinBox;
import org.springframework.beans.BeanUtils;

/**
 * @author wurui
 * @create 2018-06-13 15:36
 **/
public class DollBusDto {

    private int busId;

    private String nickName;

    private int status;

    private String stream1;

    private String stream2;

    private int valid;

    private int type;

    private int price;


    private int jackpot;
    private int total;
    private int current;
    private int rate;


    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStream1() {
        return stream1;
    }

    public void setStream1(String stream1) {
        this.stream1 = stream1;
    }

    public String getStream2() {
        return stream2;
    }

    public void setStream2(String stream2) {
        this.stream2 = stream2;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getJackpot() {
        return jackpot;
    }

    public void setJackpot(int jackpot) {
        this.jackpot = jackpot;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public static DollBusDto adapter(DollBus dollBus, PushCoinBox pushCoinBox){
        DollBusDto dollBusDto = new DollBusDto();
        if (dollBus != null){
            BeanUtils.copyProperties(dollBus,dollBusDto);

            //推币机数据
            if (pushCoinBox != null && dollBus.getType()==1){
                BeanUtils.copyProperties(pushCoinBox,dollBusDto);
            }
        }

        return dollBusDto;
    }

}
