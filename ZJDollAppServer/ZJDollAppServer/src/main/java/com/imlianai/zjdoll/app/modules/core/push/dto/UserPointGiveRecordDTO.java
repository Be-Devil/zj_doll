package com.imlianai.zjdoll.app.modules.core.push.dto;

import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollInfo;
import com.imlianai.zjdoll.domain.pushcoin.PushCoinBoxPointGive;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;

/**
 * @author wurui
 * @create 2018-06-20 11:56
 **/
public class UserPointGiveRecordDTO extends PushCoinBoxPointGive {

    private String name;

    private String nickName;

    private String createTimeStr;

    private String endTimeStr;

    //额外赠送的娃娃
    private Map<String,String> dollMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Map<String, String> getDollMap() {
        return dollMap;
    }

    public void setDollMap(Map<String, String> dollMap) {
        this.dollMap = dollMap;
    }

    public UserPointGiveRecordDTO() {
    }


    public static UserPointGiveRecordDTO adapter(UserGeneral userGeneral, DollBus dollBus, PushCoinBoxPointGive pushCoinBoxPointGive){
        UserPointGiveRecordDTO userPointGiveRecordDTO = new UserPointGiveRecordDTO();
        if (pushCoinBoxPointGive != null){
            BeanUtils.copyProperties(pushCoinBoxPointGive,userPointGiveRecordDTO);
            //设置额外的时间
            userPointGiveRecordDTO.setCreateTimeStr(DateUtils.dateToString(pushCoinBoxPointGive.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            userPointGiveRecordDTO.setEndTimeStr(DateUtils.dateToString(pushCoinBoxPointGive.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
            if (dollBus != null){
                userPointGiveRecordDTO.setNickName(dollBus.getNickName());
            }
            if (userGeneral != null){
                userPointGiveRecordDTO.setName(userGeneral.getName());
            }
        }
        return userPointGiveRecordDTO;
    }
}
