package com.bingbaihanji.utils.fileUtil;

import java.io.*;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 功能：zip格式的压缩与解压
 */
public class FileZip {
    private static final String ZIP_FILE_SUFFIX = ".zip";


    /**
     * 文档压缩
     *
     * @param file 需要压缩的文件或目录
     * @param dest 压缩后的文件名称
     * @throws IOException
     */
    public static void deCompress(File file, String dest) throws IOException {
        ZipFile zf = new ZipFile(dest);

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dest))) {
            String dir = "";
            if (file.isDirectory()) {
                dir = file.getName();
            }
            zipFile(file, zos, dir);
        } catch (IOException e) {
            throw e;
        }
    }

    public static void zipFile(File inFile, ZipOutputStream zos, String dir) throws IOException {
        if (inFile.isDirectory()) {
            File[] files = inFile.listFiles();
            if (files == null || files.length == 0) {
                String entryName = dir + "/";
                zos.putNextEntry(new ZipEntry(entryName));
                return;
            }
            for (File file : files) {
                String entryName = dir + "/" + file.getName();
                if (file.isDirectory()) {
                    zipFile(file, zos, entryName);
                } else {
                    ZipEntry entry = new ZipEntry(entryName);
                    zos.putNextEntry(entry);
                    try (InputStream is = new FileInputStream(file)) {
                        int len = 0;
                        while ((len = is.read()) != -1) {
                            zos.write(len);
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                }
            }
        } else {
            String entryName = dir + "/" + inFile.getName();
            ZipEntry entry = new ZipEntry(entryName);
            zos.putNextEntry(entry);
            try (InputStream is = new FileInputStream(inFile)) {
                int len = 0;
                while ((len = is.read()) != -1) {
                    zos.write(len);
                }
            } catch (IOException e) {
                throw e;
            }
        }

    }

    /**
     * 文档解压
     *
     * @param source 需要解压缩的文档名称
     * @param path   需要解压缩的路径
     */
    public static void unCompress(File source, String path) throws IOException {
        ZipEntry zipEntry = null;
        FileUtil.createPaths(path);
        //实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
        //实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
        try (ZipFile zipFile = new ZipFile(source);
             ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(source))) {
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                String filePath = path + "/" + fileName;
                File temp = new File(filePath);
                if (zipEntry.isDirectory()) {
                    if (!temp.exists()) {
                        temp.mkdirs();
                    }
                } else {
                    if (!temp.getParentFile().exists()) {
                        temp.getParentFile().mkdirs();
                    }
                    try (OutputStream os = new FileOutputStream(temp);
                         //通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
                         InputStream is = zipFile.getInputStream(zipEntry)) {
                        int len = 0;
                        while ((len = is.read()) != -1) {
                            os.write(len);
                        }
                    }
                }
            }
        }
    }

    /**
     * 压缩文件
     *
     * @param resourcePath 源文件
     * @param targetPath   目的文件,保存文件路径
     */
    public static void zipFile(String resourcePath, String targetPath) {
        File resourcesFile = new File(resourcePath);
        File targetFile = new File(targetPath);

        //目的文件不存在，则新建
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //文件名
        String targetName = resourcesFile.getName() + ZIP_FILE_SUFFIX;

        ZipOutputStream out = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(targetPath + "//" + targetName);
            out = new ZipOutputStream(new BufferedOutputStream(outputStream));

            compressedFile(out, resourcesFile, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param out  压缩流
     * @param file 目的文件
     * @param dir  条目
     */
    private static void compressedFile(ZipOutputStream out, File file, String dir) {
        FileInputStream fis = null;
        try {
            if (file.isDirectory()) {    //文件夹
                // 得到文件列表信息
                File[] files = file.listFiles();
                // 将文件夹添加到下一级打包目录
                out.putNextEntry(new ZipEntry(dir + "/"));

                dir = dir.length() == 0 ? "" : dir + "/";

                // 循环将文件夹中的文件打包
                for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                    compressedFile(out, files[i], dir + files[i].getName()); // 递归处理
                }
            } else {    //如果是文件则打包处理
                fis = new FileInputStream(file);

                out.putNextEntry(new ZipEntry(dir));
                // 进行写操作
                int j = 0;
                byte[] buffer = new byte[1024];
                while ((j = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, j);
                }
                // 关闭输入流
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param inputFileName 要压缩的文件夹(整个完整路径)
     * @param zipFileName   压缩后的文件(整个完整路径加文件名)
     */
    public static void zip(String inputFileName, String zipFileName) throws Exception {
        zip(zipFileName, new File(inputFileName));
    }

    public static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        out.flush();
        out.close();
    }


    private static void zip(ZipOutputStream out, File file, String base) throws Exception {
        if (file.isDirectory()) {
            File[] file1 = file.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < Objects.requireNonNull(file1).length; i++) {
                zip(out, file1[i], base + file1[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(file);
            int b;
            while (-1 != (b = in.read())) {
                out.write(b);
            }
            in.close();
        }
    }
}
