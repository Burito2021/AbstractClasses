package net.education.kyivstar.examples;

import net.education.kyivstar.threads.CustomThread;

public class Extra {
    public static void main(String[] args) throws InterruptedException {
//        Thread thread = new Thread(new CustomerRunnable());
//        thread.setDaemon(true);
//        thread.start();


        var t2 = new CustomThread();
        t2.start();
        var t3 = new CustomThread();
        t3.start();
        var t4 = new CustomThread();
        t4.start();
//        t2.setDaemon(false);
//        var t3 = new CustomThread();
//        t2.start();
//
//        t2.interrupt();
//        t2.join();
//        t3.start();
    }
}
