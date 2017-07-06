package com.hengtiansoft.common.util;

import java.security.MessageDigest;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class SHACoder {
    /**
     * SHA-1 Message digest algorithm
     */
    public static String encodeSHA(byte[] data) throws Exception {
        // Initialize MessageDigest, SHA is short for SHA-1
        MessageDigest md = MessageDigest.getInstance("SHA");
        // Executive summary method
        byte[] digest = md.digest(data);
        return new HexBinaryAdapter().marshal(digest);
    }

    /**
     * SHA-256 Message digest algorithm
     */
    public static String encodeSHA256(byte[] data) throws Exception {
        // Initialize MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // Executive summary method
        byte[] digest = md.digest(data);
        return new HexBinaryAdapter().marshal(digest);
    }

    /**
     * SHA-384 Message digest algorithm
     */
    public static String encodeSHA384(byte[] data) throws Exception {
        // Initialize MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-384");
        // Executive summary method
        byte[] digest = md.digest(data);
        return new HexBinaryAdapter().marshal(digest);
    }

    /**
     * SHA-512 Message digest algorithm
     */
    public static String encodeSHA512(byte[] data) throws Exception {
        // Initialize MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        // Executive summary method
        byte[] digest = md.digest(data);
        return new HexBinaryAdapter().marshal(digest);
    }
}
