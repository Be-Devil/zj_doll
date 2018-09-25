package com.imlianai.dollpub.app.modules.support.shopkeeper.dto;

import com.imlianai.rpc.support.common.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wurui
 * @create 2018-04-28 10:15
 **/
@ApiModel(value = "一级好友")
public class FirstFriendDTO extends BaseModel {

    @ApiModelProperty(value = "昵称")
    private String name;
    @ApiModelProperty(value = "头像")
    private String head;
    @ApiModelProperty(value = "邀请时间")
    private String inviteTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(String inviteTime) {
        this.inviteTime = inviteTime;
    }

    public FirstFriendDTO() {
    }

    public FirstFriendDTO(String name, String head, String inviteTime) {
        this.name = name;
        this.head = head;
        this.inviteTime = inviteTime;
    }
}
