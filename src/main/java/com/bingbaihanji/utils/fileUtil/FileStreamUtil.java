package com.bingbaihanji.utils.fileUtil;

import java.io.*;

/**
 * 流相关的操作方法封装
 */
public class FileStreamUtil {
    /**
     * 将输入流读入字符串
     */
    public static String streamToString(InputStream in) throws IOException {
        StringBuilder stringBuffer = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            stringBuffer.append(new String(b, 0, n));
        }
        return stringBuffer.toString();
    }

    /**
     * 将输入流读入字节数组
     */
    public static byte[] streamToByteArr(InputStream is) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len;
        byte[] b = new byte[1024];
        while ((len = is.read(b, 0, b.length)) != -1) {
            byteArrayOutputStream.write(b, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * InputStream 转为 byte
     */
    public static byte[] inputStream2Byte(InputStream inStream) throws Exception {
        int count = 0;
        while (count == 0) {
            count = inStream.available();
        }
        byte[] b = new byte[count];
        inStream.read(b);
        return b;
    }

    /**
     * byte 转为 InputStream
     *
     * @return InputStream
     */
    public static InputStream byte2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 将流另存为文件
     */
    public static void streamSaveAsFile(InputStream is, File outfile) {
        try (FileOutputStream fos = new FileOutputStream(outfile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
