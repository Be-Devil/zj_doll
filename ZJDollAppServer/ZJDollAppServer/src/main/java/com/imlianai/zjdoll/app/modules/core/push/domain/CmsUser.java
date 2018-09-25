package com.imlianai.zjdoll.app.modules.core.push.domain;

import com.imlianai.rpc.support.common.BaseModel;
import com.imlianai.rpc.support.utils.MD5_HexUtil;

import java.util.Date;
import java.util.UUID;

public class CmsUser extends BaseModel {
    private int id;

    private String name;

    private String email;

    private String pwd;

    private String salt;

    private Date createTime;

    private Date onlineTime;

    private int status; // 0禁止 1允许

    private int modId;

    private String modName;

    private Date modTime;

    private String rids;

    private int isPlayer;

    public CmsUser() {
    }

    //新建内部用户
    public CmsUser(String email) {
        this.id = 0;
        this.name = email.split("@")[0];
        this.email = email;
        this.salt = UUID.randomUUID().toString().replace("-","");
        this.pwd = MD5_HexUtil.md5Hex("123456" + this.salt);
        this.status = 1;
        this.rids = "[]";
        this.modId = 1;
    }


    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getModId() {
        return modId;
    }

    public void setModId(int modId) {
        this.modId = modId;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public String getRids() {
        return rids;
    }

    public void setRids(String rids) {
        this.rids = rids;
    }


    public int getIsPlayer() {
        return isPlayer;
    }

    public void setIsPlayer(int isPlayer) {
        this.isPlayer = isPlayer;
    }

    public static void main(String[] args) {
        String salt = UUID.randomUUID().toString().replace("-","");
        System.out.println(salt);
        System.out.println(MD5_HexUtil.md5Hex("123456dbd8c424cf074f2d9e14e3d44820f7f6"));
    }
}

