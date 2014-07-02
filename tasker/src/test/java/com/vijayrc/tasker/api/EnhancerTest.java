
package com.vijayrc.tasker.api;

import com.vijayrc.meta.Bean;
import com.vijayrc.meta.NoArgsConstr;
import com.vijayrc.meta.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.reflections.Reflections;

public class EnhancerTest {
    private static Logger log = LogManager.getLogger(EnhancerTest.class);
    @Test
    public void shouldPrintAnnotatedClasses(){
        Reflections reflections = new Reflections("com.vijayrc.tasker");
        reflections.getTypesAnnotatedWith(ToString.class).forEach(t ->log.info(t.getName()));
        log.info("--------------");
        reflections.getTypesAnnotatedWith(Bean.class).forEach(t -> log.info(t.getName()));
        log.info("--------------");
        reflections.getTypesAnnotatedWith(NoArgsConstr.class).forEach(t ->log.info(t.getName()));

    }
}
