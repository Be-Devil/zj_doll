package com.imlianai.zjdoll.app.modules.support.event.invite20180320.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.imlianai.zjdoll.domain.invite.InviteRelation;
import com.imlianai.zjdoll.domain.msg.MsgNotice;
import com.imlianai.zjdoll.domain.msg.MsgType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.user.dao.UserDAO;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.dao.Event20180320InviteEnergyRankDao;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.dao.Event20180320InviteEnergyRecordDao;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.dao.Event20180320InviteUsedTimesDao;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteEnergyRank;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteEnergyRecord;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteUsedTimes;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.dto.Event20180320InviteEnergyRankDTO;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.util.Event20180320InviteUtil;
import com.imlianai.zjdoll.app.modules.support.invite.service.InviteService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wurui
 * @create 2018-03-20 20:16
 **/
@Service
public class Event20180320InviteEnergyServiceImpl implements Event20180320InviteEnergyService {

    private BaseLogger logger = BaseLogger.getLogger(getClass());

    @Resource
    private Event20180320InviteEnergyRankDao rankDao;

    @Resource
    private Event20180320InviteEnergyRecordDao recordDao;

    @Resource
    private UserService userService;

    @Resource
    private InviteService inviteService;

    @Resource
    private MsgService msgService;

    @Resource
    private UserDAO userDAO;

    @Resource
    private Event20180320InviteUsedTimesDao usedTimesDao;

    @Override
    public List<Event20180320InviteEnergyRankDTO> getRankList() {
        logger.info("===========getRankList=======>获取邀请活动榜单列表" );
        List<Event20180320InviteEnergyRankDTO> rankDTOList = Lists.newArrayList();
        try{
            List<Event20180320InviteEnergyRank> rankList = rankDao.getEnergyRankLimit50();
            logger.info("rankList==>" + JSON.toJSONString(rankList));
            if (!StringUtil.isNullOrEmpty(rankList)){
                List<Long> uIds = Lists.newArrayList();
                for (Event20180320InviteEnergyRank energyRank : rankList){
                    uIds.add(energyRank.getUid());
                }
                Map<Long,UserGeneral> userGeneralMap = userService.getUserGeneralMap(uIds);
                for (Event20180320InviteEnergyRank energyRank : rankList){
                    UserGeneral userGeneral = userGeneralMap.get(energyRank.getUid());
                    if (userGeneral != null){
                        rankDTOList.add(Event20180320InviteEnergyRankDTO.adapter(energyRank,userGeneral));
                    }
                }
            }
        }catch (Exception e){
            logger.error("getRankList : error => " + e.getMessage());
        }
        return rankDTOList;
    }

    @Override
    public Event20180320InviteEnergyRank getOneByUid(long uid) {
        return rankDao.getEnergyRankByUid(uid);
    }

    @Override
    public void inviteUidEnergyHandle(long uid, long inviteUid,long code,String remark) {
        //判断是否有上一个邀请者
        InviteRelation relation_1 = inviteService.getInviteRelationByUid(inviteUid);
        UserGeneral userGeneral = userService.getUserGeneral(uid);
        if (relation_1 != null){
            if (userGeneral != null){
                remark = "成功邀请朋友 " + Event20180320InviteUtil.nameFormat(userGeneral.getName()) + " 加入";
            }
            int usedTimes = usedTimesDao.getByUid(inviteUid).getUsedTimes();
            int energyValue = Event20180320InviteUtil.getEnergyValue(usedTimes);
            Event20180320InviteEnergyRecord energyRecord_1 = new Event20180320InviteEnergyRecord(uid,0,relation_1.getUid(),energyValue,code,remark);
            if (recordDao.addRecord(energyRecord_1) > 0){
                logger.info("新增邀请得能量值记录成功=>" + JSON.toJSONString(energyRecord_1));
                if (rankDao.addEnergyRank(new Event20180320InviteEnergyRank(inviteUid,energyValue)) > 0){
                    logger.info("邀请人=>" + inviteUid + ",获得=>" + energyValue + " 能量值");
                }
            }


            //说明是活动期间的...
            if (recordDao.getRecordByInviteUidTop(relation_1.getInviteUid()) != null){
                //只处理到两级
                InviteRelation relation_2 = inviteService.getInviteRelationByUid(relation_1.getInviteUid());
                if (relation_2 != null ){
                    UserGeneral  a = userService.getUserGeneral(relation_1.getUid());
                    if (a != null && userGeneral != null){
                        remark = "好友 " + Event20180320InviteUtil.nameFormat(a.getName()) + " 邀请了 " + Event20180320InviteUtil.nameFormat(userGeneral.getName()) + " 加入";
                    }
                    Event20180320InviteEnergyRecord energyRecord_2 = new Event20180320InviteEnergyRecord(uid,relation_1.getUid(),relation_2.getUid(),20,code,remark);
                    if (recordDao.addRecord(energyRecord_2) > 0){
                        logger.info("新增邀请得能量值记录成功=>" + JSON.toJSONString(energyRecord_2));
                        if (rankDao.addEnergyRank(new Event20180320InviteEnergyRank(relation_2.getUid(),Event20180320InviteUtil.ENERGY_VALUE_20)) > 0){
                            logger.info("邀请人=>" + relation_2.getUid() + ",获得=>" + Event20180320InviteUtil.ENERGY_VALUE_20 + " 能量值");
                        }
                    }
                    //消息
                    this.sendMsgA_B_C(uid,relation_1.getUid(),Event20180320InviteUtil.ENERGY_VALUE_20);
                }
            }
        }
    }



    @Override
    public void successEnergyHandle(long uid) {
        InviteRelation relation_1 = inviteService.getInviteRelationByUid(uid);
        if (relation_1 != null && relation_1.getInviteUid() != 0){
            String remark = "邀请的好友抓中第一个娃娃获得50能量";
            UserGeneral userGeneral = userService.getUserGeneral(uid);
            if (userGeneral != null){
                remark = "好友 " + userGeneral.getName() + " 抓到第1个娃娃";
            }
            Event20180320InviteEnergyRecord energyRecord_1 = new Event20180320InviteEnergyRecord(uid,0,relation_1.getInviteUid(),Event20180320InviteUtil.ENERGY_VALUE_50,relation_1.getCode(),remark);
            energyRecord_1.setType(1);//标记第一次抓中
            if (recordDao.addRecord(energyRecord_1) > 0){
                logger.info("新增能量值记录成功=>" + JSON.toJSONString(energyRecord_1));
                if (rankDao.addEnergyRank(new Event20180320InviteEnergyRank(energyRecord_1.getInviteUidTop(),Event20180320InviteUtil.ENERGY_VALUE_50)) > 0){
                    logger.info("邀请人=>" + energyRecord_1.getInviteUidTop() + ",获得=>" + Event20180320InviteUtil.ENERGY_VALUE_50 + " 能量值");
                }
            }
        }
        handleCatchDollSuccess(uid);
    }

    @Override
    public List<Event20180320InviteEnergyRecord> getRecordByInviteUidTop(long inviteUidTop) {
        return recordDao.getRecordByInviteUidTop(inviteUidTop);
    }

    @Override
    public Event20180320InviteEnergyRank getRankByUid(long uid) {
        return rankDao.getEnergyRankByUid(uid);
    }

    @Override
    public int getRank(long uid) {
        int rank = 0;
        List<Event20180320InviteEnergyRank> rankList = rankDao.getEnergyRankLimit50();
        if (!StringUtil.isNullOrEmpty(rankList)){
            int num = 1;
            for (Event20180320InviteEnergyRank energyRank : rankList){
                if (energyRank.getUid() == uid){
                    return num;
                }
                num++;
            }
        }

        return rank;
    }

    @Override
    public void sendInviteSuccessMsg(UserGeneral user,int energy) {

        InviteRelation inviteRelation = inviteService.getInviteRelationByUid(user.getUid());
        if (inviteRelation != null){

            UserBase invite = userDAO.getUserBase(inviteRelation.getInviteUid());

            String textString = "成功邀请朋友  " + user.getName() + " 加入，获得 "+ energy + " 能量值";
            MsgNotice msg=new MsgNotice(inviteRelation.getInviteUid(), MsgType.NOTICE_SYS.type, textString);
            msg.setJumpWeb(Event20180320InviteUtil.H5_URL + "?uid=" + invite.getUid() + "&loginKey=" + invite.getLoginKey());
            msg.setPushMsg(textString);
            msg.setTargetTitle("邀请好友获得能量活动");
            msg.setTargetName("邀请好友获得能量活动");
            msgService.sendMsg(msg);
        }

    }

    @Override
    public void handleCatchDollSuccess(long uid) {

        //被邀请方
        UserGeneral user = userService.getUserGeneral(uid);
        UserBase userBase = userDAO.getUserBase(uid);
        InviteRelation inviteRelation = inviteService.getInviteRelationByUid(uid);

        if (inviteRelation != null){
            //邀请方
            long inviteUid = inviteRelation.getInviteUid();
            UserGeneral invite_user = userService.getUserGeneral(inviteUid);
            UserBase invite_userBase = userDAO.getUserBase(inviteUid);

            String textString = "您邀请的朋友 "+user.getName()+" 抓到第1个娃娃啦，您获得了50能量值~~";
            MsgNotice msg=new MsgNotice(inviteUid, MsgType.NOTICE_SYS.type, textString);
            msg.setJumpWeb(Event20180320InviteUtil.H5_URL + "?uid=" + inviteUid + "&loginKey=" + invite_userBase.getLoginKey());
            msg.setPushMsg(textString);
            msg.setTargetTitle("邀请好友获得能量活动");
            msg.setTargetName("邀请好友获得能量活动");
            msgService.sendMsg(msg);


            //被邀请方
            String textString_me = "您抓到了第1个娃娃，邀请您到萌趣抓娃娃的好友 " + invite_user.getName() + " 获得了50能量值~~";
            MsgNotice msg_me=new MsgNotice(uid, MsgType.NOTICE_SYS.type, textString_me);
            msg_me.setJumpWeb(Event20180320InviteUtil.H5_URL + "?uid=" + userBase.getUid() + "&loginKey=" + userBase.getLoginKey());
            msg_me.setPushMsg(textString_me);
            msg_me.setTargetTitle("邀请好友获得能量活动");
            msg_me.setTargetName("邀请好友获得能量活动");
            msgService.sendMsg(msg_me);
        }

    }

    @Override
    public void sendMsgA_B_C(long uid,long inviteUid, int energy) {

        InviteRelation inviteRelation = inviteService.getInviteRelationByUid(inviteUid);
        if (inviteRelation != null){
            String bodyString = "好友 "+userService.getUserGeneral(inviteUid).getName()+" 邀请了 "+userService.getUserGeneral(uid).getName()+"，您获得" +energy+"能量值~~";
            MsgNotice msg = new MsgNotice(inviteRelation.getInviteUid(),
                    MsgType.NOTICE_SYS.type, bodyString);
            msg.setJumpWeb(Event20180320InviteUtil.H5_URL + "?uid=" + userService.getUserBase(inviteRelation.getInviteUid()).getUid() + "&loginKey=" + userService.getUserBase(inviteRelation.getInviteUid()).getLoginKey());
            msg.setPushMsg(bodyString);
            msg.setTargetTitle("邀请好友获得能量活动");
            msg.setTargetName("邀请好友获得能量活动");
            msgService.sendMsg(msg);
        }
    }

    @Override
    public void sendMsg20Min() {
        try{
            if (Event20180320InviteUtil.eventStatus() == 1){
                List<Event20180320InviteEnergyRank> rankList = rankDao.getEnergyRankLimit30();
                logger.info("sendMsg20Min   rankList=>" + JSON.toJSONString(rankList));
                if (!StringUtil.isNullOrEmpty(rankList)){
                    List<Long> uids = Lists.newArrayList();
                    for (Event20180320InviteEnergyRank energyRank: rankList){
                        uids.add(energyRank.getUid());
                    }
                    Map<Long, UserBase> userBaseMap = userService.getUserBaseMap(uids);
                    Map<Long,String> msgMap = Maps.newHashMap();
                    for (int i = 0;i < rankList.size();i++){
                        if (i == 0){
                            Event20180320InviteEnergyRank curr_energyRank = rankList.get(i);//当前
                            Event20180320InviteEnergyRank next_energyRank = rankList.get(i+1);//下一名
                            if (curr_energyRank != null && next_energyRank != null){
                                msgMap.put(curr_energyRank.getUid(),"小主在榜单暂列第1名,超越下一名 "+ (curr_energyRank.getTotalEnergy() - next_energyRank.getTotalEnergy()) +" 能量值");
                            }
                        }else {
                            if ( i != rankList.size() -1){
                                Event20180320InviteEnergyRank curr_energyRank = rankList.get(i);//当前
                                Event20180320InviteEnergyRank before_energyRank = rankList.get(i-1);//上一名
                                Event20180320InviteEnergyRank next_energyRank = rankList.get(i+1);//下一名
                                if (curr_energyRank != null && before_energyRank != null && next_energyRank != null){
                                    msgMap.put(curr_energyRank.getUid(),"小主在榜单暂列第" + (i+1) +"名," +
                                            "距离上一名 " + (before_energyRank.getTotalEnergy() - curr_energyRank.getTotalEnergy()) + " 能量值，" +
                                            "超越下一名 "+ (curr_energyRank.getTotalEnergy() - next_energyRank.getTotalEnergy()) +" 能量值");
                                }
                            }else {
                                //处理最后一名
                                if (i == rankList.size() -1 ){
                                    Event20180320InviteEnergyRank curr_energyRank = rankList.get(i);//当前
                                    Event20180320InviteEnergyRank before_energyRank = rankList.get(i-1);//上一名
                                    if (curr_energyRank != null && before_energyRank != null){
                                        msgMap.put(curr_energyRank.getUid(),"小主在榜单暂列第" + (i + 1) +"名" +
                                                "距离上一名" + (before_energyRank.getTotalEnergy() - curr_energyRank.getTotalEnergy()) + "能量值");

                                    }
                                }
                            }
                        }
                    }
                    if (!StringUtil.isNullOrEmpty(msgMap)){
                        logger.info("sendMsg20Min   msgMap=>" + JSON.toJSONString(msgMap));
                        Set<Map.Entry<Long, String>> set = msgMap.entrySet();
                        for (Map.Entry<Long, String> stringSet : set){

                            if (!StringUtil.isNullOrEmpty(userBaseMap)){
                                UserBase userBase = userBaseMap.get(stringSet.getKey());
                                if (userBase != null){
                                    String bodyString = stringSet.getValue();
                                    MsgNotice msg = new MsgNotice(stringSet.getKey(),
                                            MsgType.NOTICE_SYS.type, bodyString);
                                    msg.setJumpWeb(Event20180320InviteUtil.H5_URL + "?uid=" + userBase.getUid() + "&loginKey=" + userBase.getLoginKey());
                                    msg.setPushMsg(bodyString);
                                    msg.setTargetTitle("邀请好友获得能量活动");
                                    msg.setTargetName("邀请好友获得能量活动");
                                    msgService.sendMsg(msg);
                                }

                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }

    @Override
    public void eventEndMsg() {
        try{
            if (Event20180320InviteUtil.eventStatus() == 1){
                List<Event20180320InviteEnergyRank> rankList = rankDao.getEnergyRankLimit30();
                logger.info("eventEndMsg   rankList=>" + JSON.toJSONString(rankList));
                if (!StringUtil.isNullOrEmpty(rankList)){
                    List<Long> uids = Lists.newArrayList();
                    for (Event20180320InviteEnergyRank energyRank: rankList){
                        uids.add(energyRank.getUid());
                    }
                    Map<Long, UserBase> userBaseMap = userService.getUserBaseMap(uids);
                    int i = 1;
                    for (Event20180320InviteEnergyRank energyRank : rankList){
                        if (!StringUtil.isNullOrEmpty(userBaseMap)) {
                            UserBase userBase = userBaseMap.get(energyRank.getUid());
                            if (userBase != null) {
                                String bodyString = "恭喜小主获得榜单第"+ i +"名，奖品将在活动结束后7个工作日内发放到背包";
                                MsgNotice msg = new MsgNotice(energyRank.getUid(),
                                        MsgType.NOTICE_SYS.type, bodyString);
                                msg.setJumpWeb(Event20180320InviteUtil.H5_URL + "?uid=" + userBase.getUid() + "&loginKey=" + userBase.getLoginKey());
                                msg.setPushMsg(bodyString);
                                msg.setTargetTitle("邀请好友获得能量活动");
                                msg.setTargetName("邀请好友获得能量活动");
                                msgService.sendMsg(msg);
                                i++;
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }

    @Override
    public int init(long uid) {
        return usedTimesDao.init(uid);
    }

    @Override
    public int updateUsedTimes(long uid) {
        return usedTimesDao.update(uid);
    }

    @Override
    public Event20180320InviteUsedTimes getUsedTimeByUid(long uid) {
        return usedTimesDao.getByUid(uid);
    }
}
