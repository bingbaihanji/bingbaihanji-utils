package com.bingbaihanji.utils.fileUtil;

import java.io.*;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtil {
    /**
     * 获取文件的行数
     *
     * @param file 统计的文件
     * @return 文本文件行数
     */
    public static int countLines(File file) {
        try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file))) {
            long fileLength = file.length();
            lineNumberReader.skip(fileLength);
            return lineNumberReader.getLineNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取文件的Mime类型
     *
     * @param file 需要处理的文件
     * @return 返回文件的mime类型
     */
    public static String mimeType(String file) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        return fileNameMap.getContentTypeFor(file);
    }

    /**
     * 获取文件最后的修改时间
     *
     * @param file 需要处理的文件
     * @return 返回文件的修改时间
     */
    public static Date modifyTime(File file) {
        return new Date(file.lastModified());
    }

    /**
     * 罗列指定路径下的全部文件
     *
     * @param path 需要处理的文件
     * @return 返回文件列表
     */
    public static List<File> listFile(File path) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                list.addAll(listFile(file));
            } else {
                list.add(file);
            }
        }
        return list;
    }

    /**
     * 罗列指定路径下的全部文件
     *
     * @param path  指定的路径
     * @param child 是否罗列子目录
     * @return
     */
    public static List<File> listFile(File path, boolean child) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        assert files != null;
        for (File file : files) {
            if (child && file.isDirectory()) {
                list.addAll(listFile(file));
            } else {
                list.add(file);
            }
        }
        return list;
    }

    /**
     * 罗列指定路径下的全部文件包括文件夹
     *
     * @param path   需要处理的文件
     * @param filter 处理文件的filter
     * @return 返回文件列表
     */
    public static List<File> listFileFilter(File path, FilenameFilter filter) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                list.addAll(listFileFilter(file, filter));
            } else {
                if (filter.accept(file.getParentFile(), file.getName())) {
                    list.add(file);
                }
            }

        }
        return list;
    }

    /**
     * 创建多级目录
     *
     * @param paths 需要创建的目录
     * @return 是否成功
     */
    public static boolean createPaths(String paths) {
        File dir = new File(paths);
        return !dir.exists() && dir.mkdir();
    }


}
