package com.imlianai.dollpub.app.modules.support.shopkeeper.dao;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperInfo;

import java.util.List;

/**
 * 店主信息dao
 * @author wurui
 * @create 2018-04-26 15:24
 **/
public interface ShopkeeperInfoDao {
    /**
     * 初始化店主
     * @param info
     * @return
     */
    int init(ShopkeeperInfo info);

    /**
     * 更新店家类型和分成比例
     * @param uid
     * @param type
     * @param ratio
     * @return
     */
    int updateTypeAndRatio(long uid, int type, int ratio);

    /**
     * 更新总邀请数
     * @param uid
     * @param totalInviteNum
     * @return
     */
    int updateTotalInviteNum(long uid, int totalInviteNum);

    /**
     * 更新总收益
     * @param uid
     * @param totalIncome
     * @return
     */
    int updateTotalIncome(long uid, int totalIncome);

    /**
     * 更新总提现
     * @param uid
     * @param totalWithdraw
     * @return
     */
    int updateTotalWithdraw(long uid, int totalWithdraw);

    /**
     * 更新可提现金额
     * @param uid
     * @param totalCanWithdraw
     * @return
     */
    int updateTotalCanWithdraw(long uid, int totalCanWithdraw);

    /**
     * 更新店主状态
     * @param status
     * @return
     */
    int updateStatus(int status,long uid);

    /**
     * 查询店家信息
     * @param uid
     * @return
     */
    ShopkeeperInfo getInfoByUid(long uid);

    /**
     * 获取所有店家
     * @return
     */
    List<ShopkeeperInfo> getInfoList();
}
