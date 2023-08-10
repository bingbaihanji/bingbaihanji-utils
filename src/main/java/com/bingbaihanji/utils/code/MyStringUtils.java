package com.bingbaihanji.utils.code;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @author 冰白寒祭
 * @date 2022-11-08 07:19:52
 * @description 这是类描述
 */
public class MyStringUtils {

    /**
     * 非空判断
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.equals("null") || str.length() == 0 || str.trim().length() == 0;
    }

    /**
     * @Title: isSpace
     * @Description: 判断字符串是否只包含空格
     * @param: 一个在字符串
     * @return: boolean 若只包含空格，则返回true
     */
    public static boolean isSpace(String str) {
        boolean bool = true;
        if (str.length() > 0 && str != null) {
            for (int i = 0; i < str.length(); i++) {
                if (' ' != str.charAt(i)) {
                    bool = false;
                    break;
                }
            }
            return bool;
        } else {
            return !bool;
        }
    }

    /**
     * @Title: isSpace
     * @Description: 判断字符串是否只包含数字
     * @param: 一个在字符串
     * @return: boolean 若只包含数字，则返回true
     */
    public static boolean isNum(String str) {
        boolean bool = true;
        if (str.length() > 0 && str != null) {
            for (int i = 0; i < str.length(); i++) {
                if (!('0' <= str.charAt(i) && str.charAt(i) <= '9')) {
                    bool = false;
                    break;
                }
            }
            return bool;
        } else {
            return false;
        }
    }

    /**
     * @Title: isSpace
     * @Description: 判断字符串是否只包含字母
     * @param: 一个在字符串
     * @return: boolean 若只包含字母，则返回true
     */
    public static boolean isAlpha(String str) {
        boolean bool = true;
        if (str.length() > 0 && str != null) {
            for (int i = 0; i < str.length(); i++) {
                if (!(('A' <= str.charAt(i) && str.charAt(i) <= 'Z')
                        || ('a' <= str.charAt(i) && str.charAt(i) <= 'z'))) {
                    bool = false;
                    break;
                }
            }
            return bool;
        } else {
            return false;
        }
    }

    /**
     * @Title: allSubString
     * @Description: 获取字符串所有子串
     * @param: str 字符串
     * @return: String[] 返回类型
     */
    static String[] allSubString(String str) {
        StringBuffer strb = new StringBuffer(str);
        String[] strA = new String[getLen(strb.length())];
        int i = 0;
        for (int j = 0; j < strb.length(); j++) {
            for (int k = j + 1; k <= strb.length(); k++) {
                strA[i++] = strb.substring(j, k);
            }
        }

        return strA;
    }

    /**
     * @Title: getLen
     * @Description: 获取字符串子串数组的长度
     * @param: @param i
     * @param: 字符串长度
     * @return: int
     */
    static int getLen(int i) {
        if (1 == i) {
            return 1;
        } else {
            return i + getLen(i - 1);
        }
    }


    /**
     * @Title: getRandomString
     * @Description: 产生指定长度随机字符串
     * @param: int
     * @param: 长度
     * @return: String 返回类型
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * @Title: getRandomString
     * @Description: 可以指定字符串的某个位置是什么范围的值
     * @param: length 需要随机字符串的的长度
     * @param: number 所需范围 0:包括数字和字母,1:大写字母,2:小写字母,3:数字
     * @return: String 所需字符串
     */
    public static String getRandomString(int length, int number) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = 0;
            switch (number) {
                case 0:
                    num = random.nextInt(str.length());
                    sb.append(str.charAt(num));
                    break;
                case 1:// 大写
                    // result = Math.round(Math.random() * 25 + 65);
                    num = (int) Math.floor(Math.random() * (51 - 26 + 1) + 26);
                    // sb.append(String.valueOf((char) result));
                    sb.append(str.charAt(num));
                    break;
                case 2:
                    num = (int) Math.floor(Math.random() * (25 - 0 + 1) + 0);
                    sb.append(str.charAt(num));
                    break;
                case 3:
                    num = (int) Math.floor(Math.random() * (61 - 52 + 1) + 52);
                    sb.append(str.charAt(num));
                    break;
            }

        }
        return sb.toString();
    }

    public static String appendZero(String str, int strLength) {
        int strLen = str.length();
        StringBuilder sb = null;
        while (strLen < strLength) {
            sb = new StringBuilder();
            sb.append("0").append(str);// 左补0
            // sb.append(str).append("0");//右补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    /**
     * @Description: 转为double类型 ，如果obj为null或者空字符串或者格式不对则返回defaultValue
     * @Param: [obj, defaultValue]
     * @return: String obj为null或者空字符串或者格式不对返回defaultValue
     */
    public static double castDouble(Object obj, double defaultValue) {
        double value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (!MyStringUtils.isEmpty(strValue)) {
                try {
                    value = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }

            }
        }
        return value;
    }

    /**
     * 转为int型(提供默认值)
     *
     * @param obj
     * @param defaultValue
     * @return 如果obj为null或者空字符串或者格式不对则返回defaultValue
     */
    public static int castInt(Object obj, int defaultValue) {
        int value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (!MyStringUtils.isEmpty(strValue)) {
                try {
                    value = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }

            }
        }
        return value;
    }


    /**
     * 获取一段唯一的字符串
     *
     * @return
     */
    public static String getKey() {
        return UUID.randomUUID().toString();
    }

    public static String replaceString(String str, String oldString, String newString) {
        if (MyStringUtils.isEmpty(str)) return "";
        return str.replaceAll(oldString, newString);
    }

    /**
     * 批量替换字符
     *
     * @param str       str
     * @param oldNewMap 输入字符串
     * @return 新的字符串
     */
    public static String replaceString(String str, Map<String, String> oldNewMap) {
        if (oldNewMap == null || oldNewMap.size() == 0) {
            return str;
        }
        String newStr = str;
        for (Map.Entry<String, String> entry : oldNewMap.entrySet()) {
            newStr = newStr.replace(entry.getKey(), entry.getValue());
        }
        return newStr;
    }

    private static String castString(Object obj) {
        return MyStringUtils.castString(obj, "");
    }

    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 将字符数组的子集合成新的字符串
     *
     * @param i   子集的开始id
     * @param j   子集的结束id
     * @param arr 字符数组
     * @return 新的字符串
     */
    public static String subArr2String(int i, int j, char[] arr) {
        if (j < i) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int k = i; k < j; k++) {
            builder.append(arr[k]);
        }
        return builder.toString();
    }

    /**
     * 将字符串数组的子串合成一个新的字符串
     *
     * @param i         子串开始index
     * @param j         子串结束index
     * @param arr       字符串数组
     * @param separator 分隔符
     * @return 新的字符串
     */
    public static String subArr2String(int i, int j, String[] arr, String separator) {
        if (j < i) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int k = i; k < j - 1; k++) {
            if (separator != null) {
                builder.append(arr[k]).append(separator);
            } else {
                builder.append(arr[k]);
            }
        }
        builder.append(arr[j - 1]);
        return builder.toString().trim();
    }

    /**
     * 全角转半角
     *
     * @param input String.
     * @return 半角字符串
     */
    public static String ToDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000')
                c[i] = ' ';
            else if (c[i] > '\uFF00' && c[i] < '\uFF5F')
                c[i] = (char) (c[i] - 65248);
        }

        return new String(c);
    }


    /**
     * 将十六进制字符转换为字节值
     *
     * @param chars
     * @return
     */
    private static byte charToByte(char chars) {
        return (byte) "0123456789abcdef".indexOf(chars);
    }

    /**
     * 字符串MD5加密
     *
     * @param value
     * @return String
     */
    public static String convertToMD5Format(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(value.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte element : messageDigest) {
                hexString.append(String.format("%02x", element & 255));
            }
            value = hexString.toString();
            return value;
        } catch (Exception e) {
            return value;
        }
    }

    /**
     * @param strings
     * @return String
     * @Description 字符串拼接
     */
    public static String sumString(String... strings) {
        StringBuilder builder = new StringBuilder();
        for (String str : strings) {
            builder.append(str);
        }
        return builder.toString();
    }

    /**
     * @param sep
     * @param strings
     * @return
     * @Description 将多个字符串连接起来, 并在每个字符串之间插入指定的分隔符
     */
    public static String sumStringWithSep(String sep, String... strings) {
        StringBuilder builder = new StringBuilder();
        for (String str : strings) {
            builder.append(str).append(sep);
        }
        if (builder.length() > 0) {
            builder.delete(builder.length() - sep.length(), builder.length());
        }
        return builder.toString();
    }

    /**
     * @param array
     * @return
     * @Description 将字节数组转换为十六进制字符串
     */

    public static String byteArrayToHexString(byte[] array) {
        if (array == null) {
            return "";
        }
        StringBuilder hexString = new StringBuilder();
        for (byte b : array) {
            int intVal = b & 255;
            if (intVal < 16) {
                hexString.append("0");
            } else {
                hexString.append(Integer.toHexString(intVal));
            }
        }
        return hexString.toString();
    }

    /**
     * @param hex
     * @return
     * @Description 将十六进制字符串转换为字节数组。
     */
    public static byte[] hexStringToByteArray(String hex) {
        if (isEmpty(hex)) {
            return null;
        }
        String hex2 = hex.toLowerCase();
        int length = hex2.length() / 2;
        char[] hexChars = hex2.toCharArray();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            bytes[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
        }
        return bytes;
    }
}
