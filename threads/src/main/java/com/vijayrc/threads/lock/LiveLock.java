package com.vijayrc.threads.lock;

import static com.vijayrc.threads.util.Printer.log;

/**
 * From stackoverflow:
 * A husband and wife are trying to eat soup, but only have one spoon between them.
 * Each spouse is too polite, and will pass the spoon if the other has not yet eaten.
 * Run, you'll find both of them passing the spoon without eating indefinitely
 */
public class LiveLock {
    static class Spoon{
        private Diner owner;
        public Spoon(Diner d) { owner = d; }
        public synchronized void setOwner(Diner d) { owner = d; }
        public synchronized void use() { System.out.printf("%s has eaten!", owner.name); }
    }
    static class Diner {
        private String name;
        private boolean isHungry;
        public Diner(String n) { name = n; isHungry = true; }
        public String getName() { return name; }
        public boolean isHungry() { return isHungry; }
        public void eatWith(Spoon spoon, Diner spouse){
            while (isHungry){
                if (spoon.owner != this){
                    try { Thread.sleep(1); } catch(InterruptedException e) { continue; }
                    continue;
                }
                if (spouse.isHungry()){
                    log(name+"|You eat first my dear "+spouse.getName());
                    spoon.setOwner(spouse);
                    continue;
                }
                spoon.use();
                isHungry = false;
                log(name+"|I am stuffed, my dear "+spouse.getName());
                spoon.setOwner(spouse);
            }
        }
    }
    public static void main(String[] args){
        final Diner husband = new Diner("Bob");
        final Diner wife = new Diner("Alice");
        final Spoon s = new Spoon(husband);
        new Thread(new Runnable() { public void run() { husband.eatWith(s, wife); } }).start();
        new Thread(new Runnable() { public void run() { wife.eatWith(s, husband); } }).start();
    }
}
