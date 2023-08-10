package com.bingbaihanji.utils.code;

public class dataType {
    /**
     * @Title: getType
     * @Description: Java获取变量的数据类型
     * @param: obj
     * @param: 变量
     * @return: String 返回类型
     */
    public static String getType(Object obj) {
        String str = obj.getClass().toString().replace(".", " ");
        String[] astr = str.split(" ");
        return astr[astr.length - 1];
    }

}
