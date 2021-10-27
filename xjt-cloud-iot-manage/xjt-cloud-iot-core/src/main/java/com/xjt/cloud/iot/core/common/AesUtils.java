package com.xjt.cloud.iot.core.common;

import com.xjt.cloud.commons.utils.SysLog;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import org.apache.commons.codec.binary.Base64;


/**
 * @author Awoke
 * @version 2018-1-24
 * @see AesUtils
 * @since
 */
public class AesUtils {
    private static final String KEY_ALGORITHM = "AES";
    private static final String CHAR_SET = "UTF-8";

    /**
     * AES的密钥长度
     */
    private static final Integer SECRET_KEY_LENGTH = 128;

    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES加密
     * @param content  字符串内容
     * @param key 密钥
     */
    public static String encrypt(String content, String key) {
        return aes(content, key, Cipher.ENCRYPT_MODE);
    }
    public static String decrypt(String content, String key) {
        return aes(content, key, Cipher.DECRYPT_MODE);
    }

    /**
     * AES加密/解密 公共方法
     * @param content  字符串
     * @param password 密钥
     * @param type     加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     */
    private static String aes(String content, String password, int type) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            generator.init(SECRET_KEY_LENGTH, random);
            SecretKey secretKey = generator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(type, key);
            if (type == Cipher.ENCRYPT_MODE) {
                byte[] byteContent = content.getBytes(CHAR_SET);
                return parseByte2HexStr(cipher.doFinal(byteContent));
            } else {
                byte[] byteContent = parseHexStr2Byte(content);
                return new String(cipher.doFinal(byteContent));
            }
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            SysLog.info(e.getMessage());
        }
        return null;
    }


    /**
     * 二进位组转十六进制字符串
     * @param buf 二进位组
     * @return 十六进制字符串
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转二进位组
     * @param hexStr 十六进制字符串
     * @return 二进位组
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1){
            return null;
        };

        byte[] result = new byte[hexStr.length() / 2];

        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
