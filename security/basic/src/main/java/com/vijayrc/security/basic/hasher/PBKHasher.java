package com.vijayrc.security.basic.hasher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;

/**
 * just slows any brute force by slowing down the hash generation
 * iterations determine how slow
 */
@Component
public class PBKHasher implements Hasher{
    private static Logger log = LogManager.getLogger(PBKHasher.class);
    private HashAlgo algo = HashAlgo.PBK;
    private int iterations = 1000;

    @Override
    public String encrypt(String password) throws Exception {
        char[] chars = password.toCharArray();
        byte[] salt = salt().getBytes();
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(algo.key());
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return algo.getHexStr(hash);
    }

    @Override
    public Hasher withAlgo(HashAlgo algo) {
        this.algo = algo;
        return this;
    }

    public Hasher withIterations(int iterations){
        this.iterations = iterations;
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
