package com.vijayrc.threads.api;

import org.apache.commons.lang.time.StopWatch;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static com.vijayrc.threads.util.Printer.log;

/**
 * Use of fork-join to calculate fibonacci number at index n (f(10)=55,f(1)=1)
 * I have 4 logical processors
 */
public class Forker {
    public static class Task {
        private long n;
        public Task(long n) {this.n = n;}
        public boolean isSmall(){return n < 5;}

        public Long compute(){
            Long output = recurse(n);
            //log(Thread.currentThread()+"|"+output);
            return output;
        }
        public Long recurse(Long n){
            if(n <= 1 )return n;
            else return recurse(n-1)+recurse(n-2);
        }
    }
    public static class Worker extends RecursiveTask<Long>{
        private Task task;
        private Long result;
        public Long result() {return result;}
        public Worker(Task task) {this.task = task;}

        @Override
        protected Long compute() {
            if(task.isSmall())
                result = task.compute();
            else{
                Worker w1 = new Worker(new Task(task.n - 1));
                Worker w2 = new Worker(new Task(task.n - 2));
                w1.fork();
                result = w2.compute()+w1.join();
            }
            return result;
        }
    }

    public static void main(String[] args){
        withoutWorkers();
        withWorkers();
        log("main end");
    }

    private static void withoutWorkers() {
        StopWatch watch = new StopWatch();
        watch.start();
        Long output = new Task(30).compute();
        watch.stop();
        log("without workers=" + output+"|time="+watch.getTime());
    }

    private static void withWorkers() {
        int processors = Runtime.getRuntime().availableProcessors();
        log("no of processors: " + processors);
        ForkJoinPool pool = new ForkJoinPool(processors);
        Worker worker = new Worker(new Task(30));

        StopWatch watch = new StopWatch();
        watch.start();
        pool.invoke(worker);
        watch.stop();
        log("with workers=" + worker.result()+"|time="+watch.getTime());
    }
}
