package com.vijayrc.security.basic.hasher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.SecureRandom;

import static com.vijayrc.security.basic.hasher.HashAlgo.MD5;
import static java.util.Arrays.toString;
import static java.util.Arrays.toString;

/**
 * a unique random salt is attached to every hash created.
 * this prevents rainbow table attacks
 * salt must be persisted per password to match
 */
@Component
public class SaltyHasher implements Hasher {
    private static Logger log = LogManager.getLogger(SaltyHasher.class);
    private HashAlgo algo = HashAlgo.MD5;

    @Override
    public String encrypt(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algo.key());
        md.update(salt().getBytes());
        byte bytes[] = md.digest(password.getBytes());
        return algo.getHexStr(bytes);
    }

    @Override
    public Hasher withAlgo(HashAlgo algo) {
        this.algo = algo;
        return this;
    }

    private String salt() throws Exception {
        byte[] bytes = new byte[16];
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
        secureRandom.nextBytes(bytes);
        String salt = new String(bytes);
        log.info("salt="+ salt);
        return salt;
    }
}
