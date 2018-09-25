package com.imlianai.dollpub.app.modules.core.user.customer.dto;

import com.imlianai.dollpub.domain.doll.DollBus;
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


    public static DollBusDto adapter(DollBus dollBus){
        DollBusDto dollBusDto = new DollBusDto();
        if (dollBus != null){
            BeanUtils.copyProperties(dollBus,dollBusDto);
        }
        return dollBusDto;
    }

}
