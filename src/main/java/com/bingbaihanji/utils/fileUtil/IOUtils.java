package com.bingbaihanji.utils.fileUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class IOUtils {

    /**
     * @param inputStream
     * @return String
     * @Description 将InputStream流转化为字符串
     */
    public static String getString(InputStream inputStream) {
        byte[] bytes = getBytes(inputStream);
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }

    /**
     * @param inputStream
     * @return byte[]
     * @Description 将InputStream流转化为字节数组
     */
    public static byte[] getBytes(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[8192];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    inputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException unused) {
            return null;
        }
    }

    /**
     * @param inputStream
     * @Description 关闭inputStream流
     */
    public static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param outputStream
     * @Description 关闭outputStream流
     */
    public static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param inputStream
     * @param outputStream
     * @param buffer
     * @throws IOException
     * @MethodName: copyLarge
     * @Author 冰白寒祭
     * @Description: // 将inputStream转为outputStream
     * @Date: 2023/7/22 0022 0:23
     * @return: long 以复制的字节数
     */

    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream,
                                 final byte[] buffer) throws IOException {
        // 检查传入的对象是否为 null,如果为 null,则抛出 NullPointerException 异常。
        Objects.requireNonNull(inputStream, "inputStream");
        Objects.requireNonNull(outputStream, "outputStream");
        long count = 0;
        int n;
        while (-1 != (n = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}