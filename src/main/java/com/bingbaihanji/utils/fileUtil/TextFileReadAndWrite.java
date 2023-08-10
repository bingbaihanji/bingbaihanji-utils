package com.bingbaihanji.utils.fileUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TextFileReadAndWrite {

    /**
     * 将字符串写入到文件
     *
     * @param path     路径
     * @param fileName 文件名
     * @param append   是否追加
     * @param text     文本数据
     * @return Boolean
     */
    public static boolean TextFileWrite(String path, String fileName, boolean append, String text) {
        boolean bool = false;
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(path + "//" + fileName, append);
            fwriter.write(text);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                assert fwriter != null;
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return bool;
    }

    /**
     * 将字符串写入到文件中
     *
     * @param file 文件对象
     * @param str  需要写入的字符串
     * @return boolean
     */
    public static boolean TextFileWrite(File file, String str) {
        try (RandomAccessFile randomFile = new RandomAccessFile(file, "rw")) {
            randomFile.writeBytes(str);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将字符串以追加的方式写入到文件中
     *
     * @param file 文件对象
     * @param str  需要写入的字符串
     * @return boolean
     */
    public static boolean TextFileWriteAppend(File file, String str) {
        try (RandomAccessFile randomFile = new RandomAccessFile(file, "rw")) {
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.writeBytes(str);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将字符串以制定的编码写入到文件中
     *
     * @param file     文件对象
     * @param str      需要写入的字符串
     * @param encoding 编码格式
     * @return boolean
     */
    public static boolean TextFileWriteEncoding(File file, String str, String encoding) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
            randomAccessFile.write(str.getBytes(encoding));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将字符串以追加的方式以制定的编码写入到文件中
     *
     * @param file     文件对象
     * @param str      需要写入的字符串
     * @param encoding 编码格式
     * @return boolean
     */
    public static boolean TextFileWriteEncodingAppend(File file, String str, String encoding) {
        try (RandomAccessFile randomFile = new RandomAccessFile(file, "rw")) {
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.write(str.getBytes(encoding));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将字符串以追加形式写入到文件,文件路径必须存在
     *
     * @param path     路径
     * @param fileName 文件名
     * @param text     文本数据
     * @return Boolean
     */
    public static boolean TextFileWrite(String path, String fileName, String text) {
        boolean bool = false;
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(path + System.getProperty("file.separator") + fileName, true);
            fwriter.write(text);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                assert fwriter != null;
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return bool;
    }

    /**
     * 在文本文件末尾追加一行
     *
     * @param file 需要处理的函数
     * @param str  添加的子字符串
     * @return 是否成功
     */
    public static boolean appendLine(File file, String str) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
            long fileLength = randomAccessFile.length();
            randomAccessFile.seek(fileLength);
            // line.separator 系统换行符key
            randomAccessFile.writeBytes(System.getProperty("line.separator") + str);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 在文本文件末尾追加一行
     *
     * @param file     需要处理的文件
     * @param str      添加的字符串
     * @param encoding 指定写入的编码
     * @return 是否成功
     */
    public static boolean appendLine(File file, String str, String encoding) {
//        String lineSeparator = System.getProperty("line.separator", "\n");
        try (
                RandomAccessFile randomFile = new RandomAccessFile(file, "rw")
        ) {
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.write((System.getProperty("line.separator") + str).getBytes(encoding));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 传入文件绝对路径读取文本文件
     *
     * @param file    文件
     * @param newline 是否换行
     * @return String
     */
    public static String TextFileReader(String file, boolean newline) {

        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                if (newline) {
                    stringBuilder.append(tempString).append('\n');
                } else {
                    stringBuilder.append(tempString);
                }

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 以列表的方式读取取文件的所有行
     *
     * @param file 需要出来的文件
     * @return 包含所有行的list
     */
    public static List<String> lines(File file) {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 以列表的方式获取到文件的指定的行数数据
     *
     * @param file  处理的文件
     * @param lines 需要读取到的行数
     * @return 包含制定行的list
     */
    public static List<String> lines(File file, int lines) {
        List<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
                if (list.size() == lines) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 以列表的方式获取文件的指定的行数数据
     *
     * @param file     需要处理的函数
     * @param lines    需要读取到的行数
     * @param encoding 指定读取文件的编码
     * @return 包含制定行的list
     */
    public static List<String> lines(File file, int lines, String encoding) {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(new FileInputStream(file), encoding))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
                if (list.size() == lines) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 快速清空一个超大文本文件
     *
     * @param file 需要处理的文件
     * @return 是否成功
     */
    public static boolean cleanFile(File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
