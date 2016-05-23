package me.qunwei.corejava.multithread.普通;

/**
 * Created by qunwei on 16-5-10.
 */
public class TransferRunnable implements Runnable{

    private Bank bank;
    private int fromAccount;
    private double maxAmount;
    private static final int DELAY = 10;

    public TransferRunnable(Bank bank, int from, double max) {
        this.bank = bank;
        this.fromAccount = from;
        this.maxAmount = max;
    }

    public void run() {
        try {
            while (true){
                int toAccount = (int) (bank.size() * Math.random());
                double amount = maxAmount * Math.random();
                bank.transfer(fromAccount,toAccount,amount);
                Thread.sleep((long) (DELAY * Math.random()));
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Bank b = new Bank(100,1000);
        for (int i = 0; i < 100; i++) {
            TransferRunnable rb = new TransferRunnable(b,i,1000*20);
            Thread thread = new Thread(rb);
            thread.start();
        }
    }
}
