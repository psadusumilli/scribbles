package com.vijayrc.springboot.controllers;

import com.vijayrc.springboot.domain.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.ChunkedInputStream;
import sun.net.www.http.ChunkedOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;

/**
 * Created by vijayrc on 4/24/15.
 */
@RestController
public class MyRestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public List<Greeting> greeting(@RequestParam(value="name", defaultValue="World") String name) {
        List<Greeting> greetings = new ArrayList<Greeting>();
        for(int i=0;i<7;i++)
            greetings.add(new Greeting(counter.incrementAndGet(),  format(template, name)));
        return greetings;
    }

    @RequestMapping("/greeting-small")
    public void chunked(@RequestParam(value="name", defaultValue="World") String name, HttpServletResponse response) throws IOException, InterruptedException {
       response.setHeader("Transfer-Encoding","chunked");
        ServletOutputStream outputStream = response.getOutputStream();
        ChunkedOutputStream chunkedOutputStream = new ChunkedOutputStream(new PrintStream(outputStream));
        for(int i=0;i<5;i++){
            String s = new Greeting(counter.incrementAndGet(), format(template, name)).getContent() + new Date() + "\n";
            chunkedOutputStream.write(s.getBytes());
            chunkedOutputStream.flush();
            Thread.sleep(1000);
        }
        chunkedOutputStream.flush();
        chunkedOutputStream.close();
        outputStream.close();
    }
}
