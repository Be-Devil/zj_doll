package com.imlianai.zjdoll.app.modules.core.push.dto;

import com.imlianai.dollpub.domain.coinfactory.MachinePushCoin;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.trade.TradeAccount;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import org.springframework.beans.BeanUtils;

/**
 * @author wurui
 * @create 2018-06-14 10:22
 **/
public class UserPushCoinRecordDTO {

    private long optId;

    //机器id
    private int busId;

    //机器别名
    private String nickName;

    //机器类型
    private int type;

    //流
    private String s1;
    private String s2;

    //状态
    private int state;
    //入币
    private int intoCoin;
    //出币
    private int outCoin;
    //钻石
    private int jewel;

    //虚拟入币
    private int vIntoCoin;

    //虚拟出币
    private int vOutCoin;

    //单价
    private int price;

    //出奖兑换钻石比例
    private int rate;

    private String startTime;

    private String endTime;


    //用户信息
    private long uid;
    private String u_name;
    private int u_coin;
    private int u_jewel;
    private int u_score;
    private int u_coupon;


    public long getOptId() {
        return optId;
    }

    public void setOptId(long optId) {
        this.optId = optId;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIntoCoin() {
        return intoCoin;
    }

    public void setIntoCoin(int intoCoin) {
        this.intoCoin = intoCoin;
    }

    public int getOutCoin() {
        return outCoin;
    }

    public void setOutCoin(int outCoin) {
        this.outCoin = outCoin;
    }

    public int getJewel() {
        return jewel;
    }

    public void setJewel(int jewel) {
        this.jewel = jewel;
    }

    public int getvIntoCoin() {
        return vIntoCoin;
    }

    public void setvIntoCoin(int vIntoCoin) {
        this.vIntoCoin = vIntoCoin;
    }

    public int getvOutCoin() {
        return vOutCoin;
    }

    public void setvOutCoin(int vOutCoin) {
        this.vOutCoin = vOutCoin;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public int getU_coin() {
        return u_coin;
    }

    public void setU_coin(int u_coin) {
        this.u_coin = u_coin;
    }

    public int getU_jewel() {
        return u_jewel;
    }

    public void setU_jewel(int u_jewel) {
        this.u_jewel = u_jewel;
    }

    public int getU_score() {
        return u_score;
    }

    public void setU_score(int u_score) {
        this.u_score = u_score;
    }

    public int getU_coupon() {
        return u_coupon;
    }

    public void setU_coupon(int u_coupon) {
        this.u_coupon = u_coupon;
    }


    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public static UserPushCoinRecordDTO adapter(MachinePushCoin machinePushCoin, UserGeneral userGeneral, TradeAccount tradeAccount, DollBus dollBus){
        UserPushCoinRecordDTO userPushCoinRecordDTO = new UserPushCoinRecordDTO();
        if (machinePushCoin != null){
            BeanUtils.copyProperties(machinePushCoin,userPushCoinRecordDTO);
            //设置转换后的时间
            userPushCoinRecordDTO.setStartTime(DateUtils.dateToString(machinePushCoin.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
            userPushCoinRecordDTO.setEndTime(DateUtils.dateToString(machinePushCoin.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
        }
        if (dollBus != null){
            userPushCoinRecordDTO.setNickName(dollBus.getNickName());
            userPushCoinRecordDTO.setType(dollBus.getType());
            userPushCoinRecordDTO.setS1(dollBus.getStream1());
            userPushCoinRecordDTO.setS2(dollBus.getStream2());
        }
        //设置用户数据
        if (userGeneral != null){
            //userPushCoinRecordDTO.setUid(userGeneral.getUid());
            userPushCoinRecordDTO.setU_name(userGeneral.getName());
        }
        //设置用户账户
        if (tradeAccount != null){
            userPushCoinRecordDTO.setU_coin(tradeAccount.getCoin());
            userPushCoinRecordDTO.setU_jewel(tradeAccount.getJewel());
            userPushCoinRecordDTO.setU_score(tradeAccount.getScore());
            userPushCoinRecordDTO.setU_coupon(tradeAccount.getCoupon());
        }

        return userPushCoinRecordDTO;
    }
}
