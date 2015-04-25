package com.vijayrc.springboot.controllers;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by vijayrc on 4/24/15.
 */
@Controller
@EnableAutoConfiguration
public class MySimpleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

}
