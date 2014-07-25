package com.vijayrc.security.basic.hasher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * widely used
 * not collision proof
 * not secure, brute and rainbow table attack
 */
@Component
public class SimpleHasher implements Hasher {
    private static Logger log = LogManager.getLogger(SaltyHasher.class);
    private HashAlgo algo = HashAlgo.MD5;

    @Override
    public String encrypt(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algo.key());
        md.update(password.getBytes());

        byte bytes[] = md.digest();
        log.info("hash="+new String(bytes));
        return algo.getHexStr(bytes);
    }

    @Override
    public Hasher withAlgo(HashAlgo algo){
        this.algo = algo;
        return this;
    }

}
