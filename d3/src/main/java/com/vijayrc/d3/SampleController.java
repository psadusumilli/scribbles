package com.vijayrc.d3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleController {

    @RequestMapping("/{sample}")
    public ModelAndView show(@PathVariable String sample){
        return new ModelAndView("layout").addObject("sample",sample);
    }

}
