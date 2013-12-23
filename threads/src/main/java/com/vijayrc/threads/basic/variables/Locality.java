package com.vijayrc.threads.basic.variables;

import static com.vijayrc.threads.util.Printer.log;

/**
 * look inside ThreadLocal Api, you will find a ThreadLocal <Thread,Object>
 * Each client should see unique amount they deposited in a single instance of Bank
 */
public class Locality {
    public static class Bank{
        private ThreadLocal<Double> account = new ThreadLocal<>();
        public void deposit(Double money){ account.set(money);}
        public Double withdraw(){return account.get();}
    }
    public static class Client extends Thread{
        private Bank bank;
        public Client(String name, Bank bank) {super(name); this.bank = bank;}
        @Override
        public void run() {
            Double deposit = Math.random()*1000;
            bank.deposit(deposit);
            log(getName()+"|deposit|"+deposit);
            try {Thread.sleep(3000);
            } catch (InterruptedException e) {log(e);}
            log(getName() + "|withdraw|" + bank.withdraw());
        }
    }
    public static void main(String args[]) throws InterruptedException {
        Bank bank = new Bank();
        Client c1 = new Client("c1",bank);
        Client c2 = new Client("c2",bank);
        Client c3 = new Client("c3",bank);
        c1.start();c2.start();c3.start();
        c1.join();c2.join();c2.join();
    }

}
