package me.qunwei.corejava.multithread.runnable;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qunwei on 16-5-10.
 */
public class Bank {

	private Lock bankLock;
	private Condition sufficientFunds;

	private final double[] accounts;

	public Bank(int n, double initialBalance) {
		accounts = new double[n];
		for (int i = 0; i < accounts.length; i++) {
			accounts[i] = initialBalance;
		}
		bankLock = new ReentrantLock();
		sufficientFunds = bankLock.newCondition();
	}

	public void transfer(int from, int to, double amout) throws InterruptedException {

		bankLock.lock();

		try {
			while (accounts[from]<amout){
				sufficientFunds.await();
			}
			System.out.print(Thread.currentThread());
			accounts[from] -= amout;
			System.out.printf("%10.2f from %d to %d", amout, from, to);
			accounts[to] += amout;
			System.out.printf("Total Balance: %10.2f\n", getTotalBalance());
			sufficientFunds.signalAll();
		}finally {
			bankLock.unlock();
		}
	}

	public double getTotalBalance() {
		bankLock.lock();
		try {
			double sum = 0;

			for (double temp : accounts) {
				sum += temp;
			}
			return sum;
		}finally {
			bankLock.unlock();
		}
	}


    public int size(){
        return accounts.length;
    }
}
