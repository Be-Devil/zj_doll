package com.imlianai.zjdoll.app.modules.core.push.controller;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.push.service.PushCoinExService;
import com.imlianai.zjdoll.app.modules.core.push.vo.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Path;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author wurui
 * @create 2018-05-17 22:01
 **/
@Component("CmdPushCoinEx")
public class CmdPushCoinEx extends RootCmd {

    @Resource
    private PushCoinExService pushCoinExService;

    @ApiHandle
    @Path("api/CmdPushCoinEx/pushCoinAuth")
    public BaseRespVO pushCoinAuth(AuthReqVO vo) {
        if (!StringUtil.isNullOrEmpty(vo)) {
            return pushCoinExService.handleAuth(vo.getEmail(), vo.getPwd());
        }
        return new BaseRespVO(0, false, "非法操作");
    }

    @ApiHandle
    @Path("api/CmdPushCoinEx/pushCoinAuthPermission")
    public BaseRespVO pushCoinAuthPermission(AuthReqVO vo) {
        if (!StringUtil.isNullOrEmpty(vo)) {
            return pushCoinExService.handlePermission(vo.getEmail(), vo.getPwd());
        }
        return new BaseRespVO(0, false, "非法操作");
    }


    public static void main(String[] args) {

        int[] box = {1,2,3,4,5};



        int total = 50000;
        int value = 49999;
        boolean isOpen = false;

        int ave = total / box.length;
        int grad[] = new int[box.length];//存放梯度
        int diff = total;

        //第一个存放总值
        grad[0] = diff;

        //计算梯度
        for (int i = 1; i < grad.length; i++) {
            diff = diff - ave;
            if (diff < total) {
                grad[i] = diff;
            }
        }
        //冒泡排序...
//        for (int i = 0; i < grad.length - 1; i++) {
//            for (int j = 0; j < grad.length - 1 - i; j++) {
//                if (grad[j] > grad[j + 1]){
//                    int temp = grad[j];
//                    grad[j] = grad[j + 1];
//                    grad[j + 1] = temp;
//                }
//            }
//        }

        Arrays.sort(grad);

        System.out.println(JSON.toJSONString(grad));
        //int split[] = new int[6];

        if (value <= grad[0]){

            System.out.println("数值:" + value + ",落在梯度区间 [" + 0 + "," + grad[0] + "] 的位置,显示图片=>" + box[0]);

        }else if (value >= grad[0] && value < grad[1]){
            System.out.println("数值:" + value + ",落在梯度区间 [" + grad[0] + "," + grad[1] + "] 的位置,显示图片=>" + box[1]);

        }else if (value >= grad[1] && value < grad[2]){

            System.out.println("数值:" + value + ",落在梯度区间 [" + grad[1] + "," + grad[2] + "] 的位置,显示图片=>" + box[2]);

        }else if (value >= grad[2] && value < grad[3]){

            System.out.println("数值:" + value + ",落在梯度区间 [" + grad[2] + "," + grad[3] + "] 的位置,显示图片=>" + box[3]);

        }else if (value >= grad[3]){

            System.out.println("数值:" + value + ",落在梯度区间 [" + grad[3] + "," + grad[4] + "] 的位置,显示图片=>" + box[3]);

            if (value >= grad[box.length -1]){

                isOpen = true;
                System.out.println("宝箱可开=>" + box[box.length-1]);
            }

        }










    }


}
