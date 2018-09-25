package com.imlianai.zjdoll.app.modules.publics.utils;

import java.math.BigDecimal;

/**
 * 用于高精确处理常用的数学运算
 * @author cls
 *
 */
public class ArithmeticUtils {

	/**
     * 提供精确的加法运算
     * @param d1    
     * @param d2
     * @param scale 保留scale 位小数
     * @return 两个参数的和
     */
    public static double add(double d1, double d2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale必须大于等于0");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    
    /**
     * 提供精确的减法运算
     * @param d1
     * @param d2
     * @param scale 保留scale 位小数
     * @return 两个参数的差
     */
    public static double sub(double d1, double d2, int scale) {
    	if (scale < 0) {
            throw new IllegalArgumentException("scale必须大于等于0");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 提供精确的乘法运算
     * @param d1
     * @param d2
     * @param scale 保留scale 位小数
     * @return 两个参数的差
     */
    public static double multiply(double d1, double d2, int scale) {
    	if (scale < 0) {
            throw new IllegalArgumentException("scale必须大于等于0");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 提供精确的除法运算
     * @param d1
     * @param d2
     * @param scale 保留scale 位小数
     * @return 两个参数的差
     */
    public static double divide(double d1, double d2, int scale) {
    	if (scale < 0) {
            throw new IllegalArgumentException("scale必须大于等于0");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
