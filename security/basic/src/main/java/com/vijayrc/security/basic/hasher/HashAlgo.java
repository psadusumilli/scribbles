package com.vijayrc.security.basic.hasher;

public enum HashAlgo {
    MD5("MD5"),
    SHA_1("SHA-1"),
    SHA_256("SHA-256"),
    SHA_384("SHA-384"),
    SHA_512("SHA-512"),
    PBK("PBKDF2WithHmacSHA1");

    private String key;

    HashAlgo(String key) {
        this.key = key;
    }
    public String key() {
        return key;
    }
    public String getHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        return sb.toString();
    }


}
