package com.vijayrc.scribbles.radio;

import com.vijayrc.scribbles.radio.repository.AllLocations;
import lombok.extern.log4j.Log4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Log4j
public class RadioStation {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AllLocations allLocations = (AllLocations) context.getBean("allLocations");
        log.info(allLocations);
    }
}
