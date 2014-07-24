package com.vijayrc.security.basic.hasher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class HasherTest {
    private static Logger log = LogManager.getLogger(HasherTest.class);

    @Test
    public void shouldDoMD5() throws Exception {
        Hasher hasher = new MD5Hasher();
        log.info(hasher.encrypt("akk1214"));
    }

}
