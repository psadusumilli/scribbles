package com.vijayrc.security.basic.hasher;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Bruce Schneier's Blowfish cipher
 * The amount of work increases exponentially (2**log_rounds), so each increment is twice as much work.
 * The default log_rounds is 10, and the valid range is 4 to 31.
 * again salt must be persisted
 */
public class BScryptHasher implements Hasher {

    private int iterations = 10;

    @Override
    public String encrypt(String password) throws Exception {
        return BCrypt.hashpw(password, BCrypt.gensalt(iterations));
    }

    public BScryptHasher withIterations(int iterations){
        this.iterations = iterations;
        return this;
    }

    @Override
    public Hasher withAlgo(HashAlgo algo) {
        return this;
    }
}
