package com.vijayrc.security.basic.hasher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class HasherTest {
    private static Logger log = LogManager.getLogger(HasherTest.class);

    @Test
    public void shouldDoWithoutSalt() throws Exception {
        final SimpleHasher hasher = new SimpleHasher();
        log.info(hasher.encrypt("akk1214"));
        log.info(hasher.withAlgo(HashAlgo.SHA_256).encrypt("akk1214"));
        log.info(hasher.withAlgo(HashAlgo.SHA_384).encrypt("akk1214"));
        log.info(hasher.withAlgo(HashAlgo.SHA_512).encrypt("akk1214"));
    }
    @Test
    public void shouldDoWithSalt() throws Exception {
        final Hasher hasher = new SaltyHasher();
        log.info(hasher.encrypt("akk1214"));
        log.info(hasher.withAlgo(HashAlgo.SHA_256).encrypt("akk1214"));
        log.info(hasher.withAlgo(HashAlgo.SHA_384).encrypt("akk1214"));
        log.info(hasher.withAlgo(HashAlgo.SHA_512).encrypt("akk1214"));
    }
    @Test
    public void shouldDoPBK() throws Exception {
        final PBKHasher hasher = new PBKHasher();
        log.info(hasher.withIterations(1000).encrypt("akk1214"));
    }
    @Test
    public void shouldDoBScrypt() throws Exception {
        final BScryptHasher hasher = new BScryptHasher();
        log.info(hasher.withIterations(4).encrypt("akk1214"));
    }
}
