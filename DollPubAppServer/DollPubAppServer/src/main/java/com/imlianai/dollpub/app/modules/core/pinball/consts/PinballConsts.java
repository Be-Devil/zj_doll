package com.imlianai.dollpub.app.modules.core.pinball.consts;

/**
 * @author wurui
 * @create 2018-07-13 22:25
 **/
public class PinballConsts {
    //开局
    public static final byte[] START = {(byte)0xfe,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0xff,(byte)0xff,(byte)0x0c,(byte)0xa2,(byte)0x01,(byte)0x20,(byte)0x01,(byte)0x08};

    //押分
    public static final byte[] BET = {(byte)0xfe,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0xff,(byte)0xff,(byte)0x0a,(byte)0xa4,(byte)0x01,(byte)0x4b};

    //下球
    public static final byte[] BALL = {(byte)0xfe,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0xff,(byte)0xff,(byte)0x09,(byte)0xa5,(byte)0x4a};

    //力度
    public static final byte[] FORCE = {(byte)0xfe,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0xff,(byte)0xff,(byte)0x0a,(byte)0xa6,(byte)0x01,(byte)0x4d};

    //发射
    public static final byte[] LAUNCH = {(byte)0xfe,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0xff,(byte)0xff,(byte)0x0b,(byte)0xa7,(byte)0xa4,(byte)0x01,(byte)0x2b};

    //接收返回的游戏结果
    public static final byte[] RESULT = {(byte)0xfe,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0xff,(byte)0xff,(byte)0x0f,(byte)0x33,(byte)0x01,(byte)0x01,(byte)0x01,(byte)0x01,(byte)0x01,(byte)0x01,(byte)0x48};

    //游戏结束
    public static final byte[] END = {(byte)0xfe,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0xff,(byte)0xff,(byte)0x0a,(byte)0xc1,(byte)0x01,(byte)0x04};
}
