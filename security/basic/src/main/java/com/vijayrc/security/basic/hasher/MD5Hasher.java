package com.vijayrc.security.basic.hasher;

import java.security.MessageDigest;

/**
 * widely used
 * not collision proof
 * not secure, brute and rainbow table attack
 *
 */
public class MD5Hasher implements Hasher {

    @Override
    public String encrypt(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte bytes[] = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        return sb.toString();
    }
}
