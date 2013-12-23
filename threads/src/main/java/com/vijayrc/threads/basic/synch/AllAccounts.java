package com.vijayrc.threads.basic.synch;

import static com.vijayrc.threads.util.Printer.log;

public class AllAccounts {
    public interface Account{
        void cash(int cash);
        void transaction(String msg);
        void print();
        void combo(String msg, int cash);
    }
    public static abstract class BaseAccount implements Account{
        protected int amount;
        protected String transaction;
        @Override
        public void print(){log(transaction+" "+amount);}
    }
    public static class NoLockAccount extends BaseAccount{
        @Override
        public void cash(int cash){amount = cash;}
        @Override
        public void transaction(String msg) {transaction = msg;}
        @Override
        public void combo(String msg, int cash) {
            amount = cash;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {log(e);}
            transaction=msg;
            print();
        }
    }
    public static class LockAccount extends BaseAccount{
        @Override
        public synchronized void cash(int cash){amount = cash;}
        @Override
        public synchronized void transaction(String msg) {transaction = msg;}
        @Override
        public synchronized void combo(String msg, int cash) {
            amount = cash;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {log(e);}
            transaction=msg;
            print();
        }
    }
    //static method synchronisation is based on class objects
    public synchronized static void dummy(){log("dummy");}
}
