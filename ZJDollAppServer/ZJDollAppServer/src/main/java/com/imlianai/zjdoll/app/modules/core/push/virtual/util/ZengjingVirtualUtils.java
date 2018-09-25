package com.imlianai.zjdoll.app.modules.core.push.virtual.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.MD5_HexUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.core.doll.utils.DollUtil;
import com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.DollMachSignUtil;
import com.imlianai.zjdoll.app.modules.core.push.virtual.vo.*;


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author wurui
 * @create 2018-07-04 11:31
 **/
public class ZengjingVirtualUtils {

    static String appid  ;
    static String appkey ;

    //测试环境
    //static String appid = "030879";
    //static String appkey = "jDMGGGeTMdzyWLeI";

    private static String hostUrl = "http://www.realgamecloud.com";
    private static String hostUrl_t = "http://119.29.208.205:6638/DollPubAppServer";

    static{
        if(AppUtils.isTestEnv()){
            //测试环境 很帅很酷很屌很牛X
            appid = "030879";
            appkey = "jDMGGGeTMdzyWLeI";
        }else{
            //正式环境 很帅很酷很屌很牛X
            appid = "370936";
            appkey = "K9ZLwVj172pXYzX4";
        }
    }



    protected static final BaseLogger logger = BaseLogger.getLogger(DollUtil.class);

    protected static final HashSet<String> ADDITION_KEYS = new HashSet<String>(
            Arrays.asList(new String[] { "sign", "app", "act" }));



    private static String getHost(){
        if(AppUtils.isTestEnv()){
            return hostUrl_t;
        }else{
            return hostUrl;
        }
    }

    private static String getSign(Map<String, Object> paraMap) {
        String signText = DollMachSignUtil.createLinkString(DollMachSignUtil.paraFilter(paraMap));
        String firstSign = MD5_HexUtil.md5Hex(signText);
        return MD5_HexUtil.md5Hex(firstSign + appkey);
    }


    /**
     * 进入房间
     * @param uid
     * @param busId
     * @return
     */
    public static BaseRespVO entryVirtualCoinPushRoom(long uid, int busId) {
        BaseRespVO vo = new BaseRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("uid", uid);
        paraMap.put("busId", busId);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/pushCoinVirtual/entryRoom";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,BaseRespVO.class);
            return vo;
        }
        return vo;
    }

    /**
     * 进入房间
     * @param uid
     * @param busId
     * @return
     */
    public static BaseRespVO laveVirtualCoinPushRoom(long uid, int busId) {
        BaseRespVO vo = new BaseRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("uid", uid);
        paraMap.put("busId", busId);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/pushCoinVirtual/leave";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,BaseRespVO.class);
            return vo;
        }
        return vo;
    }


    /**
     * 上机
     * @param uid
     * @param busId
     * @return
     */
    public static ApplyVirtualRespVO apply(long uid, int busId, int weight) {
        ApplyVirtualRespVO vo = new ApplyVirtualRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("uid", uid);
        paraMap.put("busId", busId);
        paraMap.put("weight", weight);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/pushCoinVirtual/apply";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,ApplyVirtualRespVO.class);
            return vo;
        }
        return vo;
    }

    /**
     * 投币
     * @param uid
     * @param busId
     * @return
     */
    public static OperateVirtualRespVO push(long uid, int busId,int weight) {
        OperateVirtualRespVO vo = new OperateVirtualRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("uid", uid);
        paraMap.put("busId", busId);
        paraMap.put("weight", weight);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/pushCoinVirtual/push";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,OperateVirtualRespVO.class);
            return vo;
        }
        return vo;
    }

    /**
     * 摆动
     * @param uid
     * @param busId
     * @return
     */
    public static BaseRespVO operate(long uid, int busId) {
        BaseRespVO vo = new BaseRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("uid", uid);
        paraMap.put("busId", busId);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/pushCoinVirtual/operate";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,BaseRespVO.class);
            return vo;
        }
        return vo;
    }

    /**
     * 主动下机
     * @param uid
     * @param busId
     * @return
     */
    public static BaseRespVO finish(long uid, int busId) {
        BaseRespVO vo = new BaseRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("uid", uid);
        paraMap.put("busId", busId);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/pushCoinVirtual/finish";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,BaseRespVO.class);
            return vo;
        }
        return vo;
    }


    /**
     * 查询水果机中奖
     * @param uid
     * @param busId
     * @return
     */
    public static VirtualFruitsQueryRespVO query(long uid, long optId, int busId) {
        VirtualFruitsQueryRespVO vo = new VirtualFruitsQueryRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("uid", uid);
        paraMap.put("busId", busId);
        paraMap.put("optId", optId);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/fruit/query";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,VirtualFruitsQueryRespVO.class);
            return vo;
        }
        return vo;
    }


    /**
     * 转盘结果请求
     * @param uid
     * @param optId
     * @param busId
     * @param allotId
     * @param result
     * @return
     */
    public static VirtualFruitsAffirmRespVO affirm(long uid, long optId, int busId,long allotId,int result) {
        VirtualFruitsAffirmRespVO vo = new VirtualFruitsAffirmRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("uid", uid);
        paraMap.put("busId", busId);
        paraMap.put("optId", optId);
        paraMap.put("allotId", allotId);
        paraMap.put("result", result);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/fruit/affirm";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,VirtualFruitsAffirmRespVO.class);
            return vo;
        }
        return vo;
    }


    /**
     * 验证中奖结果有效性
     * @param allotId
     * @param result
     * @return
     */
    public static BaseRespVO verifyResult(long allotId,int busId,long optId,int result) {
        BaseRespVO vo = new BaseRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("allotId", allotId);
        paraMap.put("result", result);
        paraMap.put("busId", busId);
        paraMap.put("optId", optId);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/fruit/verify";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,BaseRespVO.class);
            return vo;
        }
        return vo;
    }



    /**
     * 刷新游戏时间
     * @param uid
     * @param optId
     * @param busId
     * @return
     */
    public static BaseRespVO refresh(long uid, long optId, int busId) {
        BaseRespVO vo = new BaseRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("uid", uid);
        paraMap.put("busId", busId);
        paraMap.put("optId", optId);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/pushCoinVirtual/refresh";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,BaseRespVO.class);
            return vo;
        }
        return vo;
    }

    public static BaseRespVO getStatus(long uid, int busId) {
        BaseRespVO vo = new BaseRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("uid", uid);
        paraMap.put("busId", busId);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/pushCoinVirtual/getStatus";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,BaseRespVO.class);
            return vo;
        }
        return vo;
    }


    /**
     * 拉霸机请求
     * @param uid
     * @param busId
     * @param weight
     * @param coin
     * @return
     */
    public static VirtualFruitsSlotsRespVO slots(long uid, long optId,int busId, int weight,int coin) {
        VirtualFruitsSlotsRespVO vo = new VirtualFruitsSlotsRespVO();
        String tsString = System.currentTimeMillis() + "";
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("appId", appid);
        paraMap.put("ts", tsString);
        paraMap.put("uid", uid);
        paraMap.put("busId", busId);
        paraMap.put("weight", weight);
        paraMap.put("coin", coin);
        paraMap.put("optId", optId);
        paraMap.put("sign", getSign(paraMap));
        String url = getHost() + "/api/fruit/slots";
        HttpEntity he = HttpUtil.Post(url, JSON.toJSONString(paraMap));
        if (he != null){
            String resString = he.getHtml();
            vo = JSONObject.parseObject(resString,VirtualFruitsSlotsRespVO.class);
            return vo;
        }
        return vo;
    }

}
