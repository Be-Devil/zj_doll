package com.imlianai.zjdoll.app.modules.support.event.invite20180320.dao;

import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteUsedTimes;

/**
 * @author wurui
 * @create 2018-03-26 11:07
 **/
public interface Event20180320InviteUsedTimesDao {

    int init(long uid);

    int update(long uid);

    Event20180320InviteUsedTimes getByUid(long uid);
}
