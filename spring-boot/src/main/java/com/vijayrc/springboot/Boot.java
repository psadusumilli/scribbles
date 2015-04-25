package com.vijayrc.springboot;

import com.vijayrc.springboot.controllers.MyRestController;
import com.vijayrc.springboot.controllers.MySimpleController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by vijayrc on 4/24/15.
 */
@SpringBootApplication
public class Boot {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Boot.class, args);
    }
}
