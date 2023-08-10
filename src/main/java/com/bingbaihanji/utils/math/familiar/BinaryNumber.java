package com.bingbaihanji.utils.math.familiar;


public class BinaryNumber {
    /**
     * int 型数据转化为二进制
     *
     * @param num
     * @return String
     */
    public static String IntNumberBinary(Integer num) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = Integer.SIZE - 1; i >= 0; i--) {
            stringBuilder.append((num & (1 << i)) == 0 ? "0" : "1");
        }
        return stringBuilder.toString();
    }

    /**
     * long 型数据转化为二进制
     *
     * @param num
     * @return String
     */
    public static String LongNumberBinary(Long num) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = Long.SIZE - 1; i >= 0; i--) {
            stringBuilder.append((num & (1L << i)) == 0 ? "0" : "1");
        }
        return stringBuilder.toString();
    }
}