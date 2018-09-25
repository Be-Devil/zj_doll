package com.imlianai.dollpub.app.modules.core.coinfactory.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.imlianai.dollpub.app.modules.core.coinfactory.vo.VirtualFruitsAffirmRespVO;
import com.imlianai.dollpub.domain.coinfactory.virtual.fruits.PushCoinFruitsAllot;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.MD5_HexUtil;
import com.imlianai.rpc.support.utils.StringUtil;

import java.util.*;

/**
 * @author wurui
 * @create 2018-06-30 11:34
 **/
public class PushCoinVirtualUtil {

    private static BaseLogger logger = BaseLogger.getLogger(PushCoinVirtualUtil.class);

    private static String pubKey = "zj-pub-key";

    private static Map<Integer, String> authMaps = Maps.newConcurrentMap();
    private static Map<Integer, Integer> coinNum = Maps.newConcurrentMap();


    static {
        if (authMaps != null) {
            authMaps.put(1, "8c350e058edaceb760080826b2d666e7");
            authMaps.put(2, "fdbe28ddade4a7e7ead149a12557b2b6");
            authMaps.put(3, "07e763a19c4e7b8d139e6bbe21398ee0");
            authMaps.put(4, "fa2b85904c518c427b307b0359e14b5b");
            authMaps.put(5, "af297c53dc3e55438d162882fc7e8924");
            authMaps.put(6, "220a49f346aae0e4fdf68a1d6878f997");
            authMaps.put(7, "3e5428c4e006c72fbb1341b339ee29c9");
            authMaps.put(8, "2557131c04c39daaa9c2791519a36e87");
            authMaps.put(9, "303fb7e1b5671b60e08eee71a4e36c24");
            authMaps.put(10, "1547b3d86e95408774c568134032cf39");
        }
        if (coinNum != null) {
            coinNum.put(1, 80);
            coinNum.put(2, 81);
            coinNum.put(3, 82);
            coinNum.put(4, 83);
            coinNum.put(5, 84);
            coinNum.put(6, 85);
            coinNum.put(7, 86);
            coinNum.put(8, 87);
            coinNum.put(9, 88);
            coinNum.put(10, 90);
        }

    }


    /**
     * 获取认证权限
     *
     * @param key
     * @return
     */
    public static String getAuthMaps(int key) {
        if (!StringUtil.isNullOrEmpty(authMaps)) {
            return authMaps.get(key);
        }
        return null;
    }


    public static int getRandomType() {
        Random random = new Random();
        int randNum = random.nextInt(10) + 1;
        if (!StringUtil.isNullOrEmpty(authMaps) && !StringUtil.isNullOrEmpty(coinNum)) {
            if (authMaps.get(randNum) != null) {
                return randNum;
            }
        }
        return 0;
    }


    /**
     * 检验出币合法性验证算法
     *
     * @param type
     * @param p1
     * @param p2
     * @param md5_value
     * @return
     */
    public static Map<String, Integer> verifyOutCoinIsValid(int type, String p1, String p2, String md5_value,
                                                            int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11) {

        Map<String, Integer> valueMaps = Maps.newConcurrentMap();

        //获取加密串
        String sign = getAuthMaps(type);

        if (sign != null && !"".equals(sign)) {
            //认证方式 p1+p1=a
            logger.info("开始认证,加密类型=>" + type + ",加密串=>" + sign);
            if (p1 != null && p2 != null && md5_value != null) {
                String str = p1 + p2;
                try {
                    logger.info("未处理过的的数据长度=>" + str.length() + ",源数据=>" + str);
                    if (str.length() == 98) {
                        String data[] = str.split("@");
                        if (data.length == 3) {
                            logger.info("处理截取后的数据长度=>" + data.length);
                            String t = data[0];
                            String z = data[1];
                            String y = data[2];

                            if (t != null && z != null && y != null &&
                                    t.length() == 32 &&
                                    z.length() == 32 &&
                                    y.length() == 32) {

                                logger.info("参数认证无异常,开始匹配结果...");

                                int T = getVerifyValue(sign, t, 1, p6, p7, 0, 0, 0, 0);
                                int Z = getVerifyValue(sign, z, 2, 0, 0, p8, p9, 0, 0);
                                int Y = getVerifyValue(sign, y, 3, 0, 0, 0, 0, p10, p11);

                                if (MD5_HexUtil.md5Hex(Y + sign).equals(md5_value)) {

                                    logger.info("匹配成功: T=" + T + ",Z=" + Z + ",Y=" + Y);

                                    valueMaps.put("T", T);
                                    valueMaps.put("Z", Z);
                                    valueMaps.put("Y", Y);
                                    valueMaps.put("S", p4);
                                    valueMaps.put("C", p5);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.info("认证解析异常=>" + e.getMessage());
                }
            }
        }
        return valueMaps;
    }


    /**
     * 验证算法: t + x + s + c = tt + z + y + o
     *
     * @param authType
     * @param X        当前投币
     * @param Y        当前出币
     * @param Z        当前回收
     * @param T        当前推板
     * @param S        水果机出奖
     * @param C        推板差值
     * @param O        当前入币
     * @return
     */
    public static boolean VerifyValidity(int authType, int X, int Y, int Z, int T, int S, int C, int O) {
        if (!StringUtil.isNullOrEmpty(coinNum)) {
            //推板金币数量
            int pushPlateCoinNum = coinNum.get(authType);
            if (pushPlateCoinNum != 0) {
                // 验证公式 t + x + s + c = tt + z + y + o
//                if (pushPlateCoinNum + X + S + C == T + Z + Y + O) {
//                    return true;
//                }

                //可能出现投币数偏差1个点
                if ((pushPlateCoinNum + X + S + C) - (T + Z + Y + O) <= 1) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 获取验证值
     *
     * @param sign
     * @param param
     * @param index
     * @param p6    \
     * @param p7    \ 推板范围
     * @param p8    \
     * @param p9    \ 回收范围
     * @param p10   \
     * @param p11   \ 金币范围
     * @return
     */
    private static int getVerifyValue(String sign, String param, int index, int p6, int p7, int p8, int p9, int p10, int p11) {
        if (param != null && !param.equals("")) {
            if (index == 1) {
                if (p6 == 0) {
                    return 0;
                }
                if (p7 > 0) {
                    for (int i = p6; i < p7; i++) {
                        if (param.equals(MD5_HexUtil.md5Hex(i + sign))) {
                            return i;
                        }
                    }
                } else {
                    return 0;
                }
            } else if (index == 2) {
                if (p8 == 0) {
                    return 0;
                }
                if (p9 > 0) {
                    for (int i = p8; i < p9; i++) {
                        if (param.equals(MD5_HexUtil.md5Hex(i + sign))) {
                            return i;
                        }
                    }
                } else {
                    return 0;
                }
            } else if (index == 3) {
                if (p10 == 0) {
                    return 0;
                }
                if (p11 > 0) {
                    for (int i = p10; i < p11; i++) {
                        if (param.equals(MD5_HexUtil.md5Hex(i + sign))) {
                            return i;
                        }
                    }
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     * 获取加密后的确认返回结果
     * <p>
     * 认证算法：md5[(key + randomValue) + coin]
     *
     * @param allot
     * @param autoType
     * @return
     */
    public static VirtualFruitsAffirmRespVO getAffirmResp(PushCoinFruitsAllot allot, int autoType) {
        VirtualFruitsAffirmRespVO respVO = new VirtualFruitsAffirmRespVO();

        if (allot != null) {

            respVO.setMagnetism(allot.getMagnetism());

            int randomValue = new Random().nextInt(2000);

            respVO.setCoin(randomValue);//随机值
            respVO.setRate(MD5_HexUtil.md5Hex(allot.getRate() + ""));//倍率加密

            int coin = allot.getCoin(); //真实金币

            //金币范围
            if (coin != 0) {
                respVO.setFrom(coin - 5 <= 0 ? 0 : coin - 5);
                respVO.setTo(coin + 5);
            } else {
                respVO.setFrom(0);
                respVO.setTo(0);
            }


            //加密串切割
            String authStr = getAuthMaps(autoType);
            String md5_value = MD5_HexUtil.md5Hex((authStr + randomValue) + coin);
            respVO.setS1(md5_value.substring(0, 16));
            respVO.setS2(md5_value.substring(16, 32));

            respVO.setTemp1(coin);
            respVO.setTemp2(allot.getRate());
        }
        return respVO;
    }

    /**
     * 随机获取一个转盘倍率
     *
     * @return
     */
    public static int getRandomDialValue() {

        List<Integer> dialArrayList = Lists.newLinkedList();

//        dialArrayList.add(0);dialArrayList.add(0);dialArrayList.add(0);dialArrayList.add(0);dialArrayList.add(0);dialArrayList.add(0);
//
//        dialArrayList.add(1);dialArrayList.add(1);dialArrayList.add(1);
//
//        dialArrayList.add(3);dialArrayList.add(3);
//
//        dialArrayList.add(5);

        dialArrayList.add(0);
        dialArrayList.add(0);
        dialArrayList.add(0);
        dialArrayList.add(5);
        dialArrayList.add(5);
        dialArrayList.add(5);

        dialArrayList.add(5);
        dialArrayList.add(5);
        dialArrayList.add(5);

        dialArrayList.add(5);
        dialArrayList.add(5);

        dialArrayList.add(5);


        Collections.shuffle(dialArrayList);

        return dialArrayList.get(0);

    }


    public static void main(String[] args) {
        //生成10个密钥
//        for (int i = 0;i < 10;i++){
//            System.out.println(MD5_HexUtil.md5Hex(pubKey + UUID.randomUUID().toString().replace("-","")));
//        }

//        for (int i = 0;i < 1000;i++){
//            System.out.println(getRandomType());
//        }
//        String s = MD5_HexUtil.md5Hex("123456");
//        System.out.println(s);
//        System.out.println(s.length());


//        int T = getVerifyValue("220a49f346aae0e4fdf68a1d6878f997", "6ad6feddfb3cd0dad6dc6a4e95a020fa", 2, 0, 90, -1, 6);
//        System.out.println(T);

        //System.out.println(MD5_HexUtil.md5Hex(85 + "af297c53dc3e55438d162882fc7e8924"));

//        String md5_value = MD5_HexUtil.md5Hex(123456+"");
//        String s1 = md5_value.substring(0,16);
//        String s2 = md5_value.substring(16,32);
//
//        System.out.println(md5_value);
//
//        System.out.println(s1 +  s2);

    }

}
