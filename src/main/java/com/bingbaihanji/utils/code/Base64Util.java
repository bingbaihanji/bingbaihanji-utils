package com.bingbaihanji.utils.code;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * Base64的编码和解码
 */
public class Base64Util {

    /**
     * 判断一个字符串是否为Base64编码后的字符串
     *
     * @param str 待验证的字符串
     * @return true表示字符串是Base64编码后的字符串，false表示不是
     */
    public static boolean isBase64(String str) {
        // 将字符串转换为字节数组
        byte[] byteArray = str.getBytes();
        // 判断是否是Base64编码后的字符串
        try {
            Base64.getDecoder().decode(byteArray);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final int[] INV = new int[256];

    static {
        Arrays.fill(INV, -1);
        for (int i = 0, iS = CHARS.length; i < iS; i++) {
            INV[CHARS[i]] = i;
        }
        INV['='] = 0;
    }

    /**
     * 将一个原始字节数组编码为BASE64 char[]
     *
     * @param lineSeparator optional CRLF after 76 chars, unless EOF.
     */
    public static char[] encodeToChar(byte[] arr, boolean lineSeparator) {
        int len = arr != null ? arr.length : 0;
        if (len == 0) {
            return new char[0];
        }

        int evenlen = (len / 3) * 3;
        int cnt = ((len - 1) / 3 + 1) << 2;
        int destLen = cnt + (lineSeparator ? (cnt - 1) / 76 << 1 : 0);
        char[] dest = new char[destLen];

        for (int s = 0, d = 0, cc = 0; s < evenlen; ) {
            int i = (arr[s++] & 0xff) << 16 | (arr[s++] & 0xff) << 8 | (arr[s++] & 0xff);

            dest[d++] = CHARS[(i >>> 18) & 0x3f];
            dest[d++] = CHARS[(i >>> 12) & 0x3f];
            dest[d++] = CHARS[(i >>> 6) & 0x3f];
            dest[d++] = CHARS[i & 0x3f];

            if (lineSeparator && (++cc == 19) && (d < (destLen - 2))) {
                dest[d++] = '\r';
                dest[d++] = '\n';
                cc = 0;
            }
        }

        int left = len - evenlen; // 0 - 2.
        if (left > 0) {
            int i = ((arr[evenlen] & 0xff) << 10) | (left == 2 ? ((arr[len - 1] & 0xff) << 2) : 0);

            dest[destLen - 4] = CHARS[i >> 12];
            dest[destLen - 3] = CHARS[(i >>> 6) & 0x3f];
            dest[destLen - 2] = left == 2 ? CHARS[i & 0x3f] : '=';
            dest[destLen - 1] = '=';
        }
        return dest;
    }

    /**
     * 将字符串编码为Base64格式的字符串
     */
    public static String encodeToString(String str) {
        return new String(encodeToChar(str.getBytes(StandardCharsets.UTF_8), false));
    }

    /**
     * 将将字符串编码为Base64格式的byte数组
     */
    public static byte[] encodeToByte(String str) {
        return encodeToByte(str.getBytes(StandardCharsets.UTF_8), false);
    }

    public static byte[] encodeToByte(String str, boolean lineSep) {
        return encodeToByte(str.getBytes(StandardCharsets.UTF_8), lineSep);
    }

    public static byte[] encodeToByte(byte[] bytes) {
        return encodeToByte(bytes, false);
    }

    /**
     * 将一个原始字节数组编码为BASE64 char[]
     *
     * @param lineSep optional CRLF after 76 chars, unless EOF.
     */
    public static byte[] encodeToByte(byte[] arr, boolean lineSep) {
        int len = arr != null ? arr.length : 0;
        if (len == 0) {
            return new byte[0];
        }

        int evenlen = (len / 3) * 3;
        int cnt = ((len - 1) / 3 + 1) << 2;
        int destlen = cnt + (lineSep ? (cnt - 1) / 76 << 1 : 0);
        byte[] dest = new byte[destlen];

        for (int s = 0, d = 0, cc = 0; s < evenlen; ) {
            int i = (arr[s++] & 0xff) << 16 | (arr[s++] & 0xff) << 8 | (arr[s++] & 0xff);

            dest[d++] = (byte) CHARS[(i >>> 18) & 0x3f];
            dest[d++] = (byte) CHARS[(i >>> 12) & 0x3f];
            dest[d++] = (byte) CHARS[(i >>> 6) & 0x3f];
            dest[d++] = (byte) CHARS[i & 0x3f];

            if (lineSep && ++cc == 19 && d < destlen - 2) {
                dest[d++] = '\r';
                dest[d++] = '\n';
                cc = 0;
            }
        }

        int left = len - evenlen;
        if (left > 0) {
            int i = ((arr[evenlen] & 0xff) << 10) | (left == 2 ? ((arr[len - 1] & 0xff) << 2) : 0);

            dest[destlen - 4] = (byte) CHARS[i >> 12];
            dest[destlen - 3] = (byte) CHARS[(i >>> 6) & 0x3f];
            dest[destlen - 2] = left == 2 ? (byte) CHARS[i & 0x3f] : (byte) '=';
            dest[destlen - 1] = '=';
        }
        return dest;
    }

    /**
     * 将原始字节数组编码为BASE64 String
     */
    public static String encodeToString(byte[] arr, boolean lineSep) {
        return new String(encodeToChar(arr, lineSep));
    }

    /**
     * 将原始字节数组编码为BASE64 String
     */
    public static String decodeToString(byte[] arr) {
        return new String(decode(arr), StandardCharsets.UTF_8);
    }

    /**
     * 解码BASE64编码的字符串为字符串。
     *
     * @param arr BASE64编码的字节数组
     * @return 解密后的字节数组
     */
    public static byte[] decode(byte[] arr) {
        int length = arr.length;
        if (length == 0) {
            return new byte[0];
        }

        int sndx = 0, index = length - 1;
        int pad = arr[index] == '=' ? (arr[index - 1] == '=' ? 2 : 1) : 0;
        int cnt = index - sndx + 1;
        int sepCnt = length > 76 ? (arr[76] == '\r' ? cnt / 78 : 0) << 1 : 0;
        int len = ((cnt - sepCnt) * 6 >> 3) - pad;
        byte[] dest = new byte[len];

        int d = 0;
        for (int cc = 0, eLen = (len / 3) * 3; d < eLen; ) {
            int i = INV[arr[sndx++]] << 18 | INV[arr[sndx++]] << 12 | INV[arr[sndx++]] << 6 | INV[arr[sndx++]];

            dest[d++] = (byte) (i >> 16);
            dest[d++] = (byte) (i >> 8);
            dest[d++] = (byte) i;

            if (sepCnt > 0 && ++cc == 19) {
                sndx += 2;
                cc = 0;
            }
        }

        if (d < len) {
            int i = 0;
            for (int j = 0; sndx <= index - pad; j++) {
                i |= INV[arr[sndx++]] << (18 - j * 6);
            }
            for (int r = 16; d < len; r -= 8) {
                dest[d++] = (byte) (i >> r);
            }
        }

        return dest;
    }


    public static String encodeToString(String s, boolean lineSep) {
        return new String(encodeToChar(s.getBytes(StandardCharsets.UTF_8), lineSep));
    }

    public static String encodeToString(byte[] arr) {
        return new String(encodeToChar(arr, false));
    }

    /**
     * 解码BASE64编码的字符串为字符串。
     *
     * @param str BASE64编码的字符串
     * @return 解密后的字符串
     */
    public static String decodeToString(String str) {
        return new String(decode(str), StandardCharsets.UTF_8);
    }

    /**
     * 解码BASE64编码的字符串为字符串。
     *
     * @param str BASE64编码的字符串
     * @return 解密后的字节数组
     */
    public static byte[] decode(String str) {
        int length = str.length();
        if (length == 0) {
            return new byte[0];
        }

        int sndx = 0, endx = length - 1;
        int pad = str.charAt(endx) == '=' ? (str.charAt(endx - 1) == '=' ? 2 : 1) : 0;
        int cnt = endx - sndx + 1;
        int sepCnt = length > 76 ? (str.charAt(76) == '\r' ? cnt / 78 : 0) << 1 : 0;
        int len = ((cnt - sepCnt) * 6 >> 3) - pad;
        byte[] dest = new byte[len];

        int d = 0;
        for (int cc = 0, eLen = (len / 3) * 3; d < eLen; ) {
            int i = INV[str.charAt(sndx++)] << 18 | INV[str.charAt(sndx++)] << 12 | INV[str.charAt(sndx++)] << 6 | INV[str.charAt(sndx++)];

            dest[d++] = (byte) (i >> 16);
            dest[d++] = (byte) (i >> 8);
            dest[d++] = (byte) i;

            if (sepCnt > 0 && ++cc == 19) {
                sndx += 2;
                cc = 0;
            }
        }

        if (d < len) {
            int i = 0;
            for (int j = 0; sndx <= endx - pad; j++) {
                i |= INV[str.charAt(sndx++)] << (18 - j * 6);
            }
            for (int r = 16; d < len; r -= 8) {
                dest[d++] = (byte) (i >> r);
            }
        }

        return dest;
    }

    /**
     * 解码BASE64编码的字符串为字符串。
     *
     * @param arr BASE64编码的char数组
     * @return 解密后的字节数组
     */
    public final byte[] decode(char[] arr) {
        int length = arr.length;
        if (length == 0) {
            return new byte[0];
        }

        int sndx = 0, endx = length - 1;
        int pad = arr[endx] == '=' ? (arr[endx - 1] == '=' ? 2 : 1) : 0;
        int cnt = endx - sndx + 1;
        int sepCnt = length > 76 ? (arr[76] == '\r' ? cnt / 78 : 0) << 1 : 0;
        int len = ((cnt - sepCnt) * 6 >> 3) - pad;
        byte[] dest = new byte[len];

        int d = 0;
        for (int cc = 0, eLen = (len / 3) * 3; d < eLen; ) {
            int i = INV[arr[sndx++]] << 18 | INV[arr[sndx++]] << 12 | INV[arr[sndx++]] << 6 | INV[arr[sndx++]];

            dest[d++] = (byte) (i >> 16);
            dest[d++] = (byte) (i >> 8);
            dest[d++] = (byte) i;

            if (sepCnt > 0 && ++cc == 19) {
                sndx += 2;
                cc = 0;
            }
        }

        if (d < len) {
            int i = 0;
            for (int j = 0; sndx <= endx - pad; j++) {
                i |= INV[arr[sndx++]] << (18 - j * 6);
            }
            for (int r = 16; d < len; r -= 8) {
                dest[d++] = (byte) (i >> r);
            }
        }

        return dest;
    }
}
