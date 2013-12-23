package com.vijayrc.threads.performance;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.concurrent.*;

import static com.vijayrc.threads.util.Printer.log;

/**
 *
 */
public class WithCallBack {
    public static final int timeout = 5;

    public static class User implements Callable<String>{
        private HttpClient httpclient = new DefaultHttpClient();
        private StopWatch watch = new StopWatch();
        private String link;
        private String name;

        public User(String link, String name) {
            this.link = link;
            this.name = name;
        }
        @Override
        public String call() throws Exception {
            watch.start();
            HttpGet httpget = new HttpGet(link);
            HttpResponse response = httpclient.execute(httpget);
            watch.stop();

            InputStream inputStream = response.getEntity().getContent();
            int statusCode = response.getStatusLine().getStatusCode();
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, "UTF-8");

            log(name+"|"+link+"|"+ statusCode +"|"+watch.getTime()/1000);
            return writer.toString();
        }
    }

    public static void main(String[] args) throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(10);
        try {
            Future[] futures = new Future[5];
            for(int i =0;i<5;i++)
               futures[i] = executor.submit(new User("http://google.com","c"+i));
            for(Future future:futures) {
                int length = future.get(5, TimeUnit.SECONDS).toString().length();
                log("response="+ length);
            }
        } finally {
            executor.shutdown();//shutdown is required or the daemon threads in pool wont let main thread die.
        }

    }
}
