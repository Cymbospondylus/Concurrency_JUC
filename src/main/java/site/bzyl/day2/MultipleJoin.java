package site.bzyl.day2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.MultipleJoin")
public class MultipleJoin {
    private static int r1 = 0;
    private static int r2 = 0;
    public static void main(String[] args) throws InterruptedException {
        method();
    }

    private static void method() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("t1 start...");
            try {
                TimeUnit.SECONDS.sleep(1);
                r1 = 10;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t1 end...");
        });
        Thread t2 = new Thread(() -> {
            log.debug("t2 start...");
            try {
                TimeUnit.SECONDS.sleep(2);
                r2 = 10;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t2 end...");
        });

        t1.start();
        t2.start();


        log.debug("t2 join...");
        t2.join();
        log.debug("t2 join finished...");
        log.debug("t1 join...");
        t1.join();
        log.debug("t1 join finished...");

        log.debug("r1 = {}, r2 = {}", r1, r2);
    }
}
