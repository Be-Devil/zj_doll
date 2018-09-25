package com.imlianai.dollpub.app.modules.support.probability.dao;

import com.imlianai.dollpub.domain.user.UserDownClaw;
import com.imlianai.dollpub.domain.user.UserDownClawRecord;
import com.imlianai.dollpub.machine.iface.domain.MachineOptRecord;

/**
 * @author wurui
 * @create 2018-04-10 15:37
 **/
public interface DownClawDao {

//    down_claw_user

    int init(Long uid);
    int updateUserDownClaw(Long uid, int num,int time);
    int updateNum(Long uid, int num);
    int updateTimeAddOne(Long uid);
    UserDownClaw get(Long uid);

//    down_claw_user_record
    int addUserDownClawRecord(UserDownClawRecord record);
    UserDownClawRecord getRecord(long uid,long optId);

    /**
     * 更新下爪抓中的结果
     * @param uid
     * @param optId
     * @param result
     * @return
     */
    int updateResult(long uid,long optId,int result);

    /**
     * 查询在某个时间段内下爪抓中的个数
     * @param busId
     * @param catchSafeTime
     * @return
     */
    int selectDownClawCatchSuccessCount(int busId,int catchSafeTime);

//    down_claw_count

    int saveOrUpdateDownClawCount(String deviceId, int time);
    int getDownClawTime(String deviceId);

//    down_claw_record

    int saveDownClawRecord(String deviceId, int time, String typeDesc, int dateCode);


//    opt

    MachineOptRecord getMachineOptRecord(long optId);
}
