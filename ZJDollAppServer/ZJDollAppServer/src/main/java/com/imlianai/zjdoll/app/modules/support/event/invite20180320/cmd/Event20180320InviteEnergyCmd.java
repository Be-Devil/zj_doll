package com.imlianai.zjdoll.app.modules.support.event.invite20180320.cmd;

import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.domain.Event20180320InviteEnergyRank;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.service.Event20180320InviteEnergyService;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.vo.Invite20180320RankRespVO;
import com.imlianai.zjdoll.app.modules.support.event.invite20180320.vo.Invite20180320RecordRespVO;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

/**
 * @author wurui
 * @create 2018-03-20 20:42
 **/
@Component("inviteEnergy")
public class Event20180320InviteEnergyCmd extends RootCmd {

    @Resource
    private Event20180320InviteEnergyService service;

    @Resource
    private UserService userService;

    @Resource
    private JdbcHandler jdbcHandler;

    @ApiHandle
    @Path("api/inviteEnergy/getRankList")
    @ApiOperation(value = "获取榜单排名列表", notes = "获取榜单排名列表", httpMethod = "POST", response = Invite20180320RankRespVO.class)
    @LogHead("获取榜单排名列表")
    public BaseRespVO getRankList(BaseReqVO reqVO) {
        try {
            Invite20180320RankRespVO vo = new Invite20180320RankRespVO(userService.getUserGeneral(reqVO.getUid()));
            Event20180320InviteEnergyRank energyRank = service.getOneByUid(vo.getUid());
            vo.setRank(service.getRank(vo.getUid()));
            if (energyRank != null) {
                vo.setTotalEnergy(energyRank.getTotalEnergy());
            }
            vo.setRankList(service.getRankList());
            vo.setResult(100);
            return vo;
        } catch (Exception e) {
            return new BaseRespVO(0, false, "数据获取失败!");
        }
    }

    @ApiHandle
    @Path("api/inviteEnergy/getRecordList")
    @ApiOperation(value = "获取能量获取记录列表", notes = "获取能量获取记录列表", httpMethod = "POST", response = Invite20180320RecordRespVO.class)
    @LogHead("获取能量获取记录列表")
    public BaseRespVO getRecordList(BaseReqVO reqVO) {
        try {
            Invite20180320RecordRespVO vo = new Invite20180320RecordRespVO(userService.getUserGeneral(reqVO.getUid()), service.getRecordByInviteUidTop(reqVO.getUid()));
            Event20180320InviteEnergyRank energyRank = service.getOneByUid(vo.getUid());
            //排名
            vo.setRank(service.getRank(vo.getUid()));
            //总能量
            if (energyRank != null) {
                vo.setTotalEnergy(energyRank.getTotalEnergy());
            }
            vo.setResult(100);
            return vo;
        } catch (Exception e) {
            return new BaseRespVO(0, false, "数据获取失败!");
        }
    }
//    @ApiHandle
//    @Path("api/inviteEnergy/msg20")
//    public void msg20(BaseReqVO reqVO) {
//
//        service.sendMsg20Min();
//    }
//
//    @ApiHandle
//    @Path("api/inviteEnergy/msgEnd")
//    public void msgEnd(BaseReqVO reqVO) {
//
//        service.eventEndMsg();
//    }

//    @ApiHandle
//    @Path("api/inviteEnergy/msgEnd")
//    public BaseRespVO msgEnd(BaseReqVO reqVO) {
//
//        List<Event20180320InviteEnergyRank> rankList = jdbcHandler.queryBySql(Event20180320InviteEnergyRank.class,"SELECT * FROM event_20180220_invite_energy_rank ORDER BY totalEnergy DESC,createTime ASC");
//        if (!StringUtil.isNullOrEmpty(rankList)){
//            for (Event20180320InviteEnergyRank rank : rankList){
//                int count = jdbcHandler.queryCount("SELECT COUNT(1) FROM event_20180320_invite_energy_record WHERE inviteUidTop=? AND remark LIKE '成功%'",rank.getUid());
//                if (count != 0){
//                    int flag = jdbcHandler.executeSql("INSERT INTO event_20180320_invite_usedTimes(uid,usedTimes,createTime) VALUES(?,?,NOW()) ON DUPLICATE KEY UPDATE usedTimes = ?,updateTime=NOW()",rank.getUid(),count,count);
//                    if (flag > 0){
//                        logger.info("插入成功 : uid=>" + rank.getUid() + ",count=>" + count);
//                    }
//                }
//            }
//
//        }
//
//        return new BaseRespVO(100,true,"success");
//   }


//    @ApiHandle
//    @Path("api/inviteEnergy/plugin")
//    public BaseRespVO plugin(Integer uid,Integer energy) {
//        UserBase userBase = userService.getUserBase(vo.getUid());
//        if (userBase != null) {
//            if (vo.getEnergy() < 0) {
//                return new BaseRespVO(0, false, "能量值必须大于0~~");
//            }
//            int flag = jdbcHandler.executeSql("INSERT INTO event_20180220_invite_energy_rank(uid,totalEnergy,remark) VALUES(?,?,'运维记录') ON DUPLICATE KEY UPDATE totalEnergy = totalEnergy+?,updateTime=now(),remark=?", vo.getUid(), vo.getEnergy(), vo.getEnergy(),vo.getRemark());
//            if (flag > 0) {
//                return new BaseRespVO(100, true, "更新榜单成功");
//            }
//        }
//        return new BaseRespVO(0, false, "当前用户不存在=>" + uid);
//    }

    @Override
    public String doBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            String uid = request.getParameter("uid");
            String energy = request.getParameter("energy");
            String key = request.getParameter("key");
            if (null != key && "MQZWW-PLUG-IN-ENERGY".equals(key)){
                if ( null!= uid && null != energy){
                    long uid_long = Long.parseLong(uid);
                    int energy_int = Integer.parseInt(energy);
                    logger.info("uid=>" + uid_long + ",energy=>" + energy_int);
                    UserBase userBase = userService.getUserBase(uid_long);
                    if (userBase != null){
                        int flag = jdbcHandler.executeSql("INSERT INTO event_20180220_invite_energy_rank(uid,totalEnergy,createTime,remark) VALUES(?,?,now(),'运维记录') ON DUPLICATE KEY UPDATE totalEnergy = totalEnergy+?,updateTime=now(),remark=?", uid_long, energy_int, energy_int,"运维记录");
                        if (flag > 0){
                            return responseJson(response, "用户=>"+ uid_long + ",能量+" + energy_int, "响应请求");
                        }
                    } else{
                        return responseJson(response, "数据操作失败，用户不存在！", "响应请求");
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return responseJson(response, e.getMessage(), "响应请求");
        }
        return responseJson(response, "非法操作！", "响应请求");
    }
}
