package com.hengtiansoft.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.common.constant.ApplicationConstant;

/**
 * Class Name: EncryptUtil Description: Encryption tool class
 * 
 * @author taochen
 *
 */
public class EncryptUtil {
    private static final Logger log = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * Description: MD5 encryption
     * 
     * @param input
     * @param salt
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encryptMd5(String input, String salt) {
        input = input.toLowerCase();
        try {
            int middle = input.length() / 2;
            byte[] result = MessageDigest.getInstance("MD5").digest(
                    (input.substring(0, middle) + salt + input.substring(middle)).getBytes());
            StringBuilder strBuilder = new StringBuilder(result.length * 2);
            for (byte b : result) {
                String s = Integer.toHexString(b & 0x00FF);
                if (1 == s.length()) {
                    strBuilder.append('0');
                }
                strBuilder.append(s);
            }
            return strBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Description: Password is encrypted once
     */
    public static String encryptMd5(String input) {
        try {
            byte[] result = MessageDigest.getInstance("MD5").digest(input.getBytes(ApplicationConstant.ENCODING));
            StringBuilder strBuilder = new StringBuilder(result.length * 2);
            for (byte b : result) {
                String s = Integer.toHexString(b & 0x00FF);
                if (1 == s.length()) {
                    strBuilder.append('0');
                }
                strBuilder.append(s);
            }
            return strBuilder.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES encryption
     * 
     * @param content
     *            Content to be encrypted
     * @param encryptKey
     *            Encryption key
     * @return Encrypted byte[]
     * @throws NoSuchAlgorithmException
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) {
        KeyGenerator kgen;
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encryptKey.getBytes());
            kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
            return cipher.doFinal(content.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("msg", e);
        }
        return null;
    }

    /**
     * AES decryption
     * 
     * @param encryptBytes
     *            To be decrypted
     * @param decryptKey
     *            Decryption key
     * @return String
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(decryptKey.getBytes());

            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (Exception e) {
            log.error("msg", e);
        }
        return null;
    }

    /**
     * Converts the binary to hexadecimal
     * 
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * Converts hexadecimal to binary
     * 
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * AES encryption
     * 
     * @param content
     *            Content to be encrypted
     * @param encryptKey
     *            Encryption key
     * @return Encrypted byte[]
     * @throws NoSuchAlgorithmException
     */
    public static String aesEncrypt(String content, String encryptKey) {
        return parseByte2HexStr(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES decryption
     * 
     * @param encryptStr
     *            The String to be decrypted
     * @param decryptKey
     *            Decryption key
     * @return String
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) {
        return aesDecryptByBytes(parseHexStr2Byte(encryptStr), decryptKey);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println(encryptMd5("123456"));
        System.out.println(encryptMd5("e10adc3949ba59abbe56e057f20f883e", "lzscs"));
    }

}
