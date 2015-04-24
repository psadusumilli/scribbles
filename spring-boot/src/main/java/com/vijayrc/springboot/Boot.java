package com.vijayrc.springboot;

import com.vijayrc.springboot.controllers.MyController;
import org.springframework.boot.SpringApplication;

/**
 * Created by vijayrc on 4/24/15.
 */
public class Boot {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyController.class, args);
    }
}
