package me.qunwei.corejava.multithread;

import me.qunwei.corejava.multithread.普通.Bank;
import me.qunwei.corejava.multithread.普通.TransferRunnable;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by qunwei on 16-5-10.
 */
public class TransferRunnableTest {

    public static final int NACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        Bank b = new Bank(NACCOUNTS,INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i++) {
            TransferRunnable rb = new TransferRunnable(b,i,INITIAL_BALANCE*20);
            Thread thread = new Thread(rb);
            thread.start();
        }
    }
}