package com.bingbaihanji.utils.fileUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
拷贝目录
 */
public class FileCopy {

    /**
     * 拷贝目录
     *
     * @param srcFile  拷贝源
     * @param destFile 拷贝目标
     */
    public static void copyDir(File srcFile, File destFile) {
        if (srcFile.isFile()) {
            // srcFile如果是一个文件的话，递归结束。
            // 是文件的时候需要拷贝。
            // ....一边读一边写。
            FileInputStream in = null;
            FileOutputStream out = null;
            try {
                // 读这个文件
                in = new FileInputStream(srcFile);
                // 写到这个文件中
                String path = (destFile.getAbsolutePath().endsWith("\\") ? destFile.getAbsolutePath()
                        : destFile.getAbsolutePath() + "\\") + srcFile.getAbsolutePath().substring(3);
                out = new FileOutputStream(path);
                // 一边读一边写
                byte[] bytes = new byte[1024 * 1024]; // 一次复制1MB
                int readCount = 0;
                while ((readCount = in.read(bytes)) != -1) {
                    out.write(bytes, 0, readCount);
                }
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return;
        }
        // 获取源下面的子目录
        File[] files = srcFile.listFiles();
        assert files != null;
        for (File file : files) {
            // 获取所有文件的（包括目录和文件）绝对路径
            // System.out.println(file.getAbsolutePath());
            if (file.isDirectory()) {
                // 新建对应的目录
                // System.out.println(file.getAbsolutePath());
                String srcDir = file.getAbsolutePath();
                String destDir = (destFile.getAbsolutePath().endsWith("\\") ? destFile.getAbsolutePath()
                        : destFile.getAbsolutePath() + "\\") + srcDir.substring(3);
                File newFile = new File(destDir);
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
            }
            // 递归调用
            copyDir(file, destFile);
        }
    }

    /**
     * 复制文件
     *
     * @param resourcePath 源文件
     * @param targetPath   目标文件
     * @return 是否成功
     */
    public static boolean copyFile(String resourcePath, String targetPath) {
        File resources = new File(resourcePath);
        File target = new File(targetPath);
        return copyFile(resources, target);
    }

    /**
     * 复制文件
     * 通过该方式复制文件文件越大速度越是明显
     *
     * @param resourcePath 需要处理的文件
     * @param targetFile   目标文件
     * @return 是否成功
     */
    public static boolean copyFile(File resourcePath, File targetFile) {
        try (FileInputStream fileInputStream = new FileInputStream(resourcePath);
             FileOutputStream fileOutputStream = new FileOutputStream(targetFile)) {
            FileChannel fileChannel = fileInputStream.getChannel();
            FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
            //设定缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            while (fileChannel.read(buffer) != -1) {
                //准备写入，防止其他读取，锁住文件
                buffer.flip();
                fileOutputStreamChannel.write(buffer);
                //准备读取。将缓冲区清理完毕，移动文件内部指针
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}