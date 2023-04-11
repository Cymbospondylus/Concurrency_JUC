package site.bzyl.day2;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ThreadInterrupt")
public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("enter sleeping...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.debug("interrupted...");
                    e.printStackTrace();
                }
            }
        };

        t1.start();

        Thread.sleep(1000);
        log.debug("interrupt...");
        t1.interrupt();
    }
}
