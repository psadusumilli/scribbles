package com.vijayrc.enhancer;

import com.vijayrc.dummies.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class ToStringEnhancerTest {
    private static Logger log = LogManager.getLogger(ToStringEnhancerTest.class);
    private ToStringEnhancer enhancer;

    @Before
    public void setup(){
        enhancer = new ToStringEnhancer();
    }
    @Test
    public void shouldEmbedToStringMethod() throws Exception {
        enhancer.run("com.vijayrc.dummies");
        Employee e = new Employee(11l,"Paul Walker","Actor",new Date());
        log.info(e);
    }

}
