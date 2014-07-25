package com.vijayrc.security.basic.hasher;

public interface Hasher {
    String encrypt(String password) throws Exception;
    Hasher withAlgo(HashAlgo algo);
}
