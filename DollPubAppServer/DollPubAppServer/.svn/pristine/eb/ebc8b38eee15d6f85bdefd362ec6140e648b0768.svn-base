package com.imlianai.dollpub.app.modules.core.pinball.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.modules.core.doll.bus.DollBusService;
import com.imlianai.dollpub.app.modules.core.pinball.consts.PinballConsts;
import com.imlianai.dollpub.app.modules.core.pinball.service.PinballService;
import com.imlianai.dollpub.app.modules.core.pinball.util.PinballByteArrayUtil;
import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.dollpub.machine.iface.IMachineConnectRemoteService;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 弹珠机服务实现
 * @author wurui
 * @create 2018-07-13 22:10
 **/
@Service
public class PinballServiceImpl implements PinballService {

    private BaseLogger logger = BaseLogger.getLogger(getClass());

    @Reference
    private IMachineConnectRemoteService iMachineConnectRemoteService;

    @Resource
    private DollBusService dollBusService;




    /**
     * 开局
     * @param busId 机器ID
     * @param uid 玩家ID
     * @param gameId 游戏局数
     * @param time_out 超时时间，单位秒。超过此时间自动结束游戏
     * @param bet 押分  取值范围为0-255
     * @return
     */
    @Override
    public BaseRespVO start(int busId,long uid,int gameId, int time_out, int bet) {
        DollBus dollBus = assertBusType(busId);
        if (dollBus != null){
            try {
                byte[] startCmd = PinballByteArrayUtil.startCmd(gameId,time_out,bet);
                if (startCmd != null){

                    List<String> hexs = PinballByteArrayUtil.bytesToHexs(startCmd);

                    logger.info("指令：" + JSON.toJSONString(hexs));

                    iMachineConnectRemoteService.handleDirective(busId,startCmd,"弹珠机开局");

                    BaseRespVO respVO = new BaseRespVO(200,true,"数据发送成功");
                    respVO.setData(hexs);
                    return respVO;

                }
            }catch (Exception error){
                logger.info(error.getMessage());
            }
        }
        return new BaseRespVO(0,false,"开局失败");

    }

    /**
     * 押分
     * @param busId
     * @param uid
     * @param bet
     * @return
     */
    @Override
    public BaseRespVO bet(int busId,long uid,int bet){
        DollBus dollBus = assertBusType(busId);
        if (dollBus != null){
            try {
                byte[] betCmd = PinballByteArrayUtil.betCmd(bet);
                if (betCmd != null){

                    List<String> hexs = PinballByteArrayUtil.bytesToHexs(betCmd);

                    logger.info("指令：" + JSON.toJSONString(hexs));

                    iMachineConnectRemoteService.handleDirective(busId,betCmd,"弹珠机押分");

                    BaseRespVO respVO = new BaseRespVO(200,true,"数据发送成功");
                    respVO.setData(hexs);
                    return respVO;

                }
            }catch (Exception error){
                logger.info(error.getMessage());
            }
        }
        return new BaseRespVO(0,false,"押分失败");

    }

    /**
     * 下球
     * @param busId
     * @param uid
     * @return
     */
    @Override
    public BaseRespVO ball(int busId, long uid){
        DollBus dollBus = assertBusType(busId);
        if (dollBus != null){
            try {
                byte[] ballCmd = PinballByteArrayUtil.ballCmd();

                List<String> hexs = PinballByteArrayUtil.bytesToHexs(ballCmd);

                logger.info("指令：" + JSON.toJSONString(hexs));

                iMachineConnectRemoteService.handleDirective(busId,ballCmd,"弹珠机下球");

                BaseRespVO respVO = new BaseRespVO(200,true,"数据发送成功");
                respVO.setData(hexs);
                return respVO;

            }catch (Exception error){
                logger.info(error.getMessage());
            }
        }
        return new BaseRespVO(0,false,"下球失败");

    }

    /**
     * 力度
     * @param busId
     * @param uid
     * @param power
     * @return
     */
    @Override
    public BaseRespVO force(int busId, long uid, int power){
        DollBus dollBus = assertBusType(busId);
        if (dollBus != null){
            try {
                byte[] forceCmd = PinballByteArrayUtil.forceCmd(power);

                if (forceCmd != null){

                    List<String> hexs = PinballByteArrayUtil.bytesToHexs(forceCmd);

                    logger.info("指令：" + JSON.toJSONString(hexs));

                    iMachineConnectRemoteService.handleDirective(busId,forceCmd,"弹珠机力度");

                    BaseRespVO respVO = new BaseRespVO(200,true,"数据发送成功");
                    respVO.setData(hexs);
                    return respVO;

                }

            }catch (Exception error){
                logger.info(error.getMessage());
            }
        }
        return new BaseRespVO(0,false,"设置力度失败");

    }

    /**
     * 发射
     * @param busId
     * @param uid
     * @param power
     * @param bet
     * @return
     */
    @Override
    public BaseRespVO launchCmd(int busId, long uid, int power, int bet){
        DollBus dollBus = assertBusType(busId);
        if (dollBus != null){
            try {
                byte[] launchCmd = PinballByteArrayUtil.launchCmd(power,bet);

                if (launchCmd != null){

                    List<String> hexs = PinballByteArrayUtil.bytesToHexs(launchCmd);

                    logger.info("指令：" + JSON.toJSONString(hexs));

                    iMachineConnectRemoteService.handleDirective(busId,launchCmd,"弹珠机发射");

                    BaseRespVO respVO = new BaseRespVO(200,true,"数据发送成功");
                    respVO.setData(hexs);
                    return respVO;

                }

            }catch (Exception error){
                logger.info(error.getMessage());
            }
        }
        return new BaseRespVO(0,false,"发射失败");

    }

    @Override
    public BaseRespVO set(int busId,long uid,int item, int low, int high) {
        DollBus dollBus = assertBusType(busId);
        if (dollBus != null){
            try {
                byte[] setParam = PinballByteArrayUtil.setParam(item,low,high);

                if (setParam != null){

                    List<String> hexs = PinballByteArrayUtil.bytesToHexs(setParam);

                    logger.info("指令：" + JSON.toJSONString(hexs));

                    iMachineConnectRemoteService.handleDirective(busId,setParam,"弹珠机设置");

                    BaseRespVO respVO = new BaseRespVO(200,true,"数据发送成功");
                    respVO.setData(hexs);
                    return respVO;

                }

            }catch (Exception error){
                logger.info(error.getMessage());
            }
        }
        return new BaseRespVO(0,false,"设置失败");
    }


    /**
     * 判断是否为弹珠机
     * @param busId
     * @return
     */
    private DollBus assertBusType(int busId){
        DollBus dollBus = dollBusService.getDollBus(busId);
        if (dollBus != null){
            if (dollBus.getType() == 3){
                return dollBus;
            }
        }
        return null;
    }


    @Override
    public void handleResult(DollBus dollBus, String deviceId, List<String> hexs) {
        logger.info("handleResult 结果：" + JSON.toJSONString(hexs));
        if (dollBus != null && dollBus.getType() == 3){
            if (!StringUtil.isNullOrEmpty(hexs)){

                switch (hexs.get(7)){
                    case "a2": //开局应答

                        int new_open_result = Integer.parseInt(hexs.get(8), 16);
                        int new_open_gameId = Integer.parseInt(hexs.get(9), 16);
                        switch (new_open_result){
                            case 0:
                                logger.info("开局成功");
                                break;
                            case 1:
                                logger.info("开局失败【线上已经开局】");
                                break;
                            case 2:
                                logger.info("开局失败，原因【错误(机器)或故障(服务器发下去的)】");
                                break;
                            case 3:
                                logger.info("开局失败，原因【线下已经开局】");
                                break;
                            case 4:
                                logger.info("开局失败，原因【线下需要开局】");
                                break;
                            default:
                                break;
                        }
                        logger.info("局数：" + new_open_gameId);
                        break;

                    case "33": //中奖应答

                        logger.info("中奖结果：" + JSON.toJSONString(hexs));

                        // 局数（该局的局数，与开局时下发的值相同）
                        // 备注：如果局数不对，返回的中奖结果值为全零
                        int gameId = Integer.parseInt(hexs.get(8), 16);

                        //结果是否产生	 1表示产生了  0表示未产生
                        int exerted = Integer.parseInt(hexs.get(9), 16);

                        //押分
                        int bet = Integer.parseInt(hexs.get(10), 16);

                        //倍数	当前的游戏倍数
                        int odd = Integer.parseInt(hexs.get(11), 16);


                        //（低字节）hexs.get(12)
                        //（高字节）hexs.get(13)

                        //得分 DEC（DEC(high) + DEC(low)）= 押分*倍数*100
                        int score = Integer.parseInt(hexs.get(13) + hexs.get(12), 16);

                        if (exerted == 1){
                            logger.info("产生结果：该轮局数=>" + gameId + ",押分=>" + bet + ",倍数=>" + odd + ",实际得分=>" + score);
                        }else {
                            logger.info("未产生结果");
                        }
                        break;

                    case "c1": //游戏结束应答

                        int game_over = Integer.parseInt(hexs.get(8), 16);
                        if (game_over == 0){
                            logger.info("游戏结束(线下)");
                        }else if (game_over == 1){
                            logger.info("游戏结束(线上)");
                        }
                    case "a0":
                        int item = Integer.parseInt(hexs.get(8), 16);
                        int value_low = Integer.parseInt(hexs.get(9), 16);
                        if (item == 0){
                            logger.info("设置难度成功 busId=>" + dollBus.getBusId() + ",当前难度等级=>" + value_low);
                        }
                        if (item == 1){
                            logger.info("设置彩票成功 busId=>" + dollBus.getBusId() + ",当前彩票值=>" + value_low);
                        }
                        break;
                    default:
                        break;
                }

            }
        }
    }


}
