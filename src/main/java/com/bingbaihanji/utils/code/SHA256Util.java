package com.bingbaihanji.utils.code;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class SHA256Util {
    /**
     * @param input
     * @return String 64位加密字符串
     * @Description 计算字符串的SHA-256哈希值
     */
    public static String getSHA256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param filePath
     * @return String 64位校验码
     * @Description 计算文件的SHA-256校验码
     */
    public static String getFileChecksum(String filePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(Files.readAllBytes(Paths.get(filePath)));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param filePath1
     * @param filePath2
     * @return boolean
     * @Description 校验文件是否损毁
     */
    public static boolean compareFileChecksums(String filePath1, String filePath2) {
        return getFileChecksum(filePath1).equals(getFileChecksum(filePath2));
    }
}