package com.bingbaihanji.utils.fileUtil;



import com.bingbaihanji.utils.code.MyStringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileOperateUtil {

    /**
     * 将多个文件压缩到制定目录
     *
     * @param files   待压缩的文件路径
     * @param zipPath 压缩包的路径名
     * @throws IOException
     */
    public static void zipFiles(ArrayList<String> files, String zipPath) throws IOException {
        OutputStream os = new BufferedOutputStream(
                new FileOutputStream(zipPath + "/checker.model"));
        ZipOutputStream zos = new ZipOutputStream(os);
        byte[] buf = new byte[8192];
        int len;
        for (String filePath : files) {
            File file = new File(filePath);
            if (!file.isFile())
                continue;
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((len = bis.read(buf)) > 0) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            bis.close();
        }
        zos.close();

        //删除已压缩的文件
        for (String filePath : files) {
            File file = new File(filePath);
            file.delete();
        }
    }

    /**
     * 解压压缩文件到指定目录, 并返回文件列表
     *
     * @param filePath 待解压的文件
     * @param toPath   解压的路径
     * @throws IOException
     */
    public static List<File> unZipFile(String filePath, String toPath) throws IOException {
        List<File> list = new ArrayList<>();

        //去目录下寻找文件
        File file = new File(filePath);
        ZipFile zipFile = null;

        try {
            zipFile = new ZipFile(file);//设置编码格式
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("解压文件不存在!");
        }

        assert zipFile != null;
        Enumeration<? extends ZipEntry> e = zipFile.entries();
        while (e.hasMoreElements()) {
            ZipEntry zipEntry = e.nextElement();
            if (zipEntry.isDirectory()) {
            } else {
                File tempFile = new File(toPath + zipEntry.getName());
                list.add(tempFile);
                tempFile.getParentFile().mkdirs();
                tempFile.createNewFile();
                InputStream is = zipFile.getInputStream(zipEntry);
                FileOutputStream fos = new FileOutputStream(tempFile);
                int length = 0;
                byte[] b = new byte[1024];
                while ((length = is.read(b, 0, 1024)) != -1) {
                    fos.write(b, 0, length);
                }
                is.close();
                fos.close();
            }
        }
        return list;
    }

    /**
     * 按行读取给定编码的文本文件，返回以行为元素的列表
     *
     * @param path     文本文件路径
     * @param encoding 文件编码
     * @return 以行为元素的列表
     * @throws IOException
     */
    public static ArrayList<String> readConfusionSetFile(String path, String encoding)
            throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        File file = new File(path);

        if (file.isFile() && file.exists()) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = "";
            while ((line = reader.readLine()) != null) {
                line = MyStringUtils.ToDBC(line).trim();
                if (MyStringUtils.isEmpty(line)) continue;
                list.add(line);
            }
            reader.close();
        } else {
            System.err.println("文件:\"" + path + "\" 读取失败!");
        }

        return list;
    }

    /**
     * 写文件
     *
     * @param contend  待写的内容
     * @param outPath  输出文件
     * @param encoding 输出文件编码
     * @throws IOException
     */
    public static void writeFile(ArrayList<String> contend, String outPath, String encoding)
            throws IOException {
        OutputStreamWriter oWriter =
                new OutputStreamWriter(new FileOutputStream(outPath), encoding);
        BufferedWriter writer = new BufferedWriter(oWriter);
        for (String line : contend) {
            writer.write(line);
            writer.newLine();

        }
        writer.close();
    }

    public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (
                InputStream inputStreams = inputStream;
                OutputStream outputStream = new FileOutputStream(file)) {
            copyLarge(inputStreams, outputStream, new byte[8192]);
        }
    }

    // 将字节从大(超过2GB)的InputStream复制到OutputStream。
    // 该方法使用提供的缓冲区，因此不需要使用BufferedInputStream。
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream, final byte[] buffer)
            throws IOException {
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
