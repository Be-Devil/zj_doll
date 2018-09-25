package com.imlianai.dollpub.app.modules.support.shopkeeper.vo;

import com.imlianai.dollpub.app.modules.support.shopkeeper.dto.FirstFriendDTO;
import com.imlianai.dollpub.app.modules.support.shopkeeper.dto.FriendTradeChargeDTO;
import com.imlianai.dollpub.app.modules.support.shopkeeper.dto.SecondFriendDTO;
import com.imlianai.dollpub.app.modules.support.shopkeeper.dto.WithdrawRecordDTO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wurui
 * @create 2018-04-28 9:58
 **/
@ApiModel(value = "我的收入请求对象")
public class IncomeRespVO extends BaseRespVO {

    @ApiModelProperty(value = "累积收益",notes = "[以获得收入]=>累积收益")
    private double totalIncome;
    @ApiModelProperty(value = "一级好友分成",notes = "[以获得收入]=>好友分成")
    private double firstDivide;
    @ApiModelProperty(value = "二级好友分成",notes = "[以获得收入]=>二级好友分成")
    private double secondDivide;

    @ApiModelProperty(value = "总充值条数")
    private int totalTradeNum;
    @ApiModelProperty(value = "好友充值记录")
    private List<FriendTradeChargeDTO> tradeRecord;

    @ApiModelProperty(value = "一级好友数",notes = "[已邀请好友]=>一级好友数")
    private int firstFriend;
    @ApiModelProperty(value = "一级好友列表",notes = "[已邀请好友]=>一级好友列表")
    private List<FirstFriendDTO> fistList;

    @ApiModelProperty(value = "二级好友列表",notes = "[已邀请好友]=>二级好友列表")
    private List<SecondFriendDTO> secondList;
    @ApiModelProperty(value = "二级好友数",notes = "[已邀请好友]=>二级好友数")
    private int secondFriend;

    @ApiModelProperty(value = "本月总收入",notes = "[收入提现]=>本月总收入")
    private double nowMonthIncome;
    @ApiModelProperty(value = "本月已提现",notes = "[收入提现]=>本月已提现")
    private double nowMonthWithdraw;
    @ApiModelProperty(value = "可提现金额",notes = "[收入提现]=>可提现金额")
    private double canWithdrawMoney;
    @ApiModelProperty(value = "提现记录")
    private List<WithdrawRecordDTO> records;

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getFirstDivide() {
        return firstDivide;
    }

    public void setFirstDivide(double firstDivide) {
        this.firstDivide = firstDivide;
    }

    public double getSecondDivide() {
        return secondDivide;
    }

    public void setSecondDivide(double secondDivide) {
        this.secondDivide = secondDivide;
    }

    public int getTotalTradeNum() {
        return totalTradeNum;
    }

    public void setTotalTradeNum(int totalTradeNum) {
        this.totalTradeNum = totalTradeNum;
    }

    public List<FriendTradeChargeDTO> getTradeRecord() {
        return tradeRecord;
    }

    public void setTradeRecord(List<FriendTradeChargeDTO> tradeRecord) {
        this.tradeRecord = tradeRecord;
    }

    public int getFirstFriend() {
        return firstFriend;
    }

    public void setFirstFriend(int firstFriend) {
        this.firstFriend = firstFriend;
    }

    public List<FirstFriendDTO> getFistList() {
        return fistList;
    }

    public void setFistList(List<FirstFriendDTO> fistList) {
        this.fistList = fistList;
    }

    public List<SecondFriendDTO> getSecondList() {
        return secondList;
    }

    public void setSecondList(List<SecondFriendDTO> secondList) {
        this.secondList = secondList;
    }

    public int getSecondFriend() {
        return secondFriend;
    }

    public void setSecondFriend(int secondFriend) {
        this.secondFriend = secondFriend;
    }

    public double getNowMonthIncome() {
        return nowMonthIncome;
    }

    public void setNowMonthIncome(double nowMonthIncome) {
        this.nowMonthIncome = nowMonthIncome;
    }

    public double getNowMonthWithdraw() {
        return nowMonthWithdraw;
    }

    public void setNowMonthWithdraw(double nowMonthWithdraw) {
        this.nowMonthWithdraw = nowMonthWithdraw;
    }

    public double getCanWithdrawMoney() {
        return canWithdrawMoney;
    }

    public void setCanWithdrawMoney(double canWithdrawMoney) {
        this.canWithdrawMoney = canWithdrawMoney;
    }

    public List<WithdrawRecordDTO> getRecords() {
        return records;
    }

    public void setRecords(List<WithdrawRecordDTO> records) {
        this.records = records;
    }

    public IncomeRespVO() {
    }
}
