package com.vijayrc.threads.api;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.vijayrc.threads.util.Printer.log;

/**
 * In this race, every racer waits on the gun
 * then they start running at random pace
 * finally the race gets over when the countdown latch is depleted
 */
public class Race {
    private final CountDownLatch gun = new CountDownLatch(1);
    private final CountDownLatch racers = new CountDownLatch(5);
    public void gunWait() throws Exception {gun.await();}
    public void gunStart() throws Exception {Thread.sleep(1000);gun.countDown();}
    public void racerDown() throws Exception {racers.countDown();}
    public void waitOnRacers() throws Exception {racers.countDown();}

    public static class Runner implements Runnable{
        private String name;
        private Race race;
        public Runner(String name, Race race) {this.name = name;this.race = race;}
        @Override
        public void run() {
            try {
                log(name+"|waiting on the gun..");
                race.gunWait();
                log(name+"|started running");
                Thread.sleep((long) (Math.random()*3000));
                log(name+"|finished");
                race.racerDown();
            } catch (Exception e) {log(e);}
        }
    }
    public static void main(String[] args) throws Exception {
        Race race = new Race();
        ExecutorService executors = Executors.newFixedThreadPool(5);
        try {
            for(int i=1;i<6;i++)
                executors.submit(new Runner("R-"+i, race));
            race.gunStart();
            log("race started");
            race.waitOnRacers();
        } finally {
            executors.shutdown();
            log("race ended!");
        }
    }

}
