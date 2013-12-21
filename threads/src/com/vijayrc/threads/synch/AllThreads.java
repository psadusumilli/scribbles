package com.vijayrc.threads.synch;

import static com.vijayrc.threads.synch.AllAccounts.*;
import static com.vijayrc.threads.Printer.log;

/**
 * Contrived example, don't confuse with a normal bank account
 * Account state is changed with a time-gap in between
 * Without locking, order gets messed up
 */
public class AllThreads {
    public abstract static class Base implements Runnable{
        protected Account account;
        protected void sleep(){
            try {
                Thread.sleep((long) (1000));
            } catch (InterruptedException e) {log(e);}
        }
    }

    public static class LockDeposit extends Base{
        private LockDeposit(Account account) {this.account = account;}
        @Override
        public void run() {
            for(int i=0;i<5;i++){
                synchronized (account){
                    account.transaction("deposit|");
                    sleep();
                    account.cash(100);
                    account.print();
                }
                sleep();
            }
        }
    }
    public static class LockWithDraw extends Base{
        private Account account;
        private LockWithDraw(Account account) {this.account = account;}
        @Override
        public void run() {
            for(int i=0;i<5;i++){
                synchronized (account){
                    account.transaction("withdraw|");
                    sleep();
                    account.cash(50);
                    account.print();
                }
                sleep();
            }
        }
    }
    public static class NoLockDeposit extends Base{
        private Account account;
        private NoLockDeposit(Account account) {this.account = account;}
        @Override
        public void run() {
            for(int i=0;i<5;i++){
                account.combo("deposit|",100);
                sleep();
            }
        }
    }
    public static class NoLockWithDraw extends Base{
        private Account account;
        private NoLockWithDraw(Account account) {this.account = account;}
        @Override
        public void run() {
            for(int i=0;i<5;i++){
                account.combo("withdraw|",50);
                sleep();
            }
        }
    }

    public static void main(String[] args){
        //test1();
        test2();
    }

    private static void test1() {
        log("test1:-------------------------");
        Account account = new NoLockAccount();
        Thread deposit = new Thread(new LockDeposit(account));
        Thread withdraw = new Thread(new LockWithDraw(account));
        deposit.start();
        withdraw.start();
    }
    private static void test2() {
        log("test2:-------------------------");
        Account account = new LockAccount();
        Thread deposit = new Thread(new NoLockDeposit(account));
        Thread withdraw = new Thread(new NoLockWithDraw(account));
        deposit.start();
        withdraw.start();
    }

}
