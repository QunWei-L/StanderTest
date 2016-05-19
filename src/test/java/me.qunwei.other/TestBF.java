package me.qunwei.other;

import org.junit.Test;

/**
 * Created by qunwei on 16-5-7.
 */
public class TestBF {

    @Test
    public void test100Thread() throws Exception {
        //同时启动1000个线程，去进行i++计算，看看实际结果

        final Boolean tag = Boolean.FALSE;

        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                public void run() {
                    Counter.inc(tag);
                }
            }).start();
        }



        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + Counter.count);
    }




}
class Counter {

    public static volatile int count = 0;

    public static void inc(Boolean tag) {

        //这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
        count++;
        tag.notifyAll();
    }

}