package com.imlianai.dollpub.app.modules.support.shopkeeper.dao.impl;

import com.imlianai.dollpub.app.modules.support.shopkeeper.dao.ShopkeeperQRCodeDao;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperQRCode;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author wurui
 * @create 2018-04-26 17:47
 **/
@Repository
public class ShopkeeperQRcodeDaoImpl implements ShopkeeperQRCodeDao {

    @Resource
    private JdbcHandler jdbcHandler;


    @Override
    public int init(ShopkeeperQRCode shopkeeperQRCode) {
        return jdbcHandler.executeSql("INSERT INTO shopkeeper_qrcode(uid,inviteUid,url,data,code,uuid,remark,createTime) VALUES(?,?,?,?,?,?,?,NOW())",
                shopkeeperQRCode.getUid(),shopkeeperQRCode.getInviteUid(),shopkeeperQRCode.getUrl(),shopkeeperQRCode.getData(),shopkeeperQRCode.getCode(),shopkeeperQRCode.getUuid(),shopkeeperQRCode.getRemark());
    }

    @Override
    public int updateQRCodeImgAndData(ShopkeeperQRCode shopkeeperQRCode) {
        return jdbcHandler.executeSql("UPDATE shopkeeper_qrcode SET url=?,`data`=?,updateTime=NOW() WHERE uid=?",
                shopkeeperQRCode.getUrl(),shopkeeperQRCode.getData(),shopkeeperQRCode.getUid());
    }

    @Override
    public ShopkeeperQRCode getQRCode(long uid) {
        return jdbcHandler.queryOneBySql(ShopkeeperQRCode.class,"SELECT * FROM shopkeeper_qrcode WHERE uid=?",uid);
    }


    @Override
    public long getInviteCode(long location) {
        String getCode = "select code from invite_code_pool where code>? limit 1";
        String getCodeDel = "delete from invite_code_pool where code=? ";
        Long code = jdbcHandler.queryOneBySql(Long.class, getCode, location);
        if (code == null) {
            return 0;
        }
        if (code > 0) {
            int flag = jdbcHandler.executeSql(getCodeDel, code);
            if (flag > 0) {
                return code;
            }
        }
        return 0;
    }
}
