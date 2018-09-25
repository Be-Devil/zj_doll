package com.imlianai.dollpub.app.modules.support.shopkeeper.dao;

import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperQRCode;

/**
 * 店主二维码dao
 * @author wurui
 * @create 2018-04-26 17:44
 **/
public interface ShopkeeperQRCodeDao {
    /**
     * 初始化二维码
     * @param shopkeeperQRCode
     * @return
     */
    int init(ShopkeeperQRCode shopkeeperQRCode);

    /**
     * 更新二维码图片和数据
     * @param shopkeeperQRCode
     * @return
     */
    int updateQRCodeImgAndData(ShopkeeperQRCode shopkeeperQRCode);

    /**
     * 获取二维码
     * @param uid
     * @return
     */
    ShopkeeperQRCode getQRCode(long uid);

    /**
     * 获取邀请码
     * @param location
     * @return
     */
    long getInviteCode(long location);

}
