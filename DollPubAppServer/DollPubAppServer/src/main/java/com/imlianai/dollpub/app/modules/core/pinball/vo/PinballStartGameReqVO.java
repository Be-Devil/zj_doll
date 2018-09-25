package com.imlianai.dollpub.app.modules.core.pinball.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

/**
 * 弹珠机开局请求对象
 * @author wurui
 * @create 2018-07-14 11:19
 **/
public class PinballStartGameReqVO extends BaseReqVO {

    private int busId;
    //游戏局数
    private int gameId;
    private int timeOut;
    private int bet;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
}
