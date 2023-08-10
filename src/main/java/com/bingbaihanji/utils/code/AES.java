package com.bingbaihanji.utils.code;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class AES {
    private static final String ALGORITHM = "AES";

    /**
     *
     * @param valueToEnc
     * @param secret
     * @return String
     * @throws Exception
     * @Description 使用AES加密字符串,需长度为16位的秘钥
     */
    public static String encrypt(String valueToEnc, String secret) throws Exception {
        Key key = generateKey(secret);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = cipher.doFinal(valueToEnc.getBytes());
        return Base64.getEncoder().encodeToString(encValue);
    }

    /**
     *
     * @param encryptedValue
     * @param secret
     * @return Strign
     * @throws Exception
     * @Description 解密
     */
    public static String decrypt(String encryptedValue, String secret) throws Exception {
        Key key = generateKey(secret);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.getDecoder().decode(encryptedValue);
        byte[] decValue = cipher.doFinal(decodedValue);
        return new String(decValue);
    }

    /**
     *
     * @param secret
     * @return
     * @throws Exception
     * @Description
     * generateKey方法用于根据给定的密钥字符串生成一个Key对象。
     * 它使用了Java内置的SecretKeySpec类来根据给定的字节数组和算法名称创建一个密钥。
     * 在这个示例代码中，generateKey方法接受一个字符串类型的密钥，并使用getBytes方法将其转换为字节数组。
     * 然后，它使用这个字节数组和算法名称（在这种情况下是"AES"）来创建一个新的SecretKeySpec对象，并将其作为一个Key对象返回。
     * 这个Key对象可以用于初始化加密和解密操作。
     */
    private static Key generateKey(String secret) throws Exception {
        return new SecretKeySpec(secret.getBytes(), ALGORITHM);
    }
}