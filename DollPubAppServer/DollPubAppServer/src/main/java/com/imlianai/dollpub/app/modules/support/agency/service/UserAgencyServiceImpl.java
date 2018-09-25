package com.imlianai.dollpub.app.modules.support.agency.service;

import com.google.common.collect.Lists;
import com.imlianai.dollpub.app.modules.core.trade.service.TradeChargeService;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.support.agency.dao.UserAgencyDao;
import com.imlianai.dollpub.app.modules.support.agency.dto.UserAgencyDTO;
import com.imlianai.dollpub.app.modules.support.agency.dto.UserAgencyListDTO;
import com.imlianai.dollpub.app.modules.support.agency.vo.UserAgencyReqVO;
import com.imlianai.dollpub.app.modules.support.agency.vo.UserAgencyRespVO;
import com.imlianai.dollpub.app.modules.support.invite.dao.InviteDAO;
import com.imlianai.dollpub.domain.agency.UserAgency;
import com.imlianai.dollpub.domain.invite.InviteRelation;
import com.imlianai.dollpub.domain.trade.TradeCharge;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.user.UserGeneral;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.utils.MD5_HexUtil;
import com.imlianai.rpc.support.utils.StringUtil;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author wurui
 * @create 2018-08-14 23:00
 **/
@Service
public class UserAgencyServiceImpl implements UserAgencyService {

    private BaseLogger logger = BaseLogger.getLogger(getClass());

    @Resource
    private UserAgencyDao userAgencyDao;

    @Resource
    private UserService userService;

    @Resource
    private TradeChargeService tradeChargeService;

    @Resource
    private InviteDAO inviteDAO;

    @Override
    public BaseRespVO addRecord(UserAgencyReqVO reqVO) {
        UserBase userBase = userService.getUserBase(reqVO.getUid());
        if (userBase != null) {

            UserGeneral general = userService.getUserGeneral(userBase.getUid());
            if (general != null) {


                UserAgency userAgency = userAgencyDao.getUserAgency(general.getUid());
                if (userAgency == null) {

                    Long phone = reqVO.getPhone();
                    try {
                        if (phone != 0 && phone.toString().length() == 11) {
                            int flag = userAgencyDao.addAgency(new UserAgency(userBase.getUid(), general.getName(), phone));
                            if (flag > 0) {
                                return new BaseRespVO(200, true, "申请代理成功,请等待客服小妹审核~~");
                            }
                        } else {
                            return new BaseRespVO(-12, false, "请输入正确的手机号码");
                        }
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }
                } else {
                    if (userAgency.getStatus() == 0) {
                        return new BaseRespVO(-18, false, "正在审核中哦,再等等吧。");
                    }
                    return new BaseRespVO(-17, false, "当前已成为代理,请勿重复申请~");
                }
            } else {
                return new BaseRespVO(-16, false, "用户不存在");
            }
        } else {
            return new BaseRespVO(-15, false, "用户不存在");
        }

        return new BaseRespVO(-200, false, "提交申请失败,请联系客服小妹~");
    }

    @Override
    public BaseRespVO query(UserAgencyReqVO reqVO) {
        if (!StringUtil.isNullOrEmpty(reqVO)) {
            UserAgency userAgency = userAgencyDao.getUserAgency(reqVO.getUid());
            if (userAgency != null) {

                if (userAgency.getStatus() == 0) {
                    return new BaseRespVO(-18, false, "正在审核中哦,再等等吧。");
                }


                if (reqVO.getStart().equals("")){
                    return new BaseRespVO(0,false,"请选择一个开始日期");
                }

                if (reqVO.getEnd().equals("")){
                    return new BaseRespVO(0,false,"请选择一个结束日期");
                }

                reqVO.setStart(reqVO.getStart() + " 00:00:00");
                reqVO.setEnd(reqVO.getEnd() + " 23:59:59");

                UserAgencyRespVO respVO = new UserAgencyRespVO();

                respVO.setResult(200);
                respVO.setState(true);

                List<InviteRelation> inviteRelationList = inviteDAO.getInviteRelationList(userAgency.getUid());
                List<UserAgencyDTO> list = Lists.newArrayList();
                List<Long> uIds = Lists.newArrayList();

                if (!StringUtil.isNullOrEmpty(inviteRelationList)) {

                    for (InviteRelation inviteRelation : inviteRelationList) {
                        uIds.add(inviteRelation.getUid());
                    }

                    Map<Long, UserGeneral> userGeneralMap = userService.getUserGeneralMap(uIds);
                    if (!StringUtil.isNullOrEmpty(userGeneralMap)) {
                        List<TradeCharge> tradeChargeList = tradeChargeService.getListByUidsAndTime(uIds, reqVO.getStart(), reqVO.getEnd());
                        if (!StringUtil.isNullOrEmpty(tradeChargeList)) {
                            for (TradeCharge tradeCharge : tradeChargeList) {
                                UserGeneral userGeneral = userGeneralMap.get(tradeCharge.getUid());
                                if (userGeneral != null) {
                                    list.add(UserAgencyDTO.adapter(userGeneral, tradeCharge));
                                }
                            }
                        }
                    }

                    //邀请总人数
                    respVO.setTotalInvite(inviteRelationList.size());
                    respVO.setList(list);

                    if (list.size() != 0){
                        for (UserAgencyDTO userAgencyDTO: list){
                            respVO.setTotalCharge(userAgencyDTO.getCharge() + respVO.getTotalCharge());
                        }
                    }

                }else {

                    respVO.setTotalInvite(0);
                    respVO.setList(list);
                    respVO.setMsg("暂无邀请成员,快去邀请得返利吧~");

                }

                return respVO;

            } else {
                return new BaseRespVO(-13, false, "你当前还不是代理哦,快去申请一个吧~");
            }
        }
        return new BaseRespVO(-14, false, "参数不能为空");
    }

    @Override
    public UserAgency getUserAgency(long uid) {
        return userAgencyDao.getUserAgency(uid);
    }

    @Override
    public BaseRespVO login(UserAgencyReqVO reqVO) {
        if (!StringUtil.isNullOrEmpty(reqVO)) {
            UserAgency userAgency = userAgencyDao.getUserAgency(reqVO.getUid());
            if (userAgency != null) {

                if (userAgency.getStatus() == 0) {
                    return new BaseRespVO(-18, false, "正在审核中哦,再等等吧。");
                }

                if (null != reqVO.getPwd() && !"".equals(reqVO.getPwd())){
                    try{
                        if (userAgency.getPwd().equals(MD5_HexUtil.md5Hex(reqVO.getPwd()+userAgency.getSalt()))){
                            return new BaseRespVO(200,true,"登录成功");
                        }
                    }catch (Exception e){
                        logger.info(e.getMessage());
                    }
                    return new BaseRespVO(-35, false, "账号或密码错误~");
                }else {
                    return new BaseRespVO(-30, false, "账号或密码不能不空~");
                }
            } else {
                return new BaseRespVO(-13, false, "你当前还不是代理哦,快去申请一个吧~");
            }
        }
        return new BaseRespVO(-14, false, "参数不能为空");
    }

    @Override
    public BaseRespVO register(UserAgencyReqVO reqVO) {
        UserAgency userAgency = userAgencyDao.getUserAgency(reqVO.getUid());
        if (userAgency != null){

            if (userAgency.getStatus() == 1){
                return new BaseRespVO(-70,false,"当前用户已是代理");
            }

            if (userAgency.getStatus() == 2){
                return new BaseRespVO(-71,false,"当前用户被取消代理");
            }

            if (userAgency.getStatus() == 0){

                if (null != reqVO.getPwd() && !"".equals(reqVO.getPwd())){

                    String salt = UUID.randomUUID().toString().replace("-","");
                    String pwd = MD5_HexUtil.md5Hex(reqVO.getPwd() + salt);

                    int flag = userAgencyDao.insertPwd(userAgency.getUid(),salt,pwd);

                    if (flag > 0){
                        return new BaseRespVO(200,true,"注册成功");
                    }

                    return new BaseRespVO(-200,false,"注册失败");

                }else {
                    return new BaseRespVO(-30, false, "账号或密码不能不空~");
                }
            }

        }

        return new BaseRespVO(-13, false, "你当前还不是代理哦,快去申请一个吧~");
    }

    @Override
    public BaseRespVO getAgencyList(UserAgencyReqVO reqVO) {


        BaseRespVO respVO = new BaseRespVO(200,true,"数据请求成功");

        List<UserAgencyListDTO> userAgencyListDTOS = Lists.newArrayList();

        List<UserAgency> userAgencyList = userAgencyDao.getAgencyList();
        if (!StringUtil.isNullOrEmpty(userAgencyList)){

            if (reqVO.getStart().equals("")){
                return new BaseRespVO(0,false,"请选择一个开始日期");
            }

            if (reqVO.getEnd().equals("")){
                return new BaseRespVO(0,false,"请选择一个结束日期");
            }

            reqVO.setStart(reqVO.getStart() + " 00:00:00");
            reqVO.setEnd(reqVO.getEnd() + " 23:59:59");

            for (UserAgency userAgency : userAgencyList){

                List<InviteRelation> inviteRelationList = inviteDAO.getInviteRelationList(userAgency.getUid());
                List<UserAgencyDTO> list = Lists.newArrayList();
                List<Long> uIds = Lists.newArrayList();

                UserAgencyListDTO userAgencyListDTO = new UserAgencyListDTO(userAgency);

                if (!StringUtil.isNullOrEmpty(inviteRelationList)) {

                    for (InviteRelation inviteRelation : inviteRelationList) {
                        uIds.add(inviteRelation.getUid());
                    }

                    Map<Long, UserGeneral> userGeneralMap = userService.getUserGeneralMap(uIds);
                    if (!StringUtil.isNullOrEmpty(userGeneralMap)) {
                        List<TradeCharge> tradeChargeList = tradeChargeService.getListByUidsAndTime(uIds, reqVO.getStart(), reqVO.getEnd());
                        if (!StringUtil.isNullOrEmpty(tradeChargeList)) {
                            for (TradeCharge tradeCharge : tradeChargeList) {
                                UserGeneral userGeneral = userGeneralMap.get(tradeCharge.getUid());
                                if (userGeneral != null) {
                                    list.add(UserAgencyDTO.adapter(userGeneral, tradeCharge));
                                }
                            }
                        }
                    }

                    userAgencyListDTO.setTotalInvite(inviteRelationList.size());

                    if (list.size() != 0){
                        for (UserAgencyDTO userAgencyDTO: list){
                            userAgencyListDTO.setTotalCharge(userAgencyDTO.getCharge() + userAgencyListDTO.getTotalCharge());
                        }
                    }

                }

                if (userAgency.getStatus() == 1){
                    userAgencyListDTO.setList(list);
                }

                userAgencyListDTOS.add(userAgencyListDTO);
            }
        }
        respVO.setData(userAgencyListDTOS);
        return respVO;
    }

    public static void main(String[] args) {
        String salt = UUID.randomUUID().toString().replace("-","");
        System.out.println(salt);
        System.out.println(MD5_HexUtil.md5Hex("123456" +salt ));
    }

}
