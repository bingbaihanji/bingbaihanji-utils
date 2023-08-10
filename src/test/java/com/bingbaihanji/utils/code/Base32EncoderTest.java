package com.bingbaihanji.utils.code;

/**
 * @author 冰白寒祭
 * @date 2023-06-07 12:23:06
 * @description 这是类描述
 */
public class Base32EncoderTest {
    public static void main(String[] args) {
        String str = "123456789abcdefghijklmnopqrstuvwxyz";
        String encode = Base32Util.encode(str.getBytes());
        System.out.println(encode);
        String de = "GEZDGNBVGY3TQOLBMJRWIZLGM5UGS2TLNRWW433QOFZHG5DVOZ3XQ6L2";
        byte[] decode = Base32Util.decode(de);
        System.out.println(new String(decode));

    }
}
