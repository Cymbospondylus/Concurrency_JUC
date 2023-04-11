package site.bzyl.day2;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ThreadSleep")
public class ThreadSleep {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        log.debug("t1 state: {}", t1.getState());

        t1.start();
        log.debug("t1 state: {}", t1.getState());

        Thread.sleep(500);
        log.debug("t1 state: {}", t1.getState());
    }
}
