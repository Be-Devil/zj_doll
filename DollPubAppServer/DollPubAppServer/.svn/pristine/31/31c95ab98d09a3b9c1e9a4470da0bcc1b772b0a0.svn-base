package com.imlianai.dollpub.app.modules.support.shopkeeper.dao;

import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperInviteRecord;

import java.util.List;

/**
 * 店主邀请记录dao
 * @author wurui
 * @create 2018-04-26 19:44
 **/
public interface ShopkeeperInviteRecordDao {
    /**
     * 新增邀请记录
     * @param record
     * @return
     */
    int addRecord(ShopkeeperInviteRecord record);

    /**
     * 查询店主邀请记录
     * @param inviteUidTop 最初邀请者
     * @param first_or_second 1:一级，2:二级
     * @return
     */
    List<ShopkeeperInviteRecord> getRecord(long inviteUidTop, int first_or_second);

}
