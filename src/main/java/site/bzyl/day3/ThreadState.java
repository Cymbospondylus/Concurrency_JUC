package site.bzyl.day3;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ThreadState")
public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("running...");
        }, "t1");

        Thread t2 = new Thread(() -> {
            while (true) ;
        }, "t2");
        t2.start();

        Thread t3 = new Thread(() -> {
            log.debug("running...");
        }, "t3");
        t3.start();

        Thread t4 = new Thread(() -> {
            synchronized (ThreadState.class) {
                try {
                    Thread.sleep(100000);   // timed_waiting
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t4.start();

        Thread t5 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t5");
        t5.start();

        Thread t6 = new Thread(() -> {
            synchronized(ThreadState.class) {
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t6");
        t6.start();

        Thread.sleep(500);

        log.debug("t1 state: {}", t1.getState());
        log.debug("t2 state: {}", t2.getState());
        log.debug("t3 state: {}", t3.getState());
        log.debug("t4 state: {}", t4.getState());
        log.debug("t5 state: {}", t5.getState());
        log.debug("t6 state: {}", t6.getState());
    }
}
