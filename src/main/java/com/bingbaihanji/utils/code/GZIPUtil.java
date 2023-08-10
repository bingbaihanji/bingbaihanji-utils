package com.bingbaihanji.utils.code;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/*
 *通过Gzip算法压缩和解压
 **/

public class GZIPUtil {

    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    /**
     * 字符串压缩为GZIP字节数组
     *
     * @param str
     * @return
     */
    public static byte[] compress(String str) {
        return compress(str, GZIP_ENCODE_UTF_8);
    }

    /**
     * 字符串压缩为GZIP字节数组
     *
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        byte[] bytes = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            GZIPOutputStream gzip;
            try {
                gzip = new GZIPOutputStream(out);
                gzip.write(str.getBytes(encoding));
                gzip.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public static byte[] compress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        byte[] byteArr = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            GZIPOutputStream gzip = null;
            try {
                gzip = new GZIPOutputStream(out);
                gzip.write(bytes);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (gzip != null) {
                    gzip.close();
                }
            }
            byteArr = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArr;
    }

    /**
     * GZIP解压缩
     *
     * @param bytes
     * @return
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        byte[] byteArr = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            GZIPInputStream ungzip = new GZIPInputStream(in);
            try {
                byte[] buffer = new byte[256];
                int n;
                while ((n = ungzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                ungzip.close();
            }
            byteArr = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArr;
    }

    /**
     * @param bytes
     * @return
     */
    public static String uncompress2Str(byte[] bytes) {
        return uncompress2Str(bytes, GZIP_ENCODE_UTF_8);
    }

    /**
     * @param bytes
     * @param encoding
     * @return
     */
    public static String uncompress2Str(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String str = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            try {
                GZIPInputStream ungzip = new GZIPInputStream(in);
                byte[] buffer = new byte[256];
                int n;
                while ((n = ungzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                in.close();
                ungzip.close();
                str = out.toString(encoding);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
