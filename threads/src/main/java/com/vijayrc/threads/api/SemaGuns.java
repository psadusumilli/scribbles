package com.vijayrc.threads.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static com.vijayrc.threads.util.Printer.log;
import static java.lang.Math.random;
import static java.util.Arrays.asList;

/**
 * Battle of Stalingrad: Russian soldiers wih limited ammo
 * Staying alive means reusing guns returned to ammo from dead soldiers
 * The ammo is guarded by a semaphore storekeeper who lets in soldiers as per weapon availability
 * The ammo also has a death ray guarded by a mutexKeeper (a mutex is just a semaphore of size 1)
 */
public class SemaGuns {
    /**
     * machine of war
     */
    public static class Weapon{
        private String name;
        private boolean inNotInUse = true;
        public Weapon(String name) {this.name = name;}
        public boolean isNotInUse(){return inNotInUse;}
        public void returnToStore(){inNotInUse = true;}
        public void givenToSoldier(){inNotInUse = false;}
        @Override
        public String toString() {return name;}
        @Override
        public boolean equals(Object obj) {return this.name.equals(((Weapon)obj).name);}
    }
    /**
     * Ammo store with just 5 weapons
     * Semaphore storekeeper lets in only 5 soldiers max
     * A solider thread will be kept waiting at 'storekeeper.acquire" when weapons are maxed out
     * however this does not stop a soldier from returning at the same instant.
     * 'synchronized' is still required at 'weapons' list datastructure manipulation level
     */
    public static class Ammo{
        private List<Weapon> weapons = new ArrayList<>();
        private Semaphore storeKeeper = new Semaphore(5,true);
        private Semaphore mutexKeeper = new Semaphore(1,true);

        public Ammo() {
            for (String name : asList("ak47", "uzi", "knife", "bazooka", "rifle"))
              weapons.add(new Weapon(name));
        }
        public Weapon give() throws Exception {
            storeKeeper.acquire();
            return fetchWeapon();
        }
        public void take(Weapon weapon) throws Exception {
            findAndRestore(weapon);
            storeKeeper.release();//release only when weapon is restored, change order, you'll get null-pointers
        }
        public void giveRay() throws Exception {
            mutexKeeper.acquire();
        }
        public void takeRay()throws Exception{
            mutexKeeper.release();
        }
        private synchronized void findAndRestore(Weapon weapon) {
            for (Weapon weapon1 : weapons)
                if(weapon.equals(weapon1))
                    weapon1.returnToStore();
        }
        private synchronized Weapon fetchWeapon() {
            for (Weapon weapon : weapons)
              if(weapon.isNotInUse()){
                  weapon.givenToSoldier();
                  return weapon;
              }
            log("maxed out!");
            return null;
        }
    }
    /**
     * soldier picks a weapon if available, or waits before storekeeper
     * fights for a random time, restores weapon to ammo
     */
    public static class Soldier implements Runnable{
        private Ammo ammo;
        private String name;

        public Soldier(String name, Ammo ammo) {this.name = name; this.ammo = ammo;}
        private void fight() throws InterruptedException {Thread.sleep((int) (random() * 3000)); }
        private boolean eligible() {return random() %2 == 0;}

        @Override
        public void run() {
            log(name+"|waiting for ammo");
            try {
                Weapon weapon = ammo.give();
                log(name+"|got "+weapon);
                fight();
                log(name+"|returned " + weapon);
                ammo.take(weapon);

                if(eligible()){
                    ammo.giveRay();
                    log(name+"|death ray, kill all nazis!!");
                    fight();
                    log(name+"|death ray depleted");
                    ammo.takeRay();
                }
            } catch (Exception e) {log(e);}
        }
    }
    /**
     * War begins
     */
    public static void main(String[] args) throws Exception {
        int armySize = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(armySize);
        try {
            final Ammo ammo = new Ammo();
            for(int i=1;i<=armySize;i++)
                executorService.submit(new Soldier("P-"+i,ammo));
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } finally {
            executorService.shutdown();
            log("war over");
        }
    }
}
