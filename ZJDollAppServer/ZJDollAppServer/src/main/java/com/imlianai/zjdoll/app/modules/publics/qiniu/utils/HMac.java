package com.imlianai.zjdoll.app.modules.publics.qiniu.utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public final class HMac {
    private static final String MAC_NAME = "HmacSHA1";

    public static byte[] HmacSHA1Encrypt(String dataStr, String secretKeyStr) throws Exception {
        SecretKey secretKeySpec = new SecretKeySpec(secretKeyStr.getBytes(), MAC_NAME);
        //生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        //用给定密钥初始化 Mac 对象
        mac.init(secretKeySpec);

        //完成 Mac 操作
        return mac.doFinal(dataStr.getBytes());
    }
}
