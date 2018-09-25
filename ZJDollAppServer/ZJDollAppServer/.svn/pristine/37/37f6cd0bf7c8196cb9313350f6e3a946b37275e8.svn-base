package com.imlianai.zjdoll.app.modules.core.push.virtual.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imlianai.rpc.support.common.BaseModel;
import com.imlianai.rpc.support.utils.StringUtil;

/**
 * 虚拟推币机配置交换对象
 * @author wurui
 * @create 2018-07-17 14:31
 **/
public class VirtualPushCoinConfigDTO extends BaseModel{

    //真实ID
    private int busId;

    //投币间隔 ms
    private int gap;

    //磁铁吸引力-回收率 50-150
    private int suction;

    //前方挡板的倾斜度 0-30
    private int lean;

    //游戏速度 2
    private int game;

    //转盘出奖速度 ms
    private int dial;

    //摇摆速度 ms
    private int sway;

    //游戏时间 默认30s
    private int playTime;

    //是否显示拉霸机开关
    private int isSlots;

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public int getSuction() {
        return suction;
    }

    public void setSuction(int suction) {
        this.suction = suction;
    }

    public int getLean() {
        return lean;
    }

    public void setLean(int lean) {
        this.lean = lean;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public int getDial() {
        return dial;
    }

    public void setDial(int dial) {
        this.dial = dial;
    }

    public int getSway() {
        return sway;
    }

    public void setSway(int sway) {
        this.sway = sway;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getIsSlots() {
        return isSlots;
    }

    public void setIsSlots(int isSlots) {
        this.isSlots = isSlots;
    }

    public static VirtualPushCoinConfigDTO jsonDataToConfig(String data){
        if (!StringUtil.isNullOrEmpty(data)){
            try {
                String config = JSONObject.parseObject(data).getJSONObject("data").getString("config");
                if (config != null){
                    VirtualPushCoinConfigDTO virtualPushCoinConfigDTO = JSON.parseObject(config,VirtualPushCoinConfigDTO.class);
                    if (!StringUtil.isNullOrEmpty(virtualPushCoinConfigDTO) && virtualPushCoinConfigDTO.getBusId() != 0){
                        virtualPushCoinConfigDTO.setBusId(-1);
                        return virtualPushCoinConfigDTO;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return new VirtualPushCoinConfigDTO();
    }


    public static void main(String[] args) {
        String d = "{\"data\":{\"type\":10,\"config\":{\"busId\":146,\"game\":2,\"lean\":10,\"createTime\":1531280380000,\"gap\":100,\"suction\":100,\"playTime\":30,\"sway\":2000,\"dial\":3000}},\"msg\":\"操作成功\",\"result\":100,\"state\":true}";
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSON(d).toString());
        System.out.println(jsonObject.getJSONObject("data").getString("config"));
    }
}
